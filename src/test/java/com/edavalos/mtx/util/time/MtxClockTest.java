package com.edavalos.mtx.util.time;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MtxClockTest {
    private static final int DELAY_LENGTH = 100;
    private final CountDownLatch waiter = new CountDownLatch(1);
    private MtxClock mtxClock;

    @BeforeEach
    public void setUp() {
        mtxClock = new MtxClock();
    }

    @Test
    public void testGetElapsed() {
        mtxClock.start();
        pause(DELAY_LENGTH);

        long timeElapsed = mtxClock.getElapsed();
        assertTrue(timeElapsed >= DELAY_LENGTH);
        assertTrue(timeElapsed < DELAY_LENGTH * 2);
        pause(DELAY_LENGTH);

        timeElapsed = mtxClock.getElapsed();
        assertTrue(timeElapsed >= DELAY_LENGTH * 2);
        assertTrue(timeElapsed < DELAY_LENGTH * 3);
    }

    @Test
    public void testRestart() {
        assertEquals(0, mtxClock.restart());
        pause(DELAY_LENGTH);

        long timeElapsed = mtxClock.restart();
        assertTrue(timeElapsed >= DELAY_LENGTH);
        assertTrue(timeElapsed < DELAY_LENGTH * 2);
        pause(DELAY_LENGTH);

        timeElapsed = mtxClock.restart();
        assertTrue(timeElapsed >= DELAY_LENGTH);
        assertTrue(timeElapsed < DELAY_LENGTH * 2);
    }

    private void pause(int milliseconds) {
        try {
            waiter.await(milliseconds, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
