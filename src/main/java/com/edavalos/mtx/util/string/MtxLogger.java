package com.edavalos.mtx.util.string;

import java.time.LocalDate;
import java.time.LocalTime;

public class MtxLogger {
    protected static String getTimestamp() {
        LocalDate date = LocalDate.now();
        StringBuilder sb = new StringBuilder("[");
        sb.append(date.getYear()).append('-');
        sb.append(date.getMonthValue()).append('-');
        sb.append(date.getDayOfMonth()).append(' ');

        LocalTime time = LocalTime.now();
        sb.append(time.getHour()).append(':');
        sb.append(time.getMinute()).append(':');
        sb.append(time.getSecond()).append(']');

        return sb.toString();
    }
}
