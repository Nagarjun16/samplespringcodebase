/**
 * ReferenceDetail.java
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
 * This class reference used in AllPackedInOneNbr
 * 
 * @author NIIT Technologies Ltd
 *
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@JsonIgnoreProperties(value = { "referenceId" })
@JsonPropertyOrder(value = { "type", "value" })
public class ReferenceDetail {

   private BigInteger referenceId;
   
   /**
    * Reference qualifier - upto 3 chars (M* - an .. 3)
    */
   @JacksonXmlProperty(localName = "type")
   private String type;
   
   /**
    * Reference Number - upto 20/35 chars (M* - an .. 20/35) 
    * 35 - <code>
    *           LoadTypeAndData
    *           BigReferenceDetail
    *      </code>
    */
   @JacksonXmlProperty(localName = "value")
   private String value;
   
}
