package com.ngen.cosys.shipment.model;

import java.math.BigInteger;
import java.time.LocalDateTime;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ngen.cosys.JsonSerializer.LocalDateTimeSerializer;
import com.ngen.cosys.framework.model.BaseBO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class CdhDocumentmaster extends BaseBO {
	/**
	 * System generated UID
	 */
	private static final long serialVersionUID = 1L;

	private BigInteger cdhdocumentmasterid;

	private int copyno;

	private String documentstatus;

	private BigInteger cdhpigeonholelocationid;

	private BigInteger cdhflightpouchid;

	@JsonSerialize(using = LocalDateTimeSerializer.class)
	private LocalDateTime storeddate;

	@JsonSerialize(using = LocalDateTimeSerializer.class)
	private LocalDateTime receiveddate;

	@JsonSerialize(using = LocalDateTimeSerializer.class)
	private LocalDateTime returneddate;

	@JsonSerialize(using = LocalDateTimeSerializer.class)
	private LocalDateTime notifieddate;

	private Boolean discrepancyflag;

	private String remarks;

	private String destination;

	private String deletereasoncode;

	private Boolean deleteflag;
	
	private String deleteremarks;

	private String carriercode;

	private String flightoffpoint;

	@JsonSerialize(using = LocalDateTimeSerializer.class)
	private LocalDateTime labelpartdate;

	private String createdusercode;

	@JsonSerialize(using = LocalDateTimeSerializer.class)
	private LocalDateTime createddatetime;

	private String lastupdatedusercode;

	@JsonSerialize(using = LocalDateTimeSerializer.class)
	private LocalDateTime lastupdateddatetime;

	private BigInteger shipmentid;
}