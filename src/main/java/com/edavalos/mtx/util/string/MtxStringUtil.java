package com.edavalos.mtx.util.string;

import java.util.regex.Pattern;

public final class MtxStringUtil {
    private MtxStringUtil() { }

    public static boolean isEmpty(String string) {
        return string == null || "".equals(string);
    }

    public static String[] splitAtChars(String string, char... chars) {
        if (chars.length == 0) {
            return new String[]{string};
        }

        if (chars.length == 1) {
            return string.split(Pattern.quote(String.valueOf(chars[0])));
        }

        StringBuilder spliterator = new StringBuilder();
        for (char c : chars) {
            spliterator.append(c).append("|");
        }
        spliterator.deleteCharAt(spliterator.length() - 1);
        return string.split(spliterator.toString());
    }
}
