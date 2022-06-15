/**
 * LoadTareWeight.java
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
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
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
@JsonIgnoreProperties(value = { "flightId", "loadReferenceId" })
public class LoadTareWeight {

   /**
    * Internal Reference
    */
   private BigInteger flightId;
   private BigInteger loadReferenceId;

   /**
    * Indicate ULD weights - (M - 2 times)
    * UTW/UGW - Uld Tare Weight/Uld Gross Weight
    */
   @JacksonXmlElementWrapper(useWrapping = false)
   @JacksonXmlProperty(localName = "quantityDetails")
   private List<QuantityDetail> quantityDetail;
   
}
