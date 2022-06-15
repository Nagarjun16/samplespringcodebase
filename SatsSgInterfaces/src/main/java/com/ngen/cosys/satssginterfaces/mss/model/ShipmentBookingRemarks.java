/**
 * 
 * ShipmentBookingRemarks.java
 * 
 * Copyright <PRE><IMG SRC = XX></IMG></PRE>
 *
 * Version      Date            Author      Reason
 * 1.0          22 December, 2017    NIIT      -
 */
package com.ngen.cosys.satssginterfaces.mss.model;

import org.springframework.stereotype.Component;

import com.ngen.cosys.framework.model.BaseBO;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * This model class represents ShipmentBookingRemarks Request from UI.
 * 
 * @author NIIT Technologies Ltd
 * @version 1.0
 */
@ApiModel
@Component
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ShipmentBookingRemarks extends BaseBO {
	/**
	 * The default serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;
	private long bookingRemarksId;
	private long flightBookingId;
	private Long partBookingId;
	private String remarkType;
	private String remarks;
}
