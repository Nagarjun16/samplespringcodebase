/**
 * PhoneAndFax.java
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
 * This class is used for Phone And Fax detail
 * 
 * @author NIIT Technologies Ltd
 *
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@JsonPropertyOrder(value = { "phoneOrEmailType", "telephoneNumberDetails" })
public class PhoneAndFax {

   /**
    * Phone or Fax Info (M - an .. 4)
    */
   @JacksonXmlProperty(localName = "phoneOrEmailType")
   private String phoneOrEmailType;
   
   /**
    * Structured Telephone Number - (C - 1 time)
    */
   @JacksonXmlProperty(localName = "telephoneNumberDetails")
   private TelephoneNumberDetail telephoneNumberDetail;
   
}
