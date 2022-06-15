package com.ngen.cosys.cscc.service.impl;

import com.ngen.cosys.cscc.modal.request.CSCCRequest;
import com.ngen.cosys.cscc.modal.request.RequestBody;
import com.ngen.cosys.cscc.service.CSCCLogServiceCallService;
import com.ngen.cosys.esb.jackson.util.JacksonUtility;
import com.ngen.cosys.events.consumer.logger.service.ApplicationLoggerService;
import com.ngen.cosys.events.esb.connector.logger.payload.IncomingMessageLog;
import com.ngen.cosys.events.esb.connector.logger.payload.OutgoingMessageLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.time.LocalDateTime;

@Service
public class CSCCLogServiceCallServiceImpl implements CSCCLogServiceCallService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CSCCLogServiceCallServiceImpl.class);

    @Autowired
    private ApplicationLoggerService loggerService;

    private BigInteger logOutgoingMessage(String carrierCode,
                                          String flightNumber,
                                          LocalDateTime flightOriginDate,
                                          String shipmentNumber,
                                          LocalDateTime shipmentDate,
                                          Object payload, String subMessageType,
                                          String status) {
        OutgoingMessageLog outgoingMessage = new OutgoingMessageLog();
        outgoingMessage.setChannelSent("HTTP");
        outgoingMessage.setInterfaceSystem("CSCC");
        outgoingMessage.setSenderOriginAddress("COSYS");
        outgoingMessage.setMessageType("CSCC");
        outgoingMessage.setSubMessageType(subMessageType);
        outgoingMessage.setCarrierCode(carrierCode);
        outgoingMessage.setFlightNumber(flightNumber);
        outgoingMessage.setFlightOriginDate(flightOriginDate);
        outgoingMessage.setShipmentNumber(shipmentNumber);
        outgoingMessage.setShipmentDate(shipmentDate);
        outgoingMessage.setRequestedOn(LocalDateTime.now());
        outgoingMessage.setSentOn(LocalDateTime.now());
        outgoingMessage.setAcknowledgementReceivedOn(LocalDateTime.now());
        outgoingMessage.setVersionNo(1);
        outgoingMessage.setSequenceNo(1);
        outgoingMessage.setMessageContentEndIndicator(null);
        outgoingMessage.setCreatedBy("COSYS");
        outgoingMessage.setCreatedOn(LocalDateTime.now());
        outgoingMessage.setStatus(status);
        outgoingMessage.setMessage((String) payload);
        loggerService.logInterfaceOutgoingMessage(outgoingMessage);
        return outgoingMessage.getOutMessageId();
    }

    private BigInteger logIncomingMessage(String carrierCode,
                                          String flightNumber,
                                          LocalDateTime flightOriginDate,
                                          String shipmentNumber,
                                          LocalDateTime shipmentDate,
                                          Object payload, String subMessageType,
                                          String status) {
        IncomingMessageLog incomingMessage = new IncomingMessageLog();
        incomingMessage.setChannelReceived("HTTP");
        incomingMessage.setInterfaceSystem("CSCC");
        incomingMessage.setSenderOriginAddress("CSCC");
        incomingMessage.setMessageType("CSCC");
        incomingMessage.setSubMessageType(subMessageType);
        incomingMessage.setMessage((String) payload);
        incomingMessage.setCarrierCode(carrierCode);
        incomingMessage.setFlightNumber(flightNumber);
        incomingMessage.setFlightOriginDate(flightOriginDate);
        incomingMessage.setShipmentNumber(shipmentNumber);
        incomingMessage.setShipmentDate(shipmentDate);
        incomingMessage.setReceivedOn(LocalDateTime.now());
        incomingMessage.setVersionNo(1);
        incomingMessage.setSequenceNo(1);
        incomingMessage.setMessageContentEndIndicator(null);
        incomingMessage.setStatus(status);
        incomingMessage.setCreatedBy("CSCC");
        incomingMessage.setCreatedOn(LocalDateTime.now());
        incomingMessage.setCreatedBy("COSYS");

        loggerService.logInterfaceIncomingMessage(incomingMessage);
        return incomingMessage.getInMessageId();
    }


    @Override
    public void logCSCCIncomingMessageLog(String messageSubType, CSCCRequest req, String status) {
        RequestBody body = req.getMessage().getBody();
        Object message = JacksonUtility.convertObjectToJSONString(req);

        logIncomingMessage(body.getFlightCarrier(), body.getFlightCarrier(), body.getFlightOriginDate(),
                body.getAwbNo(), body.getShipmentDate(), message, messageSubType, status);
    }

    @Override
    public void logCSCCOutgoingMessageLog(String messageSubType, CSCCRequest req, Object message, String status) {
        RequestBody body = req.getMessage().getBody();

        logOutgoingMessage(body.getFlightCarrier(), body.getFlightCarrier(), body.getFlightOriginDate(),
                body.getAwbNo(), body.getShipmentDate(), message, messageSubType, status);
    }

    @Override
    public void logSuccessMessageLog(String messageSubType, CSCCRequest req, Object message) {
        logCSCCIncomingMessageLog(messageSubType,req,"PROCESSED");
        logCSCCOutgoingMessageLog(messageSubType,req,message,"SENT");
    }

    @Override
    public void logFailMessageLog(String messageSubType, CSCCRequest req, Object message) {
        logCSCCIncomingMessageLog(messageSubType,req,"ERROR");
        logCSCCOutgoingMessageLog(messageSubType,req,message,"SENT");
    }

}
