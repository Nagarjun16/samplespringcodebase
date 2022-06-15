package com.ngen.cosys.platform.rfid.tracker.feeder.dao;

import java.util.List;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.platform.rfid.tracker.feeder.model.JobStatus;
import com.ngen.cosys.platform.rfid.tracker.feeder.model.ScanFeed;
import com.ngen.cosys.platform.rfid.tracker.interfaces.model.AuthModel;

public interface FeederRepository {

	public JobStatus getJobStatus() throws CustomException;

	public void updateJobStatusLastJobRun(JobStatus jobStatus) throws CustomException;

	public void updateJobStatusLastSequenceNumber(JobStatus jobStatus) throws CustomException;

	public List<ScanFeed> getScanFeeds(JobStatus jobStatus) throws CustomException;

	public Integer getAWBTotalTags(ScanFeed scanFeed) throws CustomException;

	public Integer getULDTotalTags(ScanFeed scanFeed) throws CustomException;

	public AuthModel getAuthUserPassword() throws CustomException;

	public List<ScanFeed> getScanFeedsFromBuffer() throws CustomException;

	public void copyScanFeedToBuffer(ScanFeed scanFeed) throws CustomException;

	public void deleteScanFeedFromBuffer(ScanFeed scanFeed) throws CustomException;

	public List<ScanFeed> getBuilUpScanFeeds(ScanFeed scanFeed) throws CustomException;
	
	public Integer getRFIDStationId() throws CustomException;

	public ScanFeed getBuildUpFlightInfo(ScanFeed scanFeed) throws CustomException;
}
