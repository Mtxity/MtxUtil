package com.edavalos.mtx.util.math;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MtxStopwatchTest {

    private MtxStopwatch mtxStopwatch;

    @BeforeEach
    public void setUp() {
        mtxStopwatch = new MtxStopwatch();
    }

    @Nested
    public class StartStopOnceTests {

        @Test
        public void testStart() {
            verifyStartingValues(mtxStopwatch);

            mtxStopwatch.start();

            assertNotEquals(0, mtxStopwatch.startTime);
            assertTrue(mtxStopwatch.started);
        }

        @Test
        public void testStop() {
            verifyStartingValues(mtxStopwatch);

            long gap = mtxStopwatch.stop();

            assertEquals(mtxStopwatch.resetTime, 0);
            assertFalse(mtxStopwatch.started);
            assertEquals(0, gap);
        }

        @Test
        public void testStartStop() throws InterruptedException {
            verifyStartingValues(mtxStopwatch);

            mtxStopwatch.start();
            Thread.sleep(10);
            long gap = mtxStopwatch.stop();

            assertEquals(mtxStopwatch.resetTime, mtxStopwatch.startTime);
            assertFalse(mtxStopwatch.started);
            assertNotEquals(0, gap);
        }

        @Test
        public void testGetLaps() throws InterruptedException {
            verifyStartingValues(mtxStopwatch);

            mtxStopwatch.start();
            Thread.sleep(10);
            mtxStopwatch.stop();

            assertFalse(mtxStopwatch.getLaps().isEmpty());
        }

        @Test
        public void testGetNumberOfLaps() {
            verifyStartingValues(mtxStopwatch);

            mtxStopwatch.start();
            mtxStopwatch.stop();

            assertEquals(1, mtxStopwatch.getNumberOfLaps());
        }

        @Test
        public void testGetLastLap() throws InterruptedException {
            verifyStartingValues(mtxStopwatch);

            mtxStopwatch.start();
            Thread.sleep(10);
            mtxStopwatch.stop();

            assertNotEquals(0, mtxStopwatch.getLastLap());
        }

        @Test
        public void testGetResetTime() throws InterruptedException {
            verifyStartingValues(mtxStopwatch);

            mtxStopwatch.start();
            Thread.sleep(10);
            mtxStopwatch.stop();

            assertNotEquals(0, mtxStopwatch.getResetTime());
        }
    }

    @Nested
    public class StartStopTwiceTests {

        @Test
        public void testStartStopTwice() throws InterruptedException {
            verifyStartingValues(mtxStopwatch);

            mtxStopwatch.start();
            Thread.sleep(10);
            long gap1 = mtxStopwatch.stop();

            mtxStopwatch.start();
            Thread.sleep(10);
            long gap2 = mtxStopwatch.stop();

            assertEquals(mtxStopwatch.resetTime, mtxStopwatch.startTime);
            assertFalse(mtxStopwatch.started);
            assertTrue(gap1 >= 10);
            assertTrue(gap2 >= 20);
        }

        @Test
        public void testGetResetTime() throws InterruptedException {
            verifyStartingValues(mtxStopwatch);

            mtxStopwatch.start();
            Thread.sleep(10);
            mtxStopwatch.stop();

            mtxStopwatch.start();
            Thread.sleep(10);
            mtxStopwatch.stop();

            assertTrue(mtxStopwatch.getResetTime() >= 20);
        }
    }

    @Nested
    public class StartStopXTimesTests {
        private static final int X = 25;

        @Test
        public void testStartStopXTimes() throws InterruptedException {
            verifyStartingValues(mtxStopwatch);

            HashMap<Integer, Long> expectedTimes = new HashMap<>();
            for (int i = 0; i < X; i++) {
                mtxStopwatch.start();
                assertTrue(mtxStopwatch.started);

                Thread.sleep(1);
                long x = mtxStopwatch.stop();

                assertTrue(x >= i);
                expectedTimes.put(i, x);
            }

            for (Map.Entry<Integer, Long> expectedTime : expectedTimes.entrySet()) {
                assertTrue(expectedTime.getValue() >= expectedTime.getKey());
            }
        }
    }

    @Nested
    public class StartStopResetTests {

        @Test
        public void testStartStopReset() throws InterruptedException {
            verifyStartingValues(mtxStopwatch);

            mtxStopwatch.start();
            Thread.sleep(10);
            long gap = mtxStopwatch.stop();
            mtxStopwatch.reset();

            assertEquals(0, mtxStopwatch.startTime);
            assertEquals(0, mtxStopwatch.resetTime);
            assertTrue(gap >= 10);
            assertFalse(mtxStopwatch.started);
            assertTrue(mtxStopwatch.laps.isEmpty());
        }
    }

    @Nested
    public class StartStopResetStartTests {

        @Test
        public void testStartStopResetStart() throws InterruptedException {
            verifyStartingValues(mtxStopwatch);

            mtxStopwatch.start();
            Thread.sleep(10);
            mtxStopwatch.stop();
            Thread.sleep(5);
            mtxStopwatch.reset();
            Thread.sleep(10);
            mtxStopwatch.start();

            assertNotEquals(0, mtxStopwatch.startTime);
            assertEquals(0, mtxStopwatch.resetTime);
            assertTrue(mtxStopwatch.started);
        }
    }

    @Nested
    public class StartStopResetStartStopTests {
        private static final int X = 25;

        @Test
        public void testStartStopResetStartStop() throws InterruptedException {
            verifyStartingValues(mtxStopwatch);

            mtxStopwatch.start();
            Thread.sleep(10);
            mtxStopwatch.stop();
            Thread.sleep(5);
            mtxStopwatch.reset();
            Thread.sleep(10);
            mtxStopwatch.start();
            Thread.sleep(10);
            long gap = mtxStopwatch.stop();
            mtxStopwatch.reset();

            assertEquals(0, mtxStopwatch.startTime);
            assertEquals(0, mtxStopwatch.resetTime);
            assertTrue(gap >= 10);
            assertFalse(mtxStopwatch.started);
        }

        @Test
        public void testStartStopLapStartStop() throws InterruptedException {
            verifyStartingValues(mtxStopwatch);

            mtxStopwatch.start();
            Thread.sleep(1);
            mtxStopwatch.lap();
            Thread.sleep(1);
            mtxStopwatch.lap();
            Thread.sleep(1);
            mtxStopwatch.lap();
            Thread.sleep(1);
            long gap = mtxStopwatch.stop();

            assertTrue(mtxStopwatch.startTime >= 10);
            assertTrue(gap >= 5);
            assertFalse(mtxStopwatch.started);

            List<Long> laps = mtxStopwatch.getLaps();
            for (int i = 0; i < 3; i++) {
                if (laps.contains(((long) i))) {
                    assertNotNull(laps.get(i));
                    assertTrue(laps.get(i) >= 1);
                }
            }
            assertEquals(4, mtxStopwatch.getNumberOfLaps());
        }

        @Test
        public void testStartStopLapStartStopXTimes() throws InterruptedException {
            mtxStopwatch.start();
            Thread.sleep(1);

            for (int i = 0; i < X; i++) {
                mtxStopwatch.lap();
                Thread.sleep(1);
                mtxStopwatch.lap();
                Thread.sleep(1);
                mtxStopwatch.lap();
                Thread.sleep(1);
                mtxStopwatch.stop();

                List<Long> laps = mtxStopwatch.getLaps();
                for (int j = 0; j < 3; j++) {
                    if (laps.contains(((long) j))) {
                        assertNotNull(laps.get(j));
                        assertTrue(laps.get(j) >= 1);
                    }
                }
                assertEquals(laps.size(), mtxStopwatch.getNumberOfLaps());
            }
        }
    }

    @Nested
    public class StartStopResetStartStopResetXTimesTests {
        private static final int X = 25;

        @Test
        public void testStartStopResetStartStop() {
            verifyStartingValues(mtxStopwatch);

            for (int i = 0; i < X; i++) {
                mtxStopwatch.start();
                mtxStopwatch.stop();
                mtxStopwatch.reset();
                mtxStopwatch.start();
                mtxStopwatch.stop();
                mtxStopwatch.reset();
            }

            assertEquals(0, mtxStopwatch.startTime);
            assertEquals(0, mtxStopwatch.resetTime);
            assertFalse(mtxStopwatch.started);
        }
    }

    private static void verifyStartingValues(MtxStopwatch sampleStopwatch) {
        assertEquals(0, sampleStopwatch.startTime);
        assertEquals(0, sampleStopwatch.resetTime);
        assertFalse(sampleStopwatch.started);
        assertTrue(sampleStopwatch.laps.isEmpty());
    }
}
