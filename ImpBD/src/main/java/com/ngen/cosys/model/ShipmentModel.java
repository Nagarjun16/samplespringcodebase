package com.ngen.cosys.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ngen.cosys.JsonSerializer.LocalDateSerializer;
import com.ngen.cosys.annotation.InjectShipmentDate;
import com.ngen.cosys.annotation.NgenCosysAppAnnotation;
import com.ngen.cosys.audit.NgenAudit;
import com.ngen.cosys.audit.NgenAuditEntityType;
import com.ngen.cosys.audit.NgenAuditEventRepository;
import com.ngen.cosys.audit.NgenAuditEventType;
import com.ngen.cosys.audit.NgenAuditField;
import com.ngen.cosys.audit.NgenAuditFieldName.NgenAuditFieldNameType;
import com.ngen.cosys.framework.model.BaseBO;
import com.ngen.cosys.impbd.shipment.breakdown.validator.InboundBreakDownValidationGroup;
import com.ngen.cosys.validator.annotations.CheckCarrierCodeConstraint;
import com.ngen.cosys.validator.annotations.CheckPieceConstraint;
import com.ngen.cosys.validator.annotations.CheckShipmentDestinationConstraint;
import com.ngen.cosys.validator.annotations.CheckShipmentNumberConstraint;
import com.ngen.cosys.validator.annotations.CheckWeightConstraint;
import com.ngen.cosys.validator.enums.MandatoryType;
import com.ngen.cosys.validators.ArrivalManifestAdditionalInfo;
import com.ngen.cosys.validators.ArrivalManifestValidationGroup;
import com.ngen.cosys.validators.InboundBreakDownValidation;
import com.ngen.cosys.validators.InwardServiceValidationGroup;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@XmlRootElement
@ApiModel
@ToString
@Setter
@Getter
@NoArgsConstructor
@NgenCosysAppAnnotation
@Validated
@NgenAudit(entityFieldName = NgenAuditFieldNameType.SHIPMENTNUMBER, entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.CAPTURE_DAMAGE, repository = NgenAuditEventRepository.AWB)
@NgenAudit(entityFieldName = NgenAuditFieldNameType.SHIPMENTNUMBER, entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.INBOUND_BREAKDOWN, repository = NgenAuditEventRepository.AWB)
@NgenAudit(entityFieldName = NgenAuditFieldNameType.SHIPMENTNUMBER, entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.ARRIVAL_MANIFEST, repository = NgenAuditEventRepository.AWB)
@NgenAudit(entityFieldName = NgenAuditFieldNameType.SHIPMENTNUMBER, entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.INWARD_SERVICE_REPORT, repository = NgenAuditEventRepository.AWB)
@CheckShipmentNumberConstraint(groups = { InboundBreakDownValidation.class,
		InboundBreakDownValidationGroup.class }, mandatory = MandatoryType.Type.REQUIRED, shipmentTypeField = "shipmentType", shipmentNumberField = "shipmentNumber")
public class ShipmentModel extends BaseBO {

	private static final long serialVersionUID = 1L;

	private BigInteger shipmentId;

	@NotBlank(message = "ERROR_AWB_NUMBER_FIELD_MANDATORY", groups = InwardServiceValidationGroup.class)
	@CheckShipmentNumberConstraint(groups = {
			ArrivalManifestAdditionalInfo.class }, mandatory = MandatoryType.Type.NOTREQUIRED)
	@NgenAuditField(fieldName = NgenAuditFieldNameType.SHIPMENTNUMBER)
	private String shipmentNumber;

	@InjectShipmentDate(shipmentNumberField = "shipmentNumber")
	@JsonSerialize(using = LocalDateSerializer.class)
	@NgenAuditField(fieldName = NgenAuditFieldNameType.SHIPMENTDATE)
	private LocalDate shipmentdate;

	private String receptacleNumber;

	@NgenAuditField(fieldName = NgenAuditFieldNameType.ORIGIN)
	@NotBlank(message = "ERROR_ORIGIN_FIELD_MANDATORY", groups = { InwardServiceValidationGroup.class,
			ArrivalManifestValidationGroup.class })
	@CheckShipmentDestinationConstraint(groups = { ArrivalManifestValidationGroup.class,
			ArrivalManifestAdditionalInfo.class })
	private String origin;

	@NgenAuditField(fieldName = NgenAuditFieldNameType.DESTINATION)
	@NotBlank(message = "ERROR_DESTINATION_FIELD_MANDATORY", groups = { InwardServiceValidationGroup.class,
			ArrivalManifestValidationGroup.class })
	@CheckShipmentDestinationConstraint(groups = { ArrivalManifestValidationGroup.class,
			ArrivalManifestAdditionalInfo.class })
	private String destination;

	@NgenAuditField(fieldName = NgenAuditFieldNameType.SHIPMENT_DESCRIPTION_CODE)
	private String shipmentDescriptionCode;

	@NotNull(message = "ERROR_PIECES_FIELD_MANDATORY", groups = InwardServiceValidationGroup.class)
	@CheckPieceConstraint(groups = { ArrivalManifestValidationGroup.class,
			ArrivalManifestAdditionalInfo.class }, type = MandatoryType.Type.REQUIRED)
	@NgenAuditField(fieldName = NgenAuditFieldNameType.PIECE)
	private BigInteger piece = BigInteger.ZERO;

	@NgenAuditField(fieldName = NgenAuditFieldNameType.WEIGHT_UNIT_CODE)
	private String weightUnitCode;

	@NgenAuditField(fieldName = NgenAuditFieldNameType.WEIGHT)
	@NotNull(message = "ERROR_WEIGHT_FIELD_MANDATORY", groups = InwardServiceValidationGroup.class)
	@CheckWeightConstraint(groups = { ArrivalManifestValidationGroup.class,
			ArrivalManifestAdditionalInfo.class }, mandatory = MandatoryType.Type.REQUIRED)
	private BigDecimal weight = BigDecimal.ZERO;
	
	private String volumeunitCode;	
	private BigDecimal volumeAmount;

	private String densityIndicator;
	private BigInteger densityGroupCode;

	@NgenAuditField(fieldName = NgenAuditFieldNameType.TOTALPIECES)
	private BigInteger totalPieces;

	@NgenAuditField(fieldName = NgenAuditFieldNameType.NATURE_OF_GOODS_DESCRIPTION)
	@NotBlank(message = "ERROR_NOG_FIELD_MANDATORY", groups = ArrivalManifestValidationGroup.class)
	@Pattern(regexp = "^[A-Za-z0-9-. ]*$", message = "data.invalid.telex.characters", groups = {
			InboundBreakDownValidationGroup.class, ArrivalManifestValidationGroup.class })
	private String natureOfGoodsDescription;

	private String movementPriorityCode;

	private String customsOriginCode;

	private String customsReference;

	@NgenAuditField(fieldName = NgenAuditFieldNameType.COPY_AWB, ignoreAuditEvents = {
			NgenAuditEventType.ARRIVAL_MANIFEST })
	private Boolean photoCopy = Boolean.FALSE;

	@NgenAuditField(fieldName = NgenAuditFieldNameType.ORIGINAL_AWB, ignoreAuditEvents = {
			NgenAuditEventType.ARRIVAL_MANIFEST })
	private Boolean originalAwb = Boolean.FALSE;

	@NgenAuditField(fieldName = NgenAuditFieldNameType.BARCODE_PRINTED, ignoreAuditEvents = {
			NgenAuditEventType.ARRIVAL_MANIFEST })
	private Boolean barcodePrintedFlag = Boolean.FALSE;

	@NgenAuditField(fieldName = NgenAuditFieldNameType.CARRIER)
	@CheckCarrierCodeConstraint(mandatory = MandatoryType.Type.NOTREQUIRED, groups = ArrivalManifestValidationGroup.class)
	private String carrierCode;

	@NgenAuditField(fieldName = NgenAuditFieldNameType.SHC_CODE)
	private String shcCode;

	@NgenAuditField(fieldName = NgenAuditFieldNameType.CARRIER_DESTINATION)
	@CheckShipmentDestinationConstraint(mandatory = MandatoryType.Type.NOTREQUIRED, groups = ArrivalManifestValidationGroup.class)
	private String carrierDestination;

	@NgenAuditField(fieldName = NgenAuditFieldNameType.SHIPMENTTYPE)
	private String shipmentType;

	@NgenAuditField(fieldName = NgenAuditFieldNameType.INSTRUCTION)
	private String instruction;

	private String bhShipmentNumber;

	@NgenAuditField(fieldName = NgenAuditFieldNameType.SHIPDATE)
	@InjectShipmentDate(shipmentNumberField = "bhShipmentNumber")
	@JsonSerialize(using = LocalDateSerializer.class)
	private LocalDate bhShipmentdate;

	private String bhShipmentOrigin;

	private String bhShipmentDestination;

	private LocalDateTime createdOn;

	private String specialHandlingCode;

	@NgenAuditField(fieldName = NgenAuditFieldNameType.BREAKDOWN_ID)
	private String breakdownId;

	private String hawbNumber;

	private String importArrivalManifestShipmentInfoId;

	private String otherServiceInformation1;

	private String otherServiceInformation2;

	private String transferType;

	private Boolean svc = Boolean.FALSE;

	private Boolean surplusFlag = Boolean.FALSE;

	@NgenAuditField(fieldName = NgenAuditFieldNameType.MAILBAGNUMBER)
	private String houseNumber;
}