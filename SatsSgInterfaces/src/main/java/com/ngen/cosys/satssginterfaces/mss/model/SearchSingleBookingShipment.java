/**
 * 
 * SearchSingleBookingShipment.java
 * 
 * Copyright <PRE><IMG SRC = XX></IMG></PRE>
 *
 * Version      Date            Author      Reason
 * 1.0          19 December, 2017    NIIT      -
 */
package com.ngen.cosys.satssginterfaces.mss.model;
import java.time.LocalDate;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ngen.cosys.JsonSerializer.LocalDateSerializer;
import com.ngen.cosys.annotation.InjectShipmentDate;
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
@ApiModel
@Component
@Getter
@Setter
@ToString
@AllArgsConstructor 
@NoArgsConstructor
public class SearchSingleBookingShipment extends BaseBO {
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
}
