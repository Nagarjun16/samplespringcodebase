/**
 * HazardReference.java
 * 
 * Copyright <PRE><IMG SRC = XX></IMG></PRE>
 *
 * Version      Date         Author      Reason
 * 1.0          22 JAN, 2019   NIIT      -
 */
package com.ngen.cosys.altea.fm.model;

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
@JsonPropertyOrder(value = { "sldgIATACode", "radioactiveCategory", "subsidiaryRiskGrp" })
public class HazardReference {

   /**
    * Hazard code identification - Mandatory (M - an .. 3)
    */
   @JacksonXmlProperty(localName = "sldgIATACode")
   private String sldgIATACode;
   
   /**
    * Hazard code version number - Optional (C - an .. 3)
    */
   @JacksonXmlProperty(localName = "radioactiveCategory")
   private String radioactiveCategory;
   
   /**
    * Hazard substance/item/page number - Optional (C - an .. 3)
    */
   @JacksonXmlProperty(localName = "subsidiaryRiskGrp")
   private String subsidiaryRiskGroup;
   
}
