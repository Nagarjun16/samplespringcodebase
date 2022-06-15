/**
 * FlightDetail.java
 * 
 * Copyright <PRE><IMG SRC = XX></IMG></PRE>
 *
 * Version      Date         Author      Reason
 * 1.0          22 JAN, 2019   NIIT      -
 */
package com.ngen.cosys.altea.fm.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * This class is used for Flight Detail
 * 
 * @author NIIT Technologies Ltd
 *
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class FlightDetail {

   /**
    * Flight number - (M - n .. 4)
    */
   @JacksonXmlProperty(localName = "flightNumber")
   private String flightNumber;
   
   /**
    * Operational Suffix to the carrier code - (C - 1)
    */
   @JacksonXmlProperty(localName = "operationalSuffix")
   private String operationalSuffix;
   
}
