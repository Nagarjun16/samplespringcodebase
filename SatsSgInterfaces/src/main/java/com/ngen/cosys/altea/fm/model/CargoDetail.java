/**
 * CargoDetail.java
 * 
 * Copyright <PRE><IMG SRC = XX></IMG></PRE>
 *
 * Version      Date         Author      Reason
 * 1.0          22 JAN, 2019   NIIT      -
 */
package com.ngen.cosys.altea.fm.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

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
public class CargoDetail {

   /**
    * Nature of Cargo - (M* - an .. 3)
    */
   @JacksonXmlProperty(localName = "natureCargo")
   private String natureOfCargo;
   
   /**
    * Commodity subtype (C - an .. 5)
    */
   @JsonInclude(Include.NON_EMPTY)
   @JacksonXmlProperty(localName = "cargoSubtype")
   private String cargoSubtype;
   
   /**
    * Priority Item - (C - n .. 2)
    */
   @JsonInclude(Include.NON_NULL)
   @JacksonXmlProperty(localName = "priorityNumber")
   private Integer priorityNumber;
   
}
