package com.ngen.cosys.icms.model.bookingicms;


import java.math.BigInteger;

import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Component
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
	private String partSuffix;
	private String bookingStatus;
	
}
