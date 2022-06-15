package com.ngen.cosys.shipment.information.model;

import java.math.BigDecimal;
import java.math.BigInteger;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ngen.cosys.JsonSerializer.LocalDateTimeSerializer; 
import java.time.LocalDateTime;
import com.ngen.cosys.framework.model.BaseBO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ShipmentDeliveryModel extends BaseBO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String doNumber;
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	private LocalDateTime deliveryDate;
	private BigInteger deliveryPieces;
	private BigDecimal deliveryWeight;
	private String locAuthority;
	private String referenceNumber;
	private String remarks;
	private String status;
}