package com.ngen.cosys.shipment.printer.util;

import javax.annotation.PostConstruct;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.ngen.cosys.service.util.api.ServiceUtil;
import com.ngen.cosys.service.util.constants.ServiceURLConstants;
import com.ngen.cosys.service.util.model.ReportRequest;


@Component
public class PrinterService {

   private static final Logger LOGGER = LoggerFactory.getLogger(PrinterService.class);
   
   @Qualifier("sqlSessionTemplate")
   @Autowired
   private SqlSession sqlSession;

   private String printerURL = null;
   private String reportURL = null;

   @PostConstruct
   public void initPrinterServiceURL() {
      LOGGER.warn("Printer Service & Report Service API URL :: init called ");
      printerURL = sqlSession.selectOne(ServiceURLConstants.SQL_SERVICE_API_URL, ServiceURLConstants.PRINTER_SERVICE);
      LOGGER.warn("Initialized Printer Service API URL :: {}", printerURL);
      reportURL = sqlSession.selectOne(ServiceURLConstants.SQL_SERVICE_API_URL, ServiceURLConstants.REPORT_SERVICE);
      LOGGER.warn("Initialized Report Service API URL :: {}", reportURL);
   }
	   
	/**
	 * @param report
	 */
	public void printReport(ReportRequest report) {
		String printerPath = getPrinterServiceURL();
		ResponseEntity<Object> response = ServiceUtil.route(report, printerPath);
		//
		if (HttpStatus.OK.equals(response.getStatusCode())) {
			//
		}
	}
	
	   public void printULDTag(ReportRequest report) {
		      String printerPath = getPrinterServiceURL();
		      ResponseEntity<Object> response = ServiceUtil.route(report, printerPath);
		      //
	      if (HttpStatus.OK.equals(response.getStatusCode())) {
	         //
	      }
	   }
	
	/**
	 * @return
	 */
	private String getPrinterServiceURL() {
	   return printerURL;
	}
	
	/**
	 * @return
	 */
	public String getReportServiceURL() {
	   LOGGER.warn("Report Service API URL :: {}", reportURL);
	   return reportURL;
	}
	
}
