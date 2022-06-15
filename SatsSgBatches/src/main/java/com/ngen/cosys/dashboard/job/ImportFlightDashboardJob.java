/**
 * {@link ImportFlightDashboardJob}
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
import com.ngen.cosys.dashboard.model.ImportFlightData;
import com.ngen.cosys.dashboard.service.FlightDashboardService;
import com.ngen.cosys.dashboard.util.FlightDashboardUtils;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.multitenancy.context.TenantContext;
import com.ngen.cosys.scheduler.job.AbstractCronJob;
import com.ngen.cosys.scheduler.service.JobService;

/**
 * Import Flight Dashboard Job
 * 
 * @author NIIT Technologies Ltd
 */
@Component(value = DashboardConstants.IMPORT_FLIGHT_DASHBOARD)
public class ImportFlightDashboardJob extends AbstractCronJob implements FlightDashboardJob {

   private static final Logger LOGGER = LoggerFactory.getLogger(ImportFlightDashboardJob.class);
   
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
         dashboardService.insertImportDashboardBatchLog(dashboardBatchLog);
         // Import Dashboard Job
         importFlightDashboard(dashboardBatchLog);
         // Batch job end time for monitoring
         dashboardBatchLog.setEndTime(FlightDashboardUtils.getCurrentZoneTime());
         // Next Fire Time
         dashboardBatchLog.setNextFireTime(jobService.getJobNextFireTime(DashboardConstants.IMPORT_FLIGHT_DASHBOARD_JOB,
               DashboardConstants.JOB_GROUP, TenantContext.get().getTenantId()));
         // Update batch log
         dashboardService.updateImportDashboardBatchLog(dashboardBatchLog);
      } catch (Exception ex) {
         LOGGER.error("Import Flight Dashboard Job exception occurred - {}", ex);
         // Update Batch Log if any exception
         try {
            if (Objects.isNull(dashboardBatchLog.getTotalFlights())) {
               dashboardBatchLog.setTotalFlights(0);
            }
            // Next Fire Time
            dashboardBatchLog.setNextFireTime(jobService
                  .getJobNextFireTime(DashboardConstants.IMPORT_FLIGHT_DASHBOARD_JOB, DashboardConstants.JOB_GROUP,
                		  TenantContext.get().getTenantId()));
            dashboardService.updateImportDashboardBatchLog(dashboardBatchLog);
         } catch (Exception e) {
         }
         // Batch job end time for monitoring
         dashboardBatchLog.setEndTime(FlightDashboardUtils.getCurrentZoneTime());
      }
      LOGGER.warn("Import Flight Dashboard Job execution completed :: START TIME - {}, END TIME - {}",
            dashboardBatchLog.getStartTime(), dashboardBatchLog.getEndTime());
   }
   
   /**
    * Import Flight Dashboard
    * 
    * @param flightsMonitoring
    * @throws CustomException
    */
   private void importFlightDashboard(DashboardBatchLog dashboardBatchLog) throws Exception {
      LocalDateTime startTime = FlightDashboardUtils.getCurrentZoneTime();
      List<EventNotification> eventConfigurations = getEventConfigurations(DashboardConstants.IMPORT);
      LocalDateTime queryStartTime = FlightDashboardUtils.getCurrentZoneTime();
      List<ImportFlightData> importFlights = dashboardService.getImportFlightsData();
      if (!CollectionUtils.isEmpty(importFlights)) {
         LOGGER.warn(
               "Import Flight Dashboard Job - Flight data query execution time :: START TIME - {}, END TIME - {}, Size - {}",
               queryStartTime, FlightDashboardUtils.getCurrentZoneTime(), importFlights.size());
         dashboardBatchLog.setTotalFlights(importFlights.size());
         // Event SLA timing verification
         FlightDashboardUtils.importFlightEventsUpdate(importFlights, eventConfigurations);
         // Insert into Import Flight Dashboard table
         dashboardService.insertImportFlightsData(importFlights, dashboardBatchLog.getDashboardBatchLogId());
      } else {
         LOGGER.warn("Import Flight Dashboard Job - Flight data is EMPTY {}");
         dashboardBatchLog.setTotalFlights(0);
      }
      LOGGER.warn("Import Flight Dashboard Job # Flight execution completed :: START TIME - {}, END TIME - {}",
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
         LOGGER.warn("Import Flight Dashboard Job :: GET Event Configurations - Module - {}, Config size - {}", //
               module, eventConfigurations.size());
      } else {
         LOGGER.warn("Import Flight Dashboard Job :: GET Event Confiugrations - Module - {}, Config is EMPTY {}",
               module);
      }
      return eventConfigurations;
   }
   
}
