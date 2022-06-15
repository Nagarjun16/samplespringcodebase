package com.ngen.cosys.aed.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

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
		"terminal", "aedMessageRemarksId", "aedMessageLogId","indicator" })
@JsonPropertyOrder(value = { "INDICATOR_TO_GHA,REMARKS_TO_GHA" })
public class ScInspecRmkGhaInspectInd {

	private long aedMessageLogId;

	private int indicator;

	@JacksonXmlProperty(localName = "INDICATOR_TO_GHA")
	private String indicatorToGha;

	@JacksonXmlProperty(localName = "REMARKS_TO_GHA")
	private String remarksToGha;
}
