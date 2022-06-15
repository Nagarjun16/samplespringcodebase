/**
 * DescriptionItem.java
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
 * This class is used for DGSL APIO & OVP
 * 
 * @author NIIT Technologies Ltd
 *
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@JsonPropertyOrder(value = { "type", "descriptionDetails" })
public class DescriptionItem {

   /**
    * Item Description type (M* - an .. 3)
    */
   @JacksonXmlProperty(localName = "type")
   private String type;
   
   /**
    * Item Description (M* - 1 time)
    */
   @JacksonXmlProperty(localName = "descriptionDetails")
   private DescriptionDetail descriptionDetails;
   
}
