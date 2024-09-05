package com.edavalos.mtx.util.math;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
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

            assertTrue(mtxStopwatch.getLaps().isEmpty());
        }

        @Test
        public void testGetLastLap() throws InterruptedException {
            verifyStartingValues(mtxStopwatch);

            mtxStopwatch.start();
            Thread.sleep(10);
            mtxStopwatch.stop();

            assertEquals(0, mtxStopwatch.getLastLap());
        }

        @Test
        public void testGetResetTime() throws InterruptedException {
            verifyStartingValues(mtxStopwatch);

            mtxStopwatch.start();
            Thread.sleep(10);
            mtxStopwatch.stop();

            assertNotEquals(0, mtxStopwatch.getResetTime());
        }

        private static void verifyStartingValues(MtxStopwatch sampleStopwatch) {
            assertEquals(0, sampleStopwatch.startTime);
            assertEquals(0, sampleStopwatch.resetTime);
            assertFalse(sampleStopwatch.started);
            assertTrue(sampleStopwatch.laps.isEmpty());
        }
    }

    @Nested
    public class StartStopTwiceTests {
        // @TODO: Implement test for this
    }

    @Nested
    public class StartStopXTimesTests {
        // @TODO: Implement test for this
    }

    @Nested
    public class StartStopResetTests {
        // @TODO: Implement test for this
    }

    @Nested
    public class StartStopResetStartTests {
        // @TODO: Implement test for this
    }

    @Nested
    public class StartStopResetStartStopTests {
        // @TODO: Implement test for this
    }

    @Nested
    public class StartStopResetStartStopResetXTimesTests {
        // @TODO: Implement test for this
    }
}
