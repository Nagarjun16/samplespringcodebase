/**
 * JacksonUtil.java
 * 
 * Copyright <PRE><IMG SRC = XX></IMG></PRE>
 *
 * Version      Date         Author      Reason
 * 1.0          22 JAN, 2019   NIIT      -
 */
package com.ngen.cosys.altea.fm.util;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule;

/**
 * This class is used for Jackson XML properties Serialization and
 * DeSerializtion
 * 
 * @author NIIT Technologies Ltd
 *
 */
public class JacksonUtil {

   private static final Logger LOGGER = LoggerFactory.getLogger(JacksonUtil.class);
   
   /**
    * Convert Payload Object to XML String
    * 
    * @param payload
    * @return
    */
   public static String convertObjectToXMLString(Object payload) {
      // Jackson XML Module
      JacksonXmlModule jacksonModule = new JacksonXmlModule();
      jacksonModule.setDefaultUseWrapper(false);
      // 
      ObjectMapper objectMapper = new XmlMapper(jacksonModule);
      objectMapper.registerModule(new JaxbAnnotationModule());
      objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
      try {
         return objectMapper.writeValueAsString(payload);
      } catch (JsonProcessingException ex) {
         LOGGER.debug("JsonProcessing Exception :: {}", ex);
      }
      return null;
   }
   
   /**
    * @param payload
    * @param clazz
    * @return
    */
   public static Object convertXMLStringToObject(String payload, Class<?> clazz) {
      JacksonXmlModule jacksonModule = new JacksonXmlModule();
      jacksonModule.setDefaultUseWrapper(false);
      //
      ObjectMapper objectMapper = new XmlMapper(jacksonModule);
      try {
         return objectMapper.readValue(payload, clazz);
      } catch (IOException ex) {
         LOGGER.debug("JsonProcessing Exception :: {}", ex);
      }
      //
      return null;
   }
   
}
