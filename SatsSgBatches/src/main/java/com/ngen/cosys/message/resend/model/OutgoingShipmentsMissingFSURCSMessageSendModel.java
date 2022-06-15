/**
 * Model for re-sending RCS message
 */
package com.ngen.cosys.message.resend.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.ngen.cosys.framework.model.BaseBO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class OutgoingShipmentsMissingFSURCSMessageSendModel extends BaseBO {

	/**
	 * System generated serial version id
	 */
	private static final long serialVersionUID = 1L;

	private String acceptanceType;
	private String serviceNumber;
	private String shipmentNumber;
	private String shipmentType;
	private String createdUserCode;
	private String carrierCode;

	private LocalDate shipmentDate;

	private LocalDateTime createdDateTime;
	
	private BigInteger shipmentId;
	private BigInteger piece;
	private BigInteger fwbPieces;
	private BigInteger depTime;

	private BigDecimal weight;
	private BigDecimal fwbWeight;

	private Boolean eawb;
	private Boolean finalizeWeight;
	private Boolean cargoPhysicallyAccepted;
	private Boolean rac;
	private Boolean weightToleranceCheckTriggered;
	private Boolean weightToleranceIssueClosed;

}