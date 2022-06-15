package com.ngen.cosys.impbd.workinglist.model;

import java.math.BigDecimal;
import java.math.BigInteger;

import com.ngen.cosys.model.ShipmentModel;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class BreakDownWorkingListShipmentVarification extends ShipmentModel {

	/**
	 * System Generated Serial Version id
	 */
	private static final long serialVersionUID = -7820930093390826280L;
	private BigInteger flightId;
	private BigInteger flightSegmentId;
	private BigInteger shipmentId;
	private BigInteger bdPieces;
	private BigDecimal bdWeight;
	private BigInteger mnPieces;
	private BigDecimal mnWeight;
	private String dmgFlag;
	private String offloadedFlag;
	private String surplus;
	private BigInteger pieceCount;
	private BigInteger weightCount;
}
