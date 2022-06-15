/**
 * 
 * ConnectorService.java
 * 
 * Copyright <PRE><IMG SRC = XX></IMG></PRE>
 *
 * Version      Date         Author      Reason
 * 1.0          22 MAY, 2017   NIIT      -
 */
package com.ngen.cosys.esb.route;

import java.math.BigInteger;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;

import javax.annotation.PostConstruct;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.ngen.cosys.events.enums.ESBRouterTypeUtils;
import com.ngen.cosys.events.esb.connector.payload.MessagePayload;
import com.ngen.cosys.events.esb.connector.router.ESBConnector;
import com.ngen.cosys.service.util.constants.InterfaceSystem;
import com.ngen.cosys.service.util.constants.ServiceURLConstants;
import com.ngen.cosys.service.util.model.ServiceAPIURL;

/**
 * This Connector class used for Send Payload data to ESB Connector
 * 
 * @author NIIT Technologies Ltd
 *
 */
@Component
public class ConnectorService implements ConnectorPublisher {

   private static final Logger LOGGER = LoggerFactory.getLogger(ConnectorService.class);

   @Value("${esb.connector.hostname}")
   private String esbHost;

   @Value("${esb.connector.portnumber}")
   private String esbPort;

   @Value("${esb.connector.path-jms}")
   private String esbPathJMS;

   @Value("${esb.connector.path-rest}")
   private String esbPathREST;

   /**
    * ESB Connector FAX Path
    */
   @Value("${esb.connector.path-fax}")
   private String esbPathFAX;
   
   /**
    * ESB Connector SMS Path
    */
   @Value("${esb.connector.path-sms}")
   private String esbPathSMS;
   
   /**
    * ESB Connector Weighing Scale Path
    */
   @Value("${esb.connector.path-wscale}")
   private String esbPathWScale;
   
   /**
    * ESB Connector IVRS Path
    */
   @Value("${esb.connector.path-ivrs}")
   private String esbPathIVRS;
   
   @Autowired
   ESBConnector router;

   @Qualifier("sqlSessionTemplate")
   @Autowired
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
    * @see com.ngen.cosys.scheduler.esb.connector.ConnectorPublisher#sendJobDataToConnector(java.lang.Object)
    * 
    */
   @Override
   public ResponseEntity<Object> sendJobDataToConnector(Object payload, String qname, MediaType mediaType,
         Map<String, String> payloadHeaders) {
      //
      String connectorURL = this.getConnectorURL(null);
      LOGGER.debug("connectorURL in SG Interface {} ", connectorURL);
      //
      Object messagePayload = this.constructMessagePayload(payload, qname, payloadHeaders);
      ResponseEntity<Object> response = router.route(messagePayload, connectorURL, mediaType, payloadHeaders);
      return response;
   }

   /**
    * @param payload
    * @param qname
    * @return
    */
   private Object constructMessagePayload(Object payload, String qname, Map<String, String> payloadHeaders) {
      //
      MessagePayload messagePayload = new MessagePayload();
      messagePayload.setQname(qname);
      messagePayload.setPayload(payload);
      //
      if (!CollectionUtils.isEmpty(payloadHeaders)) {
         for (Map.Entry<String, String> entry : payloadHeaders.entrySet()) {
            if (Objects.nonNull(entry.getKey())) {
               switch (entry.getKey()) {
               case (ESBRouterTypeUtils.Type.MESSAGE_ID):
                  messagePayload.setMessageId(new BigInteger(entry.getValue()));
                  break;
               case (ESBRouterTypeUtils.Type.ERROR_MESSAGE_ID):
                  break;
               case (ESBRouterTypeUtils.Type.LOGGER_ENABLED):
                  if (Boolean.valueOf(entry.getKey())) {
                     messagePayload.setLoggerEnabled(true);
                  }
                  break;
               case (ESBRouterTypeUtils.Type.INTERFACE_SYSTEM):
                  break;
               case (ESBRouterTypeUtils.Type.SYSTEM_NAME):
                  break;
               case (ESBRouterTypeUtils.Type.TENANT_ID):
                  messagePayload.setTenantID(entry.getValue());
                  break;
               default:
                  break;
               }
            }
         }
      }
      return messagePayload;
   }

   /**
    * @see com.ngen.cosys.esb.route.ConnectorPublisher#sendPayloadDataToConnector(java.lang.Object)
    * 
    */
   @Override
   public ResponseEntity<Object> sendPayloadDataToConnector(Object payload, String endPointURL, MediaType mediaType,
         Map<String, String> payloadHeaders) {
      //
      LOGGER.debug("SG Interface :: REST EndPointURL {} ", endPointURL);
      String connectorURL = this.getConnectorURL(endPointURL);
      LOGGER.debug("connectorURL in SG Interface {} ", connectorURL);
      //
      return router.route(payload, connectorURL, mediaType, payloadHeaders);
   }

   /**
    * FAX, SMS Interface payload connector service
    * 
    * @see com.ngen.cosys.esb.route.ConnectorPublisher#sendInterfacePayloadToConnector(java.lang.Object,
    *      java.lang.String, org.springframework.http.MediaType, java.util.Map)
    * 
    */
   @Override
   public ResponseEntity<Object> sendInterfacePayloadToConnector(Object payload, String systemName, MediaType mediaType,
         Map<String, String> payloadHeaders) {
      //
      LOGGER.debug("SG Interface System Name :: {} ", systemName);
      String connectorURL = getESBConnectorURI(systemName);
      LOGGER.debug("connectorURL in SG Interface {} ", connectorURL);
      //
      return router.route(payload, connectorURL, mediaType, payloadHeaders);
   }

   /**
    * @param endPointURL
    * @return
    */
   private String getConnectorURL(String endPointURL) {
      //
      String connectorURL = null;
      if (!StringUtils.isEmpty(endPointURL)) {
         connectorURL = getServiceURL(InterfaceSystem.REST);
          if (!StringUtils.isEmpty(connectorURL)) {
            return connectorURL.replaceAll("\\" + ServiceURLConstants.RELATIVE_PATH_PATTERN, endPointURL);
         }
      } else {
         connectorURL = getServiceURL(InterfaceSystem.MQ);
      }
      if (StringUtils.isEmpty(connectorURL)) {
         StringBuilder path = new StringBuilder();
         path.append("http://").append(esbHost).append(":").append(esbPort);
         path.append(esbPathJMS);
         connectorURL = path.toString();
      }
      return connectorURL;
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
      case InterfaceSystem.MQ:
         config = ServiceURLConstants.ESB_CONNECTOR_MQ;
         break;
      case InterfaceSystem.REST:
         config = ServiceURLConstants.ESB_CONNECTOR_REST;
         break;
      case InterfaceSystem.FAX:
         config = ServiceURLConstants.ESB_CONNECTOR_FAX;
         break;
      case InterfaceSystem.SMS:
         config = ServiceURLConstants.ESB_CONNECTOR_SMS;
         break;
      case InterfaceSystem.WSCALE:
         config = ServiceURLConstants.ESB_CONNECTOR_WSCALE;
         break;
      case InterfaceSystem.IVRS:
         config = ServiceURLConstants.ESB_CONNECTOR_SMARTGATE;
         break;
      case InterfaceSystem.REPORT:
         config = ServiceURLConstants.REPORT_SERVICE;
         break;
      case InterfaceSystem.PRINTER:
         config = ServiceURLConstants.PRINTER_SERVICE;
         break;
      case InterfaceSystem.ALTEA_FM:
         config = ServiceURLConstants.API_ALTEA_FM_WEB_SERVICE;
         break;
      default:
         break;
      }
      return config;
   }

   /**
    * @param systemName
    * @return
    */
   public String getESBConnectorURI(String systemName) {
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
    * @param result
    * @return
    */
   public boolean checkResponseStatus(Map<String, String> result) {
      String status = null;
      for (Entry<String, String> entry : result.entrySet()) {
         if (entry.getKey().equalsIgnoreCase("status")) {
            status = String.valueOf(entry.getValue());
         }
      }
      if ("success".equalsIgnoreCase(status)) {
         return true;
      } else {
         return false;
      }
   }

   @Override
   public ResponseEntity<String> sendPayloadDataToESBConnector(String payload, String endPointURL, MediaType mediaType,
         Map<String, String> payloadHeaders) {
      //
      LOGGER.debug("SG Interface :: REST EndPointURL {} ", endPointURL);
      String connectorURL = this.getConnectorURL(endPointURL);
      LOGGER.debug("connectorURL in SG Interface {} ", connectorURL);
      //
      return router.routeString(payload, connectorURL, mediaType, payloadHeaders);
   }

}
