package com.ngen.cosys.platform.rfid.tracker.feeder.dao;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.ngen.cosys.framework.dao.BaseDAO;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.multitenancy.utilities.MultiTenantUtility;
import com.ngen.cosys.platform.rfid.tracker.feeder.model.JobStatus;
import com.ngen.cosys.platform.rfid.tracker.feeder.model.ScanFeed;
import com.ngen.cosys.platform.rfid.tracker.interfaces.model.AuthModel;

@Repository
public class FeederRepositoryImpl extends BaseDAO implements FeederRepository {

	@Autowired
	@Qualifier("sqlSessionTemplate")
//	private SqlSessionTemplate sqlSession;
	private SqlSession sqlSession;

	@Override
	public JobStatus getJobStatus() throws CustomException {
		return super.fetchObject("getJobStatus", "", sqlSession);
	}

	@Override
	public void updateJobStatusLastJobRun(JobStatus jobStatus) throws CustomException {
		super.updateData("updateJobStatusLastJobRun", jobStatus, sqlSession);
	}

	@Override
	public void updateJobStatusLastSequenceNumber(JobStatus jobStatus) throws CustomException {
		super.updateData("updateJobStatusLastSequenceNumber", jobStatus, sqlSession);
	}

	@Override
	public List<ScanFeed> getScanFeeds(JobStatus jobStatus) throws CustomException {
		return super.fetchList("getScanFeeds", jobStatus, sqlSession);
	}

	@Override
	public Integer getAWBTotalTags(ScanFeed scanFeed) throws CustomException {
		return fetchObject("getAWBTotalTags", scanFeed, sqlSession);
	}

	@Override
	public Integer getULDTotalTags(ScanFeed scanFeed) throws CustomException {
		return super.fetchObject("getULDTotalTags", scanFeed, sqlSession);
	}

	@Override
	public AuthModel getAuthUserPassword() throws CustomException {
		return super.fetchObject("getAuthUserPasswordRFID", "", sqlSession);
	}

	@Override
	public List<ScanFeed> getScanFeedsFromBuffer() throws CustomException {
		return super.fetchList("getScanFeedsFromBuffer", "", sqlSession);
	}

	@Override
	public void copyScanFeedToBuffer(ScanFeed scanFeed) throws CustomException {
		super.insertData("copyScanFeedToBuffer", scanFeed, sqlSession);
	}

	@Override
	public void deleteScanFeedFromBuffer(ScanFeed scanFeed) throws CustomException {
		super.updateData("deleteScanFeedFromBuffer", scanFeed, sqlSession);
	}

	@Override
	public List<ScanFeed> getBuilUpScanFeeds(ScanFeed scanFeed) throws CustomException {
		return super.fetchList("getBuilUpScanFeeds", MultiTenantUtility.getAirportCityMap(scanFeed.getContainerTagid()), sqlSession);
	}

	@Override
	public ScanFeed getBuildUpFlightInfo(ScanFeed scanFeed) throws CustomException {
		ScanFeed buildUpFlightInfo = super.fetchObject("getBuildUpFlightInfo", scanFeed, sqlSession);// sqlSession.selectOne("getBuildUpFlightInfo",
																										// scanFeed);
		if (buildUpFlightInfo != null) {
			/*
			 * scanFeed.setOutFlightCarrier(buildUpFlightInfo.getOutFlightCarrier());
			 * scanFeed.setOutFlightNumber(buildUpFlightInfo.getOutFlightNumber());
			 * scanFeed.setOutFlightOriginDate(buildUpFlightInfo.getOutFlightOriginDate());
			 * scanFeed.setUldRfidTag(buildUpFlightInfo.getUldRfidTag());
			 */
		}
		return scanFeed;
	}

	@Override
	public Integer getRFIDStationId() throws CustomException {
		return super.fetchObject("getRFIDStationId", null, sqlSession);
	}
}
