package com.ngen.cosys.shipment.revive.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ngen.cosys.JsonSerializer.LocalDateSerializer;
import com.ngen.cosys.annotation.NgenCosysAppAnnotation;
import com.ngen.cosys.audit.NgenAudit;
import com.ngen.cosys.audit.NgenAuditEntityType;
import com.ngen.cosys.audit.NgenAuditEventRepository;
import com.ngen.cosys.audit.NgenAuditEventType;
import com.ngen.cosys.audit.NgenAuditField;
import com.ngen.cosys.audit.NgenAuditFieldName.NgenAuditFieldNameType;
import com.ngen.cosys.framework.model.BaseBO;
import com.ngen.cosys.shipment.model.SHC;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@NgenCosysAppAnnotation
@NgenAudit(entityFieldName ="shipmentNumber", entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.REVIVE_SHIPMENT, repository = NgenAuditEventRepository.AWB)
public class ReviveShipmentModel extends BaseBO {

   private static final long serialVersionUID = 1L;

   private BigInteger referenceId;
   
   @NgenAuditField(fieldName = NgenAuditFieldNameType.REVIVE_PIECES)
   private BigInteger freightOutPieces;
   
   private BigInteger revivePieces;
   private BigInteger shipmentId;
   private BigInteger freightOutId;
   private BigInteger flightId;
   private BigInteger inboundFlightId;
   private BigInteger shipmentInventoryId;
   private String referenceDetails;

   private BigDecimal freightOutWeight;
   private BigDecimal reviveWeight;
   @NgenAuditField(fieldName = NgenAuditFieldNameType.SHIPMENT_LOCATION)
   private String shipmentLocation;
   @NgenAuditField(fieldName = NgenAuditFieldNameType.WAREHOUSE_LOCAITON)
   private String warehouseLocation;
   private String trmNumber;
   @NgenAuditField(fieldName = NgenAuditFieldNameType.SHIPMENTNUMBER)
   private String shipmentNumber;
   private String reason;
   private String shipmentType;
   @NgenAuditField(fieldName = NgenAuditFieldNameType.REASON)
   private String reasonForRevive;
   private String partSuffix;

   @JsonSerialize(using = LocalDateSerializer.class)
   @NgenAuditField(fieldName = NgenAuditFieldNameType.SHIPMENTDATE)
   private LocalDate shipmentDate;
   private String origin;
   private String previousShipmentLocation;
   private String previousWarehouseLocation;
   private List<SHC> inventorySHCSList;
}