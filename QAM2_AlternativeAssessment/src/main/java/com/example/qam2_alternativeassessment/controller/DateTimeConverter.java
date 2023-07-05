package com.example.qam2_alternativeassessment.controller;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.TimeZone;

public class DateTimeConverter {

    /**
     * @param date Date
     * @param timezone Timezone
     * @return local date time
     */
    public static LocalDateTime dateToLocalDateTimeTimezone(Date date, ZoneId timezone) {
        return localDateTimeTimezone(dateToLocalDateTime(date), timezone);
    }

    /**
     * @param date Date
     * @return Local date and time
     */
    public static LocalDateTime dateToLocalDateTime(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    /**
     * @param date Date
     * @param timezone Time zone
     * @return local date and time
     */
    public static LocalDateTime localDateTimeTimezone(LocalDateTime date, ZoneId timezone) {
        return date.atZone(ZoneId.systemDefault()).withZoneSameInstant(timezone).toLocalDateTime();
    }

    public static LocalDateTime get8am(LocalDate date) {
        return LocalDateTime.of(date, LocalTime.of(8, 0));
    }

    public static LocalDateTime get10pm(LocalDate date) {
        return LocalDateTime.of(date, LocalTime.of(22, 0));
    }

    /**
     * This method converts the time to UTC time
     *
     * @param dateTime date time as parameter
     * @return UTC time
     */
    public static String convertTimeDateUTC(String dateTime) {
        Timestamp currentTimeStamp = Timestamp.valueOf(String.valueOf(dateTime));
        LocalDateTime LocalDT = currentTimeStamp.toLocalDateTime();
        ZonedDateTime zoneDT = LocalDT.atZone(ZoneId.of(ZoneId.systemDefault().toString()));
        ZonedDateTime utcDT = zoneDT.withZoneSameInstant(ZoneId.of("UTC"));
        LocalDateTime localOUT = utcDT.toLocalDateTime();
        String utcOUT = localOUT.format(DateTimeFormatter.ofPattern("yyy-MM-dd HH:mm:ss"));
        return utcOUT;
    }

    /**
     * This method converts the time to UTC time
     *
     * @param dateTime date time as parameter
     * @return Local time
     */
    public static String convertTimeDateLocal(String dateTime) {
        String converted = "";
        ZoneId z = ZoneId.systemDefault();
        try {
            DateFormat utcFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            utcFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

            Date date = utcFormat.parse(dateTime);
            DateFormat pstFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            pstFormat.setTimeZone(TimeZone.getTimeZone(z));
            converted = pstFormat.format(date);

        } catch (Exception e) {
            System.err.println(e);
        }
        return converted;
    }

}
