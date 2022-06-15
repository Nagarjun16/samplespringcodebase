package com.ngen.cosys.satssginterfaces.message.exception;

import org.springframework.context.annotation.Lazy;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Component;

@Component
@Lazy
public class MessageProcessingException extends MessagingException {

	/**
	 * System generated serial version id
	 */
	private static final long serialVersionUID = 1L;
	
	
	public MessageProcessingException(Message<Object> message) {
		super(message);
		
	}
	public MessageProcessingException(String message,Throwable e) {
		super(message,e);
	}
	public MessageProcessingException(String message) {
		super(message);
	}

}
