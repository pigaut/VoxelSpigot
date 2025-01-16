package io.github.pigaut.voxel.command.parameter;

import io.github.pigaut.voxel.command.completion.*;
import io.github.pigaut.voxel.util.*;
import org.bukkit.command.*;
import org.jetbrains.annotations.*;

import java.util.*;

public class CommandParameter implements CommandCompletion {

    private final String name;
    private final boolean optional;
    private final String defaultValue;
    private final CommandCompletion parameterCompletions;

    public CommandParameter(@NotNull String name) {
        this(name, false, null, null);
    }

    public CommandParameter(@NotNull String name, CommandCompletion parameterCompletions) {
        this(name, false, null, parameterCompletions);
    }

    public CommandParameter(@NotNull String name, boolean optional) {
        this(name, optional, null, null);
    }

    public CommandParameter(@NotNull String name, boolean optional, String defaultValue) {
        this(name, optional, defaultValue, null);
    }

    public CommandParameter(@NotNull String name, boolean optional, String defaultValue, CommandCompletion parameterCompletions) {
        this.name = name;
        this.optional = optional;
        this.defaultValue = defaultValue;
        this.parameterCompletions = parameterCompletions;
    }

    public String getName() {
        return name;
    }

    public boolean isOptional() {
        return optional;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    @Override
    public @NotNull List<@NotNull String> tabComplete(CommandSender sender, String[] args) {
        return parameterCompletions != null ? parameterCompletions.tabComplete(sender, args) : List.of();
    }

    @Override
    public String toString() {
        return optional ? "(" + name + ")" : "<" + name + ">";
    }

    public static boolean isParameter(String name) {
        return StringUtil.isParenthesized(name, "(", ")") ||
                StringUtil.isParenthesized(name, "<", ">");
    }

}
