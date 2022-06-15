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

public class DiscrepancyReportJob extends AbstractCronJob {
	  @Autowired
	   ScnLyingListService service;
	   @Autowired
	   ReportMailService  reportMailService;
	   
	   final String DATE_FORMAT = "dd-MMM-yy";
	 
	   private static final String MAIL_BODY = "Attached File: Damage_Report";
	   private static final String MAIL_BODY1 = "Attached File: SVC_Report";
	   private static final Logger logger = LoggerFactory.getLogger(DiscrepancyReportJob.class);
	   final String reporttime ="ddMMMyyyy HHmm";
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
		         String toDate = todayTime.format(DateTimeFormatter.ofPattern(TIMEFORMAT));
		       
		            List<String> damageSQ_Import = null;
		            List<String> damageOAL_Import = null;
		            List<String> damageSQ_Export = null;
		            List<String> damageOAL_Export = null;
		            
		            List<String> svcSQ_Import = null;
		            List<String> svcOAL_Import = null;
		            List<String> svcSQ_Export = null;
		            List<String> svcOAL_Export = null;
		            try {
		            	damageSQ_Import  = service.getEmailAddressesDamageSQImport();
		               logger.info("emailAddresses", damageSQ_Import);
		               
		               damageOAL_Import  = service.getEmailAddressesDamageOALImport();
		               logger.info("emailAddresses", damageOAL_Import);
		               
		               damageSQ_Export  = service.getEmailAddressesDamageSQExport();
		               logger.info("emailAddresses", damageSQ_Export);
		               
		               damageOAL_Export  = service.getEmailAddressesDamageOALExport();
		               logger.info("emailAddresses", damageOAL_Export);
		               
     
		               svcSQ_Import  = service.getEmailAddressesSVCSQImport();
		               svcOAL_Import  = service.getEmailAddressesSVCOALImport();
		               svcSQ_Export  = service.getEmailAddressesSVCSQExport();
		               svcOAL_Export  = service.getEmailAddressesSVCOALExport();
		               
		               // Damage Report SQ(IMPORT)
			            
			        	if (!CollectionUtils.isEmpty(damageSQ_Import)) {
			        		Map<String, Object> reportParams = new HashMap<>();
							reportParams.put("carriergroupcode", "SQ");
							reportParams.put("from", fromDate);
							reportParams.put("to", toDate);
							reportParams.put("reporttime", currentZoneTime.format(DateTimeFormatter.ofPattern(reporttime)).toUpperCase());
							
						    List<ReportMailPayload> reportPayload = new ArrayList<>();
				            ReportMailPayload reportMailPayload = new ReportMailPayload();
				            reportMailPayload.setReportName("DAMG_IMP_REP_report");
				            reportMailPayload.setFileName(shiftFileNameFormat("DAMG_SQ_IMP_REP",currentZoneTime));
				            reportMailPayload.setReportFormat(ReportFormat.CSV);
				            reportMailPayload.setReportParams(reportParams);
				            logger.info("reportParams for DamageSQiMPORT", reportParams);
				            EMailEvent emailEvent = new EMailEvent();
				         
				               emailEvent.setMailToAddress(damageSQ_Import.toArray(new String[damageSQ_Import.size()]));
				               emailEvent.setNotifyAddress(null);
				               emailEvent.setMailSubject("DAMG_SQ_IMP_REP_"  + currentZoneTime.toLocalDate().format(DateTimeFormatter.ofPattern(MailSub))+ "-" + "[DO NOT REPLY]");
				               emailEvent.setMailBody(MAIL_BODY);
				               reportPayload.add(reportMailPayload);
				               reportMailService.sendReport(reportPayload, emailEvent);
				           
				            
						}
			        	
			        	// Damage Report OAL(IMPORT)
			        	if (!CollectionUtils.isEmpty(damageOAL_Import)) {
			        		Map<String, Object> reportParams1 = new HashMap<>();
							reportParams1.put("carriergroupcode", "OAL");
							reportParams1.put("from", fromDate);
							reportParams1.put("to", toDate);
							reportParams1.put("reporttime", currentZoneTime.format(DateTimeFormatter.ofPattern(reporttime)).toUpperCase());
						   
							List<ReportMailPayload> reportPayload = new ArrayList<>();
				            ReportMailPayload reportMailPayload = new ReportMailPayload();
				            reportMailPayload.setReportName("DAMG_IMP_REP_report");
				            reportMailPayload.setFileName(shiftFileNameFormat("DAMG_OAL_IMP_REP",currentZoneTime));
				            reportMailPayload.setReportFormat(ReportFormat.CSV);
				            reportMailPayload.setReportParams(reportParams1);
				            logger.info("reportParams for DamageOALiMPORT", reportParams1);
				            EMailEvent emailEvent1 = new EMailEvent();
				         
				               emailEvent1.setMailToAddress(damageOAL_Import.toArray(new String[damageOAL_Import.size()]));
				               emailEvent1.setNotifyAddress(null);
				               emailEvent1.setMailSubject("DAMG_OAL_IMP_REP_"  + currentZoneTime.toLocalDate().format(DateTimeFormatter.ofPattern(MailSub))+ "-" + "[DO NOT REPLY]");
				               emailEvent1.setMailBody(MAIL_BODY);
				               reportPayload.add(reportMailPayload);
				               reportMailService.sendReport(reportPayload, emailEvent1);
				           
			        	
						}
			        	
			        	// Damage Report SQ(EXPORT)
			        	
			        	if (!CollectionUtils.isEmpty(damageSQ_Export)) {
			        		Map<String, Object> reportParams2 = new HashMap<>();
							reportParams2.put("carriergroupcode", "SQ");
							reportParams2.put("from", fromDate);
							reportParams2.put("to", toDate);
							reportParams2.put("reporttime", currentZoneTime.format(DateTimeFormatter.ofPattern(reporttime)).toUpperCase());
							
						    List<ReportMailPayload> reportPayload = new ArrayList<>();
				            ReportMailPayload reportMailPayload = new ReportMailPayload();
				            reportMailPayload.setReportName("DAMG_EXP_REP_report");
				            reportMailPayload.setFileName(shiftFileNameFormat("DAMG_SQ_EXP_REP",currentZoneTime));
				            reportMailPayload.setReportFormat(ReportFormat.CSV);
				            reportMailPayload.setReportParams(reportParams2);
				            logger.info("reportParams for DamageSQEXPORT", reportParams2);
				            EMailEvent emailEvent2 = new EMailEvent();
				         
				               emailEvent2.setMailToAddress(damageSQ_Export.toArray(new String[damageSQ_Export.size()]));
				               emailEvent2.setNotifyAddress(null);
				               emailEvent2.setMailSubject("DAMG_SQ_EXP_REP_"  + currentZoneTime.toLocalDate().format(DateTimeFormatter.ofPattern(MailSub))+ "-" + "[DO NOT REPLY]");
				               emailEvent2.setMailBody(MAIL_BODY);
				               reportPayload.add(reportMailPayload);
				               reportMailService.sendReport(reportPayload, emailEvent2);
				           
				            
						}
			        	// Damage Report OAL(EXPORT)
			        	if (!CollectionUtils.isEmpty(damageOAL_Export)) {
			        		Map<String, Object> reportParams3 = new HashMap<>();
							reportParams3.put("carriergroupcode", "OAL");
							reportParams3.put("from", fromDate);
							reportParams3.put("to", toDate);
							reportParams3.put("reporttime", currentZoneTime.format(DateTimeFormatter.ofPattern(reporttime)).toUpperCase());
						    List<ReportMailPayload> reportPayload = new ArrayList<>();
				            ReportMailPayload reportMailPayload = new ReportMailPayload();
				            reportMailPayload.setReportName("DAMG_EXP_REP_report");
				            reportMailPayload.setFileName(shiftFileNameFormat("DAMG_OAL_EXP_REP",currentZoneTime));
				            reportMailPayload.setReportFormat(ReportFormat.CSV);
				            reportMailPayload.setReportParams(reportParams3);
				            logger.info("reportParams for DAMG_OAL_EXP_REP", reportParams3);
				            EMailEvent emailEvent3= new EMailEvent();
				         
				               emailEvent3.setMailToAddress(damageOAL_Export.toArray(new String[damageOAL_Export.size()]));
				               emailEvent3.setNotifyAddress(null);
				               emailEvent3.setMailSubject("DAMG_OAL_EXP_REP_"  + currentZoneTime.toLocalDate().format(DateTimeFormatter.ofPattern(MailSub))+ "-" + "[DO NOT REPLY]");
				               emailEvent3.setMailBody(MAIL_BODY);
				               reportPayload.add(reportMailPayload);
				               reportMailService.sendReport(reportPayload, emailEvent3);}
				          
			        	
	       // SVC Report SQ(IMPORT)
			            
			        	if (!CollectionUtils.isEmpty(svcSQ_Import)) {
			        		Map<String, Object> reportParams = new HashMap<>();
							reportParams.put("carriergroupcode", "SQ");
							reportParams.put("from", fromDate);
							reportParams.put("to", toDate);
							reportParams.put("reporttime", currentZoneTime.format(DateTimeFormatter.ofPattern(reporttime)).toUpperCase());
						    List<ReportMailPayload> reportPayload = new ArrayList<>();
				            ReportMailPayload reportMailPayload = new ReportMailPayload();
				            reportMailPayload.setReportName("svcInwardService_report");
				            reportMailPayload.setFileName(shiftFileNameFormat("SVC_SQ_IMP_REP",currentZoneTime));
				            reportMailPayload.setReportFormat(ReportFormat.CSV);
				            reportMailPayload.setReportParams(reportParams);
				            
				            EMailEvent emailEvent = new EMailEvent();
				         
				               emailEvent.setMailToAddress(svcSQ_Import.toArray(new String[svcSQ_Import.size()]));
				               emailEvent.setNotifyAddress(null);
				               emailEvent.setMailSubject("SVC_SQ_IMP_REP_"  + currentZoneTime.toLocalDate().format(DateTimeFormatter.ofPattern(MailSub))+ "-" + "[DO NOT REPLY]");
				               emailEvent.setMailBody(MAIL_BODY1);
				               reportPayload.add(reportMailPayload);
				               reportMailService.sendReport(reportPayload, emailEvent);
				           
				            
						}
			        	
			        	// svc Report OAL(IMPORT)
			        	if (!CollectionUtils.isEmpty(svcOAL_Import)) {
			        		Map<String, Object> reportParams1 = new HashMap<>();
							reportParams1.put("carriergroupcode", "OAL");
							reportParams1.put("from", fromDate);
							reportParams1.put("to", toDate);
							reportParams1.put("reporttime", currentZoneTime.format(DateTimeFormatter.ofPattern(reporttime)).toUpperCase());
						    List<ReportMailPayload> reportPayload = new ArrayList<>();
				            ReportMailPayload reportMailPayload = new ReportMailPayload();
				            reportMailPayload.setReportName("svcInwardService_report");
				            reportMailPayload.setFileName(shiftFileNameFormat("SVC_OAL_IMP_REP",currentZoneTime));
				            reportMailPayload.setReportFormat(ReportFormat.CSV);
				            reportMailPayload.setReportParams(reportParams1);
				     
				            EMailEvent emailEvent1 = new EMailEvent();
				         
				               emailEvent1.setMailToAddress(svcOAL_Import.toArray(new String[svcOAL_Import.size()]));
				               emailEvent1.setNotifyAddress(null);
				               emailEvent1.setMailSubject("SVC_OAL_IMP_REP_"  + currentZoneTime.toLocalDate().format(DateTimeFormatter.ofPattern(MailSub))+ "-" + "[DO NOT REPLY]");
				               emailEvent1.setMailBody(MAIL_BODY1);
				               reportPayload.add(reportMailPayload);
				               reportMailService.sendReport(reportPayload, emailEvent1);
				           
			        	
						}
			        	
			        	// SVC Report SQ(EXPORT)
			        	
			        	if (!CollectionUtils.isEmpty(svcSQ_Export)) {
			        		Map<String, Object> reportParams2 = new HashMap<>();
							reportParams2.put("carriergroupcode", "SQ");
							reportParams2.put("from", fromDate);
							reportParams2.put("to", toDate);
							reportParams2.put("reporttime", currentZoneTime.format(DateTimeFormatter.ofPattern(reporttime)).toUpperCase());
						    List<ReportMailPayload> reportPayload = new ArrayList<>();
				            ReportMailPayload reportMailPayload = new ReportMailPayload();
				            reportMailPayload.setReportName("svcOutwardService_report");
				            reportMailPayload.setFileName(shiftFileNameFormat("SVC_SQ_EXP_REP",currentZoneTime));
				            reportMailPayload.setReportFormat(ReportFormat.CSV);
				            reportMailPayload.setReportParams(reportParams2);
				            logger.info("reportParams for SVC_SQ_EXP_REP", reportParams2);
				            EMailEvent emailEvent2 = new EMailEvent();
				         
				               emailEvent2.setMailToAddress(svcSQ_Export.toArray(new String[svcSQ_Export.size()]));
				               emailEvent2.setNotifyAddress(null);
				               emailEvent2.setMailSubject("SVC_SQ_EXP_REP_"  + currentZoneTime.toLocalDate().format(DateTimeFormatter.ofPattern(MailSub))+ "-" + "[DO NOT REPLY]");
				               emailEvent2.setMailBody(MAIL_BODY1);
				               reportPayload.add(reportMailPayload);
				               reportMailService.sendReport(reportPayload, emailEvent2);
				           
				            
						}
			        	// svc Report OAL(EXPORT)
			        	if (!CollectionUtils.isEmpty(svcOAL_Export)) {
			        		Map<String, Object> reportParams3 = new HashMap<>();
							reportParams3.put("carriergroupcode", "OAL");
							reportParams3.put("from", fromDate);
							reportParams3.put("to", toDate);
							reportParams3.put("reporttime", currentZoneTime.format(DateTimeFormatter.ofPattern(reporttime)).toUpperCase());
						    List<ReportMailPayload> reportPayload = new ArrayList<>();
				            ReportMailPayload reportMailPayload = new ReportMailPayload();
				            reportMailPayload.setReportName("svcOutwardService_report");
				            reportMailPayload.setFileName(shiftFileNameFormat("SVC_OAL_EXP_REP",currentZoneTime));
				            reportMailPayload.setReportFormat(ReportFormat.CSV);
				            reportMailPayload.setReportParams(reportParams3);
				            logger.info("reportParams for SVC_OAL_EXP_REP", reportParams3);
				            EMailEvent emailEvent3= new EMailEvent();
				         
				               emailEvent3.setMailToAddress(svcOAL_Export.toArray(new String[svcOAL_Export.size()]));
				               emailEvent3.setNotifyAddress(null);
				               emailEvent3.setMailSubject("SVC_OAL_EXP_REP_"  + currentZoneTime.toLocalDate().format(DateTimeFormatter.ofPattern(MailSub))+ "-" + "[DO NOT REPLY]");
				               emailEvent3.setMailBody(MAIL_BODY1);
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
