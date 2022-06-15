package com.ngen.cosys.mailbag.overview.information.model;

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
public class AllStatusOfMailBag extends BaseBO {
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;

	private BigInteger shipmentId;
	private String mailBagNumber;
	
	private String breakdowndoneBy;
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	private LocalDateTime breakdowndoneOn;
	private String acceptedBy;
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	private LocalDateTime acceptedOn;
	private String transferredBy;
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	private LocalDateTime transferredOn;
	private String locationassignedBy;
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	private LocalDateTime locationassignedOn;
	private String bookingdoneBy;
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	private LocalDateTime bookingdoneOn;
	private String loadedBy;
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	private LocalDateTime loadedOn;
	private String manifestBy;
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	private LocalDateTime manifestOn;
	private String offloadedBy;
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	private LocalDateTime offloadedOn;
	private String dNataHandoverBy;
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	private LocalDateTime dNataHandoverOn;
	private String deliveredBy;
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	private LocalDateTime deliveredOn;
	private String returnedBy;
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	private LocalDateTime returnedOn;
	private String returnReason;
}
