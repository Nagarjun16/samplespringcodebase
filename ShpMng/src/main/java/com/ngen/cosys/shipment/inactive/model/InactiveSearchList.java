package com.ngen.cosys.shipment.inactive.model;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ngen.cosys.JsonSerializer.LocalDateSerializer;
import com.ngen.cosys.JsonSerializer.LocalDateTimeSerializer;
import com.ngen.cosys.framework.model.BaseBO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class InactiveSearchList extends BaseBO {

   /**
    * System generated serial version id
    */
   private static final long serialVersionUID = 1L;

   private String awbNumber;

   @JsonSerialize(using = LocalDateSerializer.class)
   private LocalDate shipmentDate;
   
   private String shipmentType;

   private String origin;

   private String destination;

   private String shipper;

   private String consignee;
   
   private String appointedAgentCode;

   private String nog;

   private String specialHandlingCode;

   private String awbPices;

   private String awbWaight;

   private String awbPicesWaight;

   private String inventaryPices;

   private String inventoryWaight;

   private String inventoryPiecesWaight;

   private String freightInFlight;

   @JsonSerialize(using = LocalDateTimeSerializer.class)
   private LocalDateTime freightInDate;

   private BigInteger shipmentId;

   private Boolean select = Boolean.FALSE;
   
   private String carrier;
   private String customerType;

}
