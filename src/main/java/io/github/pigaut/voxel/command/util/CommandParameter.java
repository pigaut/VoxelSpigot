package io.github.pigaut.voxel.command.util;

import io.github.pigaut.voxel.util.*;

public record CommandParameter(String name, boolean optional, String defaultValue) {

    @Override
    public String toString() {
        return optional ? "(" + name + ")" : "<" + name + ">";
    }

    public static boolean isParameter(String name) {
        return StringUtil.isParenthesized(name, "(", ")") ||
                StringUtil.isParenthesized(name, "<", ">");
    }

}
