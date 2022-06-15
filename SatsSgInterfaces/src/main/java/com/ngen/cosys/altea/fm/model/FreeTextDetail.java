/**
 * FreeTextDetail.java
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
 * This class reference used as part Error response
 * 
 * @author NIIT Technologies Ltd
 *
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@JsonPropertyOrder(value = { "textSubjectQualifier", "language", "source", "encoding" })
public class FreeTextDetail {

   /**
    * Text subject qualifier - (M - an .. 3)
    */
   @JacksonXmlProperty(localName = "textSubjectQualifier")
   private String textSubjectQualifier;
   
   /**
    * Language - (C - an .. 3)
    */
   @JacksonXmlProperty(localName = "language")
   private String language;
   
   /**
    * Source - (M - an .. 3)
    */
   @JacksonXmlProperty(localName = "source")
   private String source;
   
   /**
    * Encoding - (M - an .. 3)
    */
   @JacksonXmlProperty(localName = "encoding")
   private String encoding;
   
}
