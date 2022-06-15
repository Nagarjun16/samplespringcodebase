/**
 * 
 * ESBConnector.java
 * 
 * Copyright <PRE><IMG SRC = XX></IMG></PRE>
 *
 * Version      Date         Author      Reason
 * 1.0          11 JUL, 2018   NIIT      -
 */
package com.ngen.cosys.esb.connector.route;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.annotation.PostConstruct;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import com.ngen.cosys.multi.tenancy.constants.BeanFactoryConstants;
import com.ngen.cosys.multitenancy.support.CosysApplicationContext;
import com.ngen.cosys.service.util.constants.InterfaceSystem;
import com.ngen.cosys.service.util.constants.ServiceURLConstants;
import com.ngen.cosys.service.util.model.ServiceAPIURL;

/**
 * This class is used for router between ESB Connector and Interfaces
 * 
 * @author NIIT Technologies Ltd
 *
 */
@Component
public class ESBConnector implements ESBConnectorService {

   private static final Logger LOGGER = LoggerFactory.getLogger(ESBConnector.class);
   
   /**
    * ESB Host
    */
   @Value("${esb.connector.hostname-esb-print}")
   private String esbHost;
   
   /**
    * ESB Port
    */
   @Value("${esb.connector.portnumber-esb-print}")
   private String esbPort;
   
   /**
    * ESB Printer Path
    */
   @Value("${esb.connector.path-printer}")
   private String esbPathPrinter;
   
   @Autowired
   @Qualifier(BeanFactoryConstants.ROI_SESSION_TEMPLATE)
   private SqlSession sqlSession;
   
   private List<ServiceAPIURL> serviceAPIConfig = Collections.emptyList();
   
   /**
    * API ESB Configuration
    */
   @PostConstruct
   private void initAPIESBConfiguration() {
      LOGGER.warn("Connector Service :: initAPIESBConfiguration ");
      serviceAPIConfig = sqlSession.selectList(ServiceURLConstants.SQL_SERVICE_ESB_LIKE_URL,
            ServiceURLConstants.BASE_API_ESB_LIKE_PARAM);
      if (!CollectionUtils.isEmpty(serviceAPIConfig)) {
         LOGGER.warn("Connector Service URL Initialized :: config API Count : {}", serviceAPIConfig.size());
      } else {
         LOGGER.warn("API ESB configuration :: Initialization failed");
      }
   }
   
   /**
    * @param payload
    * @param connectorURL
    * @return
    */
   public ResponseEntity<Object> route(Object payload, MediaType mediaType, Map<String, String> payloadHeaders) {
      //
      ResponseEntity<Object> result = null;
      String connectorURL = getESBPrinterURI();
      if (Objects.nonNull(payload)) {
         RestTemplate restTemplate = CosysApplicationContext.getRestTemplate();
         result = restTemplate.exchange(connectorURL, HttpMethod.POST,
               getMessagePayload(payload, mediaType, payloadHeaders), Object.class);
      }
      return result;
   }
   
   /**
    * @param payload
    * @return
    */
   private HttpEntity<Object> getMessagePayload(Object payload, MediaType mediaType,
         Map<String, String> payloadHeaders) {
      //
      final HttpHeaders headers = new HttpHeaders();
      headers.setAccept(Arrays.asList(mediaType));
      headers.setContentType(mediaType);
      if (!CollectionUtils.isEmpty(payloadHeaders)) {
         for (Map.Entry<String, String> entry : payloadHeaders.entrySet()) {
            if (Objects.nonNull(entry.getKey())) {
               headers.set(entry.getKey(), entry.getValue());
            }
         }
      }
      //
      return new HttpEntity<>(payload, headers);
   }
   
   /**
    * @return
    */
   public String getESBPrinterURI() {
      String printerURL = getServiceURL(InterfaceSystem.PRINTER);
      if (StringUtils.isEmpty(printerURL)) {
         StringBuilder path = new StringBuilder();
         path.append("http://").append(esbHost).append(":");
         path.append(esbPort).append(esbPathPrinter);
         printerURL = path.toString();
      }
      return printerURL;
   }
   
   /**
    * @param systemName
    * @return
    */
   public String getServiceURL(String systemName) {
      //
      if (!CollectionUtils.isEmpty(serviceAPIConfig)) {
         for (ServiceAPIURL apiConfig : serviceAPIConfig) {
            // API Configuration
            String config = getAPIConfig(systemName);
            if (!StringUtils.isEmpty(config) && Objects.equals(config, apiConfig.getParam())) {
               return apiConfig.getValue();
            }
         }
      }
      return null;
   }

   /**
    * Get Service API Config
    *
    * @param systemName
    * @return
    */
   private String getAPIConfig(String systemName) {
      //
      String config = null;
      switch (systemName) {
      case InterfaceSystem.PRINTER:
         config = ServiceURLConstants.ESB_CONNECTOR_PRINTER;
         break;
      default:
         break;
      }
      return config;
   }
   
}
