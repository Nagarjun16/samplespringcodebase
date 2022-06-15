package com.ngen.cosys.impbd.model;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.validation.Valid;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ngen.cosys.JsonSerializer.LocalDateSerializer;
import com.ngen.cosys.JsonSerializer.LocalDateTimeSerializer;
import com.ngen.cosys.audit.NgenAudit;
import com.ngen.cosys.audit.NgenAuditEntityType;
import com.ngen.cosys.audit.NgenAuditEventRepository;
import com.ngen.cosys.audit.NgenAuditEventType;
import com.ngen.cosys.audit.NgenAuditField;
import com.ngen.cosys.audit.NgenAuditFieldName.NgenAuditFieldNameType;
import com.ngen.cosys.framework.model.BaseBO;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ApiModel
@Getter
@ToString
@Setter
@NoArgsConstructor
@NgenAudit(entityFieldName = "flightNumber", entityType = NgenAuditEntityType.FLIGHT, eventName = NgenAuditEventType.INBOUND_RAMP_CEHCKIN, repository = NgenAuditEventRepository.FLIGHT)
public class RampCheckInStatus extends BaseBO {

	private static final long serialVersionUID = 1L;
	private BigInteger impFlightEventsId;

	private BigInteger flightId;

	@NgenAuditField(fieldName = NgenAuditFieldNameType.FLIGHTKEY)
	private String flightNumber;

	@JsonSerialize(using = LocalDateSerializer.class)
	@NgenAuditField(fieldName = NgenAuditFieldNameType.FLT_DATE)
	private LocalDate flightDate;

	private BigInteger impRampCheckInId;

	private String rampCheckInCompletedBy;

	private String firstULDTowedBy;

	@JsonSerialize(using = LocalDateTimeSerializer.class)
	private LocalDateTime rampCheckInCompletedAt;
	
	@NgenAuditField(fieldName = NgenAuditFieldNameType.RAMP_CHECKIN_COMPLETED)
	private Boolean rampCheckInCompletedStatus;


	private String firstTimeRampCheckInCompletedBy;

	private String firstULDCheckedInBy;
	
	@Valid
	@NgenAuditField(fieldName = NgenAuditFieldNameType.ULD_INFO)
	private List<RampCheckInUld> uldList;

	@NgenAuditField(fieldName = NgenAuditFieldNameType.ULD_MANIFESTED)
	private String uldManifested;

	@NgenAuditField(fieldName = NgenAuditFieldNameType.ULD_RECEIVED)
	private String uldReceived;

	@NgenAuditField(fieldName = NgenAuditFieldNameType.TROLLEY_RECEIVED)
	private String trollyReceived;
}
