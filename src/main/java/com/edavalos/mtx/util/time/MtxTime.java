package com.edavalos.mtx.util.time;

import java.time.LocalDateTime;

/**
 * A time is a vector with a length and direction falling on the 2D time number line.
 * Days, hours, minutes and seconds represent the length. 'Backwards' represents the direction.
 */
public final class MtxTime {
    private static final int MAX_HOURS_LENGTH = 24;
    private static final int MAX_MINUTES_LENGTH = 60;
    private static final int MAX_SECONDS_LENGTH = 60;

    private int days;
    private int hours;
    private int minutes;
    private int seconds;
    // If enough time is subtracted from this time to become negative, it is now facing backwards
    private boolean backwards;

    // ----------------------- Constructors ------------------------

    /**
     * Constructs a new time length with given values for hours, minutes, seconds and days
     */
    public MtxTime(int hours, int minutes, int seconds, int days) {
        this.days = days;
        this.hours = hours;
        this.minutes = minutes;
        this.seconds = seconds;
        this.backwards = false;

        this.balance();
    }

    /**
     * Constructs a new time length with given values for hours, minutes and seconds
     */
    public MtxTime(int hours, int minutes, int seconds) {
        this(hours, minutes, seconds, 0);
    }

    /**
     * Constructs a new time length with given values for hours and minutes
     */
    public MtxTime(int hours, int minutes) {
        this(hours, minutes, 0, 0);
    }

    /**
     * Constructs a new time length representing the time between last midnight and now
     */
    public MtxTime() {
        this(LocalDateTime.now().getHour(),
             LocalDateTime.now().getMinute(),
             LocalDateTime.now().getSecond(),
             0);
    }

    // ---------------------- Public Methods -----------------------

    /**
     * @return true if this time is longer than the given time
     */
    public boolean isLongerThan(MtxTime time) {
        return this.getTotalSeconds() > time.getTotalSeconds();
    }

    public int getDays() {
        return this.days;
    }

    public void setDays(int days) {
        this.days = days;
        this.balance();
    }

    public int getHours() {
        return this.hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
        this.balance();
    }

    public int getMinutes() {
        return this.minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
        this.balance();
    }

    public int getSeconds() {
        return this.seconds;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
        this.balance();
    }

    public boolean isBackwards() {
        return this.backwards;
    }

    public void flipDirection() {
        this.backwards = !this.backwards;
    }

    // ---------------------- Private Methods ----------------------

    private void balance() {
        this.balancePositive();
        this.balanceNegative();
    }

    private void balancePositive() {
        // Balance overflow seconds
        while (this.seconds >= MAX_SECONDS_LENGTH * 2) {
            this.minutes ++;
            this.seconds -= MAX_SECONDS_LENGTH;
        }
        if (this.seconds >= MAX_SECONDS_LENGTH) {
            this.minutes ++;
            this.seconds %= MAX_SECONDS_LENGTH;
        }

        // Balance overflow minutes
        while (this.minutes >= MAX_MINUTES_LENGTH * 2) {
            this.hours ++;
            this.minutes -= MAX_MINUTES_LENGTH;
        }
        if (this.minutes >= MAX_MINUTES_LENGTH) {
            this.hours ++;
            this.minutes %= MAX_MINUTES_LENGTH;
        }

        // Balance overflow hours
        while (this.hours >= MAX_HOURS_LENGTH * 2) {
            this.days ++;
            this.hours -= MAX_HOURS_LENGTH;
        }
        if (this.hours >= MAX_HOURS_LENGTH) {
            this.days ++;
            this.hours %= MAX_HOURS_LENGTH;
        }
    }

    private void balanceNegative() {
        // Balance negative seconds
        while (this.seconds < MAX_SECONDS_LENGTH * -1) {
            this.minutes --;
            this.seconds += MAX_SECONDS_LENGTH;
        }
        if (this.seconds < 0) {
            this.minutes --;
            this.seconds = MAX_SECONDS_LENGTH - Math.abs(this.seconds);
        }

        // Balance negative minutes
        while (this.minutes < MAX_MINUTES_LENGTH * -1) {
            this.hours --;
            this.minutes += MAX_MINUTES_LENGTH;
        }
        if (this.minutes < 0) {
            this.hours --;
            this.minutes = MAX_MINUTES_LENGTH - Math.abs(this.minutes);
        }

        // Balance negative hours
        while (this.hours < MAX_HOURS_LENGTH * -1) {
            this.days --;
            this.hours += MAX_HOURS_LENGTH;
        }
        if (this.hours < 0) {
            this.days --;
            this.hours = MAX_HOURS_LENGTH - Math.abs(this.hours);
        }

        // Balance negative days
        if (this.days < 0) {
            this.days = Math.abs(this.days);
            this.backwards = !this.backwards;
        }
    }

    private int getTotalSeconds() {
        int totalHours = this.hours + (this.days * MAX_HOURS_LENGTH);
        int totalMinutes = this.minutes + (totalHours * MAX_MINUTES_LENGTH);
        return this.seconds + (totalMinutes * MAX_SECONDS_LENGTH);
    }
}
