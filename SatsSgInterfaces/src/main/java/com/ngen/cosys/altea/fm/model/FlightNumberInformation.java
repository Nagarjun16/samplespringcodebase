/**
 * FlightNumberInformation.java
 * 
 * Copyright <PRE><IMG SRC = XX></IMG></PRE>
 *
 * Version      Date         Author      Reason
 * 1.0          22 JAN, 2019   NIIT      -
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
 * This class is used for Flight Identification
 * 
 * @author NIIT Technologies Ltd
 *
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@JsonIgnoreProperties(value = { "flightId" })
@JsonPropertyOrder(value = { "companyIdentification", "flightDetails" })
public class FlightNumberInformation {

   /**
    * Used for internally
    */
   private BigInteger flightId;
   
   /**
    * Company Identification - (M* - 1 time)
    */
   @JacksonXmlProperty(localName = "companyIdentification")
   private CompanyIdentification companyIdentification;
   
   /**
    * Flight Number and detail - (M* - 1 time)
    */
   @JacksonXmlProperty(localName = "flightDetails")
   private FlightDetail flightDetail;
   
}
