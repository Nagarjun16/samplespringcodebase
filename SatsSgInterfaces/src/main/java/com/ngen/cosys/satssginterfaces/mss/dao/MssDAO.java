package com.ngen.cosys.satssginterfaces.mss.dao;

import java.math.BigInteger;
import java.util.List;

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

public interface MssDAO {
   List<PreannouncementUldMessagesModel> preannouncementUldMessage(RequestPreannouncementUldMessagesModel model)
         throws CustomException;

   List<ResponseErrorMessagesHeaderMss> responseHeader() throws CustomException;

   Integer updateErrorResponseMessageinOutgoingMessage(BigInteger id) throws CustomException;

   RequestPreannouncementUldMessagesModel selectFlightKey(BigInteger flightId) throws CustomException;

   ResponseMssMailBagMovement messageTypeTGFMMBSI(MSSMailBagMovement requestModel) throws CustomException;

   void messageTypeTGFMMBUL(MSSMailBagMovement requestModel) throws CustomException;

   void messageTypeTGFMCULD(MSSMailBagMovement requestModel) throws CustomException;

   BigInteger getIncommingFlightId(MSSMailBagMovement mailBagUldMovementBaseModel) throws CustomException;

   BigInteger getOutgoingFlightId(MSSMailBagMovement mailBagUldMovementBaseModel) throws CustomException;

   void updateLyingList(MSSMailBagMovement mailBagUldMovementBaseModel) throws CustomException;

   List<BuildUpMailSearch> fetchMailbagDetail(MSSMailBagMovement requestModel) throws CustomException;

   BigInteger getFLightSegmentIdForAssignedTrolley(MSSMailBagMovement requestModel) throws CustomException;

   BigInteger getFLightSegmentIdForexportFlight(MSSMailBagMovement requestModel) throws CustomException;

   MSSMailBagMovement getExpFLightEventsInfo(MSSMailBagMovement requestModel) throws CustomException;

   Integer checkDataInUldMaster(MSSMailBagMovement requestModel) throws CustomException;

   List<String> getFlightBoardPointsForCheckDestination(MSSMailBagMovement requestModel) throws CustomException;

   ExportMailBagManifest getPiecesWeight(MSSMailBagMovement updateList) throws CustomException;

   Integer checkManifestedIdexistance(ManifestSegment requestModel) throws CustomException;

   void updateShipmentInventory(MSSMailBagMovement previousLoc, MSSMailBagMovement newLoc) throws CustomException;

   BigInteger getShipmentIdForExportAccptMail(MSSMailBagMovement requestModel) throws CustomException;

   String getStoreLocationType(String shipmentLocation) throws CustomException;

   String getConnectingUrls() throws CustomException;

   void updateStoreLocationOfShipment(MSSMailBagMovement requestModel) throws CustomException ;

   void insertXrayData(MSSMailBagMovement requestModel)throws CustomException;

   BigInteger getShipmentId(MSSMailBagMovement requestModel)throws CustomException;

   void bookShipmentInfo(MSSMailBagMovement requestModel)throws CustomException;

	BigInteger getShipmentIdForMBMovement(MSSMailBagMovement requestModel) throws CustomException;

	void updateLocationForMBMovement(MSSMailBagMovement requestModel) throws CustomException;

	ULDIInformationDetails getUldConfigDetails(MSSMailBagMovement requestModel) throws CustomException;

	String getContentCodeOfUld(String containerlocation) throws CustomException;

	BigInteger getCustIdForAgent(String CustomerCode) throws CustomException;

	MSSMailBagMovement getULDAndFLightEventsInfo(MSSMailBagMovement requestModel) throws CustomException;

	Integer checkIsMailBagAccepted(MSSMailBagMovement requestModel) throws CustomException;

	Integer checkIsMailBagLoaded(MSSMailBagMovement requestModel) throws CustomException;

	MSSMailBagMovement getULDAndFLightManifestInfo(MSSMailBagMovement requestModel) throws CustomException;

    boolean checkMssIncomingIsActiveOrNot() throws CustomException;
}
