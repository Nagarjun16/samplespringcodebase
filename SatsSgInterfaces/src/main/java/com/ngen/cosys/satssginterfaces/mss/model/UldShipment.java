package com.ngen.cosys.satssginterfaces.mss.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.stereotype.Component;

import com.ngen.cosys.framework.model.BaseBO;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ApiModel
@XmlRootElement
@Component
@ToString
@Getter
@Setter
@NoArgsConstructor
public class UldShipment extends BaseBO {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	private BigInteger id;
	private String shipmentNumber; //
	private LocalDateTime shipmentDate;
	private String assUldTrolleyNo; //
	private BigInteger assUldTrolleyId;
	private String contentCode;
	private String heightCode;
	private String flightKey; //
	private LocalDate flightOriginDate; //
	private String segment; //
	private String uldType;
	private String uldCarrierCode;
	private String uldCarrierCode2;
	private Integer phcIndicator;
	private BigInteger shipmentId; //
	private BigInteger segmentId; //
	private BigInteger flightSegmentId;
	private BigInteger flightId; //
	private BigInteger pieces;
	private BigDecimal weight;
	private boolean shipmentLockFlag;
	private Integer partConfirm;
	private Integer returned;
	private Integer finalizeWeight;
	private Integer rejected;
	private boolean trolleyInd;
	private BigDecimal tareWeight;
	private String eccIndicator;
	private boolean usedAsTrolley;
	private boolean usedAsStandBy;
	private boolean ocsCargoFlag;
	private String remarks;
	private BigDecimal grossWeight;
	private BigInteger assignedPieces;
	private BigDecimal assignedWeight;

	// List of Inventory which need to be loaded Common Load Shipment
	private List<LoadedShipment> loadShipmentList;
	// List of Shipment House for Loading mail bag
	private List<ShipmentHouse> mailBagList;
	private String mailInd;

	// For transhipment scenario
	private String transferType;
	private List<String> shcList;
	private boolean fromAmendFlag;
	private String newUldNumber;
	private boolean newULD;
	private String shipmentType;

}
