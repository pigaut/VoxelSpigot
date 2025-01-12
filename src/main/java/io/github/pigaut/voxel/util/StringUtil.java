package io.github.pigaut.voxel.util;

import io.github.pigaut.yaml.*;

import java.util.*;

public class StringUtil {

    public static boolean isParenthesized(String str, String prefix, String suffix) {
        if (str == null || str.isEmpty()) {
            return false;
        }
        return str.startsWith(prefix) && str.endsWith(suffix);
    }

    public static String addParentheses(String str, String prefix, String suffix) {
        return prefix + str + suffix;
    }

    public static String removeParentheses(String str) {
        return str.substring(1, str.length() - 1);
    }

    public static String removeTag(String str, String tag) {
        return str.replace(tag, "").trim();
    }

    public static String buildString(String... elements) {
        StringBuilder builder = new StringBuilder();
        Arrays.stream(elements)
                .filter(Objects::nonNull)
                .forEach(builder::append);
        return builder.toString();
    }

}
