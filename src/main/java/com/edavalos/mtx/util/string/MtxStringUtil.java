package com.edavalos.mtx.util.string;

public final class MtxStringUtil {
    private MtxStringUtil() { }

    public static boolean isEmpty(String string) {
        return string == null || "".equals(string);
    }
}
