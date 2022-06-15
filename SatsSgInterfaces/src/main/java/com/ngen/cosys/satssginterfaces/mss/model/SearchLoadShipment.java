package com.ngen.cosys.satssginterfaces.mss.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalTime;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ngen.cosys.JsonSerializer.LocalDateSerializer;
import com.ngen.cosys.JsonSerializer.LocalTimeSerializer;
import com.ngen.cosys.framework.model.BaseBO;
import com.ngen.cosys.validator.annotations.CheckDlsCompleteConstraint;
import com.ngen.cosys.validator.annotations.CheckFlightCompleteConstraint;
import com.ngen.cosys.validator.annotations.CheckManifestCompleteConstraint;
import com.ngen.cosys.validator.annotations.CheckValidFlightConstraint;
import com.ngen.cosys.validator.enums.FlightType;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 
 * @author NIIT Technologies Ltd.
 * @version 1.0
 */
@ApiModel
@Component
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Validated
@CheckValidFlightConstraint(type = FlightType.Type.EXPORT, number = "flightKey", originDate = "flightOriginDate")
@CheckManifestCompleteConstraint(flightKey = "flightKey", flightDate = "flightOriginDate", tenantId = "tenantId")
@CheckDlsCompleteConstraint(flightKey = "flightKey", flightDate = "flightOriginDate", tenantId = "tenantId")
@CheckFlightCompleteConstraint(type=FlightType.Type.EXPORT,number="flightKey",originDate="flightOriginDate")
public class SearchLoadShipment extends BaseBO {

	/**
	 * The default serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;
	@NotBlank(message = "flight.key.is.blank")
	private String flightKey;

	@NotNull(message = "flight.date.is.blank")
	@JsonSerialize(using = LocalDateSerializer.class)
	private LocalDate flightOriginDate;

	private String uldNumber;

	private String segment;

	@JsonSerialize(using = LocalTimeSerializer.class)
	private LocalTime std;

	@JsonSerialize(using = LocalTimeSerializer.class)
	private LocalTime etd;
	
	// For Mobile Screen  	
	private Long flightId;

	private String contentCode;

	private String heightCode;
	
	private String shc;
	
	private BigInteger segmentId;
	
	// Ends	

	/**
	* Used For Perishable Container
	*/
	private boolean phcFlag;
	
	private BigDecimal maxWeight;
	
	private String flightStatus;
	
	private String flightType;
}
