/**
 * {@link MessageResendUtils}
 * 
 * @copyright SATS Singapore 2019-20
 * @author NIIT
 */
package com.ngen.cosys.message.resend.util;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.ngen.cosys.message.resend.common.MessageMediumConstants;

/**
 * Incoming and Outgoing messages utility
 * 
 * @author NIIT Technologies Ltd
 */
public class MessageResendUtils {

   private static final Logger LOGGER = LoggerFactory.getLogger(MessageResendUtils.class);
   
   /**
    * @param payload
    * @return
    */
   public static String convertStringToBase64Text(Object payload) {
      return Base64.getEncoder().encodeToString(((String) payload).getBytes());
   }
   
   /**
    * Convert object to XML String
    * 
    * @param payload
    * @return
    * @throws Exception
    */
   public static Object convertObjectToXMLString(Object payload) {
      Object response = null;
      XmlMapper xmlMapper = new XmlMapper();
      try {
         response = xmlMapper.writeValueAsString(payload);
      } catch (Exception ex) {
         LOGGER.debug("Jackson XML Parsing error : convertObjectToXMLString - {}", ex);
      }
      return response;
   }

   /**
    * GET XML String to Object
    * 
    * @param payload
    * @param clazz
    * @return
    * @throws Exception
    */
   public static Object convertXMLStringToObject(Object payload, Class<?> clazz) {
      XmlMapper xmlMapper = new XmlMapper();
      Object object = null;
      try {
         object = xmlMapper.readValue(payload.toString(), clazz);
      } catch (Exception ex) {
         LOGGER.debug("Jackson XML Parsing error : convertXMLStringToObject - {}", ex);
      }
      return object;
   }
   
   /**
    * @param systemName
    * @param interfaceSystem
    * @param tenantID
    * @param loggerEnabled
    * @param esbMessageId
    * @return
    */
   public static Map<String, String> getPayloadHeaders(String systemName, String interfaceSystem, String tenantID,
         boolean loggerEnabled, BigInteger esbMessageId) {
      Map<String, String> requestHeaders = new HashMap<>();
      requestHeaders.put(MessageMediumConstants.LOGGER_ENABLED, String.valueOf(loggerEnabled));
      if (Objects.nonNull(systemName)) {
         requestHeaders.put(MessageMediumConstants.SYSTEM_NAME, systemName);
      }
      if (Objects.nonNull(interfaceSystem)) {
         requestHeaders.put(MessageMediumConstants.INTERFACE_SYSTEM, systemName);
      }
      if (Objects.nonNull(tenantID)) {
         requestHeaders.put(MessageMediumConstants.TENANT_ID, tenantID);
      }
      if (Objects.nonNull(esbMessageId)) {
         requestHeaders.put(MessageMediumConstants.ESB_MESSAGE_ID, String.valueOf(esbMessageId));
      }
      return requestHeaders;
   }
   
   /**
    * @param systemName
    * @param payload
    * @param referenceId
    * @param mediaType
    * @param security
    * @param authToken
    * @return
    */
   public static HttpEntity<String> getMessagePayload(String systemName, Object payload, MediaType mediaType,
         Map<String, String> requestHeaders, boolean security, String authToken) {
      // Headers
      final HttpHeaders headers = new HttpHeaders();
      headers.setAccept(Arrays.asList(mediaType));
      headers.setContentType(mediaType);
      //
      if (!CollectionUtils.isEmpty(requestHeaders)) {
         for (Map.Entry<String, String> entry : requestHeaders.entrySet())
            if (Objects.nonNull(entry.getKey()))
               headers.set(entry.getKey(), entry.getValue());
      }
      if (security && !StringUtils.isEmpty(authToken)) {
         headers.set(HttpHeaders.AUTHORIZATION, authToken);
      }
      //
      return new HttpEntity<>(payload.toString(), headers);
   }
   
}
