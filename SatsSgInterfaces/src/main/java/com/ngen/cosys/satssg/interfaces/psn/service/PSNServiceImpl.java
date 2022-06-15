package com.ngen.cosys.satssg.interfaces.psn.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.ngen.cosys.events.consumer.logger.service.ApplicationLoggerService;
import com.ngen.cosys.events.esb.connector.logger.payload.IncomingMessageErrorLog;
import com.ngen.cosys.events.esb.connector.logger.payload.IncomingMessageLog;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.satssg.interfaces.psn.dao.PSNDAO;
import com.ngen.cosys.satssg.interfaces.psn.model.AirwayBillIdentification;
import com.ngen.cosys.satssg.interfaces.psn.model.PsnMessageModel;
import com.ngen.cosys.satssg.interfaces.util.MessageEnvelop;
import com.ngen.cosys.satssg.interfaces.util.TextMessageUtils;
import com.ngen.cosys.satssginterfaces.message.exception.MessageParsingException;

@Service
public class PSNServiceImpl implements PSNService {

   @Autowired
   private PSNDAO psnDao;

   @Autowired
   private TextMessageUtils textMessageUtils;

   @Autowired
   ApplicationLoggerService loggerService;

   @Override
   public ResponseEntity<String> processMessages(String payload) throws MessageParsingException, CustomException {
      PsnMessageModel psnMessageModel = new PsnMessageModel();

      // Get message as list
      List<String> messageAsList = textMessageUtils.getMessageList(payload);

      // Get message Envelop
      MessageEnvelop messageEnvelop = textMessageUtils.getMessageEnvelop(messageAsList);
      psnMessageModel.setMessageEnvelop(messageEnvelop);

      PSNInboundTransformerV1 transformer = new PSNInboundTransformerV1();
      transformer.lookForMsgsToProcess(messageAsList, 0, psnMessageModel);
      psnMessageModel.setTransactionDateTime(LocalDateTime.now());

      // Log the message
      String status = "PROCESSED";
      String message = null;

      boolean isValidPSN = this.psnDao.validatePSNCode(psnMessageModel);
      if (!isValidPSN) {
         status = "REJECTED";
         message = "invalid.psn.code";
      }

      // Save the PSN info
      psnDao.insertPSNInfo(psnMessageModel);

      this.logMessage(payload, psnMessageModel, status, message);

      return new ResponseEntity<>(payload, HttpStatus.OK);
   }

   /**
    * Method to log the message
    * 
    * @param psnMessageModel
    */
   private void logMessage(String payload, PsnMessageModel psnMessageModel, String status, String message) {
      for (AirwayBillIdentification awb : psnMessageModel.getAirwayBillIdentifications()) {
         IncomingMessageLog incomingMessage = new IncomingMessageLog();
         incomingMessage.setChannelReceived("HTTP");
         incomingMessage.setInterfaceSystem("CCN");
         incomingMessage.setSenderOriginAddress(psnMessageModel.getMessageEnvelop().getRecipientAddress().get(0));
         incomingMessage.setMessageType("PSN");
         incomingMessage.setShipmentNumber(awb.getShipmentNumber());
         incomingMessage.setShipmentDate(awb.getShipmentDate().atStartOfDay());
         incomingMessage.setReceivedOn(LocalDateTime.now());
         incomingMessage.setVersionNo(1);
         incomingMessage.setSequenceNo(1);
         incomingMessage.setMessageContentEndIndicator(null);
         incomingMessage.setMessage(payload);
         incomingMessage.setStatus(status);
         incomingMessage.setCreatedBy("CCN");
         incomingMessage.setLastModifiedBy("CCN");
         loggerService.logInterfaceIncomingMessage(incomingMessage);

         if (!StringUtils.isEmpty(message)) {
            IncomingMessageErrorLog incomingErrorMessage = new IncomingMessageErrorLog();
            incomingErrorMessage.setInMessageId(incomingMessage.getInMessageId());
            incomingErrorMessage.setCreatedBy("CCN");
            incomingErrorMessage.setLastModifiedBy("CCN");
            incomingErrorMessage.setErrorCode(message);
            incomingErrorMessage.setMessage(message);
            this.loggerService.logInterfaceIncomingErrorMessage(incomingErrorMessage);
         }
      }
   }
}