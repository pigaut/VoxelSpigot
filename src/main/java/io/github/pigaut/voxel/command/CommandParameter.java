package io.github.pigaut.voxel.command;

import io.github.pigaut.voxel.command.completion.*;
import io.github.pigaut.voxel.util.*;
import org.bukkit.command.*;
import org.jetbrains.annotations.*;

import java.util.*;

public interface CommandParameter extends CommandCompletion {

    String getName();

    default boolean isOptional() {
        return false;
    }

    default @Nullable String getDefaultValue() {
        return null;
    }

    static boolean isParameter(String name) {
        return StringUtil.isParenthesized(name, "(", ")") ||
                StringUtil.isParenthesized(name, "<", ">");
    }

    static CommandParameter create(String name) {
        return create(name, false, null, null);
    }

    static CommandParameter create(String name, boolean optional) {
        return create(name, optional, null, null);
    }

    static CommandParameter create(String name, boolean optional, String defaultValue) {
        return create(name, optional, defaultValue, null);
    }

    static CommandParameter create(String name, CommandCompletion completions) {
        return create(name, false, null, completions);
    }

    static CommandParameter create(String name, boolean optional, CommandCompletion completions) {
        return create(name, optional, null, completions);
    }

    static CommandParameter create(String name, boolean optional, @Nullable String defaultValue, @Nullable CommandCompletion completions) {
        return new CommandParameter() {
            @Override
            public String getName() {
                return name;
            }

            @Override
            public boolean isOptional() {
                return optional;
            }

            @Override
            public @Nullable String getDefaultValue() {
                return defaultValue;
            }

            @Override
            public @NotNull List<@NotNull String> tabComplete(CommandSender sender, String[] args) {
                return completions != null ? completions.tabComplete(sender, args) : List.of();
            }

            @Override
            public String toString() {
                return optional ? "(" + name + ")" : "<" + name + ">";
            }
        };
    }

}
