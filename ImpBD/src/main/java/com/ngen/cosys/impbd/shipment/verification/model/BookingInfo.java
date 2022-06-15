package com.ngen.cosys.impbd.shipment.verification.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import org.springframework.stereotype.Component;

import com.ngen.cosys.framework.model.BaseBO;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ApiModel
@Component
@Setter
@Getter
@ToString
@NoArgsConstructor
public class BookingInfo extends BaseBO {

	/**
	 * default
	 */
	private static final long serialVersionUID = 1L;
	private BigInteger bookigId;
	private BigInteger incomingFlightId;
	private BigInteger awbPieces = BigInteger.ZERO;
	private BigDecimal awbWeight = BigDecimal.ZERO;
	private BigInteger bookingPieces = BigInteger.ZERO;
	private BigDecimal bookingWeight = BigDecimal.ZERO;
	private BigInteger partBookingId;
	private BigInteger partBookingPieces = BigInteger.ZERO;
	private BigDecimal partbookingWeight = BigDecimal.ZERO;
	private String flightBoardPoint;
	private String flightOffPoint;
	private BigInteger totalBookingPieces = BigInteger.ZERO;
	private BigDecimal totalBookingWeight = BigDecimal.ZERO;

}
