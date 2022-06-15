package com.ngen.cosys.impbd.model;

import java.math.BigInteger;
import java.time.LocalDateTime;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ngen.cosys.JsonSerializer.LocalDateTimeSerializer;
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

@Setter
@Getter
@NoArgsConstructor
@NgenAudit(entityFieldName = "shipmentNumber",  entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.CDH_DOC_VERIFICATION, repository = NgenAuditEventRepository.AWB)
public class CdhDocumentmaster extends BaseBO {
	/**
	 * System generated UID
	 */
	private static final long serialVersionUID = 1L;

	private BigInteger cdhdocumentmasterid;

	@NgenAuditField(fieldName = NgenAuditFieldNameType.COPY_NUM)
	private int copyno;

	@NgenAuditField(fieldName = NgenAuditFieldNameType.DOCUMENT)
	private String documentstatus;

	private BigInteger cdhpigeonholelocationid;

	private BigInteger cdhflightpouchid;

	@JsonSerialize(using = LocalDateTimeSerializer.class)
	private LocalDateTime storeddate;

	@NgenAuditField(fieldName = NgenAuditFieldNameType.RECEIVED_DATE)
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	private LocalDateTime receiveddate;

	@JsonSerialize(using = LocalDateTimeSerializer.class)
	private LocalDateTime returneddate;

	@JsonSerialize(using = LocalDateTimeSerializer.class)
	private LocalDateTime notifieddate;

	private Boolean discrepancyflag;

	private String remarks;

	private String destination;

	private String deletereasoncode;

	private Boolean deleteflag;
	
	private String deleteremarks;

	private String carriercode;

	private String flightoffpoint;

	@JsonSerialize(using = LocalDateTimeSerializer.class)
	private LocalDateTime labelpartdate;

	private String createdusercode;

	@JsonSerialize(using = LocalDateTimeSerializer.class)
	private LocalDateTime createddatetime;

	private String lastupdatedusercode;

	@JsonSerialize(using = LocalDateTimeSerializer.class)
	private LocalDateTime lastupdateddatetime;

	private BigInteger shipmentid;
	
	@NgenAuditField(fieldName = NgenAuditFieldNameType.AWB_NUMBER)
	private String shipmentNumber;
	
	@NgenAuditField(fieldName = NgenAuditFieldNameType.LOCATION_NAME)
	private String locationName;
	
	@NgenAuditField(fieldName = NgenAuditFieldNameType.PRINTER_NAME)
	private String printerForAT;
}