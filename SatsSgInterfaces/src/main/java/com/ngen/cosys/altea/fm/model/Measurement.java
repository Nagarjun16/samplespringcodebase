/**
 * Measurement.java
 * 
 * Copyright <PRE><IMG SRC = XX></IMG></PRE>
 *
 * Version      Date         Author      Reason
 * 1.0          22 JAN, 2019   NIIT      -
 */
package com.ngen.cosys.altea.fm.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * This class reference used in Load Info
 * 
 * @author NIIT Technologies Ltd
 *
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Measurement {

   /**
    * Type of measurement - (M* - an .. 3)
    */
   @JacksonXmlProperty(localName = "measurementQualifier")
   private String measurementQualifier;
   
   /**
    * Value - (C - 1 time)
    */
   @JacksonXmlProperty(localName = "valueRange")
   private ValueRange valueRange;
   
}
