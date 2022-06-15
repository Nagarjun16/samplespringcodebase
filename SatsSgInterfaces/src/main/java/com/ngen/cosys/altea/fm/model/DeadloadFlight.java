/**
 * DeadloadFlight.java
 * 
 * Copyright <PRE><IMG SRC = XX></IMG></PRE>
 *
 * Version      Date         Author      Reason
 * 1.0          23 JUL, 2019   NIIT      -
 */
package com.ngen.cosys.altea.fm.model;

import java.math.BigInteger;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * This class reference used as part Deadload Info flight
 * 
 * @author NIIT Technologies Ltd
 *
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@JsonIgnoreProperties(value = { "flightId", "flightSegmentId", "deadloadFlightId" })
@JsonPropertyOrder(value = { "origin", "destination" })
public class DeadloadFlight {

   /**
    * Internal reference
    */
   private BigInteger flightId;
   private BigInteger flightSegmentId;
   private BigInteger deadloadFlightId;
   
   /**
    * Carrier Details - (M - 1)
    */
   @JacksonXmlProperty(localName = "carrierDetails")
   private CarrierDetail carrierDetail;
   
   /**
    * Carrier Details - (M - 1)
    */
   @JacksonXmlProperty(localName = "flightDetails")
   private FlightDetail flightDetail;
   
   /**
    * Offpoint - (C - 1)
    */
   @JacksonXmlProperty(localName = "offPoint")
   private String offPoint;
   
   /**
    * Carrier Details - (M - 1)
    */
   @JacksonXmlProperty(localName = "inboundCarrierDetails")
   private CarrierDetail inboundCarrierDetail;
   
   /**
    * Carrier Details - (M - 1)
    */
   @JacksonXmlProperty(localName = "inboundFlightDetails")
   private FlightDetail inboundFlightDetail;
   
}
