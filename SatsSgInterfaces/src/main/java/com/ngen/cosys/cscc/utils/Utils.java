package com.ngen.cosys.cscc.utils;

import com.ngen.cosys.multitenancy.timezone.support.TenantTimeZoneUtility;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Utils {
    public final static String DATE_TIME_FORMAT_NO_SECOND = "dd/MM/yyyy HH:mm";
    public final static String DB_DATE_TIME_FORMAT_NO_SECOND = "yyyy-MM-dd HH:mm";
    public final static String DATE_TIME_FORMAT_SECOND = "dd/MM/yyyy HH:mm:ss";
    public final static String DATE_FORMAT = "dd/MM/yyyy";
    public final static String DB_DATE_FORMAT = "yyyy-MM-dd";
    public final static String ULD_PATTERN = "[A-Z]{3}[0-9]{4,5}[A-Z0-9]{2,3}";
    public final static String AWB_PATTERN = "[A-Z0-9]{3}[0-9]{8}";

    public static String getLocalDateTime() {
        LocalDateTime ldt = getTenantLocalDateTime();

        return formatDateTime(ldt, DATE_TIME_FORMAT_SECOND);
    }

    public static String isnull(String val, String defaultVal) {
        if (isNullOrBlank(val)) return defaultVal;

        return val;
    }

    public static LocalDateTime getTenantLocalDateTime() {
        return TenantTimeZoneUtility.fromServerToTenantDateTime(LocalDateTime.now());
    }

    public static LocalDate getTenantLocalDate() {
        return TenantTimeZoneUtility.fromServerToTenantDate(LocalDate.now());
    }

    public static LocalDateTime getDbLocalDateTime(LocalDateTime localDateTime){
        return TenantTimeZoneUtility.fromTenantToDatabaseDateTime(localDateTime);
    }

    public static LocalDate getDBLocalDate(LocalDate localDate){
        return TenantTimeZoneUtility.fromTenantToDatabaseDate(localDate);
    }

    public static String formatDateTime(LocalDateTime ld, String format) {
        String formatedDate = "";

        if (format == null || "".equals(format)) {
            format = DATE_TIME_FORMAT_NO_SECOND;
        }

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(format);
        formatedDate = ld.format(dtf);

        return formatedDate;
    }

    public static String formatDate(LocalDate ld, String format) {
        String formatedDate = "";

        if (format == null || "".equals(format)) {
            format = DATE_FORMAT;
        }

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(format);
        formatedDate = ld.format(dtf);

        return formatedDate;
    }

    public static boolean isNullOrBlank(String val) {
        return val == null || "".equals(val);
    }

    public static boolean isNotBlank(String val){
        return !isNullOrBlank(val);
    }

    public static LocalDateTime convert2DateTime(String datetime, String format) {
        if (datetime == null || "".equals(datetime)) {
            return null;
        }

        if (format == null || "".equals(format)) {
            format = DATE_TIME_FORMAT_NO_SECOND;
        }

        LocalDateTime ldt = null;
        try {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern(format);
            ldt = LocalDateTime.parse(datetime, dtf);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            ldt = null;
        }

        return ldt;
    }

    public static LocalDateTime convert2DateTime(String datetime) {
        return convert2DateTime(datetime, DATE_TIME_FORMAT_NO_SECOND);
    }

    public static LocalDate convert2Date(String date, String format) {
        if (date == null || "".equals(date)) {
            return null;
        }
        if (format == null || "".equals(format)) {
            format = DATE_FORMAT;
        }
        LocalDate ldt = null;
        try {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern(format);
            ldt = LocalDate.parse(date, dtf);
        } catch (Exception e) {
            return null;
        }
        return ldt;
    }

    public static LocalDate convert2Date(String date) {
        return convert2Date(date, DATE_FORMAT);
    }

    public static boolean validatedULD(String uldNumber) {
        return uldNumber.matches(ULD_PATTERN);
    }

    public static boolean validatedAWB(String awbNumber) {
        return awbNumber.matches(AWB_PATTERN);
    }
}
