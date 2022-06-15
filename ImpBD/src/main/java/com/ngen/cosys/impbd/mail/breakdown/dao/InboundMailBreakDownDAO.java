package com.ngen.cosys.impbd.mail.breakdown.dao;

import java.math.BigInteger;
import java.util.List;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.impbd.mail.breakdown.model.InboundMailBreakDownModel;
import com.ngen.cosys.impbd.mail.breakdown.model.InboundMailBreakDownShipmentModel;
import com.ngen.cosys.impbd.mail.breakdown.model.UldDestinationEntry;
import com.ngen.cosys.impbd.mail.breakdown.model.UldDestinationRequest;
import com.ngen.cosys.impbd.shipment.breakdown.model.InboundBreakdownShipmentInventoryModel;
import com.ngen.cosys.impbd.shipment.verification.model.ShipmentVerificationModel;

public interface InboundMailBreakDownDAO {
	InboundMailBreakDownModel flightDetail(InboundMailBreakDownModel requestModel) throws CustomException;

	/**
	 * get code InboundMailBreakdown dao
	 * 
	 * @return fetch the data on the call of search
	 * @throws CustomException
	 */
	InboundMailBreakDownModel search(InboundMailBreakDownModel requestModel) throws CustomException;

	InboundMailBreakDownModel searchMailBreakDown(InboundMailBreakDownModel requestModel) throws CustomException;

	InboundMailBreakDownModel flightData(InboundMailBreakDownModel requestModel) throws CustomException;

	List<InboundMailBreakDownShipmentModel> updateOutgoingCarrier(List<InboundMailBreakDownShipmentModel> requestModel)
			throws CustomException;

	int getMailBagNumberCount(String mailBagNumber) throws CustomException;

	int checkBreakdownComplete(BigInteger flightId) throws CustomException;

	void insertDataintoEmbargoFailure(InboundMailBreakDownShipmentModel requestModel) throws CustomException;

	BigInteger getShipmentHouseInformationId(InboundMailBreakDownShipmentModel requestModel) throws CustomException;

	public String getMailShipmentType(String mailBag) throws CustomException;

	List<InboundMailBreakDownShipmentModel> insertOutgoingCarrier(List<InboundMailBreakDownShipmentModel> requestModel)
			throws CustomException;

	List<InboundMailBreakDownShipmentModel> deleteOutgoingCarrier(List<InboundMailBreakDownShipmentModel> requestModel)
			throws CustomException;

	/**
	 * @param request
	 * @return
	 * @throws CustomException
	 */
	void updateShipmentMasterComplete(BigInteger request) throws CustomException;

	ShipmentVerificationModel getShipmentVerificationPiecesAndWeight(ShipmentVerificationModel requestModel)
			throws CustomException;

	InboundBreakdownShipmentInventoryModel getShipmentInventoryPiecesAndWeight(
			InboundBreakdownShipmentInventoryModel requestModel) throws CustomException;

	InboundBreakdownShipmentInventoryModel getPiecesAndWeightForStorageInfo(
			InboundBreakdownShipmentInventoryModel requestModel) throws CustomException;

	/* Delete Function Declaration starts here */
	int checkShipmentHouseInfo(InboundMailBreakDownShipmentModel requestModel) throws CustomException;

	int fetchInvHseInfo(InboundMailBreakDownShipmentModel requestModel) throws CustomException;

	void deleteInvntryShc(InboundMailBreakDownShipmentModel requestModel) throws CustomException;

	void deleteInvntry(InboundMailBreakDownShipmentModel requestModel) throws CustomException;

	void deletebreakdownHouseInfo(InboundMailBreakDownShipmentModel requestModel) throws CustomException;

	void updateBreakdownStorageInfo(InboundMailBreakDownShipmentModel requestModel) throws CustomException;

	int fetchBreakdownStorageInfoPieceWeight(InboundMailBreakDownShipmentModel requestModel) throws CustomException;

	void deleteBreakdownStorageShcInfo(InboundMailBreakDownShipmentModel requestModel) throws CustomException;

	void deleteBreakdownStorageInfo(InboundMailBreakDownShipmentModel requestModel) throws CustomException;

	void deleteBreakdownTrolleyInfo(InboundMailBreakDownShipmentModel requestModel) throws CustomException;

	void deleteShipmentVerification(InboundMailBreakDownShipmentModel requestModel) throws CustomException;

	void updateShipmentVerificationinfo(InboundMailBreakDownShipmentModel requestModel) throws CustomException;

	void deleteShpHseInf(InboundMailBreakDownShipmentModel requestModel) throws CustomException;

	void deleteShipmentMasterRoutingInfo(InboundMailBreakDownShipmentModel requestModel) throws CustomException;

	void deleteShipmentMaster(InboundMailBreakDownShipmentModel requestModel) throws CustomException;

	void deleteInventoryHouse(InboundMailBreakDownShipmentModel requestModel) throws CustomException;

	void updateInventoryPieceWeight(InboundMailBreakDownShipmentModel requestModel) throws CustomException;

	int getPieceWeightInventryCheck(InboundMailBreakDownShipmentModel requestModel) throws CustomException;

	String getContentCode(String uldNumber) throws CustomException;

	int getLoadedSHC(String shipmentLocation) throws CustomException;

	List<UldDestinationEntry> getUldDestinations(UldDestinationRequest destinationRequest) throws CustomException;

	String checkContainerDestinationForBreakDown(String shipmentLocation) throws CustomException;

	BigInteger getBreakDownStoragePiecesInfo(InboundMailBreakDownShipmentModel requestModel) throws CustomException;

	BigInteger getShpVerPiecesInfo(InboundMailBreakDownShipmentModel requestModel) throws CustomException;

    int checkMailBagAlreadyBrokenDown(InboundMailBreakDownShipmentModel value) throws CustomException;
}