/**
 * FlightEvents.java
 * 
 * @copyright SATS Singapore 2019-20
 * @author NIIT
 */
package com.ngen.cosys.event.notification.model;

import java.math.BigInteger;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * This class is used for Flight events
 * 
 * @author NIIT Technologies Ltd
 *
 */
@Getter
@Setter
@NoArgsConstructor
public class FlightEvents {

   private BigInteger flightEventId;
   private BigInteger flightId;
   private String carrierCode;
   private String flightNumber;
   private String flightKey;
   private LocalDateTime flightOriginDate;
   private String flightType;
   private String aircraftType;
   //
   private LocalDateTime dateSTD;
   private LocalDateTime dateETD;
   private LocalDateTime dateATD;
   private LocalDateTime dateSTA;
   private LocalDateTime dateETA;
   private LocalDateTime dateATA;
   // Import
   private LocalDateTime rampCheckInCompletedOn;
   private LocalDateTime documentVerificationCompletedOn;
   private LocalDateTime breakdownCompletedOn;
   private LocalDateTime flightClosedOn;
   // Export
   private LocalDateTime buildupCompletedOn;
   private LocalDateTime dlsCompletedOn;
   private LocalDateTime manifestCompletedOn;
   private LocalDateTime offloadCompletedOn;
   private LocalDateTime flightDepartedOn;
   // Common
   private LocalDateTime flightCompletedOn;
   
}
