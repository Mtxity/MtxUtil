package com.edavalos.mtx.util.math;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

public class MtxStopwatch {
    protected long startTime;
    protected boolean started;
    protected long resetTime;
    protected List<Long> laps;

    public MtxStopwatch() {
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
        this.laps.add(gap);
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
        return new LinkedList<>(this.laps);
    }

    public int getNumberOfLaps() {
        return this.laps.size();
    }

    public long getLastLap() {
        try {
            return this.laps.get(this.laps.size() - 1);
        } catch (NoSuchElementException | IndexOutOfBoundsException e) {
            return 0;
        }
    }

    public long getResetTime() {
        return this.resetTime;
    }
}
