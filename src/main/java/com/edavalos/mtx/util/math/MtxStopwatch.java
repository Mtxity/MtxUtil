package com.edavalos.mtx.util.math;

public class MtxStopwatch {
    private long startTime;

    public MtxStopwatch() { }

    public void start() {
        this.startTime = System.currentTimeMillis();
    }

    public long lap() {
        return System.currentTimeMillis() - startTime;
    }

    public long stop() {
        long gap = System.currentTimeMillis() - startTime;
        startTime = 0;
        return gap;
    }
}
