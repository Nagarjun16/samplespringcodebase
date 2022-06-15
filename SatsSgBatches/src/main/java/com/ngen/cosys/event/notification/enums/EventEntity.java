/**
 * EventEntity.java
 * 
 * @copyright SATS Singapore 2019-20
 * @author NIIT
 */
package com.ngen.cosys.event.notification.enums;

/**
 * This enum is used for Event Entity
 * 
 * @author NIIT Technologies Ltd
 *
 */
public enum EventEntity {

   AWB(Key.AWB), //
   FLIGHT(Key.FLIGHT), //
   ULD(Key.ULD);

   public class Key {
      //
      private Key() {}
      //
      public static final String AWB = "AWB";
      public static final String FLIGHT = "FLIGHT";
      public static final String ULD = "ULD";
   }
   
   private String value;
   
   /**
    * Initialize
    * 
    * @param entity
    */
   EventEntity(String entity) {
      this.value = entity;
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
   public static EventEntity enumOf(String value) {
      //
      for (EventEntity entity : values())
         if (entity.value().equalsIgnoreCase(value))
            return entity;
      //
      return null;
   }
   
}
