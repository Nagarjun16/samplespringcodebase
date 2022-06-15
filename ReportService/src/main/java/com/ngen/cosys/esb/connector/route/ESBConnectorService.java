/**
 * 
 * ESBConnectorService.java
 * 
 * Copyright <PRE><IMG SRC = XX></IMG></PRE>
 *
 * Version      Date         Author      Reason
 * 1.0          11 JUL, 2018   NIIT      -
 */
package com.ngen.cosys.esb.connector.route;

import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

/**
 * This interface is used for router between ESB Connector and Interfaces
 * 
 * @author NIIT Technologies Ltd
 *
 */
public interface ESBConnectorService {

   /**
    * @param payload
    * @param mediaType
    * @param payloadHeaders
    * @return
    */
   public ResponseEntity<Object> route(Object payload, MediaType mediaType, Map<String, String> payloadHeaders);
   
}
