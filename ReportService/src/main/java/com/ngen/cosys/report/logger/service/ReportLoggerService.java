/**
 * 
 * ReportLoggerService.java
 * 
 * Copyright <PRE><IMG SRC = XX></IMG></PRE>
 *
 * Version      Date         Author      Reason
 * 1.0          11 JUL, 2018   NIIT      -
 */
package com.ngen.cosys.report.logger.service;

import java.math.BigInteger;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.report.logger.model.ReportPayload;

/**
 * This interface is used for Report logger service
 * 
 * @author NIIT Technologies Ltd
 *
 */
public interface ReportLoggerService {

   /**
    * @param payload
    * @return
    * @throws CustomException
    */
   public BigInteger logReportService(ReportPayload payload) throws CustomException;

   /**
    * @param payload
    * @throws CustomException
    */
   public void updateReportServiceLog(ReportPayload payload) throws CustomException;

}
