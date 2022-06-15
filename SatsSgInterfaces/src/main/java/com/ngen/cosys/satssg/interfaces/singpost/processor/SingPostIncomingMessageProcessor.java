package com.ngen.cosys.satssg.interfaces.singpost.processor;

import java.io.IOException;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.ngen.cosys.annotation.service.ShipmentProcessorService;
import com.ngen.cosys.events.consumer.logger.service.ApplicationLoggerService;
import com.ngen.cosys.events.esb.connector.logger.payload.IncomingMessageErrorLog;
import com.ngen.cosys.events.esb.connector.logger.payload.IncomingMessageLog;
import com.ngen.cosys.events.stream.business.processor.BaseBusinessEventStreamProcessor;
import com.ngen.cosys.events.stream.payload.SatsSgInterfacePayload;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.jsonhelper.ConvertJSONToObject;
import com.ngen.cosys.satssg.interfaces.singpost.model.MailBagResponseModel;
import com.ngen.cosys.satssg.interfaces.singpost.model.PullMailBagResponseModel;
import com.ngen.cosys.satssg.interfaces.singpost.service.ConsumeSingPostMessageService;

@Component("SingPostIncomingMessageEvent")
public class SingPostIncomingMessageProcessor implements BaseBusinessEventStreamProcessor {

	@Autowired
	ConvertJSONToObject convertJSONToObject;

	@Autowired
	ShipmentProcessorService shipmentDateService;

	@Autowired
	ApplicationLoggerService loggerService;

	@Autowired
	private ConsumeSingPostMessageService consumeSingPostMessageService;

	private String queueName;

	private static final Logger logger = LoggerFactory.getLogger(SingPostIncomingMessageProcessor.class);

	@Override
	public void process(Message<?> payload) throws JsonGenerationException, JsonMappingException, IOException {
		logger.warn("Inside SingPost Incoming messages event at : " + LocalDateTime.now());
		SatsSgInterfacePayload eventPayload = (SatsSgInterfacePayload) payload.getPayload();

		PullMailBagResponseModel singPostPayload = (PullMailBagResponseModel) convertJSONToObject
				.convertMapToObject(eventPayload.getPayload(), PullMailBagResponseModel.class);

		logger.warn("request after casting the payload to the model");

		StringBuilder allxmlpayload = new StringBuilder();
		allxmlpayload.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		allxmlpayload.append("\r\n");
		allxmlpayload.append("<Request>");
		allxmlpayload.append("\r\n");
		if (!CollectionUtils.isEmpty(singPostPayload.getMailBag())) {
			for (MailBagResponseModel mailBag : singPostPayload.getMailBag()) {
				if (!StringUtils.isEmpty(mailBag.getRecpID())) {
					// Inject shipment date
					LocalDate shipmentDate = shipmentDateService.getShipmentDate(mailBag.getRecpID().substring(0, 20));
					mailBag.setShipmentDate(shipmentDate);
					String xmlPayload = convertListOFObjectToString(mailBag);
					xmlPayload = xmlPayload.replaceAll("MailBagResponseModel", "Bag");
					allxmlpayload.append(xmlPayload);
				}

			}
			allxmlpayload.append("\r\n");
			allxmlpayload.append("</Request>");
			if (!StringUtils.isEmpty(allxmlpayload)) {
				List<MailBagResponseModel> response = null;
				try {
					logger.warn("SingPost Incoming messages event started processing");
					response = processMessages(singPostPayload);
					this.updateMessageStatus(allxmlpayload.toString(), "PROCESSED");
				} catch (Exception e) {
					logger.warn("SINGPOST not able to process: "+e);
					updateErrorLogs(allxmlpayload.toString(), singPostPayload, e);
					logger.warn("SINGPOST Error updated in incoming message log table");
				}

			}
		}
		logger.warn("SingPost Incoming messages event executed successfully at : " + LocalDateTime.now());
	}

	private String convertListOFObjectToString(Object payload) {

		JacksonXmlModule jacksonModule = new JacksonXmlModule();
		jacksonModule.setDefaultUseWrapper(false);
		//
		ObjectMapper objectMapper = new XmlMapper(jacksonModule);
		objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
		try {
			return objectMapper.writeValueAsString(payload);
		} catch (JsonProcessingException ex) {
		}
		return null;
	}

	private List<MailBagResponseModel> processMessages(final PullMailBagResponseModel pullMailBagResponseModel)
			throws CustomException {
		List<MailBagResponseModel> paSummaryMailBags = new ArrayList<>();
		List<MailBagResponseModel> paDetailMailBags = new ArrayList<>();
		List<MailBagResponseModel> dlvMailBags = new ArrayList<>();
		List<MailBagResponseModel> ipsAaMailBags = new ArrayList<>();
		for (MailBagResponseModel bag : pullMailBagResponseModel.getMailBag()) {
			if (!StringUtils.isEmpty(bag.getDispatchID()) && 20 == bag.getDispatchID().length()) {
				paSummaryMailBags.add(bag);
			} else if ("PA".equalsIgnoreCase(bag.getBagStatus()) && 29 == bag.getRecpID().length()) {
				paDetailMailBags.add(bag);
			} else if ("DL".equalsIgnoreCase(bag.getBagStatus())) {
				dlvMailBags.add(bag);
			} else if ("AA".equalsIgnoreCase(bag.getBagStatus())) {
				ipsAaMailBags.add(bag);
			} else {
			}
		}

		List<MailBagResponseModel> responseList = null;
		if (!CollectionUtils.isEmpty(paSummaryMailBags)) {
			this.queueName = "PA-SUMMARY";
			responseList = consumeSingPostMessageService.processPASummary(paSummaryMailBags);
		}
		if (!CollectionUtils.isEmpty(paDetailMailBags)) {
			this.queueName = "PA-DETAIL";
			List<MailBagResponseModel> responsePADetList = consumeSingPostMessageService
					.processPADetail(paDetailMailBags);
			if (!CollectionUtils.isEmpty(responseList)) {
				responseList.addAll(responsePADetList);
			}
		}
		if (!CollectionUtils.isEmpty(dlvMailBags)) {
			this.queueName = "DL";
			responseList = consumeSingPostMessageService.processDLV(dlvMailBags);
		}
		if (!CollectionUtils.isEmpty(ipsAaMailBags)) {
			this.queueName = "AA";
			responseList = consumeSingPostMessageService.processIPSAA(ipsAaMailBags);
		}
		return responseList;
	}

	private BigInteger updateMessageStatus(String allPayload, String status) {
		IncomingMessageLog incomingMessage = new IncomingMessageLog();
		incomingMessage.setChannelReceived("HTTP");
		incomingMessage.setInterfaceSystem("SINGPOST");
		incomingMessage.setSenderOriginAddress("SINGPOST");
		incomingMessage.setMessageType("SINGPOST");
		incomingMessage.setSubMessageType(this.queueName);
		incomingMessage.setMessage(allPayload);
		incomingMessage.setCarrierCode(null);
		incomingMessage.setFlightNumber(null);
		incomingMessage.setFlightOriginDate(null);
		incomingMessage.setShipmentNumber(null);
		incomingMessage.setShipmentDate(null);
		incomingMessage.setReceivedOn(LocalDateTime.now());
		incomingMessage.setVersionNo(1);
		incomingMessage.setSequenceNo(1);
		incomingMessage.setMessageContentEndIndicator(null);
		incomingMessage.setStatus(status);
		incomingMessage.setCreatedBy("SINGPOST");
		incomingMessage.setCreatedOn(LocalDateTime.now());
		// Logging the data into incoming message log table
		loggerService.logInterfaceIncomingMessage(incomingMessage);
		return incomingMessage.getInMessageId();
	}

	private void updateErrorLogs(String payload, PullMailBagResponseModel pullMailBagResponseModel, Exception e) {
		List<MailBagResponseModel> errorResponse = pullMailBagResponseModel.getMailBag().stream()
				.filter(obj -> !StringUtils.isEmpty(obj.getRecpID())).collect(Collectors.toList());
		StringBuilder allError = new StringBuilder();
		for (MailBagResponseModel errorData : errorResponse) {
			if (!CollectionUtils.isEmpty(errorData.getMessageList())) {
				if (StringUtils.isEmpty(allError)) {
					allError.append(errorData.getMessageList().get(0).getCode());
				} else {
				   if(!allError.toString().contains(errorData.getMessageList().get(0).getCode())) {
				      allError.append("\r\n");
	                  allError.append(errorData.getMessageList().get(0).getCode());
				   }
				}
			}

		}
		IncomingMessageErrorLog errorLog = new IncomingMessageErrorLog();
		BigInteger messageLogId = updateMessageStatus(payload, "ERROR");
		errorLog.setInMessageId(messageLogId);
		errorLog.setCreatedBy("SINGPOST");
		errorLog.setCreatedOn(LocalDateTime.now());
		errorLog.setErrorCode("ERROR");
		errorLog.setMessage(e.toString());
		if(!StringUtils.isEmpty(allError.toString())) {
			errorLog.setLineItem(allError.toString());
		}
		else {
			errorLog.setLineItem("ERROR");
		}
		loggerService.logInterfaceIncomingErrorMessage(errorLog);

	}

}
