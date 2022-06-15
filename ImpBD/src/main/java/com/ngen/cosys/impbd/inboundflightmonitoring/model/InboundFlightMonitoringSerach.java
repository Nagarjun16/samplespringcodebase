package com.ngen.cosys.impbd.inboundflightmonitoring.model;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.ngen.cosys.JsonSerializer.LocalDateSerializer;
import com.ngen.cosys.framework.model.BaseBO;

import groovy.transform.ToString;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@ApiModel
@Getter
@Setter
@ToString
@NoArgsConstructor
public class InboundFlightMonitoringSerach extends BaseBO {

   /**
    * Default serialVersionUID.
    */
   private static final long serialVersionUID = 1L;
   private BigInteger id;
   /**
    * Terminal by default
    */
   private String terminals;
   /**
    * dateFrom
    */
   @JsonSerialize(using = LocalDateTimeSerializer.class)
   private LocalDateTime fromDate;
   /**
    * dateAndTimeTo
    */
   @JsonSerialize(using = LocalDateTimeSerializer.class)
   private LocalDateTime toDate;

   /**
    * Carrier Group
    */
   private String carrierGroup;
   /**
    * Air Craft Type
    */
   private String acType;
   /**
    * carrier
    */
   private String carrier;
   /**
    * flight key
    */
   private String flight;
   /**
    * date
    */
   @JsonSerialize(using = LocalDateSerializer.class)
   private LocalDate date;
}