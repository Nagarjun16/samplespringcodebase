/**
 * 
 * JacksonUtility.java
 * 
 * Copyright <PRE><IMG SRC = XX></IMG></PRE>
 *
 * Version      Date         Author      Reason
 * 1.0          22 MAY, 2017   NIIT      -
 */
package com.ngen.cosys.esb.jackson.util;

import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.ser.ToXmlGenerator;

/**
 * Jackson Utility class converts Payload from Object to String in XML/JSON format
 * 
 * 
 * @author NIIT Technologies Ltd
 *
 */
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
         LOGGER.debug("Jackson JSON Parsing error ");
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
         LOGGER.debug("Jackson JSON Parsing error ");
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
      xmlMapper.configure(ToXmlGenerator.Feature.WRITE_XML_DECLARATION, true);
      try {
         response = xmlMapper.writeValueAsString(payload);
      } catch (Exception ex) {
         // Exception
         LOGGER.debug("Jackson XML Parsing error ");
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
      //
      XmlMapper xmlMapper = new XmlMapper();
      Object object = null;
      try {
         object = xmlMapper.readValue(payload.toString(), clazz);
      } catch (Exception ex) {
         // Exception
         LOGGER.debug("Jackson XML Parsing error ");
      }
      return object;
   }

	
   /**
    * @param payload
    * @return
    */
   public static Collection<?> convertJSONStringToList(Object payload) {
      //
      ObjectMapper mapper = new ObjectMapper();
      TypeReference<List<?>> referenceType = new TypeReference<List<?>>() {};
      List<?> collectionList = null;
      try {
         collectionList = mapper.readValue(payload.toString(), referenceType);
      } catch (Exception ex) {
         // Exception
         LOGGER.debug("Jackson List Object Parsing error ");
      }
	  return collectionList;
	}
	
}
