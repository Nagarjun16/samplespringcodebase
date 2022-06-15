package com.ngen.cosys.shipment.exportawbdocument.model;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ngen.cosys.JsonSerializer.LocalDateSerializer;
import com.ngen.cosys.annotation.InjectShipmentDate;
import com.ngen.cosys.audit.NgenAudit;
import com.ngen.cosys.audit.NgenAuditEntityType;
import com.ngen.cosys.audit.NgenAuditEventRepository;
import com.ngen.cosys.audit.NgenAuditEventType;
import com.ngen.cosys.audit.NgenAuditField;
import com.ngen.cosys.audit.NgenAuditFieldName.NgenAuditFieldNameType;
import com.ngen.cosys.framework.model.BaseBO;
import com.ngen.cosys.shipment.validators.SaveAWBDocument;
import com.ngen.cosys.validator.annotations.CheckRemarksConstraint;
import com.ngen.cosys.validator.enums.MandatoryType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@NgenAudit(entityFieldName = NgenAuditFieldNameType.SHIPMENTNUMBER, entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.EXPORT_AWB_DOCUMENT, repository = NgenAuditEventRepository.AWB)
public class ShipmentRemarksModel extends BaseBO{
	
	private static final long serialVersionUID = 1L;
	
	private BigInteger shipmentRemark_Id;
	
	private String shipmentNumber;
	
	@JsonSerialize(using = LocalDateSerializer.class)
	@InjectShipmentDate(shipmentNumberField = "shipmentNumber")
	private LocalDate shipmentDate;
	
	private BigInteger shipmentId;
	
	private String remarkType;
	
	private BigInteger flightId;
	
	@NgenAuditField(fieldName = NgenAuditFieldNameType.REMARKS)
	@CheckRemarksConstraint(mandatory = MandatoryType.Type.NOTREQUIRED, groups = {SaveAWBDocument.class })
	private String shipmentRemarks;

	private String shipmentType;
	
	private String houseWayBillNumber;
	
	private String houseNumber;
}
