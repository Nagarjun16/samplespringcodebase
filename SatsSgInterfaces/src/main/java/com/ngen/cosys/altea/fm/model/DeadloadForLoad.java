/**
 * DeadloadForLoad.java
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
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * This class is used for agent details contains first and last name
 * 
 * @author NIIT Technologies Ltd
 *
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@JsonIgnoreProperties(value = { "eventSource", "tenant", "flightId", "flightSegmentId", "deadloadId" })
@JsonPropertyOrder(value = { "deadloadType", "deadloadNbr", "dldReferences", "connectionTime", "deadloadOriginDest",
      "deadloadWeightAndPieces", "deadloadIndicators", "deadloadDescription" })
@JacksonXmlRootElement(localName = "deadloadForLoad")
public class DeadloadForLoad {

   /**
    * Internal reference
    */
   private String eventSource;
   private String tenant;
   private BigInteger flightId;
   private BigInteger flightSegmentId;
   private BigInteger deadloadId;
   
   /**
    * Load Detail information - (M - 1 time)
    */
   @JacksonXmlProperty(localName = "deadloadType")
   private DeadloadType deadloadType;
   
   /**
    * Load Nbr - (M - 1 time)
    */
   @JacksonXmlProperty(localName = "deadloadNbr")
   private DeadloadNbr deadloadNbr;
   
   /**
    * Reference information - (M - 1 time)
    */
   @JacksonXmlProperty(localName = "dldReferences")
   private DLDReference dldReference;
   
   /**
    * Repetition - (C - 1 time)
    */
   @JacksonXmlProperty(localName = "connectionTime")
   private ConnectionTime connectionTime;
   
   /**
    * Repetition - (M - 1 time)
    */
   @JacksonXmlProperty(localName = "deadloadOriginDest")
   private OriginDestination deadloadOriginDest;
   
   /**
    * Repetition - (C - 1 time)
    */
   @JacksonXmlProperty(localName = "commercialLinkID")
   private CommercialLinkID commercialLinkID;
   
   /**
    * Deadload Weight and Pieces - (M - 3 times)
    */
   @JacksonXmlElementWrapper(useWrapping = false)
   @JacksonXmlProperty(localName = "deadloadWeightAndPieces")
   private List<DeadloadWeightAndPieces> deadloadWeightAndPieces;
   
   /**
    * Repetition - (C - 1 time)
    */
   @JacksonXmlProperty(localName = "deadloadFlights")
   private DeadloadFlight deadloadFlight;
   
   /**
    * Repetition - (C - 1 time)
    */
   @JacksonXmlProperty(localName = "deadloadIndicators")
   private DeadloadIndicator deadloadIndicator;
   
   /**
    * Repetition - (C - 1 time)
    */
   @JacksonXmlProperty(localName = "deadloadDescription")
   private FreeTextQualifier deadloadDescription;
   
   /**
    * Associated Special Loads and Dangerous Goods - (C - 200 times)
    */
   @JacksonXmlElementWrapper(useWrapping = false)
   @JacksonXmlProperty(localName = "dgslForDeadload")
   private List<DGSL> dgslForDeadload;
   
   /**
    * Associated all packed in one items - (C - 200 times)
    */
   @JacksonXmlElementWrapper(useWrapping = false)
   @JacksonXmlProperty(localName = "allPackedInOneForDeadload")
   private List<AllPackedInOne> allPackedInOneForDeadload;
   
   /**
    * Associated overpack items - (C - 200 times)
    */
   @JsonInclude(Include.NON_NULL)
   @JacksonXmlElementWrapper(useWrapping = false)
   @JacksonXmlProperty(localName = "overpackForDeadload")
   private List<OverpackForDeadload> overpackForDeadload;
   
}
