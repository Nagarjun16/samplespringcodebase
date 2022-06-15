package com.ngen.cosys.flight.service;

import java.util.List;

import com.ngen.cosys.flight.model.BatchJobsLog;
import com.ngen.cosys.flight.model.OperativeFlight;
import com.ngen.cosys.framework.exception.CustomException;

public interface OperativeFlightScheduleService {
   List<OperativeFlight> fetchOperativeFlightSchedule() throws CustomException;

   String getServiceFlightURL(String endpointURLAppYML) throws CustomException;

   void performJobLog(BatchJobsLog logDetails)  throws CustomException;
}