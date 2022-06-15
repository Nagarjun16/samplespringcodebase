package com.ngen.cosys.icms.model.bookingicms;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Component
@Getter
@Setter
@ToString
public class ValidateBookingResponseBo {
	private HttpStatus httpStatus;
	private String messageHeader;
	private String messageType;
	private String sourceSystem;
	private String responseDetails;
	private String requestID;
	private String status;
	private String errorCode;


}
