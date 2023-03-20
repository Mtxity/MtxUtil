package com.edavalos.mtx.util.string;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public final class MtxStringUtilTest {
    @Nested
    class IsEmptyTests {
        @Test
        public void testIsEmpty_null() {
            assertTrue(MtxStringUtil.isEmpty(null));
        }

        @Test
        public void testIsEmpty_emptyString() {
            assertTrue(MtxStringUtil.isEmpty(""));
        }

        @Test
        public void testIsEmpty_validString() {
            assertFalse(MtxStringUtil.isEmpty("Valid String"));
        }
    }

    @Nested
    class SplitAtCharsTests {
        String sampleString = "s$t$r$i$n$g&e*x^a%m%p@l!e";
        char[] specialChars = new char[]{'$', '$', '$', '$', '$', '&', '*', '^', '%', '%', '@', '!'};

        @Test
        public void testSplitAtChars_noArgs() {
            String[] sampleStrArr = new String[]{sampleString};
            assertArrayEquals(sampleStrArr, MtxStringUtil.splitAtChars(sampleString));
        }

        @Test
        public void testSplitAtChars_singleArg_once() {
            String[] sampleStrArr = sampleString.split(Pattern.quote(String.valueOf(specialChars[0])), 2);
            assertArrayEquals(sampleStrArr, MtxStringUtil.splitAtChars(sampleString, specialChars[0]));
        }

        @Test
        public void testSplitAtChars_singleArg_multipleTimes() {
            String[] expected = new String[]{"s", "t", "r", "i", "n", "g&e*x^a%m%p@l!e"};
            assertArrayEquals(expected, MtxStringUtil.splitAtChars(sampleString, '$', '$', '$', '$', '$'));
        }

        @Test
        public void testSplitAtChars_multipleArg() {
            String[] expected = new String[]{"s", "t", "r", "i", "n", "g", "e", "x", "a", "m", "p", "l", "e"};
            assertArrayEquals(expected, MtxStringUtil.splitAtChars(sampleString, specialChars));
        }
    }

    @Nested
    class RepeatTests {
        String starting = "x";

        @Test
        public void testRepeat_oneTime() {
            String expected = "x";
            int times = 1;

            assertEquals(expected, MtxStringUtil.repeat(starting, times));
        }

        @Test
        public void testRepeat_zeroTimes() {
            String expected = "";
            int times = 0;

            assertEquals(expected, MtxStringUtil.repeat(starting, times));
        }

        @Test
        public void testRepeat_nTimes() {
            String expected = "";
            int times = 10;

            for (int i = 0; i < times; i++) {
                assertEquals(expected, MtxStringUtil.repeat(starting, i));
                expected += starting;
            }
        }
    }

    @Nested
    class LeftPadTests {
        String val = "12345";

        @Test
        public void testLeftPad_longerLength() {
            String expected = "     " + val;
            int length = 5 + val.length();

            assertEquals(expected, MtxStringUtil.leftPad(val, length));
            assertEquals(length, MtxStringUtil.leftPad(val, length).length());
        }

        @Test
        public void testLeftPad_sameLength() {
            String expected = val;
            int length = val.length();

            assertEquals(expected, MtxStringUtil.leftPad(val, length));
            assertEquals(length, MtxStringUtil.leftPad(val, length).length());
        }

        @Test
        public void testLeftPad_shorterLength() {
            String expected = "345";
            int length = val.length() - 2;

            assertEquals(expected, MtxStringUtil.leftPad(val, length));
            assertEquals(length, MtxStringUtil.leftPad(val, length).length());
        }

        @Test
        public void testLeftPad_negativeLength() {
            String expected = "";
            int length = -1;

            assertEquals(expected, MtxStringUtil.leftPad(val, length));
            assertEquals(0, MtxStringUtil.leftPad(val, length).length());
        }
    }

    @Nested
    class RightPadTests {
        String val = "12345";

        @Test
        public void testRightPad_longerLength() {
            String expected = val + "     ";
            int length = 5 + val.length();

            assertEquals(expected, MtxStringUtil.rightPad(val, length));
            assertEquals(length, MtxStringUtil.rightPad(val, length).length());
        }

        @Test
        public void testRightPad_sameLength() {
            String expected = val;
            int length = val.length();

            assertEquals(expected, MtxStringUtil.rightPad(val, length));
            assertEquals(length, MtxStringUtil.rightPad(val, length).length());
        }

        @Test
        public void testRightPad_shorterLength() {
            String expected = "123";
            int length = val.length() - 2;

            assertEquals(expected, MtxStringUtil.rightPad(val, length));
            assertEquals(length, MtxStringUtil.rightPad(val, length).length());
        }

        @Test
        public void testRightPad_negativeLength() {
            String expected = "";
            int length = -1;

            assertEquals(expected, MtxStringUtil.rightPad(val, length));
            assertEquals(0, MtxStringUtil.rightPad(val, length).length());
        }
    }
}
