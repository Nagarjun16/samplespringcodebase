/**
 * CommercialLinkID.java
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
 * This class is used for Commercial LinkID
 * 
 * @author NIIT Technologies Ltd
 *
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@JsonIgnoreProperties(value = { "flightId", "flightSegmentId" })
public class CommercialLinkID {

   /**
    * Internal reference
    */
   private BigInteger flightId;
   private BigInteger flightSegmentId;
   
   /**
    * Repetition 1 time (M* - 1 time) 
    */
   @JacksonXmlProperty(localName = "referenceDetails")
   private ReferenceDetail referenceDetail;
   
}
