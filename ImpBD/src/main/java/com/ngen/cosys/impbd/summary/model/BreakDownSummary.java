package com.ngen.cosys.impbd.summary.model;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ngen.cosys.JsonSerializer.LocalDateTimeSerializer;
import com.ngen.cosys.annotation.NgenCosysAppAnnotation;
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
@Validated
@NgenAudit(entityFieldName = "flightNumber", entityType = NgenAuditEntityType.FLIGHT, eventName = NgenAuditEventType.BREAKDOWN_SUMMARY, repository = NgenAuditEventRepository.FLIGHT)
public class BreakDownSummary extends BaseBO {
   
   /**
    * System generated serial version id
    */
   private static final long serialVersionUID = 1L;

   private BigInteger id;
   private BigInteger flightId;   
   private BigInteger feedbackForStaff;

   private String breakDownStaffGroup;
   @NgenAuditField(fieldName = NgenAuditFieldNameType.REASON_FOR_WAIVE)
   private String reasonForWaive;
   @NgenAuditField(fieldName = NgenAuditFieldNameType.REASON_FOR_DELAY)
   private String reasonForDelay;
   private String approvedLDWaiveApprovedBy;
   private String approvedLDApplicableApprovedBy;
   @NgenAuditField(fieldName = NgenAuditFieldNameType.LD_WAIVED)
   private Boolean liquIdatedDamagesWaived = Boolean.FALSE;
   @NgenAuditField(fieldName = NgenAuditFieldNameType.LD_APPLICABLE)
   private Boolean liquIdatedDamageApplicable = Boolean.FALSE;
   private Boolean approvedLDWaive = Boolean.FALSE;
   private Boolean approvedLDApplicable = Boolean.FALSE;
   private BigInteger delayInMinutes;
   @NgenAuditField(fieldName = NgenAuditFieldNameType.FLIGHTKEY)
   private String flightNumber;
   @NgenAuditField(fieldName = NgenAuditFieldNameType.FLIGHTDATE)
   private String flightDate;
   @JsonSerialize(using = LocalDateTimeSerializer.class)
   private LocalDateTime approvedLDWaiveApprovedOn;
   
   @JsonSerialize(using = LocalDateTimeSerializer.class)
   private LocalDateTime approvedLDApplicableApprovedOn;

   @Valid
   @NgenCosysAppAnnotation
   @NgenAuditField(fieldName = NgenAuditFieldNameType.ULD_INFO)
   private List<BreakDownSummaryUldModel> uldInfo;
   
   @Valid
   @NgenCosysAppAnnotation
   @NgenAuditField(fieldName = NgenAuditFieldNameType.TONNAGE_HANDLING_INFO)
   private List<BreakDownSummaryTonnageHandledModel> tonnageHandlingInfo;
   
  private String emailGroup;

   private List<Email> emails;
   
}