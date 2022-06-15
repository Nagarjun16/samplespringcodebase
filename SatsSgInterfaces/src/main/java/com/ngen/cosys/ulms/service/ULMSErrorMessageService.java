package com.ngen.cosys.ulms.service;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.ulms.constant.ErrorCode;
import com.ngen.cosys.ulms.model.ServiceStatus;

public interface ULMSErrorMessageService {
    String getErrorMessageDescription(String errorCode) throws CustomException;
    ServiceStatus buildServiceStatus(String errorCode, String errorNumber);
    ServiceStatus buildServiceStatus(ErrorCode errorCodeDesc);
}
