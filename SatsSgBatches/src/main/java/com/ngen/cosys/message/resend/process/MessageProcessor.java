/**
 * {@link MessageProcessor}
 * 
 * @copyright SATS Singapore 2019-20
 * @author NIIT
 */
package com.ngen.cosys.message.resend.process;

import java.net.SocketTimeoutException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import com.ngen.cosys.message.resend.common.InterfaceSystemConstants;
import com.ngen.cosys.message.resend.config.IncomingInterfaceConfig;
import com.ngen.cosys.message.resend.core.MessageResponse;
import com.ngen.cosys.message.resend.enums.MessageType;
import com.ngen.cosys.multitenancy.support.CosysApplicationContext;

/**
 * Message Resend Processor
 * 
 * @author NIIT Technologies Ltd
 */
public class MessageProcessor {

   protected static final Logger LOGGER = LoggerFactory.getLogger(MessageProcessor.class);
   
   /**
    * Route Object payload to ESB Connector
    * 
    * @param payload
    * @param connectorURL
    * @param interfaceSystem
    * @param payloadHeaders
    * @param incomingMessage
    * @return
    */
   protected MessageResponse route(Object payload, String connectorURL, String interfaceSystem,
         Map<String, String> payloadHeaders, boolean incomingMessage) {
      // Exception holder if any
      String errorMessage = null;
      MessageResponse response = null;
      ResponseEntity<Object> responseEntity = null;
      try {
         RestTemplate restTemplate = CosysApplicationContext.getRestTemplate();
         responseEntity = restTemplate.exchange(connectorURL, HttpMethod.POST,
               getMessagePayload(payload, getMediaType(interfaceSystem, incomingMessage), payloadHeaders, false),
               Object.class);
      } catch (Exception ex) {
         LOGGER.error("Message Processor Exception occurred - Interface System - {}, Error Message - {}",
               interfaceSystem, String.valueOf(ex));
         errorMessage = String.valueOf(ex);
      }
      response = getMessageResponse(responseEntity, errorMessage);
      return response;
   }

   /**
    * Route String payload to ESB Connector
    * 
    * @param payload
    * @param connectorURL
    * @param interfaceSystem
    * @param payloadHeaders
    * @param incomingMessage
    * @param isTimeoutFeatureRequired - To state HTTP timeout need to add
    * @return
    */
   protected MessageResponse routeString(String payload, String connectorURL, String interfaceSystem,
         Map<String, String> payloadHeaders, boolean incomingMessage, boolean isTimeoutFeatureRequired) {
		// Exception holder if any
		String errorMessage = null;
		MessageResponse response = null;
		ResponseEntity<String> responseEntity = null;
		try {
			// Set time out for HTTP connection
			SimpleClientHttpRequestFactory clientHttpRequest = new SimpleClientHttpRequestFactory();
			if (isTimeoutFeatureRequired) {
				clientHttpRequest.setConnectTimeout(180000);        //3 minutes
				clientHttpRequest.setReadTimeout(180000);			//3 minutes
			}
			RestTemplate restTemplate = CosysApplicationContext.getRestTemplate(clientHttpRequest);
			responseEntity = restTemplate.exchange(connectorURL, HttpMethod.POST,
					getMessagePayload(payload, getMediaType(interfaceSystem, incomingMessage), payloadHeaders, true),
					String.class);
		} 
//			catch (SocketTimeoutException ex) {
//			
//			responseEntity = new ResponseEntity<>(HttpStatus.OK);
//		} 
		catch (Exception ex) {
			if (ex.getCause() instanceof SocketTimeoutException){
				
				// In case of socket/http time out exception simply assuming message is
				// successfully delivered to end point and result is handled by that processor
				// itself and not by invoker
				LOGGER.error(
						"Message Processor PlainText Router FFM SocketTimeoutException Exception occurred - Interface System - {}, Error Message - {}",
						interfaceSystem, String.valueOf(ex));
				errorMessage = String.valueOf(ex);
				responseEntity = new ResponseEntity<>(HttpStatus.OK);
			}else {
				LOGGER.error(
						"Message Processor PlainText Router Exception occurred - Interface System - {}, Error Message - {}",
						interfaceSystem, String.valueOf(ex));
				errorMessage = String.valueOf(ex);
			}
		}
		response = getMessageResponse(responseEntity, errorMessage);
		return response;
   }
   
   /**
    * @param payload
    * @param interfaceSystem
    * @param endPointKeys
    * @param incomingInterfaceConfigList
    * @param messageType
    * @param payloadHeaders
    * @param incomingMessage
    * @param isTimeoutFeatureRequired - To state timeout to be added with HTTP call
    * @return MessageResponse
    */
   protected MessageResponse routeToInterface(String payload, String interfaceSystem, Set<String> endPointKeys,
         List<IncomingInterfaceConfig> incomingInterfaceConfigList, String messageType,
         Map<String, String> payloadHeaders, boolean incomingMessage, boolean isTimeoutFeatureRequired) {
		MessageResponse response = null;
		String interfaceURL = getInterfacePath(interfaceSystem, endPointKeys, incomingInterfaceConfigList, messageType);
		if (!StringUtils.isEmpty(interfaceURL)) {

			// Appeng message type if the message is FFM/FWB/FHL for cargo messaging
			// interface
			if (!StringUtils.isEmpty(messageType) && (MessageType.FFM.name().equalsIgnoreCase(messageType)
					|| MessageType.FWB.name().equalsIgnoreCase(messageType)
					|| MessageType.FHL.name().equalsIgnoreCase(messageType))) {
				interfaceURL = interfaceURL + "/" + messageType.toLowerCase();
			}

			response = routeString(payload, interfaceURL, interfaceSystem, payloadHeaders, incomingMessage,
					isTimeoutFeatureRequired);
			if (Objects.nonNull(response.getHttpStatus()) && response.getHttpStatus().is2xxSuccessful()) {
				return response;
			} else {
				if (!StringUtils.isEmpty(response.getErrorMessage()) && StringUtils.isEmpty(messageType)) {
					return routeToInterface(payload, interfaceSystem, endPointKeys, incomingInterfaceConfigList,
							messageType, payloadHeaders, incomingMessage, isTimeoutFeatureRequired);
				}
			}
		}
		return response;
   }
   
   /**
    * @param interfaceSystem
    * @param endPointKeys
    * @param incomingInterfaceConfigList
    * @param messageType
    * @return
    */
   private String getInterfacePath(String interfaceSystem, Set<String> endPointKeys,
         List<IncomingInterfaceConfig> incomingInterfaceConfigList, String messageType) {
      String endPointURL = null;
      for (IncomingInterfaceConfig interfaceConfig : incomingInterfaceConfigList) {
         if (Objects.equals(interfaceSystem, interfaceConfig.getSystemName())) {
            if (Objects.equals(interfaceSystem, InterfaceSystemConstants.SYSTEM_CCN)
                  && !StringUtils.isEmpty(messageType) && !StringUtils.isEmpty(interfaceConfig.getMessageType())) {
               if (validMessageType(messageType, interfaceConfig.getMessageType())
                     && endPointKeys.add(interfaceConfig.getEndPointUrl())) {
                  endPointURL = interfaceConfig.getEndPointUrl();
               } else {
                  continue;
               }
            } else if (endPointKeys.add(interfaceConfig.getEndPointUrl())) {
               endPointURL = interfaceConfig.getEndPointUrl();
            }
            if (!StringUtils.isEmpty(endPointURL)) {
               break;
            }
         }
      }
      LOGGER.info("Interface System Name : {}, End Point URL : {} ", interfaceSystem, endPointURL);
      return endPointURL;
   }
   
   /**
    * @param messageType
    * @param configMessageType
    * @return
    */
   private boolean validMessageType(String messageType, String configMessageType) {
      return configMessageType.contains(messageType);
   }
   
   /**
    * GET Message Response
    * 
    * @param response
    * @return
    */
   private MessageResponse getMessageResponse(ResponseEntity<?> responseEntity, String errorMessage) {
      MessageResponse response = new MessageResponse();
      if (Objects.nonNull(responseEntity)) {
         response.setHttpStatus(responseEntity.getStatusCode());
         response.setBody(responseEntity.getBody());
      }
      if (!StringUtils.isEmpty(errorMessage)) {
         response.setErrorMessage(errorMessage);
      }
      return response;
   }
   
   /**
    * Media Type
    * 
    * @param interfaceSystem
    * @param incomingMessage
    * @return
    */
   private MediaType getMediaType(String interfaceSystem, boolean incomingMessage) {
      if (StringUtils.isEmpty(interfaceSystem)) {
         throw new IllegalArgumentException("Message Interface System cannot be blank..!");
      }
      return incomingMessage ? MediaType.APPLICATION_XML : MediaType.APPLICATION_JSON;
   }

   /**
    * GET Message Payload
    * 
    * @param payload
    * @param mediaType
    * @param payloadHeaders
    * @param plainTextPayload
    * @return
    */
   private HttpEntity<?> getMessagePayload(Object payload, MediaType mediaType,
         Map<String, String> payloadHeaders, boolean plainTextPayload) {
      final HttpHeaders headers = new HttpHeaders();
      //
      headers.setAccept(Arrays.asList(mediaType));
      headers.setContentType(mediaType);
      if (!CollectionUtils.isEmpty(payloadHeaders)) {
         for (Map.Entry<String, String> entry : payloadHeaders.entrySet())
            if (Objects.nonNull(entry.getKey()))
               headers.set(entry.getKey(), entry.getValue());
      }
      //
      return new HttpEntity<>(plainTextPayload ? payload.toString() : payload, headers);
   }

}
