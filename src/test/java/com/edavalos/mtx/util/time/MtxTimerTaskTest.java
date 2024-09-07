package com.edavalos.mtx.util.time;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MtxTimerTaskTest {
    private static final long SAMPLE_DELAY = 5;
    private static final long SAMPLE_PERIOD = 1;
    private static final int SAMPLE_TIMES_TO_RUN = 20;

    private MtxTimerTask mtxTimerTask;

    @BeforeEach
    public void setUp() {
        mtxTimerTask = new MtxTimerTask(SAMPLE_DELAY, SAMPLE_PERIOD, SAMPLE_TIMES_TO_RUN);
    }

    @Test
    public void testRun() throws InterruptedException {
        mtxTimerTask.start();
    }

    @Test
    public void testSchedule() {
//        mtxTimerTask.schedule();
//        mtxTimerTask.run();
    }
}
