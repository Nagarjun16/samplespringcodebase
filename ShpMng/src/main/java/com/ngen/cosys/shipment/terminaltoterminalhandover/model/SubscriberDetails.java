package com.ngen.cosys.shipment.terminaltoterminalhandover.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ngen.cosys.JsonSerializer.LocalDateSerializer;
import com.ngen.cosys.JsonSerializer.LocalDateTimeSerializer;
import com.ngen.cosys.JsonSerializer.LocalTimeSerializer;
import com.ngen.cosys.framework.model.BaseBO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class SubscriberDetails extends BaseBO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5914935624191616628L;

	private String handedOverBy;

	@JsonSerialize(using = LocalDateTimeSerializer.class)
	private LocalDateTime handedOverOn;

	@JsonSerialize(using = LocalTimeSerializer.class)
	private LocalTime handedTime;

}