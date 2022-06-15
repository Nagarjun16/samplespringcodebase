package com.ngen.cosys.impbd.mail.document.dao;

import java.util.List;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.impbd.mail.document.model.InboundMailDocumentModel;
import com.ngen.cosys.impbd.mail.document.model.InboundMailDocumentShipmentModel;

public interface InboundMailDocumentDao {

   InboundMailDocumentModel flightdata(InboundMailDocumentModel requestModel) throws CustomException;

   /**
    * Method to get mail DN information by flight
    * 
    * @param requestModel
    * @return List<InboundMailDocumentShipmentModel>
    * @throws CustomException
    */
   List<InboundMailDocumentShipmentModel> get(InboundMailDocumentModel requestModel) throws CustomException;

   /**
    * Method to update mail DN information by shipmentId
    * 
    * @param requestModel
    * @return List<InboundMailDocumentShipmentModel>
    * @throws CustomException
    */
   void update(InboundMailDocumentShipmentModel requestModel) throws CustomException;

   void updateRemarks(List<InboundMailDocumentShipmentModel> requestModel) throws CustomException;

   /**
    * Method to delete mail DN information by shipmentId
    * 
    * @param requestModel
    * @throws CustomException
    */
   void deleteShipmentVerificetion(List<InboundMailDocumentShipmentModel> requestModel) throws CustomException;

   /**
    * Deletes records from Shipment Other Charges Information by shipmentId
    * 
    * @param requestModel
    * @throws CustomException
    */
   void deleteShipmentOCI(List<InboundMailDocumentShipmentModel> requestModel) throws CustomException;
   
   void deleteShipmentMasterHA(List<InboundMailDocumentShipmentModel> requestModel) throws CustomException;
   
   /**
    * Deletes records from Shipment Remark by shipmentId
    * 
    * @param requestModel
    * @throws CustomException
    */
   void deleteShipmentRemark(List<InboundMailDocumentShipmentModel> requestModel) throws CustomException;

   /**
    * Deletes records from Shipment Master record by shipmentId
    * 
    * @param requestModel
    * @throws CustomException
    */
   void deleteShipmentMaster(List<InboundMailDocumentShipmentModel> requestModel) throws CustomException;

   Integer checkShipmentId(InboundMailDocumentShipmentModel requestModel) throws CustomException;
   
   Integer checkShipmentVerificationId(InboundMailDocumentShipmentModel requestModel) throws CustomException;

   Integer checkOriginOfficeExchange(InboundMailDocumentShipmentModel requestModel) throws CustomException;

   Integer checkDestinationOfficeExchange(InboundMailDocumentShipmentModel requestModel) throws CustomException;

   Integer checkShipmentNumber(InboundMailDocumentShipmentModel requestModel) throws CustomException;

   void updateShpMst(InboundMailDocumentShipmentModel updateShipmentData) throws CustomException;

   void updateShpVer(InboundMailDocumentShipmentModel value) throws CustomException;
   InboundMailDocumentShipmentModel getTotalPiecesAndWeightFromVerification(InboundMailDocumentShipmentModel requestModel) throws CustomException;
}