package com.edavalos.mtx.util.time;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public final class MtxTimeUtilTest {
    @Nested
    class IsValidTimeTests {
        final String[] VALID_TIMES_TWOS_PLACES = new String[]{
                "00:00:00",
                "00:30:30",
                "00:59:59",
                "06:40:20",
                "11:46:27"
        };
        final String[] VALID_TIMES_ONES_PLACES = new String[]{
                "0:0:0",
                "0:3:30",
                "0:59:9",
                "6:40:20",
                "11:6:7"
        };
        final String[] INVALID_TIMES_TOO_BIG = new String[]{
                "0:00:61",
                "0:345:30",
                "25:59:9",
                "004:59:9",
        };
        final String[] INVALID_TIMES_NEGATIVES = new String[]{
                "-10:49:59",
                "8:30:-20",
                "2:-10:-53"
        };

        @Test
        public void testIsValidTime_twosPlaces() {
            for (String testCase : VALID_TIMES_TWOS_PLACES) {
                assertTrue(MtxTimeUtil.isValidTime(testCase));
            }
        }

        @Test
        public void testIsValidTime_onesPlaces() {
            for (String testCase : VALID_TIMES_ONES_PLACES) {
                assertTrue(MtxTimeUtil.isValidTime(testCase));
            }
        }

        @Test
        public void testIsValidTime_invalid_tooBig() {
            for (String testCase : INVALID_TIMES_TOO_BIG) {
                assertFalse(MtxTimeUtil.isValidTime(testCase));
            }
        }

        @Test
        public void testIsValidTime_invalid_negative() {
            for (String testCase : INVALID_TIMES_NEGATIVES) {
                assertFalse(MtxTimeUtil.isValidTime(testCase));
            }
        }
    }

    @Nested
    class ExtractTimeDigitsTests {
        final String TIME_WITH_SECONDS = "12:34:56";
        final String TIME_WITHOUT_SECONDS = "12:34";

        @Test
        public void testExtractTimeDigits_withSeconds() {
            int[] expected = new int[]{12, 34, 56};
            assertEquals(
                    Arrays.toString(expected),
                    Arrays.toString(MtxTimeUtil.extractTimeDigits(TIME_WITH_SECONDS))
            );
        }

        @Test
        public void testExtractTimeDigits_withoutSeconds() {
            int[] expected = new int[]{12, 34, 0};
            assertEquals(
                    Arrays.toString(expected),
                    Arrays.toString(MtxTimeUtil.extractTimeDigits(TIME_WITHOUT_SECONDS))
            );
        }
    }
}
