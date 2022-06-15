package com.ngen.cosys.satssg.interfaces.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.ngen.cosys.esb.route.ConnectorService;
import com.ngen.cosys.service.util.api.ServiceUtil;
import com.ngen.cosys.service.util.constants.InterfaceSystem;
import com.ngen.cosys.service.util.model.ReportRequest;

@Component
public class PrinterService {

   private static final Logger LOGGER = LoggerFactory.getLogger(PrinterService.class);
   
   @Autowired
   ConnectorService connectorService;
   
   public void printULDTag(ReportRequest report) {
      String printerPath = getPrinterServiceURL();
      ResponseEntity<Object> response = ServiceUtil.route(report, printerPath);
      //
      if (HttpStatus.OK.equals(response.getStatusCode())) {
         //
      }
   }

   private String getPrinterServiceURL() {
      String printerURL = connectorService.getServiceURL(InterfaceSystem.PRINTER);
      LOGGER.warn("Printer Service - printerURL :: {}", printerURL);
      return printerURL;
   }
   
}