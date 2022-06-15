package com.ngen.cosys.satssginterfaces.mss.dao;

import java.math.BigInteger;
import java.util.List;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.satssginterfaces.mss.model.MailExportAcceptance;
import com.ngen.cosys.satssginterfaces.mss.model.MailExportAcceptanceDetails;
import com.ngen.cosys.satssginterfaces.mss.model.MailExportAcceptanceRequest;

public interface MailExportAcceptanceDAO {

   /**
    * @param request
    * @return
    * @throws CustomException
    */
   MailExportAcceptanceRequest getPAFlightDetails(MailExportAcceptanceRequest request) throws CustomException;

   /**
    * @param request
    * @return
    * @throws CustomException
    */
   MailExportAcceptanceRequest getUldDetails(MailExportAcceptanceRequest request) throws CustomException;

   /**
    * @param carrier
    * @return
    * @throws CustomException
    */
   boolean isSatsAssistedFlight(String carrier) throws CustomException;

   /**
    * @param request
    * @return
    * @throws CustomException
    */
   MailExportAcceptanceRequest insertServiceInfo(MailExportAcceptanceRequest request) throws CustomException;

   /**
    * @param request
    * @return
    * @throws CustomException
    */
   MailExportAcceptanceRequest insertDocumentInfo(MailExportAcceptanceRequest request) throws CustomException;

   /**
    * @param request
    * @return
    * @throws CustomException
    */
   MailExportAcceptanceRequest insertEacceptnaceHouseInfo(List<MailExportAcceptance> request) throws CustomException;

   /**
    * @param request
    * @return
    * @throws CustomException
    */
   int updateShipmentMasterComplete(int request) throws CustomException;

   /**
    * @param request
    * @return
    * @throws CustomException
    */
   MailExportAcceptanceRequest insertDocumentInfoSHC(MailExportAcceptanceRequest request) throws CustomException;

   /**
    * @param request
    * @return
    * @throws CustomException
    */
   MailExportAcceptanceRequest insertShipmentinfo(MailExportAcceptanceRequest request) throws CustomException;

   /**
    * @param request
    * @return
    * @throws CustomException
    */
   MailExportAcceptanceRequest insertShipmentCustomerInfo(MailExportAcceptanceRequest request) throws CustomException;

   /**
    * @param request
    * @return
    * @throws CustomException
    */
   MailExportAcceptanceRequest insertShipmentHandlingAreaInfo(MailExportAcceptanceRequest request)
         throws CustomException;

   /**
    * @param request
    * @return
    * @throws CustomException
    */
   MailExportAcceptanceRequest insertShipmentHouseInfo(List<MailExportAcceptance> request) throws CustomException;

   /**
    * @param request
    * @return
    * @throws CustomException
    */
   MailExportAcceptanceRequest insertShipmentSHC(MailExportAcceptanceRequest request) throws CustomException;

   /**
    * @param request
    * @return
    * @throws CustomException
    */
   MailExportAcceptanceRequest insertShipmentInventory(MailExportAcceptanceRequest request) throws CustomException;

   /**
    * @param request
    * @return
    * @throws CustomException
    */
   MailExportAcceptanceRequest insertShipmentInventoryHouse(List<MailExportAcceptance> request) throws CustomException;

   /**
    * @param request
    * @return
    * @throws CustomException
    */
   MailExportAcceptanceRequest insertShipmentInventorySHC(MailExportAcceptanceRequest request) throws CustomException;

   /**
    * @param request
    * @return
    * @throws CustomException
    */
   MailExportAcceptanceRequest getDnDetails(MailExportAcceptanceRequest request) throws CustomException;

   /**
    * @param request
    * @return
    * @throws CustomException
    */
   MailExportAcceptanceRequest updateDocPiecesWeight(MailExportAcceptanceRequest request) throws CustomException;

   /**
    * @param request
    * @return
    * @throws CustomException
    */
   MailExportAcceptanceRequest updateShpMasterPiecesWeight(MailExportAcceptanceRequest request) throws CustomException;

   int getShipmentInventoryId(MailExportAcceptanceRequest request) throws CustomException;

   /**
    * @param request
    * @return
    * @throws CustomException
    */
   MailExportAcceptanceRequest updateShpInventoryPiecesWeight(MailExportAcceptanceRequest request)
         throws CustomException;

   /**
    * @param request
    * @return
    * @throws CustomException
    */
   MailExportAcceptanceRequest updateHouseInfo(List<MailExportAcceptance> request) throws CustomException;

   /**
    * @param request
    * @return
    * @throws CustomException
    */
   MailExportAcceptanceRequest updateShipmentHouseInfo(List<MailExportAcceptance> request) throws CustomException;

   /**
    * @param request
    * @return
    * @throws CustomException
    */
   MailExportAcceptanceRequest updateShipmentInventoryHouse(List<MailExportAcceptance> request) throws CustomException;

   /**
    * @param request
    * @return
    * @throws CustomException
    */
   MailExportAcceptanceRequest getMailBagNumbereAcceptanceHouse(MailExportAcceptance request) throws CustomException;

   /**
    * @param request
    * @return
    * @throws CustomException
    */
   MailExportAcceptanceRequest updateAcceptanceHouseInfoWeight(List<MailExportAcceptance> request)
         throws CustomException;

   /**
    * @param request
    * @return
    * @throws CustomException
    */
   MailExportAcceptanceRequest updateShipmentHouseInfoWeight(List<MailExportAcceptance> request) throws CustomException;

   /**
    * @param request
    * @return
    * @throws CustomException
    */
   MailExportAcceptanceRequest updateInventoryHouseWeight(List<MailExportAcceptance> request) throws CustomException;

   /**
    * @param request
    * @return
    * @throws CustomException
    */
   List<MailExportAcceptance> fetchAcceptanceDetails(MailExportAcceptanceDetails request) throws CustomException;

   /**
    * @param request
    * @return
    * @throws CustomException
    */
   MailExportAcceptanceDetails updateNestedId(MailExportAcceptanceDetails request) throws CustomException;

   /**
    * @param request
    * @return
    * @throws CustomException
    */
   MailExportAcceptanceDetails updateUldMasterLocation(MailExportAcceptanceRequest request) throws CustomException;

   /**
    * @param request
    * @return
    * @throws CustomException
    */
   MailExportAcceptanceRequest getCountryCode(MailExportAcceptanceRequest request) throws CustomException;

   int getMailBagNumberCount(String mailBagNumber) throws CustomException;

   MailExportAcceptanceRequest insertOuthouseInfo(List<MailExportAcceptance> list) throws CustomException;
   BigInteger getCustomerIdForPAFlight(MailExportAcceptanceRequest requestModel) throws CustomException;
}
