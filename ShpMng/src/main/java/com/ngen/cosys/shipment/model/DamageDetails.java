package com.ngen.cosys.shipment.model;

import java.math.BigInteger;
import java.time.LocalDateTime;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ngen.cosys.JsonSerializer.LocalDateTimeSerializer;
import com.ngen.cosys.framework.model.BaseBO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DamageDetails extends BaseBO {

   /**
    * System generated serial version id
    */
   private static final long serialVersionUID = 1L;
   private String natureOfDamage;
   private BigInteger damagedPieces;
   private String severity;
   private String occurrence;
   private String remarks;
   private String flightKey;

   @JsonSerialize(using = LocalDateTimeSerializer.class)
   private LocalDateTime flightDate;

}