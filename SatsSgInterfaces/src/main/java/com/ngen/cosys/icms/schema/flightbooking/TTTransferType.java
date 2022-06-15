package com.ngen.cosys.icms.schema.flightbooking;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TTTransferType {
	
	private String code;
	private String transferType;
	private String equation;
	private String fromMinutes;
	private String toMinutes;	
	private Integer minConnTime;
	
}
