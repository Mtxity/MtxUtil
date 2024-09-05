package com.edavalos.mtx.util.math;

import java.util.LinkedList;
import java.util.List;

public class MtxStartWatch {
    private long startTime;
    private boolean started;
    private long resetTime;
    private List<Long> laps;

    public MtxStartWatch() {
        this.startTime = 0;
        this.started = false;
        this.resetTime = 0;
        this.laps = new LinkedList<>();
    }

    public void start() {
        if (this.resetTime == 0) {
            this.startTime = System.currentTimeMillis();
        } else {
            this.startTime = this.resetTime;
        }
        this.started = true;
    }

    public long lap() {
        long lap = System.currentTimeMillis() - this.startTime;
        this.laps.add(lap);
        return lap;
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

    public long reset() {
        long lap = this.resetTime;

        this.startTime = 0;
        this.started = false;
        this.resetTime = 0;
        this.laps.clear();

        return lap;
    }

    public List<Long> getLaps() {
        return this.laps;
    }
}
