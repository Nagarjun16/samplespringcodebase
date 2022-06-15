package com.ngen.cosys.aed.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.ngen.cosys.JsonSerializer.LocalDateTimeSerializer;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@ApiModel
@JsonIgnoreProperties(value = { "createdBy", "createdOn", "modifiedBy", "modifiedOn", "flagUpdate", "flagDelete",
		"flagInsert", "flagSaved", "localDateFormat", "locale", "messageList", "loggedInUser", "flagCRUD", "tenantId",
		"terminal" })
@JsonPropertyOrder(value = { "RECORD_IND", "MAWB_NO", "FLIGHT_DEPART_DATETIME", })
public class FlightSchedule {

	@JacksonXmlProperty(localName = "RECORD_IND")
	private String recordIndicator;

	@JacksonXmlProperty(localName = "MAWB_NO")
	private String mawbNo;

	@JacksonXmlProperty(localName = "FLIGHT_DEPART_DATETIME")
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	private LocalDateTime flightDepartDateTime;

}
