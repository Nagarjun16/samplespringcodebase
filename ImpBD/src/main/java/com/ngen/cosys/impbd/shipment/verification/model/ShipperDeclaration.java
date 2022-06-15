/**
 * ShipperDeclarationDetail.java
 * 
 * Copyright
 * 
 * <PRE>
 * <IMG SRC = XX></IMG>
 * </PRE>
 *
 * Version Date Author Reason 1.0 06 MAR, 2018 NIIT -
 */
package com.ngen.cosys.impbd.shipment.verification.model;

import java.util.List;

import javax.validation.Valid;

import org.hibernate.validator.constraints.Length;
import org.springframework.stereotype.Component;

import com.ngen.cosys.annotation.NgenCosysAppAnnotation;
import com.ngen.cosys.framework.model.BaseBO;
import com.ngen.cosys.validator.annotations.CheckAirportCodeConstraint;
import com.ngen.cosys.validator.annotations.CheckShipmentNumberConstraint;
import com.ngen.cosys.validator.enums.MandatoryType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * This class takes care of the responsibilities related to the DG Declaration
 * screen data
 * 
 * @author NIIT Technologies Ltd
 *
 */

@Component
@ToString
@Getter
@Setter
@NgenCosysAppAnnotation
@NoArgsConstructor
public class ShipperDeclaration extends BaseBO {

	private static final long serialVersionUID = 1L;

	private Long expDgShipperDeclarationId;

	private Short dgdReferenceNo;

	@CheckShipmentNumberConstraint(mandatory = MandatoryType.Type.REQUIRED)
	private String shipmentNumber;

	private String transhipmentFlag;

	@CheckAirportCodeConstraint
	private String departureAirport;
	@Length(max = 3, message = "ERROR_3_CHARACTERS_IN_DESTINATION_AIRPORT_FIELD")
	@CheckAirportCodeConstraint
	private String destinationAirport;
	@Length(max = 3, message = "ERROR_3_CHARACTERS_IN_AIRCRAFTTYPE_FIELD")
	private String aircraftType;
	@Length(max = 3, message = "ERROR_3_CHARACTERS_IN_SHIPMENT_RADIOACTIVEFLAG_FIELD")
	private String shipmentRadioactiveFlag;
	@Length(max = 250, message = "ERRRO_250_CHARACTERS_IN_OVERPACKNUMBER_FIELD")
	private String additionalHandlingInformation;
	@Valid
	private List<ShipperDeclarationDetail> declarationDetails;

	private short shipmentID;

	private String origin;

	private String destination;

}
