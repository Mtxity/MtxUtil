package com.edavalos.mtx.util.string;

import java.time.LocalDate;

public class MtxLogger {
    protected static String getTimestamp() {
        LocalDate ts = LocalDate.now();
        StringBuilder sb = new StringBuilder("[");
        sb.append(ts.getYear()).append('-');
        sb.append(ts.getMonthValue()).append('-');
        sb.append(ts.getDayOfMonth());

        return sb.toString();
    }
}
