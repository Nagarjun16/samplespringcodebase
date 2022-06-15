/**
 * 
 * SearchAWB.java
 *
 *
 * Version      Date         Author      Reason
 * 1.0          12 January 2018   NIIT      -
 */
package com.ngen.cosys.shipment.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ngen.cosys.JsonSerializer.LocalDateSerializer;
import com.ngen.cosys.annotation.InjectShipmentDate;
import com.ngen.cosys.framework.model.BaseBO;
import com.ngen.cosys.validator.annotations.CheckShipmentNumberConstraint;
import com.ngen.cosys.validator.enums.MandatoryType;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * This model class SearchAWB entity to search for shipment on hold
 *  i.e, by entering shipment number shipment on hold can be tracked
 * 
 * @author NIIT Technologies Ltd
 * @version 1.0
 */

@ApiModel
@Component
@Setter
@Getter
@NoArgsConstructor
public class SearchAWB extends BaseBO {
	
	/**
	 * The default serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Shipment Number 
	 */
	@CheckShipmentNumberConstraint(mandatory = MandatoryType.Type.REQUIRED)
	private String shipmentNumber;
	
	@InjectShipmentDate(shipmentNumberField = "shipmentNumber")
	@JsonSerialize(using = LocalDateSerializer.class)
	private LocalDate shipmentDate;
	
	/**
	 * flight number 
	 */
	private int flightId;
	
	/**
	 * flight number 
	 */
	private String flightKey;
	
	/**
	 * date for which shipment is on hold
	 */
	private LocalDateTime flightKeyDate;
	private String hawbNumber;
	private int shipmentId;
	private boolean handledbyHouse; 
}