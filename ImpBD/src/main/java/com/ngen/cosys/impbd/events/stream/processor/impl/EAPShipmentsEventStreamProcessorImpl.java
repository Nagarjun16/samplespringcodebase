/**
 * This is an event component which post processes EAP shipment for inbound
 * process
 */
package com.ngen.cosys.impbd.events.stream.processor.impl;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.ngen.cosys.events.payload.EMailEvent;
import com.ngen.cosys.events.payload.EMailEvent.MessageReferenceDetail;
import com.ngen.cosys.events.stream.business.processor.BaseBusinessEventStreamProcessor;
import com.ngen.cosys.events.stream.payload.ImpBdPayload;
import com.ngen.cosys.events.template.model.TemplateBO;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.impbd.events.enums.ImpBdEventTypes;
import com.ngen.cosys.impbd.events.payload.EAPShipmentsEvent;
import com.ngen.cosys.impbd.events.payload.InboundShipmentInfoModel;
import com.ngen.cosys.impbd.events.service.ImpBdEventService;
import com.ngen.cosys.impbd.service.mail.processor.MailProcessor;
import com.ngen.cosys.jsonhelper.ConvertJSONToObject;
import com.ngen.cosys.multitenancy.feature.model.ApplicationFeatures;
import com.ngen.cosys.multitenancy.feature.support.FeatureUtility;

@Component(ImpBdEventTypes.Names.EAP_SHIPMENTS_EVENT)
public class EAPShipmentsEventStreamProcessorImpl implements BaseBusinessEventStreamProcessor {

   private static final Logger LOGGER = LoggerFactory.getLogger(EAPShipmentsEventStreamProcessorImpl.class);

   @Autowired
   private ConvertJSONToObject convertJSONToObject;

   @Autowired
   private ImpBdEventService eventService;

   @Autowired
   @Qualifier("ImpBDMailProcessor")
   private MailProcessor mailProcessor;

   @Override
   public void process(Message<?> payload) throws JsonGenerationException, JsonMappingException, IOException {

      ImpBdPayload impBdPayload = (ImpBdPayload) payload.getPayload();

      // Concert JSON to actual event object
      EAPShipmentsEvent event = (EAPShipmentsEvent) convertJSONToObject.convertMapToObject(impBdPayload.getPayload(),
            EAPShipmentsEvent.class);

      // Get list of shipments whose SHC handling group belongs to EAP
      try {
         List<InboundShipmentInfoModel> customerShipmentList = this.eventService.getEAPShipments(event);

         // Check for data existence
         if (!CollectionUtils.isEmpty(customerShipmentList)) {

            for (InboundShipmentInfoModel customer : customerShipmentList) {

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

                  Map<String, String> mapParameters = new HashMap<>();
                  mapParameters.put("FlightKey", customer.getFlightKey());
                  mapParameters.put("Consignee", customer.getCustomerName());
                  DateTimeFormatter formatter = new DateTimeFormatterBuilder().parseCaseInsensitive()
                        .appendPattern("ddMMMyyyy").toFormatter();
                  mapParameters.put("ToDate", LocalDate.now().format(formatter).toString().toUpperCase());

                  // For each customer build notifications on shipments
                  if (!CollectionUtils.isEmpty(customer.getShipments())) {
                     //
                     List<MessageReferenceDetail> referenceDetails = new ArrayList<>();

                     StringBuilder sb = new StringBuilder();
                     sb.append("<html>");
                     sb.append("<head>");
                     sb.append("<style>");
                     sb.append("table {\r\n" + "  border-collapse: collapse;\r\n" + "  width: 100%;\r\n" + "}\r\n"
                           + "\r\n" + "th, td {\r\n" + "  padding: 8px;\r\n" + "  text-align: left;\r\n"
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
                    	 sb.append("<th>HAWB PCS/WGT </th>");
                     }
                     sb.append("<th> TTL PCS/WGT </th>");
                     sb.append("<th> Flight/Date </th>");
                     sb.append("<th> Collection Terminal </th>");
                     sb.append("<th> Consignee </th>");
                     sb.append("<th> DocumentPouchReceived </th>");
                     sb.append("</thead>");
                     sb.append("<tbody>");
                     // Iterate each shipment list for appending to email
                     for (InboundShipmentInfoModel shipment : customer.getShipments()) {

                        // Set the flight key and flight date
                        shipment.setFlightKey(event.getFlightKey());
                        shipment.setFlightDate(event.getFlightDate());
                        shipment.setEmailAddresses(customer.getEmailAddresses());
                        shipment.setLoggedInUser(event.getCreatedBy());
                        //
                        MessageReferenceDetail reference = new MessageReferenceDetail();
                        reference.setMessageReferenceNumber(shipment.getShipmentNumber());
                        reference.setMessageReferenceDate(shipment.getShipmentDate());
                        referenceDetails.add(reference);
                        //
                        sb.append("<tr>");
                        sb.append("<td> " + shipment.getShipmentNumber() + " </td>");
                        if (FeatureUtility.hasAnyFeatureEnabled(ApplicationFeatures.Imp.Bd.HAWBHandling.class)) { 
                        	if(!StringUtils.isEmpty(shipment.getHouseNumber())) {                        		
                        		sb.append("<td> " + shipment.getHouseNumber() + " </td>");
                        	}else {
                        		sb.append("<td> " + "" + " </td>");
                        	}
                        }
                        sb.append("<td> " + shipment.getOrigin() + " </td>");
                        sb.append("<td> " + shipment.getDestination() + " </td>");
                        sb.append("<td> " + shipment.getPieces() + "/" + shipment.getWeight() + " </td>");
                        if (FeatureUtility.hasAnyFeatureEnabled(ApplicationFeatures.Imp.Bd.HAWBHandling.class)) {  
                        	if(shipment.getHousePieces()!=null) {                        		
                        		sb.append(
                        				"<td> " + shipment.getHousePieces() + "/" + shipment.getHouseWeight() + " </td>");
                        	}else {
                        		sb.append("<td> " + "" + " </td>");
                        	}
                        }
                        sb.append(
                        		"<td> " + shipment.getShipmentPieces() + "/" + shipment.getShipmentWeight() + " </td>");
                        sb.append("<td> " + event.getFlightKey() + "/"
                              + event.getFlightDate().format(formatter).toString().toUpperCase() + " </td>");
                        sb.append("<td> " + shipment.getTerminal() + " </td>");
                        sb.append("<td> " + shipment.getConsigneeName() + " </td>");

                        // Set the document received to "Y"/"N". Either Original/Pouch should have been
                        // received for a shipment
                        if (shipment.getDocumentReceived()) {
                           sb.append("<td> Y </td>");
                        } else {
                           sb.append("<td> N </td>");
                        }

                        sb.append("</tr>");

                        // Set the audit trail parameters
                        shipment.setCreatedBy(event.getCreatedBy());
                        shipment.setFlightId(event.getFlightId());
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
                    	 
                        this.eventService.updateNOAForShipment(customer.getShipments());
                     }
                  }
               }
            }
         }

      } catch (CustomException customException) {
         LOGGER.error("Exception while fetching customer and their shipment list for EAP", customException);
      }
   }

}