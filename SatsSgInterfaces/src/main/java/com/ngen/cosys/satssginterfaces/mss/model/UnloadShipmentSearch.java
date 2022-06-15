/**
 * 
 * UnloadShpRequest.java
 * 
 * 
 * Copyright <PRE><IMG SRC = XX></IMG></PRE>
 *
 * Version Date Author Reason 1.0 2 February,2018 NIIT -
 */
package com.ngen.cosys.satssginterfaces.mss.model;

import java.time.LocalDate;
import java.util.List;

import javax.validation.Valid;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ngen.cosys.JsonSerializer.LocalDateSerializer;
import com.ngen.cosys.annotation.InjectShipmentDate;
import com.ngen.cosys.annotation.NgenCosysAppAnnotation;
import com.ngen.cosys.framework.model.BaseBO;
import com.ngen.cosys.validator.annotations.CheckContainerNumberConstraint;
import com.ngen.cosys.validator.annotations.CheckShipmentNumberConstraint;
import com.ngen.cosys.validator.enums.MandatoryType;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
/**
*
* This class represents the model for Unload shipment request
* 
* @author NIIT Technologies Ltd
* @version 1.0
*/
@ApiModel
@Component
@ToString
@Getter
@Setter
@NoArgsConstructor
@NgenCosysAppAnnotation
public class UnloadShipmentSearch extends BaseBO{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Flight details to which ULD is assigned
	 */
	@Valid	
	Flight flight;
	/**
	 * ULD from which shipment to be unloaded
	 */
	@CheckContainerNumberConstraint(mandatory = MandatoryType.Type.REQUIRED)
	private String uldNumber;
	/**
	 * AWB numbers of the shipment to be unloaded
	 */	
	private List<String> shipmentNumbers;
	/**
	 * AWB number of the shipment to be unloaded
	 */	
   @CheckShipmentNumberConstraint(mandatory = MandatoryType.Type.NOTREQUIRED)
    @NotBlank(message="g.mandatory")
	private String shipmentNumber;
	@JsonSerialize(using = LocalDateSerializer.class)
    @InjectShipmentDate(shipmentNumberField = "shipmentNumber")
	private LocalDate shipmentDate;
	@NotBlank(message="g.mandatory")
    private  String reason;
	private Segment segment;
	@NgenCosysAppAnnotation
	private Shipment shipment;
	@NgenCosysAppAnnotation
	private List<Shipment> shipmentNumberList;


	
}
