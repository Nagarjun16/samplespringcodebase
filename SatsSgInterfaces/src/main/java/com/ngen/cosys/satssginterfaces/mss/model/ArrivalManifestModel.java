package com.ngen.cosys.satssginterfaces.mss.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;

import com.ngen.cosys.framework.model.BaseBO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ArrivalManifestModel extends BaseBO {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8963973509492167768L;
	private BigInteger flightId;
	private BigInteger flightSegmentId;
	private BigInteger transTTWAConnectingFlightId;
	private String flightBoardPoint;
	private String flightOffPoint;
	private String carrier;
	
	private String shipmentNumber;
	private LocalDate shipmentDate;
	private BigInteger outBoundFlightId;
	private BigInteger inBoundFlightId;
	private BigInteger impArrivalManifestULDId;
	
	private String uldNumber;
	private BigDecimal weight;
	private String transferType;
	private String contourCode;
	private BigInteger impArrivalManifestShipmentInfoId;

}
