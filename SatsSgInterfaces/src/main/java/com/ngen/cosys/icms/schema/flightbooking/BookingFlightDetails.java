package com.ngen.cosys.icms.schema.flightbooking;


import java.util.Date;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import com.ngen.cosys.framework.model.BaseBO;
import com.ngen.cosys.icms.util.BookingConstant;
import com.ngen.cosys.icms.validation.xmlValidator;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@XmlAccessorType(XmlAccessType.FIELD)
@ToString
@Setter
@Getter
public class BookingFlightDetails extends BaseBO {
	private static final long serialVersionUID = 1L;
	private int flightId;
	private String bookingFlightDetailsId;
	private String bookingDetailsId;
	private int serialNumber;
	private String segmentOrigin;
	private String segmentDestination;
	private String carrierCode;
	private String flightNumber;
	private String flightKey;
	private String segmentDepartureDate;
	private String segmentDepartureDateUTC;
	private int pieces;
	private double weight;
	private double displayWeight;
	private String allotmentId;
	private String aircraftType;
	private String flightBookingStatus;
	private double volume;
	private double displayVolume;
	private double volumeThreeDecimal;
	private int confirmedNumberOfPieces;
	private double confirmedWeight;
	private double confirmedVolume;
	private int routeNumber;
	private int flightSegNumber;
	private int shipmentRank;
	private String flightDate;
	private String segmentArrivalDate;
	private String segmentArrivalDateUTC;
	private int segmentSerialNumber;
	private int flightCarrierId;
	private int flightSequenceNumber;
	private String truckIndicator;
	private String remarks;
	
	private int balancePiece;
	private double balanceWeight;
	private double balanceVolume;
	public boolean isPairPresent;
	private String createdUserId = BookingConstant.CREATEDBY;
	private String modifiedUserId = BookingConstant.CREATEDBY;
	private String cosysSegmentDepartureDate;
	private String cosysSegmentArrivalDate;
	private String shipmentNumber;
	private String errorMessage;
	private String workedOnStatus;
}
