package com.ngen.cosys.icms.model.bookingicms;


import java.math.BigInteger;
import java.util.List;

import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Component
@Getter
@Setter
@ToString
public class BookingShipmentDetails{
	private String messageType;
	private String sourceSystem;
	private String weightUnit;
	private String volumeUnit;
	private String shipmentPrefix;
	private String masterDocumentNumber;
	private String ubrNumber;
	private String shipmentOrigin;
	private String shipmentDestination;
	private String agentCode;
	private String customerCode;
	private String bookingSource;
	private String currency;
	private BigInteger totalNumberOfPieces;
	private double totalWeight;
	private double totalVolume;
	private String commodityCode;
	private String shipmentDescription;
	private String dummyDimensionValue;
	private String dimensionUnit;
	private String flightNumber;
	private String remarks;
	private String bookingRemarks;
	private String flightDate;
	private String segmentDepartureDate;
	private String carrierCode;
	private String segmentOrigin;
	private String segmentDestination;
	private List<DimensionDetails> dimensionDetails;
	private List<BookingFlightDetails> bookingFlightDetails;
	private BigInteger pieces;
	private double weight;
	private FlightPair flightPair;
	private List<String> partSuffixList;
	private String userId;
	private String shipmentDate;
	private String numberOfPieces;
	private String bookingStatus;
	private String lastUpdateuser;
	private String lastUpdateTime;
	private List<String> sccCodeList;
	private String svcIndicator;
	private String createdBy;
}
