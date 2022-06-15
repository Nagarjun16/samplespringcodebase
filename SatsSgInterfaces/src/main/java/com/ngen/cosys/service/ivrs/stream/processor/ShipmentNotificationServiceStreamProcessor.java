/**
 * {@link ShipmentNotificationServiceStreamProcessor}
 * 
 * @copyright SATS Singapore 2019-20
 * @author NIIT
 */
package com.ngen.cosys.service.ivrs.stream.processor;

import java.io.IOException;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.ngen.cosys.events.enums.EventTypes;
import com.ngen.cosys.events.payload.ShipmentNotification;
import com.ngen.cosys.events.stream.business.processor.BaseBusinessEventStreamProcessor;
import com.ngen.cosys.events.stream.payload.SatsSgInterfacePayload;
import com.ngen.cosys.jsonhelper.ConvertJSONToObject;
import com.ngen.cosys.service.ivrs.constants.IVRSConstants;
import com.ngen.cosys.service.ivrs.service.IVRSInterfaceService;

/**
 * Shipment Notification Service Stream Processor
 * 
 * @author NIIT Technologies Ltd
 */
@Component(EventTypes.Names.SHIPMENT_NOTIFICATION_EVENT)
public class ShipmentNotificationServiceStreamProcessor implements BaseBusinessEventStreamProcessor {

   private static final Logger LOGGER = LoggerFactory.getLogger(ShipmentNotificationServiceStreamProcessor.class);
   
   @Autowired
   ConvertJSONToObject convertJSONToObject;
   
   @Autowired
   IVRSInterfaceService ivrsInterfaceService;
   
   /**
    * @see com.ngen.cosys.events.stream.business.processor.BaseBusinessEventStreamProcessor#process(org.springframework.messaging.Message)
    */
   @Override
   public void process(Message<?> payload) throws JsonGenerationException, JsonMappingException, IOException {
      LOGGER.info("Shipment Notification Service Stream Processor :: Payload Received - {}, Headers Information : {} ",
            payload.getPayload(), payload.getHeaders());
      SatsSgInterfacePayload payloadObject = (SatsSgInterfacePayload) payload.getPayload();
      // Convert that payload to business payload
      ShipmentNotification shipmentNotification = (ShipmentNotification) convertJSONToObject
            .convertMapToObject(payloadObject.getPayload(), ShipmentNotification.class);
      try {
         if (Objects.nonNull(shipmentNotification)) {
            String notificationType = shipmentNotification.getNotificationType();
            if (IVRSConstants.SYSTEM_IVRS.equalsIgnoreCase(notificationType)) {
               ivrsInterfaceService.notifyArrivalOfShipment(shipmentNotification);
            } else if (IVRSConstants.SYSTEM_FAX.equalsIgnoreCase(notificationType)) {
               ivrsInterfaceService.sendAirWayBillFaxCopy(shipmentNotification);
            } else {
               LOGGER.info("Shipment Notification Service Stream Process :: Received Notification Type - {}",
                     notificationType);
            }
         } else {
            LOGGER.info("Shipment Notification Service Stream Processor :: Payload received EMPTY - {}");
         }
      } catch (Exception ex) {
         LOGGER.error("Shipment Notification Service Stream Processor :: Exception occurred - {}", ex);
      }
   }
   
}
