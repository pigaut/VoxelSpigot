package io.github.pigaut.voxel.meta.placeholder;

import io.github.pigaut.voxel.util.*;
import io.github.pigaut.yaml.parser.*;
import org.jetbrains.annotations.*;

import java.util.*;
import java.util.regex.*;

public class Placeholder implements StringFormatter {

    private final String id;
    private final Map<String, StringFormatter> formatIds = new HashMap<>();
    private final String value;

    private Placeholder(String id) {
        this.id = id;
        this.value = "";
    }

    private Placeholder(@NotNull String id, @NotNull String value) {
        this.id = id;
        this.value = Matcher.quoteReplacement(value);
        final String idName = StringUtil.removeParentheses(id);
        for (StringStyle style : StringStyle.values()) {
            formatIds.put(StringUtil.buildString("%", idName, "_", style.getTagName(), "%"), style);
        }
    }

    @NotNull
    public static Placeholder of(@NotNull String id, @Nullable Object object) {
        if (!StringUtil.isParenthesized(id, "%", "%")) {
            throw new IllegalArgumentException("Placeholder id must start and end with '%'");
        }
        if (object == null) {
            return new Placeholder(id);
        }
        return new Placeholder(id, object.toString());
    }

    @NotNull
    public static Placeholder fromName(@NotNull String name, @Nullable Object object) {
        if (StringUtil.isParenthesized(name, "%", "%")) {
            return of(name, object);
        }
        name = StringUtil.addParentheses(name, "%", "%");
        if (object == null) {
            return new Placeholder(name);
        }
        return new Placeholder(name, object.toString());
    }

    @NotNull
    public static Placeholder of(@NotNull String id, @Nullable Object object, @NotNull String def) {
        if (!StringUtil.isParenthesized(id, "%", "%")) {
            throw new IllegalArgumentException("Placeholder id must start and end with '%'");
        }
        if (object == null) {
            return new Placeholder(id, def);
        }
        return new Placeholder(id, object.toString());
    }

    @Override
    public @NotNull String format(@NotNull String str) {
        str = str.replaceAll(id, value);
        for (Map.Entry<String, StringFormatter> entry : formatIds.entrySet()) {
            str = str.replaceAll(entry.getKey(), entry.getValue().format(value));
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
