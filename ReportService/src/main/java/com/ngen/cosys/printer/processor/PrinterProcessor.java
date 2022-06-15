/**
 * 
 * ReportProcessor.java
 * 
 * Copyright <PRE><IMG SRC = XX></IMG></PRE>
 *
 * Version      Date         Author      Reason
 * 1.0          11 JUL, 2018   NIIT      -
 */
package com.ngen.cosys.printer.processor;

import java.math.BigInteger;
import java.util.Map;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.ngen.cosys.esb.connector.payload.PrinterPayload;
import com.ngen.cosys.esb.connector.route.ESBConnectorService;
import com.ngen.cosys.esb.connector.util.ConnectorUtils;
import com.ngen.cosys.esb.connector.util.JacksonUtility;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.printer.enums.PrintStatus;
import com.ngen.cosys.report.logger.model.ReportPayload;
import com.ngen.cosys.report.logger.service.ReportLoggerService;

/**
 * This class is the Report Processor deals with ESB Connector 
 * 
 * @author NIIT Technologies Ltd
 *
 */
@Component
public class PrinterProcessor {

   private static final Logger LOGGER = LoggerFactory.getLogger(PrinterProcessor.class);
   
   @Autowired
   ReportLoggerService logger;
   
   @Autowired
   ESBConnectorService router;
   
   /**
    * @param payload
    */
   public void processReport(PrinterPayload payload) {
      //
      String systemName = "PRINTER";
      boolean loggerEnabled = false;
      BigInteger reportLogId = null;
      Map<String, String> payloadHeaders = null;
      ResponseEntity<Object> response = null;
      String jsonPayload = null;
      //
      try {
         jsonPayload = (String) JacksonUtility.convertObjectToJSONString(payload);
         if (!loggerEnabled) {
            reportLogId = logReportServicePayload(payload);
         }
         payloadHeaders = ConnectorUtils.getPayloadHeaders(reportLogId, loggerEnabled, systemName,
               payload.getTenantID());
         response = router.route(jsonPayload, MediaType.APPLICATION_JSON, payloadHeaders);
         //
         updatePrinterServiceResponse(response, reportLogId, loggerEnabled);
      } catch (Exception ex) {
         if (LOGGER.isErrorEnabled()) {
            LOGGER.error("Exception occurred while processing Printer Payload :: {} ", String.valueOf(ex));
         } else {
            LOGGER.debug("Exception occurred while processing Printer Payload :: {} ", String.valueOf(ex));
         }
      }
   }
   
   /**
    * @param response
    * @param reportLogId
    * @param loggerEnabled
    * @throws CustomException 
    */
   @SuppressWarnings("unchecked")
   private void updatePrinterServiceResponse(ResponseEntity<Object> response, BigInteger reportLogId,
         boolean loggerEnabled) throws CustomException {
      //
      if (Objects.nonNull(response)) {
         String exceptionMsg = null;
         if (Objects.equals(HttpStatus.BAD_REQUEST, response.getStatusCode())) {
            CustomException ex = null;
            if (Objects.nonNull(response.getBody()) && response.getBody() instanceof CustomException) {
               ex = (CustomException) response.getBody();
               exceptionMsg = ConnectorUtils.getErrorMessage(ex.getErrorCode());
               reportLogId = (BigInteger) ex.getPlaceHolders()[1];
               if (!loggerEnabled) {
                  updateReportServiceLog(reportLogId, PrintStatus.FAILED, null, exceptionMsg);
               }
            } else {
               // Partial Success Case
               reportLogId = getMessageID(loggerEnabled, reportLogId, response);
               updateReportServiceLog(reportLogId, PrintStatus.FAILED, response.getStatusCode(), null);
            }
         } else {
            // Success Case
            reportLogId = getMessageID(loggerEnabled, reportLogId, response);
            Map<String, String> responseBody = null;
            PrintStatus printStatus = null;
            String errorMessage = null;
            if (response.getBody() instanceof Map) {
               responseBody = (Map<String, String>) response.getBody();
               //
               if (responseBody.containsKey("status")
                     && Objects.equals("SUCCESS", responseBody.get("status").toUpperCase())) {
                  printStatus = PrintStatus.PRINTED;
               } else {
                  printStatus = PrintStatus.FAILED;
                  errorMessage = responseBody.get("description");
               }
            }
            updateReportServiceLog(reportLogId, printStatus, null, errorMessage);
         }
      }
   }
   
   
   /**
    * @param payload
    * @return
    */
   public ReportPayload getReportLoggerPayload(PrinterPayload payload) {
      ReportPayload reportPayload = new ReportPayload();
      //
      reportPayload.setReportName(payload.getContentName());
      reportPayload.setReportFormat(payload.getContentFormat());
      if (!CollectionUtils.isEmpty(payload.getContentParams())) {
         reportPayload.setReportParams(payload.getContentParams().toString());
      }
      reportPayload.setReportStatus(PrintStatus.INITIATED.getStatus());
      // Set Printer Queue Name and Printer type
      reportPayload.setPrinterQueueName(payload.getQueueName());
      reportPayload.setPrinterType(payload.getPrinterType());
      //
      return reportPayload;
   }
   
   /**
    * @param reportLogId
    * @param reportStatus
    * @param errorMessage
    * @return
    * @throws CustomException 
    */
   public void updateReportServiceLog(BigInteger reportLogId, PrintStatus printStatus, HttpStatus httpStatus,
         String errorMessage) throws CustomException {
      //
      ReportPayload reportPayload = new ReportPayload();
      reportPayload.setReportServiceLogId(reportLogId);
      reportPayload.setReportStatus(printStatus.getStatus());
      String failedReason = ConnectorUtils.getErrorMessage(errorMessage);
      if (Objects.nonNull(httpStatus)) {
         failedReason = ConnectorUtils.getHttpStatusMessage(httpStatus);
      }
      reportPayload.setFailedReason(failedReason);
      //
      logger.updateReportServiceLog(reportPayload);
   }
   
   /**
    * @param payload
    * @return
    * @throws CustomException
    */
   private BigInteger logReportServicePayload(PrinterPayload payload) throws CustomException {
      ReportPayload reportPayload = getReportLoggerPayload(payload);
      return logger.logReportService(reportPayload);
   }
   
   /**
    * @param loggerEnabled
    * @param messageId
    * @param response
    * @return
    */
   private BigInteger getMessageID(boolean loggerEnabled, BigInteger reportLogId, ResponseEntity<Object> response) {
      //
      if (loggerEnabled) {
         return ConnectorUtils.getMessageID(response);
      }
      return reportLogId;
   }
   
}
