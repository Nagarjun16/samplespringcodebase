package com.ngen.cosys.impbd.shipment.document.model;

import java.math.BigInteger;

import com.ngen.cosys.framework.model.BaseBO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class ShipmentMasterShcHandlingGroup extends BaseBO {
	/**
	 * System Generated Serial Version id
	 */
	private static final long serialVersionUID = 2172301450258653442L;
	private BigInteger id;
	private BigInteger shipmentId;
	private BigInteger handlingGroupId;
}