package com.ngen.cosys.application.model;

import java.math.BigInteger;
import java.time.LocalDate;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ngen.cosys.JsonSerializer.LocalDateSerializer;
import com.ngen.cosys.framework.model.BaseBO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AutoFreightoutModel extends BaseBO {

   private static final long serialVersionUID = 1L;

   private BigInteger deliveryId;
   private BigInteger shipmentId;
   private BigInteger shipmentHouseId;
   private String deliveryOrderNo;
   private String shipmentType;
   private String hawbNumber;
   private String shipmentNumber;
   private String carrierCode;
   private String issuedToPersonnelNumber;
   private String issuedToPersonnelName;
   private String agentCode;
   private String shipmentRemarks;

   @JsonSerialize(using = LocalDateSerializer.class)
   private LocalDate shipmentDate;

}