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
@JsonPropertyOrder(value = { "GHASCANINFO_ID", "RECORD_IND", "MAWB_NO", "INSPECTION_OUTCOME", "INSPECTION_DATETIME",
		"INSPECTION_REMARKS" })
public class GhaScanInfoRequestDetails {

	@JacksonXmlProperty(localName = "GHASCANINFO_ID")
	private String ghaScanInfoId;

	@JacksonXmlProperty(localName = "RECORD_IND")
	private String recordInd;

	@JacksonXmlProperty(localName = "MAWB_NO")
	private String mawbNo;

	@JacksonXmlProperty(localName = "INSPECTION_OUTCOME")
	private String inspectionOutcome;

	@JacksonXmlProperty(localName = "INSPECTION_DATETIME")
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	private LocalDateTime inspectionDateTime;

	@JacksonXmlProperty(localName = "INSPECTION_REMARKS")
	private String inspectionRemarks;

}
