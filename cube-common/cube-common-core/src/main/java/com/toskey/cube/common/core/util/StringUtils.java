package com.toskey.cube.common.core.util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * StringUtils
 *
 * @author lis
 * @version 1.0
 * @description TODO
 * @date 2024/6/12 15:34
 */
public class StringUtils extends org.apache.commons.lang3.StringUtils {

    private static final List<Character> ESCAPE_CHAR = Arrays.asList(new Character[]{'$', '(', ')', '*', '+', '.', '[', ']', '?', '\\', '^', '{', '}', '|'});

    /**
     * 下划线
     */
    private static final char SEPARATOR = '_';

    /**
     * 驼峰转下划线命名
     */
    public static String toUnderScoreCase(String str) {
        if (str == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        // 前置字符是否大写
        boolean preCharIsUpperCase = true;
        // 当前字符是否大写
        boolean curreCharIsUpperCase = true;
        // 下一字符是否大写
        boolean nexteCharIsUpperCase = true;
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (i > 0) {
                preCharIsUpperCase = Character.isUpperCase(str.charAt(i - 1));
            } else {
                preCharIsUpperCase = false;
            }

            curreCharIsUpperCase = Character.isUpperCase(c);

            if (i < (str.length() - 1)) {
                nexteCharIsUpperCase = Character.isUpperCase(str.charAt(i + 1));
            }

            if (preCharIsUpperCase && curreCharIsUpperCase && !nexteCharIsUpperCase) {
                sb.append(SEPARATOR);
            } else if ((i != 0 && !preCharIsUpperCase) && curreCharIsUpperCase) {
                sb.append(SEPARATOR);
            }
            sb.append(Character.toLowerCase(c));
        }

        return sb.toString();
    }

    public static String escape(String str) {
        if (StringUtils.isBlank(str)) {
            return str;
        }

        final StringBuilder builder = new StringBuilder();
        int len = str.length();
        char current;
        for (int i = 0; i < len; i++) {
            current = str.charAt(i);
            if (ESCAPE_CHAR.contains(current)) {
                builder.append('\\');
            }
            builder.append(current);
        }
        return builder.toString();
    }

    public static String toUpperCase(String src, int index) {
        char[] chars = src.toCharArray();
        chars[index] = Character.toUpperCase(chars[index]);
        return new String(chars);
    }

}
