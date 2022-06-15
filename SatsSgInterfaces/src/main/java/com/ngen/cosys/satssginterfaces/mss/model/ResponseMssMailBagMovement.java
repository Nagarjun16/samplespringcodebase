package com.ngen.cosys.satssginterfaces.mss.model;

import java.math.BigInteger;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ResponseMssMailBagMovement extends AirmailHouseCommonModel {
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;

	private String transactionSequenceNumber;
	private String action;
	private String mailBagId;
	private String uldKey;
	private String uldType;
	private String uldNumber;
	private String uldCarrier;
	private String containerlocation;
	private String acceptanceDateTime;
	private String xRayStatus;
	private String damageStatus;
	private String overSizeBagStatus;
	private String tornStatus;
	private String wetStatus;
	private String sealStatus;
	private String plunStatuts;
	private String mailBagWeight;
	private String mailBagRemarks;
	private String agent;
	private String outgoingFlightCarrier;
	private String outgoingFlightNumber;
	private String outgoingFlightDate;
	private String outgoingFlightTime;
	private String mailBagNewDestination;
	private String weighingDateTime;
	private String sortDateTime;
	private String storeDateTime;
	private String dispatchNo;
	private String piecesNo;
	private String occurrenceDateTime;
	private String user;
	private String rejectIndicator;
	private String rejectRsn;
	private String offloadIndicator;
	private String offloadCount;
	private String offloadRsn;
	private String incomimngFlightCarrier;
	private String incomimngFlightNumber;
	private String incomimngFlightDate;
	private String incomimngFlightAirport;
	private String breakDownUldType;
	private String breakDownUldSerialNo;
	private String breakDownUldAirline;
	private String uldDestination;
	private String uldNewDestination;
	private String bupIndicatorForDelivery;
	private String transitIndicatorBup;
	private String cltrStat;
	private String uldRemarks;
	private String grossWeight;

	private String segmentId;

	private int bookingId;

	private int flightBookingId;

	private String methodFor;

	private BigInteger segmentIdForAssignedTrolley;

}
