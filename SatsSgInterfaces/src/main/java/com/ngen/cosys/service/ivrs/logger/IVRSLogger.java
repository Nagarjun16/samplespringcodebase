/**
 * {@link IVRSLogger}
 * 
 * @copyright SATS Singapore 2019-20
 * @author NIIT
 */
package com.ngen.cosys.service.ivrs.logger;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.ngen.cosys.audit.NgenAuditAction;
import com.ngen.cosys.audit.NgenAuditEntityType;
import com.ngen.cosys.audit.NgenAuditEventType;
import com.ngen.cosys.events.consumer.logger.service.ApplicationLoggerService;
import com.ngen.cosys.events.enums.EventStatus;
import com.ngen.cosys.events.esb.connector.logger.payload.IncomingMessageLog;
import com.ngen.cosys.events.esb.connector.logger.payload.OutgoingMessageErrorLog;
import com.ngen.cosys.events.esb.connector.logger.payload.OutgoingMessageLog;
import com.ngen.cosys.events.service.stream.processor.logger.ProcessorLoggerService;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.multi.tenancy.constants.BeanFactoryConstants;
import com.ngen.cosys.service.ivrs.constants.IVRSConstants;
import com.ngen.cosys.service.ivrs.model.IVRSAWBResponse;
import com.ngen.cosys.service.ivrs.model.IVRSBaseBO;
import com.ngen.cosys.service.ivrs.model.IVRSDataResponse;
import com.ngen.cosys.service.ivrs.model.IVRSMessageLog;
import com.ngen.cosys.service.ivrs.model.IVRSRequest;
import com.ngen.cosys.service.ivrs.utils.IVRSUtils;

/**
 * IVRS Logger
 * 
 * @author NIIT Technologies Ltd
 */
@Component
public class IVRSLogger {

   private static final Logger LOGGER = LoggerFactory.getLogger(IVRSLogger.class);
   
   @Autowired
   ProcessorLoggerService eventLogger;
   
   @Autowired
   ApplicationLoggerService loggerService;
   
   @Autowired
   @Qualifier(BeanFactoryConstants.SESSION_TEMPLATE)
   private SqlSession sqlSession;
   
   /**
    * @param request
    * @param outgoing
    * @throws CustomException
    */
   private void logAuditTrailDetails(IVRSBaseBO baseData, boolean outgoing) throws CustomException {
      LOGGER.debug("IVRS Logger - Audit Trail update {}");
      String query = outgoing ? "sqlUpdateIVRSOutgoingMessageLog" : "sqlUpdateIVRSIncomingMessageLog";
      sqlSession.update(query, baseData);
   }
   
   /**
    * @param request
    * @return
    * @throws CustomException
    */
   @NgenAuditAction(entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.IVRS)
   public BigInteger logIVRSEventMessage(IVRSRequest request) throws CustomException {
      LOGGER.debug("IVRS Logger - Event Message Log for Message Request :: {}");
      BigInteger messageId = eventLogger.logProcessorEventMessage(request);
      request.setMessageId(messageId);
      // Audit log
      logAuditTrailDetails(request, true);
      return messageId;
   }
   
   /**
    * @param request
    * @return
    * @throws CustomException
    */
   @NgenAuditAction(entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.FAX)
   public BigInteger logFAXEventMessage(IVRSRequest request) throws CustomException {
      LOGGER.debug("IVRS Logger - Event Message Log for Message Request :: {}");
      BigInteger messageId = eventLogger.logProcessorEventMessage(request);
      request.setMessageId(messageId);
      // Audit log
      logAuditTrailDetails(request, true);
      return messageId;
   }
   
   /**
    * @param outgoingMessage
    * @throws CustomException
    */
   public void logOutgoingMessage(OutgoingMessageLog outgoingMessage) throws CustomException {
      LOGGER.debug("IVRS Logger - Interface Outgoing Message Log for Message Request :: {}");
      loggerService.logInterfaceOutgoingMessage(outgoingMessage);
   }
   
   /**
    * @param outgoingMessage
    * @param dateTime
    * @param eventStatus
    * @param errorMessage
    */
   public void logOutgoingMessageDetails(OutgoingMessageLog outgoingMessage, LocalDateTime dateTime,
         EventStatus eventStatus, EventStatus responseStatus, String errorMessage) {
      // Outgoing Message Log status update
      loggerService.logOutgoingMessage(
            getOutgoingMessageLogForUpdate(outgoingMessage, dateTime, eventStatus));
      // Outgoing Error Message Log
      loggerService.logInterfaceOutgoingErrorMessage(
            getOutgoingErrorMessageLog(outgoingMessage, responseStatus, errorMessage));
   }
   
   /**
    * @param dataResponse
    * @param messageLog
    */
   public void updateMessageDetails(IVRSDataResponse dataResponse, IVRSMessageLog messageLog) throws CustomException {
      LOGGER.debug("IVRS Logger - UPDATE Message Log status - {}");
      OutgoingMessageLog outgoingMessage = getOutgoingMessageLogForUpdate(dataResponse, messageLog);
      OutgoingMessageErrorLog outgoingErrorMessage = getOutgoingMessageErrorLogForUpdate(dataResponse, messageLog);
      loggerService.logOutgoingMessage(outgoingMessage);
      if (Objects.nonNull(outgoingErrorMessage.getOutMessageErrorId())) {
         loggerService.updateInterfaceOutgoingErrorMessageStatus(outgoingErrorMessage);
      } else {
         loggerService.logInterfaceOutgoingErrorMessage(outgoingErrorMessage);
      }
      // Audit log
      dataResponse.setMessageId(outgoingMessage.getOutMessageId());
      logAuditTrailDetails(dataResponse, true);
   }
   
   /**
    * @param incomingMessage
    */
   @NgenAuditAction(entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.IVRS)
   public void logIncomingMessage(IVRSAWBResponse dataResponse) throws CustomException {
      LOGGER.debug("IVRS Logger - Interface Incoming Message Log for Message Request :: {}");
      IncomingMessageLog incomingMessage = getIncomingMessageLog(dataResponse);
      loggerService.logInterfaceIncomingMessage(incomingMessage);
      // Audit log
      dataResponse.setMessageId(incomingMessage.getInMessageId());
      logAuditTrailDetails(dataResponse, false);
   }
   
   /**
    * @param dataResponse
    * @param requestTime
    * @return
    */
   private IncomingMessageLog getIncomingMessageLog(IVRSAWBResponse dataResponse) {
      String shipmentNumber = dataResponse.getAwbPrefix() + dataResponse.getAwbSuffix();
      LocalDateTime requestTime = Objects.nonNull(dataResponse.getRequestDateTime()) ? dataResponse.getRequestDateTime()
            : LocalDateTime.now();
      IncomingMessageLog incomingMessage = new IncomingMessageLog();
      //
      incomingMessage.setChannelReceived(IVRSConstants.HTTP);
      incomingMessage.setSenderOriginAddress(IVRSConstants.SYSTEM_IVRS);
      incomingMessage.setInterfaceSystem(IVRSConstants.SYSTEM_IVRS);
      incomingMessage.setMessageType(IVRSConstants.SYSTEM_IVRS);
      incomingMessage.setSubMessageType(null);
      incomingMessage.setCarrierCode(null);
      incomingMessage.setFlightNumber(null);
      incomingMessage.setFlightOriginDate(null);
      incomingMessage.setShipmentNumber(shipmentNumber);
      incomingMessage.setShipmentDate(LocalDateTime.of(dataResponse.getShipmentDate(), LocalTime.MIDNIGHT));
      incomingMessage.setReceivedOn(requestTime);
      incomingMessage.setVersionNo(1);
      incomingMessage.setSequenceNo(1);
      incomingMessage.setMessageContentEndIndicator(null);
      incomingMessage.setStatus(EventStatus.PROCESSED.getStatus());
      incomingMessage.setMessage(IVRSUtils.convertObjectToJSONString(dataResponse));
      incomingMessage.setEsbMessageLogId(null);
      //
      return incomingMessage;
   }
   
   /**
    * @param ivrsRequest
    * @param interfaceSystem
    * @param messageType
    * @param requestTime
    * @return
    */
   public OutgoingMessageLog getOutgoingMessageLog(IVRSRequest ivrsRequest, String interfaceSystem,
         String messageType, LocalDateTime requestTime) {
      LOGGER.debug("IVRS Logger - Outgoing Message Log for Message Request :: {}");
      String shipmentNumber = ivrsRequest.getAwbPrefix() + ivrsRequest.getAwbSuffix();
      OutgoingMessageLog outgoingMessage = new OutgoingMessageLog();
      //
      outgoingMessage.setChannelSent(IVRSConstants.HTTP);
      outgoingMessage.setInterfaceSystem(interfaceSystem);
      outgoingMessage.setSenderOriginAddress(IVRSConstants.COSYS);
      outgoingMessage.setMessageType(messageType);
      outgoingMessage.setShipmentNumber(shipmentNumber);
      outgoingMessage.setShipmentDate(LocalDateTime.of(ivrsRequest.getShipmentDate(), LocalTime.MIDNIGHT));
      outgoingMessage.setRequestedOn(requestTime);
      outgoingMessage.setSentOn(requestTime);
      outgoingMessage.setAcknowledgementReceivedOn(null);
      outgoingMessage.setVersionNo(1);
      outgoingMessage.setSequenceNo(1);
      outgoingMessage.setMessageContentEndIndicator(null);
      outgoingMessage.setMessageFormat(String.valueOf(ivrsRequest.getMessageSequenceNo()));
      outgoingMessage.setAirportCode(null);
      outgoingMessage.setStatus(EventStatus.INITIATED.getStatus());
      outgoingMessage.setMessage(IVRSUtils.convertObjectToJSONString(ivrsRequest));
      //
      return outgoingMessage;
   }
   
   /**
    * @param dataResponse
    * @param messageLog
    * @return
    */
   private OutgoingMessageLog getOutgoingMessageLogForUpdate(IVRSDataResponse dataResponse, IVRSMessageLog messageLog) {
      OutgoingMessageLog outgoingMessage = new OutgoingMessageLog();
      outgoingMessage.setOutMessageId(messageLog.getMessageId());
      outgoingMessage.setAcknowledgementReceivedOn(LocalDateTime.now());
      outgoingMessage.setStatus(getDataResponseStatus(dataResponse));
      return outgoingMessage;
   }
   
   /**
    * @param dataResponse
    * @param messageLog
    * @return
    */
   private OutgoingMessageErrorLog getOutgoingMessageErrorLogForUpdate(IVRSDataResponse dataResponse,
         IVRSMessageLog messageLog) {
      OutgoingMessageErrorLog errorMessageLog = new OutgoingMessageErrorLog();
      errorMessageLog.setOutMessageErrorId(messageLog.getErrorMessageId());
      errorMessageLog.setOutMessageId(messageLog.getMessageId());
      errorMessageLog.setErrorCode(getDataResponseStatus(dataResponse));
      errorMessageLog.setMessage(getDataResponseErrorMessage(dataResponse));
      return errorMessageLog;
   }
   
   /**
    * @param outgoingMessage
    * @param responseTime
    * @param responseStatus
    * @return
    */
   private OutgoingMessageLog getOutgoingMessageLogForUpdate(OutgoingMessageLog outgoingMessage,
         LocalDateTime responseTime, EventStatus responseStatus) {
      LOGGER.debug("IVRS Logger - Outgoing Message Log for UPDATE :: {}");
      outgoingMessage.setAcknowledgementReceivedOn(responseTime);
      outgoingMessage.setStatus(responseStatus.getStatus());
      return outgoingMessage;
   }
   
   /**
    * @param outgoingMessage
    * @param responseStatus
    * @param errorMessage
    * @return
    */
   private OutgoingMessageErrorLog getOutgoingErrorMessageLog(OutgoingMessageLog outgoingMessage,
         EventStatus responseStatus, String errorMessage) {
      LOGGER.debug("IVRS Logger - Outgoing Error Message Log for Message Request :: {}");
      OutgoingMessageErrorLog errorMessageLog = new OutgoingMessageErrorLog();
      errorMessageLog.setOutMessageId(outgoingMessage.getOutMessageId());
      errorMessageLog.setErrorCode(responseStatus.getStatus());
      errorMessageLog.setMessage(errorMessage);
      errorMessageLog.setLineItem(null);
      return errorMessageLog;
   }
   
   /**
    * @param dataResponse
    * @return
    */
   private String getDataResponseStatus(IVRSDataResponse dataResponse) {
      String status = null;
      if (IVRSConstants.SUCCESS_RESPONSE.equalsIgnoreCase(dataResponse.getStatus())) {
         status = EventStatus.PROCESSED.getStatus();
      } else if (IVRSConstants.FAILURE_RESPONSE.equalsIgnoreCase(dataResponse.getStatus())) {
         status = EventStatus.REJECTED.getStatus();
      } else {
         status = EventStatus.ERROR.getStatus();
      }
      return status;
   }
   
   /**
    * @param dataResponse
    * @return
    */
   private String getDataResponseErrorMessage(IVRSDataResponse dataResponse) {
      return IVRSConstants.FAILURE_RESPONSE.equalsIgnoreCase(dataResponse.getStatus())
            ? dataResponse.getErrorDescription()
            : null;
   }
   
}
