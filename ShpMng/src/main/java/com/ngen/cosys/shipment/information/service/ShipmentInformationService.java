package com.ngen.cosys.shipment.information.service;

import java.util.List;

import javax.validation.Valid;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.shipment.awb.model.AWBPrintRequest;
import com.ngen.cosys.shipment.information.model.DimensionInfoModel;
import com.ngen.cosys.shipment.information.model.ShipmentInfoMessageModel;
import com.ngen.cosys.shipment.information.model.ShipmentInfoModel;
import com.ngen.cosys.shipment.information.model.ShipmentInfoSearchReq;
import com.ngen.cosys.shipment.model.ShipmentMaster;

/**
 * @author NIIT Technologies Ltd
 *
 */
public interface ShipmentInformationService {

   /**
    * Method to fetch shipment information for a given shipment number
    * 
    * @param search
    * @return ShipmentInfoModel - Information all data transacted for a given
    *         shipment number
    * @throws CustomException
    */
   ShipmentInfoModel getShipmentInfo(ShipmentInfoSearchReq search) throws CustomException;

   /**
    * Method to perform cancel shipment and remove all associated information
    * 
    * @param search
    * @return ShipmentInfoModel
    * @throws CustomException
    */
   ShipmentInfoModel cancelShipment(ShipmentInfoModel search) throws CustomException;

   /**
    * Print Barcode for Document handling process
    * 
    * @param awbPrintRequest
    * @throws CustomException
    */
   void printAWBBarcode(AWBPrintRequest awbPrintRequest) throws CustomException;

   /**
    * Method to fetch all FSU/FWB/FHL messages for the shipment
    * 
    * @param requestModel
    * @return List<ShipmentInfoMessageModel> - Messages list for the given shipment
    * @throws CustomException
    */
   List<ShipmentInfoMessageModel> fetchMessages(ShipmentInfoModel requestModel) throws CustomException;

   /**
    * Method to move shipment details to archival database
    * 
    * @param search
    * @return ShipmentInfoModel
    * @throws CustomException
    */
   ShipmentInfoModel purgeShipment(ShipmentInfoModel search) throws CustomException;

   /**
    * Method to move shipment details to archival database
    * 
    * @throws CustomException
    */
   void executeProcedureToMerge() throws CustomException;

   /**
    * Method to get URL for shipment date cache refresh
    * 
    * @return String - URL for shipment date cache manager
    * @throws CustomException
    */
   String getUrlForClearingShipmentDateCache() throws CustomException;
   
   /**
    * Method to check if shipment is handled by House or Master
    * 
    * @param shipmentInfoModel
    * @return boolean - handledby house
    * @throws CustomException
    */
   boolean isHandledByHouse(ShipmentMaster shipmentMaster) throws CustomException;

   /**
    * Method to save dimension List
    * 
    * @param dimensionInfoModel
    * @return 
    * @throws CustomException
    */
   DimensionInfoModel saveShipmentDimensions(@Valid DimensionInfoModel dimensionInfoModel) throws CustomException;

   /**
    * Method to get dimension details
    * 
    * @param ShipmentInfoSearchReq
    * @return DimensionInfoModel List
    * @throws CustomException
    */
   DimensionInfoModel getDimensionInfo(@Valid ShipmentInfoSearchReq search) throws CustomException;


}