package com.edavalos.mtx.util.string;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.regex.Pattern;

public class MtxLogger {
    @FunctionalInterface
    public interface MtxCompatibleLogger {
        void log(String statement);
    }

    protected static String getTimestamp() {
        LocalDate date = LocalDate.now();
        StringBuilder sb = new StringBuilder("[");
        sb.append(date.getYear()).append('-');
        sb.append(MtxStringUtil.padZeroToDateRelatedInt(date.getMonthValue())).append('-');
        sb.append(MtxStringUtil.padZeroToDateRelatedInt(date.getDayOfMonth())).append(' ');

        LocalTime time = LocalTime.now();
        int hour;
        char AmPm;
        if (time.getHour() == 0) {
            hour = 12;
            AmPm = 'A';
        } else if (time.getHour() <= 11) {
            hour = time.getHour();
            AmPm = 'A';
        } else if (time.getHour() == 12) {
            hour = 12;
            AmPm = 'P';
        } else {
            hour = time.getHour() % 12;
            AmPm = 'P';
        }
        sb.append(MtxStringUtil.padZeroToDateRelatedInt(hour)).append(':');
        sb.append(MtxStringUtil.padZeroToDateRelatedInt(time.getMinute())).append(':');
        sb.append(MtxStringUtil.padZeroToDateRelatedInt(time.getSecond())).append(' ').append(AmPm).append("M]");

        return sb.toString();
    }

    protected static String fillInBrackets(String originalLog, String... variables) {
        int varCount = MtxStringUtil.countOccurrencesOf(originalLog, "{}");
        if (variables.length != varCount) {
            throw new IllegalArgumentException("Number of arguments do not match log template! " +
                    "Expected " + varCount + " but found " + variables.length);
        }

        for (int i = 0; i < varCount; i++) {
            originalLog = originalLog.replaceFirst(Pattern.quote("{}"), variables[i]);
        }
        return originalLog;
    }

    public void log(String statement, MtxCompatibleLogger logger) {
        logger.log(getTimestamp() + " " + statement);
    }

    public void log(String statement) {
        System.out.println(getTimestamp() + " " + statement);
    }

    public void log(String template, String... args) {
        this.log(fillInBrackets(template, args));
    }

    public void log(String template, MtxCompatibleLogger logger, String... args) {
        this.log(fillInBrackets(template, args), logger);
    }
}
