package com.ngen.cosys.shipment.information.dao;

import java.math.BigInteger;
import java.util.List;

import com.ngen.cosys.events.payload.ShipmentCancellationStoreEvent;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.shipment.awb.model.AWBPrintRequest;
import com.ngen.cosys.shipment.information.model.DimensionDetails;
import com.ngen.cosys.shipment.information.model.DimensionInfoModel;
import com.ngen.cosys.shipment.information.model.ShipmentAndBookingDetails;
import com.ngen.cosys.shipment.information.model.ShipmentFlightModel;
import com.ngen.cosys.shipment.information.model.ShipmentInfoMessageModel;
import com.ngen.cosys.shipment.information.model.ShipmentInfoModel;
import com.ngen.cosys.shipment.information.model.ShipmentInfoSearchReq;
import com.ngen.cosys.shipment.model.CdhDocumentmaster;
import com.ngen.cosys.shipment.model.ShipmentMaster;

/**
 * @author NIIT Technologies Ltd
 *
 */
public interface ShipmentInformationDAO {

   /**
    * Method to derive shipment process type
    * 
    * @param search
    * @return String
    * @throws CustomException
    */
   String getShipmentProcessType(ShipmentInfoSearchReq search) throws CustomException;

   /**
    * Method to get shipment information for a given shipment
    * 
    * @param search
    * @return ShipmentInfoModel - The model consists of shipment
    *         master/incoming/delivery info etc
    * @throws CustomException
    */
   ShipmentInfoModel getShipmentInfo(ShipmentInfoSearchReq search) throws CustomException;

   ShipmentInfoModel cancel(ShipmentInfoModel search) throws CustomException;

   ShipmentInfoModel cancelShipment(ShipmentInfoModel search) throws CustomException;

   String getUserPwd(ShipmentInfoModel search) throws CustomException;

   BigInteger checkShipmentLoadedOrNot(ShipmentInfoModel search) throws CustomException;

   void updateCDHShipmetMasterData(CdhDocumentmaster shipmentVerModel) throws CustomException;

   Integer checkShipmentIsAddedOrNot(AWBPrintRequest awbPrintRequest) throws CustomException;

   List<ShipmentInfoMessageModel> fetchMessages(ShipmentInfoModel requestModel) throws CustomException;

   BigInteger getPigeonHoleLocationId(CdhDocumentmaster cdhModel) throws CustomException;

   ShipmentFlightModel getPiecesInfoForPurge(ShipmentInfoModel search) throws CustomException;

   void insertIntoArchivalShipmentFlight(ShipmentFlightModel piecesInfoForPurge) throws CustomException;

   void executeProcedureToMerge() throws CustomException;

   String getUrlForClearingShipmentDateCache() throws CustomException;

   BigInteger checkIfAnyPurgeIsInProgress(ShipmentInfoModel search) throws CustomException;

   List<ShipmentFlightModel> fetchBookingFFRFlightInfo(ShipmentInfoModel search) throws CustomException;

   List<ShipmentCancellationStoreEvent> getCancelShipmentForCancel(ShipmentInfoModel search) throws CustomException;
   
   ShipmentMaster isHandledByHouse(ShipmentMaster shipmentMaster) throws CustomException;

   List<DimensionInfoModel> getDimensionInfo(int bookingId) throws CustomException;

 void saveShipmentDimensions(List<DimensionInfoModel> dimensionInfoList) throws CustomException;

 int deleteShipemntDimensions(int dimIds) throws CustomException;

 List<Integer> getDimensionsId(int bookingId) throws CustomException;

 DimensionInfoModel getBookingId(String shipmentNumber) throws CustomException;
 
 List<ShipmentAndBookingDetails> fetchAllDetails(ShipmentAndBookingDetails shipmentAndBookingDetails) throws CustomException;
 
 int checkCarrierGroup(String carrierCode);
 
 List<DimensionDetails> fetchDimensionDetailsByBookingId(String awbNumber) throws CustomException; 
}