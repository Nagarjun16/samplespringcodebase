package com.ngen.cosys.shipment.information.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ngen.cosys.JsonSerializer.LocalDateSerializer;
import com.ngen.cosys.JsonSerializer.LocalDateTimeSerializer;
import com.ngen.cosys.framework.model.BaseBO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ShipmentFreightOutInfoModel extends BaseBO{

	private static final long serialVersionUID = 1L;
	
	private BigInteger flightId;
	private BigInteger freightOutPieces;
	
	private String flightKey;
	private String remarks;
    private String shc;
    
	private BigDecimal freightOutWeight;
	
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	private LocalDateTime freightOutflightDate;
	
	//Added for Purge//
	private String carrierCode;
	private String flightNumber;
	@JsonSerialize(using = LocalDateSerializer.class)
	private LocalDate flightOriginDate;
	
}