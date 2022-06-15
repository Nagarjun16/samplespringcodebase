package com.ngen.cosys.impbd.workinglist.dao;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.ngen.cosys.audit.NgenAuditAction;
import com.ngen.cosys.audit.NgenAuditEntityType;
import com.ngen.cosys.audit.NgenAuditEventType;
import com.ngen.cosys.events.payload.ShipmentNotification;
import com.ngen.cosys.framework.dao.BaseDAO;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.impbd.constants.SqlIDs;
import com.ngen.cosys.impbd.model.ArrivalManifestByFlightModel;
import com.ngen.cosys.impbd.model.ArrivalManifestShipmentInfoModel;
import com.ngen.cosys.impbd.notification.ShipmentNotificationDetail;
import com.ngen.cosys.impbd.shipment.breakdown.constant.InboundBreakDownSqlId;
import com.ngen.cosys.impbd.shipment.irregularity.model.ShipmentIrregularityModel;
import com.ngen.cosys.impbd.workinglist.model.BreakDownWorkingListModel;
import com.ngen.cosys.impbd.workinglist.model.BreakDownWorkingListSegmentModel;
import com.ngen.cosys.impbd.workinglist.model.BreakDownWorkingListShipmentResult;
import com.ngen.cosys.impbd.workinglist.model.ExportWorkingListModel;
import com.ngen.cosys.impbd.workinglist.model.SendTSMMessage;
import com.ngen.cosys.multitenancy.utilities.MultiTenantUtility;

/**
 * This class is responsible for the Break Down Working List DAO operation that
 * comes from the Service.
 * 
 * @author NIIT Technologies Ltd
 *
 */
@Repository
public class BreakDownWorkingListDAOImpl extends BaseDAO implements BreakDownWorkingListDAO {

	@Autowired
	@Qualifier("sqlSessionTemplate")
	private SqlSessionTemplate sqlSession;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ngen.cosys.impbd.dao.BreakDownWorkingListDAO#getBreakDownWorkingList(com.
	 * ngen.cosys.impbd.model.ArrivalManifestFlight)
	 */
	@Override
	public BreakDownWorkingListModel getBreakDownWorkingList(BreakDownWorkingListModel breakDownWorkingListModel)
			throws CustomException {
		return this.fetchObject("sqlGetBreakDownWorkingListFlights", breakDownWorkingListModel, sqlSession);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ngen.cosys.impbd.dao.BreakDownWorkingListDAO#
	 * updateFlightDelayForShipments(java.util.List)
	 */
	@Override
	public List<Integer> updateFlightDelayForShipments(
			List<BreakDownWorkingListShipmentResult> breakDownWorkingListShipmentResultList) throws CustomException {
		ArrayList<Integer> updatedList = new ArrayList<>();
		for (int nCount = 0; nCount < breakDownWorkingListShipmentResultList.size(); nCount++) {
			BreakDownWorkingListShipmentResult breakDownWorkingListShipment = breakDownWorkingListShipmentResultList
					.get(nCount);
			if (breakDownWorkingListShipment != null && breakDownWorkingListShipment.getFlightId() != null
					&& breakDownWorkingListShipment.getShipmentNumber() != null
					&& breakDownWorkingListShipment.getShipmentId() != null) {
				updatedList.add(this.insertData("sqlUpdateFlightDelayForShipment",
						breakDownWorkingListShipmentResultList.get(nCount), sqlSession));
			}
		}
		return updatedList;

	}

	@Override
	@NgenAuditAction(entityType = NgenAuditEntityType.FLIGHT, eventName = NgenAuditEventType.BREAKDOWN_COMPLETE)
	public void updateBreakdownCompleteFirstTimeStatus(BreakDownWorkingListModel workinListFlightModel)
			throws CustomException {
		BreakDownWorkingListModel requestModel = new BreakDownWorkingListModel();
		requestModel.setFlightId(workinListFlightModel.getFlightId());
		requestModel.setFlightRemarks(workinListFlightModel.getFlightRemarks());
		requestModel.setLastupdatedBy(workinListFlightModel.getModifiedBy());
		requestModel.setLastmodifiedOn(workinListFlightModel.getModifiedOn());
		requestModel.setFlightNumber(workinListFlightModel.getFlightNumber());
		requestModel.setFlightDate(workinListFlightModel.getFlightDate());
		requestModel.setBreakdownComplete(Boolean.TRUE);
		updateData(SqlIDs.UPDATEBREAKDOWNCOMPLETEFIRSTTIMESTATUS.toString(), requestModel,
				sqlSession);
	}

	@Override
	@NgenAuditAction(entityType = NgenAuditEntityType.FLIGHT, eventName = NgenAuditEventType.BREAKDOWN_COMPLETE)
	public void updateBreakdownCompleteNextTimeStatus(BreakDownWorkingListModel workinListFlightModel)
			throws CustomException {
		BreakDownWorkingListModel requestModel = new BreakDownWorkingListModel();
		requestModel.setFlightId(workinListFlightModel.getFlightId());
		requestModel.setFlightRemarks(workinListFlightModel.getFlightRemarks());
		requestModel.setLastupdatedBy(workinListFlightModel.getModifiedBy());
		requestModel.setLastmodifiedOn(workinListFlightModel.getModifiedOn());
		requestModel.setFlightNumber(workinListFlightModel.getFlightNumber());
		requestModel.setFlightDate(workinListFlightModel.getFlightDate());
		requestModel.setBreakdownComplete(Boolean.FALSE);
		updateData(SqlIDs.UPDATEBREAKDOWNCOMPLETENEXTTIMESTATUS.toString(), requestModel,
				sqlSession);
	}

	@Override
	public List<ShipmentIrregularityModel> getIrregularity(BreakDownWorkingListModel documentVerificationFlightModel)
			throws CustomException {
		return fetchList(InboundBreakDownSqlId.GETIRREGULARITYINFO.toString(), documentVerificationFlightModel,
				sqlSession);
	}
	
	@Override
	public List<ShipmentIrregularityModel> getIrregularityForHawbHandling(BreakDownWorkingListModel documentVerificationFlightModel)
			throws CustomException {
		return fetchList(InboundBreakDownSqlId.GETIRREGULARITYINFO_HAWB_HANDLING.toString(), documentVerificationFlightModel,
				sqlSession);
	}

	@Override
	public Integer checkDocumentCompleted(BreakDownWorkingListModel workListModel) throws CustomException {
		return fetchObject(InboundBreakDownSqlId.CHECKDOCUMENTCOMPLETED.getQueryId(), workListModel, sqlSession);
	}

	@Override
	public void updateTracingInfo(ShipmentIrregularityModel irregularityData) throws CustomException {
		updateData("updateTracingShipmentInfo", irregularityData, sqlSession);
	}

	@Override
	public void insertWorkingListFlight(ArrivalManifestByFlightModel flightData) throws CustomException {
		insertData("insertExportWorkingListFlightDetails", flightData, sqlSession);
	}

	@Override
	public void insertWorkingListShipment(ArrivalManifestShipmentInfoModel shipmentData) throws CustomException {
		insertData("insertExportWorkingListShipmentDetails", shipmentData, sqlSession);
	}

	@Override
	public List<ExportWorkingListModel> fetchBookingDetails(ArrivalManifestShipmentInfoModel shipmentData)
			throws CustomException {
		return fetchList("sqlGetBookingDetails", shipmentData, sqlSession);
	}

	@Override
	@NgenAuditAction(entityType = NgenAuditEntityType.FLIGHT, eventName = NgenAuditEventType.FLIGHT_COMPLETE)
	public void updateFlightComplete(BreakDownWorkingListModel breakDownWorkingListModel) throws CustomException {
		breakDownWorkingListModel.setFlightCompleted(Boolean.TRUE);
		updateData("updateFlightComplete", breakDownWorkingListModel, sqlSession);
	}

	@Override
	public Integer isFlightCompleted(BreakDownWorkingListModel workListModel) throws CustomException {
		return fetchObject("sqlCheckFlightCompleted", workListModel, sqlSession);
	}

	@Override
	public Integer getULDCount(BreakDownWorkingListSegmentModel segmentData) throws CustomException {
		return fetchObject("sqlGetULDCountt", segmentData, sqlSession);
	}

	@Override
	public BigInteger checkShipmentExists(ArrivalManifestShipmentInfoModel shipmentData) throws CustomException {
		return fetchObject("sqlCheckExportShipmentsExists", shipmentData, sqlSession);
	}

	@Override
	public ArrivalManifestByFlightModel checkOutgoingFlightExists(ArrivalManifestByFlightModel flightData)
			throws CustomException {
		return fetchObject("sqlCheckExportFlightExists", flightData, sqlSession);
	}

	@Override
	public void reopenFinalizeStatus(BreakDownWorkingListModel breakDownWorkingListModel) throws CustomException {
		updateData("unFinalizeServiceReportforcargo", breakDownWorkingListModel, sqlSession);
	}

	@Override
	public void insertLocalAuthorityDetails(BreakDownWorkingListShipmentResult shipmentData) throws CustomException {
		Integer count = fetchObject("sqlCheckAuthorityDetailsExists", shipmentData, sqlSession);
		if (count == 0 && Objects.nonNull(shipmentData.getShipmentId())) {
			insertData("inserLocalAuthorityInfoDetails", shipmentData, sqlSession);
			insertData("inserLocalAuthorityDetails", shipmentData, sqlSession);
		}
	}

	public Boolean getTotalRCFStatusUpdateEventShipmentPieces(BreakDownWorkingListShipmentResult shipmentData)
			throws CustomException {
		return fetchObject("sqltotalStatusUpdateEventShipmentPieces", shipmentData, sqlSession);

	}
	
	@Override
	public List<String> getEmailsForLhCarrier(BreakDownWorkingListModel requestModel) throws CustomException {
		return fetchList("getEmailsForLhCarrierForRCFNFD", requestModel, sqlSession);
	}

   @Override
   public ShipmentNotificationDetail getShipmentNotificationDetail(ShipmentNotification shipmentNotification)
         throws CustomException {
      return fetchObject("sqlSelectShipmentNotificationContactDetail", shipmentNotification, sqlSession);
   }
   @Override
   public boolean isFlighHandledInSystem(String awbNumber) throws CustomException {
	   
		//check for booking if booking not there then check then route
		boolean bookingflag =fetchObject("sqlQueryBookingForTSM", MultiTenantUtility.getAirportCityMap(awbNumber), sqlSession);
		if(bookingflag) {
			// get out bound booking if it has, if it has check for handledInSystem flag
			return fetchObject("sqlCheckFlightHandledInSystem",  MultiTenantUtility.getAirportCityMap(awbNumber), sqlSession); 
		}
		if(!bookingflag) {
			//else check for the routing 
			 return fetchObject("sqlroutingConfigForTsm",  MultiTenantUtility.getAirportCityMap(awbNumber), sqlSession);
		}
		return bookingflag;
	}

	@Override
	public void validateDataAndTriggerEvents(SendTSMMessage model) throws CustomException {
		// check for flight if exists return flightid
		if (!StringUtils.isEmpty(model.getFlightKey()) && model.getFlightDate() != null) {
			SendTSMMessage flight = this.fetchObject("validateFlightForTSM", model, sqlSession);
			if (!ObjectUtils.isEmpty(flight))
				model.setFlightId(flight.getFlightId());

		}
		// check for shipment, if exists return shipmentId
		SendTSMMessage shipment = this.fetchObject("validateShipmentForTSM", model, sqlSession);
		if (!ObjectUtils.isEmpty(shipment)) {
			model.setShipmentId((shipment.getShipmentId()));
			model.setFlightId(shipment.getFlightId());
		}
	}
	
	public List<String> getpartSuffixForshipment(BreakDownWorkingListShipmentResult parameter) throws CustomException{
		return fetchList("getShipmentInventoryDetailsForTsm", parameter, sqlSession);
	}

	@Override
	public boolean isDataSyncCREnabled() throws CustomException {
		String flag=fetchObject("sqlCheckDataSyncCREnabled",null,sqlSession);
		return flag.equalsIgnoreCase("Y");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ngen.cosys.impbd.workinglist.dao.BreakDownWorkingListDAO#
	 * reopenFlightComplete(com.ngen.cosys.impbd.workinglist.model.
	 * BreakDownWorkingListModel)
	 */
	@Override
	@NgenAuditAction(entityType = NgenAuditEntityType.FLIGHT, eventName = NgenAuditEventType.FLIGHT_COMPLETE)
	public void reopenFlightComplete(BreakDownWorkingListModel requestModel) throws CustomException {
		requestModel.setFlightCompleted(Boolean.FALSE);
		updateData("sqlQueryReOpenFlightCompleteForBreakDown", requestModel, sqlSession);
	}

	@Override
	public BreakDownWorkingListShipmentResult getBreakdownPiecesWeight(BreakDownWorkingListShipmentResult shipmentData)
			throws CustomException {
		
		return fetchObject("sqlBreakdownPicesWeightForRCFEevent",shipmentData,sqlSession);
	}

	@Override
	public Boolean getFlightType(BreakDownWorkingListModel breakDownWorkingListModel) throws CustomException {
		
		return fetchObject("sqlQueryforInternationalValidationBD", breakDownWorkingListModel, sqlSession);
	}
	
	@Override
	public String getFlightIgmNumber(BreakDownWorkingListModel breakDownWorkingListModel) throws CustomException {
		
		return fetchObject("sqlQueryforFlightIgmNumber", breakDownWorkingListModel, sqlSession);
	}
	
}