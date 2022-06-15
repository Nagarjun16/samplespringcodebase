/**
 * {@link EventFlightInformation}
 * 
 * @copyright SATS Singapore 2020-21
 * @author NIIT
 */
package com.ngen.cosys.dashboard.model;

import java.io.Serializable;
import java.math.BigInteger;
import java.time.LocalDate;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Flight Information detail
 * 
 * @author NIIT Technologies Ltd
 */
@Getter
@Setter
@NoArgsConstructor
public class EventFlightInformation implements Serializable {
   // Generated Serial VersionUID
   private static final long serialVersionUID = -7152648327039102866L;
   //
   private BigInteger eventNotificationId;
   private String carrierCode;
   private String flightKey;
   private LocalDate flightDate;
   
}
