package com.ngen.cosys.ulms.service;

import org.springframework.ws.WebServiceMessage;
import org.springframework.ws.context.MessageContext;

import java.io.IOException;
import java.math.BigInteger;
import java.time.LocalDateTime;

public interface LogWebserviceCallService {
    public BigInteger insertMessageLog(String inOutFlag, String carrierCode, String flightNumber, LocalDateTime flightOriginDate, Object payload, String subMessageType);

    public void insertMessageLog(MessageContext messageContext, Object endpoint, String subMessageType, Exception ex);

    public  String getMessageContent(WebServiceMessage message) throws IOException;
}
