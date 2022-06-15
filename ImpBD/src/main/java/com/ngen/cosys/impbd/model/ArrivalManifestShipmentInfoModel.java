package com.ngen.cosys.impbd.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ngen.cosys.JsonSerializer.LocalDateSerializer;
import com.ngen.cosys.JsonSerializer.LocalDateTimeSerializer;
import com.ngen.cosys.annotation.NgenCosysAppAnnotation;
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

@Getter
@Setter
@NoArgsConstructor
@NgenCosysAppAnnotation
@NgenAudit(entityFieldName = "shipmentNumber", entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.ARRIVAL_MANIFEST, repository = NgenAuditEventRepository.AWB)
public class ArrivalManifestShipmentInfoModel extends ShipmentModel {

	private static final long serialVersionUID = 1L;

	private BigInteger id;

	private BigInteger impArrivalManifestUldId;

	private BigInteger impArrivalManifestShipmentInfoId;

	// For Audit trail
	@NgenAuditField(fieldName = NgenAuditFieldNameType.SHIPMENTNUMBER)
	private String shipmentNumber;

	private String offloadReasonCode;

	private Boolean offloadedFlag = Boolean.FALSE;

	private BigInteger bookingFlightId;

	private BigInteger connectingFlightId;

	private BigInteger bookingFlightSegmentId;

	private BigInteger flightId;

	private BigInteger onwwardMovementId;

	private String onwardDestination;

	@NgenAuditField(fieldName = NgenAuditFieldNameType.ULDNUM)
	private String uldNumber;

	@NgenAuditField(fieldName = NgenAuditFieldNameType.FLTKEY)
	private String flightKey;

	@NgenAuditField(fieldName = NgenAuditFieldNameType.FLTDATE)
	@JsonSerialize(using = LocalDateSerializer.class)
	private LocalDate flightDate;

	private BigInteger flightSegmentId;

	private BigInteger exportWorkiglistId;

	private String createdUserId;

	private String incomingFlightBoardPoint;

	private String departureCarrierCode;
	private String departureFlightNumber;
	private String departureAirportCityCode;

	private String transferType;
	private String onwardMovement;

	// AISATS
	private String handledByDOMINT;

	@NgenAuditField(fieldName = NgenAuditFieldNameType.HANDLED_BY_MASTER_HOUSE)
	private String handledByMasterHouse;

	@JsonSerialize(using = LocalDateTimeSerializer.class)
	private LocalDateTime departureFlightDate;

	@JsonSerialize(using = LocalDateSerializer.class)
	private LocalDate createdDate;

	@Valid
	private List<ArrivalManifestShipmentDimensionInfoModel> dimensions;

	@Valid
	private List<ArrivalManifestShipmentOciModel> oci;

	@Valid
	private List<ArrivalManifestOtherServiceInfoModel> osi;

	@Valid
	private List<ArrivalManifestShipmentOnwardMovementModel> movementInfo = new ArrayList<ArrivalManifestShipmentOnwardMovementModel>();

	@Valid
	private List<ArrivalManifestByShipmentShcModel> shc;
	

	private boolean bookingRecordFound;
	private BigInteger totalPieces;
	private BigDecimal totalWeight;
}