/**
 * 
 * FAXEventServiceStreamProcessor.java
 * 
 * Copyright <PRE><IMG SRC = XX></IMG></PRE>
 *
 * Version      Date         Author      Reason
 * 1.0          22 JAN, 2019   NIIT      -
 */
package com.ngen.cosys.altea.fm.stream.processor;

import java.io.IOException;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.ngen.cosys.altea.fm.model.DCSFMUpdateCargoFigures;
import com.ngen.cosys.altea.fm.processor.AlteaFMProcessor;
import com.ngen.cosys.altea.fm.processor.AlteaFMSOAPMessageProcessor;
import com.ngen.cosys.altea.fm.service.AlteaFMService;
import com.ngen.cosys.altea.fm.validation.AlteaFMValidation;
import com.ngen.cosys.events.enums.EventTypes;
import com.ngen.cosys.events.payload.AlteaFMEvent;
import com.ngen.cosys.events.stream.business.processor.BaseBusinessEventStreamProcessor;
import com.ngen.cosys.events.stream.payload.SatsSgInterfacePayload;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.jsonhelper.ConvertJSONToObject;

/**
 * This Altea FM Event class used for Send DLS, NOTOC details to ALTEA FM WS
 * 
 * @author NIIT Technologies Ltd
 *
 */
@Component(EventTypes.Names.ALTEA_FM_EVENT)
public class AlteaFMStreamProcessor implements BaseBusinessEventStreamProcessor {

   private static final Logger LOGGER = LoggerFactory.getLogger(AlteaFMStreamProcessor.class);
   
   @Autowired
   private ConvertJSONToObject jsonParser;

   @Autowired
   private AlteaFMService alteaFMService;
   
   @Autowired
   private AlteaFMProcessor alteaFMProcessor;
   
   @Autowired
   private AlteaFMSOAPMessageProcessor alteaFMSOAPMessageProcessor;
   
   @Override
   public void process(Message<?> payload) throws JsonGenerationException, JsonMappingException, IOException {
      LOGGER.debug("Altea FM Event Stream Processor :: Payload Received from SCS :: {}, Headers Information : {} ",
            payload.getPayload(), payload.getHeaders());
      //
      SatsSgInterfacePayload payloadObject = (SatsSgInterfacePayload) payload.getPayload();
      // Convert that payload to business payload
      AlteaFMEvent alteaFMEvent = (AlteaFMEvent) jsonParser.convertMapToObject(payloadObject.getPayload(),
            AlteaFMEvent.class);
      //
      if (Objects.nonNull(alteaFMEvent)) {
         // Payload data preparation
         DCSFMUpdateCargoFigures dcsfmUpdateCargofigures = null;
         // Event Payload verification
         if (!AlteaFMValidation.isPayloadValid(alteaFMEvent)) {
            return;
         }
         try {
            // Message Setup Verification
            if (!alteaFMService.isMessageAddressingSetupValid(alteaFMEvent)) {
               LOGGER.warn(
                     "Altea FM Message Addresssing Setup is not Valid :: Flight Key : {}, Flight Date : {}, Event Source : {}",
                     alteaFMEvent.getFlightKey(), String.valueOf(alteaFMEvent.getFlightDate()),
                     alteaFMEvent.getEventSource());
               return;
            }
            int messageLimit = alteaFMService.getConfiguredPerFlightMessageCount(alteaFMEvent);
            int sentMessages = alteaFMService.getAlteaFMSENTMessageCountByFlight(alteaFMEvent);
            LOGGER.warn(
                  "Altea FM Message LIMIT Verification : Flight Key : {}, Flight Date : {}, Event Source : {}, Configured Message Limit : {}, SENT Messages : {}",
                  alteaFMEvent.getFlightKey(), String.valueOf(alteaFMEvent.getFlightDate()),
                  alteaFMEvent.getEventSource(), messageLimit, sentMessages);
            if (messageLimit == -1 || sentMessages >= messageLimit) {
               LOGGER.warn("Altea FM Message Count Verification not satisfied");
               return;
            }
            // Payload Preparation
            dcsfmUpdateCargofigures = alteaFMService.prepareUpdateCargoPayload(alteaFMEvent);
         } catch (CustomException ex) {
            LOGGER.warn("Altea FM DCSFM Update cargo data prepation exception :: {}", ex);
         }
         // AlteaFM API Processor
         if (Objects.nonNull(dcsfmUpdateCargofigures)) {
            // alteaFMProcessor.processToAlteaFM(dcsfmUpdateCargofigures, alteaFMEvent.getEventSource());
            alteaFMSOAPMessageProcessor.process(dcsfmUpdateCargofigures, alteaFMEvent.getEventSource());
         } else {
            LOGGER.warn("Altea FM DCSFM Payload EMPTY - WebService is Stopped {}");
         }
      } else {
         LOGGER.debug("Altea FM Event payload received NULL in Stream Listener");
      }
   }

}
