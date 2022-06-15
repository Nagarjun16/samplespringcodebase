package com.ngen.cosys.impbd.damage.model;

import java.math.BigInteger;
import java.time.LocalDate;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ngen.cosys.JsonSerializer.LocalDateSerializer;
import com.ngen.cosys.model.FlightModel;

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

public class DamageSearchModel extends FlightModel{
   
   /**
    * The default serialVersionUID.
    */
   private static final long serialVersionUID = 1L;

   /**
    * FlightNumber  of the flight for damage report
    */
   private String flight;
   /**
    * Flight ID
    */
   private BigInteger flightId;
   
   /**
    * Flight Origin Date  for damage report
    */
   @JsonSerialize(using = LocalDateSerializer.class)
   private LocalDate  flightDate;
   
   private String weatherCondition;
   
   private String preparedBy;


}
