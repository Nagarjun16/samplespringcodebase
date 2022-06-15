package com.ngen.cosys.shipment.awb.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;

import javax.validation.Valid;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ngen.cosys.JsonSerializer.LocalDateTimeSerializer;
import com.ngen.cosys.annotation.NgenCosysAppAnnotation;
import com.ngen.cosys.audit.NgenAudit;
import com.ngen.cosys.audit.NgenAuditEntityType;
import com.ngen.cosys.audit.NgenAuditEventRepository;
import com.ngen.cosys.audit.NgenAuditEventType;
import com.ngen.cosys.audit.NgenAuditField;
import com.ngen.cosys.audit.NgenAuditFieldName.NgenAuditFieldNameType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@NgenCosysAppAnnotation
@NgenAudit(entityFieldName = NgenAuditFieldNameType.SHIPMENTNUMBER, entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.AWB_DOCUMENT, repository = NgenAuditEventRepository.AWB, entityRefFieldName = "shipmentId")
public class AWB extends ShipmentModel {

	private static final long serialVersionUID = 1L;

	private BigInteger flightId;

	private BigInteger dispatchYear;

	@NgenAuditField(fieldName = NgenAuditFieldNameType.CUSTOMER_TYPE)
	private String customerType;

	@NgenAuditField(fieldName = NgenAuditFieldNameType.ORIGIN_OFFICE_EXCHANGE)
	private String originOfficeExchange;

	@NgenAuditField(fieldName = NgenAuditFieldNameType.MAIL_CATEGORY)
	private String mailCategory;

	@NgenAuditField(fieldName = NgenAuditFieldNameType.MAIL_SUB_CATEGORY)
	private String mailSubCategory;

	@NgenAuditField(fieldName = NgenAuditFieldNameType.DESTINATION_OFFICE_EXCHANGE)
	private String destinationOfficeExchange;

	private String fwb;
	private String fhl;

	@NgenAuditField(fieldName = NgenAuditFieldNameType.FLIGHTKEY)
	private String flightKey;

	@NgenAuditField(fieldName = NgenAuditFieldNameType.FLIGHT_BOARD_POINT)
	private String flightBoardPoint;

	@NgenAuditField(fieldName = NgenAuditFieldNameType.FLIGHT_OFF_POINT)
	private String flightOffPoint;

	@NgenAuditField(fieldName = NgenAuditFieldNameType.FLIGHT_TYPE)
	private String flightType;

	@NgenAuditField(fieldName = NgenAuditFieldNameType.OVCD_REASON_NUMBER)
	private String ovcdReasonCode;

	@NgenAuditField(fieldName = NgenAuditFieldNameType.CASE_NUMBER)
	private String caseNumber;

	@NgenAuditField(fieldName = NgenAuditFieldNameType.CHARGE_CODE)
	private String chargeCode;

	private String eawb;
	
	private BigInteger irregularityPieces;

	@NgenAuditField(fieldName = NgenAuditFieldNameType.ACTUAL_PIECES)
	private BigInteger actualPieces;

	private BigInteger oldPieces;

	private BigDecimal irregularityWeight;

	@NgenAuditField(fieldName = NgenAuditFieldNameType.ACTUAL_WEIGHT)
	private BigDecimal actualWeight;

	private BigDecimal oldWeight;

	private BigInteger breakDownPieces = BigInteger.ZERO;

	private BigDecimal breakDownWeight;

	private BigInteger foundPieces = BigInteger.ZERO;

	@NgenAuditField(fieldName = NgenAuditFieldNameType.NON_IATA)
	private Boolean nonIATA = false;

	@JsonSerialize(using = LocalDateTimeSerializer.class)
	private LocalDateTime departedOn;

	private Boolean svc = Boolean.FALSE;

	@NgenAuditField(fieldName = NgenAuditFieldNameType.PART_SHIPMENT)
	private Boolean partShipment = Boolean.FALSE;

	private Boolean registered = Boolean.FALSE;

	private Boolean manuallyCreated = Boolean.FALSE;

	private Boolean console = Boolean.FALSE;

	private Boolean docRecieved = Boolean.FALSE;

	private Boolean copyAwb = Boolean.FALSE;

	private Boolean checkList = Boolean.FALSE;

	private Boolean pouchRecieved = Boolean.FALSE;

	private Boolean isExport = Boolean.FALSE;

	private Boolean original = Boolean.FALSE;

	private Boolean photoCopy = Boolean.FALSE;

	private BigInteger rcfStatusUpdateEventPieces;

	@NgenAuditField(fieldName = NgenAuditFieldNameType.CC_FEE_PERCENTAGE)
	private BigDecimal ccFeeprecentage;

	private BigDecimal minccFee;

	@NgenAuditField(fieldName = NgenAuditFieldNameType.FLIGHTDATE)
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	private LocalDateTime flightDate;

	@NgenAuditField(fieldName = NgenAuditFieldNameType.DOC_RECEIVED_ON)
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	private LocalDateTime documentReceivedOn;

	@NgenAuditField(fieldName = NgenAuditFieldNameType.DOC_POUCH_RECEIVED_ON)
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	private LocalDateTime documentPouchReceivedOn;

	@NgenAuditField(fieldName = NgenAuditFieldNameType.PHOTO_COPY_RECEIVED_ON)
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	private LocalDateTime photoCopyReceivedOn;

	@NgenAuditField(fieldName = NgenAuditFieldNameType.FIRST_OFF_POINT)
	private String firstOffPoint;

	@NgenAuditField(fieldName = NgenAuditFieldNameType.FIRST_BOOKED_FLIGHT)
	private String firstBookedFlight;

	@NgenAuditField(fieldName = NgenAuditFieldNameType.RCAR_STATUS)
	private String rcarStatus;

	@NgenAuditField(fieldName = NgenAuditFieldNameType.ACCEPTANCE_TYPE)
	private String acceptanceType;

	private BigInteger directConsigneeCustomerId;

	private BigInteger directShipperCustomerId;

	@NgenAuditField(fieldName = NgenAuditFieldNameType.DOC_TYPE)
	private String documentType;

	@NgenAuditField(fieldName = NgenAuditFieldNameType.DOC_DATE)
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	private LocalDateTime documentDate;

	private Boolean oldPouchRecieved = Boolean.FALSE;

	/*
	 * Document consignee information
	 */
	@Valid
	@NgenCosysAppAnnotation
	@NgenAuditField(fieldName = NgenAuditFieldNameType.CONSIGNEE)
	private ShipmentMasterCustomerInfo consignee;

	/*
	 * Document shipper information
	 */
	@Valid
	@NgenCosysAppAnnotation
	@NgenAuditField(fieldName = NgenAuditFieldNameType.SHIPPER)
	private ShipmentMasterCustomerInfo shipper;

	@NgenAuditField(fieldName = NgenAuditFieldNameType.NOTIFY)
	private ShipmentMasterCustomerInfo alsoNotify;
	/*
	 * 
	 * Document charge info
	 */
	@NgenAuditField(fieldName = NgenAuditFieldNameType.OTHER_CHARGE_INFO)
	private ShipmentOtherChargeInfo otherChargeInfo;

	/*
	 * Document handling area
	 */
	// @NgenAuditField(fieldName = NgenAuditFieldNameType.HANDLIING_AREA)
	private ShipmentMasterHandlingArea handlingArea;

	/*
	 * Document local authority/customs information
	 */
	@Valid
	@NgenAuditField(fieldName = NgenAuditFieldNameType.LOCAL_AUTHORITY)
	private List<ShipmentMasterLocalAuthorityInfo> localAuthority;

	/*
	 * Shipment routing info
	 */
	@NgenAuditField(fieldName = NgenAuditFieldNameType.ROUTING)
	private List<ShipmentMasterRoutingInfo> routing;

	/*
	 * Shipment shcs
	 */
	@NgenAuditField(fieldName = NgenAuditFieldNameType.SHC)
	private List<ShipmentMasterShc> shcs;

	/*
	 * Shipment shc handling group
	 */
	// @NgenAuditField(fieldName = NgenAuditFieldNameType.SHC_GROUP)
	private List<ShipmentMasterShcHandlingGroup> shcHandlingGroup;

	/*
	 * Shipment Special Service Remarks
	 */
	@Valid
	@NgenAuditField(fieldName = NgenAuditFieldNameType.SSR_REMARKS)
	private List<ShipmentRemarksModel> ssrRemarksList;

	/*
	 * Shipment Other Service Information
	 */
	@Valid
	@NgenAuditField(fieldName = NgenAuditFieldNameType.OSI_REMARKS)
	private List<ShipmentRemarksModel> osiRemarksList;

	/*
	 * General remarks
	 */
	@Valid
	@NgenAuditField(fieldName = NgenAuditFieldNameType.GENERAL_REMARKS)
	private List<ShipmentRemarksModel> generalRemarks;

	private Boolean isLastUpdatedDateTimeExist = Boolean.FALSE;

	private Boolean shipmentDelivered = Boolean.FALSE;

	private Boolean poIssued = Boolean.FALSE;

	@NgenAuditField(fieldName = NgenAuditFieldNameType.SHIPMENTNUMBER)
	private String shipmentNumberForAuditTrail;

	@NgenAuditField(fieldName = NgenAuditFieldNameType.CHANGE_TO_TRANSHIPMENT_ON)
	private LocalDateTime changeToTranshipmentOn;

	private LocalDateTime lastUpdatedOn;

	@NgenAuditField(fieldName = NgenAuditFieldNameType.LAST_UPDATED_USER)
	private String lastUpdatedUserCode;

	private BigDecimal roundUpValue = BigDecimal.ZERO;
	
	private BigInteger deliveredPieces = BigInteger.ZERO;
	
	private BigDecimal deliveredWeight = BigDecimal.ZERO;
	//AISATS 
	private String handledByDOMINT;
	
	private String handledByMasterHouse;
	@NgenAuditField(fieldName = NgenAuditFieldNameType.AWB_CHARGEABLE_WEIGHT)
	private BigDecimal chargeableWeight;
	
	@NgenAuditField(fieldName = NgenAuditFieldNameType.COU_TO_COMMERCIAL_FLAG)
	private Boolean couToCommercial = Boolean.FALSE;
	
	@NgenAuditField(fieldName = NgenAuditFieldNameType.COU_TO_COMMERCIAL_CHANGE_DATE)
	private LocalDateTime couToCommercialDate;
	
	private String shipmentTypeConvertFrom;
	
	private Boolean awbOnHold= Boolean.FALSE;
	
	private LocalDateTime CancelledOn;

	private Boolean shipmentInventoryFlag = Boolean.FALSE;
	
	private Boolean shipmentIrregularityFlag = Boolean.FALSE;
	
	private Boolean shipmentFreightOutFlag = Boolean.FALSE;
	
	private Boolean handlingChangeFlag = Boolean.TRUE;
}