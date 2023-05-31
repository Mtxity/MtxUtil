package com.edavalos.mtx.util.time;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
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
                    LocalDateTime otherTime = LocalDateTime.now()
                                              .plus(1, ChronoUnit.YEARS)
                                              .plus(1, ChronoUnit.MINUTES);
                    assertEquals(expected, MtxTimeUtil.getRelativeTimeToNow(otherTime));
                }

                @Test
                public void testGetRelativeTimeToNow_future_singular_months() {
                    String expected = "in 1 month";
                    LocalDateTime otherTime = LocalDateTime.now()
                                              .plus(1, ChronoUnit.MONTHS)
                                              .plus(1, ChronoUnit.MINUTES);
                    assertEquals(expected, MtxTimeUtil.getRelativeTimeToNow(otherTime));
                }

                @Test
                public void testGetRelativeTimeToNow_future_singular_days() {
                    String expected = "in 1 day";
                    LocalDateTime otherTime = LocalDateTime.now()
                                              .plus(1, ChronoUnit.DAYS)
                                              .plus(1, ChronoUnit.MINUTES);
                    assertEquals(expected, MtxTimeUtil.getRelativeTimeToNow(otherTime));
                }

                @Test
                public void testGetRelativeTimeToNow_future_singular_hours() {
                    String expected = "in 1 hour";
                    LocalDateTime otherTime = LocalDateTime.now()
                                              .plus(1, ChronoUnit.HOURS)
                                              .plus(1, ChronoUnit.MINUTES);
                    assertEquals(expected, MtxTimeUtil.getRelativeTimeToNow(otherTime));
                }

                @Test
                public void testGetRelativeTimeToNow_future_singular_minutes() {
                    String expected = "in 1 minute";
                    LocalDateTime otherTime = LocalDateTime.now()
                                              .plus(2, ChronoUnit.MINUTES);
                    assertEquals(expected, MtxTimeUtil.getRelativeTimeToNow(otherTime));
                }

                @Test
                public void testGetRelativeTimeToNow_future_singular_seconds() {
                    String expected = "in 1 second";
                    LocalDateTime otherTime = LocalDateTime.now()
                                              .plus(2, ChronoUnit.SECONDS);
                    assertEquals(expected, MtxTimeUtil.getRelativeTimeToNow(otherTime));
                }
            }

            @Nested
            class GetFuturePluralRelativeTimeToNowTests {

                @Test
                public void testGetRelativeTimeToNow_future_plural_years() {
                    String expected = "in 2 years";
                    LocalDateTime otherTime = LocalDateTime.now()
                                              .plus(2, ChronoUnit.YEARS)
                                              .plus(1, ChronoUnit.MINUTES);
                    assertEquals(expected, MtxTimeUtil.getRelativeTimeToNow(otherTime));
                }

                @Test
                public void testGetRelativeTimeToNow_future_plural_months() {
                    String expected = "in 10 months";
                    LocalDateTime otherTime = LocalDateTime.now()
                                              .plus(10, ChronoUnit.MONTHS)
                                              .plus(1, ChronoUnit.MINUTES);
                    assertEquals(expected, MtxTimeUtil.getRelativeTimeToNow(otherTime));
                }

                @Test
                public void testGetRelativeTimeToNow_future_plural_days() {
                    String expected = "in 5 days";
                    LocalDateTime otherTime = LocalDateTime.now()
                                             .plus(5, ChronoUnit.DAYS)
                                             .plus(1, ChronoUnit.MINUTES);
                    assertEquals(expected, MtxTimeUtil.getRelativeTimeToNow(otherTime));
                }

                @Test
                public void testGetRelativeTimeToNow_future_plural_hours() {
                    String expected = "in 9 hours";
                    LocalDateTime otherTime = LocalDateTime.now()
                                             .plus(9, ChronoUnit.HOURS)
                                             .plus(1, ChronoUnit.MINUTES);
                    assertEquals(expected, MtxTimeUtil.getRelativeTimeToNow(otherTime));
                }

                @Test
                public void testGetRelativeTimeToNow_future_plural_minutes() {
                    String expected = "in 32 minutes";
                    LocalDateTime otherTime = LocalDateTime.now()
                                              .plus(33, ChronoUnit.MINUTES);
                    assertEquals(expected, MtxTimeUtil.getRelativeTimeToNow(otherTime));
                }

                @Test
                public void testGetRelativeTimeToNow_future_plural_seconds() {
                    String expected = "in 50 seconds";
                    LocalDateTime otherTime = LocalDateTime.now()
                                              .plus(51, ChronoUnit.SECONDS);
                    assertEquals(expected, MtxTimeUtil.getRelativeTimeToNow(otherTime));
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
                    LocalDateTime otherTime = LocalDateTime.now()
                                              .minus(1, ChronoUnit.YEARS)
                                              .minus(1, ChronoUnit.MINUTES);
                    assertEquals(expected, MtxTimeUtil.getRelativeTimeToNow(otherTime));
                }

                @Test
                public void testGetRelativeTimeToNow_past_singular_months() {
                    String expected = "1 month ago";
                    LocalDateTime otherTime = LocalDateTime.now()
                                              .minus(1, ChronoUnit.MONTHS)
                                              .minus(1, ChronoUnit.MINUTES);
                    assertEquals(expected, MtxTimeUtil.getRelativeTimeToNow(otherTime));
                }

                @Test
                public void testGetRelativeTimeToNow_past_singular_days() {
                    String expected = "1 day ago";
                    LocalDateTime otherTime = LocalDateTime.now()
                                              .minus(1, ChronoUnit.DAYS)
                                              .minus(1, ChronoUnit.MINUTES);
                    assertEquals(expected, MtxTimeUtil.getRelativeTimeToNow(otherTime));
                }

                @Test
                public void testGetRelativeTimeToNow_past_singular_hours() {
                    String expected = "1 hour ago";
                    LocalDateTime otherTime = LocalDateTime.now()
                                              .minus(1, ChronoUnit.HOURS)
                                              .minus(1, ChronoUnit.MINUTES);
                    assertEquals(expected, MtxTimeUtil.getRelativeTimeToNow(otherTime));
                }

                @Test
                public void testGetRelativeTimeToNow_past_singular_minutes() {
                    String expected = "1 minute ago";
                    LocalDateTime otherTime = LocalDateTime.now()
                                              .minus(1, ChronoUnit.MINUTES);
                    assertEquals(expected, MtxTimeUtil.getRelativeTimeToNow(otherTime));
                }

                @Test
                public void testGetRelativeTimeToNow_past_singular_seconds() {
                    String expected = "1 second ago";
                    LocalDateTime otherTime = LocalDateTime.now()
                                              .minus(1, ChronoUnit.SECONDS);
                    assertEquals(expected, MtxTimeUtil.getRelativeTimeToNow(otherTime));
                }
            }

            @Nested
            class GetPastPluralRelativeTimeToNowTests {

                @Test
                public void testGetRelativeTimeToNow_past_plural_years() {
                    String expected = "2 years ago";
                    LocalDateTime otherTime = LocalDateTime.now()
                                              .minus(2, ChronoUnit.YEARS)
                                              .minus(1, ChronoUnit.MINUTES);
                    assertEquals(expected, MtxTimeUtil.getRelativeTimeToNow(otherTime));
                }

                @Test
                public void testGetRelativeTimeToNow_past_plural_months() {
                    String expected = "10 months ago";
                    LocalDateTime otherTime = LocalDateTime.now()
                                              .minus(10, ChronoUnit.MONTHS)
                                              .minus(1, ChronoUnit.MINUTES);
                    assertEquals(expected, MtxTimeUtil.getRelativeTimeToNow(otherTime));
                }

                @Test
                public void testGetRelativeTimeToNow_past_plural_days() {
                    String expected = "5 days ago";
                    LocalDateTime otherTime = LocalDateTime.now()
                                              .minus(5, ChronoUnit.DAYS)
                                              .minus(1, ChronoUnit.MINUTES);
                    assertEquals(expected, MtxTimeUtil.getRelativeTimeToNow(otherTime));
                }

                @Test
                public void testGetRelativeTimeToNow_past_plural_hours() {
                    String expected = "9 hours ago";
                    LocalDateTime otherTime = LocalDateTime.now()
                                              .minus(9, ChronoUnit.HOURS)
                                              .minus(1, ChronoUnit.MINUTES);
                    assertEquals(expected, MtxTimeUtil.getRelativeTimeToNow(otherTime));
                }

                @Test
                public void testGetRelativeTimeToNow_past_plural_minutes() {
                    String expected = "32 minutes ago";
                    LocalDateTime otherTime = LocalDateTime.now()
                                              .minus(32, ChronoUnit.MINUTES);
                    assertEquals(expected, MtxTimeUtil.getRelativeTimeToNow(otherTime));
                }

                @Test
                public void testGetRelativeTimeToNow_past_plural_seconds() {
                    String expected = "50 seconds ago";
                    LocalDateTime otherTime = LocalDateTime.now()
                                              .minus(50, ChronoUnit.SECONDS);
                    assertEquals(expected, MtxTimeUtil.getRelativeTimeToNow(otherTime));
                }
            }
        }

        @Test
        public void testGetRelativeTimeToNow_now() {
            String expected = "now";
            assertEquals(expected, MtxTimeUtil.getRelativeTimeToNow(LocalDateTime.now()));
        }
    }
}
