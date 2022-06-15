package com.ngen.cosys.icms.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ngen.cosys.esb.route.ConnectorService;
import com.ngen.cosys.icms.dao.flightbooking.FlightBookingDao;
import com.ngen.cosys.icms.model.BookingInterface.InterfaceExternalSystemUrl;
import com.ngen.cosys.icms.model.bookingicms.BookingPublishOutboundSampleBo;

@Component
public class SoapServiceUtil {
	public SoapServiceUtil() {

	}

	private static final Logger LOGGER = LoggerFactory.getLogger(SoapServiceUtil.class);
	@Autowired
	ConnectorService connectorService;

	@Autowired
	BookingPublishOutboundSampleBo bo;
	
	@Autowired
	FlightBookingDao flightBookingDao;
	

	/**
	 * call icms soap services
	 * 
	 * @param requestXML
	 * @param serviceName
	 * @return
	 * @throws IOException
	 * @throws SOAPException
	 * @throws UnsupportedOperationException
	 */
	public String callSoapService(String requestXML, String serviceName)
			throws UnsupportedOperationException, SOAPException, IOException {
		

		String soapEndpointUrl = connectorService.getServiceURL(serviceName);
		String soapAction = connectorService.getServiceURL(BookingConstant.ICMS_ACTION);
		// String soapEndpointUrl =
		// "https://apidev.sq.com.sg/SATS/CapacityBookingService/saveBookingDetails";
		// String soapAction =
		// "https://apidev.sq.com.sg/SATS/CapacityBookingService/saveBookingDetails";

		callSoapWebService(soapEndpointUrl, soapAction);
		return "";
	}

	/**
	 * create soap envelope
	 * 
	 * @param soapMessage
	 * @throws SOAPException
	 */
	private static void createSoapEnvelope(SOAPMessage soapMessage) throws SOAPException {
		SOAPPart soapPart = soapMessage.getSOAPPart();

		String myNamespace = "n:3";
		String myNamespaceURI = "https://apidev.sq.com.sg";

		// SOAP Envelope
		SOAPEnvelope envelope = soapPart.getEnvelope();
		envelope.addNamespaceDeclaration(myNamespace, myNamespaceURI);

		// SOAP Body
		SOAPBody soapBody = envelope.getBody();
//	        soapBody.addAttribute(name, value)

	}

	/**
	 * call soap web service
	 * 
	 * @param soapEndpointUrl
	 * @param soapAction
	 * @throws SOAPException
	 * @throws UnsupportedOperationException
	 * @throws IOException
	 */
	private static void callSoapWebService(String soapEndpointUrl, String soapAction)
			throws UnsupportedOperationException, SOAPException, IOException {
		SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
		SOAPConnection soapConnection = soapConnectionFactory.createConnection();
		SOAPMessage soapResponse = soapConnection.call(createSOAPRequest(soapAction), soapEndpointUrl);

	}

	/**
	 * create soap request
	 * 
	 * @param soapAction
	 * @return
	 * @throws SOAPException
	 * @throws IOException
	 * @throws Exception
	 */
	private static SOAPMessage createSOAPRequest(String soapAction) throws SOAPException, IOException {
		MessageFactory messageFactory = MessageFactory.newInstance();
		SOAPMessage soapMessage = messageFactory.createMessage();
		createSoapEnvelope(soapMessage);
		MimeHeaders headers = soapMessage.getMimeHeaders();
		headers.addHeader("SOAPAction", soapAction);
		soapMessage.saveChanges();

		return soapMessage;
	}
	
	// FOR SIT
	public String postSOAPXML(String input, String action) {
		StringBuilder builder = new StringBuilder();
		try (CloseableHttpClient client = HttpClientBuilder.create().build()) {

            HttpPost request = new HttpPost("https://apidev.sq.com.sg/SATS/CapacityBookingService/?api_key=9mutwcgbcxtvytqprem34c59");
           
            StringEntity strEntity = new StringEntity(input, "text/xml", "UTF-8");
            request.addHeader("api_key","9mutwcgbcxtvytqprem34c59");
            request.addHeader("Content-Type", "application/xml");
			
            request.setEntity(strEntity);
            
            HttpResponse response = client.execute(request);

            BufferedReader bufReader = new BufferedReader(new InputStreamReader(
                    response.getEntity().getContent()));

            String line;

            while ((line = bufReader.readLine()) != null) {
                
                builder.append(line);
                builder.append(System.lineSeparator());
            }

            System.out.println(builder);
        }catch(Exception e) {
        	e.printStackTrace();
        }
		return builder.toString();
    }

	/**
	 * call icms service 
	 * @param requestXml
	 * @param action
	 * @param requestType
	 * @return
	 */
	public Map<String,String> callIcmsService(String requestXml, String action,String requestType) {
		Map<String,String> responseMap = new HashMap<>();
		StringBuilder requestPayload = new StringBuilder();
		StringBuilder builder = new StringBuilder();
		try (CloseableHttpClient client = HttpClientBuilder.create().build()) {
			InterfaceExternalSystemUrl externalSystemParam =  flightBookingDao.getExternalUrlDetails(BookingConstant.ICMS);
			requestXml = modifyRequestXml(requestXml, requestType);
			
			String payloadHeader = getRequestPayloadHeader(externalSystemParam);
			
			String payloadFooter="</soapenv:Body> </soapenv:Envelope>";
			requestPayload = requestPayload.append(payloadHeader).append(requestXml).append(payloadFooter);
			
			StringEntity requestEntity = new StringEntity(requestPayload.toString(), "text/xml", "UTF-8");
			HttpPost request = new HttpPost(externalSystemParam.getEndPointUrl());
            request.addHeader("api_key",externalSystemParam.getToken());
            request.addHeader("Content-Type", "application/xml");	
            request.setEntity(requestEntity);
            
            HttpResponse response = client.execute(request);
            BufferedReader bufReader = new BufferedReader(new InputStreamReader(
                    response.getEntity().getContent()));
            String line;
            while ((line = bufReader.readLine()) != null) {   
                builder.append(line);
                builder.append(System.lineSeparator());
            }
            LOGGER.info("Method End callIcmsService -> builder-> "+builder.toString());
            responseMap.put("responseXml", builder.toString());
            responseMap.put("statusCode", String.valueOf(response.getStatusLine().getStatusCode()));
            LOGGER.info("Method End callIcmsService -> response-> "+response.toString());
        }catch(Exception e) {
        	LOGGER.info("Method End callIcmsService -> Exception -> "+e.getMessage());
        }
		return responseMap;
    }

	/**
	 * @param externalSystemParam
	 * @return
	 */
	private String getRequestPayloadHeader(InterfaceExternalSystemUrl externalSystemParam) {
		String payloadHeader="<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:_01=\"http://www.ibsplc.com/icargo/services/CapacityBookingService/2013/06/04_01\" xmlns:_011=\"http://www.ibsplc.com/icargo/services/types/CapacityBookingService/2013/06/04_01\">\r\n" + 
				"                <soapenv:Header>\r\n" + 
				"                                <wsse:Security xmlns:wsse=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\">\r\n" + 
				"                                                <wsse:UsernameToken>\r\n" + 
				"                                                                <wsse:Username>ICMS_OUTBOUND_USER</wsse:Username>\r\n" + 
				"                                                                <wsse:Password>ICMS_OUTBOUND_PASSWORD</wsse:Password>\r\n" + 
				"                                                </wsse:UsernameToken>\r\n" + 
				"                                </wsse:Security>\r\n" + 
				"                </soapenv:Header>\r\n" + 
				"                <soapenv:Body>";
		payloadHeader = payloadHeader.replace("ICMS_OUTBOUND_USER",externalSystemParam.getUserName());
		payloadHeader = payloadHeader.replace("ICMS_OUTBOUND_PASSWORD",externalSystemParam.getPassword());
		System.out.println("Payload header -> "+payloadHeader);
		return payloadHeader;
	}

	/**
	 * @param requestXml
	 * @param requestType
	 * @return
	 */
	private String modifyRequestXml(String requestXml, String requestType) {
		if(requestType.equalsIgnoreCase(BookingConstant.SAVE_REQUEST)) {
			requestXml = requestXml.replaceAll("ns2:SaveBookingDetailsRequest xmlns:ns2=\"http://www.ibsplc.com/icargo/services/types/CapacityBookingService/2013/06/04_01\"", "_01:SaveBookingDetailsRequest");
			requestXml = requestXml.replaceAll("</ns2:SaveBookingDetailsRequest>","</_01:SaveBookingDetailsRequest>");
		}else if(requestType.equalsIgnoreCase(BookingConstant.VALIDATE_REQUEST)) {
			requestXml = requestXml.replaceAll("ns2:ValidateBookingRequest xmlns:ns2=\"http://www.ibsplc.com/icargo/services/types/CapacityBookingService/2013/06/04_01\"", "_01:ValidateBookingRequest");
			requestXml = requestXml.replaceAll("</ns2:ValidateBookingRequest>","</_01:ValidateBookingRequest>");
		}else if(requestType.equalsIgnoreCase(BookingConstant.CANCEL_REQUEST)) {
			requestXml = requestXml.replaceAll("ns2:CancelBookingRequest xmlns:ns2=\"http://www.ibsplc.com/icargo/services/types/CapacityBookingService/2013/06/04_01\"", "_01:CancelBookingRequest");
			requestXml = requestXml.replaceAll("</ns2:CancelBookingRequest>","</_01:CancelBookingRequest>");
		}
		requestXml = requestXml.replaceAll("ns2","_011");
		System.out.println("After replacing requestXml ->"+requestXml);
		return requestXml;
	}
}