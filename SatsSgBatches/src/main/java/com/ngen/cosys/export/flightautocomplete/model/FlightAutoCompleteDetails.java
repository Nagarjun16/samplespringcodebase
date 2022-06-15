package com.ngen.cosys.export.flightautocomplete.model;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ngen.cosys.JsonSerializer.LocalDateSerializer;
import com.ngen.cosys.JsonSerializer.LocalDateTimeSerializer;
import com.ngen.cosys.audit.NgenAudit;
import com.ngen.cosys.audit.NgenAuditEntityType;
import com.ngen.cosys.audit.NgenAuditEntityValue2;
import com.ngen.cosys.audit.NgenAuditEventRepository;
import com.ngen.cosys.audit.NgenAuditEventType;
import com.ngen.cosys.audit.NgenAuditField;
import com.ngen.cosys.audit.NgenAuditFieldName.NgenAuditFieldNameType;
import com.ngen.cosys.framework.model.BaseBO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@NgenAudit(entityFieldName = "flightKey", entityType = NgenAuditEntityType.FLIGHT, eventName = NgenAuditEventType.AUTO_FLIGHT_COMPLETE, repository = NgenAuditEventRepository.FLIGHT, entityRefFieldName = "flightKey")
public class FlightAutoCompleteDetails extends BaseBO {

	/**
	 * Default system generated version id
	 */
	private static final long serialVersionUID = 1L;

	
	private BigInteger flightId;

	private BigInteger configuredMinutes;

	@NgenAuditField(fieldName = NgenAuditFieldNameType.FLIGHTKEY)
	private String flightKey;

	private String carrierCode;
	private String boardPoint;
	private String offPoint;

	@NgenAuditField(fieldName = NgenAuditFieldNameType.FLIGHTDATE)
	 @NgenAuditEntityValue2(entityType = NgenAuditEntityType.FLIGHT, parentEntityType = NgenAuditEntityType.FLIGHT)
	private LocalDate flightOriginDate;

	private LocalDateTime dateSTD;

	private LocalDateTime currentDate;

	private boolean includeVolumeInFFM;

	private List<String> communicationEmails;

	private List<FlightAutoCompleteShipmentDetails> shipments;
	private List<FlightAutoCompleteShipmentDetails> shipmentsFfm;

	// for audit trial
	@NgenAuditField(fieldName = NgenAuditFieldNameType.FLIGHTCOMPLETEDBY)
	private String flightCompletedBy;
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@NgenAuditField(fieldName = NgenAuditFieldNameType.COMPLETEDON)
	private LocalDateTime flightCompletedOn;
        private String dipSvcSTATS;
}