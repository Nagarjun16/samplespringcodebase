package com.ngen.cosys.shipment.exportawbdocument.model;

import java.math.BigInteger;

import com.ngen.cosys.audit.NgenAuditField;
import com.ngen.cosys.audit.NgenAuditFieldName.NgenAuditFieldNameType;
import com.ngen.cosys.framework.model.BaseBO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ShipmentMasterRoutingInfoModel extends BaseBO{
	
	private static final long serialVersionUID = 1L;
	
	private BigInteger shipmentMasterRoutingId;
	
	private BigInteger shipmentId;
	
	@NgenAuditField(fieldName = NgenAuditFieldNameType.TO)
	private String fromPoint;
	
	@NgenAuditField(fieldName = NgenAuditFieldNameType.BY)
	private String carrier;
}
