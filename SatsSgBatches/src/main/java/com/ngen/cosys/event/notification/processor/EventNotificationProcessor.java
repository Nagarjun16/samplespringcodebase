/**
 * EventNotificationProcessor.java
 * 
 * @copyright SATS Singapore 2019-20
 * @author NIIT
 */
package com.ngen.cosys.event.notification.processor;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.ngen.cosys.event.notification.core.EntityConstraint;
import com.ngen.cosys.event.notification.core.NotificationConstraint;
import com.ngen.cosys.event.notification.core.NotificationStats;
import com.ngen.cosys.event.notification.enums.NotificationType;
import com.ngen.cosys.event.notification.enums.SLAType;
import com.ngen.cosys.event.notification.model.EventCommunication;
import com.ngen.cosys.event.notification.model.EventNotification;
import com.ngen.cosys.event.notification.model.EventNotificationLogDetails;
import com.ngen.cosys.event.notification.model.EventNotificationModel;
import com.ngen.cosys.event.notification.model.EventTemplate;
import com.ngen.cosys.event.notification.model.NotificationFrequency;
import com.ngen.cosys.event.notification.model.NotificationUser;
import com.ngen.cosys.event.notification.util.EventNotificationUtils;
import com.ngen.cosys.events.payload.EMailEvent;
import com.ngen.cosys.events.producer.SendEmailEventProducer;
import com.ngen.cosys.multi.tenancy.enums.TenantZone;
import com.ngen.cosys.timezone.util.TenantZoneTime;

/**
 * Event Notification Processor used to send mails
 * 
 * @author NIIT
 */
@Component
public class EventNotificationProcessor {

   private static final Logger LOGGER = LoggerFactory.getLogger(EventNotificationProcessor.class);
   // CR LF
   private static final byte[] LINEFEED_CRLF = { 0x0D, 0x0A };
   // Horizontal Tab
   private static final byte[] TAB = { 0x09 };
   // SPACE
   private static final byte[] SPACE = { 0x20 };
   
   @Autowired
   private SendEmailEventProducer publisher;
   
   /**
    * @param eventNotificationModel
    * @param notificationStats
    */
   public void sendNotification(EventNotificationModel eventNotificationModel, NotificationStats notificationStats) {
      LOGGER.info("Notification Processor - Send Notification");
      boolean eventNotificationAvailability = EventNotificationUtils.notificationAvailability(eventNotificationModel)
            ? true
            : false;
      LOGGER.info("{} Entity and {} Event type notification availability - {}", eventNotificationModel.getEntity(),
            eventNotificationModel.getEventName(), eventNotificationAvailability);
      //
      for (EventNotification notification : eventNotificationModel.getEventNotification()) {
         boolean slaNotificationIdentified = true;
         //
         Map<EntityConstraint, NotificationConstraint> notificationConstraints = Collections.emptyMap();
         switch (notification.getSlaType()) {
         case SLAType.Key.RED:
            notificationConstraints = filteredNotificationConstraint(notification, notificationStats);
            break;
         case SLAType.Key.AMBER:
            notificationConstraints = filteredNotificationConstraint(notification, notificationStats);
            break;
         case SLAType.Key.GREEN:
            notificationConstraints = filteredNotificationConstraint(notification, notificationStats);
            break;
         default:
            slaNotificationIdentified = false;
            break;
         }
         // Trigger Notification
         if (slaNotificationIdentified) {
            triggerNotification(eventNotificationModel.getEntity(), eventNotificationModel.getEventName(), notification,
                  notificationConstraints);
            break;
         }
      }
   }
   
   /**
    * @param eventNotificationModel
    * @param notificationLogDetails
    * @param lastExecutionTime
    */
   public boolean sendNotification(EventNotificationModel eventNotificationModel,
         List<EventNotificationLogDetails> notificationLogDetails, LocalDateTime lastExecutionTime) {
      LOGGER.info("Notification Processor - Send Notification frequency");
      boolean eventNotificationAvailability = EventNotificationUtils.notificationAvailability(eventNotificationModel)
            ? true
            : false;
      LOGGER.info("{} Entity and {} Event type notification availability - {}", eventNotificationModel.getEntity(),
            eventNotificationModel.getEventName(), eventNotificationAvailability);
      boolean notificationTobeSend = false;
      if (eventNotificationAvailability) {
         for (EventNotification notification : eventNotificationModel.getEventNotification()) {
            List<EventNotificationLogDetails> filteredLogDetails = null;
            //
            switch (notification.getSlaType()) {
            case SLAType.Key.RED:
               notificationTobeSend = notificationFrequencyWithInConfiguredTime(notification.getFrequency(),
                     lastExecutionTime);
               filteredLogDetails = getFilteredLogDetails(notification, notificationLogDetails);
               break;
            case SLAType.Key.AMBER:
               notificationTobeSend = notificationFrequencyWithInConfiguredTime(notification.getFrequency(),
                     lastExecutionTime);
               filteredLogDetails = getFilteredLogDetails(notification, notificationLogDetails);
               break;
            case SLAType.Key.GREEN:
               notificationTobeSend = notificationFrequencyWithInConfiguredTime(notification.getFrequency(),
                     lastExecutionTime);
               filteredLogDetails = getFilteredLogDetails(notification, notificationLogDetails);
               break;
            default: break;
            }
            if (notificationTobeSend) {
               // Trigger Notification
               triggerNotification(eventNotificationModel.getEntity(), eventNotificationModel.getEventName(), notification,
                     filteredLogDetails);
            }
         }
      }
      return notificationTobeSend;
   }
   
   /**
    * @param notification
    * @param notificationStats
    * @return
    */
   private Map<EntityConstraint, NotificationConstraint> filteredNotificationConstraint(EventNotification notification,
         NotificationStats notificationStats) {
      LOGGER.info("Filtered Entity & Notification Constraint detailed information with ALL Match");
      Map<EntityConstraint, NotificationConstraint> notificationConstraints = new HashMap<>();
      //
      for (Iterator<Entry<EntityConstraint, List<NotificationConstraint>>> iterator = notificationStats
            .getNotificationConstraints().entrySet().iterator(); iterator.hasNext();) {
         Entry<EntityConstraint, List<NotificationConstraint>> entry = iterator.next();
         //
         if (Objects.nonNull(entry) && !CollectionUtils.isEmpty(entry.getValue())) {
            for (NotificationConstraint constraint : entry.getValue()) {
               if (Objects.equals(notification.getEventNotificationId(), constraint.getEventNotificationId())
                     && constraint.isAllMatch()) {
                  notificationConstraints.put(entry.getKey(), constraint);
                  break;
               }
            }
         }
      }
      LOGGER.info("Filtered Entity & Notification Constraint Size - {}", notificationConstraints.size());
      //
      return notificationConstraints;
   }
   
   /**
    * Notification Frequency Within the configured Time frame
    * 
    * @param frequency
    * @param lastExecutionTime
    * @return
    */
   private boolean notificationFrequencyWithInConfiguredTime(NotificationFrequency frequency,
         LocalDateTime lastExecutionTime) {
      boolean frequencyAvailability = Objects.nonNull(frequency) ? true : false;
      LOGGER.info(
            "Notification Frequency WithIn Configured Time :: Last execution Time - {} and Frequency Configuration Availability - {}",
            lastExecutionTime, frequencyAvailability);
      boolean frequencyWithInLimit = true;
      if (frequencyAvailability) {
         // Fixed Time With Current Job Execution Time (Tenant)
         LocalDateTime tenantDateTime = null;
         if (Objects.nonNull(frequency.getFixedTime())) {
            tenantDateTime = TenantZoneTime.getZoneDateTime(TenantZoneTime.getUTCDateTime(),
                  TenantZone.SIN.getAirportCode());
            LocalTime tenantTime = LocalTime.of(tenantDateTime.getHour(), tenantDateTime.getMinute());
            LOGGER.info("Frequency Fixed Time - {}, Tenant Time - {}", frequency.getFixedTime(), tenantTime);
            if (tenantTime.isBefore(frequency.getFixedTime())) {
               frequencyWithInLimit = false;
            }
         }
         if (Objects.nonNull(frequency.getRepeatTime())) {
            LocalDateTime localTime = LocalDateTime.now();
            LocalDateTime repeatScheduledTime = lastExecutionTime.plusHours(localTime.getHour())
                  .plusMinutes(frequency.getRepeatTime());
            tenantDateTime = TenantZoneTime.getZoneDateTime(TenantZoneTime.getUTCDateTime(),
                  TenantZone.SIN.getAirportCode());
            LOGGER.info("Frequency Repeat Scheduled Time - {}, Tenant Date Time - {}", repeatScheduledTime,
                  tenantDateTime);
            if (tenantDateTime.isBefore(repeatScheduledTime)) {
               frequencyWithInLimit = false;
            }
         }
      }
      return frequencyWithInLimit;
   }
   
   /**
    * @param entity
    * @param eventType
    * @param notification
    * @param notificationConstraints
    */
   private void triggerNotification(String entity, String eventType, EventNotification notification,
         Map<EntityConstraint, NotificationConstraint> notificationConstraints) {
      LOGGER.info("Notification Processor - Trigger Notification Constraint");
      boolean notificationTypesAvailability = EventNotificationUtils.notificationTypesAvailability(notification) //
            ? true //
            : false;
      boolean notificationUsersAvailability = EventNotificationUtils.notificationUsersAvailability(notification) //
            ? true //
            : false;
      LOGGER.info("Notification Availability for Types - {} and Users - {}", notificationTypesAvailability,
            notificationUsersAvailability);
      if (notificationTypesAvailability && notificationUsersAvailability) {
         for (EventCommunication communicationMode : notification.getNotificationTypes()) {
            boolean notificationSent = false;
            switch (communicationMode.getCommunicationType()) {
            case NotificationType.Key.CHAT:
               // No possibility
               break;
            case NotificationType.Key.EMAIL:
               if (Objects.nonNull(notification.getTemplate())) {
                  String mailSubject = getMailSubject(entity, eventType, SLAType.enumOf(notification.getSlaType()));
                  String mailBody = getMailBodyContent(notification.getTemplate(), notificationConstraints);
                  sendEmail(mailSubject, mailBody, notification.getNotificationUsers());
                  notificationSent = true;
               }
               break;
            case NotificationType.Key.FAX:
               // No possibility
               break;
            case NotificationType.Key.MESSAGE:
               // No possibility
               break;
            default: break;
            }
            if (notificationSent) {
               LOGGER.info("Notification Trigger Event is successfully completed {}");
               break;
            }
         }
      }
   }
   
   /**
    * @param entity
    * @param eventType
    * @param notification
    * @param notificationLogDetails
    */
   private void triggerNotification(String entity, String eventType, EventNotification notification,
         List<EventNotificationLogDetails> notificationLogDetails) {
      LOGGER.info("Notification Processor - Trigger Notification Frequency Job Process");
      boolean notificationTypesAvailability = EventNotificationUtils.notificationTypesAvailability(notification) //
            ? true //
            : false;
      boolean notificationUsersAvailability = EventNotificationUtils.notificationUsersAvailability(notification) //
            ? true //
            : false;
      LOGGER.info("Notification Availability for Types - {} and Users - {}", notificationTypesAvailability,
            notificationUsersAvailability);
      if (notificationTypesAvailability && notificationUsersAvailability) {
         for (EventCommunication communicationMode : notification.getNotificationTypes()) {
            boolean notificationSent = false;
            switch (communicationMode.getCommunicationType()) {
            case NotificationType.Key.CHAT:
               // No possibility
               break;
            case NotificationType.Key.EMAIL:
               if (Objects.nonNull(notification.getTemplate())) {
                  String mailSubject = getMailSubject(entity, eventType, SLAType.enumOf(notification.getSlaType()));
                  String mailBody = getMailBodyContent(notification.getTemplate(), notificationLogDetails);
                  sendEmail(mailSubject, mailBody, notification.getNotificationUsers());
                  notificationSent = true;
               }
               break;
            case NotificationType.Key.FAX:
               // No possibility
               break;
            case NotificationType.Key.MESSAGE:
               // No possibility
               break;
            default: break;
            }
            if (notificationSent) {
               LOGGER.info("Notification Frequency Job Trigger Event is successfully completed {}");
               break;
            }
         }
      }
   }
   
   /**
    * @param entity
    * @param eventType
    * @param slaType
    * @return
    */
   private String getMailSubject(String entity, String eventType, SLAType slaType) {
      StringBuilder subject = new StringBuilder();
      subject.append(entity).append(" - ").append(eventType).append(" ");
      subject.append("Event Not completed details dated : ");
      subject.append(String.valueOf(String.valueOf(TenantZoneTime.getZoneDateTime(LocalDateTime.now(), null))));
      subject.append(" ").append("SLA Type - ").append(slaType.name());
      return subject.toString();
   }
   
   /**
    * @param template
    * @param notificationLogDetails
    * @return
    */
   private String getMailBodyContent(EventTemplate template, List<EventNotificationLogDetails> notificationLogDetails) {
      // Template Parser and Builder
      StringBuilder mailBody = new StringBuilder();
      boolean started = false;
      for (EventNotificationLogDetails notificationLog : notificationLogDetails) {
         if (started) {
            mailBody.append(String.valueOf(charSequence(LINEFEED_CRLF)));
         }
         mailBody.append("Flight Key - ").append(notificationLog.getFlightKey());
         mailBody.append(String.valueOf(charSequence(TAB)));
         mailBody.append("Origin Date - ").append(notificationLog.getFlightDateTime());
      }
      return mailBody.toString();
   }
   
   /**
    * @param template
    * @param notificationConstraints
    * @return
    */
   private String getMailBodyContent(EventTemplate template,
         Map<EntityConstraint, NotificationConstraint> notificationConstraints) {
      // Template Parser and Builder
      StringBuilder mailBody = new StringBuilder();
      boolean started = false;
      for (EntityConstraint constraint : notificationConstraints.keySet()) {
         if (started) {
            mailBody.append(String.valueOf(charSequence(LINEFEED_CRLF)));
         }
         mailBody.append("Flight Key - ").append(constraint.getFlightKey());
         mailBody.append(String.valueOf(charSequence(TAB)));
         mailBody.append("Origin Date - ").append(constraint.getFlightOriginDate());
         mailBody.append(String.valueOf(charSequence(TAB)));
         mailBody.append("Time - ").append(constraint.getFlightDateTime());
         mailBody.append(String.valueOf(charSequence(SPACE)));
         mailBody.append("(").append(constraint.getFlightTime()).append(")");
         mailBody.append(String.valueOf(charSequence(TAB)));
         mailBody.append("Equation - ").append(constraint.getEquation());
         mailBody.append(String.valueOf(charSequence(TAB)));
         mailBody.append("Event Time - ").append(constraint.getEventDateTime());
         mailBody.append(String.valueOf(charSequence(TAB)));
         mailBody.append("Configured Minutes - ").append(constraint.getConfiguredMinutes());
      }
      return mailBody.toString();
   }

   /**
    * @param subject
    * @param mailBody
    * @param notificationUsers
    */
   private void sendEmail(String subject, String mailBody, List<NotificationUser> notificationUsers) {
      LOGGER.info("Send Mail Notification");
      // TODO:
      EMailEvent emailEvent = new EMailEvent();
      emailEvent.setMailSubject(subject);
      emailEvent.setMailBody(mailBody);
      if (!CollectionUtils.isEmpty(notificationUsers)) {
         emailEvent.setMailToAddress(EventNotificationUtils.getNotificationUserMailingList(notificationUsers));
      } else {
         emailEvent.setMailTo("vigneshshiv@niit-tech.com");
      }
      publisher.publish(emailEvent);
   }
   
   /**
    * @param notification
    * @param notificationLogDetails
    * @return
    */
   private List<EventNotificationLogDetails> getFilteredLogDetails(EventNotification notification,
         List<EventNotificationLogDetails> notificationLogDetails) {
      return notificationLogDetails.stream() //
            .filter(e -> Objects.equals(e.getEventNotificationId(), notification.getEventNotificationId())) //
            .collect(Collectors.toList());
   }
   
   /**
    * Character Feed
    * 
    * @param feed
    * @return
    */
   private static CharSequence charSequence(byte[] feed) {
      return new String(feed);
   }
   
}
