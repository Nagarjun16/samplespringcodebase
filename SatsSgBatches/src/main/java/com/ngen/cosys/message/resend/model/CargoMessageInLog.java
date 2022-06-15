package com.ngen.cosys.message.resend.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class CargoMessageInLog {

	/**
	 * Default serial version id
	 */
	private static final long serialVersionUID = 1L;

	private BigInteger id;

	private String messageContentEndIndicator;
	private String senderOriginAddress;
	private String interfacingSystem;
	private String channel;
	private String msgType;
	private String subMsgType;
	private String carrCode;
	private String fltNo;
	private String fltKey;
	private String successMsgsSeqNo;
	private String awbNo;
	private String status;
	private String airportCode;

	private BigDecimal messageVersion;

	private LocalDate awbDate;
	private LocalDateTime recvTime;
	private LocalDateTime fltDate;

	private String request;
	private String response;

	// ESB Message LogId
	private BigInteger esbMessageLogId;

	// base model attribute
	private String tenantId;
	private String createdUserId;
	private String modifiedUserId;
	private LocalDateTime createdDate;
	private LocalDateTime modifiedDate;
	private String flagCRUD; // ADD/UPD
}
