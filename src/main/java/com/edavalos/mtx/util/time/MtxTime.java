package com.edavalos.mtx.util.time;

import java.text.ParseException;
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

    /**
     * Constructs a new time length representing the given string. String must be in HH:MM:SS format and meet the following conditions:
     * <ul>
     *   <li>Minutes and seconds cannot be greater than 60</li>
     *   <li>Hours cannot be greater than 24</li>
     *   <li>No component can be negative. For negative MtxTimes input the values as positives then run 'flipDirection()'</li>
     * </ul>
     */
    public MtxTime(String time) throws ParseException {
        if (!MtxTimeUtil.isValidTime(time)) {
            throw new ParseException(
                    "This string could not be parsed into a time!\n" +
                    "Time strings need to be in the format: HH:MM:SS and meet the following conditions:\n" +
                    "  * Minutes and seconds cannot be greater than 60\n" +
                    "  * Hours cannot be greater than 24\n" +
                    "  * No component can be negative. For negative MtxTimes input the values as positives then run 'flipDirection()'",
                    0
            );
        }

        int[] timeComponents = MtxTimeUtil.extractTimeDigits(time);

        this.days = 0;
        this.hours = timeComponents[0];
        this.minutes = timeComponents[1];
        this.seconds = timeComponents[2];
        this.backwards = false;
    }

    // ---------------------- Public Methods -----------------------

    /**
     * Adds the given time to this time
     */
    public void addTime(MtxTime time) {
        if (time.isBackwards()) {
            this.subtractTimeComponents(time);
        } else {
            this.addTimeComponents(time);
        }
        this.balance();
    }

    /**
     * Subtracts the given time from this time
     */
    public void subtractTime(MtxTime time) {
        if (time.isBackwards()) {
            this.addTimeComponents(time);
        } else {
            this.subtractTimeComponents(time);
        }
        this.balance();
    }

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

    @Override
    public String toString() {
        return new StringBuilder()
                   .append((this.backwards ? "-" : ""))
                   .append(
                           (this.days > 0) ?
                           this.days + "d + " :
                           ""
                   )
                   .append(MtxTimeUtil.addLeadingZero(this.hours))
                   .append(":")
                   .append(MtxTimeUtil.addLeadingZero(this.minutes))
                   .append(
                           (this.seconds > 0) ?
                           ":" + MtxTimeUtil.addLeadingZero(this.seconds) :
                           ""
                   )
                   .toString();
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

    private void addTimeComponents(MtxTime time) {
        this.days += time.getDays();
        this.hours += time.getHours();
        this.minutes += time.getMinutes();
        this.seconds += time.getSeconds();
    }

    private void subtractTimeComponents(MtxTime time) {
        this.days -= time.getDays();
        this.hours -= time.getHours();
        this.minutes -= time.getMinutes();
        this.seconds -= time.getSeconds();
    }
}
