/**
 * OperativeFlightExp.java
 * 
 * Copyright <PRE><IMG SRC = XX></IMG></PRE>
 *
 * Version      Date         Author      Reason
 * 1.0          28 July, 2017   NIIT      -
 */
package com.ngen.cosys.flight.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ngen.cosys.JsonSerializer.LocalDateTimeSerializer;
import com.ngen.cosys.audit.NgenAudit;
import com.ngen.cosys.audit.NgenAuditEntityType;
import com.ngen.cosys.audit.NgenAuditEventRepository;
import com.ngen.cosys.audit.NgenAuditEventType;
import com.ngen.cosys.audit.NgenAuditField;
import com.ngen.cosys.framework.model.BaseBO;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * This class takes care for OperativeFlightException Model.
 * 
 * @author NIIT Technologies Ltd
 * @version 1.0
 */
@ApiModel
@Component
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@NgenAudit(eventName = NgenAuditEventType.FLIGHT_CREATION_UPDATION, repository = NgenAuditEventRepository.FLIGHT, entityFieldName = "Flight", entityType = NgenAuditEntityType.FLIGHT)
public class OperativeFlightExp extends BaseBO {
	/**
	 * The default serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * date std of operating flight Exp
	 */
	@NgenAuditField(fieldName = "DepartureDateTime")
	private String departureDateTime;
	/**
	 * boarding point of operating flight Exp
	 */
	@NgenAuditField(fieldName = "ULDExpType")
	private String uldExpType;
	/**
	 * arrivalDateTime of operating flight Exp
	 */
	@NgenAuditField(fieldName = "ULD NO")
	private String uldNo;
	/**
	 * carrier code of operating flight Exp
	 */
	@NgenAuditField(fieldName = "Reason")
	private String uldWtReason;
	/**
	 * InBound Seq No for operating flight Exp
	 */
	private BigInteger seqNo;
	/**
	 * createdUserCode for operating flight Exp
	 */
	private String createdUserCode;
	/**
	 * createdDateTime for operating flight Exp
	 */
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	private LocalDateTime createdDateTime;
	/**
	 * createdDateTime for operating flight Segment
	 */
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	private LocalDateTime createdOn;
	/**
	 * flightId for operating flight Exp
	 */
	private long flightId;
	/**
	 * exceptionType for operating flight Exp
	 */
	@NgenAuditField(fieldName = "Exception Type")
	private String exceptionType;
	/**
	 * tareWeight for operating flight Exp
	 */
	@NgenAuditField(fieldName = "Tare Weight")
	private BigDecimal tareWeight;
}