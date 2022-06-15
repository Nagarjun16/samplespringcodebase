package com.ngen.cosys.report.mail.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.ngen.cosys.events.payload.EMailEvent;
import com.ngen.cosys.events.payload.EMailEvent.AttachmentStream;
import com.ngen.cosys.events.producer.SendEmailEventProducer;
import com.ngen.cosys.scheduler.esb.connector.ConnectorService;
import com.ngen.cosys.service.util.api.ServiceUtil;
import com.ngen.cosys.service.util.constants.InterfaceSystem;
import com.ngen.cosys.service.util.enums.PrinterType;
import com.ngen.cosys.service.util.enums.ReportFormat;
import com.ngen.cosys.service.util.enums.ReportRequestType;
import com.ngen.cosys.service.util.model.ReportMailPayload;
import com.ngen.cosys.service.util.model.ReportRequest;

@Component
public class ReportMailService {

   @Value("${gui.protocol}")
   private String protocol;
   
   @Value("${gui.host}")
   private String hostName;
   
   @Value("${gui.port}")
   private String portNumber;
   
   @Value("${report.service-path}")
   private String reportPath;
   
   @Autowired
   SendEmailEventProducer producer;
   
   @Autowired
   ConnectorService connectorService;
   
   /**
    * @param reportMailPayload
    * @param emailEvent
    */
   public void sendReport(List<ReportMailPayload> reportMailPayload, EMailEvent emailEvent) {
      String reportURL = getReportServiceURL();
      if (!CollectionUtils.isEmpty(reportMailPayload)) {
         for (ReportMailPayload mailPayload : reportMailPayload) {
            String pdfContent = getReportPDFContent(mailPayload, reportURL);
            mailPayload.setPdfContent(pdfContent);
         }
         reportEmailService(emailEvent, reportMailPayload);
      }
   }
   
   /**
    * @param mailPayload
    * @param reportURL
    * @return
    */
   private String getReportPDFContent(ReportMailPayload mailPayload, String reportURL) {
      ReportRequest report = getReportRequest(mailPayload);
      // Temporary Testing for Excel Report generation causes Response payload (Message converters)
      //ResponseEntity<Object> response = ServiceUtil.route(report, reportURL);
      ResponseEntity<Object> response = ServiceUtil.routeJSONResponse(report, reportURL);
      return getReportPDFContent(response);
   }
   
   /**
    * @param response
    * @return
    */
   @SuppressWarnings("unchecked")
   private String getReportPDFContent(ResponseEntity<Object> response) {
      //
      if (Objects.nonNull(response) && Objects.nonNull(response.getBody())) {
         Map<String, Object> reportResponse = (Map<String, Object>) response.getBody();
         if (!CollectionUtils.isEmpty(reportResponse)) {
            Map<String, Object> reportResponse1 = (Map<String, Object>) reportResponse.get("data");
            String reportData = (String) reportResponse1.get("reportData");
            return reportData;
         }
      }
      //
      return null;
   }
   
   /**
    * @param reportName
    * @param reportFormat
    * @param reportParams
    * @return
    */
   private ReportRequest getReportRequest(ReportMailPayload mailPayload) {
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
   
   /**
    * @return
    */
   public String getReportServiceURL() {

      String reportURL = connectorService.getServiceURL(InterfaceSystem.REPORT);
      if (StringUtils.isEmpty(reportURL)) {
         StringBuilder path = new StringBuilder();
         path.append(protocol).append("://").append(hostName);
         path.append(":").append(portNumber).append(reportPath);
         reportURL = path.toString();
      }
      return reportURL;
   }
   
   /**
    * @param emailEvent
    * @param reportMailPayload
    */
   private void reportEmailService(EMailEvent emailEvent, List<ReportMailPayload> reportMailPayload) {
      getEMailPayload(emailEvent, reportMailPayload);
      producer.publish(emailEvent);
   }
   
   /**
    * @param emailEvent
    * @param reportName
    * @param formatType
    * @param reportData
    */
   private void getEMailPayload(EMailEvent emailEvent, List<ReportMailPayload> reportMailPayload) {
      emailEvent.setMailAttachments(getAttachments(reportMailPayload));
   }
   
   /**
    * @param reportMailPayload
    * @return
    */
   private Map<String, AttachmentStream> getAttachments(List<ReportMailPayload> reportMailPayload) {
      //
      Map<String, AttachmentStream> attachments = new HashMap<>();
      AttachmentStream attachment = null;
      for (ReportMailPayload mailPayload : reportMailPayload) {
         attachment = new AttachmentStream();
         String fileName = null;
         String contentType = null;
         String extension = null;
         if (Objects.equals(ReportFormat.PDF.format(), mailPayload.getReportFormat().format())) {
            contentType = MediaType.APPLICATION_PDF_VALUE;
            extension = "." + ReportFormat.PDF.format();
         } else if (Objects.equals(ReportFormat.XLS.format(), mailPayload.getReportFormat().format())) {
            contentType = "application/vnd.ms-excel";
            extension = "." + ReportFormat.XLS.format();
         } else if (Objects.equals(ReportFormat.CSV.format(), mailPayload.getReportFormat().format())) {
            contentType = "application/csv";
            extension = "." + ReportFormat.CSV.format();
         }
         // FileName
         if (!StringUtils.isEmpty(mailPayload.getFileName())) {
            fileName = mailPayload.getFileName();
         } else {
            fileName = mailPayload.getReportName();
         }
         attachment.setFileName(fileName + extension);
         attachment.setFileType(contentType);
         attachment.setFileData(mailPayload.getPdfContent());
         attachments.put(mailPayload.getReportName(), attachment);
      }
      //
      return attachments;
   }
   
}
