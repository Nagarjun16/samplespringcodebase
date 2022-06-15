package com.ngen.cosys.satssginterfaces.mss.model;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ngen.cosys.JsonSerializer.LocalDateSerializer;
import com.ngen.cosys.JsonSerializer.STDETDDateTimeSerializer;
import com.ngen.cosys.framework.model.BaseBO;
import com.ngen.cosys.validator.annotations.CheckValidFlightConstraint;
import com.ngen.cosys.validator.enums.FlightType;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ApiModel
@Getter
@Setter
@ToString
@NoArgsConstructor
@CheckValidFlightConstraint(type = FlightType.Type.IMPORT, number = "flightNumber", originDate = "flightDate"
		)
public class FlightAirlineInterfaceModel extends BaseBO {
	
	/**
	 * System generated serial version id
	 */
	private static final long serialVersionUID = -4172013140519434912L;

	private BigInteger flightId;

	@NotBlank(message = "ERROR_FLIGHT_NUMBER_REQUIRED")	
	@Size(max = 12, message = "ERROR_FLIGHT_NUMBER_SHOULD_BE_8_CHARACTER")
	private String flightNumber;

	@NotNull(message = "ERRRO_FLIGHT_DATE_REQUIRED")
    @JsonSerialize(using= LocalDateSerializer.class)
	private LocalDate flightDate;

	private String aircraftRegCode;

	@JsonSerialize(using= STDETDDateTimeSerializer.class)
	private LocalDateTime sta;

	@JsonSerialize(using= STDETDDateTimeSerializer.class)
	private LocalDateTime eta;
	@JsonSerialize(using= STDETDDateTimeSerializer.class)
	private LocalDateTime ata;
	
	@JsonSerialize(using= STDETDDateTimeSerializer.class)
	private LocalDateTime std;
	
	private String boardingPoint;
	
	private String offPoint;
	
	private String carrierCode;
	private String messageCopy;
	private String rejectCount;
    /*private String segmentId;
    private String segmentdesc;*/
	/*@Valid
	private List<InwardServiceReportModel> segmentArray;*/

}