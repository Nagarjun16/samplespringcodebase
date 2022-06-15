package com.ngen.cosys.platform.rfid.tracker.feeder.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.platform.rfid.tracker.feeder.dao.FeederRepository;
import com.ngen.cosys.platform.rfid.tracker.feeder.model.JobStatus;
import com.ngen.cosys.platform.rfid.tracker.feeder.model.ScanFeed;
import com.ngen.cosys.platform.rfid.tracker.feeder.task.FeederTask;
import com.ngen.cosys.platform.rfid.tracker.interfaces.model.AuthModel;

@Service
@Transactional
public class FeederServiceImpl implements FeederService {

	@Autowired
	FeederRepository feederReporitory;
	
	@Autowired
	FeederTask feederTask;

	@Override
	public JobStatus getJobStatus() throws CustomException {
		return feederReporitory.getJobStatus();
	}

	@Override
	public void updateJobStatusLastJobRun(JobStatus jobStatus) throws CustomException {
		feederReporitory.updateJobStatusLastJobRun(jobStatus);
	}

	@Override
	public void updateJobStatusLastSequenceNumber(JobStatus jobStatus) throws CustomException {
		feederReporitory.updateJobStatusLastSequenceNumber(jobStatus);

	}

	@Override
	public List<ScanFeed> getScanFeeds(JobStatus jobStatus) throws CustomException {
		return feederReporitory.getScanFeeds(jobStatus);
	}

	@Override
	public Integer getAWBTotalTags(ScanFeed scanFeed) throws CustomException {
		return feederReporitory.getAWBTotalTags(scanFeed);
	}

	@Override
	public Integer getULDTotalTags(ScanFeed scanFeed) throws CustomException {
		return feederReporitory.getULDTotalTags(scanFeed);
	}

	@Override
	public AuthModel getAuthUserPassword()  throws CustomException{
		return feederReporitory.getAuthUserPassword();
	}

	@Override
	public List<ScanFeed> getScanFeedsFromBuffer() throws CustomException {
		return feederReporitory.getScanFeedsFromBuffer();
	}

	@Override
	public void copyScanFeedToBuffer(ScanFeed scanFeed) throws CustomException {
		feederReporitory.copyScanFeedToBuffer(scanFeed);
	}

	@Override
	public void deleteScanFeedFromBuffer(ScanFeed scanFeed) throws CustomException {
		feederReporitory.deleteScanFeedFromBuffer(scanFeed);
	}

	@Override
	public List<ScanFeed> getBuilUpScanFeeds(ScanFeed scanFeed) throws CustomException {
		return feederReporitory.getBuilUpScanFeeds(scanFeed);
	}

	@Override
	public ScanFeed getBuildUpFlightInfo(ScanFeed scanFeed) throws CustomException {
		return feederReporitory.getBuildUpFlightInfo(scanFeed);
	}
	
	@Override
	public Integer getRFIDStationId() throws CustomException {
		return feederReporitory.getRFIDStationId();
	}

	@Override
	public void pushTrackingFeeds() {
		feederTask.pushTrackingFeeds();		
	}
}
