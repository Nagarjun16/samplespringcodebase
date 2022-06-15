/**
 * {@link ExcelReportService}
 * 
 * @copyright SATS Singapore 2019-20
 * @author NIIT
 */
package com.ngen.cosys.report.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.report.model.ExcelReportRequest;
import com.ngen.cosys.report.model.ExcelReportResponse;

/**
 * Excel Report Service has implemented using Abstract factory design pattern
 * 
 * @author NIIT Technologies Ltd
 */
public interface ExcelReportService {

   /**
    * @param reportRequest
    * @param request
    * @param response
    * @return
    * @throws CustomException
    */
   ExcelReportResponse generateReport(ExcelReportRequest reportRequest, HttpServletRequest request,
         HttpServletResponse response) throws CustomException;
   
}
