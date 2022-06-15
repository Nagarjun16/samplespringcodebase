/**
 * AlteaFMServiceImpl.java
 * 
 * Copyright <PRE><IMG SRC = XX></IMG></PRE>
 *
 * Version      Date         Author      Reason
 * 1.0          22 JAN, 2019   NIIT      -
 */
package com.ngen.cosys.altea.fm.service;

import java.time.LocalDate;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.ngen.cosys.altea.fm.common.DCSFMConstants;
import com.ngen.cosys.altea.fm.dao.AlteaFMRepository;
import com.ngen.cosys.altea.fm.model.DCSFMUpdateCargoFigures;
import com.ngen.cosys.altea.fm.processor.AlteaFMSOAPMessageProcessor;
import com.ngen.cosys.events.payload.AlteaFMEvent;
import com.ngen.cosys.framework.exception.CustomException;

/**
 * This calss is used for DCS Update Cargo 
 * 
 * @author NIIT Technologies Ltd
 *
 */
@Service
public class AlteaFMServiceImpl implements AlteaFMService {

   private static final Logger LOGGER = LoggerFactory.getLogger(AlteaFMService.class);
   
   @Autowired
   AlteaFMRepository alteaFMRepository;
   
   @Autowired
   AlteaFMSOAPMessageProcessor alteaFMSOAPMessageProcessor;
   
   /**
    * @see com.ngen.cosys.altea.fm.service.AlteaFMService#isMessageAddressingSetupValid(com.ngen.cosys.events.payload.AlteaFMEvent)
    * 
    */
   @Override
   public boolean isMessageAddressingSetupValid(AlteaFMEvent event) throws CustomException {
      return alteaFMRepository.isMessageAddressingSetupConfigured(event);
   }
   
   /**
    * @see com.ngen.cosys.altea.fm.service.AlteaFMService#getConfiguredPerFlightMessageCount(com.ngen.cosys.events.payload.AlteaFMEvent)
    * 
    */
   @Override
   public int getConfiguredPerFlightMessageCount(AlteaFMEvent event) throws CustomException {
      String messageCount = alteaFMRepository.getConfiguredPerFlightMessageCount(DCSFMConstants.FLIGHT_MESSAGE_LIMIT);
      LOGGER.warn("AlteaFM Configured Flight Message Limit : {}", messageCount);
      if (!StringUtils.isEmpty(messageCount)) {
         return Integer.parseInt(messageCount);
      }
      return -1;
   }
   
   /**
    * @see com.ngen.cosys.altea.fm.service.AlteaFMService#getAlteaFMSENTMessageCountByFlight(com.ngen.cosys.events.payload.AlteaFMEvent)
    * 
    */
   @Override
   public int getAlteaFMSENTMessageCountByFlight(AlteaFMEvent event) throws CustomException {
      return alteaFMRepository.getSENTMessageCountByFlight(event);
   }
   
   /**
    * @see com.ngen.cosys.altea.fm.service.AlteaFMService#prepareUpdateCargoPayload(com.ngen.cosys.events.payload.AlteaFMEvent)
    * 
    */
   @Override
   public DCSFMUpdateCargoFigures prepareUpdateCargoPayload(AlteaFMEvent event) throws CustomException {
      LOGGER.debug("Prepare update cargo figure payload - Event Source :: {}", event.getEventSource());
      DCSFMUpdateCargoFigures dcsfmUpdateCargofigures = null;
      String eventSource = event.getEventSource();
      if (!StringUtils.isEmpty(eventSource)) {
         LOGGER.debug("AlteaFM Payload - Event Source :: {}", eventSource);
         if (Objects.isNull(event.getFlightDate()) && Objects.equals("SQ8521", event.getFlightKey())) {
            event.setFlightDate(LocalDate.of(2019, 07, 19));
         }
         dcsfmUpdateCargofigures = alteaFMRepository.getAlteaFMEventSourcePayload(event);
      }
      // AlteaFM API Processor
      if (Objects.nonNull(dcsfmUpdateCargofigures)) {
         // alteaFMProcessor.processToAlteaFM(dcsfmUpdateCargofigures, alteaFMEvent.getEventSource());
         alteaFMSOAPMessageProcessor.process(dcsfmUpdateCargofigures, event.getEventSource());
      } else {
         LOGGER.warn("Altea FM DCSFM Payload EMPTY - WebService is Stopped {}");
      }
      //
      return dcsfmUpdateCargofigures;
   }

}
