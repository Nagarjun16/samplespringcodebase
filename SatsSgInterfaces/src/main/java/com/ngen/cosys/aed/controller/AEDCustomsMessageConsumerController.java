/**
 * This is a ESB connector consumer controller class for AED customs system
 */
package com.ngen.cosys.aed.controller;

import java.time.LocalDateTime;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.ngen.cosys.aed.model.ScInspecRmkGhaResponseModel;
import com.ngen.cosys.aed.model.ScSumofWtGhaResposeModel;
import com.ngen.cosys.aed.service.OutboundShipmentEAcceptanceService;
import com.ngen.cosys.aed.validators.HeaderValidations;
import com.ngen.cosys.annotation.NgenCosysAppInfraAnnotation;
import com.ngen.cosys.annotation.PostRequest;
import com.ngen.cosys.events.consumer.logger.service.ApplicationLoggerService;
import com.ngen.cosys.events.esb.connector.logger.payload.IncomingMessageLog;
import com.ngen.cosys.events.esb.connector.logger.service.ConnectorLoggerService;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.ics.enums.ResponseStatus;
import com.ngen.cosys.ics.model.ResponseModel;

import io.swagger.annotations.ApiOperation;

@NgenCosysAppInfraAnnotation
public class AEDCustomsMessageConsumerController {

	@Autowired
	BeanFactory beanFactory;

	@Autowired
	OutboundShipmentEAcceptanceService eService;

	@Autowired
	ConnectorLoggerService connectorLogger;
	
	@Autowired
	private ApplicationLoggerService loggerService;


	@Autowired
	Validator validator;

	String queueName;

	private static final Logger LOGGER = LoggerFactory.getLogger(AEDCustomsMessageConsumerController.class);

	@SuppressWarnings("static-access")
	@ApiOperation("API method to consume messages pushed from ESB Connector")
	@PostRequest(path = "/api/aed/consume", method = RequestMethod.POST, consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
	public ResponseEntity<Object> consume(@RequestBody ScSumofWtGhaResposeModel requestModel,
			HttpServletRequest request) throws CustomException, JsonProcessingException {
		LOGGER.info("Enter into the Respective Service " + eService);

		String status = null;
		ResponseModel responseModel = null;
		
		if (requestModel != null && requestModel.getHeader() != null) {
			responseModel = HeaderValidations.modelValidation(requestModel.getHeader());
		} else {
			responseModel = new ResponseModel();
			responseModel.setStatus(ResponseStatus.FAIL);
			responseModel.setErrorNumber(HttpStatus.BAD_REQUEST.toString());
			responseModel.setErrorDescription("Header can not be null");
		}

		if (requestModel != null && requestModel.getDetails() != null) {
			//responseModel = ScSumOfWaightGhaDetailsValidator.validate(requestModel.getDetails());
		} else {
			responseModel = new ResponseModel();
			responseModel.setStatus(ResponseStatus.FAIL);
			responseModel.setErrorNumber(HttpStatus.BAD_REQUEST.toString());
			responseModel.setErrorDescription("ScSumOfWtGhaRequestDetails can not be null");
		}

		if (responseModel == null) {
			XmlMapper xmlMapper = new XmlMapper();
			String xmlData = xmlMapper.writeValueAsString(requestModel);
			status = eService.insertOuboundShipmentSumOfDeclaredWaight(requestModel);
				this.queueName = "AED2";
				if(status.equalsIgnoreCase("Success")) {
					status="RECEIVED";
				}else {
					status="REJECTED";
				}
				updateIncomingMessageLog(this.queueName,status,requestModel.getDetails().getMawbNo(),xmlData);
				responseModel = new ResponseModel();
				responseModel.setStatus(ResponseStatus.SUCCESS);
				return new ResponseEntity<>(responseModel, HttpStatus.OK);
		}
		return new ResponseEntity<>(responseModel, HttpStatus.BAD_REQUEST);
	}

	//AED3
	@SuppressWarnings("static-access")
	@ApiOperation("API method to consume messages pushed from ESB Connector")
	@PostRequest(path = "/api/aed/consume/remarks", method = RequestMethod.POST, consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
	public ResponseEntity<Object> consumeRemarks(@RequestBody ScInspecRmkGhaResponseModel requestModel,
			HttpServletRequest request) throws CustomException, JsonProcessingException {

		String status = null;
		LOGGER.info("Enter into the Respective Service " + eService);

		ResponseModel responseModel = null;

		if (requestModel != null && requestModel.getHeader() != null) {
			responseModel = HeaderValidations.modelValidation(requestModel.getHeader());
		} else {
			responseModel = new ResponseModel();
			responseModel.setStatus(ResponseStatus.FAIL);
			responseModel.setErrorNumber(HttpStatus.BAD_REQUEST.toString());
			responseModel.setErrorDescription("Header can not be null");
		}
		if (requestModel != null && requestModel.getDetails() != null) {
			//responseModel = ScInspectionRemarksValidator.validate(requestModel.getDetails());
		} else {
			responseModel = new ResponseModel();
			responseModel.setStatus(ResponseStatus.FAIL);
			responseModel.setErrorNumber(HttpStatus.BAD_REQUEST.toString());
			responseModel.setErrorDescription("ScInspecRmkGhaRequestDetails can not be null");
		}

		if (responseModel == null) {
			XmlMapper xmlMapper = new XmlMapper();
			String xmlData = xmlMapper.writeValueAsString(requestModel);
			status = eService.insertOuboundShipmentInspectionRemarks(requestModel);
				this.queueName = "AED3";
				if(status.equalsIgnoreCase("Success")) {
					status="RECEIVED";
				}else {
					status="REJECTED";
				}
				updateIncomingMessageLog(this.queueName,status,requestModel.getDetails().getMawbNo(),xmlData);
				responseModel = new ResponseModel();
				responseModel.setStatus(ResponseStatus.SUCCESS);
				return new ResponseEntity<>(responseModel, HttpStatus.OK);
			
		}
		return new ResponseEntity<>(responseModel, HttpStatus.BAD_REQUEST);
	}
	
	private void updateIncomingMessageLog(String type,String status,String awbNumber,String payload) {
	      IncomingMessageLog incomingMessage = new IncomingMessageLog();
	      incomingMessage.setChannelReceived("HTTP");
	      incomingMessage.setInterfaceSystem("AED");
	      incomingMessage.setMessageType("AED");
	      incomingMessage.setSubMessageType(type);
	      incomingMessage.setCarrierCode(null);
	      incomingMessage.setFlightNumber(null);
	      incomingMessage.setFlightOriginDate(null);
	      incomingMessage.setShipmentNumber(awbNumber);
	      incomingMessage.setShipmentDate(null);
	      incomingMessage.setReceivedOn(LocalDateTime.now());
	      incomingMessage.setVersionNo(1);
	      incomingMessage.setSequenceNo(1);
	      incomingMessage.setMessage(payload);
	      incomingMessage.setMessageContentEndIndicator(null);
	      incomingMessage.setStatus(status);
	      loggerService.logInterfaceIncomingMessage(incomingMessage);
	   }

}