package com.ngen.cosys.impbd.workinglist.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ngen.cosys.JsonSerializer.LocalDateSerializer;
import com.ngen.cosys.JsonSerializer.LocalDateTimeSerializer;
import com.ngen.cosys.annotation.InjectRequestTime;
import com.ngen.cosys.annotation.InjectSegment;
import com.ngen.cosys.audit.NgenAudit;
import com.ngen.cosys.audit.NgenAuditEntityType;
import com.ngen.cosys.audit.NgenAuditEventRepository;
import com.ngen.cosys.audit.NgenAuditEventType;
import com.ngen.cosys.audit.NgenAuditField;
import com.ngen.cosys.audit.NgenAuditFieldName.NgenAuditFieldNameType;
import com.ngen.cosys.model.FlightModel;
import com.ngen.cosys.validator.annotations.UserCarrierValidation;
import com.ngen.cosys.validators.BreakDownWorkListValidationGroup;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * BreakDownWorkingListModel represents the Arrival manifest details of a
 * Flight.
 * 
 * 
 * @author NIIT Technologies Ltd
 * @version 1.0
 *
 */
@XmlRootElement
@ApiModel
@Setter
@Getter
@NoArgsConstructor
@NgenAudit(entityFieldName = "flightNumber", entityType = NgenAuditEntityType.FLIGHT, eventName = NgenAuditEventType.FLIGHT_COMPLETE, repository = NgenAuditEventRepository.FLIGHT)
@NgenAudit(entityFieldName = "flightNumber", entityType = NgenAuditEntityType.FLIGHT, eventName = NgenAuditEventType.BREAKDOWN_COMPLETE, repository = NgenAuditEventRepository.FLIGHT)
@UserCarrierValidation(shipmentNumber = "", flightKey = "flightNumber", loggedInUser = "loggedInUser", type = "FLIGHT", groups = {BreakDownWorkListValidationGroup.class })
public class BreakDownWorkingListModel extends FlightModel {

	/**
	 * System Generated Serial Version id
	 */
	private static final long serialVersionUID = 8972621865563427008L;
	private String boardPoint;
	private String aircraftType;
	private String flightRemarks;
	private String status;
	
	@NgenAuditField(fieldName = NgenAuditFieldNameType.BREAKDOWN_COMPLETED)
	private Boolean breakdownComplete;
	
	private String weatherCondition;
	private String carrierCode;
	
	@JsonSerialize(using = LocalDateSerializer.class)
	private LocalDate departureTime;

	@Size(max = 12, message = "ERROR_FLIGHT_NUMBER_SHOULD_BE_8_CHARACTER")
	@NgenAuditField(fieldName = NgenAuditFieldNameType.FLIGHTKEY)
	private String flightNumber;

	@JsonSerialize(using = LocalDateSerializer.class)
	@NgenAuditField(fieldName = NgenAuditFieldNameType.FLT_DATE)
	private LocalDate flightDate;

	@InjectSegment(flightDate = "flightDate", flightId = "", flightNumber = "flightNumber", flightType = "I", segment = "segment")
	private String segment;

	@NgenAuditField(fieldName = NgenAuditFieldNameType.LASTUPDATEDUSERCODE)
	private String lastupdatedBy;

	@InjectRequestTime
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@NgenAuditField(fieldName = NgenAuditFieldNameType.LASTUPDATEDON)
	private LocalDateTime lastmodifiedOn;
		
	@NgenAuditField(fieldName = NgenAuditFieldNameType.FLIGHT_COMPLETE)
	private Boolean flightCompleted;
	
	private Boolean documentCompleted;
	
	private String customSubmission;
	
	private String customSubmissionAcknowledgement;
	
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	private LocalDateTime firstTimeBreakDownCompletedAt;
	
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	private LocalDateTime flightCompletedDate;
	  

	private List<BreakDownWorkingListSegmentModel> breakDownWorkingListSegment;
	private List<BreakDownWorkingListShipmentResult> breakDownWorkingListShipmentResult;
	private List<BreakDownWorkingListShipmentMismatchPieces> shipmentMismatchPieces;
	
}