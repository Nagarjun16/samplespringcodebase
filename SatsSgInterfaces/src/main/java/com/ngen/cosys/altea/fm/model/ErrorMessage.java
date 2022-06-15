/**
 * ErrorMessage.java
 * 
 * Copyright <PRE><IMG SRC = XX></IMG></PRE>
 *
 * Version      Date         Author      Reason
 * 1.0          22 JAN, 2019   NIIT      -
 */
package com.ngen.cosys.altea.fm.model;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * This class is used for amadeus error response for Internal
 * 
 * @author NIIT Technologies Ltd
 *
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@JsonPropertyOrder(value = { "errorCode", "errorMessage", "description" })
@JacksonXmlRootElement(localName = "DCSFM_ErrorMessage")
public class ErrorMessage {

   @JacksonXmlProperty(localName = "errorCode")
   private String errorCode;
   
   @JacksonXmlProperty(localName = "errorMessage")
   private String errorMessage;
   
   @JacksonXmlProperty(localName = "description")
   private String description;
   
}
