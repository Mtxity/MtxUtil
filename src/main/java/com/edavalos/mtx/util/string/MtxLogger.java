package com.edavalos.mtx.util.string;

import java.sql.Timestamp;
import java.util.Calendar;

public class MtxLogger {
    protected static String getTimestamp() {
        Timestamp ts = new Timestamp(System.currentTimeMillis());
        StringBuilder sb = new StringBuilder("[");
        sb.append(ts.getYear()).append('-');
        sb.append(ts.getDate()).append('-');
        sb.append(ts.getMonth());

        return sb.toString();
    }
}
