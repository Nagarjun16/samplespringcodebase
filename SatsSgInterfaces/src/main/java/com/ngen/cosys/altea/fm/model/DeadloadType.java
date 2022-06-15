/**
 * DeadloadType.java
 * 
 * Copyright <PRE><IMG SRC = XX></IMG></PRE>
 *
 * Version      Date         Author      Reason
 * 1.0          22 JAN, 2019   NIIT      -
 */
package com.ngen.cosys.altea.fm.model;

import java.math.BigInteger;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * This class is used in DeadloadForLoad
 * 
 * @author NIIT Technologies Ltd
 *
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@JsonIgnoreProperties(value = { "deadloadTypeId" })
public class DeadloadType {

   /**
    * Internal Reference
    */
   private BigInteger deadloadTypeId;
   
   /**
    * Cargo Details (M* - 1 time)
    */
   @JacksonXmlProperty(localName = "cargoDetails")
   private CargoDetail cargoDetail;
   
}
