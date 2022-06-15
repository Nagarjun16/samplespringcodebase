package com.ngen.cosys.satssginterfaces.mss.model;

import java.math.BigInteger;

import com.ngen.cosys.framework.model.BaseBO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class ShipmentMasterAirmailInterfaceRoutingInfo extends BaseBO {

	/**
	 * System Generated Serial Version id
	 */
	private static final long serialVersionUID = 6345873808074055416L;

	private BigInteger shipmentId;
	private BigInteger shipmentMasterRoutingId;

	private String fromPoint;
	private String carrier;

}