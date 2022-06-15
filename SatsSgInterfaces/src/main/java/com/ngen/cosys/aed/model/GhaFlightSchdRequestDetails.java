package com.ngen.cosys.aed.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

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
@JsonPropertyOrder(value = { "GHAFLIGHTSCHD_ID", "FLIGHT_SCHEDULE"})
public class GhaFlightSchdRequestDetails {

	@JacksonXmlProperty(localName = "GHAFLIGHTSCHD_ID")
	private String ghaFlightSchdId;

	@JacksonXmlElementWrapper(useWrapping = false)
	@JacksonXmlProperty(localName = "FLIGHT_SCHEDULE")
	private FlightSchedule[] flightSchdule;

}
