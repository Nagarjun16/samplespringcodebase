package com.ngen.cosys.impbd.summary.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ngen.cosys.JsonSerializer.LocalDateTimeSerializer;
import com.ngen.cosys.audit.NgenAudit;
import com.ngen.cosys.audit.NgenAuditEntityType;
import com.ngen.cosys.audit.NgenAuditEventRepository;
import com.ngen.cosys.audit.NgenAuditEventType;
import com.ngen.cosys.audit.NgenAuditField;
import com.ngen.cosys.audit.NgenAuditFieldName.NgenAuditFieldNameType;
import com.ngen.cosys.model.ULDModel;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@ApiModel
@Getter
@Setter
@NoArgsConstructor
@NgenAudit(entityFieldName = "flightNumber", entityType = NgenAuditEntityType.FLIGHT, eventName = NgenAuditEventType.BREAKDOWN_SUMMARY, repository = NgenAuditEventRepository.FLIGHT)
@NgenAudit(entityFieldName = "uldNumber", entityType = NgenAuditEntityType.ULD, eventName = NgenAuditEventType.BREAKDOWN_SUMMARY, repository = NgenAuditEventRepository.ULD)
public class BreakDownSummaryUldModel extends ULDModel {

   /**
    * System generated serial version id
    */
   private static final long serialVersionUID = 3902131233921150054L;

   private BigInteger id;
   private BigInteger flightId;
   private BigInteger summaryId;
   private BigInteger rowNumber;

   private String contentCode;
   private String handlingMode;
   private String breakdownStaff;
   @NgenAuditField(fieldName = NgenAuditFieldNameType.SATS)
   private String sats;
   private String serviceContrator;
   private String handlingArea;

   @NgenAuditField(fieldName = NgenAuditFieldNameType.SERVICE_CONTRACTOR_NAME)
   private String serviceContractorName;

   @NgenAuditField(fieldName =NgenAuditFieldNameType.MANIFESTED_WEIGHT)
   private BigDecimal manifestedWeight;

   @NgenAuditField(fieldName =NgenAuditFieldNameType.TONNAGE_BY_CONTRACTOR)
   private BigDecimal tonnageByContractor;
   
   @NgenAuditField(fieldName =NgenAuditFieldNameType.TONNAGE_BY_GHA)
   private BigDecimal tonnageByGha;

   @NgenAuditField(fieldName =NgenAuditFieldNameType.DIFFERENCE)
   private BigDecimal differece=BigDecimal.ZERO;

   @NgenAuditField(fieldName =NgenAuditFieldNameType.BREAKDOWN_STARTTIME)
   @JsonSerialize(using = LocalDateTimeSerializer.class)
   private LocalDateTime breakdownStartDataTime;

   @NgenAuditField(fieldName =NgenAuditFieldNameType.BREAKDOWN_ENDTIME)
   @JsonSerialize(using = LocalDateTimeSerializer.class)
   private LocalDateTime breakdownEndDataTime;

   private List<String> serviceContractor;

   private String requestedFrom;
   private List<BreakDownSummaryUldTrolleyShcModel> shcTonnageInfo;

}