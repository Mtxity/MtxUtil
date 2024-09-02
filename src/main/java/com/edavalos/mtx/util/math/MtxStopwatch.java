package com.edavalos.mtx.util.math;

public class MtxStopwatch {
    private long startTime;
    private boolean started;

    public MtxStopwatch() {
        this.startTime = 0;
        this.started = false;
    }

    public void start() {
        this.startTime = System.currentTimeMillis();
        this.started = true;
    }

    public long lap() {
        return System.currentTimeMillis() - this.startTime;
    }

    public long stop() {
        if (!this.started) {
            return 0;
        }

        long gap = System.currentTimeMillis() - this.startTime;
        this.startTime = 0;
        this.started = false;
        return gap;
    }
}
