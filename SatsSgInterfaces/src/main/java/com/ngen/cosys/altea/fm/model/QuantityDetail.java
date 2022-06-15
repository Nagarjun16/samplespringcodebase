/**
 * QuantityDetail.java
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
 * This class reference used in Weight and Pieces 
 * 
 * @author NIIT Technologies Ltd
 *
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@JsonIgnoreProperties(value = { "flightId", "quantityId" })
@JsonPropertyOrder(value = { "qualifier", "value", "numberOfUnit", "unitQualifier", "unit" })
public class QuantityDetail {

   /**
    * Internal Reference
    */
   private BigInteger flightId;
   private BigInteger quantityId;
   
   /**
    * Quantity qualifier (M - an .. 3)
    */
   @JsonInclude(Include.NON_EMPTY)
   @JacksonXmlProperty(localName = "qualifier")
   private String qualifier;
   
   /**
    * Numeric value holds 15 digit (M - n .. 15)
    */
   @JsonInclude(Include.NON_NULL)
   @JacksonXmlProperty(localName = "value")
   private BigInteger value;
   
   /**
    * Only for Pieces and holds 4 digit (M* - n .. 4)
    */
   @JsonInclude(Include.NON_NULL)
   @JacksonXmlProperty(localName = "numberOfUnit")
   private Integer numberOfUnit;
   
   /**
    * Only for Pieces and chars length is 3 (M* - an .. 3)
    */
   @JsonInclude(Include.NON_EMPTY)
   @JacksonXmlProperty(localName = "unitQualifier")
   private String unitQualifier;

   /**
    * Measure of unit qualifier - Chars length is 3 (M* - an .. 3)
    */
   @JsonInclude(Include.NON_EMPTY)
   @JacksonXmlProperty(localName = "unit")
   private String unit;
   
}
