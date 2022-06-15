/**
 * {@link CiQReportBuilder}
 * 
 * @copyright SATS Singapore 2019-20
 * @author NIIT
 */
package com.ngen.cosys.ciq.report.builder;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import com.ngen.cosys.ciq.report.common.CiQReportConstants;
import com.ngen.cosys.ciq.report.common.ReportFrequency;
import com.ngen.cosys.ciq.report.model.CiQReportMailPayload;
import com.ngen.cosys.ciq.report.model.NotificationSchedule;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.multi.tenancy.enums.TenantZone;
import com.ngen.cosys.multitenancy.context.TenantContext;
import com.ngen.cosys.multitenancy.timezone.support.TenantTimeZoneUtility;
import com.ngen.cosys.report.mail.service.ReportMailService;
import com.ngen.cosys.service.util.api.ServiceUtil;
import com.ngen.cosys.service.util.enums.PrinterType;
import com.ngen.cosys.service.util.enums.ReportFormat;
import com.ngen.cosys.service.util.enums.ReportRequestType;
import com.ngen.cosys.service.util.model.ReportRequest;
import com.ngen.cosys.timezone.util.TenantZoneTime;

/**
 * CiQ Report Builder for report generation
 * 
 * @author NIIT Technologies Ltd
 */
@Component
public class CiQReportBuilder {

   private static final Logger LOGGER = LoggerFactory.getLogger(CiQReportBuilder.class);

   @Autowired
   ReportMailService reportMailService;

   /**
    * Generate Report
    * 
    * @param reportSchedule
    * @return
    * @throws CustomException
    */
   private static final String TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
   private static final String FILE_NAME_TIME_FORMAT = "yyyy-MM-dd";
   private static final String YEAR_MONTH = "yyyy-MM";
   

   public CiQReportMailPayload generate(NotificationSchedule reportSchedule) throws CustomException {
      LOGGER.info("CiQ Report Builder :: Generate - {}");
      CiQReportMailPayload reportMailPayload = getReportMailPayload(reportSchedule);
      String reportURL = reportMailService.getReportServiceURL();
      ReportRequest report = getReportRequest(reportMailPayload);
      ResponseEntity<Object> response = ServiceUtil.routeJSONResponse(report, reportURL);
      reportMailPayload.setBase64Content(getBase64ReportContent(response));
      LOGGER.warn("CIQ Exception Report Params  --"+reportMailPayload.getReportParams().get("fromdate")+","+reportMailPayload.getReportParams().get("todate")+","+reportMailPayload.getReportParams().get("messagetype")+","+reportMailPayload.getReportParams().get("carrier"));
      LOGGER.warn(reportSchedule.getMessageType() + "CIQ Exception Report Data"+ reportMailPayload.getBase64Content());
      return reportMailPayload;
   }

   /**
    * GET Report Mail Payload s
    * 
    * @param reportSchedule
    * @return
    */
   private CiQReportMailPayload getReportMailPayload(NotificationSchedule reportSchedule) {
      CiQReportMailPayload reportMailPayload = new CiQReportMailPayload();
      
      Map<String, Object> reportParams = new HashMap<>();
      reportParams.put("messagetype", reportSchedule.getMessageType());
      reportParams.put("carrier", reportSchedule.getCarrierCode());
      if(!StringUtils.isEmpty(reportSchedule.getCarrierCode())) {    	  
    	  reportParams.put("carrierflag", 1);
      }else {
    	  reportParams.put("carrierflag", 0);
      }
      // For FSU reports
      reportParams.put("fromdate", reportSchedule.getFromDate());
      reportParams.put("todate", reportSchedule.getToDate());
      
      // For POI reports
      reportParams.put("fromDate", reportSchedule.getFromDate());
      reportParams.put("toDate", reportSchedule.getToDate());
  
      setReportDataGeneratonRange(reportSchedule, reportParams);

      switch (reportSchedule.getReportName()) {

      case CiQReportConstants.IMPORT_EXPORT:
         setReportPayload(null, CiQReportConstants.IMPORT_EXPORT_SUBJECT, CiQReportConstants.IMPORT_EXPORT_REPORT,
               CiQReportConstants.IMPORT_EXPORT_REPORT_NAME, CiQReportConstants.XLS, reportParams, reportMailPayload);
         break;

      case CiQReportConstants.IMPORT_EXPORT_WORKING:
         setReportPayload(null, CiQReportConstants.IMPORT_EXPORT_WORKING_SUBJECT,
               CiQReportConstants.IMPORT_EXPORT_WORKING_REPORT, CiQReportConstants.IMPORT_EXPORT_WORKING_REPORT_NAME,
               CiQReportConstants.XLSX, reportParams, reportMailPayload);
         break;

      case CiQReportConstants.IMPORT_EXPORT_REFERENCE_WORKING_LIST:
         setReportPayload(null, CiQReportConstants.IMPORT_EXPORT_REFERENCE_WORKING_LIST_SUBJECT,
               CiQReportConstants.IMPORT_EXPORT_REFERENCE_WORKING_LIST_REPORT,
               CiQReportConstants.IMPORT_EXPORT_REFERENCE_WORKING_LIST_REPORT_NAME, CiQReportConstants.XLSX,
               reportParams, reportMailPayload);
         break;

      case CiQReportConstants.IMPORT_EXPORT_REFERENCE:
         setReportPayload(null, CiQReportConstants.IMPORT_EXPORT_REFERENCE_SUBJECT,
               CiQReportConstants.IMPORT_EXPORT_REFERENCE_REPORT,
               CiQReportConstants.IMPORT_EXPORT_REFERENCE_REPORT_NAME, CiQReportConstants.XLS, reportParams,
               reportMailPayload);
         break;

      case CiQReportConstants.CIQ_MESSAGE_TEMPLATE:
         setReportPayload(null, CiQReportConstants.CIQ_MESSAGE_TEMPLATE_SUBJECT,
               CiQReportConstants.CIQ_MESSAGE_TEMPLATE_REPORT, CiQReportConstants.CIQ_MESSAGE_TEMPLATE_REPORT_NAME,
               CiQReportConstants.XLS, reportParams, reportMailPayload);
         break;

      case CiQReportConstants.TRANSFER_TRANSIT_WORKING:
         setReportPayload(null, CiQReportConstants.TRANSFER_TRANSIT_WORKING_SUBJECT,
               CiQReportConstants.TRANSFER_TRANSIT_WORKING_REPORT,
               CiQReportConstants.TRANSFER_TRANSIT_WORKING_REPORT_NAME, CiQReportConstants.XLSX, reportParams,
               reportMailPayload);
         break;

      case CiQReportConstants.TRANSFER_TRANSIT:
         setReportPayload(null, CiQReportConstants.TRANSFER_TRANSIT_SUBJECT, CiQReportConstants.TRANSFER_TRANSIT_REPORT,
               CiQReportConstants.TRANSFER_TRANSIT_REPORT_NAME, CiQReportConstants.XLS, reportParams,
               reportMailPayload);
         break;

      case CiQReportConstants.TRANSFER_TRANSIT_REFERENCE:
         setReportPayload(null, CiQReportConstants.TRANSFER_TRANSIT_REFERENCE_SUBJECT,
               CiQReportConstants.TRANSFER_TRANSIT_REFERENCE_REPORT,
               CiQReportConstants.TRANSFER_TRANSIT_REFERENCE_REPORT_NAME, CiQReportConstants.XLS, reportParams,
               reportMailPayload);
         break;

      case CiQReportConstants.TRANSFER_TRANSIT_REFERENCE_WORKING_LIST:
         setReportPayload(null, CiQReportConstants.TRANSFER_TRANSIT_REFERENCE_WORKING_LIST_SUBJECT,
               CiQReportConstants.TRANSFER_TRANSIT_REFERENCE_WORKING_LIST_REPORT,
               CiQReportConstants.TRANSFER_TRANSIT_REFERENCE_WORKING_LIST_REPORT_NAME, CiQReportConstants.XLSX,
               reportParams, reportMailPayload);
         break;

      case CiQReportConstants.CIQ_FSU_EXCEPTION_REPORT:
         setFSUMessageReportNames(CiQReportConstants.CIQ_FSU_EXCEPTION_REPORT, reportSchedule, reportMailPayload,
               reportParams);
         break;

      case CiQReportConstants.CIQ_FSU_MESSAGING_REPORT:
         setFSUMessageReportNames(CiQReportConstants.CIQ_FSU_MESSAGING_REPORT, reportSchedule, reportMailPayload,
               reportParams);
         break;

      default:
         break;
      }
      reportMailPayload.setReportParams(reportParams);
      return reportMailPayload;
   }

   private void setFSUMessageReportNames(String reportName, NotificationSchedule schedule,
         CiQReportMailPayload reportMailPayload, Map<String, Object> reportParams) {

      switch (schedule.getMessageType()) {
      case CiQReportConstants.RCS:
         if (reportName.equalsIgnoreCase(CiQReportConstants.CIQ_FSU_EXCEPTION_REPORT)) {
            setReportPayload(schedule.getMessageType(), reportName, CiQReportConstants.FSU_EXCEPTION_REPORT_RCS,
                  "FSU-Exception-" + schedule.getMessageType() + CiQReportConstants.MESSAGE + schedule.getCarrierCode(),
                  CiQReportConstants.XLS, reportParams, reportMailPayload);
         } else {
            setReportPayload(schedule.getMessageType(), reportName, CiQReportConstants.FSU_MESSAGING_REPORT_RCS,
                  "FSU-Messaging-" + schedule.getMessageType() + CiQReportConstants.MESSAGE + schedule.getCarrierCode(),
                  CiQReportConstants.XLS, reportParams, reportMailPayload);
         }
         break;
      case CiQReportConstants.NFD:
         if (reportName.equalsIgnoreCase(CiQReportConstants.CIQ_FSU_EXCEPTION_REPORT)) {
            setReportPayload(schedule.getMessageType(), reportName, CiQReportConstants.FSU_EXCEPTION_REPORT_RCF_OR_NFD,
                  "FSU-Exception-" + schedule.getMessageType() + CiQReportConstants.MESSAGE + schedule.getCarrierCode(),
                  CiQReportConstants.XLS, reportParams, reportMailPayload);
         } else {
            setReportPayload(schedule.getMessageType(), reportName, CiQReportConstants.FSU_MESSAGING_REPORT_RCF_OR_NFD,
                  "FSU-Messaging-" + schedule.getMessageType() + CiQReportConstants.MESSAGE + schedule.getCarrierCode(),
                  CiQReportConstants.XLS, reportParams, reportMailPayload);
         }
         break;
      case CiQReportConstants.RCF:
         if (reportName.equalsIgnoreCase(CiQReportConstants.CIQ_FSU_EXCEPTION_REPORT)) {
            setReportPayload(schedule.getMessageType(), reportName, CiQReportConstants.FSU_EXCEPTION_REPORT_RCF_OR_NFD,
                  "FSU-Exception-" + schedule.getMessageType() + CiQReportConstants.MESSAGE + schedule.getCarrierCode(),
                  CiQReportConstants.XLS, reportParams, reportMailPayload);
         } else {
            setReportPayload(schedule.getMessageType(), reportName, CiQReportConstants.FSU_MESSAGING_REPORT_RCF_OR_NFD,
                  "FSU-Messaging-" + schedule.getMessageType() + CiQReportConstants.MESSAGE + schedule.getCarrierCode(),
                  CiQReportConstants.XLS, reportParams, reportMailPayload);
         }
         break;
      case CiQReportConstants.AWD:
         if (reportName.equalsIgnoreCase(CiQReportConstants.CIQ_FSU_EXCEPTION_REPORT)) {
            setReportPayload(schedule.getMessageType(), reportName, CiQReportConstants.FSU_EXCEPTION_REPORT_AWD,
                  "FSU-Exception-" + schedule.getMessageType() + CiQReportConstants.MESSAGE + schedule.getCarrierCode(),
                  CiQReportConstants.XLS, reportParams, reportMailPayload);
         } else {
            setReportPayload(schedule.getMessageType(), reportName, CiQReportConstants.FSU_MESSAGING_REPORT_AWD,
                  "FSU-Messaging-" + schedule.getMessageType() + CiQReportConstants.MESSAGE + schedule.getCarrierCode(),
                  CiQReportConstants.XLS, reportParams, reportMailPayload);
         }
         break;
      case CiQReportConstants.DEP:
         if (reportName.equalsIgnoreCase(CiQReportConstants.CIQ_FSU_EXCEPTION_REPORT)) {
            setReportPayload(schedule.getMessageType(), reportName, CiQReportConstants.FSU_EXCEPTION_REPORT_DEP,
                  "FSU-Exception-" + schedule.getMessageType() + CiQReportConstants.MESSAGE + schedule.getCarrierCode(),
                  CiQReportConstants.XLS, reportParams, reportMailPayload);
         } else {
            setReportPayload(schedule.getMessageType(), reportName, CiQReportConstants.FSU_MESSAGING_REPORT_DEP,
                  "FSU-Messaging-" + schedule.getMessageType() + CiQReportConstants.MESSAGE + schedule.getCarrierCode(),
                  CiQReportConstants.XLS, reportParams, reportMailPayload);
         }
         break;

      case CiQReportConstants.DLV:
         if (reportName.equalsIgnoreCase(CiQReportConstants.CIQ_FSU_EXCEPTION_REPORT)) {
            setReportPayload(schedule.getMessageType(), reportName, CiQReportConstants.FSU_EXCEPTION_REPORT_DLV,
                  "FSU-Exception-" + schedule.getMessageType() + CiQReportConstants.MESSAGE + schedule.getCarrierCode(),
                  CiQReportConstants.XLS, reportParams, reportMailPayload);
         } else {
            setReportPayload(schedule.getMessageType(), reportName, CiQReportConstants.FSU_MESSAGING_REPORT_DLV,
                  "FSU-Messaging-" + schedule.getMessageType() + CiQReportConstants.MESSAGE + schedule.getCarrierCode(),
                  CiQReportConstants.XLS, reportParams, reportMailPayload);
         }
         break;
      case CiQReportConstants.TFD:
         if (reportName.equalsIgnoreCase(CiQReportConstants.CIQ_FSU_EXCEPTION_REPORT)) {
            setReportPayload(schedule.getMessageType(), reportName, CiQReportConstants.FSU_EXCEPTION_REPORT_TFD,
                  "FSU-Exception-" + schedule.getMessageType() + CiQReportConstants.MESSAGE + schedule.getCarrierCode(),
                  CiQReportConstants.XLS, reportParams, reportMailPayload);
         } else {
            setReportPayload(schedule.getMessageType(), reportName, CiQReportConstants.FSU_MESSAGING_REPORT_TFD,
                  "FSU-Messaging-" + schedule.getMessageType() + CiQReportConstants.MESSAGE + schedule.getCarrierCode(),
                  CiQReportConstants.XLS, reportParams, reportMailPayload);
         }
         break;
      case CiQReportConstants.RCT:
         if (reportName.equalsIgnoreCase(CiQReportConstants.CIQ_FSU_EXCEPTION_REPORT)) {
            setReportPayload(schedule.getMessageType(), reportName, CiQReportConstants.FSU_EXCEPTION_REPORT_RCT,
                  "FSU-Exception-" + schedule.getMessageType() + CiQReportConstants.MESSAGE + schedule.getCarrierCode(),
                  CiQReportConstants.XLS, reportParams, reportMailPayload);
         } else {
            setReportPayload(schedule.getMessageType(), reportName, CiQReportConstants.FSU_MESSAGING_REPORT_RCT,
                  "FSU-Messaging-" + schedule.getMessageType() + CiQReportConstants.MESSAGE + schedule.getCarrierCode(),
                  CiQReportConstants.XLS, reportParams, reportMailPayload);
         }
         break;

      default:
         break;

      }
      if (!ObjectUtils.isEmpty(schedule.getCarrierCode()) && !schedule.getCarrierCode().equalsIgnoreCase("ALL")) {
         reportParams.put(CiQReportConstants.CARRIER_EXISTS, CiQReportConstants.TRUE);
         reportParams.put(CiQReportConstants.CARRIER_CODE, schedule.getCarrierCode());

      } else {
         reportParams.put(CiQReportConstants.CARRIER_EXISTS, CiQReportConstants.FALSE);
         reportParams.put(CiQReportConstants.CARRIER_CODE, null);
      }

   }

   /**
    * @param response
    * @return
    */
   @SuppressWarnings({ "unchecked" })
   private String getBase64ReportContent(ResponseEntity<Object> response) {
      if (Objects.nonNull(response) && Objects.nonNull(response.getBody())) {
         Map<String, Object> reportResponse = (Map<String, Object>) response.getBody();
         if (!CollectionUtils.isEmpty(reportResponse)) {
            if (!ObjectUtils.isEmpty(reportResponse.get("data"))) {
               reportResponse = (Map<String, Object>) reportResponse.get("data");
               if (Objects.nonNull(reportResponse.get("reportData"))) {
                  String reportData = (String) reportResponse.get("reportData");
                  return reportData;
               }
            }
         }
      }
      return null;
   }

   /**
    * @param reportName
    * @param reportFormat
    * @param reportParams
    * @return
    */
   private ReportRequest getReportRequest(CiQReportMailPayload mailPayload) {
      //
      ReportRequest report = new ReportRequest();
      report.setReportName(mailPayload.getReportName());
      report.setRequestType(ReportRequestType.EMAIL);
      report.setPrinterType(PrinterType.LASER);
      report.setFormat(mailPayload.getReportFormat());
      report.setParameters(mailPayload.getReportParams());
      //
      return report;
   }

   private void setReportDataGeneratonRange(NotificationSchedule reportSchedule, Map<String, Object> reportParams) {
	   LocalDateTime currentUTCTime = TenantZoneTime.getZoneDateTime(LocalDateTime.now(),TenantContext.get().getTenantId());
      switch (reportSchedule.getReportFrequency()) {
      case ReportFrequency.DAILY:
         reportParams.put(CiQReportConstants.FROM,
               LocalDateTime.of(currentUTCTime.toLocalDate().minusDays(1), reportSchedule.getConfiguredTime())
                     .format(DateTimeFormatter.ofPattern(TIME_FORMAT)));
         reportParams.put(CiQReportConstants.FROM_POI_REPORT,
               LocalDateTime.of(currentUTCTime.toLocalDate().minusDays(1), reportSchedule.getConfiguredTime())
                     .format(DateTimeFormatter.ofPattern(TIME_FORMAT)));
         if (reportSchedule.getConfiguredTime().compareTo(LocalTime.of(00, 00)) == 0) {
            reportParams.put(CiQReportConstants.TO,
                  LocalDateTime
                        .of(currentUTCTime.toLocalDate().minusDays(1),
                              reportSchedule.getConfiguredTime().minusMinutes(1))
                        .format(DateTimeFormatter.ofPattern(TIME_FORMAT)));
            reportParams.put(CiQReportConstants.TO_POI_REPORT,
                  LocalDateTime
                        .of(currentUTCTime.toLocalDate().minusDays(1),
                              reportSchedule.getConfiguredTime().minusMinutes(1))
                        .format(DateTimeFormatter.ofPattern(TIME_FORMAT)));
         } else {
            reportParams.put(CiQReportConstants.TO,
                  LocalDateTime.of(currentUTCTime.toLocalDate(), reportSchedule.getConfiguredTime().minusMinutes(1))
                        .format(DateTimeFormatter.ofPattern(TIME_FORMAT)));
            reportParams.put(CiQReportConstants.TO_POI_REPORT,
                  LocalDateTime.of(currentUTCTime.toLocalDate(), reportSchedule.getConfiguredTime().minusMinutes(1))
                        .format(DateTimeFormatter.ofPattern(TIME_FORMAT)));
         }
         break;
      case ReportFrequency.WEEKLY:
         reportParams.put(CiQReportConstants.FROM,
               LocalDateTime.of(currentUTCTime.toLocalDate().minusDays(7), LocalTime.of(00, 00))
                     .format(DateTimeFormatter.ofPattern(TIME_FORMAT)));
         reportParams.put(CiQReportConstants.TO,
               LocalDateTime.of(currentUTCTime.toLocalDate().minusDays(1), LocalTime.of(23, 59))
                     .format(DateTimeFormatter.ofPattern(TIME_FORMAT)));
         reportParams.put(CiQReportConstants.FROM_POI_REPORT,
               LocalDateTime.of(currentUTCTime.toLocalDate().minusDays(7), LocalTime.of(00, 00))
                     .format(DateTimeFormatter.ofPattern(TIME_FORMAT)));
         reportParams.put(CiQReportConstants.TO_POI_REPORT,
               LocalDateTime.of(currentUTCTime.toLocalDate().minusDays(1), LocalTime.of(23, 59))
                     .format(DateTimeFormatter.ofPattern(TIME_FORMAT)));
         break;
      case ReportFrequency.MONTHLY:
         reportParams.put(CiQReportConstants.FROM,
               LocalDateTime.of(currentUTCTime.toLocalDate().minusMonths(1), LocalTime.of(00, 00))
                     .format(DateTimeFormatter.ofPattern(TIME_FORMAT)));
         reportParams.put(CiQReportConstants.TO,
               LocalDateTime.of(currentUTCTime.toLocalDate().minusDays(1), LocalTime.of(23, 59))
                     .format(DateTimeFormatter.ofPattern(TIME_FORMAT)));
         reportParams.put(CiQReportConstants.FROM_POI_REPORT,
               LocalDateTime.of(currentUTCTime.toLocalDate().minusMonths(1), LocalTime.of(00, 00))
                     .format(DateTimeFormatter.ofPattern(TIME_FORMAT)));
         reportParams.put(CiQReportConstants.TO_POI_REPORT,
               LocalDateTime.of(currentUTCTime.toLocalDate().minusDays(1), LocalTime.of(23, 59))
                     .format(DateTimeFormatter.ofPattern(TIME_FORMAT)));
         break;

      default:
         reportParams.put(CiQReportConstants.FROM,
               LocalDateTime.of(currentUTCTime.toLocalDate().minusDays(1), reportSchedule.getConfiguredTime())
                     .format(DateTimeFormatter.ofPattern(TIME_FORMAT)));
         reportParams.put(CiQReportConstants.TO,
               LocalDateTime.of(currentUTCTime.toLocalDate(), reportSchedule.getConfiguredTime())
                     .format(DateTimeFormatter.ofPattern(TIME_FORMAT)));
         reportParams.put(CiQReportConstants.FROM_POI_REPORT,
               LocalDateTime.of(currentUTCTime.toLocalDate().minusDays(1), reportSchedule.getConfiguredTime())
                     .format(DateTimeFormatter.ofPattern(TIME_FORMAT)));
         reportParams.put(CiQReportConstants.TO_POI_REPORT,
               LocalDateTime.of(currentUTCTime.toLocalDate(), reportSchedule.getConfiguredTime())
                     .format(DateTimeFormatter.ofPattern(TIME_FORMAT)));
         break;
      }
   }

   private void setReportPayload(String messageType, String mailSubject, String reportName, String fileName,
		   String reportFormat, Map<String, Object> reportParams, CiQReportMailPayload reportMailPayload) {
	  String dateString = LocalDateTime.now().format(DateTimeFormatter.ofPattern(YEAR_MONTH));
      if (!StringUtils.isEmpty(messageType)) {
         reportParams.put("messagetype", messageType);
      }
      reportMailPayload.setMailSubject(mailSubject);
      reportMailPayload.setReportName(reportName);
      if (!StringUtils.isEmpty(reportFormat) && reportFormat.equalsIgnoreCase(CiQReportConstants.XLS) && fileName.contains("%s")) {
    	  reportMailPayload.setFileName(String.format(fileName, dateString) + ReportFormat.XLS);
         reportMailPayload.setReportFormat(ReportFormat.XLS);
         reportParams.put(CiQReportConstants.WORKSHEET_NAME, String.format(fileName, dateString));
      } else if (!StringUtils.isEmpty(reportFormat) && reportFormat.equalsIgnoreCase(CiQReportConstants.XLS)) {
         reportMailPayload.setFileName(fileName + dateString + ReportFormat.XLS);
         reportMailPayload.setReportFormat(ReportFormat.XLS);
         reportParams.put(CiQReportConstants.WORKSHEET_NAME, fileName + dateString);
      } else if (!StringUtils.isEmpty(reportFormat) && reportFormat.equalsIgnoreCase(CiQReportConstants.XLSX)) {
         reportMailPayload.setFileName(fileName + dateString + ReportFormat.XLSX);
         reportMailPayload.setReportFormat(ReportFormat.XLSX);
      }

   }

}
