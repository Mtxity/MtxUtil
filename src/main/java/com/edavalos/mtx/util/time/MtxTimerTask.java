package com.edavalos.mtx.util.time;

import java.lang.reflect.Executable;
import java.util.Timer;
import java.util.TimerTask;

public class MtxTimerTask {

    private class MtxTimerTaskSchedule extends TimerTask {
        private final int timesToRun;
        private final Executable executable;
        private int timesRan;

        public MtxTimerTaskSchedule(int timesToRun, Executable executable) {
            this.timesToRun = timesToRun;
            this.executable = executable;
            this.timesRan = 0;
        }

        @Override
        public void run() {
            synchronized (this) {
                this.notify();
            }

            if (this.timesRan > this.timesToRun) {
                synchronized (this) {
                    this.cancel();
                }
            }

            this.executable.execute();
            this.timesRan ++;
        }
    }

    private Timer timer;
    private long delay;
    private long period;
    private TimerTask timerTask;

    public MtxTimerTask(Executable executable, long delay, long period, int timesToRun) {
        this.timer = new Timer();
        this.delay = delay;
        this.period = period;
        this.timerTask = new MtxTimerTaskSchedule(timesToRun, executable);
    }

    public void start() {
        timer.schedule(this.timerTask, this.delay, this.period);
    }
}
