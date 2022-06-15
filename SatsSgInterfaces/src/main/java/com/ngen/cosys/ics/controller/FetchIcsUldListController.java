package com.ngen.cosys.ics.controller;

import java.math.BigInteger;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.ngen.cosys.annotation.NgenCosysAppInfraAnnotation;
import com.ngen.cosys.esb.jackson.util.JacksonUtility;
import com.ngen.cosys.esb.route.ConnectorService;
import com.ngen.cosys.esb.route.ConnectorUtils;
import com.ngen.cosys.esb.route.ICSEndPointURIGenerator;
import com.ngen.cosys.events.consumer.logger.service.ApplicationLoggerService;
import com.ngen.cosys.ics.model.FetchICSULDListModel;
import com.ngen.cosys.ics.model.FetchICSULDListResponse;
import com.ngen.cosys.ics.model.FetchUldListModel;
import com.ngen.cosys.multitenancy.context.TenantContext;
import com.ngen.cosys.multitenancy.utilities.MultiTenantUtility;

@NgenCosysAppInfraAnnotation(path = "/api/ics")
public class FetchIcsUldListController {

   @Autowired
   private ConnectorService router;

   @Autowired
   private ApplicationLoggerService loggerService;

   @Autowired
   private ICSEndPointURIGenerator urlGenerator;

   @PostMapping(value = "/fetch-uld-list", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
   public ResponseEntity<Object> getULDList(@RequestBody FetchUldListModel fetchUldListModel) {

      final String endPointURL = urlGenerator.getICSConnectorURL("fetch-uld-list");
      String icsuldInventoryCheckQueryModelXmlString = (String) JacksonUtility.convertObjectToXMLString(fetchUldListModel);
      boolean loggerEnabled = true;
      BigInteger messageId = null;

      Map<String, String> payloadHeaders = ConnectorUtils.getPayloadHeaders(messageId, loggerEnabled, "ICS", TenantContext.getTenantId());
      ResponseEntity<String> response = router.sendPayloadDataToESBConnector(icsuldInventoryCheckQueryModelXmlString, endPointURL, MediaType.APPLICATION_XML, payloadHeaders);
      

      FetchICSULDListModel successResponse = (FetchICSULDListModel) JacksonUtility.convertXMLStringToObject(response.getBody(), FetchICSULDListModel.class);
      if (successResponse != null) {
         return new ResponseEntity<>(successResponse, HttpStatus.OK);
      } else {
         FetchICSULDListResponse failResponse = new FetchICSULDListResponse();
         failResponse.setStatus("Fail");
         failResponse.setErrorNumber("-5003");
         failResponse.setErrorDescription("No ULD record found");
         return new ResponseEntity<>(failResponse, HttpStatus.OK);
      }
   }
}
