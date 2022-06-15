package com.ngen.cosys.satssginterfaces.mss.model;

import java.math.BigInteger;
import java.util.List;

import com.ngen.cosys.framework.model.BaseBO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class ShipmentMasterAirmailInterfaceCustomerAddressInfo extends BaseBO {
	/**
	 * System Generated Serial Version id
	 */
	private static final long serialVersionUID = 4267660863797366845L;

	private BigInteger id;
	private BigInteger shipmentCustomerInfoId;
	private BigInteger shipmentCustomerAddInfoId;

	private String streetAddress;
	private String place;
	private String postal;
	private String stateCode;
	private String countryCode;

	private List<ShipmentMasterAirmailInterfaceCustomerContactInfo> contacts;

}