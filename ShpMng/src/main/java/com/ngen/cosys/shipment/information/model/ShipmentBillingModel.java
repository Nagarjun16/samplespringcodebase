package com.ngen.cosys.shipment.information.model;

import java.math.BigDecimal;
import java.math.BigInteger;

import com.ngen.cosys.framework.model.BaseBO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ShipmentBillingModel extends BaseBO {
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;

	private String serviceType;
	private BigDecimal quantity;
	private BigInteger duration;
	private BigDecimal amount;
	private String paid;
	private String receiptNumber;
	private BigDecimal waivedAmount;

}
