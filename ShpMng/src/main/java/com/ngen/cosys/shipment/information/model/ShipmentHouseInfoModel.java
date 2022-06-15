package com.ngen.cosys.shipment.information.model;

import java.math.BigDecimal;
import java.math.BigInteger;

import com.ngen.cosys.framework.model.BaseBO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ShipmentHouseInfoModel extends BaseBO {
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;

	private BigInteger shipmentId;
	private String houseInfoTagNumber;
	private String houseInfoRfidTagId;
	private String houseInfoRfidTagPieces;
	private String houseInfoShipmentType;
	private BigDecimal houseChgWeight;

}
