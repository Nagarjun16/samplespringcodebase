package com.ngen.cosys.impbd.events.stream.processor;

import org.springframework.messaging.Message;

import com.ngen.cosys.events.stream.payload.ImpBdPayload;

public interface ImpBdBaseBusinessEventStreamProcessor {
	  /**
	    * Method to process the event stream object
	    * 
	    * @param payload
	    *           - Message<ImpBdPayload> payload
	    *
	    */
	   void process(Message<ImpBdPayload> payload);
}
