package com.ngen.cosys.icms.model.BookingInterface;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Component;

import com.ngen.cosys.framework.model.BaseBO;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * This model class represents Search SingleBooking Shipment Request from UI.
 * 
 * @author NIIT Technologies Ltd
 * @version 1.0
 */

@Getter
@Setter
@ToString 
@NoArgsConstructor
public class BookingDeltaDetails extends BaseBO {
	/**
	 * The default serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;
	private Integer bookingVersion;
	private Integer flightId;
	private String flightBoardPoint;
	private String flightOffPoint;
	private String shipmentNumber;
	private Integer shipmentBookingPieces;
	private Double shipmentBookingWeight;
	private boolean throughTransitFlag = false;
	private String bookingStatusCode;
	private String bookingChanges;
	private LocalDate shipmentDate;
	private String flightKey;
	private LocalDate flightDate;
	private String partSuffix;
}
