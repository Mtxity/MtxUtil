package com.edavalos.mtx.util.time;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public final class MtxTimeTest {
    private static final String SAMPLE_TIME = "12:51";

    private MtxTime mtxTime;

    @Nested
    class ConstructorTests {
        final int SAMPLE_DAYS = 1;
        final int SAMPLE_HOURS = 5;
        final int SAMPLE_MINUTES = 25;
        final int SAMPLE_SECONDS = 45;
        final String SAMPLE_VALID_TIME = "05:2:19";
        final String SAMPLE_INVALID_TIME = "13:64:79";

        @Test
        public void testConstructor_hoursMinutesSecondsDays() {
            mtxTime = new MtxTime(SAMPLE_HOURS, SAMPLE_MINUTES, SAMPLE_SECONDS, SAMPLE_DAYS);

            assertEquals(SAMPLE_DAYS, mtxTime.getDays());
            assertEquals(SAMPLE_HOURS, mtxTime.getHours());
            assertEquals(SAMPLE_MINUTES, mtxTime.getMinutes());
            assertEquals(SAMPLE_SECONDS, mtxTime.getSeconds());
        }

        @Test
        public void testConstructor_hoursMinutesSeconds() {
            mtxTime = new MtxTime(SAMPLE_HOURS, SAMPLE_MINUTES, SAMPLE_SECONDS);

            assertEquals(0, mtxTime.getDays());
            assertEquals(SAMPLE_HOURS, mtxTime.getHours());
            assertEquals(SAMPLE_MINUTES, mtxTime.getMinutes());
            assertEquals(SAMPLE_SECONDS, mtxTime.getSeconds());
        }

        @Test
        public void testConstructor_hoursMinutes() {
            mtxTime = new MtxTime(SAMPLE_HOURS, SAMPLE_MINUTES);

            assertEquals(0, mtxTime.getDays());
            assertEquals(SAMPLE_HOURS, mtxTime.getHours());
            assertEquals(SAMPLE_MINUTES, mtxTime.getMinutes());
            assertEquals(0, mtxTime.getSeconds());
        }

        @Test
        public void testConstructor_empty() {
            mtxTime = new MtxTime();
            LocalDateTime now = LocalDateTime.now();

            assertEquals(0, mtxTime.getDays());
            assertEquals(now.getHour(), mtxTime.getHours());
            assertEquals(now.getMinute(), mtxTime.getMinutes());
            assertEquals(now.getSecond(), mtxTime.getSeconds());
        }

        @Test
        public void testConstructor_timeString_valid() {
            try {
                mtxTime = new MtxTime(SAMPLE_VALID_TIME);
            } catch (ParseException e) {
                fail();
            }
        }

        @Test
        public void testConstructor_timeString_invalid() {
            assertThrows(ParseException.class, () -> mtxTime = new MtxTime(SAMPLE_INVALID_TIME));
        }
    }

    @Test
    public void testIsLongerThan() {
        try {
            mtxTime = new MtxTime(SAMPLE_TIME);
            MtxTime longerTime = new MtxTime("12:52");
            MtxTime shorterTime = new MtxTime("12:50");
            MtxTime equalTime = new MtxTime(SAMPLE_TIME);

            assertFalse(mtxTime.isLongerThan(longerTime));
            assertTrue(mtxTime.isLongerThan(shorterTime));
            assertFalse(mtxTime.isLongerThan(equalTime));
        } catch (ParseException e) {
            fail();
        }
    }

    @Nested
    class BalancePositiveTests {

        @Test
        public void testBalancePositive_overflowSeconds_oneMinuteOver() {
            mtxTime = new MtxTime(0, 0, 70);
            assertEquals(10, mtxTime.getSeconds());
            assertEquals(1, mtxTime.getMinutes());
        }

        @Test
        public void testBalancePositive_overflowSeconds_manyMinutesOver() {
            mtxTime = new MtxTime(0, 0, 310);
            assertEquals(10, mtxTime.getSeconds());
            assertEquals(5, mtxTime.getMinutes());
        }

        @Test
        public void testBalancePositive_overflowMinutes_oneHourOver() {
            mtxTime = new MtxTime(0, 70, 0);
            assertEquals(10, mtxTime.getMinutes());
            assertEquals(1, mtxTime.getHours());
        }

        @Test
        public void testBalancePositive_overflowMinutes_manyHoursOver() {
            mtxTime = new MtxTime(0, 315, 0);
            assertEquals(15, mtxTime.getMinutes());
            assertEquals(5, mtxTime.getHours());
        }

        @Test
        public void testBalancePositive_overflowHours_oneDayOver() {
            mtxTime = new MtxTime(28, 0, 0);
            assertEquals(4, mtxTime.getHours());
            assertEquals(1, mtxTime.getDays());
        }

        @Test
        public void testBalancePositive_overflowHours_manyDaysOver() {
            mtxTime = new MtxTime(125, 0, 0);
            assertEquals(5, mtxTime.getHours());
            assertEquals(5, mtxTime.getDays());
        }
    }

    @Nested
    class BalanceNegativeTests {

        @Test
        public void testBalanceNegative_overflowSeconds_oneMinuteOver() {
            mtxTime = new MtxTime(0, 2, -25);
            assertEquals(35, mtxTime.getSeconds());
            assertEquals(1, mtxTime.getMinutes());
        }

        @Test
        public void testBalanceNegative_overflowSeconds_manyMinutesOver() {
            mtxTime = new MtxTime(0, 6, -245);
            assertEquals(55, mtxTime.getSeconds());
            assertEquals(1, mtxTime.getMinutes());
        }

        @Test
        public void testBalanceNegative_overflowMinutes_oneHourOver() {
            mtxTime = new MtxTime(3, -40, 0);
            assertEquals(20, mtxTime.getMinutes());
            assertEquals(2, mtxTime.getHours());
        }

        @Test
        public void testBalanceNegative_overflowMinutes_manyHoursOver() {
            mtxTime = new MtxTime(6, -250, 0);
            assertEquals(50, mtxTime.getMinutes());
            assertEquals(1, mtxTime.getHours());
        }

        @Test
        public void testBalanceNegative_overflowHours_oneDayOver() {
            mtxTime = new MtxTime(-6, 0, 0);
            assertEquals(18, mtxTime.getHours());
            assertEquals(1, mtxTime.getDays());
            assertTrue(mtxTime.isBackwards());
        }

        @Test
        public void testBalanceNegative_overflowHours_manyDaysOver() {
            mtxTime = new MtxTime(-145, 0, 0);
            assertEquals(23, mtxTime.getHours());
            assertEquals(7, mtxTime.getDays());
            assertTrue(mtxTime.isBackwards());
        }
    }

    @Nested
    class SetterTests {
        @BeforeEach
        public void setUp() {
            mtxTime = new MtxTime(0, 0, 0, 0);
        }

        @Test
        public void testSetDays() {
            mtxTime.setDays(10);
            assertEquals(10, mtxTime.getDays());
        }

        @Test
        public void testSetHours() {
            mtxTime.setHours(11);
            assertEquals(11, mtxTime.getHours());
        }

        @Test
        public void testSetMinutes() {
            mtxTime.setMinutes(12);
            assertEquals(12, mtxTime.getMinutes());
        }

        @Test
        public void testSetSeconds() {
            mtxTime.setSeconds(13);
            assertEquals(13, mtxTime.getSeconds());
        }

        @Test
        public void testFlipDirection() {
            assertFalse(mtxTime.isBackwards());
            mtxTime.flipDirection();
            assertTrue(mtxTime.isBackwards());
        }
    }

    @Nested
    class AddTimeTests {
        @BeforeEach
        public void setUp() {
            mtxTime = new MtxTime(15, 15, 15, 0);
        }

        @Test
        public void testAddTime_noDayChange_noBalancing() {
            MtxTime otherTime = new MtxTime(8, 10, 8);
            mtxTime.addTime(otherTime);
            // seconds: 15 +  8 = 23
            // minutes: 15 + 10 = 25
            //   hours: 15 +  8 = 23
            //    days:  0 +  0 =  0

            assertEquals(23, mtxTime.getSeconds());
            assertEquals(25, mtxTime.getMinutes());
            assertEquals(23, mtxTime.getHours());
            assertEquals(0, mtxTime.getDays());
        }

        @Test
        public void testAddTime_noDayChange_yesBalancing() {
            MtxTime otherTime = new MtxTime(7, 50, 58);
            mtxTime.addTime(otherTime);
            // seconds: 15 + 58 = 73
            //                    73 % 60 = 13 rem 1
            // minutes: 15 + 50 = 65
            //                   (65 + 1) % 60 = 6 rem 1
            //   hours: 15 +  7 = 22
            //                   (22 + 1) = 23
            //    days:  0 +  0 =  0

            assertEquals(13, mtxTime.getSeconds());
            assertEquals(6, mtxTime.getMinutes());
            assertEquals(23, mtxTime.getHours());
            assertEquals(0, mtxTime.getDays());
        }

        @Test
        public void testAddTime_yesDayChange() {
            MtxTime otherTime = new MtxTime(50, 15, 15, 0);
            mtxTime.addTime(otherTime);
            // seconds: 15 + 15 = 30
            // minutes: 15 + 15 = 30
            //   hours: 15 + 50 = 65
            //                    65 % 24 = 17 rem 2
            //    days:  0 +  0 =  0
            //                    (0 + 2) = 2

            assertEquals(30, mtxTime.getSeconds());
            assertEquals(30, mtxTime.getMinutes());
            assertEquals(17, mtxTime.getHours());
            assertEquals(2, mtxTime.getDays());
        }

        @Test
        public void testAddTime_addNegativeTime() {
            MtxTime otherTime = new MtxTime(2, 5, 10, 0);
            otherTime.flipDirection();
            mtxTime.addTime(otherTime);
            // seconds: 15 - 10 =  5
            // minutes: 15 -  5 = 10
            //   hours: 15 -  2 = 13
            //    days:  0 -  0 =  0

            assertEquals(5, mtxTime.getSeconds());
            assertEquals(10, mtxTime.getMinutes());
            assertEquals(13, mtxTime.getHours());
            assertEquals(0, mtxTime.getDays());
        }
    }

    @Nested
    class SubtractTimeTests {
        @BeforeEach
        public void setUp() {
            mtxTime = new MtxTime(15, 30, 30, 5);
        }

        @Test
        public void testSubtractTime_noDayChange_noBalancing() {
            MtxTime otherTime = new MtxTime(15, 10, 5);
            mtxTime.subtractTime(otherTime);
            // seconds: 30 -  5 = 25
            // minutes: 30 - 10 = 20
            //   hours: 15 - 15 =  0
            //    days:  5 -  0 =  5

            assertEquals(25, mtxTime.getSeconds());
            assertEquals(20, mtxTime.getMinutes());
            assertEquals(0, mtxTime.getHours());
            assertEquals(5, mtxTime.getDays());
        }

        @Test
        public void testSubtractTime_noDayChange_yesBalancing() {
            MtxTime otherTime = new MtxTime(7, 50, 95);
            mtxTime.subtractTime(otherTime);
            // seconds: 30 - 95 = -65
            //                    -65 - 120 = 55 (- 2 minutes)
            // minutes: 30 - 50 = -20
            //                    -20 -   2 = -22
            //                    -22 = 38 (- 1 hour)
            //   hours: 15 -  7 =   8
            //                      8 - 1 = 7
            //    days:  5 -  0 =  0

            assertEquals(55, mtxTime.getSeconds());
            assertEquals(38, mtxTime.getMinutes());
            assertEquals(7, mtxTime.getHours());
            assertEquals(5, mtxTime.getDays());
        }

        @Test
        public void testSubtractTime_yesDayChange() {
            MtxTime otherTime = new MtxTime(35, 0, 0);
            mtxTime.subtractTime(otherTime);
            // seconds: 30 -  0 = 30
            // minutes: 30 -  0 = 30
            //   hours: 15 - 35 = 15 - (1 day & 11 hours)
            //                    15 - 11 = 4 (- 1 day)
            //    days:  5 -  0 =  5
            //                     5 - 1 = 4

            assertEquals(30, mtxTime.getSeconds());
            assertEquals(30, mtxTime.getMinutes());
            assertEquals(4, mtxTime.getHours());
            assertEquals(4, mtxTime.getDays());
        }

        @Test
        public void testSubtractTime_subtractNegativeTime() {
            MtxTime otherTime = new MtxTime(8, 8, 8);
            otherTime.flipDirection();
            mtxTime.subtractTime(otherTime);
            // seconds: 30 +  8 = 38
            // minutes: 30 +  8 = 38
            //   hours: 15 +  8 = 23
            //    days:  5 +  0 =  5

            assertEquals(38, mtxTime.getSeconds());
            assertEquals(38, mtxTime.getMinutes());
            assertEquals(23, mtxTime.getHours());
            assertEquals(5, mtxTime.getDays());
        }
    }

    @Nested
    class ToStringTests {

        @Test
        public void testToString_hoursMinutes() {
            mtxTime = new MtxTime(8, 32);
            String stringRepresentation = "08:32";

            assertEquals(stringRepresentation, mtxTime.toString());
        }

        @Test
        public void testToString_hoursMinutesSeconds() {
            mtxTime = new MtxTime(12, 32, 4);
            String stringRepresentation = "12:32:04";

            assertEquals(stringRepresentation, mtxTime.toString());
        }

        @Test
        public void testToString_hoursMinutesSecondsDays() {
            mtxTime = new MtxTime(12, 32, 4, 20);
            String stringRepresentation = "20d 12:32:04";

            assertEquals(stringRepresentation, mtxTime.toString());
        }

        @Test
        public void testToString_negative_hoursMinutes() {
            mtxTime = new MtxTime(9, 14);
            mtxTime.flipDirection();
            String stringRepresentation = "-09:14";

            assertEquals(stringRepresentation, mtxTime.toString());
        }

        @Test
        public void testToString_negative_hoursMinutesDays() {
            mtxTime = new MtxTime(9, 14, 0, 6);
            mtxTime.flipDirection();
            String stringRepresentation = "-6d 09:14";

            assertEquals(stringRepresentation, mtxTime.toString());
        }
    }

    @Nested
    class EqualsTests {
        @BeforeEach
        public void setUp() {
            mtxTime = new MtxTime(1, 2, 3, 4);
        }

        @Test
        public void testEquals_trivial() {
            assertFalse(mtxTime.equals(null));
            assertFalse(mtxTime.equals("not an MtxTime"));
        }

        @Test
        public void testEquals_nontrivial() {
            MtxTime otherTime = new MtxTime(1, 0, 3, 4);
            assertFalse(mtxTime.equals(otherTime));

            otherTime.setMinutes(2);
            assertTrue(mtxTime.equals(otherTime));
        }
    }

    @Test
    public void testHashCode() {
        try {
            mtxTime = new MtxTime(SAMPLE_TIME);

            // I'm just going to trust Java on this one
            int correctHash = 29036891;
            assertEquals(correctHash, mtxTime.hashCode());
        } catch (ParseException e) {
            fail();
        }
    }
}
