package com.ngen.cosys.impbd.shipment.breakdown.model;

import java.math.BigInteger;

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
@NgenAudit(entityFieldName = NgenAuditFieldNameType.SHIPMENTNUMBER, entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.INBOUND_BREAKDOWN, repository = NgenAuditEventRepository.AWB)
public class InboundBreakdownShipmentShcModel extends BaseBO {
	/**
	 * System Generated Serial Version id
	 */
	private static final long serialVersionUID = -75719742529682581L;

	private BigInteger inboundBreakDownShipmentId;
	private BigInteger shipmentInventoryId;
	
	@NgenAuditField(fieldName = NgenAuditFieldNameType.SHCSS)
	private String specialHandlingCode;
	
	@NgenAuditField(fieldName = NgenAuditFieldNameType.SHIPMENTNUMBER)
	private String bhShipmentNumber;



}