package com.ngen.cosys.cscc.service;

import com.ngen.cosys.cscc.modal.request.CSCCRequest;
import org.springframework.ws.WebServiceMessage;
import org.springframework.ws.context.MessageContext;

import java.io.IOException;
import java.math.BigInteger;
import java.time.LocalDateTime;

public interface CSCCLogServiceCallService {
    public void logCSCCIncomingMessageLog(String messageSubType,CSCCRequest req, String status);
    public void logCSCCOutgoingMessageLog(String messageSubType,CSCCRequest req, Object message, String status);

    public void logSuccessMessageLog(String messageSubType,CSCCRequest req, Object message);
    public void logFailMessageLog(String messageSubType,CSCCRequest req, Object message);
}
