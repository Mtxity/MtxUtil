package com.edavalos.mtx.util.math;

public class MtxStopwatch {
    private long startTime;

    public MtxStopwatch() { }

    public void start() {
        startTime = System.currentTimeMillis();
    }
}
