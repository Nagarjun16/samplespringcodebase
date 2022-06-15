package com.ngen.cosys.cscc.modal.keyShipmentInformation;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ngen.cosys.cscc.modal.BaseMessageObject;
import com.ngen.cosys.cscc.modal.Errors;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
public class KeyShipmentInformation extends BaseMessageObject implements Serializable {
	@JsonProperty("message")
	private Message message;
}