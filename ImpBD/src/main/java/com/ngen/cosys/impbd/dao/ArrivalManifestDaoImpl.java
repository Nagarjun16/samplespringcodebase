package com.ngen.cosys.impbd.dao;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.ngen.cosys.audit.NgenAuditAction;
import com.ngen.cosys.audit.NgenAuditEntityType;
import com.ngen.cosys.audit.NgenAuditEventType;
import com.ngen.cosys.events.payload.ManageBookingEvent;
import com.ngen.cosys.framework.constant.Action;
import com.ngen.cosys.framework.dao.BaseDAO;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.impbd.constants.SqlIDs;
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

/**
 * This class takes care of the responsibilities related to the Arrival Manifest
 * DAO operation that comes from the Service.
 * 
 * @author NIIT Technologies Ltd
 *
 */
@Repository
public class ArrivalManifestDaoImpl extends BaseDAO implements ArrivalManifestDao {

   @Autowired
   @Qualifier("sqlSessionTemplate")
   private SqlSession sqlSessionImportAM;

   @Override
   public ArrivalManifestByFlightModel fetchShipmentDetails(SearchArrivalManifestModel searchArrivalManifestModel)
         throws CustomException {
      return fetchObject(SqlIDs.SQL_GET_ARRIVAL_MANIFEST_INFO.toString(), searchArrivalManifestModel,
            sqlSessionImportAM);
   }

   @Override
   public void createShipmentDimension(ArrivalManifestShipmentDimensionInfoModel dimensionData) throws CustomException {
      insertData(SqlIDs.INSERTSHIPMENTDIMENSION.toString(), dimensionData, sqlSessionImportAM);
   }

   @Override
   public Boolean getShipmentOnwardMovement(ArrivalManifestShipmentOnwardMovementModel onwardMvmtData)
         throws CustomException {
      return fetchObject(SqlIDs.GETSHIPMENTONWARDMVMT.toString(), onwardMvmtData, sqlSessionImportAM);
   }

   @Override
   public void createShipmentOnwardMovement(ArrivalManifestShipmentOnwardMovementModel onwardMvmtData)
         throws CustomException {
      insertData(SqlIDs.INSERTSHIPMENTONWARDMVMT.toString(), onwardMvmtData, sqlSessionImportAM);
   }

   @Override
   public void createShipmentOCI(ArrivalManifestShipmentOciModel ociData) throws CustomException {
      insertData(SqlIDs.INSERTSHIPMENTOCI.toString(), ociData, sqlSessionImportAM);
   }

   @Override
   public void updateDensityInformation(ArrivalManifestShipmentInfoModel shpModel) throws CustomException {
      updateData(SqlIDs.UPDATESHIPMENTDENSITY.toString(), shpModel, sqlSessionImportAM);
   }

   @Override
   public void updateShipmentOCI(ArrivalManifestShipmentOciModel ociData) throws CustomException {
      updateData(SqlIDs.UPDATESHIPMENTOCI.toString(), ociData, sqlSessionImportAM);
   }

   @Override
   public void updateShipmentDimension(ArrivalManifestShipmentDimensionInfoModel dimensionData) throws CustomException {
      updateData(SqlIDs.UPDATESHIPMENTDIMENSION.toString(), dimensionData, sqlSessionImportAM);
   }

   @Override
   public void updateShipmentOnwardMovement(ArrivalManifestShipmentOnwardMovementModel onwardMvmtData)
         throws CustomException {
      updateData(SqlIDs.UPDATESHIPMENTONWARD.toString(), onwardMvmtData, sqlSessionImportAM);
   }

   @Override
   public void deleteShipmentDimension(ArrivalManifestShipmentInfoModel dimensionData) throws CustomException {
      deleteData(SqlIDs.DELETESHIPMENTDIMENSION.toString(), dimensionData, sqlSessionImportAM);
   }

   @Override
   public void deleteShipmentOCI(ArrivalManifestShipmentInfoModel ociData) throws CustomException {
      deleteData(SqlIDs.DELETESHIPMENTOCI.toString(), ociData, sqlSessionImportAM);
   }

   @Override
   public void deleteShipmentOnward(ArrivalManifestShipmentInfoModel onwardMvmtData) throws CustomException {
      deleteData(SqlIDs.DELETESHIPMENTONWARD.toString(), onwardMvmtData, sqlSessionImportAM);
   }

   @Override
   public void createULD(ArrivalManifestUldModel uldData) throws CustomException {
      insertData(SqlIDs.INSERTULDINFO.toString(), uldData, sqlSessionImportAM);
   }

   @Override
   @NgenAuditAction(entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.ARRIVAL_MANIFEST)
   public void createShipment(ArrivalManifestShipmentInfoModel shipmentData) throws CustomException {
	  shipmentData.setOriginalAwb(null);
	  shipmentData.setPhotoCopy(null);
	  shipmentData.setBarcodePrintedFlag(null);
      insertData(SqlIDs.INSERTSHIPMENTINFO.toString(), shipmentData, sqlSessionImportAM);
   }

   @Override
   @NgenAuditAction(entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.ARRIVAL_MANIFEST)
   public void updateShipment(ArrivalManifestShipmentInfoModel shipmentData) throws CustomException {
	  shipmentData.setOriginalAwb(null);
	  shipmentData.setPhotoCopy(null);
	  shipmentData.setBarcodePrintedFlag(null);
      updateData(SqlIDs.UPDATESHIPMENTINFO.toString(), shipmentData, sqlSessionImportAM);
   }

   @Override
   public void updateULD(ArrivalManifestUldModel uldData) throws CustomException {
      updateData(SqlIDs.UPDATEULDINFO.toString(), uldData, sqlSessionImportAM);
   }

   @Override
   public void createSHC(ArrivalManifestByShipmentShcModel shcData) throws CustomException {
      insertData(SqlIDs.INSERTSHCINFO.toString(), shcData, sqlSessionImportAM);
   }

   @Override
   public void updateSHC(ArrivalManifestByShipmentShcModel shcData) throws CustomException {
      updateData(SqlIDs.UPDATESHCINFO.toString(), shcData, sqlSessionImportAM);
   }

   @Override
   public void deleteULD(ArrivalManifestUldModel uldData) throws CustomException {
      deleteData(SqlIDs.DELETEULDINFO.toString(), uldData, sqlSessionImportAM);
   }

   @Override
   @NgenAuditAction(entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.ARRIVAL_MANIFEST)
   public void deleteShipment(ArrivalManifestShipmentInfoModel shipmentData) throws CustomException {
	  shipmentData.setOriginalAwb(null);
	  shipmentData.setPhotoCopy(null);
	  shipmentData.setBarcodePrintedFlag(null); 
      deleteData(SqlIDs.DELETESHIPMENTINFO.toString(), shipmentData, sqlSessionImportAM);
   }

   @Override
   public void deleteSHC(ArrivalManifestShipmentInfoModel shcData) throws CustomException {
      deleteData(SqlIDs.DELETESHCINFO.toString(), shcData, sqlSessionImportAM);
   }

   @Override
   public void createShipmentOSI(ArrivalManifestOtherServiceInfoModel ociModelData) throws CustomException {
      insertData(SqlIDs.INSERTOSIINFO.toString(), ociModelData, sqlSessionImportAM);
   }

   @Override
   public void updateShipmentOSI(ArrivalManifestOtherServiceInfoModel ociModelData) throws CustomException {
      updateData(SqlIDs.UPDATEOSIINFO.toString(), ociModelData, sqlSessionImportAM);
   }

   @Override
   public void deleteOSI(ArrivalManifestShipmentInfoModel ociModelData) throws CustomException {
      deleteData(SqlIDs.DELETEOSIINFO.toString(), ociModelData, sqlSessionImportAM);
   }

   @Override
   public List<ArrivalManifestByFlightModel> checkArrivalManifestFlight(ArrivalManifestByFlightModel flightInfo)
         throws CustomException {
      return fetchList(SqlIDs.CHECKARRIVALMANIFESTFLIGHT.toString(), flightInfo, sqlSessionImportAM);
   }

   @Override
   public List<ArrivalManifestBySegmentModel> checkArrivalManifestSegment(ArrivalManifestBySegmentModel segmentInfo)
         throws CustomException {
      return fetchList(SqlIDs.CHECKARRIVALMANIFESTSEGMENT.toString(), segmentInfo, sqlSessionImportAM);
   }

   @Override
   public void createArrivalManifestFlight(ArrivalManifestByFlightModel flightInfo) throws CustomException {
      insertData(SqlIDs.INSERTARRIVALMANIFESTFLIGHT.toString(), flightInfo, sqlSessionImportAM);
   }

   @Override
   public void createArrivalManifestSegment(ArrivalManifestBySegmentModel flightInfo) throws CustomException {
      insertData(SqlIDs.INSERTARRIVALMANIFESTSEGMENT.toString(), flightInfo, sqlSessionImportAM);
   }

   @Override
   public List<ArrivalManifestUldModel> checkArrivalManifestUld(ArrivalManifestUldModel uldInfo)
         throws CustomException {
      return fetchList(SqlIDs.CHECKARRIVALMANIFESTULD.toString(), uldInfo, sqlSessionImportAM);
   }

   @Override
   public List<ArrivalManifestShipmentInfoModel> checkArrivalManifestShipment(
         ArrivalManifestShipmentInfoModel shipmentInfo) throws CustomException {
      return fetchList(SqlIDs.CHECKARRIVALMANIFESTSHIPMENT.toString(), shipmentInfo, sqlSessionImportAM);
   }

   @Override
   public String fetchFlightStatus(ArrivalManifestByFlightModel flightInfo) throws CustomException {
      ArrivalManifestByFlightModel flightData = fetchObject(SqlIDs.FETCHFLIGHTSTATUS.toString(), flightInfo,
            sqlSessionImportAM);

      if (Objects.nonNull(flightData) && !StringUtils.isEmpty(flightData.getFlightStatus())) {
         flightData.getFlightStatus().concat("At " + flightData.getFlightDate().toString());
         return flightData.getFlightStatus();
      }
      return null;
   }

   @Override
   public Boolean checkManualShipment(CargoPreAnnouncement preannounceMentData) throws CustomException {
      Integer a = fetchObject(SqlIDs.CHECKMANUALSHIPMENT.toString(), preannounceMentData, sqlSessionImportAM);
      return a > 0;
   }

   @Override
   public boolean checkOutBoundFlight(ArrivalManifestShipmentOnwardMovementModel flightKey) throws CustomException {
      Integer count = fetchObject(SqlIDs.CHECKOUTBOUNDFLIGHT.toString(), flightKey, sqlSessionImportAM);
      if (count > 0) {
         return true;
      }
      return false;
   }

   @Override
   public void ociDelete(ArrivalManifestShipmentOciModel ociModelData) throws CustomException {
      deleteData(SqlIDs.OCIDELETE.toString(), ociModelData, sqlSessionImportAM);
   }

   @Override
   public void dimensionDelete(ArrivalManifestShipmentDimensionInfoModel dimensionData) throws CustomException {
      deleteData(SqlIDs.DIMENSIONDELETE.toString(), dimensionData, sqlSessionImportAM);
   }

   @Override
   public void onwardDelete(ArrivalManifestShipmentOnwardMovementModel onwardData) throws CustomException {
      deleteData(SqlIDs.ONWARDDELETE.toString(), onwardData, sqlSessionImportAM);
   }

   @Override
   public boolean checkOnwardDetails(ArrivalManifestShipmentOnwardMovementModel onwardData) throws CustomException {
      Integer count = fetchObject(SqlIDs.CHECKONWARDMOVEMENT.toString(), onwardData, sqlSessionImportAM);
      if (count > 0) {
         return true;
      }
      return false;

   }

   @Override
   public void updateShipmentOnwardFlightDetails(ArrivalManifestShipmentOnwardMovementModel onwardModel)
         throws CustomException {
      updateData(SqlIDs.UPDATEONWARDFLIGHT.toString(), onwardModel, sqlSessionImportAM);

   }

   @Override
   public void createPreannounceMent(CargoPreAnnouncement preannounceMentData) throws CustomException {
      updateData(SqlIDs.UPDATE_PREANNOUNCEMENT_BULK.toString(), preannounceMentData, sqlSessionImportAM);
      insertData(SqlIDs.INSERT_PREANNOUNCEMENT.toString(), preannounceMentData, sqlSessionImportAM);
   }

   @Override
   public void updateBulkIndicator(ArrivalManifestByFlightModel flghtModel) throws CustomException {
      updateData(SqlIDs.UPDATE_PREANNOUNCEMENT_BULK.toString(), flghtModel, sqlSessionImportAM);
   }

   @Override
   public CargoPreAnnouncement checkPreannounceMent(CargoPreAnnouncement preannounceMentData) throws CustomException {
      return fetchObject(SqlIDs.CHECK_PREANNOUNCEMENT.toString(), preannounceMentData, sqlSessionImportAM);
   }

   @Override
   public void updatePreannounceMent(CargoPreAnnouncement preannounceMentData) throws CustomException {
      updateData(SqlIDs.UPDATE_PREANNOUNCEMENT_BULK.toString(), preannounceMentData, sqlSessionImportAM);
      updateData(SqlIDs.UPDATE_CARGO_PREANNOUNCEMENT_MANIFEST.toString(), preannounceMentData, sqlSessionImportAM);
   }

   @Override
   public boolean checkBookingExists(ArrivalManifestShipmentInfoModel shipmentInfo) throws CustomException {
      Integer result = fetchObject(SqlIDs.CHECK_BOOKING_EXISTS.toString(), shipmentInfo, sqlSessionImportAM);
      return result > 0;
   }

   @Override
   public boolean checkSameBookingExists(ArrivalManifestShipmentInfoModel shipmentInfo) throws CustomException {
      Integer result = fetchObject(SqlIDs.CHECK_SAME_BOOKING_EXISTS.toString(), shipmentInfo, sqlSessionImportAM);
      return result > 0;
   }

   @Override
   public ArrivalManifestUldModel fetchSegmentID(ArrivalManifestUldModel uldData) throws CustomException {
      return fetchObject(SqlIDs.FETCH_SEGMENT_ID.toString(), uldData, sqlSessionImportAM);
   }

   @Override
   public void insertRamCheckIn(CargoPreAnnouncement cargoPreAnnouncement) throws CustomException {
      insertData(SqlIDs.INSERT_CARGOPREANNOUNCEMENT_RAMCHECKIN.toString(), cargoPreAnnouncement, sqlSessionImportAM);
   }

   @Override
   public boolean isRampcheckinExist(CargoPreAnnouncement cargoPreAnnouncement) throws CustomException {
      boolean flag = false;
      BigInteger count = this.fetchObject(SqlIDs.IS_EXIST_RAMPCHECKIN.toString(), cargoPreAnnouncement,
            sqlSessionImportAM);
      if (count != null) {
         flag = true;
      }
      return flag;
   }

   @Override
   public ArrivalManifestBySegmentModel fetchSegmentDetails(ArrivalManifestByFlightModel uldData)
         throws CustomException {
      return fetchObject(SqlIDs.FETCH_OPERATIVE_SEGMENTS.toString(), uldData, sqlSessionImportAM);
   }

   @Override
   public void updateBulkShipmentIndicator(ArrivalManifestByFlightModel uldData) throws CustomException {
      updateData(SqlIDs.UPDATE_BULK_INDICATOR.toString(), uldData, sqlSessionImportAM);
   }

   @Override
   public boolean isDocumentReceived(ArrivalManifestByFlightModel uldData) throws CustomException {
      return fetchObject("sqlCheckInboundFlightStatus", uldData, sqlSessionImportAM);
   }

   @Override
   public ArrivalManifestShipmentInfoModel fetchBooking(ArrivalManifestShipmentInfoModel shipmentInfo)
         throws CustomException {
      return fetchObject("fetchBookingExists", shipmentInfo, sqlSessionImportAM);
   }

   @Override
   public RampCheckInUld fetchRampCheckInDetails(CargoPreAnnouncement cargoPreAnnouncement) throws CustomException {
      return fetchObject("fetchRampCheckInDetails", cargoPreAnnouncement, sqlSessionImportAM);
   }

   @Override
   public boolean transhipmentPlanningExists(ArrivalManifestShipmentInfoModel shipmentData) throws CustomException {
      return fetchObject("sqlCheckTranshipmentPlanningExists", shipmentData, sqlSessionImportAM);
   }

   @Override
   public void updateServiceIndicator(ArrivalManifestShipmentInfoModel shpModel) throws CustomException {
      // update serviceIndiator in ExportShipmentBooking
      this.updateData(SqlIDs.UPDATE_SERVICE_FLAG_BOOKING_EXISTS.toString(), shpModel, sqlSessionImportAM);
   }

   @Override
   public ManageBookingEvent getShipmentPieces(ArrivalManifestShipmentInfoModel shpModel) throws CustomException {
      return fetchObject("sqlGetShipmentPieces", shpModel, sqlSessionImportAM);
   }

   @Override
   public boolean checkValidFlight(ArrivalManifestByFlightModel flightData) throws CustomException {
      return fetchObject("sqlCheckValidArrivalFlight", flightData, sqlSessionImportAM);
   }

   @Override
   public void deleteShipmentMasters(ShipmentMaster shipmentData) throws CustomException {
      deleteData("deleteShipmentMasters", shipmentData, sqlSessionImportAM);
   }

   @Override
   public boolean documentReceived(ShipmentMaster shipmentData) throws CustomException {
      return fetchObject("sqlCheckShipmentDocumentReceivedOn", shipmentData, sqlSessionImportAM);
   }

   @Override
   public boolean checkAssignedULD(ArrivalManifestUldModel uldData) throws CustomException {
      if (!Action.CREATE.toString().equalsIgnoreCase(uldData.getFlagCRUD())) {
         return false;
      } else {
         return fetchObject("sqlCheckUldAssignments", uldData, sqlSessionImportAM);
      }
   }

   @Override
   public void updateNilCargo(ArrivalManifestBySegmentModel segmentData) throws CustomException {
      updateData("updateNilCargo", segmentData, sqlSessionImportAM);
   }

   @Override
   public boolean checkSHCGrouping(ArrivalManifestShipmentInfoModel flightData) throws CustomException {
      return fetchObject("sqlCheckSHCGrouping", flightData, sqlSessionImportAM);
   }

   @Override
   public ArrivalManifestShipmentInfoModel fetchShipmentPieces(ArrivalManifestShipmentInfoModel shpModel)
         throws CustomException {
      return fetchObject("sqlFetchShipmentPieces", shpModel, sqlSessionImportAM);
   }

   @Override
   public String fetchSHCGroupHandlingCode(ArrivalManifestUldModel uldData) throws CustomException {
      return fetchObject("sqlGetArrivalManifestSHCHandlingGroupCode", uldData, sqlSessionImportAM);
   }

   @Override
   public List<ArrivalManifestShipmentInfoModel> fetchNotBookedTranshipments(ArrivalManifestByFlightModel shpData)
         throws CustomException {
      return fetchList("sqlGetNotBookedShipments", shpData, sqlSessionImportAM);
   }

   @Override
   public void updateNextDestination(ArrivalManifestShipmentInfoModel shipmentInfo) throws CustomException {
      updateData("updateOnwardNextDestination", shipmentInfo, sqlSessionImportAM);
   }

   @Override
   public BigInteger getSegmentLevelULDCount(ArrivalManifestBySegmentModel segmentData) throws CustomException {
      return fetchObject("sqlGetSegmentLevelULDCount", segmentData, sqlSessionImportAM);
   }

   @Override
   public boolean isDocumentReceivedStatus(ShipmentMaster shipmentData) throws CustomException {
      return fetchObject("sqlGetArrivalManifestShipmentMasterDocumentReceivedStatus", shipmentData, sqlSessionImportAM);
   }

   /*
    * (non-Javadoc)
    * 
    * @see
    * com.ngen.cosys.impbd.dao.ArrivalManifestDao#getThroughServiceShipmentInfo(com
    * .ngen.cosys.impbd.model.ArrivalManifestByFlightModel, java.util.Set)
    */
   @Override
   public List<ArrivalManifestShipmentInfoModel> getThroughServiceShipmentInfo(
         ArrivalManifestByFlightModel requestModel, Set<String> distinctShipmentNumbers) throws CustomException {

      // Set the parameter map
      Map<String, Object> parameterMap = new HashMap<>();
      parameterMap.put("flightId", requestModel.getFlightId());
      parameterMap.put("shipmentNumbers", distinctShipmentNumbers);

      return this.fetchList("sqlGetThroughTransitAdviceShipmentsInfo", parameterMap, sqlSessionImportAM);
   }

   /*
    * (non-Javadoc)
    * 
    * @see
    * com.ngen.cosys.impbd.dao.ArrivalManifestDao#getArrivalManifestShipments(com.
    * ngen.cosys.transfertype.model.InboundFlightModel)
    */
   @Override
   public List<InboundFlightModel> getArrivalManifestShipments(InboundFlightModel obj) throws CustomException {
      return this.fetchList("sqlGetArrivalManifestTransferType_SSB", obj, sqlSessionImportAM);
   }

   /*
    * (non-Javadoc)
    * 
    * @see com.ngen.cosys.impbd.dao.ArrivalManifestDao#
    * clearBookingInfoFromArrivalManifestByFlight(com.ngen.cosys.transfertype.model
    * .InboundFlightModel)
    */
   @Override
   public void clearBookingInfoFromArrivalManifestByFlight(InboundFlightModel obj) throws CustomException {
      this.updateData("sqlClearBookingInfoFromArrivalManifestByFlight", obj, sqlSessionImportAM);
   }

   /*
    * (non-Javadoc)
    * 
    * @see com.ngen.cosys.impbd.dao.ArrivalManifestDao#
    * updateTransferTypeInArrivalManifestByShipmentInfoId(com.ngen.cosys.
    * transfertype.model.InboundFlightModel)
    */
   @Override
   public void updateTransferTypeInArrivalManifestByShipmentInfoId(InboundFlightModel obj) throws CustomException {
      this.updateData("updateArrivalManifestTransferTypeBooking", obj, sqlSessionImportAM);
   }

   /*
    * (non-Javadoc)
    * 
    * @see com.ngen.cosys.impbd.dao.ArrivalManifestDao#
    * updateTransferTypeInArrivalManifestByShipmentNumber(com.ngen.cosys.
    * transfertype.model.InboundFlightModel)
    */
   @Override
   public void updateTransferTypeInArrivalManifestByShipmentNumber(InboundFlightModel obj) throws CustomException {
      this.updateData("sqlUpdateTransferTypeArrivalManifestByShipmentNumber", obj, sqlSessionImportAM);
   }

   /*
    * (non-Javadoc)
    * 
    * @see
    * com.ngen.cosys.impbd.dao.ArrivalManifestDao#updatePartBookingPiecesWeight(com
    * .ngen.cosys.transfertype.model.InboundFlightModel)
    */
   @Override
   public void updatePartBookingPiecesWeight(InboundFlightModel obj) throws CustomException {

      // Update the part pieces/weight
      this.updateData("sqlUpdateBookingPartPiecesWeightFromArrivalManifest", obj, sqlSessionImportAM);

      // Reduce the part pieces/weight from Flight Booking Info specific to part
      this.updateData("sqlReduceOldBookingFlightPiecesWeightFromArrivalManifest", obj, sqlSessionImportAM);

      // Increase the pieces/weight from Flight Booking Info specific to part
      this.updateData("sqlIncreaseNewBookingFlightPiecesWeightFromArrivalManifest", obj, sqlSessionImportAM);

      // Update total booking piece/weight
      this.updateData("sqlUpdateTotalBookingPiecesWeightFromArrivalManifest", obj, sqlSessionImportAM);

   }

   /*
    * (non-Javadoc)
    * 
    * @see
    * com.ngen.cosys.impbd.dao.ArrivalManifestDao#deleteThrougServiceInfo(java.math
    * .BigInteger, java.util.List, java.util.List)
    */
   @Override
   public void deleteThrougServiceInfo(BigInteger flightId, List<ArrivalManifestUldModel> deletedArrivalManifestULDs,
         List<ArrivalManifestShipmentInfoModel> deletedDistinctManifestedTransshipments) throws CustomException {

      // Delete the ULD
      if (!CollectionUtils.isEmpty(deletedArrivalManifestULDs)) {
         Map<String, Object> parameterMap = new HashMap<>();

         for (ArrivalManifestUldModel t : deletedArrivalManifestULDs) {

            // Clear the map
            parameterMap.clear();

            parameterMap.put("flightId", flightId);
            parameterMap.put("uldKey", t.getUldNumber());

            Boolean isAdviceSent = this.fetchObject("sqlGetThroughServiceSentForArrivalManifestByULD", parameterMap,
                  sqlSessionImportAM);

            if (!ObjectUtils.isEmpty(isAdviceSent) && isAdviceSent) {
               // Do nothing
            } else {
               // Delete the ULD/Shipments
               this.deleteData("sqlDeleteThroughServiceShipmentSHCforArrivalManifest", parameterMap,
                     sqlSessionImportAM);
               this.deleteData("sqlDeleteThroughServiceShipmentforArrivalManifest", parameterMap, sqlSessionImportAM);

               this.deleteData("sqlDeleteThroughServiceULDPiggyBackforArrivalManifest", parameterMap,
                     sqlSessionImportAM);
               this.deleteData("sqlDeleteThroughServiceULDSHCforArrivalManifest", parameterMap, sqlSessionImportAM);
               this.deleteData("sqlDeleteThroughServiceULDforArrivalManifest", parameterMap, sqlSessionImportAM);
            }
         }
      }

      // Delete the ULD
      if (!CollectionUtils.isEmpty(deletedDistinctManifestedTransshipments)) {
         Map<String, Object> parameterMap = new HashMap<>();

         for (ArrivalManifestShipmentInfoModel t : deletedDistinctManifestedTransshipments) {
            // Clear the map
            parameterMap.clear();

            parameterMap.put("flightId", flightId);
            parameterMap.put("shipmentNumber", t.getShipmentNumber());
            parameterMap.put("uldNumber", t.getUldNumber());
            parameterMap.put("shipmentDate", t.getShipmentdate());

            Boolean isAdviceSent = this.fetchObject("sqlGetThroughServiceSentForArrivalManifestByShipment",
                  parameterMap, sqlSessionImportAM);

            if (!ObjectUtils.isEmpty(isAdviceSent) && isAdviceSent) {
               // Do nothing
            } else {
               // Delete the Shipments
               this.deleteData("sqlDeleteThroughServiceShipmentSHCforArrivalManifestByShipment", parameterMap,
                     sqlSessionImportAM);
               this.deleteData("sqlDeleteThroughServiceShipmentforArrivalManifestByShipment", parameterMap,
                     sqlSessionImportAM);
            }
         }
      }
   }

   @Override
   public boolean isImportTransShipment(ArrivalManifestShipmentInfoModel shipmentValidatorModel)
         throws CustomException {

      return this.fetchObject("sqlQueryCheckImportTranShipmentAM", shipmentValidatorModel, sqlSessionImportAM);
   }

   @Override
   public List<ArrivalManifestShipmentInfoModel> getUldShipmentList(ArrivalManifestUldModel uldInfo)
         throws CustomException {
      List<ArrivalManifestShipmentInfoModel> shipmentList = this.fetchList("sqlGetArrivalManifestULDShipmentInfoList",
            uldInfo, sqlSessionImportAM);
      return shipmentList;
   }

   @Override
   public List<String> getUldShipmentSHCList(ArrivalManifestUldModel uldInfo) throws CustomException {

      return this.fetchList("sqlGetArrivalManifestULDShipmentInfoSHCList", uldInfo, sqlSessionImportAM);
   }
   
   @Override
   public Boolean validatateAWBDocInvFreightOutInfo(ShipmentMaster shipmentValidatorModel)
         throws CustomException {

      return this.fetchObject("sqlValidateAWBDocInvFreightInfo", shipmentValidatorModel, sqlSessionImportAM);
   }

}