package com.ngen.cosys.satssg.breakdown.service;

import java.util.List;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.satssg.breakdown.model.BreakDownSummary;
import com.ngen.cosys.satssg.breakdown.model.BreakDownSummaryModel;
import com.ngen.cosys.satssg.breakdown.model.BreakDownSummaryProvider;
import com.ngen.cosys.satssg.breakdown.model.FlightDetails;

public interface BreakDownSummaryService {

	void getFlightDetails()throws CustomException;
	
	void getSummaryDetails(FlightDetails flightData,BreakDownSummaryProvider serviceContractor)throws CustomException;
	
	void createSummary(BreakDownSummary summaryData)throws CustomException;
}
