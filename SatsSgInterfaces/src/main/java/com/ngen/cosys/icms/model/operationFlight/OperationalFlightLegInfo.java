package com.ngen.cosys.icms.model.operationFlight;

import java.math.BigInteger;

import javax.validation.Valid;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

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

@ToString
@Setter
@Getter
@Valid
@NgenAudit(eventName = NgenAuditEventType.FLIGHT_MAINTAIN_SCHEDULE, repository = NgenAuditEventRepository.FLIGHT, entityFieldName = NgenAuditFieldNameType.FLIGHTKEY, entityType = NgenAuditEntityType.FLIGHT)
public class OperationalFlightLegInfo extends BaseBO  {
	private static final long serialVersionUID = 1L;
	@NgenAuditField(fieldName = NgenAuditFieldNameType.BOARDING_POINT)
	private String legOrigin;
	
	@NgenAuditField(fieldName = NgenAuditFieldNameType.OFFPOINT)
	private String legDestination;
	
	private String dateSTA;
	private String STAUTC;
	private String dateSTD;
	private String STDUTC;
	private String dateETD;
	
	@NgenAuditField(fieldName = NgenAuditFieldNameType.AIRCRAFTTYPE)
	private String aircraftType;
	
	@NgenAuditField(fieldName = NgenAuditFieldNameType.SERVICETYPE)
	private String serviceType;
	private BigInteger flightId;
	private int flightSegmentOrder;
	private String domesticStatus;
	private String aircraftRegistrationNo;
	private String carrier;
	private String flightKey;
	private String flightNumber;
	private Boolean isDeleteLeg=false;
	
	private String staDate;
	private String staTime;
	private String stdDate;
	private String stdTime;
	
	private String createdUserId = "COSYS";
	private String modifiedUserId = "COSYS";
	

}
