package com.ngen.cosys.ulms.service.impl;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.ulms.constant.ErrorCode;
import com.ngen.cosys.ulms.dao.ULMSAssignedLoadedULDListDao;
import com.ngen.cosys.ulms.model.ServiceStatus;
import com.ngen.cosys.ulms.service.ULMSErrorMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class ULMSErrorMessageServiceImpl implements ULMSErrorMessageService {

    @Autowired
    ULMSAssignedLoadedULDListDao ulmsAssignedLoadedULDListDao;

    @Value("${locale}")
    private static String locale;

    @Override
    public String getErrorMessageDescription(String errorCode) throws CustomException {
        String localStr = StringUtils.isEmpty(locale)?"en_US": locale;
        String errorDescription = ulmsAssignedLoadedULDListDao.getAppErrorMessage4ULMS(errorCode, localStr);

        return StringUtils.isEmpty(errorDescription)?errorCode: errorDescription;
    }

    @Override
    public ServiceStatus buildServiceStatus(String errorCode,String errorNumber) {
        ServiceStatus serviceStatus = new ServiceStatus();
        String errorDescription = null;
        try {
            errorDescription = getErrorMessageDescription(errorCode);
        } catch (CustomException e) {
            errorDescription = errorCode;
        }
        if(StringUtils.isEmpty(errorDescription)){
            errorDescription = errorCode;
        }
        serviceStatus.setStatusCode(errorNumber);
        serviceStatus.setMessage(errorDescription);

        return serviceStatus;
    }

    @Override
    public ServiceStatus buildServiceStatus(ErrorCode errorCodeDesc) {
        return buildServiceStatus(errorCodeDesc.getErrorCode(), errorCodeDesc.getErrorNumber());
    }
}
