package com.ngen.cosys.impbd.model;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ngen.cosys.JsonSerializer.LocalDateTimeSerializer;
import com.ngen.cosys.framework.model.BaseBO;
import com.ngen.cosys.validator.annotations.CheckValidFlightConstraint;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@XmlRootElement
@ApiModel
@Component
@ToString
@Setter
@Getter
@NoArgsConstructor
@CheckValidFlightConstraint(number = "flightKey", originDate = "flightOriginDate", type = "Export")
public class FlightDetails extends BaseBO {

   private static final long serialVersionUID = 1L;

   @NotBlank(message = "ERRRO_FLIGHT_CANNOT_BE_BLANK")
   @NotNull(message = "ERRRO_FLIGHT_CANNOT_BE_BLANK")
   private String flightKey;

   @NotBlank(message = "ERRRO_FLIGHT_DATE_REQUIRED")
   @JsonSerialize(using = LocalDateTimeSerializer.class)
   private LocalDateTime flightOriginDate;

}
