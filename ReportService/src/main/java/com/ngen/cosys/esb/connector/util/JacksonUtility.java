/**
 * 
 * JacksonXMLUtility.java
 * 
 * Copyright <PRE><IMG SRC = XX></IMG></PRE>
 *
 * Version      Date         Author      Reason
 * 1.0          22 MAY, 2018   NIIT      -
 */
package com.ngen.cosys.esb.connector.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

/**
 * This class is used for ESB Custom Jackson XML Utility for Marshal and Unmarshal
 * 
 * @author NIIT Technologies Ltd
 *
 */
public class JacksonUtility {

   private static final Logger LOGGER = LoggerFactory.getLogger(JacksonUtility.class);
   
   private JacksonUtility() {}
   
   /**
    * @param payload
    * @return
    * @throws Exception
    */
   public static Object convertObjectToXMLString(Object payload) {
      //
      Object response = null;
      XmlMapper xmlMapper = new XmlMapper();
      try {
         response = xmlMapper.writeValueAsString(payload);
      } catch (Exception ex) {
         // Exception 
         LOGGER.debug("Jackson XML Parsing error : convertObjectToXMLString");
      }
      return response;
   }
   
   /**
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
         // Exception 
         LOGGER.debug("Jackson XML Parsing error : convertXMLStringToObject");
      }
      return object;
   }
   
   /**
    * @param payload
    * @return
    */
   public static Object convertObjectToJSONString(Object payload) {
      //
      Object result = null;
      ObjectMapper objectMapper = new ObjectMapper();
      try {
         result = objectMapper.writeValueAsString(payload);
      } catch (Exception ex) {
         // Exception 
         LOGGER.debug("Jackson JSON Parsing error : convertObjectToJSONString");
      }
      return result;
   }
   
   /**
    * @param payload
    * @return
    */
   public static Object convertJSONStringToObject(Object payload, Class<?> clazz) {
      //
      Object result = null;
      ObjectMapper objectMapper = new ObjectMapper();
      try {
         result = objectMapper.readValue(payload.toString(), clazz);
      } catch (Exception ex) {
         // Exception 
         LOGGER.debug("Jackson JSON Parsing error : convertJSONStringToObject");
      }
      return result;
   }
  
}
