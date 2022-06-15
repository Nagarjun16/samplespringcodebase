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
@NoArgsConstructor
@ApiModel
@JsonIgnoreProperties(value = { "createdBy", "createdOn", "modifiedBy", "modifiedOn", "flagUpdate", "flagDelete",
		"flagInsert", "flagSaved", "localDateFormat", "locale", "messageList", "loggedInUser", "flagCRUD", "tenantId",
		"terminal" })
@JsonPropertyOrder(value = { "RECORD_IND", "MAWB_NO" })
public class MawbDeatails {

	@JacksonXmlProperty(localName = "RECORD_IND")
	private String recordIndicator;

	@JacksonXmlProperty(localName = "MAWB_NO")
	private String mawbNo;

}
