/**
 * FlightTime.java
 * 
 * @copyright SATS Singapore 2019-20
 * @author NIIT
 * 
 */
package com.ngen.cosys.event.notification.enums;

/**
 * Flight Time Constant Enum
 * 
 * @author NIIT Technologies Ltd
 */
public enum FlightTime {

   ATA(Key.ATA), // Actual Time Arrival
   ATD(Key.ATD), // Actual Time Departure
   ETA(Key.ETA), // Estimated Time Arrival
   ETD(Key.ETD), // Estimated Time Departure
   STA(Key.STA), // Standard Time Arrival
   STD(Key.STD); // Standard Time Departure
   
   public class Key {
      //
      private Key() {}
      //
      public static final String ATA = "ATA";
      public static final String ATD = "ATD";
      public static final String ETA = "ETA";
      public static final String ETD = "ETD";
      public static final String STA = "STA";
      public static final String STD = "STD";
      
   }
   
   private String value;
   
   FlightTime(String time) {
      this.value = time;
   }
   
   public String getValue() {
      return this.value;
   }
   
}
