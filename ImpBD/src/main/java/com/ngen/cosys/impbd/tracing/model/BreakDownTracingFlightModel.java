package com.ngen.cosys.impbd.tracing.model;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ngen.cosys.JsonSerializer.LocalDateSerializer;
import com.ngen.cosys.annotation.InjectSegment;
import com.ngen.cosys.annotation.InjectShipmentDate;
import com.ngen.cosys.annotation.NgenCosysAppAnnotation;
import com.ngen.cosys.audit.NgenAudit;
import com.ngen.cosys.audit.NgenAuditEntityType;
import com.ngen.cosys.audit.NgenAuditEventRepository;
import com.ngen.cosys.audit.NgenAuditEventType;
import com.ngen.cosys.audit.NgenAuditField;
import com.ngen.cosys.audit.NgenAuditFieldName.NgenAuditFieldNameType;
import com.ngen.cosys.impbd.instruction.validator.BreakDownHandlingInstructionValidationGroup;
import com.ngen.cosys.impbd.summary.validator.BreakDownSummaryValidation;
import com.ngen.cosys.model.FlightModel;
import com.ngen.cosys.validator.annotations.CheckContainerNumberConstraint;
import com.ngen.cosys.validator.annotations.CheckShipmentNumberConstraint;
import com.ngen.cosys.validator.enums.MandatoryType;
import com.ngen.cosys.validators.ArrivalManifestAdditionalInfo;
import com.ngen.cosys.validators.ArrivalManifestValidationGroup;
import com.ngen.cosys.validators.InwardSearchValidationGroup;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@XmlRootElement
@ApiModel
@Getter
@Setter
@NoArgsConstructor
@NgenCosysAppAnnotation

public class BreakDownTracingFlightModel extends FlightModel {

	/**
	 * System generated serial version id
	 */
	private static final long serialVersionUID = 2297252195630362399L;

	@SuppressWarnings("deprecation")
	@NotBlank(message = "ERROR_FLIGHT_NUMBER_REQUIRED", groups = { InwardSearchValidationGroup.class,
			ArrivalManifestValidationGroup.class, BreakDownSummaryValidation.class })
	@Size(max = 12, message = "ERROR_FLIGHT_NUMBER_SHOULD_BE_8_CHARACTER")
	@NgenAuditField(fieldName = NgenAuditFieldNameType.FLIGHTKEY)
	private String flightNumber;

	@NotNull(message = "ERRRO_FLIGHT_DATE_REQUIRED", groups = { InwardSearchValidationGroup.class,
			BreakDownSummaryValidation.class })
	@JsonSerialize(using = LocalDateSerializer.class)
	@NgenAuditField(fieldName = NgenAuditFieldNameType.FLT_DATE)
	private LocalDate flightDate;

	@CheckShipmentNumberConstraint(mandatory = "MandatoryType.Type.NOTREQUIRED")
	private String shipmentNumber;

	@InjectShipmentDate(shipmentNumberField = "shipmentNumber")
	@JsonSerialize(using = LocalDateSerializer.class)
	private LocalDate shipmentDate;

	@CheckContainerNumberConstraint(mandatory = MandatoryType.Type.NOTREQUIRED, groups = {
			ArrivalManifestValidationGroup.class })
	private String uldNumber;

	private String flightkey;

	private String flightStatus;

	private String boardPoint;

	private BigInteger flightSegmentId;

	@InjectSegment(flightNumber = "flightNumber", flightDate = "flightDate", flightType = "I", segment = "segment", flightId = "")
	private String segment;

	private List<BreakDownTracingShipmentModel> shipments;

	private List<BreakDownTracingFlightSegmentModel> segmentData;

	private List<BreakDownTracingUldModel> uldData;

}
