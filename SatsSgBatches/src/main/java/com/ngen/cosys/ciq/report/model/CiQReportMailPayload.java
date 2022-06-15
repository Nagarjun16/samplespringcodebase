/**
 * {@link CiQReportMailPayload}
 * 
 * @copyright SATS Singapore 2019-20
 * @author NIIT
 */
package com.ngen.cosys.ciq.report.model;

import java.util.Map;
import java.util.Set;

import com.ngen.cosys.service.util.enums.ReportFormat;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * CiQ Report Mail Payload for download and Email notification
 * 
 * @author NIIT Technologies Ltd
 */
@Getter
@Setter
@NoArgsConstructor
public class CiQReportMailPayload extends CiQReport {

   /**
    * Default serialVersionUID
    */
   private static final long serialVersionUID = 1L;
   //
   private String fileName;
   private ReportFormat reportFormat;
   private Map<String, Object> reportParams;
   private String base64Content;
   // <Party, EmailList>
   private Map<String, Set<String>> notificationEmails;
   private String mailSubject;

}
