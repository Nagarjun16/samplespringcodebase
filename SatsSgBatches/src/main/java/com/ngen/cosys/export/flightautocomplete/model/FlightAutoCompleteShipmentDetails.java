package com.ngen.cosys.export.flightautocomplete.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

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

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@NgenAudit(entityFieldName = "shipmentNumber", entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.AUTO_FLIGHT_COMPLETE, repository = NgenAuditEventRepository.AWB)
public class FlightAutoCompleteShipmentDetails extends BaseBO {

	/**
	 * Default system generated serial version id
	 */
	private static final long serialVersionUID = 1L;

	private BigInteger shipmentId;
	private BigInteger flightId;
	private BigInteger shipmentInventoryId;
	private BigInteger shipmentFreightOutId;

	@NgenAuditField(fieldName = NgenAuditFieldNameType.TOTALPIECES)
	private BigInteger shipmentPieces;

	private BigInteger manifestedPieces;

	@NgenAuditField(fieldName = NgenAuditFieldNameType.FREIGHTOUTPIECES)
	private BigInteger freightOutPieces;

	@NgenAuditField(fieldName = NgenAuditFieldNameType.FLIGHTKEY)
	@NgenAuditEntityValue2(entityType = NgenAuditEntityType.FLIGHT, parentEntityType = NgenAuditEntityType.AWB)
	@NgenAuditEntityRef2(entityType = NgenAuditEntityType.FLIGHT, parentEntityType = NgenAuditEntityType.AWB)
	private String flightKey;

	private String shipmentType;
	
	@NgenAuditField(fieldName = NgenAuditFieldNameType.SHIPMENT_NUMBER)
	private String shipmentNumber;

	@NgenAuditField(fieldName = NgenAuditFieldNameType.ORIGIN)
	private String origin;

	@NgenAuditField(fieldName = NgenAuditFieldNameType.DESTINATION)
	private String destination;

	@NgenAuditField(fieldName = NgenAuditFieldNameType.NATUREOFGOODS)
	private String natureOfGoodsDescription;

	private String flightBoardPoint;

	@NgenAuditField(fieldName = NgenAuditFieldNameType.OFFPOINT)
	private String flightOffPoint;

	@NgenAuditField(fieldName = NgenAuditFieldNameType.TOTALWEIGHT)
	private BigDecimal shipmentWeight;

	private BigDecimal manifestWeight;

	@NgenAuditField(fieldName = NgenAuditFieldNameType.FREIGHTOUTWEIGHT)
	private BigDecimal freightOutWeight;

	private LocalDate shipmentDate;

	@NgenAuditField(fieldName = NgenAuditFieldNameType.FLIGHTDATE)
	@NgenAuditEntityValue3(entityType = NgenAuditEntityType.FLIGHT, parentEntityType = NgenAuditEntityType.AWB)
	@NgenAuditEntityRef3(entityType = NgenAuditEntityType.FLIGHT, parentEntityType = NgenAuditEntityType.AWB)
	private LocalDateTime flightDate;

	// List of house
	private List<FlightAutoCompleteShipmentHouseDetails> houses;

	// List of inventory id's
	private List<BigInteger> inventoryIds;

	private BigDecimal chargeableWeight = BigDecimal.ZERO;
	private BigDecimal usedChargeableWeight = BigDecimal.ZERO;
	private BigInteger totalConsignmentPieces = BigInteger.ZERO;
	private BigDecimal totalConsignmentWeight = BigDecimal.ZERO;
	private BigInteger manifestShipmentInfoId;
	private Volume volume;

}