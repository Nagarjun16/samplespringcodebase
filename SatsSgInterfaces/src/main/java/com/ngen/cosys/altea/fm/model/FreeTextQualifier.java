/**
 * FreeTextQualifier.java
 * 
 * Copyright <PRE><IMG SRC = XX></IMG></PRE>
 *
 * Version      Date         Author      Reason
 * 1.0          22 JAN, 2019   NIIT      -
 */
package com.ngen.cosys.altea.fm.model;

import java.math.BigInteger;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@JsonIgnoreProperties(value = { "flightId", "flightSegmentId", "freeTextQualifierId" })
@JsonPropertyOrder(value = { "freeTextQualification", "freeText" })
public class FreeTextQualifier {

   /**
    * Internal reference
    */
   private BigInteger flightId;
   private BigInteger flightSegmentId;
   private BigInteger freeTextQualifierId;
   
   /**
    * Repetition 1 time (M - 1 time)
    */
   @JacksonXmlProperty(localName = "freeTextQualification")
   private TextSubjectQualifier textSubjectQualifier;
   
   /**
    * Free Text description holds 256 char length - (M* - an .. 256)
    */
   @JacksonXmlProperty(localName = "freeText")
   private String freeText;
   
}
