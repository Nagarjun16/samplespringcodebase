package com.ngen.cosys.impbd.inboundflightmonitoring.model;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ngen.cosys.JsonSerializer.LocalDateSerializer;
import com.ngen.cosys.JsonSerializer.LocalDateTimeSerializer;
import com.ngen.cosys.JsonSerializer.LocalTimeSerializer;
import com.ngen.cosys.framework.model.BaseBO;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ApiModel
@Getter
@Setter
@ToString
@NoArgsConstructor
public class InboundFlightMonitoringModel extends BaseBO {

   /**
    * System generated serial version id
    */
   private static final long serialVersionUID = 1L;

   private BigInteger id;

   /**
    * flight key
    */

   private String flight;

   /**
    * date
    */

   @JsonSerialize(using = LocalDateSerializer.class)
   private LocalDate date;

   /**
    * lastBoardPoint
    */

   private String lastBoardPoint;

   /**
    * STA Capture time from Oprative Flight
    */

   @JsonSerialize(using = LocalTimeSerializer.class)
   private LocalTime sta;

   /**
    * ETA Capture time from Oprative Flight
    */

   @JsonSerialize(using = LocalTimeSerializer.class)
   private LocalTime eta;

   /**
    * ATA Capture time from Oprative Flight
    */

   @JsonSerialize(using = LocalTimeSerializer.class)
   private LocalTime ata;

   /**
    * Aircraft Type Code
    */
   private String acType;

   /**
    * Aircraft Registration Code
    */

   private String acRegistration;

   /**
    * rampStartDateTime
    */

   @JsonSerialize(using = LocalDateTimeSerializer.class)
   private LocalDateTime rampStartDateTime;

   /**
    * rampCompleteDateTime
    */

   @JsonSerialize(using = LocalDateTimeSerializer.class)
   private LocalDateTime rampCompleteDateTime;

   /**
    * documentStartDateTime
    */

   @JsonSerialize(using = LocalDateTimeSerializer.class)
   private LocalDateTime documentStartDateTime;

   /**
    * documentCompleteDateTime
    */

   @JsonSerialize(using = LocalDateTimeSerializer.class)
   private LocalDateTime documentCompleteDateTime;

   /**
    * documentCompleteDateTime
    */

   @JsonSerialize(using = LocalDateTimeSerializer.class)
   private LocalDateTime breakdownStartDateTime;

   /**
    * breakdownCompleteDateTime
    */

   @JsonSerialize(using = LocalDateTimeSerializer.class)
   private LocalDateTime breakdownCompleteDateTime;

   /**
    * flightCompleteDateTime
    */

   @JsonSerialize(using = LocalDateTimeSerializer.class)
   private LocalDateTime flightCompleteDateTime;

   /**
    * flightCloseDateTime
    */

   @JsonSerialize(using = LocalDateTimeSerializer.class)
   private LocalDateTime flightCloseDateTime;
   
   @JsonSerialize(using = LocalDateTimeSerializer.class)
   private LocalDateTime flightDiscrepancyListSentAt;

   private String tth;

   private int ffmCount;
   
   private long flightStatus;
   
   private String nilCargo;

   private String cancelled;
}