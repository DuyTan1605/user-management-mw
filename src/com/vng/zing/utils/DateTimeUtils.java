/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vng.zing.utils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author tanhd
 */
public class DateTimeUtils {

    public static String getLocalDateTime(long msSeconds) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime date = LocalDateTime.ofInstant(Instant.ofEpochMilli(msSeconds), ZoneId.of("Asia/Ho_Chi_Minh"));
        return formatter.format(date);
    }

    public static long formatDateTime(String dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateFormat = LocalDateTime.parse(dateTime, formatter);
        return dateFormat.atZone(ZoneId.of("Asia/Ho_Chi_Minh")).toInstant().toEpochMilli();
    }

    public static java.sql.Date convertUtilToSql(long msSeconds) {
        java.util.Date uDate = new java.util.Date(msSeconds);
        java.sql.Date sDate = new java.sql.Date(uDate.getTime());
        return sDate;
    }
}
