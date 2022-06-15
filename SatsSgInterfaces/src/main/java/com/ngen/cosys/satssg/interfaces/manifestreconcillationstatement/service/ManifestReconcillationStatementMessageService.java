package com.ngen.cosys.satssg.interfaces.manifestreconcillationstatement.service;

import org.springframework.http.ResponseEntity;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.satssginterfaces.message.exception.MessageParsingException;

public interface ManifestReconcillationStatementMessageService {
   public ResponseEntity<String> processAcKMessages(
         String acknowledgementmessages, String interfaceName) throws MessageParsingException, CustomException, Exception;

   }