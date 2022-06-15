package com.ngen.cosys.aed.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.ngen.cosys.framework.model.BaseBO;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ApiModel
@JacksonXmlRootElement(localName = "SCINSPECTRMKGHA")
@NoArgsConstructor
@ToString
@JsonIgnoreProperties(value = { "createdBy", "createdOn", "modifiedBy", "modifiedOn", "flagUpdate", "flagDelete",
		"flagInsert", "flagSaved", "localDateFormat", "locale", "messageList", "loggedInUser", "flagCRUD", "tenantId",
		"terminal","userType","sectorId","aedMessageLogId","versionDateTime"})

@JsonPropertyOrder(value = { "SCINSPECTRMKGHA", "HEADER", "DETAIL", })
public class ScInspecRmkGhaResponseModel extends BaseBO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private long aedMessageLogId;

	@JacksonXmlProperty(localName = "HEADER")
	private Header header;

	@JacksonXmlProperty(localName = "DETAIL")
	private ScInspecRmkGhaRequestDetails details;

}
