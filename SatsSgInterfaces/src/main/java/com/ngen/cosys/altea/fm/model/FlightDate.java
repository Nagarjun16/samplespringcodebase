/**
 * FlightDate.java
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
 * This class is used for Error Warning description
 * 
 * @author NIIT Technologies Ltd
 *
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@JsonIgnoreProperties(value = { "tenant", "flightId" })
@JsonPropertyOrder(value = { "timeMode", "dateTime" })
public class FlightDate {

   /**
    * Used for internally
    */
   private String tenant;
   private BigInteger flightId;
   
   /**
    * Indicate the time expressed in UTC(U)/Local(L) - (C - an .. 3)
    */
   @JacksonXmlProperty(localName = "timeMode")
   private String timeMode;
   
   /**
    * Structured date time - (M* - 1 time)
    */
   @JacksonXmlProperty(localName = "dateTime")
   private DateTime dateTime;
   
}
