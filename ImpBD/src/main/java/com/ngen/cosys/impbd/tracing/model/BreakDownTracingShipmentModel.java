package com.ngen.cosys.impbd.tracing.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import com.ngen.cosys.model.ShipmentModel;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@ApiModel
@Getter
@Setter
@NoArgsConstructor
public class BreakDownTracingShipmentModel extends ShipmentModel{
	
	/**
	 * System generated serial version id  
	 */
	private static final long serialVersionUID = 3660542730665252808L;
    private String  shipmentNumber;
    private String origin;
    private String destination;
    private String natureOfGoods;
    private String shc;
    private Boolean handCarry = Boolean.FALSE;
    private String transferType;
    private BigInteger pieces;
    private BigDecimal weight;
    private BigInteger damagePieces;
    private String shipmentLocation;
    private String warehouseLocation;
    private String irregularityCode;
    private String irregularityPiece;
    private String handlingTerminal;
    private Boolean offLoadedFlag= Boolean.FALSE;
    private Boolean surplusFlag= Boolean.FALSE;
    private String uldNumber;
    private BigInteger segmentId;
    private BigInteger manifestPieces;
    private BigDecimal manifestWeight;
    private BigInteger flightSegmentId;
    private String hawbNumber;
    private BigInteger flightId;
    private Integer localShipmentFlag = 0;
    
    private List<ShipmentLocationDetails> locationInfo;
}
