/**
 * {@link MessageResendConstants}
 * 
 * @copyright SATS Singapore 2019-20
 * @author NIIT
 */
package com.ngen.cosys.message.resend.common;

/**
 * Message Resends Constants
 * 
 * @author NIIT Technologies Ltd
 */
public final class MessageResendConstants {

   private MessageResendConstants() {
      throw new RuntimeException("Creating instances are not allowed");
   }
   // Types
   public static final String INCOMING_MESSAGE_RESEND = "incomingMessageResend";
   public static final String INCOMING_CMD_MESSAGE_RESEND = "incomingCMDMessageResend";
   public static final String INCOMING_FFM_MESSAGE_RESEND = "incomingFFMMessageResend";
   public static final String INCOMING_FFR_MESSAGE_RESEND = "incomingFFRMessageResend";
   public static final String INCOMING_FWB_MESSAGE_RESEND = "incomingFWBMessageResend";
   public static final String INCOMING_FHL_MESSAGE_RESEND = "incomingFHLMessageResend";
   public static final String INCOMING_ASM_SSM_FFR_MESSAGE_RESEND = "incomingASMSSMFFRMessageResend";
   public static final String INCOMING_ASM_SSM_FFM_FFR_MESSAGE_RESEND = "incomingASMSSMFFMFFRMessageResend";
   public static final String OUTGOING_MESSAGE_RESEND = "outgoingMessageResend";
   public static final String INCOMING_MESSAGE_DISCARD = "incomingMessageDiscard";
   public static final String OUTGOING_MESSAGE_RESEND_AISATS = "aisatsOutgoingMessageResendJob";
   // Alerts
   public static final String INFO_ALERT = "INFO";
   public static final String WARNING_ALERT = "WARNING";
   public static final String CRITICAL_ALERT = "CRITICAL";
   // Status
   public static final String HOLD = "HOLD";
   public static final String INITIATED = "INITIATED";
   public static final String PROCESSSING = "PROCESSING";
   public static final String PROCESSED = "PROCESSED";
   public static final String REJECTED = "REJECTED";
   public static final String RESENT = "RESENT";
   public static final String FAILED = "FAILED";
   public static final String SPLIT = "SPLIT";
   public static final String SENT = "SENT";
   public static final String DIVERTED = "DIVERTED";
   public static final String REROUTE = "REROUTE";
  
   // Flag CRUD
   public static final String ADD = "ADD";
   public static final String UPD = "UPD";
   //
   public static final String FFM = "FFM";
   public static final String LAST = "LAST";
   public static final String SCHEDULER = "SCHEDULER";
   //
   public static final String MESSAGE_RESEND_RETRY_LIMIT = "MESSAGE_RESEND_RETRY_LIMIT";
   // API Config
   public static final String API_MQ_CONFIG_KEY = "API_MQ_CONFIG_KEY";
   public static final String API_MQ_CONFIG_VALUE = "API_ESB_CONNECTOR_MQ";
   public static final String API_HTTP_CONFIG_KEY = "API_HTTP_CONFIG_KEY";
   public static final String API_HTTP_CONFIG_VALUE = "API_ESB_CONNECTOR_REST";
   
}
