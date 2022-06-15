package com.ngen.cosys.application.model;

import java.util.List;

import com.ngen.cosys.export.flightautocomplete.model.FlightAutoCompleteDetails;
import com.ngen.cosys.framework.model.BaseBO;

public class RcsSchedulerList extends BaseBO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<RcsSchedulerDetail> getRcsSchedulerDetails;
	public List<RcsSchedulerDetail> getGetRcsSchedulerDetails() {
		return getRcsSchedulerDetails;
	}
	public void setGetRcsSchedulerDetails(List<RcsSchedulerDetail> getRcsSchedulerDetails) {
		this.getRcsSchedulerDetails = getRcsSchedulerDetails;
	}



}
