package com.ngen.cosys.export.flightautocomplete.model;

import java.math.BigInteger;
import java.time.LocalDateTime;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ngen.cosys.JsonSerializer.LocalDateTimeSerializer;
import com.ngen.cosys.framework.model.BaseBO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Uld extends BaseBO {
	/**
	 * Default serial version id
	 */
	private static final long serialVersionUID = 1L;
	
	private String uldTrolleyNo;
	private String contentCode;
	private BigInteger flightId;
	
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	private LocalDateTime flightCompletedOn; 
	
	private BigInteger uldId;
	
	private String handlingCarrierCode;
}
