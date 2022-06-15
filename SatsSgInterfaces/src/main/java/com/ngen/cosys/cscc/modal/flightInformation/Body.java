package com.ngen.cosys.cscc.modal.flightInformation;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.annotation.Generated;
import java.io.Serializable;

@Getter
@Setter
@ToString
public class Body implements Serializable {
	@JsonProperty("flights")
	private List<FlightsItem> flights;
}