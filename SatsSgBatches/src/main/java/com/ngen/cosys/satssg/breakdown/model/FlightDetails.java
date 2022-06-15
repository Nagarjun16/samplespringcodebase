package com.ngen.cosys.satssg.breakdown.model;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ngen.cosys.JsonSerializer.LocalDateSerializer;
import com.ngen.cosys.framework.model.BaseBO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FlightDetails extends BaseBO {
	
	private BigInteger fctTime;
	
	private BigInteger ataTime;
	
	private BigInteger stpTime;
	
	private BigInteger flightId;
	
	private BigInteger delay;
	
	private String carrierCode;
	
	private String flightNumber;
	
	@JsonSerialize(using= LocalDateSerializer.class)
	private LocalDate flightDate;

}
