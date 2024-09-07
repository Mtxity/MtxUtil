package com.edavalos.mtx.util.time;

import java.util.Timer;
import java.util.TimerTask;

public class MtxTimerTask {

    private class MtxTimerTaskSchedule extends TimerTask {
        private final int timesToRun;
        private int timesRan;

        public MtxTimerTaskSchedule(int timesToRun) {
            this.timesToRun = timesToRun;
            this.timesRan = 0;
        }

        @Override
        public void run() {
            if (this.timesRan > this.timesToRun) {
                this.cancel();
            }

            System.out.println("Test");
            this.timesRan ++;
        }
    }

    private Timer timer;
    private long delay;
    private long period;
    private TimerTask timerTask;

    public MtxTimerTask(long delay, long period, int timesToRun) {
        this.timer = new Timer();
        this.delay = delay;
        this.period = period;
        this.timerTask = new MtxTimerTaskSchedule(timesToRun);
    }

    public void start() {
        timer.schedule(this.timerTask, this.delay, this.period);
    }
}
