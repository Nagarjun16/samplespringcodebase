package com.ngen.cosys.flight.dao;

import java.util.List;

import com.ngen.cosys.flight.model.BatchJobsLog;
import com.ngen.cosys.flight.model.FlightSchedulePeriod;
import com.ngen.cosys.flight.model.OperativeFlight;
import com.ngen.cosys.flight.model.SchFlight;
import com.ngen.cosys.framework.exception.CustomException;

public interface ScheduleFlightDetailsDao {
	List<FlightSchedulePeriod> getScheduleList(FlightSchedulePeriod scedulePeriod) throws CustomException;

	Integer getFutureDays() throws CustomException;

	Long getSchedulePeriodId(FlightSchedulePeriod period) throws CustomException;

	Integer fetchFrequencyWithSchedulePeriodId(SchFlight schFlight) throws CustomException;

	Integer oprFlightExists(OperativeFlight oprFlight) throws CustomException;

	FlightSchedulePeriod findSchDetailsForSchPeriod(FlightSchedulePeriod period) throws CustomException;

	String getServiceFlightURL(String endpointURLAppYML) throws CustomException;

	long getJobIdFromBatch() throws CustomException;

	void saveBatchLogs(BatchJobsLog log) throws CustomException;
}