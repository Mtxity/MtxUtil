package com.edavalos.mtx.util.math;

import com.edavalos.mtx.util.map.MtxBiMap;
import com.edavalos.mtx.util.map.MtxHashBiMap;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MtxRomanNumeralTest {
    private static final MtxBiMap<Integer, String> ROMAN_NUMERAL_TESTS = new MtxHashBiMap<>() {{
        // Base cases
        put(1, "I");
        put(5, "V");
        put(10, "X");
        put(50, "L");
        put(100, "C");
        put(500, "D");
        put(1000, "M");
        // Random values
        // Source: https://catonmat.net/tools/generate-random-roman-numerals
        put(857, "DCCCLVII");
        put(560, "DLX");
        put(601, "DCI");
        put(367, "CCCLXVII");
        put(892, "DCCCXCII");
        put(260, "CCLX");
        put(733, "DCCXXXIII");
        put(139, "CXXXIX");
        put(320, "CCCXX");
        put(251, "CCLI");
        put(542, "DXLII");
        put(534, "DXXXIV");
        put(164, "CLXIV");
        put(1731, "MDCCXXXI");
        put(1713, "MDCCXIII");
        put(1835, "MDCCCXXXV");
        put(2433, "MMCDXXXIII");
        put(1125, "MCXXV");
        put(1208, "MCCVIII");
        put(2761, "MMDCCLXI");
        put(2692, "MMDCXCII");
        put(2275, "MMCCLXXV");
        put(2601, "MMDCI");
        put(3804, "MMMDCCCIV");
        put(1624, "MDCXXIV");
        put(3076, "MMMLXXVI");
    }};

    @Test
    public void testGetRomanNumeralFromInteger() {
        for (int testCase : ROMAN_NUMERAL_TESTS.keySet()) {
            assertEquals(
                    ROMAN_NUMERAL_TESTS.get(testCase),
                    MtxRomanNumeral.getRomanNumeralFromInteger(testCase)
            );
        }
    }

    @Test
    public void testGetIntegerFromRomanNumeral() {
        for (String testCase : ROMAN_NUMERAL_TESTS.inverse().keySet()) {
            assertEquals(
                    ROMAN_NUMERAL_TESTS.inverse().get(testCase),
                    MtxRomanNumeral.getIntegerFromRomanNumeral(testCase)
            );
        }

        assertEquals(0, MtxRomanNumeral.getIntegerFromRomanNumeral(null));
        assertEquals(0, MtxRomanNumeral.getIntegerFromRomanNumeral(""));
    }
}
