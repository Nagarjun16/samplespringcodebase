package com.ngen.cosys.uncollectedfreightout.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ngen.cosys.annotation.NgenCosysAppInfraAnnotation;
import com.ngen.cosys.annotation.PostRequest;
import com.ngen.cosys.events.payload.EMailEvent;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.framework.model.BaseResponse;
import com.ngen.cosys.report.mail.service.ReportMailService;
import com.ngen.cosys.service.util.enums.ReportFormat;
import com.ngen.cosys.service.util.model.ReportMailPayload;
import com.ngen.cosys.timezone.util.TenantZoneTime;
import com.ngen.cosys.uncollectedfreightout.model.UncollectedFreightoutShipmentModel;
import com.ngen.cosys.uncollectedfreightout.service.UncollectedFreightoutService;

import io.swagger.annotations.ApiOperation;

@NgenCosysAppInfraAnnotation(path = "/api/shipment/delivery")
public class UncollectedFreightoutController {

   @Autowired
   BeanFactory beanFactory;
   
   @Autowired
   UncollectedFreightoutService service;
   
  /* @Autowired
	Uldbtcoolportservice service1;*/
	@Autowired
	ReportMailService reportMailService;
	private static final String TENANT_ID = "SIN";
   @SuppressWarnings("unchecked")
   @ApiOperation(value = "API method to Send date for UncollectedFreightOUt")
   @PostRequest(path = "/unCollectedFreightout", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
   public BaseResponse<UncollectedFreightoutShipmentModel> sendDateForUncollectedFreightout(
           @RequestBody UncollectedFreightoutShipmentModel requestModel) throws CustomException {
       BaseResponse<UncollectedFreightoutShipmentModel> response = this.beanFactory.getBean(BaseResponse.class);
       try {
           service.sendDateForUncollectedFreightout(requestModel);
           response.setData(requestModel);
           response.setSuccess(true);

       } catch (CustomException e) {
           response.setData(requestModel);
       }

       return response;
   }
   
   
   
	
	@ApiOperation("Get Flight from cosys")
	@PostMapping(value = "/api/batches/uldbtmailsend", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public void coolport() throws CustomException {
		final String TIMEFORMAT = "yyyy-MM-dd HH:mm:ss";
		/*LocalDateTime currenttime = null;
		currenttime = LocalDateTime.now().plusHours(8);
		LocalDateTime afterTime1 = LocalDate.now().atTime(00,30).plusHours(8);
		LocalDateTime beforeTime1 = LocalDate.now().atTime(8,59).plusHours(8);
		LocalDateTime afterTime2 = LocalDate.now().atTime(9,00).plusHours(8);
		LocalDateTime beforeTime2 = LocalDate.now().atTime(16,59).plusHours(8); 
		LocalDateTime afterTime3 = LocalDate.now().atTime(00,30).plusHours(8);
		LocalDateTime beforeTime3 = LocalDate.now().atTime(17,00).plusHours(8); 
		LocalDateTime shift1 = LocalDate.now().atTime(9,30).plusHours(8);
		LocalDateTime shift2 = LocalDate.now().atTime(17,30).plusHours(8);
		LocalDateTime shift3 = LocalDate.now().atTime(01,30).plusHours(8);*/
		List<String> emailAddresses = null;
		String time = TenantZoneTime.getZoneDateTime(LocalDateTime.now(), TENANT_ID)
              .format(DateTimeFormatter.ofPattern("HH:mm"));
		try {
			emailAddresses = service.getEmailAddress();
				
		}catch (CustomException e) {
			//logger.warn(e.getMessage());
		}
		Map<String, Object> reportParams = new HashMap<>();
		if (time.equals(TenantZoneTime.getZoneDateTime(LocalDate.now().atTime(01,30), TENANT_ID).toString())) {
           reportParams.put("FromDate",TenantZoneTime.getZoneDateTime(LocalDate.now().atTime(17,00), TENANT_ID).minusDays(1).format(DateTimeFormatter.ofPattern(TIMEFORMAT)) );
           reportParams.put("ToDate", TenantZoneTime.getZoneDateTime(LocalDate.now().atTime(00,29), TENANT_ID).format(DateTimeFormatter.ofPattern(TIMEFORMAT)));
           reportParams.put("Shift","3");
        }
        else if (time.equals(TenantZoneTime.getZoneDateTime(LocalDate.now().atTime(9,30), TENANT_ID).toString())) {
           reportParams.put("FromDate",TenantZoneTime.getZoneDateTime(LocalDate.now().atTime(00,30), TENANT_ID).format(DateTimeFormatter.ofPattern(TIMEFORMAT)) );
           reportParams.put("ToDate", TenantZoneTime.getZoneDateTime(LocalDate.now().atTime(8,59), TENANT_ID).format(DateTimeFormatter.ofPattern(TIMEFORMAT)));
           reportParams.put("Shift","1");
        }
        else if (time.equals(TenantZoneTime.getZoneDateTime(LocalDate.now().atTime(17,30), TENANT_ID).toString())) {
           reportParams.put("FromDate",TenantZoneTime.getZoneDateTime(LocalDate.now().atTime(9,00), TENANT_ID).format(DateTimeFormatter.ofPattern(TIMEFORMAT)) );
           reportParams.put("ToDate", TenantZoneTime.getZoneDateTime(LocalDate.now().atTime(16,59), TENANT_ID).format(DateTimeFormatter.ofPattern(TIMEFORMAT)));
           reportParams.put("Shift","2");
        }
		/*if(currenttime.equals(shift1)) {
			reportParams.put("FromDate",afterTime1.format(DateTimeFormatter.ofPattern(TIMEFORMAT)) );
			reportParams.put("ToDate", beforeTime1.format(DateTimeFormatter.ofPattern(TIMEFORMAT)));
			reportParams.put("Shift","1");
		}if(currenttime.isEqual(shift2)) {
			reportParams.put("FromDate",afterTime2.format(DateTimeFormatter.ofPattern(TIMEFORMAT)) );
			reportParams.put("ToDate", beforeTime2.format(DateTimeFormatter.ofPattern(TIMEFORMAT)));
			reportParams.put("Shift","2");
		}if(currenttime.isEqual(shift3)) {
			reportParams.put("FromDate",afterTime3.format(DateTimeFormatter.ofPattern(TIMEFORMAT)));
			reportParams.put("ToDate", beforeTime3.format(DateTimeFormatter.ofPattern(TIMEFORMAT)));
			reportParams.put("Shift","3");
		}*//*else {
			reportParams.put("FromDate","2019-05-01 00:00:00.000");
			reportParams.put("ToDate", "2019-08-01 00:00:00.000");
			reportParams.put("Shift","1");
		}*/
		
		//logger.warn("report param data for COOLPORT : "+ reportParams);
		List<ReportMailPayload> reportPayload = new ArrayList<>();
		ReportMailPayload reportMailPayload = new ReportMailPayload();
		reportMailPayload.setReportName("PHC_Ramp_Handover_Export");
		reportMailPayload.setReportFormat(ReportFormat.XLS);
		reportMailPayload.setReportParams(reportParams);
		
		List<ReportMailPayload> reportPayload1 = new ArrayList<>();
		ReportMailPayload reportMailPayload1 = new ReportMailPayload();
		reportMailPayload1.setReportName("PHC_Ramp_Handover_Import");
		reportMailPayload1.setReportFormat(ReportFormat.XLS);
		reportMailPayload1.setReportParams(reportParams);
		EMailEvent emailEvent = new EMailEvent();
		EMailEvent emailEvent1 = new EMailEvent();
		if (!CollectionUtils.isEmpty(emailAddresses)) { {
			emailEvent.setMailToAddress(emailAddresses.toArray(new String[emailAddresses.size()]));
			emailEvent.setNotifyAddress(null);
			emailEvent.setMailSubject("Export ULD/BT Towed from Coolport List " + "-" 
					+ LocalDate.now().format(DateTimeFormatter.ofPattern("ddMMyyyy")) + "-" + "[DO NOT REPLY]");
			emailEvent.setMailBody(" Export ULD/BT Towed from Coolport List");
			reportPayload.add(reportMailPayload);
			//logger.warn("email event data: "+ emailEvent);
			reportMailService.sendReport(reportPayload, emailEvent);
			
			emailEvent1.setMailToAddress(emailAddresses.toArray(new String[emailAddresses.size()]));
			emailEvent1.setNotifyAddress(null);
			emailEvent1.setMailSubject("Import ULD/BT Towed from Coolport List " + "-" 
					+ LocalDate.now().format(DateTimeFormatter.ofPattern("ddMMyyyy")) + "-" + "[DO NOT REPLY]");
			emailEvent1.setMailBody(" Import ULD/BT Towed from Coolport List");
			reportPayload1.add(reportMailPayload1);
			
			reportMailService.sendReport(reportPayload1, emailEvent1);
		}
		
		
	
	}
	}

}
