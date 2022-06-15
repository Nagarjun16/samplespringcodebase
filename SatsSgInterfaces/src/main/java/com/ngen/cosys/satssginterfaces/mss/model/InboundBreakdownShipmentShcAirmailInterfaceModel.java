package com.ngen.cosys.satssginterfaces.mss.model;

import java.math.BigInteger;

import com.ngen.cosys.validator.annotations.CheckShipmentNumberConstraint;
import com.ngen.cosys.validator.enums.MandatoryType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class InboundBreakdownShipmentShcAirmailInterfaceModel extends SHCAirmailInterfaceModel {
	/**
	 * System Generated Serial Version id
	 */
	private static final long serialVersionUID = -75719742529682581L;

	private BigInteger inboundBreakDownShipmentId;
	private BigInteger shipmentInventoryId;

	@CheckShipmentNumberConstraint(message = "shipmentNumber.can.not.be.blank", mandatory = MandatoryType.Type.REQUIRED)
	private String bhShipmentNumber;

}