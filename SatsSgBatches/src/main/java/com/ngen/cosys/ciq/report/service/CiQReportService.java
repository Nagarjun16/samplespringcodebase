/**
 * {@link CiQReportService}
 * 
 * @copyright SATS Singapore 2019-20
 * @author NIIT
 */
package com.ngen.cosys.ciq.report.service;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;

import com.ngen.cosys.ciq.report.model.CiQReportLog;
import com.ngen.cosys.ciq.report.model.NotificationSchedule;
import com.ngen.cosys.framework.exception.CustomException;

/**
 * CiQ Report Service
 * 
 * @author NIIT Technologies Ltd
 */
public interface CiQReportService {

   /**
    * GET Scheduled Reports Config
    * 
    * @param frequency
    * @return
    * @throws CustomException
    */
   List<NotificationSchedule> getScheduledReports(String frequency) throws CustomException;
   
   /**
    * INSERT CiQ Report Request
    * 
    * @param reportName
    * @param frequency
    * @return
    * @throws CustomException
    */
   BigInteger logCiQReport(NotificationSchedule reportSchedule) throws CustomException;
   
   /**
    * UPDATE CiQ Report Request
    * 
    * @param reportLogId
    * @throws CustomException
    */
   void updateCiQReport(BigInteger reportLogId) throws CustomException;
   
   /**
    * Verify CiQ Report Request
    * 
    * @param reportName
    * @param frequency
    * @return
    * @throws CustomException
    */
   boolean verifyCiQReport(NotificationSchedule reportSchedule) throws CustomException;
   
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
