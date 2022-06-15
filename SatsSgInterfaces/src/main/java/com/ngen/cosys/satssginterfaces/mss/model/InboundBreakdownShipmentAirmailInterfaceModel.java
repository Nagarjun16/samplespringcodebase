package com.ngen.cosys.satssginterfaces.mss.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;

import javax.validation.Valid;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ngen.cosys.JsonSerializer.LocalDateTimeSerializer;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@Validated
public class InboundBreakdownShipmentAirmailInterfaceModel extends ShipmentModelAirmailInterface {
	/**
	 * System Generated Serial Verison id
	 */
	private static final long serialVersionUID = 6022541580654061914L;
	
	private BigInteger inboundBreakdownId;
	private BigInteger id;
	private BigInteger flightId;
	private BigInteger shipmentVerificationId;

	private String shipmentType;
	private String handlingMode;
	private String transferType;
	private String uldNumber;
	private String breakdownStaffGroup;
	private String breakdownInstruction;
	private String warehouseHandlingInstruction;

	private BigInteger manifestPieces;
	private BigDecimal manifestWeight;
	private BigInteger breakDownPieces;	
	private BigDecimal breakDownWeight;

	@JsonSerialize(using = LocalDateTimeSerializer.class)
	private LocalDateTime breakDownStartDate;

	private Boolean handCarry = Boolean.FALSE;
	private Boolean uldDamage = Boolean.FALSE;
	private Boolean intactContainer = Boolean.FALSE;
	private Boolean breakContainer = Boolean.FALSE;
	
	private Boolean expressFlag= Boolean.FALSE;
	
	private Boolean preBookedPieces= Boolean.FALSE;

	private List<InboundBreakdownShipmentShcAirmailInterfaceModel> shcs;

	@Valid
	private List<InboundBreakdownShipmentInventoryAirmailInterfaceModel> inventory;

}