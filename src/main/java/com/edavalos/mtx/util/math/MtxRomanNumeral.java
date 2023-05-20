package com.edavalos.mtx.util.math;

public class MtxRomanNumeral {
    private static final MtxBiMap<Integer, String> ROMAN_LITERALS = new MtxBiMap<>();
    static {
        ROMAN_LITERALS.put(1000, "M");
        ROMAN_LITERALS.put(900, "CM");
        ROMAN_LITERALS.put(500, "D");
        ROMAN_LITERALS.put(400, "CD");
        ROMAN_LITERALS.put(100, "C");
        ROMAN_LITERALS.put(90, "XC");
        ROMAN_LITERALS.put(50, "L");
        ROMAN_LITERALS.put(40, "XL");
        ROMAN_LITERALS.put(10, "X");
        ROMAN_LITERALS.put(9, "IX");
        ROMAN_LITERALS.put(5, "V");
        ROMAN_LITERALS.put(4, "IV");
        ROMAN_LITERALS.put(1, "I");
    }
}
