package com.platydev.calculmental.data.utils;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Utils {

    public static final  DateTimeFormatter MM_SS_TIME_FORMATTER = DateTimeFormatter.ofPattern("mm:ss");
    public static final int HOUR_IN_SECONDS = 3600;
    public static final int DAY_IN_SECONDS = 24*3600;

    public static String formatTime(long time) {
        if (time < DAY_IN_SECONDS) {
            LocalTime localTime = LocalTime.ofSecondOfDay(time);
            DateTimeFormatter formatter = time < HOUR_IN_SECONDS ? MM_SS_TIME_FORMATTER : DateTimeFormatter.ISO_LOCAL_TIME;
            return localTime.format(formatter);
        } else {
            long days = time / DAY_IN_SECONDS;
            LocalTime localTime = LocalTime.ofSecondOfDay(time%DAY_IN_SECONDS);
            return days + "j " + localTime.format(DateTimeFormatter.ISO_LOCAL_TIME);
        }
    }
}
