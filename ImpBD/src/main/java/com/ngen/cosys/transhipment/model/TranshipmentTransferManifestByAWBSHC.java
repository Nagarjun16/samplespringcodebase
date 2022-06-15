package com.ngen.cosys.transhipment.model;

import java.math.BigInteger;

import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.validation.annotation.Validated;

import com.ngen.cosys.audit.NgenAudit;
import com.ngen.cosys.audit.NgenAuditEntityType;
import com.ngen.cosys.audit.NgenAuditEventRepository;
import com.ngen.cosys.audit.NgenAuditEventType;
import com.ngen.cosys.audit.NgenAuditField;
import com.ngen.cosys.audit.NgenAuditFieldName.NgenAuditFieldNameType;
import com.ngen.cosys.framework.model.BaseBO;
import com.ngen.cosys.transhipment.validator.group.TransferManifestByAWBMaintain;
import com.ngen.cosys.validator.annotations.CheckSpecialHandlingCodeConstraint;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@XmlRootElement
@ApiModel
@Setter
@Getter
@NoArgsConstructor
@Validated
@NgenAudit(entityFieldName = "shipmentNumber", entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.MAINTAIN_TRM_AWB, repository = NgenAuditEventRepository.AWB, entityRefFieldName = "shipmentDate")
@NgenAudit(entityFieldName = "shipmentNumber", entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.CANCEL_TRM_AWB, repository = NgenAuditEventRepository.AWB, entityRefFieldName = "shipmentDate")
@NgenAudit(entityFieldName = "shipmentNumber", entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.FINALIZE_TRM_AWB, repository = NgenAuditEventRepository.AWB, entityRefFieldName = "shipmentDate")
public class TranshipmentTransferManifestByAWBSHC extends BaseBO {

	/**
	 * System generated serial version id
	 */
	private static final long serialVersionUID = 1L;
	private BigInteger transTransferManifestByAWBInfoId;
	@NgenAuditField(fieldName = NgenAuditFieldNameType.SHCSS)
	@CheckSpecialHandlingCodeConstraint(groups = { TransferManifestByAWBMaintain.class })
	private String specialHandlingCode;

	private BigInteger transTransferManifestByAWBInfoSHCId;
}