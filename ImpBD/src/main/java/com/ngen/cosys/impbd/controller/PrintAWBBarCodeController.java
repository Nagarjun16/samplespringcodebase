/**
 * PrintAWBBarCodeController.java
 * 
 * Copyright
 * 
 * <PRE>
 * <IMG SRC = XX></IMG>
 * </PRE>
 *
 * Version Date Author Reason 1.0 23 December, 2017 NIIT -
 */
package com.ngen.cosys.impbd.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ngen.cosys.annotation.NgenCosysAppInfraAnnotation;
import com.ngen.cosys.annotation.PostRequest;
import com.ngen.cosys.framework.config.UtilitiesModelConfiguration;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.framework.model.BaseResponse;
import com.ngen.cosys.impbd.model.AWBPrintRequest;
import com.ngen.cosys.impbd.printer.util.PrinterService;
import com.ngen.cosys.impbd.service.PrintAWBBarCodeService;
import com.ngen.cosys.service.util.enums.PrinterType;
import com.ngen.cosys.service.util.enums.ReportRequestType;
import com.ngen.cosys.service.util.model.ReportRequest;

import io.swagger.annotations.ApiOperation;

/**
 * This class is controller for AWB barcode printing ,which will take care of
 * Searching for AWB number against the manifest,printing the AWB barcode
 * 
 * @author NIIT Technologies Ltd
 *
 */
@NgenCosysAppInfraAnnotation
public class PrintAWBBarCodeController {

   @Autowired
   private BeanFactory beanFactory;

   @Autowired
   UtilitiesModelConfiguration utility;

   @Autowired
   PrintAWBBarCodeService printAWBBarCodeService;

   @Autowired
   private PrinterService printerService;

   /**
    * REST API to validate AWB Number
    * 
    * @param awbDetail
    * @return
    * @throws CustomException
    */
   @ApiOperation("Validating AWB number")
   @PostRequest(value = "/api/shpmng/printAWBbarcode/validate", method = RequestMethod.POST)
   public BaseResponse<AWBPrintRequest> validateAWBNumber(@RequestBody @Valid AWBPrintRequest awbDetail)
         throws CustomException {
      @SuppressWarnings("unchecked")
      BaseResponse<AWBPrintRequest> validationRes = utility.getBaseResponseInstance();
      validationRes.setData(printAWBBarCodeService.validateAWBNumber(awbDetail));
      return validationRes;
   }

   /**
    * REST API to print AWB barcode
    * 
    * @param shpInfo
    * @return
    * @throws CustomException
    */
   @ApiOperation("Prints AWB barcode")
   @PostRequest(value = "/api/shpmng/printAWBbarcode/print", method = RequestMethod.POST)
   public BaseResponse<AWBPrintRequest> printBarcode(@RequestBody @Valid AWBPrintRequest shpInfo)
         throws CustomException {
      // BaseResponse<AWBPrintRequest> printRes = utility.getBaseResponseInstance();
      @SuppressWarnings("unchecked")
      BaseResponse<AWBPrintRequest> printRes = (BaseResponse<AWBPrintRequest>) this.beanFactory
            .getBean(BaseResponse.class);
      printRes.setData(printAWBBarCodeService.printBarcode(shpInfo));
      printAwbBarcode(shpInfo);
      return printRes;
   }

   /**
    * REST API to print Multi AWB barcode
    * 
    * @param shpInfo
    * @return
    * @throws CustomException
    */
   @ApiOperation("Prints Multi AWB barcode")
   @PostRequest(value = "/api/shpmng/printMultiAWBbarcode/print", method = RequestMethod.POST)
   public BaseResponse<AWBPrintRequest> printMultiBarcode(@RequestBody List<AWBPrintRequest> awbNosList)
         throws CustomException {
      @SuppressWarnings("unchecked")
      BaseResponse<AWBPrintRequest> printRes = (BaseResponse<AWBPrintRequest>) this.beanFactory
            .getBean(BaseResponse.class);
      printAWBBarCodeService.printMultiBarcode(awbNosList);
      printRes.setSuccess(true);
      return printRes;
   }

   private void printAwbBarcode(AWBPrintRequest request) {
      ReportRequest report = new ReportRequest();
      report.setRequestType(ReportRequestType.PRINT);
      report.setPrinterType(PrinterType.AWB_BARCODE);
      report.setQueueName(request.getPrinterName());
      Map<String, Object> parameters = new HashMap<>();
      parameters.put("awbNumBarCode", request.getAwbNumber());
      parameters.put("awbNumTextCode", request.getAwbNumber());
      report.setParameters(parameters);
      if (null != report.getQueueName()) {
         printerService.printReport(report);
      }
   }

}