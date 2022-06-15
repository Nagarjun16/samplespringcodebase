/**
 * {@link IVRSUtils}
 * 
 * @copyright SATS Singapore 2019-20
 * @author NIIT
 */
package com.ngen.cosys.service.ivrs.utils;

import java.io.IOException;
import java.security.SecureRandom;
import java.util.Collections;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * IVRS Utils
 * 
 * @author NIIT Technologies Ltd
 */
public class IVRSUtils {

   private static final long MIN_VALUE = 1_000_000_000L;
   private static final long MAX_VALUE = 9_999_999_999L;
   private static final long RND_CYCLE = 100L;
   private static final String BASE_RESPONSE = "data";
   private static final String REPORT_RESPONSE = "reportData";
   
   /**
    * @return
    */
   public static String generateSequenceNumber() {
      return String.valueOf(secureRandomInteger());
   }
   
   /**
    * Secure Random Integer
    * 
    * @return
    */
   private static long secureRandomInteger() {
      SecureRandom cipher = new SecureRandom();
      long range = MAX_VALUE - MIN_VALUE;
      double nextDigits = 0D;
      for (int j = 0; j < RND_CYCLE; j++) {
         nextDigits = cipher.nextDouble();
      }
      return (long) (range * nextDigits) + MIN_VALUE;
   }
   
   /**
    * @param payload
    * @return
    */
   public static String convertObjectToJSONString(Object payload) {
      String result = null;
      ObjectMapper objectMapper = new ObjectMapper();
      try {
         result = objectMapper.writeValueAsString(payload);
      } catch (Exception ex) {
      }
      return result;
   }

   /**
    * @param payload
    * @param clazz
    * @return
    */
   public static Object convertJSONStringToObject(Object payload, Class<?> clazz) {
      Object result = null;
      ObjectMapper objectMapper = new ObjectMapper();
      try {
         result = objectMapper.readValue(payload.toString(), clazz);
      } catch (Exception ex) {
      }
      return result;
   }
   
   /**
    * @param responseBody
    * @param clazz
    * @return
    */
   public static Object convertToIVRSResponse(String responseBody, Class<?> clazz) {
      ObjectMapper mapper = new ObjectMapper();
      Map<String, Object> jsonValues = readResponseBody(responseBody);
      return !CollectionUtils.isEmpty(jsonValues) ? mapper.convertValue(jsonValues, clazz) : null;
   }
   
   /**
    * @param responseBody
    * @return
    */
   @SuppressWarnings("unchecked")
   public static String readBase64Content(Object responseBody) {
      String json = convertObjectToJSONString(responseBody);
      Map<String, Object> jsonValues = readResponseBody(json);
      if (!CollectionUtils.isEmpty(jsonValues)) {
         Map<String, Object> dataValues = (Map<String, Object>) jsonValues.get(BASE_RESPONSE);
         if (!CollectionUtils.isEmpty(dataValues)) {
            return String.valueOf(dataValues.get(REPORT_RESPONSE));
         }
      }
      return null;
   }
   
   /**
    * @param responseBody
    * @return
    */
   @SuppressWarnings("unchecked")
   private static Map<String, Object> readResponseBody(String responseBody) {
      ObjectMapper mapper = new ObjectMapper();
      Map<String, Object> jsonValues = Collections.emptyMap();
      try {
         jsonValues = mapper.readValue(responseBody, Map.class);
      } catch (IOException ex) {
      }
      return jsonValues;
   }
   
   /**
    * @param error
    * @param length
    * @return
    */
   public static String getFailureErrorMessage(String error, int length) {
      return !StringUtils.isEmpty(error) ? (error.length() > length ? error.substring(0, length - 1) : error) : null;
   }
   
   /**
    * @param httpStatus
    * @return
    */
   public static String getHttpStatusMessage(HttpStatus httpStatus) {
      StringBuilder message = new StringBuilder();
      message.append("{Statuscode=[").append(String.valueOf(httpStatus.value())).append("], Message=[");
      message.append(httpStatus.getReasonPhrase()).append("]}");
      if (message.length() > 100) {
         return String.valueOf(message.substring(0, 100));
      }
      return message.toString();
   }
   
}
