/**
 * EventNotificationParameter.java
 * 
 * @copyright SATS Singapore 2019-20
 * @author NIIT
 */
package com.ngen.cosys.event.notification.enums;

/**
 * This enum is used for Event Notification Parameters
 * 
 * @author NIIT Technologies Ltd
 *
 */
public enum EventNotificationParameter {

   SLA_TYPE(Key.SLA_TYPE), //
   AIRCRAFT_TYPE(Key.AIRCRAFT_TYPE), //
   FLIGHT_TYPE(Key.FLIGHT_TYPE), //
   SPECIAL_HANDLING_CODE(Key.SPECIAL_HANDLING_CODE), //
   FLIGHT_TIME(Key.FLIGHT_TIME), //
   EQUATION(Key.EQUATION), //
   MINUTES(Key.MINUTES), //
   DURATION(Key.DURATION), //
   COUNT(Key.COUNT), //
   NOTIFICATION_TYPE(Key.NOTIFICATION_TYPE), //
   USER_CONFIGURATION(Key.USER_CONFIGURATION), //
   USER_CONFIG_GROUP(Key.USER_CONFIG_GROUP), //
   USER_CONFIG_LOGIN_CODE(Key.USER_CONFIG_LOGIN_CODE), //
   FLIGHT_INFORMATION(Key.FLIGHT_INFORMATION), //
   FLIGHT_CARRIER_CODE(Key.FLIGHT_CARRIER_CODE), //
   FLIGHT_KEY(Key.FLIGHT_KEY), //
   FLIGHT_DATETIME(Key.FLIGHT_DATETIME), //
   FREQUENCY(Key.FREQUENCY), //
   FIXED_TIME(Key.FIXED_TIME), //
   REPEAT_TIME(Key.REPEAT_TIME), //
   TEMPLATE_TYPE(Key.TEMPLATE_TYPE), //
   DLS_PRECISION_TIME(Key.DLS_PRECISION_TIME), //
   FLIGHT_PRECISION_TIME(Key.FLIGHT_PRECISION_TIME);

   public class Key {
      //
      private Key() {
      }

      //
      public static final String SLA_TYPE = "SLA_TYPE";
      public static final String AIRCRAFT_TYPE = "AIRCRAFT_TYPE";
      public static final String FLIGHT_TYPE = "FLIGHT_TYPE";
      public static final String SPECIAL_HANDLING_CODE = "SPECIAL_HANDLING_CODE";
      public static final String FLIGHT_TIME = "FLIGHT_TIME";
      public static final String EQUATION = "EQUATION";
      public static final String MINUTES = "MINUTES";
      public static final String DURATION = "DURATION";
      public static final String COUNT = "COUNT";
      public static final String NOTIFICATION_TYPE = "NOTIFICATION_TYPE";
      public static final String USER_CONFIGURATION = "USER_CONFIGURATION";
      public static final String USER_CONFIG_GROUP = "USER_CONFIG_GROUP";
      public static final String USER_CONFIG_LOGIN_CODE = "USER_CONFIG_LOGIN_CODE";
      public static final String FLIGHT_INFORMATION = "FLIGHT_INFORMATION";
      public static final String FLIGHT_CARRIER_CODE = "FLIGHT_CARRIER_CODE";
      public static final String FLIGHT_KEY = "FLIGHT_KEY";
      public static final String FLIGHT_DATETIME = "FLIGHT_DATETIME";
      public static final String FREQUENCY = "FREQUENCY";
      public static final String FIXED_TIME = "FIXED_TIME";
      public static final String REPEAT_TIME = "REPEAT_TIME";
      public static final String TEMPLATE_TYPE = "TEMPLATE_TYPE";
      public static final String DLS_PRECISION_TIME = "DLS_PRECISION_TIME";
      public static final String FLIGHT_PRECISION_TIME = "FLIGHT_PRECISION_TIME";

   }

   String value;

   EventNotificationParameter(String value) {
      this.value = value;
   }

   public String getValue() {
      return this.value;
   }

}
