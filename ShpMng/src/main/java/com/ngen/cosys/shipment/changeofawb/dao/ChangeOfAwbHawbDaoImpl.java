/**re++requ
 *   ChangeOfAwbHawbDaoImpl.java
 *   
 *   Copyright <PRE><IMG SRC = XX></IMG></PRE>
 *
 * Version      Date         Author      Reason
 * 1.0          30 May, 2018   NIIT      -
 */

package com.ngen.cosys.shipment.changeofawb.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.ngen.cosys.audit.NgenAuditAction;
import com.ngen.cosys.audit.NgenAuditEntityType;
import com.ngen.cosys.audit.NgenAuditEventType;
import com.ngen.cosys.framework.constant.ErrorType;
import com.ngen.cosys.framework.dao.BaseDAO;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.shipment.changeofawb.model.ChangeOfAwbHawb;
import com.ngen.cosys.shipment.enums.ChangeOfAWbHawbQueryIds;
import com.ngen.cosys.validator.dao.ShipmentValidationDao;

/**
 * Implementation class for DAO methods
 * 
 * @author Yuganshu.K
 *
 */

@Repository
public class ChangeOfAwbHawbDaoImpl extends BaseDAO implements ChangeOfAwbHawbDAO {

	@Autowired
	@Qualifier("sqlSessionTemplate")
	private SqlSession sqlSession;
	
	@Autowired
	private ShipmentValidationDao shipmentValidationDao;
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ngen.cosys.shipment.changeofawb.dao.ChangeOfAwbHawbDAO#updateAwbNumber(
	 * com.ngen.cosys.shipment.changeofawb.model.ChangeOfAwbHawb)
	 */

	private static final Logger LOGGER = LoggerFactory.getLogger(ChangeOfAwbHawbDaoImpl.class);

	@Override
	public ChangeOfAwbHawb updateAwbNumber(ChangeOfAwbHawb request) throws CustomException {
		int count = fetchObject(ChangeOfAWbHawbQueryIds.SQL_CHECK_SHIPMENT_ORIGIN.toString(), request, sqlSession);

		// Insert Shipment Remarks and Audit Trail
		String reason = fetchObject(ChangeOfAWbHawbQueryIds.SQL_GET_REASON.toString(), request, sqlSession);
		request.setReasonOfChangeAwbForAuditTrail(reason);

		/* if(count>0) { */
		int newShipmentNumberExist = fetchObject(ChangeOfAWbHawbQueryIds.SQL_CHECK_NEW_AWB_NUMBER.toString(), request,
				sqlSession);
		if (newShipmentNumberExist == 0) {
			updateData(ChangeOfAWbHawbQueryIds.SQL_UPDATE_SHIPMENT_MASTER.toString(), request, sqlSession);
			updateData(ChangeOfAWbHawbQueryIds.SQL_UPDATE_ARRIVAL_MANIFEST.toString(), request, sqlSession);
			updateData(ChangeOfAWbHawbQueryIds.SQL_UPDATE_BREAKDOWN_HANDLING_DEFINATION.toString(), request,
					sqlSession);
			updateData(ChangeOfAWbHawbQueryIds.SQL_UPDATE_INWARD_SERVICE_REPORT_SHIPMENT_DISCREPANCY.toString(),
					request, sqlSession);
			updateData(ChangeOfAWbHawbQueryIds.SQL_UPDATE_AGENT_DELIVERY_PLANNING_WORKSHEET_DISCREPANCY.toString(),
					request, sqlSession);
			updateData(ChangeOfAWbHawbQueryIds.SQL_UPDATE_VEHICLE_PERMIT_AUTHORIZATION.toString(), request, sqlSession);
			updateData(ChangeOfAWbHawbQueryIds.SQL_UPDATE_EQUIPMENT_REQUEST_SHIPMENT.toString(), request, sqlSession);
			updateData(ChangeOfAWbHawbQueryIds.SQL_UPDATE_SHIPMENT_IRREGULARITY.toString(), request, sqlSession);
			updateData(ChangeOfAWbHawbQueryIds.SQL_UPDATE_SHIPMENT_REMARKS.toString(), request, sqlSession);
			updateData(ChangeOfAWbHawbQueryIds.SQL_UPDATE_TRANSSHIPMENT_TRANSFER_MANIFEST_BYAWBINFO.toString(), request,
					sqlSession);
			updateData(ChangeOfAWbHawbQueryIds.SQL_UPDATE_TRANSSHIPMENT_TTWA_CONNECTING_FLIGHT_SHIPMENT.toString(),
					request, sqlSession);
			updateData(ChangeOfAWbHawbQueryIds.SQL_UPDATE_AGT_SID_HEADER.toString(), request, sqlSession);
			updateData(ChangeOfAWbHawbQueryIds.SQL_UPDATE_AGT_INBOUND_PREBOOKING_SHIPMENT.toString(), request,
					sqlSession);
			updateData(ChangeOfAWbHawbQueryIds.SQL_UPDATE_TRANSSHIPMENT_THROUGH_TRANSIT_WORKINGLIST_SHIPMENT.toString(),
					request, sqlSession);
			updateData(ChangeOfAWbHawbQueryIds.SQL_UPDATE_FWB.toString(), request, sqlSession);
			updateData(ChangeOfAWbHawbQueryIds.SQL_UPDATE_DG_DECLARATION.toString(), request, sqlSession);
			updateData(ChangeOfAWbHawbQueryIds.SQL_UPDATE_AgentAcceptancePlanningShipments.toString(), request,
					sqlSession);
			updateData(ChangeOfAWbHawbQueryIds.SQL_UPDATE_EACCEPTANCEDOCUMENTINFORMATION.toString(), request,
					sqlSession);
			updateData(ChangeOfAWbHawbQueryIds.SQL_UPDATE_EXPBOOKINGDELTA.toString(), request, sqlSession);
			updateData(ChangeOfAWbHawbQueryIds.SQL_UPDATE_EXPBOOKING.toString(), request, sqlSession);
			updateData(ChangeOfAWbHawbQueryIds.SQL_UPDATEPRELODGEINFO.toString(), request, sqlSession);
			updateData(ChangeOfAWbHawbQueryIds.SQL_UPDATE_EXP_WORKINGLISTSNAPSHOT.toString(), request, sqlSession);
			updateData(ChangeOfAWbHawbQueryIds.SQL_UPDATE_EXP_AUTOWEIGHSHIPMENT.toString(), request, sqlSession);
			updateData(ChangeOfAWbHawbQueryIds.SQL_UPDATE_SHIPMENTCHECKLISTSTATUS.toString(), request, sqlSession);
			updateData(ChangeOfAWbHawbQueryIds.SQL_UPDATE_BLACKLISTSHIPMENT.toString(), request, sqlSession);
			updateData(ChangeOfAWbHawbQueryIds.SQL_UPDATE_OUTWARDSERVICEREPORTDISC.toString(), request, sqlSession);
			insertData(ChangeOfAWbHawbQueryIds.SQL_INSERT_CHANGE_AWB_HISTORY.toString(), request, sqlSession);

			// For Audit Trail
			changeAWBRemarks(request);

		} else {
			request.addError("shpmng.changeawb.newshipmentnumberalreadyexist", "newShipmentNumber", ErrorType.ERROR);
		}
		/*
		 * }else { request.addError("shpmng.changeawb.exportawb","shipmentNumber",
		 * ErrorType.ERROR); }
		 */
		return request;
	}

	@NgenAuditAction(entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.ADD_CHANGE_AWB_HAWB)
	public void changeAWBRemarks(ChangeOfAwbHawb request) throws CustomException {
		String createdDate = null;
		try {
			String stringCreatedDate = request.getCreatedOn().toString();
			Date createdDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS").parse(stringCreatedDate);
			SimpleDateFormat formatter = new SimpleDateFormat("ddMMMYYYY HH:mm");
			createdDate = formatter.format(createdDateFormat);
		} catch (ParseException e) {
			LOGGER.error("Date Format Issue");
		}
		String shipmentRemark = "CAW/" + request.getShipmentNumber().toString() + '/'
				+ request.getReasonOfChangeAwbForAuditTrail() + '/' + request.getCreatedBy() + '/' + createdDate;
		request.setShipmentRemark(shipmentRemark);
		insertData(ChangeOfAWbHawbQueryIds.SQL_INSERT_CHANGEREMARK.toString(), request, sqlSession);
	}

	@Override
	public ChangeOfAwbHawb updateHawbNumber(ChangeOfAwbHawb request) throws CustomException {
		String reason = null;
		reason = fetchObject(ChangeOfAWbHawbQueryIds.SQL_GET_REASONHAWB.toString(), request, sqlSession);
		request = fetchObject(ChangeOfAWbHawbQueryIds.SQL_CHECK_HAWB_EXISTS.toString(), request, sqlSession);

		if (request != null && request.getShipmentFreightHouseListByHAWBId() > 0) {
			request.setReasonOfChangeHawbForAuditTrail(reason);
			updateData(ChangeOfAWbHawbQueryIds.SQL_UPDATE_HAWB_NUMBER.toString(), request, sqlSession);
			insertData(ChangeOfAWbHawbQueryIds.SQL_INSERT_CHANGE_HAWB_HISTORY.toString(), request, sqlSession);
			changeHAWBRemarks(request);
		} else {
			request = new ChangeOfAwbHawb();
			request.addError("shpmng.changeawb.hawbnotvalid", "hawbNumber", ErrorType.ERROR);
		}
		return request;
	}

	@NgenAuditAction(entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.ADD_CHANGE_AWB_HAWB)
	public void changeHAWBRemarks(ChangeOfAwbHawb request) throws CustomException {
		String createdDate = null;
		try {
			String stringCreatedDate = request.getCreatedOn().toString();
			Date createdDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS").parse(stringCreatedDate);
			SimpleDateFormat formatter = new SimpleDateFormat("ddMMMYYYY HH:mm");
			createdDate = formatter.format(createdDateFormat);
		} catch (ParseException e) {
			LOGGER.error("Date Format Issue");
		}

		String shipmentRemark = "CHW/" + request.getHawbNumber().toString() + '/'
				+ request.getReasonOfChangeHawbForAuditTrail() + '/' + request.getCreatedBy() + '/' + createdDate;
		request.setShipmentRemark(shipmentRemark);
		insertData(ChangeOfAWbHawbQueryIds.SQL_INSERT_CHANGEREHAWBMARK.toString(), request, sqlSession);
	}

	@Override
	public ChangeOfAwbHawb updateHawbNumberHandledByHouse(ChangeOfAwbHawb request) throws CustomException {
		String reason = null;
		reason = fetchObject(ChangeOfAWbHawbQueryIds.SQL_GET_REASONHAWB.toString(), request, sqlSession);
		shipmentValidationDao.validateShipmentBeforeUpdate(request.getShipmentNumber(),
				request.getShipmentDate(), request.getHawbNumber(), true);

		request = fetchObject(ChangeOfAWbHawbQueryIds.SQL_CHECK_HWB_EXISTS_HANDLED_BY_HOUSE.toString(), request,
				sqlSession);
		if (request != null && request.getShipmentHouseId() > 0) {
			request.setReasonOfChangeHawbForAuditTrail(reason);
			updateData(ChangeOfAWbHawbQueryIds.SQL_UPDATE_HWB_HANDLED_BY_HOUSE.toString(), request, sqlSession);
			updateData(ChangeOfAWbHawbQueryIds.SQL_UPDATE_HWB_SHIPMENT_IRREGULARITY.toString(), request, sqlSession);
			updateData(ChangeOfAWbHawbQueryIds.SQL_UPDATE_HWB_SHIPMENT_REMARKS.toString(), request, sqlSession);
			updateData(ChangeOfAWbHawbQueryIds.SQL_UPDATE_HWB_COM_DAMAGE_INFO.toString(), request, sqlSession);
			insertData(ChangeOfAWbHawbQueryIds.SQL_INSERT_CHANGE_HAWB_HISTORY.toString(), request, sqlSession);
			changeHAWBRemarks(request);
		} else {
			request = new ChangeOfAwbHawb();
			request.addError("shpmng.changeawb.hawbnotvalid", "hawbNumber", ErrorType.ERROR);
		}
		return request;
	}
	

	@Override
	public boolean CheckAnyPaidChargeForAWB(ChangeOfAwbHawb request) throws CustomException {
		return fetchObject(ChangeOfAWbHawbQueryIds.SQL_CHECK_ANY_PAID_CHARGE_FOR_AWB.toString(), request, sqlSession);
	}


}
