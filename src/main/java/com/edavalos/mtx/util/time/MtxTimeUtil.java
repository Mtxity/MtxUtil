package com.edavalos.mtx.util.time;

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
}
