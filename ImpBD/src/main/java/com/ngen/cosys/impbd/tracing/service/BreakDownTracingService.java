package com.ngen.cosys.impbd.tracing.service;

import java.util.List;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.impbd.tracing.model.BreakDownTracingFlightModel;

public interface BreakDownTracingService {

   public List<BreakDownTracingFlightModel> fetchTracingList(BreakDownTracingFlightModel flightBreakdownData) throws CustomException;
	
	
}