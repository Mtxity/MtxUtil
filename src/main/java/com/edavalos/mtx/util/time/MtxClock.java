package com.edavalos.mtx.util.time;

public final class MtxClock {
    private long startTime;

    public MtxClock() {
        this.startTime = -1;
    }

    public void start() {
        this.startTime = System.currentTimeMillis();
    }

    public long getElapsed() {
        long endTime = System.currentTimeMillis();
        return endTime - this.startTime;
    }

    public long restart() {
        long timeElapsed = (this.startTime == -1)
                           ? 0
                           : this.getElapsed();
        this.start();
        return timeElapsed;
    }
}
