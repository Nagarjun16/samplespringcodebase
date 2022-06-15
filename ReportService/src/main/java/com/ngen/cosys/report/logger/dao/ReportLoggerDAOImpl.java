/**
 * 
 * ReportLoggerDAOImpl.java
 * 
 * Copyright <PRE><IMG SRC = XX></IMG></PRE>
 *
 * Version      Date         Author      Reason
 * 1.0          11 JUL, 2018   NIIT      -
 */
package com.ngen.cosys.report.logger.dao;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.event.Level;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.report.logger.enums.ReportLoggerSQL;
import com.ngen.cosys.report.logger.model.ReportPayload;

/**
 * This Class is used for Report logger DAO Implementation
 * 
 * @author NIIT Technologies Ltd
 *
 */
@Repository
public class ReportLoggerDAOImpl implements ReportLoggerDAO {

   private static final Logger LOGGER = LoggerFactory.getLogger(ReportLoggerDAOImpl.class);
   
   @Qualifier("sqlSessionTemplate") 
   @Autowired
   private SqlSession sqlSession;
   
   /**
    * @see com.ngen.cosys.report.logger.dao.ReportLoggerDAO#logReportService(com.ngen.cosys.report.logger.model.ReportPayload)
    * 
    */
   @Override
   public void logReportService(ReportPayload payload) throws CustomException {
      //
      LOGGER.debug(this.getClass().getName(), "logReportService", Level.DEBUG, null, null);
      //
      sqlSession.insert(ReportLoggerSQL.INSERT_REPORT_LOG.getQuery(), payload);
   }

   /**
    * @see com.ngen.cosys.report.logger.dao.ReportLoggerDAO#updateReportServiceLog(com.ngen.cosys.report.logger.model.ReportPayload)
    * 
    */
   @Override
   public void updateReportServiceLog(ReportPayload payload) throws CustomException {
      //
      LOGGER.debug(this.getClass().getName(), "updateReportServiceLog", Level.DEBUG, null, null);
      //
      sqlSession.update(ReportLoggerSQL.UPDATE_REPORT_LOG.getQuery(), payload);
   }

}
