package com.ngen.cosys.impbd.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import javax.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ngen.cosys.JsonSerializer.LocalDateSerializer;
import com.ngen.cosys.JsonSerializer.LocalDateTimeSerializer;
import com.ngen.cosys.JsonSerializer.LocalTimeSerializer;
import com.ngen.cosys.framework.model.BaseBO;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * This is the Base class for Change of Customer Code.
 * 
 * @author NIIT Technologies Ltd
 * @version 1.0
 */
@ApiModel
@Getter
@Setter
@NoArgsConstructor
@Validated
public class SearchInbound extends BaseBO {

   /**
    * The default serialVersionUID.
    */
   private static final long serialVersionUID = 1L;

   /**
    * Working Shift Time
    */
   @NotNull
   @JsonSerialize(using = LocalTimeSerializer.class)
   private LocalTime startsAt;

   @NotNull
   @JsonSerialize(using = LocalTimeSerializer.class)
   private LocalTime endsAt;

   private String workingShift;

   /**
    * Working date.
    */
   @JsonSerialize(using = LocalDateSerializer.class)
   @NotNull
   private LocalDate date;

   @JsonSerialize(using = LocalDateTimeSerializer.class)
   private LocalDateTime shiftStartTime;

   @JsonSerialize(using = LocalDateTimeSerializer.class)
   private LocalDateTime shiftEndTime;

   /**
    * Agent name.
    */
   private String agent;

   private String flightID;
   private int comTeamId;
   
   private String flightKey;

}