/**
 * This is a event processor which is invoked by stream listener for processing
 * messages from SatsSgInterface Service App messages. messages.
 * 
 * @author NIIT Technologies Pvt Ltd
 */
package com.ngen.cosys.events.stream.processor;

import org.springframework.messaging.Message;

import com.ngen.cosys.events.stream.payload.SatsSgInterfacePayload;

public interface SatsSgInterfaceEventStreamProcessor {

   /**
    * Method to process the event stream object
    * 
    * @param payload
    *           - Message<SatsSgInterfacePayload> payload
    *
    */
   void process(Message<SatsSgInterfacePayload> payload);

}