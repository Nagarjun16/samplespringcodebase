package com.ngen.cosys.impbd.delaystatus.service;

import java.util.List;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.impbd.delaystatus.model.DelayStatusSearch;
import com.ngen.cosys.impbd.summary.model.BreakDownSummaryModel;

public interface BreakDownStatusService {

	List<BreakDownSummaryModel> fetchDelayList(DelayStatusSearch flightData)throws CustomException;
	
	void closeFlight(DelayStatusSearch flightData)throws CustomException;
}
