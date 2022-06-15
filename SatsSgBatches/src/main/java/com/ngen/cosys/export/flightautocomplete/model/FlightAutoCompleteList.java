package com.ngen.cosys.export.flightautocomplete.model;

import java.util.List;

import com.ngen.cosys.framework.model.BaseBO;


public class FlightAutoCompleteList extends BaseBO{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<FlightAutoCompleteDetails> flightAutoCompleteDetails;

	public List<FlightAutoCompleteDetails> getFlightAutoCompleteDetails() {
		return flightAutoCompleteDetails;
	}

	public void setFlightAutoCompleteDetails(List<FlightAutoCompleteDetails> flightAutoCompleteDetails) {
		this.flightAutoCompleteDetails = flightAutoCompleteDetails;
	}

}
