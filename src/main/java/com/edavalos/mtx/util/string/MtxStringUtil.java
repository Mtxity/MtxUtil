package com.edavalos.mtx.util.string;

import java.util.regex.Pattern;

public final class MtxStringUtil {
    private static final char SEPARATOR_CHAR = 'ҁ';

    private MtxStringUtil() { }

    public static boolean isEmpty(String string) {
        return string == null || "".equals(string);
    }

    public static String[] splitAtChars(String string, char... chars) {
        if (chars.length == 0) {
            return new String[]{string};
        }

        if (chars.length == 1) {
            return string.split(Pattern.quote(String.valueOf(chars[0])), 2);
        }

        for (char separator : chars) {
            string = string.replaceFirst(Pattern.quote(String.valueOf(separator)), String.valueOf(SEPARATOR_CHAR));
        }
        return string.split(Pattern.quote(String.valueOf(SEPARATOR_CHAR)));
    }

    public static String repeat(String s, int times) {
        return new String(new char[times]).replace("\0", s);
    }

    public static String leftPad(String s, int length) {
        if (length <= 0) {
            return "";
        }
        if (length < s.length()) {
            return s.substring(s.length() - length);
        }
        if (length == s.length()) {
            return s;
        }
        return new String(new char[length - s.length()]).replace('\0', ' ') + s;
    }

    public static String rightPad(String s, int length) {
        if (length <= 0) {
            return "";
        }
        if (length < s.length()) {
            return s.substring(0, length);
        }
        if (length == s.length()) {
            return s;
        }
        return s + new String(new char[length - s.length()]).replace('\0', ' ');
    }

    public static String centerPad(String s, int length) {
        if (length <= 0) {
            return "";
        }
        if (length == s.length()) {
            return s;
        }

        if (length < s.length()) {
            int lengthDifference = s.length() - length;
            boolean front = true;
            while (lengthDifference > 0) {
                if (front) {
                    s = s.substring(1);
                    front = false;
                } else {
                    s = s.substring(0, s.length() - 1);
                    front = true;
                }
                lengthDifference --;
            }
            return s;
        }

        int extraLength = length - s.length();
        int leftPad = ((int) Math.ceil(((double) extraLength) / 2));
        int rightPad = ((int) Math.floor(((double) extraLength) / 2));

        return repeat(" ", leftPad) + s + repeat(" ", rightPad);
    }
}
