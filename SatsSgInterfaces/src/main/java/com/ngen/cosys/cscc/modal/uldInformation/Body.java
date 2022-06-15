package com.ngen.cosys.cscc.modal.uldInformation;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Body{
	@JsonProperty("ULDs")
	private List<ULDsItem> ULDs;
}