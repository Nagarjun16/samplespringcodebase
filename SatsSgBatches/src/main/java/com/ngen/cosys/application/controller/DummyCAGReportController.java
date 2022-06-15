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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

import com.ngen.cosys.annotation.NgenCosysAppInfraAnnotation;
import com.ngen.cosys.events.payload.EMailEvent;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.report.mail.service.ReportMailService;
// import com.ngen.cosys.report.mail.service.ReportMailService;
import com.ngen.cosys.service.util.enums.ReportFormat;
import com.ngen.cosys.service.util.model.ReportMailPayload;
import com.ngen.cosys.sqcsla.model.Emails;
import com.ngen.cosys.sqcsla.service.SqcSlaService;

@NgenCosysAppInfraAnnotation(path = "api/ics/report")
public class DummyCAGReportController {

   @Autowired
   private ReportMailService reportMailService;

   @Autowired
   SqcSlaService sqcSlaService;

   @PostMapping(value = "/generate-report", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
   public ResponseEntity<Object> generateReport() throws ParseException, CustomException {

      Calendar aCalendar = Calendar.getInstance();
      aCalendar.set(Calendar.DATE, 1);
      aCalendar.add(Calendar.DAY_OF_MONTH, -1);

      LocalDateTime lastDayOfMonth = toLocalDateTime(aCalendar);
      aCalendar.set(Calendar.DATE, 1);

      LocalDateTime firstDayOfMonth = toLocalDateTime(aCalendar);

      DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(firstDayOfMonth);
      Map<String, Object> reportParams = new HashMap<>();
      reportParams.put("fromdate", String.valueOf(firstDayOfMonth));
      reportParams.put("todate", String.valueOf(lastDayOfMonth));

      List<ReportMailPayload> reportPayload1 = new ArrayList<>();
      ReportMailPayload reportMailPayload1 = new ReportMailPayload();
      reportMailPayload1.setReportName("exportCagReportrptdesign");
      reportMailPayload1.setReportFormat(ReportFormat.PDF);
      reportMailPayload1.setReportParams(reportParams);
      reportPayload1.add(reportMailPayload1);

      List<ReportMailPayload> reportPayload2 = new ArrayList<>();
      ReportMailPayload reportMailPayload2 = new ReportMailPayload();
      reportMailPayload2.setReportName("cagsurvey_report");
      reportMailPayload2.setReportFormat(ReportFormat.PDF);
      reportMailPayload2.setReportParams(reportParams);
      reportPayload2.add(reportMailPayload2);

      List<Emails> sqcEmailGroup = sqcSlaService.getSqcGroupEmail();
      List<Emails> cagEmailGroup = sqcSlaService.getCagGroupEmail();

      EMailEvent emailEvent = new EMailEvent();
      List<String> emails = new ArrayList<>();
      sqcEmailGroup.stream().forEach(value -> emails.add(value.getEmailId()));
      emailEvent.setMailToAddress(emails.stream().toArray(String[]::new));
      // emailEvent.setNotifyAddress("prakhar.5.pandey@niit-tech.com");
      emailEvent.setMailSubject("SQC Report");
      emailEvent.setMailBody("SQC Report From "
            + String.valueOf(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(firstDayOfMonth)) + " to "
            + String.valueOf(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(lastDayOfMonth)));
      // emailEvent.setTemplate(null);

      reportMailService.sendReport(reportPayload1, emailEvent);

      EMailEvent emailEvent1 = new EMailEvent();
      List<String> emailsCag = new ArrayList<>();
      cagEmailGroup.stream().forEach(value -> emailsCag.add(value.getEmailId()));
      emailEvent1.setMailToAddress(emailsCag.stream().toArray(String[]::new));
      // emailEvent.setNotifyAddress("prakhar.5.pandey@niit-tech.com");
      emailEvent1.setMailSubject("CAG Report");
      emailEvent1.setMailBody("CAG Report From "
            + String.valueOf(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(firstDayOfMonth)) + " to "
            + String.valueOf(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(lastDayOfMonth)));
      // emailEvent.setTemplate(null);

      reportMailService.sendReport(reportPayload2, emailEvent1);
      return null;
   }

   private static LocalDateTime toLocalDateTime(Calendar calendar) {
      if (calendar == null) {
         return null;
      }
      TimeZone tz = calendar.getTimeZone();
      ZoneId zid = tz == null ? ZoneId.systemDefault() : tz.toZoneId();
      return LocalDateTime.ofInstant(calendar.toInstant(), zid);
   }

}
