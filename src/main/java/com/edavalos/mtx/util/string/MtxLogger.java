package com.edavalos.mtx.util.string;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

public class MtxLogger {
    protected static String getTimestamp() {
        Date ts = new Date();
        StringBuilder sb = new StringBuilder("[");
        sb.append(ts.getYear()).append('-');
        sb.append(ts.getMonth()).append('-');
        sb.append(ts.getDay());

        return sb.toString();
    }
}
