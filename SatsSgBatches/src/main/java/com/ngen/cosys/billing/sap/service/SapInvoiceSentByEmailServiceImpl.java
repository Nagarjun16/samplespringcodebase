package com.ngen.cosys.billing.sap.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ngen.cosys.billing.sap.dao.SapInvoiceAndCreditNoteProcessDAO;
import com.ngen.cosys.billing.sap.model.InvoiceSentByEmail;
import com.ngen.cosys.events.payload.EMailEvent;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.report.mail.service.ReportMailService;
import com.ngen.cosys.service.util.enums.ReportFormat;
import com.ngen.cosys.service.util.model.ReportMailPayload;

@Service
public class SapInvoiceSentByEmailServiceImpl implements SapInvoiceSentByEmailService {

   @Autowired
   private SapInvoiceAndCreditNoteProcessDAO sapInvoiceAndCreditNoteProcessDAO;

   @Autowired
   private ReportMailService reportMailService;

   private static Logger logger = LoggerFactory.getLogger(SapInvoiceSentByEmailServiceImpl.class);

   @Override
   public void invoiceSentByEmail() throws CustomException {
      List<InvoiceSentByEmail> invoiceDetails = sapInvoiceAndCreditNoteProcessDAO.getInvoiceSentByEmail();

      for (InvoiceSentByEmail invoice : invoiceDetails) {
         printEreceipt(invoice);
      }

   }

   public void printEreceipt(InvoiceSentByEmail invoiceSentByEmail) throws CustomException {
      List<ReportMailPayload> reportPayload = new ArrayList<>();

      ReportMailPayload reportMailPayload = new ReportMailPayload();
      reportMailPayload.setReportName("TaxSupportingDocumentReport");
      reportMailPayload.setReportFormat(ReportFormat.PDF);

      Map<String, Object> reportParams = new HashMap<>();
      reportParams.put("InvoiceNo", invoiceSentByEmail.getFinSysInvoiceNumber());
      reportParams.put("CustId", invoiceSentByEmail.getCustomerId().toString());
      reportMailPayload.setReportParams(reportParams);

      reportPayload.add(reportMailPayload);
      // }

      List<String> emailIds = new ArrayList<>();

      emailIds = sapInvoiceAndCreditNoteProcessDAO.fetchEmail(invoiceSentByEmail);
      if (emailIds != null && !emailIds.isEmpty()) {
         String[] mailaddressArray = new String[emailIds.size()];

         EMailEvent emailEvent = new EMailEvent();
         emailEvent.setMailToAddress(emailIds.toArray(mailaddressArray));
         emailEvent.setMailBCC("Raghunandan.S@NIIT-Tech.com");
         emailEvent.setNotifyAddress(null);
         emailEvent.setMailSubject("Invoice - " + invoiceSentByEmail.getFinSysInvoiceNumber());
         emailEvent.setMailBody(" ");
         try {
            reportMailService.sendReport(reportPayload, emailEvent);
            sapInvoiceAndCreditNoteProcessDAO.updateESupportDocEmailSent(invoiceSentByEmail);
         } catch (Exception e) {
            logger.info("Failed due to " + e);
         }
      }
   }
}
