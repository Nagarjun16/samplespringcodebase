package com.ngen.cosys.ulms.service;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.ulms.model.HeaderInfo;
import org.springframework.ws.soap.SoapHeaderElement;

public interface ULMSAuthorityService {
    public void authorityCaller(SoapHeaderElement soapHeaderElement) throws CustomException;
}
