/**
 * {@link CiQReportUtils}
 * 
 * @copyright SATS Singapore 2019-20
 * @author NIIT
 */
package com.ngen.cosys.ciq.report.util;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * CiQ Report Utils to generate CiQ Report
 * 
 * @author NIIT Technologies Ltd
 */
public class CiQReportUtils {

   private static final Logger LOGGER = LoggerFactory.getLogger(CiQReportUtils.class);

   /**
    * With IN Duration Limit
    * 
    * @param currentZoneTime
    * @param configuredTime
    * @return
    */
   public static boolean withInDurationLimit(LocalDateTime currentZoneTime, LocalTime configuredTime) {
      LOGGER.info("CiQ Report Utils - WithInDuration Limit {}");
      LocalTime zoneTime = currentZoneTime.toLocalTime();

      boolean withInTimeRange = zoneTime.isAfter(configuredTime) || zoneTime.equals(configuredTime);

      LOGGER.info("CiQ Report Utils :: withInDurationLimit # Current Zone Time - {}, Configured Time - {}", zoneTime,
            configuredTime);
      return withInTimeRange;
   }

   /**
    * Start of The Day
    * 
    * @param currentZoneTime
    * @return
    */
   public static boolean startOfTheDay(LocalDateTime currentZoneTime) {
      LOGGER.info("CiQ Report Utils - Start Of the Day {}");
      Duration duration = Duration.between(currentZoneTime.toLocalTime(), LocalTime.MIDNIGHT);
      return duration.toMinutes() == 0;
   }

   /**
    * Day of the Week
    * 
    * @param currentZoneTime
    * @param weekOfTheDay
    * @return
    */
   public static boolean dayOfTheWeek(LocalDateTime currentZoneTime, String weekOfTheDay) {
      LOGGER.info("CiQ Report Utils - Day Of the Week {}");
      DayOfWeek dayOfWeek = currentZoneTime.toLocalDate().getDayOfWeek();
      DayOfWeek configuredDayOfWeek = null;
      try {
         configuredDayOfWeek = DayOfWeek.valueOf(weekOfTheDay.toUpperCase());
      } catch (IllegalArgumentException ex) {
         LOGGER.error("CiQ Report Utils - Day of the Week Exception occurred - {}", ex);
         return false;
      }
      return dayOfWeek.equals(configuredDayOfWeek);
   }

   /**
    * Day of the Month
    * 
    * @param currentZoneTime
    * @param dayInMonth
    * @return
    */
   public static boolean dayOfTheMonth(LocalDateTime currentZoneTime, Integer dayInMonth) {
      LOGGER.info("CiQ Report Utils - Day Of the Month {}");
      return currentZoneTime.getDayOfMonth() == dayInMonth.intValue();
   }

}
