/**
 * {@link ShipmentDateCache}
 * 
 * @copyright SATS Singapore 2019-20
 * @author NIIT
 */
package com.ngen.cosys.cache.client.model;

import java.time.LocalDate;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Shipment Date Cache detail/payload
 * 
 * @author NIIT Technologies Ltd
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class ShipmentDateCache {

   private String shipmentNumber;
   private LocalDate shipmentDate;
   private LocalDate cancelledOn;
   private LocalDate closedOn;
   private LocalDate departedOn;
   private LocalDate deliveredOn;
   private boolean abandoned;
   private boolean dispose;
   
}
