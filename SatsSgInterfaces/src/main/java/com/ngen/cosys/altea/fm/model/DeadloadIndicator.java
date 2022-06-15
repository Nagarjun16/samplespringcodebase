/**
 * DeadloadIndicator.java
 * 
 * Copyright <PRE><IMG SRC = XX></IMG></PRE>
 *
 * Version      Date         Author      Reason
 * 1.0          22 JAN, 2019   NIIT      -
 */
package com.ngen.cosys.altea.fm.model;

import java.math.BigInteger;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * This class is used for DeadloadIndicator
 * 
 * @author NIIT Technologies Ltd
 *
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@JsonIgnoreProperties(value = { "flightId", "flightSegmentId", "deadloadIndicatorId" })
@JsonPropertyOrder(value = { "statusDetails", "otherStatusDetails" })
public class DeadloadIndicator {

   /**
    * Internal reference
    */
   private BigInteger flightId;
   private BigInteger flightSegmentId;
   private BigInteger deadloadIndicatorId;
   
   /**
    * Repetition 1 time (M - 1 time)
    */
   @JacksonXmlProperty(localName = "statusDetails")
   private StatusDetail statusDetails;
   
   /**
    * Repetition 1 time (C - 1 time)
    */
   @JacksonXmlElementWrapper(useWrapping = false)
   @JacksonXmlProperty(localName = "otherStatusDetails")
   private List<StatusDetail> otherStatusDetails;
   
}
