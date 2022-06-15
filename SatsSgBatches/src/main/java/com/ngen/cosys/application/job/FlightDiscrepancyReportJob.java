package com.ngen.cosys.application.job;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import com.ngen.cosys.ScnLyingList.Service.ScnLyingListService;
import com.ngen.cosys.events.payload.EMailEvent;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.multi.tenancy.enums.TenantZone;
import com.ngen.cosys.multitenancy.context.TenantContext;
import com.ngen.cosys.report.mail.service.ReportMailService;
import com.ngen.cosys.scheduler.job.AbstractCronJob;
import com.ngen.cosys.service.util.enums.ReportFormat;
import com.ngen.cosys.service.util.model.ReportMailPayload;
import com.ngen.cosys.timezone.util.TenantZoneTime;

public class FlightDiscrepancyReportJob extends AbstractCronJob{
	 @Autowired
	   ReportMailService  reportMailService;
	 @Autowired
	   ScnLyingListService service;
	 
	 final String DATE_FORMAT = "dd-MMM-yy";
	 final String reporttime ="ddMMMyyyy HHmm";
	 private static final String MAIL_BODY = "Attached File: Delay_Report";
	   private static final Logger logger = LoggerFactory.getLogger(DiscrepancyReportJob.class);
	   
	   @Override
	   protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
	      //method will take care of sending mail with report  
	      super.executeInternal(jobExecutionContext);
	
		   LocalDateTime yesterdayTime = null;
		      LocalDateTime todayTime = null;
		      
		      final String TIMEFORMAT = "yyyy-MM-dd HH:mm:ss";
		      final String MailSub = "dd-MMM-yyyy";
		      LocalDateTime currentUTCTime = LocalDateTime.now();
		      LocalDateTime currentZoneTime = TenantZoneTime.getZoneDateTime(currentUTCTime,TenantContext.get().getTenantId());
		  
		            yesterdayTime = LocalDateTime.of(currentZoneTime.toLocalDate().minusDays(1),LocalTime.of(00,00));//.ofTimeatTime(8, 20).minusDays(1);
		            todayTime = LocalDateTime.of(currentZoneTime.toLocalDate().minusDays(1),LocalTime.of(23, 59));
		            logger.info("yesterdaytime", yesterdayTime);
		            logger.info("todayTime", todayTime);
		            String fromDate = yesterdayTime.format(DateTimeFormatter.ofPattern(TIMEFORMAT));
		            String toDate = todayTime.plusHours(5).format(DateTimeFormatter.ofPattern(TIMEFORMAT));
		       
		            List<String> delaySQ_Import = null;
		            List<String> delayOAL_Import = null;
		            List<String> delaySQ_Export = null;
		            List<String> delayOAL_Export = null;
		            
		            try {
		            	delaySQ_Import  = service.getEmailAddressesDelaySQImport();
		               logger.info("emailAddresses", delaySQ_Import);
		               
		               delayOAL_Import  = service.getEmailAddressesDelayOALImport();
		               logger.info("emailAddresses", delayOAL_Import);
		               
		               delaySQ_Export  = service.getEmailAddressesDelaySQExport();
		               logger.info("emailAddresses", delaySQ_Export);
		               
		               delayOAL_Export  = service.getEmailAddressesDelayOALExport();
		               logger.info("emailAddresses", delayOAL_Export);
		               
		               // Delay Report SQ(IMPORT)
			            
			        	if (!CollectionUtils.isEmpty(delaySQ_Import)) {
			        		Map<String, Object> reportParams = new HashMap<>();
							reportParams.put("carriergroupcode", "SQ");
							reportParams.put("from", fromDate);
							reportParams.put("to", toDate);
							reportParams.put("reporttime",currentZoneTime.format(DateTimeFormatter.ofPattern(reporttime)).toUpperCase());
							
						    List<ReportMailPayload> reportPayload = new ArrayList<>();
				            ReportMailPayload reportMailPayload = new ReportMailPayload();
				            reportMailPayload.setReportName("delayInwardService_report");
				            reportMailPayload.setFileName(shiftFileNameFormat("DELAY_SQ_IMP_REP",currentZoneTime));
				            reportMailPayload.setReportFormat(ReportFormat.CSV);
				            reportMailPayload.setReportParams(reportParams);
				            logger.info("reportParams for DelaySQImport", reportParams);
				            EMailEvent emailEvent = new EMailEvent();
				         
				               emailEvent.setMailToAddress(delaySQ_Import.toArray(new String[delaySQ_Import.size()]));
				               emailEvent.setNotifyAddress(null);
				               emailEvent.setMailSubject("DELAY_SQ_IMP_REP_"  + currentZoneTime.toLocalDate().format(DateTimeFormatter.ofPattern(MailSub))+ "-" + "[DO NOT REPLY]");
				               emailEvent.setMailBody(MAIL_BODY);
				               reportPayload.add(reportMailPayload);
				               reportMailService.sendReport(reportPayload, emailEvent);
				           
				            
						}
			        	
			        	// Delay Report OAL(IMPORT)
			        	if (!CollectionUtils.isEmpty(delayOAL_Import)) {
			        		Map<String, Object> reportParams1 = new HashMap<>();
							reportParams1.put("carriergroupcode", "OAL");
							reportParams1.put("from", fromDate);
							reportParams1.put("to", toDate);
							reportParams1.put("reporttime",currentZoneTime.format(DateTimeFormatter.ofPattern(reporttime)).toUpperCase());
							
						    List<ReportMailPayload> reportPayload = new ArrayList<>();
				            ReportMailPayload reportMailPayload = new ReportMailPayload();
				            reportMailPayload.setReportName("delayInwardService_report");
				            reportMailPayload.setFileName(shiftFileNameFormat("DELAY_OAL_IMP_REP",currentZoneTime));
				            reportMailPayload.setReportFormat(ReportFormat.CSV);
				            reportMailPayload.setReportParams(reportParams1);
				            logger.info("reportParams for DelayOALiMPORT", reportParams1);
				            EMailEvent emailEvent1 = new EMailEvent();
				         
				               emailEvent1.setMailToAddress(delayOAL_Import.toArray(new String[delayOAL_Import.size()]));
				               emailEvent1.setNotifyAddress(null);
				               emailEvent1.setMailSubject("DELAY_OAL_IMP_REP_"  + currentZoneTime.toLocalDate().format(DateTimeFormatter.ofPattern(MailSub))+ "-" + "[DO NOT REPLY]");
				               emailEvent1.setMailBody(MAIL_BODY);
				               reportPayload.add(reportMailPayload);
				               reportMailService.sendReport(reportPayload, emailEvent1);
				           
			        	
						}
			        	
			        	// Delay Report SQ(EXPORT)
			        	
			        	if (!CollectionUtils.isEmpty(delaySQ_Export)) {
			        		Map<String, Object> reportParams2 = new HashMap<>();
							reportParams2.put("carriergroupcode", "SQ");
							reportParams2.put("from", fromDate);
							reportParams2.put("to", toDate);
							reportParams2.put("reporttime",currentZoneTime.format(DateTimeFormatter.ofPattern(reporttime)).toUpperCase());
							
						    List<ReportMailPayload> reportPayload = new ArrayList<>();
				            ReportMailPayload reportMailPayload = new ReportMailPayload();
				            reportMailPayload.setReportName("delayOutwardService_report");
				            reportMailPayload.setFileName(shiftFileNameFormat("DELAY_SQ_EXP_REP",currentZoneTime));
				            reportMailPayload.setReportFormat(ReportFormat.CSV);
				            reportMailPayload.setReportParams(reportParams2);
				            logger.info("reportParams for DelaySQEXPORT", reportParams2);
				            EMailEvent emailEvent2 = new EMailEvent();
				         
				               emailEvent2.setMailToAddress(delaySQ_Export.toArray(new String[delaySQ_Export.size()]));
				               emailEvent2.setNotifyAddress(null);
				               emailEvent2.setMailSubject("DELAY_SQ_EXP_REP_"  + currentZoneTime.toLocalDate().format(DateTimeFormatter.ofPattern(MailSub))+ "-" + "[DO NOT REPLY]");
				               emailEvent2.setMailBody(MAIL_BODY);
				               reportPayload.add(reportMailPayload);
				               reportMailService.sendReport(reportPayload, emailEvent2);
				           
				            
						}
			        	// Delay Report OAL(EXPORT)
			        	if (!CollectionUtils.isEmpty(delayOAL_Export)) {
			        		Map<String, Object> reportParams3 = new HashMap<>();
							reportParams3.put("carriergroupcode", "OAL");
							reportParams3.put("from", fromDate);
							reportParams3.put("to", toDate);
							reportParams3.put("reporttime",currentZoneTime.format(DateTimeFormatter.ofPattern(reporttime)).toUpperCase());
							
						    List<ReportMailPayload> reportPayload = new ArrayList<>();
				            ReportMailPayload reportMailPayload = new ReportMailPayload();
				            reportMailPayload.setReportName("delayOutwardService_report");
				            reportMailPayload.setFileName(shiftFileNameFormat("DELAY_OAL_EXP_REP",currentZoneTime));
				            reportMailPayload.setReportFormat(ReportFormat.CSV);
				            reportMailPayload.setReportParams(reportParams3);
				            logger.info("reportParams for DELAY_OAL_EXP_REP", reportParams3);
				            EMailEvent emailEvent3= new EMailEvent();
				         
				               emailEvent3.setMailToAddress(delayOAL_Export.toArray(new String[delayOAL_Export.size()]));
				               emailEvent3.setNotifyAddress(null);
				               emailEvent3.setMailSubject("DELAY_OAL_EXP_REP_"  + currentZoneTime.toLocalDate().format(DateTimeFormatter.ofPattern(MailSub))+ "-" + "[DO NOT REPLY]");
				               emailEvent3.setMailBody(MAIL_BODY);
				               reportPayload.add(reportMailPayload);
				               reportMailService.sendReport(reportPayload, emailEvent3);
				               }
				          
			        	
		               
   
		            } catch (CustomException e) {
		               e.printStackTrace();
		            
		            }
		        	
				}
		 
	   
		   private String dateFormat(LocalDateTime currentZoneTime) {
			      return currentZoneTime.format(DateTimeFormatter.ofPattern(DATE_FORMAT)).toUpperCase();
			   }
			   
		   private String shiftFileNameFormat(String reportName, LocalDateTime currentZoneTime) {
			      return reportName + "_" + dateFormat(currentZoneTime);
			   }
	      

}
