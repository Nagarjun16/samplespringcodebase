package com.ngen.cosys.cscc.service;

import com.ngen.cosys.cscc.modal.Errors;
import com.ngen.cosys.cscc.modal.request.CSCCRequest;
import com.ngen.cosys.framework.exception.CustomException;

import java.time.LocalDateTime;
import java.util.Date;

public interface ValidateService {
    public LocalDateTime validateFlight(String flightNo, String flightDate, String inOutFlag) throws CustomException;

    public LocalDateTime validateAwbNo(String awbNo) throws CustomException;

    public boolean validateULDNo(String uldNo) throws CustomException;

    public void validateRequest(CSCCRequest request, Errors errors) throws CustomException;

    public String getAppErrorMessage(String errorCode, String local, String defaultValue) throws CustomException;
}
