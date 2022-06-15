/**
 * AllPackedInOneNbr.java
 * 
 * Copyright <PRE><IMG SRC = XX></IMG></PRE>
 *
 * Version      Date         Author      Reason
 * 1.0          22 JAN, 2019   NIIT      -
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
 * This class reference used in AllPackedInOneIdentifier
 * 
 * @author NIIT Technologies Ltd
 *
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@JsonIgnoreProperties(value = { "allPackedInOneId" })
public class AllPackedInOneNbr {

   /**
    * Internal Reference
    */
   private BigInteger allPackedInOneId;
   
   /**
    * Repetition 1 time (M - 1 time) 
    */
   @JacksonXmlProperty(localName = "referenceDetails")
   private ReferenceDetail referenceDetail;
   
}
