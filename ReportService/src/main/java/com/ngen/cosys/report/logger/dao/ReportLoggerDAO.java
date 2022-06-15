/**
 * 
 * ReportLoggerDAO.java
 * 
 * Copyright <PRE><IMG SRC = XX></IMG></PRE>
 *
 * Version      Date         Author      Reason
 * 1.0          11 JUL, 2018   NIIT      -
 */
package com.ngen.cosys.report.logger.dao;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.report.logger.model.ReportPayload;

/**
 * This interface is used for Report logger DAO
 * 
 * @author NIIT Technologies Ltd
 *
 */
public interface ReportLoggerDAO {

   /**
    * @param payload
    * @return
    * @throws CustomException
    */
   public void logReportService(ReportPayload payload) throws CustomException;

   /**
    * @param payload
    * @throws CustomException
    */
   public void updateReportServiceLog(ReportPayload payload) throws CustomException;
   
}
