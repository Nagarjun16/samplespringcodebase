/**
 * Service implementation component which has business method for sending
 * notification
 */
package com.ngen.cosys.application.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.ngen.cosys.application.dao.ImportArrivalNotificationDAO;
import com.ngen.cosys.application.model.ImportArrivalNotificationModel;
import com.ngen.cosys.email.processor.MailProcessor;
import com.ngen.cosys.events.payload.EMailEvent;
import com.ngen.cosys.events.payload.EMailEvent.MessageReferenceDetail;
import com.ngen.cosys.events.template.model.TemplateBO;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.multitenancy.feature.model.ApplicationFeatures;
import com.ngen.cosys.multitenancy.feature.support.FeatureUtility;

@Service
@Transactional
public class ImportArrivalNotificationServiceImpl implements ImportArrivalNotificationService {

   private static final String TD2 = " </td>";

   private static final String TD = "<td> ";

   private static final Logger LOGGER = LoggerFactory.getLogger(ImportArrivalNotificationServiceImpl.class);

   @Autowired
   private ImportArrivalNotificationDAO dao;

   @Autowired
   @Qualifier("SatsSgBatchesMailProcessor")
   private MailProcessor mailProcessor;

   @Override
   public void sendNotification() throws CustomException {
      // 1. Get all general/EAP/EAW shipments grouped by Flight/SHC category
      List<ImportArrivalNotificationModel> shipments = this.dao.get();

      if (!CollectionUtils.isEmpty(shipments)) {
         // 2. Get General shipments and send notification
         List<ImportArrivalNotificationModel> generalShipments = shipments.stream().filter(
               t -> (!"EAP".equalsIgnoreCase(t.getShipmentType()) && !"EAW".equalsIgnoreCase(t.getShipmentType())))
               .collect(Collectors.toList());
         this.sendGEN(generalShipments);

         // 3. Get EAP shipments and send notification
         List<ImportArrivalNotificationModel> eapShipments = shipments.stream()
               .filter(t -> ("EAP".equalsIgnoreCase(t.getShipmentType()))).collect(Collectors.toList());
         this.sendEAP(eapShipments);

         // 4. Get EAW shipments and send notification
         List<ImportArrivalNotificationModel> eawShipments = shipments.stream()
               .filter(t -> ("EAW".equalsIgnoreCase(t.getShipmentType()))).collect(Collectors.toList());
         this.sendEAW(eawShipments);
      }
   }

   /**
    * Method to send GEN email
    */
   private void sendGEN(List<ImportArrivalNotificationModel> shipments) {
      // Check for data existence
      if (!CollectionUtils.isEmpty(shipments)) {

         for (ImportArrivalNotificationModel customer : shipments) {

            // If only when email addresses are present for each customer
            if (!StringUtils.isEmpty(customer.getEmailAddresses())) {

               // Set the base email template data
               EMailEvent emailEvent = new EMailEvent();
               // Set the email to
               emailEvent.setMailToAddress(customer.getEmailAddresses().split(","));
               // No CC address
               emailEvent.setMailCC("");
               // Body of the message
               emailEvent.setMailBody("SHIPMENT ARRIVAL NOTICE - [DO NOT REPLY]");
               // Subject line of the message
               emailEvent.setMailSubject("SHIPMENT ARRIVAL NOTICE - [DO NOT REPLY]");

               // Email Template - Settings
               TemplateBO template = new TemplateBO();
               template.setTemplateName("GENERAL SHIPMENT ARRIVAL");

               try {
                  buildAndSendNotification(customer, emailEvent, template);
               } catch (CustomException e) {
                  LOGGER.error("Exception while fetching customer and their shipment list for GEN", e);
               }
            }
         }
      }
   }

   /**
    * Method which constructs the email content
    * 
    * @param customer
    * @param emailEvent
    * @param template
    * @param mapParameters
    * @param formatter
    * @throws CustomException
    */
   private void buildAndSendNotification(ImportArrivalNotificationModel customer, EMailEvent emailEvent,
         TemplateBO template) throws CustomException {

      Map<String, String> mapParameters = new HashMap<>();
      mapParameters.put("FlightKey", customer.getFlightKey());
      mapParameters.put("Consignee", customer.getCustomerName());
      DateTimeFormatter formatter = new DateTimeFormatterBuilder().parseCaseInsensitive().appendPattern("ddMMMyyyy")
            .toFormatter();
      mapParameters.put("ToDate", LocalDate.now().format(formatter).toUpperCase());

      // For each customer build notifications on shipments
      if (!CollectionUtils.isEmpty(customer.getShipments())) {
         //
         List<MessageReferenceDetail> referenceDetails = new ArrayList<>();

         StringBuilder sb = new StringBuilder();
         sb.append("<html>");
         sb.append("<head>");
         sb.append("<style>");
         sb.append("table {\r\n" + "  border-collapse: collapse;\r\n" + "  width: 100%;\r\n" + "}\r\n" + "\r\n"
               + "th, td {\r\n" + "  padding: 8px;\r\n" + "  text-align: left;\r\n"
               + "  border-bottom: 1px solid #ddd;\r\n" + "}");
         sb.append("</style>");
         sb.append("</head>");
         sb.append("<body>");
         sb.append("<div>");
         sb.append("<table>");
         sb.append("<thead>");
         sb.append("<th> AWB NO </th>");
         if (FeatureUtility.hasAnyFeatureEnabled(ApplicationFeatures.Imp.Bd.HAWBHandling.class)) {                    	 
        	 sb.append("<th> HAWB NO </th>");
         }
         sb.append("<th> ORG </th>");
         sb.append("<th> DES </th>");
         sb.append("<th> PCS/WGT </th>");
         if (FeatureUtility.hasAnyFeatureEnabled(ApplicationFeatures.Imp.Bd.HAWBHandling.class)) {                    	 
        	 sb.append("<th> HAWB PCS/WGT </th>");
         }
         sb.append("<th> TTL PCS/WGT </th>");
         sb.append("<th> Flight/Date </th>");
         sb.append("<th> Collection Terminal </th>");
         sb.append("<th> Consignee </th>");
         sb.append("</thead>");
         sb.append("<tbody>");
         // Iterate each shipment list for appending to email
         for (ImportArrivalNotificationModel shipment : customer.getShipments()) {
            //
            MessageReferenceDetail reference = new MessageReferenceDetail();
            reference.setMessageReferenceNumber(shipment.getShipmentNumber());
            reference.setMessageReferenceDate(shipment.getShipmentDate());
            referenceDetails.add(reference);
            //
            sb.append("<tr>");
            sb.append(TD + shipment.getShipmentNumber() + TD2);
            if (FeatureUtility.hasAnyFeatureEnabled(ApplicationFeatures.Imp.Bd.HAWBHandling.class)) { 
            	if(!StringUtils.isEmpty(shipment.getHouseNumber())) {            		
            		sb.append(TD + shipment.getHouseNumber() + TD2);
            	}else {
            		sb.append(TD + " " + TD2);
            	}
            }
            sb.append(TD + shipment.getOrigin() + TD2);
            sb.append(TD + shipment.getDestination() + TD2);
            sb.append(TD + shipment.getPieces() + "/" + shipment.getWeight() + TD2);
            if (FeatureUtility.hasAnyFeatureEnabled(ApplicationFeatures.Imp.Bd.HAWBHandling.class)) { 
            	if(!ObjectUtils.isEmpty(shipment.getHousePieces())) {            		
            		sb.append(TD + shipment.getHousePieces() + "/" + shipment.getHouseWeight() + TD2);
            	}else {
            		sb.append(TD + " " + TD2);
            	}
            }
            sb.append(TD + shipment.getShipmentPieces() + "/" + shipment.getShipmentWeight() + TD2);
            sb.append(
                  TD + shipment.getFlightKey() + "/" + shipment.getFlightDate().format(formatter).toUpperCase() + TD2);
            sb.append(TD + shipment.getTerminal() + TD2);
            sb.append(TD + shipment.getConsigneeName() + TD2);
            sb.append("</tr>");

            // Set the audit trail parameters
            shipment.setCreatedBy("BATCHJOB");
            shipment.setFlightId(shipment.getFlightId());
         }
         sb.append("</tbody>");
         sb.append("</table>");
         sb.append("</div>");
         sb.append("</body>");
         sb.append("</html>");
         template.setTemplateParams(mapParameters);
         template.setTemplateTable(sb.toString());
         emailEvent.setTemplate(template);
         emailEvent.setMessageReferenceDetails(referenceDetails);

         // Send email
         boolean mailSent = this.mailProcessor.sendEmail(emailEvent);

         // Update the NOA flag for shipments for which email event triggered
         if (mailSent) {
            this.dao.updateNOAForShipment(customer.getShipments());
         }
      }
   }

   /**
    * Method to send EAW
    */
   private void sendEAW(List<ImportArrivalNotificationModel> shipments) {
      // Check for data existence
      if (!CollectionUtils.isEmpty(shipments)) {

         for (ImportArrivalNotificationModel customer : shipments) {

            // If only when email addresses are present for each customer
            if (!StringUtils.isEmpty(customer.getEmailAddresses())) {
               // Set the base email template data
               EMailEvent emailEvent = new EMailEvent();
               // Set the email to
               emailEvent.setMailToAddress(customer.getEmailAddresses().split(","));
               // No CC address
               emailEvent.setMailCC("");
               // Body of the message
               emailEvent.setMailBody("SHIPMENT ARRIVAL NOTICE - E-AWB SHIPMENT - [DO NOT REPLY]");
               // Subject line of the message
               emailEvent.setMailSubject("SHIPMENT ARRIVAL NOTICE - E-AWB SHIPMENT - [DO NOT REPLY]");

               // Email Template - Settings
               TemplateBO template = new TemplateBO();
               template.setTemplateName("E-AWB SHIPMENT ARRIVAL NOTICE");

               try {
                  this.buildAndSendNotification(customer, emailEvent, template);
               } catch (CustomException e) {
                  LOGGER.error("Exception while fetching customer and their shipment list for EAW", e);
               }
            }
         }
      }
   }

   /**
    * Method to send EAP
    */
   private void sendEAP(List<ImportArrivalNotificationModel> shipments) {
      // Check for data existence
      if (!CollectionUtils.isEmpty(shipments)) {

         for (ImportArrivalNotificationModel customer : shipments) {

            // If only when email addresses are present for each customer
            if (!StringUtils.isEmpty(customer.getEmailAddresses())) {

               // Set the base email template data
               EMailEvent emailEvent = new EMailEvent();
               // Set the email to
               emailEvent.setMailToAddress(customer.getEmailAddresses().split(","));
               // No CC address
               emailEvent.setMailCC("");
               // Body of the message
               emailEvent.setMailBody("SHIPMENT ARRIVAL NOTICE - E-FREIGHT SHIPMENT - [DO NOT REPLY]");
               // Subject line of the message
               emailEvent.setMailSubject("SHIPMENT ARRIVAL NOTICE - E-FREIGHT SHIPMENT - [DO NOT REPLY]");
               // Email Template - Settings
               TemplateBO template = new TemplateBO();
               template.setTemplateName("E-FREIGHT SHIPMENT ARRIVAL NOTICE");

               try {
                  this.buildAndSendNotification(customer, emailEvent, template);
               } catch (CustomException e) {
                  LOGGER.error("Exception while fetching customer and their shipment list for EAP", e);
               }
            }
         }
      }
   }

}