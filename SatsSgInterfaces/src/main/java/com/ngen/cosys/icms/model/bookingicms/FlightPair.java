package com.ngen.cosys.icms.model.bookingicms;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
@Getter
@Setter
@ToString
public class FlightPair {
	private List<FlightPairDetails> flightPairDetails;
}
