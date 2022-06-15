/**
 * ErrorWarningDescription.java
 * 
 * Copyright <PRE><IMG SRC = XX></IMG></PRE>
 *
 * Version      Date         Author      Reason
 * 1.0          22 JAN, 2019   NIIT      -
 */
package com.ngen.cosys.altea.fm.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * This class is used for Error Warning description
 * 
 * @author NIIT Technologies Ltd
 *
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class ErrorWarningDescription {

   /**
    * Free Text Information - (M - 1 time)
    */
   @JacksonXmlProperty
   private FreeTextDetail freeTextDetail;
   
   /**
    * Long Free Text - Repetition 99 times (M - an .. 199)
    */
   @JacksonXmlElementWrapper(useWrapping = false)
   @JacksonXmlProperty(localName = "freeText")
   private String freeText;
   
}
