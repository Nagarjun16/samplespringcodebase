package com.ngen.cosys.inward.model;

import java.math.BigInteger;
import java.util.List;

import org.springframework.validation.annotation.Validated;

import com.ngen.cosys.annotation.NgenCosysAppAnnotation;
import com.ngen.cosys.audit.NgenAudit;
import com.ngen.cosys.audit.NgenAuditEntityType;
import com.ngen.cosys.audit.NgenAuditEventRepository;
import com.ngen.cosys.audit.NgenAuditEventType;
import com.ngen.cosys.audit.NgenAuditField;
import com.ngen.cosys.audit.NgenAuditFieldName.NgenAuditFieldNameType;
import com.ngen.cosys.model.ShipmentModel;
import com.ngen.cosys.validator.annotations.CheckRemarksConstraint;
import com.ngen.cosys.validator.enums.MandatoryType;
import com.ngen.cosys.validators.InwardServiceValidationGroup;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@NgenCosysAppAnnotation
@Validated
@NgenAudit(entityFieldName = "shipmentNumber",eventName = NgenAuditEventType.INWARD_SERVICE_REPORT, repository = NgenAuditEventRepository.AWB,  entityType = NgenAuditEntityType.AWB)
public class InwardServiceReportShipmentDiscrepancyModel extends ShipmentModel {

	/**
	 * System generated serial version id
	 */
	private static final long serialVersionUID = -6450314767235761907L;

	private BigInteger id;
	private BigInteger inwardServiceReportId;

	private Boolean partShipment = Boolean.FALSE;
	@NgenAuditField(fieldName = NgenAuditFieldNameType.DISCREPANCY_TYPE)
	private String discrepancyType;
	@NgenAuditField(fieldName = NgenAuditFieldNameType.IRREGULARITY_TYPE)
	private String irregularityType;
	@NgenAuditField(fieldName = NgenAuditFieldNameType.IRREGULARITY_PIECES)
	private BigInteger irregularityPieces;
	 @CheckRemarksConstraint(mandatory = MandatoryType.Type.NOTREQUIRED,groups={InwardServiceValidationGroup.class})
	@NgenAuditField(fieldName = NgenAuditFieldNameType.IRREGULARITY_DESCRIPTION)
	private String irregularityDescription;
	@NgenAuditField(fieldName = NgenAuditFieldNameType.REMARKS)
	@CheckRemarksConstraint(mandatory = MandatoryType.Type.NOTREQUIRED,groups={InwardServiceValidationGroup.class})
	private String remarks;
    private Boolean manual;
    private Boolean checkBox = Boolean.FALSE;
    @NgenAuditField(fieldName = NgenAuditFieldNameType.MAIL_TYPE)
    private String mailType;
    
    private String dataTypes;
    private List<InwardServiceReportModel> segmentArray;
    
    private String hawbnumber;

}