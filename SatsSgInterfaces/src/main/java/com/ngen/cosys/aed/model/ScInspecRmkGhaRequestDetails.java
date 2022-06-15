package com.ngen.cosys.aed.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.ngen.cosys.JsonSerializer.LocalDateTimeSerializer;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@ApiModel
@NoArgsConstructor
@JsonIgnoreProperties(value = { "createdBy", "createdOn", "modifiedBy", "modifiedOn", "flagUpdate", "flagDelete",
		"flagInsert", "flagSaved", "localDateFormat", "locale", "messageList", "loggedInUser", "flagCRUD", "tenantId",
		"terminal","userType","sectorId","aedMessageLogId","shipmentDate","versionDateTime" })
@JsonPropertyOrder(value = { "SCINSPECTRMKGHA_ID", "MAWB_NO", "PERMIT_NO", "INSPECT_IND_GHA" })
public class ScInspecRmkGhaRequestDetails {

	private long aedMessageLogId;

	@JsonSerialize(using = LocalDateTimeSerializer.class)
	private LocalDate shipmentDate;

	@JacksonXmlProperty(localName = "SCINSPECTRMKGHA_ID")
	private String scSumOfWtghaId;

	@JacksonXmlProperty(localName = "MAWB_NO")
	private String mawbNo;

	@JacksonXmlProperty(localName = "PERMIT_NO")
	private String permitNo;

	@JacksonXmlElementWrapper(useWrapping = false)
	@JacksonXmlProperty(localName = "INSPECT_IND_GHA")
	private ScInspecRmkGhaInspectInd[] inspectIndGha;

}
