package com.ngen.cosys.impbd.workinglist.model;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;

import javax.validation.constraints.Size;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ngen.cosys.JsonSerializer.LocalDateSerializer;
import com.ngen.cosys.model.SegmentModel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BreakDownWorkingListSegmentModel extends SegmentModel {

	/**
	 * System Generated Serial Version id
	 */
	private static final long serialVersionUID = -234562394342031137L;
	private BigInteger impArrivalManifestBySegmentId;
	private BigInteger impArrivalManifestByFlightId;
	private String flightBoardPoint;
	private String flightOffPoint;
	private BigInteger flightSegmentId;
	private Boolean nilCargo= Boolean.FALSE;
	 @Size(max = 12, message = "ERROR_FLIGHT_NUMBER_SHOULD_BE_8_CHARACTER")
	
	   private String flightNumber;

	
	   @JsonSerialize(using = LocalDateSerializer.class)
	  
	   private LocalDate flightDate;

	private List<BreakDownWorkingListShipmentInfoModel> breakDownWorkingListShipmentInfoModel;
	
}