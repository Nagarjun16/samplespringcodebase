/**
 * {@link CiQReportJob}
 * 
 * @copyright SATS Singapore 2019-20
 * @author NIIT
 */
package com.ngen.cosys.ciq.report.job;

import java.util.List;

import com.ngen.cosys.ciq.report.model.CiQReportMailPayload;
import com.ngen.cosys.ciq.report.model.NotificationSchedule;
import com.ngen.cosys.framework.exception.CustomException;

/**
 * CiQ Report Job Interface
 * 
 * @author NIIT Technologies Ltd
 */
public interface CiQReportJob {

   /**
    * Notification Schedule configuration
    * 
    * @param frequency
    * @return
    * @throws CustomException
    */
   List<NotificationSchedule> notificationScheduleConfig(String frequency) throws CustomException;
   
   /**
    * Generate Report
    * 
    * @param reportSchedule
    * @return
    * @throws CustomException
    */
   CiQReportMailPayload generateReport(NotificationSchedule reportSchedule) throws CustomException;
   
   /**
    * Send Notification
    * 
    * @param reportSchedule
    * @param reportMailPayload
    * @throws CustomException
    */
   void sendNotification(NotificationSchedule reportSchedule, CiQReportMailPayload reportMailPayload)
         throws CustomException;
   
}
