/**
 * ErrorDetail.java
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
 * This class is used for amadeus error response/reply for updatecargo
 * 
 * @author NIIT Technologies Ltd
 *
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@JsonPropertyOrder(value = { "errorCode", "errorCategory", "errorCodeOwner" })
public class ErrorDetail {

   /**
    * Application error code - (M - an .. 5)
    */
   @JacksonXmlProperty(localName = "errorCode")
   private String errorCode;
   
   /**
    * Code list qualifier - (C - an .. 3)
    */
   @JacksonXmlProperty(localName = "errorCategory")
   private String errorCategory;
   
   /**
    * Code list responsible agency - (M* - an .. 3)
    */
   @JacksonXmlProperty(localName = "errorCodeOwner")
   private String errorCodeOwner;
   
}
