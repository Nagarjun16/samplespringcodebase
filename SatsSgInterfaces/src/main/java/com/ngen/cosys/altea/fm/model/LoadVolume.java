/**
 * LoadVolume.java
 * 
 * Copyright <PRE><IMG SRC = XX></IMG></PRE>
 *
 * Version      Date         Author      Reason
 * 1.0          23 JUL, 2019   NIIT      -
 */
package com.ngen.cosys.altea.fm.model;

import java.math.BigInteger;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * This class reference used in Load Volume
 * 
 * @author NIIT Technologies Ltd
 *
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@JsonIgnoreProperties(value = { "flightId", "flightSegmentId", "loadReferenceId" })
public class LoadVolume {

   /**
    * Internal reference
    */
   private BigInteger flightId;
   private BigInteger flightSegmentId;
   private BigInteger loadReferenceId;

   /**
    * Load Details - C - 1
    */
   @JacksonXmlProperty(localName = "loadDetails")
   private LoadDetail loadDetail;
   
}
