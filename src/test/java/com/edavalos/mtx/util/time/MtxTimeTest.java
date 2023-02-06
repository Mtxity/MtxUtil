package com.edavalos.mtx.util.time;

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
}
