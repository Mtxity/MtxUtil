package com.edavalos.mtx.util.string;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
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

    @Nested
    class CenterPadTests {
        String val = "12345";

        @Test
        public void testCenterPad_longerLength_evenNumber() {
            String expected = "   " + val + "   ";
            int length = 3 + val.length() + 3;

            assertEquals(expected, MtxStringUtil.centerPad(val, length));
            assertEquals(length, MtxStringUtil.centerPad(val, length).length());
        }

        @Test
        public void testCenterPad_longerLength_oddNumber() {
            String expected = "    " + val + "   ";
            int length = 4 + val.length() + 3;

            assertEquals(expected, MtxStringUtil.centerPad(val, length));
            assertEquals(length, MtxStringUtil.centerPad(val, length).length());
        }

        @Test
        public void testCenterPad_sameLength() {
            String expected = val;
            int length = val.length();

            assertEquals(expected, MtxStringUtil.centerPad(val, length));
            assertEquals(length, MtxStringUtil.centerPad(val, length).length());
        }

        @Test
        public void testCenterPad_shorterLength_evenNumber() {
            String expected = "234";
            int length = val.length() - 2;

            assertEquals(expected, MtxStringUtil.centerPad(val, length));
            assertEquals(length, MtxStringUtil.centerPad(val, length).length());
        }

        @Test
        public void testCenterPad_shorterLength_oddNumber() {
            String expected = "34";
            int length = val.length() - 3;

            assertEquals(expected, MtxStringUtil.centerPad(val, length));
            assertEquals(length, MtxStringUtil.centerPad(val, length).length());
        }

        @Test
        public void testCenterPad_negativeLength() {
            String expected = "";
            int length = -1;

            assertEquals(expected, MtxStringUtil.centerPad(val, length));
            assertEquals(0, MtxStringUtil.centerPad(val, length).length());
        }
    }

    @Nested
    class ToIntTests {

        @Test
        public void testToInt_nullValue() {
            assertNull(MtxStringUtil.toInt(null));
            assertNull(MtxStringUtil.toInt(""));
        }

        @Test
        public void testToInt_notAnInt() {
            assertNull(MtxStringUtil.toInt("string"));
            assertNull(MtxStringUtil.toInt("1.2"));
        }

        @Test
        public void testToInt() {
            assertEquals(5, MtxStringUtil.toInt("5"));
            assertEquals(5, MtxStringUtil.toInt("5.0"));
            assertEquals(-5, MtxStringUtil.toInt("-5"));
            assertEquals(-5, MtxStringUtil.toInt("-5.0"));
        }
    }

    @Nested
    class ToDoubleTests {

        @Test
        public void testToDouble_nullValue() {
            assertNull(MtxStringUtil.toDouble(null));
            assertNull(MtxStringUtil.toDouble(""));
        }

        @Test
        public void testToDouble_notADouble() {
            assertNull(MtxStringUtil.toDouble("string"));
            assertNull(MtxStringUtil.toDouble("1.2."));
        }

        @Test
        public void testToDouble() {
            assertEquals(5, MtxStringUtil.toDouble("5"));
            assertEquals(5, MtxStringUtil.toDouble("5.0"));
            assertEquals(5.0, MtxStringUtil.toDouble("5.0"));
            assertEquals(5.5, MtxStringUtil.toDouble("5.5"));
            assertEquals(-5.5, MtxStringUtil.toDouble("-5.5"));
        }
    }

    @Nested
    class MergeStringArraysTests {
        String[] arr1 = new String[] {"a", "b", "c"};
        String[] arr2 = new String[] {"c", "d", "e"};
        String[] arr3 = new String[] {"c", "d", "e", "f"};

        @Test
        public void testMergeStringArrays_nullValues() {
            assertArrayEquals(arr2, MtxStringUtil.mergeStringArrays(null, arr2, false));
            assertArrayEquals(arr2, MtxStringUtil.mergeStringArrays(new String[0], arr2, false));
            assertArrayEquals(arr1, MtxStringUtil.mergeStringArrays(arr1, null, false));
            assertArrayEquals(arr1, MtxStringUtil.mergeStringArrays(arr1, new String[0], false));
            assertNull(MtxStringUtil.mergeStringArrays(null, null, false));
        }

        @Test
        public void testMergeStringArrays_dontRemoveDuplicates_sameLengthArrays() {
            String[] arr1plus2 = new String[] {"a", "b", "c", "c", "d", "e"};
            String[] arr2plus1 = new String[] {"c", "d", "e", "a", "b", "c"};
            String[] arr1plus1 = new String[] {"a", "b", "c", "a", "b", "c"};

            assertArrayEquals(arr1plus2, MtxStringUtil.mergeStringArrays(arr1, arr2, false));
            assertArrayEquals(arr2plus1, MtxStringUtil.mergeStringArrays(arr2, arr1, false));
            assertArrayEquals(arr1plus1, MtxStringUtil.mergeStringArrays(arr1, arr1, false));
        }

        @Test
        public void testMergeStringArrays_removeDuplicates_sameLengthArrays() {
            String[] arr1plus2 = new String[] {"a", "b", "c", "d", "e"};
            String[] arr2plus1 = new String[] {"c", "d", "e", "a", "b"};

            assertArrayEquals(arr1plus2, MtxStringUtil.mergeStringArrays(arr1, arr2, true));
            assertArrayEquals(arr2plus1, MtxStringUtil.mergeStringArrays(arr2, arr1, true));
            assertArrayEquals(arr1,      MtxStringUtil.mergeStringArrays(arr1, arr1, true));
        }

        @Test
        public void testMergeStringArrays_dontRemoveDuplicates_differentLengthArrays() {
            String[] arr1plus3 = new String[] {"a", "b", "c", "c", "d", "e", "f"};
            String[] arr3plus1 = new String[] {"c", "d", "e", "f", "a", "b", "c"};

            assertArrayEquals(arr1plus3, MtxStringUtil.mergeStringArrays(arr1, arr3, false));
            assertArrayEquals(arr3plus1, MtxStringUtil.mergeStringArrays(arr3, arr1, false));
        }

        @Test
        public void testMergeStringArrays_removeDuplicates_differentLengthArrays() {
            String[] arr1plus3 = new String[] {"a", "b", "c", "d", "e", "f"};
            String[] arr3plus1 = new String[] {"c", "d", "e", "f", "a", "b"};

            assertArrayEquals(arr1plus3, MtxStringUtil.mergeStringArrays(arr1, arr3, true));
            assertArrayEquals(arr3plus1, MtxStringUtil.mergeStringArrays(arr3, arr1, true));
        }
    }

    @Nested
    class CountOccurrencesOfTests {
        String sampleString = "acoustic acceleration contraption";

        @Test
        public void testCountOccurrencesOf_nullValues() {
            assertEquals(0, MtxStringUtil.countOccurrencesOf("", ""));
            assertEquals(0, MtxStringUtil.countOccurrencesOf(null, null));
        }

        @Test
        public void testCountOccurrencesOf_substringLongerThanString() {
            assertEquals(0, MtxStringUtil.countOccurrencesOf(sampleString, sampleString + " detection"));
        }

        @Test
        public void testCountOccurrencesOf_substringSameLengthAsString() {
            String sampleSubString = "ocoostic ecceleration contraptium";
            assertEquals(sampleString.length(), sampleSubString.length());

            assertEquals(1, MtxStringUtil.countOccurrencesOf(sampleString, sampleString));
            assertEquals(0, MtxStringUtil.countOccurrencesOf(sampleString, sampleSubString));
        }

        @Test
        public void testCountOccurrencesOf() {
            Map<String, Integer> testCases = new HashMap<>() {{
                put("acoustic", 1);
                put("acoustic ", 1);
                put("acceleration", 1);
                put(" acceleration", 1);
                put("contraption", 1);
                put("ac", 2);
                put("a", 4);
                put("t", 4);
                put(" ", 2);
                put("x", 0);
                put("ra", 2);
                put("ti", 3);
                put("tio", 2);
                put("tion", 2);
            }};

            for (Map.Entry<String, Integer> testCase : testCases.entrySet()) {
                assertEquals(
                        testCase.getValue(),
                        MtxStringUtil.countOccurrencesOf(sampleString, testCase.getKey())
                );
            }
        }
    }

    @Nested
    class AreParenthesisValidTests {
        String[] validTestCases = new String[] {
                "test()",
                "(test)",
                "f(){ }",
                "f(g(x))",
                "{{}}",
                "{[]}",
                "((((()))))",
                "(({{[{[]}]}}))",
                "{}[]()",
                "[][][][][][][][][]"
        };
        String[] invalidTestCases = new String[] {
                "test(",
                ")test(",
                ")(",
                "(",
                ")",
                "()()()(",
                ")()()()()",
                "[[ ]]]",
                "{{ }}}",
                "[{ }]]",
                "(({))",
                "([)]",
                "[{]}",
                "{([})]"
        };

        @Test
        public void testAreParenthesisValid_emptyInput() {
            assertTrue(MtxStringUtil.areParenthesisValid(""));
        }

        @Test
        public void testAreParenthesisValid_validInput() {
            for (String testCase : validTestCases) {
                assertTrue(MtxStringUtil.areParenthesisValid(testCase));
            }
        }

        @Test
        public void testAreParenthesisValid_invalidInput() {
            for (String testCase : invalidTestCases) {
                assertFalse(MtxStringUtil.areParenthesisValid(testCase));
            }
        }
    }

    @Nested
    class ReverseTests {
        String sampleForward = "abc def ghi jkl mno pqr";
        String sampleBackward = "rqp onm lkj ihg fed cba";
        String[] palindromes = new String[] {
                "kayak",
                "hannah",
                "12:21",
                "SATIREv vERITAS",
                "x"
        };

        @Test
        public void testReverse_emptyInput() {
            assertEquals("", MtxStringUtil.reverse(""));
        }

        @Test
        public void testReverse() {
            assertEquals(sampleBackward, MtxStringUtil.reverse(sampleForward));
            assertEquals(sampleForward, MtxStringUtil.reverse(sampleBackward));

            assertNotEquals(sampleForward, MtxStringUtil.reverse(sampleForward));
            assertNotEquals(sampleBackward, MtxStringUtil.reverse(sampleBackward));
        }

        @Test
        public void testReverse_palindromes() {
            for (String testCase : palindromes) {
                assertEquals(testCase, MtxStringUtil.reverse(testCase));
            }
        }
    }

    @Nested
    class IsValidIpAddressTests {
        String[] validIpAddresses = new String[] {
                "0.0.0.0",
                "255.255.255.255",
                "192.168.1.57"
        };
        String[] wrongLength = new String[] {
                "32.34.68.92.56",
                "2.3.4",
                "192367"
        };
        String[] hasInvalidChars = new String[] {
                "-32.34.5.56",
                "2..3.4",
                "abc.def.ghi.jkl"
        };
        String[] wrongSize = new String[] {
                "0.0.-7.0",
                "255.256.255.255",
                "192.168.1.575354"
        };

        @Test
        public void testIsValidIpAddress_validInput() {
            for (String ipAddress : validIpAddresses) {
                assertTrue(MtxStringUtil.isValidIpAddress(ipAddress));
            }
        }

        @Test
        public void testIsValidIpAddress_invalidInput_wrongLength() {
            for (String ipAddress : wrongLength) {
                assertFalse(MtxStringUtil.isValidIpAddress(ipAddress));
            }
        }

        @Test
        public void testIsValidIpAddress_invalidInput_invalidChars() {
            for (String ipAddress : hasInvalidChars) {
                assertFalse(MtxStringUtil.isValidIpAddress(ipAddress));
            }
        }

        @Test
        public void testIsValidIpAddress_invalidInput_wrongSize() {
            for (String ipAddress : wrongSize) {
                assertFalse(MtxStringUtil.isValidIpAddress(ipAddress));
            }
        }
    }

    @Nested
    class SplitAtCommasTests {

        @Test
        public void testSplitAtCommas_withClosingQuotes() {
            String row = "foo,bar,c;qual=\"baz,blurb\",d;junk=\"quux,syzygy\"";
            String[] expected = new String[] {"foo", "bar", "c;qual=\"baz,blurb\"", "d;junk=\"quux,syzygy\""};

            assertArrayEquals(expected, MtxStringUtil.splitAtCommas(row));
        }

        @Test
        public void testSplitAtCommas_noClosingQuotes() {
            String row = "foo,bar,c;qual=\"baz,blurb\",d;junk=\"quux,syzygy";
            String[] expected = new String[] {"foo,bar,c;qual=\"baz", "blurb\",d;junk=\"quux", "syzygy"};

            assertArrayEquals(expected, MtxStringUtil.splitAtCommas(row));
        }

        @Test
        public void testSplitAtCommas_fakeQuotes() {
            String row = "abc\\\"def,ghi";
            String[] expected = new String[] {"abc\\\"def", "ghi"};

            assertArrayEquals(expected, MtxStringUtil.splitAtCommas(row));
        }
    }
}
