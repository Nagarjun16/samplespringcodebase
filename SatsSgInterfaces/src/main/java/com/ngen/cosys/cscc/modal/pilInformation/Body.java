package com.ngen.cosys.cscc.modal.pilInformation;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Body{
	@JsonProperty("PILs")
	private List<PILsItem> PILs;
}