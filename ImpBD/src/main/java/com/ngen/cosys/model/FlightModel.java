package com.ngen.cosys.model;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ngen.cosys.JsonSerializer.LocalDateSerializer;
import com.ngen.cosys.JsonSerializer.STDETDDateTimeSerializer;
import com.ngen.cosys.audit.NgenAudit;
import com.ngen.cosys.audit.NgenAuditEntityType;
import com.ngen.cosys.audit.NgenAuditEventRepository;
import com.ngen.cosys.audit.NgenAuditEventType;
import com.ngen.cosys.audit.NgenAuditField;
import com.ngen.cosys.audit.NgenAuditFieldName.NgenAuditFieldNameType;
import com.ngen.cosys.framework.model.BaseBO;
import com.ngen.cosys.impbd.flightdiscrepancylist.validators.FlightValidatorGroup;
import com.ngen.cosys.impbd.instruction.validator.BreakDownHandlingInstructionValidationGroup;
import com.ngen.cosys.impbd.mail.validator.group.InboundMailDocumentValidationGroup;
import com.ngen.cosys.impbd.mail.validator.group.InboundMailManifestValidationGroup;
import com.ngen.cosys.impbd.summary.validator.BreakDownSummaryValidation;
import com.ngen.cosys.inward.model.InwardServiceReportModel;
import com.ngen.cosys.validator.annotations.CheckValidFlightConstraint;
import com.ngen.cosys.validator.annotations.UserCarrierValidation;
import com.ngen.cosys.validator.enums.FlightType;
import com.ngen.cosys.validators.ArrivalManifestValidationGroup;
import com.ngen.cosys.validators.BreakDownWorkListValidationGroup;
import com.ngen.cosys.validators.CaptureDamageReport;
import com.ngen.cosys.validators.DocumentVerificationValidation;
import com.ngen.cosys.validators.HandoverValidationGroup;
import com.ngen.cosys.validators.InboundBreakDownValidation;
import com.ngen.cosys.validators.InwardSearchValidationGroup;
import com.ngen.cosys.validators.UserAssignedCarrierValidation;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ApiModel
@Getter
@Setter
@ToString
@NoArgsConstructor
@UserCarrierValidation(flightKey = "flightNumber", groups = {UserAssignedCarrierValidation.class, InboundMailManifestValidationGroup.class}, loggedInUser = "loggedInUser", type = "FLIGHT", shipmentNumber = "")
@CheckValidFlightConstraint(type = FlightType.Type.IMPORT, number = "flightNumber", originDate = "flightDate", groups = {
      ArrivalManifestValidationGroup.class, HandoverValidationGroup.class,
      BreakDownHandlingInstructionValidationGroup.class, InboundMailDocumentValidationGroup.class,FlightValidatorGroup.class,
      BreakDownSummaryValidation.class,InboundBreakDownValidation.class,DocumentVerificationValidation.class,BreakDownWorkListValidationGroup.class,CaptureDamageReport.class})
@NgenAudit(entityFieldName = NgenAuditFieldNameType.FLIGHTKEY, entityType = NgenAuditEntityType.FLIGHT, eventName = NgenAuditEventType.BREAKDOWN_DELAY_STATUS, repository = NgenAuditEventRepository.FLIGHT)
@NgenAudit(entityFieldName = NgenAuditFieldNameType.FLIGHTKEY, entityType = NgenAuditEntityType.FLIGHT, eventName = NgenAuditEventType.ARRIVAL_MANIFEST, repository = NgenAuditEventRepository.FLIGHT)
@NgenAudit(entityFieldName = NgenAuditFieldNameType.FLIGHTKEY, entityType = NgenAuditEntityType.FLIGHT, eventName = NgenAuditEventType.BREAKDOWN_COMPLETE, repository = NgenAuditEventRepository.FLIGHT)
@NgenAudit(entityFieldName = "flightNumber", entityType = NgenAuditEntityType.FLIGHT, eventName = NgenAuditEventType.DOCUMENT_VERIFICATION, repository = NgenAuditEventRepository.FLIGHT)
@NgenAudit(entityFieldName = "flightNumber", entityType = NgenAuditEntityType.FLIGHT, eventName = NgenAuditEventType.FLIGHT_COMPLETE, repository = NgenAuditEventRepository.FLIGHT)
public class FlightModel extends BaseBO {

   /**
    * System generated serial version id
    */
   private static final long serialVersionUID = -4172013140519434912L;

   private BigInteger flightId;

   @SuppressWarnings("deprecation")
@NotBlank(message = "ERROR_FLIGHT_NUMBER_REQUIRED", groups = { InwardSearchValidationGroup.class,
         ArrivalManifestValidationGroup.class, BreakDownSummaryValidation.class })
   @Size(max = 12, message = "ERROR_FLIGHT_NUMBER_SHOULD_BE_8_CHARACTER")
   @NgenAuditField(fieldName = NgenAuditFieldNameType.FLIGHTKEY)
   private String flightNumber;

   @NotNull(message = "ERRRO_FLIGHT_DATE_REQUIRED", groups = { InwardSearchValidationGroup.class,
         BreakDownSummaryValidation.class })
   @JsonSerialize(using = LocalDateSerializer.class)
   @NgenAuditField(fieldName = NgenAuditFieldNameType.FLT_DATE)
   private LocalDate flightDate;

   private String aircraftRegCode;

   @NgenAuditField(fieldName = NgenAuditFieldNameType.STAS)
   @JsonSerialize(using = STDETDDateTimeSerializer.class)
   private LocalDateTime sta;

   @NgenAuditField(fieldName = NgenAuditFieldNameType.ETAS)
   @JsonSerialize(using = STDETDDateTimeSerializer.class)
   private LocalDateTime eta;

   @NgenAuditField(fieldName = NgenAuditFieldNameType.ATA)
   @JsonSerialize(using = STDETDDateTimeSerializer.class)
   private LocalDateTime ata;

   @JsonSerialize(using = STDETDDateTimeSerializer.class)
   private LocalDateTime std;

   @NgenAuditField(fieldName = NgenAuditFieldNameType.BOARDING_POINT)
   private String boardingPoint;

   @NgenAuditField(fieldName = NgenAuditFieldNameType.OFF_POINT)
   private String offPoint;
   
   private Boolean handlinginSystem =Boolean.FALSE;

   @NgenAuditField(fieldName = NgenAuditFieldNameType.CARRIER)
   private String carrierCode;
   private String messageCopy;
   private String rejectCount;
   private String routingInfo;
  
   @Valid
   private List<InwardServiceReportModel> segmentArray;
   

}