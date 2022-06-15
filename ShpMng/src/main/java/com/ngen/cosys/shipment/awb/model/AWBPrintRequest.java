package com.ngen.cosys.shipment.awb.model;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ngen.cosys.framework.model.BaseBO;
import com.ngen.cosys.validator.annotations.CheckShipmentNumberConstraint;
import com.ngen.cosys.validator.enums.MandatoryType;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
@ApiModel
@Component
@ToString
@Setter
@Getter
@NoArgsConstructor
public class AWBPrintRequest  extends BaseBO{
	/**
	  * The default serialVersionUID.
	  */
	private static final long serialVersionUID = 1L;
	/**
	 * AWB number
	 */
	@CheckShipmentNumberConstraint(mandatory = MandatoryType.Type.REQUIRED)
	private String awbNumber;
	private List<String> awbNumbers;
	/**
	 * Tells printed copy or a original document
	 */
	private boolean photoCopy;
	
	
	private boolean documentReceivedFlag;
	/**
	 * Flight Number
	 */
	private String flight;
	/**
	 * Flight Departure time
	 */
	private  LocalDate flightDate;
	
	
	
	private String flightKeyId;
	private String printerName;
	
	private String flightOffPoint;
	private String destination;
	private String carrierCode;
	private BigInteger shipmentId;

}
