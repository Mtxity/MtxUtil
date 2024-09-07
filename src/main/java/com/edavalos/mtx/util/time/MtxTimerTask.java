package com.edavalos.mtx.util.time;

import java.util.Timer;
import java.util.TimerTask;

public class MtxTimerTask {

    private class MtxTimerTaskSchedule extends TimerTask {

        @Override
        public void run() {
            System.out.println("Test");
        }
    }

    private Timer timer;
    private long delay;
    private long period;
    private TimerTask timerTask;

    public MtxTimerTask(long delay, long period) {
        this.timer = new Timer();
        this.delay = delay;
        this.period = period;
    }

    public void start() {
        timer.schedule(new MtxTimerTaskSchedule(), this.delay, this.period);
    }
}
