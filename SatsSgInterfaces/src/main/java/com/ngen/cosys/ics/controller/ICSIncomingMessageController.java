package com.ngen.cosys.ics.controller;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ngen.cosys.annotation.NgenCosysAppInfraAnnotation;
import com.ngen.cosys.annotation.PostRequest;
import com.ngen.cosys.esb.route.ConnectorUtils;
import com.ngen.cosys.events.consumer.logger.service.ApplicationLoggerService;
import com.ngen.cosys.events.enums.ESBRouterTypeUtils;
import com.ngen.cosys.events.esb.connector.logger.payload.IncomingMessageErrorLog;
import com.ngen.cosys.events.esb.connector.logger.payload.IncomingMessageLog;
import com.ngen.cosys.events.esb.connector.logger.payload.OutgoingMessageErrorLog;
import com.ngen.cosys.events.esb.connector.logger.payload.OutgoingMessageLog;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.ics.enums.ResponseStatus;
import com.ngen.cosys.ics.model.ContainerExitFromLaneModel;
import com.ngen.cosys.ics.model.ContainerExitFromLaneResponseModel;
import com.ngen.cosys.ics.model.ContainerMovementModel;
import com.ngen.cosys.ics.model.ContainerMovementResponseModel;
import com.ngen.cosys.ics.model.ICSPreAnnouncementResponseModel;
import com.ngen.cosys.ics.model.InfeedWorkstationResponseModel;
import com.ngen.cosys.ics.model.PreAnnouncementRequestModel;
import com.ngen.cosys.ics.model.PreAnnouncementResponseModel;
import com.ngen.cosys.ics.model.ResponseModel;
import com.ngen.cosys.ics.model.ULD;
import com.ngen.cosys.ics.model.ULDAutoWeightResponseModel;
import com.ngen.cosys.ics.service.ContainerExitFromLaneService;
import com.ngen.cosys.ics.service.ContainerMovementService;
import com.ngen.cosys.ics.service.ICSPreannouncementService;
import com.ngen.cosys.satssg.interfaces.singpost.model.FetchULDDataRequestModel;
import com.ngen.cosys.satssg.interfaces.singpost.model.FetchULDSuccessResponseModel;
import com.ngen.cosys.satssg.interfaces.singpost.model.InfeedULDRequestModel;
import com.ngen.cosys.satssg.interfaces.singpost.model.PrintULDTagRequestModel;
import com.ngen.cosys.satssg.interfaces.singpost.model.PrintULDTagResponseModel;
import com.ngen.cosys.satssg.interfaces.singpost.model.UldAutoWeightModel;
import com.ngen.cosys.satssg.interfaces.singpost.service.FetchUldDataService;
import com.ngen.cosys.satssg.interfaces.singpost.service.InfeedUldService;
import com.ngen.cosys.satssg.interfaces.singpost.service.PrintUldTagService;
import com.ngen.cosys.satssg.interfaces.singpost.service.UpdateUldAutoWeightService;
import com.ngen.cosys.scheduler.esb.connector.jackson.util.JacksonUtility;
import com.ngen.cosys.validation.groups.ContainerExitLaneValidationGroup;
import com.ngen.cosys.validation.groups.ContainerMovementValidationGroup;
import com.ngen.cosys.validation.groups.UldAutoWeightValidationGroup;

@NgenCosysAppInfraAnnotation(path = "/api/ics")
public class ICSIncomingMessageController {

   private static final String SEMICOLON = "  ;  ";

   private static final Logger LOGGER = LoggerFactory.getLogger(ICSIncomingMessageController.class);

   private static final String PROCESSED = "PROCESSED";
   private static final String REJECTED = "REJECTED";
   private static final String SENT = "SENT";
   private static final String ERROR = "ERROR";
   private static final String INVALID_REQUEST = "Invalid Request";
   private static final String PREANNOUCEMENT_TO_ICS="PREANNOUNCEMENT DATA TO ICS CUSTOMS";

   @Autowired
   private Validator validator;

   @Autowired
   private ContainerExitFromLaneService containerExitService;

   @Autowired
   private ContainerMovementService containerMovementService;

   @Autowired
   private ICSPreannouncementService preAnnouncementService;

   @Autowired
   private ApplicationLoggerService loggerService;

   @Autowired
   private UpdateUldAutoWeightService uldService;

   @Autowired
   private PrintUldTagService printUldTagService;

   @Autowired
   private InfeedUldService infeedULDfromAirsideService;

   @Autowired
   private FetchUldDataService fetchUldDataService;

   /**
    * Update Container Exit For Incoming Message
    * 
    * @param requestPayload
    * @param request
    * @return
    * @throws CustomException
    */
   @PostMapping(path = "/update-container-exit", consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
   public ResponseEntity<ContainerExitFromLaneResponseModel> updateContainerExit(
         @RequestBody ContainerExitFromLaneModel requestPayload, HttpServletRequest request) throws CustomException {

      BigInteger messageId = BigInteger.ZERO;
      String containerExitStatus = null;
      ContainerExitFromLaneResponseModel containerExitResponse = null;

      // Validate The Request Model
      Set<ConstraintViolation<ContainerExitFromLaneModel>> violations = this.validator.validate(requestPayload,
            ContainerExitLaneValidationGroup.class);
      StringBuilder errorMessage = new StringBuilder();

      if (!violations.isEmpty()) {
         Iterator<ConstraintViolation<ContainerExitFromLaneModel>> iterator = violations.iterator();
         while (iterator.hasNext()) {
            errorMessage.append(iterator.next().getMessage()).append(SEMICOLON);
         }
         containerExitResponse = new ContainerExitFromLaneResponseModel();
         containerExitResponse.setStatus(ResponseStatus.FAIL);
         containerExitResponse.setErrorNumber(HttpStatus.BAD_REQUEST.toString());
         containerExitResponse.setErrorDescription(errorMessage.toString());
         Object xmlPayload = JacksonUtility.convertObjectToXMLString(requestPayload);
         updateIncomingMessageLog(messageId, REJECTED,"ContainerExit",xmlPayload.toString(),null,null,null);
         // Error Insert
         insertIncomingErrorMessage(messageId, null, INVALID_REQUEST);
      } else {
         // Call The Service To Get Lane Information
         containerExitStatus = containerExitService.getLaneInformationForContainerExit(requestPayload);

         if (containerExitStatus != null && containerExitStatus.equalsIgnoreCase(ResponseStatus.SUCCESS)) {
            containerExitResponse = new ContainerExitFromLaneResponseModel();
            containerExitResponse.setStatus(ResponseStatus.SUCCESS);
            Object xmlPayload = JacksonUtility.convertObjectToXMLString(requestPayload);
            updateIncomingMessageLog(messageId, PROCESSED,"ContainerExit",xmlPayload.toString(),null,null,null);
         } else {
            containerExitResponse = new ContainerExitFromLaneResponseModel();
            containerExitResponse.setStatus(ResponseStatus.FAIL);
            containerExitResponse.setErrorNumber(HttpStatus.NO_CONTENT.toString());
            containerExitResponse
                  .setErrorDescription("No Container Found for the location - " + requestPayload.getContainerId());
            Object xmlPayload = JacksonUtility.convertObjectToXMLString(requestPayload);
            updateIncomingMessageLog(messageId, REJECTED,"ContainerExit",xmlPayload.toString(),null,null,null);
         }
      }
      return new ResponseEntity<>(containerExitResponse, HttpStatus.OK);
   }

   /**
    * Updates Container Movement For Incoming Message
    * 
    * @param requestPayload
    * @param request
    * @return
    * @throws CustomException
    */
   @PostMapping(path = "/update-container-movement", consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
   public ResponseEntity<ContainerMovementResponseModel> updateContainerMovementStatus(
         @RequestBody ContainerMovementModel requestPayload, HttpServletRequest request) throws CustomException {

      BigInteger messageId = new BigInteger(request.getHeader(ESBRouterTypeUtils.MESSAGE_ID.getName()));

      LOGGER.error("Messeage Has been sent CUSTOMS");
      ContainerMovementResponseModel containerMovementResponse = null;

      // Validate The Request Model
      Set<ConstraintViolation<ContainerMovementModel>> violations = this.validator.validate(requestPayload,
            ContainerMovementValidationGroup.class);
      String containerMovementStatus = null;
      StringBuilder errorMessage = new StringBuilder();

      if (!violations.isEmpty()) {
         Iterator<ConstraintViolation<ContainerMovementModel>> iterator = violations.iterator();
         while (iterator.hasNext()) {
            errorMessage.append(iterator.next().getMessage()).append(SEMICOLON);
         }
         containerMovementResponse = new ContainerMovementResponseModel();
         containerMovementResponse.setStatus(ResponseStatus.FAIL);
         containerMovementResponse.setErrorNumber(HttpStatus.BAD_REQUEST.toString());
         containerMovementResponse.setErrorDescription(errorMessage.toString());
         Object xmlPayload = JacksonUtility.convertObjectToXMLString(requestPayload);
         updateIncomingMessageLog(messageId, REJECTED,"ContainerMove",xmlPayload.toString(),null,null,null);
         // Error Insert
         insertIncomingErrorMessage(messageId, null, INVALID_REQUEST);
      } else {
         containerMovementStatus = containerMovementService.getContainerMovementStatus(requestPayload);
         if (containerMovementStatus != null && containerMovementStatus.equalsIgnoreCase(ResponseStatus.SUCCESS)) {
            containerMovementResponse = new ContainerMovementResponseModel();
            containerMovementResponse.setStatus(ResponseStatus.SUCCESS);
            Object xmlPayload = JacksonUtility.convertObjectToXMLString(requestPayload);
            updateIncomingMessageLog(messageId, PROCESSED,"ContainerMove",xmlPayload.toString(),null,null,null);
         } else {
            containerMovementResponse = new ContainerMovementResponseModel();
            containerMovementResponse.setStatus(ResponseStatus.FAIL);
            containerMovementResponse.setErrorNumber(HttpStatus.NO_CONTENT.toString());
            containerMovementResponse
                  .setErrorDescription("No Container Found for the location  " + requestPayload.getContainerId());
            Object xmlPayload = JacksonUtility.convertObjectToXMLString(requestPayload);
            updateIncomingMessageLog(messageId, REJECTED,"ContainerMove",xmlPayload.toString(),null,null,null);
         }
      }

      return new ResponseEntity<>(containerMovementResponse, HttpStatus.OK);
   }

   /**
    * Fetch Pre-Announcement For Incoming Message
    * 
    * @param requestPayload
    * @param request
    * @return
    * @throws CustomException
    */
   //Pre Annocument data to ICS Customs
   @PostMapping(path = "fetch-import-uld-data", consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
   public ResponseEntity<Object> preAnnouncementDetails(@RequestBody PreAnnouncementRequestModel requestPayload,
         HttpServletRequest request) throws CustomException {

      BigInteger messageId = BigInteger.ZERO;
      if(!ObjectUtils.isEmpty(request.getHeader(ESBRouterTypeUtils.MESSAGE_ID.getName()))) {
    	  messageId=new BigInteger(request.getHeader(ESBRouterTypeUtils.MESSAGE_ID.getName()));
      }
      BigInteger referenceId = null;
      ICSPreAnnouncementResponseModel iCSPreAnnouncementResponseModel = null;
      ULD preannouncementUldMessage = null;
      LOGGER.info(PREANNOUCEMENT_TO_ICS);
      preannouncementUldMessage = preAnnouncementService.preannouncementUldMessage(requestPayload);
      Object xmlPayload = JacksonUtility.convertObjectToXMLString(preannouncementUldMessage);
      if (Objects.nonNull(preannouncementUldMessage)) {
    	 LOGGER.info(PREANNOUCEMENT_TO_ICS+": "+ preannouncementUldMessage.getContainerId());
         iCSPreAnnouncementResponseModel = new ICSPreAnnouncementResponseModel();
         iCSPreAnnouncementResponseModel.setStatus(ResponseStatus.SUCCESS);
         iCSPreAnnouncementResponseModel.setUld(preannouncementUldMessage);
         referenceId = logOutgoingMessage((String)xmlPayload,"PreAnnouncement");
         logOutgoingMessage(referenceId, SENT,"PreAnnouncement",preannouncementUldMessage.getIncomingFlightCarrier(),preannouncementUldMessage.getIncomingFlightNumber(),preannouncementUldMessage.getIncomingFlightDate().atStartOfDay());         
      } else {
    	 referenceId = logOutgoingMessage((String)xmlPayload,"PreAnnouncement");
    	 if(Objects.nonNull(preannouncementUldMessage)) {
    		 logOutgoingMessage(referenceId, ERROR,"PreAnnouncement",preannouncementUldMessage.getIncomingFlightCarrier(),preannouncementUldMessage.getIncomingFlightNumber(),preannouncementUldMessage.getIncomingFlightDate().atStartOfDay());
    	 }
         PreAnnouncementResponseModel failedResponse = new PreAnnouncementResponseModel();
         failedResponse.setStatus(ResponseStatus.FAIL);
         failedResponse.setErrorNumber(HttpStatus.NO_CONTENT.toString());
         failedResponse.setErrorDescription(
               "Requested ULD " + requestPayload.getContainerId() + " information is not available in NG COSYS");
         return new ResponseEntity<>(failedResponse, HttpStatus.OK);
      }

      return new ResponseEntity<>(iCSPreAnnouncementResponseModel, HttpStatus.OK);
   }

   /**
    * Infeed ULD from Air Side/Land Side workstation
    * 
    * @param requestPayload
    * @return
    * @throws CustomException
    */
   @PostRequest(value = "/infeed-uld", method = RequestMethod.POST, consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
   public ResponseEntity<InfeedWorkstationResponseModel> infeedULD(@RequestBody InfeedULDRequestModel requestPayload,
         HttpServletRequest request) throws CustomException {
      InfeedWorkstationResponseModel responseModel = null;
      BigInteger messageId = new BigInteger(request.getHeader(ESBRouterTypeUtils.MESSAGE_ID.getName()));

      // Validate Request Model
      Set<ConstraintViolation<InfeedULDRequestModel>> violations = this.validator.validate(requestPayload,
            ContainerExitLaneValidationGroup.class);
      StringBuilder errorMessage = new StringBuilder();
      if (!violations.isEmpty()) {
         Iterator<ConstraintViolation<InfeedULDRequestModel>> iterator = violations.iterator();
         while (iterator.hasNext()) {
            errorMessage.append(iterator.next().getMessage()).append(SEMICOLON);
         }
         responseModel = new InfeedWorkstationResponseModel();
         responseModel.setStatus(ResponseStatus.FAIL);
         Object xmlPayload = JacksonUtility.convertObjectToXMLString(requestPayload);
         updateIncomingMessageLog(messageId, REJECTED,"InfWorkstation",xmlPayload.toString(),null,null,null);
         // Error Insert
         insertIncomingErrorMessage(messageId, null, INVALID_REQUEST);
      } else {
         Integer updateRecordCount = infeedULDfromAirsideService.infeeduldfromAirside(requestPayload);

         if (updateRecordCount > 0) {
            responseModel = new InfeedWorkstationResponseModel();
            responseModel.setStatus(ResponseStatus.SUCCESS);
            Object xmlPayload = JacksonUtility.convertObjectToXMLString(requestPayload);
            updateIncomingMessageLog(messageId, PROCESSED,"InfWorkstation",xmlPayload.toString(),null,null,null);
         } else {
            responseModel = new InfeedWorkstationResponseModel();
            responseModel.setStatus(ResponseStatus.FAIL);
            responseModel.setErrorNumber(HttpStatus.NO_CONTENT.toString());
            responseModel.setErrorDescription("No Infeed Found");
            Object xmlPayload = JacksonUtility.convertObjectToXMLString(requestPayload);
            updateIncomingMessageLog(messageId, REJECTED,"InfWorkstation",xmlPayload.toString(),null,null,null);
         }
      }
      return new ResponseEntity<>(responseModel, HttpStatus.OK);
   }

   /**
    * Update ULD Autoweight
    * 
    * @param requestPayload
    * @param request
    * @return
    * @throws CustomException
    */
   @PostRequest(value = "/update-uld-weight", method = RequestMethod.POST, consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
   public ResponseEntity<ULDAutoWeightResponseModel> updateUldAutoWeight(
         @RequestBody @Valid UldAutoWeightModel requestPayload, HttpServletRequest request) throws CustomException {
      ULDAutoWeightResponseModel autoWeightResponse = null;
      
      BigInteger messageId = BigInteger.ZERO;
      
      if(!ObjectUtils.isEmpty(request.getHeader(ESBRouterTypeUtils.MESSAGE_ID.getName()))){
         messageId = new BigInteger(request.getHeader(ESBRouterTypeUtils.MESSAGE_ID.getName()));   
      }

      Set<ConstraintViolation<UldAutoWeightModel>> violations = this.validator.validate(requestPayload,
            UldAutoWeightValidationGroup.class);
      StringBuilder errorMessage = new StringBuilder();
      if (!violations.isEmpty()) {
         Iterator<ConstraintViolation<UldAutoWeightModel>> iterator = violations.iterator();
         while (iterator.hasNext()) {
            errorMessage.append(iterator.next().getMessage()).append(SEMICOLON);
         }
         autoWeightResponse = new ULDAutoWeightResponseModel();
         autoWeightResponse.setStatus(ResponseStatus.FAIL);
         autoWeightResponse.setErrorNumber(HttpStatus.BAD_REQUEST.toString());
         autoWeightResponse.setErrorDescription(errorMessage.toString());
         Object xmlPayload = JacksonUtility.convertObjectToXMLString(requestPayload);
         updateIncomingMessageLog(messageId, REJECTED,"UpdateULDweight",xmlPayload.toString(),null,null,null);
         // Error Insert
         insertIncomingErrorMessage(messageId, null, INVALID_REQUEST);
      } else {
         Integer updateUldAutoWeightCount = uldService.updateUldAutoWeight(requestPayload);
         if (updateUldAutoWeightCount > 0) {
            autoWeightResponse = new ULDAutoWeightResponseModel();
            autoWeightResponse.setStatus(ResponseStatus.SUCCESS);
            Object xmlPayload = JacksonUtility.convertObjectToXMLString(requestPayload);
            updateIncomingMessageLog(messageId, PROCESSED,"UpdateULDweight",xmlPayload.toString(),null,null,null);
         } else {
            autoWeightResponse = new ULDAutoWeightResponseModel();
            autoWeightResponse.setStatus(ResponseStatus.FAIL);
            autoWeightResponse.setErrorNumber(HttpStatus.NO_CONTENT.toString());
            autoWeightResponse.setErrorDescription(
                  "ULD Autoweignt Not Found For The Container ID " + requestPayload.getContainerId());
            Object xmlPayload = JacksonUtility.convertObjectToXMLString(requestPayload);
            updateIncomingMessageLog(messageId, REJECTED,"UpdateULDweight",xmlPayload.toString(),null,null,null);
         }
      }
      return new ResponseEntity<>(autoWeightResponse, HttpStatus.OK);
   }

   /**
    * Print ULD Tag For Incoming Message
    * 
    * @param printULDTagRequestModel
    * @param request
    * @return
    * @throws CustomException
    */
   @PostRequest(value = "/print-uld-tag", method = RequestMethod.POST, consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
   public ResponseEntity<PrintULDTagResponseModel> printUldTag(
         @RequestBody PrintULDTagRequestModel printULDTagRequestModel, HttpServletRequest request)
         throws CustomException {
      PrintULDTagResponseModel printUldTagresponse = null;
      BigInteger referenceId=null;
      Object requestPayload = JacksonUtility.convertObjectToXMLString(printULDTagRequestModel);
      Set<ConstraintViolation<PrintULDTagRequestModel>> violations = this.validator.validate(printULDTagRequestModel,
            ContainerExitLaneValidationGroup.class);
      StringBuilder errorMessage = new StringBuilder();

      BigInteger messageId = BigInteger.ZERO;
      
      if(!ObjectUtils.isEmpty(request.getHeader(ESBRouterTypeUtils.MESSAGE_ID.getName()))){
         messageId = new BigInteger(request.getHeader(ESBRouterTypeUtils.MESSAGE_ID.getName()));   
      }
      

      if (!violations.isEmpty()) {
         Iterator<ConstraintViolation<PrintULDTagRequestModel>> iterator = violations.iterator();
         while (iterator.hasNext()) {
            errorMessage.append(iterator.next().getMessage()).append(SEMICOLON);
         }
      }
      try {
         printUldTagService.printUldTag(printULDTagRequestModel);
         printUldTagresponse = new PrintULDTagResponseModel();
         referenceId = logOutgoingMessage((String)requestPayload,"PrintULDTag");
         updateIncomingMessageLog(referenceId, PROCESSED,"PrintULDTag",(String)requestPayload,printULDTagRequestModel.getOutgoingFlightCarrier(),printULDTagRequestModel.getOutgoingFlightNumber(),printULDTagRequestModel.getOutgoingFlightDate().atStartOfDay());
         printUldTagresponse.setStatus(ResponseStatus.SUCCESS);
      } catch (CustomException e) {
         printUldTagresponse = new PrintULDTagResponseModel();
         printUldTagresponse.setStatus(ResponseStatus.FAIL);
         printUldTagresponse.setErrorNumber(HttpStatus.BAD_REQUEST.toString());
         printUldTagresponse.setErrorDiscription(e.getMessage());
      }
      return new ResponseEntity<>(printUldTagresponse, HttpStatus.OK);
   }

   /**
    * Fetch ULD Data For Incoming Message
    * 
    * @param fetchULDDataRequestModel
    * @param request
    * @return
    * @throws CustomException
    */
   @PostRequest(value = "/fetch-uld-data", method = RequestMethod.POST, consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
   public ResponseEntity<Object> fetchULDData(@RequestBody @Valid FetchULDDataRequestModel fetchULDDataRequestModel,
         HttpServletRequest request) throws CustomException {
	   BigInteger messageId = BigInteger.ZERO;
	   if(!ObjectUtils.isEmpty(request.getHeader(ESBRouterTypeUtils.MESSAGE_ID.getName()))){
	        messageId = new BigInteger(request.getHeader(ESBRouterTypeUtils.MESSAGE_ID.getName()));   
	    }

      // Validate The Request Model
      Set<ConstraintViolation<FetchULDDataRequestModel>> violations = this.validator.validate(fetchULDDataRequestModel,
            ContainerExitLaneValidationGroup.class);
      StringBuilder errorMessage = new StringBuilder();

      ResponseModel responseModel = new ResponseModel();

      if (!violations.isEmpty()) {
         Iterator<ConstraintViolation<FetchULDDataRequestModel>> iterator = violations.iterator();
         while (iterator.hasNext()) {
            errorMessage.append(iterator.next().getMessage()).append(SEMICOLON);
         }
         Object xmlPayload = JacksonUtility.convertObjectToXMLString(fetchULDDataRequestModel);
         updateIncomingMessageLog(messageId, REJECTED,"FetchICSULDList",xmlPayload.toString(),null,null,null);
         // Error Insert
         insertIncomingErrorMessage(messageId, null, INVALID_REQUEST);
         responseModel.setStatus(ResponseStatus.FAIL);
         responseModel.setErrorNumber(HttpStatus.BAD_REQUEST.toString());
         responseModel.setErrorDescription(errorMessage.toString());
      } else {
         FetchULDSuccessResponseModel fetchUldData;
         try {
            fetchUldData = fetchUldDataService.fetchUldData(fetchULDDataRequestModel);
            if (fetchUldData != null) {
               fetchUldData.setStatus(ResponseStatus.SUCCESS);
               Object xmlPayload = JacksonUtility.convertObjectToXMLString(fetchUldData);
               messageId= logOutgoingMessage(xmlPayload.toString(),"FetchICSULDList");
               logOutgoingMessage(messageId,SENT,"FetchICSULDList",fetchUldData.getOutgoingFlightCarrier(),fetchUldData.getOutgoingFlightNumber(),fetchUldData.getOutgoingFlightDate() != null ? fetchUldData.getOutgoingFlightDate().atStartOfDay():null);
               return new ResponseEntity<>(fetchUldData, HttpStatus.OK);
            }
         } catch (CustomException e) {
            responseModel.setStatus(ResponseStatus.FAIL);
            responseModel.setErrorNumber(HttpStatus.BAD_REQUEST.toString());
            responseModel.setErrorDescription(e.getMessage());
            return new ResponseEntity<>(responseModel, HttpStatus.OK);
         }
      }
      return new ResponseEntity<>(responseModel, HttpStatus.OK);

   }

   /**
    * Updating Logs For Incoming Message
    * 
    * @param messageId
    * @param status
    */
   private void updateIncomingMessageLog(BigInteger messageId, String status,String submessageType,String payload,
		   String carrier,String flightNum,LocalDateTime flightDate) {
      IncomingMessageLog incomingMessage = new IncomingMessageLog();
      incomingMessage.setInMessageId(messageId);
      incomingMessage.setChannelReceived("HTTP");
      incomingMessage.setInterfaceSystem("ICS");
      incomingMessage.setMessageType("ICS");
      incomingMessage.setSubMessageType(submessageType);
      incomingMessage.setCarrierCode(carrier);
      incomingMessage.setFlightNumber(flightNum);
      incomingMessage.setFlightOriginDate(flightDate);
      incomingMessage.setShipmentNumber(null);
      incomingMessage.setShipmentDate(null);
      incomingMessage.setReceivedOn(LocalDateTime.now());
      incomingMessage.setVersionNo(1);
      incomingMessage.setSequenceNo(1);
      incomingMessage.setMessageContentEndIndicator(null);
      incomingMessage.setMessage(payload);
      incomingMessage.setStatus(status);
      loggerService.logInterfaceIncomingMessage(incomingMessage);
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
      return outgoingErrorMessage.getOutMessageId();
   }
   
   public BigInteger insertIncomingErrorMessage(BigInteger messageId, HttpStatus httpStatus, String errorMessage) {
	      IncomingMessageErrorLog incomingErrorMessage = new IncomingMessageErrorLog();
	      incomingErrorMessage.setInMessageId(messageId);
	      incomingErrorMessage.setErrorCode("EXCEPTION");
	      if (Objects.nonNull(httpStatus)) {
	    	  incomingErrorMessage.setMessage(ConnectorUtils.getHttpStatusMessage(httpStatus));
	      } else {
	         if (Objects.nonNull(errorMessage)) {
	        	 incomingErrorMessage.setMessage(ConnectorUtils.getErrorMessage(errorMessage));
	         }
	      }
	      incomingErrorMessage.setLineItem(null);
	      loggerService.logInterfaceIncomingErrorMessage(incomingErrorMessage);
	      return incomingErrorMessage.getInMessageId();
	   }
   
   private void logOutgoingMessage(BigInteger referenceId, String status,String submessageType,String carrier,String flightNum,LocalDateTime flightDate) {
		OutgoingMessageLog outgoingMessage = new OutgoingMessageLog();
		outgoingMessage.setOutMessageId(referenceId);
		outgoingMessage.setChannelSent("HTTP");
		outgoingMessage.setInterfaceSystem("ICS");
		outgoingMessage.setSenderOriginAddress("COSYS");
		outgoingMessage.setMessageType("ICS");
		outgoingMessage.setSubMessageType(submessageType);
		outgoingMessage.setCarrierCode(carrier);
		outgoingMessage.setFlightNumber(flightNum);
		outgoingMessage.setFlightOriginDate(flightDate);
		outgoingMessage.setShipmentNumber(null);
		outgoingMessage.setShipmentDate(null);
		outgoingMessage.setRequestedOn(LocalDateTime.now());
		outgoingMessage.setSentOn(LocalDateTime.now());
		outgoingMessage.setAcknowledgementReceivedOn(LocalDateTime.now());
		outgoingMessage.setVersionNo(1);
		outgoingMessage.setSequenceNo(1);
		outgoingMessage.setMessageContentEndIndicator(null);
		outgoingMessage.setStatus(status);
		loggerService.logOutgoingMessage(outgoingMessage);
	}

	private BigInteger logOutgoingMessage(String payload,String submessageType) {
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
		outgoingMessage.setMessage(payload);
		loggerService.logInterfaceOutgoingMessage(outgoingMessage);
		return outgoingMessage.getOutMessageId();
	}
}