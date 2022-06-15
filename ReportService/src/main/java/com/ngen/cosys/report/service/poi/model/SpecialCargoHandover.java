package com.ngen.cosys.report.service.poi.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ngen.cosys.JsonSerializer.LocalDateTimeSerializer;
import com.ngen.cosys.annotation.NgenCosysAppAnnotation;
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

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ApiModel
@XmlRootElement
@ToString
@Setter
@Getter
@NoArgsConstructor
@NgenCosysAppAnnotation
@NgenAudit(eventName = NgenAuditEventType.SPECIALCARGO_HANDOVER, repository = NgenAuditEventRepository.AWB, entityFieldName = "AWB number", entityType = NgenAuditEntityType.AWB)
public class SpecialCargoHandover extends BaseBO {
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;
	private BigInteger specialCargoHandoverId;
	private BigInteger specialCargoRequestId;
	private BigInteger handoverBookingId;
	private BigInteger handoverFlightBookingId;
	private BigInteger handoverInventoryId;
	private BigInteger handoverShipmentId;
	@NgenAuditField(fieldName = NgenAuditFieldNameType.HANDOVERPIECES)
	private BigInteger handoverPieces;
	@NgenAuditField(fieldName = NgenAuditFieldNameType.HANDOVERWEIGHT)
	private BigDecimal handoverWeight;
	@NgenAuditField(fieldName = NgenAuditFieldNameType.HANDOVERLOCATION)
	private String handoverLocation;
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@NgenAuditField(fieldName = NgenAuditFieldNameType.HANDOVERDDATETIME)
	private LocalDateTime handoverDateTime;
	@NgenAuditField(fieldName = NgenAuditFieldNameType.HANDOVERBY)
	private String handoverBy;
	private BigInteger flightId;
	// for audit Trail purpose adding below three fields
	@NgenAuditField(fieldName = NgenAuditFieldNameType.AWBNUMBER)
	private String shipmentNumber;
	@NgenAuditEntityRef2(entityType = NgenAuditEntityType.FLIGHT, parentEntityType = NgenAuditEntityType.AWB)
	@NgenAuditEntityValue2(entityType = NgenAuditEntityType.FLIGHT, parentEntityType = NgenAuditEntityType.AWB)
	@NgenAuditField(fieldName = NgenAuditFieldNameType.FLIGHT)
	private String flightKey;
	@NgenAuditEntityRef3(entityType = NgenAuditEntityType.FLIGHT, parentEntityType = NgenAuditEntityType.AWB)
	@NgenAuditEntityValue3(entityType = NgenAuditEntityType.FLIGHT, parentEntityType = NgenAuditEntityType.AWB)
	@NgenAuditField(fieldName = NgenAuditFieldNameType.FLIGHTDATE)
	private LocalDate flightDate;
	@NgenAuditField(fieldName = NgenAuditFieldNameType.SEGMENT)
	private String segment;

	// For Inserting Handover to Login Id and Staff Id
	@NgenAuditField(fieldName = NgenAuditFieldNameType.HANDOVERTOCCSTAFFID)
	private String handOverToCCStaffId;
	@NgenAuditField(fieldName = NgenAuditFieldNameType.HANDOVERTOLOGINID)
	private String handoverToLoginId;
	@NgenAuditField(fieldName = NgenAuditFieldNameType.STAFFNAME)
	private String staffName;

	// Audit Table for Monitering Screen
	private BigInteger specialCargoHandoverHdrId;
	private BigInteger specialCargoHandoverDtlId;
	private String fromLoc;
	private BigInteger reqPc;
	private String reqBy;
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	private LocalDateTime reqTime;
	private String handoverToStaffId;
	private List<SpecialCargoSHC> handOverShcList;
	private String handOverShcForDisplay;
	private String flight;
	private BigInteger shipmentInventoryBookingId;
	private BigInteger shipmentInventoryFlightBookingId;
	private BigInteger shipmentInventoryId;
	private BigInteger shipmentInventoryShipmentId;

	private BigInteger bookingId;
	private BigInteger flightBookingId;
	private BigInteger shipmentId;
	private BigInteger partBookingId;
	private BigInteger flightPartBookingId;
	@NgenAuditField(fieldName = NgenAuditFieldNameType.PARTSUFFIX)
	private String partsuffix;
	private String shcGroup;

	@NgenAuditField(fieldName = NgenAuditFieldNameType.SHIPMENTINVENTORYLOCATION)
	private String shipmentInventoryShipmentLocation;
	@NgenAuditField(fieldName = NgenAuditFieldNameType.REQUESTPIECES)
	private BigInteger requestPieces;
	 //for audit trail
	 @NgenAuditField(fieldName = NgenAuditFieldNameType.TIMESTAMP)
	 @JsonSerialize(using = LocalDateTimeSerializer.class)
	  private LocalDateTime timeStamp;
	private LocalDate shipmentDate;
	// for Image
	private List<SpecialCargoHandover> imageSection;
	private BigInteger uploadedDocId;
	private String documentName;
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	private LocalDateTime dateForFileUpload;
	private String userNameForFileUpload;
	private String locationForFileUpload;
	private String identityKeyForImage;
	private String document;
	private boolean uLDFlag;
}
