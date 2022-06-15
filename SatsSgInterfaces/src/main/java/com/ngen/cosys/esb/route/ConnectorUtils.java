package com.ngen.cosys.esb.route;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.ngen.cosys.events.enums.ESBRouterTypeUtils;

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
      headers.put(ESBRouterTypeUtils.COSYS_ESB.getName(), "false");
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
      if (message.length() > 100) {
         return String.valueOf(message.substring(0, 100));
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
         if (errorMessage.length() > 100) {
            return errorMessage.substring(0, 100);
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
   
   /**
    * @param response
    * @return
    */
   public static BigInteger getESBMessageID(HttpServletRequest request) {
      //
      if (Objects.nonNull(request.getHeader(ESBRouterTypeUtils.ESB_MESSAGE_ID.getName()))) {
         return new BigInteger(request.getHeader(ESBRouterTypeUtils.ESB_MESSAGE_ID.getName()));
      }
      return null;
   }
   
   /**
    * @param response
    * @return
    */
   public static BigInteger getErrorMessageID(ResponseEntity<Object> response) {
      //
      if (response.getHeaders().containsKey(ESBRouterTypeUtils.ERROR_MESSAGE_ID.getName())
            && Objects.nonNull(response.getHeaders().get(ESBRouterTypeUtils.ERROR_MESSAGE_ID.getName()))) {
         return new BigInteger(response.getHeaders().get(ESBRouterTypeUtils.ERROR_MESSAGE_ID.getName()).get(0));
      }
      return null;
   }

}
