package com.ngen.cosys.impbd.dao;

import java.math.BigInteger;
import java.util.List;
import java.util.Set;

import com.ngen.cosys.events.payload.ManageBookingEvent;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.impbd.model.ArrivalManifestByFlightModel;
import com.ngen.cosys.impbd.model.ArrivalManifestBySegmentModel;
import com.ngen.cosys.impbd.model.ArrivalManifestByShipmentShcModel;
import com.ngen.cosys.impbd.model.ArrivalManifestOtherServiceInfoModel;
import com.ngen.cosys.impbd.model.ArrivalManifestShipmentDimensionInfoModel;
import com.ngen.cosys.impbd.model.ArrivalManifestShipmentInfoModel;
import com.ngen.cosys.impbd.model.ArrivalManifestShipmentOciModel;
import com.ngen.cosys.impbd.model.ArrivalManifestShipmentOnwardMovementModel;
import com.ngen.cosys.impbd.model.ArrivalManifestUldModel;
import com.ngen.cosys.impbd.model.CargoPreAnnouncement;
import com.ngen.cosys.impbd.model.RampCheckInUld;
import com.ngen.cosys.impbd.model.SearchArrivalManifestModel;
import com.ngen.cosys.impbd.shipment.document.model.ShipmentMaster;
import com.ngen.cosys.transfertype.model.InboundFlightModel;

public interface ArrivalManifestDao {

   public ArrivalManifestByFlightModel fetchShipmentDetails(SearchArrivalManifestModel searchArrivalManifestModel)
         throws CustomException;

   public void createShipmentDimension(ArrivalManifestShipmentDimensionInfoModel dimensionData) throws CustomException;

   public void createShipmentOnwardMovement(ArrivalManifestShipmentOnwardMovementModel onwardModel)
         throws CustomException;

   public Boolean getShipmentOnwardMovement(ArrivalManifestShipmentOnwardMovementModel onwardModel)
         throws CustomException;

   public void createShipmentOCI(ArrivalManifestShipmentOciModel ociModelData) throws CustomException;

   public void updateDensityInformation(ArrivalManifestShipmentInfoModel shpModel) throws CustomException;

   public void updateShipmentOCI(ArrivalManifestShipmentOciModel ociModelData) throws CustomException;

   public void updateShipmentDimension(ArrivalManifestShipmentDimensionInfoModel dimensionData) throws CustomException;

   public void updateShipmentOnwardMovement(ArrivalManifestShipmentOnwardMovementModel onwardModel)
         throws CustomException;

   public void deleteShipmentDimension(ArrivalManifestShipmentInfoModel dimensionData) throws CustomException;

   public void deleteShipmentOCI(ArrivalManifestShipmentInfoModel ociModelData) throws CustomException;

   public void deleteShipmentOnward(ArrivalManifestShipmentInfoModel onwardModel) throws CustomException;

   public void createULD(ArrivalManifestUldModel uldModelData) throws CustomException;

   public void createShipment(ArrivalManifestShipmentInfoModel shpModel) throws CustomException;

   public void updateShipment(ArrivalManifestShipmentInfoModel shpModel) throws CustomException;

   public void updateULD(ArrivalManifestUldModel uldModelData) throws CustomException;

   public void createSHC(ArrivalManifestByShipmentShcModel shcModel) throws CustomException;

   public void updateSHC(ArrivalManifestByShipmentShcModel shcModel) throws CustomException;

   public void deleteULD(ArrivalManifestUldModel uldModelData) throws CustomException;

   public void deleteShipment(ArrivalManifestShipmentInfoModel shpModel) throws CustomException;

   public void deleteSHC(ArrivalManifestShipmentInfoModel shcModel) throws CustomException;

   public void createShipmentOSI(ArrivalManifestOtherServiceInfoModel ociModelData) throws CustomException;

   public void updateShipmentOSI(ArrivalManifestOtherServiceInfoModel ociModelData) throws CustomException;

   public void deleteOSI(ArrivalManifestShipmentInfoModel ociModelData) throws CustomException;

   public List<ArrivalManifestByFlightModel> checkArrivalManifestFlight(ArrivalManifestByFlightModel flightInfo)
         throws CustomException;

   public List<ArrivalManifestBySegmentModel> checkArrivalManifestSegment(ArrivalManifestBySegmentModel flightInfo)
         throws CustomException;

   public List<ArrivalManifestUldModel> checkArrivalManifestUld(ArrivalManifestUldModel uldInfo) throws CustomException;

   public void createArrivalManifestFlight(ArrivalManifestByFlightModel flightInfo) throws CustomException;

   public void createArrivalManifestSegment(ArrivalManifestBySegmentModel flightInfo) throws CustomException;

   public List<ArrivalManifestShipmentInfoModel> checkArrivalManifestShipment(
         ArrivalManifestShipmentInfoModel shipmentInfo) throws CustomException;

   public String fetchFlightStatus(ArrivalManifestByFlightModel flightInfo) throws CustomException;

   public Boolean checkManualShipment(CargoPreAnnouncement preannounceMentData) throws CustomException;

   public boolean checkOutBoundFlight(ArrivalManifestShipmentOnwardMovementModel onwardData) throws CustomException;

   public void ociDelete(ArrivalManifestShipmentOciModel ociModelData) throws CustomException;

   public void dimensionDelete(ArrivalManifestShipmentDimensionInfoModel dimensionData) throws CustomException;

   public void onwardDelete(ArrivalManifestShipmentOnwardMovementModel onwardData) throws CustomException;

   public boolean checkOnwardDetails(ArrivalManifestShipmentOnwardMovementModel onwardData) throws CustomException;

   public void updateShipmentOnwardFlightDetails(ArrivalManifestShipmentOnwardMovementModel onwardModel)
         throws CustomException;

   public void createPreannounceMent(CargoPreAnnouncement preannounceMentData) throws CustomException;
   
   public void updateBulkIndicator(ArrivalManifestByFlightModel flghtModel) throws CustomException;

   public CargoPreAnnouncement checkPreannounceMent(CargoPreAnnouncement preannounceMentData) throws CustomException;

   public void updatePreannounceMent(CargoPreAnnouncement preannounceMentData) throws CustomException;

   public boolean checkBookingExists(ArrivalManifestShipmentInfoModel shipmentInfo) throws CustomException;

   public boolean checkSameBookingExists(ArrivalManifestShipmentInfoModel shipmentInfo) throws CustomException;

   public ArrivalManifestUldModel fetchSegmentID(ArrivalManifestUldModel uldData) throws CustomException;

   void insertRamCheckIn(CargoPreAnnouncement cargoPreAnnouncement) throws CustomException;

   boolean isRampcheckinExist(CargoPreAnnouncement cargoPreAnnouncement) throws CustomException;

   public ArrivalManifestBySegmentModel fetchSegmentDetails(ArrivalManifestByFlightModel uldData)
         throws CustomException;

   public void updateBulkShipmentIndicator(ArrivalManifestByFlightModel uldData) throws CustomException;

   /**
    * Method to check document received for an consignment or not
    * 
    * @param consignmentDetail
    * @return boolean - true if completed OR false if not completed
    * @throws CustomException
    */
   boolean isDocumentReceived(ArrivalManifestByFlightModel uldData) throws CustomException;

   public ArrivalManifestShipmentInfoModel fetchBooking(ArrivalManifestShipmentInfoModel shipmentInfo)
         throws CustomException;

   public RampCheckInUld fetchRampCheckInDetails(CargoPreAnnouncement cargoPreAnnouncement) throws CustomException;

   public boolean transhipmentPlanningExists(ArrivalManifestShipmentInfoModel shipmentData) throws CustomException;

   ManageBookingEvent getShipmentPieces(ArrivalManifestShipmentInfoModel shpModel) throws CustomException;

   void updateServiceIndicator(ArrivalManifestShipmentInfoModel model) throws CustomException;

   boolean checkValidFlight(ArrivalManifestByFlightModel flightData) throws CustomException;

   void deleteShipmentMasters(ShipmentMaster shipmentData) throws CustomException;

   boolean documentReceived(ShipmentMaster shipmentData) throws CustomException;

   boolean checkAssignedULD(ArrivalManifestUldModel uldData) throws CustomException;

   void updateNilCargo(ArrivalManifestBySegmentModel segmentData) throws CustomException;

   boolean checkSHCGrouping(ArrivalManifestShipmentInfoModel flightData) throws CustomException;

   ArrivalManifestShipmentInfoModel fetchShipmentPieces(ArrivalManifestShipmentInfoModel shpModel)
         throws CustomException;

   String fetchSHCGroupHandlingCode(ArrivalManifestUldModel uldData) throws CustomException;

   List<ArrivalManifestShipmentInfoModel> fetchNotBookedTranshipments(ArrivalManifestByFlightModel shpData)
         throws CustomException;

   public void updateNextDestination(ArrivalManifestShipmentInfoModel shipmentModel) throws CustomException;

   public BigInteger getSegmentLevelULDCount(ArrivalManifestBySegmentModel segmentData) throws CustomException;

   boolean isDocumentReceivedStatus(ShipmentMaster shipmentData) throws CustomException;

   /**
    * Method to get through service shipment info
    * 
    * @param requestModel
    * @param distinctShipmentNumbers
    *           - List of shipments which are undergone change
    * @return List<ArrivalManifestShipmentInfoModel> - List of shipments whose
    *         transfer type is Through Transit
    * @throws CustomException
    */
   List<ArrivalManifestShipmentInfoModel> getThroughServiceShipmentInfo(ArrivalManifestByFlightModel requestModel,
         Set<String> distinctShipmentNumbers) throws CustomException;

   /**
    * Method to clear the manifest booking information
    * 
    * @param obj
    * @throws CustomException
    */
   void clearBookingInfoFromArrivalManifestByFlight(InboundFlightModel obj) throws CustomException;

   /**
    * Method to update booking flight pieces/weight and part pieces/weight
    * 
    * @param obj
    * @throws CustomException
    */
   void updatePartBookingPiecesWeight(InboundFlightModel obj) throws CustomException;

   /**
    * Method to update booking information based on shipment id and flight id
    * 
    * @param obj
    * @throws CustomException
    */
   void updateTransferTypeInArrivalManifestByShipmentInfoId(InboundFlightModel obj) throws CustomException;

   /**
    * Method to update Booking Info and Transfer Type based on Shipment Number and
    * Flight Id
    * 
    * @param obj
    * @throws CustomException
    */
   void updateTransferTypeInArrivalManifestByShipmentNumber(InboundFlightModel obj) throws CustomException;

   /**
    * Method to get list of manifested shipment and it's piece information
    * 
    * @param obj
    * @return List<InboundFlightModel> - List of shipment line items
    * @throws CustomException
    */
   List<InboundFlightModel> getArrivalManifestShipments(InboundFlightModel obj) throws CustomException;

   /**
    * Method to delete through service information for a given shipment/ULD
    * 
    * @param deletedDistinctManifestedTransshipments
    * @throws CustomException
    */
   void deleteThrougServiceInfo(BigInteger flightId, List<ArrivalManifestUldModel> deletedArrivalManifestULDs,
         List<ArrivalManifestShipmentInfoModel> deletedDistinctManifestedTransshipments) throws CustomException;
   
   /**
    * 
    * @param ArrivalManifestShipmentInfoModel
    * @return
    */
   boolean isImportTransShipment(ArrivalManifestShipmentInfoModel reuseShipmentValidatorModel) throws CustomException;
   
   /**
    * 
    * @param uldInfo
    * @return list of shipments assigned to ULD
    * @throws CustomException
    */
   List<ArrivalManifestShipmentInfoModel> getUldShipmentList(ArrivalManifestUldModel uldInfo)throws CustomException;
   
   /**
    * 
    * @param SHC Info
    * @return
    * @throws CustomException
    */
   List<String> getUldShipmentSHCList(ArrivalManifestUldModel uldInfo)throws CustomException;
   
   /**
    * 
    * @param shipmentData
    * @return
    * @throws CustomException
    */
   Boolean validatateAWBDocInvFreightOutInfo(ShipmentMaster shipmentData)throws CustomException;
   
}