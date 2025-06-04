package io.github.pigaut.voxel.util;

import java.util.*;

public class StringUtil {

    public static boolean isParenthesized(String str, String parenthesis) {
        return isParenthesized(str, parenthesis, parenthesis);
    }

    public static boolean isParenthesized(String str, String prefix, String suffix) {
        if (str == null || str.isEmpty()) {
            return false;
        }
        return str.startsWith(prefix) && str.endsWith(suffix);
    }

    public static String addParentheses(String str, char parenthesis) {
        return addParentheses(str, parenthesis, parenthesis);
    }

    public static String addParentheses(String str, char prefix, char suffix) {
        return prefix + str + suffix;
    }

    public static String removeParentheses(String str) {
        return str.substring(1, str.length() - 1);
    }

    public static String removeTag(String str, String tag) {
        return str.replace(tag, "").trim();
    }

    public static String buildString(Object... elements) {
        StringBuilder builder = new StringBuilder();
        Arrays.stream(elements)
                .filter(Objects::nonNull)
                .forEach(builder::append);
        return builder.toString();
    }

    public static List<String> splitByLength(String string, int length) {
        List<String> result = new ArrayList<>();
        for (int i = 0; i < string.length(); i += length) {
            result.add(string.substring(i, Math.min(i + length, string.length())));
        }
        return result;
    }

}
