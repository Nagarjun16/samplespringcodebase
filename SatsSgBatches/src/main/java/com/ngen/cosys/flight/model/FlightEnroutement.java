/**
 * FlightEnroutement.java
 * 
 * Copyright <PRE><IMG SRC = XX></IMG></PRE>
 *
 * Version      Date         Author      Reason
 * 1.0          28 July, 2017   NIIT      -
 */
package com.ngen.cosys.flight.model;

import java.time.LocalDate;

import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ngen.cosys.JsonSerializer.LocalDateSerializer;
import com.ngen.cosys.audit.NgenAudit;
import com.ngen.cosys.audit.NgenAuditEntityType;
import com.ngen.cosys.audit.NgenAuditEventRepository;
import com.ngen.cosys.audit.NgenAuditEventType;
import com.ngen.cosys.audit.NgenAuditField;
import com.ngen.cosys.audit.NgenAudits;
import com.ngen.cosys.framework.model.BaseBO;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * This is the FlightEnroutement Model class.
 * 
 * @author NIIT Technologies Ltd
 * @version 1.0
 */
@XmlRootElement
@ApiModel
@Component
@ToString
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@NgenAudits({
		@NgenAudit(eventName = NgenAuditEventType.FLIGHT_DELETE_ENROUTEMENT, repository = NgenAuditEventRepository.FLIGHT, entityFieldName = "Carrier", entityType = NgenAuditEntityType.FLIGHT),
		@NgenAudit(eventName = NgenAuditEventType.FLIGHT_CREATE_ENROUTEMENT, repository = NgenAuditEventRepository.FLIGHT, entityFieldName = "Carrier", entityType = NgenAuditEntityType.FLIGHT),
		@NgenAudit(eventName = NgenAuditEventType.FLIGHT_UPDATE_ENROUTEMENT, repository = NgenAuditEventRepository.FLIGHT, entityFieldName = "Carrier", entityType = NgenAuditEntityType.FLIGHT) })
public class FlightEnroutement extends BaseBO {
	/**
	 * The default serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;
	private String enroutementId;
	@NgenAuditField(fieldName = "Carrier")
	private String carrierCode;

	@JsonSerialize(using = LocalDateSerializer.class)
	@NgenAuditField(fieldName = "From Date")
	private LocalDate periodFrom;

	@JsonSerialize(using = LocalDateSerializer.class)
	@NgenAuditField(fieldName = "To Date ")
	private LocalDate periodTo;
	@NgenAuditField(fieldName = "Via")
	private String via;
	@NgenAuditField(fieldName = "Service Type")
	private String serviceType;
	@NgenAuditField(fieldName = "Transfer")
	private String transfer;
	@NgenAuditField(fieldName = "Board Point")
	private String boardPointCode;
	@NgenAuditField(fieldName = "Final Destination")
	private String finalDestination;
	@NgenAuditField(fieldName = "Filter Condtion")
	private String filterCondtion;
}