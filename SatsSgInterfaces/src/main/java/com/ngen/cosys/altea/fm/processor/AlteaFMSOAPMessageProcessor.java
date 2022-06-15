/**
 * {@link AlteaFMSOAPMessageProcessor}
 * 
 * @copyright SATS Singapore 2019-20
 * @author NIIT
 */
package com.ngen.cosys.altea.fm.processor;

import java.time.LocalDateTime;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.ws.soap.security.wss4j2.Wss4jSecurityInterceptor;

import com.ngen.cosys.altea.fm.amadeus.xml.response.DCSFMUpdateCargoFiguresReply;
import com.ngen.cosys.altea.fm.amadeus.xml.response.ErrorGroupType;
import com.ngen.cosys.altea.fm.common.WebServiceClientConstants;
import com.ngen.cosys.altea.fm.config.WebServiceClientProvider;
import com.ngen.cosys.altea.fm.logger.AlteaFMLogger;
import com.ngen.cosys.altea.fm.model.DCSFMUpdateCargoFigures;
import com.ngen.cosys.altea.fm.util.AlteaFMDatasetUtil;
import com.ngen.cosys.esb.route.ConnectorService;
import com.ngen.cosys.events.consumer.logger.service.ApplicationLoggerService;
import com.ngen.cosys.events.enums.EventStatus;
import com.ngen.cosys.events.esb.connector.logger.payload.OutgoingMessageLog;
import com.ngen.cosys.service.util.constants.InterfaceSystem;

/**
 * Altea FM Soap Message Processor
 * 
 * @author NIIT Technologies Ltd
 */
@Component
public class AlteaFMSOAPMessageProcessor extends WebServiceClientProvider {

   private static final Logger LOG = LoggerFactory.getLogger(AlteaFMSOAPMessageProcessor.class);
   
   @Autowired
   @Qualifier(WebServiceClientConstants.WEB_SERVICE_SECURITY_INTERCEPTOR_BEAN)
   Wss4jSecurityInterceptor webServiceSecurityInterceptor;
   
   @Autowired
   ApplicationLoggerService loggerService;
   
   @Autowired
   ConnectorService connectorService;
   
   /**
    * @param dcsfmUpdateCargofigures
    * @param eventSource
    */
   public void process(DCSFMUpdateCargoFigures dcsfmUpdateCargofigures, String eventSource) {
      LOG.debug("AlteaFM SOAP Processor initiate API call :: Request Time : {}", LocalDateTime.now());
      String alteaFMURL = connectorService.getServiceURL(InterfaceSystem.ALTEA_FM);
      OutgoingMessageLog outgoingMessage = AlteaFMLogger.getOutgoingMessageLog(dcsfmUpdateCargofigures, eventSource,
            LocalDateTime.now());
      Object responseMessage = null;
      try {
         responseMessage = marshalSendAndReceive(alteaFMURL, requestPayload(dcsfmUpdateCargofigures), outgoingMessage,
               webServiceSecurityInterceptor, loggerService);
         if (Objects.nonNull(responseMessage)) {
            DCSFMUpdateCargoFiguresReply cargoFiguresReply = null;
            if (responseMessage instanceof DCSFMUpdateCargoFiguresReply) {
               cargoFiguresReply = (DCSFMUpdateCargoFiguresReply) responseMessage;
            }
            if (Objects.nonNull(cargoFiguresReply)) {
               // Check for Error
               if (Objects.nonNull(cargoFiguresReply.getError())) {
                  String errorMessage = getFreeTextIdentifierError(cargoFiguresReply.getError());
                  LOG.warn("Altea FM Error response received :: Flight Key - {}, Date - {}, Rejection Details - {}",
                        (outgoingMessage.getCarrierCode() + outgoingMessage.getFlightNumber()),
                        outgoingMessage.getFlightOriginDate(), errorMessage);
                  logOutgoingMessageDetails(outgoingMessage, LocalDateTime.now(), EventStatus.REJECTED, errorMessage);
               } else {
                  // All success case
               }
            }
         }
      } catch (Exception ex) {
         LOG.error("AlteaFM SOAP Processor Exception - {}", String.valueOf(ex));
         logOutgoingMessageDetails(outgoingMessage, LocalDateTime.now(), EventStatus.ERROR, String.valueOf(ex));
      }
      boolean responseReceived = Objects.nonNull(responseMessage) ? true : false;
      LOG.debug("AlteaFM SOAP Processor response received :: {}, Response Time : {}", String.valueOf(responseReceived),
            LocalDateTime.now());
   }
   
   /**
    * @param error
    * @return
    */
   private String getFreeTextIdentifierError(ErrorGroupType error) {
      String freeTextError = null;
      if (Objects.nonNull(error.getErrorWarningDescription())
            && !CollectionUtils.isEmpty(error.getErrorWarningDescription().getFreeText())) {
         for (String freeText : error.getErrorWarningDescription().getFreeText()) {
            freeTextError = freeText;
            break;
         }
      }
      return freeTextError;
   }
   
   /**
    * @param outgoingMessage
    * @param dateTime
    * @param eventStatus
    * @param errorMessage
    */
   private void logOutgoingMessageDetails(OutgoingMessageLog outgoingMessage, LocalDateTime dateTime,
         EventStatus eventStatus, String errorMessage) {
      // Outgoing Message Log status update
      loggerService.logOutgoingMessage(
            AlteaFMLogger.getOutgoingMessageLogForUpdate(outgoingMessage, LocalDateTime.now(), eventStatus));
      // Outgoing Error Message Log
      loggerService.logInterfaceOutgoingErrorMessage(
            AlteaFMLogger.getOutgoingErrorMessageLog(outgoingMessage, eventStatus, errorMessage));
   }
   
   /**
    * @param dcsfmUpdateCargofigures
    * @return
    */
   private com.ngen.cosys.altea.fm.amadeus.xml.request.DCSFMUpdateCargoFigures requestPayload(
         DCSFMUpdateCargoFigures dcsfmUpdateCargofigures) {
      com.ngen.cosys.altea.fm.amadeus.xml.request.DCSFMUpdateCargoFigures payload //
         = new com.ngen.cosys.altea.fm.amadeus.xml.request.DCSFMUpdateCargoFigures();
      // Flight Number Information
      payload.setFlightNumberInformation(AlteaFMDatasetUtil.flightNumberInformation(dcsfmUpdateCargofigures));
      // Flight Date
      AlteaFMDatasetUtil.flightDate(dcsfmUpdateCargofigures, payload.getFlightDate());
      // Leg Origin
      payload.setLegOrigin(AlteaFMDatasetUtil.legOrigin(dcsfmUpdateCargofigures));
      // Flight Leg Date
      AlteaFMDatasetUtil.flightLegDate(dcsfmUpdateCargofigures, payload.getFlightLegDate());
      // Source System Info
      payload.setSourceSystemInfo(AlteaFMDatasetUtil.sourceSystemInfo(dcsfmUpdateCargofigures));
      // Aircraft Type and Registration
      payload.setAcTypeAndReg(AlteaFMDatasetUtil.acTypeAndReg(dcsfmUpdateCargofigures));
      // Agent Name for Notoc
      payload.setAgentNameForNOTOC(AlteaFMDatasetUtil.agentNameForNOTOC(dcsfmUpdateCargofigures));
      // Cargo Agent Name Separator
      payload.setCargoAgentNameSeparator(AlteaFMDatasetUtil.cargoAgentNameSeparator(dcsfmUpdateCargofigures));
      // Cargo Agent Name
      payload.setCargoAgentName(AlteaFMDatasetUtil.cargoAgentName(dcsfmUpdateCargofigures));
      // Work Station and Printer
      payload.setWorkStationAndPrinter(AlteaFMDatasetUtil.workStationAndPrinter(dcsfmUpdateCargofigures));
      // Phone and Fax
      AlteaFMDatasetUtil.phoneAndFax(dcsfmUpdateCargofigures, payload.getPhoneAndFax());
      // DWS Comments
      // payload.setDwsComments(AlteaFMDatasetUtil.dwsComments(dcsfmUpdateCargofigures));
      // Indicators
      payload.setIndicators(AlteaFMDatasetUtil.indicators(dcsfmUpdateCargofigures));
      // Dispatch Time
      payload.setDispatchTime(AlteaFMDatasetUtil.dispatchTime(dcsfmUpdateCargofigures));
      // Load Info
      AlteaFMDatasetUtil.loadInfo(dcsfmUpdateCargofigures, payload.getLoadInfo());
      //
      return payload;
   }
   
}
