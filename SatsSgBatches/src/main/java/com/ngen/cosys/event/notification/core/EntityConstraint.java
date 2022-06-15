/**
 * EntityConstraint.java
 * 
 * @copyright SATS Singapore 2019-20
 * @author NIIT
 */
package com.ngen.cosys.event.notification.core;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * This class is used for Entity Constraint
 * 
 * @author NIIT Technologies Ltd
 *
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode(of = "flightId")
public class EntityConstraint {
   // Shipment
   private BigInteger shipmentId;
   private String shipmentNumber;
   private LocalDate shipmentDate;
   // Flight
   private BigInteger flightId;
   private String flightKey;
   private LocalDateTime flightOriginDate;
   private LocalDateTime flightDateTime;
   private String flightTime;
   private String equation;
   private Integer configuredMinutes;
   private LocalDateTime eventDateTime;
   // ULD
   private String uldNumber;
   
}
