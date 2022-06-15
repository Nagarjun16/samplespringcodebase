package com.ngen.cosys.impbd.workinglist.model;

import java.math.BigInteger;
import java.time.LocalDate;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ngen.cosys.JsonSerializer.LocalDateSerializer;
import com.ngen.cosys.framework.model.BaseBO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@NoArgsConstructor
public class BreakDownHandlingInstructionModel extends BaseBO {
	/**
	 * System Generated Serial Version id
	 */
	private static final long serialVersionUID = 5671835386646707881L;
	private BigInteger flightId;
	private BigInteger flightSegmentId;
	private String shipmentNumber;

	@JsonSerialize(using = LocalDateSerializer.class)
	private LocalDate ShipmentDate;

	private String instruction;
	private String origin;
	private String destination;
}
