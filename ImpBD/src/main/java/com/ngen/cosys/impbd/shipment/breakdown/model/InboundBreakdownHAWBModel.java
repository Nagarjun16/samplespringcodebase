package com.ngen.cosys.impbd.shipment.breakdown.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ngen.cosys.JsonSerializer.LocalDateSerializer;
import com.ngen.cosys.JsonSerializer.LocalDateTimeSerializer;
import com.ngen.cosys.annotation.InjectShipmentDate;
import com.ngen.cosys.audit.NgenAudit;
import com.ngen.cosys.audit.NgenAuditEntityRef6;
import com.ngen.cosys.audit.NgenAuditEntityType;
import com.ngen.cosys.audit.NgenAuditEntityValue6;
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
@Validated
@NgenAudit(entityFieldName = "shipmentNumber", entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.INBOUND_BREAKDOWN, repository = NgenAuditEventRepository.AWB)
public class InboundBreakdownHAWBModel extends BaseBO {
	/**
	 * default
	 */
	private static final long serialVersionUID = 1L;

	private BigInteger shipmentId;

	@NgenAuditField(fieldName = NgenAuditFieldNameType.SHIPMENTNUMBER)
	private String shipmentNumber;

	@InjectShipmentDate(shipmentNumberField = "shipmentNumber")
	@JsonSerialize(using = LocalDateSerializer.class)
	@NgenAuditField(fieldName = NgenAuditFieldNameType.SHIPMENTDATE)
	private LocalDate shipmentdate;

	private String shipmentType;

	private BigInteger shipmentHouseId;

	@NgenAuditEntityValue6(entityType = NgenAuditEntityType.HAWB, parentEntityType = NgenAuditEntityType.AWB)
	@NgenAuditEntityRef6(entityType = NgenAuditEntityType.HAWB, parentEntityType = NgenAuditEntityType.AWB)
	private String hawbNumber;

	@NgenAuditField(fieldName = NgenAuditFieldNameType.ORIGIN)
	private String hawbOrigin;

	@NgenAuditField(fieldName = NgenAuditFieldNameType.DESTINATION)
	private String hawbDestination;

	@NgenAuditField(fieldName = NgenAuditFieldNameType.PIECES)
	private BigInteger hawbPieces;

	@NgenAuditField(fieldName = NgenAuditFieldNameType.WEIGHT)
	private BigDecimal hawbWeight;

	private BigDecimal hawbChargebleWeight;

	private String weightUnitCode;

	private String hawbNatureOfGoods;

	private Boolean hawbManuallyCreated;

	private Boolean hawbLocked;

	private String lockedReason;

	private BigInteger uldLoadCount;

	@JsonSerialize(using = LocalDateTimeSerializer.class)
	private LocalDateTime deliverOn;

	private List<InboundBreakdownHAWBShcModel> hawbShcs;

}
