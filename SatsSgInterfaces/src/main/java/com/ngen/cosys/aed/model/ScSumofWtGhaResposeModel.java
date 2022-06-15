package com.ngen.cosys.aed.model;

import java.math.BigInteger;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.ngen.cosys.framework.model.BaseBO;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Component
@Getter
@Setter
@ApiModel
@JacksonXmlRootElement(localName = "SCSUMOFWTGHA")
@NoArgsConstructor
@JsonIgnoreProperties(value = { "createdBy", "createdOn", "modifiedBy", "modifiedOn", "flagUpdate", "flagDelete",
		"flagInsert", "flagSaved", "localDateFormat", "locale", "messageList", "loggedInUser", "flagCRUD", "tenantId",
		"terminal","userType","sectorId","shipmeNumber","aedMessageLogId","indicator","versionDateTime"})
@JsonPropertyOrder(value = { "SCSUMOFWTGHA", "HEADER", "DETAIL" })
public class ScSumofWtGhaResposeModel extends BaseBO {

	/**
	 * 
	 */

	private static final long serialVersionUID = 1L;

	private BigInteger aedMessageLogId;

	private String shipmeNumber;

	private int indicator;

	@JacksonXmlProperty(localName = "HEADER")
	private Header header;

	@JacksonXmlProperty(localName = "DETAIL")
	private ScSumOfWtGhaRequestDetails details;

}
