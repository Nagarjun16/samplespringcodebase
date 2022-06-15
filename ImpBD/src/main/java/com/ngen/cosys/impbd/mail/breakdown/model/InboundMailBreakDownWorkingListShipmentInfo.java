package com.ngen.cosys.impbd.mail.breakdown.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import com.ngen.cosys.audit.NgenAuditField;
import com.ngen.cosys.framework.model.BaseBO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class InboundMailBreakDownWorkingListShipmentInfo extends BaseBO {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String shipmentNumber;
	private BigInteger shipmentId;
	private BigInteger shpPieces;
	private BigDecimal shpWeight;
	private String dispatchNumber;
	private String origin;
	private String destination;
	private String shipmentLocation;
	private String warehouseLocation;
    @NgenAuditField(fieldName = "Shipments")
    private List<InboundMailBreakDownShipmentModel> shipments;
}
