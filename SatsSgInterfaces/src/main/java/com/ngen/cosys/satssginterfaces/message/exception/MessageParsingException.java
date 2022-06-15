package com.ngen.cosys.satssginterfaces.message.exception;

import java.util.LinkedList;
import java.util.List;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.ngen.cosys.cxml.response.ParserError;

@Component
@Lazy
public class MessageParsingException extends Exception {

	/**
	 * SYstem generated serial version id
	 */
	private static final long serialVersionUID = 1L;

	private final List<ParserError> parserError = new LinkedList<>();

	public MessageParsingException(String description) {
		ParserError error = new ParserError();
		error.setDescription(description);
		parserError.add(error);
	}

	public MessageParsingException(String message, Throwable t) {
		super(message, t);
	}

	public MessageParsingException(String description, String message) {
		ParserError error = new ParserError();
		error.setDescription(description);
		error.setMessage(message);
		parserError.add(error);
	}

	public MessageParsingException(String code, String description, String message) {
		ParserError error = new ParserError();
		error.setCode(code);
		error.setDescription(description);
		error.setMessage(message);
		parserError.add(error);
	}

	/**
	 * @return the parserError
	 */
	public List<ParserError> getParserError() {
		return parserError;
	}

}