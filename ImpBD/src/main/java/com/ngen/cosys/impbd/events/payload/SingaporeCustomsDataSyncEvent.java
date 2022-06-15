package com.ngen.cosys.impbd.events.payload;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * ShipmentDiscrepancyEvent instance
 * 
 * @author NIIT Technologies Ltd
 * @version 1.0
 */
@Getter
@Setter
@NoArgsConstructor
public class SingaporeCustomsDataSyncEvent {

	private BigInteger flightId;
	
	private String flightKey;
	private String tenantId;
	private String eventType;
	private String createdBy;
	
	private LocalDate flightDate;
	
	private LocalDateTime createdDate;
	
}
