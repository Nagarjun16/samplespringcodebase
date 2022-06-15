/**
 * {@link EventNotificationLoggerDAO}
 * 
 * @copyright SATS Singapore 2019-20
 * @author NIIT
 */
package com.ngen.cosys.event.notification.logger;

import java.util.List;

import com.ngen.cosys.event.notification.model.EventNotificationLog;
import com.ngen.cosys.event.notification.model.EventNotificationLogDetails;
import com.ngen.cosys.event.notification.model.EventTypeConfig;
import com.ngen.cosys.framework.exception.CustomException;

/**
 * EventNotificationLogger DAO Interface
 * 
 * @author NIIT Technologies Ltd
 */
public interface EventNotificationLoggerDAO {

   /**
    * GET Event Notification Job Log
    * 
    * @param eventTypeConfig
    * @return
    * @throws CustomException
    */
   EventNotificationLog selectEventNotificationJobLog(EventTypeConfig eventTypeConfig) throws CustomException;
   
   /**
    * INSERT Event Notification Job Log
    * 
    * @param eventNotificationLog
    * @throws CustomException
    */
   void insertEventNotificationJobLog(EventNotificationLog eventNotificationLog) throws CustomException;
   
   /**
    * UPDATE Event Notification Job Log
    * 
    * @param eventNotificationLog
    * @throws CustomException
    */
   void updateEventNotificationJobLog(EventNotificationLog eventNotificationLog) throws CustomException;
   
   /**
    * UPDATE Event Notifcation SENT Time Job Log
    * 
    * @param eventNotificationLog
    * @throws CustomException
    */
   void updateEventNotificationSENTTimeJobLog(EventNotificationLog eventNotificationLog) throws CustomException;
   
   /**
    * GET Event Notification Job Details Log
    * 
    * @param eventNotificationLog
    * @return
    * @throws CustomException
    */
   List<EventNotificationLogDetails> selectEventNotificationJobDetailsLog(EventNotificationLog eventNotificationLog)
         throws CustomException;
   
   /**
    * INSERT Event Notification Job Details Log
    * 
    * @param eventNotificationLogDetails
    * @throws CustomException
    */
   void insertEventNotificationJobDetailsLog(List<EventNotificationLogDetails> eventNotificationLogDetails)
         throws CustomException;
   
   /**
    * UPDATE Event Notification Job Details Log
    * 
    * @param eventNotificationLogDetails
    * @throws CustomException
    */
   void updateEventNotificationJobDetailsLog(List<EventNotificationLogDetails> eventNotificationLogDetails)
         throws CustomException;
   
}
