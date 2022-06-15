/**
 * Error.java
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
@JsonPropertyOrder(value = { "errorOrWarningCodeDetails", "errorWarningDescription" })
public class Error {
   
   /**
    * Details of Error/Warning code - (M - 1 time)
    */
   @JacksonXmlProperty(localName = "errorOrWarningCodeDetails")
   private ErrorOrWarningCodeDetail errorOrWarningCodeDetail;
   
   /**
    * Description of Error/Warning - (C - 1 time)
    */
   @JacksonXmlProperty(localName = "errorWarningDescription")
   private ErrorWarningDescription errorWarningDescription;
   
}
