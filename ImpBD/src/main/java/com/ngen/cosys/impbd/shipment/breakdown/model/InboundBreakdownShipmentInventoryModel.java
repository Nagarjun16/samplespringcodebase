package com.ngen.cosys.impbd.shipment.breakdown.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ngen.cosys.JsonSerializer.LocalDateSerializer;
import com.ngen.cosys.audit.NgenAudit;
import com.ngen.cosys.audit.NgenAuditEntityRef2;
import com.ngen.cosys.audit.NgenAuditEntityRef3;
import com.ngen.cosys.audit.NgenAuditEntityRef6;
import com.ngen.cosys.audit.NgenAuditEntityType;
import com.ngen.cosys.audit.NgenAuditEntityValue2;
import com.ngen.cosys.audit.NgenAuditEntityValue3;
import com.ngen.cosys.audit.NgenAuditEntityValue6;
import com.ngen.cosys.audit.NgenAuditEventRepository;
import com.ngen.cosys.audit.NgenAuditEventType;
import com.ngen.cosys.audit.NgenAuditField;
import com.ngen.cosys.audit.NgenAuditFieldName.NgenAuditFieldNameType;
import com.ngen.cosys.framework.model.BaseBO;
import com.ngen.cosys.impbd.shipment.breakdown.validator.InboundBreakDownValidationGroup;
import com.ngen.cosys.validator.annotations.CheckPieceConstraint;
import com.ngen.cosys.validator.annotations.CheckWeightConstraint;
import com.ngen.cosys.validator.enums.MandatoryType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@NgenAudit(entityFieldName = "flightId", entityType = NgenAuditEntityType.FLIGHT, eventName = NgenAuditEventType.INBOUND_BREAKDOWN, repository = NgenAuditEventRepository.FLIGHT)
@NgenAudit(entityFieldName = "shipmentNumber", entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.INBOUND_BREAKDOWN, repository = NgenAuditEventRepository.AWB)
@NgenAudit(entityFieldName = "shipmentNumber", entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.MAINTAIN_LOCATION, repository = NgenAuditEventRepository.AWB)
public class InboundBreakdownShipmentInventoryModel extends BaseBO {
	/**
	 * System Generated Serial Version id
	 */
	private static final long serialVersionUID = 1L;

	private BigInteger inventoryId;
	private BigInteger inboundBreakdownStorageInfoId;
	private BigInteger inboundBreakdownShipmentId;
	private BigInteger shipmentId;
	private BigInteger flightId;
	private BigInteger inboundFlightId;

	@NgenAuditField(fieldName = NgenAuditFieldNameType.FLIGHTKEY)
	private String flightKey;

	@NgenAuditField(fieldName = NgenAuditFieldNameType.FLIGHTDATE)
	@JsonSerialize(using = LocalDateSerializer.class)
	private LocalDate flightOriginDate;

	@NgenAuditField(fieldName = NgenAuditFieldNameType.MANPIECES)
	private BigInteger manifestPieces;

	@NgenAuditField(fieldName = NgenAuditFieldNameType.MANWEIGHT)
	private BigDecimal manifestWeight;

	@NgenAuditField(fieldName = NgenAuditFieldNameType.SHIPLOC)
	@NgenAuditEntityRef2(entityType = NgenAuditEntityType.ULD, parentEntityType = NgenAuditEntityType.AWB)
	@NgenAuditEntityValue2(entityType = NgenAuditEntityType.ULD, parentEntityType = NgenAuditEntityType.AWB)
	private String shipmentLocation;

	@NgenAuditField(fieldName = NgenAuditFieldNameType.WRHOUSELOC)
	@NgenAuditEntityRef3(entityType = NgenAuditEntityType.LOCATION, parentEntityType = NgenAuditEntityType.AWB)
	@NgenAuditEntityValue3(entityType = NgenAuditEntityType.LOCATION, parentEntityType = NgenAuditEntityType.AWB)
	private String warehouseLocation;

	@NgenAuditField(fieldName = NgenAuditFieldNameType.PCS)
	@CheckPieceConstraint(type = MandatoryType.Type.REQUIRED, groups = { InboundBreakDownValidationGroup.class })
	private BigInteger pieces;

	@NgenAuditField(fieldName = NgenAuditFieldNameType.DAMAGEPCS)
	private BigDecimal damagePieces;

	@NgenAuditField(fieldName = NgenAuditFieldNameType.WGT)
	@CheckWeightConstraint(mandatory = MandatoryType.Type.NOTREQUIRED, groups = {
			InboundBreakDownValidationGroup.class })
	private BigDecimal weight = new BigDecimal(0.00, MathContext.DECIMAL64);

	private BigInteger deliveryOrderNo = BigInteger.ZERO;

	private BigInteger deliveryRequestOrderNo = BigInteger.ZERO;

	private String handlingMode;

	private String handlingArea;

	private String transferType;

	@NgenAuditField(fieldName = "Hand Carry")
	private Boolean handCarry = Boolean.FALSE;

	@NgenAuditField(fieldName = NgenAuditFieldNameType.ULDNUM)
	@NgenAuditEntityRef3(entityType = NgenAuditEntityType.ULD, parentEntityType = NgenAuditEntityType.AWB)
	@NgenAuditEntityValue3(entityType = NgenAuditEntityType.ULD, parentEntityType = NgenAuditEntityType.AWB)
	private String uldNumber;

	@NgenAuditField(fieldName = NgenAuditFieldNameType.PART_SUFFIX)
	private String partSuffix;

	private String warehouseHandlingInstruction;

	private BigInteger impArrivalManifestULDId;

	@NgenAuditField(fieldName = NgenAuditFieldNameType.ULDDAMAGE)
	private Boolean uldDamage = Boolean.FALSE;

	@NgenAuditField(fieldName = NgenAuditFieldNameType.SHIPNUM)
	private String shipmentNumber;

	@NgenAuditField(fieldName = NgenAuditFieldNameType.ORIGIN)
	private String origin;

	@NgenAuditField(fieldName = NgenAuditFieldNameType.DESTINATION)
	private String destination;
	
	@NgenAuditField(fieldName = NgenAuditFieldNameType.HAWB_NUMBER)
	@NgenAuditEntityValue6(entityType = NgenAuditEntityType.HAWB, parentEntityType = NgenAuditEntityType.AWB)
	@NgenAuditEntityRef6(entityType = NgenAuditEntityType.HAWB, parentEntityType = NgenAuditEntityType.AWB)
	private String hawbNumber;
	
	private BigInteger shipmentHouseAWBId;
	
	@NgenAuditField(fieldName = NgenAuditFieldNameType.INV_CHARGEABLE_WEIGHT)
	private BigDecimal chargeableWeight = new BigDecimal(0.00, MathContext.DECIMAL64);
	
	@NgenAuditField(fieldName = NgenAuditFieldNameType.HAWB_ORIGIN)
	private String hawbOrigin;

	@NgenAuditField(fieldName = NgenAuditFieldNameType.HAWB_DESTINATION)
	private String hawbDestination;
	
	@NgenAuditField(fieldName = NgenAuditFieldNameType.HAWB_PIECES)
	private BigInteger hawbPieces;
	
	@NgenAuditField(fieldName = NgenAuditFieldNameType.HAWB_WEIGHT)
	private BigDecimal hawbWeight = new BigDecimal(0.00, MathContext.DECIMAL64);


	private String weightAsString;

	@JsonSerialize(using = LocalDateSerializer.class)
	private LocalDate shipmentdate;

	private BigInteger consumedPieces;

	private BigDecimal consumedWeight;

	private Boolean isDeliveryInitiated = Boolean.FALSE;
	private Boolean isTrmintiated = Boolean.FALSE;

	private Boolean throughTransit = Boolean.FALSE;

	private Boolean hold = Boolean.FALSE;

	private String assignedUldTrolley;

	private BigInteger loaded = BigInteger.ZERO;

	@NgenAuditField(fieldName = NgenAuditFieldNameType.GROUP_LOCATION)
	private Boolean isGroupLocation = Boolean.FALSE;

	@NgenAuditField(fieldName = NgenAuditFieldNameType.SERVICE_CONTRACTOR_NAME)
	private String bdStaffGroupName;

	private List<InboundBreakdownShipmentHouseModel> house = new ArrayList<InboundBreakdownShipmentHouseModel>();

	@Valid
	@NgenAuditField(fieldName = NgenAuditFieldNameType.SHCLIST)
	private List<InboundBreakdownShipmentShcModel> shc = new ArrayList<InboundBreakdownShipmentShcModel>();

	private Boolean accessLocation = Boolean.FALSE;

}