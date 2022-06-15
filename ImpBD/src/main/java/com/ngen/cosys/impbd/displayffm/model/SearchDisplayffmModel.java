package com.ngen.cosys.impbd.displayffm.model;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ngen.cosys.JsonSerializer.LocalDateSerializer;
import com.ngen.cosys.JsonSerializer.STDETDDateTimeSerializer;
import com.ngen.cosys.annotation.InjectSegment;
import com.ngen.cosys.audit.NgenAuditField;
import com.ngen.cosys.audit.NgenAuditFieldName.NgenAuditFieldNameType;
import com.ngen.cosys.framework.model.BaseBO;
import com.ngen.cosys.impbd.summary.validator.BreakDownSummaryValidation;
import com.ngen.cosys.validator.annotations.UserCarrierValidation;
import com.ngen.cosys.validators.ArrivalManifestValidationGroup;
import com.ngen.cosys.validators.DisplayffmValidationGroup;
import com.ngen.cosys.validators.InwardSearchValidationGroup;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@UserCarrierValidation(shipmentNumber = "", flightKey = "flightNumber", loggedInUser = "loggedInUser", type = "FLIGHT", groups = {DisplayffmValidationGroup.class })
public class SearchDisplayffmModel  extends BaseBO{
   /**
    * System generated serial version id
    */
   private static final long serialVersionUID = 1L;
   
   private BigInteger flightId;

   @NotBlank(message = "ERROR_FLIGHT_NUMBER_REQUIRED", groups = { InwardSearchValidationGroup.class,
         ArrivalManifestValidationGroup.class, BreakDownSummaryValidation.class })
   @Size(max = 12, message = "ERROR_FLIGHT_NUMBER_SHOULD_BE_8_CHARACTER")
   @NgenAuditField(fieldName = NgenAuditFieldNameType.FLIGHTKEY)
   private String flightNumber;

   @NotNull(message = "ERRRO_FLIGHT_DATE_REQUIRED", groups = { InwardSearchValidationGroup.class,
         BreakDownSummaryValidation.class })
   @JsonSerialize(using = LocalDateSerializer.class)
   @NgenAuditField(fieldName = "flight Date")
   private LocalDate flightDate;


	@InjectSegment(flightNumber="flightNumber",flightDate="flightDate",flightType = "I" , segment="segment", flightId = "")
	private String segment;
	
   private String aircraftRegCode;

   @NgenAuditField(fieldName = "sta")
   @JsonSerialize(using = STDETDDateTimeSerializer.class)
   private LocalDateTime sta;

   @NgenAuditField(fieldName = "eta")
   @JsonSerialize(using = STDETDDateTimeSerializer.class)
   private LocalDateTime eta;

   @NgenAuditField(fieldName = "ATA")
   @JsonSerialize(using = STDETDDateTimeSerializer.class)
   private LocalDateTime ata;

   @JsonSerialize(using = STDETDDateTimeSerializer.class)
   private LocalDateTime std;

   @NgenAuditField(fieldName = "Boarding Point")
   private String boardingPoint;

   @NgenAuditField(fieldName = "Off Point")
   private String offPoint;

   @NgenAuditField(fieldName = "Carrier Code")
   private String carrierCode;
   
   private BigInteger segmentId;
   
   private String typeOfFFM;

   private BigInteger segmentCopy;

}
