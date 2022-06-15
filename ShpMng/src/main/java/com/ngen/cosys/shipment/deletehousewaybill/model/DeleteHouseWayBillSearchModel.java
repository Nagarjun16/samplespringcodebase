package com.ngen.cosys.shipment.deletehousewaybill.model;

import java.math.BigInteger;
import java.time.LocalDate;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ngen.cosys.JsonSerializer.LocalDateSerializer;
import com.ngen.cosys.annotation.InjectShipmentDate;
import com.ngen.cosys.audit.NgenAudit;
import com.ngen.cosys.audit.NgenAuditEntityType;
import com.ngen.cosys.audit.NgenAuditEventRepository;
import com.ngen.cosys.audit.NgenAuditEventType;
/*import com.ngen.cosys.audit.NgenAuditEntityRef2;
import com.ngen.cosys.audit.NgenAuditEntityType;
import com.ngen.cosys.audit.NgenAuditEventType;*/
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
//@CheckShipmentUserAccessConstraint(ShipmentNumber = "shipmentNumber",skipAccessCheck=true)
@NgenAudit(eventName = NgenAuditEventType.DELETE_HOUSE_WAY_BILL, repository = NgenAuditEventRepository.AWB, entityFieldName = "AWB Number", entityType = NgenAuditEntityType.AWB)
public class DeleteHouseWayBillSearchModel extends BaseBO {
	/**
	 * System Generated Serial Verison id
	 */
	private static final long serialVersionUID = 6022541580654061914L;

	private String shipmentNumber;
	
	@NgenAuditField(fieldName = "House Number")
	//@NgenAuditEntityRef2(eventName = NgenAuditEventType.MAINTAIN_HOUSE, entityType = NgenAuditEntityType.HAWB, parentEntityType = NgenAuditEntityType.AWB)
	private String hawbNumber;
	private String remarks;
	private String shipmentType;
	@InjectShipmentDate(shipmentNumberField = "shipmentNumber")
	@JsonSerialize(using = LocalDateSerializer.class)
	private LocalDate shipmentDate;
	private BigInteger shipmentId;
}
