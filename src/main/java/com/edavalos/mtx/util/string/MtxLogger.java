package com.edavalos.mtx.util.string;

import java.time.LocalDate;
import java.time.LocalTime;

public class MtxLogger {
    protected static String getTimestamp() {
        LocalDate date = LocalDate.now();
        StringBuilder sb = new StringBuilder("[");
        sb.append(date.getYear()).append('-');
        sb.append(MtxStringUtil.padZeroToDateRelatedInt(date.getMonthValue())).append('-');
        sb.append(MtxStringUtil.padZeroToDateRelatedInt(date.getDayOfMonth())).append(' ');

        LocalTime time = LocalTime.now();
        char amPm = (time.getSecond() > 11) ? 'P' : 'A';
        sb.append(MtxStringUtil.padZeroToDateRelatedInt(time.getHour())).append(':');
        sb.append(MtxStringUtil.padZeroToDateRelatedInt(time.getMinute())).append(':');
        sb.append(MtxStringUtil.padZeroToDateRelatedInt(time.getSecond())).append(' ').append(amPm).append("M]");

        return sb.toString();
    }
}
