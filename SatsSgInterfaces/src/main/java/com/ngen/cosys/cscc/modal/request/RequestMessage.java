package com.ngen.cosys.cscc.modal.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RequestMessage {

	@JsonProperty("header")
	private RequestHeader header;

	@JsonProperty("body")
	private RequestBody body;

	public RequestHeader getHeader(){
		return header;
	}

	public RequestBody getBody(){
		return body;
	}
}