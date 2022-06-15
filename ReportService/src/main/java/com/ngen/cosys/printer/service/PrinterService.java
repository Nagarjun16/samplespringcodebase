/**
 * Printer Service
 * 
 * @copyright SATS Singapore 2018-19
 */
package com.ngen.cosys.printer.service;

import com.ngen.cosys.esb.connector.payload.PrinterPayload;
import com.ngen.cosys.report.model.ReportRequest;

/**
 * Printer Service
 */
public interface PrinterService {

   final String TEXT_FORMAT = "txt";
   
   /**
    * Create Printer Specific Report
    * 
    * @param reportRequest
    *            Report Request
    * @return PrinterPayload
    * @throws Exception
    */
   PrinterPayload generatePrinterReport(ReportRequest reportRequest) throws Exception;
   
}
