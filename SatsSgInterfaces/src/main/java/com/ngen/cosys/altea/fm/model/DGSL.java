/**
 * DGSL.java
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

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * This class is used for DGSL APIO & OVP
 * 
 * @author NIIT Technologies Ltd
 *
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@JsonIgnoreProperties(value = { "eventSource", "tenant", "flightId", "flightSegmentId", "dgslId", "notocId" })
@JsonPropertyOrder(value = { "dgslCode", "dgslNbr", "sldgWeightAndHeightData", "dgslPieces", "dgslIndicators",
      "temperatureVentilationSetting", "supplementaryInfoAndQuantityTI" })
public class DGSL {

   /**
    * Internal reference
    */
   private String eventSource;
   private String tenant;
   private BigInteger flightId;
   private BigInteger flightSegmentId;
   private BigInteger dgslId;
   private BigInteger notocId;
   
   /**
    * Special Load Details - (M - 1 time)
    */
   @JacksonXmlProperty(localName = "dgslCode")
   private DGSLCode dgslCode;
   
   /**
    * Reference Information (M - 1 time)
    */
   @JacksonXmlProperty(localName = "dgslNbr")
   private DGSLNbr dgslNbr;
   
   /**
    * Quantity - (C - 1 time)
    */
   @JacksonXmlProperty(localName = "sldgWeightAndHeightData")
   private WeightAndHeight sldgWeightAndHeightData;
   
   /**
    * Number of units - (C - 1 time)
    */
   @JacksonXmlProperty(localName = "dgslPieces")
   private Pieces dgslPieces;
   
   /**
    * Status Details - (C - 1 time)
    */
   @JsonInclude(Include.NON_NULL) // Optional
   @JacksonXmlProperty(localName = "dgslIndicators")
   private DGSLIndicator dgslIndicators;
   
   /**
    * Ventilation Setting - (C - 2 time)
    */
   @JsonInclude(Include.NON_EMPTY) // Optional
   @JacksonXmlElementWrapper(useWrapping = false)
   @JacksonXmlProperty(localName = "temperatureVentilationSetting")
   private List<DescriptionItem> temperatureVentilationSetting;
   
   /**
    * Interactive Free Text - (C - 2 time)
    */
   @JacksonXmlElementWrapper(useWrapping = false)
   @JacksonXmlProperty(localName = "supplementaryInfoAndQuantityTI")
   private List<FreeTextQualifier> supplementaryInfoAndQuantityTI;
   
}
