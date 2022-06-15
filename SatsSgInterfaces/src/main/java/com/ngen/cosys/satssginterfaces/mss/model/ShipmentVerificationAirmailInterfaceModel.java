package com.ngen.cosys.satssginterfaces.mss.model;

import java.math.BigDecimal;
import java.math.BigInteger;

import com.ngen.cosys.framework.model.BaseBO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ShipmentVerificationAirmailInterfaceModel extends BaseBO {

	/**
	 * System generated serial version id
	 */
	private static final long serialVersionUID = 2472405689488543180L;
	private BigInteger impShipmentVerificationId = BigInteger.ZERO;
	private BigInteger flightId = BigInteger.ZERO;
	private BigInteger shipmentId = BigInteger.ZERO;

	private BigInteger breakDownPieces = BigInteger.ZERO;
	private BigDecimal breakDownWeight = BigDecimal.ZERO;

	private Boolean documentReceivedFlag = Boolean.FALSE;
	private Boolean photoCopyAwbFlag = Boolean.FALSE;
	private Boolean documentPouchReceivedFlag = Boolean.FALSE;
	private Boolean barcodePrintedFlag = Boolean.FALSE;	
	private Boolean registered = Boolean.FALSE;
	private Boolean checkListRequired = Boolean.FALSE;
	private String documentName="DGLIST";
	
	private String weatherCondition;

}