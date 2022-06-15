/**
 * {@link EventNotificationFrequencyJob}
 * 
 * @copyright SATS Singapore 2019-20
 * @author NIIT
 */
package com.ngen.cosys.event.notification.job;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.ngen.cosys.event.notification.config.EventNotificationConfig;
import com.ngen.cosys.event.notification.core.NotificationEngine;
import com.ngen.cosys.event.notification.enums.EntityEventTypes;
import com.ngen.cosys.event.notification.enums.EventEntity;
import com.ngen.cosys.event.notification.logger.EventNotificationLogger;
import com.ngen.cosys.event.notification.model.EventNotificationLog;
import com.ngen.cosys.event.notification.model.EventNotificationLogDetails;
import com.ngen.cosys.event.notification.model.EventNotificationModel;
import com.ngen.cosys.event.notification.model.EventTypeConfig;
import com.ngen.cosys.event.notification.model.FlightEvents;
import com.ngen.cosys.event.notification.processor.EventNotificationProcessor;
import com.ngen.cosys.event.notification.service.FlightDLSCompleteService;
import com.ngen.cosys.event.notification.service.FlightManifestCompleteService;
import com.ngen.cosys.event.notification.service.InboundFlightCompleteService;
import com.ngen.cosys.event.notification.service.OutboundFlightCompleteService;
import com.ngen.cosys.event.notification.service.RampCheckInCompleteService;
import com.ngen.cosys.event.notification.service.ShipmentBreakdownCompleteService;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.scheduler.job.AbstractCronJob;
import com.ngen.cosys.timezone.util.TenantZoneTime;

/**
 * Event Notification Frequency Job is a collective SEND notification If any
 * event type is configured with Notification Frequency of Fixed Time and Repeat
 * Time then frequency job is used to send notification
 * 
 * Notification Job data based on the last event type job ran Stats
 * 
 * Before SEND notification verify the last execution data with current table
 * data, If any event is completed discard it (HasCompleted - TRUE) and trigger
 * the Notification
 * 
 * @author NIIT Technologies Ltd
 */
@Component
public class EventNotificationFrequencyJob extends AbstractCronJob {

   private static final Logger LOGGER = LoggerFactory.getLogger(EventNotificationFrequencyJob.class);
   
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
   
   @Autowired
   RampCheckInCompleteService rampCheckInCompleteService;
   
   @Autowired
   ShipmentBreakdownCompleteService shipmentBreakdownCompleteService;
   
   @Autowired
   FlightDLSCompleteService flightDLSCompleteService;
   
   @Autowired
   FlightManifestCompleteService flightManifestCompleteService;
   
   @Autowired
   OutboundFlightCompleteService outboundFlightCompleteService;
   
   /**
    * @see com.ngen.cosys.scheduler.job.AbstractCronJob#executeInternal(org.quartz.JobExecutionContext)
    */
   @Override
   protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
      LOGGER.info("Frequency Job Internal execution started");
      //
      try {
         // Notification Frequency Job Configuration
         List<EventTypeConfig> eventTypeFrequency = eventNotificationConfig.getNotificationFrequencyJobDetails();
         boolean frequencyJobAvailability = !CollectionUtils.isEmpty(eventTypeFrequency) ? true : false;
         LOGGER.info("Frequency Job Availability - {}", String.valueOf(frequencyJobAvailability));
         if (frequencyJobAvailability) {
            notificationFrequency(eventTypeFrequency);
         }
      } catch (CustomException ex) {
         LOGGER.info("Notification Frequency Job Exception occurred - ", ex);
      }
   }
   
   /**
    * Notification Frequency Testing
    */
   public void notificationFrequencyTesting() {
      LOGGER.info("Frequency Job Internal execution started");
      //
      try {
         // Notification Frequency Job Configuration
         List<EventTypeConfig> eventTypeFrequency = eventNotificationConfig.getNotificationFrequencyJobDetails();
         boolean frequencyJobAvailability = !CollectionUtils.isEmpty(eventTypeFrequency) ? true : false;
         LOGGER.info("Frequency Job Availability - {}", String.valueOf(frequencyJobAvailability));
         if (frequencyJobAvailability) {
            notificationFrequency(eventTypeFrequency);
         }
      } catch (CustomException ex) {
         LOGGER.info("Notification Frequency Job Exception occurred - ", ex);
      }
   }
   
   /**
    * @param eventTypeFrequency
    * @throws CustomException
    */
   private void notificationFrequency(List<EventTypeConfig> eventTypeFrequency) throws CustomException {
      LOGGER.info("Notification Frequency Job configured event type execution started");
      for (EventTypeConfig eventTypeConfig : eventTypeFrequency) {
         LocalDateTime lastExecutionTime = eventNotificationConfig.lastExecutedEventTime(eventTypeConfig);
         LOGGER.info("{} Entity and {} Event Type notification last execution time - {}", eventTypeConfig.getEntity(),
               eventTypeConfig.getEventName(), lastExecutionTime);
         if (Objects.nonNull(lastExecutionTime)) {
            List<FlightEvents> completedFlightDetails = eventCompletedFlightDetails(eventTypeConfig.getEventName(),
                  lastExecutionTime);
            boolean completedFlightsAvailability = !CollectionUtils.isEmpty(completedFlightDetails) ? true : false;
            LOGGER.info("{} Entity and {} Event Type notification completed Flights availability - {}",
                  eventTypeConfig.getEntity(), eventTypeConfig.getEventName(),
                  String.valueOf(completedFlightsAvailability));
            // If Completed Flight Details NOT Null - Update it in the table
            if (completedFlightsAvailability) {
               eventNotificationLogger.updateEventNotificationDetailSnapshots(eventTypeConfig, completedFlightDetails);
            }
         }
         initiateNotificationProcess(eventTypeConfig, lastExecutionTime);
      }
   }
   
   /**
    * Initiate Notification Process
    * 
    * @param eventTypeConfig
    * @param lastExecutionTime
    * @throws CustomException 
    */
   private void initiateNotificationProcess(EventTypeConfig eventTypeConfig, LocalDateTime lastExecutionTime)
         throws CustomException {
      LOGGER.info("Notification Frequency Job - Initiate Notification Process");
      EventNotificationModel eventNotificationModel = eventNotificationConfig.getDetails(
            EventEntity.enumOf(eventTypeConfig.getEntity()), EntityEventTypes.enumOf(eventTypeConfig.getEventName()));
      EventNotificationLog notificationLog = eventNotificationLogger.getEventNotificationLog(eventTypeConfig);
      List<EventNotificationLogDetails> notificationLogDetails = eventNotificationLogger
            .getEventNotificationLogDetails(notificationLog);
      boolean notificationLogDetailsAvailability = !CollectionUtils.isEmpty(notificationLogDetails) ? true : false;
      LOGGER.info("Notification Log Details Availability - {}", String.valueOf(notificationLogDetailsAvailability));
      if (notificationLogDetailsAvailability) {
         boolean notificationSent = eventNotificationProcessor.sendNotification(eventNotificationModel,
               notificationLogDetails, lastExecutionTime);
         LOGGER.info("Notification Frequency Job - Notification SENT Flag - {}", String.valueOf(notificationSent));
         if (notificationSent) {
            // Update Notification Log 
            notificationLog.setNotificationSentTime(TenantZoneTime.getUTCDateTime());
            Integer notificationSentCount = notificationLog.getNotificationSentCount();
            notificationSentCount = Objects.nonNull(notificationSentCount) ? notificationSentCount + 1 : 1;
            notificationLog.setNotificationSentCount(notificationSentCount);
            //
            eventNotificationLogger.updateEventNotificationLog(notificationLog);
         }
      }
   }
   
   /**
    * @param eventType
    * @param lastExecutionTime
    * @return
    * @throws CustomException
    */
   private List<FlightEvents> eventCompletedFlightDetails(String eventType, LocalDateTime lastExecutionTime)
         throws CustomException {
      List<FlightEvents> completedFlightDetails = Collections.emptyList();
      //
      switch (eventType) {
      case EntityEventTypes.Key.AWB_INBOUND_BREAKDOWN_COMPLETE:
         completedFlightDetails = shipmentBreakdownCompleteService
               .getBreakdownCompletedFlightsAfterTheLastExecutionTime(EntityEventTypes.AWB_INBOUND_BREAKDOWN_COMPLETE,
                     lastExecutionTime);
         break;
      case EntityEventTypes.Key.FLIGHT_INBOUND_RAMP_CHECK_IN:
         completedFlightDetails = rampCheckInCompleteService.getRampCheckInCompletedFlightsAfterTheLastExecutionTime(
               EntityEventTypes.FLIGHT_INBOUND_RAMP_CHECK_IN, lastExecutionTime);
         break;
      case EntityEventTypes.Key.FLIGHT_INBOUND_COMPLETE:
         completedFlightDetails = inboundFlightCompleteService.getInboundCompletedFlightsAfterTheLastExecutionTime(
               EntityEventTypes.FLIGHT_INBOUND_COMPLETE, lastExecutionTime);
         break;
      case EntityEventTypes.Key.FLIGHT_OUTBOUND_DLS_COMPLETE:
         completedFlightDetails = flightDLSCompleteService.getDLSCompletedFlightsAfterTheLastExecutionTime(
               EntityEventTypes.FLIGHT_OUTBOUND_DLS_COMPLETE, lastExecutionTime);
         break;
      case EntityEventTypes.Key.FLIGHT_OUTBOUND_MANIFEST_COMPLETE:
         completedFlightDetails = flightManifestCompleteService.getManifestCompletedFlightsAfterTheLastExecutionTime(
               EntityEventTypes.FLIGHT_OUTBOUND_MANIFEST_COMPLETE, lastExecutionTime);
         break;
      case EntityEventTypes.Key.FLIGHT_OUTBOUND_COMPLETE:
         completedFlightDetails = outboundFlightCompleteService.getOutboundCompletedFlightsAfterTheLastExecutionTime(
               EntityEventTypes.FLIGHT_OUTBOUND_COMPLETE, lastExecutionTime);
         break;
      default: break;
      }
      //
      return completedFlightDetails;
   }
   
}
