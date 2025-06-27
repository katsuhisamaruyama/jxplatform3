/*
 *  Copyright 2022
 *  Software Science and Technology Lab., Ritsumeikan University
 */

package org.jtool.jxplatform.builder;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.ZoneId;
import java.util.Calendar;

/**
 * Manages time information.
 * 
 * @author Katsuhisa Maruyama
 */
public class TimeInfo {
    
    /**
     * Creates a new, empty object that builds Java models.
     */
    private TimeInfo() {
    }
    
    /**
     * Returns an {@code ZonedDateTime} object corresponding to the current time.
     * @return the {@code ZonedDateTime} object
     */
    public static ZonedDateTime getCurrentTime() {
        return ZonedDateTime.now();
    }
    
    /**
     * Returns the epoch second corresponding to the current time.
     * @return the number of seconds from 1970-01-01T00:00:00Z
     */
    public static long getCurrentTimeAsLong() {
        return getTimeAsLong(ZonedDateTime.now());
    }
    
    /**
     * Returns the number of seconds corresponding to given time information.
     * @param time the {@code ZonedDateTime} object
     * @return the number of seconds from 1970-01-01T00:00:00Z
     */
    public static long getTimeAsLong(ZonedDateTime time) {
        return time.toInstant().toEpochMilli();
    }
    
    /**
     * Returns the ISO-formatted string for given time information.
     * @param time the {@code ZonedDateTime} object
     * @return the formatted string
     */
    public static String getTimeAsISOString(ZonedDateTime time) {
        return time.format(DateTimeFormatter.ISO_ZONED_DATE_TIME);
    }
    
    /**
     * Returns the formatted string of the time for given time information.
     * @param time the {@code ZonedDateTime} object
     * @return the formatted string
     */
    public static String getFormatedTime(ZonedDateTime time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss.SSS");
        return time.format(formatter);
    }
    
    /**
     * Returns the formatted string of the date for given time information.
     * @param time the {@code ZonedDateTime} object
     * @return the formatted string
     */
    public static String getFormatedDate(ZonedDateTime time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        return time.format(formatter);
    }
    
    /**
     * Returns an {@code ZonedDateTime} object obtained from a given string.
     * @param str the string that represents the time
     * @return the {@code ZonedDateTime} object
     */
    public static ZonedDateTime getTime(String str) {
        return ZonedDateTime.parse(str, DateTimeFormatter.ISO_ZONED_DATE_TIME);
    }
    
    /**
     * Returns an {@code ZonedDateTime} object for a given number of seconds.
     * @param utime the number of seconds from 1970-01-01T00:00:00Z
     * @return the {@code ZonedDateTime} object
     */
    public static ZonedDateTime getTime(long utime) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(utime);
        return ZonedDateTime.ofInstant(calendar.toInstant(), ZoneId.systemDefault());
    }
}
