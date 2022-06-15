/**
 * Payload object for processing EAP Shipment
 */
package com.ngen.cosys.impbd.events.payload;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.ngen.cosys.framework.model.BaseBO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EAPShipmentsEvent extends BaseBO {

	private BigInteger flightId;

	private String flightKey;
	private String shipmentNumber;
	private String createdBy;

	private LocalDate shipmentDate;
	private LocalDate flightDate;
	private LocalDateTime createdOn;

}
