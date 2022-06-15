/**
 * 
 * BookingShipment.java
 * 
 * Copyright <PRE><IMG SRC = XX></IMG></PRE>
 *
 * Version      Date            Author      Reason
 * 1.0          19 December, 2017    NIIT      -
 */
package com.ngen.cosys.icms.model.BookingInterface;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ngen.cosys.JsonSerializer.LocalDateSerializer;
import com.ngen.cosys.annotation.InjectShipmentDate;
import com.ngen.cosys.framework.model.BaseBO;
//import com.ngen.cosys.validator.annotations.CheckHandleShipment;
import com.ngen.cosys.validator.annotations.CheckShipmentNumberConstraint;
import com.ngen.cosys.validator.annotations.UserCarrierValidation;
import com.ngen.cosys.validator.enums.MandatoryType;
import com.ngen.cosys.icms.validation.ShipmentNotCheckGroup;

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
@ApiModel
@Component
@Getter
@Setter
@ToString
@AllArgsConstructor 

@NoArgsConstructor
@UserCarrierValidation(shipmentNumber = "shipmentNumber", flightKey = "", loggedInUser = "loggedInUser", type = "AWB")
@CheckShipmentNumberConstraint(mandatory = MandatoryType.Type.REQUIRED, shipmentNumberField = "shipmentNumber", groups = {
		ShipmentNotCheckGroup.class })
public class BookingShipment extends BaseBO {
	/**
	 * The default serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;
	@NotBlank(message = "g.required")
    @Size(min=1, max=11, message = "export.awb.number.length.validation")
	
	private String shipmentNumber;
	
	@JsonSerialize(using = LocalDateSerializer.class)
    @InjectShipmentDate(shipmentNumberField = "shipmentNumber")
    private LocalDate shipmentDate;
	
	private int blockSpace;
	
	private String suffix;
	
	private String flagUpdate ="Y";
	
	private BigInteger flightId;
	private List<String> FlightKeyList;
}
