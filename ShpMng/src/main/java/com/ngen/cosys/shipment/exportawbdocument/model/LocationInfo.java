package com.ngen.cosys.shipment.exportawbdocument.model;

import java.math.BigDecimal;
import java.math.BigInteger;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LocationInfo {
	private BigInteger locPcs;
	private BigDecimal locWeight;
	private String wareHouseLocation;
	private String shipmentLocation;
	private String locShc;
}
