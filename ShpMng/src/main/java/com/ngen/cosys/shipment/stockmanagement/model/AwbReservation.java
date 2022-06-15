package com.ngen.cosys.shipment.stockmanagement.model;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ngen.cosys.JsonSerializer.LocalDateSerializer;
import com.ngen.cosys.audit.NgenAudit;
import com.ngen.cosys.audit.NgenAuditEntityType;
import com.ngen.cosys.audit.NgenAuditEventRepository;
import com.ngen.cosys.audit.NgenAuditEventType;
import com.ngen.cosys.audit.NgenAuditField;
import com.ngen.cosys.audit.NgenAuditFieldName.NgenAuditFieldNameType;
import com.ngen.cosys.framework.model.BaseBO;
import com.ngen.cosys.validator.annotations.CheckValidFlightConstraint;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Model class for Awb Reservation.
 * 
 * @author NIIT Technologies Ltd
 * @version 1.0
 */
@ApiModel
@XmlRootElement
@ToString
@Getter
@Setter
@NoArgsConstructor
@CheckValidFlightConstraint(type = "EXPORT", number = "flightKey", originDate = "flightDate")
@NgenAudit(entityFieldName = NgenAuditFieldNameType.SHIPMENTNUMBER, entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.AWB_RESERVATION, repository = NgenAuditEventRepository.AWB)
public class AwbReservation extends BaseBO {

	private static final long serialVersionUID = 1L;
	private BigInteger awbStockReservationId;
	private BigInteger awbStockDetailsId;
	private BigInteger ShipperId;

	@NgenAuditField(fieldName = NgenAuditFieldNameType.ORIGIN)
	private String origin;

	@NgenAuditField(fieldName = NgenAuditFieldNameType.DESTINATION)
	private String destination;

	@NgenAuditField(fieldName = NgenAuditFieldNameType.FLIGHTKEY)
	private String flightKey;

	@NgenAuditField(fieldName = NgenAuditFieldNameType.FLIGHTDATE)
	@JsonSerialize(using = LocalDateSerializer.class)
	private LocalDate flightDate;

	private BigInteger terminalId;
	private BigInteger core;

	@NgenAuditField(fieldName = NgenAuditFieldNameType.SHIPPERNAME)
	private String shipperName;

	@NgenAuditField(fieldName = NgenAuditFieldNameType.SHIPMENTNUMBER)
	private String awbNumber;

	private String status;
	private String segment;

	@JsonFormat(pattern = "ddMMMyyyy HH:mm")
	private LocalDateTime std;

	private String staff;
	
	@JsonFormat(pattern = "ddMMMyyyy HH:mm")
	private LocalDateTime reservationDate;

	private boolean selectFlg;

	@NgenAuditField(fieldName = NgenAuditFieldNameType.CARRIER)
	private String carrierCode;

	@NgenAuditField(fieldName = NgenAuditFieldNameType.TERMINAL)
	private String terminalDescription;
	
	@NgenAuditField(fieldName = NgenAuditFieldNameType.CORE)
	private String coreDescription;
	
	@NgenAuditField(fieldName = NgenAuditFieldNameType.STOCK_TYPE)
	private String stockDescription;

	@NgenAuditField(fieldName = NgenAuditFieldNameType.SHIPPERCODE)
	private String shipperCode;
}