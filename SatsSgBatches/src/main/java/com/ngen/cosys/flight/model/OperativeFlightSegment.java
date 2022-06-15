/**
 * OperativeFlightSegment.java
 * 
 * Copyright <PRE><IMG SRC = XX></IMG></PRE>
 *
 * Version      Date         Author      Reason
 * 1.0          28 July, 2017   NIIT      -
 */
package com.ngen.cosys.flight.model;

import java.time.LocalDateTime;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
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
 * This class takes care for Flight Segment entity.
 * 
 * @author NIIT Technologies Ltd
 * @version 1.0
 */
@ApiModel
@Component
@ToString
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@NgenAudit(eventName = NgenAuditEventType.FLIGHT_CREATION_UPDATION, repository = NgenAuditEventRepository.FLIGHT, entityFieldName = "Flight", entityType = NgenAuditEntityType.FLIGHT)
public class OperativeFlightSegment extends BaseBO {
	/**
	 * The default serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * flightId for operating flight Segment
	 */
	private long flightId;
	/**
	 * codAptBrd for operating flight Segment
	 */
	@NgenAuditField(fieldName = "Airport BoardPoint")
	private String codAptBrd;
	/**
	 * codAptOff for operating flight Segment
	 */
	@NgenAuditField(fieldName = "Airport OffPoint")
	private String codAptOff;
	/**
	 * codSegOdr for operating flight Segment
	 */
	private String codSegOdr;
	/**
	 * datStd for operating flight Segment
	 */
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@NgenAuditField(fieldName = "STD DateTime")
	private LocalDateTime datStd;
	/**
	 * flgLeg for operating flight Segment
	 */
	private String flgLeg;
	/**
	 * flgNfl for operating flight Segment
	 */
	@NgenAuditField(fieldName = "Frieght NA")
	private String flgNfl;
	/**
	 * flgTecStp for operating flight Segment
	 */
	@NgenAuditField(fieldName = "Technical Stop")
	private String flgTecStp;
	/**
	 * codFfm for operating flight Segment
	 */
	private String codFfm;
	/**
	 * codArmIni for operating flight Segment
	 */
	private String codArmIni;
	/**
	 * datArm for operating flight Segment
	 */
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	private LocalDateTime datArm;
	/**
	 * flgPrt for operating flight Segment
	 */
	private String flgPrt;
	/**
	 * codFin for operating flight Segment
	 */
	private String codFin;
	/**
	 * datFfm for operating flight Segment
	 */
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	private LocalDateTime datFfm;
	/**
	 * datLstUpd for operating flight Segment
	 */
	private String datLstUpd;
	/**
	 * ffmRejCnt for operating flight Segment
	 */
	private String ffmRejCnt;
	/**
	 * ffmAcpCnt for operating flight Segment
	 */
	private String ffmAcpCnt;
	/**
	 * flgDom for operating flight Segment
	 */
	private String flgDom;
	/**
	 * serviceType for operating flight Segment
	 */
	private String serviceType;
	/**
	 * flgCargo for operating flight Segment
	 */
	private String flgCargo;
	/**
	 * datSta for operating flight Segment
	 */
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@NgenAuditField(fieldName = "STA DateTime")
	private LocalDateTime datSta;
	/**
	 * noMail for operating flight Segment
	 */
	@NgenAuditField(fieldName = "No Mail")
	private String noMail;
	/**
	 * createdUserCode for operating flight Segment
	 */
	private String createdUserCode;
	/**
	 * createdDateTime for operating flight Segment
	 */
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	private LocalDateTime createdDateTime;
	/**
	 * createdDateTime for operating flight Segment
	 */
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	private LocalDateTime createdOn;
}