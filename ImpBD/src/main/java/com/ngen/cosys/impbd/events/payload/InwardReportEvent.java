package com.ngen.cosys.impbd.events.payload;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ngen.cosys.JsonSerializer.LocalDateTimeSerializer;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
public class InwardReportEvent {
	private BigInteger flightId;
    private String segmentId;
    private String segmentDesc;
	private String flightKey;
	private String shipmentNumber;
	private String tenantId;
	private String createdBy;
    
	private LocalDate shipmentDate;
	private LocalDate flightDate;
	private LocalDateTime createdOn;
    private String carrierCode ;
    private String emailAddress[];
	private String damagestatus;
	private String registration;
	private String inwardfinalizeBy;
	private LocalDateTime inwardfinalizeAt;
	private String cargoDamageCompletedBy;
	private LocalDateTime cargoDamageCompletedAt;
	
}
