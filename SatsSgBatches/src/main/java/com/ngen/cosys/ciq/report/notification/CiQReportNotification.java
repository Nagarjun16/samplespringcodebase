/**
 * {@link CiQReportNotification}
 * 
 * @copyright SATS Singapore 2019-20
 * @author NIIT
 */
package com.ngen.cosys.ciq.report.notification;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.ngen.cosys.ciq.report.common.CiQReportConstants;
import com.ngen.cosys.ciq.report.model.CiQReportMailPayload;
import com.ngen.cosys.ciq.report.model.NotificationSchedule;
import com.ngen.cosys.events.payload.EMailEvent;
import com.ngen.cosys.events.payload.EMailEvent.AttachmentStream;
import com.ngen.cosys.events.producer.SendEmailEventProducer;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.service.util.enums.ReportFormat;

/**
 * CiQ Report Email Notification
 * 
 * @author NIIT Technologies Ltd
 */
@Component
public class CiQReportNotification {

   private static final Logger LOGGER = LoggerFactory.getLogger(CiQReportNotification.class);

   @Autowired
   SendEmailEventProducer producer;

   /**
    * Send Email
    * 
    * @param reportMailPayload
    * @throws CustomException
    */
   public void sendEmail(CiQReportMailPayload reportMailPayload, NotificationSchedule reportSchedule)
         throws CustomException {
      LOGGER.info("CiQ Report Notification :: Send Email - {}");
      EMailEvent emailEvent = null;
      for (Iterator<Entry<String, Set<String>>> iterator = reportMailPayload.getNotificationEmails().entrySet()
            .iterator(); iterator.hasNext();) {
         Entry<String, Set<String>> entry = iterator.next();
         emailEvent = new EMailEvent();
         emailEvent.setMailSubject(getMailSubject(entry, reportMailPayload));
         emailEvent.setMailBody(getMailBodyContent(entry, reportMailPayload));
         emailEvent.setMailAttachments(getMailAttachments(reportMailPayload));
         if (!CollectionUtils.isEmpty(entry.getValue())) {
            emailEvent
                  .setMailToAddress(Arrays.copyOf(entry.getValue().toArray(), entry.getValue().size(), String[].class));
         }
         producer.publish(emailEvent);
      }
   }

   /**
    * @param entry
    * @return
    */
   private String getMailSubject(Entry<String, Set<String>> entry, CiQReportMailPayload reportSchedule) {
      // TODO:
      String mailSubject = null;
      if (Objects.equals(CiQReportConstants.PARTY_IATA, entry.getKey())) {
         mailSubject = reportSchedule.getMailSubject();
      } else if (Objects.equals(CiQReportConstants.PARTY_SATS, entry.getKey())) {
         mailSubject = reportSchedule.getMailSubject();
      }
      return mailSubject;
   }

   /**
    * @param entry
    * @return
    */
   private String getMailBodyContent(Entry<String, Set<String>> entry, CiQReportMailPayload reportSchedule) {
      // TODO:
      String bodyContent = null;
      if (Objects.equals(CiQReportConstants.PARTY_IATA, entry.getKey())) {
         bodyContent = reportSchedule.getMailSubject();
      } else if (Objects.equals(CiQReportConstants.PARTY_SATS, entry.getKey())) {
         bodyContent = reportSchedule.getMailSubject();
      }
      return bodyContent;
   }

   /**
    * GET Mail Attachments
    * 
    * @param reportMailPayload
    * @return
    */
   private Map<String, AttachmentStream> getMailAttachments(CiQReportMailPayload reportMailPayload) {
      Map<String, AttachmentStream> attachments = new HashMap<>();
      AttachmentStream attachment = new AttachmentStream();
      String fileName = null;
      String contentType = null;
      String extension = null;
      if (Objects.equals(ReportFormat.PDF.format(), reportMailPayload.getReportFormat().format())) {
         contentType = MediaType.APPLICATION_PDF_VALUE;
         extension = "." + ReportFormat.PDF.format();
      } else if (Objects.equals(ReportFormat.XLS.format(), reportMailPayload.getReportFormat().format())) {
         contentType = "application/vnd.ms-excel";
         extension = "." + ReportFormat.XLS.format();
      } else if (Objects.equals(ReportFormat.CSV.format(), reportMailPayload.getReportFormat().format())) {
         contentType = "application/csv";
         extension = "." + ReportFormat.CSV.format();
      } else if (Objects.equals(ReportFormat.XLSX.format(), reportMailPayload.getReportFormat().format())) {
         contentType = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
         extension = "." + ReportFormat.XLSX.format();
      }
      // FileName
      if (!StringUtils.isEmpty(reportMailPayload.getFileName())) {
         fileName = reportMailPayload.getFileName();
      } else {
         fileName = reportMailPayload.getReportName();
      }
      attachment.setFileName(fileName + extension);
      attachment.setFileType(contentType);
      attachment.setFileData(reportMailPayload.getBase64Content());
      attachments.put(reportMailPayload.getReportName(), attachment);
      return attachments;
   }

}
