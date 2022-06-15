package com.ngen.cosys.impbd.shipment.verification.dao;

import java.util.List;

import com.ngen.cosys.events.payload.ShipmentNotification;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.impbd.model.ArrivalManifestShipmentInfoModel;
import com.ngen.cosys.impbd.notification.ShipmentNotificationDetail;
import com.ngen.cosys.impbd.shipment.irregularity.model.ShipmentIrregularityModel;
import com.ngen.cosys.impbd.shipment.verification.model.BookingInfo;
import com.ngen.cosys.impbd.shipment.verification.model.DgRegulations;
import com.ngen.cosys.impbd.shipment.verification.model.DocumentVerificationFlightModel;
import com.ngen.cosys.impbd.shipment.verification.model.DocumentVerificationShipmentModel;
import com.ngen.cosys.impbd.shipment.verification.model.EliElmDGDModel;
import com.ngen.cosys.impbd.shipment.verification.model.EliElmDGDModelList;
import com.ngen.cosys.impbd.shipment.verification.model.SearchDGDeclations;
import com.ngen.cosys.impbd.shipment.verification.model.SearchRegulationDetails;
import com.ngen.cosys.impbd.shipment.verification.model.ShipperDeclaration;
import com.ngen.cosys.impbd.shipment.verification.model.ShipperDeclarationDetail;
import com.ngen.cosys.impbd.shipment.verification.model.UNIDOverpackDetails;
import com.ngen.cosys.inward.model.InwardServiceReportModel;
import com.ngen.cosys.inward.model.InwardServiceReportShipmentDiscrepancyModel;

public interface DocumentVerificationDAO {

	/**
	 * Method to get all shipments for verification
	 * 
	 * @param requestModel
	 * @return DocumentVerificationFlightModel
	 * @throws CustomException
	 */
	DocumentVerificationFlightModel get(DocumentVerificationFlightModel requestModel) throws CustomException;

	/**
	 * @param arrivalManifestShipmentInfoModel
	 * @throws CustomException
	 */
	void offloadToArrivalManifest(ArrivalManifestShipmentInfoModel arrivalManifestShipmentInfoModel)
			throws CustomException;

	/**
	 * @param documentVerificationFlightModel
	 * @return
	 */
	boolean isFlightExistsinRemarks(DocumentVerificationFlightModel documentVerificationFlightModel)
			throws CustomException;

	/**
	 * @param documentVerificationFlightModel
	 */
	void updateDocumentCompleteFirstTimeStatus(DocumentVerificationFlightModel documentVerificationFlightModel)
			throws CustomException;

	/**
	 * @param documentVerificationFlightModel
	 */
	void insertIntoDocumentCompleteRemarks(DocumentVerificationFlightModel documentVerificationFlightModel)
			throws CustomException;

	/**
	 * @param documentVerificationFlightModel
	 * @return
	 */
	boolean isChecksforFirstTime(DocumentVerificationFlightModel documentVerificationFlightModel)
			throws CustomException;

	/**
	 * @param documentVerificationFlightModel
	 * @throws CustomException
	 */
	void updateDocumentCompleteNextTimeStatus(DocumentVerificationFlightModel documentVerificationFlightModel)
			throws CustomException;

	/**
	 * @param documentVerificationFlightModel
	 * @throws CustomException
	 */
	void updateDocumentCompleReopen(DocumentVerificationFlightModel documentVerificationFlightModel)
			throws CustomException;

	/**
	 * @param documentVerificationFlightModel
	 * @return
	 * @throws CustomException
	 */
	List<ShipmentIrregularityModel> getIrregularity(DocumentVerificationFlightModel documentVerificationFlightModel)
			throws CustomException;

	DocumentVerificationFlightModel onHoldShipments(DocumentVerificationFlightModel documentVerificationFlightModel)
			throws CustomException;

	ShipperDeclaration saveDgDeclarationsDetails(ShipperDeclaration shippersDeclarationDetails) throws CustomException;

	List<ShipperDeclaration> getDgdDetails(SearchDGDeclations search) throws CustomException;

	void deleteUnidOverPackDetails(List<UNIDOverpackDetails> unidDetails) throws CustomException;

	void deleteShipperDeclarationDetails(List<ShipperDeclarationDetail> dgdDetails) throws CustomException;

	List<DgRegulations> getRegDetails(SearchRegulationDetails searchRegId) throws CustomException;

	Integer getSeqNO(SearchDGDeclations search) throws CustomException;

	/**
	 * Method to check break down is complete or not
	 * 
	 * @param documentVerificationFlightModel
	 * @return boolean - if true then break down is marked as complete otherwise it
	 *         is not marked as complete
	 * @throws CustomException
	 */
	boolean isBreakDownComplete(DocumentVerificationFlightModel documentVerificationFlightModel) throws CustomException;

	/**
	 * Method to mark flight as complete
	 * 
	 * @param documentVerificationFlightModel
	 * @throws CustomException
	 */
	void updateFlightComplete(DocumentVerificationFlightModel documentVerificationFlightModel) throws CustomException;

	/**
	 * Fetch Notification Email ID Based On ShipmentId
	 * 
	 * @param shipmentId
	 * @return
	 * @throws CustomException
	 */
	List<String> getEmailBasedOnShipmentId(String shipmentId) throws CustomException;

	EliElmDGDModel saveOrDelEliElm(EliElmDGDModel elmDGDModel) throws CustomException;

	EliElmDGDModel deleteEliElmDetails(EliElmDGDModel elmDGDModel) throws CustomException;

	EliElmDGDModel getEliElmDetails(EliElmDGDModel elmDGDModel) throws CustomException;

	EliElmDGDModelList getEliElmRemark(EliElmDGDModelList elmDGDModel) throws CustomException;

	void deleteDataFromShipmentVerification(DocumentVerificationShipmentModel verificationModelList)
			throws CustomException;

	public int recordExistInCDH(DocumentVerificationShipmentModel model) throws CustomException;

	public EliElmDGDModelList isBookingDoneForShipment(EliElmDGDModel request) throws CustomException;

	public EliElmDGDModelList getDangerousGoodsHandlingInstructions(EliElmDGDModelList request) throws CustomException;

	public void insertOffloadInfoinInwardServiceRerort(InwardServiceReportModel model,
			InwardServiceReportShipmentDiscrepancyModel descripancyModel) throws CustomException;

	/**
	 * @param shipmentNotification
	 * @return
	 * @throws CustomException
	 */
	public ShipmentNotificationDetail getShipmentNotificationDetail(ShipmentNotification shipmentNotification)
			throws CustomException;

	public List<BookingInfo> getBookingInfo(DocumentVerificationShipmentModel verificationModel) throws CustomException;
	
	public List<BookingInfo> getPartBookingInfo(DocumentVerificationShipmentModel verificationModel) throws CustomException;

	public void updateBookingInfo(BookingInfo bookingDetails) throws CustomException;
	
	public List<String> getCarrierCodesForSQGroup(String carrier) throws CustomException;
	
	public String getStatusOfCodeAdministration(String carrier) throws CustomException;

	/**
	 * Method to re-open the flight complete if either document OR break down status has been re-opened
	 * 
	 * @param requestModel - Flight Information to re-open the flight complete
	 * @throws CustomException
	 */
	void reopenFlightComplete(DocumentVerificationFlightModel requestModel)throws CustomException;
	
	public String getFlightType(DocumentVerificationFlightModel flightId) throws CustomException;
	
}