/**
 * {@link NotificationEventTypes}
 * 
 * @copyright SATS Singapore 2019-20
 * @author NIIT
 */
package com.ngen.cosys.event.notification.common;

/**
 * Notification Event Types holds of events
 * 
 * @author NIIT Technologies Ltd
 */
public final class NotificationEventTypes {

   private NotificationEventTypes() {
      throw new RuntimeException("Creating instances are not allowed");
   }
   
   public static final String DLS_COMPLETE = "dlsComplete";
   public static final String MANIFEST_COMPLETE = "manifestComplete";
   public static final String RAMP_CHECKIN_COMPLETE = "rampCheckInComplete";
   public static final String BREAKDOWN_COMPLETE = "breakdownComplete";
   public static final String INBOUND_FLIGHT_COMPLETE = "inboundFlightComplete";
   public static final String OUTBOUND_FLIGHT_COMPLETE = "outboundFlightComplete";
   
}
