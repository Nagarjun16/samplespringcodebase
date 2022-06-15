/**
 * NotificationType.java
 * 
 * @copyright SATS Singapore 2019-20
 * @author NIIT
 * 
 */
package com.ngen.cosys.event.notification.enums;

/**
 * Notification Type
 * 
 * @author NIIT Technologies Ltd
 */
public enum NotificationType {

   CHAT(Key.CHAT), //
   EMAIL(Key.EMAIL), //
   FAX(Key.FAX), //
   MESSAGE(Key.MESSAGE);
   
   public class Key {
      //
      private Key() {}
      //
      public static final String CHAT = "CHAT";
      public static final String EMAIL = "E-MAIL";
      public static final String FAX = "FAX";
      public static final String MESSAGE = "MESSAGE";
            
   }
   
   private String medium;
   
   /**
    * @param value
    */
   NotificationType(String value) {
      this.medium = value;
   }

   /**
    * @return
    */
   public String medium() {
      return this.medium;
   }
   
   /**
    * @param value
    * @return
    */
   public static NotificationType enumOf(String value) {
      //
      for (NotificationType notification : values())
         if (notification.medium().equalsIgnoreCase(value))
            return notification;
      //
      return null;
   }
   
}
