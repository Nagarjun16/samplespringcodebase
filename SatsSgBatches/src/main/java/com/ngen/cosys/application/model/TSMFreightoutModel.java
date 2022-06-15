package com.ngen.cosys.application.model;

import java.math.BigInteger;
import java.time.LocalDate;

import com.ngen.cosys.framework.model.BaseBO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TSMFreightoutModel extends BaseBO {
	/**
	 * default 
	 */
	private static final long serialVersionUID = 1L;
	private BigInteger shipmentId;
	private BigInteger dataSyncShipmentId;
	private BigInteger shipmentInventoryId;
	private BigInteger freightOutId;
	private String shipmentNumber;
	private LocalDate shipmentDate;
	private String shipmentSuffix;
	private BigInteger flightId;
	private String remarks;
	private String messageType;
	
}
