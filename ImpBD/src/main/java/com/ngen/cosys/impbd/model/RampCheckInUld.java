package com.ngen.cosys.impbd.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

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
import com.ngen.cosys.impbd.mail.validator.group.ContainerValidatorGroup;
import com.ngen.cosys.impbd.mail.validator.group.RampCheckinValidationGroup;
import com.ngen.cosys.validator.annotations.CheckContainerNumberConstraint;
import com.ngen.cosys.validator.annotations.CheckDriverIDConstraint;
import com.ngen.cosys.validator.enums.MandatoryType;

import groovy.transform.ToString;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author NIIT Technologies
 *
 */
@XmlRootElement
@ApiModel
@Component
@ToString
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Validated
@NgenAudit(entityFieldName = "flight", entityType = NgenAuditEntityType.FLIGHT, eventName = NgenAuditEventType.INBOUND_RAMP_CEHCKIN, repository = NgenAuditEventRepository.FLIGHT)
@NgenAudit(entityFieldName = NgenAuditFieldNameType.ULDNUMBER, entityType = NgenAuditEntityType.ULD, eventName = NgenAuditEventType.INBOUND_RAMP_CEHCKIN, repository = NgenAuditEventRepository.ULD)
@NgenAudit(entityFieldName = NgenAuditFieldNameType.ULDNUMBER, entityType = NgenAuditEntityType.ULD, eventName = NgenAuditEventType.INBOUND_RAMP_UNCEHCKIN, repository = NgenAuditEventRepository.ULD)
public class RampCheckInUld extends BaseBO {
   /**
    * The default serialVersionUID.
    */
   private static final long serialVersionUID = 1L;

   private BigInteger impRampCheckInId;

   private String id;

   private BigInteger flightId;

   @NgenAuditField(fieldName = NgenAuditFieldNameType.ORIGIN)
   private String origin;

   @NgenAuditField(fieldName = NgenAuditFieldNameType.FLIGHTKEY)
   private String flight;

   @JsonSerialize(using = LocalDateSerializer.class)
   @NgenAuditField(fieldName = NgenAuditFieldNameType.FLIGHTDATE)
   private LocalDate flightDate;

   @NgenAuditField(fieldName = NgenAuditFieldNameType.ULDNUMBER)
   @CheckContainerNumberConstraint(mandatory = MandatoryType.Type.REQUIRED, groups = {
		   RampCheckinValidationGroup.class, ContainerValidatorGroup.class })
   private String uldNumber;

   @NgenAuditField(fieldName = NgenAuditFieldNameType.CREATESHC)
   private List<RampCheckInSHC> shcs;

   @NgenAuditField(fieldName = NgenAuditFieldNameType.TRANSFER_TYPE)
   private String transferType;

   @NgenAuditField(fieldName = NgenAuditFieldNameType.CONTENTCOD)
   private String contentCode;

   @NgenAuditField(fieldName = NgenAuditFieldNameType.ULD_TROLLEY)
   private Boolean usedAsTrolley = false;

   @NgenAuditField(fieldName = NgenAuditFieldNameType.DAMAGED)
   private Boolean damaged = false;

   private Boolean empty = false;

   @NgenAuditField(fieldName = NgenAuditFieldNameType.PIGGYBACK)
   private Boolean piggyback = false;

   @NgenAuditField(fieldName = NgenAuditFieldNameType.PHCS)
   private Boolean phc = false;

   @NgenAuditField(fieldName = NgenAuditFieldNameType.VALS)
   private Boolean val = false;

   private Boolean manual;

   @CheckDriverIDConstraint(mandatory = MandatoryType.Type.NOTREQUIRED, groups = { RampCheckinValidationGroup.class }) 
   @NgenAuditField(fieldName = NgenAuditFieldNameType.DRIVER_ID)
   private String driverId;

   @NgenAuditField(fieldName = NgenAuditFieldNameType.CHECKEDIN_TIME)
   @JsonSerialize(using = LocalDateTimeSerializer.class)
   private LocalDateTime checkedinAt;

   @NgenAuditField(fieldName = NgenAuditFieldNameType.CHECKEDIN_BY)
   private String checkedinBy;

   @NgenAuditField(fieldName = NgenAuditFieldNameType.CHECKED_AREA)
   private String checkedinArea;

   @NgenAuditField(fieldName = NgenAuditFieldNameType.OFFLOADREASON)
   private String offloadReason;

   @NgenAuditField(fieldName = NgenAuditFieldNameType.REMARKS)
   private String remarks;
   
   //@NgenAuditField(fieldName = NgenAuditFieldNameType.CREATESHC)
   private List<RampCheckInSHCInput> createshc;

   @NgenAuditField(fieldName = NgenAuditFieldNameType.STATUECODE)
   private Integer statueCode;

   private Boolean offloadedFlag;

   @NgenAuditField(fieldName = NgenAuditFieldNameType.TRACTOR_NUMBER)
   private String tractorNumber;

   private BigInteger handOverId;

   @NgenAuditField(fieldName = NgenAuditFieldNameType.HANDOVERAREA)
   private String handedOverArea;

   @NgenAuditField(fieldName = NgenAuditFieldNameType.HANDOVERDDATETIME)
   @JsonSerialize(using = LocalDateTimeSerializer.class)
   private LocalDateTime handoverDateTime;

   private String impHandOverContainerTrolleyInformationId;

   private String carrierCode;

   private String nilCargo;

   private BigInteger ouboundFlightId;

   private String ouboundFlightKey;

   @JsonSerialize(using = LocalDateSerializer.class)
   private LocalDate ouboundFlightDate;

   private String handlingInstructions;

   private String handlingMode;

   @NgenAuditField(fieldName = NgenAuditFieldNameType.PHC_FLAG)
   private String phcFlag;
   
   private Boolean manualUpdate;
   
   private String shcCode;

   @NgenAuditField(fieldName = NgenAuditFieldNameType.UCM_FINALIZED)
   private Integer ucmFinalized;
   
   private Boolean uploadphotoFlag;
   
   private BigInteger uldTemperatureLogId;
   
   private String temperatureType;
   
   private BigDecimal temperatureValue;
   
   @JsonSerialize(using = LocalDateTimeSerializer.class)
   private LocalDateTime temperatureCaptureDt;

   private String uldEvent;
   
   private String tempRemarks;
   
   private BigDecimal temperatureTypeValue;
}
