package com.ngen.cosys.impbd.model;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ngen.cosys.JsonSerializer.LocalDateTimeSerializer;
import com.ngen.cosys.audit.NgenAudit;
import com.ngen.cosys.audit.NgenAuditEntityType;
import com.ngen.cosys.audit.NgenAuditEventRepository;
import com.ngen.cosys.audit.NgenAuditEventType;
import com.ngen.cosys.audit.NgenAuditField;
import com.ngen.cosys.audit.NgenAuditFieldName.NgenAuditFieldNameType;
import com.ngen.cosys.framework.model.BaseBO;
import com.ngen.cosys.validator.annotations.CheckAirportCodeConstraint;
import com.ngen.cosys.validator.annotations.CheckBlackOutFlightConstraint;
import com.ngen.cosys.validator.annotations.CheckContainerNumberConstraint;
import com.ngen.cosys.validator.enums.MandatoryType;
import com.ngen.cosys.validators.ArrivalManifestValidationGroup;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@XmlRootElement
@ApiModel
@Component
@ToString
@Setter
@Getter
@NoArgsConstructor
@Validated
@NgenAudit(entityFieldName = "flight", entityType = NgenAuditEntityType.FLIGHT, eventName = NgenAuditEventType.CARGO_PREANNOUNCEMENT, repository = NgenAuditEventRepository.FLIGHT)
@NgenAudit(entityFieldName = NgenAuditFieldNameType.ULDNUMBER, entityType = NgenAuditEntityType.ULD, eventName = NgenAuditEventType.CARGO_PREANNOUNCEMENT, repository = NgenAuditEventRepository.ULD)
public class CargoPreAnnouncement extends BaseBO {

   private static final long serialVersionUID = 1L;

   private BigInteger cargoPreAnnouncementId;

   @NgenAuditField(fieldName = NgenAuditFieldNameType.ULDNUMBER)
   @NotNull(message = "ERROR_ULDNUMBER_CANNOT_BE_BLANK")
   @NotBlank(message = "ERROR_ULDNUMBER_CANNOT_BE_BLANK")
   @CheckContainerNumberConstraint(mandatory = MandatoryType.Type.REQUIRED,groups = {
	         ArrivalManifestValidationGroup.class })
   private String uldNumber;
   
   private String rampUldNumber;

   private String incomingFlightId;
   
   @NgenAuditField(fieldName = NgenAuditFieldNameType.ULD_BOARD_POINT)
   @NotNull(message = "ERRRO-ULDBOARDPOINT_CANNOT-BE_BLANK")
   @NotBlank(message = "ERRRO-ULDBOARDPOINT_CANNOT-BE_BLANK")
   @CheckAirportCodeConstraint(groups=ArrivalManifestValidationGroup.class)
   private String uldBoardPoint;

   @NgenAuditField(fieldName = NgenAuditFieldNameType.ULD_OFF_POINT)
   @NotNull(message = "ERROR_ULDOFFPOINT_CANNOT_BE_BLANK")
   @NotBlank(message = "ERROR_ULDOFFPOINT_CANNOT_BE_BLANK")
   @CheckAirportCodeConstraint(groups=ArrivalManifestValidationGroup.class)
   private String uldOffPoint;

   @NgenAuditField(fieldName = NgenAuditFieldNameType.CONTENTCODE)
   @NotNull(message = "ERROR_CONTENT_CODE_CANNOT_BE_BLANK")
   @NotBlank(message = "ERROR_CONTENT_CODE_CANNOT_BE_BLANK")
   private String contentCode;

   @NgenAuditField(fieldName = NgenAuditFieldNameType.ULD_STATUS)
   private String uldStatus;
   
   @NgenAuditField(fieldName = NgenAuditFieldNameType.TRANSFER_TYPE)
   private String transferType;

   @NgenAuditField(fieldName = NgenAuditFieldNameType.ULD_LOADED_WITH)
   private String uldLoadedWith;

   @NgenAuditField(fieldName = NgenAuditFieldNameType.PHC)
   private boolean phcFlag;

   @NgenAuditField(fieldName = NgenAuditFieldNameType.ICS_OUTPUT_LOCATION)
   private String icsOutputLocation;

   @NgenAuditField(fieldName = NgenAuditFieldNameType.HANDLING_AREA_CODE)
   private String handlingAreaCode;

   @NgenAuditField(fieldName = NgenAuditFieldNameType.CARGO_TERMINAL_CODE)
   private String cargoTerminalCode;

   @NgenAuditField(fieldName = NgenAuditFieldNameType.WAREHOUSE_LOCATION_CODE)
   private String warehouseLocationCode;
   
   @NgenAuditField(fieldName = NgenAuditFieldNameType.SHCS_LIST)
   private String shcCode;
   
   
   private Boolean editShcs=Boolean.FALSE;

   private String connectingFlightId;
   
   private BigInteger bookingFlightId;

   @Valid
   @NgenAuditField(fieldName = NgenAuditFieldNameType.SHCS)
   private List<CargPreAnnouncementShcModel> specialHandlingCodes;

   @NgenAuditField(fieldName = NgenAuditFieldNameType.RAMP_HANDLING_INSTRUCTION)
   @Pattern(regexp = "^[A-Za-z0-9\\s]{0,65}$", message = "ERROR_INVALID_RAMPHANDLING_INSTRUCTION")
   private String rampHandlingInstructions;

   @NgenAuditField(fieldName = NgenAuditFieldNameType.WAREHOUSE_HANDLING_INSTRUCTION)
   @Pattern(regexp = "^[A-Za-z0-9\\s]{0,65}$", message = "ERROR_INVALID_RAMPHANDLING_INSTRUCTION")
   private String warehouseHandlingInstructions;

   @NgenAuditField(fieldName =NgenAuditFieldNameType.SOURCE_TYPE)
   private String announcementSourceType;

   @NgenAuditField(fieldName = NgenAuditFieldNameType.HANDLING_MODE)
   private String handlingMode;

   @CheckBlackOutFlightConstraint
   private BigInteger flightId;
   
   @NgenAuditField(fieldName =NgenAuditFieldNameType.OUTBOUNDFLIGHT)
   private String flight;

   @NgenAuditField(fieldName =NgenAuditFieldNameType.OUTBOUNDFLIGHTDATE)
   @JsonSerialize(using = LocalDateTimeSerializer.class)
   private LocalDateTime date;
   
   @NgenAuditField(fieldName =NgenAuditFieldNameType.FLIGHTKEY)
   private String incomingFlight;
   
   @NgenAuditField(fieldName =NgenAuditFieldNameType.FLIGHTDATE)
   private LocalDateTime incomingFlightDate;

   private boolean manualFlag;
   
   private boolean manualUpdateFlag;

   private boolean selectFlag = false;

   private BigInteger rampCheckinId;
   
   private Boolean bulk=Boolean.FALSE;
   
   private String screenFunction;

   private boolean checkboxFlag = false;
   
   private Boolean handlingWerehouseValidation;

}
