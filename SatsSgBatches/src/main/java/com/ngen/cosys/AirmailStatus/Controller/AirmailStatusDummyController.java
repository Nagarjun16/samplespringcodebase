package com.ngen.cosys.AirmailStatus.Controller;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ibsplc.icargo.business.mailtracking.defaults.types.standard.MailDetailsRequestType;
import com.ibsplc.icargo.business.mailtracking.defaults.types.standard.SaveMailDetailsRequestType;
import com.ibsplc.icargo.business.mailtracking.defaults.types.standard.SaveMailDetailsResponseType;
import com.ngen.cosys.AirmailStatus.DAO.AirmailStatusDAO;
import com.ngen.cosys.AirmailStatus.Model.AirmailStatusChildModel;
import com.ngen.cosys.AirmailStatus.Model.AirmailStatusFlightModel;
import com.ngen.cosys.AirmailStatus.Service.AirmailStatusService;
import com.ngen.cosys.AirmailStatus.Service.CamsConnectorConfigService;
import com.ngen.cosys.annotation.service.ShipmentProcessorService;
import com.ngen.cosys.events.consumer.logger.service.ApplicationLoggerService;
import com.ngen.cosys.events.esb.connector.logger.payload.OutgoingMessageErrorLog;
import com.ngen.cosys.events.esb.connector.logger.payload.OutgoingMessageLog;
import com.ngen.cosys.events.payload.AirmailStatusEvent;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.multitenancy.utilities.MultiTenantUtility;
import com.ngen.cosys.scheduler.esb.connector.jackson.util.JacksonUtility;

import io.swagger.annotations.ApiOperation;

@Controller
public class AirmailStatusDummyController {

   @Autowired
   AirmailStatusService service;

   @Autowired
   ApplicationLoggerService loggerService;
   @Autowired
   private ShipmentProcessorService shipmentProcessorService;

   @Autowired
   private CamsConnectorConfigService camsConfigService;

   @Autowired
   AirmailStatusDAO airmailStatusDao;

   private static Logger logger = LoggerFactory.getLogger(AirmailStatusDummyController.class);

   private static final String ARR = "ARR";
   private static final String ACP = "ACP";
   private static final String TRA = "TRA";
   private static final String EXP = "EXP";
   private static final String RTN = "RTN";
   private static final String DMG = "DMG";
   private static final String DEL = "DEL";
   private static final String DLV = "DLV";
   private static final String RSN = "RSN";
   private static final String CNL = "CNL";
   private static final String CAMS = "CAMS";
   private static final String IMPORTBREAKDOWN = "IMPORTBREAKDOWN";
   private static final String IMPORTBREAKDOWNUPDATELOCATION = "IMPORTBREAKDOWNUPDATELOCATION";
   private static final String LYINGLISTUPDATELOCATION = "ContainerToConatinerMovement";
   private static final String EXPORTACCEPTANCE = "EXPORTACCEPTANCE";
   private static final String ASSIGNCONTAINERDEST = "ASSIGNCONTAINERDESTINATION";
   private static final String TRANSFERMANIFEST = "TRANSFERMANIFEST";
   private static final String MANIFESTCOMPLETE = "EXPMANIFESTMANIFESTCOMPLETE";
   private static final String MANIFESTUNLOAD = "MANIFESTUNLOAD";
   private static final String MANIFESTUNLOADWHOLEULDORMT = "MANIFESTUNLOADWHOLEULDORMT";
   private static final String MAILTRACKING = "mailtracking.defaults";
   private static final String DATEFORMAT = "dd-MMM-yyyy";
   private static final String DATETIMEFORMAT = "dd-MMM-yyyy HH:mm:ss";
   private static final String COMPANYCODE = "SQ";
   private static final String FALSE = "F";
   private static final String SENT = "SENT";
   private static final String ERROR = "ERROR";
   private static final String HTTP = "HTTP";
   private static final String CONNECTION_FAILURE = "Connection Failure";
   private static final String SUCCESS = "Success";
   private static final String CAMSERROR = "CAMSERROR";
   private static final String CUSTOMEXCEPTION = "Custom Exception";

   @Transactional(rollbackFor = Throwable.class)
   @ApiOperation("Get stored events")
   @RequestMapping(value = "/api/dummy/getstoredeventsdata", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
   public void getAirmailStoredEvents() throws CustomException {
      List<AirmailStatusEvent> allData = null;
      try {
         allData = service.getAirmailStoredEvents();
         logger.warn("All Data with Created status in AirmailStatusJob: " + allData);
         for (AirmailStatusEvent value : allData) {
            value.setStatus("PENDING");
            service.updateStatus(value);
            logger.warn("Event Type: " + value.getEventsType());
            switch (value.getEventsType()) {
            case ARR:
               performARREvent(value);
               break;
            case EXP:
               performEXPEvent(value);
               break;
            case TRA:
               performTRAEvent(value);
               break;
            case ACP:
               performACPEvent(value);
               break;
            case RTN:
               performRTNEvent(value);
               break;
            case DMG:
               performCaptureDamage(value);
               break;
            case DEL:
               performDELEvent(value);
               break;
            case DLV:
               performDLVEvent(value);
               break;
            case RSN:
               performRSNEvent(value);
               break;
            case CNL:
               performCNLEvent(value);
               break;
            default:
               break;
            }
            value.setStatus("TRANSFERRED");
            service.updateStatus(value);
            logger.warn("Status updated as Transferred in AirmailStatusJob");
         }
      } catch (CustomException e) {
         logger.warn(e.toString());
      }

   }

   private void performARREvent(AirmailStatusEvent value) {
      if ("T".equalsIgnoreCase(value.getImportExportIndicator())) {
         logger.warn("Inside ARR event for Transit");
         arrEventForTransit(value);
      } else if ("I".equalsIgnoreCase(value.getImportExportIndicator())) {
         logger.warn("Inside ARR event for Import");
         arrEventForimport(value);
      }

   }

   private void arrEventForTransit(AirmailStatusEvent value) {
      logger.warn("Started executing the ARR event for Transit");
      AirmailStatusChildModel brkdwnModel = new AirmailStatusChildModel();
      brkdwnModel.setProduct(MAILTRACKING);
      brkdwnModel.setEventType(ARR);
      brkdwnModel.setFlightId(value.getFlightId());
      AirmailStatusFlightModel flightInfo = null;
      try {
         flightInfo = service.getFlightInformationForImport(brkdwnModel);
         if (Objects.nonNull(flightInfo)) {
            SaveMailDetailsRequestType parentModel = new SaveMailDetailsRequestType();
            MailDetailsRequestType childModel = new MailDetailsRequestType();
            List<MailDetailsRequestType> childModelList = new ArrayList<>();
            childModel.setProduct(MAILTRACKING);
            childModel.setScanType(ARR);
            childModel.setCarrierCode(flightInfo.getCarrierCode());
            childModel.setFlightNumber(flightInfo.getFlightNumber());
            childModel.setFlightDate(flightInfo.getDateSTA().format(DateTimeFormatter.ofPattern(DATEFORMAT)));
            childModel.setMailTag(value.getMailBag());
            childModel.setContainerNumber(value.getStoreLocation());
            childModel.setSerialNumber("1");
            childModel.setUserName(value.getCreatedBy());
            childModel.setScanDateTime(value.getCreatedOn().format(DateTimeFormatter.ofPattern(DATETIMEFORMAT)));
            parentModel.setCompanyCode(COMPANYCODE);
            parentModel.setHhtVersion("1.0");
            parentModel.setScanningPort(MultiTenantUtility.getAirportCodeFromContext());
            parentModel.setMessagePartId(1);
            childModelList.add(childModel);
            parentModel.setMailDetails(childModelList);
            SaveMailDetailsResponseType response = new SaveMailDetailsResponseType();
            try {
               logger.warn("Going to trigger the message to the URL For ARR Import without BUP");
               response = camsConfigService.connectorConfig(parentModel);
               logger.warn("Message triggered to the url for ARR Import without BUP");
               convertIntoXmlPayload(parentModel, value, response, SUCCESS, null);
            } catch (Throwable e) {
               // tracing the connection failures
               logger.warn("error happened" + e.getMessage());
               convertIntoXmlPayload(parentModel, value, response, CONNECTION_FAILURE, e.getMessage());
            }

         }
      } catch (CustomException e) {
         logger.warn(e.getMessage());
         saveFailedDataIntoOutgoingMessageLog(value, CUSTOMEXCEPTION, e.getMessage());
      } catch (Throwable e) {
         logger.warn(e.toString());
         saveFailedDataIntoOutgoingMessageLog(value, ERROR, e.getMessage());
      }

   }

   private void arrEventForimport(AirmailStatusEvent value) {

      if (value.isBup()) {
         logger.warn("Started executing the ARR event for Import having BUP");
         AirmailStatusChildModel brkdwnModel = new AirmailStatusChildModel();
         brkdwnModel.setProduct(MAILTRACKING);
         brkdwnModel.setEventType(ARR);
         brkdwnModel.setFlightId(value.getFlightId());
         AirmailStatusFlightModel flightInfo = null;
         try {
            flightInfo = service.getFlightInformationForImport(brkdwnModel);
            if (Objects.nonNull(flightInfo)) {
               SaveMailDetailsRequestType parentModel = new SaveMailDetailsRequestType();
               MailDetailsRequestType childModel = new MailDetailsRequestType();
               List<MailDetailsRequestType> childModelList = new ArrayList<>();
               childModel.setProduct(MAILTRACKING);
               childModel.setScanType(ARR);
               childModel.setCarrierCode(flightInfo.getCarrierCode());
               childModel.setFlightNumber(flightInfo.getFlightNumber());
               childModel.setFlightDate(flightInfo.getDateSTA().format(DateTimeFormatter.ofPattern(DATEFORMAT)));
               childModel.setContainerNumber(value.getStoreLocation());
               childModel.setMailTag(value.getMailBag());
               childModel.setSerialNumber("1");
               childModel.setIsPABuilt("Y");
               childModel.setUserName(value.getCreatedBy());
               childModel.setScanDateTime(value.getCreatedOn().format(DateTimeFormatter.ofPattern(DATETIMEFORMAT)));
               parentModel.setCompanyCode(COMPANYCODE);
               parentModel.setHhtVersion("1.0");
               parentModel.setScanningPort(MultiTenantUtility.getAirportCodeFromContext());
               parentModel.setMessagePartId(1);
               childModelList.add(childModel);
               parentModel.setMailDetails(childModelList);
               SaveMailDetailsResponseType response = new SaveMailDetailsResponseType();
               try {
                  logger.warn("Going to trigger the message to the URL For ARR Import without BUP");
                  response = camsConfigService.connectorConfig(parentModel);
                  logger.warn("Message triggered to the url for ARR Import without BUP");
                  convertIntoXmlPayload(parentModel, value, response, SUCCESS, null);
               } catch (Throwable e) {
                  // tracing the connection failures
                  logger.warn("error happened" + e.getMessage());
                  convertIntoXmlPayload(parentModel, value, response, CONNECTION_FAILURE, e.getMessage());
               }
            }
         } catch (CustomException e) {
            logger.warn(e.getMessage());
            saveFailedDataIntoOutgoingMessageLog(value, CUSTOMEXCEPTION, e.getMessage());
         } catch (Throwable e) {
            logger.warn(e.toString());
            saveFailedDataIntoOutgoingMessageLog(value, ERROR, e.getMessage());
         }

      } else {
         logger.warn("Started executing the ARR event for Import not having BUP");
         AirmailStatusChildModel brkdwnModel = new AirmailStatusChildModel();
         brkdwnModel.setProduct(MAILTRACKING);
         brkdwnModel.setEventType(ARR);
         brkdwnModel.setFlightId(value.getFlightId());
         AirmailStatusFlightModel flightInfo = null;
         try {
            flightInfo = service.getFlightInformationForImport(brkdwnModel);
            if (Objects.nonNull(flightInfo)) {
               SaveMailDetailsRequestType parentModel = new SaveMailDetailsRequestType();
               MailDetailsRequestType childModel = new MailDetailsRequestType();
               List<MailDetailsRequestType> childModelList = new ArrayList<>();
               childModel.setProduct(MAILTRACKING);
               childModel.setScanType(ARR);
               childModel.setCarrierCode(flightInfo.getCarrierCode());
               childModel.setFlightNumber(flightInfo.getFlightNumber());
               childModel.setFlightDate(flightInfo.getDateSTA().format(DateTimeFormatter.ofPattern(DATEFORMAT)));
               childModel.setMailTag(value.getMailBag());
               childModel.setSerialNumber("1");
               childModel.setUserName(value.getCreatedBy());
               childModel.setScanDateTime(value.getCreatedOn().format(DateTimeFormatter.ofPattern(DATETIMEFORMAT)));
               parentModel.setCompanyCode(COMPANYCODE);
               parentModel.setHhtVersion("1.0");
               parentModel.setScanningPort(MultiTenantUtility.getAirportCodeFromContext());
               parentModel.setMessagePartId(1);
               childModelList.add(childModel);
               parentModel.setMailDetails(childModelList);
               SaveMailDetailsResponseType response = new SaveMailDetailsResponseType();
               try {
                  logger.warn("Going to trigger the message to the URL For ARR Import without BUP");
                  response = camsConfigService.connectorConfig(parentModel);
                  logger.warn("Message triggered to the url for ARR Import without BUP");
                  convertIntoXmlPayload(parentModel, value, response, SUCCESS, null);
               } catch (Throwable e) {
                  // tracing the connection failures
                  logger.warn("error happened" + e.getMessage());
                  convertIntoXmlPayload(parentModel, value, response, CONNECTION_FAILURE, e.getMessage());
               }
            }
         } catch (CustomException e) {
            logger.warn(e.getMessage());
            saveFailedDataIntoOutgoingMessageLog(value, CUSTOMEXCEPTION, e.getMessage());
         } catch (Throwable e) {
            logger.warn(e.toString());
            saveFailedDataIntoOutgoingMessageLog(value, ERROR, e.getMessage());
         }

      }

   }

   private void performEXPEvent(AirmailStatusEvent value) {
      try {
         logger.warn("Started executing the EXP event");
         AirmailStatusChildModel brkdwnModel = new AirmailStatusChildModel();
         brkdwnModel.setProduct(MAILTRACKING);
         brkdwnModel.setEventType(EXP);
         brkdwnModel.setShipmentId(value.getShipmentId());
         SaveMailDetailsRequestType parentModel = new SaveMailDetailsRequestType();
         MailDetailsRequestType childModel = new MailDetailsRequestType();
         List<MailDetailsRequestType> childModelList = new ArrayList<>();
         if (MANIFESTCOMPLETE.equalsIgnoreCase(value.getSourceTriggerType())) {
            AirmailStatusEvent flightData = service.getFlightInformationForExport(value);
            if (Optional.ofNullable(flightData).isPresent()) {
               childModel.setFlightNumber(flightData.getFlightNumber());
               childModel.setFlightDate(flightData.getFlightDate().format(DateTimeFormatter.ofPattern(DATEFORMAT)));
            }
            childModel.setContainerPou(flightData.getFlightOffPoint());
         }
         childModel.setProduct(MAILTRACKING);
         childModel.setScanType(EXP);
         if (!StringUtils.isEmpty(value.getManifestedUldKey())) {
            childModel.setContainerNumber(value.getManifestedUldKey());
            childModel.setContainerType(value.getManifestedUldType());
         } else {
            childModel.setContainerNumber(value.getStoreLocation());
            childModel.setContainerType(value.getStoreLocationType());
         }
         childModel.setCarrierCode(value.getCarrierCode());
         childModel.setContainerDestination(value.getContainerDestination());
         childModel.setSerialNumber("1");
         if (value.isBup()) {
            childModel.setIsPABuilt("Y");
         }
         childModel.setUserName(value.getCreatedBy());
         childModel.setScanDateTime(value.getCreatedOn().format(DateTimeFormatter.ofPattern(DATETIMEFORMAT)));
         parentModel.setCompanyCode(COMPANYCODE);
         parentModel.setHhtVersion("1.0");
         parentModel.setScanningPort(MultiTenantUtility.getAirportCodeFromContext());
         parentModel.setMessagePartId(1);
         childModelList.add(childModel);
         parentModel.setMailDetails(childModelList);
         SaveMailDetailsResponseType response = new SaveMailDetailsResponseType();
         try {
            logger.warn("Going to trigger the message to the URL For ARR Import without BUP");
            response = camsConfigService.connectorConfig(parentModel);
            logger.warn("Message triggered to the url for ARR Import without BUP");
            convertIntoXmlPayload(parentModel, value, response, SUCCESS, null);
         } catch (Throwable e) {
            // tracing the connection failures
            logger.warn("error happened" + e.getMessage());
            convertIntoXmlPayload(parentModel, value, response, CONNECTION_FAILURE, e.getMessage());
         }
      } catch (CustomException e) {
         logger.warn(e.getMessage());
         saveFailedDataIntoOutgoingMessageLog(value, CUSTOMEXCEPTION, e.getMessage());
      } catch (Throwable e) {
         logger.warn(e.toString());
         saveFailedDataIntoOutgoingMessageLog(value, ERROR, e.getMessage());
      }

   }

   private void performTRAEvent(AirmailStatusEvent value) {
      if (value.getCarrierCode().equalsIgnoreCase(value.getTransferCarrierTo())
            || StringUtils.isEmpty(value.getTransferCarrierTo())) {
         traEventForSameCarrier(value);
      } else {
         traEventForAnotherCarrier(value);
      }

   }

   private void traEventForAnotherCarrier(AirmailStatusEvent value) {
      logger.warn("Started executing the TRA event for another carrier");
      try {
         SaveMailDetailsRequestType parentModel = new SaveMailDetailsRequestType();
         MailDetailsRequestType childModel = new MailDetailsRequestType();
         List<MailDetailsRequestType> childModelList = new ArrayList<>();
         childModel.setProduct(MAILTRACKING);
         childModel.setScanType(TRA);
         childModel.setMailTag(value.getMailBag());
         if (!StringUtils.isEmpty(value.getTransferCarrierTo())) {
            childModel.setToCarrierCode(value.getTransferCarrierTo());
         } else {
            childModel.setToCarrierCode(value.getCarrierCode());
         }
         if (value.isBup()) {
            childModel.setContainerNumber(value.getStoreLocation());
         }
         childModel.setSerialNumber("1");
         childModel.setCarrierCode(value.getCarrierCode());
         childModel.setUserName(value.getCreatedBy());
         childModel.setScanDateTime(value.getCreatedOn().format(DateTimeFormatter.ofPattern(DATETIMEFORMAT)));
         parentModel.setCompanyCode(COMPANYCODE);
         parentModel.setHhtVersion("1.0");
         parentModel.setScanningPort(MultiTenantUtility.getAirportCodeFromContext());
         parentModel.setMessagePartId(1);
         childModelList.add(childModel);
         parentModel.setMailDetails(childModelList);
         SaveMailDetailsResponseType response = new SaveMailDetailsResponseType();
         try {
            logger.warn("Going to trigger the message to the URL For ARR Import without BUP");
            response = camsConfigService.connectorConfig(parentModel);
            logger.warn("Message triggered to the url for ARR Import without BUP");
            convertIntoXmlPayload(parentModel, value, response, SUCCESS, null);
         } catch (Throwable e) {
            // tracing the connection failures
            logger.warn("error happened" + e.getMessage());
            convertIntoXmlPayload(parentModel, value, response, CONNECTION_FAILURE, e.getMessage());
         }
      } catch (Throwable e) {
         saveFailedDataIntoOutgoingMessageLog(value, ERROR, e.getMessage());
      }
   }

   private void traEventForSameCarrier(AirmailStatusEvent value) {
      traEventForAnotherCarrier(value);

   }

   private void performACPEvent(AirmailStatusEvent value) {
      if (value.getSourceTriggerType().equalsIgnoreCase(IMPORTBREAKDOWN)) {
         performACPForBreakdown(value);
      } else if (value.getSourceTriggerType().equalsIgnoreCase(EXPORTACCEPTANCE)
            || value.getSourceTriggerType().equalsIgnoreCase(ASSIGNCONTAINERDEST)
            || value.getSourceTriggerType().equalsIgnoreCase(TRANSFERMANIFEST)) {
         performACPForExportAcceptance(value);
      } else if (value.getSourceTriggerType().equalsIgnoreCase(IMPORTBREAKDOWNUPDATELOCATION)
            || value.getSourceTriggerType().equalsIgnoreCase(LYINGLISTUPDATELOCATION)
            || value.getSourceTriggerType().equalsIgnoreCase(MANIFESTUNLOAD)
            || value.getSourceTriggerType().equalsIgnoreCase(MANIFESTUNLOADWHOLEULDORMT)) {
         performACPForUpdateLocation(value);
      } else if (value.getSourceTriggerType().equalsIgnoreCase(MANIFESTCOMPLETE)) {
         performACPForManifestComplete(value);
      }

   }

   private void performACPForBreakdown(AirmailStatusEvent value) {
      try {
         logger.warn("Started executing the ACP event  for breakdown");
         SaveMailDetailsRequestType parentModel = new SaveMailDetailsRequestType();
         MailDetailsRequestType childModel = new MailDetailsRequestType();
         List<MailDetailsRequestType> childModelList = new ArrayList<>();
         childModel.setProduct(MAILTRACKING);
         childModel.setScanType(ACP);
         childModel.setContainerNumber(value.getStoreLocation());
         childModel.setMailTag(value.getMailBag());
         childModel.setSerialNumber("1");
         childModel.setUserName(value.getCreatedBy());
         childModel.setScanDateTime(value.getCreatedOn().format(DateTimeFormatter.ofPattern(DATETIMEFORMAT)));
         parentModel.setCompanyCode(COMPANYCODE);
         parentModel.setHhtVersion("1.0");
         parentModel.setScanningPort(MultiTenantUtility.getAirportCodeFromContext());
         parentModel.setMessagePartId(1);
         childModelList.add(childModel);
         parentModel.setMailDetails(childModelList);
         SaveMailDetailsResponseType response = new SaveMailDetailsResponseType();
         try {
            logger.warn("Going to trigger the message to the URL For ARR Import without BUP");
            response = camsConfigService.connectorConfig(parentModel);
            logger.warn("Message triggered to the url for ARR Import without BUP");
            convertIntoXmlPayload(parentModel, value, response, SUCCESS, null);
         } catch (Throwable e) {
            // tracing the connection failures
            logger.warn("error happened" + e.getMessage());
            convertIntoXmlPayload(parentModel, value, response, CONNECTION_FAILURE, e.getMessage());
         }

      } catch (Throwable e) {
         logger.warn(e.toString());
         saveFailedDataIntoOutgoingMessageLog(value, ERROR, e.getMessage());
      }

   }

   private void performACPForExportAcceptance(AirmailStatusEvent value) {
      try {
         // Accept a mail Bag from PA-With Container/ without container(Next
         // destination+STR)
         logger.warn("Started executing the ACP event  for export acceptance");
         SaveMailDetailsRequestType parentModel = new SaveMailDetailsRequestType();
         MailDetailsRequestType childModel = new MailDetailsRequestType();
         List<MailDetailsRequestType> childModelList = new ArrayList<>();
         childModel.setProduct(MAILTRACKING);
         childModel.setScanType(ACP);
         childModel.setCarrierCode(value.getCarrierCode());
         childModel.setContainerNumber(value.getStoreLocation());
         childModel.setMailTag(value.getMailBag());
         childModel.setSerialNumber("1");
         childModel.setUserName(value.getCreatedBy());
         childModel.setScanDateTime(value.getCreatedOn().format(DateTimeFormatter.ofPattern(DATETIMEFORMAT)));
         parentModel.setCompanyCode(COMPANYCODE);
         parentModel.setHhtVersion("1.0");
         parentModel.setScanningPort(MultiTenantUtility.getAirportCodeFromContext());
         parentModel.setMessagePartId(1);
         childModelList.add(childModel);
         parentModel.setMailDetails(childModelList);
         SaveMailDetailsResponseType response = new SaveMailDetailsResponseType();
         try {
            logger.warn("Going to trigger the message to the URL For ARR Import without BUP");
            response = camsConfigService.connectorConfig(parentModel);
            logger.warn("Message triggered to the url for ARR Import without BUP");
            convertIntoXmlPayload(parentModel, value, response, SUCCESS, null);
         } catch (Throwable e) {
            // tracing the connection failures
            logger.warn("error happened" + e.getMessage());
            convertIntoXmlPayload(parentModel, value, response, CONNECTION_FAILURE, e.getMessage());
         }
      } catch (Throwable e) {
         logger.warn(e.toString());
         saveFailedDataIntoOutgoingMessageLog(value, ERROR, e.getMessage());
      }

   }

   private void performACPForUpdateLocation(AirmailStatusEvent value) {
      // Remove a mailBag From a container MT
      try {
         logger.warn("Started executing the ACP event  for update location");
         SaveMailDetailsRequestType parentModel = new SaveMailDetailsRequestType();
         MailDetailsRequestType childModel = new MailDetailsRequestType();
         List<MailDetailsRequestType> childModelList = new ArrayList<>();
         childModel.setProduct(MAILTRACKING);
         childModel.setScanType(ACP);
         childModel.setCarrierCode(value.getCarrierCode());
         childModel.setContainerNumber(value.getStoreLocation());
         childModel.setMailTag(value.getMailBag());
         childModel.setSerialNumber("1");
         childModel.setUserName(value.getCreatedBy());
         childModel.setScanDateTime(value.getCreatedOn().format(DateTimeFormatter.ofPattern(DATETIMEFORMAT)));
         parentModel.setCompanyCode(COMPANYCODE);
         parentModel.setHhtVersion("1.0");
         parentModel.setScanningPort(MultiTenantUtility.getAirportCodeFromContext());
         parentModel.setMessagePartId(1);
         childModelList.add(childModel);
         parentModel.setMailDetails(childModelList);
         SaveMailDetailsResponseType response = new SaveMailDetailsResponseType();
         try {
            logger.warn("Going to trigger the message to the URL For ARR Import without BUP");
            response = camsConfigService.connectorConfig(parentModel);
            logger.warn("Message triggered to the url for ARR Import without BUP");
            convertIntoXmlPayload(parentModel, value, response, SUCCESS, null);
         } catch (Throwable e) {
            // tracing the connection failures
            logger.warn("error happened" + e.getMessage());
            convertIntoXmlPayload(parentModel, value, response, CONNECTION_FAILURE, e.getMessage());
         }

      } catch (Throwable e) {
         logger.warn(e.toString());
         saveFailedDataIntoOutgoingMessageLog(value, ERROR, e.getMessage());
      }

   }

   private void performACPForManifestComplete(AirmailStatusEvent value) {
      try {
         logger.warn("Started executing the ACP event  for update location");
         SaveMailDetailsRequestType parentModel = new SaveMailDetailsRequestType();
         MailDetailsRequestType childModel = new MailDetailsRequestType();
         List<MailDetailsRequestType> childModelList = new ArrayList<>();
         childModel.setProduct(MAILTRACKING);
         childModel.setScanType(ACP);
         childModel.setCarrierCode(value.getCarrierCode());
         childModel.setContainerNumber(value.getManifestedUldKey());
         childModel.setMailTag(value.getMailBag());
         AirmailStatusEvent flightData = service.getFlightInformationForExport(value);
         if (Optional.ofNullable(flightData).isPresent()) {
            childModel.setFlightNumber(flightData.getFlightNumber());
            childModel.setFlightDate(flightData.getFlightDate().format(DateTimeFormatter.ofPattern(DATEFORMAT)));
         }
         childModel.setSerialNumber("1");
         childModel.setUserName(value.getCreatedBy());
         childModel.setScanDateTime(value.getCreatedOn().format(DateTimeFormatter.ofPattern(DATETIMEFORMAT)));
         parentModel.setCompanyCode(COMPANYCODE);
         parentModel.setHhtVersion("1.0");
         parentModel.setScanningPort(MultiTenantUtility.getAirportCodeFromContext());
         parentModel.setMessagePartId(1);
         childModelList.add(childModel);
         parentModel.setMailDetails(childModelList);
         SaveMailDetailsResponseType response = new SaveMailDetailsResponseType();
         try {
            logger.warn("Going to trigger the message to the URL For ARR Import without BUP");
            response = camsConfigService.connectorConfig(parentModel);
            logger.warn("Message triggered to the url for ARR Import without BUP");
            convertIntoXmlPayload(parentModel, value, response, SUCCESS, null);
         } catch (Throwable e) {
            // tracing the connection failures
            logger.warn("error happened" + e.getMessage());
            convertIntoXmlPayload(parentModel, value, response, CONNECTION_FAILURE, e.getMessage());
         }
      } catch (CustomException e) {
         logger.warn(e.getMessage());
         saveFailedDataIntoOutgoingMessageLog(value, CUSTOMEXCEPTION, e.getMessage());
      } catch (Throwable e) {
         logger.warn(e.toString());
         saveFailedDataIntoOutgoingMessageLog(value, ERROR, e.getMessage());
      }

   }

   private void performRTNEvent(AirmailStatusEvent value) {

      try {
         logger.warn("Started executing the RTN event");
         SaveMailDetailsRequestType parentModel = new SaveMailDetailsRequestType();
         MailDetailsRequestType childModel = new MailDetailsRequestType();
         List<MailDetailsRequestType> childModelList = new ArrayList<>();
         childModel.setProduct(MAILTRACKING);
         childModel.setScanType(RTN);
         childModel.setCarrierCode(value.getCarrierCode());
         childModel.setRemarks(value.getRemarks());
         childModel.setMailTag(value.getMailBag());
         if (Optional.ofNullable(value.getReturnCode()).isPresent()) {
            childModel.setReturnCode(value.getReturnCode().toString());
         }
         childModel.setSerialNumber("1");
         childModel.setUserName(value.getCreatedBy());
         childModel.setScanDateTime(value.getCreatedOn().format(DateTimeFormatter.ofPattern(DATETIMEFORMAT)));
         parentModel.setCompanyCode(COMPANYCODE);
         parentModel.setHhtVersion("1.0");
         parentModel.setScanningPort(MultiTenantUtility.getAirportCodeFromContext());
         parentModel.setMessagePartId(1);
         childModelList.add(childModel);
         parentModel.setMailDetails(childModelList);
         SaveMailDetailsResponseType response = new SaveMailDetailsResponseType();
         try {
            logger.warn("Going to trigger the message to the URL For ARR Import without BUP");
            response = camsConfigService.connectorConfig(parentModel);
            logger.warn("Message triggered to the url for ARR Import without BUP");
            convertIntoXmlPayload(parentModel, value, response, SUCCESS, null);
         } catch (Throwable e) {
            // tracing the connection failures
            logger.warn("error happened" + e.getMessage());
            convertIntoXmlPayload(parentModel, value, response, CONNECTION_FAILURE, e.getMessage());
         }

      } catch (Throwable e) {
         logger.warn(e.toString());
         saveFailedDataIntoOutgoingMessageLog(value, ERROR, e.getMessage());
      }

   }

   private void performCaptureDamage(AirmailStatusEvent value) {
      logger.warn("Started executing the DMG event");
      AirmailStatusChildModel brkdwnModel = new AirmailStatusChildModel();
      brkdwnModel.setMailBag(value.getMailBag());
      try {
         SaveMailDetailsRequestType parentModel = new SaveMailDetailsRequestType();
         MailDetailsRequestType childModel = new MailDetailsRequestType();
         List<MailDetailsRequestType> childModelList = new ArrayList<>();
         childModel.setProduct(MAILTRACKING);
         childModel.setScanType(DMG);
         childModel.setCarrierCode(value.getCarrierCode());
         childModel.setDamageRemarks(value.getRemarks());
         childModel.setDamageCode("100");
         childModel.setMailTag(value.getMailBag());
         childModel.setSerialNumber("1");
         childModel.setUserName(value.getCreatedBy());
         childModel.setScanDateTime(value.getCreatedOn().format(DateTimeFormatter.ofPattern(DATETIMEFORMAT)));
         parentModel.setCompanyCode(COMPANYCODE);
         parentModel.setHhtVersion("1.0");
         parentModel.setScanningPort(MultiTenantUtility.getAirportCodeFromContext());
         parentModel.setMessagePartId(1);
         childModelList.add(childModel);
         parentModel.setMailDetails(childModelList);
         SaveMailDetailsResponseType response = new SaveMailDetailsResponseType();
         try {
            logger.warn("Going to trigger the message to the URL For ARR Import without BUP");
            response = camsConfigService.connectorConfig(parentModel);
            logger.warn("Message triggered to the url for ARR Import without BUP");
            convertIntoXmlPayload(parentModel, value, response, SUCCESS, null);
         } catch (Throwable e) {
            // tracing the connection failures
            logger.warn("error happened" + e.getMessage());
            convertIntoXmlPayload(parentModel, value, response, CONNECTION_FAILURE, e.getMessage());
         }

      } catch (Throwable e) {
         logger.warn(e.toString());
         saveFailedDataIntoOutgoingMessageLog(value, ERROR, e.getMessage());
      }

   }

   private void performDELEvent(AirmailStatusEvent value) {
      try {
         logger.warn("Started executing the DEL event");
         SaveMailDetailsRequestType parentModel = new SaveMailDetailsRequestType();
         MailDetailsRequestType childModel = new MailDetailsRequestType();
         List<MailDetailsRequestType> childModelList = new ArrayList<>();
         childModel.setProduct(MAILTRACKING);
         childModel.setScanType(DEL);
         childModel.setCarrierCode(value.getCarrierCode());
         childModel.setMailTag(value.getMailBag());
         childModel.setSerialNumber("1");
         childModel.setUserName(value.getCreatedBy());
         childModel.setScanDateTime(value.getCreatedOn().format(DateTimeFormatter.ofPattern(DATETIMEFORMAT)));
         parentModel.setCompanyCode(COMPANYCODE);
         parentModel.setHhtVersion("1.0");
         parentModel.setScanningPort(MultiTenantUtility.getAirportCodeFromContext());
         parentModel.setMessagePartId(1);
         childModelList.add(childModel);
         parentModel.setMailDetails(childModelList);
         SaveMailDetailsResponseType response = new SaveMailDetailsResponseType();
         try {
            logger.warn("Going to trigger the message to the URL For ARR Import without BUP");
            response = camsConfigService.connectorConfig(parentModel);
            logger.warn("Message triggered to the url for ARR Import without BUP");
            convertIntoXmlPayload(parentModel, value, response, SUCCESS, null);
         } catch (Throwable e) {
            // tracing the connection failures
            logger.warn("error happened" + e.getMessage());
            convertIntoXmlPayload(parentModel, value, response, CONNECTION_FAILURE, e.getMessage());
         }

      } catch (Throwable e) {
         logger.warn(e.toString());
         saveFailedDataIntoOutgoingMessageLog(value, ERROR, e.getMessage());
      }
   }

   private void performDLVEvent(AirmailStatusEvent value) {
      try {
         logger.warn("Started executing the DLV event");
         SaveMailDetailsRequestType parentModel = new SaveMailDetailsRequestType();
         MailDetailsRequestType childModel = new MailDetailsRequestType();
         List<MailDetailsRequestType> childModelList = new ArrayList<>();
         childModel.setProduct(MAILTRACKING);
         childModel.setScanType(DLV);
         childModel.setCarrierCode(value.getCarrierCode());
         childModel.setMailTag(value.getMailBag());
         childModel.setContainerNumber(value.getStoreLocation());
         childModel.setSerialNumber("1");
         if (value.isBup()) {
            childModel.setIsPABuilt("Y");
         }
         childModel.setUserName(value.getCreatedBy());
         childModel.setScanDateTime(value.getCreatedOn().format(DateTimeFormatter.ofPattern(DATETIMEFORMAT)));
         parentModel.setCompanyCode(COMPANYCODE);
         parentModel.setHhtVersion("1.0");
         parentModel.setScanningPort(MultiTenantUtility.getAirportCodeFromContext());
         parentModel.setMessagePartId(1);
         childModelList.add(childModel);
         parentModel.setMailDetails(childModelList);
         SaveMailDetailsResponseType response = new SaveMailDetailsResponseType();
         try {
            logger.warn("Going to trigger the message to the URL For ARR Import without BUP");
            response = camsConfigService.connectorConfig(parentModel);
            logger.warn("Message triggered to the url for ARR Import without BUP");
            convertIntoXmlPayload(parentModel, value, response, SUCCESS, null);
         } catch (Throwable e) {
            // tracing the connection failures
            logger.warn("error happened" + e.getMessage());
            convertIntoXmlPayload(parentModel, value, response, CONNECTION_FAILURE, e.getMessage());
         }

      } catch (Throwable e) {
         logger.warn(e.toString());
         saveFailedDataIntoOutgoingMessageLog(value, ERROR, e.getMessage());
      }
   }

   private void performRSNEvent(AirmailStatusEvent value) {
      try {
         logger.warn("Started executing the RSN event");
         SaveMailDetailsRequestType parentModel = new SaveMailDetailsRequestType();
         MailDetailsRequestType childModel = new MailDetailsRequestType();
         List<MailDetailsRequestType> childModelList = new ArrayList<>();
         childModel.setProduct(MAILTRACKING);
         childModel.setScanType(RSN);
         childModel.setCarrierCode(value.getCarrierCode());
         childModel.setMailTag(value.getMailBag());
         childModel.setSerialNumber("1");
         childModel.setUserName(value.getCreatedBy());
         childModel.setScanDateTime(value.getCreatedOn().format(DateTimeFormatter.ofPattern(DATETIMEFORMAT)));
         parentModel.setCompanyCode(COMPANYCODE);
         parentModel.setHhtVersion("1.0");
         parentModel.setScanningPort(MultiTenantUtility.getAirportCodeFromContext());
         parentModel.setMessagePartId(1);
         childModelList.add(childModel);
         parentModel.setMailDetails(childModelList);
         SaveMailDetailsResponseType response = new SaveMailDetailsResponseType();
         try {
            logger.warn("Going to trigger the message to the URL For ARR Import without BUP");
            response = camsConfigService.connectorConfig(parentModel);
            logger.warn("Message triggered to the url for ARR Import without BUP");
            convertIntoXmlPayload(parentModel, value, response, SUCCESS, null);
         } catch (Throwable e) {
            // tracing the connection failures
            logger.warn("error happened" + e.getMessage());
            convertIntoXmlPayload(parentModel, value, response, CONNECTION_FAILURE, e.getMessage());
         }

      } catch (Throwable e) {
         logger.warn(e.toString());
         saveFailedDataIntoOutgoingMessageLog(value, ERROR, e.getMessage());
      }
   }

   private void performCNLEvent(AirmailStatusEvent value) {
      try {
         logger.warn("Started executing the CNL event");
         SaveMailDetailsRequestType parentModel = new SaveMailDetailsRequestType();
         MailDetailsRequestType childModel = new MailDetailsRequestType();
         List<MailDetailsRequestType> childModelList = new ArrayList<>();
         childModel.setProduct(MAILTRACKING);
         childModel.setScanType(CNL);
         childModel.setCarrierCode(value.getCarrierCode());
         childModel.setContainerNumber(value.getStoreLocation());
         childModel.setOffloadReason(value.getOffloadCode().toString());
         childModel.setSerialNumber("1");
         childModel.setUserName(value.getCreatedBy());
         childModel.setScanDateTime(value.getCreatedOn().format(DateTimeFormatter.ofPattern(DATETIMEFORMAT)));
         parentModel.setCompanyCode(COMPANYCODE);
         parentModel.setHhtVersion("1.0");
         parentModel.setScanningPort(MultiTenantUtility.getAirportCodeFromContext());
         parentModel.setMessagePartId(1);
         childModelList.add(childModel);
         parentModel.setMailDetails(childModelList);
         SaveMailDetailsResponseType response = new SaveMailDetailsResponseType();
         try {
            logger.warn("Going to trigger the message to the URL For ARR Import without BUP");
            response = camsConfigService.connectorConfig(parentModel);
            logger.warn("Message triggered to the url for ARR Import without BUP");
            convertIntoXmlPayload(parentModel, value, response, SUCCESS, null);
         } catch (Throwable e) {
            // tracing the connection failures
            logger.warn("error happened while sending the message to CAMS" + e.getMessage());
            convertIntoXmlPayload(parentModel, value, response, CONNECTION_FAILURE, e.getMessage());
         }

      } catch (Throwable e) {
         logger.warn(e.toString());
         saveFailedDataIntoOutgoingMessageLog(value, ERROR, e.getMessage());
      }

   }

   private void convertIntoXmlPayload(SaveMailDetailsRequestType brkdwnprntModel, AirmailStatusEvent value,
         SaveMailDetailsResponseType response, String status, String errorMessage) {
      String payload = null;
      try {

      } catch (Exception e) {
      }
      payload = JacksonUtility.convertObjectToXMLString(brkdwnprntModel).toString();
      if (!StringUtils.isEmpty(payload))
         saveDataIntoOutgoingMessageLog(payload, brkdwnprntModel, value, response, status, errorMessage);

   }

   private void saveDataIntoOutgoingMessageLog(String payload, SaveMailDetailsRequestType brkdwnprntModel,
         AirmailStatusEvent value, SaveMailDetailsResponseType response, String status, String errorMessage) {
      OutgoingMessageLog outgoingMessage = new OutgoingMessageLog();
      setCommonInfos(value, outgoingMessage);
      outgoingMessage.setMessage(payload);
      outgoingMessage.setCarrierCode(brkdwnprntModel.getCompanyCode());
      outgoingMessage.setFlightNumber(brkdwnprntModel.getMailDetails().get(0).getFlightNumber());
      if (!StringUtils.isEmpty(brkdwnprntModel.getMailDetails().get(0).getFlightDate())) {
         LocalDate flightDate = LocalDate.parse(brkdwnprntModel.getMailDetails().get(0).getFlightDate(),
               DateTimeFormatter.ofPattern("d-MMM-yyyy"));
         outgoingMessage.setFlightOriginDate(LocalDateTime.of(flightDate, LocalTime.now()));
      }
      outgoingMessage.setSubMessageType(brkdwnprntModel.getMailDetails().get(0).getScanType());
      LocalDate shipmentDate = null;
      if (!StringUtils.isEmpty(brkdwnprntModel.getMailDetails().get(0).getMailTag())) {
         outgoingMessage.setShipmentNumber(brkdwnprntModel.getMailDetails().get(0).getMailTag().substring(0, 20));
         shipmentDate = shipmentProcessorService
               .getShipmentDate(brkdwnprntModel.getMailDetails().get(0).getMailTag().substring(0, 20));
         if (Optional.ofNullable(shipmentDate).isPresent()) {
            outgoingMessage.setShipmentDate(LocalDateTime.of(shipmentDate, LocalTime.now()));
         }
      }

      if (CONNECTION_FAILURE.equalsIgnoreCase(status)) {
         outgoingMessage.setStatus(CAMSERROR);
      } else if (!ObjectUtils.isEmpty(response) && FALSE.equalsIgnoreCase(response.getErrorFlag())) {
         outgoingMessage.setStatus(ERROR);
      } else if (!ObjectUtils.isEmpty(response) && "S".equalsIgnoreCase(response.getErrorFlag())) {
         outgoingMessage.setStatus(SENT);
      }

      // save the data to the outgoing message table
      loggerService.logInterfaceOutgoingMessage(outgoingMessage);
      logger.warn("Data saved into Outgoing message log table");
      if (CAMSERROR.equalsIgnoreCase(outgoingMessage.getStatus()) || ERROR.equalsIgnoreCase(outgoingMessage.getStatus())
            || CUSTOMEXCEPTION.equalsIgnoreCase(outgoingMessage.getStatus())) {
         logMessageIntoOutgoingErrorMessageLog(response, outgoingMessage.getOutMessageId(), errorMessage);
      }

   }

   private void setCommonInfos(AirmailStatusEvent event, OutgoingMessageLog outgoingMessage) {
      outgoingMessage.setChannelSent(HTTP);
      outgoingMessage.setInterfaceSystem(null);
      outgoingMessage.setMessageType(CAMS);
      outgoingMessage.setRequestedOn(LocalDateTime.now(ZoneOffset.UTC));
      outgoingMessage.setVersionNo(1);
      outgoingMessage.setSequenceNo(1);
      outgoingMessage.setMessageContentEndIndicator(null);
      outgoingMessage.setAcknowledgementReceivedOn(LocalDateTime.now(ZoneOffset.UTC));
      outgoingMessage.setEventId(event.getEventCamsStoreEventid());
      outgoingMessage.setEventName(event.getSourceTriggerType());
   }

   private void logMessageIntoOutgoingErrorMessageLog(SaveMailDetailsResponseType response, BigInteger outMessageId,
         String errorMessage) {
      OutgoingMessageErrorLog errorLog = new OutgoingMessageErrorLog();
      errorLog.setOutMessageId(outMessageId);
      errorLog.setCreatedBy(CAMS);
      errorLog.setCreatedOn(LocalDateTime.now(ZoneOffset.UTC));
      if (!StringUtils.isEmpty(errorMessage)) {
         errorLog.setErrorCode(ERROR);
         errorLog.setMessage(errorMessage);
      } else {
         if (!ObjectUtils.isEmpty(response) && !CollectionUtils.isEmpty(response.getMailResponseDetails())) {
            errorLog.setErrorCode(response.getMailResponseDetails().get(0).getErrorCode());
         } else {
            errorLog.setErrorCode(ERROR);
         }
         try {
            errorLog.setMessage(airmailStatusDao.getErrorMessage(errorLog.getErrorCode()));
         } catch (CustomException e) {
         }
         if (StringUtils.isEmpty(errorLog.getMessage())) {
            errorLog.setMessage("Error not defined in the system");
         }

      }
      errorLog.setLineItem(ERROR);
      loggerService.logInterfaceOutgoingErrorMessage(errorLog);
   }

   private void saveFailedDataIntoOutgoingMessageLog(AirmailStatusEvent event, String Status, String errorMessage) {
      OutgoingMessageLog outgoingMessage = new OutgoingMessageLog();
      setCommonInfos(event, outgoingMessage);
      outgoingMessage.setMessage(" ");
      outgoingMessage.setSubMessageType(event.getEventsType());
      outgoingMessage.setCarrierCode(event.getCarrierCode());
      outgoingMessage.setFlightNumber(event.getFlightKey());
      outgoingMessage.setFlightOriginDate(event.getFlightDate());
      if (!StringUtils.isEmpty(event.getMailBag())) {
         outgoingMessage.setShipmentNumber(event.getMailBag().substring(0, 20));
         LocalDate shipmentDate = shipmentProcessorService.getShipmentDate(outgoingMessage.getShipmentNumber());
         if (Optional.ofNullable(shipmentDate).isPresent()) {
            outgoingMessage.setShipmentDate(LocalDateTime.of(shipmentDate, LocalTime.now()));
         }
      }
      outgoingMessage.setStatus(ERROR);
      // log error data into outgoing message log
      loggerService.logInterfaceOutgoingMessage(outgoingMessage);
      // log data into outgoing error message log to show the user actual error
      logMessageIntoOutgoingErrorMessageLog(null, outgoingMessage.getOutMessageId(), errorMessage);
   }

}
