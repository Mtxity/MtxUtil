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
    private TimerTask timerTask;

    public MtxTimerTask(long delay, long period) {
        this.timer = new Timer();
        this.timer.schedule(new MtxTimerTaskSchedule(), delay, period);
    }
}
