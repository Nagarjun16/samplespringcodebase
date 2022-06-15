package com.ngen.cosys.aed.dao;

import java.math.BigInteger;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ngen.cosys.aed.model.GhaFlightSchdRequestModel;
import com.ngen.cosys.aed.model.GhaMawbInfoRequestModel;
import com.ngen.cosys.aed.model.GhaMawbNoRequestModel;
import com.ngen.cosys.aed.model.GhaScanInfoRequestModel;
import com.ngen.cosys.aed.model.ScInspecRmkGhaResponseModel;
import com.ngen.cosys.aed.model.ScSumofWtGhaResposeModel;
import com.ngen.cosys.events.esb.connector.logger.payload.OutgoingMessageLog;
import com.ngen.cosys.events.payload.OutboundFlightCompleteStoreEvent;
import com.ngen.cosys.events.payload.OutboundShipmentPiecesEqualsToAcceptedPiecesStoreEvent;
import com.ngen.cosys.events.payload.OutboundShipmentRcarScreenScanInfoStoreEvent;
import com.ngen.cosys.events.payload.OutboundShipmentStartCargoAcceptanceStoreEvent;
import com.ngen.cosys.framework.exception.CustomException;

@Repository
public interface OutboundShipmentStartCargoAcceptanceDAO {
   /**
    * AD1 Message (outgoing).
    * 
    * @param requestModel
    * @return
    * @throws CustomException
    */
   GhaMawbNoRequestModel constructingCargoAcceptanceMessage(OutboundShipmentStartCargoAcceptanceStoreEvent requestModel)
         throws CustomException;

   /**
    * AD4 Message (outgoing).
    * 
    * @param requestModel
    * @return
    * @throws CustomException
    */
   GhaMawbInfoRequestModel constructingFinalizedAutoWaightMessage(
         OutboundShipmentPiecesEqualsToAcceptedPiecesStoreEvent requestModel) throws CustomException;

   /**
    * AD5 Message (outgoing).
    * 
    * @param requestModel
    * @return
    * @throws CustomException
    */
   GhaScanInfoRequestModel constructingRcarScanInfoMessage(OutboundShipmentRcarScreenScanInfoStoreEvent requestModel)
         throws CustomException;

   /**
    * AD6 Message (outgoing).
    * 
    * @param requestModel
    * @return
    * @throws CustomException
    */
   GhaFlightSchdRequestModel constructingFlihtScheduleMessage(OutboundFlightCompleteStoreEvent requestModel)
         throws CustomException;

   /**
    * AD2 Message (incoming).
    * 
    * @param respoceData
    * @return
    * @throws CustomException
    */
   String saveOuboundShipmentSumOfDeclaredWaighte(ScSumofWtGhaResposeModel respoceData) throws CustomException;

   /**
    * AD3 Message (incoming).
    * 
    * @param respoceData
    * @return
    * @throws CustomException
    */
   String saveOuboundShipmentInspectionRemarks(ScInspecRmkGhaResponseModel respoceData) throws CustomException;

   /**
    * Update SentOn for StartCargoAcceptance.
    * 
    * @param requestModel
    * @throws CustomException
    */
   void saveOuboundShipmentSentOnDetail(OutboundShipmentStartCargoAcceptanceStoreEvent requestModel)
         throws CustomException;

   /**
    * Update Out going Interface Message Log.
    * 
    * @param id
    * @throws CustomException
    */
   void updateOutGoingMessagelog(String id) throws CustomException;

   /**
    * Get the shipment number and date
    * 
    * @param shipmentNumber
    * @return Map<String, Object> - Map has both shipment number and date
    * @throws CustomException
    */
   Map<String, Object> getShipmentInfo(BigInteger shipmentId) throws CustomException;

   /**
    * Get the flight number and date
    * 
    * @param flightId
    * @return Map<String, Object> - Map has both shipment number and date
    * @throws CustomException
    */
   Map<String, Object> getFlightInfo(BigInteger flightId) throws CustomException;

   public OutgoingMessageLog getFlightDetailsForLogging(OutgoingMessageLog log) throws CustomException;
}