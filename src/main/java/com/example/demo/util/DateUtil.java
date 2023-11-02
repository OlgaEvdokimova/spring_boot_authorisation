package com.example.demo.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtil {

    public static final String DATE_TIME_FORMAT = "dd-MM-yyyy HH:mm";

    public static String formatter(LocalDateTime localDateTime) {
        DateTimeFormatter format = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);
        return localDateTime.format(format);
    }
}
