/**
 * This is a event stream listener for processing events issued for
 * SatsSgInterface Service App
 * 
 * @author NIIT Technologies Pvt. Ltd
 */
package com.ngen.cosys.events.stream.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.Message;

import com.ngen.cosys.events.stream.destination.SatsSgInterfaceEventStreamChannels;
import com.ngen.cosys.events.stream.payload.SatsSgInterfacePayload;
import com.ngen.cosys.events.stream.processor.SatsSgInterfaceEventStreamProcessor;
import com.ngen.cosys.events.stream.source.SatsSgInterfaceEventSource;

@EnableBinding(SatsSgInterfaceEventSource.class)
public class SatsSgInterfaceEventStreamListener {

   @Autowired
   private SatsSgInterfaceEventStreamProcessor eventStreamProcessor;

   /**
    * Method to process event payload for SatsSgInterface
    * 
    * @param payload
    */
   @StreamListener(SatsSgInterfaceEventStreamChannels.Names.INPUT)
   public void handle(Message<SatsSgInterfacePayload> payload) {
      // Process the message
      eventStreamProcessor.process(payload);
   }
}