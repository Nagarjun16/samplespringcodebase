package com.ngen.cosys.application.job;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.ngen.cosys.flight.model.BatchJobsLog;
import com.ngen.cosys.flight.model.OperativeFlight;
import com.ngen.cosys.flight.model.OperativeFlightLeg;
import com.ngen.cosys.flight.service.OperativeFlightScheduleService;
import com.ngen.cosys.flight.service.OperativeFlightService;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.scheduler.job.AbstractCronJob;

public class ScheduleFlightCreationJob extends AbstractCronJob {

   private static final Logger LOGGER = LoggerFactory.getLogger(ScheduleFlightCreationJob.class);

   private static final String TENANT = "SIN";

   @Autowired
   private OperativeFlightScheduleService operativeFlightScheduleService;

   @Autowired
   private OperativeFlightService oprFltService;

   @Override
   protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
      try {
         BatchJobsLog logDetails = new BatchJobsLog();
         logDetails.setStartDateTime(LocalDateTime.now());
         LOGGER.warn("ScheduleFlightCreationJob Job Started");
         List<OperativeFlight> ofList = operativeFlightScheduleService.fetchOperativeFlightSchedule();
         processList(ofList, logDetails);
         logDetails.setEndDateTime(LocalDateTime.now());
         operativeFlightScheduleService.performJobLog(logDetails);
         LOGGER.warn("ScheduleFlightCreationJob Job Finished");
      } catch (CustomException e) {
         LOGGER.error("Error Processing Job " + e);
      }
   }

   protected void processList(List<OperativeFlight> ofList, final BatchJobsLog logDetails) {
      long totalCount = 0;
      long successCount = 0;
      long failedCount = 0;
      if (CollectionUtils.isEmpty(ofList)) {
         LOGGER.warn("No Operative Flight Records to Process from ScheduleFlightCreationJob...");
      } else {
         totalCount = ofList.size();
         ofList.stream().forEach(a -> a.setTenantId(TENANT));
         ofList.stream().forEach(a-> a.getFlightLegs().stream().forEach(leg->leg.setTenantId(TENANT)));
         for (OperativeFlight oprFlt : ofList) {
            // inserts the operative flight
            try {
               oprFltService.maintain(oprFlt);
               successCount++;
            } catch (Exception e) {
               failedCount++;

               //If it is an custom exception then get the error message
               String message = e.getMessage();

               if (e instanceof CustomException) {
                  message = ((CustomException) e).getMessage() + ((CustomException) e).getPropertyName();
               }

               LOGGER.error("Error Happenned while calling Flight Service ::: ", e);
               LOGGER.warn("Error Message is as below ::: " + message);
            }
            if(!CollectionUtils.isEmpty(oprFlt.getMessageList())) {
            	failedCount++;
            }else {
            	for(OperativeFlightLeg leg:oprFlt.getFlightLegs()) {
            		if(!CollectionUtils.isEmpty(leg.getMessageList())) {
            			failedCount++;
            		}
            	  }
            }
         }
      }
      logDetails.setTotalRecords(totalCount);
      logDetails.setFailureRecords(failedCount);
      logDetails.setSuccessRecords(successCount);
   }

}