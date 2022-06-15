package com.ngen.cosys.impbd.dao;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.ngen.cosys.audit.NgenAuditAction;
import com.ngen.cosys.audit.NgenAuditEntityType;
import com.ngen.cosys.audit.NgenAuditEventType;
import com.ngen.cosys.common.validator.LoadingMoveableLocationValidator;
import com.ngen.cosys.common.validator.MoveableLocationTypeModel;
import com.ngen.cosys.common.validator.MoveableLocationTypeProcess;
import com.ngen.cosys.framework.constant.ErrorType;
import com.ngen.cosys.framework.dao.BaseDAO;
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
import com.ngen.cosys.timezone.util.TenantZoneTime;
import com.ngen.cosys.uldinfo.UldMovementFunctionTypes;
import com.ngen.cosys.uldinfo.model.UldInfoModel;
import com.ngen.cosys.uldinfo.service.UldInfoService;

@Repository("rampCheckInDao")
public class RampCheckInDAOImpl extends BaseDAO implements RampCheckInDAO {

	private static final String UPDATE_INBOUND_RAMP = "updateInboundRampUld";

	@Autowired
	@Qualifier("sqlSessionTemplate")
	private SqlSessionTemplate sqlSession;

	@Autowired
	UldInfoService uldinfoService;

	@Autowired
	LoadingMoveableLocationValidator loadingmoveablelocationvalidator;

	/*
	 * 
	 * Fetch Incoming Flight details with ULD information for checkIn and
	 * verification
	 * 
	 */
	@Override
	public RampCheckInDetails fetch(RampCheckInSearchFlight rampCheckInSearchFlight) throws CustomException {
		return super.fetchObject("getFlight", rampCheckInSearchFlight, sqlSession);
	}

	@Override
	public List<RampCheckInSegmentList> fetchSegments(RampCheckInDetails rampCheckIn) throws CustomException {
		return super.fetchList("getFlightSegmentList", rampCheckIn, sqlSession);
	}

	@Override
	public boolean checkULD(RampCheckInUld rampCheckInUld) throws CustomException {
		Integer number = super.fetchObject("getUldFromUldMaster", rampCheckInUld, sqlSession);
		return number.intValue() > 0;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RampCheckInUld> create(List<RampCheckInUld> rampCheckInUld) throws CustomException {
		return (List<RampCheckInUld>) super.insertList("insertInboundRampUlds", rampCheckInUld, sqlSession);
	}

	@Override
	@NgenAuditAction(entityType = NgenAuditEntityType.ULD, eventName = NgenAuditEventType.INBOUND_RAMP_CEHCKIN)
	public RampCheckInModel updateAll(List<RampCheckInUld> rampCheckInUld) throws CustomException {
		RampCheckInModel model = new RampCheckInModel();

		for (RampCheckInUld rampCheckIn : rampCheckInUld) {
			if (rampCheckIn.getOffloadReason() != null) {
				rampCheckIn.setCheckedinAt(null);
				rampCheckIn.setCheckedinBy(null);
			}
			super.updateData(UPDATE_INBOUND_RAMP, rampCheckIn, sqlSession);

		}

		model.setUldList(rampCheckInUld);
		return model;

	}
	@Override
	@NgenAuditAction(entityType = NgenAuditEntityType.ULD, eventName = NgenAuditEventType.INBOUND_RAMP_CEHCKIN)
	public RampCheckInModel saveAllULDTemperature(List<RampCheckInUld> rampCheckInUld) throws CustomException {
		RampCheckInModel model = new RampCheckInModel();

		for (RampCheckInUld rampCheckIn : rampCheckInUld) {
            
			int existingUldinTemperatureFlag = fetchObject("sqlFetchExistingTemperatureLog", rampCheckIn, sqlSession);
			
			if(existingUldinTemperatureFlag == 0) {

				this.insertData("saveAllUldTemperatureLog", rampCheckIn, sqlSession);
			}


			else if(existingUldinTemperatureFlag == 1) {

				super.updateData("sqlUpdateTemperatureLog",rampCheckIn, sqlSession);
			}

			}

		model.setUldList(rampCheckInUld);
		return model;

	}

	@Override
	@NgenAuditAction(entityType = NgenAuditEntityType.ULD, eventName = NgenAuditEventType.INBOUND_RAMP_CEHCKIN)
	public RampCheckInUld createUld(RampCheckInUld rampCheckInUld) throws CustomException {
		Integer flag = super.fetchObject("checkUldExistedForthisFlight", rampCheckInUld, sqlSession);
		if (flag == 0) {
			super.insertData("insertInboundRampUlds", rampCheckInUld, sqlSession);
		} else {
			throw new CustomException("impbd.uld.assigned.to.flight", "eForm", ErrorType.ERROR);
		}
		return rampCheckInUld;
	}

	/*
	 * 
	 * Update Incoming Flight details with ULD information
	 * 
	 */
	@Override
	public void update(List<RampCheckInUld> rampCheckInUld) throws CustomException {
		super.updateData(UPDATE_INBOUND_RAMP, rampCheckInUld, sqlSession);
	}

	@Override
	public List<RampCheckInSHC> insertSHC(List<RampCheckInSHC> data) throws CustomException {
		super.insertList("insertRampULDSHC", data, sqlSession);
		return data;
	}

	@Override
	public boolean checkShcMaster(RampCheckInSHC data) throws CustomException {
		Integer number = super.fetchObject("getSHCFromSHCMaster", data, sqlSession);
		return number.intValue() > 0;
	}

	@Override
	public List<RampCheckInSHC> insertSHCUsingId(List<RampCheckInSHC> data) throws CustomException {
		super.insertList("insertRampULDSHCUsingId", data, sqlSession);
		return data;
	}

	@Override
	public List<RampCheckInSHC> deleteSHCs(List<RampCheckInSHC> rampCheckInUld) throws CustomException {
		super.deleteData("deleteInboundRampUldShc", rampCheckInUld, sqlSession);
		return rampCheckInUld;
	}

	@Override
	public RampCheckInUld deleteSHCs(RampCheckInUld rampCheckInUld) throws CustomException {
		super.deleteData("deleteInboundRampUldShcUsingRampId", rampCheckInUld, sqlSession);
		return rampCheckInUld;
	}

	@Override
	public RampCheckInSHC deleteSHCs(RampCheckInSHC rampCheckInUld) throws CustomException {
		super.deleteData("deleteInboundRampUldShc", rampCheckInUld, sqlSession);
		return rampCheckInUld;
	}
	public String fecthHandoverinfo(RampCheckInUld rampCheckInUld) throws CustomException{
		
		return super.fetchObject("fetchHandoverrecord", rampCheckInUld, sqlSession);
	}

	@Override
	public RampCheckInUld createHandOver(RampCheckInUld rampCheckInUld) throws CustomException {
		super.insertData("insertHandoverByRampCheckin", rampCheckInUld, sqlSession);
		return rampCheckInUld;
	}

	@Override
	public RampCheckInUld insertHandOverULD(RampCheckInUld data) throws CustomException {
		super.insertData("insertHandoverByRampCheckinUld", data, sqlSession);
		return data;
	}

	@Override
	public List<RampCheckInUld> updateHandOver(List<RampCheckInUld> rampCheckInUld) throws CustomException {
		super.insertList("updateHandoverByRampCheckin", rampCheckInUld, sqlSession);
		return rampCheckInUld;
	}

	@Override
	public RampCheckInUld updateHandOverULD(RampCheckInUld data) throws CustomException {
		super.updateData("updateHandoverByRampCheckin", data, sqlSession);
		return data;
	}

	// getIdForRampCheckInHandOverUldTrolly
	@Override
	public RampCheckInUld getHandoverId(RampCheckInUld data) throws CustomException {
		return super.fetchObject("updateHandoverByRampCheckinUld", data, sqlSession);
	}

	@Override
	public RampCheckInModel delete(List<RampCheckInUld> rampCheckInUld) throws CustomException {
		for (RampCheckInUld uld : rampCheckInUld) {
			BigInteger impRampCheckInId = fetchObject("getSqlImpRampCheckInId", uld, sqlSession);
			if (!StringUtils.isEmpty(impRampCheckInId)) {
				uld.setImpRampCheckInId(impRampCheckInId);
				deleteData("deleteSqlImpRampCheckInULDSHC", uld, sqlSession);
				deleteData("deleteSqlImpRampCheckInPiggyBackULDInfo", uld, sqlSession);
			}

			deleteData("deleteUldByRampCheckIn", uld, sqlSession);
		}
		RampCheckInModel model = new RampCheckInModel();
		model.setUldList(rampCheckInUld);
		return model;
	}

	@Override
	public List<RampCheckInUld> deleteuldFromImpHandOver(List<RampCheckInUld> rampCheckInUld) throws CustomException {
		super.deleteData("deleteuldFromImpHandOver", rampCheckInUld, sqlSession);

		return rampCheckInUld;
	}

	@Override
	public List<RampCheckInUld> deleteUldTrollyInfo(List<RampCheckInUld> rampCheckInUld) throws CustomException {
		super.deleteData("deleteUldTrollyInfo", rampCheckInUld, sqlSession);

		return rampCheckInUld;
	}

	@Override
	public RampCheckInStatus getEventStatus(RampCheckInStatus data) throws CustomException {
		return super.fetchObject("selectRampCheckInEvent", data, sqlSession);
	}

	@Override
	@NgenAuditAction(entityType = NgenAuditEntityType.FLIGHT, eventName = NgenAuditEventType.INBOUND_RAMP_CEHCKIN)
	public RampCheckInStatus insertEvent(RampCheckInStatus data) throws CustomException {
		String dateTime = TenantZoneTime.getZoneDateTime(data.getCreatedOn(),data.getTenantId()).toString();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS"); 
		
		LocalDateTime localDateTime = LocalDateTime.parse(dateTime, formatter);
		data.setRampCheckInCompletedAt(localDateTime);
		data.setRampCheckInCompletedBy(data.getModifiedBy());
		super.insertData("insertRampCheckInEvent", data, sqlSession);
		return data;
	}

	@Override
	@NgenAuditAction(entityType = NgenAuditEntityType.FLIGHT, eventName = NgenAuditEventType.INBOUND_RAMP_CEHCKIN)
	public RampCheckInStatus updateEvent(RampCheckInStatus data) throws CustomException {
		String dateTime = TenantZoneTime.getZoneDateTime(data.getCreatedOn(),data.getTenantId()).toString();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS"); 
		LocalDateTime localDateTime = LocalDateTime.parse(dateTime, formatter);
		data.setRampCheckInCompletedAt(localDateTime);
		data.setRampCheckInCompletedBy(data.getCreatedBy());
		super.updateData("updateRampCheckInEvent", data, sqlSession);
		return data;
	}

	@Override
	@NgenAuditAction(entityType = NgenAuditEntityType.FLIGHT, eventName = NgenAuditEventType.INBOUND_RAMP_CEHCKIN)
	public RampCheckInStatus updateEventFirstTime(RampCheckInStatus data) throws CustomException {
		
		String dateTime = TenantZoneTime.getZoneDateTime(data.getCreatedOn(),data.getTenantId()).toString();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS"); 
		LocalDateTime localDateTime = LocalDateTime.parse(dateTime, formatter);
		data.setRampCheckInCompletedAt(localDateTime);
		data.setRampCheckInCompletedBy(data.getCreatedBy());
		super.updateData("updateFirstTimeRampCheckInEvent", data, sqlSession);
		return data;
	}

	@Override
	@NgenAuditAction(entityType = NgenAuditEntityType.FLIGHT, eventName = NgenAuditEventType.INBOUND_RAMP_CEHCKIN)
	public RampCheckInStatus reopenEvent(RampCheckInStatus data) throws CustomException {
		String dateTime = TenantZoneTime.getZoneDateTime(data.getCreatedOn(),data.getTenantId()).toString();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS"); 
		
		LocalDateTime localDateTime = LocalDateTime.parse(dateTime, formatter);
		data.setRampCheckInCompletedAt(localDateTime);
		data.setRampCheckInCompletedBy(data.getCreatedBy());
		data.setRampCheckInCompletedStatus(false);
		super.updateData("updateRampCheckInEventReopen", data, sqlSession);
		return data;
	}

	@Override
	public List<RampCheckInPiggyback> fetchPiggyback(RampCheckInPiggyback data) throws CustomException {
		return super.fetchList("selectPiggyBackULD", data, sqlSession);
	}

	@Override
	public List<RampCheckInPiggyback> insertPiggyback(List<RampCheckInPiggyback> data) throws CustomException {
		for (RampCheckInPiggyback rampCheckInPiggyback : data) {
			String uldNumber = super.fetchObject("selectPiggyBackULDNumber", rampCheckInPiggyback, sqlSession);
			if (uldNumber != null) {
				throw new CustomException("impbd.uld.is.assigned", "form", ErrorType.ERROR,new String[] {uldNumber});
			}
		}
		if(!data.isEmpty() && data != null) {
			RampCheckInUld request = new RampCheckInUld();
			request.setPiggyback(true);
			request.setRemarks(data.get(0).getRemarks());
			request.setFlightId(data.get(0).getFlightId());
			request.setImpRampCheckInId(data.get(0).getImpRampCheckInId());
			super.updateData("updateRampCheckInPiggyBackDetails", request, sqlSession);
		}
		super.insertList("insertPiggyBackULD", data, sqlSession);
		updateUcmUlds(data);
		return data;
	}

	private void pigyBackUldsUpdate(RampCheckInUld rampCheckIn) throws CustomException {
		RampCheckInUld object = this.fetchObject("getFlightOriginCarrier", rampCheckIn, sqlSession);
		rampCheckIn.setOrigin(object.getOrigin());
		BigInteger ucmInfoId = super.fetchObject("getUcmFlightDetails", rampCheckIn, sqlSession);
		if (ucmInfoId == null) {
			super.insertData("insertUCMFlightDetails", rampCheckIn, sqlSession);
		} else {
			rampCheckIn.setImpRampCheckInId(ucmInfoId);
		}
		if (super.fetchObject("getUCMUldDetails", rampCheckIn, sqlSession) == null) {
			super.insertData("insertUCMDetails", rampCheckIn, sqlSession);
		}

		MoveableLocationTypeModel requestModel = new MoveableLocationTypeModel();
		requestModel.setKey(rampCheckIn.getUldNumber());
		requestModel.setFlightKey(rampCheckIn.getFlight());
		requestModel.setFlightDate(rampCheckIn.getFlightDate());
		requestModel.setProcess(MoveableLocationTypeProcess.ProcessTypes.IMPORT);
		requestModel.setPropertyKey("uldTrolleyNo");
		requestModel.setContentCode(rampCheckIn.getContentCode());
		MoveableLocationTypeModel moveableLocationTypeModel = loadingmoveablelocationvalidator.split(requestModel);

		UldInfoModel uldInfoModel = new UldInfoModel();
		uldInfoModel.setUldConditionType("SER");
		uldInfoModel.setUldKey(rampCheckIn.getUldNumber());
		uldInfoModel.setInboundFlightId(rampCheckIn.getFlightId());
		uldInfoModel.setFlightBoardPoint(rampCheckIn.getOrigin());
		uldInfoModel.setFlightOffPoint(rampCheckIn.getTenantAirport());
		uldInfoModel.setContentCode(rampCheckIn.getContentCode());
		uldInfoModel.setIntact(Boolean.FALSE);
		uldInfoModel.setMovableLocationType(requestModel.getLocationType());
		uldInfoModel.setOutboundFlightId(rampCheckIn.getFlightId());
		uldInfoModel.setTenantAirport(rampCheckIn.getTenantAirport());
		uldInfoModel.setTerminal(rampCheckIn.getTerminal());
		uldInfoModel.setUldLocationCode(rampCheckIn.getTerminal());
		uldInfoModel.setHandlingCarrierCode(moveableLocationTypeModel.getCarrierCode());
		uldInfoModel.setUldCarrierCode(moveableLocationTypeModel.getPart3());
		uldInfoModel.setUldNumber(String.valueOf(moveableLocationTypeModel.getPart2()));
		uldInfoModel.setUldType(moveableLocationTypeModel.getPart1());
		uldInfoModel.setFunctionName(UldMovementFunctionTypes.Names.RAMPCHECKIN);
		uldinfoService.updateUldInfo(uldInfoModel);
	}

	@Override
	public List<RampCheckInPiggyback> updatePiggyback(List<RampCheckInPiggyback> data) throws CustomException {
		super.updateData("updatePiggyBackULD", data, sqlSession);
		updateUcmUlds(data);
		return data;
	}

	private void updateUcmUlds(List<RampCheckInPiggyback> data) throws CustomException {
		for (RampCheckInPiggyback rampCheck : data) {
			RampCheckInUld rampCheckIn = new RampCheckInUld();
			rampCheckIn.setUldNumber(rampCheck.getUldNumber());
			rampCheckIn.setFlightId(rampCheck.getFlightId());
			rampCheckIn.setOrigin(rampCheck.getOrigin());
			rampCheckIn.setImpRampCheckInId(rampCheck.getImpRampCheckInId());
			rampCheckIn.setContentCode("X");
			rampCheckIn.setFlight(rampCheck.getFlight());
			rampCheckIn.setFlightDate(rampCheck.getFlightDate());
			pigyBackUldsUpdate(rampCheckIn);
		}
	}

	@Override
	public List<RampCheckInPiggyback> deletePiggyback(List<RampCheckInPiggyback> data) throws CustomException {
		super.deleteData("deletePiggyBackULD", data, sqlSession);
		return data;
	}

	@Override
	public RampCheckInUld deletePiggyback(RampCheckInUld data) throws CustomException {
		super.deleteData("deletePiggyBackULDByRampCheckInId", data, sqlSession);
		return data;
	}

	@Override
	public RampCheckInStatus updateFirstULDCheckedIn(RampCheckInStatus data) throws CustomException {
		RampCheckInStatus firstULDCheckedInBy = super.fetchObject("findFirstUldCheckedIn", data, sqlSession);
		if (ObjectUtils.isEmpty(firstULDCheckedInBy)) {
			super.insertData("insertFirstUldCheckedIn", data, sqlSession);
		} else if (firstULDCheckedInBy.getFirstULDCheckedInBy() == null) {
			super.updateData("updateFirstUldCheckedIn", data, sqlSession);
		}
		return data;
	}

	@Override
	public RampCheckInStatus updateFirstULDTowedIn(RampCheckInStatus data) throws CustomException {
		RampCheckInStatus firstULDTowedInBy = super.fetchObject("findFirstUldTowedIn", data, sqlSession);
		if (ObjectUtils.isEmpty(firstULDTowedInBy)) {
			throw new CustomException("impbd.operative.flight.not.found", "form", ErrorType.ERROR);
		} else if (firstULDTowedInBy.getFirstULDTowedBy() == null) {
			super.updateData("updateFirstUldTowedIn", data, sqlSession);
		} else {
			super.updateData("updateLastUldTowedIn", data, sqlSession);
		}
		return data;
	}

	@Override
	public RampCheckInDetails getBulkStatus(RampCheckInDetails data) throws CustomException {
		RampCheckInDetails rd = super.fetchObject("getBulkFlag", data, sqlSession);
		if (rd != null) {
			data.setBulk(rd.getBulk());
		}
		return data;
	}

	@Override
	public RampCheckInDetails updateBulkStatus(RampCheckInDetails data) throws CustomException {
		super.updateData("updateBulkStatus", data, sqlSession);
		return data;
	}

	@Override
	public RampCheckInUldModel createHandOverId(RampCheckInUldModel rampCheckInUld) throws CustomException {
		super.insertData("insertHandoverByRampCheckin", rampCheckInUld, sqlSession);
		return rampCheckInUld;
	}

	@Override
	public RampCheckInUldNumber insertHandOverULDId(RampCheckInUldNumber data) throws CustomException {
		super.insertData("insertHandoverByRampCheckinUld", data, sqlSession);
		return data;
	}

	@Override
	public RampCheckInList fetchList(RampCheckInSearchFlight rampCheckInSearchFlight) throws CustomException {
		return super.fetchObject("getFlightList", rampCheckInSearchFlight, sqlSession);
	}

	@Override
	public RampCheckInList getBulkStatusList(RampCheckInList data) throws CustomException {
		RampCheckInList rd = super.fetchObject("getBulkList", data, sqlSession);
		data.setBulk(rd.getBulk());
		return data;
	}

	@Override
	public RampCheckInPiggybackList insertPiggybackList(RampCheckInPiggybackList data) throws CustomException {
		super.insertData("insertPiggyBackULDList", data, sqlSession);
		return data;
	}

	@Override
	public RampCheckInPiggybackList updatePiggybackList(RampCheckInPiggybackList data) throws CustomException {
		super.updateData("updatePiggyBackULDList", data, sqlSession);
		return data;
	}

	@Override
	public RampCheckInPiggybackList existUldNumber(RampCheckInPiggybackList data) throws CustomException {
		return super.fetchObject("existULDList", data, sqlSession);
	}

	@Override
	public List<RampCheckInPiggybackList> fetchPiggybackList(RampCheckInPiggybackList data) throws CustomException {
		return super.fetchList("selectPiggyBackULDList", data, sqlSession);
	}

	@Override
	public List<RampCheckInPiggybackList> deletePiggybackList(RampCheckInPiggybackList data) throws CustomException {
		super.deleteData("deletePiggyBackULDList", data, sqlSession);
		List<RampCheckInPiggybackList> listData = super.fetchList("selectPiggyBackULDList", data, sqlSession);
		return listData;
	}

	@Override
	public RampCheckInUld updateAllList(RampCheckInUld rampCheckInUld) throws CustomException {
		super.updateData(UPDATE_INBOUND_RAMP, rampCheckInUld, sqlSession);
		return rampCheckInUld;
	}

	@Override
	public Integer getCountofCarrierCode(RampCheckInUld carrier) throws CustomException {
		Integer value = 0;
		Integer phcvalue = 0;
		Integer valValue = 0;
		phcvalue = super.fetchObject("getCarrierCodeCountphc", carrier, sqlSession);
		valValue = super.fetchObject("getCarrierCodeCountVal", carrier, sqlSession);
		if (phcvalue > 0) {
			value = 1;
		} else if (valValue > 0) {
			value = 2;
		} else if (phcvalue > 0 && valValue > 0) {
			value = 3;
		}
		return value;
	}

	public Integer checkContentCode(RampCheckInUld rampCheckInUld) throws CustomException {

		return super.fetchObject("checkForContentCode", rampCheckInUld, sqlSession);
	}

	@Override
	public Integer getFFMUldCount(RampCheckInDetails details) throws CustomException {

		return super.fetchObject("getCountOfFFMUlds", details, sqlSession);
	}

	@Override
	public String isTrolley(String uldType) throws CustomException {

		return this.fetchObject("sqlIsTrolley", uldType, sqlSession);
	}

	@Override
	public Integer getCountOfNilCargo(RampCheckInSearchFlight rampCheckData) throws CustomException {
		return this.fetchObject("getCountOfNilCargo", rampCheckData, sqlSession);
	}

	@Override
	public Integer getCountOfSegments(RampCheckInSearchFlight rampCheckData) throws CustomException {
		return this.fetchObject("getCountOfSegments", rampCheckData, sqlSession);
	}

	@Override
	public RampCheckInModel unCheckIn(List<RampCheckInUld> rampCheckInUld) throws CustomException {
		unCheckInUlds(rampCheckInUld);
		rampCheckInUld.forEach(e -> e.setDriverId(null));
		rampCheckInUld.forEach(e -> e.setTerminal(null));
		super.updateData("updateHandoverByRampCheckin", rampCheckInUld, sqlSession);
		RampCheckInModel model = new RampCheckInModel();
		model.setUldList(rampCheckInUld);
		return model;
	}

	@NgenAuditAction(entityType = NgenAuditEntityType.ULD, eventName = NgenAuditEventType.INBOUND_RAMP_UNCEHCKIN)
	public void unCheckInUlds(List<RampCheckInUld> rampCheckInUld) throws CustomException  {
	   super.updateData("unCheckinUlds", rampCheckInUld, sqlSession);      
   }

   @Override
	public Integer checkSHC(RampCheckInSHC query) throws CustomException {
		return this.fetchObject("getCountShcs", query, sqlSession);
	}

	@Override
	public RampCheckInUld getFlightOrigin(RampCheckInUld data) throws CustomException {
		return this.fetchObject("getFlightOriginCarrier", data, sqlSession);
	}

	@Override
	public Integer getCountOfUCMFlightLevel(BigInteger flightId) throws CustomException {

		return super.fetchObject("getCountOfUcmFinalizedForFlightLevel", flightId, sqlSession);
	}

	@Override
	public Integer getCountOfUCMDetailsLevel(BigInteger flightId) throws CustomException {

		return super.fetchObject("getCountOfUcmFinalizedForUCMDetailsLevel", flightId, sqlSession);
	}

	@Override
	public void insertULDsIntoUldUCMTables(List<RampCheckInUld> ulds) throws CustomException {
		Integer ucmFlightFinalized = this.getCountOfUCMFlightLevel(ulds.get(0).getFlightId());

		Integer ucmDetailsFinalized = this.getCountOfUCMDetailsLevel(ulds.get(0).getFlightId());

		if ((ucmFlightFinalized == null || ucmFlightFinalized == 0 || ucmFlightFinalized != ucmDetailsFinalized)) {
			for (RampCheckInUld rampCheckIn : ulds) {
				if (!rampCheckIn.getUsedAsTrolley() && StringUtils.isEmpty(rampCheckIn.getOffloadReason())) {
					// updating piggy bag ulds both uld master and uld_ucm tables
					RampCheckInPiggyback data = new RampCheckInPiggyback();
					data.setImpRampCheckInId(rampCheckIn.getImpRampCheckInId());
					data.setOrigin(rampCheckIn.getOrigin());
					List<RampCheckInPiggyback> listData = fetchPiggyback(data);
					listData.forEach(t -> t.setContentCode("X"));
					listData.forEach(t -> t.setOrigin(rampCheckIn.getOrigin()));
					listData.forEach(t -> t.setFlightId(rampCheckIn.getFlightId()));
					listData.forEach(t -> t.setImpRampCheckInId(rampCheckIn.getImpRampCheckInId()));
					if (!CollectionUtils.isEmpty(listData)) {
						updateUcmUlds(listData);
						rampCheckIn.setContentCode("N");
					}
					RampCheckInUld object = this.fetchObject("getFlightOriginCarrier", rampCheckIn, sqlSession);
					if (rampCheckIn.getOrigin() != null) {
						rampCheckIn.setOrigin(rampCheckIn.getOrigin());
					} else {
						rampCheckIn.setOrigin(object.getOrigin());
					}
					if (rampCheckIn.getDriverId() != null || rampCheckIn.getDriverId() != ""
							|| !rampCheckIn.getDriverId().isEmpty()) {
						BigInteger ucmInfoId = super.fetchObject("getUcmFlightDetails", rampCheckIn, sqlSession);
						if (ucmInfoId == null) {
							super.insertData("insertUCMFlightDetails", rampCheckIn, sqlSession);
						} else {
							rampCheckIn.setImpRampCheckInId(ucmInfoId);
						}
						if (super.fetchObject("getUCMUldDetails", rampCheckIn, sqlSession) == null) {
							super.insertData("insertUCMDetails", rampCheckIn, sqlSession);
						}
					}

				}
			}
		}
	}

	public void insertDataIntoUldMasterAndMovements(List<RampCheckInUld> ulds) throws CustomException {

		for (RampCheckInUld rampCheckIn : ulds) {
			RampCheckInUld flightRes = this.fetchObject("getFlightOriginCarrier", rampCheckIn, sqlSession);
				MoveableLocationTypeModel requestModel = new MoveableLocationTypeModel();
				requestModel.setKey(rampCheckIn.getUldNumber());
				requestModel.setFlightKey(rampCheckIn.getFlight());
				requestModel.setFlightDate(rampCheckIn.getFlightDate());
				requestModel.setProcess(MoveableLocationTypeProcess.ProcessTypes.IMPORT);
				requestModel.setTenantAirport(rampCheckIn.getTenantAirport());
				requestModel.setPropertyKey("uldTrolleyNo");
				requestModel.setContentCode(rampCheckIn.getContentCode());
				MoveableLocationTypeModel moveableLocationTypeModel = loadingmoveablelocationvalidator
						.split(requestModel);

				UldInfoModel uldInfoModel = new UldInfoModel();
				if (rampCheckIn.getDamaged() != null && rampCheckIn.getDamaged()) {
					uldInfoModel.setUldDamaged(true);
				} 
				uldInfoModel.setUldKey(rampCheckIn.getUldNumber());
				uldInfoModel.setInboundFlightId(rampCheckIn.getFlightId());
				uldInfoModel.setFlightBoardPoint(rampCheckIn.getOrigin());
				uldInfoModel.setFlightOffPoint(rampCheckIn.getTenantAirport());
				uldInfoModel.setContentCode(rampCheckIn.getContentCode());
				uldInfoModel.setIntact(Boolean.FALSE);
				uldInfoModel.setMovableLocationType(requestModel.getLocationType());
				uldInfoModel.setOutboundFlightId(rampCheckIn.getOuboundFlightId());
				uldInfoModel.setTenantAirport(rampCheckIn.getTenantAirport());
				uldInfoModel.setTerminal(rampCheckIn.getTerminal());
				uldInfoModel.setUldLocationCode(rampCheckIn.getTerminal());
				if(!ObjectUtils.isEmpty(flightRes) && !StringUtils.isEmpty(flightRes.getCarrierCode()))
				uldInfoModel.setHandlingCarrierCode(flightRes.getCarrierCode());
				uldInfoModel.setUldCarrierCode(moveableLocationTypeModel.getPart3());
				uldInfoModel.setUldNumber(String.valueOf(moveableLocationTypeModel.getPart2()));
				uldInfoModel.setUldType(moveableLocationTypeModel.getPart1());
				uldInfoModel.setFunctionName(UldMovementFunctionTypes.Names.RAMPCHECKIN);
				uldinfoService.updateUldInfo(uldInfoModel);
		}
	}

	@Override
	public String getFlightType(BigInteger flightId) throws CustomException {
		// TODO Auto-generated method stub
		return super.fetchObject("getFlightType", flightId, sqlSession);
	}

	@Override
	public boolean checkUploadPhoto(RampCheckInUld uld) throws CustomException {
		// TODO Auto-generated method stub
		return super.fetchObject("checkUploadPhoto", uld, sqlSession);
	}

	@Override
	public BigInteger fetchHandOverID(RampCheckInUld query) throws CustomException {
		
		return super.fetchObject("fetchHandoverID", query, sqlSession);
	}

}
