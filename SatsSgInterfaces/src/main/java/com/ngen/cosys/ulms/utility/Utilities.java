package com.ngen.cosys.ulms.utility;

import com.ngen.cosys.multitenancy.timezone.support.TenantTimeZoneUtility;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class Utilities {
    private static final String INTERFACE_DATE_TIME_FORMAT = "yyyyMMddHHmmss";
    private static final String DB_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final String DATE_TIME_FORMAT="yyyyMMdd";
    private static DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern(DB_DATE_TIME_FORMAT);

    private static final Map<String, String> SUB_MESSAGE_TYPE = new HashMap<String, String>() {
        {
            put("flightAssignedULD", "FltULDList");
            put("flightULDPDAssociation", "NMEULD");
            put("flightAssignedULDChanges", "ULDFltChange");
            put("ULDReleaseToAgent", "ULDRlsAgt");
        }
    };


    public static LocalDateTime getDBDateTime(String dateStr, LocalDateTime serverDateTime) {
        LocalDateTime tenantDateTime = null;
        if (dateStr != null && !"".equals(dateStr)) {
            try {
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern(INTERFACE_DATE_TIME_FORMAT);
                tenantDateTime = LocalDateTime.parse(dateStr.replace("T", ""), dtf);

            } catch (Exception e) {
                //tenantDateTime = null;
                return null;
            }
        }
        if (tenantDateTime == null && serverDateTime == null) return null;

        if (tenantDateTime != null)
            return TenantTimeZoneUtility.fromTenantToDatabaseDateTime(tenantDateTime);

        return TenantTimeZoneUtility.fromServerToDatabaseDateTime(serverDateTime);
    }

    public static String formatToDBDateTime(LocalDateTime ldt) {
        return ldt.format(FORMATTER);
    }

    public static String formatToXmlDateTime(LocalDateTime ldt) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd HHmmss");
        return ldt.format(formatter).replace(" ", "T");
    }

    public static String getSubMessageType(String method) {
        return SUB_MESSAGE_TYPE.get(method);
    }

    public static String getTextFromXml(String source, String segment) {
        String textValue = null;
        int start = source.indexOf(segment + ">");
        if (start > 0) {
            start = start + segment.length() + 1;
            int end = source.indexOf("<", start);
            if (end > 0)
                textValue = source.substring(start, end - 1);
        }
        return textValue;
    }

    public static LocalDateTime getLocalDateTimeFromString(String dateStr, String format){
        String localFormat = format;
        if(format == null || "".equals(format)){
            localFormat = DATE_TIME_FORMAT;
        }
        DateTimeFormatter df = DateTimeFormatter.ofPattern(localFormat);

        return LocalDateTime.parse(dateStr,df);
    }
}
