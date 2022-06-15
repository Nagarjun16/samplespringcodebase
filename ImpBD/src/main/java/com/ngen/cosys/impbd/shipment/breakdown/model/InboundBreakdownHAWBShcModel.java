package com.ngen.cosys.impbd.shipment.breakdown.model;

import java.math.BigInteger;

import com.ngen.cosys.framework.model.BaseBO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Setter
@Getter
@NoArgsConstructor
public class InboundBreakdownHAWBShcModel extends BaseBO{
	/**
	 * default
	 */
	private static final long serialVersionUID = 1L;
	
	private BigInteger shipmentHouseId;
	
	private BigInteger shipmentHouseShcId;
	
	private String specialHandlingCode;
}
