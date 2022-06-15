/**
 * This is a event stream processor implementation class which receives a event
 * payload for SatsSgInterface
 */
package com.ngen.cosys.events.stream.processor.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import com.ngen.cosys.events.enums.EventStreamHeaders;
import com.ngen.cosys.events.stream.business.processor.BaseBusinessEventStreamProcessor;
import com.ngen.cosys.events.stream.payload.SatsSgInterfacePayload;
import com.ngen.cosys.events.stream.processor.SatsSgInterfaceEventStreamProcessor;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class SatsSgInterfaceEventStreamProcessorImpl implements SatsSgInterfaceEventStreamProcessor {

   private static final Logger LOGGER = LoggerFactory.getLogger(SatsSgInterfaceEventStreamProcessor.class);

   @Autowired
   private BeanFactory beanFactory;

   @Override
   public void process(Message<SatsSgInterfacePayload> payload) {
      // Log payload
      if (LOGGER.isDebugEnabled()) {
         LOGGER.debug("Payload message is", payload);
      }
      try {
         // Get the bean processor for specific Event Type
         BaseBusinessEventStreamProcessor processor = (BaseBusinessEventStreamProcessor) beanFactory
               .getBean((String) payload.getHeaders().get(EventStreamHeaders.Names.EVENT_TYPE));
         // Route the event to business event processor
         processor.process(payload);
      } catch (Exception ex) {
         if (LOGGER.isErrorEnabled()) {
            LOGGER.error("No bean defined for the specific event type", ex);
         }
      }
   }
}