package com.ngen.cosys.scheduler.esb.connector.jackson.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.ngen.cosys.platform.rfid.tracker.interfaces.model.ApiRequestModel;

public class JacksonUtility {
	
	private static final Logger LOG = LoggerFactory.getLogger(JacksonUtility.class);
	
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

   //  private static final Logger LOGGER = LoggerFactory.getLogger(JacksonXMLUtility.class);

   /**
    * @param payload
    * @return
    * @throws Exception
    */
   public static Object convertObjectToXMLString(Object payload) {
      //
      Object response = null;
      XmlMapper xmlMapper = new XmlMapper();
      //xmlMapper.configure(ToXmlGenerator.Feature.WRITE_XML_DECLARATION, true);
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

   public Map<String, String> convertJsonToMapObject(String json) {
      Map<String, String> map = new HashMap<>();
      ObjectMapper mapper = new ObjectMapper();
      try {
         //convert JSON string to Map
         map = mapper.readValue(json, new TypeReference<HashMap<String, String>>() {
         });
         System.out.println(map);
      } catch (Exception e) {
         e.printStackTrace();
      }
      return map;
   }

   
   public static String handleObjectToJSONString(ApiRequestModel apiRequest) throws IOException {
		String jsonOutput = null;
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			jsonOutput = objectMapper.writeValueAsString(apiRequest);
			LOG.debug("JSON response for outbound request", jsonOutput);
		} catch (JsonProcessingException jsonProcessingException) {
			LOG.error("JsonProcessingException while writing response object to json format", jsonProcessingException);
		}
		return jsonOutput;
	}
    
  
   
}
