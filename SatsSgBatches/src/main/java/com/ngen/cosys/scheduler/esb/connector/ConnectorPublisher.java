package com.ngen.cosys.scheduler.esb.connector;

import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

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
   
}
