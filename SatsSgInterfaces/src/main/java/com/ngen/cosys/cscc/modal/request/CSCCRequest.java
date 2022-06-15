package com.ngen.cosys.cscc.modal.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CSCCRequest {

	@JsonProperty("message")
	private RequestMessage message;

	public RequestMessage getMessage(){
		return message;
	}

	@Override
	public String toString() {
		return "CSCCRequest{" +
				"message=" + message +
				'}';
	}
}