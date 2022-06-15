/**
 * FlightInformation.java
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
 * This class is used for Event Type config
 * 
 * @author NIIT Technologies Ltd
 *
 */
@Getter
@Setter
@NoArgsConstructor
public class FlightInformation {

   private BigInteger eventNotificationId;
   private String carrierCode;
   private String flightKey;
   private LocalDateTime flightDateTime;
   
}
