/**
 * {@link EventNotification}
 * 
 * @copyright SATS Singapore 2020-21
 * @author NIIT
 */
package com.ngen.cosys.dashboard.model;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * This class used for Event Notification
 * 
 * @author NIIT Technologies Ltd
 */
@Getter
@Setter
@NoArgsConstructor
public class EventNotification implements Serializable {
   // Generated Serial VersionUID
   private static final long serialVersionUID = -1112937258099984893L;
   //
   private BigInteger eventNotificationId;
   private String module;
   private String entity;
   private String eventName;
   private String slaType;
   private String flightType;
   private String aircraftType;
   private String flightTime;
   private String equation;
   private Integer minutes;
   // Event Flight Information
   private List<EventFlightInformation> flightInformations;
   
}
