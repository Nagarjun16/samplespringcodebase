/**
 * AlteaFMLogger.java
 * 
 * Copyright <PRE><IMG SRC = XX></IMG></PRE>
 *
 * Version      Date         Author      Reason
 * 1.0          22 JAN, 2019   NIIT      -
 */
package com.ngen.cosys.altea.fm.logger;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.ngen.cosys.altea.fm.common.AlteaFMEventSource;
import com.ngen.cosys.altea.fm.common.DCSFMConstants;
import com.ngen.cosys.altea.fm.model.DCSFMUpdateCargoFigures;
import com.ngen.cosys.altea.fm.model.DateTime;
import com.ngen.cosys.altea.fm.model.FlightDate;
import com.ngen.cosys.altea.fm.model.FlightNumberInformation;
import com.ngen.cosys.events.enums.EventStatus;
import com.ngen.cosys.events.esb.connector.logger.payload.OutgoingMessageErrorLog;
import com.ngen.cosys.events.esb.connector.logger.payload.OutgoingMessageLog;

/**
 * This class holds Outbound Message Log
 * 
 * @author NIIT Technologies Ltd
 *
 */
public class AlteaFMLogger {

   private static final Logger LOGGER = LoggerFactory.getLogger(AlteaFMLogger.class);
   
   /**
    * Outgoing Message log
    * 
    * @param dcsfmUpdateCargofigures
    * @param eventSource
    * @param requestTime
    * @return
    */
   public static OutgoingMessageLog getOutgoingMessageLog(DCSFMUpdateCargoFigures dcsfmUpdateCargofigures,
         String eventSource, LocalDateTime requestTime) {
      //
      LOGGER.debug("Altea FM Logger - Outgoing Message Log for Message Request :: {}");
      FlightNumberInformation flightNumberInformation = dcsfmUpdateCargofigures.getFlightNumberInformation();
      FlightDate flightDate = !CollectionUtils.isEmpty(dcsfmUpdateCargofigures.getFlightDate())
            ? dcsfmUpdateCargofigures.getFlightDate().get(0)
            : null;
      OutgoingMessageLog outgoingMessage = new OutgoingMessageLog();
      //
      outgoingMessage.setChannelSent(DCSFMConstants.HTTP);
      outgoingMessage.setInterfaceSystem(DCSFMConstants.ALTEAWEB);
      outgoingMessage.setSenderOriginAddress(DCSFMConstants.COSYS);
      outgoingMessage.setMessageType(DCSFMConstants.AFM);
      if (!StringUtils.isEmpty(eventSource)) {
         if (Objects.equals(AlteaFMEventSource.DLS, eventSource)) {
            outgoingMessage.setSubMessageType(AlteaFMEventSource.DLSFM);
         } else if (Objects.equals(AlteaFMEventSource.NOTOC, eventSource)) {
            outgoingMessage.setSubMessageType(AlteaFMEventSource.NTMFM);
         }
      }
      // Carrier code
      String carrierCode = null;
      // Flight Number
      Integer flightNumber = null;
      //
      if (Objects.nonNull(flightNumberInformation)) {
         if (Objects.nonNull(flightNumberInformation.getCompanyIdentification())) {
            carrierCode = flightNumberInformation.getCompanyIdentification().getOperatingCompany();
         }
         if (Objects.nonNull(flightNumberInformation.getFlightDetail())
               && !StringUtils.isEmpty(flightNumberInformation.getFlightDetail().getFlightNumber())) {
            flightNumber = Integer.parseInt(flightNumberInformation.getFlightDetail().getFlightNumber());
         }
      }
      outgoingMessage.setCarrierCode(carrierCode);
      outgoingMessage.setFlightNumber(String.valueOf(flightNumber));
      // Flight Date
      LocalDate flightOriginDate = null;
      if (Objects.nonNull(flightDate)) {
         DateTime dateTime = flightDate.getDateTime();
         if (Objects.nonNull(dateTime)) {
            flightOriginDate = LocalDate.of(dateTime.getYear(), dateTime.getMonth(), dateTime.getDay());
         }
      }
      outgoingMessage.setFlightOriginDate(
            Objects.nonNull(flightOriginDate) ? LocalDateTime.of(flightOriginDate, LocalTime.MIDNIGHT) : null);
      outgoingMessage.setShipmentNumber(null);
      outgoingMessage.setShipmentDate(null);
      outgoingMessage.setRequestedOn(requestTime);
      outgoingMessage.setSentOn(requestTime);
      outgoingMessage.setAcknowledgementReceivedOn(null);
      outgoingMessage.setVersionNo(1);
      outgoingMessage.setSequenceNo(1);
      outgoingMessage.setMessageContentEndIndicator(null);
      outgoingMessage.setStatus(EventStatus.INITIATED.getStatus());
      //
      return outgoingMessage;
   }
   
   /**
    * Outgoing Message Log for Update
    * 
    * @param dcsfmUpdateCargofigures
    * @param messageLogId
    * @param responseTime
    * @param responseStatus
    * @return
    */
   @Deprecated
   public static OutgoingMessageLog getOutgoingMessageLogForUpdate(DCSFMUpdateCargoFigures dcsfmUpdateCargofigures,
         BigInteger messageLogId, LocalDateTime responseTime, EventStatus responseStatus) {
      //
      LOGGER.debug("Altea FM Logger - Outgoing Message Log for Message Response :: {}", String.valueOf(messageLogId));
      FlightNumberInformation flightNumberInformation = dcsfmUpdateCargofigures.getFlightNumberInformation();
      FlightDate flightDate = !CollectionUtils.isEmpty(dcsfmUpdateCargofigures.getFlightDate())
            ? dcsfmUpdateCargofigures.getFlightDate().get(0)
            : null;
      OutgoingMessageLog outgoingMessage = new OutgoingMessageLog();
      //
      outgoingMessage.setOutMessageId(messageLogId);
      outgoingMessage.setAcknowledgementReceivedOn(responseTime);
      outgoingMessage.setStatus(responseStatus.getStatus());
      // Carrier code
      String carrierCode = null;
      // Flight Number
      Integer flightNumber = null;
      //
      if (Objects.nonNull(flightNumberInformation)) {
         if (Objects.nonNull(flightNumberInformation.getCompanyIdentification())) {
            carrierCode = flightNumberInformation.getCompanyIdentification().getOperatingCompany();
         }
         if (Objects.nonNull(flightNumberInformation.getFlightDetail())
               && !StringUtils.isEmpty(flightNumberInformation.getFlightDetail().getFlightNumber())) {
            flightNumber = Integer.parseInt(flightNumberInformation.getFlightDetail().getFlightNumber());
         }
      }
      outgoingMessage.setCarrierCode(carrierCode);
      outgoingMessage.setFlightNumber(String.valueOf(flightNumber));
      // Flight Date
      LocalDate flightOriginDate = null;
      if (Objects.nonNull(flightDate)) {
         DateTime dateTime = flightDate.getDateTime();
         if (Objects.nonNull(dateTime)) {
            flightOriginDate = LocalDate.of(dateTime.getYear(), dateTime.getMonth(), dateTime.getDay());
         }
      }
      outgoingMessage.setFlightOriginDate(
            Objects.nonNull(flightOriginDate) ? LocalDateTime.of(flightOriginDate, LocalTime.MIDNIGHT) : null);
      //
      return outgoingMessage;
   }
   
   /**
    * @param outgoingMessage
    * @param responseTime
    * @param responseStatus
    * @return
    */
   public static OutgoingMessageLog getOutgoingMessageLogForUpdate(OutgoingMessageLog outgoingMessage,
         LocalDateTime responseTime, EventStatus responseStatus) {
      //
      LOGGER.debug("Altea FM Logger - Outgoing Message Log for UPDATE :: {}");
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
   public static OutgoingMessageErrorLog getOutgoingErrorMessageLog(OutgoingMessageLog outgoingMessage,
         EventStatus responseStatus, String errorMessage) {
      //
      LOGGER.debug("Altea FM Logger - Outgoing Error Message Log for Message Request :: {}");
      OutgoingMessageErrorLog errorMessageLog = new OutgoingMessageErrorLog();
      errorMessageLog.setOutMessageId(outgoingMessage.getOutMessageId());
      errorMessageLog.setErrorCode(responseStatus.getStatus());
      errorMessageLog.setMessage(errorMessage);
      errorMessageLog.setLineItem(null);
      return errorMessageLog;
   }
   
}
