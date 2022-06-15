package com.ngen.cosys.impbd.tracing.model;


import java.math.BigInteger;

import com.ngen.cosys.framework.model.BaseBO;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@ApiModel
@Getter
@Setter
@NoArgsConstructor
public class ShipmentLocationDetails extends BaseBO {/**
	 * 
	 */
	private static final long serialVersionUID = 635890041862472146L;
	
	private String  uldNumber;
	
	private BigInteger shipmentId; 
	
	private String shipmentLocation;
	
	private BigInteger pieces;
	
	private String warehouseLocation;
	
	private String shc;
	
	private BigInteger flightID; 
	
	private String HAWBNumber;
	

}
