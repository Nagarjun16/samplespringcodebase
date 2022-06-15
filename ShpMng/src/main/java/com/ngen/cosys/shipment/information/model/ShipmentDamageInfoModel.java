package com.ngen.cosys.shipment.information.model;

import java.math.BigInteger;

import com.ngen.cosys.framework.model.BaseBO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ShipmentDamageInfoModel extends BaseBO {
	
	private static final long serialVersionUID = 1L;
	
	private BigInteger referenceId;
	private BigInteger flightId;
	private String natureOfDamage;
	private BigInteger damagedPieces;
	private String occurrence;
	private String severity;

}
