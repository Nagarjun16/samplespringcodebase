package com.ngen.cosys.impbd.dao;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.ngen.cosys.framework.dao.BaseDAO;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.impbd.model.FlightEventsModel;
import com.ngen.cosys.impbd.model.HandoverInboundContainerTrolly;
import com.ngen.cosys.impbd.model.HandoverInboundTrolly;
import com.ngen.cosys.impbd.model.HandoverRampCheckInModel;
import com.ngen.cosys.model.FlightModel;

@Repository("handoverInboundUldTrollyDao")
public class HandoverInboundUldTrollyDaoImpl extends BaseDAO implements HandoverInboundUldTrollyDao {

	@Autowired
	@Qualifier("sqlSessionTemplate")
	private SqlSession sqlSessionImpBd;

	private static final String SQL_FETCH_INBOUND_TROLLY_LIST = "fetchInboundTrollyList";
	private static final String SQL_DELETE_TROLLY_NUMBER_LIST = "deleteTrollyNo";
	private static final String SQL_FIND_INBOUND_TROLLY_ID = "findInboundTrollyId";
	private static final String SQL_UPDATE_INBOUND_TROLLY = "updateInboundTrolly";
	private static final String SQL_INSERT_INBOUND_TROLLY = "insertInboundTrolly";
	private static final String SQL_INSERT_TROLLY_DATA = "insertTrollyData";
	private static final String SQL_UPDATE_TROLLY_DATA = "updateTrollyData";
	private static final String SQL_FETCH_INBOUND_TROLLY_CONTAINER_LIST = "fetchInboundTrollyDetails";
	private static final String SQL_FETCH_FLIGHT_ID_LIST = "getFlightId";
	private static final String SQL_FETCH_TRIP_ID_LIST = "tripDetails";
	private static final String SQL_UPDATE_TROLLEY_LIST = "updateDateTrolley";
	private static final String SQL_CHECK_FLIGHT_ID = "checkFlightTrolley";
	private static final String SQL_DATA_LIST = "fetchData";
	private static final String SQL_UPDATE_DATA = "updateData";
	private static final String SQL_INSERT_RAMP_DATA = "insertRampCheckIn";
	private static final String SQL_FIND_INBOUND_RAMP_ID = "findInboundRampId";
	private static final String SQL_UPDATE_RAMP_DATA = "updateRampData";
	private static final String SQL_FIND_CONTENT_CODE = "findcontentcode";
	private static final String SQL_FIND_CONTENT_TYPE_CODE = "findcontentcodetype";
	private static final String SQl_FIND_DUPLICATE_TROLLEY_NUMBER = "findDuplicateTrolley";
	private static final String SQl_FIND_RAMP_CHECK_IN_COMPLETED = "sqlCheckRampCheckInCompletedForHandoverTrolley";

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ngen.cosys.impbd.dao.HandoverInboundUldTrollyDao#fetchInboundTrolly(com.
	 * ngen.cosys.impbd.model.HandoverInboundTrolly)
	 */
	@Override
	public List<HandoverInboundTrolly> fetchInboundTrolly(FlightModel inboundTrollys) throws CustomException {
		return super.fetchList(SQL_FETCH_INBOUND_TROLLY_LIST, inboundTrollys, sqlSessionImpBd);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ngen.cosys.impbd.dao.HandoverInboundUldTrollyDao#addInboundTrolly(com.
	 * ngen.cosys.impbd.model.HandoverInboundTrolly)
	 */
	@Override
	public List<HandoverInboundTrolly> insertInboundTrolly(HandoverInboundTrolly inboundTrolly) throws CustomException {
		List<HandoverInboundTrolly> inboundData = new ArrayList<>();
		super.insertData(SQL_INSERT_INBOUND_TROLLY, inboundTrolly, sqlSessionImpBd);
		return inboundData;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ngen.cosys.impbd.dao.HandoverInboundUldTrollyDao#addInboundTrolly(com.
	 * ngen.cosys.impbd.model.HandoverInboundTrolly)
	 */
	@Override
	public List<HandoverInboundTrolly> updateInboundTrolly(HandoverInboundTrolly inboundTrolly) throws CustomException {
		List<HandoverInboundTrolly> inboundData = new ArrayList<>();
		super.updateData(SQL_UPDATE_INBOUND_TROLLY, inboundTrolly, sqlSessionImpBd);
		return inboundData;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ngen.cosys.handover.dao.HandoverInboundUldTrollyDao#deleteTrollyNo(com.
	 * ngen.cosys.impbd.model.HandoverInboundContainerTrolly)
	 */
	public List<HandoverInboundContainerTrolly> deleteTrollyNo(HandoverInboundContainerTrolly deleteTrollyNo)
			throws CustomException {
		super.deleteData(SQL_DELETE_TROLLY_NUMBER_LIST, deleteTrollyNo, sqlSessionImpBd);
		return new ArrayList<>();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ngen.cosys.handover.dao.HandoverInboundUldTrollyDao#
	 * searchContainerTrollyId(com.ngen.cosys.handover.model.HandoverInboundTrolly)
	 */
	public List<HandoverInboundContainerTrolly> searchContainerTrollyId(HandoverInboundContainerTrolly handoverInbound)
			throws CustomException {
		return super.fetchList(SQL_FIND_INBOUND_TROLLY_ID, handoverInbound, sqlSessionImpBd);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ngen.cosys.impbd.dao.HandoverInboundContainerTrolly#insertContainerTrolly
	 * (com.ngen.cosys.impbd.model.HandoverInboundTrolly)
	 */
	public HandoverInboundContainerTrolly insertContainerTrolly(HandoverInboundContainerTrolly insertTrolly)
			throws CustomException {
		super.insertData(SQL_INSERT_TROLLY_DATA, insertTrolly, sqlSessionImpBd);
		return insertTrolly;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ngen.cosys.impbd.dao.HandoverInboundContainerTrolly#updateContainerTrolly
	 * (com.ngen.cosys.impbd.model.HandoverInboundTrolly)
	 */
	@Override
	public HandoverInboundContainerTrolly updateContainerTrolly(HandoverInboundContainerTrolly updateTrolly)
			throws CustomException {
		super.updateData(SQL_UPDATE_TROLLY_DATA, updateTrolly, sqlSessionImpBd);
		return updateTrolly;
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ngen.cosys.impbd.dao.HandoverInboundContainerTrolly#editInboundTrolly(com
	 * .ngen.cosys.impbd.model.HandoverInboundTrolly)
	 */

	@Override
	public List<HandoverInboundTrolly> editInboundTrolly(HandoverInboundTrolly editTrollyData) throws CustomException {
		return super.fetchList(SQL_FETCH_INBOUND_TROLLY_CONTAINER_LIST, editTrollyData, sqlSessionImpBd);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ngen.cosys.impbd.dao.HandoverInboundUldTrollyDao#getFlightId(com.ngen.
	 * cosys.impbd.model.HandoverInboundTrolly)
	 */
	@Override
	public HandoverInboundTrolly getFlightId(HandoverInboundTrolly flightDetails) throws CustomException {
		return super.fetchObject(SQL_FETCH_FLIGHT_ID_LIST, flightDetails, sqlSessionImpBd);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ngen.cosys.impbd.dao.HandoverInboundUldTrollyDao#getFlightId(com.ngen.
	 * cosys.impbd.model.HandoverInboundTrolly)
	 */
	@Override
	public HandoverInboundTrolly tripDetails(HandoverInboundTrolly tripDetails) throws CustomException {
		return super.fetchObject(SQL_FETCH_TRIP_ID_LIST, tripDetails, sqlSessionImpBd);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ngen.cosys.impbd.dao.HandoverInboundUldTrollyDao#getFlightId(com.ngen.
	 * cosys.impbd.model.FlightEventsModel)
	 */
	@Override
	public FlightEventsModel updatetractorId(FlightEventsModel update) throws CustomException {
		FlightEventsModel events = new FlightEventsModel();
		if (update != null) {
			super.updateData(SQL_UPDATE_TROLLEY_LIST, update, sqlSessionImpBd);
		}
		return events;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ngen.cosys.impbd.dao.HandoverInboundUldTrollyDao#getFlightId(com.ngen.
	 * cosys.impbd.model.FlightEventsModel)
	 */
	@Override
	public FlightEventsModel checkFligtId(FlightEventsModel update) throws CustomException {
		return super.fetchObject(SQL_CHECK_FLIGHT_ID, update, sqlSessionImpBd);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ngen.cosys.impbd.dao.HandoverInboundUldTrollyDao#getFlightId(com.ngen.
	 * cosys.impbd.model.HandoverInboundTrolly)
	 */
	@Override
	public HandoverInboundTrolly getData(HandoverInboundTrolly data) throws CustomException {
		return super.fetchObject(SQL_DATA_LIST, data, sqlSessionImpBd);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ngen.cosys.impbd.dao.HandoverInboundUldTrollyDao#getFlightId(com.ngen.
	 * cosys.impbd.model.FlightEventsModel)
	 */
	@Override
	public FlightEventsModel updatetractorData(FlightEventsModel updateData) throws CustomException {
		return super.fetchObject(SQL_UPDATE_DATA, updateData, sqlSessionImpBd);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ngen.cosys.impbd.dao.HandoverInboundUldTrollyDao#insertContainerTrolly(
	 * com.ngen.cosys.impbd.model.HandoverRampCheckInModel)
	 */
	public HandoverRampCheckInModel insertRampCheckIn(HandoverRampCheckInModel insertUld) throws CustomException {
		super.insertData(SQL_INSERT_RAMP_DATA, insertUld, sqlSessionImpBd);
		return insertUld;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ngen.cosys.handover.dao.HandoverInboundUldTrollyDao#searchRampCheckInId(
	 * com.ngen.cosys.handover.model.HandoverRampCheckInModel)
	 */
	public List<HandoverRampCheckInModel> searchRampCheckInId(HandoverRampCheckInModel handoverRamp)
			throws CustomException {
		return super.fetchList(SQL_FIND_INBOUND_RAMP_ID, handoverRamp, sqlSessionImpBd);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ngen.cosys.impbd.dao.HandoverInboundUldTrollyDao#updateContainerTrolly(
	 * com.ngen.cosys.impbd.model.HandoverRampCheckInModel)
	 */
	@Override
	public HandoverRampCheckInModel updateRampCheckIn(HandoverRampCheckInModel updateRampCheck) throws CustomException {
		super.updateData(SQL_UPDATE_RAMP_DATA, updateRampCheck, sqlSessionImpBd);
		return updateRampCheck;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ngen.cosys.handover.dao.HandoverInboundUldTrollyDao#
	 * searchContainerTrollyId(com.ngen.cosys.handover.model.HandoverInboundTrolly)
	 */
	public HandoverInboundContainerTrolly searchContentCode(HandoverInboundContainerTrolly contentCodeValue)
			throws CustomException {
		return super.fetchObject(SQL_FIND_CONTENT_CODE, contentCodeValue, sqlSessionImpBd);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ngen.cosys.handover.dao.HandoverInboundUldTrollyDao#
	 * searchContainerTrollyId(com.ngen.cosys.handover.model.HandoverInboundTrolly)
	 */
	public HandoverInboundContainerTrolly searchContentCodeType(HandoverInboundContainerTrolly contentCodeType)
			throws CustomException {
		return super.fetchObject(SQL_FIND_CONTENT_TYPE_CODE, contentCodeType, sqlSessionImpBd);
	}

	@Override
	public List<String> searchContainerTrollyForDuplicate(HandoverInboundTrolly inboundTrolly) throws CustomException {
		return super.fetchList(SQl_FIND_DUPLICATE_TROLLEY_NUMBER, inboundTrolly, sqlSessionImpBd);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ngen.cosys.impbd.dao.HandoverInboundUldTrollyDao#isCheckInCompleted(java.
	 * math.BigInteger)
	 */
	@Override
	public boolean isCheckInCompleted(BigInteger flightId) throws CustomException {
		return this.fetchObject(SQl_FIND_RAMP_CHECK_IN_COMPLETED, flightId, sqlSessionImpBd);
	}

}