package com.ngen.cosys.billing.sap.airline.Controller;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ngen.cosys.annotation.NgenCosysAppInfraAnnotation;
import com.ngen.cosys.annotation.PostRequest;
import com.ngen.cosys.billing.sap.airline.service.SapIncommingPaymentReceiptService;
import com.ngen.cosys.billing.sap.airline.service.SapInvoiceCreditDebitNoteService;
import com.ngen.cosys.billing.sap.airline.service.SapInvoiceSalesEntryService;
import com.ngen.cosys.framework.config.UtilitiesModelConfiguration;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.framework.model.BaseResponse;

import io.swagger.annotations.ApiOperation;
@NgenCosysAppInfraAnnotation
public class SapAirlineController {

	   @Autowired
	   private UtilitiesModelConfiguration utilitiesModelConfiguration;
	   @Autowired
	   private SapInvoiceSalesEntryService sapInvoiceSalesEntryService;
	   @Autowired
	   private SapIncommingPaymentReceiptService sapIncommingPaymentReceiptService;
	   @Autowired
	   private SapInvoiceCreditDebitNoteService sapInvoiceCreditDebitNoteService;

	   private static Logger logger = LoggerFactory.getLogger(SapAirlineController.class);

	   @SuppressWarnings({ "unchecked", "rawtypes" })
	   @ApiOperation("SAP Airline from Cosys to SAP")
	   @PostRequest(value = "api/billing/sapAirline", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	   public BaseResponse sapAirlineBilling() {
	      BaseResponse response = utilitiesModelConfiguration.getBaseResponseInstance();
	      logger.info("---SAP Airline Triggered---" + LocalDateTime.now());

	      try {
	    	  sapInvoiceSalesEntryService.createAndSendMessageToSap();
	    	  sapInvoiceCreditDebitNoteService.createAndSendCreditNoteMessageToSap();
	    	  sapInvoiceCreditDebitNoteService.createAndSendDebitNoteMessageToSap();
	    	  sapIncommingPaymentReceiptService.createAndSendMessageToSap();
	         logger.info("---SAP Airline Completed---" + LocalDateTime.now());
	      } catch (CustomException err) {
	         logger.error("SAP Airline Error : " + err);
	      }
	      response.setData(null);

	      return response;
	   }
}
