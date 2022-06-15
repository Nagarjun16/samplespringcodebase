package com.ngen.cosys.satssginterfaces.mss.model;

import java.math.BigInteger;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
@ApiModel
@XmlRootElement
@ToString
@Getter
@Setter
@NoArgsConstructor
public class ManifestShipment extends Shipment {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long manifestId;
	private long manifestUldId;
	private long manifestShipmentInfoId;
	private BigInteger loadedShipmentInfoId;
	private long flightId; 
	private BigInteger uldSequenceId;
	private String uldNumber;
	private boolean trolleyInd;
	private String remarkType;
	private String manifestRemark;
	private boolean assignedToFlight;
	private boolean pieceWeightMatches;
	private List<ManifestSHC> shcList ;
	private List<ManifestHouse> houseList;
	private String segment;
	
}
