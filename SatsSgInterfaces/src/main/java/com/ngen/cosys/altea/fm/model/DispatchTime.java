/**
 * DispatchTime.java
 * 
 * Copyright <PRE><IMG SRC = XX></IMG></PRE>
 *
 * Version      Date         Author      Reason
 * 1.0          22 JAN, 2019   NIIT      -
 */
package com.ngen.cosys.altea.fm.model;

import java.math.BigInteger;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * This class is used for Update Cargo figures
 * 
 * @author NIIT Technologies Ltd
 *
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@JsonIgnoreProperties(value = { "flightId" })
@JsonPropertyOrder(value = { "businessSemantic", "timeMode", "dateTime" })
public class DispatchTime {

   /**
    * Used for internally
    */
   private BigInteger flightId;
   
   /**
    * Indicator - (M* - an .. 3)
    */
   @JsonInclude(Include.NON_EMPTY)
   @JacksonXmlProperty(localName = "businessSemantic")
   private String businessSemantic;
   
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
