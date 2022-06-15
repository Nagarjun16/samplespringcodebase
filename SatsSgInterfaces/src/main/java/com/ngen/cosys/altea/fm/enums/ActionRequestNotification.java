/**
 * ActionRequestNotification.java
 * 
 * Copyright <PRE><IMG SRC = XX></IMG></PRE>
 *
 * Version      Date         Author      Reason
 * 1.0          22 JAN, 2019   NIIT      -
 */
package com.ngen.cosys.altea.fm.enums;

/**
 * This Enum used for Action Request Notification
 * 
 * @author NIIT Technologies Ltd
 *
 */
public enum ActionRequestNotification {

   ZERO("0"), // No
   ONE("1"); // Yes
   
   private String value;
   
   /**
    * @param value
    */
   private ActionRequestNotification(String value) {
      this.value = value;
   }
   
   /**
    * @return
    */
   public String getValue() {
      return this.value;
   }
   
}
