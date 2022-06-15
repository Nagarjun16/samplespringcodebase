package com.ngen.cosys.impbd.workinglist.model;

import java.math.BigDecimal;
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
public class BreakDownWorkingListShipmentResult extends BaseBO {

	/**
	 * System Generated Serial Version id
	 */
	private static final long serialVersionUID = -3740576766984283040L;
	private BigInteger flightId;
	private String flightBoardPoint;
	private String flightOffPoint;
	private String destination;
	private String natureOfGoodsDescription;
	private String shipmentNumber;
	private String shipmentType;
	private BigInteger shipmentId;
	private BigInteger awbPieces;
	private BigDecimal awbWeight;
	private BigInteger mnPieces;
	private BigDecimal mnWeight;
	private BigInteger bdPieces;
	private BigDecimal bdWeight;
	private String origin;
	private String uldNumber;
	private String specialHandlingCode;
	private String instruction;
	private String uldORawbNumber;
	private String transferType;
	private String outboundFlight;
	private String dmgFlag;
	private String readyForDelivery;
	private boolean flag;
	private boolean isEAPExists;
	private boolean isEAWExists;
	private BigInteger outgoingFlightid;
	@JsonSerialize(using = LocalDateSerializer.class)
	private LocalDate shipmentdate;
	private BigInteger localAuthorityDetailsId;

}
