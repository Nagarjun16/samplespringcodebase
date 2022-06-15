package com.ngen.cosys.impbd.service;

import java.math.BigInteger;
import java.util.List;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.impbd.model.RampCheckInDetails;
import com.ngen.cosys.impbd.model.RampCheckInList;
import com.ngen.cosys.impbd.model.RampCheckInModel;
import com.ngen.cosys.impbd.model.RampCheckInParentModel;
import com.ngen.cosys.impbd.model.RampCheckInPiggyback;
import com.ngen.cosys.impbd.model.RampCheckInPiggybackModel;
import com.ngen.cosys.impbd.model.RampCheckInSearchFlight;
import com.ngen.cosys.impbd.model.RampCheckInUld;
import com.ngen.cosys.impbd.model.RampCheckInUldModel;

public interface RampCheckInService {

	RampCheckInDetails fetch(RampCheckInSearchFlight query) throws CustomException;

	RampCheckInModel delete(RampCheckInModel query) throws CustomException;

	List<RampCheckInUld> create(List<RampCheckInUld> query) throws CustomException;

	RampCheckInParentModel createUld(RampCheckInParentModel query) throws CustomException;

	RampCheckInModel assignDriver(RampCheckInModel query) throws CustomException;

	RampCheckInUld onDriverUpdate(RampCheckInUld query) throws CustomException;

	RampCheckInModel updateAll(RampCheckInModel query) throws CustomException;

	RampCheckInModel checkIn(RampCheckInModel query) throws CustomException;
	
	RampCheckInModel unCheckIn(RampCheckInModel query) throws CustomException;

	RampCheckInModel reopen(RampCheckInModel query) throws CustomException;

	List<RampCheckInPiggyback> managePiggyback(List<RampCheckInPiggyback> paramObject) throws CustomException;

	List<RampCheckInPiggyback> getPiggyback(RampCheckInPiggyback query) throws CustomException;

	BigInteger reopen(BigInteger query) throws CustomException;

	RampCheckInDetails updateBulkStatus(RampCheckInDetails data) throws CustomException;

	RampCheckInUldModel assignDriverId(RampCheckInUldModel query) throws CustomException;

	RampCheckInList fetchList(RampCheckInSearchFlight query) throws CustomException;

	RampCheckInPiggybackModel maintainPiggybackList(RampCheckInPiggybackModel query) throws CustomException;

	RampCheckInPiggybackModel getPiggybackList(RampCheckInPiggybackModel query) throws CustomException;

	RampCheckInPiggybackModel deletePiggybackList(RampCheckInPiggybackModel query) throws CustomException;

	RampCheckInModel checkInData(RampCheckInModel query) throws CustomException;

	RampCheckInModel updateAllList(RampCheckInModel query) throws CustomException;

	Integer checkCarrierCode(RampCheckInUld data) throws CustomException;

	RampCheckInModel saveAllULDTemperature(RampCheckInModel bquery) throws CustomException;

}
