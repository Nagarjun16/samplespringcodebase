package com.ngen.cosys.impbd.tracing.dao;

import java.math.BigInteger;
import java.util.List;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.impbd.tracing.model.BreakDownTracingFlightModel;
import com.ngen.cosys.impbd.tracing.model.BreakDownTracingFlightSegmentModel;

public interface BreakDownTracingDAO {

   public List<BreakDownTracingFlightModel> fetchTracingList(BreakDownTracingFlightModel flightBreakdownData) throws CustomException;
   
   public BigInteger getUldCount(BreakDownTracingFlightSegmentModel segmentData)throws CustomException;

}