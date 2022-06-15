package com.ngen.cosys.impbd.dao;

import java.math.BigInteger;
import java.util.List;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.impbd.model.RampCheckInDetails;
import com.ngen.cosys.impbd.model.RampCheckInList;
import com.ngen.cosys.impbd.model.RampCheckInModel;
import com.ngen.cosys.impbd.model.RampCheckInPiggyback;
import com.ngen.cosys.impbd.model.RampCheckInPiggybackList;
import com.ngen.cosys.impbd.model.RampCheckInSHC;
import com.ngen.cosys.impbd.model.RampCheckInSearchFlight;
import com.ngen.cosys.impbd.model.RampCheckInSegmentList;
import com.ngen.cosys.impbd.model.RampCheckInStatus;
import com.ngen.cosys.impbd.model.RampCheckInUld;
import com.ngen.cosys.impbd.model.RampCheckInUldModel;
import com.ngen.cosys.impbd.model.RampCheckInUldNumber;

public interface RampCheckInDAO {

	RampCheckInDetails fetch(RampCheckInSearchFlight rampCheckInSearchFlight) throws CustomException;

	List<RampCheckInUld> create(List<RampCheckInUld> rampCheckInUld) throws CustomException;

	void update(List<RampCheckInUld> rampCheckInUld) throws CustomException;

	RampCheckInModel delete(List<RampCheckInUld> rampCheckInUld) throws CustomException;

	RampCheckInUld createUld(RampCheckInUld rampCheckInUld) throws CustomException;

	List<RampCheckInUld> updateHandOver(List<RampCheckInUld> rampCheckInUld) throws CustomException;

	RampCheckInUld updateHandOverULD(RampCheckInUld data) throws CustomException;

	RampCheckInUld createHandOver(RampCheckInUld rampCheckInUld) throws CustomException;
	
	String fecthHandoverinfo(RampCheckInUld rampCheckInUld) throws CustomException;

	RampCheckInUld insertHandOverULD(RampCheckInUld data) throws CustomException;

	RampCheckInUld getHandoverId(RampCheckInUld data) throws CustomException;

	RampCheckInModel updateAll(List<RampCheckInUld> rampCheckInUld) throws CustomException;
	
	RampCheckInModel unCheckIn(List<RampCheckInUld> rampCheckInUld) throws CustomException;

	RampCheckInStatus insertEvent(RampCheckInStatus data) throws CustomException;

	RampCheckInStatus updateEvent(RampCheckInStatus data) throws CustomException;

	RampCheckInStatus getEventStatus(RampCheckInStatus data) throws CustomException;

	RampCheckInStatus reopenEvent(RampCheckInStatus data) throws CustomException;

	List<RampCheckInPiggyback> insertPiggyback(List<RampCheckInPiggyback> data) throws CustomException;

	List<RampCheckInPiggyback> updatePiggyback(List<RampCheckInPiggyback> data) throws CustomException;

	List<RampCheckInPiggyback> deletePiggyback(List<RampCheckInPiggyback> data) throws CustomException;

	List<RampCheckInSHC> deleteSHCs(List<RampCheckInSHC> rampCheckInUld) throws CustomException;

	List<RampCheckInSHC> insertSHC(List<RampCheckInSHC> data) throws CustomException;

	List<RampCheckInPiggyback> fetchPiggyback(RampCheckInPiggyback data) throws CustomException;

	RampCheckInSHC deleteSHCs(RampCheckInSHC rampCheckInUld) throws CustomException;

	RampCheckInUld deleteSHCs(RampCheckInUld rampCheckInUld) throws CustomException;

	List<RampCheckInSHC> insertSHCUsingId(List<RampCheckInSHC> data) throws CustomException;

	RampCheckInUld deletePiggyback(RampCheckInUld data) throws CustomException;

	boolean checkULD(RampCheckInUld rampCheckInUld) throws CustomException;

	boolean checkShcMaster(RampCheckInSHC data) throws CustomException;

	RampCheckInStatus updateEventFirstTime(RampCheckInStatus data) throws CustomException;

	RampCheckInStatus updateFirstULDCheckedIn(RampCheckInStatus data) throws CustomException;

	RampCheckInStatus updateFirstULDTowedIn(RampCheckInStatus data) throws CustomException;

	RampCheckInDetails getBulkStatus(RampCheckInDetails data) throws CustomException;

	RampCheckInDetails updateBulkStatus(RampCheckInDetails data) throws CustomException;

	RampCheckInUldNumber insertHandOverULDId(RampCheckInUldNumber data) throws CustomException;

	List<RampCheckInUld> deleteuldFromImpHandOver(List<RampCheckInUld> rampCheckInUld) throws CustomException;

	RampCheckInUldModel createHandOverId(RampCheckInUldModel rampCheckInUld) throws CustomException;

	RampCheckInList fetchList(RampCheckInSearchFlight rampCheckInSearchFlight) throws CustomException;

	RampCheckInList getBulkStatusList(RampCheckInList data) throws CustomException;

	List<RampCheckInUld> deleteUldTrollyInfo(List<RampCheckInUld> rampCheckInUld) throws CustomException;

	RampCheckInPiggybackList insertPiggybackList(RampCheckInPiggybackList data) throws CustomException;

	RampCheckInPiggybackList updatePiggybackList(RampCheckInPiggybackList data) throws CustomException;

	RampCheckInPiggybackList existUldNumber(RampCheckInPiggybackList data) throws CustomException;

	List<RampCheckInPiggybackList> fetchPiggybackList(RampCheckInPiggybackList data) throws CustomException;

	List<RampCheckInPiggybackList> deletePiggybackList(RampCheckInPiggybackList data) throws CustomException;

	RampCheckInUld updateAllList(RampCheckInUld rampCheckInUld) throws CustomException;

	Integer getCountofCarrierCode(RampCheckInUld rampCheckInUld) throws CustomException;

	Integer checkContentCode(RampCheckInUld rampCheckInUld) throws CustomException;

	List<RampCheckInSegmentList> fetchSegments(RampCheckInDetails rampCheckIn) throws CustomException;
	
	Integer getFFMUldCount(RampCheckInDetails detaisl) throws CustomException;

	String isTrolley(String uldType) throws CustomException;

	
	Integer getCountOfSegments(RampCheckInSearchFlight query)throws CustomException;

	Integer getCountOfNilCargo(RampCheckInSearchFlight query)throws CustomException;
	
	Integer checkSHC(RampCheckInSHC query)throws CustomException;
	
	RampCheckInUld getFlightOrigin(RampCheckInUld data)throws CustomException;
	
	void insertULDsIntoUldUCMTables(List<RampCheckInUld> ulds) throws CustomException;
	
	void insertDataIntoUldMasterAndMovements(List<RampCheckInUld> ulds) throws CustomException;
	
	Integer getCountOfUCMFlightLevel(BigInteger query)throws CustomException;
	
	Integer getCountOfUCMDetailsLevel(BigInteger query)throws CustomException;
	
	String getFlightType(BigInteger flightId)throws CustomException;
	
	boolean checkUploadPhoto(RampCheckInUld uld)throws CustomException;
	
	BigInteger fetchHandOverID(RampCheckInUld query) throws CustomException;

	RampCheckInModel saveAllULDTemperature(List<RampCheckInUld> uldList) throws CustomException;;

}
