package com.ngen.cosys.shipment.information.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ngen.cosys.JsonSerializer.LocalDateTimeSerializer;
import com.ngen.cosys.framework.model.BaseBO;
import com.ngen.cosys.shipment.awb.model.ShipmentRemarksModel;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ShipmentAcceptanceModel extends BaseBO {

   /**
    * Default generated serial version id
    */
   private static final long serialVersionUID = 1L;

   private String eAcceptance;
   
   @JsonSerialize(using = LocalDateTimeSerializer.class)
   private LocalDateTime serviceCreationDateTime;
   
   private BigInteger acceptedPiece;
   private BigDecimal acceptedWeight;
   private String acceptanceType;
   private String acceptanceStatus;
   private String charges;
   private String checkList;
   private String requestedTemperatureRange;
   private String truckType;
   private String shipmentNumber;
   private LocalDate shipmentDate;
   private String acceptanceSreeningCompleted;
   private String acceptanceRemarks;
   private String readyForLoad;
   private String shc;

   private List<ShipmentInventoryModel> inventoryDetails;
   private List<ShipmentDamageInfoModel> damageDetails;
   private List<ShipmentRemarksModel> remarks;

   private String declaredPieceWeight;
   private int ereceiptTriggerCount;
   private String carrierCode;
   private BigInteger documentInformationId;
}