/**
 * LoadInfo.java
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
 * This class reference used in DCS FM Update Cargo Figure
 * 
 * @author NIIT Technologies Ltd
 *
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@JsonIgnoreProperties(value = { "eventSource", "tenant", "flightId", "flightSegmentId", "dlsULDTrolleyId" })
@JsonPropertyOrder(value = { "loadTypeAndData", "barrowID", "loadTareWeight", "loadDescription", "bIGReferenceDetails",
      "loadIndicators", "measurements", "loadOriginDest", "linkedULDId", "netWeightCentre", "loadSeparator",
      "deadloadForLoad" })
public class LoadInfo {

   /**
    * Used for Internal Reference
    */
   private String eventSource;
   private String tenant;
   private BigInteger flightId;
   private BigInteger flightSegmentId;
   private BigInteger dlsULDTrolleyId;

   /**
    * Load Reference type - (M - 1 time)
    */
   @JacksonXmlProperty(localName = "loadTypeAndData")
   private LoadTypeAndData loadTypeAndData;
   
   /**
    * Barrow ID if Load Item is part of Barrow - (C - 1 time)
    */
   @JacksonXmlProperty(localName = "barrowID")
   private DescriptionItem barrowID;
   
   /**
    * ULD Weights - (C - 1 time)
    */
   @JacksonXmlProperty(localName = "loadTareWeight")
   private LoadTareWeight loadTareWeight;
   
   /**
    * Load Volume - (C - 1 time)
    */
   @JacksonXmlProperty(localName = "loadVolume")
   private LoadVolume loadVolume;
   
   /**
    * Interactive Free Text about the load - (C - 1 time)
    */
   @JacksonXmlProperty(localName = "loadDescription")
   private FreeTextQualifier loadDescription;
   
   /**
    * BRN/BPT - (C - 2 times)
    */
   @JacksonXmlElementWrapper(useWrapping = false)
   @JacksonXmlProperty(localName = "bIGReferenceDetails")
   private List<BigReferenceDetail> bigReferenceDetail;
   
   /**
    * FPA/RIG/ROB/DGA - (C - 4 times)
    */
   @JacksonXmlElementWrapper(useWrapping = false)
   @JacksonXmlProperty(localName = "loadIndicators")
   private List<Indicator> loadIndicator;
   
   /**
    * Measurements - (C - 9 times)
    */
   @JacksonXmlElementWrapper(useWrapping = false)
   @JacksonXmlProperty(localName = "measurements")
   private List<Measurement> measurement;
   
   /**
    * The actual Origin & Destination of Load - (M - 1 time)
    */
   @JacksonXmlProperty(localName = "loadOriginDest")
   private OriginDestination loadOriginDestination;
   
   /**
    * ULD Warehouse Location - (C - 1)
    */
   @JacksonXmlProperty(localName = "uldWarehouseLocation")
   private ULDWarehouseLocation uldWarehouseLocation;
   
   /**
    * Linked ULD Series - (C - 4 times)
    */
   @JacksonXmlElementWrapper(useWrapping = false)
   @JacksonXmlProperty(localName = "linkedULDId")
   private List<DescriptionItem> linkedULDId;
   
   /**
    * Floating Pallet & Net Weight Centre information - (C - 200 times)
    */
   @JacksonXmlElementWrapper(useWrapping = false)
   @JacksonXmlProperty(localName = "netWeightCentre")
   private List<NetWeightCentre> netWeightCentre;
   
   /**
    * Dummy Segment - (M - 1 time)
    */
   @JacksonXmlProperty(localName = "loadSeparator")
   private LoadSeparator loadSeparator;
   
   /**
    * Deadload items associated with Load Item - (C - 200 times)
    */
   @JacksonXmlElementWrapper(useWrapping = false)
   @JacksonXmlProperty(localName = "deadloadForLoad")
   private List<DeadloadForLoad> deadloadForLoad;
   
}
