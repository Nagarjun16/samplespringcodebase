package com.ngen.cosys.impbd.instruction.model;

import java.util.List;

import com.ngen.cosys.model.FlightModel;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@ApiModel
@Getter
@Setter
@NoArgsConstructor
public class BreakDownHandlingInstructionFlightModel extends FlightModel{

	/**
	 * System Generated Serial Version id
	 */
	private static final long serialVersionUID = -9026609418431097297L;
	
	private List<BreakDownHandlingInstructionFlightSegmentModel> segments;

}