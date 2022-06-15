/**
 * NetWeightCentre.java
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
 * This class reference used in Load Info
 * 
 * @author NIIT Technologies Ltd
 *
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@JsonPropertyOrder(value = { "netWeightCentrePos", "weight" })
public class NetWeightCentre {
   
   /**
    * Net Weight Centre Position - (M - 1 time)
    */
   @JacksonXmlProperty(localName = "netWeightCentrePos")
   private Measurement netWeightCentrePosition;
   
   /**
    * Weight - (M - 1 time)
    */
   @JacksonXmlProperty(localName = "weight")
   private Weight weight;
   
}
