package com.ngen.cosys.ulms.service;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.ulms.model.ULDReleaseToAgentRequest;
import com.ngen.cosys.ulms.model.ULDReleaseToAgentResponse;

import javax.xml.datatype.DatatypeConfigurationException;

public interface UldReleaseToAgentService {
    ULDReleaseToAgentResponse uldReleaseToAgent(ULDReleaseToAgentRequest request) throws  CustomException;

}
