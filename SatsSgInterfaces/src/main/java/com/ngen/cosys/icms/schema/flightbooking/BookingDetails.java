package com.ngen.cosys.icms.schema.flightbooking;

import java.time.LocalDate;
import java.util.List;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import org.springframework.stereotype.Component;
import com.ngen.cosys.framework.model.BaseBO;
import com.ngen.cosys.icms.schema.flightbooking.BookingCommodityDetails;
import com.ngen.cosys.icms.schema.flightbooking.BookingFlightDetails;
import com.ngen.cosys.icms.schema.flightbooking.OtherChargeDetails;
import com.ngen.cosys.icms.schema.flightbooking.ShipmentDetails;
import com.ngen.cosys.icms.schema.flightbooking.ShipmentIdentifierDetails;
import com.ngen.cosys.icms.util.BookingConstant;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@XmlAccessorType(XmlAccessType.FIELD)
@ToString
@Setter
@Getter
@Component
public class BookingDetails extends BaseBO{
	private String bookingDetailsId;
	private String ubrNumber;
	private String bookingRemarks;
	private String handlingInformation;
	private String bookingStatus;
	private int slacPieces;
	private String capacityType;
	private String baselineStatus;
	private boolean ccShipmentIndicator;
	private String latestAcceptanceTime;
	private String latestAcceptanceTimeUTC;
	private String shipmentCategory;
	private String userStation;
	private boolean isSplitBooking;
	private int bookingVersion;
	private double airFreightCharge;
	private String weightUnit;
	private String volumeUnit;
	private String dimensionUnit;
	private String lastUpdateSource;
	private boolean isAWBAutoPopulatedFromStock;
	private String bookingDate;
	private String bookingDateUTC;
	private String createdUser;
	private String sccCodes;
	private int latModifiedCounter;
	private String autoAllocationEligible;
	private String uniqueReference;
	private String volume;
	private String AWBNumber;
	private String serviceCargoClass;
	private String serviceCode;
	private int workedOnPiece = 0;
	private double workedOnWeight = 0.0;
	private BookingFlightPairDetails bookingFlightPairDetails;
	ShipmentIdentifierDetails shipmentIdentifierDetails;
	ShipmentDetails shipmentDetails;
	List<BookingCommodityDetails> bookingCommodityDetails;
	List<OtherChargeDetails> otherChargeDetails;
	List<BookingFlightDetails> bookingFlightDetails;
	List<BookingDetails> cosysBookingDetails;
	private LocalDate shipmentDate;
	Boolean isShipmentCancelled;
	
	private String createdUserId = BookingConstant.CREATEDBY;
	private String modifiedUserId = BookingConstant.CREATEDBY;
	}
