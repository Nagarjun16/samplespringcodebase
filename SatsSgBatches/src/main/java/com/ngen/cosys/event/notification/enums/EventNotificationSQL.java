/**
 * EventNotificationSQL.java
 * 
 * @copyright SATS Singapore 2019-20
 * @author NIIT
 */
package com.ngen.cosys.event.notification.enums;

/**
 * This class is used for Event Notification SQL
 * 
 * @author NIIT Technologies Ltd
 *
 */
public enum EventNotificationSQL {

   SELECT_EVENT_NOTIFICATION_CONFIG("sqlSelectEventNotificationConfig"), //
   SELECT_EVENT_NOTIFICATION_DETAILS("sqlSelectEventNotificationDetails"), //
   SELECT_EVENT_NOTIFICATION_FREQUENCY_JOB_DETAILS("sqlSelectEventNotificationFrequencyJobDetails"), //
   SELECT_EVENT_NOTIFICATION_LAST_EXECUTION_TIME("sqlSelectEventNotificationLastExecutionTime"), //
   SELECT_EVENT_NOTIFICATION_JOB_LOG("sqlSelectEventNotificationJobLog"), //
   INSERT_EVENT_NOTIFICATION_JOB_LOG("sqlInsertEventNotificationJobLog"), //
   UPDATE_EVENT_NOTIFICATION_JOB_LOG("sqlUpdateEventNotificationJobLog"), //
   UPDATE_EVENT_NOTIFICATION_SENT_TIME_JOB_LOG("sqlUpdateEventNotificationSentTimeJobLog"), //
   SELECT_EVENT_NOTIFICATION_JOB_DETAILS_LOG("sqlSelectEventNotificationJobDetailsLog"), //
   INSERT_EVENT_NOTIFICATION_JOB_DETAILS_LOG("sqlInsertEventNotificationJobDetailsLog"), //
   UPDATE_EVENT_NOTIFICATION_JOB_DETAILS_LOG("sqlUpdateEventNotificationJobDetailsLog"), //
   SELECT_INBOUND_NOT_COMPLETED_FLIGHT_DETAILS("sqlSelectInboundNotCompletedFlightDetails"), //
   SELECT_INBOUND_COMPLETED_FLIGHT_DETAILS("sqlSelectInboundCompletedFlightDetails"), //
   SELECT_OUTBOUND_NOT_COMPLETED_FLIGHT_DETAILS("sqlSelectOutboundNotCompletedFlightDetails"), //
   SELECT_OUTBOUND_COMPLETED_FLIGHT_DETAILS("sqlSelectOutboundCompletedFlightDetails");
   
   private String queryId;
   
   /**
    * @param value
    */
   private EventNotificationSQL(String value) {
      this.queryId = value;
   }
   
   /**
    * @return
    */
   public String getQueryId() {
      return this.queryId;
   }
   
}
