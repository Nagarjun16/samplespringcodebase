/**
 * {@link ExportFlightDashboardJob}
 * 
 * @copyright SATS Singapore 2020-21
 * @author NIIT
 */
package com.ngen.cosys.dashboard.job;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.ngen.cosys.dashboard.common.DashboardConstants;
import com.ngen.cosys.dashboard.config.DashboardConfig;
import com.ngen.cosys.dashboard.model.DashboardBatchLog;
import com.ngen.cosys.dashboard.model.EventNotification;
import com.ngen.cosys.dashboard.model.ExportFlightData;
import com.ngen.cosys.dashboard.service.FlightDashboardService;
import com.ngen.cosys.dashboard.util.FlightDashboardUtils;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.multitenancy.context.TenantContext;
import com.ngen.cosys.scheduler.job.AbstractCronJob;
import com.ngen.cosys.scheduler.service.JobService;

/**
 * Export Flight Dashboard Job
 * 
 * @author NIIT Technologies Ltd
 */
@Component(value = DashboardConstants.EXPORT_FLIGHT_DASHBOARD)
public class ExportFlightDashboardJob extends AbstractCronJob implements FlightDashboardJob {
   
   private static final Logger LOGGER = LoggerFactory.getLogger(ExportFlightDashboardJob.class);
   
   @Autowired
   JobService jobService;
   
   @Autowired
   DashboardConfig dashboardConfig;
   
   @Autowired
   FlightDashboardService dashboardService;
   
   /**
    * @see com.ngen.cosys.scheduler.job.AbstractCronJob#executeInternal(org.quartz.JobExecutionContext)
    */
   @Override
   protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
      DashboardBatchLog dashboardBatchLog = new DashboardBatchLog();
      dashboardBatchLog.setStartTime(FlightDashboardUtils.getCurrentZoneTime());
      try {
         dashboardService.insertExportDashboardBatchLog(dashboardBatchLog);
         // Export Dashboard Job
         exportFlightDashboard(dashboardBatchLog);
         // Batch job end time for monitoring
         dashboardBatchLog.setEndTime(FlightDashboardUtils.getCurrentZoneTime());
         // Next Fire Time
         dashboardBatchLog.setNextFireTime(jobService.getJobNextFireTime(DashboardConstants.EXPORT_FLIGHT_DASHBOARD_JOB,
               DashboardConstants.JOB_GROUP, TenantContext.get().getTenantId()));
         // Update batch log
         dashboardService.updateExportDashboardBatchLog(dashboardBatchLog);
      } catch (Exception ex) {
         LOGGER.error("Export Flight Dashboard Job exception occurred - {}", ex);
         // Update Batch Log if any exception
         try {
            if (Objects.isNull(dashboardBatchLog.getTotalFlights())) {
               dashboardBatchLog.setTotalFlights(0);
            }
            // Next Fire Time
            dashboardBatchLog.setNextFireTime(jobService
                  .getJobNextFireTime(DashboardConstants.EXPORT_FLIGHT_DASHBOARD_JOB, DashboardConstants.JOB_GROUP,
                		  TenantContext.get().getTenantId()));
            dashboardService.updateExportDashboardBatchLog(dashboardBatchLog);
         } catch (Exception e) {
         }
         // Batch job end time for monitoring
         dashboardBatchLog.setEndTime(FlightDashboardUtils.getCurrentZoneTime());
      }
      LOGGER.warn("Export Flight Dashboard Job execution completed :: START TIME - {}, END TIME - {}",
            dashboardBatchLog.getStartTime(), dashboardBatchLog.getEndTime());
   }
   
   /**
    * Export Flight Dashboard
    * 
    * @param flightsMonitoring
    * @throws CustomException 
    */
   private void exportFlightDashboard(DashboardBatchLog dashboardBatchLog) throws Exception {
      LocalDateTime startTime = FlightDashboardUtils.getCurrentZoneTime();
      List<EventNotification> eventConfigurations = getEventConfigurations(DashboardConstants.EXPORT);
      LocalDateTime queryStartTime = FlightDashboardUtils.getCurrentZoneTime();
      List<ExportFlightData> exportFlights = dashboardService.getExportFlightsData();
      if (!CollectionUtils.isEmpty(exportFlights)) {
         LOGGER.warn(
               "Export Flight Dashboard Job - Flight data query execution time :: START TIME - {}, END TIME - {}, Size - {}",
               queryStartTime, FlightDashboardUtils.getCurrentZoneTime(), exportFlights.size());
         dashboardBatchLog.setTotalFlights(exportFlights.size());
         // Event SLA timing verification
         FlightDashboardUtils.exportFlightEventsUpdate(exportFlights, eventConfigurations);
         // Insert into Export Flight Dashboard table
         dashboardService.insertExportFlightsData(exportFlights, dashboardBatchLog.getDashboardBatchLogId());
      } else {
         LOGGER.warn("Export Flight Dashboard Job - Flight data is EMPTY {}");
         dashboardBatchLog.setTotalFlights(0);
      }
      LOGGER.warn("Export Flight Dashboard Job # Flight execution completed :: START TIME - {}, END TIME - {}",
            startTime, FlightDashboardUtils.getCurrentZoneTime());
   }
   
   /**
    * GET Event notification configuration
    * 
    * @param module
    *       Import/Export
    * @return
    */
   public List<EventNotification> getEventConfigurations(String module) {
      List<EventNotification> eventConfigurations = dashboardConfig.getEventNotificationConfiguration(module);
      if (!CollectionUtils.isEmpty(eventConfigurations)) {
         LOGGER.warn("Export Flight Dashboard Job :: GET Event Configurations - Module - {}, Config size - {}", //
               module, eventConfigurations.size());
      } else {
         LOGGER.warn("Export Flight Dashboard Job :: GET Event Confiugrations - Module - {}, Config is EMPTY {}",
               module);
      }
      return eventConfigurations;
   }
   
}
