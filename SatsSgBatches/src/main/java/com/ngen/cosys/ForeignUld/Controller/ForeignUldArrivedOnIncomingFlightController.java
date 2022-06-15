package com.ngen.cosys.ForeignUld.Controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ngen.cosys.ForeignUld.Model.ForeignUldArrivedOnIncomingFlightModel;
import com.ngen.cosys.ForeignUld.Service.ForeignUldArrivedOnIncomingFlightSrevice;
import com.ngen.cosys.annotation.PostRequest;
import com.ngen.cosys.events.payload.EMailEvent;
import com.ngen.cosys.framework.config.UtilitiesModelConfiguration;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.framework.model.BaseResponse;
import com.ngen.cosys.multitenancy.utilities.MultiTenantUtility;
import com.ngen.cosys.report.mail.service.ReportMailService;
import com.ngen.cosys.service.util.enums.ReportFormat;
import com.ngen.cosys.service.util.model.ReportMailPayload;

import io.swagger.annotations.ApiOperation;

@RestController
public class ForeignUldArrivedOnIncomingFlightController {

   @Autowired
   private UtilitiesModelConfiguration utilitiesModelConfiguration;

   @Autowired
   ForeignUldArrivedOnIncomingFlightSrevice service;

   @Autowired
   ReportMailService reportMailService;


   @ApiOperation("fetch data according to carrier code")
   @PostRequest(value = "api/jobdata/foreignuldarrivedonincomingcarrier", method = RequestMethod.POST)
   public BaseResponse<List<ForeignUldArrivedOnIncomingFlightModel>> getReportOnTheBasisOfCarriercode()
         throws CustomException {
      BaseResponse<List<ForeignUldArrivedOnIncomingFlightModel>> response = utilitiesModelConfiguration
            .getBaseResponseInstance();
      String responseData = service.getReportOnTheBasisOfCarriercode();

      if (!StringUtils.isEmpty(responseData)) {
         ForeignUldArrivedOnIncomingFlightModel parameters = service.getParametersForForeignUldsReport();
         List<String> emailAddresses = null;
         emailAddresses = service.getEmailAddresses(responseData);
         Map<String, Object> reportParams = new HashMap<>();
         reportParams.put("carrierCode", responseData);
         reportParams.put("fromDate", parameters.getFromDate().toString());
         reportParams.put("toDate", parameters.getToDate().toString());
         List<ReportMailPayload> reportPayload = new ArrayList<>();
         ReportMailPayload reportMailPayload = new ReportMailPayload();
         reportMailPayload.setReportName("ForeignUldArrivedOnIncomingFlightReport");
         reportMailPayload.setReportFormat(ReportFormat.CSV);
         reportMailPayload.setReportParams(reportParams);
         EMailEvent emailEvent = new EMailEvent();
         if (!CollectionUtils.isEmpty(emailAddresses)) {
            emailEvent.setMailToAddress(emailAddresses.toArray(new String[emailAddresses.size()]));
            emailEvent.setNotifyAddress(null);
            emailEvent.setMailSubject("Foreign Uld Arrived On Incoming Flight Report " + "-" + responseData + "_"
                  + LocalDate.now().format(MultiTenantUtility.getTenantDateFormat()) + "-" + "[DO NOT REPLY]");
            emailEvent.setMailBody("Foreign Uld Arrived On Incoming Flight Report ");
            reportPayload.add(reportMailPayload);
            reportMailService.sendReport(reportPayload, emailEvent);
         }

      }

      service.updateLatestFromDateForForeignULD();

      return response;

   }

}
