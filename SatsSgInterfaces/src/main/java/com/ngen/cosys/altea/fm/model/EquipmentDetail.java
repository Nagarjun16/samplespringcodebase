/**
 * EquipmentDetail.java
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
 * This class is holds of aircraft type and registration number as Equipment details
 * 
 * @author NIIT Technologies Ltd
 *
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@JsonPropertyOrder(value = { "aircraftType", "registrationNumber" })
public class EquipmentDetail {

   /**
    * Aircraft type length upto 8 chars (M* - an .. 8)
    */
   @JacksonXmlProperty(localName = "aircraftType")
   private String aircraftType;
   
   /**
    * Char length is 10 (C - an .. 10)
    */
   @JacksonXmlProperty(localName = "registrationNumber")
   private String registrationNumber;
   
}
