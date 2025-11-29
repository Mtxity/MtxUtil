package com.edavalos.mtx.util.string;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
        public void testToInt_validInt() {
            assertEquals(5, MtxStringUtil.toInt("5"));
            assertEquals(5, MtxStringUtil.toInt("5.0"));
            assertEquals(-5, MtxStringUtil.toInt("-5"));
            assertEquals(-5, MtxStringUtil.toInt("-5.0"));
        }

        @Test
        public void testToInt_invalidInt() {
            assertNull(MtxStringUtil.toInt("5.2"));
        }

        @Test
        public void testToInt_invalidNumber() {
            assertNull(MtxStringUtil.toInt("-.0.1."));
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
            assertEquals(0, MtxStringUtil.countOccurrencesOf("x", null));
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

        @Test
        public void testCountOccurrencesOf_forUseInLogging() {
            String logTemplate = "{}";
            for (int i = 0; i < 10; i++) {
                String logStatementWithTemplate = "TestLog " + ((logTemplate + " ").repeat(i));
                assertEquals(i, MtxStringUtil.countOccurrencesOf(logStatementWithTemplate, logTemplate));
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

    @Nested
    class IsValidZipcodeTests {
        String[] validZipcodesNoExtension = new String[] {
                "12345",
                "67890",
                "11697"
        };
        String[] validZipcodesWithExtension = new String[] {
                "12345-6789",
                "67890-1234",
                "11697-4413"
        };
        String[] invalidZipcodes = new String[] {
                "123456",
                "1234",
                "12345-678",
                "12345-67890"
        };

        @Test
        public void testIsValidZipcode_validZipcode() {
            for (String zipcode : validZipcodesNoExtension) {
                assertTrue(MtxStringUtil.isValidZipcode(zipcode));
            }
        }

        @Test
        public void testIsValidZipcode_validZipcode_withExtension() {
            for (String zipcode : validZipcodesWithExtension) {
                assertTrue(MtxStringUtil.isValidZipcode(zipcode));
            }
        }

        @Test
        public void testIsValidIpAddress_invalidZipcode() {
            for (String zipcode : invalidZipcodes) {
                assertFalse(MtxStringUtil.isValidZipcode(zipcode));
            }
        }
    }

    @Nested
    class ConvertToMockingCaseTests {
        HashMap<String, String> lettersOnlyTestCases = new HashMap<>(){{
            put("String", "StRiNg");
            put("abcdef", "AbCdEf");
            put("aBcDeF", "AbCdEf");
            put("AAAAAA", "AaAaAa");
        }};
        HashMap<String, String> otherTestCases = new HashMap<>(){{
            put("a b c", "A b C");
            put("va/lue.s", "Va/LuE.s");
            put("lorem ipsum", "LoReM iPsUm");
        }};
        String[] noLettersTestCases = new String[] {
                "123456",
                "!@#$%",
                "   ",
                "0 1 0"
        };

        @Test
        public void testConvertToMockingCase_singleWords() {
            for (Map.Entry<String, String> testCase : lettersOnlyTestCases.entrySet()) {
                assertEquals(
                        testCase.getValue(),
                        MtxStringUtil.convertToMockingCase(testCase.getKey())
                );
            }
        }

        @Test
        public void testConvertToMockingCase_nonLetterChars() {
            for (Map.Entry<String, String> testCase : otherTestCases.entrySet()) {
                assertEquals(
                        testCase.getValue(),
                        MtxStringUtil.convertToMockingCase(testCase.getKey())
                );
            }
        }

        @Test
        public void testConvertToMockingCase_noLetters() {
            for (String testCase : noLettersTestCases) {
                assertEquals(
                        testCase,
                        MtxStringUtil.convertToMockingCase(testCase)
                );
            }
        }
    }

    @Nested
    class IsPalindromeTests {
        String[] samplePalindomesEvenLength = new String[]{
                "hannah",
                "SATIREv  vERITAS",
                ""
        };
        String[] samplePalindomesOddLength = new String[]{
                "kayak",
                "12:21",
                "SATIREv vERITAS",
                "x"
        };
        String[] sampleNonPalindomes = new String[]{
                "random word",
                "phrase",
                "hello there",
                "test",
                "xcs",
                "CaPs TeSt",
                "Lorem Ipsum",
                "xd"
        };

        @Test
        public void testIsPalindrome_palindromes_evenLength() {
            for (String testCase : samplePalindomesEvenLength) {
                assertTrue(MtxStringUtil.isPalindrome(testCase));
            }
        }

        @Test
        public void testIsPalindrome_palindromes_oddLength() {
            for (String testCase : samplePalindomesOddLength) {
                assertTrue(MtxStringUtil.isPalindrome(testCase));
            }
        }

        @Test
        public void testIsPalindrome_notPalindromes() {
            for (String testCase : sampleNonPalindomes) {
                assertFalse(MtxStringUtil.isPalindrome(testCase));
            }
        }

        @Nested
        class IsPalindromeArrayTests {
            String[] samplePalindromeArraysEvenLength = new String[]{
                    "sample text |sample text ",
                    "a|b|b|a",
                    "1|2|3|4|5|6|7|8|9|10|11|12|13|14|15|16|17|18|19|20|20|19|18|17|16|15|14|13|12|11|10|9|8|7|6|5|4|3|2|1"
            };
            String[] samplePalindromeArraysOddLength = new String[]{
                    "test",
                    "a|b|a",
                    "a|b|c|b|a",
                    "1|2|3|4|5|6|7|8|9|10|11|12|13|14|15|16|17|18|19|20|19|18|17|16|15|14|13|12|11|10|9|8|7|6|5|4|3|2|1"
            };
            String[] sampleNonPalindromeArrays = new String[]{
                    "random|word",
                    "phras|e",
                    "hello|there",
                    "|test",
                    "x|c|s",
                    "Ca|Ps|Te|St",
                    "Lorem | Ipsum",
                    "xd|m",
                    "123|32",
                    "a|b|c|d|c|b|x",
                    "l|m|n|n|m|o"
            };
            String sampleDelimiter = "|";

            @Test
            public void testIsPalindromeArray_emptyString() {
                assertTrue(MtxStringUtil.isPalindrome("", sampleDelimiter));
            }

            @Test
            public void testIsPalindromeArray_emptyDelimiter() {
                assertTrue(MtxStringUtil.isPalindrome("abc", ""));
            }

            @Test
            public void testIsPalindromeArray_tooSmall() {
                assertTrue(MtxStringUtil.isPalindrome("abc", sampleDelimiter));
            }

            @Test
            public void testIsPalindromeArray_palindromes_evenLength() {
                assertTrue(MtxStringUtil.isPalindrome("abc", ", "));
            }

            @Test
            public void testIsPalindrome_palindromes_evenLength() {
                for (String testCase : samplePalindromeArraysEvenLength) {
                    assertTrue(MtxStringUtil.isPalindrome(testCase, sampleDelimiter));
                }
            }

            @Test
            public void testIsPalindrome_palindromes_oddLength() {
                for (String testCase : samplePalindromeArraysOddLength) {
                    assertTrue(MtxStringUtil.isPalindrome(testCase, sampleDelimiter));
                }
            }

            @Test
            public void testIsPalindrome_notPalindromes() {
                for (String testCase : sampleNonPalindromeArrays) {
                    assertFalse(MtxStringUtil.isPalindrome(testCase, sampleDelimiter));
                }
            }
        }
    }

    @Nested
    class MostCommonCharTests {

        @Test
        public void testMostCommonChar() {
            String testCase = "abcadaccefghftcli";
            char testMostCommon = 'c';

            assertEquals(testMostCommon, MtxStringUtil.mostCommonChar(testCase));
        }

        @Test
        public void testMostCommonChar_charNotFound() {
            String testCase = "";
            char nullChar = '\u0000';

            assertEquals(nullChar, MtxStringUtil.mostCommonChar(testCase));
        }

        @Test
        public void testMostCommonChar_nullInput() {
            char nullChar = '\u0000';

            assertEquals(nullChar, MtxStringUtil.mostCommonChar(null));
        }
    }

    @Nested
    class ReplaceFromMapTests {
        HashMap<String, String> sampleMap = new HashMap<>(){{
            put("Lorem", "Carpe");
            put("dolor", "comon");
            put("occaecat", "sonomeat");
            put("non", "ont");
            put("u", "v");
        }};

        HashMap<String, String> testCases = new HashMap<>(){{
            put(
                    "Lorem ipsum dolor sit amet",
                    "Carpe ipsvm comon sit amet"
            );
            put(
                    "Ut enim ad minim veniam occaecat",
                    "Ut enim ad minim veniam sonomeat"
            );
            put(
                    "Excepteur sint occaecat cupidatat non proident",
                    "Exceptevr sint sonomeat cvpidatat ont proident"
            );
        }};

        @Test
        public void testReplaceFromMap() {
            for (Map.Entry<String, String> testEntry : testCases.entrySet()) {
                assertEquals(
                        testEntry.getValue(),
                        MtxStringUtil.replaceFromMap(testEntry.getKey(), sampleMap)
                );
            }
        }

        @Test
        public void testReplaceFromMap_mapWithNullKey() {
            HashMap<String, String> mapWithNullKey = new HashMap<>();
            mapWithNullKey.put(null, "Value not in return string");
            String unchangedString = "Bacon ipsum dolor amet corned beef";

            assertEquals(
                    unchangedString,
                    MtxStringUtil.replaceFromMap(unchangedString, mapWithNullKey)
            );
        }

        @Test
        public void testReplaceFromMap_mapWithNullValue() {
            HashMap<String, String> regularBeefOnly = new HashMap<>();
            regularBeefOnly.put("corned ", null);
            String input = "Bacon ipsum dolor amet corned beef";
            String output = "Bacon ipsum dolor amet beef";

            assertEquals(
                    output,
                    MtxStringUtil.replaceFromMap(input, regularBeefOnly)
            );
        }
    }

    @Nested
    class LeetSpeakTests {
        HashMap<String, String> testCases = new HashMap<>(){{
            put(
                    "Hello, world! This is a leet speak transformation.",
                    "H3110, w0r1d! 7h!5 !5 4 1337 5p34k 7r4n5f0rm47!0n."
            );
            put(
                    "Sample verbiage that should be translated",
                    "54mp13 v3rb!4g3 7h47 5h0u1d b3 7r4n51473d"
            );
            put(
                    "Totally literally like just ask your mom bro",
                    "707411y 1!73r411y 1!k3 ju57 45k y0ur m0m br0"
            );
        }};

        @Test
        public void testLeetSpeak() {
            for (Map.Entry<String, String> testEntry : testCases.entrySet()) {
                assertEquals(testEntry.getValue(), MtxStringUtil.leetSpeak(testEntry.getKey()));
            }
        }

        @Test
        public void testLeetSpeak_regularText() {
            String nonLeetSpeak = "Bcdfghjkmnpqruvwxyz";
            assertEquals(nonLeetSpeak, MtxStringUtil.leetSpeak(nonLeetSpeak));
        }
    }

    @Nested
    class TestConcatenateWithOverlap {
        // Random strings from: https://baconipsum.com/
        HashMap<String[], String> testCases = new HashMap<>(){{
            put(
                    new String[] {
                            "T-bone frankfurter flank",
                            "flank cow filet mignon"
                    },
                    "T-bone frankfurter flank cow filet mignon"
            );
            put(
                    new String[] {
                            "Ham hock short ribs ribeye ",
                            " jowl pig strip steak"
                    },
                    "Ham hock short ribs ribeye jowl pig strip steak"
            );
            put(
                    new String[] {
                            "Strip steak boudin",
                            "steak boudin porchetta"
                    },
                    "Strip steak boudin porchetta"
            );
            put(
                    new String[] {
                            "Strip steak boudin",
                            "din porchetta"
                    },
                    "Strip steak boudin porchetta"
            );
            put(
                    new String[] {
                            "Strip steak bou",
                            "boudin porchetta"
                    },
                    "Strip steak boudin porchetta"
            );
            put(
                    new String[] {
                            "Doner brisket filet",
                            " mignon short loin"
                    },
                    "Doner brisket filet mignon short loin"
            );
        }};

        @Test
        public void concatenateWithOverlapTest() {
            for (Map.Entry<String[], String> testEntry : testCases.entrySet()) {
                assertEquals(
                        testEntry.getValue(),
                        MtxStringUtil.concatenateWithOverlap(testEntry.getKey()[0], testEntry.getKey()[1])
                );
            }
        }

        @Test
        public void concatenateWithOverlapTest_noOverlap() {
            String s1 = "Sample string that ";
            String s2 = "should end here";
            assertEquals(s1 + s2, MtxStringUtil.concatenateWithOverlap(s1, s2));
        }

        @Test
        public void concatenateWithOverlapTest_fullOverlap() {
            String s1 = "Unsalted highly unsaturated string";
            assertEquals(s1, MtxStringUtil.concatenateWithOverlap(s1, s1));
        }

        @Test
        public void concatenateWithOverlapTest_nullArgs() {
            assertThrows(NullPointerException.class, () -> MtxStringUtil.concatenateWithOverlap(null, "test"));
            assertThrows(NullPointerException.class, () -> MtxStringUtil.concatenateWithOverlap("test", null));
        }

        // Exercises the inner while-loop with multiple successful iterations and drains ix down to -1,
        // then hits: if (ix == -1) { return s1 + s2.substring(indexOfLast2 + 1); }
        @Test
        public void concatenateWithOverlapTest_multiCharOverlap_returnsWithIxMinusOne() {
            String s1 = "abXYZ";
            String s2 = "XYZ12";
            String result = MtxStringUtil.concatenateWithOverlap(s1, s2);
            assertEquals("abXYZ12", result);
        }

        // Exercises the inner while-loop for a single-character overlap (ix starts at 0),
        // then ix == -1 branch triggers.
        @Test
        public void concatenateWithOverlapTest_singleCharOverlap_returnsWithIxMinusOne() {
            String s1 = "abc";
            String s2 = "cde";
            String result = MtxStringUtil.concatenateWithOverlap(s1, s2);
            assertEquals("abcde", result);
        }

        // Enters the inner while-loop, matches once at the last char, then mismatches on the next comparison,
        // so ix != -1 and the function does NOT take the ix == -1 early return. No other overlap is found,
        // so it returns s1 + s2.
        @Test
        public void concatenateWithOverlapTest_partialPrefixMismatch_breaksInnerWhile_noOverlap() {
            String s1 = "abQRS";
            String s2 = "QTS";
            String result = MtxStringUtil.concatenateWithOverlap(s1, s2);
            assertEquals("abQRSQTS", result);
        }
    }

    @Nested
    class TestJoinObjectsAsStrings {
        private class LowerString {
            String s;

            public LowerString(String s) {
                this.s = s;
            }

            @Override
            public String toString() {
                return this.s.toLowerCase();
            }
        }

        private class ErrorOnToString {
            @Override
            public String toString() {
                throw new UnsupportedOperationException();
            }
        }

        Object[] randomItems = new Object[] {
                "String",                     // String
                3.5,                          // Double
                40,                           // Integer
                1f,                           // Float
                2L,                           // Long
                new LowerString("StRInG"), // LowerString
                new ErrorOnToString()         // ErrorOnToString
        };
        String randomItemsAsAString = randomItems[0].toString() +
                                      randomItems[1].toString() +
                                      randomItems[2].toString() +
                                      randomItems[3].toString() +
                                      randomItems[4].toString() +
                                      randomItems[5].toString();

        @Test
        public void testJoinObjectsAsStrings() {
            assertEquals(randomItemsAsAString, MtxStringUtil.joinObjectsAsStrings(randomItems));
        }
    }

    @Nested
    class TestJoinArrayAsString {
        Integer[] intArray1 = {1, 2, 3};
        Integer[] intArray2 = {1};
        Integer[] intArray3 = {};

        @Test
        public void testJoinArrayAsString() {
            String arrayAsString = "[1, 2, 3]";
            assertEquals(arrayAsString, MtxStringUtil.joinArrayAsString(intArray1));
        }

        @Test
        public void testJoinArrayAsString_oneElement() {
            String arrayAsString = "[1]";
            assertEquals(arrayAsString, MtxStringUtil.joinArrayAsString(intArray2));
        }

        @Test
        public void testJoinArrayAsString_noElements() {
            String arrayAsString = "[]";
            assertEquals(arrayAsString, MtxStringUtil.joinArrayAsString(intArray3));
        }
    }

    @Nested
    class TestPadZeroToDateRelatedInt {
        int tooLow = -2;
        int tooHigh = 102;
        int oneDigit = 7;
        int twoDigit = 14;

        @Test
        public void testPadZeroToDateRelatedInt_tooLow() {
            String errMSg = assertThrows(
                    IllegalArgumentException.class,
                    () -> MtxStringUtil.padZeroToDateRelatedInt(tooLow)
            ).getMessage();

            assertEquals("Negative values cannot be padded: " + tooLow, errMSg);
        }

        @Test
        public void testPadZeroToDateRelatedInt_tooHigh() {
            String errMSg = assertThrows(
                    IllegalArgumentException.class,
                    () -> MtxStringUtil.padZeroToDateRelatedInt(tooHigh)
            ).getMessage();

            assertEquals("Value is too big to be padded (and fit in two digits): " + tooHigh, errMSg);
        }

        @Test
        public void testPadZeroToDateRelatedInt_noZeroAdded() {
            assertEquals("14", MtxStringUtil.padZeroToDateRelatedInt(twoDigit));
        }

        @Test
        public void testPadZeroToDateRelatedInt_withZeroAdded() {
            assertEquals("07", MtxStringUtil.padZeroToDateRelatedInt(oneDigit));
        }
    }

    @Nested
    class TestReplaceLast {

        @Test
        public void testReplaceLast_nullVals() {
            String test = "test";
            assertEquals(test, MtxStringUtil.replaceLast(test, "t", null));
            assertEquals(test, MtxStringUtil.replaceLast(test, null, "x"));
            assertNull(MtxStringUtil.replaceLast(null, "t", "x"));
        }

        @Test
        public void testReplaceLast_lastChar() {
            assertEquals("tesx", MtxStringUtil.replaceLast("test", "t", "x"));
        }

        @Test
        public void testReplaceLast_firstChar() {
            assertEquals("zest", MtxStringUtil.replaceLast("yest", "y", "z"));
        }

        @Test
        public void testReplaceLast_middleChar() {
            assertEquals("tust", MtxStringUtil.replaceLast("test", "e", "u"));
        }

        @Test
        public void testReplaceLast_longerChar() {
            assertEquals("testies", MtxStringUtil.replaceLast("test", "t", "ties"));
        }

        @Test
        public void testReplaceLast_longerReplacement() {
            assertEquals("trust", MtxStringUtil.replaceLast("test", "e", "ru"));
        }

        @Test
        public void testReplaceLast_notFound() {
            assertEquals("test", MtxStringUtil.replaceLast("test", "x", "y"));
        }
    }

    @Nested
    class TestSplitAtMultiple {
        private static final String TEST_STR = "a b c d e f";
        private static final String[] TEST_DELIMS = {"x", "y", "c", "v", "w"};

        @Test
        public void testSplitAtMultiple_foundDelim_delimInFirst_caseSensitive() {
            String[] expected = {"a b c", "d e f"};
            String[] parts = MtxStringUtil.splitAtMultiple(TEST_STR, TEST_DELIMS, true, true);
            assertArrayEquals(expected, parts);
        }

        @Test
        public void testSplitAtMultiple_foundDelim_delimInSecond_caseSensitive() {
            String[] expected = {"a b", "c d e f"};
            String[] parts = MtxStringUtil.splitAtMultiple(TEST_STR, TEST_DELIMS, false, true);
            assertArrayEquals(expected, parts);
        }

        @Test
        public void testSplitAtMultiple_foundDelim_delimInFirst_caseInsensitive() {
            String[] expected = {"a b c", "d e f"};
            String[] parts = MtxStringUtil.splitAtMultiple(TEST_STR, TEST_DELIMS, true, false);
            assertArrayEquals(expected, parts);
        }

        @Test
        public void testSplitAtMultiple_foundDelim_delimInSecond_caseInsensitive() {
            String[] expected = {"a b", "c d e f"};
            String[] parts = MtxStringUtil.splitAtMultiple(TEST_STR, TEST_DELIMS, false, false);
            assertArrayEquals(expected, parts);
        }

        @Test
        public void testSplitAtMultiple_noFoundDelim() {
            String[] expected = {"a b c d e f"};
            String[] parts = MtxStringUtil.splitAtMultiple(TEST_STR, new String[] {"x"}, false, false);
            assertArrayEquals(expected, parts);
        }
    }

    @Nested
    class TestLevenshteinDistance {
        private static final String TEST_STR = "given";
        private static final Map<String, Integer> EXPECTED_SIMILARITY_SCORES = Map.of(
                "gibon", 2,
                "giveb", 1,
                "givenn", 1,
                "triven", 2,
                "driven", 2,
                "lisben", 3,
                "tempor", 6,
                "ipsum", 5,
                "sit", 4,
                "incididunt", 8
        );

        @Test
        public void testLevenshteinDistance_realValues() {
            for (Map.Entry<String, Integer> test : EXPECTED_SIMILARITY_SCORES.entrySet()) {
                assertEquals(test.getValue(), MtxStringUtil.levenshteinDistance(TEST_STR, test.getKey()));
            }
        }

        @Test
        public void testLevenshteinDistance_nullA() {
            assertThrows(NullPointerException.class, () -> MtxStringUtil.levenshteinDistance(null, TEST_STR));
        }

        @Test
        public void testLevenshteinDistance_nullB() {
            assertThrows(NullPointerException.class, () -> MtxStringUtil.levenshteinDistance(TEST_STR, null));
        }
    }

    @Nested
    class TestJaroWinklerDistance {
        private static final String TEST_STR = "given";
        private static final Map<String, Double> EXPECTED_SIMILARITY_SCORES = Map.of(
                "gibon", 0.7866666666666667,
                "giveb", 0.92,
                "givenn", 0.9666666666666667,
                "triven", 0.8222222222222223,
                "driven", 0.8222222222222223,
                "lisben", 0.7000000000000001,
                "tempor", 0.45555555555555555,
                "ipsum", 0.4666666666666666,
                "sit", 0.5111111111111111,
                "incididunt", 0.5333333333333333
        );

        @Test
        public void testJaroWinklerDistance_realValues() {
            for (Map.Entry<String, Double> test : EXPECTED_SIMILARITY_SCORES.entrySet()) {
                assertEquals(test.getValue(), MtxStringUtil.jaroWinklerDistance(TEST_STR, test.getKey()));
            }
        }

        @Test
        public void testJaroWinklerDistance_nullA() {
            assertEquals(0.0, MtxStringUtil.jaroWinklerDistance(null, TEST_STR));
        }

        @Test
        public void testJaroWinklerDistance_nullB() {
            assertEquals(0.0, MtxStringUtil.jaroWinklerDistance(TEST_STR, null));
        }

        @Test
        public void testJaroWinklerDistance_lengthZero() {
            assertEquals(1.0, MtxStringUtil.jaroWinklerDistance("", ""));
        }

        @Test
        public void testJaroWinklerDistance_lengthOne() {
            assertEquals(0.0, MtxStringUtil.jaroWinklerDistance("a", ""));
            assertEquals(0.0, MtxStringUtil.jaroWinklerDistance("", "b"));
        }

        @Test
        public void testJaroWinklerDistance_noMatches() {
            assertEquals(0.0, MtxStringUtil.jaroWinklerDistance("xyz", "abc"));
        }
    }

    @Nested
    class TestGenericHashDistance {
        private static final String TEST_STR = "given";
        private static final Map<String, Integer> EXPECTED_SIMILARITY_SCORES = Map.of(
                "gibon", 30,
                "giveb", 12,
                "givenn", 110,
                "triven", 171,
                "driven", 161,
                "lisben", 130,
                "tempor", 152,
                "ipsum", 29,
                "sit", 225,
                "given", 0
        );

        @Test
        public void testGenericHashDistance_realValues() {
            for (Map.Entry<String, Integer> test : EXPECTED_SIMILARITY_SCORES.entrySet()) {System.out.println(test.getKey() + " : " + MtxStringUtil.genericHashDistance(TEST_STR, test.getKey()));
                assertEquals(test.getValue(), MtxStringUtil.genericHashDistance(TEST_STR, test.getKey()));
            }
        }

        @Test
        public void testGenericHashDistance_nullA() {
            assertEquals(-1, MtxStringUtil.genericHashDistance(null, TEST_STR));
        }

        @Test
        public void testGenericHashDistance_emptyA() {
            assertEquals(-1, MtxStringUtil.genericHashDistance("", TEST_STR));
        }

        @Test
        public void testGenericHashDistance_nullB() {
            assertEquals(-1, MtxStringUtil.genericHashDistance(TEST_STR, null));
        }

        @Test
        public void testGenericHashDistance_emptyB() {
            assertEquals(-1, MtxStringUtil.genericHashDistance(TEST_STR, ""));
        }
    }

    @Nested
    class TestCountOccurrences {
        private static final String TEST_STR = "x y z z y";

        @Test
        public void testCountOccurrences_oneFound() {
            assertEquals(1, MtxStringUtil.countOccurrences(TEST_STR, "x"));
        }

        @Test
        public void testCountOccurrences_multipleFound() {
            assertEquals(2, MtxStringUtil.countOccurrences(TEST_STR, "z"));
        }

        @Test
        public void testCountOccurrences_noneFound() {
            assertEquals(0, MtxStringUtil.countOccurrences(TEST_STR, "w"));
        }
    }

    @Nested
    class TestLogFill {
        private static final String TEST_STR = "check {} at {} by {} for {}";

        @Test
        public void testLogFill_allTemplatesFilled() {
            String expected = "check a at b by c for d";
            assertEquals(expected, MtxStringUtil.logFill(TEST_STR, "a", "b", "c", "d"));
        }

        @Test
        public void testLogFill_someTemplatesFilled() {
            IllegalArgumentException e = assertThrows(
                    IllegalArgumentException.class,
                    () -> MtxStringUtil.logFill(TEST_STR, "a", "b")
            );
            assertEquals("Argument count mismatch", e.getMessage());
        }

        @Test
        public void testLogFill_noTemplatesFilled() {
            IllegalArgumentException e = assertThrows(
                    IllegalArgumentException.class,
                    () -> MtxStringUtil.logFill(TEST_STR)
            );
            assertEquals("Argument count mismatch", e.getMessage());
        }

        @Test
        public void testLogFill_tooManyTemplatesFilled() {
            IllegalArgumentException e = assertThrows(
                    IllegalArgumentException.class,
                    () -> MtxStringUtil.logFill(TEST_STR, "a", "b", "c", "d", "e")
            );
            assertEquals("Argument count mismatch", e.getMessage());
        }

        @Nested
        class TestLogFillVariableReplacement {
            private static MtxStringWrapper customTestReplVar;
            private static String customTestStr;

            @BeforeEach
            public void setUp() {
                customTestReplVar = new MtxStringWrapper(UUID.randomUUID().toString());
                customTestStr = TEST_STR.replace("{}", customTestReplVar.toString());
            }

            @Test
            public void testLogFill_allTemplatesFilled() {
                String expected = "check a at b by c for d";
                assertEquals(expected, MtxStringUtil.logFill(customTestReplVar, customTestStr, "a", "b", "c", "d"));
            }

            @Test
            public void testLogFill_someTemplatesFilled() {
                IllegalArgumentException e = assertThrows(
                        IllegalArgumentException.class,
                        () -> MtxStringUtil.logFill(customTestReplVar, customTestStr, "a", "b")
                );
                assertEquals("Argument count mismatch", e.getMessage());
            }

            @Test
            public void testLogFill_noTemplatesFilled() {
                IllegalArgumentException e = assertThrows(
                        IllegalArgumentException.class,
                        () -> MtxStringUtil.logFill(customTestReplVar, customTestStr)
                );
                assertEquals("Argument count mismatch", e.getMessage());
            }

            @Test
            public void testLogFill_tooManyTemplatesFilled() {
                IllegalArgumentException e = assertThrows(
                        IllegalArgumentException.class,
                        () -> MtxStringUtil.logFill(customTestReplVar, customTestStr, "a", "b", "c", "d", "e")
                );
                assertEquals("Argument count mismatch", e.getMessage());
            }
        }
    }

    @Nested
    class TestLogMapFill {
        private static final String TEST_STR = "k1={}, k2={}, k3={}, k4={}";
        private static final String[] TEST_ARGS = {"v1", "v2", "v3", "v4"};

        @Test
        public void testLogMapFill_allTemplatesFilled() {
            Map<String, String> expected = Map.of("k1", "v1", "k2", "v2", "k3", "v3", "k4", "v4");
            assertEquals(expected, MtxStringUtil.logMapFill(TEST_STR, TEST_ARGS));
        }

        @Test
        public void testLogMapFill_mismatchedFormat() {
            Map<String, String> expected = Map.of("k1", "v1", "k2", "v2", "k3", "v3", "k4", "v4");
            assertEquals(expected, MtxStringUtil.logMapFill(TEST_STR + ", k5=v5=i5", TEST_ARGS));
        }
    }

    @Nested
    class TestAreAnagrams {
        private static final String[] ARE_ANAGRAMS = {"abc", "cba", "bac", "cba"};
        private static final String[] NOT_ANAGRAMS = {"abc", "aba", "dac", "cb", "abcd"};

        @Test
        public void testAreAnagrams_yes() {
            for (int i = 1; i < ARE_ANAGRAMS.length; i++) {
                assertTrue(MtxStringUtil.areAnagrams(ARE_ANAGRAMS[0], ARE_ANAGRAMS[i]));
            }
        }

        @Test
        public void testAreAnagrams_no() {
            for (int i = 1; i < NOT_ANAGRAMS.length; i++) {
                assertFalse(MtxStringUtil.areAnagrams(NOT_ANAGRAMS[0], NOT_ANAGRAMS[i]));
            }
        }

        @Test
        public void testAreAnagrams_differentLengths() {
            assertFalse(MtxStringUtil.areAnagrams("a", "abc"));
            assertFalse(MtxStringUtil.areAnagrams("abc", "a"));
        }

        @Test
        public void testAreAnagrams_nulls() {
            assertFalse(MtxStringUtil.areAnagrams(null, "abc"));
            assertFalse(MtxStringUtil.areAnagrams("abc", null));
        }

        @Test
        public void testAreAnagrams_emptyStrings() {
            assertTrue(MtxStringUtil.areAnagrams("", ""));
        }
    }
}
