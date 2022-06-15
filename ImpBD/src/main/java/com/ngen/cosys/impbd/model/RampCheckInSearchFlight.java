package com.ngen.cosys.impbd.model;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ngen.cosys.JsonSerializer.LocalDateSerializer;
import com.ngen.cosys.impbd.flightdiscrepancylist.validators.FlightValidatorGroup;
import com.ngen.cosys.model.FlightModel;
import com.ngen.cosys.annotation.InjectSegment;
import groovy.transform.ToString;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@XmlRootElement
@ApiModel
@Component
@ToString
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Validated
public class RampCheckInSearchFlight extends FlightModel {
	
	private static final long serialVersionUID = 1L;
	
	@NotNull(message = "ERROR_FLIGHT_IS_MANDATORY",groups= FlightValidatorGroup.class)
	private String flight;
	
	@NotNull(message = "ERROR_FLIGHT_DATE_IS_MANDATORY" ,groups=FlightValidatorGroup.class)
	@JsonSerialize(using = LocalDateSerializer.class)
	private LocalDate flightDate;

	@InjectSegment(flightDate = "flightDate", flightId = "", flightNumber = "flight", flightType = "I", segment = "segment")
	private String segment;
}
