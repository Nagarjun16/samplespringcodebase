/**
 * SLAType.java
 * 
 * @copyright SATS Singapore 2019-20
 * @author NIIT
 * 
 */
package com.ngen.cosys.event.notification.enums;

/**
 * This enum is used for SLA Types
 * 
 * @author NIIT Technologies Ltd
 */
public enum SLAType {

   AMBER(Key.AMBER), //
   GREEN(Key.GREEN), //
   RED(Key.RED);
   
   public class Key {
      //
      private Key() {}
      //
      public static final String AMBER = "AMBER";
      public static final String GREEN = "GREEN";
      public static final String RED = "RED";
   }
   
   private String value;
   
   /**
    * Initialize
    * 
    * @param slaType
    */
   SLAType(String slaType) {
      this.value = slaType;
   }
   
   /**
    * @return
    */
   public String value() {
      return this.value;
   }
   
   /**
    * Enum of the value
    * 
    * @param value
    * @return
    */
   public static SLAType enumOf(String value) {
      //
      for (SLAType slaType : values())
         if (slaType.value().equalsIgnoreCase(value))
            return slaType;
      //
      return null;
   }
   
}
