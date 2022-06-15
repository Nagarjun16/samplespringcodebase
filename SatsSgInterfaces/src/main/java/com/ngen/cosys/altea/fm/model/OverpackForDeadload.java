/**
 * OverpackForDeadload.java
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
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * This class is used for OtherPaxDetail of Agent First name
 * 
 * @author NIIT Technologies Ltd
 *
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@JsonIgnoreProperties(value = { "eventSource", "tenant", "flightId", "flightSegmentId", "overpackId", "notocId" })
@JsonPropertyOrder(value = { "overpackIdentifier", "overpackNbr", "weightAndHeightOfOVP", "piecesOfOVP",
      "supplementaryInfoForOVP", "dgslForOVP", "allPackedInOneForOVP" })
public class OverpackForDeadload {

   /**
    * Internal reference
    */
   private String eventSource;
   private String tenant;
   private BigInteger flightId;
   private BigInteger flightSegmentId;
   private BigInteger overpackId;
   private BigInteger notocId;
   
   /**
    * Overpack load details - (M - 1 time)
    */
   @JsonInclude(Include.NON_NULL)
   @JacksonXmlProperty(localName = "overpackIdentifier")
   private OverpackIdentifier overpackIdentifier;
   
   /**
    * Unique ID Reference - (M - 1 time)
    */
   @JsonInclude(Include.NON_NULL)
   @JacksonXmlProperty(localName = "overpackNbr")
   private OverpackNbr overpackNbr;
   
   /**
    * Repetition 1 time (C - 1 time)
    */
   @JsonInclude(Include.NON_NULL)
   @JacksonXmlProperty(localName = "weightAndHeightOfOVP")
   private QuantityDetail weightAndHeightOfOVP;
   
   /**
    * Repetition 1 time (C - 1 time)
    */
   @JsonInclude(Include.NON_NULL)
   @JacksonXmlProperty(localName = "piecesOfOVP")
   private QuantityDetail piecesOfOVP;
   
   /**
    * Supplementary Information - Repetition 2 times (C - 2 times)
    */
   @JsonInclude(Include.NON_NULL)
   @JacksonXmlElementWrapper(useWrapping = false)
   @JacksonXmlProperty(localName = "supplementaryInfoForOVP")
   private List<FreeTextQualifier> supplementaryInfoForOVP;
   
   /**
    * Group - Repetition 200 times (C - 200 times)
    */
   @JacksonXmlElementWrapper(useWrapping = false)
   @JacksonXmlProperty(localName = "dgslForOVP")
   private List<DGSL> dgslForOVP;

   /**
    * Associated data for all in One items - Repetition 200 times (C - 200 times)
    */
   @JacksonXmlElementWrapper(useWrapping = false)
   @JacksonXmlProperty(localName = "allPackedInOneForOVP")
   private List<AllPackedInOne> allPackedInOneForOVP;
   
}
