package com.edavalos.mtx.util.time;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.regex.Pattern;

public final class MtxTimeUtil {
    private static final Pattern REGEX_VALID_TIME = Pattern.compile("^(([0-1])?[0-9]|2[0-3]):([0-5])?[0-9](:([0-5])?[0-9])?$");

    private MtxTimeUtil() { }

    public static boolean isValidTime(String timeStr) {
        return REGEX_VALID_TIME.matcher(timeStr).matches();
    }

    public static int[] extractTimeDigits(String timeStr) {
        assert isValidTime(timeStr);

        String[] places = timeStr.split(":");
        int hours;
        int minutes;
        int seconds;

        if (places.length == 2) {
            seconds = 0;
        } else {
            seconds = Integer.parseInt(places[2]);
        }

        minutes = Integer.parseInt(places[1]);
        hours = Integer.parseInt(places[0]);

        return new int[]{hours, minutes, seconds};
    }

    public static String addLeadingZero(int time) {
        return (time < 10) ?
                "0" + time :
                String.valueOf(time);
    }

    // Source: https://stackoverflow.com/a/25760725
    public static String getRelativeTimeToNow(LocalDateTime otherTime) {
        LocalDateTime now = LocalDateTime.now();
        boolean isInFuture = otherTime.isAfter(now);

        LocalDateTime fromDateTime, toDateTime;
        if (isInFuture) {
            fromDateTime = now;
            toDateTime = otherTime;
        } else {
            fromDateTime = otherTime;
            toDateTime = now;
        }

        LocalDateTime tempDateTime = LocalDateTime.from(fromDateTime);

        long years = tempDateTime.until(toDateTime, ChronoUnit.YEARS);
        tempDateTime = tempDateTime.plusYears(years);

        long months = tempDateTime.until(toDateTime, ChronoUnit.MONTHS);
        tempDateTime = tempDateTime.plusMonths(months);

        long days = tempDateTime.until(toDateTime, ChronoUnit.DAYS);
        tempDateTime = tempDateTime.plusDays(days);

        long hours = tempDateTime.until(toDateTime, ChronoUnit.HOURS);
        tempDateTime = tempDateTime.plusHours(hours);

        long minutes = tempDateTime.until(toDateTime, ChronoUnit.MINUTES);
        tempDateTime = tempDateTime.plusMinutes(minutes);

        long seconds = tempDateTime.until(toDateTime, ChronoUnit.SECONDS);

        String summarizedTimeLength;
        if (years > 0) {
            summarizedTimeLength = years + " year" + (years > 1 ? "s" : "");
        } else if (months > 0) {
            summarizedTimeLength = months + " month" + (months > 1 ? "s" : "");
        } else if (days > 0) {
            summarizedTimeLength = days + " day" + (days > 1 ? "s" : "");
        } else if (hours > 0) {
            summarizedTimeLength = hours + " hour" + (hours > 1 ? "s" : "");
        } else if (minutes > 0) {
            summarizedTimeLength = minutes + " minute" + (minutes > 1 ? "s" : "");
        } else if (seconds > 0) {
            summarizedTimeLength = seconds + " second" + (seconds > 1 ? "s" : "");
        } else {
            return "now";
        }

        if (isInFuture) {
            return "in " + summarizedTimeLength;
        } else {
            return summarizedTimeLength + " ago";
        }
    }
}
