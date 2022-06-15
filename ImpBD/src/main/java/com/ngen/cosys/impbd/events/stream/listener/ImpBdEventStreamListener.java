/**
 * Stream listener for processing incoming messages via ImpBdChannel
 */
package com.ngen.cosys.impbd.events.stream.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.Message;

import com.ngen.cosys.events.stream.destination.ImpBdEventStreamChannels;
import com.ngen.cosys.events.stream.payload.ImpBdPayload;
import com.ngen.cosys.events.stream.source.ImpBdEventSource;
import com.ngen.cosys.impbd.events.stream.processor.ImpBdBaseBusinessEventStreamProcessor;

@EnableBinding(ImpBdEventSource.class)
public class ImpBdEventStreamListener {

	@Autowired
	private ImpBdBaseBusinessEventStreamProcessor eventStreamProcessor;

	@StreamListener(ImpBdEventStreamChannels.Names.INPUT)
	public void handle(Message<ImpBdPayload> payload) {
		// Process the event
		eventStreamProcessor.process(payload);

	}

}
