package com.ngen.cosys.satssginterfaces.mss.model;

import java.math.BigInteger;

import javax.validation.Valid;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.validation.annotation.Validated;

import com.ngen.cosys.annotation.NgenCosysAppAnnotation;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * InboundBreakdownModel represents the inbounds breakdown of Arrival manifest
 * details of a Flight.
 * 
 * 
 * @author NIIT Technologies Ltd
 * @version 1.0
 *
 */
@XmlRootElement
@ApiModel
@Setter
@Getter
@NoArgsConstructor
@Validated
public class InboundBreakdownAirmailInterfaceModel extends FlightAirlineInterfaceModel {

	/**
	 * System Generated Serial Version id
	 */
	private static final long serialVersionUID = 7119281067901182164L;
	private BigInteger id;
	@NgenCosysAppAnnotation
	@Valid
	private InboundBreakdownShipmentAirmailInterfaceModel shipment;
	
	private String flightNo;

}