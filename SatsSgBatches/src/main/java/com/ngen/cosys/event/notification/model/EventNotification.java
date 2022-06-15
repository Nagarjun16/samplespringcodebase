/**
 * EventNotification.java
 * 
 * @copyright SATS Singapore 2019-20
 * @author NIIT
 */
package com.ngen.cosys.event.notification.model;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Constraint Configuration of event Notification
 * 
 * @author NIIT Technologies Ltd
 */
@Getter
@Setter
@NoArgsConstructor
public class EventNotification {

   private BigInteger eventNotificationId;
   private BigInteger eventTypeId;
   private String eventModule;
   private String slaType;
   private String aircraftType;
   private String flightType;
   private String shc;
   // Flight Time Parameter - Type Reference FlightTime
   private String flightTime;
   // Equation Type
   private String equation;
   private Integer minutes;
   private Integer withInTimeDuration;
   private Integer count;
   // NotificationType
   private List<EventCommunication> notificationTypes;
   private List<NotificationUser> notificationUsers;
   private List<FlightInformation> flightInformations;
   private NotificationFrequency frequency;
   private EventTemplate template;
   private LocalDateTime dlsPrecisionTime;
   private LocalDateTime flightPrecisionTime;
   
}
