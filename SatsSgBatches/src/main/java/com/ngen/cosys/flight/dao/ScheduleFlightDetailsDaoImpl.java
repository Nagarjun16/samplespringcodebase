package com.ngen.cosys.flight.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.ngen.cosys.flight.model.BatchJobsLog;
import com.ngen.cosys.flight.model.FlightSchedulePeriod;
import com.ngen.cosys.flight.model.OperativeFlight;
import com.ngen.cosys.flight.model.SchFlight;
import com.ngen.cosys.flight.model.SchFlightJoint;
import com.ngen.cosys.flight.model.SchFlightLeg;
import com.ngen.cosys.flight.model.SchFlightSeg;
import com.ngen.cosys.flight.model.ScheduleFlightFact;
import com.ngen.cosys.framework.dao.BaseDAO;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.service.util.constants.ServiceURLConstants;

@Repository("scheduleFlightDetailsDaoImpl")
public class ScheduleFlightDetailsDaoImpl extends BaseDAO implements ScheduleFlightDetailsDao {

	private static final Logger LOGGER = LoggerFactory.getLogger(ScheduleFlightDetailsDao.class);

	@Autowired
	@Qualifier("sqlSessionTemplate")
	public SqlSession sqlSession;

	@Override
	public List<FlightSchedulePeriod> getScheduleList(FlightSchedulePeriod scedulePeriod) throws CustomException {
		return super.fetchList("findScheduleList", scedulePeriod, sqlSession);
	}

	@Override
	public Integer getFutureDays() throws CustomException {
		return super.fetchObject("getFutureDaysFromConfig", null, sqlSession);
	}

	@Override
	public Long getSchedulePeriodId(FlightSchedulePeriod period) throws CustomException {
		return super.fetchObject("fetchSchedulePeriodId", period, sqlSession);
	}

	@Override
	public Integer fetchFrequencyWithSchedulePeriodId(SchFlight schFlight) throws CustomException {
		return super.fetchObject("fetchSchedulewithFrequency", schFlight, sqlSession);
	}

	@Override
	public Integer oprFlightExists(OperativeFlight oprFlight) throws CustomException {
		return super.fetchObject("checkOprFlightExists", oprFlight, sqlSession);
	}

	@Override
	public FlightSchedulePeriod findSchDetailsForSchPeriod(FlightSchedulePeriod period) throws CustomException {
		FlightSchedulePeriod flightSchedulePeriodNew = super.fetchObject("fetchSchedulePeriod", period, sqlSession);
		List<SchFlight> schList = super.fetchList("fetchSchFlightDetailList", period, sqlSession);
		flightSchedulePeriodNew.setSchFlightList(schList);
		for (SchFlight schDetailList : flightSchedulePeriodNew.getSchFlightList()) {
			List<SchFlightLeg> legList = super.fetchList("fetchSchFlightLegs", schDetailList, sqlSession);
			schDetailList.setSchFlightLegs(legList);

			List<SchFlightSeg> segmentList = super.fetchList("fetchSchFlightsegs", schDetailList, sqlSession);
			schDetailList.setSchFlightSegments(segmentList);
			List<ScheduleFlightFact> factList = super.fetchList("findScheduleFacts", schDetailList, sqlSession);
			schDetailList.setFactList(factList);
			List<SchFlightJoint> schFlightJointList = super.fetchList("fetchScheduleJointFlightList", schDetailList,
					sqlSession);
			schDetailList.setSchFlightJointList(schFlightJointList);
		}

		return flightSchedulePeriodNew;
	}

	/**
	 * @param endpointURLAppYML
	 * @return
	 */
	@Override
	public String getServiceFlightURL(String endpointURLAppYML) throws CustomException {
		String endPointURL = sqlSession.selectOne(ServiceURLConstants.SQL_SERVICE_API_URL,
				ServiceURLConstants.API_OPERATIVE_FLIGHT_SAVE_CODE);
		if (StringUtils.isEmpty(endPointURL)) {
			endPointURL = endpointURLAppYML;
		}
		LOGGER.warn("Operative Flight endPointURL :: {}", endPointURL);
		return endPointURL;
	}

	@Override
	public long getJobIdFromBatch() throws CustomException {
		return super.fetchObject("fetchJobId", null, sqlSession);
	}

	@Override
	public void saveBatchLogs(BatchJobsLog log) throws CustomException {
		super.insertData("saveBatchInfo", log, sqlSession);

	}
}