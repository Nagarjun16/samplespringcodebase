package com.ngen.cosys.shipment.exportawbdocument.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.validation.Valid;

import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ngen.cosys.JsonSerializer.LocalDateSerializer;
import com.ngen.cosys.JsonSerializer.LocalDateTimeSerializer;
import com.ngen.cosys.annotation.InjectShipmentDate;
import com.ngen.cosys.audit.NgenAudit;
import com.ngen.cosys.audit.NgenAuditEntityType;
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
@NgenAudit(entityFieldName = NgenAuditFieldNameType.SHIPMENTNUMBER, entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.EXPORT_AWB_DOCUMENT, repository = NgenAuditEventRepository.AWB, entityRefFieldName = "shipmentId")
public class ExportAwbDocumentModel extends BaseBO{
	private static final long serialVersionUID = 1L;
	
	private BigInteger shipmentId;
	
	@NgenAuditField(fieldName = NgenAuditFieldNameType.SHIPMENTTYPE)
	private String shipmentType;
	
	@NgenAuditField(fieldName = NgenAuditFieldNameType.SHIPMENTNUMBER)
	@NotBlank(message = "data.mandatory.required")
	private String shipmentNumber;
	
	@NgenAuditField(fieldName = NgenAuditFieldNameType.SHIPMENTDATE)
	@InjectShipmentDate(shipmentNumberField = "shipmentNumber")
	@JsonSerialize(using = LocalDateSerializer.class)
	private LocalDate shipmentDate;

	private Boolean nonIATA= false;
	
	private Boolean svc= false;
	
	@NgenAuditField(fieldName = NgenAuditFieldNameType.ORIGIN)
	private String origin;
	
	@NgenAuditField(fieldName = NgenAuditFieldNameType.DESTINATION)
	private String destination;
	
	@NgenAuditField(fieldName = NgenAuditFieldNameType.PIECES)
	private BigInteger pieces;
	
	@NgenAuditField(fieldName = NgenAuditFieldNameType.WEIGHT)
	private BigDecimal weight;
	
	private BigDecimal chargeableWeight;
	
	@NgenAuditField(fieldName = NgenAuditFieldNameType.WEIGHTCODE)
	private String weightUnitCode;
	
	@NgenAuditField(fieldName = NgenAuditFieldNameType.NATUREOFGOODS)
	private String natureOfGoodsDescription;

	private String handledByDOMINT;
	
	@NgenAuditField(fieldName = NgenAuditFieldNameType.CHARGE_CODE)
	private String chargeCode;
	
	//private String customsOrigin;
	@NgenAuditField(fieldName = NgenAuditFieldNameType.CARRIER)
	private String carrierCode;
	
	@NgenAuditField(fieldName = NgenAuditFieldNameType.FIRST_BOOKED_FLIGHT)
	private String firstBookedFlight;
	
	private BigInteger flightId;
	
	@NgenAuditField(fieldName = NgenAuditFieldNameType.FLIGHTDATE)
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	private LocalDateTime firstBookedFlightDate;
	
	@NgenAuditField(fieldName = NgenAuditFieldNameType.DOC_POUCH_RECEIVED_ON)
	private Boolean pouchReceived= false;
		
	private Boolean checkListReceived= false;
	
	private String requestedTemperatureRange;
	
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	private LocalDateTime acceptedDateTime;
	
	private String acceptanceType;
	
	private Boolean accepted;
	
	private Boolean finalizeWeight;
	
	@NgenAuditField(fieldName = NgenAuditFieldNameType.SHC)
	private List<ShipmentMasterSHCModel> specialHandlingCodeList;
	
	private List<AcceptanceInfoModel> acceptanceInfoList;
	
	@NgenAuditField(fieldName = NgenAuditFieldNameType.ROUTING)
	@Valid
	private List<ShipmentMasterRoutingInfoModel> routing;
	
	@NgenAuditField(fieldName = NgenAuditFieldNameType.SHIPPER)
	@Valid
	private ShipmentMasterCustomerInfoModel shipper;
	
	@NgenAuditField(fieldName = NgenAuditFieldNameType.CONSIGNEE)
	@Valid
	private ShipmentMasterCustomerInfoModel consignee;
	
	@NgenAuditField(fieldName = NgenAuditFieldNameType.OSI_REMARKS)
	@Valid
	private List<ShipmentRemarksModel> otherServiceInformationList;

	@NgenAuditField(fieldName = NgenAuditFieldNameType.GENERAL_REMARKS)
	@Valid
	private List<ShipmentRemarksModel> generalRemarksList;

	@NgenAuditField(fieldName = NgenAuditFieldNameType.SSR_REMARKS)
	@Valid
	private List<ShipmentRemarksModel> specialServiceRequestList;
	
	
}
