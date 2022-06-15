/**
 * {@link ExcelReportController}
 * 
 * @copyright SATS Singapore 2019-20
 * @author NIIT
 */
package com.ngen.cosys.report.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ngen.cosys.annotation.NgenCosysAppInfraAnnotation;
import com.ngen.cosys.annotation.PostRequest;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.framework.model.BaseResponse;
import com.ngen.cosys.report.model.ExcelReportRequest;
import com.ngen.cosys.report.model.ExcelReportResponse;
import com.ngen.cosys.report.service.ExcelReportService;

import io.swagger.annotations.ApiOperation;

/**
 * Excel Report controller used for Manual excel based report additional
 * features includes Custom Styles and Apply formula's
 * 
 * Reference - POI and API is still at beta level 
 * 
 * @author NIIT Technologies Ltd
 */
@NgenCosysAppInfraAnnotation(path = "/api/excel")
public class ExcelReportController {

   private static final Logger LOGGER = LoggerFactory.getLogger(ExcelReportController.class);
   
   @Autowired
   BeanFactory beanFactory;
   
   ExcelReportService excelReportService;
   
   /**
    * Generate EXCEL report using POI 
    * 
    * @param report
    *           Excel Report Request
    * @param request
    *           Http Request
    * @param response
    *           Http Response
    * @return
    * @throws CustomException 
    */
   @SuppressWarnings("unchecked")
   @ApiOperation(value = "API Excel Report download")
   @PostRequest(value = "/download", method = RequestMethod.POST)
   public BaseResponse<ExcelReportResponse> generateExcelReport(@RequestBody ExcelReportRequest reportRequest,
         HttpServletRequest request, HttpServletResponse response) {
      LOGGER.debug("Generate Excel Report request payload - {}", reportRequest.toString());
      BaseResponse<ExcelReportResponse> baseResponse = beanFactory.getBean(BaseResponse.class);
      excelReportService = (ExcelReportService) beanFactory.getBean(reportRequest.getReportName());
      //
      ExcelReportResponse reportResponse = null;
      try {
         reportResponse = excelReportService.generateReport(reportRequest, request, response);
      } catch (CustomException ex) {
         LOGGER.error("Excel Report Controller Exception occurred - {}", ex);
         baseResponse.setSuccess(false);
      }
      baseResponse.setData(reportResponse);
      return baseResponse;
   }
   
}
