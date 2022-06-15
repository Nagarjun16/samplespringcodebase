/**
 * DCSFMUpdateCargoFiguresReply.java
 * 
 * Copyright <PRE><IMG SRC = XX></IMG></PRE>
 *
 * Version      Date         Author      Reason
 * 1.0          22 JAN, 2019   NIIT      -
 */
package com.ngen.cosys.altea.fm.model;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * This class is used for Departure Control FM Cargo Figure response/reply
 * 
 * @author NIIT Technologies Ltd
 *
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@JsonPropertyOrder(value = { "flightId", "error" })
@JacksonXmlRootElement(localName = "DCSFM_UpdateCargoFiguresReply", namespace = "http://xml.amadeus.com/FECFUR_17_1_1A")
public class DCSFMUpdateCargoFiguresReply {

   /**
    * Flight detail response - (C - 1 time)
    */
   @JacksonXmlProperty(localName = "flightId")
   private FlightId flightId;
   
   /**
    * Error Code and Message - (C - 1 time)
    */
   @JacksonXmlProperty(localName = "error")
   private Error error;
   
}
