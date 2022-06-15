package com.ngen.cosys.impbd.workinglist.dao;

import java.math.BigInteger;
import java.util.List;

import com.ngen.cosys.events.payload.ShipmentNotification;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.impbd.model.ArrivalManifestByFlightModel;
import com.ngen.cosys.impbd.model.ArrivalManifestShipmentInfoModel;
import com.ngen.cosys.impbd.notification.ShipmentNotificationDetail;
import com.ngen.cosys.impbd.shipment.irregularity.model.ShipmentIrregularityModel;
import com.ngen.cosys.impbd.workinglist.model.BreakDownWorkingListModel;
import com.ngen.cosys.impbd.workinglist.model.BreakDownWorkingListSegmentModel;
import com.ngen.cosys.impbd.workinglist.model.BreakDownWorkingListShipmentResult;
import com.ngen.cosys.impbd.workinglist.model.ExportWorkingListModel;
import com.ngen.cosys.impbd.workinglist.model.SendTSMMessage;

public interface BreakDownWorkingListDAO {
	
	public BreakDownWorkingListModel getBreakDownWorkingList(BreakDownWorkingListModel breakDownWorkingListModel)
			throws CustomException;

	public List<Integer> updateFlightDelayForShipments(
			List<BreakDownWorkingListShipmentResult> breakDownWorkingListShipmentResult) throws CustomException;

	void updateBreakdownCompleteFirstTimeStatus(BreakDownWorkingListModel documentVerificationFlightModel)
			throws CustomException;

	void updateBreakdownCompleteNextTimeStatus(BreakDownWorkingListModel documentVerificationFlightModel)
			throws CustomException;

	List<ShipmentIrregularityModel> getIrregularity(BreakDownWorkingListModel documentVerificationFlightModel)
			throws CustomException;
	
	List<ShipmentIrregularityModel> getIrregularityForHawbHandling(BreakDownWorkingListModel documentVerificationFlightModel)
			throws CustomException;

	public Integer checkDocumentCompleted(BreakDownWorkingListModel workListModel) throws CustomException;

	public void updateTracingInfo(ShipmentIrregularityModel irregularityData) throws CustomException;

	public void insertWorkingListFlight(ArrivalManifestByFlightModel flightData) throws CustomException;

	public void insertWorkingListShipment(ArrivalManifestShipmentInfoModel shipmentData) throws CustomException;

	public List<ExportWorkingListModel> fetchBookingDetails(ArrivalManifestShipmentInfoModel shipmentData)
			throws CustomException;

	void updateFlightComplete(BreakDownWorkingListModel breakDownWorkingListModel) throws CustomException;

	Integer isFlightCompleted(BreakDownWorkingListModel workListModel) throws CustomException;

	Integer getULDCount(BreakDownWorkingListSegmentModel segmentData) throws CustomException;

	BigInteger checkShipmentExists(ArrivalManifestShipmentInfoModel shipmentData) throws CustomException;

	ArrivalManifestByFlightModel checkOutgoingFlightExists(ArrivalManifestByFlightModel flightData)
			throws CustomException;

	public void reopenFinalizeStatus(BreakDownWorkingListModel breakDownWorkingListModel) throws CustomException;

	void insertLocalAuthorityDetails(BreakDownWorkingListShipmentResult shipmentData) throws CustomException;
	
	/**
	 * 
	 * @param shipmentData
	 * @return
	 * @throws CustomException
	 */
	Boolean getTotalRCFStatusUpdateEventShipmentPieces(BreakDownWorkingListShipmentResult shipmentData) throws CustomException;
	
	
	/**
	 * 
	 * @param requestModel
	 * @return get MailIds from master configuration 
	 * @throws CustomException
	 */
	public List<String> getEmailsForLhCarrier(BreakDownWorkingListModel requestModel) throws CustomException;
	
	/**
	 * @param shipmentNotification
	 * @return
	 * @throws CustomException
	 */
	public ShipmentNotificationDetail getShipmentNotificationDetail(ShipmentNotification shipmentNotification)
          throws CustomException;
    public boolean isFlighHandledInSystem(String awbNumber) throws CustomException;
	public void validateDataAndTriggerEvents(SendTSMMessage model)throws CustomException;
	public List<String> getpartSuffixForshipment(BreakDownWorkingListShipmentResult parameter) throws CustomException;
	public boolean isDataSyncCREnabled() throws CustomException;
	
	/**
	 * Method to re-open the flight complete if either document OR break down status has been re-opened
	 * 
	 * @param requestModel - Flight Information to re-open the flight complete
	 * @throws CustomException
	 */
	void reopenFlightComplete(BreakDownWorkingListModel requestModel)throws CustomException;
	
	/**
	 * 
	 * @param shipmentData
	 * @return
	 * @throws CustomException
	 */
	BreakDownWorkingListShipmentResult getBreakdownPiecesWeight(BreakDownWorkingListShipmentResult shipmentData) throws CustomException;

	Boolean getFlightType(BreakDownWorkingListModel breakDownWorkingListModel) throws CustomException;

	String getFlightIgmNumber(BreakDownWorkingListModel breakDownWorkingListModel) throws CustomException;

}