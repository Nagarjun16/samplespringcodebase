/**
 * SourceSystemInfo.java
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
@JsonIgnoreProperties(value = { "eventSource", "tenant", "flightId" })
public class SourceSystemInfo {

   /**
    * Used for Internal Reference
    */
   private String eventSource;
   private String tenant;
   private BigInteger flightId;
   
   /**
    * External Cargo System Detail - (M* - 3 times)
    */
   @JacksonXmlElementWrapper(localName = "referenceDetails")
   private List<ReferenceDetail> referenceDetail;
   
}
