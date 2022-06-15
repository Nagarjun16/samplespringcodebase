package com.ngen.cosys.impbd.shipment.irregularity.model;

import java.math.BigDecimal;
import java.math.BigInteger;

import com.ngen.cosys.audit.NgenAudit;
import com.ngen.cosys.audit.NgenAuditEntityType;
import com.ngen.cosys.audit.NgenAuditEventRepository;
import com.ngen.cosys.audit.NgenAuditEventType;
import com.ngen.cosys.audit.NgenAuditField;
import com.ngen.cosys.model.ShipmentModel;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@ApiModel
@Setter
@Getter
@NoArgsConstructor
@NgenAudit(entityFieldName = "shipmentNumber", entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.ADD_MAINTAIN_SHIPMENT_IRREGULARITY, repository = NgenAuditEventRepository.AWB)
public class ShipmentIrregularityModel extends ShipmentModel {

   /**
    * System generated version id
    */
   private static final long serialVersionUID = 5520578621981045403L;

   private BigInteger flightId;
   
   private BigInteger flightSegmentId;

   @NgenAuditField(fieldName = "Irregularity Type")
   private String cargoIrregularityCode;

   @NgenAuditField(fieldName = "Remark")
   private String irregularityRemarks;

   private String discrepancyType;

   private BigInteger transactionSequenceNumber;

   // Shipment Piece/Weight Information Deriving Logic
   private String shipmentType;
   private BigInteger documentedPieces = BigInteger.ZERO;
   private BigInteger foundPieces = BigInteger.ZERO;

   private Boolean partShipment = Boolean.FALSE;

   private BigInteger damagedPieces;
   
   private BigInteger hawbPieces;
   
   private BigDecimal hawbWeight;
   
   private BigInteger manifestPieces;
   private BigInteger breakDownPieces;

}