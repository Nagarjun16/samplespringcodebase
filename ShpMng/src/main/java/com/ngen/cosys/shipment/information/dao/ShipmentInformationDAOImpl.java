package com.ngen.cosys.shipment.information.dao;

import java.math.BigInteger;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.ngen.cosys.audit.NgenAuditAction;
import com.ngen.cosys.audit.NgenAuditEntityType;
import com.ngen.cosys.audit.NgenAuditEventType;
import com.ngen.cosys.events.payload.ShipmentCancellationStoreEvent;
import com.ngen.cosys.framework.constant.ErrorType;
import com.ngen.cosys.framework.dao.BaseDAO;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.multi.tenancy.constants.BeanFactoryConstants;
import com.ngen.cosys.multitenancy.config.MultiTenantDataSourceConnectionProperties;
import com.ngen.cosys.multitenancy.context.TenantContext;
import com.ngen.cosys.multitenancy.feature.model.ApplicationFeatures;
import com.ngen.cosys.multitenancy.feature.support.FeatureUtility;
import com.ngen.cosys.multitenancy.model.DbInstanceModel;
import com.ngen.cosys.multitenancy.model.TenantDbConnectionModel;
import com.ngen.cosys.shipment.awb.model.AWBPrintRequest;
import com.ngen.cosys.shipment.awb.model.ShipmentRemarksModel;
import com.ngen.cosys.shipment.enums.ShipmentInformationQueryIds;
import com.ngen.cosys.shipment.information.model.DimensionDetails;
import com.ngen.cosys.shipment.information.model.DimensionInfoModel;
import com.ngen.cosys.shipment.information.model.EpouchFileModel;
import com.ngen.cosys.shipment.information.model.HandoverLocation;
import com.ngen.cosys.shipment.information.model.ShipmentAcceptanceModel;
import com.ngen.cosys.shipment.information.model.ShipmentAndBookingDetails;
import com.ngen.cosys.shipment.information.model.ShipmentDamageInfoModel;
import com.ngen.cosys.shipment.information.model.ShipmentDeliveryModel;
import com.ngen.cosys.shipment.information.model.ShipmentFlightModel;
import com.ngen.cosys.shipment.information.model.ShipmentFreightOutInfoModel;
import com.ngen.cosys.shipment.information.model.ShipmentHouseInfoModel;
import com.ngen.cosys.shipment.information.model.ShipmentHouseInfoSummaryModel;
import com.ngen.cosys.shipment.information.model.ShipmentInfoMessageModel;
import com.ngen.cosys.shipment.information.model.ShipmentInfoModel;
import com.ngen.cosys.shipment.information.model.ShipmentInfoSearchReq;
import com.ngen.cosys.shipment.information.model.ShipmentInventoryModel;
import com.ngen.cosys.shipment.information.model.ShipmentLoadingInfoModel;
import com.ngen.cosys.shipment.information.model.ShipmentNoaListModel;
import com.ngen.cosys.shipment.information.model.ShipmentTransferManifestModel;
import com.ngen.cosys.shipment.model.CdhDocumentmaster;
import com.ngen.cosys.shipment.model.ShipmentMaster;

/**
 * This is a repository implementation class for Shipment Info which allows
 * functionalities for fetching entire shipment information
 * 
 * @author NIIT Technologies Ltd
 *
 */

@Repository("shipmentInformationDAO")
public class ShipmentInformationDAOImpl extends BaseDAO implements ShipmentInformationDAO {

   @Autowired
   @Qualifier(BeanFactoryConstants.SESSION_TEMPLATE)
   private SqlSessionTemplate sqlSessionTemplate;

   @Autowired
   @Qualifier(BeanFactoryConstants.ROI_SESSION_TEMPLATE)
   private SqlSession sqlSessionROI;
   
   @Autowired
   private MultiTenantDataSourceConnectionProperties multiTenantDataSourceConnectionProperties;
   
   private static final String CONFIG_LOCATION = "classpath:config/mybatis-configuration.xml";
	
  	/** The Constant MAPPER_LOCATION. */
  	private static final String MAPPER_LOCATION = "classpath*:mapper/*.xml";
  	

   /*
    * (non-Javadoc)
    * 
    * @see com.ngen.cosys.shipment.information.dao.ShipmentInformationDAO#
    * getShipmentProcessType(com.ngen.cosys.shipment.information.model.
    * ShipmentInfoSearchReq)
    */
   @Override
   public String getShipmentProcessType(ShipmentInfoSearchReq search) throws CustomException {
      return this.fetchObject(ShipmentInformationQueryIds.SQL_DERIVE_PROCESS_TYPE.getQueryId(), search,
            sqlSessionTemplate);
   }

   /*
    * (non-Javadoc)
    * 
    * @see com.ngen.cosys.shipment.information.dao.ShipmentInformationDAO#
    * getImportShipmentInfo(com.ngen.cosys.shipment.information.model.
    * ShipmentInfoSearchReq)
    */
   @Override
   public ShipmentInfoModel getShipmentInfo(ShipmentInfoSearchReq search) throws CustomException {
      // Check for shipment type if exists
      String shipmentType = this.fetchObject("sqlGetShipmentMasterShipmentType", search, sqlSessionROI);

      if (!StringUtils.isEmpty(shipmentType) && !shipmentType.equalsIgnoreCase(search.getShipmentType())) {
         throw new CustomException("data.shipment.type.not.matching", "shipmentNumber", ErrorType.ERROR,
               new String[] { shipmentType });
      }

      // Fetch the shipment list. In case of acceptance which has reject and accepted
      // can result in duplicate
      List<ShipmentInfoModel> shipmentList = this.fetchList("sqlGetShipmentInfo", search, sqlSessionROI);

      ShipmentInfoModel shipmentInfoModel = null;

      if (!CollectionUtils.isEmpty(shipmentList)) {
         shipmentInfoModel = shipmentList.get(0);
      }

      Optional<ShipmentInfoModel> o = Optional.ofNullable(shipmentInfoModel);
      if (!o.isPresent()) {
         if ("AWB".equalsIgnoreCase(search.getShipmentType())) {
            // Look for FWB has been received
            shipmentInfoModel = this.fetchObject("sqlGetFWBShipmentInfo", search, sqlSessionROI);
            o = Optional.ofNullable(shipmentInfoModel);
            if (!o.isPresent()) {
               // Look for Shipment has been booked
               shipmentInfoModel = this.fetchObject("sqlGetBookingShipmentInfo", search, sqlSessionROI);
               o = Optional.ofNullable(shipmentInfoModel);
               if (!o.isPresent()) {
                  throw new CustomException("NORECORD", "shipmentNumber", ErrorType.ERROR);
               }
            }
         } else {
            throw new CustomException("NORECORD", "shipmentNumber", ErrorType.ERROR);
         }
      }

      // check cancellation is done based on remarks
      if (ObjectUtils.isEmpty(shipmentInfoModel)) {

         Boolean isCancelShipment = this.fetchObject("sqlCheckCancelShipmentRemarks", search, sqlSessionROI);
         if (isCancelShipment) {
            if (shipmentInfoModel == null) {
               shipmentInfoModel = new ShipmentInfoModel();
            }
            shipmentInfoModel.setStausForCancel("Yes");
            shipmentInfoModel
                  .setCancelShipmentRmrk(this.fetchObject("sqlGetCancelShipmentRemarks", search, sqlSessionROI));
            return shipmentInfoModel;
         }

      }
      
      // Set the tenant id and terminal id
      shipmentInfoModel.setTerminal(search.getTerminal());

      // Set the remarks
      List<ShipmentRemarksModel> shipmentRemarks = this.fetchList("sqlGetShipmentInfoShipmentRemarks",
            shipmentInfoModel, sqlSessionTemplate);
      shipmentInfoModel.setRemarks(shipmentRemarks);
      // Fetch Epouch Doc
      List<EpouchFileModel> epouchFile = this.fetchList("sqlFetchEpouchDocuments", shipmentInfoModel,
            sqlSessionTemplate);
      shipmentInfoModel.setEpouchDocuments(epouchFile);

      // Fetch Arrival Manifest Info
      List<ShipmentFlightModel> arrivalManifestDetails = this.fetchList("sqlGetIncomingShipmentFilghtDetails",
            shipmentInfoModel, sqlSessionROI);
      shipmentInfoModel.setIncomingFlightDetails(arrivalManifestDetails);
      for (ShipmentFlightModel arrivalManifestInfo : arrivalManifestDetails) {
         arrivalManifestInfo.setShipmentId(shipmentInfoModel.getShipmentId());
         List<ShipmentInventoryModel> shipmentInventory = this.fetchList("sqlGetShipmentInventoryInfo",
               arrivalManifestInfo, sqlSessionROI);
         arrivalManifestInfo.setInventoryDetails(shipmentInventory);

         // Set the remarks
         List<ShipmentRemarksModel> remarks = this.fetchList("sqlGetShipmentFlightInfoShipmentRemarks",
               arrivalManifestInfo, sqlSessionROI);
         arrivalManifestInfo.setRemarks(remarks);

         // Get Damage Info
         List<ShipmentDamageInfoModel> damageInfo = this.fetchList("sqlGetDamageInfo", arrivalManifestInfo,
               sqlSessionROI);
         arrivalManifestInfo.setDamageDetails(damageInfo);

      }
    //Check Acceptance for AISATS
      if (!FeatureUtility.isFeatureEnabled(ApplicationFeatures.Exp.Accpt.DocumentFirst.class)) {
    	  shipmentInfoModel.setCheckDocumentAccepted(true);
		}
      // Fetch Acceptance Info
      List<ShipmentAcceptanceModel> acceptanceInfo = this.fetchList("sqlGetEacceptanceInfo", shipmentInfoModel,
            sqlSessionROI);
      shipmentInfoModel.setAcceptanceDetails(acceptanceInfo);
      for (ShipmentAcceptanceModel acceptanceModel : acceptanceInfo) {
         // Set the shipment info
         acceptanceModel.setShipmentNumber(shipmentInfoModel.getShipmentNumber());
         acceptanceModel.setShipmentDate(shipmentInfoModel.getShipmentDate());

         if (!"RETURNED".equalsIgnoreCase(acceptanceModel.getAcceptanceStatus())
               && !"REJECTED".equalsIgnoreCase(acceptanceModel.getAcceptanceStatus())) {
            // Set the acceptance
            ShipmentFlightModel inventoryFlightModel = new ShipmentFlightModel();
            inventoryFlightModel.setShipmentId(shipmentInfoModel.getShipmentId());
            List<ShipmentInventoryModel> shipmentInventory = this.fetchList("sqlGetShipmentInventoryInfo",
                  inventoryFlightModel, sqlSessionROI);
            acceptanceModel.setInventoryDetails(shipmentInventory);

            // Set the remarks
            List<ShipmentRemarksModel> acceptanceRemarks = this.fetchList("sqlGetShipmentAcceptanceShipmentRemarks",
                  acceptanceModel, sqlSessionROI);
            acceptanceModel.setRemarks(acceptanceRemarks);
         }
      }

      // Fetch Booking Info
      List<ShipmentFlightModel> bookingInfo = this.fetchList("sqlGetExportBookingInfo", shipmentInfoModel,
            sqlSessionROI);
      shipmentInfoModel.setOutbooundFlightDetails(bookingInfo);
      for (ShipmentFlightModel booking : bookingInfo) {
         booking.setShipmentId(shipmentInfoModel.getShipmentId());
         booking.setShipmentNumber(shipmentInfoModel.getShipmentNumber());
         booking.setShipmentDate(shipmentInfoModel.getShipmentDate());

         // Get the part info if no offload
         String partInfo = this.fetchObject("sqlGetShipmentInfoExportPartBookingInfo", booking, sqlSessionROI);
         booking.setPartSuffix(partInfo);

         List<ShipmentLoadingInfoModel> loadingInfoDetails = this.fetchList("sqlExportLoadingInfo", booking,
               sqlSessionROI);
         booking.setLoadingInfoModel(loadingInfoDetails);

         // Set the remarks
         List<ShipmentRemarksModel> remarks = this.fetchList("sqlGetShipmentFlightInfoShipmentRemarks", booking,
               sqlSessionROI);
         booking.setRemarks(remarks);
      }

      // Get Freight Out Info
      List<ShipmentFreightOutInfoModel> freightOutInfo = this.fetchList("sqlGetFreightOutInfo", shipmentInfoModel,
            sqlSessionROI);
      shipmentInfoModel.setFreightOutDetails(freightOutInfo);

      // Get Transfer Manifest Info
      List<ShipmentTransferManifestModel> transferManifestInfo = this.fetchList("sqlGetTrmInfo", shipmentInfoModel,
            sqlSessionROI);
      shipmentInfoModel.setTmDetails(transferManifestInfo);

      // Get E-Warehouse Trigger Details
      List<ShipmentNoaListModel> eWarehouseRemarks = this.fetchList("sqlGetEWarehouseReceiptDetails", search,
            sqlSessionROI);
      shipmentInfoModel.setEWarehouseRemarks(eWarehouseRemarks);

      // Fetch Delivery Info
      List<ShipmentDeliveryModel> shipmentDeliveryDetails = this.fetchList("sqlGetImportDeliveryInfo",
            shipmentInfoModel, sqlSessionROI);
      shipmentInfoModel.setShipmentDeliveryDetails(shipmentDeliveryDetails);

      // set noa details
      List<ShipmentNoaListModel> noaListModels = this.fetchList("sqlGetNoaDetails", search, sqlSessionROI);
      shipmentInfoModel.setNoalist(noaListModels);

      String ifNON = this.fetchObject("sqlQUeryCheckRemarksTypeIfNON", shipmentInfoModel, sqlSessionROI);
      if (!Objects.isNull(ifNON) && (ifNON.equalsIgnoreCase("NON") || ifNON.equalsIgnoreCase("RVE"))) {

         if (ifNON.equalsIgnoreCase("RVE")) {
            shipmentInfoModel.setIfNON("NON");
         } else {
            shipmentInfoModel.setIfNON(ifNON);
         }
      }

      // Fetch House info
      List<ShipmentHouseInfoModel> houseInfoDetails = this.fetchList("sqlGetShipmentHouseInfo", shipmentInfoModel,
            sqlSessionROI);
      shipmentInfoModel.setHouseInfoDetails(houseInfoDetails);

      // Fetch House info summary details
      List<ShipmentHouseInfoSummaryModel> houseSummaryDetails = this.fetchList("sqlGetHouseSummary", shipmentInfoModel,
            sqlSessionROI);
      shipmentInfoModel.setHouseSummaryDetails(houseSummaryDetails);

      // part shipment info
      List<ShipmentFlightModel> partShpInfo = this.fetchList("sqlGetPartShipmentInfo", shipmentInfoModel,
            sqlSessionROI);
      if (!CollectionUtils.isEmpty(partShpInfo)) {
         Set<String> partSuffixSet = new HashSet<>();
         String previousSuffix = "";
         for (ShipmentFlightModel t : partShpInfo) {
            if (StringUtils.isEmpty(previousSuffix) || (!StringUtils.isEmpty(previousSuffix)
                  && !previousSuffix.equalsIgnoreCase(t.getCurrentPartSuffix()))) {
               previousSuffix = t.getCurrentPartSuffix();
               t.setPartSuffix(previousSuffix);
            }

            // Add it to the set
            if (!partSuffixSet.contains(t.getPartSuffix()) && !StringUtils.isEmpty(t.getPartSuffix())) {
               partSuffixSet.add(t.getPartSuffix());
            }
         }

         // Check if the part suffix is more than 1 length then turn on the part shipment
         // flag
         if ((ObjectUtils.isEmpty(shipmentInfoModel.getPartShipment())
               || (!ObjectUtils.isEmpty(shipmentInfoModel.getPartShipment()) && !shipmentInfoModel.getPartShipment()))
               && partSuffixSet.size() > 1) {
            shipmentInfoModel.setPartShipment(Boolean.TRUE);
         }
      }
      shipmentInfoModel.setPartShipmentDetails(partShpInfo);
       // fetch HandoverLocation detail to Show Photo
      List<HandoverLocation> handoverLocation = this.fetchList("fetchHandoverLocation", shipmentInfoModel,  sqlSessionROI);
      if(!CollectionUtils.isEmpty(handoverLocation))
    	  shipmentInfoModel.setHandOverLocation(handoverLocation);
      
      return shipmentInfoModel;
   }

   @Override
   public ShipmentInfoModel cancel(ShipmentInfoModel search) throws CustomException {

      // Check any location information exists
      if ("IMPORT".equalsIgnoreCase(search.getProcess())) {
         int shipmentStatusCount = this.fetchObject("sqlGetShipmentStatus", search, sqlSessionTemplate);
         if (shipmentStatusCount > 0) {
            search.addError("shipment.cannot.cancelled.due.location.po.do.exists", "", ErrorType.ERROR);
            throw new CustomException(search.getMessageList());
         }
      } else {
         int shipmentStatusCount = this.fetchObject("sqlGetShipmentStatus", search, sqlSessionTemplate);
         if (shipmentStatusCount > 0) {
            search.addError("shipment.cannot.cancelled.due.location.loaded.freightOut.exists", "", ErrorType.ERROR);
            throw new CustomException(search.getMessageList());
         }
      }

      this.deleteData("deleteArrManShpShcFromSI", search, sqlSessionTemplate);
      this.deleteData("deleteArrManShpOnwordMovementFromSI", search, sqlSessionTemplate);
      this.deleteData("deleteArrMansiFromSI", search, sqlSessionTemplate);
      this.deleteData("deleteArrManUldFromSI", search, sqlSessionTemplate);
      
      this.deleteData("deleteDeliveryReqAuthDetailsFromSI", search, sqlSessionTemplate);
      this.deleteData("deleteDeliveryReqAuthInfoFromSI", search, sqlSessionTemplate);
      this.deleteData("deleteDeliveryReqFromSI", search, sqlSessionTemplate);
      this.deleteData("deleteBreakdownShcFromSI", search, sqlSessionTemplate);
      this.deleteData("deleteBreakdownHouseInfoFromSI", search, sqlSessionTemplate);
      this.deleteData("deleteBreakdownStorageFromSI", search, sqlSessionTemplate);
      this.deleteData("deleteBreakdownTrolleyFromSI", search, sqlSessionTemplate);
      this.deleteData("deleteShpVerDocInfoFromSI", search, sqlSessionTemplate);
      this.deleteData("deleteBreakdownShpVerFromSI", search, sqlSessionTemplate);

      this.deleteData("deleteShpInvShcFromSI", search, sqlSessionTemplate);
      this.deleteData("deleteShpInvFromSI", search, sqlSessionTemplate);

      this.deleteData("deleteshpOtherChginfoFromSI", search, sqlSessionTemplate);
      this.deleteData("deleteshpshcInfoFromSI", search, sqlSessionTemplate);
      this.deleteData("deleteshpshcHandlingInfoFromSI", search, sqlSessionTemplate);
      this.deleteData("deleteshpCusConInfoFromSI", search, sqlSessionTemplate);
      this.deleteData("deleteshpCusAddInfoFromSI", search, sqlSessionTemplate);
      this.deleteData("deleteshpCusInfoFromSI", search, sqlSessionTemplate);
      this.deleteData("deleteshpMstHandAreaFromSI", search, sqlSessionTemplate);
      this.deleteData("deleteshpMstRouteInfoFromSI", search, sqlSessionTemplate);
      this.deleteData("deleteshpLocAuthReqDetailsFromSI", search, sqlSessionTemplate);
      this.deleteData("deleteshpLocAuthReqsFromSI", search, sqlSessionTemplate);
      this.deleteData("deleteshpMstCustAddInfo", search, sqlSessionTemplate);
      this.deleteData("deleteshpMstCustInfo", search, sqlSessionTemplate);

      this.deleteData("deleteIrregularities", search, sqlSessionTemplate);

      this.deleteData("deleteshpCustInfo", search, sqlSessionTemplate);

      this.deleteData("deleteShpMstLocAuthDetailsFromSI", search, sqlSessionTemplate);
      this.deleteData("deleteShpMstLocAuthInfoFromSI", search, sqlSessionTemplate);

      this.deleteData("deletefromShpRmrk", search, sqlSessionTemplate);
      this.deleteData("deleteShpCdhDocMstFromSI", search, sqlSessionTemplate);
      this.deleteData("deleteShpCdhDocDiscrepancyFromSI", search, sqlSessionTemplate);

      this.deleteData("deleteShpInvShcFromSI", search, sqlSessionTemplate);
      this.deleteData("deleteShpInvHusFromSI", search, sqlSessionTemplate);
      this.deleteData("deleteShpInvFromSI", search, sqlSessionTemplate);

      this.deleteData("deleteShpExpWeighDimitemsFromSI", search, sqlSessionTemplate);
      this.deleteData("deleteShpExpWeighDimFromSI", search, sqlSessionTemplate);
      this.deleteData("deleteShpExpWeighFromSI", search, sqlSessionTemplate);
      this.deleteData("deleteShpExpStorageWeighFromSI", search, sqlSessionTemplate);
      this.deleteData("deleteShpExpAuthDetailsFromSI", search, sqlSessionTemplate);
      this.deleteData("deleteShpExpAuthInfoFromSI", search, sqlSessionTemplate);
      this.deleteData("deleteShpExpRemarkInfoFromSI", search, sqlSessionTemplate);// added by Anurag
      this.deleteData("deleteShpExpHusInfoFromSI", search, sqlSessionTemplate);
      this.deleteData("deleteShpExpDryIceInfoFromSI", search, sqlSessionTemplate);
      this.deleteData("deleteShpExpShcInfoFromSI", search, sqlSessionTemplate);

      //
      this.deleteData("deleteShpExpBkgDeltaShcFromSI", search, sqlSessionTemplate);
      this.deleteData("deleteShpExpBkgDeltaFromSI", search, sqlSessionTemplate);
      this.deleteData("deleteShpExpBkgDemFromSI", search, sqlSessionTemplate);
      this.deleteData("deleteShpExpFltBkgPartRemarkFromSI", search, sqlSessionTemplate);
      this.deleteData("deleteShpExpFltBkgPartFromSI", search, sqlSessionTemplate);
      this.deleteData("deleteShpExpPartBkgShcFromSI", search, sqlSessionTemplate);
      this.deleteData("deleteShpExpPartBkgDetsFromSI", search, sqlSessionTemplate);
      this.deleteData("deleteShpExpFltBkgDemFromSI", search, sqlSessionTemplate);
      this.deleteData("deleteShpExpFltBkgShcFromSI", search, sqlSessionTemplate);
      this.deleteData("deleteShpExpFltBkgDelFromSI", search, sqlSessionTemplate);
      this.deleteData("deleteShpWorkListFromSI", search, sqlSessionTemplate);
      this.deleteData("deleteExpOffLoadFromSI", search, sqlSessionTemplate);
      this.deleteData("deleteExportScreeningReason", search, sqlSessionTemplate);
      this.deleteData("deleteExportScreening", search, sqlSessionTemplate);
      this.deleteData("deleteShpExpFltBkgFromSI", search, sqlSessionTemplate);

      // Clear Acceptance Information in case of only one shipment in a given service
      // then clear such service information also
      List<BigInteger> acceptanceServiceIds = this.fetchList("sqlGetShipmentInformationAcceptanceServiceInformation",
            search, sqlSessionTemplate);

      // Clear acceptance document information
      this.deleteData("deleteShpExpDocInfoFromSI", search, sqlSessionTemplate);

      if (!CollectionUtils.isEmpty(acceptanceServiceIds)) {
         for (BigInteger serviceId : acceptanceServiceIds) {
            // delete Exp_eAcceptanceServiceInformation when there are no other shipments in
            // the service number
            this.deleteData("sqlDeleteAcceptanceServiceInformation", serviceId, sqlSessionTemplate);
         }
      }

      // update Agt_PrelodgeExportDocuments status to open
      this.updateData("sqlUpdatePrelodgeExportDocuments", search, sqlSessionTemplate);
      // update Agt_PrelodgeExportService status to open
      this.updateData("sqlUpdatePrelodgeExportService", search, sqlSessionTemplate);

      // Clear the events
      this.clearEventInformation(search);

      // Clear the FHL info
      this.clearFHLInfo(search);

      // Clear the FWB info
      this.clearFWBInfo(search);

      // Add remarks
      this.insertData("insertcancelremark", search, sqlSessionTemplate);

      // Update the AWB stock
      this.updateData("updateAwbStockDetails", search, sqlSessionTemplate);

      // Clear the events
      this.clearEventInformation(search);

      // Clear the FHL info
      this.clearFHLInfo(search);

      // Clear the FWB info
      this.clearFWBInfo(search);

      // Add remarks
      this.insertData("insertcancelremark", search, sqlSessionTemplate);

      return search;
   }

   @Override
   @NgenAuditAction(entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.CANCEL_SHIPMENT)
   public ShipmentInfoModel cancelShipment(ShipmentInfoModel search) throws CustomException {
      this.deleteData("deleteCancelledSI", search, sqlSessionTemplate);
      search.setStausForCancel("Yes");
      return search;
   }

   /**
    * Clear Event table information
    * 
    * @param request
    *           - Shipment Information for which FHL to be cleared
    * @throws CustomException
    */
   private void clearEventInformation(ShipmentInfoModel request) throws CustomException {
      this.deleteData("deleteShipmentStatusUpdateEvent", request, sqlSessionTemplate);
      this.deleteData("deleteEventOutboundShipmentOffloadedStore", request, sqlSessionTemplate);
      this.deleteData("deleteEventInboundShipmentBreakDownCompleteStore", request, sqlSessionTemplate);
      this.deleteData("deleteEventInboundShipmentDocumentCompleteStore", request, sqlSessionTemplate);
      this.deleteData("deleteEventInboundShipmentDocumentReleaseStore", request, sqlSessionTemplate);
      this.deleteData("deleteEventInboundShipmentPiecesEqualsToBreakDownPiecesStore", request, sqlSessionTemplate);
      this.deleteData("deleteEventOutboundLoadShipmentStore", request, sqlSessionTemplate);
      this.deleteData("deleteEventOutboundShipmentAssignedToFlightStore", request, sqlSessionTemplate);
      this.deleteData("deleteEventOutboundShipmentCargoAcceptanceCompleteStore", request, sqlSessionTemplate);
      this.deleteData("deleteEventOutboundShipmentCargoAcceptanceReturnedStore", request, sqlSessionTemplate);
      this.deleteData("deleteEventOutboundShipmentFlightCompletedStore", request, sqlSessionTemplate);
      this.deleteData("deleteEventOutboundShipmentManifestedStore", request, sqlSessionTemplate);
      this.deleteData("deleteEventOutboundShipmentPartiallyAcceptedPiecesStore", request, sqlSessionTemplate);
      this.deleteData("deleteEventOutboundShipmentPiecesEqualsToAcceptedPiecesStore", request, sqlSessionTemplate);
      this.deleteData("deleteEventOutboundShipmentScreeningStore", request, sqlSessionTemplate);
      this.deleteData("deleteEventOutboundUnloadShipmentStore", request, sqlSessionTemplate);
      this.deleteData("deleteEventTranshipmentThroughTransitFinalizedByShipmentStore", request, sqlSessionTemplate);
      this.deleteData("deleteEventTranshipmentTransferManifestUnFinalizedStore", request, sqlSessionTemplate);
   }

   /**
    * Clear FWB Information
    * 
    * @param request
    * @throws CustomException
    */
   private void clearFWBInfo(ShipmentInfoModel request) throws CustomException {
      // 1. SSR/OSI
      this.deleteData("sqlDeleteShipmentInformationFWBOSISSRInfo", request, sqlSessionTemplate);
      // 2. Shipment Reference
      this.deleteData("sqlDeleteShipmentInformationFWBShipmentReferenceInformation", request, sqlSessionTemplate);
      // 3. SHC
      this.deleteData("sqlDeleteShipmentInformationFWBSHC", request, sqlSessionTemplate);
      // 4. Sender Reference
      this.deleteData("sqlDeleteShipmentInformationFWBSenderReference", request, sqlSessionTemplate);
      // 5. Routing
      this.deleteData("sqlDeleteShipmentInformationFWBRouting", request, sqlSessionTemplate);
      // 6. RTD Other Info
      this.deleteData("sqlDeleteShipmentInformationFWBRateDescriptionOtherInfo", request, sqlSessionTemplate);
      // 7. Rate Description
      this.deleteData("sqlDeleteShipmentInformationFWBRateDescription", request, sqlSessionTemplate);
      // 8. PPD/COL
      this.deleteData("sqlDeleteShipmentInformationFWBPPDCOL", request, sqlSessionTemplate);
      // 9. OPI
      this.deleteData("sqlDeleteShipmentInformationFWBOtherParticipantInformation", request, sqlSessionTemplate);
      // 10. Other Customs Info
      this.deleteData("sqlDeleteShipmentInformationFWBOtherCustomsInfo", request, sqlSessionTemplate);
      // 11. Other Charges
      this.deleteData("sqlDeleteShipmentInformationFWBOtherCharges", request, sqlSessionTemplate);
      // 12. Nominated Handling Party
      this.deleteData("sqlDeleteShipmentInformationFWBNominatedHandlingParty", request, sqlSessionTemplate);
      // 13. Flight Booking
      this.deleteData("sqlDeleteShipmentInformationFWBFlightBooking", request, sqlSessionTemplate);
      // 14. Customer Contact Info
      this.deleteData("sqlDeleteShipmentInformationFWBCustomerContactInfo", request, sqlSessionTemplate);
      // 15. Customer Address Info
      this.deleteData("sqlDeleteShipmentInformationFWBCustomerAddressInfo", request, sqlSessionTemplate);
      // 16. Customer Info
      this.deleteData("sqlDeleteShipmentInformationShipmentInformationFWBCustomerInfo", request, sqlSessionTemplate);
      // 17. Charge Declaration
      this.deleteData("sqlDeleteShipmentInformationFWBChargeDeclaration", request, sqlSessionTemplate);
      // 18. CCC Charges
      this.deleteData("sqlDeleteShipmentInformationFWBCCCCharges", request, sqlSessionTemplate);
      // 19. Agent Info
      this.deleteData("sqlDeleteShipmentInformationFWBAgentInfo", request, sqlSessionTemplate);
      // 20. Accounting Information
      this.deleteData("sqlDeleteShipmentInformationFWBAccountingInformation", request, sqlSessionTemplate);
      // 21. FWB
      this.updateData("sqlDeleteShipmentInformationFWB", request, sqlSessionTemplate);
   }

   /**
    * Clear FHL Information
    * 
    * @param request
    *           - Shipment Information for which FHL to be cleared
    * @throws CustomException
    */
   private void clearFHLInfo(ShipmentInfoModel request) throws CustomException {
      // 1. SHC
      super.deleteData("sqlDeleteShipmentInformationHouseShcs", request, sqlSessionTemplate);

      // 2. FreeText
      super.deleteData("sqlDeleteShipmentInformationHouseDescriptions", request, sqlSessionTemplate);

      // 3. Tariffs
      super.deleteData("sqlDeleteShipmentInformationHouseTariffs", request, sqlSessionTemplate);

      // 4. OCI
      super.deleteData("sqlDeleteShipmentInformationHouseOtherCustomInfo", request, sqlSessionTemplate);

      // 5. Customer Address Contacts
      super.deleteData("sqlDeleteShipmentInformationHouseCustomerAddressContacts", request, sqlSessionTemplate);

      // 6. Customer Address
      super.deleteData("sqlDeleteShipmentInformationHouseCustomerAddress", request, sqlSessionTemplate);

      // 7. Customer
      super.deleteData("sqlDeleteShipmentInformationHouseCustomer", request, sqlSessionTemplate);

      // 8. Other Charges Info
      super.deleteData("sqlDeleteShipmentInformationHouseOtherCharges", request, sqlSessionTemplate);

      // 9. House
      super.deleteData("sqlDeleteShipmentInformationHouse", request, sqlSessionTemplate);

      // 10. House AWB
      super.deleteData("sqlDeleteShipmentInformationHouseAWB", request, sqlSessionTemplate);
   }

   @Override
   public String getUserPwd(ShipmentInfoModel search) throws CustomException {
      return this.fetchObject("sqlGetuserPwd", search, sqlSessionTemplate);
   }

   @Override
   public BigInteger checkShipmentLoadedOrNot(ShipmentInfoModel search) throws CustomException {

      return this.fetchObject("sqlGetShipmentLoadedOrNot", search, sqlSessionTemplate);
   }

   @Override
   public void updateCDHShipmetMasterData(CdhDocumentmaster cdhModel) throws CustomException {
      this.insertData("insertCdhDocumentMaster", cdhModel, sqlSessionTemplate);
   }

   @Override
   public BigInteger getPigeonHoleLocationId(CdhDocumentmaster cdhModel) throws CustomException {
      return this.fetchObject("getPigeonHoleLocationId", cdhModel, sqlSessionTemplate);
   }

   @Override
   public Integer checkShipmentIsAddedOrNot(AWBPrintRequest awbPrintRequest) throws CustomException {
      return this.fetchObject("checkShipmentIsAddedOrNot", awbPrintRequest, sqlSessionTemplate);
   }

   @Override
   public List<ShipmentInfoMessageModel> fetchMessages(ShipmentInfoModel requestModel) throws CustomException {
      return this.fetchList("sqlGetMessagesInformation", requestModel, sqlSessionTemplate);
   }

   @Override
   public ShipmentFlightModel getPiecesInfoForPurge(ShipmentInfoModel search) throws CustomException {
      return this.fetchObject("sqlQueryGetPiecesInfoForPurge", search, sqlSessionTemplate);
   }

   @Override
   @NgenAuditAction(entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.FORCE_PURGE)
   public void insertIntoArchivalShipmentFlight(ShipmentFlightModel piecesInfoForPurge) throws CustomException {
      this.insertData("sqlQueryInsertArchivalShipmentFlight", piecesInfoForPurge, sqlSessionTemplate);
   }

   @Override
	public void executeProcedureToMerge() throws CustomException {
		// this.fetchObject("sqlQueryExecuteProcedureToMerge", true,
		// sqlSessionTemplate);

		try {
			SqlSessionTemplate sqlSessionTemplateArch = getSessionTemplateToMerge();
			this.fetchObject("sqlQueryExecuteProcedureToMerge", true, sqlSessionTemplateArch);
			sqlSessionTemplateArch.destroy();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new CustomException("awb.force.purge.db.error","",e.getMessage());
			
		}
	}
   
   private SqlSessionTemplate getSessionTemplateToMerge() throws Exception {

	    String tenantPurgeDBInstance = this.fetchObject("getSystemParameterValue", "TENANT_PURGE_DB_INSTANCE", sqlSessionTemplate);
		TenantDbConnectionModel tenantDbConnectionModel =  multiTenantDataSourceConnectionProperties.getTenantConnectionProperties().get(TenantContext.get().getTenantConfig().getArchivalTenantId());
		DbInstanceModel dbInstanceModel = multiTenantDataSourceConnectionProperties.getDataSourceProperties().get(tenantPurgeDBInstance);
		DataSource dataSource = 				
				DataSourceBuilder.create().url("jdbc:sqlserver://"+dbInstanceModel.getInstanceName()+":"+dbInstanceModel.getInstancePort()+";databaseName="+tenantDbConnectionModel.getDatabaseSchema()).username(dbInstanceModel.getUsername()).password(dbInstanceModel.getDecryptedPassword())
				.driverClassName(dbInstanceModel.getDriverClassName()).build();
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(dataSource);
		sqlSessionFactoryBean.setConfigLocation(new PathMatchingResourcePatternResolver().getResource(CONFIG_LOCATION));
		sqlSessionFactoryBean
				.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(MAPPER_LOCATION));
		SqlSessionTemplate sqlSessionTemplate = new SqlSessionTemplate(sqlSessionFactoryBean.getObject());
		return sqlSessionTemplate;
	}

   @Override
   public String getUrlForClearingShipmentDateCache() throws CustomException {
      return this.fetchObject("sqlQueryGetUrlForClearingShipmentDateCache", true, sqlSessionTemplate);
   }

   @Override
   public BigInteger checkIfAnyPurgeIsInProgress(ShipmentInfoModel search) throws CustomException {
      return this.fetchObject("sqlQueryCheckIfAnyPurgeIsInProgress", search, sqlSessionTemplate);
   }

   @Override
   public List<ShipmentFlightModel> fetchBookingFFRFlightInfo(ShipmentInfoModel search) throws CustomException {
      return this.fetchList("sqlQueryFetchBookingFFRFlightInfo", search, sqlSessionTemplate);
   }

   @Override
   public List<ShipmentCancellationStoreEvent> getCancelShipmentForCancel(ShipmentInfoModel search)
         throws CustomException {
      return this.fetchList("sqlGetCancelShipmentForCancel", search, sqlSessionTemplate);
   }
   
   /*
    * (non-Javadoc)
    * 
    * @see com.ngen.cosys.shipment.information.dao.ShipmentInformationDAO#
    * isHandledByHouse(com.ngen.cosys.shipment.information.model.ShipmentInfoModel)
    */
	@Override
	public ShipmentMaster isHandledByHouse(ShipmentMaster shipmentMaster) throws CustomException {
		return fetchObject("checkIfHandledByHouse", shipmentMaster, sqlSessionTemplate);
	}

	@Override
	public List<DimensionInfoModel> getDimensionInfo(int bookingId) throws CustomException {
		return this.fetchList("getAWBDimensionDetails", bookingId, sqlSessionTemplate);
	   }

	@Override
	public void saveShipmentDimensions(List<DimensionInfoModel> dimensionInfoList) throws CustomException {
		insertList("saveShipmentDimensions", dimensionInfoList, sqlSessionTemplate);
	}

	@Override
	public int deleteShipemntDimensions(int dimIds) throws CustomException {

		System.out.println("dimIds="+dimIds);
		return this.deleteData("deleteShipemntDimensions", dimIds, sqlSessionTemplate);
	}

	@Override
	public List<Integer> getDimensionsId(int bookingId) throws CustomException {
		return this.fetchList("getDimensionId", bookingId, sqlSessionTemplate);
	}

	@Override
	public DimensionInfoModel getBookingId(String shipmentNumber) throws CustomException {
		 return this.fetchObject("getBookingId", shipmentNumber, sqlSessionTemplate);
	}

	@Override
	public List<ShipmentAndBookingDetails> fetchAllDetails(ShipmentAndBookingDetails shipmentAndBookingDetails)
			throws CustomException {
		return this.fetchList("fetchAllDetails", shipmentAndBookingDetails, sqlSessionTemplate);
	}
	
	@Override
	public int checkCarrierGroup(String carrierCode) {
		int count=0;
		try {
			count= fetchObject("validateCarrierGroup", carrierCode, sqlSessionTemplate);
		} catch (CustomException e) {
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public List<DimensionDetails> fetchDimensionDetailsByBookingId(String awbNumber) throws CustomException {
		return this.fetchList("fetchDimensionDetailsByBookingId", awbNumber, sqlSessionTemplate);
	}


}