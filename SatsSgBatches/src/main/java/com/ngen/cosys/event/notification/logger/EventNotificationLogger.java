/**
 * EventNotificationLogger
 * 
 * @copyright SATS Singapore 2019-20
 * @author NIIT
 */
package com.ngen.cosys.event.notification.logger;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;

import com.ngen.cosys.event.notification.core.NotificationStats;
import com.ngen.cosys.event.notification.model.EventNotificationLog;
import com.ngen.cosys.event.notification.model.EventNotificationLogDetails;
import com.ngen.cosys.event.notification.model.EventTypeConfig;
import com.ngen.cosys.event.notification.model.FlightEvents;
import com.ngen.cosys.framework.exception.CustomException;

/**
 * Event Notification Logger
 * 
 * @author NIIT Technologies Ltd
 */
public interface EventNotificationLogger {

   /**
    * Log Event Notification execution stats
    * 
    * @param notificationStats
    * @throws CustomException
    */
   void logEventNotificationExecutionStats(NotificationStats notificationStats) throws CustomException;
   
   /**
    * GET Event Notification Log
    * 
    * @param eventTypeConfig
    * @return
    * @throws CustomException
    */
   EventNotificationLog getEventNotificationLog(EventTypeConfig eventTypeConfig) throws CustomException;
   
   /**
    * UPDATE Event Notification Log
    * 
    * @param notificationLog
    * @throws CustomException
    */
   void updateEventNotificationLog(EventNotificationLog notificationLog) throws CustomException;
   
   /**
    * @param notificationLogId
    * @param notificationSentTime
    * @throws CustomException
    */
   void updateEventNotificationLog(BigInteger notificationLogId, LocalDateTime notificationSentTime)
         throws CustomException;
   
   /**
    * Log Detailed Snapshots
    * 
    * @param notificationStats
    * @throws CustomException
    */
   void logEventNotificationDetailedSnapshots(NotificationStats notificationStats) throws CustomException;
   
   /**
    * @param notificationLog
    * @return
    * @throws CustomException
    */
   List<EventNotificationLogDetails> getEventNotificationLogDetails(EventNotificationLog notificationLog)
         throws CustomException;
   
   /**  
    * @param eventTypeConfig
    * @param completedFlightDetails
    * @throws CustomException
    */
   void updateEventNotificationDetailSnapshots(EventTypeConfig eventTypeConfig,
         List<FlightEvents> completedFlightDetails) throws CustomException;
   
}
