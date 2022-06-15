/**
 * AllPackedInOneIdentifier.java
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
 * This class reference used in AllPackedInOneForDeadload
 * 
 * @author NIIT Technologies Ltd
 *
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@JsonPropertyOrder(value = { "airwayBillNbr", "description", "radioactiveCategory" })
public class AllPackedInOneIdentifier {

   /**
    * Optional (C - an .. 12)
    */
   @JacksonXmlProperty(localName = "airwayBillNbr")
   private String airwayBillNbr;
   
   /**
    * Optional (C - an .. 256)
    */
   @JacksonXmlProperty(localName = "description")
   private String description;
   
   /**
    * Optional (C - an .. 3)
    */
   @JacksonXmlProperty(localName = "radioactiveCategory")
   private String radioactiveCategory;
   
}
