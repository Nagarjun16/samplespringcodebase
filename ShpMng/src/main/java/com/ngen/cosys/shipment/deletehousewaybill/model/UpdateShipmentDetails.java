package com.ngen.cosys.shipment.deletehousewaybill.model;

import java.math.BigDecimal;
import java.math.BigInteger;

import com.ngen.cosys.framework.model.BaseBO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class UpdateShipmentDetails extends BaseBO{
	
	private static final long serialVersionUID = 1L;
	private String messageType;
	private String MAWBNo;
	private String HAWBNo;
	private String isConsol;
	private String IGMNo;
	private int IGMYear;
	private BigInteger flightId;
	
	private String flightNo;
	private String flightDate;
	private String ATA;
	private String segregationTime;
	private int declaredPcs;
	private BigDecimal declaredGrWt;
	private BigDecimal declaredChWt;
	private int receivedPcs;
	private BigDecimal receivedGrWt;
	private BigDecimal receivedChWt;
	private String HAWBOrigin;
	private String HAWBDest;
	private String consolAgentCode;
	private String consolAgentName;
	private String clearingAgentCode;
	private String clearingAgentName;
	private String isDamage;
	private String damageRemarks;
	private String consigneeName;
	private String consigneeAddress;
}
