package com.ngen.cosys.icms.model;
/**
 * Api Response model 
 */
import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.ngen.cosys.annotation.InjectLoggedInUser;
import com.ngen.cosys.audit.NgenAuditField;
import com.ngen.cosys.audit.NgenAuditFieldName.NgenAuditFieldNameType;
import com.ngen.cosys.events.esb.connector.logger.payload.IncomingMessageErrorLog;
import com.ngen.cosys.events.esb.connector.logger.payload.IncomingMessageLog;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
@Component
@Getter
@Setter
@ToString
public class ICMSResponseModel {
	
	private HttpStatus httpStatus;
	private String errorMessage;
	private String carrier;
	private String flightNo;
	private LocalDateTime flightDate;
	private String messageType;
	private String flightStatus;
	private String shipmentNumber;
	private LocalDateTime shipmentDate;
	private IncomingMessageErrorLog messageErrorLog;
	private IncomingMessageLog messageLog;
	private String subMessageType;
	private String createdBy;
	

}
