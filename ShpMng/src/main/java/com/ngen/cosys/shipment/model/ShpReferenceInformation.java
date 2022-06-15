/**
 * 
 * ShpReferenceInformation.java
 * 
 * Copyright <PRE><IMG SRC = XX></IMG></PRE>
 *
 * Version      Date            Author      Reason
 * 1.0          29 January, 2018 NIIT      -
 */
package com.ngen.cosys.shipment.model;

import java.math.BigInteger;

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

/**
 * Model Class- ShpReferenceInformation
 * 
 * @author NIIT Technologies Ltd
 *
 */
@ApiModel
@Getter
@Setter
@NoArgsConstructor
@NgenAudit(entityFieldName = "awbNumber", entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.MAINTAIN_FWB, repository = NgenAuditEventRepository.AWB)
@NgenAudit(entityType = NgenAuditEntityType.AWB, entityFieldName = "awbNumber", eventName = NgenAuditEventType.NAWB_MANAGEMENT, repository = NgenAuditEventRepository.AWB)
public class ShpReferenceInformation extends BaseBO {

	private static final long serialVersionUID = 1L;
	private long shipmentFreightWayBillId;
	private long shipmentFreightWayBillShipmentReferenceInformationId;
	private BigInteger neutralAWBId;
	private BigInteger expNeutralAWBShipmentReferenceInfoId;
	
	@NgenAuditField(fieldName = NgenAuditFieldNameType.REFERENCE_NUMBER)
	private String referenceNumber;
	@NgenAuditField(fieldName = NgenAuditFieldNameType.SUPLIMENTORY)
	private String supplementaryShipmentInformation1;
	@NgenAuditField(fieldName = NgenAuditFieldNameType.SHIPMENT_INFO)
	private String supplementaryShipmentInformation2;

}
