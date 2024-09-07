package com.edavalos.mtx.util.time;

import java.util.Timer;
import java.util.TimerTask;

public class MtxTimerTask {

    @FunctionalInterface
    public interface Executable {
        void execute();
    }

    private static class MtxTimerTaskSchedule extends TimerTask {
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

                if (this.timesRan > this.timesToRun) {
                    this.cancel();
                }

                this.executable.execute();
                this.timesRan ++;
            }
        }
    }

    private final Timer timer;
    private final long delay;
    private final long period;
    private final TimerTask timerTask;

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
