package com.ngen.cosys.shipment.dao;

import java.util.HashMap;
import java.util.List;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.impbd.model.AWBPrintRequest;
import com.ngen.cosys.impbd.model.AWBPrintResponse;
import com.ngen.cosys.model.ShipmentModel;

public interface ShipmentVerificationDAO {

   void createVerificationDocument(List<ShipmentModel> shipmentInfo) throws CustomException;

   /**
    * Validates AWB number against Arival manifest shipment info
    * 
    * @param awbDetail
    * @return int
    * @throws CustomException
    */
   boolean validateAWBNumber(AWBPrintRequest awbDetail) throws CustomException;

   /**
    * get Shipment information from Arrivalmanifest Shipment info
    * 
    * @param awbDetail
    * @return int
    * @throws CustomException
    */
   public List<AWBPrintResponse> getShipmentInformation(AWBPrintRequest awbDetail) throws CustomException;

   public AWBPrintResponse getShipmentInformationByFlightId(AWBPrintRequest awbDetail) throws CustomException;

   public AWBPrintResponse checkShipmentVerificationDetails(AWBPrintRequest awbDetail) throws CustomException;

   public AWBPrintRequest checkShipmentReceiveOriginallyStatus(String awbNumber) throws CustomException;

   public AWBPrintResponse saveShipmentVerificationDetails(AWBPrintResponse response) throws CustomException;

   public AWBPrintResponse updateShipmentVerificationDetails(AWBPrintResponse response) throws CustomException;

   public AWBPrintResponse saveShipmentMasterDetails(AWBPrintResponse response) throws CustomException;

   public String checkShipmentExistInShipmentMaster(String awbNumber) throws CustomException;

   public AWBPrintResponse saveShipmentIrregularityDetails(AWBPrintResponse response) throws CustomException;

   public boolean checkShipmentIrregularityDetails(AWBPrintResponse response) throws CustomException;

   public String getFlightIdByFlightKeyAndDate(AWBPrintRequest awbDetail) throws CustomException;

   void captutreAuditTrail(HashMap<String, Object> auditMap) throws CustomException;

}