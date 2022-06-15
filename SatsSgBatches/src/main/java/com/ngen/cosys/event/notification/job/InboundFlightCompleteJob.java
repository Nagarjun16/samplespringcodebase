/**
 * {@link InboundFlightCompleteJob}
 * 
 * @copyright SATS Singapore 2019-20
 * @author NIIT
 */
package com.ngen.cosys.event.notification.job;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.ngen.cosys.event.notification.common.NotificationEventTypes;
import com.ngen.cosys.event.notification.config.EventNotificationConfig;
import com.ngen.cosys.event.notification.core.NotificationEngine;
import com.ngen.cosys.event.notification.core.NotificationStats;
import com.ngen.cosys.event.notification.logger.EventNotificationLogger;
import com.ngen.cosys.event.notification.model.EventNotificationModel;
import com.ngen.cosys.event.notification.model.FlightEvents;
import com.ngen.cosys.event.notification.processor.EventNotificationProcessor;
import com.ngen.cosys.event.notification.service.InboundFlightCompleteService;
import com.ngen.cosys.event.notification.type.InboundFlightComplete;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.scheduler.job.AbstractCronJob;
import com.ngen.cosys.timezone.util.TenantZoneTime;

/**
 * This implementation class is used for Inbound Flight Complete
 * 
 * @author NIIT Technologies Ltd
 */
@Component(value = NotificationEventTypes.INBOUND_FLIGHT_COMPLETE)
public class InboundFlightCompleteJob extends AbstractCronJob implements InboundFlightComplete {

   private static final Logger LOGGER = LoggerFactory.getLogger(InboundFlightCompleteJob.class);
   
   @Autowired
   EventNotificationConfig eventNotificationConfig;
   
   @Autowired
   EventNotificationLogger eventNotificationLogger;
   
   @Autowired
   EventNotificationProcessor eventNotificationProcessor;
   
   @Autowired
   NotificationEngine notificationEngine;
   
   @Autowired
   InboundFlightCompleteService inboundFlightCompleteService;
   
   /**
    * @see com.ngen.cosys.scheduler.job.AbstractCronJob#executeInternal(org.quartz.JobExecutionContext)
    */
   @Override
   protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
      LOGGER.debug("Inbound Flight complete job initialized {}, Initiating the required configuration details");
      boolean eventConfigured = eventNotificationConfig.eventConfigured(entity, eventType);
      LOGGER.debug("{} Entity and {} Event Type is configured - {}", String.valueOf(entity), String.valueOf(eventType),
            String.valueOf(eventConfigured));
      //
      if (eventConfigured) {
         try {
            EventNotificationModel eventNotificationModel = eventNotificationConfig.getDetails(entity, eventType);
            boolean eventNotificationAvailability = Objects.nonNull(eventNotificationModel) //
                  ? (!CollectionUtils.isEmpty(eventNotificationModel.getEventNotification()) ? true : false) //
                  : false;
            LOGGER.debug("{} Entity and {} Event Type notification configuration available - {}",
                  String.valueOf(entity), String.valueOf(eventType), String.valueOf(eventNotificationAvailability));
            if (eventNotificationAvailability) {
               flightCompleteEvent(eventNotificationModel);
            }
         } catch (CustomException ex) {
            LOGGER.debug("Inbound Flight complete job exception occurred - {}", ex);
         }
      }
   }

   /**
    * @see com.ngen.cosys.event.notification.type.InboundFlightComplete#flightCompleteEvent(com.ngen.cosys.event.notification.model.EventNotificationModel)
    */
   @Override
   public void flightCompleteEvent(EventNotificationModel eventNotificationModel) throws CustomException {
      LOGGER.debug("{} Entity and {} Event Type notification started", entity, eventType);
      // Not completed flight details
      List<FlightEvents> flightDetails = inboundFlightCompleteService.getInboundNotCompletedFlights(eventType);
      // Notification Engine - inference analysis on the data
      NotificationStats notificationStats = notificationEngine.inferenceAnalysis(eventNotificationModel, flightDetails);
      // Event Logging
      logEventNotification(notificationStats);
      // Send Mail Notification at the first time - Subsequent notification will be
      // taken care by Frequency (Fixed & Repeat)
      sendEventNotification(eventNotificationModel, notificationStats);
   }
   
   /**
    * @see com.ngen.cosys.event.notification.type.EventNotificationConfiguration#logEventNotification(com.ngen.cosys.event.notification.core.NotificationStats)
    */
   @Override
   public void logEventNotification(NotificationStats notificationStats) throws CustomException {
      LOGGER.debug("{} Entity and {} Event Type notification Logging", entity, eventType);
      // Log Event Notification Stats
      eventNotificationLogger.logEventNotificationExecutionStats(notificationStats);
      // Log Event Notification Detailed Snapshots
      eventNotificationLogger.logEventNotificationDetailedSnapshots(notificationStats);
   }

   /**
    * @see com.ngen.cosys.event.notification.type.EventNotificationConfiguration#sendEventNotification(com.ngen.cosys.event.notification.model.EventNotificationModel,
    *      com.ngen.cosys.event.notification.core.NotificationStats)
    */
   @Override
   public void sendEventNotification(EventNotificationModel eventNotificationModel, NotificationStats notificationStats)
         throws CustomException {
      LOGGER.debug("{} Entity and {} Event Type notification Processor", entity, eventType);
      // Notification SENT Time - Before Start
      LocalDateTime notificationSentTime = TenantZoneTime.getUTCDateTime();
      eventNotificationProcessor.sendNotification(eventNotificationModel, notificationStats);
      // Update Event Sent Time in Notification Log
      eventNotificationLogger.updateEventNotificationLog(notificationStats.getEventNotificationLogId(),
            notificationSentTime);
   }

}
