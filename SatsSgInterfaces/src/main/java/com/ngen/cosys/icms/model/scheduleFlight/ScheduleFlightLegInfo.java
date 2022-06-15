package com.ngen.cosys.icms.model.scheduleFlight;


import java.math.BigInteger;

import org.springframework.stereotype.Component;

import com.ngen.cosys.audit.NgenAudit;
import com.ngen.cosys.audit.NgenAuditEntityType;
import com.ngen.cosys.audit.NgenAuditEventRepository;
import com.ngen.cosys.audit.NgenAuditEventType;
import com.ngen.cosys.audit.NgenAuditField;
import com.ngen.cosys.audit.NgenAuditFieldName.NgenAuditFieldNameType;
import com.ngen.cosys.framework.model.BaseBO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Model contains schedule flight leg informations
 */
@Component
@ToString
@Setter
@Getter
@NgenAudit(eventName = NgenAuditEventType.FLIGHT_MAINTAIN_SCHEDULE, repository = NgenAuditEventRepository.FLIGHT, entityFieldName = NgenAuditFieldNameType.FLIGHTKEY, entityType = NgenAuditEntityType.FLIGHT)
public class ScheduleFlightLegInfo extends BaseBO {
	private static final long serialVersionUID = 1L;
	
	private int flightSegmentOrder;
	@NgenAuditField(fieldName = NgenAuditFieldNameType.BOARDPOINT)
	private String origin;
	@NgenAuditField(fieldName = NgenAuditFieldNameType.DEPARTURETIME)
	private String departureTime;
	@NgenAuditField(fieldName = NgenAuditFieldNameType.ARRIVALTIME)
	private String arrivalTime;
	@NgenAuditField(fieldName = NgenAuditFieldNameType.OFFPOINT)
	private String destination;
	private int departureDayChange;
	private int arrivalDayChange;
	@NgenAuditField(fieldName = NgenAuditFieldNameType.SERVICETYPE)
	private String serviceType;
	@NgenAuditField(fieldName = NgenAuditFieldNameType.AIRCRAFTTYPE)
	private String aircraftType;
	private BigInteger flightScheduleId;
	private String STAUTC;
	private String STDUTC;
	private String createdUserId = "COSYS";
}
