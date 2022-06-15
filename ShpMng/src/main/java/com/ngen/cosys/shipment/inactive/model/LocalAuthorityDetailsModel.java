package com.ngen.cosys.shipment.inactive.model;

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
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@ToString
@NgenAudit(entityFieldName = NgenAuditFieldNameType.SHIPMENTNUMBER, entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.INACTIVE_OLD_CARGO, repository = NgenAuditEventRepository.AWB)
public class LocalAuthorityDetailsModel extends BaseBO {

	/**
	 * default
	 */
	private static final long serialVersionUID = 1L;
	
	private BigInteger localAutorityInfoId;

	@NgenAuditField(fieldName = "Reference Number")
	private String referenceNumber;
	
	@NgenAuditField(fieldName = NgenAuditFieldNameType.CONSIGNEECODE)
	private BigInteger customerAppAgentId;
	
	private String license;
	
	private String remarks;

}
