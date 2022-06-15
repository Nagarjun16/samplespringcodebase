package com.ngen.cosys.satssginterfaces.mss.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import com.ngen.cosys.framework.model.BaseBO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ShipmentInventory extends BaseBO {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BigInteger shipmentId;
	private BigInteger shipmentInventoryId;
	private BigInteger comTracingShipmentInfoId;
	private BigInteger comTracingShipmentLocationInfoId;
	private String locationCode;
	private BigInteger pieces;
	private BigDecimal weight;
	private BigInteger movPieces;
	private BigDecimal movWeight;
	private BigInteger tagPieces;
	private BigDecimal tagWeight;
	private String shipmentLocation;
	private String warehouseLocation;
	private String tagNumber;
	private BigInteger housePieces;
	private BigInteger sspdPieces;
	//private HouseInformationModel house;
	   private String status;
	    private String referenceDetails;
	    
	    
	    private List<ShipmentHouse> houseInfo;

}

