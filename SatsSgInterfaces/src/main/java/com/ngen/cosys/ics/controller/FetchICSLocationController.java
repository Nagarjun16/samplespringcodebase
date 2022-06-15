package com.ngen.cosys.ics.controller;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.context.request.WebRequest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ngen.cosys.annotation.NgenCosysAppInfraAnnotation;
import com.ngen.cosys.esb.jackson.util.JacksonUtility;
import com.ngen.cosys.esb.route.ConnectorService;
import com.ngen.cosys.esb.route.ConnectorUtils;
import com.ngen.cosys.esb.route.ICSEndPointURIGenerator;
import com.ngen.cosys.events.consumer.logger.service.ApplicationLoggerService;
import com.ngen.cosys.events.enums.ESBRouterTypeUtils;
import com.ngen.cosys.events.esb.connector.logger.payload.OutgoingMessageErrorLog;
import com.ngen.cosys.events.esb.connector.logger.payload.OutgoingMessageLog;
import com.ngen.cosys.events.esb.connector.logger.service.ConnectorLoggerService;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.ics.enums.ResponseStatus;
import com.ngen.cosys.ics.model.FetchICSLocationFailResponseModel;
import com.ngen.cosys.ics.model.FetchICSLocationRequestModel;
import com.ngen.cosys.ics.model.FetchICSLocationSuccessResponseModel;
import com.ngen.cosys.ics.model.ResponseModel;
import com.ngen.cosys.multitenancy.context.TenantContext;
import com.ngen.cosys.multitenancy.utilities.MultiTenantUtility;
import com.ngen.cosys.validation.groups.FetchICSLocationValidationGroup;

@NgenCosysAppInfraAnnotation(path = "/api/ics")
public class FetchICSLocationController {

   @Autowired
   private ConnectorService router;

   @Autowired
   private ConnectorLoggerService logger;

   @Autowired
   private ApplicationLoggerService loggerService;

   @Autowired
   private ICSEndPointURIGenerator urlGenerator;

   @Autowired
   private Validator validator;

   private static final Logger LOGGER = LoggerFactory.getLogger(FetchICSLocationController.class);

   @PostMapping(value = "/fetch-ics-location", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
   public ResponseEntity<Object> fetchICSLocationFromICS(@RequestBody @Valid FetchICSLocationRequestModel payload)
         throws JsonProcessingException {

      final String endPointURL = urlGenerator.getICSConnectorURL("fetch-ics-location");

      Set<ConstraintViolation<FetchICSLocationRequestModel>> violations = this.validator.validate(payload,
            FetchICSLocationValidationGroup.class);
      ResponseModel responseModel = null;
      BigInteger referenceId=null;
      StringBuilder errorMessage = new StringBuilder();

      if (!violations.isEmpty()) {
         Iterator<ConstraintViolation<FetchICSLocationRequestModel>> iterator = violations.iterator();
         while (iterator.hasNext()) {
            errorMessage.append(iterator.next().getMessage()).append("  ;  ");
         }
         responseModel = new ResponseModel();
         responseModel.setStatus(ResponseStatus.FAIL);
         responseModel.setErrorNumber(HttpStatus.BAD_REQUEST.toString());
         responseModel.setErrorDescription(errorMessage.toString());
      } else {
         Object XMLPayload = JacksonUtility.convertObjectToXMLString(payload);

         boolean loggerEnabled = true;
         BigInteger messageId = null;
         BigInteger errorMessageId = null;
         if (!loggerEnabled) {
        	referenceId = logOutgoingMessage(XMLPayload,"PCHSLocation");
         }
         Map<String, String> payloadHeaders = ConnectorUtils.getPayloadHeaders(messageId, loggerEnabled, "ICS", TenantContext.getTenantId());
         ResponseEntity<Object> response = router.sendPayloadDataToConnector(XMLPayload, endPointURL,
               MediaType.APPLICATION_XML, payloadHeaders);
         if (Objects.nonNull(response)) {
            String exceptionMsg = null;
            if (Objects.equals(HttpStatus.BAD_REQUEST, response.getStatusCode())) {
               CustomException ex = null;
               if (Objects.nonNull(response.getBody()) && response.getBody() instanceof CustomException) {
                  ex = (CustomException) response.getBody();
                  exceptionMsg = ex.getErrorCode();
                  messageId = (BigInteger) ex.getPlaceHolders()[1];
                  if (Objects.nonNull(ex.getPlaceHolders()[2])) {
                     errorMessageId = (BigInteger) ex.getPlaceHolders()[2];
                  }
                  if (!loggerEnabled) {
                     // LogINTO outgoing error message table
                     insertOutgoingErrorMessage(messageId, response.getStatusCode(), "EXCEPTION");
                  }
               } else {
                  // Partial Success Case
                  if (loggerEnabled) {
                     messageId = new BigInteger(
                           response.getHeaders().get(ESBRouterTypeUtils.MESSAGE_ID.getName()).get(0));
                     if (response.getHeaders().containsKey(ESBRouterTypeUtils.MESSAGE_ID.getName()) && Objects
                           .nonNull(response.getHeaders().get(ESBRouterTypeUtils.ERROR_MESSAGE_ID.getName()))) {
                        errorMessageId = new BigInteger(
                              response.getHeaders().get(ESBRouterTypeUtils.ERROR_MESSAGE_ID.getName()).get(0));
                        // Error Message log update
                        updateOutgoingErrorMessage(errorMessageId);
                     }
                  }
                  // Success Case
                  logOutgoingMessage(messageId, "SENT");
               }
            } else {
               // Success Case
               if (loggerEnabled) {
                  messageId = new BigInteger(response.getHeaders().get(ESBRouterTypeUtils.MESSAGE_ID.getName()).get(0));
                  if (response.getHeaders().containsKey(ESBRouterTypeUtils.MESSAGE_ID.getName())
                        && Objects.nonNull(response.getHeaders().get(ESBRouterTypeUtils.ERROR_MESSAGE_ID.getName()))) {
                     errorMessageId = new BigInteger(
                           response.getHeaders().get(ESBRouterTypeUtils.ERROR_MESSAGE_ID.getName()).get(0));
                  }
               }
               //sucess
               referenceId = logOutgoingMessage(XMLPayload,"PCHSLocation");
               logOutgoingMessage(referenceId,"SENT","PCHSLocation");
            }
         }

         Object xmlResponseString = JacksonUtility.convertObjectToXMLString(response.getBody());
         @SuppressWarnings("unchecked")
         Map<String, String> result = (Map<String, String>) response.getBody();
         if (router.checkResponseStatus(result)) {
            FetchICSLocationSuccessResponseModel locationList = (FetchICSLocationSuccessResponseModel) JacksonUtility
                  .convertXMLStringToObject(xmlResponseString, FetchICSLocationSuccessResponseModel.class);
            // Log Outgoing message
            referenceId = logOutgoingMessage(XMLPayload,"PCHSLocation");
            logOutgoingMessage(referenceId,"SENT","PCHSLocation");
            return new ResponseEntity<>(locationList, HttpStatus.OK);
         } else {
        	 referenceId = logOutgoingMessage(XMLPayload,"PCHSLocation");
             logOutgoingMessage(referenceId,"FAILED","PCHSLocation");
            FetchICSLocationFailResponseModel failResponse = (FetchICSLocationFailResponseModel) JacksonUtility
                  .convertXMLStringToObject(xmlResponseString, FetchICSLocationFailResponseModel.class);
            return new ResponseEntity<>(failResponse, HttpStatus.OK);
         }
      }
      return new ResponseEntity<>(responseModel, HttpStatus.OK);

   }

   private BigInteger logOutgoingMessage(Object payload,String submessageType) {
      OutgoingMessageLog outgoingMessage = new OutgoingMessageLog();
      outgoingMessage.setChannelSent("HTTP");
      outgoingMessage.setInterfaceSystem("ICS");
      outgoingMessage.setSenderOriginAddress("COSYS");
      outgoingMessage.setMessageType("ICS");
      outgoingMessage.setSubMessageType(submessageType);
      outgoingMessage.setCarrierCode(null);
      outgoingMessage.setFlightNumber(null);
      outgoingMessage.setFlightOriginDate(null);
      outgoingMessage.setShipmentNumber(null);
      outgoingMessage.setShipmentDate(null);
      outgoingMessage.setRequestedOn(LocalDateTime.now());
      outgoingMessage.setSentOn(LocalDateTime.now());
      outgoingMessage.setAcknowledgementReceivedOn(LocalDateTime.now());
      outgoingMessage.setVersionNo(1);
      outgoingMessage.setSequenceNo(1);
      outgoingMessage.setMessageContentEndIndicator(null);
      outgoingMessage.setStatus("SENT");
      outgoingMessage.setMessage((String) payload);
      loggerService.logInterfaceOutgoingMessage(outgoingMessage);
      return outgoingMessage.getOutMessageId();
   }

   private void logOutgoingMessage(BigInteger referenceId, String status,String submessageType) {
      OutgoingMessageLog outgoingMessage = new OutgoingMessageLog();
      outgoingMessage.setOutMessageId(referenceId);
      outgoingMessage.setChannelSent("HTTP");
      outgoingMessage.setInterfaceSystem("ICS");
      outgoingMessage.setSenderOriginAddress("COSYS");
      outgoingMessage.setMessageType("ICS");
      outgoingMessage.setSubMessageType(submessageType);
      outgoingMessage.setCarrierCode(null);
      outgoingMessage.setFlightNumber(null);
      outgoingMessage.setFlightOriginDate(null);
      outgoingMessage.setShipmentNumber(null);
      outgoingMessage.setShipmentDate(null);
      outgoingMessage.setRequestedOn(LocalDateTime.now());
      outgoingMessage.setSentOn(LocalDateTime.now());
      outgoingMessage.setAcknowledgementReceivedOn(LocalDateTime.now());
      outgoingMessage.setVersionNo(1);
      outgoingMessage.setSequenceNo(1);
      outgoingMessage.setMessageContentEndIndicator(null);
      outgoingMessage.setStatus(status);
      LOGGER.debug("logOutgoingMessage :: LOGGED FOR Fetch ICS Location");
      logger.logOutgoingMessage(outgoingMessage);
   }

   public BigInteger insertOutgoingErrorMessage(BigInteger messageId, HttpStatus httpStatus, String errorMessage) {
      OutgoingMessageErrorLog outgoingErrorMessage = new OutgoingMessageErrorLog();

      outgoingErrorMessage.setOutMessageId(messageId);
      outgoingErrorMessage.setErrorCode("EXCEPTION");
      if (Objects.nonNull(httpStatus)) {
         outgoingErrorMessage.setMessage(ConnectorUtils.getHttpStatusMessage(httpStatus));
      } else {
         if (Objects.nonNull(errorMessage)) {
            outgoingErrorMessage.setMessage(ConnectorUtils.getErrorMessage(errorMessage));
         }
      }
      outgoingErrorMessage.setLineItem(null);
      loggerService.logInterfaceOutgoingErrorMessage(outgoingErrorMessage);
      LOGGER.debug("insertOutgoingErrorMessage :: LOGGED FOR Fetch ICS Location");
      return outgoingErrorMessage.getOutMessageId();
   }

   public void updateOutgoingErrorMessage(BigInteger errorMessageId) {
      OutgoingMessageErrorLog outgoingErrorMessage = new OutgoingMessageErrorLog();
      outgoingErrorMessage.setOutMessageErrorId(errorMessageId);
      LOGGER.debug("updateOutgoingErrorMessage :: LOGGED FOR Fetch ICS Location");
      loggerService.updateInterfaceOutgoingErrorMessage(outgoingErrorMessage);
   }

   @ExceptionHandler({ Exception.class })
   public ResponseEntity<ResponseModel> handleMethodArgumentTypeMismatch(final Exception ex, HttpServletRequest req,
         final WebRequest request) {
      ResponseModel response = new ResponseModel();
      response.setStatus(ResponseStatus.FAIL);
      response.setErrorNumber(HttpStatus.NOT_FOUND.toString());
      response.setErrorDescription(ex.getMessage());

      return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.OK);
   }

}
