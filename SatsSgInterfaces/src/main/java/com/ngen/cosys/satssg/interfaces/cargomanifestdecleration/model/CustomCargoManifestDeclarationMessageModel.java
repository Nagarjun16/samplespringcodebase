package com.ngen.cosys.satssg.interfaces.cargomanifestdecleration.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ngen.cosys.JsonSerializer.LocalDateSerializer;
import com.ngen.cosys.JsonSerializer.LocalDateTimeSerializer;
import com.ngen.cosys.annotation.NgenCosysAppAnnotation;
import com.ngen.cosys.audit.NgenAudit;
import com.ngen.cosys.audit.NgenAuditEntityRef2;
import com.ngen.cosys.audit.NgenAuditEntityRef3;
import com.ngen.cosys.audit.NgenAuditEntityType;
import com.ngen.cosys.audit.NgenAuditEntityValue2;
import com.ngen.cosys.audit.NgenAuditEntityValue3;
import com.ngen.cosys.audit.NgenAuditEventRepository;
import com.ngen.cosys.audit.NgenAuditEventType;
import com.ngen.cosys.audit.NgenAuditField;
import com.ngen.cosys.audit.NgenAuditFieldName.NgenAuditFieldNameType;
import com.ngen.cosys.framework.model.BaseBO;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Component
@ApiModel
@ToString
@Getter
@Setter
@NoArgsConstructor
@NgenCosysAppAnnotation
@NgenAudit(entityFieldName ="shipmentNumber", entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.ACES_CUSTOMS, repository = NgenAuditEventRepository.AWB)
public class CustomCargoManifestDeclarationMessageModel extends BaseBO {

   /**
    * System generated serail version id
    */
   private static final long serialVersionUID = 1L;

   private BigInteger cmdVersionNumber;
   private BigInteger pieces;
   private BigInteger harmonisedGoods;
   private BigInteger flightDay;
   private BigInteger hwbPieces;
   private BigInteger hwbHarmonisedGoods;
   private BigInteger customFlightId;
   private BigInteger count;
   private BigInteger customShipmentInfoId;
   private BigInteger earliestFlight;

   private BigDecimal weight;
   private BigDecimal hwbWeight;

   private String actionCode;
   private String loadListIndicator;
   private String lateIndicator;
   private String cmdProcessingId;
   private BigInteger customCargoManifestDeclarationMessageId;
   private String awbPrefix;
   private String awbSerialNumber;
   private String origin;
   private String finalDestination;
   private String natureOfGoods;
   private String carrierCode;
   private String flightNumber;
   @NgenAuditEntityRef2(entityType = NgenAuditEntityType.FLIGHT,  parentEntityType = NgenAuditEntityType.AWB)
   @NgenAuditEntityValue2(entityType = NgenAuditEntityType.FLIGHT, parentEntityType = NgenAuditEntityType.AWB)
   @NgenAuditField(fieldName =NgenAuditFieldNameType.FLIGHTKEY)
   private String flightkey;
   private String flightMonth;
   private String houseWayBillNumber;
   private String hwbWeightUnitCode;
   private String hwbNatureOfGoods;
   private String shipperName;
   private String shipperAddress;
   private String consigneeName;
   private String consigneeAddress;
   private String localAuthorityType;
   private String permitNumber;
   private String mixPack;
   private String importAwbPrefix;
   private String importAwbSerialNumber;
   private String exemptionCode;
   private String exemptionRemarks;
   private String deliveredOrUndeliveredIndicator;
   private String senderName;
   private String sendrCompany;
   private String agentCrOrIaNumber;
   private String cargoAgentCode;
   private String cargoAgentReference;
   private String mrsStatusCode;
   @NgenAuditField(fieldName = NgenAuditFieldNameType.SHIPMENTNUMBER)
   private String shipmentNumber;
   private String importExportIndicator;
   private String doNumber;
   private String cancellationReasonCode;

   private char weightUnitCode;

   @JsonSerialize(using = LocalDateSerializer.class)
   private LocalDate mrsSentDate;
   @NgenAuditEntityRef3(entityType = NgenAuditEntityType.FLIGHT,  parentEntityType = NgenAuditEntityType.AWB)
   @NgenAuditEntityValue3(entityType = NgenAuditEntityType.FLIGHT, parentEntityType = NgenAuditEntityType.AWB)
   @NgenAuditField(fieldName =NgenAuditFieldNameType.FLIGHTDATE)
   @JsonSerialize(using = LocalDateSerializer.class)
   private LocalDate flightDate;

   @JsonSerialize(using = LocalDateSerializer.class)
   private LocalDate firstWindowForImport;

   @JsonSerialize(using = LocalDateSerializer.class)
   private LocalDate secondWindowForImport;

   @JsonSerialize(using = LocalDateSerializer.class)
   private LocalDate firstWindowForExport;

   @JsonSerialize(using = LocalDateSerializer.class)
   private LocalDate secondWindowForExport;

   @JsonSerialize(using = LocalDateSerializer.class)
   private LocalDate cmdRecievedDate;

   @JsonSerialize(using = LocalDateTimeSerializer.class)
   private LocalDateTime scheduleArrivalDate;

   @JsonSerialize(using = LocalDateTimeSerializer.class)
   private LocalDateTime scheduleDepartureDate;

   @JsonSerialize(using = LocalDateTimeSerializer.class)
   private LocalDateTime scheduleDate;

   @JsonSerialize(using = LocalDateSerializer.class)
   private LocalDate fromDate;

   @JsonSerialize(using = LocalDateSerializer.class)
   private LocalDate toDate;

   private Boolean PartShipmentFlag;

   private boolean isMrsSent;

   private List<CmdLocalAuthorityInfoModel> larInfo;
   
   @NgenAuditField(fieldName = NgenAuditFieldNameType.CMD_RECDATE)
  
   private LocalDate cmdRecieved;

}