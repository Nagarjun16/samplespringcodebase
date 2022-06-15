/**
 * 
 * ESBConnector.java
 * 
 * Copyright <PRE><IMG SRC = XX></IMG></PRE>
 *
 * Version      Date         Author      Reason
 * 1.0          11 JUL, 2018   NIIT      -
 */
package com.ngen.cosys.esb.connector.util;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.ngen.cosys.esb.connector.enums.ESBRouterTypeUtils;

import lombok.NoArgsConstructor;

/**
 * This class is used for ESB Connector common Utility required for interfacing
 * 
 * @author NIIT Technologies Ltd
 *
 */
@NoArgsConstructor
public class ConnectorUtils {

   /**
    * @param messageId
    * @param loggerEnabled
    * @param systemName
    * @param tenanID
    * @return
    */
   public static Map<String, String> getPayloadHeaders(BigInteger messageId, boolean loggerEnabled, String systemName, String tenantID) {
      //
      Map<String, String> headers = new HashMap<>();
      headers.put(ESBRouterTypeUtils.LOGGER_ENABLED.getName(), String.valueOf(loggerEnabled));
      if (Objects.nonNull(messageId)) {
         headers.put(ESBRouterTypeUtils.MESSAGE_ID.getName(), String.valueOf(messageId));
      }
      if (Objects.nonNull(systemName)) {
         headers.put(ESBRouterTypeUtils.INTERFACE_SYSTEM.getName(), systemName);
      }
      if (Objects.nonNull(tenantID)) {
         headers.put(ESBRouterTypeUtils.TENANT_ID.getName(), tenantID);
      }
      return headers;
   }

   /**
    * @param httpStatus
    * @return
    */
   public static String getHttpStatusMessage(HttpStatus httpStatus) {
      //
      StringBuilder message = new StringBuilder();
      message.append("{Statuscode=[").append(String.valueOf(httpStatus.value())).append("], Message=[");
      message.append(httpStatus.getReasonPhrase()).append("]}");
      if (message.length() > 900) {
         return String.valueOf(message.substring(0, 900));
      }
      //
      return message.toString();
   }

   /**
    * @param errorMessage
    * @return
    */
   public static String getErrorMessage(String errorMessage) {
      //
      if (Objects.nonNull(errorMessage)) {
         if (errorMessage.length() > 1000) {
            return errorMessage.substring(0, 1000);
         }
         return errorMessage;
      }
      return null;
   }

   /**
    * @param objectName
    * @param resultPayload
    * @return
    */
   public static String getObjectValue(String objectName, Map<String, Object> resultPayload) {
      //
      if (Objects.nonNull(objectName)) {
         return (String) resultPayload.get(objectName);
      }
      return null;
   }
   
   /**
    * @param response
    * @return
    */
   public static BigInteger getMessageID(ResponseEntity<Object> response) {
      //
      if (response.getHeaders().containsKey(ESBRouterTypeUtils.MESSAGE_ID.getName())) {
         return new BigInteger(response.getHeaders().get(ESBRouterTypeUtils.MESSAGE_ID.getName()).get(0));
      }
      return null;
   }
   
}
