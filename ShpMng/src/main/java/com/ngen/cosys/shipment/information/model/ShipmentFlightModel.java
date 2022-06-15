package com.ngen.cosys.shipment.information.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

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
import com.ngen.cosys.shipment.awb.model.ShipmentRemarksModel;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@NgenAudit(entityFieldName = "shipmentNumber", entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.FORCE_PURGE, repository = NgenAuditEventRepository.AWB, entityRefFieldName = "shipmentId")
public class ShipmentFlightModel extends BaseBO {
   /**
   * 
   */
   private static final long serialVersionUID = 1L;

   private BigInteger shipmentId;
   private BigInteger referenceId;
   private BigInteger flightId;
   @NgenAuditField(fieldName = NgenAuditFieldNameType.SHIPMENT_NUMBER)
   private String shipmentNumber;
   @JsonSerialize(using = LocalDateSerializer.class)
   private LocalDate shipmentDate;

   private String flightDetailsKey;
   private String bookingFlightKey;
   private String irregularityInfo;
   private String damagedPieces;
   @JsonSerialize(using = LocalDateSerializer.class)
   private LocalDate flightDate;

   @JsonSerialize(using = LocalDateTimeSerializer.class)
   private LocalDateTime flightDetailsSta;

   @JsonSerialize(using = LocalDateTimeSerializer.class)
   private LocalDateTime flightDetailsStaTime;

   @JsonSerialize(using = LocalDateTimeSerializer.class)
   private LocalDateTime bookingSta;

   @JsonSerialize(using = LocalDateTimeSerializer.class)
   private LocalDateTime bookingStaTime;

   private String flightDetailsBoardPoint;
   private String flightOffPoint;
   private String bookingFlightBoardingPoint;
   private String bookingFlightOffPoint;
   private BigInteger manifestPieces;
   private BigDecimal manifestWeight;
   private String flightDocuments;
   private Boolean documentReceivedFlag;
   private Boolean photoCopyAwbFlag;

   private String readyForDelivery;
   private String flightScreeningCompleted;
   private String flightRemarks;
   private String flightFsuStatus;
   private String bookingInfo;
   private BigInteger bookingPieces;
   private BigDecimal bookingWeight;

   private String buildupComplete;
   private String shipmentstatus;
   private Boolean throughTransitFlag;
   private String bookingStatusCode;
   private String shc;
   private String transferType;

   // Damage Information for a ShipmentId/FlightId
   private List<ShipmentDamageInfoModel> damageDetails;

   private List<ShipmentInventoryModel> inventoryDetails;
   private List<ShipmentLoadingInfoModel> loadingInfoModel;

   private List<ShipmentRemarksModel> remarks;

   private BigInteger breakdownPieces;
   private BigDecimal breakdownWeight;
   private String partSuffix;
   private String currentPartSuffix;

   private BigInteger offLoadedPieces;
   private BigDecimal offLoadedWeight;
   
   private String flightAtaDay;
   
   private String bookingShc;
   //Added for Purge//
   private String carrierCode;   
   @NgenAuditField(fieldName = NgenAuditFieldNameType.REASONFORPURGE)
   private String reasonForPurge;
   private BigInteger inventoryPieces;
   private BigInteger freightOutPieces;
   private BigInteger missingCargoPieces;
   private BigInteger missingAWBPieces;
   private boolean readyForPurge;
   private String flightNumber;
   @NgenAuditField(fieldName = NgenAuditFieldNameType.FLIGHTKEY)
   private String flightKey;
   @NgenAuditField(fieldName = NgenAuditFieldNameType.FLIGHTDATE)
   @JsonSerialize(using = LocalDateSerializer.class)
   private LocalDate flightOriginDate;
   @NgenAuditField(fieldName = NgenAuditFieldNameType.FORCEPURGEBY)
   private String archivedBy;
   private BigInteger awbPiece;
   private String stausForCancel;
   @NgenAuditField(fieldName = NgenAuditFieldNameType.FORCEPURGETIME)
   private LocalDateTime forcePurgeTime;
   
   @JsonSerialize(using = LocalDateTimeSerializer.class)
   private LocalDateTime sta;
   //Added for Purge//
   
   private String message;
   private String hwbNumber;
   
   private ShipmentFlightCustomsInfo shipmentFlightCustomsInfo = new ShipmentFlightCustomsInfo();
}
