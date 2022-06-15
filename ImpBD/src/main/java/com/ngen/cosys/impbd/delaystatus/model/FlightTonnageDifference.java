package com.ngen.cosys.impbd.delaystatus.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ngen.cosys.JsonSerializer.LocalDateSerializer;
import com.ngen.cosys.framework.model.BaseBO;

import groovy.transform.ToString;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@ApiModel
@Getter
@Setter
@ToString
@NoArgsConstructor
public class FlightTonnageDifference extends BaseBO{
	
	 /**
	    * 
	    */
	   private static final long serialVersionUID = 1L;
	   
	   private BigDecimal manifestWeight;
	   
	   private BigDecimal breakdownWeight;
	   
	   private BigDecimal tonnageDifference;
	   
	   private String FlightKey;
	   
	   @JsonSerialize(using = LocalDateSerializer.class)
	   private LocalDate flightDate;
}
