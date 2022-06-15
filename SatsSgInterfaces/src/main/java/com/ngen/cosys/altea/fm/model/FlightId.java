/**
 * FlightId.java
 * 
 * Copyright <PRE><IMG SRC = XX></IMG></PRE>
 *
 * Version      Date         Author      Reason
 * 1.0          22 JAN, 2019   NIIT      -
 */
package com.ngen.cosys.altea.fm.model;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * This class is used for Flight Identification
 * 
 * @author NIIT Technologies Ltd
 *
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@JsonPropertyOrder(value = { "carrierDetails", "flightDetails", "departureDate", "boardPoint" })
public class FlightId {

   /**
    * Outbound carrier detail - (M - 1 time)
    */
   @JacksonXmlProperty(localName = "carrierDetails")
   private CarrierDetail carrierDetail;
   
   /**
    * Outbound Flight number detail - (M - 1 time)
    */
   @JacksonXmlProperty(localName = "flightDetails")
   private FlightDetail flightDetail;
   
   /**
    * Departure date and time - (M - n .. 8)
    */
   @JacksonXmlProperty(localName = "departureDate")
   private Integer departureDate;
   
   /**
    * Place of departure - (M* - a .. 3)
    */
   @JacksonXmlProperty(localName = "boardPoint")
   private String boardPoint;
   
}
