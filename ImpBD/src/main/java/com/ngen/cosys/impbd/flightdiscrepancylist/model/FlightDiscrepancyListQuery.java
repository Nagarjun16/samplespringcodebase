package com.ngen.cosys.impbd.flightdiscrepancylist.model;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ngen.cosys.JsonSerializer.LocalDateSerializer;
import com.ngen.cosys.impbd.flightdiscrepancylist.validators.FlightValidatorGroup;
import com.ngen.cosys.validator.annotations.CheckValidFlightConstraint;
import com.ngen.cosys.validator.enums.FlightType;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@ApiModel
@Getter
@Setter
@NoArgsConstructor
@Validated
@CheckValidFlightConstraint(originDate = "flightOriginDate", number = "flightKey", type = FlightType.Type.IMPORT, groups = {
		FlightValidatorGroup.class })
public class FlightDiscrepancyListQuery {
   @NotNull(message = "ERROR_FLIGHT_KEY_CANNOT_BE_BLANK")
   private String flightKey;
   @NotNull(message = "ERROR_FROM_DATE_CANNOT_BE_BLANK")
   @JsonSerialize(using = LocalDateSerializer.class)
   private LocalDate flightOriginDate;
   private boolean sendEvent;
   private Long flightId;
}