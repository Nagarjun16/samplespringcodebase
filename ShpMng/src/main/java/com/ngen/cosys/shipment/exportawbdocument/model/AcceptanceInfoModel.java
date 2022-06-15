package com.ngen.cosys.shipment.exportawbdocument.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ngen.cosys.JsonSerializer.LocalDateTimeSerializer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AcceptanceInfoModel {
	
    private BigInteger documentInformationId;
    
    private BigInteger houseInformationId;
	
	private String houseNumber;
	
	private String houseDate;
	
	private Boolean arrivalReportSent;
	
	private Boolean ackReceived;
	
	private BigInteger housePcs;
	
	private BigDecimal houseWeight;
	
	private List<LocationInfo> locationInfoList;
	
}
