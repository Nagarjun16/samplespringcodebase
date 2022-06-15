package com.ngen.cosys.impbd.shipment.breakdown.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.validation.Valid;

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
import com.ngen.cosys.model.ShipmentModel;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@Validated
@NgenAudit(entityFieldName = "shipmentNumber", entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.INBOUND_BREAKDOWN, repository = NgenAuditEventRepository.AWB)
public class InboundBreakdownShipmentModel extends ShipmentModel {
	/**
	 * System Generated Serial Verison id
	 */
	private static final long serialVersionUID = 6022541580654061914L;
	
	private BigInteger inboundBreakdownId;
	private BigInteger id;
	private BigInteger flightId;
	private BigInteger shipmentVerificationId;
	private BigInteger shipmentInventoryId;
	
	private String hawbNumber;
	
	private BigDecimal awbChargeableWeight;
	
	@NgenAuditField(fieldName = NgenAuditFieldNameType.FLIGHTKEY)
	private String flightKey;
	
	@NgenAuditField(fieldName = NgenAuditFieldNameType.FLIGHTDATE)
	@JsonSerialize(using = LocalDateSerializer.class)
	private LocalDate flightOriginDate;

	private String handlingMode;
	private String transferType;
	private String uldNumber;
	private String breakdownStaffGroup;
	private String breakdownInstruction;
	private String warehouseHandlingInstruction;
	@NgenAuditField(fieldName = NgenAuditFieldNameType.MANIFESTEDPIECES)
	private BigInteger manifestPieces = BigInteger.ZERO;
	@NgenAuditField(fieldName = NgenAuditFieldNameType.MANIFESTEDWEIGHT)
	private BigDecimal manifestWeight=BigDecimal.ZERO;
	private BigInteger breakDownPieces = BigInteger.ZERO;
	private BigDecimal breakDownWeight=BigDecimal.ZERO;
	
	private BigInteger foundPieces;
	
	private BigInteger shipmentHouseId;
	
	private String houseNumber;
	
	private String houseBreakDownHandlingInstructions;

	@JsonSerialize(using = LocalDateTimeSerializer.class)
	private LocalDateTime breakDownStartDate;
	
	private LocalDateTime lastUpdatedTime;

	private Boolean handCarry = Boolean.FALSE;
	
	private Boolean intactContainer = Boolean.FALSE;
	private Boolean breakContainer = Boolean.FALSE;
	
	private Boolean expressFlag= Boolean.FALSE;
	
	private Boolean preBookedPieces= Boolean.FALSE;
	
	private Boolean uldDamage = Boolean.FALSE;
	
	private Boolean isDeliveryInitiated = Boolean.FALSE;

	private List<InboundBreakdownShipmentShcModel> shcs;
	
	private BigInteger totalUtilisedPieces = BigInteger.ZERO;

	private BigDecimal totalUtilisedWeight=BigDecimal.ZERO;
	
	private BigInteger totalBreakDownPieces = BigInteger.ZERO;

	private BigDecimal totalBreakDownWeight=BigDecimal.ZERO;

	private BigInteger irregularityPiecesFound = BigInteger.ZERO;

	private BigInteger irregularityPiecesMissing = BigInteger.ZERO;

	private BigInteger bookingPieces=BigInteger.ZERO;

	private BigDecimal bookingWeight=BigDecimal.ZERO;

	private List<SingleShipmentBookingBreakDown> bookingDetails;
	
	private BigInteger totalHousePieces=BigInteger.ZERO;
	
	private BigDecimal totalHouseWeight=BigDecimal.ZERO;
	
	private BigInteger totalShipmentHousesBreakDownPieces=BigInteger.ZERO;
	
	private BigDecimal totalShipmentHousesBreakDownWeight=BigDecimal.ZERO;
	
	private BigInteger totalHouseChargeableWeight=BigInteger.ZERO;	 
	
	@Valid
	@NgenAuditField(fieldName = NgenAuditFieldNameType.INVENTORY_LIST)
	private List<InboundBreakdownShipmentInventoryModel> inventory;
	
	private String bdstaffGroups;

}