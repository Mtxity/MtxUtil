package com.edavalos.mtx.util.math;

import com.edavalos.mtx.util.map.MtxBiMap;
import com.edavalos.mtx.util.map.MtxHashBiMap;

import java.util.TreeSet;

public class MtxRomanNumeral {
    private static final MtxBiMap<Integer, String> ROMAN_LITERALS = new MtxHashBiMap<>() {{
        put(1000, "M");
        put(900, "CM");
        put(500, "D");
        put(400, "CD");
        put(100, "C");
        put(90, "XC");
        put(50, "L");
        put(40, "XL");
        put(10, "X");
        put(9, "IX");
        put(5, "V");
        put(4, "IV");
        put(1, "I");
    }};
    private static final TreeSet<Integer> ROMAN_NUMERAL_INTERVALS = new TreeSet<>(ROMAN_LITERALS.keySet());

    // Source: https://codingnconcepts.com/java/integer-to-roman/
    public static String getRomanNumeralFromInteger(int number) {
        Integer l = ROMAN_NUMERAL_INTERVALS.floor(number);
        assert l != null;

        if (number == l) {
            return ROMAN_LITERALS.get(number);
        }
        return ROMAN_LITERALS.get(l) + getRomanNumeralFromInteger(number - l);
    }

    public static int getIntegerFromRomanNumeral(String romanNumeral) {
        if (romanNumeral == null || romanNumeral.length() == 0) {
            return 0;
        }

        int result = 0;
        int previousValue = 0;

        for (int i = romanNumeral.length() - 1; i >= 0; i--) {
            char currentSymbol = romanNumeral.charAt(i);
            int currentValue = ROMAN_LITERALS.inverse().get(String.valueOf(currentSymbol));

            if (currentValue < previousValue) {
                result -= currentValue;
            } else {
                result += currentValue;
                previousValue = currentValue;
            }
        }

        return result;
    }
}
