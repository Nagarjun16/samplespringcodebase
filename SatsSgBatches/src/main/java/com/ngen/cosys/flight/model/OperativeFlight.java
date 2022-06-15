/**
 * OperativeFlight.java
 * 
 * Copyright
 * 
 * <PRE>
 * <IMG SRC = XX></IMG>
 * </PRE>
 *
 * Version Date Author Reason 1.0 28 July, 2017 NIIT -
 */
package com.ngen.cosys.flight.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.ngen.cosys.JsonSerializer.LocalDateTimeSerializer;
import com.ngen.cosys.audit.NgenAudit;
import com.ngen.cosys.audit.NgenAuditEntityType;
import com.ngen.cosys.audit.NgenAuditEntityValue2;
import com.ngen.cosys.audit.NgenAuditEntityValue3;
import com.ngen.cosys.audit.NgenAuditEntityValue4;
import com.ngen.cosys.audit.NgenAuditEventRepository;
import com.ngen.cosys.audit.NgenAuditEventType;
import com.ngen.cosys.audit.NgenAuditField;
import com.ngen.cosys.audit.NgenAuditFieldName;
import com.ngen.cosys.audit.NgenAuditFieldName.NgenAuditFieldNameType;
import com.ngen.cosys.audit.NgenAudits;
import com.ngen.cosys.framework.model.BaseBO;
import com.ngen.cosys.validator.annotations.UserCarrierValidation;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * This is the OperativeFlight Model class representing the Flt_OperativeFlight
 * Table.
 * 
 * @author NIIT Technologies Ltd
 * @version 1.0
 */
@ApiModel
@XmlRootElement
@Component
@ToString
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@UserCarrierValidation(carrierCode="carrierCode", flightKey = "", loggedInUser = "loggedInUser", type = "CARRIER", shipmentNumber = "")
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@NgenAudits({
		@NgenAudit(eventName = NgenAuditEventType.FLIGHT_CREATION, repository = NgenAuditEventRepository.FLIGHT, entityFieldName = "Flight Key", entityType = NgenAuditEntityType.FLIGHT),
		@NgenAudit(eventName = NgenAuditEventType.FLIGHT_UPDATE, repository = NgenAuditEventRepository.FLIGHT, entityFieldName = "Flight Key", entityType = NgenAuditEntityType.FLIGHT),
		@NgenAudit(eventName = NgenAuditEventType.FLIGHT_CANCELLATION, repository = NgenAuditEventRepository.FLIGHT, entityFieldName = "Flight Key", entityType = NgenAuditEntityType.FLIGHT),
		@NgenAudit(eventName = NgenAuditEventType.FLIGHT_RESTORE, repository = NgenAuditEventRepository.FLIGHT, entityFieldName = "Flight Key", entityType = NgenAuditEntityType.FLIGHT),
		@NgenAudit(eventName = NgenAuditEventType.FLIGHT_DELETE_LEG, repository = NgenAuditEventRepository.FLIGHT, entityFieldName = "Flight Key", entityType = NgenAuditEntityType.FLIGHT),
		@NgenAudit(eventName = NgenAuditEventType.FLIGHT_DELETE_FACT, repository = NgenAuditEventRepository.FLIGHT, entityFieldName = "Flight Key", entityType = NgenAuditEntityType.FLIGHT),
		@NgenAudit(eventName = NgenAuditEventType.FLIGHT_BOOKING_OPEN, repository = NgenAuditEventRepository.FLIGHT, entityFieldName = "Flight Key", entityType = NgenAuditEntityType.FLIGHT),
		@NgenAudit(eventName = NgenAuditEventType.FLIGHT_BOOKING_CLOSE, repository = NgenAuditEventRepository.FLIGHT, entityFieldName = "Flight Key", entityType = NgenAuditEntityType.FLIGHT),
		@NgenAudit(eventName = NgenAuditEventType.ACES_CUSTOMS, repository = NgenAuditEventRepository.FLIGHT, entityFieldName = "Flight Key", entityType = NgenAuditEntityType.FLIGHT)})

public class OperativeFlight extends BaseBO {
	/**
	 * The default serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * flightId of operating flight.
	 */
	
	
	
	private long flightId;
	/**
	 * carrierCode of operating flight.
	 */
	@NotBlank(message = "g.carrier.code.cannot.blank")
	@Length(min = 2, max = 3, message = "flight.min.length.2")
	//@NgenAuditField(fieldName = NgenAuditFieldNameType.CARRIER)
	private String carrierCode;
	/**
	 * flightDate of operating flight.
	 */
	@NotNull(message = "export.flight.date.cannot.blank")
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@NgenAuditField(fieldName = NgenAuditFieldNameType.FLIGHTDATEORIGIN)
	private LocalDateTime flightDate;
	/**
	 * flightKey of operating flight.
	 */
	@NgenAuditField(fieldName = NgenAuditFieldNameType.FLIGHTKEY)
	private String flightKey;
	/**
	 * flightNo of operating flight.
	 */
	@NotEmpty(message = "export.flight.number.cannot.blank")
	@Pattern(regexp = "^(([0-9]{4,4}[^0-9]{0,1})|([0-9]{3,4}))$", message = "flight.invalid")
	private String flightNo;
	/**
	 * extraFlight of operating flight.
	 */
	private String extraFlight;
	/**
	 * adHocFlightCode of operating flight.
	 */
	private String adHocFlightCode;
	/**
	 * cancellation of operating flight.
	 */
	@NgenAuditField(fieldName = NgenAuditFieldNameType.CANCELLED)
	private String cancellation;
	/**
	 * flgChaSch of operating flight.
	 */
	private String flgChaSch;
	/**
	 * flgChaAsm of operating flight.
	 */
	private String flgChaAsm;
	/**
	 * codFltSts of operating flight.
	 */
	private String codFltSts;
	/**
	 * qtyAliCrsOpn of operating flight.
	 */
	private BigDecimal qtyAliCrsOpn;
	/**
	 * qtyAliPltCgo of operating flight.
	 */
	private BigDecimal qtyAliPltCgo;
	/**
	 * qtyAliCrsCgo of operating flight.
	 */
	private BigDecimal qtyAliCrsCgo;
	/**
	 * qtyAliPltTrn of operating flight.
	 */
	private BigDecimal qtyAliPltTrn;
	/**
	 * qtyAliCrsTrn of operating flight.
	 */
	private BigDecimal qtyAliCrsTrn;
	/**
	 * codWgtUnt of operating flight.
	 */
	private String codWgtUnt;
	/**
	 * jointFlight of operating flight.
	 */
	@NgenAuditField(fieldName = NgenAuditFieldNameType.JOINTFLIGHT)
	private String jointFlight;
	/**
	 * flgApn of operating flight.
	 */
	@NgenAuditField(fieldName = NgenAuditFieldNameType.APRON)
	private String flgApn;
	/**
	 * datApn of operating flight.
	 */
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	private LocalDateTime datApn;
	/**
	 * flgRes of operating flight.
	 */
	private String flgRes;
	/**
	 * cntSntApr of operating flight.
	 */
	private BigDecimal cntSntApr;
	/**
	 * datFltDep of operating flight.
	 */
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	private LocalDateTime datFltDep;
	/**
	 * codPrkBayArr of operating flight.
	 */
	// @Pattern(regexp="^([a-zA-Z0-9]{1,4})$", message="Invaid Inbound Parking Bay")
	@NgenAuditField(fieldName = NgenAuditFieldNameType.INBOUNDBAY)
	private String codPrkBayArr;
	@NgenAuditField(fieldName = NgenAuditFieldNameType.PREV_INBOUNDBAY)
	private String previousCodPrkBayArr;
	@NgenAuditField(fieldName = NgenAuditFieldNameType.INBOUNDBAY_UPDATED_TIME)
	private LocalDateTime codPrkBayArrUpdatedTime;
	
	/**
	 * codPrkBayDep of operating flight.
	 */
	// @Pattern(regexp="^([a-zA-Z0-9]{1,4})$", message="Invaid Outbound Parking
	// Bay")
	@NgenAuditField(fieldName = NgenAuditFieldNameType.OUTBOUNDBAY)
	private String codPrkBayDep;
	@NgenAuditField(fieldName = NgenAuditFieldNameType.PREV_OUTBOUNDBAY)
	private String previousCodPrkBayDep;
	@NgenAuditField(fieldName = NgenAuditFieldNameType.OUTBOUNDBAY_UPDATED_TIME)
	private LocalDateTime codPrkBayDepUpdatedTime;
	
	/**
	 * serviceType of operating flight.
	 */
	// @NotBlank(message = "Service Type cannot be blank")
	@NgenAuditField(fieldName = NgenAuditFieldNameType.SERVICETYPE)
	private String serviceType;
	/**
	 * flgDlyIn of operating flight.
	 */
	@NgenAuditField(fieldName = NgenAuditFieldNameType.DELAYIN)
	private String flgDlyIn;
	/**
	 * dlyInReason of operating flight.
	 */
	@NgenAuditField(fieldName = NgenAuditFieldNameType.DELAYREASON)
	private String dlyInReason;
	/**
	 * flgDlyOut of operating flight.
	 */
	@NgenAuditField(fieldName = NgenAuditFieldNameType.DELAYOUT)
	private String flgDlyOut;
	/**
	 * dlyOutReason of operating flight.
	 */
	@NgenAuditField(fieldName = NgenAuditFieldNameType.DELAYOUTREASON)
	private String dlyOutReason;
	/**
	 * maxUldCount of operating flight.
	 */

	private String maxUldCount;
	/**
	 * regisArrival of operating flight.
	 */

	// @Pattern(regexp="^(.{1,10})$", message="Invaid Inbound Aircraft Registration
	// Number")
	@NgenAuditField(fieldName = NgenAuditFieldNameType.REGISTRATIONARRIVAL)
	private String regisArrival;
	@NgenAuditField(fieldName = NgenAuditFieldNameType.PREV_REGISTRATIONARRIVAL)
	private String previousRegisArrival;
	@NgenAuditField(fieldName = NgenAuditFieldNameType.REGISTRATIONARRIVAL_UPDATED_TIME)
	private LocalDateTime regisArrivalUpdatedTime;
	
	/**
	 * regisDeparture of operating flight.
	 */
	// @Pattern(regexp="^(.{1,10})$", message="Invaid Outbound Aircraft Registration
	// Number")
	@NgenAuditField(fieldName = NgenAuditFieldNameType.DEPARTUREREGISTRATION)
	private String regisDeparture;
	@NgenAuditField(fieldName = NgenAuditFieldNameType.PREV_DEPARTUREREGISTRATION)
	private String previousRegisDeparture;
	@NgenAuditField(fieldName = NgenAuditFieldNameType.DEPARTUREREGISTRATION_UPDATED_TIME)
	private LocalDateTime regisDepartureUpdatedTime;
	
	/**
	 * dateStd of operating flight.
	 */
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	private LocalDateTime dateStd;
	/**
	 * dateSta of operating flight.
	 */
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	private LocalDateTime dateSta;
	/**
	 * caoPax of operating flight.
	 */
	@NgenAuditField(fieldName = NgenAuditFieldNameType.CAOPAX)
	private String caoPax;
	/**
	 * groundHandlerCode of operating flight.
	 */
	// @NotBlank(message = "Ground Handler Code cannot be blank")
	@NgenAuditField(fieldName = NgenAuditFieldNameType.GROUNDHANDLER)
	private String groundHandlerCode;
	/**
	 * flightAutoCompleteFlag of operating flight.
	 */
	@NgenAuditField(fieldName = NgenAuditFieldNameType.FLIGHTAUTOCOMPLETE)
	private String flightAutoCompleteFlag;
	/**
	 * createdUserCode of operating flight.
	 */
	private String createdUserCode;
	/**
	 * createdDateTime of operating flight.
	 */
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	private LocalDateTime createdDateTime;
	/**
	 * createdDateTime of operating flight.
	 */
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	private LocalDateTime createdOn;
	/**
	 * flgExpUld of operating flight.
	 */
	private String flgExpUld;
	/**
	 * flgExpWt of operating flight.
	 */
	private String flgExpWt;
	/**
	 * flgSsmAsm of operating flight.
	 */
	@NgenAuditField(fieldName = NgenAuditFieldNameType.SSMASM)
	private String flgSsmAsm;
	/**
	 * flgManCtl of operating flight.
	 */
	private String flgManCtl;
	/**
	 * flgKvl of operating flight.
	 */
	private String flgKvl;
	/**
	 * flgDlsCtl of operating flight.
	 */
	private String flgDlsCtl;
	/**
	 * routing of operating flight.
	 */
	private String routing;
	/**
	 * description of operating flight.
	 */
	private String description;
	/**
	 * qtyAliBts of operating flight.
	 */
	private BigDecimal qtyAliBts;
	/**
	 * qtyAliPltOpn of operating flight.
	 */
	private BigDecimal qtyAliPltOpn;
	/**
	 * lastUpdateUser of operating flight.
	 */
	private String lastUpdateUser;
	/**
	 * lastUpdateDateTime of operating flight.
	 */
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	private LocalDateTime lastUpdateDateTime;
	/**
	 * datLstUpd of operating flight.
	 */
	private String datLstUpd;
	/**
	 * flgEvmFin of operating flight.
	 */
	private String flgEvmFin;
	/**
	 * List<OperativeFlightLeg> of operating flight.
	 */
	@NgenAuditField(fieldName = NgenAuditFieldNameType.FLIGHTLEGS)
	private List<OperativeFlightLeg> flightLegs;
	/**
	 * List<OperativeFlightSegment> of operating flight.
	 */
	//@NgenAuditField(fieldName = "FlightSegments")
	private List<OperativeFlightSegment> flightSegments;
	/**
	 * List<OperativeFlightFct> of operating flight.
	 */
	//@NgenAuditField(fieldName = "FlightFacts")
	private List<OperativeFlightFct> flightFcts;
	/**
	 * List<OperativeFlightExp> of operating flight.
	 */
	//@NgenAuditField(fieldName = "FlightExps")
	private List<OperativeFlightExp> flightExps;
	/**
	 * List<OperativeFlightExp> of operating flight.
	 */
	//@NgenAuditField(fieldName = "FlightExpULDTyps")
	private List<OperativeFlightExp> flightExpULDTyps;
	/**
	 * List<OperativeFlightJoint> of operating flight.
	 */
	//@NgenAuditField(fieldName = "FlightJoints")
	private List<OperativeFlightJoint> flightJoints;

	private String assisted;

	private boolean flgDispJointFlight;

	private String aircraftType;
	/**
	 * Aircraft Body Type
	 */

	private String aircraftBodyType;
	/**
	 * Flight is an import/export
	 */
	@NgenAuditField(fieldName = NgenAuditFieldNameType.FLIGHTTYPE)
	private String flightType;
	@NgenAuditField(fieldName = NgenAuditFieldNameType.BOOKINGSTATUS)
	private String flightbookingStatus;
	/**
	 * Flight Handling Terminal
	 */
	@NgenAuditField(fieldName = NgenAuditFieldNameType.HANDLINGAREA)
	private List<String> handlingArea;
	private String autoFlightFlag;
	private boolean manuallyAutoFlag;
	private boolean errorFlag;
	private String isSearchFlag;
	private String isUpdateFlag;
	private String cancelFlag;
	private String deleteSchFlag;
	// private String tenantId;
	@NgenAuditEntityValue2(entityType = NgenAuditEntityType.FLIGHT, parentEntityType = NgenAuditEntityType.FLIGHT)
	@NgenAuditField(fieldName = NgenAuditFieldNameType.DEPAURTUREDATE)
	@JsonSerialize(using = LocalDateSerializer.class)
	private LocalDate departreDate;
	
	@NgenAuditEntityValue3(entityType = NgenAuditEntityType.FLIGHT, parentEntityType = NgenAuditEntityType.FLIGHT)
	@NgenAuditField(fieldName = NgenAuditFieldNameType.ARRIVALDATE)
	@JsonSerialize(using = LocalDateSerializer.class)
	private LocalDate arrivalDate;
	@NgenAuditField(fieldName = NgenAuditFieldNameType.EXPORTIMPORTINDICATOR)
	 private String ExportorImportFlight;

}