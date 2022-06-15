package com.ngen.cosys.satssginterfaces.mss.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import javax.validation.Valid;
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
public class ManifestSegment extends Segment {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long manifestId;
	private long flightId;
	private String type;
	private long versionNo;
	private BigInteger pieces;
	private BigDecimal weight;
	@Valid
	private List<ConnectingFlight> connectingFlight;
	private List<ManifestULD> ulds;
}
