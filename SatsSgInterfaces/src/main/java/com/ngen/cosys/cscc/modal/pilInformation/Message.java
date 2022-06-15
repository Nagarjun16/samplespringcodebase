package com.ngen.cosys.cscc.modal.pilInformation;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Message{
	@JsonProperty("header")
	private Header header;

	@JsonProperty("body")
	private Body body;
}