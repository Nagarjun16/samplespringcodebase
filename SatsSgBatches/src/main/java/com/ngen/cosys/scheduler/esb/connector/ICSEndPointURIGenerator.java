package com.ngen.cosys.scheduler.esb.connector;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ICSEndPointURIGenerator {
   @Value("${esb.connector.hostname}")
   private String icsHost;

   @Value("${esb.connector.portnumber}")
   private String icsPort;

   @Value("${ics-url-relative.fetch-uld-list}")
   private String fetchUldListURL;

   @Value("${ics-url-relative.fetch-ics-location}")
   private String fetchIcsLocationURL;

   @Value("${ics-url-relative.retrieve-uld-at-requested-lane}")
   private String retrieveUldAtRequestedLaneURL;

   @Value("${ics-url-relative.operative-flight-details}")
   private String operativeFlightDetailsURL;

   @Autowired
   ConnectorService connectorService;
   
   public String getICSConnectorURL(String endPointURL) {

      StringBuilder path = new StringBuilder();
      switch (endPointURL) {
      case "fetch-uld-list":
         path = new StringBuilder(generateICSURL(fetchUldListURL));
         break;
      case "fetch-ics-location":
         path = new StringBuilder(generateICSURL(fetchIcsLocationURL));
         break;
      case "retrieve-uld-at-requested-lane":
         path = new StringBuilder(generateICSURL(retrieveUldAtRequestedLaneURL));
         break;
      case "operative-flight-details":
         path = new StringBuilder(generateICSURL(operativeFlightDetailsURL));
         break;
      default:
         break;
      }
      return path.toString();
   }

   private StringBuilder generateICSURL(String endPointURL) {
      String connectorURL = connectorService.getConnectorURL(endPointURL);
      return new StringBuilder(connectorURL);
   }
}
