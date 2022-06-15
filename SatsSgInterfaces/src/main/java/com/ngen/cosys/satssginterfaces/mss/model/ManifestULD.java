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
public class ManifestULD extends ULDIInformationDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long manifestId;
	private long manifestUldId;
	private BigInteger uldSequenceId;
	private Segment segment;
	private List<ManifestShipment> shipment;
	private List<ManifestShipment> courier;
}
