package com.ngen.cosys.cscc.service;

import com.ngen.cosys.cscc.constant.MessageType;
import com.ngen.cosys.cscc.modal.request.CSCCRequest;
import com.ngen.cosys.framework.exception.CustomException;
import org.springframework.http.ResponseEntity;

public interface BuildMessageService {
    ResponseEntity<Object> buildMessage(MessageType messageType, CSCCRequest requestBody);
}
