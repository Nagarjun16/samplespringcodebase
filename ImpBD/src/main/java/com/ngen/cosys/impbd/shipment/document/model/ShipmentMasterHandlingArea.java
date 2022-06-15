package com.ngen.cosys.impbd.shipment.document.model;

import java.math.BigInteger;

import com.ngen.cosys.framework.model.BaseBO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class ShipmentMasterHandlingArea extends BaseBO {

	/**
	 * System Generated Serial Version id
	 */
	private static final long serialVersionUID = 7583718140553172789L;

	private BigInteger shipmentId;
	private BigInteger shipmentMasterHandlAreaId;
	private BigInteger handlingGroupId;

	private String handledBy;

}