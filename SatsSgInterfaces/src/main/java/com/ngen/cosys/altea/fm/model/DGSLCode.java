/**
 * DGSLCode.java
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
 * This class is used for DGSL Code
 * 
 * @author NIIT Technologies Ltd
 *
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@JsonIgnoreProperties(value = { "notocId" })
@JsonPropertyOrder(value = { "emergencyRespCode", "airwayBillNbr", "uNOrIDNbr", "description", "sldgPackingGroup",
      "hazardID", "hazardReference", "technicalDescription" })
public class DGSLCode {

   /**
    * Internal Reference
    */
   private BigInteger notocId;
   
   /**
    * Handling Instructions - Optional (C - an .. 3)
    */
   @JacksonXmlProperty(localName = "emergencyRespCode")
   private String emergencyRespCode;
   
   /**
    * Document/Message number - Optional (C - an .. 12)
    */
   @JacksonXmlProperty(localName = "airwayBillNbr")
   private String airwayBillNbr;
   
   /**
    * UNDG number - Optional (C - an4)
    */
   @JacksonXmlProperty(localName = "uNOrIDNbr")
   private String unOrIdNbr;
   
   /**
    * Long Free Text - Optional (C - an .. 256)
    */
   @JacksonXmlProperty(localName = "description")
   private String description;
   
   /**
    * Packing Group - Optional (C - an .. 3)
    */
   @JacksonXmlProperty(localName = "sldgPackingGroup")
   private String sldgPackingGroup;
   
   /**
    * Hazard Identification - Optional (Repetition - 1 time)
    */
   @JacksonXmlProperty(localName = "hazardID")
   private HazardID hazardID;
   
   /**
    * Hazard Reference - Optional (Repetition - 1 time)
    */
   @JacksonXmlProperty(localName = "hazardReference")
   private HazardReference hazardReference;
   
   /**
    * Long Free Text - Optional (C - an .. 256)
    */
   @JacksonXmlProperty(localName = "technicalDescription")
   private String technicalDescription;
   
}
