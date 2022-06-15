package com.ngen.cosys.satssginterfaces.mss.model;

import java.math.BigInteger;

import com.ngen.cosys.framework.model.BaseBO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class ShipmentMasterAirmailInterfaceCustomerInfo extends BaseBO {
	/**
	 * System Generated Serial Version id
	 */
	private static final long serialVersionUID = 1780813367216153752L;

	private BigInteger id;
	private BigInteger shipmentId;
	
	private String customerType;
	private String customerCode;
	private String customerName;
	private String contactEmail;
	private String notifyPartyCode;
	private String notifyPartyName;

	private String accountNumber;
	private BigInteger appointedAgent;

	private Boolean overseasCustomer = Boolean.FALSE;

	private ShipmentMasterAirmailInterfaceCustomerAddressInfo address;

}