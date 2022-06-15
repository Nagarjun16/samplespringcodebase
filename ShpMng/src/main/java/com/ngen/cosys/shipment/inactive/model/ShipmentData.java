package com.ngen.cosys.shipment.inactive.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ngen.cosys.JsonSerializer.LocalDateSerializer;
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
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@ToString
@NgenAudit(entityFieldName = NgenAuditFieldNameType.SHIPMENTNUMBER, entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.INACTIVE_OLD_CARGO, repository = NgenAuditEventRepository.AWB)
public class ShipmentData extends BaseBO {
	/**
	 * System generated serial version id
	 */
	private static final long serialVersionUID = 1L;

	private BigInteger shipmentId;

	private BigInteger deliveryId;

	@NgenAuditField(fieldName = NgenAuditFieldNameType.ORIGIN)
	private String origin;

	@NgenAuditField(fieldName = NgenAuditFieldNameType.DESTINATION)
	private String destination;

	@NgenAuditField(fieldName = NgenAuditFieldNameType.SHIPMENTNUMBER)
	private String awbNumber;

	@NgenAuditField(fieldName = NgenAuditFieldNameType.REMARKS)
	private String remarks;

	@NgenAuditField(fieldName = NgenAuditFieldNameType.REMARK_TYPE)
	private String remarkType;

	@NgenAuditField(fieldName = NgenAuditFieldNameType.FLIGHTKEY)
	private String flightkey;

	@NgenAuditField(fieldName = NgenAuditFieldNameType.FLIGHTDATE)
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	private LocalDateTime flightDate;

	private BigInteger flightId;

	@NgenAuditField(fieldName = NgenAuditFieldNameType.DELIVERYORDER_NUMBER)
	private String deliveryOrderNo;

	@NgenAuditField(fieldName = NgenAuditFieldNameType.PIECES)
	private BigInteger pieces;

	@NgenAuditField(fieldName = NgenAuditFieldNameType.WEIGHT)
	private BigDecimal weight;

	private String agent;

	@NgenAuditField(fieldName = NgenAuditFieldNameType.SHIPMENTDATE)
	@JsonSerialize(using = LocalDateSerializer.class)
	private LocalDate shipmentDate;

	private String shipmentType;

	@NgenAuditField(fieldName = NgenAuditFieldNameType.CHARGECODE)
	private String chargeCode;

	private String groundHandlingCode;

	@NgenAuditField(fieldName = NgenAuditFieldNameType.TRM_NUMBER)
	private String trmNumber;

	@NgenAuditField(fieldName = NgenAuditFieldNameType.TRM_DATE)
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	private LocalDateTime trmDate;

	private String natureOfGoodsDescription;
	
    private BigInteger totalPieces;

    private BigDecimal totalWeight;
    
    private String poStatus;
    
    private String carrier;
	
    private String flightOffPoint;
    
    private BigInteger shipmentHouseId;
    
    private String hawbnumber;
    private BigInteger totalHousePieces;
    private BigInteger totalShipmentPieces;
    private BigInteger totalFreightoutShipmentPieces;
    private BigInteger totalFreightoutHousePieces;
    
}