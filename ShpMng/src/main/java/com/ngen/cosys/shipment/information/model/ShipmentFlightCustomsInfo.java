package com.ngen.cosys.shipment.information.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ngen.cosys.JsonSerializer.LocalDateTimeSerializer;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ShipmentFlightCustomsInfo {

	@JsonSerialize(using = LocalDateTimeSerializer.class)
	private LocalDateTime rfe;
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	private LocalDateTime ffe;
	private String boe;
	private String oc;
	private String cav;
	private String edoNumber;
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	private LocalDateTime edoDate;
	private String totalDuty;
}
