/**
 * ValueRange.java
 * 
 * Copyright <PRE><IMG SRC = XX></IMG></PRE>
 *
 * Version      Date         Author      Reason
 * 1.0          22 JAN, 2019   NIIT      -
 */
package com.ngen.cosys.altea.fm.model;

import java.math.BigInteger;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * This class reference used in DCSFM Update Cargo Figure
 * 
 * @author NIIT Technologies Ltd
 *
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@JsonPropertyOrder(value = { "unit", "value" })
public class ValueRange {

   /**
    * Measure unit qualifier - (M - an .. 3)
    */
   @JacksonXmlProperty(localName = "unit")
   private String unit;
   
   /**
    * Measurement value - (M* - n .. 18)
    */
   @JacksonXmlProperty(localName = "value")
   private BigInteger value;
   
}
