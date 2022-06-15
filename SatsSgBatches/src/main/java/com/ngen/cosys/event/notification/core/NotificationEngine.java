/**
 * {@link NotificationEngine}
 * 
 * @copyright SATS Singapore 2019-20
 * @author NIIT
 */
package com.ngen.cosys.event.notification.core;

import java.math.BigInteger;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.ngen.cosys.event.notification.enums.EventNotificationParameter;
import com.ngen.cosys.event.notification.enums.SLAType;
import com.ngen.cosys.event.notification.model.EventNotification;
import com.ngen.cosys.event.notification.model.EventNotificationModel;
import com.ngen.cosys.event.notification.model.FlightEvents;
import com.ngen.cosys.event.notification.model.FlightInformation;
import com.ngen.cosys.event.notification.util.EventNotificationUtils;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.timezone.util.TenantZoneTime;

/**
 * Notification Engine evaluates the notification entity and event type based
 * process and provides the result
 * 
 * @author NIIT Technologies Ltd
 */
@Component
public class NotificationEngine {

   private static final Logger LOGGER = LoggerFactory.getLogger(NotificationEngine.class);
   
   /**
    * Analysis
    * 
    * @param eventNotificationModel
    * @param flightDetails
    * @return
    * @throws CustomException
    */
   public NotificationStats inferenceAnalysis(EventNotificationModel eventNotificationModel,
         List<FlightEvents> flightDetails) throws CustomException {
      LOGGER.info("{} Entity and {} Event Type - inference analysis", eventNotificationModel.getEntity(),
            eventNotificationModel.getEventName());
      // Notification Stats initialization
      NotificationStats notificationStats = new NotificationStats();
      initialize(notificationStats, eventNotificationModel);
      //
      for (FlightEvents flight : flightDetails) {
         notificationAnalysis(eventNotificationModel, flight, notificationStats);
      }
      // Perform Decision analysis
      decisionAnalysis(notificationStats);
      return notificationStats;
   }
   
   /**
    * Initialize
    * 
    * @param notificationStats
    * @param eventNotificationModel
    */
   private void initialize(NotificationStats notificationStats, EventNotificationModel eventNotificationModel) {
      LOGGER.info("Notification Stats Initialization Process");
      notificationStats.setModule(eventNotificationModel.getModule());
      notificationStats.setEntity(eventNotificationModel.getEntity());
      notificationStats.setEventName(eventNotificationModel.getEventName());
      notificationStats.setStartTime(TenantZoneTime.getUTCDateTime());
      notificationStats.setTotalRecords(eventNotificationModel.getEventNotification().size());
      notificationStats.setNotificationConstraints(Collections.synchronizedMap(new HashMap<>()));
   }
   
   /**
    * @param eventNotificationModel
    * @param flight
    * @param notificationStats
    */
   private void notificationAnalysis(EventNotificationModel eventNotificationModel, FlightEvents flight,
         NotificationStats notificationStats) {
      LOGGER.info("Notification Analysis :: Flight Key - {} and Flight Date - {}", flight.getFlightKey(),
            flight.getFlightOriginDate());
      EntityConstraint entityConstraint = new EntityConstraint();
      notificationStats.getNotificationConstraints().put(constraintKey(entityConstraint, flight), new ArrayList<>());
      String module = notificationStats.getModule();
      String eventType = notificationStats.getEventName();
      //
      for (EventNotification notification : eventNotificationModel.getEventNotification()) {
         //
         NotificationConstraint constraint = new NotificationConstraint();
         constraint.setHasValues(new HashSet<>());
         constraint.setParameterConstraints(new ArrayList<>());
         constraint.setSlaType(notification.getSlaType());
         constraint.setEventNotificationId(notification.getEventNotificationId());
         //
         boolean match = false;
         LOGGER.info("{} Entity and {} Event Type configured Notification SLA Category - {}",
               eventNotificationModel.getEntity(), eventNotificationModel.getEventName(), notification.getSlaType());
         // FLIGHT_INFORMATION
         boolean flightInfoAvailability = !CollectionUtils.isEmpty(notification.getFlightInformations()) ? true : false;
         LOGGER.info("{} Entity and {} Event Type configured Notification Availability - {}",
               eventNotificationModel.getEntity(), eventNotificationModel.getEventName(),
               String.valueOf(flightInfoAvailability));
         if (flightInfoAvailability) {
            // Add Parameter Constraint
            constraint.getHasValues().add(EventNotificationParameter.Key.FLIGHT_INFORMATION);
            // Any attempt flag 
            EventNotificationUtils.anyAttemptConstraint(constraint);
            boolean anyMatch = false;
            String result = null;
            //
            for (FlightInformation flightInfo : notification.getFlightInformations()) {
               String rawValue = null;
               boolean flightInfoMatch = false;
               // FLIGHT_CARRIER_CODE
               if (!StringUtils.isEmpty(flightInfo.getCarrierCode())) {
                  flightInfoMatch = Objects.equals(flightInfo.getCarrierCode(), flight.getCarrierCode());
                  LOGGER.info("Flight Key - {}, Parameter - {}, Constraint Values - {}, Matching Status - {}",
                        flight.getFlightKey(), EventNotificationParameter.Key.FLIGHT_CARRIER_CODE,
                        EventNotificationUtils.constraintValues(flightInfo.getCarrierCode(), flight.getCarrierCode()),
                        String.valueOf(flightInfoMatch));
                  if (!flightInfoMatch) {
                     rawValue = EventNotificationUtils.paramConstraintValues(
                           EventNotificationParameter.Key.FLIGHT_CARRIER_CODE, flightInfo.getCarrierCode(),
                           flight.getCarrierCode(), flightInfoMatch, result);
                     result = EventNotificationUtils.getRawValue(result, rawValue);
                     continue;
                  }
               }
               // FLIGHT_KEY
               if (!StringUtils.isEmpty(flightInfo.getFlightKey())) {
                  flightInfoMatch = Objects.equals(flightInfo.getFlightKey(), flight.getFlightKey());
                  LOGGER.info("Flight Key - {}, Parameter - {}, Constraint Values - {}, Matching Status - {}",
                        flight.getFlightKey(), EventNotificationParameter.Key.FLIGHT_KEY,
                        EventNotificationUtils.constraintValues(flightInfo.getFlightKey(), flight.getFlightKey()),
                        String.valueOf(flightInfoMatch));
                  if (!flightInfoMatch) {
                     rawValue = EventNotificationUtils.paramConstraintValues(EventNotificationParameter.Key.FLIGHT_KEY,
                           flightInfo.getFlightKey(), flight.getFlightKey(), flightInfoMatch, result);
                     result = EventNotificationUtils.getRawValue(result, rawValue);
                     continue;
                  }
               }
               // FLIGHT_DATETIME
               if (Objects.nonNull(flightInfo.getFlightDateTime())) {
                  flightInfoMatch = Objects.equals(flightInfo.getFlightDateTime(), flight.getFlightOriginDate());
                  LOGGER.info("Flight Key - {}, Parameter - {}, Constraint Values - {}, Matching Status - {}",
                        flight.getFlightKey(), EventNotificationParameter.Key.FLIGHT_DATETIME,
                        EventNotificationUtils.constraintValues(String.valueOf(flightInfo.getFlightDateTime()),
                              String.valueOf(flight.getFlightOriginDate())),
                        String.valueOf(flightInfoMatch));
                  rawValue = EventNotificationUtils.paramConstraintValues(
                        EventNotificationParameter.Key.FLIGHT_DATETIME, String.valueOf(flightInfo.getFlightDateTime()),
                        String.valueOf(flight.getFlightOriginDate()), flightInfoMatch, result);
                  result = EventNotificationUtils.getRawValue(result, rawValue);
               }
               //
               if (flightInfoMatch) {
                  if (!anyMatch) {
                     anyMatch = true;
                  }
                  break;
               }
            }
            LOGGER.info("Flight Key - {}, Parameter - {}, Constraint Values - {}, Matching Status - {}",
                  flight.getFlightKey(), EventNotificationParameter.Key.FLIGHT_INFORMATION,
                  EventNotificationUtils.getParameterConstraint(EventNotificationParameter.Key.FLIGHT_INFORMATION,
                        String.valueOf("null"), result, anyMatch));
            constraint.getParameterConstraints().add(EventNotificationUtils.getParameterConstraint(
                  EventNotificationParameter.Key.FLIGHT_INFORMATION, String.valueOf("null"), result, anyMatch));
            if (anyMatch) {
               // Any match constraint
               EventNotificationUtils.anyMatchConstraint(constraint);
            } else {
               LOGGER.info("Notification Finalize OF Flight :: Key - {}, Parameter - {} is not matched",
                     flight.getFlightKey(), EventNotificationParameter.Key.FLIGHT_INFORMATION);
               notificationStats.getNotificationConstraints().get(entityConstraint).add(constraint);
               continue;
            }
         }
         // AIRCRAFT_TYPE
         if (!StringUtils.isEmpty(notification.getAircraftType()) && !StringUtils.isEmpty(flight.getAircraftType())) {
            // Add Parameter Constraint
            constraint.getHasValues().add(EventNotificationParameter.Key.AIRCRAFT_TYPE);
            // Any attempt flag
            EventNotificationUtils.anyAttemptConstraint(constraint);
            match = Objects.equals(notification.getAircraftType(), flight.getAircraftType());
            LOGGER.info("Flight Key - {}, Parameter - {}, Constraint Values - {}, Matching Status - {}",
                  flight.getFlightKey(), EventNotificationParameter.Key.AIRCRAFT_TYPE,
                  EventNotificationUtils.constraintValues(notification.getAircraftType(), flight.getAircraftType()),
                  String.valueOf(match));
            constraint.getParameterConstraints()
                  .add(EventNotificationUtils.getParameterConstraint(EventNotificationParameter.Key.AIRCRAFT_TYPE,
                        notification.getAircraftType(), flight.getAircraftType(), match));
            // Any match flag
            if (match) {
               EventNotificationUtils.anyMatchConstraint(constraint);
            } else {
               LOGGER.info("Notification Finalize OF Flight :: Key - {}, Parameter - {} is not matched",
                     flight.getFlightKey(), EventNotificationParameter.Key.AIRCRAFT_TYPE);
               notificationStats.getNotificationConstraints().get(entityConstraint).add(constraint);
               continue;
            }
         }
         // FLIGHT_TYPE
         if (!StringUtils.isEmpty(notification.getFlightType()) && !StringUtils.isEmpty(flight.getFlightType())) {
            // Add Parameter Constraint
            constraint.getHasValues().add(EventNotificationParameter.Key.FLIGHT_TYPE);
            // Any attempt flag
            EventNotificationUtils.anyAttemptConstraint(constraint);
            match = Objects.equals(notification.getFlightType(), flight.getFlightType());
            LOGGER.info("Flight Key - {}, Parameter - {}, Constraint Values - {}, Matching Status - {}",
                  flight.getFlightKey(), EventNotificationParameter.Key.FLIGHT_TYPE,
                  EventNotificationUtils.constraintValues(notification.getFlightType(), flight.getFlightType()),
                  String.valueOf(match));
            constraint.getParameterConstraints()
                  .add(EventNotificationUtils.getParameterConstraint(EventNotificationParameter.Key.FLIGHT_TYPE,
                        notification.getFlightType(), flight.getFlightType(), match));
            // Any match flag
            if (match) {
               EventNotificationUtils.anyMatchConstraint(constraint);
            } else {
               LOGGER.info("Notification Finalize OF Flight :: Key - {}, Parameter - {} is not matched",
                     flight.getFlightKey(), EventNotificationParameter.Key.FLIGHT_TYPE);
               notificationStats.getNotificationConstraints().get(entityConstraint).add(constraint);
               continue;
            }
         }
         // FLIGHT_TIME || EQUATION || MINUTES
         if (!StringUtils.isEmpty(notification.getEquation()) && Objects.nonNull(notification.getMinutes())) {
            // Any attempt flag
            EventNotificationUtils.anyAttemptConstraint(constraint);
            //
            Duration eventDuration = null;
            String flightTime = !StringUtils.isEmpty(notification.getFlightTime()) //
                                    ? notification.getFlightTime() //
                                    : EventNotificationUtils.defaultFlightTime(flight, module);
            LOGGER.info("Notification Configured OR Default Flight Time - {}", flightTime);
            eventDuration = EventNotificationUtils.calculateEventDuration(entityConstraint, flightTime, flight,
                  eventType);
            LOGGER.info("Notification Calculated Event duration - {}", String.valueOf(eventDuration));
            entityConstraint.setEquation(notification.getEquation());
            entityConstraint.setConfiguredMinutes(notification.getMinutes());
            match = EventNotificationUtils.configuredEventSLABreached(eventNotificationModel.getEventName(),
                  eventDuration, BigInteger.valueOf(notification.getMinutes()),
                  BigInteger.valueOf(notification.getWithInTimeDuration()), notification.getEquation());
            //
            LOGGER.info("Flight Key - {}, Parameter - {}, Constraint Values - {}, Matching Status - {}",
                  flight.getFlightKey(), EventNotificationParameter.Key.DURATION,
                  EventNotificationUtils.equationConstraintValues(String.valueOf(notification.getMinutes()),
                        String.valueOf(eventDuration), notification.getEquation()),
                  String.valueOf(match));
            constraint.getParameterConstraints()
                  .add(EventNotificationUtils.getParameterConstraint(EventNotificationParameter.Key.DURATION,
                        String.valueOf(notification.getMinutes()), String.valueOf(eventDuration),
                        notification.getEquation(), match));
            // Any match constraint
            if (match) {
               EventNotificationUtils.anyMatchConstraint(constraint);
               // Priority 
               if (Objects.equals(SLAType.RED.name(), notification.getSlaType())
                     || Objects.equals(SLAType.AMBER.name(), notification.getSlaType())) {
                  LOGGER.info("Notification Priority satisfied {}");
                  // Breaks from current notification
                  notificationStats.getNotificationConstraints().get(entityConstraint).add(constraint);
                  break;
               }
            } else {
               LOGGER.info("Notification Finalize OF Flight :: Key - {}, Parameter - {} is not matched",
                     flight.getFlightKey(), EventNotificationParameter.Key.DURATION);
               notificationStats.getNotificationConstraints().get(entityConstraint).add(constraint);
               continue;
            }
         } else {
            LOGGER.info(
                  "Notification Finalize OF Flight :: Notification - FLIGHT_TIME || EQUATION || MINUTES config not exists");
         }
         // Check the attempt flag
         if (constraint.isAnyAttempt()) {
            LOGGER.info("Notification Stats - ANY attempt flag constraint ");
            notificationStats.getNotificationConstraints().get(entityConstraint).add(constraint);
         }
      }
   }
   
   /**
    * Constraint Key
    * 
    * @param entityConstraint
    * @param flight
    * @return
    */
   private EntityConstraint constraintKey(EntityConstraint entityConstraint, FlightEvents flight) {
      entityConstraint.setFlightId(flight.getFlightId());
      entityConstraint.setFlightKey(flight.getFlightKey());
      entityConstraint.setFlightOriginDate(flight.getFlightOriginDate());
      return entityConstraint;
   }
   
   /**
    * @param notificationStats
    */
   private void decisionAnalysis(NotificationStats notificationStats) {
      LOGGER.info("{} Entity and {} Event Type - decision Analysis", notificationStats.getEntity(),
            notificationStats.getEventName());
      if (!CollectionUtils.isEmpty(notificationStats.getNotificationConstraints())) {
         for (Iterator<Entry<EntityConstraint, List<NotificationConstraint>>> iterator = notificationStats
               .getNotificationConstraints().entrySet().iterator(); iterator.hasNext();) {
            Entry<EntityConstraint, List<NotificationConstraint>> entry = iterator.next();
            if (Objects.nonNull(entry)) {
               EntityConstraint entityConstraint = entry.getKey();
               LOGGER.info("Entity Constraint Details :: Flight Key - {}, Date - {}", entityConstraint.getFlightKey(),
                     entityConstraint.getFlightOriginDate());
               //
               if (!CollectionUtils.isEmpty(entry.getValue())) {
                  for (NotificationConstraint constraint : entry.getValue()) {
                     if (constraint.isAnyAttempt() && constraint.isAnyMatch()) {
                        boolean falseMatch = constraint.getParameterConstraints().stream().anyMatch(e -> !e.isMatch());
                        LOGGER.info("Notification Parameter Constraint All Match Flag of FALSE Identification :: {} ",
                              String.valueOf(falseMatch));
                        if (!falseMatch) {
                           constraint.setAllMatch(true);
                        }
                     }
                  }
               }
            }
         }
         // Collect stats
         collectStats(notificationStats);
      }
      // End Time
      if (Objects.isNull(notificationStats.getEndTime())) {
         notificationStats.setEndTime(TenantZoneTime.getUTCDateTime());
      }
   }
   
   /**
    * @param notificationStats
    */
   private void collectStats(NotificationStats notificationStats) {
      LOGGER.info("{} Entity and {} Event Type - collectStats", notificationStats.getEntity(),
            notificationStats.getEventName());
      int totalRecords = notificationStats.getNotificationConstraints().size();
      int newlyAdded = 0;
      for (Iterator<Entry<EntityConstraint, List<NotificationConstraint>>> iterator = notificationStats
            .getNotificationConstraints().entrySet().iterator(); iterator.hasNext();) {
         Entry<EntityConstraint, List<NotificationConstraint>> entry = iterator.next();
         if (!CollectionUtils.isEmpty(entry.getValue())) {
            newlyAdded += entry.getValue().stream().filter(e -> e.isAllMatch()).count();
         }
      }
      LOGGER.info("{} Entity and {} Event Type - Total Records - {}, Newly Added - {}", notificationStats.getEntity(),
            notificationStats.getEventName(), totalRecords, newlyAdded);
      // Update Stats
      notificationStats.setTotalRecords(totalRecords);
      notificationStats.setNewlyAdded(newlyAdded);
      notificationStats.setExecutionCount(newlyAdded);
   }
   
   /**
    * @param notificationStats
    * @param flightDetails
    * @return
    */
   public NotificationStats updateAnalysis(NotificationStats notificationStats, List<FlightEvents> flightDetails) {
      // TODO:
      return notificationStats;
   }
   
   /**
    * @param notificationStats
    */
   private void updateStats(NotificationStats notificationStats) {
      LOGGER.info("{} Entity and {} Event Type - updateStats", notificationStats.getEntity(),
            notificationStats.getEventName());
      // TODO:
   }
   
}
