package com.ngen.cosys.shipment.terminaltoterminalhandover.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ngen.cosys.JsonSerializer.LocalDateSerializer;
import com.ngen.cosys.JsonSerializer.LocalDateTimeSerializer;
import com.ngen.cosys.framework.model.BaseBO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class ReceipentDetails extends BaseBO {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7785086817858380943L;
	private String receivedBy;
	private String receiverSignature;

	@JsonSerialize(using = LocalDateTimeSerializer.class)
	private LocalDateTime receivedDate;

	@JsonSerialize(using = LocalDateSerializer.class)
	private LocalDate receivedTime;
}