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

    @Nested
    class AddLeadingZeroTests {

        @Test
        public void testAddLeadingZero_noZeroNeeded() {
            for (int i = 10; i < 20; i++) {
                assertEquals(String.valueOf(i), MtxTimeUtil.addLeadingZero(i));
            }
        }

        @Test
        public void testAddLeadingZero_yesZeroNeeded() {
            for (int i = 0; i < 10; i++) {
                assertEquals("0" + i, MtxTimeUtil.addLeadingZero(i));
            }
        }
    }

    @Nested
    class GetRelativeTimeToNowTests {

        @Nested
        class GetFutureRelativeTimeToNowTests {

            @Nested
            class GetFutureSingularRelativeTimeToNowTests {

                @Test
                public void testGetRelativeTimeToNow_future_singular_years() {
                    String expected = "in 1 year";
                }

                @Test
                public void testGetRelativeTimeToNow_future_singular_months() {
                    String expected = "in 1 month";
                }

                @Test
                public void testGetRelativeTimeToNow_future_singular_days() {
                    String expected = "in 1 day";
                }

                @Test
                public void testGetRelativeTimeToNow_future_singular_hours() {
                    String expected = "in 1 hour";
                }

                @Test
                public void testGetRelativeTimeToNow_future_singular_minutes() {
                    String expected = "in 1 minute";
                }

                @Test
                public void testGetRelativeTimeToNow_future_singular_seconds() {
                    String expected = "in 1 second";
                }
            }

            @Nested
            class GetFuturePluralRelativeTimeToNowTests {

                @Test
                public void testGetRelativeTimeToNow_future_plural_years() {
                    String expected = "in 2 years";
                }

                @Test
                public void testGetRelativeTimeToNow_future_plural_months() {
                    String expected = "in 10 months";
                }

                @Test
                public void testGetRelativeTimeToNow_future_plural_days() {
                    String expected = "in 5 days";
                }

                @Test
                public void testGetRelativeTimeToNow_future_plural_hours() {
                    String expected = "in 9 hours";
                }

                @Test
                public void testGetRelativeTimeToNow_future_plural_minutes() {
                    String expected = "in 32 minutes";
                }

                @Test
                public void testGetRelativeTimeToNow_future_plural_seconds() {
                    String expected = "in 50 seconds";
                }
            }
        }

        @Nested
        class GetPastRelativeTimeToNowTests {

            @Nested
            class GetPastSingularRelativeTimeToNowTests {

                @Test
                public void testGetRelativeTimeToNow_past_singular_years() {
                    String expected = "1 year ago";
                }

                @Test
                public void testGetRelativeTimeToNow_past_singular_months() {
                    String expected = "1 month ago";
                }

                @Test
                public void testGetRelativeTimeToNow_past_singular_days() {
                    String expected = "1 day ago";
                }

                @Test
                public void testGetRelativeTimeToNow_past_singular_hours() {
                    String expected = "1 hour ago";
                }

                @Test
                public void testGetRelativeTimeToNow_past_singular_minutes() {
                    String expected = "1 minute ago";
                }

                @Test
                public void testGetRelativeTimeToNow_past_singular_seconds() {
                    String expected = "1 second ago";
                }
            }

            @Nested
            class GetPastPluralRelativeTimeToNowTests {

                @Test
                public void testGetRelativeTimeToNow_past_plural_years() {
                    String expected = "2 years ago";
                }

                @Test
                public void testGetRelativeTimeToNow_past_plural_months() {
                    String expected = "10 months ago";
                }

                @Test
                public void testGetRelativeTimeToNow_past_plural_days() {
                    String expected = "5 days ago";
                }

                @Test
                public void testGetRelativeTimeToNow_past_plural_hours() {
                    String expected = "9 hours ago";
                }

                @Test
                public void testGetRelativeTimeToNow_past_plural_minutes() {
                    String expected = "32 minutes ago";
                }

                @Test
                public void testGetRelativeTimeToNow_past_plural_seconds() {
                    String expected = "50 seconds ago";
                }
            }
        }
    }
}
