package com.ngen.cosys.satssg.interfaces.manifestreconcillationstatement.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.StringTokenizer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.ngen.cosys.events.consumer.logger.service.ApplicationLoggerService;
import com.ngen.cosys.events.esb.connector.logger.payload.IncomingMessageErrorLog;
import com.ngen.cosys.events.esb.connector.logger.payload.IncomingMessageLog;
import com.ngen.cosys.message.enums.TextMessageConstants;
import com.ngen.cosys.satssg.interfaces.manifestreconcillationstatement.dao.ManifestReconcillationMessageDAO;
import com.ngen.cosys.satssg.interfaces.manifestreconcillationstatement.model.ManifesrReconciliationStatementModel;
import com.ngen.cosys.satssginterfaces.message.exception.MessageParsingException;

@Service
public class ManifestReconcillationStatementMessageServiceImpl
      implements ManifestReconcillationStatementMessageService {

   private static final Logger LOGGER = LoggerFactory
         .getLogger(ManifestReconcillationStatementMessageServiceImpl.class);

   @Autowired
   private ManifestReconcillationMessageDAO manifestReconcillationMessageDAO;

   @Autowired
   private ApplicationLoggerService loggerService;

   @Override
   public ResponseEntity<String> processAcKMessages(String acknowledgementmessages, String systemName) throws Exception {
      ManifesrReconciliationStatementModel ackInfo = new ManifesrReconciliationStatementModel();
     
      IncomingMessageLog incomingMessage = null;
      try {
         parseIncomingAckMessage(acknowledgementmessages, ackInfo);
         
		 // Check if system name is null then set the acknowledgement code for interface
		 // system
		 if (!StringUtils.isEmpty(systemName)) {
			 ackInfo.setCreatedBy(systemName);
			 ackInfo.setModifiedBy(systemName);
		 } else {
			 ackInfo.setCreatedBy(ackInfo.getAcknowledgeCode());
			 ackInfo.setModifiedBy(ackInfo.getAcknowledgeCode());
		 }
         
         
         
         incomingMessage = saveDataIntoIncomingMessageLog(acknowledgementmessages, ackInfo);
         if(null!=ackInfo.getModifiedOn()) {
        	 ackInfo.setFma(ackInfo.getModifiedOn());
             ackInfo.setFna(ackInfo.getModifiedOn()); 
         }
        
         manifestReconcillationMessageDAO.addAckInfo(ackInfo);
         incomingMessage.setStatus("PROCESSED");
         updateDataIntoIncomingMessageLog(incomingMessage);
      } catch (Exception e) {
         if (Objects.nonNull(incomingMessage) && incomingMessage.getInMessageId() != null) {
            IncomingMessageErrorLog incomingErrorMessage = new IncomingMessageErrorLog();
            incomingErrorMessage.setInMessageId(incomingMessage.getInMessageId());
            incomingErrorMessage.setErrorCode("ERROR");
            incomingErrorMessage.setMessage(e.getMessage());
            loggerService.logInterfaceIncomingErrorMessage(incomingErrorMessage);
            incomingMessage.setStatus("ERROR");
            updateDataIntoIncomingMessageLog(incomingMessage);
         }
      }
      return new ResponseEntity<>(null, HttpStatus.OK);
   }

   private IncomingMessageLog saveDataIntoIncomingMessageLog(String payload,
         ManifesrReconciliationStatementModel ackInfo) {
      IncomingMessageLog incomingMessage = new IncomingMessageLog();
      incomingMessage.setChannelReceived("HTTP");
      incomingMessage.setInterfaceSystem(ackInfo.getCreatedBy());
      incomingMessage.setCarrierCode(ackInfo.getCarrierCode());
      incomingMessage.setFlightNumber(ackInfo.getFlightNumber());
      incomingMessage.setFlightOriginDate(LocalDateTime.of(ackInfo.getFlightOriginDate(), LocalTime.now()));
      incomingMessage.setMessageType(ackInfo.getAcknowledgeCode());
      incomingMessage.setSubMessageType(null);
      incomingMessage.setShipmentNumber(null);
      incomingMessage.setShipmentDate(null);
      incomingMessage.setReceivedOn(LocalDateTime.now());
      incomingMessage.setVersionNo(1);
      incomingMessage.setSequenceNo(1);
      incomingMessage.setMessageContentEndIndicator(null);
      incomingMessage.setMessage(payload);
      incomingMessage.setStatus("PENDING");
      //
      loggerService.logInterfaceIncomingMessage(incomingMessage);
      return incomingMessage;
   }

   private void updateDataIntoIncomingMessageLog(IncomingMessageLog incomingMessage) {
      loggerService.logIncomingMessage(incomingMessage);
   }

   /**
    * Parse incoming MRS Message
    * 
    * @param customIncomingCargoManifestDeclarationContentModel
    * @return
    * @throws MessageParsingException
    */
   public void parseIncomingAckMessage(String acknowledgementmessages,
         ManifesrReconciliationStatementModel customCargoManifestDeclarationMessageModel)
         throws MessageParsingException {

      String[] lines = acknowledgementmessages.split("\\n");
      List<String> msgLis = new LinkedList<>();
      for (String line : lines) {
         msgLis.add(line);
      }

      List<String> finalMsgList = removeCR(msgLis);

      lookForMsgsToProcess(finalMsgList, customCargoManifestDeclarationMessageModel);

   }

   /**
    * This method divides each line into different elements and puts it in the
    * array list
    * 
    * @param msgList
    *           - ArrayList old array list
    * @return List containing each line
    */
   public List<String> removeCR(List<String> msgList) {
      List<String> finalMsgList = new LinkedList<>();
      for (int i = 2; i < msgList.size(); i++) {
         String msg = msgList.get(i);
         if (msg != null)
            if (msg.indexOf('\n') >= 0) {
               for (StringTokenizer st = new StringTokenizer(msg, "\n"); st.hasMoreElements();) {
                  String subMsg = st.nextToken();
                  if (subMsg.length() > 0)
                     finalMsgList.add(subMsg.toUpperCase());
               }

            } else if (msg.length() > 0) {
               finalMsgList.add(msg);
            }
      }
      return finalMsgList;
   }

   public void lookForMsgsToProcess(List<String> finalMsgList,
         ManifesrReconciliationStatementModel customCargoManifestDeclarationMessageModel)
         throws MessageParsingException {
      String[] msgdata = new String[30];
      int strNo = 0;
      msgdata[0] = null;
      // resuming from the sent Array list index (After reading the header)
      LOGGER.warn("Final message list inside the lookformsgstoprocess method", finalMsgList);
      for (int msgloop = 0; msgloop < finalMsgList.size(); msgloop++) {
         String msg = finalMsgList.get(msgloop);
         if (msg.charAt(0) == TextMessageConstants.SLANTCHAR) {
            String lineIdentifier = "";
            int loop = msgloop;
            for (; loop > 0; loop--) {
               String previousline = finalMsgList.get(loop - 1);
               if (previousline.charAt(0) != TextMessageConstants.SLANTCHAR) {
                  lineIdentifier = previousline.substring(0, 3);
                  StringBuilder sb = new StringBuilder();
                  sb.append(lineIdentifier);
                  sb.append(msg);
                  msgdata[0] = sb.toString();
                  strNo = 1;
                  dispatchMsg(msgdata, customCargoManifestDeclarationMessageModel);
                  break;
               }
            }

            // child record
            msgdata[strNo++] = msg;
         } else {
            LOGGER.warn(
                  "msgData which is an array and customCargoManifestDeclarationMessageModel which contains all the object data:",
                  msgdata, customCargoManifestDeclarationMessageModel);
            // if its not a child record
            msgdata[0] = msg;
            strNo = 0;
            dispatchMsg(msgdata, customCargoManifestDeclarationMessageModel);

         }
      }

   }

   public void dispatchMsg(String[] msgdata, ManifesrReconciliationStatementModel manifesrReconciliationStatementModel)
         throws MessageParsingException {
      if (msgdata[0].indexOf(TextMessageConstants.FMA_IDENTIFIER.getValue()) == 0) {
         manifesrReconciliationStatementModel.setAcknowledgeCode(msgdata[0].trim());

      }
      if (msgdata[0].indexOf(TextMessageConstants.FNA_IDENTIFIER.getValue()) == 0) {
         manifesrReconciliationStatementModel.setAcknowledgeCode(msgdata[0].trim());

      }
      if (msgdata[0].indexOf(TextMessageConstants.MRS_IDENTIFIER.getValue()) == 0) {
         String msgversionNumber = msgdata[0].substring(msgdata[0].indexOf(TextMessageConstants.SLANTCHAR) + 1);
         manifesrReconciliationStatementModel.setAcknowledgeCodeSequenceNo(msgversionNumber);
      }
      if (msgdata[0].indexOf("00") == 0 || msgdata[0].indexOf("01") == 0) {

         String importExportIndicator = msgdata[0].substring(3, 4);
         LOGGER.warn("ImportExport Indicator:", importExportIndicator);
         String flightkey = msgdata[0].substring(5, 12).trim();
         // get carrier code based on flight number
         int carthirdaplha = (int) flightkey.charAt(3);
         String carrierCode = (carthirdaplha > 47 && carthirdaplha < 58) ? flightkey.substring(0, 2)
               : flightkey.substring(0, 3);
         String flightNumber = flightkey.substring(carrierCode.length());
         LOGGER.warn("Flight Key:", flightNumber);

         String flightMonth = msgdata[0].substring(15, 18);
         String origin = msgdata[0].substring(18, 21);
         String destination = msgdata[0].substring(21, 24);

         int month = getMonth(flightMonth);
         int monthCompare = LocalDate.now(ZoneId.systemDefault()).getMonthValue();
         int flightDay = Integer.parseInt(msgdata[0].substring(13, 15));
         LocalDate flightDate = null;
         if (month > monthCompare) {
            flightDate = LocalDate.of(LocalDate.now(ZoneId.systemDefault()).getYear() - 1, month, flightDay);
         } else {
            flightDate = LocalDate.of(LocalDate.now(ZoneId.systemDefault()).getYear(), month, flightDay);
         }
         LOGGER.warn("Flight Date:", flightDate);
         manifesrReconciliationStatementModel.setImportExportIndicator(importExportIndicator);
         manifesrReconciliationStatementModel.setFlightOriginDate(flightDate);
         manifesrReconciliationStatementModel.setScheduledFlightDate(flightDate);
         manifesrReconciliationStatementModel.setCarrierCode(carrierCode);
         manifesrReconciliationStatementModel.setFlightNumber(flightNumber);
         manifesrReconciliationStatementModel.setPointOfLading(destination);
         manifesrReconciliationStatementModel.setPointOfUnlading(origin);
      }
   }

   public int getMonth(String month) {
      try {
         Date date = new SimpleDateFormat("MMM", Locale.ENGLISH).parse(month);
         Calendar cal = Calendar.getInstance();
         cal.setTime(date);
         return cal.get(Calendar.MONTH) + 1;
      } catch (ParseException parseException) {
         return 13;
      }
   }

   public LocalDate convertToLocalDateViaInstant(Date dateToConvert) {
      return dateToConvert.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
   }

}