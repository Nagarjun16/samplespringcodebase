package com.ngen.cosys.cscc.modal.uldInformation;

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