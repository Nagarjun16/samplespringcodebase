package com.ngen.cosys.report.service.poi.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.xml.bind.annotation.XmlRootElement;

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
@NgenAudit(eventName = NgenAuditEventType.SPECIALCARGO_REQUEST, repository = NgenAuditEventRepository.AWB, entityFieldName = "AWB number", entityType = NgenAuditEntityType.AWB)
@NgenAudit(entityType = NgenAuditEntityType.FLIGHT, entityFieldName = "flightKey", eventName = NgenAuditEventType.SPECIALCARGO_REQUEST, repository = NgenAuditEventRepository.FLIGHT)
public class SpecialCargoRequest extends BaseBO{
	/**
	 * 
	 */
	private static final long serialVersionUID = 523434928734339882L;
	 private BigInteger specialCargoRequestId;
	 private BigInteger requestBookingId;
	 private BigInteger requestFlightBookingId;
	 private BigInteger requestInventoryId;
     private BigInteger requestShipmentId;
	 private BigInteger requestInventoryPieces;
	 private BigDecimal requestInventoryWeight;
	 @NgenAuditField(fieldName = NgenAuditFieldNameType.SHIPMENTINVENTORYLOCATION)
	 private String inventoryLocation;
	 @NgenAuditField(fieldName = NgenAuditFieldNameType.REQUESTPIECES)
	 private BigInteger requestPieces;
	 private BigDecimal requestWeight;
	 @NgenAuditField(fieldName = NgenAuditFieldNameType.REQUESTLOCATION)
	 private String requestLocation;
	 @JsonSerialize(using = LocalDateTimeSerializer.class)
	 private LocalDateTime requestDateTime;
	// @NgenAuditField(fieldName = NgenAuditFieldNameType.REQUESTED_BY)
	 private String requestBy;
	 @NgenAuditField(fieldName = NgenAuditFieldNameType.FLIGHTKEY)
	 private String flightKey;
	 @NgenAuditField(fieldName = NgenAuditFieldNameType.FLIGHTDATE)
	 @JsonSerialize(using = LocalDateSerializer.class)
	 private LocalDate flightDate;
	 private BigInteger requestId;
	 private String requestSummary;
	 private BigInteger flightId;
	 @NgenAuditField(fieldName = NgenAuditFieldNameType.AWBNUMBER)
	 private String shipmentNumber;
	 @NgenAuditField(fieldName = NgenAuditFieldNameType.SEGMENT)
	 private String segment;
	 @NgenAuditField(fieldName = NgenAuditFieldNameType.EXPECTEDDATETIME)
	 @JsonSerialize(using = LocalDateTimeSerializer.class)
	 private LocalDateTime expDateTime;
	 @NgenAuditField(fieldName = NgenAuditFieldNameType.PARTSUFFIX)
	 private String partsuffix;
	
	 //for audit trail
	 @NgenAuditField(fieldName = NgenAuditFieldNameType.TIMESTAMP)
	 @JsonSerialize(using = LocalDateTimeSerializer.class)
	  private LocalDateTime timeStamp;
	 
	 private String shipmentInventoryShipmentLocation;
	 private String shcGroup;
	 private String handOverLocForReq;
	
	

}
