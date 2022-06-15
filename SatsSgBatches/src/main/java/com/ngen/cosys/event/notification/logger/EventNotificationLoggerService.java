/**
 * EventNotificationLoggerService
 * 
 * @copyright SATS Singapore 2019-20
 * @author NIIT
 */
package com.ngen.cosys.event.notification.logger;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.ngen.cosys.event.notification.core.EntityConstraint;
import com.ngen.cosys.event.notification.core.NotificationConstraint;
import com.ngen.cosys.event.notification.core.NotificationStats;
import com.ngen.cosys.event.notification.model.EventNotificationLog;
import com.ngen.cosys.event.notification.model.EventNotificationLogDetails;
import com.ngen.cosys.event.notification.model.EventTypeConfig;
import com.ngen.cosys.event.notification.model.FlightEvents;
import com.ngen.cosys.event.notification.util.EventNotificationUtils;
import com.ngen.cosys.framework.exception.CustomException;

/**
 * Event Notification Logger Service
 * 
 * @author NIIT Technologies Ltd
 */
@Service
public class EventNotificationLoggerService implements EventNotificationLogger {

   @Autowired
   EventNotificationLoggerDAO eventNotificationLoggerDAO;
   
   private static final Logger LOGGER = LoggerFactory.getLogger(EventNotificationLogger.class);
   
   /**
    * @see com.ngen.cosys.event.notification.logger.EventNotificationLogger#logEventNotificationExecutionStats(com.ngen.cosys.event.notification.core.NotificationStats)
    * 
    */
   @Override
   public void logEventNotificationExecutionStats(NotificationStats notificationStats) throws CustomException {
      LOGGER.debug("Event Notification Logger Stats of job run");
      EventNotificationLog notificationLog = getNotificationLog(notificationStats);
      eventNotificationLoggerDAO.insertEventNotificationJobLog(notificationLog);
      // Update Event Notification Log Id in Notification Stats
      if (Objects.nonNull(notificationLog.getEventNotificationLogId())) {
         LOGGER.debug("Event Notification Log Id - {}", notificationLog.getEventNotificationLogId());
         notificationStats.setEventNotificationLogId(notificationLog.getEventNotificationLogId());
      }
   }
   
   /**
    * @see com.ngen.cosys.event.notification.logger.EventNotificationLogger#getEventNotificationLog(com.ngen.cosys.event.notification.model.EventTypeConfig)
    */
   @Override
   public EventNotificationLog getEventNotificationLog(EventTypeConfig eventTypeConfig) throws CustomException {
      LOGGER.debug("Event Notification Logger - SELECT Notification log stats");
      return eventNotificationLoggerDAO.selectEventNotificationJobLog(eventTypeConfig);
   }

   /**
    * @see com.ngen.cosys.event.notification.logger.EventNotificationLogger#updateEventNotificationLog(com.ngen.cosys.event.notification.model.EventNotificationLog)
    */
   @Override
   public void updateEventNotificationLog(EventNotificationLog notificationLog) throws CustomException {
      LOGGER.debug("Event Notification Logger - UPDATE Notification log stats");
      eventNotificationLoggerDAO.updateEventNotificationJobLog(notificationLog);
   }
   
   /**
    * @see com.ngen.cosys.event.notification.logger.EventNotificationLogger#updateEventNotificationLog(java.math.BigInteger,
    *      java.time.LocalDateTime)
    */
   @Override
   public void updateEventNotificationLog(BigInteger notificationLogId, LocalDateTime notificationSentTime)
         throws CustomException {
      LOGGER.debug("Event Notification Logger - UPDATE Notification log Notification SENT Time");
      eventNotificationLoggerDAO
            .updateEventNotificationSENTTimeJobLog(getNotificationLog(notificationLogId, notificationSentTime));
   }
   
   /**
    * @see com.ngen.cosys.event.notification.logger.EventNotificationLogger#logEventNotificationDetailedSnapshots(com.ngen.cosys.event.notification.core.NotificationStats)
    * 
    */
   @Override
   public void logEventNotificationDetailedSnapshots(NotificationStats notificationStats) throws CustomException {
      LOGGER.debug("EventNotificationLogger - Detailed Snapshots insert");
      if (!CollectionUtils.isEmpty(notificationStats.getNotificationConstraints())) {
         List<EventNotificationLogDetails> notificationLogDetails = getNotificationLogDetails(notificationStats);
         eventNotificationLoggerDAO.insertEventNotificationJobDetailsLog(notificationLogDetails);
      }
   }

   /**
    * @see com.ngen.cosys.event.notification.logger.EventNotificationLogger#getEventNotificationLogDetails(com.ngen.cosys.event.notification.model.EventNotificationLog)
    */
   @Override
   public List<EventNotificationLogDetails> getEventNotificationLogDetails(EventNotificationLog notificationLog)
         throws CustomException {
      LOGGER.debug("EventNotificationLogger - Detailed Snapshots select");
      return eventNotificationLoggerDAO.selectEventNotificationJobDetailsLog(notificationLog);
   }
   
   /**
    * @see com.ngen.cosys.event.notification.logger.EventNotificationLogger#updateEventNotificationDetailSnapshots(com.ngen.cosys.event.notification.model.EventTypeConfig,
    *      java.util.List)
    */
   @Override
   public void updateEventNotificationDetailSnapshots(EventTypeConfig eventTypeConfig,
         List<FlightEvents> completedFlightDetails) throws CustomException {
      LOGGER.debug(
            "Event Notification Logger Service - updateEventNotificationDetailSnapshots for {} Entity and {} Event Type",
            eventTypeConfig.getEntity(), eventTypeConfig.getEventName());
      //
      EventNotificationLog notificationLog = eventNotificationLoggerDAO.selectEventNotificationJobLog(eventTypeConfig);
      if (Objects.nonNull(notificationLog)) {
         List<EventNotificationLogDetails> eventNotificationLogDetails = new ArrayList<>();
         for (FlightEvents flight : completedFlightDetails) {
            eventNotificationLogDetails.add(getNotificationLogDetails(notificationLog, flight));
         }
         eventNotificationLoggerDAO.updateEventNotificationJobDetailsLog(eventNotificationLogDetails);
         // Update Notification Log
         Integer completedCount = notificationLog.getCompletedCount();
         completedCount = Objects.nonNull(completedCount) ? completedCount + 1 : eventNotificationLogDetails.size();
         notificationLog.setCompletedCount(completedCount);
         eventNotificationLoggerDAO.updateEventNotificationJobLog(notificationLog);
      }
   }
   
   /**
    * @param notificationStats
    * @return
    */
   private EventNotificationLog getNotificationLog(NotificationStats notificationStats) {
      EventNotificationLog notificationLog = new EventNotificationLog();
      //
      notificationLog.setEntity(notificationStats.getEntity());
      notificationLog.setEventName(notificationStats.getEventName());
      notificationLog.setTotalRecords(notificationStats.getTotalRecords());
      notificationLog.setExecutionCount(notificationStats.getExecutionCount());
      notificationLog.setNewlyAdded(notificationStats.getNewlyAdded());
      notificationLog.setUpdatedCount(notificationStats.getUpdatedCount());
      notificationLog.setCompletedCount(notificationStats.getCompletedCount());
      notificationLog.setStartTime(notificationStats.getStartTime());
      notificationLog.setEndTime(notificationStats.getEndTime());
      //
      return notificationLog;
   }
   
   /**
    * @param notificationLogId
    * @param notificationSentTime
    * @return
    */
   private EventNotificationLog getNotificationLog(BigInteger notificationLogId, LocalDateTime notificationSentTime) {
      EventNotificationLog notificationLog = new EventNotificationLog();
      //
      notificationLog.setEventNotificationLogId(notificationLogId);
      notificationLog.setNotificationSentTime(notificationSentTime);
      //
      return notificationLog;
   }
   
   /**
    * @param notificationStats
    * @return
    */
   private List<EventNotificationLogDetails> getNotificationLogDetails(NotificationStats notificationStats) {
      List<EventNotificationLogDetails> notificationLogDetails = Collections.emptyList();
      //
      if (!CollectionUtils.isEmpty(notificationStats.getNotificationConstraints())) {
         // Initialize
         notificationLogDetails = new ArrayList<>();
         BigInteger notificationLogId = notificationStats.getEventNotificationLogId();
         //
         for (Iterator<Entry<EntityConstraint, List<NotificationConstraint>>> iterator = notificationStats
               .getNotificationConstraints().entrySet().iterator(); iterator.hasNext();) {
            Entry<EntityConstraint, List<NotificationConstraint>> entry = iterator.next();
            if (Objects.nonNull(entry.getKey()) && EventNotificationUtils.allMatchConstraint(entry.getValue())) {
               NotificationConstraint constraint = EventNotificationUtils.getAllMatchConstraint(entry.getValue());
               if (Objects.nonNull(constraint)) {
                  notificationLogDetails.add(copyNotificationLogDetail(entry.getKey(), constraint, notificationLogId));
               }
            }
         }
         LOGGER.debug("Notification Log Details Snapshot size - ", notificationLogDetails.size());
      }
      //
      return notificationLogDetails;
   }
   
   /**
    * @param entity
    * @param constraint
    * @param notificationLogId
    * @return
    */
   private EventNotificationLogDetails copyNotificationLogDetail(EntityConstraint entity,
         NotificationConstraint constraint, BigInteger notificationLogId) {
      EventNotificationLogDetails notificationLogDetails = new EventNotificationLogDetails();
      //
      notificationLogDetails.setEventNotificationLogId(notificationLogId);
      notificationLogDetails.setEventNotificationId(constraint.getEventNotificationId());
      notificationLogDetails.setFlightId(entity.getFlightId());
      notificationLogDetails.setFlightKey(entity.getFlightKey());
      notificationLogDetails.setFlightDateTime(entity.getFlightOriginDate());
      notificationLogDetails.setHasCompleted(false);
      //
      return notificationLogDetails;
   }
   
   /**
    * Notification log details
    * 
    * @param notificationLog
    * @param flight
    * @return
    */
   private EventNotificationLogDetails getNotificationLogDetails(EventNotificationLog notificationLog,
         FlightEvents flight) {
      EventNotificationLogDetails notificationLogDetails = new EventNotificationLogDetails();
      //
      notificationLogDetails.setEventNotificationLogId(notificationLog.getEventNotificationLogId());
      notificationLogDetails.setFlightId(flight.getFlightId());
      notificationLogDetails.setFlightKey(flight.getFlightKey());
      notificationLogDetails.setFlightDateTime(flight.getFlightOriginDate());
      notificationLogDetails.setHasCompleted(true);
      //
      return notificationLogDetails;
   }

}
