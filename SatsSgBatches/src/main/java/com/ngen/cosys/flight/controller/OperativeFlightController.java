package com.ngen.cosys.flight.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ngen.cosys.annotation.NgenCosysAppInfraAnnotation;
import com.ngen.cosys.annotation.PostRequest;
import com.ngen.cosys.flight.model.BatchJobsLog;
import com.ngen.cosys.flight.model.OperativeFlight;
import com.ngen.cosys.flight.service.OperativeFlightScheduleService;
import com.ngen.cosys.flight.service.OperativeFlightService;
import com.ngen.cosys.framework.config.UtilitiesModelConfiguration;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.framework.model.BaseResponse;

import io.swagger.annotations.ApiOperation;

@NgenCosysAppInfraAnnotation
public class OperativeFlightController {

   private static final String TENANT = "SIN";

   @Autowired
   private UtilitiesModelConfiguration utilitiesModelConfiguration;

   @Autowired
   @Qualifier("operativeFlightScheduleServiceImpl")
   private OperativeFlightScheduleService operativeFlightScheduleService;

   @Autowired
   private OperativeFlightService oprFltService;

   private static Logger LOGGER = LoggerFactory.getLogger(OperativeFlightController.class);

   @ApiOperation("Create flight every day based on schedule")
   @PostRequest(value = "api/FlightScheduleDetails/getOperativeFlightScheduleDetails", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
   public BaseResponse<List<OperativeFlight>> getOperativeFlightScheduleDetails() {
      @SuppressWarnings("unchecked")
      BaseResponse<List<OperativeFlight>> response = utilitiesModelConfiguration.getBaseResponseInstance();
      List<OperativeFlight> data = null;
      try {
         BatchJobsLog logDetails = new BatchJobsLog();
         logDetails.setStartDateTime(LocalDateTime.now());
         LOGGER.warn("Job Started");
         List<OperativeFlight> ofList = operativeFlightScheduleService.fetchOperativeFlightSchedule();
         processList(ofList, logDetails);
         logDetails.setEndDateTime(LocalDateTime.now());
         operativeFlightScheduleService.performJobLog(logDetails);
         LOGGER.warn("Job Finished");
      } catch (CustomException e) {
         LOGGER.error("Exception", e);
      }
      // end service calling
      response.setData(data);
      return response;
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
         for (OperativeFlight oprFlt : ofList) {
            // inserts the operative flight
            try {
               // Set the tenant for leg
               oprFlt.getFlightLegs().forEach(a -> a.setTenantId(TENANT));

               oprFltService.maintain(oprFlt);
               successCount++;
            } catch (Exception e) {
               failedCount++;

               // If it is an custom exception then get the error message
               String message = e.getMessage();

               if (e instanceof CustomException) {
                  message = ((CustomException) e).getMessage() + ((CustomException) e).getPropertyName();
               }

               LOGGER.error("Error Happenned while calling Flight Service ::: ", e);
               LOGGER.warn("Error Message is as below ::: " + message);
            }
         }
      }
      successCount = totalCount - failedCount;
      logDetails.setTotalRecords(totalCount);
      logDetails.setFailureRecords(failedCount);
      logDetails.setSuccessRecords(successCount);
   }

}