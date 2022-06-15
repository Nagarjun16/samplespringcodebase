package com.ngen.cosys.icms.model.operationFlight;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.ngen.cosys.annotation.NgenCosysAppAnnotation;
import com.ngen.cosys.audit.NgenAudit;
import com.ngen.cosys.audit.NgenAuditEntityRef2;
import com.ngen.cosys.audit.NgenAuditEntityType;
import com.ngen.cosys.audit.NgenAuditEventRepository;
import com.ngen.cosys.audit.NgenAuditEventType;
import com.ngen.cosys.audit.NgenAuditField;
import com.ngen.cosys.audit.NgenAuditFieldName;
import com.ngen.cosys.audit.NgenAuditFieldName.NgenAuditFieldNameType;
import com.ngen.cosys.framework.model.BaseBO;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@NgenCosysAppAnnotation
@NgenAudit(eventName = NgenAuditEventType.FLIGHT_MAINTAIN_SCHEDULE, repository = NgenAuditEventRepository.FLIGHT, entityFieldName = NgenAuditFieldNameType.FLIGHTKEY, entityType = NgenAuditEntityType.FLIGHT)
@ToString
@Setter
@Getter
public class OperationalFlightInfo extends BaseBO {

	private static final long serialVersionUID = 1L;
	private int referenceId;

	@NgenAuditField(fieldName = NgenAuditFieldName.NgenAuditFieldNameType.CARRIER)
	private String carrier;
	@NgenAuditField(fieldName = NgenAuditFieldNameType.FLIGHT_TYPE)
	private String flightType;
	private String flightNo;

	@NgenAuditField(fieldName = NgenAuditFieldName.NgenAuditFieldNameType.FLIGHTDATEORIGIN)
	@NgenAuditEntityRef2(parentEntityType = NgenAuditEntityType.FLIGHT, entityType = NgenAuditEntityType.FLIGHT, eventName = NgenAuditEventType.FLIGHT_MAINTAIN_SCHEDULE) 
	private LocalDate flightDate;

	@NgenAuditField(fieldName = NgenAuditFieldName.NgenAuditFieldNameType.FLIGHTKEY)
	private String flightKey;
	@NgenAuditField(fieldName = NgenAuditFieldNameType.BOARDPOINT)
	private String loadingPoint;

	private boolean bookingExists;

	@NgenAuditField(fieldName = NgenAuditFieldName.NgenAuditFieldNameType.AIRCRAFTREGISTRATION)
	private String aircraftRegistrationNo;
	private String previousAircraftRegCode;

	private String siType = "I";

	@NgenAuditField(fieldName = NgenAuditFieldName.NgenAuditFieldNameType.ETD)
	private String etd;
	private LocalDateTime flightDateSta;

	@NgenAuditField(fieldName = NgenAuditFieldNameType.OFFPOINT)
	private String firstPointArrival;

// Added ASM message
	@NgenAuditField(fieldName = NgenAuditFieldNameType.SERVICETYPE)
	private String serviceType;
	@NgenAuditField(fieldName = NgenAuditFieldNameType.AIRCRAFTTYPE)
	private String aircraftType;
	@NgenAuditField(fieldName = NgenAuditFieldNameType.REMARKSFORASM)
	private List<String> remarksForASM;
	@NgenAuditField(fieldName = NgenAuditFieldNameType.TECHNICALSTOP)
	private boolean technicalStop;
	@NgenAuditField(fieldName = NgenAuditFieldNameType.NOFREIGHT)
	private boolean noFreight;
	@NgenAuditField(fieldName = NgenAuditFieldNameType.TERMINALS)
	private List<String> handlingArea;

	private String dateSTD;
	private String dateSTA;

	private BigInteger bookingId;

	private BigInteger flightId;

	private String flightSegmentId;

	private String messageByFlightId;

	private int flightSegmentOrder;

	@NgenAuditField(fieldName = NgenAuditFieldNameType.STATUS)
	private String status;

	private String flightStatus;
	@NgenCosysAppAnnotation
	@NgenAuditField(fieldName = NgenAuditFieldNameType.FLIGHTLEGS)
	private List<OperationalFlightLegInfo> flightLegInfo;
	
	@NgenCosysAppAnnotation
	@NgenAuditField(fieldName = NgenAuditFieldNameType.FLIGHTSEGMENTS)
	private List<OperationalFlightSegmentInfo> flightSegmentInfo;
	
	private String aircraftBodyType;

	private String flightRemarks;

	private String impFlightEventsId;

	private String expFlightEventsId;

	private String inboundAircraftRegNo;
	private String outboundAircraftRegNo;
	@NgenAuditField(fieldName = NgenAuditFieldNameType.FLIGHTAUTOCOMPLETE)
	private String flightAutoCompleteFlag;
	private String autoFlightFlag;
	private String flightCancelFag;
	private String closedForBooking;
	private String createdUserId = "COSYS";
	private String modifiedUserId = "COSYS";
	private Boolean changedByASM = true;
	

}