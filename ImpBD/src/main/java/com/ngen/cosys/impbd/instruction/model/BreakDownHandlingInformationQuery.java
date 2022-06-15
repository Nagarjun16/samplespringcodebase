package com.ngen.cosys.impbd.instruction.model;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ngen.cosys.JsonSerializer.LocalDateSerializer;
import com.ngen.cosys.validator.annotations.CheckValidFlightConstraint;
import com.ngen.cosys.validator.enums.FlightType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@CheckValidFlightConstraint(type = FlightType.Type.IMPORT, number = "flightKey", originDate = "flightOriginDate")
public class BreakDownHandlingInformationQuery {
	
	@NotNull(message = "Flight Key is blank")
    private String flightKey;
	@JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate  flightOriginDate;
	
	private String parameter1;
	private LocalDate parameter2;
	
	private String tenantAirport;
	
	
}
