/**
 * {@link MessageMediumConstants}
 * 
 * @copyright SATS Singapore 2019-20
 * @author NIIT
 */
package com.ngen.cosys.message.resend.common;

/**
 * Message Medium Constants
 * 
 * @author NIIT Technologies Ltd
 */
public class MessageMediumConstants {

   private MessageMediumConstants() {
      throw new RuntimeException("Creating instances are not allowed");
   }
   // Medium
   public static final String MQ = "MQ";
   public static final String HTTP = "HTTP";
   //
   public static final String TENANT_ID = "TENANT-ID";
   public static final String MESSAGE_ID = "MESSAGE_ID";
   public static final String ESB_MESSAGE_ID = "ESB_MESSAGE_ID";
   public static final String LOGGER_ENABLED = "LOGGER_ENABLED";
   public static final String SYSTEM_NAME = "SYSTEM_NAME";
   public static final String INTERFACE_SYSTEM = "INTERFACE_SYSTEM";
   
}
