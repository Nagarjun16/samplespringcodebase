package com.ngen.cosys.aed.model;

import java.math.BigInteger;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.ngen.cosys.JsonSerializer.LocalDateTimeSerializer;

import groovy.transform.ToString;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@ApiModel
@ToString
@NoArgsConstructor
@JsonIgnoreProperties(value = { "createdBy", "createdOn", "modifiedBy", "modifiedOn", "flagUpdate", "flagDelete",
		"flagInsert", "flagSaved", "localDateFormat", "locale", "messageList", "loggedInUser", "flagCRUD", "tenantId",
		"terminal" })
@JsonPropertyOrder(value = { "MESSAGE_TYPE", "MESSAGE_ID", "SEND_DATETIME" })
public class Header {

	@JacksonXmlProperty(localName = "MESSAGE_TYPE")
	private String messageType;
	

	@JacksonXmlProperty(localName = "MESSAGE_ID")
	private BigInteger messageId;

	@JacksonXmlProperty(localName = "SEND_DATETIME")
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	private LocalDateTime sendDateTime;
}
