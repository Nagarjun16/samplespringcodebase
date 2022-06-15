package com.ngen.cosys.impbd.shipment.breakdown.model;

import java.math.BigDecimal;
import java.math.BigInteger;

import javax.validation.Valid;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.validation.annotation.Validated;

import com.ngen.cosys.annotation.NgenCosysAppAnnotation;
import com.ngen.cosys.audit.NgenAuditField;
import com.ngen.cosys.model.FlightModel;

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
public class InboundBreakdownModel extends FlightModel {

	/**
	 * System Generated Serial Version id
	 */
	private static final long serialVersionUID = 7119281067901182164L;
	private BigInteger id;

	@NgenAuditField(fieldName = "Shipment")
	@NgenCosysAppAnnotation
	@Valid
	private InboundBreakdownShipmentModel shipment;
	
	@NgenAuditField(fieldName = "Hawb Info")
	private InboundBreakdownHAWBModel hawbInfo;

	private String flightNo;
	
	private String hawbNumber;

	private Boolean bulk = Boolean.FALSE;

	private Boolean flightArrivalCheck = Boolean.FALSE;

	@NgenAuditField(fieldName = "Group Location Function")
	private Boolean groupCreateLocation = Boolean.FALSE;

	private BigInteger shipmentId;
	private BigInteger breakDownPieces;
	private BigDecimal breakDownWeight;
	private String breakdownCompleted;
	private Boolean checkForRCFNFDtrigger = Boolean.FALSE;
	private Boolean checkForSQGroupCarrier = Boolean.FALSE;
	private Boolean flightHandledInSystem = Boolean.TRUE;
	private Boolean isShipmentTargetted = Boolean.FALSE;

}