package com.ngen.cosys.satssginterfaces.message.exception;

public class MessageParsingExceptionFactory {

	public static MessageParsingException createMessageParsingException(String description) {
		return new MessageParsingException(description);
	}

	public static MessageParsingException createMessageParsingException(String description, String message) {
		return new MessageParsingException(description, message);
	}

	public static MessageParsingException createMessageParsingException(String code, String description,
			String message) {
		return new MessageParsingException(code, description, message);
	}

}
