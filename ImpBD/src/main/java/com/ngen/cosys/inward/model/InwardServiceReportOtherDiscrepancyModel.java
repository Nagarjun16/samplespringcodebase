package com.ngen.cosys.inward.model;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ngen.cosys.JsonSerializer.LocalDateSerializer;
import com.ngen.cosys.audit.NgenAudit;
import com.ngen.cosys.audit.NgenAuditEntityType;
import com.ngen.cosys.audit.NgenAuditEntityValue2;
import com.ngen.cosys.audit.NgenAuditEventRepository;
import com.ngen.cosys.audit.NgenAuditEventType;
import com.ngen.cosys.audit.NgenAuditField;
import com.ngen.cosys.audit.NgenAuditFieldName.NgenAuditFieldNameType;
import com.ngen.cosys.framework.model.BaseBO;
import com.ngen.cosys.validator.annotations.CheckRemarksConstraint;
import com.ngen.cosys.validator.enums.MandatoryType;
import com.ngen.cosys.validators.InwardServiceValidationGroup;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@NgenAudit(entityFieldName = "key",eventName = NgenAuditEventType.INWARD_SERVICE_REPORT, repository = NgenAuditEventRepository.FLIGHT,  entityType = NgenAuditEntityType.FLIGHT)
public class InwardServiceReportOtherDiscrepancyModel extends BaseBO {

	/**
	 * System generated serial version id
	 */
	private static final long serialVersionUID = -3633593615343841971L;
	 @NgenAuditField(fieldName = NgenAuditFieldNameType.FLIGHT)
	   private String key;
	   
	   @NgenAuditEntityValue2(entityType = NgenAuditEntityType.FLIGHT, parentEntityType = NgenAuditEntityType.FLIGHT)
	   @JsonSerialize(using = LocalDateSerializer.class)
	   @NgenAuditField(fieldName = NgenAuditFieldNameType.FLT_DATE)
	   private LocalDate date1;
	private BigInteger id;
//	@NgenAuditField(fieldName = "inwardServiceReportId")
	private BigInteger inwardServiceReportId;
    private Boolean Manual = Boolean.FALSE;
	@NgenAuditField(fieldName = NgenAuditFieldNameType.RMKS)
	@CheckRemarksConstraint(mandatory = MandatoryType.Type.NOTREQUIRED, groups={InwardServiceValidationGroup.class})
	private String remarks;
	//@NgenAuditField(fieldName = NgenAuditFieldNameType.ROUTING)
	private List<InwardServiceReportModel> segmentArray;
	private BigInteger Sid;
    private Boolean manual;
	private Boolean checkBox = Boolean.FALSE;

}