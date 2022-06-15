package com.ngen.cosys.uncollectedfreightout.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ngen.cosys.JsonSerializer.LocalDateTimeSerializer;
import com.ngen.cosys.framework.model.BaseBO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
public class UncollectedFreightOutEvent extends BaseBO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	   
	private String shipmentNumber;
	   private String natureOfGoods;
	   private BigInteger pieces;
	   private BigDecimal weight;
	   private String origin;
	   private String chargeCode;
	   private String flight;
	   //@JsonSerialize(using = LocalDateTimeSerializer.class)
	   private String flightDate;
	   private String reminder1;
	   private String reminder2;
	   private String reminder3;
	   private String reminder4;
	   private String abandon;
	   private String irpRefNo;
       private String agentName;
       private String collectionTerminal;
       private String awbNumber;
 

}
