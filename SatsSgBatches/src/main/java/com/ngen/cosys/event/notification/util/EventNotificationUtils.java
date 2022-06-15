/**
 * {@link EventNotificationUtils}
 * 
 * @copyright SATS Singapore 2019-20
 * @author NIIT
 */
package com.ngen.cosys.event.notification.util;

import java.math.BigInteger;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.ngen.cosys.event.notification.core.EntityConstraint;
import com.ngen.cosys.event.notification.core.NotificationConstraint;
import com.ngen.cosys.event.notification.core.ParameterConstraint;
import com.ngen.cosys.event.notification.enums.EntityEventTypes;
import com.ngen.cosys.event.notification.enums.Equation;
import com.ngen.cosys.event.notification.enums.FlightTime;
import com.ngen.cosys.event.notification.model.EventNotification;
import com.ngen.cosys.event.notification.model.EventNotificationModel;
import com.ngen.cosys.event.notification.model.ExportFlightEvents;
import com.ngen.cosys.event.notification.model.FlightEvents;
import com.ngen.cosys.event.notification.model.ImportFlightEvents;
import com.ngen.cosys.event.notification.model.NotificationUser;
import com.ngen.cosys.multi.tenancy.enums.TenantZone;
import com.ngen.cosys.timezone.util.TenantZoneTime;

import lombok.NoArgsConstructor;

/**
 * Event Notification Utility class
 * 
 * @author NIIT Technologies Ltd
 */
@NoArgsConstructor
public class EventNotificationUtils {

   private static final Logger LOGGER = LoggerFactory.getLogger(EventNotificationUtils.class);

   private static final String EXPORT = "EXPORT";
   private static final String IMPORT = "IMPORT";
   private static final String HASH = "#";
   private static final String PIPE = " | ";
   
   /**
    * Import Flight Events Config
    * 
    * @param eventType
    * @param lastExecutionTime
    * @return
    */
   public static ImportFlightEvents importFlightEvents(EntityEventTypes eventType, LocalDateTime lastExecutionTime) {
      ImportFlightEvents importFlightEvents = new ImportFlightEvents();
      //
      if (Objects.equals(EntityEventTypes.AWB_INBOUND_BREAKDOWN_COMPLETE, eventType)) {
         importFlightEvents.setBreakdownCompleted(true);
      } else if (Objects.equals(EntityEventTypes.FLIGHT_INBOUND_RAMP_CHECK_IN, eventType)) {
         importFlightEvents.setRampCheckInCompleted(true);
      } else if (Objects.equals(EntityEventTypes.FLIGHT_INBOUND_COMPLETE, eventType)) {
         importFlightEvents.setInboundFlightCompleted(true);
      }
      if (Objects.nonNull(lastExecutionTime)) {
         importFlightEvents.setLastExecutionTime(lastExecutionTime);
      }
      //
      return importFlightEvents;
   }
   
   /**
    * Export Flight Events Config
    * 
    * @param eventType
    * @return
    */
   public static ExportFlightEvents exportFlightEvents(EntityEventTypes eventType, LocalDateTime lastExecutionTime) {
      ExportFlightEvents exportFlightEvents = new ExportFlightEvents();
      //
      if (Objects.equals(EntityEventTypes.FLIGHT_OUTBOUND_DLS_COMPLETE, eventType)
            || Objects.equals(EntityEventTypes.FLIGHT_OUTBOUND_DLS_NFM, eventType)) {
         exportFlightEvents.setDlsCompleted(true);
      } else if (Objects.equals(EntityEventTypes.FLIGHT_OUTBOUND_MANIFEST_COMPLETE, eventType)) {
         exportFlightEvents.setManifestCompleted(true);
      } else if (Objects.equals(EntityEventTypes.FLIGHT_OUTBOUND_COMPLETE, eventType)) {
         exportFlightEvents.setOutboundFlightCompleted(true);
      }
      if (Objects.nonNull(lastExecutionTime)) {
         exportFlightEvents.setLastExecutionTime(lastExecutionTime);
      }
      //
      return exportFlightEvents;
   }
   
   /**
    * Default Flight Time
    * 
    * @param flight
    * @param module
    * @return
    */
   public static String defaultFlightTime(FlightEvents flight, String module) {
      if (!StringUtils.isEmpty(module)) {
         String flightTime = null;
         switch (module) {
         case EXPORT:
            if (Objects.nonNull(flight.getDateATD())) {
               flightTime = FlightTime.Key.ATD;
            } else if (Objects.nonNull(flight.getDateETD())) {
               flightTime = FlightTime.Key.ETD;
            } else {
               flightTime = FlightTime.Key.STD;
            }
            break;
         case IMPORT:
            if (Objects.nonNull(flight.getDateATA())) {
               flightTime = FlightTime.Key.ATA;
            } else if (Objects.nonNull(flight.getDateETA())) {
               flightTime = FlightTime.Key.ETA;
            } else {
               flightTime = FlightTime.Key.STA;
            }
            break;
         default: break;
         }
         return flightTime;
      }
      return null;
   }
   
   /**
    * Calculate SLA Duration
    * 
    * @param entityConstraint
    * @param flightTime
    * @param flight
    * @param eventType
    * @return
    */
   public static Duration calculateEventDuration(EntityConstraint entityConstraint, String flightTime,
         FlightEvents flight, String eventType) {
      LocalDateTime eventTime = eventTime(flight, eventType);
      LocalDateTime defaultTime = getFlightDefaultTime(flightTime, flight);
      entityConstraint.setEventDateTime(eventTime);
      entityConstraint.setFlightTime(flightTime);
      entityConstraint.setFlightDateTime(defaultTime);
      return Duration.between(defaultTime, eventTime);
   }
   
   /**
    * GET Flight Default Time
    * 
    * @param flightTime
    * @param flight
    * @return
    */
   private static LocalDateTime getFlightDefaultTime(String flightTime, FlightEvents flight) {
      LocalDateTime defaultTime = null;
      //
      switch (flightTime) {
      case FlightTime.Key.ATA:
         if (Objects.nonNull(flight.getDateATA())) {
            defaultTime = flight.getDateATA();
         } else {
            defaultTime = Objects.nonNull(flight.getDateETA()) ? flight.getDateETA() : flight.getDateSTA();
         }
         break;
      case FlightTime.Key.ETA:
         defaultTime = Objects.nonNull(flight.getDateETA()) ? flight.getDateETA() : flight.getDateSTA();
         break;
      case FlightTime.Key.STA:
         defaultTime = flight.getDateSTA();
         break;
      case FlightTime.Key.ATD:
         if (Objects.nonNull(flight.getDateATD())) {
            defaultTime = flight.getDateATD();
         } else {
            defaultTime = Objects.nonNull(flight.getDateETD()) ? flight.getDateETD() : flight.getDateSTD();
         }
         break;
      case FlightTime.Key.ETD:
         defaultTime = Objects.nonNull(flight.getDateETD()) ? flight.getDateETD() : flight.getDateSTD();
         break;
      case FlightTime.Key.STD:
         defaultTime = flight.getDateSTD();
         break;
      default: break;
      }
      LOGGER.info(
            "Event Notification Utils :: GET Flight Default Time - Flight Key - {}, TimeType  - {}, DefaultTime - {}",
            flight.getFlightKey(), flightTime, defaultTime);
      //
      return defaultTime;
   }
   
   /**
    * Event Time
    * 
    * @param flight
    * @param eventType
    * @return
    */
   private static LocalDateTime eventTime(FlightEvents flight, String eventType) {
      // Tenant Zone Time to match with flight absolute time
      LocalDateTime eventTime = TenantZoneTime.getZoneDateTime(TenantZoneTime.getUTCDateTime(),
            TenantZone.SIN.getAirportCode());
      //
      switch (eventType) {
      case EntityEventTypes.Key.AWB_INBOUND_BREAKDOWN_COMPLETE:
         if (Objects.nonNull(flight.getBreakdownCompletedOn())) {
            eventTime = flight.getBreakdownCompletedOn();
         }
         break;
      case EntityEventTypes.Key.FLIGHT_INBOUND_RAMP_CHECK_IN:
         if (Objects.nonNull(flight.getRampCheckInCompletedOn())) {
            eventTime = flight.getRampCheckInCompletedOn();
         }
         break;
      case EntityEventTypes.Key.FLIGHT_INBOUND_COMPLETE:
         if (Objects.nonNull(flight.getFlightCompletedOn())) {
            eventTime = flight.getFlightCompletedOn();
         }
         break;
      case EntityEventTypes.Key.FLIGHT_OUTBOUND_DLS_COMPLETE:
         if (Objects.nonNull(flight.getDlsCompletedOn())) {
            eventTime = flight.getDlsCompletedOn();
         }
         break;
      case EntityEventTypes.Key.FLIGHT_OUTBOUND_MANIFEST_COMPLETE:
         if (Objects.nonNull(flight.getManifestCompletedOn())) {
            eventTime = flight.getManifestCompletedOn();
         }
         break;
      case EntityEventTypes.Key.FLIGHT_OUTBOUND_COMPLETE:
         if (Objects.nonNull(flight.getFlightCompletedOn())) {
            eventTime = flight.getFlightCompletedOn();
         }
      default: break;
      }
      LOGGER.info("Event Notification Utils :: Event Name - {}, Flight Key - {}, Event Time - {}", eventType,
            flight.getFlightKey(), eventTime);
      //
      return eventTime;
   }
   
   /**
    * Configured SLA Breached
    * 
    * @param eventName
    * @param eventDuration
    * @param configuredMinutes
    * @param withInTimeDuration
    * @param equation
    * @return
    */
   public static boolean configuredEventSLABreached(String eventName, Duration eventDuration,
         BigInteger configuredMinutes, BigInteger withInTimeDuration, String equation) {
      boolean eventSLABreached = false;
      boolean importEvent = importEvent(eventName);
      BigInteger eventMinutes = BigInteger.valueOf(Math.abs(eventDuration.toMinutes()));
      //
      switch (equation) {
      case Equation.Type.EQUALS:
         eventSLABreached = eventMinutes.compareTo(configuredMinutes) == 0;
         break;
      case Equation.Type.NOT_EQUALS:
         eventSLABreached = eventMinutes.compareTo(configuredMinutes) != 0;
         break;
      case Equation.Type.LESS_THAN:
         eventSLABreached = eventMinutes.compareTo(configuredMinutes) == -1;
         break;
      case Equation.Type.LESS_THAN_EQUALS:
         eventSLABreached = eventMinutes.compareTo(configuredMinutes) != 1;
         break;
      case Equation.Type.GREATER_THAN:
         eventSLABreached = eventMinutes.compareTo(configuredMinutes) == 1;
         break;
      case Equation.Type.GREATER_THAN_EQUALS:
         eventSLABreached = eventMinutes.compareTo(configuredMinutes) != -1;
         if (!eventSLABreached) {
            if (!importEvent && Objects.nonNull(withInTimeDuration)) { // Export Event
               eventSLABreached = eventMinutes.compareTo(withInTimeDuration) != 1;
               LOGGER.info("EventNotificationUtils :: WithInTimeDuration check - {}", eventSLABreached);
            }
         }
         break;
      }
      LOGGER.info(
            "EventNotificationUtils :: Configured EventSLA breached - EventName - {}, MinutesConfigured - {}, Equation - {}, EventMinutes - {}",
            eventName, configuredMinutes, equation, eventMinutes);
      return importEvent ? !eventSLABreached : eventSLABreached;
   }
   
   /**
    * Import Event
    * 
    * @return
    */
   public static boolean importEvent(String eventName) {
      if (Objects.equals(EntityEventTypes.Key.FLIGHT_INBOUND_RAMP_CHECK_IN, eventName)
            || Objects.equals(EntityEventTypes.Key.AWB_INBOUND_BREAKDOWN_COMPLETE, eventName)
            || Objects.equals(EntityEventTypes.Key.FLIGHT_INBOUND_COMPLETE, eventName)) {
         return true;
      }
      return false;
   }
   
   /**
    * @param eventNotificationModel
    * @return
    */
   public static boolean notificationAvailability(EventNotificationModel eventNotificationModel) {
      return !CollectionUtils.isEmpty(eventNotificationModel.getEventNotification());
   }
   
   /**
    * @param notification
    * @return
    */
   public static boolean notificationTypesAvailability(EventNotification notification) {
      return !CollectionUtils.isEmpty(notification.getNotificationTypes());
   }
   
   /**
    * @param notification
    * @return
    */
   public static boolean notificationUsersAvailability(EventNotification notification) {
      return !CollectionUtils.isEmpty(notification.getNotificationUsers());
   }
   
   /**
    * Notification Users mailing list
    * 
    * @param notificationUsers
    * @return
    */
   public static String[] getNotificationUserMailingList(List<NotificationUser> notificationUsers) {
      String[] mailingList = new String[notificationUsers.size()];
      int j = 0;
      for (NotificationUser notificationUser : notificationUsers) {
         mailingList[j++] = notificationUser.getEmailId();
      }
      return mailingList;
   }
   
   /**
    * NotificationParameter any attempt constraint
    * 
    * @param constraint
    */
   public static void anyAttemptConstraint(NotificationConstraint constraint) {
      if (!constraint.isAnyAttempt()) {
         constraint.setAnyAttempt(true);
      }
   }

   /**
    * Any match constraint
    * 
    * @param constraint
    */
   public static void anyMatchConstraint(NotificationConstraint constraint) {
      if (!constraint.isAnyMatch()) {
         constraint.setAnyMatch(true);
      }
   }
   
   /**
    * @param constraint
    * @return
    */
   public static boolean allMatchConstraint(List<NotificationConstraint> constraint) {
      if (!CollectionUtils.isEmpty(constraint) && constraint.stream().anyMatch(e -> e.isAllMatch())) {
         return true;
      }
      return false;
   }
   
   /**
    * @param constraint
    * @return
    */
   public static NotificationConstraint getAllMatchConstraint(List<NotificationConstraint> constraint) {
      return constraint.stream().findFirst().filter(e -> e.isAllMatch()).get();
   }
   
   /**
    * Modify Parameter constraint
    * 
    * @param constraint
    * @param match
    */
   public static void modifyParameterConstraint(ParameterConstraint constraint, boolean match) {
      constraint.setMatch(true);
   }
   
   /**
    * @param s1
    * @param s2
    * @return
    */
   public static String constraintValues(String s1, String s2) {
      return s1 + HASH + s2;
   }
   
   /**
    * @param s1
    * @param s2
    * @param equation
    * @return
    */
   public static String equationConstraintValues(String s1, String s2, String equation) {
      return s1 + " " + equation + " " + s2;
   }

   /**
    * @param param
    * @param s1
    * @param s2
    * @param match
    * @param value
    * @return
    */
   public static String paramConstraintValues(String param, String s1, String s2, boolean match, String value) {
      String result = constraintValues(s1, s2);
      StringBuilder paramConstraint = new StringBuilder();
      paramConstraint.append(param).append(" : ").append(result);
      paramConstraint.append(" = ").append(String.valueOf(match));
      return StringUtils.isEmpty(value) ? paramConstraint.toString() : value + HASH + paramConstraint.toString();
   }
   
   /**
    * @param param
    * @param s1
    *           Notification constraint value
    * @param s2
    *           Payload constraint value
    * @param match
    * @return
    */
   public static ParameterConstraint getParameterConstraint(String param, String s1, String s2, boolean match) {
      ParameterConstraint constraint = new ParameterConstraint();
      constraint.setConstraint(param);
      constraint.setValues(constraintValues(s1, s2));
      constraint.setMatch(match);
      return constraint;
   }
   
   /**
    * @param param
    * @param s1
    * @param s2
    * @param equation
    * @param match
    * @return
    */
   public static ParameterConstraint getParameterConstraint(String param, String s1, String s2, String equation,
         boolean match) {
      ParameterConstraint constraint = new ParameterConstraint();
      constraint.setConstraint(param);
      constraint.setValues(equationConstraintValues(s1, s2, equation));
      constraint.setMatch(match);
      return constraint;
   }
   
   /**
    * @param paramConstraints
    * @param paramKey
    * @return
    */
   private static boolean containsKey(List<ParameterConstraint> paramConstraints, String paramKey) {
      for (ParameterConstraint paramConstraint : paramConstraints) {
         if (Objects.equals(paramKey, paramConstraint.getConstraint())) {
            return true;
         }
      }
      return false;
   }

   /**
    * EAWB Inference Matcher
    * 
    * @param paramConstraints
    * @param paramKey
    * @return
    */
   private static boolean inferenceConstraint(List<ParameterConstraint> paramConstraints, String paramKey) {
      for (ParameterConstraint paramConstraint : paramConstraints) {
         if (Objects.equals(paramKey, paramConstraint.getConstraint())) {
            return paramConstraint.isMatch();
         }
      }
      //
      return false;
   }
   
   /**
    * @param precedentsConstraint
    * @return
    */
   public static String justificationResult(NotificationConstraint notificationConstraint) {
      String justifiedResult = "";
      for (ParameterConstraint paramConstraint : notificationConstraint.getParameterConstraints()) {
         justifiedResult = getRawValue(justifiedResult, constraintHandler(paramConstraint));
      }
      //
      return justifiedResult;
   }

   /**
    * @param paramConstraint
    * @return
    */
   private static String constraintHandler(ParameterConstraint paramConstraint) {
      LOGGER.debug("Parameter constraint Handler {} ", paramConstraint.toString());
      String result = paramConstraint.getConstraint();
      result += " : [" + paramConstraint.getValues() + "]";
      return result;
   }

   /**
    * @param result
    * @param value
    * @return
    */
   public static String getRawValue(String result, String value) {
      return StringUtils.isEmpty(result) ? value : result + PIPE + value;
   }
   
}
