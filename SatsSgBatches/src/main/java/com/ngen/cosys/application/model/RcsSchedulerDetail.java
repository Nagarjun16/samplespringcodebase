package com.ngen.cosys.application.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;

import com.ngen.cosys.framework.model.BaseBO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RcsSchedulerDetail extends BaseBO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	   private BigInteger eventOutboundShipmentPiecesEqualsToAcceptedPiecesStoreId;
	   private BigInteger shipmentId;
	   private BigInteger firstBookingFlightId;
	   private BigInteger pieces;
	   private BigDecimal weight;
	   private String status;
	   private LocalDateTime confirmedAt;
	   private String confirmedBy;
	   private String createdBy;
	   private LocalDateTime createdOn;
	   private String lastModifiedBy;
	   private LocalDateTime lastModifiedOn;
	   private boolean rcsEawbStatus;
	   private int rcsRACShcCheck;
	   private String messageType;
	   private String subMessageType;
	   private String triggerPoint;
	   private String shipmentNumber;

	   // Audit trail fields
	   private String eventName;
	   private String function;

}
