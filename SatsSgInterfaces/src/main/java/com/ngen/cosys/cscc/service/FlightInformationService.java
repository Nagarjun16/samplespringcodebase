package com.ngen.cosys.cscc.service;

import com.ngen.cosys.cscc.modal.FlightInform;
import com.ngen.cosys.cscc.modal.request.CSCCRequest;
import com.ngen.cosys.framework.exception.CustomException;

import java.util.List;

public interface FlightInformationService {
    List<FlightInform> getFlightInformation(CSCCRequest request) throws CustomException;
    Object buildMessageObject(CSCCRequest request) throws CustomException;
}
