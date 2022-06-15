package com.ngen.cosys.satssg.interfaces.psn.service;

import org.springframework.http.ResponseEntity;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.satssginterfaces.message.exception.MessageParsingException;

public interface PSNService {

	public ResponseEntity<String> processMessages(String payload)  throws MessageParsingException, CustomException;

}
