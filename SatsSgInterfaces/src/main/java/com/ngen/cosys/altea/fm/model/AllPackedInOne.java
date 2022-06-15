/**
 * AllPackedInOneForDeadload.java
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
 * This class is used for All Packed In One For Dead Load sub types
 * 
 * @author NIIT Technologies Ltd
 *
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@JsonIgnoreProperties(value = { "eventSource", "tenant", "flightId", "flightSegmentId", "allPackedInOneId", "notocId" })
@JsonPropertyOrder(value = { "allPackedInOneIdentifier", "allPackedInOneNbr", "weightAndHeightOfAPIO", "piecesOfAPIO",
      "supplementaryInfoForAPIO", "dgslForAPIO" })
public class AllPackedInOne {

   /**
    * Internal reference
    */
   private String eventSource;
   private String tenant;
   private BigInteger flightId;
   private BigInteger flightSegmentId;
   private BigInteger allPackedInOneId;
   private BigInteger notocId;
   
   /**
    * Repetition 1 time (M - 1 time)
    */
   @JacksonXmlProperty(localName = "allPackedInOneIdentifier")
   private AllPackedInOneIdentifier allPackedInOneIdentifier;
   
   /**
    * Repetition 1 time (M - 1 time)
    */
   @JacksonXmlProperty(localName = "allPackedInOneNbr")
   private AllPackedInOneNbr allPackedInOneNbr;
   
   /**
    * Repetition 1 time (C - 1 time)
    */
   @JacksonXmlProperty(localName = "weightAndHeightOfAPIO")
   private WeightAndHeight weightAndHeightOfAPIO;
   
   /**
    * Repetition 1 time (C - 1 time)
    */
   @JacksonXmlProperty(localName = "piecesOfAPIO")
   private Pieces piecesOfAPIO;
   
   /**
    * Repetition 2 times (C - 2 times)
    */
   @JacksonXmlElementWrapper(useWrapping = false)
   @JacksonXmlProperty(localName = "supplementaryInfoForAPIO")
   private List<FreeTextQualifier> supplementaryInfoForAPIO;
   
   /**
    * Group - Repetition 200 times (C - 200 times)
    */
   @JacksonXmlElementWrapper(useWrapping = false)
   @JacksonXmlProperty(localName = "dgslForAPIO")
   private List<DGSL> dgslForAPIO;
   
}
