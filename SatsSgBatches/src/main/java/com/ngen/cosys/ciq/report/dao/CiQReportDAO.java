/**
 * {@link CiQReportDAO}
 * 
 * @copyright SATS Singapore 2019-20
 * @author NIIT
 */
package com.ngen.cosys.ciq.report.dao;

import java.math.BigInteger;
import java.util.List;

import com.ngen.cosys.ciq.report.model.CiQReportLog;
import com.ngen.cosys.ciq.report.model.NotificationSchedule;
import com.ngen.cosys.framework.exception.CustomException;

/**
 * CiQ Report DAO
 * 
 * @author NIIT Technologies Ltd
 */
public interface CiQReportDAO {

   /**
    * GET Scheduled Reports
    * 
    * @param frequency
    * @return
    * @throws CustomException
    */
   List<NotificationSchedule> getScheduledReports(String frequency) throws CustomException;
   
   /**
    * INSERT CiQ Report Request
    * 
    * @param reportLog
    * @return
    * @throws CustomException
    */
   BigInteger logCiQReport(CiQReportLog reportLog) throws CustomException;
   
   /**
    * UPDATE CiQ Report Request
    * 
    * @param reportLog
    * @throws CustomException
    */
   void updateCiQReport(CiQReportLog reportLog) throws CustomException;
   
   /**
    * Verify CiQ Report Request
    * 
    * @param reportLog
    * @return
    * @throws CustomException
    */
   boolean verifyCiQReport(CiQReportLog reportLog) throws CustomException;
   /**
    * Verify report data exists
    * 
    * @param reportName
    * @param message type
    * @param frequency
    * @return
    * @throws CustomException
    */
   boolean dataExists(NotificationSchedule reportSchedule) throws CustomException;
   
   
}
