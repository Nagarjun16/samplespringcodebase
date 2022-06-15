/**
 * 
 * ReportLogger.java
 * 
 * Copyright <PRE><IMG SRC = XX></IMG></PRE>
 *
 * Version      Date         Author      Reason
 * 1.0          11 JUL, 2018   NIIT      -
 */
package com.ngen.cosys.report.logger.service;

import java.math.BigInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.report.logger.dao.ReportLoggerDAO;
import com.ngen.cosys.report.logger.model.ReportPayload;

/**
 * This Class is used for Report logger service
 * 
 * @author NIIT Technologies Ltd
 *
 */
@Service
public class ReportLogger implements ReportLoggerService {

   @Autowired
   ReportLoggerDAO reportLoggerDAO;
   
   /**
    * @see com.ngen.cosys.report.logger.service.ReportLoggerService#logReportService(com.ngen.cosys.report.logger.model.ReportPayload)
    * 
    */
   @Override
   public BigInteger logReportService(ReportPayload payload) throws CustomException {
      reportLoggerDAO.logReportService(payload);
      return payload.getReportServiceLogId();
   }

   /**
    * @see com.ngen.cosys.report.logger.service.ReportLoggerService#updateReportServiceLog(com.ngen.cosys.report.logger.model.ReportPayload)
    * 
    */
   @Override
   public void updateReportServiceLog(ReportPayload payload) throws CustomException {
      reportLoggerDAO.updateReportServiceLog(payload);
   }
   
}
