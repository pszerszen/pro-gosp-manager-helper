package com.manager.store.utils;

import org.apache.commons.lang3.StringUtils;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @author Piotr
 */
public final class DateUtils {

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private DateUtils(){}

    public static Date toDate(String date){
        if (StringUtils.isBlank(date)) {
            return null;
        }
        final LocalDate localDate = LocalDate.parse(date, DATE_FORMAT);
        return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }

    public static Date toDateTime(String dateTime){
        if (StringUtils.isBlank(dateTime)) {
            return null;
        }
        final LocalDateTime localDateTime = LocalDateTime.parse(dateTime, DATE_TIME_FORMAT);
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static String fromDate(Date date){
        if(date == null){
            return null;
        }
        final LocalDate localDate = Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
        return localDate.format(DATE_FORMAT);
    }

    public static String fromDateTime(Date dateTime){
        if(dateTime == null){
            return null;
        }
        final LocalDateTime localDateTime = Instant.ofEpochMilli(dateTime.getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();
        return localDateTime.format(DATE_TIME_FORMAT);
    }
}
