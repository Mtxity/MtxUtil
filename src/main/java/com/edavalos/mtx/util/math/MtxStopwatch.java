package com.edavalos.mtx.util.math;

public class MtxStopwatch {
    private long startTime;
    private boolean started;
    private long resetTime;

    public MtxStopwatch() {
        this.startTime = 0;
        this.started = false;
        this.resetTime = 0;
    }

    public void start() {
        if (this.resetTime == 0) {
            this.startTime = System.currentTimeMillis();
        } else {
            this.startTime = resetTime;
        }
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
        this.resetTime = this.startTime;
        this.started = false;
        return gap;
    }
}
