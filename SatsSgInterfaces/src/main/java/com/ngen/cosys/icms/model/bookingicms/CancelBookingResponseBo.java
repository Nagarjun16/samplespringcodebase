package com.ngen.cosys.icms.model.bookingicms;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Component
@Getter
@Setter
public class CancelBookingResponseBo {
	private HttpStatus httpStatus;
}
