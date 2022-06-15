package com.ngen.cosys.shipment.information.model;

import java.math.BigInteger;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
@Getter
@Setter
@ToString
public class BookingFlightDetails {
	private String carrierCode;
	private BigInteger piece;
	private double weight;
	private double volume;
	private String segmentOrigin;
	private String segmentDestination;
	private String flightNumber;
	private String segmentDepartureDate;
	private String remark;
	private String flightDate;
	//needed for part booking;
	private String bookingStatus;
	private String flightKey;
	private String partSuffix;
}
