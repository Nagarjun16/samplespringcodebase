/**
 * {@link IVRSInterfaceServiceImpl}
 * 
 * @copyright SATS Singapore 2019-20
 * @author NIIT
 */
package com.ngen.cosys.service.ivrs.service;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.ngen.cosys.annotation.service.ShipmentProcessorService;
import com.ngen.cosys.events.payload.ShipmentNotification;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.multi.tenancy.enums.TenantZone;
import com.ngen.cosys.multitenancy.context.TenantContext;
import com.ngen.cosys.multitenancy.utilities.MultiTenantUtility;
import com.ngen.cosys.service.ivrs.config.IVRSConfig;
import com.ngen.cosys.service.ivrs.constants.IVRSConstants;
import com.ngen.cosys.service.ivrs.logger.IVRSLogger;
import com.ngen.cosys.service.ivrs.model.IVRSAWBRequest;
import com.ngen.cosys.service.ivrs.model.IVRSAWBResponse;
import com.ngen.cosys.service.ivrs.model.IVRSDataResponse;
import com.ngen.cosys.service.ivrs.model.IVRSFaxReportParams;
import com.ngen.cosys.service.ivrs.model.IVRSMessageLog;
import com.ngen.cosys.service.ivrs.model.IVRSRequest;
import com.ngen.cosys.service.ivrs.model.IVRSResponse;
import com.ngen.cosys.service.ivrs.processor.IVRSProcessor;
import com.ngen.cosys.service.ivrs.repository.IVRSRepository;
import com.ngen.cosys.service.ivrs.utils.IVRSUtils;
import com.ngen.cosys.service.util.api.ServiceUtil;
import com.ngen.cosys.service.util.enums.PrinterType;
import com.ngen.cosys.service.util.enums.ReportFormat;
import com.ngen.cosys.service.util.enums.ReportRequestType;
import com.ngen.cosys.service.util.model.ReportRequest;
import com.ngen.cosys.timezone.util.TenantZoneTime;

/**
 * IVRS Interface Service implementation of as service
 * 
 * @author NIIT Technologies Ltd
 */
@Service
public class IVRSInterfaceServiceImpl implements IVRSInterfaceService {
   
   private static final Logger LOGGER = LoggerFactory.getLogger(IVRSInterfaceService.class);
   //
   private static final String DATE_FORMAT = "ddMMMyyyy";
   private static final String IVRS_FAX_REPORT = "IVRSFaxTemplate";
   private static final String PARAM_AWB = "airwayBill";
   private static final String PARAM_ORIGIN = "orgin";
   private static final String PARAM_DESTINATION = "destination";
   private static final String PARAM_PIECES = "pieces";
   private static final String PARAM_WEIGHT = "weight";
   private static final String PARAM_FLIGHT_KEY = "flightNo";
   private static final String PARAM_DATE_ARRIVED = "dateArrived";
   private static final String PARAM_CONSIGNEE_NAME = "consigneeName";
   private static final String PARAM_STAFF_NO = "staffNo";
   
   @Autowired
   IVRSConfig ivrsConfig;
   
   @Autowired
   IVRSRepository ivrsRepository;
   
   @Autowired
   IVRSLogger ivrsLogger;
   
   @Autowired
   IVRSProcessor ivrsProcessor;
   
   @Autowired
   ShipmentProcessorService shipmentDateService;
   
   /**
    * @see com.ngen.cosys.service.ivrs.service.IVRSInterfaceService#notifyArrivalOfShipment(com.ngen.cosys.events.payload.ShipmentNotification)
    */
   @Override
   public void notifyArrivalOfShipment(ShipmentNotification shipmentNotification) throws CustomException {
      LOGGER.info("IVRS Interface Service :: Notify Arrival Of a Shipment Service - {}",
            shipmentNotification.getShipmentNumber());
      String messageSequenceNo = IVRSUtils.generateSequenceNumber();
      LocalDateTime requestTime = TenantZoneTime.getZoneDateTime(LocalDateTime.now(),TenantContext.get().getTenantId());
      LocalDate shipmentDate = shipmentDateService.getShipmentDate(shipmentNotification.getShipmentNumber());
      shipmentNotification.setShipmentDate(shipmentDate);
      IVRSRequest request = ivrsRepository.getIVRSRequestData(shipmentNotification);
      if (Objects.nonNull(request)) {
         request.setMessageSequenceNo(messageSequenceNo);
         request.setRequestDateTime(requestTime);
         // If Telephone no provided, modify the contact detail instead of default
         if (!StringUtils.isEmpty(shipmentNotification.getContactInfo())) {
            request.setContactNo(shipmentNotification.getContactInfo());
         }
      } else {
         request = getBaseIVRSRequestData(shipmentNotification, shipmentDate, messageSequenceNo, requestTime);
      }
      BigInteger processorMessageId = ivrsLogger.logIVRSEventMessage(request);
      ivrsProcessor.process(request, IVRSConstants.SYSTEM_IVRS, IVRSConstants.SYSTEM_IVRS);
   }
   
   /**
    * @see com.ngen.cosys.service.ivrs.service.IVRSInterfaceService#sendAirWayBillFaxCopy(com.ngen.cosys.events.payload.ShipmentNotification)
    */
   @Override
   public void sendAirWayBillFaxCopy(ShipmentNotification shipmentNotification) throws CustomException {
      LOGGER.info("IVRS Interface Service :: Send AirWayBill Fax Copy - {}",
            shipmentNotification.getShipmentNumber());
      String messageSequenceNo = IVRSUtils.generateSequenceNumber();
      LocalDateTime requestTime = TenantZoneTime.getZoneDateTime(LocalDateTime.now(), TenantContext.get().getTenantId());
      LocalDate shipmentDate = shipmentDateService.getShipmentDate(shipmentNotification.getShipmentNumber());
      shipmentNotification.setShipmentDate(shipmentDate);
      IVRSRequest request = ivrsRepository.getFaxCopyRequestData(shipmentNotification);
      if (Objects.nonNull(request)) {
         request.setMessageSequenceNo(messageSequenceNo);
         request.setRequestDateTime(requestTime);
         // If FAX no provided, modify the contact detail instead of default
         if (!StringUtils.isEmpty(shipmentNotification.getContactInfo())) {
            request.setContactNo(shipmentNotification.getContactInfo());
         }
      } else {
         request = getBaseIVRSRequestData(shipmentNotification, shipmentDate, messageSequenceNo, requestTime);
      }
      IVRSFaxReportParams reportParams = ivrsRepository.getFaxReportParams(shipmentNotification);
      getReportBase64Content(request, reportParams);
      BigInteger processorMessageId = ivrsLogger.logFAXEventMessage(request);
      ivrsProcessor.process(request, IVRSConstants.SYSTEM_FAX, IVRSConstants.SYSTEM_FAX);
   }

   /**
    * @see com.ngen.cosys.service.ivrs.service.IVRSInterfaceService#acknowledgementStatusUpdate(com.ngen.cosys.service.ivrs.model.IVRSDataResponse)
    */
   @Override
   public IVRSResponse acknowledgementStatusUpdate(IVRSDataResponse dataResponse) throws CustomException {
      String shipmentNumber = dataResponse.getAwbPrefix() + dataResponse.getAwbSuffix();
      LOGGER.info(
            "IVRS Interface Service :: Acknowledgement Status UPDATE Shipment Number - {}, Message Sequence Number - {}",
            shipmentNumber, dataResponse.getMessageSequenceNo());
      LocalDate shipmentDate = shipmentDateService.getShipmentDate(shipmentNumber);
      dataResponse.setShipmentDate(shipmentDate);
      IVRSMessageLog messageLog = ivrsRepository.getMessageLogDetail(dataResponse);
      IVRSResponse response = null;
      if (Objects.nonNull(messageLog)) {
         ivrsLogger.updateMessageDetails(dataResponse, messageLog);
         response = getIVRSAcknowledgementResponse(dataResponse, true);
      } else { // Never happens scenario
         LOGGER.error("IVRS Interface Service :: Acknowledgement Status UPDATE Message Log Details NOT found - {}");
         response = getIVRSAcknowledgementResponse(dataResponse, false);
      }
      return response;
   }

   /**
    * @see com.ngen.cosys.service.ivrs.service.IVRSInterfaceService#enquireAirWayBillDetail(com.ngen.cosys.service.ivrs.model.IVRSAWBRequest)
    */
   @Override
   public IVRSAWBResponse enquireAirWayBillDetail(IVRSAWBRequest dataRequest) throws CustomException {
      String shipmentNumber = dataRequest.getAwbPrefix() + dataRequest.getAwbSuffix();
      LOGGER.info("IVRS Interface Service :: Enquire AirWayBill Details - {}, Message Sequence Number - {}",
            shipmentNumber, dataRequest.getMessageSequenceNo());
      LocalDate shipmentDate = shipmentDateService.getShipmentDate(shipmentNumber);
      dataRequest.setShipmentDate(shipmentDate);
      IVRSAWBResponse dataResponse = null;
      if (Objects.nonNull(shipmentDate)) {
         dataResponse = ivrsRepository.getAirWayBillDetail(dataRequest);
      }
      if (Objects.isNull(dataResponse)) {
         LOGGER.info(
               "IVRS Interface Service :: Enquire AirWayBill Details NOT found - {}, Message Sequence Number - {}",
               shipmentNumber, dataRequest.getMessageSequenceNo());
         dataResponse = enquireAWBErrorResponse(dataRequest);
         dataResponse.setShipmentDate(shipmentDate);
      }
      ivrsLogger.logIncomingMessage(dataResponse);
      return dataResponse;
   }
   
   /**
    * @param dataRequest
    * @param reportParams
    * @return
    * @throws CustomException
    */
   private void getReportBase64Content(IVRSRequest dataRequest, IVRSFaxReportParams reportParams)
         throws CustomException {
      String shipmentNumber = dataRequest.getAwbPrefix() + dataRequest.getAwbSuffix();
      LOGGER.info(
            "IVRS Interface Service :: GET Report PDF Base64 Content for the Shipment - {}, Message Sequence Number - {}",
            shipmentNumber, dataRequest.getMessageSequenceNo());
      String reportURL = ivrsConfig.getInterfaceURL(IVRSConstants.SYSTEM_REPORT);
      ReportRequest report = getReportRequest(reportParams);
      ResponseEntity<Object> response = ServiceUtil.routeJSONResponse(report, reportURL);
      for (IVRSRequest.AWBDetail awbDetail : dataRequest.getAwbDetails()) {
         awbDetail.setBase64Content(IVRSUtils.readBase64Content(response.getBody()));
         break;
      }
   }
   
   /**
    * @param reportParams
    * @return
    */
   private ReportRequest getReportRequest(IVRSFaxReportParams reportParams) {
      //
      ReportRequest report = new ReportRequest();
      report.setReportName(IVRS_FAX_REPORT);
      report.setRequestType(ReportRequestType.EMAIL);
      report.setPrinterType(PrinterType.LASER);
      report.setFormat(ReportFormat.PDF);
      report.setParameters(getReportParams(reportParams));
      //
      return report;
   }
   
   /**
    * @param reportParams
    * @return
    */
   private Map<String, Object> getReportParams(IVRSFaxReportParams reportParams) {
      Map<String, Object> params = new HashMap<>();
      params.put(PARAM_AWB, reportParams.getShipmentNumber());
      params.put(PARAM_ORIGIN, reportParams.getOrigin());
      params.put(PARAM_PIECES, String.valueOf(reportParams.getPieces()));
      params.put(PARAM_WEIGHT, String.valueOf(reportParams.getWeight()));
      params.put(PARAM_FLIGHT_KEY, reportParams.getFlightKey());
      String arrivedDate = reportParams.getArrivedDate().format(DateTimeFormatter.ofPattern(DATE_FORMAT)).toUpperCase();
      params.put(PARAM_DATE_ARRIVED, arrivedDate);
      params.put(PARAM_CONSIGNEE_NAME, reportParams.getConsigneeName());
      params.put(PARAM_STAFF_NO, null);
      return params;
   }
   
   /**
    * @param shipmentNotification
    * @param shipmentDate
    * @param messageSequenceNo
    * @param requestTime
    * @return
    */
   private IVRSRequest getBaseIVRSRequestData(ShipmentNotification shipmentNotification, LocalDate shipmentDate,
         String messageSequenceNo, LocalDateTime requestTime) {
      String shipmentNumber = shipmentNotification.getShipmentNumber();
      IVRSRequest dataRequest = new IVRSRequest();
      dataRequest.setMessageSequenceNo(messageSequenceNo);
      dataRequest.setRequestDateTime(requestTime);
      dataRequest.setAwbPrefix(shipmentNumber.substring(0, 3));
      dataRequest.setAwbSuffix(shipmentNumber.substring(3, 11));
      dataRequest.setShipmentDate(shipmentDate);
      return dataRequest;
   }
   
   /**
    * @param dataResponse
    * @param success
    * @return
    */
   private IVRSResponse getIVRSAcknowledgementResponse(IVRSDataResponse dataResponse, boolean success) {
      IVRSResponse response = new IVRSResponse();
      response.setMessageSequenceNo(dataResponse.getMessageSequenceNo());
      response.setRequestDateTime(dataResponse.getRequestDateTime());
      if (success) {
         response.setStatus(IVRSConstants.SUCCESS_RESPONSE);
      } else {
         response.setStatus(IVRSConstants.FAILURE_RESPONSE);
         response.setErrorCode(IVRSConstants.FAILURE_UPDATE_ERROR_CODE);
         response.setErrorDescription(IVRSConstants.FAILURE_UPDATE_ERROR_DESC);
      }
      return response;
   }
   
   /**
    * @param dataRequest
    * @return
    */
   private IVRSAWBResponse enquireAWBErrorResponse(IVRSAWBRequest dataRequest) {
      IVRSAWBResponse dataResponse = new IVRSAWBResponse();
      dataResponse.setMessageSequenceNo(dataRequest.getMessageSequenceNo());
      dataResponse.setRequestDateTime(dataRequest.getRequestDateTime());
      dataResponse.setAwbPrefix(dataRequest.getAwbPrefix());
      dataResponse.setAwbSuffix(dataRequest.getAwbSuffix());
      dataResponse.setStatus(IVRSConstants.FAILURE_RESPONSE);
      dataResponse.setErrorCode(IVRSConstants.FAILURE_ERROR_CODE);
      dataResponse.setErrorDescription(IVRSConstants.FAILURE_ERROR_DESC);
      return dataResponse;
   }
   
}
