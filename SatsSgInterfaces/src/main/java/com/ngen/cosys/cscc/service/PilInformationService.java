package com.ngen.cosys.cscc.service;

import com.ngen.cosys.cscc.modal.PilInform;
import com.ngen.cosys.cscc.modal.request.CSCCRequest;
import com.ngen.cosys.framework.exception.CustomException;

import java.util.List;

public interface PilInformationService {
    List<PilInform> getPilInformation(CSCCRequest request) throws CustomException;
    Object buildMessageObject(CSCCRequest request) throws CustomException;
}
