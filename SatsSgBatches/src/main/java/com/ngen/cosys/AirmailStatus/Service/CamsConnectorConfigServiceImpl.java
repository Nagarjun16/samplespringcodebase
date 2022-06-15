package com.ngen.cosys.AirmailStatus.Service;

import java.io.IOException;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import org.apache.commons.collections4.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.ws.client.core.WebServiceMessageCallback;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.transport.context.TransportContext;
import org.springframework.ws.transport.context.TransportContextHolder;
import org.springframework.ws.transport.http.HttpUrlConnection;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.ibsplc.icargo.business.mailtracking.defaults.types.standard.SaveMailDetailsRequestType;
import com.ibsplc.icargo.business.mailtracking.defaults.types.standard.SaveMailDetailsResponseType;
import com.ngen.cosys.WebServiceTemplateConfiguration;
import com.ngen.cosys.AirmailStatus.DAO.AirmailStatusDAO;
import com.ngen.cosys.events.consumer.logger.service.ApplicationLoggerService;
import com.ngen.cosys.events.esb.connector.logger.payload.IncomingMessageErrorLog;
import com.ngen.cosys.events.esb.connector.logger.payload.IncomingMessageLog;
import com.ngen.cosys.framework.exception.CustomException;

@Service
public class CamsConnectorConfigServiceImpl implements CamsConnectorConfigService {

	@Autowired
	private WebServiceTemplate saveWebServiceTemplate;
	
	@Autowired
	@Qualifier("wsTemplate")
	private WebServiceTemplate wsTemplate;

	@Autowired
	ApplicationLoggerService loggerService;
	
	@Autowired
	AirmailStatusDAO airmailStatusDao;

	private static final String CAMS = "CAMS";
	private static final String PROCESSED = "PROCESSED";
	private static final String ERROR = "ERROR";
	private static final String FALSE = "F";

	private static Logger logger = LoggerFactory.getLogger(CamsConnectorConfigServiceImpl.class);

	public SaveMailDetailsResponseType connectorConfig(@RequestBody SaveMailDetailsRequestType req)
			throws CustomException {
		logger.warn("Before sending the payload to CAMS URL: " + req);
		SaveMailDetailsResponseType response = getResponse(req);
		logger.warn("getting the response after triggering the message to the CAMS url for SQ: " + response);
		String payload = convertListOFObjectToString(response);
		logDataIntoIncomingMessageLogs(payload, response, req);
		return response;
	}

	private Map<String, String> getHeader() {
		Map<String, String> headers = new HashedMap<>();
		headers.put("icargo-identitytoken",
				"aWNTZXNzaW9uSWQ6RkdwUkYlMkZ0NmZmOFdEYmdVWUtFTXRlJTJCbDdNdkp5Z1E4ME0wQnYzT0o5aFpwR1VnV0UlMkZOV3Q5akg3akFqdG1DU1JuWkdpeG95Rk45eiUwRCUwQWduR250VHBqVGclM0QlM0Q=");
		logger.warn("Headers for the token of CAMS: " + headers);
		return headers;
	}

	private SaveMailDetailsResponseType getResponse(SaveMailDetailsRequestType request) {
	/*	Wss4jSecurityInterceptor wss4jSecurityInterceptor = new Wss4jSecurityInterceptor();
		wss4jSecurityInterceptor.setSecurementActions("UsernameToken");
		wss4jSecurityInterceptor.setSecurementUsername("ICGUSR");
		wss4jSecurityInterceptor.setSecurementPassword("iCargoSQ");
		wss4jSecurityInterceptor.setSecurementPasswordType("PasswordText");
		ClientInterceptor[] interceptors = { wss4jSecurityInterceptor };
		saveWebServiceTemplate.setInterceptors(interceptors);
		SaveMailDetailsResponseType response = (SaveMailDetailsResponseType) saveWebServiceTemplate
				.marshalSendAndReceive(request, getRequestCallback(getHeader()));
		*/	
		 
		SaveMailDetailsResponseType response = (SaveMailDetailsResponseType)
		wsTemplate.marshalSendAndReceive(request, getRequestCallback(WebServiceTemplateConfiguration.headerMap));
		 
		logger.warn("Response for webservicetemplate: " + response);
		return response;
	}

	private WebServiceMessageCallback getRequestCallback(Map<String, String> headers) {
		return message -> {
			TransportContext context = TransportContextHolder.getTransportContext();
			HttpUrlConnection connection = (HttpUrlConnection) context.getConnection();
			addHeadersToConnection(connection, headers);
		};
	}

	private void addHeadersToConnection(HttpUrlConnection connection, Map<String, String> headers) {
		headers.forEach((name, value) -> {
			try {
				connection.addRequestHeader(name, value);
			} catch (IOException e) {
				e.printStackTrace(); // or whatever you want
			}
		});
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

	private BigInteger logDataIntoIncomingMessageLogs(String payload, SaveMailDetailsResponseType response,
			SaveMailDetailsRequestType req) {
		IncomingMessageLog incomingMessage = new IncomingMessageLog();
		incomingMessage.setChannelReceived("HTTP");
		incomingMessage.setInterfaceSystem(CAMS);
		incomingMessage.setSenderOriginAddress(CAMS);
		incomingMessage.setMessageType(CAMS);
		incomingMessage.setSubMessageType(req.getMailDetails().get(0).getScanType());
		incomingMessage.setMessage(payload);
		incomingMessage.setCarrierCode(req.getMailDetails().get(0).getCarrierCode());
		incomingMessage.setFlightNumber(req.getMailDetails().get(0).getFlightNumber());
		if(!StringUtils.isEmpty(req.getMailDetails().get(0).getFlightDate())) {
			LocalDate flightDate = LocalDate.parse(req.getMailDetails().get(0).getFlightDate(),
					DateTimeFormatter.ofPattern("d-MMM-yyyy"));
			incomingMessage.setFlightOriginDate(LocalDateTime.of(flightDate, LocalTime.of(0, 0)));
		}
		if(!StringUtils.isEmpty(req.getMailDetails().get(0).getMailTag())) {
			incomingMessage.setShipmentNumber(req.getMailDetails().get(0).getMailTag().substring(0,20));
		}
		incomingMessage.setShipmentDate(null);
		incomingMessage.setReceivedOn(LocalDateTime.now(ZoneOffset.UTC));
		incomingMessage.setVersionNo(1);
		incomingMessage.setSequenceNo(1);
		incomingMessage.setMessageContentEndIndicator(null);
		if (FALSE.equalsIgnoreCase(response.getErrorFlag())) {
			incomingMessage.setStatus(ERROR);
		} else {
			incomingMessage.setStatus(PROCESSED);
		}
		incomingMessage.setCreatedBy(CAMS);
		incomingMessage.setCreatedOn(LocalDateTime.now(ZoneOffset.UTC));
		// Logging the data into incoming message log table
		loggerService.logInterfaceIncomingMessage(incomingMessage);
		if (FALSE.equalsIgnoreCase(response.getErrorFlag())) {
			logMessageIntoIncomingErrorMessageLog(response, incomingMessage.getInMessageId());
		}
		return incomingMessage.getInMessageId();

	}

	private void logMessageIntoIncomingErrorMessageLog(SaveMailDetailsResponseType response, BigInteger incomingLogId) {
		IncomingMessageErrorLog errorLog = new IncomingMessageErrorLog();
		errorLog.setInMessageId(incomingLogId);
		errorLog.setCreatedBy(CAMS);
		errorLog.setCreatedOn(LocalDateTime.now(ZoneOffset.UTC));
		errorLog.setErrorCode(response.getMailResponseDetails().get(0).getErrorCode());
		try {
			errorLog.setMessage(airmailStatusDao.getErrorMessage(errorLog.getErrorCode()));
		} catch (CustomException e) {}
		if(StringUtils.isEmpty(errorLog.getMessage())) {
		   errorLog.setMessage("Error not defined in the system"); 
		}
	    errorLog.setLineItem("ERROR");
		loggerService.logInterfaceIncomingErrorMessage(errorLog);
		
	}
}
