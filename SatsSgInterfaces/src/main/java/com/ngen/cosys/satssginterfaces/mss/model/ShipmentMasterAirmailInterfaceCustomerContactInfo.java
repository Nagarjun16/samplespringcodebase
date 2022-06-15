package com.ngen.cosys.satssginterfaces.mss.model;

import java.math.BigInteger;

import com.ngen.cosys.framework.model.BaseBO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class ShipmentMasterAirmailInterfaceCustomerContactInfo extends BaseBO {

	/**
	 * System Generated Serial Version id
	 */
	private static final long serialVersionUID = 4030008443077329913L;

	private BigInteger id;
	private BigInteger shipmentAddressInfoId;

	private String contactTypeCode;
	private String contactTypeDetail;

}