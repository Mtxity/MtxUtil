package com.edavalos.mtx.util.time;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MtxTimerTaskTest {
    private static final long SAMPLE_DELAY = 1;
    private static final long SAMPLE_PERIOD = 1;
    private static final int SAMPLE_TIMES_TO_RUN = 1;

    private static int SAMPLE_COUNTER;
    private static final MtxTimerTask.Executable SAMPLE_METHOD = () -> SAMPLE_COUNTER ++;

    private MtxTimerTask mtxTimerTask;

    @BeforeEach
    public void setUp() {
        SAMPLE_COUNTER = 0;
        mtxTimerTask = new MtxTimerTask(SAMPLE_METHOD, SAMPLE_DELAY, SAMPLE_PERIOD, SAMPLE_TIMES_TO_RUN);
    }

    @Test
    public void testStart() throws InterruptedException {
        mtxTimerTask.start();

        Thread.sleep(10);
        assertEquals(3, SAMPLE_COUNTER);
    }
}
