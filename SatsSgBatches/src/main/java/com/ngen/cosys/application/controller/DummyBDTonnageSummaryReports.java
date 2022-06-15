package com.ngen.cosys.application.controller;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.json.simple.parser.ParseException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

import com.ngen.cosys.annotation.NgenCosysAppInfraAnnotation;
import com.ngen.cosys.events.payload.EMailEvent;
import com.ngen.cosys.service.util.enums.ReportFormat;
import com.ngen.cosys.service.util.model.ReportMailPayload;

@NgenCosysAppInfraAnnotation
public class DummyBDTonnageSummaryReports {
	
	

	@PostMapping(value = "/monthlyTonnageSummary", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> generateBDTonnageSummaryReportsMonthly() throws ParseException {
		
		Calendar aCalendar = Calendar.getInstance();
	      aCalendar.set(Calendar.DATE, 1);
	      aCalendar.add(Calendar.DAY_OF_MONTH, -1);

	      LocalDateTime lastDayOfMonth = toLocalDateTime(aCalendar);  

	      aCalendar.set(Calendar.DATE, 1);

	      LocalDateTime firstDayOfMonth = toLocalDateTime(aCalendar);     
	      
	      DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(firstDayOfMonth);
	      
	      Map<String, Object> reportParams = new HashMap<>();	     

	      reportParams.put("fromdate", firstDayOfMonth);
	      reportParams.put("todate", lastDayOfMonth);
	      
	      List<ReportMailPayload> reportPayload = new ArrayList<>();
	      ReportMailPayload reportMailPayload = new ReportMailPayload();
	      reportMailPayload.setReportName("Break Down Tonnage Summary Report");
	      reportMailPayload.setReportFormat(ReportFormat.PDF);
	      reportMailPayload.setReportParams(reportParams);

	      EMailEvent emailEvent = new EMailEvent();
	      emailEvent.setMailToAddress(new String[] { "santosh.lakshmanan@niit-tech.com" });
	      emailEvent.setNotifyAddress("santosh.lakshmanan@niit-tech.com");
	      emailEvent.setMailSubject("Monthly Tonnage Report");
	      emailEvent.setMailBody("Please Find the attached Tonnage Monthly Summary Report");
	      emailEvent.setTemplate(null);
	      
	      reportPayload.add(reportMailPayload);
	      //reportMailService.sendReport(reportPayload, emailEvent);	      
		return null;
	}
	
	public static LocalDateTime toLocalDateTime(Calendar calendar) {
	      if (calendar == null) {
	         return null;
	      }
	      TimeZone tz = calendar.getTimeZone();
	      ZoneId zid = tz == null ? ZoneId.systemDefault() : tz.toZoneId();
	      return LocalDateTime.ofInstant(calendar.toInstant(), zid);
	   }
	
	@PostMapping(value = "/dailyTonnageSummary", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> generateBDTonnageSummaryReportsDaily() throws ParseException {
		Calendar aCalendar = Calendar.getInstance();
		LocalDateTime currentDate = toLocalDateTime(aCalendar); 
		 Map<String, Object> reportParams = new HashMap<>();
	      reportParams.put("fromdate", currentDate);
	      
	      List<ReportMailPayload> reportPayload = new ArrayList<>();
	      ReportMailPayload reportMailPayload = new ReportMailPayload();
	      reportMailPayload.setReportName("Break Down Tonnage Summary Report");
	      reportMailPayload.setReportFormat(ReportFormat.PDF);
	      reportMailPayload.setReportParams(reportParams);

	      EMailEvent emailEvent = new EMailEvent();
	      emailEvent.setMailToAddress(new String[] { "santosh.lakshmanan@niit-tech.com" });
	      emailEvent.setNotifyAddress("santosh.lakshmanan@niit-tech.com");
	      emailEvent.setMailSubject("Daily Tonnage Report");
	      emailEvent.setMailBody("Please Find the attached Tonnage Daily Summary Report");
	      emailEvent.setTemplate(null);
	      
	      reportPayload.add(reportMailPayload);
	      //reportMailService.sendReport(reportPayload, emailEvent);	      
		return null;		
	}
}
