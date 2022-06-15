package com.ngen.cosys.icms.schema.flightbooking;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
@XmlAccessorType(XmlAccessType.FIELD)
@ToString
@Setter
@Getter
public class FlightPair {
	private List<FlightDetails> flightDetails;
	private String pairParameterType;
}
