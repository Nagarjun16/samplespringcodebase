package com.ngen.cosys.satssginterfaces.mss.model;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ngen.cosys.JsonSerializer.LocalDateSerializer;
import com.ngen.cosys.JsonSerializer.LocalDateTimeSerializer;
import com.ngen.cosys.JsonSerializer.STDETDDateTimeSerializer;
import com.ngen.cosys.audit.NgenAudit;
import com.ngen.cosys.audit.NgenAuditEntityType;
import com.ngen.cosys.audit.NgenAuditEventRepository;
import com.ngen.cosys.audit.NgenAuditEventType;
import com.ngen.cosys.audit.NgenAuditField;
import com.ngen.cosys.framework.model.BaseBO;
import com.ngen.cosys.validator.annotations.CheckManifestCompleteConstraint;
import com.ngen.cosys.validator.annotations.CheckValidFlightConstraint;
import com.ngen.cosys.validator.annotations.UserCarrierValidation;
import com.ngen.cosys.validator.enums.FlightType;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ApiModel
@XmlRootElement
@Component
@ToString
@Getter
@Setter
@NoArgsConstructor
@Validated

@NgenAudit(entityFieldName = "Flight Key", entityType =NgenAuditEntityType.FLIGHT, eventName = NgenAuditEventType.CARGOMANIFEST, entityRefFieldName = "flightId" ,repository = NgenAuditEventRepository.FLIGHT)
@NgenAudit(entityType = NgenAuditEntityType.FLIGHT, entityFieldName = "flightKey", eventName = NgenAuditEventType.FLIGHT_COMPLETE, repository = NgenAuditEventRepository.FLIGHT)
@NgenAudit(eventName = NgenAuditEventType.FINALIZE_DLS, repository = NgenAuditEventRepository.ULD, entityRefFieldName = "flightId", entityFieldName = "Flight Key", entityType = NgenAuditEntityType.FLIGHT)
@NgenAudit(eventName = NgenAuditEventType.UPDATEDLS, repository = NgenAuditEventRepository.FLIGHT, entityRefFieldName = "flightId", entityFieldName = "flightId", entityType = NgenAuditEntityType.FLIGHT)
@NgenAudit(entityFieldName = "shipmentNumber", entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.UNLOAD_SHIPMENT, repository = NgenAuditEventRepository.AWB)
public class Flight extends BaseBO {

	private static final long serialVersionUID = 1L;

	private BigInteger flightId;
	private BigInteger flightSegmentId;

	@NgenAuditField(fieldName = "Flight Key")
	private String flightKey;

	private int numbrOfZeroToRemove;

	private long separateCount;

	private String flightNumber;

	private String carrierCode;
	@NgenAuditField(fieldName = "Flight Date")
	@JsonSerialize(using = LocalDateSerializer.class)
	private LocalDate flightOriginDate;

	@NgenAuditField(fieldName = "Aircraft Registration")
	private String aircraftRegistration;

	@JsonSerialize(using = STDETDDateTimeSerializer.class)
	private LocalDateTime std;

	@JsonSerialize(using = STDETDDateTimeSerializer.class)
	private LocalDateTime etd;
	
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	private LocalDateTime etdDate;
	
	private String aircraftType;
	private String status;
	private String flightType;
	private int segmentId;
	private Boolean apron;
	private String routingInfo;
	private String notocFinalizeStatus;
	private boolean skipCpeCheck;

}