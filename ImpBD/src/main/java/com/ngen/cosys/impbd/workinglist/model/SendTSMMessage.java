package com.ngen.cosys.impbd.workinglist.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ngen.cosys.JsonSerializer.LocalDateSerializer;
import com.ngen.cosys.JsonSerializer.LocalDateTimeSerializer;
import com.ngen.cosys.framework.model.BaseBO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SendTSMMessage extends BaseBO {

	   /**
	    * System generated serial version id
	    */
	   private static final long serialVersionUID = -5608103435151371249L;

	   private BigInteger id;
	   private BigInteger sequenceNumber;
	   
	   private BigDecimal messageVersion;
	   
	   private String messageType;
	   private String subMessageType;
	   private String sequence;
	   private String messageEndingIndicator;
	   private String senderOriginAddress;
	   private String interfacingSystem;
	   private String channelName;
	   private String shipmentNumber;
	   private String carrierCode;
	   private String flightKey;
	   private String messageStatus;
	   private String message;
	   private String recipientAddress;
	   private String messageHeader;

	   @JsonSerialize(using = LocalDateTimeSerializer.class)
	   private LocalDateTime messageDateTime;

	   @JsonSerialize(using = LocalDateSerializer.class)
	   private LocalDate shipmentDate;

	   @JsonSerialize(using = LocalDateTimeSerializer.class)
	   private LocalDateTime flightDate;

	   @JsonSerialize(using = LocalDateTimeSerializer.class)
	   private LocalDateTime messageFromDateTime;

	   @JsonSerialize(using = LocalDateTimeSerializer.class)
	   private LocalDateTime messageToDateTime;

	   
	   private String eventName;
	   private BigInteger eventLogid;
	   private String partSuffx;
	   private BigInteger shipmentId;
	   private BigInteger flightId;
	   
	   private List<SendTSMMessage> shipments;
}
