package com.ngen.cosys.satssginterfaces.mss.dao;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ngen.cosys.framework.dao.BaseDAO;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.satssginterfaces.mss.model.BuildUpMailSearch;
import com.ngen.cosys.satssginterfaces.mss.model.ExportMailBagManifest;
import com.ngen.cosys.satssginterfaces.mss.model.MSSMailBagMovement;
import com.ngen.cosys.satssginterfaces.mss.model.ManifestSegment;
import com.ngen.cosys.satssginterfaces.mss.model.PreannouncementUldMessagesModel;
import com.ngen.cosys.satssginterfaces.mss.model.RequestPreannouncementUldMessagesModel;
import com.ngen.cosys.satssginterfaces.mss.model.ResponseErrorMessagesHeaderMss;
import com.ngen.cosys.satssginterfaces.mss.model.ResponseMssMailBagMovement;
import com.ngen.cosys.satssginterfaces.mss.model.ULDIInformationDetails;

@Repository
public class MssDAOImpl extends BaseDAO implements MssDAO {

	@Autowired
	@Qualifier("sqlSessionTemplate")
	private SqlSession sqlSessionTemplate;

	@Override
	public List<PreannouncementUldMessagesModel> preannouncementUldMessage(RequestPreannouncementUldMessagesModel model)
			throws CustomException {
		return super.fetchList("getPreannouncementMessageData", model, sqlSessionTemplate);
	}

	@Override
	public List<ResponseErrorMessagesHeaderMss> responseHeader() throws CustomException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer updateErrorResponseMessageinOutgoingMessage(BigInteger id) throws CustomException {
		return super.updateData("updateOutgoingMessageTableStatus", id, sqlSessionTemplate);
	}

	@Override
	public RequestPreannouncementUldMessagesModel selectFlightKey(BigInteger flightId) throws CustomException {
		return super.fetchObject("selectRequestPreannouncementULDModelInfo", flightId, sqlSessionTemplate);
	}

	@Override
	public ResponseMssMailBagMovement messageTypeTGFMMBSI(MSSMailBagMovement requestModel) throws CustomException {
		return super.fetchObject("selectMailBagDataForTGFMMBSI", requestModel, sqlSessionTemplate);

	}

	@Override
	public void messageTypeTGFMMBUL(MSSMailBagMovement requestModel) throws CustomException {
		// TODO Auto-generated method stub

	}

	@Override
	public void messageTypeTGFMCULD(MSSMailBagMovement requestModel) throws CustomException {
		// TODO Auto-generated method stub

	}

	@Override
	public BigInteger getIncommingFlightId(MSSMailBagMovement mailBagUldMovementBaseModel) throws CustomException {
		BigInteger flightId = super.fetchObject("getImportFlightIdForMSS", mailBagUldMovementBaseModel,
				sqlSessionTemplate);
		return flightId;
	}

	@Override
	public BigInteger getOutgoingFlightId(MSSMailBagMovement mailBagUldMovementBaseModel) throws CustomException {
		BigInteger flightId = super.fetchObject("getExportFlightIdForMSS", mailBagUldMovementBaseModel,
				sqlSessionTemplate);
		return flightId;
	}

	@Override
	public void updateLyingList(MSSMailBagMovement mailBagUldMovementBaseModel) throws CustomException {
		super.updateData("updateLyingListShipmentMaster", mailBagUldMovementBaseModel, sqlSessionTemplate);
	}

	@Override
	public List<BuildUpMailSearch> fetchMailbagDetail(MSSMailBagMovement requestModel) throws CustomException {
		return fetchList("sqlGetMailBagtDetail", requestModel, sqlSessionTemplate);
	}

	@Override
	public BigInteger getFLightSegmentIdForAssignedTrolley(MSSMailBagMovement requestModel) throws CustomException {
		return fetchObject("sqlGetAssignedFLightSegment", requestModel, sqlSessionTemplate);
	}

	@Override
	public BigInteger getFLightSegmentIdForexportFlight(MSSMailBagMovement requestModel) throws CustomException {
		return fetchObject("getFLightSegmentForExportFlight", requestModel, sqlSessionTemplate);
	}

	@Override
	public MSSMailBagMovement getExpFLightEventsInfo(MSSMailBagMovement requestModel) throws CustomException {
		return fetchObject("getExpFLightEventsInfo", requestModel, sqlSessionTemplate);
	}

	@Override
	public Integer checkDataInUldMaster(MSSMailBagMovement requestModel) throws CustomException {
		return fetchObject("checkDataInUldMaster", requestModel, sqlSessionTemplate);
	}

	@Override
	public List<String> getFlightBoardPointsForCheckDestination(MSSMailBagMovement requestModel)
			throws CustomException {
		return fetchList("getFlightBoardPointsForCheckDestination", requestModel, sqlSessionTemplate);
	}

	@Override
	public ExportMailBagManifest getPiecesWeight(MSSMailBagMovement piecesWeight) throws CustomException {
		ExportMailBagManifest mail = fetchObject("getPiecesWeightOfInventory", piecesWeight, sqlSessionTemplate);
		return mail;
	}

	@Override
	public Integer checkManifestedIdexistance(ManifestSegment requestModel) throws CustomException {

		return fetchObject("checkManifestedIdexistance", requestModel, sqlSessionTemplate);
	}

	@Override
	public void updateShipmentInventory(MSSMailBagMovement previousLoc, MSSMailBagMovement newLoc)
			throws CustomException {
		if (Optional.ofNullable(previousLoc.getShipmentInventoryId()).isPresent()) {
			super.updateData("updatePrevShipmentInventory", previousLoc, sqlSessionTemplate);
			super.updateData("removePrevHouseInventorySHCs", previousLoc, sqlSessionTemplate);
			super.updateData("removePrevHouseInventry", previousLoc, sqlSessionTemplate);
			super.updateData("removeUnusedPrevShipmentInventory", previousLoc, sqlSessionTemplate);
			int newInventory = super.updateData("updateShipmentInventory", newLoc, sqlSessionTemplate);
			if (newInventory == 0) {
				newLoc.setShipmentId(previousLoc.getShipmentId());
				newLoc.setShipmentHouseId(previousLoc.getShipmentHouseId());
				super.insertData("insertShipmentInventoryForMss", newLoc, sqlSessionTemplate);
				// newLoc.getShipmentInventoryId()
				super.insertData("insertHouseInventoryForMss", newLoc, sqlSessionTemplate);
			}
		}
	}

	@Override
	public BigInteger getShipmentIdForExportAccptMail(MSSMailBagMovement requestModel) throws CustomException {
		return super.fetchObject("getShipmentIdForExportAccptMail", requestModel, sqlSessionTemplate);
	}

	@Override
	public String getStoreLocationType(String shipmentLocation) throws CustomException {
		return super.fetchObject("getStoreLocationType", shipmentLocation, sqlSessionTemplate);
	}

	@Override
	public String getConnectingUrls() throws CustomException {
		return super.fetchObject("getConnectingUrls", null, sqlSessionTemplate);
	}

	@Override
	@Transactional(rollbackFor = CustomException.class)
	public void updateStoreLocationOfShipment(MSSMailBagMovement requestModel) throws CustomException {
		super.updateData("updateStoreLocationOfShipment", requestModel, sqlSessionTemplate);
//		if (super.updateData("updateStoreLocationOfShipment", requestModel, sqlSessionTemplate) == 0) {
//			super.insertData("insertStoreLocationOfShipment", requestModel, sqlSessionTemplate);
//			super.insertData("insertHouseInventoryStoreLocationOfShipment", requestModel, sqlSessionTemplate);
//		}
	}

	@Override
	public void insertXrayData(MSSMailBagMovement requestModel) throws CustomException {
		int checkRecord = fetchObject("checkForScreening", requestModel, sqlSessionTemplate);
		if (checkRecord == 0) {
			super.insertData("insertXRAYData", requestModel, sqlSessionTemplate);
		} else {
			super.updateData("UpdateXRAYData", requestModel, sqlSessionTemplate);
		}

	}

	@Override
	public BigInteger getShipmentId(MSSMailBagMovement requestModel) throws CustomException {
		return fetchObject("getShipmentIdForMBMovement", requestModel, sqlSessionTemplate);

	}

	@Override
	public void bookShipmentInfo(MSSMailBagMovement requestModel) throws CustomException {
		String getOffPoint = super.fetchObject("getFlightOffPointForManifestedFlight", requestModel,
				sqlSessionTemplate);
		if (!StringUtils.isEmpty(getOffPoint)) {
			requestModel.setFlightOffPoint(getOffPoint);
		}

		BigInteger getSegmentId = super.fetchObject("getFlightSegmentIdForManifestedFlight", requestModel,
				sqlSessionTemplate);
		if (Optional.ofNullable(getSegmentId).isPresent()) {
			requestModel.setSegmentId(getSegmentId);
		}

		// get booking Id
		BigInteger getBookingId = super.fetchObject("getBookingId", requestModel, sqlSessionTemplate);
		if (Optional.ofNullable(getBookingId).isPresent()) {
			requestModel.setBookingId(getBookingId);
			super.updateData("updateMailBagBooking", requestModel, sqlSessionTemplate);
			super.updateData("updateBagBookingDetails", requestModel, sqlSessionTemplate);
		} else {
			super.insertData("insertMailBagBooking", requestModel, sqlSessionTemplate);
			super.insertData("insertBagBookingDetails", requestModel, sqlSessionTemplate);
			super.insertData("insertBagBookingDetailsSHC", requestModel, sqlSessionTemplate);
		}

	}

	@Override
	public BigInteger getShipmentIdForMBMovement(MSSMailBagMovement requestModel) throws CustomException {
		return super.fetchObject("getShipmentIdForMBMovement", requestModel, sqlSessionTemplate);
	}

	@Override
	public void updateLocationForMBMovement(MSSMailBagMovement requestModel) throws CustomException {
		super.updateData("updateStoreLocationOfShipment", requestModel, sqlSessionTemplate);
	}

	@Override
	public ULDIInformationDetails getUldConfigDetails(MSSMailBagMovement requestModel) throws CustomException {
		return super.fetchObject("getUldConfigInfo", requestModel, sqlSessionTemplate);
	}

	@Override
	public String getContentCodeOfUld(String containerlocation) throws CustomException {
		return super.fetchObject("getContentCodeOfUld", containerlocation, sqlSessionTemplate);
	}
	
	@Override
	public BigInteger getCustIdForAgent(String CustomerCode) throws CustomException {
		return super.fetchObject("getCustIdForAgent", CustomerCode, sqlSessionTemplate);
	}

	@Override
	public MSSMailBagMovement getULDAndFLightEventsInfo(MSSMailBagMovement requestModel) throws CustomException {
		return super.fetchObject("getULDAndFLightEventsInfo", requestModel, sqlSessionTemplate);
	}
	
	@Override
	public Integer checkIsMailBagAccepted(MSSMailBagMovement requestModel) throws CustomException {
		return super.fetchObject("checkIsMailBagAccepted", requestModel, sqlSessionTemplate);
	}
	
	@Override
	public Integer checkIsMailBagLoaded(MSSMailBagMovement requestModel) throws CustomException {
		return super.fetchObject("checkIsMailBagLoaded", requestModel, sqlSessionTemplate);
	}

	@Override
	public MSSMailBagMovement getULDAndFLightManifestInfo(MSSMailBagMovement requestModel) throws CustomException {
		return super.fetchObject("getULDAndFLightManifestInfo", requestModel, sqlSessionTemplate);
	}

   @Override
   public boolean checkMssIncomingIsActiveOrNot() throws CustomException {
      return super.fetchObject("sqlGetMssIncomingIsActiveOrNot", null, sqlSessionTemplate);
   }
}
