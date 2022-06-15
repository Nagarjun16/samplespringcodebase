package com.ngen.cosys.inward.model;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.validation.Valid;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ngen.cosys.JsonSerializer.LocalDateSerializer;
import com.ngen.cosys.JsonSerializer.LocalDateTimeSerializer;
import com.ngen.cosys.annotation.NgenCosysAppAnnotation;
import com.ngen.cosys.audit.NgenAudit;
import com.ngen.cosys.audit.NgenAuditEntityType;
import com.ngen.cosys.audit.NgenAuditEntityValue2;
import com.ngen.cosys.audit.NgenAuditEventRepository;
import com.ngen.cosys.audit.NgenAuditEventType;
import com.ngen.cosys.audit.NgenAuditField;
import com.ngen.cosys.audit.NgenAuditFieldName.NgenAuditFieldNameType;
import com.ngen.cosys.model.FlightModel;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@ApiModel
@Getter
@Setter
@NoArgsConstructor
@NgenCosysAppAnnotation
@Validated
@NgenAudit(entityFieldName = "key", entityType = NgenAuditEntityType.FLIGHT, eventName = NgenAuditEventType.INWARD_SERVICE_REPORT, repository = NgenAuditEventRepository.FLIGHT)
@NgenAudit(entityFieldName = "shipmentNumber", eventName = NgenAuditEventType.INWARD_SERVICE_REPORT, repository = NgenAuditEventRepository.AWB, entityType = NgenAuditEntityType.AWB)
public class InwardServiceReportModel extends FlightModel {

	/**
	 * System generated serial version id
	 */
	private static final long serialVersionUID = -8461879863634731364L;

	private BigInteger id;

	private BigInteger inwardServiceReportId;

	@NgenAuditField(fieldName = NgenAuditFieldNameType.FLIGHTKEY)
	private String key;

	@NgenAuditEntityValue2(entityType = NgenAuditEntityType.FLIGHT, parentEntityType = NgenAuditEntityType.FLIGHT)
	@JsonSerialize(using = LocalDateSerializer.class)
	@NgenAuditField(fieldName = NgenAuditFieldNameType.FLT_DATE)
	private LocalDate date1;
	// @NotBlank(message = "Select mandatory field" , groups =
	// InwardServiceValidationGroup.class)
	private String segmentId;

	private String segmentdesc;

	private String serviceReportFor;
	@NgenAuditField(fieldName = NgenAuditFieldNameType.DOCCOMPBY)
	private String documentCompletedBy;
	@NgenAuditField(fieldName = NgenAuditFieldNameType.FINALIZE_DATE)
	private LocalDateTime finalizedate;
	// @NgenAuditField(fieldName = NgenAuditFieldNameType.FINALIZE_DATE)
	private LocalDateTime inwardServiceReportFinalizedAt = LocalDateTime.now();
	// @NgenAuditField(fieldName = NgenAuditFieldNameType.FINALIZED_BY)
	private String inwardServiceReportFinalizedBy;
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	private LocalDateTime finalizeCompletedAt;
	// @NgenAuditField(fieldName = NgenAuditFieldNameType.DOCCOMPAT)
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	private LocalDateTime documentCompletedAt;
	@NgenAuditField(fieldName = NgenAuditFieldNameType.BREAKDOWNCOMPBY)
	private String breakDownCompletedBy;
	@NgenAuditField(fieldName = NgenAuditFieldNameType.BREAKDOWNCOMPAT)
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	private LocalDateTime breakDownCompletedAt;
	@NgenAuditField(fieldName = NgenAuditFieldNameType.RAMPCHECKEDINBY)
	private String rampCheckedInBy;
	@NgenAuditField(fieldName = NgenAuditFieldNameType.RAMPCHECKEDINDATE)
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	private LocalDateTime rampCheckedInDate;
	private String cargoDamageCompletedBy;

	@JsonSerialize(using = LocalDateTimeSerializer.class)
	private LocalDateTime cargoDamageCompletedAt;
	@NgenAuditField(fieldName = NgenAuditFieldNameType.MANIFESTEDPAGES)
	private BigInteger manifestedPages;

	private boolean flag;
	@NgenAuditField(fieldName = NgenAuditFieldNameType.ACTIONTAKEN)
	private String actionTaken;
	@NgenAuditField(fieldName = NgenAuditFieldNameType.FINALIZED_STATUS)
	private String finalizeunfinalize;

	private String status;
	private String mailstatus;
	private String maildocumentCompletedBy;
	private String damagestatus;

	// private String emailAddress;

	private String[] emailAddress;
	private String[] emailAddressDamageCargo;

	@JsonSerialize(using = LocalDateTimeSerializer.class)
	private LocalDateTime maildocumentCompletedAt;

	private String mailbreakDownCompletedBy;

	@JsonSerialize(using = LocalDateTimeSerializer.class)
	private LocalDateTime mailbreakDownCompletedAt;

	private String mailrampCheckedInBy;

	@JsonSerialize(using = LocalDateTimeSerializer.class)
	private LocalDateTime mailrampCheckedInDate;

	@NgenAuditField(fieldName = NgenAuditFieldNameType.NATUREOFDISC)
	private String natureOfDiscrepancies;

	private Boolean checkstatus = Boolean.FALSE;

	@NgenCosysAppAnnotation
	@Valid
	@NgenAuditField(fieldName = "shipmentDiscrepancy")
	private List<InwardServiceReportShipmentDiscrepancyModel> shipmentDiscrepancy;

	@NgenCosysAppAnnotation
	@Valid
	@NgenAuditField(fieldName = "physicalDiscrepancy")
	private List<InwardServiceReportShipmentDiscrepancyModel> physicalDiscrepancy;
	@Valid
	// @NgenAuditField(fieldName = "otherDiscrepancy")
	private List<InwardServiceReportOtherDiscrepancyModel> otherDiscrepancy;

	private List<InwardSegmentModel> segment;
	@Valid
	private List<InwardSegmentConcatenate> segmentConcatAwb;

	private List<InwardSegmentConcatenate> segmentConcatPhy;

	private List<InwardSegmentConcatenate> segmentConcatOther;
	private String registration;
	private String hawbnumber;

}