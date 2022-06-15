package com.ngen.cosys.shipment.information.model;

import java.math.BigInteger;

import com.ngen.cosys.framework.model.BaseBO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ShipmentDocumentBreadkdownStatusModel extends BaseBO {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BigInteger flightId;
	private String docComStatus;
	private String brkDwnComStatus;
	private BigInteger shipmentId;
}
