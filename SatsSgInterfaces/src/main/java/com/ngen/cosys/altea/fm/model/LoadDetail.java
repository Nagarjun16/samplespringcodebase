/**
 * LoadDetail.java
 * 
 * Copyright <PRE><IMG SRC = XX></IMG></PRE>
 *
 * Version      Date         Author      Reason
 * 1.0          23 JUL, 2019   NIIT      -
 */
package com.ngen.cosys.altea.fm.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * This class reference used in DCS FM Update Cargo Figure
 * 
 * @author NIIT Technologies Ltd
 *
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class LoadDetail {

   /**
    * Full Empty Indicator (M* - 1)
    */
   @JacksonXmlProperty(localName = "fullEmptyIndicator")
   private String fullEmptyIndicator;
   
}
