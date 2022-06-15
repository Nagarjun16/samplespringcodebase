package com.ngen.cosys.cscc.modal.keyShipmentInformation;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.annotation.Generated;
import java.io.Serializable;

@Getter
@Setter
@ToString
public class Message implements Serializable {
	@JsonProperty("header")
	private Header header;

	@JsonProperty("body")
	private List<BodyItem> body;

}