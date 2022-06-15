/**
 * {@link CiQReportServiceImpl}
 * 
 * @copyright SATS Singapore 2019-20
 * @author NIIT
 */
package com.ngen.cosys.ciq.report.service;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.ngen.cosys.ciq.report.common.CiQReportConstants;
import com.ngen.cosys.ciq.report.common.ReportFrequency;
import com.ngen.cosys.ciq.report.dao.CiQReportDAO;
import com.ngen.cosys.ciq.report.model.CiQReportLog;
import com.ngen.cosys.ciq.report.model.NotificationSchedule;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.multi.tenancy.enums.TenantZone;
import com.ngen.cosys.multitenancy.context.TenantContext;
import com.ngen.cosys.timezone.util.TenantZoneTime;

/**
 * CiQ Report Service Implementation
 * 
 * @author NIIT Technologies Ltd
 */
@Service
public class CiQReportServiceImpl implements CiQReportService {

   private static final Logger LOGGER = LoggerFactory.getLogger(CiQReportService.class);
   private static final String TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
  

   @Autowired
   CiQReportDAO ciqReportDAO;

   /**
    * @see com.ngen.cosys.ciq.report.service.CiQReportService#getScheduledReports(com.ngen.cosys.ciq.report.common.ReportFrequency)
    */
   @Override
   public List<NotificationSchedule> getScheduledReports(String frequency) throws CustomException {
      LOGGER.info("CiQ Report Service :: GET Scheduled Reports - {}", frequency);
      return ciqReportDAO.getScheduledReports(frequency);
   }

   /**
    * @see com.ngen.cosys.ciq.report.service.CiQReportService#logCiQReport(java.lang.String,
    *      java.lang.String)
    */
   @Override
   public BigInteger logCiQReport(NotificationSchedule reportSchedule) throws CustomException {
      LOGGER.info("CiQ Report Service :: logCiQReport Name - {}, Frequency - {}", reportSchedule.getReportName(),
            reportSchedule.getReportFrequency());
      return ciqReportDAO.logCiQReport(getCiQReportLog(null, reportSchedule, CiQReportConstants.INITIATED));
   }

   /**
    * @see com.ngen.cosys.ciq.report.service.CiQReportService#updateCiQReport(java.math.BigInteger)
    */
   @Override
   public void updateCiQReport(BigInteger reportLogId) throws CustomException {
      LOGGER.info("CiQ Report Service :: logCiQReport LogID - {}", String.valueOf(reportLogId));
      ciqReportDAO.updateCiQReport(getCiQReportLog(reportLogId, null, CiQReportConstants.COMPLETED));
   }

   /**
    * @see com.ngen.cosys.ciq.report.service.CiQReportService#verifyCiQReport(java.lang.String,
    *      java.lang.String)
    */
   @Override
   public boolean verifyCiQReport(NotificationSchedule reportSchedule) throws CustomException {
      LOGGER.info("CiQ Report Service :: logCiQReport Name - {}, Frequency - {}", reportSchedule.getReportName(),
            reportSchedule.getReportFrequency());
      return ciqReportDAO.verifyCiQReport(getCiQReportLog(null, reportSchedule, null));
   }

   /**
    * @param reportLogId
    * @param reportName
    * @param frequency
    * @param status
    * @return
    */
   private CiQReportLog getCiQReportLog(BigInteger reportLogId, NotificationSchedule reportSchedule, String status) {
      CiQReportLog reportLog = new CiQReportLog();
      //
      if (Objects.nonNull(reportLogId)) {
         reportLog.setReportLogId(reportLogId);
      }
      if (!ObjectUtils.isEmpty(reportSchedule)) {
         reportLog.setReportName(reportSchedule.getReportName());
         reportLog.setReportFrequency(reportSchedule.getReportFrequency());
         reportLog.setTransitFlag(reportSchedule.isTransitFlag());
         if (!StringUtils.isEmpty(reportSchedule.getMessageType())
               && !StringUtils.isEmpty(reportSchedule.getCarrierCode())) {
            reportLog.setMessageType(reportSchedule.getMessageType());
            reportLog.setCarrierCode(reportSchedule.getCarrierCode());
         }
      }
      reportLog.setStatus(status);
      //
      return reportLog;
   }

   @Override
   public boolean dataExists(NotificationSchedule reportSchedule) throws CustomException {
	   LocalDateTime currentUTCTime = TenantZoneTime.getZoneDateTime(LocalDateTime.now(),TenantContext.get().getTenantId());
      switch (reportSchedule.getReportFrequency()) {
      case ReportFrequency.DAILY:
         reportSchedule.setFromDate(
               LocalDateTime.of(currentUTCTime.toLocalDate().minusDays(1), reportSchedule.getConfiguredTime())
                     .format(DateTimeFormatter.ofPattern(TIME_FORMAT)));

         if (reportSchedule.getConfiguredTime().compareTo(LocalTime.of(00, 00)) == 0) {
            reportSchedule.setToDate(LocalDateTime
                  .of(currentUTCTime.toLocalDate().minusDays(1), reportSchedule.getConfiguredTime().minusMinutes(1))
                  .format(DateTimeFormatter.ofPattern(TIME_FORMAT)));
         } else {
            reportSchedule.setToDate(
                  LocalDateTime.of(currentUTCTime.toLocalDate(), reportSchedule.getConfiguredTime().minusMinutes(1))
                        .format(DateTimeFormatter.ofPattern(TIME_FORMAT)));
         }
         break;
      case ReportFrequency.WEEKLY:
         reportSchedule.setFromDate(LocalDateTime.of(currentUTCTime.toLocalDate().minusDays(7), LocalTime.of(00, 00))
               .format(DateTimeFormatter.ofPattern(TIME_FORMAT)));
         reportSchedule.setToDate(LocalDateTime.of(currentUTCTime.toLocalDate().minusDays(1), LocalTime.of(23, 59))
               .format(DateTimeFormatter.ofPattern(TIME_FORMAT)));
         break;
      case ReportFrequency.MONTHLY:
         reportSchedule.setFromDate(LocalDateTime.of(currentUTCTime.toLocalDate().minusMonths(1), LocalTime.of(00, 00))
               .format(DateTimeFormatter.ofPattern(TIME_FORMAT)));
         reportSchedule.setToDate(LocalDateTime.of(currentUTCTime.toLocalDate().minusDays(1), LocalTime.of(23, 59))
               .format(DateTimeFormatter.ofPattern(TIME_FORMAT)));
         break;
      default:
         reportSchedule.setFromDate(
               LocalDateTime.of(currentUTCTime.toLocalDate().minusDays(1), reportSchedule.getConfiguredTime())
                     .format(DateTimeFormatter.ofPattern(TIME_FORMAT)));
         reportSchedule.setToDate(LocalDateTime.of(currentUTCTime.toLocalDate(), reportSchedule.getConfiguredTime())
               .format(DateTimeFormatter.ofPattern(TIME_FORMAT)));
         ;
      }

      LOGGER.info("CiQ Report Service,checking data exists or not:: logCiQReport Name - {}, Frequency - {}",
            reportSchedule.getReportName(), reportSchedule.getReportFrequency());
      return ciqReportDAO.dataExists(reportSchedule);
   }
}
