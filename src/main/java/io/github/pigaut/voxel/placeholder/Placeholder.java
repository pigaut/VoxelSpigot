package io.github.pigaut.voxel.placeholder;

import io.github.pigaut.voxel.util.*;
import io.github.pigaut.yaml.convert.format.*;
import org.jetbrains.annotations.*;

import java.util.*;
import java.util.regex.*;

public class Placeholder implements StringFormatter {

    private final String id;
    private final Map<String, StringFormatter> formatIds = new HashMap<>();
    private final String value;

    public Placeholder(@NotNull String id, @NotNull String value) {
        this.id = id;
        this.value = value;
        final String idName = StringUtil.removeParentheses(id);
        for (CaseStyle style : CaseStyle.values()) {
            final String styleId = StringUtil.buildString(id.charAt(0), idName, "_", style.getTagName(), id.charAt(id.length() - 1));
            formatIds.put(styleId, style);
        }
    }

    @NotNull
    public static Placeholder of(@NotNull String id, @Nullable Object value) {
        return of(id, value, "");
    }

    @NotNull
    public static Placeholder of(@NotNull String id, @Nullable Object value, @NotNull String def) {
        return new Placeholder(id, value != null ? value.toString() : def);
    }

    @NotNull
    public static Placeholder create(@NotNull String name, char parenthesis, @Nullable Object value) {
        return create(name, parenthesis, value, "");
    }

    @NotNull
    public static Placeholder create(@NotNull String name, char parenthesis, @Nullable Object value, @NotNull String def) {
        final String id = StringUtil.addParentheses(name, parenthesis);
        return new Placeholder(id, value != null ? value.toString() : def);
    }

    @NotNull
    public static Placeholder create(@NotNull String name, char prefix, char suffix, @Nullable Object value) {
        return create(name, prefix, suffix, value, "");
    }

    @NotNull
    public static Placeholder create(@NotNull String name, char prefix, char suffix, @Nullable Object value, @NotNull String def) {
        final String id = StringUtil.addParentheses(name, prefix, suffix);
        return new Placeholder(id, value != null ? value.toString() : def);
    }

    @NotNull
    public static Placeholder[] mergeAll(Placeholder[] placeholdersA, Placeholder... placeholdersB) {
        final Placeholder[] mergedPlaceholders = new Placeholder[placeholdersA.length + placeholdersB.length];
        System.arraycopy(placeholdersA, 0, mergedPlaceholders, 0, placeholdersA.length);
        System.arraycopy(placeholdersB, 0, mergedPlaceholders, placeholdersA.length, placeholdersB.length);
        return mergedPlaceholders;
    }

    @Override
    public @NotNull String format(@NotNull String str) {
        str = str.replace(id, value);
        for (Map.Entry<String, StringFormatter> entry : formatIds.entrySet()) {
            str = str.replace(entry.getKey(), entry.getValue().format(value));
        }
        return str;
    }

    @Override
    public String toString() {
        return "Placeholder{" +
                "id='" + id + '\'' +
                ", value='" + value + '\'' +
                '}';
    }

}
