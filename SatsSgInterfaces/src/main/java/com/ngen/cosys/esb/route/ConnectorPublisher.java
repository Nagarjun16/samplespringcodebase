/**
 * 
 * ConnectorPublisher.java
 * 
 * Copyright <PRE><IMG SRC = XX></IMG></PRE>
 *
 * Version      Date         Author      Reason
 * 1.0          22 MAY, 2017   NIIT      -
 */
package com.ngen.cosys.esb.route;

import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

/**
 * This interface is routing from Interface to ESB Connector
 * 
 * @author NIIT Technologies Ltd
 *
 */
public interface ConnectorPublisher {

   /**
    * @param data
    * @return
    */
   public ResponseEntity<Object> sendJobDataToConnector(Object payload, String qname, MediaType mediaType, Map<String, String> payloadHeaders);
   
   /**
    * @param payload
    * @return
    */
   public ResponseEntity<Object> sendPayloadDataToConnector(Object payload, String endPointURL, MediaType mediaType, Map<String, String> payloadHeaders);
   
   /**
    * @param payload
    * @param endPointURL
    * @param mediaType
    * @param payloadHeaders
    * @return
    */
   public ResponseEntity<Object> sendInterfacePayloadToConnector(Object payload, String systemName,
         MediaType mediaType, Map<String, String> payloadHeaders);
   
   /**
    * 
    * @param payload
    * @param endPointURL
    * @param mediaType
    * @param payloadHeaders
    * @return
    */
   public ResponseEntity<String> sendPayloadDataToESBConnector(String payload, String endPointURL, MediaType mediaType, Map<String, String> payloadHeaders);
   
}
