package com.ngen.cosys.impbd.workinglist.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ngen.cosys.JsonSerializer.LocalDateTimeSerializer;
import com.ngen.cosys.impbd.shipment.breakdown.model.InboundBreakdownHAWBModel;
import com.ngen.cosys.impbd.shipment.document.model.ShipmentMasterShc;
import com.ngen.cosys.model.ShipmentModel;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class BreakDownWorkingListShipmentInfoModel extends ShipmentModel {

	/**
	 * System Generated Serial Version id
	 */
	private static final long serialVersionUID = -8180316003621034629L;
	private BigInteger impArrivalManifestShipmentInfoId;
	private BigInteger impArrivalManifestULDId;
	private BigInteger impArrivalManifestBySegmentId;
	private BigInteger flightSegmentId;
	private BigInteger flightId;
	private BigInteger totalPieces;
	private BigInteger irregularPieces;
	private BigInteger awbPieces;
	private BigInteger mnPieces;
	private BigInteger bdPieces;
	private BigInteger readyForDelivery;
	private BigInteger outboundFlightId;
	private BigInteger damagedPieces;
	private String damageInfoDetails;

	private String uldNumber;
	private String shipmentInstruction;
	private String cargoIrregularityCode;
	private String irregularity;
	private String outboundFlightKey;
	private String transferType;
	private String partSuffix;

	private BigDecimal mnWeight;
	private BigDecimal bdWeight;
	private BigDecimal awbWeight;
	private BigDecimal irregularWeight;

	private String handledByDOMINT;
	private String handledByMasterHouse;
	private String currentLocation;
	private String ffmLocation;
	private BigInteger shipmenthargebleWeight;

	@JsonSerialize(using = LocalDateTimeSerializer.class)
	private LocalDateTime outboundFlightDate;

	private List<BreakDownWorkingListShipmentVarification> shipmentIrregularityInfo;
	private List<BreakDownWorkingListSHC> breakDownWorkingListSHC;
	private List<ShipmentMasterShc> masterShcs;
	private List<BreakDownWorkingListULDDetails> breakDownWorkingListULDDetails;
	
	//hawb list for the shipment
	private List<InboundBreakdownHAWBModel> shipmentHawbList;

}