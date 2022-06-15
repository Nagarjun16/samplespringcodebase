package com.ngen.cosys.impbd.shipment.verification.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ngen.cosys.JsonSerializer.LocalDateSerializer;
import com.ngen.cosys.JsonSerializer.LocalDateTimeSerializer;
import com.ngen.cosys.audit.NgenAudit;
import com.ngen.cosys.audit.NgenAuditEntityType;
import com.ngen.cosys.audit.NgenAuditEventRepository;
import com.ngen.cosys.audit.NgenAuditEventType;
import com.ngen.cosys.audit.NgenAuditField;
import com.ngen.cosys.audit.NgenAuditFieldName.NgenAuditFieldNameType;
import com.ngen.cosys.framework.model.BaseBO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@NgenAudit(entityFieldName = NgenAuditFieldNameType.SHIPMENTNUMBER, entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.DOCUMENT_VERIFICATION, repository = NgenAuditEventRepository.AWB)
public class ShipmentVerificationModel extends BaseBO {

   /**
    * System generated serial version id
    */
   private static final long serialVersionUID = 2472405689488543180L;
   
   @NgenAuditField(fieldName = NgenAuditFieldNameType.FLIGHTKEY)
   private String flightNumber;
   
   @JsonSerialize(using = LocalDateSerializer.class)
   @NgenAuditField(fieldName = NgenAuditFieldNameType.FLIGHTDATE)
   private LocalDate flightDate;
   
   private BigInteger impShipmentVerificationId = BigInteger.ZERO;
   
   private BigInteger flightId = BigInteger.ZERO;
   
   @NgenAuditField(fieldName = "Shipment Id")
   private BigInteger shipmentId = BigInteger.ZERO;

   private BigInteger breakDownPieces = BigInteger.ZERO;
   
   private BigDecimal breakDownWeight = BigDecimal.ZERO;

   @NgenAuditField(fieldName = "Original AWB")
   private Boolean documentReceivedFlag = Boolean.FALSE;
   
   @NgenAuditField(fieldName = "Copy AWB")
   private Boolean photoCopyAwbFlag = Boolean.FALSE;
   
   @NgenAuditField(fieldName = "Document Pouch")
   private Boolean documentPouchReceivedFlag = Boolean.FALSE;
   
   @NgenAuditField(fieldName = "Print Barcode")
   private Boolean barcodePrintedFlag = Boolean.FALSE;
   
   @NgenAuditField(fieldName = "registered")
   private Boolean registered = Boolean.FALSE;
   
   @NgenAuditField(fieldName = "Checklist Flag")
   private Boolean checkListRequired = Boolean.FALSE;
   
   //@NgenAuditField(fieldName = "documentName")
   private String documentName = "DGLIST";

   @NgenAuditField(fieldName = "weatherCondition")
   private String weatherCondition;

   private Boolean invokedFromBreakDown = Boolean.FALSE;

   @JsonSerialize(using = LocalDateTimeSerializer.class)
   private LocalDateTime documentReceivedDateTime;

   @JsonSerialize(using = LocalDateTimeSerializer.class)
   private LocalDateTime photoCopyReceivedOn;
   
   @NgenAuditField(fieldName = NgenAuditFieldNameType.SHIPMENTNUMBER)
   private String shipmentNumber;
   
   private BigInteger documentPieces;
   
   private BigDecimal documentWeight;

}