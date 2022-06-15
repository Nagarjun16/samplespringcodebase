/**
 * 
 * ShipmentFlightPartDetails.java
 * 
 * Copyright <PRE><IMG SRC = XX></IMG></PRE>
 *
 * Version      Date         Author      Reason
 * 1.0          19 December, 2017   NIIT      -
 */
package com.ngen.cosys.icms.model.BookingInterface;

import org.springframework.stereotype.Component;

import com.ngen.cosys.framework.model.BaseBO;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * This model class represents ShipmentFlightPartDetails Request from UI.
 * 
 * @author NIIT Technologies Ltd.
 * @version 1.0
 */
@ApiModel
@Component
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ShipmentFlightPartDetails extends BaseBO {
	/**
	 * The default serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	private long flightPartBookingId;

	private long flightBookingId;

	private long partBookingId;
	private String bookingStatusCode;

}
