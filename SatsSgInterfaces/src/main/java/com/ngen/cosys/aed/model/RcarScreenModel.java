package com.ngen.cosys.aed.model;

import java.math.BigDecimal;
import java.math.BigInteger;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@ApiModel
public class RcarScreenModel {
	
	private String reason;
	
	private String awbNuber;
	
	private BigInteger shipmentId;
	
	private String registeredIndicator;
 
	private BigDecimal finalizeWeight;
	
	private BigDecimal totalGrossWeight;
	
	private String origin;
	
	private String destination;
	
	

}
