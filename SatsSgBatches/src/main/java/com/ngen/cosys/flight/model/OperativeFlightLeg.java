/**
 * OperativeFlightLeg.java
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

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
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
import com.ngen.cosys.audit.NgenAuditFieldName.NgenAuditFieldNameType;
import com.ngen.cosys.audit.NgenAudits;
import com.ngen.cosys.framework.model.BaseBO;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * This class takes care for Flight Leg entity.
 * 
 * @author NIIT Technologies Ltd
 * @version 1.0
 *
 */
@ApiModel
@Component
@ToString
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@NgenAudits({
		@NgenAudit(eventName = NgenAuditEventType.FLIGHT_CREATION, repository = NgenAuditEventRepository.FLIGHT, entityFieldName = "Flight Key", entityType = NgenAuditEntityType.FLIGHT),
		@NgenAudit(eventName = NgenAuditEventType.FLIGHT_UPDATE, repository = NgenAuditEventRepository.FLIGHT, entityFieldName = "Flight Key", entityType = NgenAuditEntityType.FLIGHT),
		@NgenAudit(eventName = NgenAuditEventType.FLIGHT_CANCELLATION, repository = NgenAuditEventRepository.FLIGHT, entityFieldName = "Flight Key", entityType = NgenAuditEntityType.FLIGHT),
		@NgenAudit(eventName = NgenAuditEventType.FLIGHT_RESTORE, repository = NgenAuditEventRepository.FLIGHT, entityFieldName = "Flight Key", entityType = NgenAuditEntityType.FLIGHT),
		@NgenAudit(eventName = NgenAuditEventType.FLIGHT_DELETE_LEG, repository = NgenAuditEventRepository.FLIGHT, entityFieldName = "Flight Key", entityType = NgenAuditEntityType.FLIGHT),
		@NgenAudit(eventName = NgenAuditEventType.FLIGHT_DELETE_FACT, repository = NgenAuditEventRepository.FLIGHT, entityFieldName = "Flight Key", entityType = NgenAuditEntityType.FLIGHT),
		@NgenAudit(eventName = NgenAuditEventType.FLIGHT_BOOKING_OPEN, repository = NgenAuditEventRepository.FLIGHT, entityFieldName = "Flight Key", entityType = NgenAuditEntityType.FLIGHT),
		@NgenAudit(eventName = NgenAuditEventType.FLIGHT_BOOKING_CLOSE, repository = NgenAuditEventRepository.FLIGHT, entityFieldName = "Flight Key", entityType = NgenAuditEntityType.FLIGHT) })
public class OperativeFlightLeg extends BaseBO {
	/**
	 * The default serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * flightId for operating flight Leg
	 */
	private long flightId;
	@NgenAuditField(fieldName = NgenAuditFieldNameType.FLIGHTKEY)
	private String flightKey;
	/**
	 * boardPointCode for operating flight Leg
	 */
	@NotNull(message = "g.board.point.cannot.blank")
	@NotEmpty(message = "g.board.point.cannot.empty")
	@NotBlank(message = "g.board.point.cannot.blank")
	@NgenAuditField(fieldName = NgenAuditFieldNameType.BOARDPOINT)
	private String boardPointCode;
	/**
	 * offPointCode for operating flight Leg
	 */
	@NotNull(message = "g.off.point.cannot.blank")
	@NotEmpty(message = "g.off.point.cannot.empty")
	@NotBlank(message = "g.off.point.cannot.blank")
	@NgenAuditField(fieldName = NgenAuditFieldNameType.OFFPOINT)
	private String offPointCode;
	/**
	 * legOrderCode for operating flight Leg
	 */
	private int legOrderCode;
	/**
	 * departureDate for operating flight Leg
	 */
	@NotNull(message = "g.schedule.depart.cannot.blank")
	@NotEmpty(message = "g.schedule.depart.cannot.empty")
	@NotBlank(message = "g.schedule.depart.cannot.blank")
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@NgenAuditField(fieldName = NgenAuditFieldNameType.DEPAURTUREDATE)
	private LocalDateTime departureDate;
	private LocalDateTime checkDepartureDate;
	/**
	 * ♦ arrivalDate for operating flight Leg
	 */
	@NotNull(message = "g.schedule.arrival.cannot.blank")
	@NotEmpty(message = "g.schedule.arrival.cannot.empty")
	@NotBlank(message = "g.schedule.arrival.cannot.blank")
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@NgenAuditField(fieldName = NgenAuditFieldNameType.ARRIVALDATE)
	private LocalDateTime arrivalDate;
	private LocalDateTime checkArrivalDate;
	/**
	 * datEtd for operating flight Leg
	 */
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@NgenAuditField(fieldName = NgenAuditFieldNameType.DATEETD)
	private LocalDateTime datEtd;
	/**
	 * datEta for operating flight Leg
	 */
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@NgenAuditField(fieldName = NgenAuditFieldNameType.DATEETA)
	private LocalDateTime datEta;
	/**
	 * datAtd for operating flight Leg
	 */
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@NgenAuditField(fieldName = NgenAuditFieldNameType.DATEATD)
	private LocalDateTime datAtd;
	/**
	 * datAta for operating flight Leg
	 */
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@NgenAuditField(fieldName = NgenAuditFieldNameType.DATEATA)
	private LocalDateTime datAta;
	/**
	 * domesticStatus for operating flight Leg
	 */
	@NgenAuditField(fieldName = "DomesticStatus")
	private String domesticStatus;
	/**
	 * registration for operating flight Leg
	 */
	@NgenAuditField(fieldName = "Registration")
	private String registration;
	/**
	 * aircraftModel for operating flight Leg
	 */
	@NotBlank(message = "g.aircraft.arrival.cannot.blank")
	@NotEmpty(message = "g.aircraft.arrival.cannot.empty")
	@NotNull(message = "g.aircraft.arrival.cannot.blank")
	@NgenAuditField(fieldName = NgenAuditFieldNameType.AIRCRAFTTYPE)
	private String aircraftModel;
	/**
	 * flgDly for operating flight Leg
	 */
	// @NgenAuditField(fieldName = "ArrivalDate")
	private String flgDly;
	/**
	 * createdUserCode for operating flight Leg
	 */
	private String createdUserCode;
	/**
	 * createdDateTime for operating flight Leg
	 */
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	private LocalDateTime createdDateTime;
	/**
	 * createdDateTime for operating flight Leg
	 */
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	private LocalDateTime createdOn;

	/**
	 * codTypFltSvc for operating flight Leg
	 */
	private String codTypFltSvc;
	/**
	 * aircraftType for operating flight Leg
	 */

	private String aircraftType;
	/**
	 * cargoWeight for operating flight Leg
	 */

	private BigDecimal cargoWeight;
	/**
	 * cargoVolume for operating flight Leg
	 */

	private BigDecimal cargoVolume;
	/**
	 * mailWeight for operating flight Leg
	 */

	private BigDecimal mailWeight;
	/**
	 * mailVolume for operating flight Leg
	 */

	private BigDecimal mailVolume;
	/**
	 * maxHalfPallets for operating flight Leg
	 */

	private BigDecimal maxHalfPallets;
	/**
	 * qtyPltUse for operating flight Leg
	 */
	private BigDecimal qtyPltUse;
	/**
	 * codAirTypCar for operating flight Leg
	 */
	private String codAirTypCar;
	/**
	 * codJntCar1 for operating flight Leg
	 */
	private String codJntCar1;
	/**
	 * codJntFlt1 for operating flight Leg
	 */
	private String codJntFlt1;
	/**
	 * codJntCar2 for operating flight Leg
	 */
	private String codJntCar2;
	/**
	 * codJntFlt2 for operating flight Leg
	 */
	private String codJntFlt2;
	/**
	 * codJntCar3 for operating flight Leg
	 */
    private String codJntCar3;
    private String firstULDTowedBy;
    
    
	/**
	 * codJntFlt3 for operating flight Leg
	 */
	private String codJntFlt3;
	/**
	 * codWgtUnt for operating flight Leg
	 */
	@NgenAuditField(fieldName = NgenAuditFieldNameType.WEIGHTUNITCODE)
	private String codWgtUnt;
	/**
	 * codVolUnt for operating flight Leg
	 */
	@NgenAuditField(fieldName = NgenAuditFieldNameType.VOLUMEUNITCODE)
	private String codVolUnt;
	/**
	 * qtyCgoWgtLbs for operating flight Leg
	 */
	private BigDecimal qtyCgoWgtLbs;
	/**
	 * qtyMalWgtLbs for operating flight Leg
	 */
	private BigDecimal qtyMalWgtLbs;
	/**
	 * datLstUpd for operating flight Leg
	 */
	private String datLstUpd;

	
	
	private Boolean boardPointFlag;
	private Boolean offPointFlag;
	private String jointFlightCarCode;
	private String bayInbound;
	private String bayOutbond;
	private String flightStatus;
	private String inboundRegistration;
	private String outboundRegistration;
	
	
	
	
	private String carrierCode;
	
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime flightDate;
    private boolean handledInSystem;
    
	// JV01
	@NgenAuditField(fieldName = NgenAuditFieldNameType.BU_BD_OFFICE)
	private String bubdOffice;

	@NgenAuditField(fieldName = NgenAuditFieldNameType.WH_LEVEL)
	private String warehouseLevel;

	@NgenAuditField(fieldName = NgenAuditFieldNameType.RHO)
	private BigInteger rho;

}