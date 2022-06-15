/**
 * DateTime.java
 * 
 * Copyright <PRE><IMG SRC = XX></IMG></PRE>
 *
 * Version      Date         Author      Reason
 * 1.0          22 JAN, 2019   NIIT      -
 */
package com.ngen.cosys.altea.fm.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * This class is used in Dispatch Time
 * 
 * @author NIIT Technologies Ltd
 *
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@JsonPropertyOrder(value = { "year", "month", "day", "hour", "minutes", "seconds" })
public class DateTime {

   /**
    * Year number (M* - n4)
    */
   @JacksonXmlProperty(localName = "year")
   private Integer year;
   
   /**
    * Month number - Begins to 1 (M* - n .. 2)
    */
   @JacksonXmlProperty(localName = "month")
   private Integer month;
   
   /**
    * Day number - Begins to 1 (M* - n .. 2)
    */
   @JacksonXmlProperty(localName = "day")
   private Integer day;
   
   /**
    * Hours between 0 to 23 (M* - n .. 2)
    */
   @JsonInclude(Include.NON_NULL)
   @JacksonXmlProperty(localName = "hour")
   private Integer hour;
   
   /**
    * Minutes between 0 to 59 (M* - n .. 2)
    */
   @JsonInclude(Include.NON_NULL)
   @JacksonXmlProperty(localName = "minutes")
   private Integer minutes;
   
   /**
    * Seconds between 0 to 59 (M* - n .. 2)
    */
   @JsonInclude(Include.NON_NULL)
   @JacksonXmlProperty(localName = "seconds")
   private Integer seconds;
   
}
