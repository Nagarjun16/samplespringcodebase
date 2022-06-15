/**
 * ErrorOrWarningCodeDetail.java
 * 
 * Copyright <PRE><IMG SRC = XX></IMG></PRE>
 *
 * Version      Date         Author      Reason
 * 1.0          22 JAN, 2019   NIIT      -
 */
package com.ngen.cosys.altea.fm.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * This class is used for Error Reply receiving from amadeus
 * 
 * @author NIIT Technologies Ltd
 *
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class ErrorOrWarningCodeDetail {

   /**
    * Application error detail - (M - 1 time)
    */
   @JacksonXmlProperty(localName = "errorDetails")
   private ErrorDetail errorDetail;
   
}
