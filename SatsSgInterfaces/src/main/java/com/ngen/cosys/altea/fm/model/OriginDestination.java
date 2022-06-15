/**
 * OriginDestination.java
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
 * This class reference used as part Free Text
 * 
 * @author NIIT Technologies Ltd
 *
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@JsonIgnoreProperties(value = { "flightId", "flightSegmentId", "originDestinationId" })
@JsonPropertyOrder(value = { "origin", "destination" })
public class OriginDestination {
   
   /**
    * Internal reference
    */
   private BigInteger flightId;
   private BigInteger flightSegmentId;
   private BigInteger originDestinationId;
   
   /**
    * Place location - (M* - an .. 3)
    */
   @JsonInclude(Include.NON_EMPTY)
   @JacksonXmlProperty(localName = "origin")
   private String origin;
   
   /**
    * Place location - (M* - an .. 3)
    */
   @JsonInclude(Include.NON_EMPTY)
   @JacksonXmlProperty(localName = "destination")
   private String destination;
   
}
