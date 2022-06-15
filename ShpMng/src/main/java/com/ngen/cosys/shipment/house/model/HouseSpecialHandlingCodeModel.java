package com.ngen.cosys.shipment.house.model;

import java.math.BigInteger;

import com.ngen.cosys.audit.NgenAudit;
import com.ngen.cosys.audit.NgenAuditEntityType;
import com.ngen.cosys.audit.NgenAuditEventRepository;
import com.ngen.cosys.audit.NgenAuditEventType;
import com.ngen.cosys.audit.NgenAuditField;
import com.ngen.cosys.audit.NgenAuditFieldName.NgenAuditFieldNameType;
import com.ngen.cosys.framework.model.BaseBO;
import com.ngen.cosys.shipment.house.validator.HouseWayBillValidationGroup;
import com.ngen.cosys.validator.annotations.CheckSpecialHandlingCodeConstraint;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
/*
 * changes for bug-81 
 * */
@NgenAudit(eventName = NgenAuditEventType.MAINTAIN_HWB_LIST, repository = NgenAuditEventRepository.AWB, entityFieldName = "AWB Number", entityType = NgenAuditEntityType.AWB)
/*
 * changes end here for bug-81 
 * */
@NgenAudit(entityFieldName = "ShipmentNumber", entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.MAINTAIN_HOUSE, repository = NgenAuditEventRepository.AWB)
public class HouseSpecialHandlingCodeModel extends BaseBO {

	/**
	 * System generated serial version id
	 */
	private static final long serialVersionUID = -6437662744556868273L;

	private BigInteger houseId;
	private BigInteger id;

	@CheckSpecialHandlingCodeConstraint(groups = { HouseWayBillValidationGroup.class })
	@NgenAuditField(fieldName = NgenAuditFieldNameType.CODE)
	private String code;

}