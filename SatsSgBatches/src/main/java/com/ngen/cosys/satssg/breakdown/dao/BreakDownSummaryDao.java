package com.ngen.cosys.satssg.breakdown.dao;

import java.util.List;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.satssg.breakdown.model.BreakDownSummary;
import com.ngen.cosys.satssg.breakdown.model.BreakDownSummaryModel;
import com.ngen.cosys.satssg.breakdown.model.BreakDownSummaryProvider;
import com.ngen.cosys.satssg.breakdown.model.BreakDownSummaryTonnageHandledModel;
import com.ngen.cosys.satssg.breakdown.model.FlightDetails;

public interface BreakDownSummaryDao {
	
List<FlightDetails> getFlightDetails()throws CustomException;
	
	BreakDownSummaryModel getSummaryDetails(FlightDetails flightData)throws CustomException;
	
	Integer createSummary(BreakDownSummary summaryData)throws CustomException;
	
	Integer createTonnageSummary(BreakDownSummaryTonnageHandledModel tonnageData)throws CustomException;
	
	Integer updateFlightComplete(FlightDetails flightData)throws CustomException;
	
	Boolean checkSummaryExists(BreakDownSummary summaryData)throws CustomException;
	
	Integer updateBreakDownSummary(BreakDownSummary breakDownSummary) throws CustomException;
	
	BreakDownSummaryProvider getServiceContractorDetails(FlightDetails flightData)throws CustomException;

}
