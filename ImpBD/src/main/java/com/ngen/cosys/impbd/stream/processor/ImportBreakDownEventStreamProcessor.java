package com.ngen.cosys.impbd.stream.processor;

import org.springframework.messaging.Message;

import com.ngen.cosys.events.stream.payload.ImpBdPayload;

public interface ImportBreakDownEventStreamProcessor {
	
	/**
	    * Method to process the event stream object
	    * 
	    * @param payload
	    *           - Message<InboundBreakDownPayLoad> payload
	    *
	    */
	   void process(Message<ImpBdPayload> payload);

}
