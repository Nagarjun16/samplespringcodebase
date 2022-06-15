/**
 * This is an payload for post processing EAW shipments
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
public class EAWShipmentsEvent extends BaseBO {

	private BigInteger flightId;

	private String flightKey;
	private String shipmentNumber;
	private String createdBy;

	private LocalDate shipmentDate;
	private LocalDate flightDate;
	private LocalDateTime createdOn;

}
