package com.ngen.cosys.aed.service;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ngen.cosys.aed.model.ScInspecRmkGhaResponseModel;
import com.ngen.cosys.aed.model.ScSumofWtGhaResposeModel;
import com.ngen.cosys.events.esb.connector.logger.payload.OutgoingMessageLog;
import com.ngen.cosys.events.payload.OutboundFlightCompleteStoreEvent;
import com.ngen.cosys.events.payload.OutboundShipmentPiecesEqualsToAcceptedPiecesStoreEvent;
import com.ngen.cosys.events.payload.OutboundShipmentRcarScreenScanInfoStoreEvent;
import com.ngen.cosys.events.payload.OutboundShipmentStartCargoAcceptanceStoreEvent;
import com.ngen.cosys.framework.exception.CustomException;

@Service
public interface OutboundShipmentEAcceptanceService {

   // AD1 Message(OutGoing)
   String getOutboundShipmentCargoAcceptancePayload(OutboundShipmentStartCargoAcceptanceStoreEvent payload)
         throws CustomException, IOException;

   // AD4 Message(OutGoing)
   String getOutboundShipmentPiecesEqualsToAcceptedPieces(
         OutboundShipmentPiecesEqualsToAcceptedPiecesStoreEvent payload)
         throws CustomException, JsonProcessingException;

   // AD5 Message(OutGoing)
   String getOutboundShipmentRcarScreenScanInfo(OutboundShipmentRcarScreenScanInfoStoreEvent payload)
         throws CustomException, JsonProcessingException;

   // AD6 Message(OutGoing)
   String getOutboundShipmentFlightCompleted(OutboundFlightCompleteStoreEvent payload)
         throws CustomException, JsonProcessingException;

   // AD2 Message(InComing)
   String insertOuboundShipmentSumOfDeclaredWaight(ScSumofWtGhaResposeModel respoceData) throws CustomException;

   // AD3 Message(InComing)
   String insertOuboundShipmentInspectionRemarks(ScInspecRmkGhaResponseModel respoceData) throws CustomException;

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