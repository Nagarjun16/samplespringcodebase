/**
 * ShipmentEvents.java
 * 
 * @copyright SATS Singapore 2019-20
 * @author NIIT
 */
package com.ngen.cosys.event.notification.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.Set;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * This class is used for Shipment events
 * 
 * @author NIIT Technologies Ltd
 *
 */
@Getter
@Setter
@NoArgsConstructor
public class ShipmentEvents extends FlightEvents {

   private BigInteger shipmentId;
   private String shipmentNumber;
   private LocalDate shipmentDate;
   private String carrierCode;
   private String shipmentType;
   private String origin;
   private String destination;
   private BigInteger pieces;
   private BigDecimal weight;
   private Set<String> shcs;
   
}
