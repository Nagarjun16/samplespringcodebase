package com.ngen.cosys.impbd.summary.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.validation.Valid;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ngen.cosys.JsonSerializer.LocalDateSerializer;
import com.ngen.cosys.JsonSerializer.LocalDateTimeSerializer;
import com.ngen.cosys.JsonSerializer.STDETDDateTimeSerializer;
import com.ngen.cosys.annotation.NgenCosysAppAnnotation;
import com.ngen.cosys.audit.NgenAuditField;
import com.ngen.cosys.audit.NgenAuditFieldName.NgenAuditFieldNameType;
import com.ngen.cosys.impbd.summary.validator.BreakDownSummaryValidation;
import com.ngen.cosys.model.FlightModel;
import com.ngen.cosys.validator.annotations.CheckBreakDownCompleteConstraint;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@XmlRootElement
@ApiModel
@Getter
@Setter
@NoArgsConstructor
@NgenCosysAppAnnotation
@Validated
@CheckBreakDownCompleteConstraint(flightKey = "flightNumber", flightOriginDate = "flightDate", groups = {
      BreakDownSummaryValidation.class })
public class BreakDownSummaryModel extends FlightModel {

   /**
    * System generated serial version id
    */
   private static final long serialVersionUID = 1L;

   private BigInteger id;
   private String dutyManager;
   private String checker;
   private String breakDownStaffGroup;
   private String approvedByCO;
   private String approvedByDM;
   private String flightNo;
   private String flightClosed;
   private String reasonForWaive;
   private String reasonForDelay;
   @NgenAuditField(fieldName = NgenAuditFieldNameType.TONNAGE_BREAKDOWNBYSP)
   private BigDecimal tonnageBreakDownBySp;
   @NgenAuditField(fieldName = NgenAuditFieldNameType.TONNAGE_BREAKDOWNBYSATS)
   private BigDecimal tonnageBreakDownBySats;
   @NgenAuditField(fieldName = NgenAuditFieldNameType.TONNAGE_ULD_WEIGHT)
   private BigDecimal tonnageULDWeight;
   @NgenAuditField(fieldName = NgenAuditFieldNameType.TONNAGE_BULK_WEIGHT)
   private BigDecimal tonnageBulkWeight;
   @NgenAuditField(fieldName = NgenAuditFieldNameType.TOTAL_TONNAGE_BY_FLIGHT)
   private BigDecimal totalTonnageByFlight;
   private Boolean liquIdatedDamagesWaived = Boolean.FALSE;
   private Boolean liquIdatedDamageApplicable = Boolean.FALSE;

   private Boolean ldWaive = Boolean.FALSE;
   private Boolean ldApplicable = Boolean.FALSE;
   private Boolean flightClosedFlag = Boolean.FALSE;

   private BigInteger delayInMinutes;
   private BigInteger fct;
   private BigInteger atat;
   private BigInteger stp;

   @JsonSerialize(using = STDETDDateTimeSerializer.class)
   private LocalDateTime firstUldTowInTime;

   @JsonSerialize(using = STDETDDateTimeSerializer.class)
   private LocalDateTime lastUldTowInTime;
   
   @JsonSerialize(using = LocalDateTimeSerializer.class)
   private LocalDateTime flightClosedAt;
   
   @JsonSerialize(using = LocalDateTimeSerializer.class)
   private LocalDateTime approvedDM;
   
   @JsonSerialize(using = LocalDateTimeSerializer.class)
   private LocalDateTime approvedCO;
   
   private String fightClosedBy;

   @NgenAuditField(fieldName = NgenAuditFieldNameType.BREAKDOWN_COMPLETION_DATETIME)
   @JsonSerialize(using = LocalDateTimeSerializer.class)
   private LocalDateTime breakdownCompletionDataTime;

   @NgenAuditField(fieldName = NgenAuditFieldNameType.FLIGHT_COMPLETION_DATETIME)
   @JsonSerialize(using = LocalDateTimeSerializer.class)
   private LocalDateTime flightCompletionDataTime;

   @NgenAuditField(fieldName = NgenAuditFieldNameType.ULD_INFO)
   private List<BreakDownSummaryUldModel> uldInfo;
   @NgenAuditField(fieldName = NgenAuditFieldNameType.TONNAGE_HANDLING_INFO)
   @Valid
   private List<BreakDownSummaryTonnageHandledModel> tonnageHandlingInfo;

}