/**
 * CargoAgentName.java
 * 
 * Copyright <PRE><IMG SRC = XX></IMG></PRE>
 *
 * Version      Date         Author      Reason
 * 1.0          22 JAN, 2019   NIIT      -
 */
package com.ngen.cosys.altea.fm.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * This class is used for agent details contains first and last name
 * 
 * @author NIIT Technologies Ltd
 *
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@JsonIgnoreProperties(value = { "eventSource", "tenant" })
@JsonPropertyOrder(value = { "paxDetails", "otherPaxDetails" })
@JacksonXmlRootElement(localName = "cargoAgentName")
public class CargoAgentName {

   /**
    * Used for Internal Reference
    */
   private String eventSource;
   private String tenant;

   @JacksonXmlProperty(localName = "paxDetails")
   private PaxDetail paxDetail;

   @JacksonXmlProperty(localName = "otherPaxDetails")
   private OtherPaxDetail otherPaxDetail;

}
