package com.aliyunidaas.sync.util;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 字符串工具类
 *
 * @author hatterjiang
 */
public class StringUtil {
    /**
     * 目前阿里云RAM不支持非第一平面字符，所以这个函数用于去掉非第一平面字符
     *
     * 第一个平面称为基本多语言平面（Basic Multilingual Plane, BMP），或称第零平面（Plane 0）。
     * 其他平面称为辅助平面（Supplementary Planes）。
     * Java在内存中处理字符串使用UTF-16编码，Java使用一个char（2bytes）存储第一平台字符，第二及以后平台字符使用两个char进行表示。
     */
    public static String filterNone1CodePlane(String str) {
        if (str == null) {
            return null;
        }
        final StringBuilder sb = new StringBuilder(str.length());
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (Character.isHighSurrogate(c) || Character.isLowSurrogate(c)) {
                // 非1号平台字符， then IGNORE
            } else {
                sb.append(c);
            }
        }
        return sb.toString().trim();
    }

    public static boolean equals(String str1, String str2) {
        if ((str1 == null) && (str2 == null)) {
            return true;
        }
        if ((str1 == null) || (str2 == null)) {
            return false;
        }
        return str1.equals(str2);
    }

    public static boolean isEmpty(String str) {
        return (str == null) || str.isEmpty();
    }

    public static boolean isNotEmpty(String str) {
        return (str != null) && (!str.isEmpty());
    }

    public static String trim(String str) {
        if (str == null) {
            return null;
        }
        return str.trim();
    }

    public static List<String> splitToList(String cidr) {
        if (isEmpty(cidr)) {
            return null;
        }
        final List<String> list = Arrays.stream(cidr.split("[,;]")).map(StringUtil::trim).collect(Collectors.toList());
        return list.isEmpty() ? null : list;
    }

    /**
     * Below code is from org.springframework:spring-core:5.2.12
     * Reference: org.springframework.util.StringUtils#split
     */
    public static String[] split(String toSplit, String delimiter) {
        if (isEmpty(toSplit) || isEmpty(delimiter)) {
            return null;
        }
        final int offset = toSplit.indexOf(delimiter);
        if (offset < 0) {
            return null;
        }

        final String beforeDelimiter = toSplit.substring(0, offset);
        final String afterDelimiter = toSplit.substring(offset + delimiter.length());
        return new String[] {beforeDelimiter, afterDelimiter};
    }
}
