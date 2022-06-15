package com.ngen.cosys.scheduler.esb.connector.jackson.util;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

public class JacksonUtility {

   private static final Logger LOGGER = LoggerFactory.getLogger(JacksonUtility.class);
   
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
         ex.printStackTrace();
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
         ex.printStackTrace();
      }
      return result;
   }

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
         // LOGGER.debug("Jackson XML Parsing error ");
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
      } catch (Exception e) {
         // Exception 
         // LOGGER.debug("Jackson XML Parsing error ");
      }
      return object;
   }
   
   public static Map<String, String> convertJsonStringToMapObject(String json) {
      Map<String, String> map = new HashMap<>();
      ObjectMapper mapper = new ObjectMapper();
      try {
         //convert JSON string to Map
         map = mapper.readValue(json, new TypeReference<HashMap<String, String>>() {});
      } catch (Exception e) {
         e.printStackTrace();
      }
      return map;
   }

}
