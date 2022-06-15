package com.ngen.cosys.impbd.displayffm.model;

import java.math.BigInteger;
import java.util.List;

import javax.validation.Valid;

import com.ngen.cosys.annotation.NgenCosysAppAnnotation;
import com.ngen.cosys.model.SegmentModel;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FreightFlightManifestBySegmentModel extends SegmentModel {
	/**
	 * System generated serial version id
	 */
	private static final long serialVersionUID = 1437227932268314632L;

	private BigInteger impFreightFlightManifestBySegmentId;
	private BigInteger impFreightFlightManifestByFlightId;
	// ULD List
	@Valid
	@NgenCosysAppAnnotation
	private List<FreightFlightManifestUldModel> freightmanifestedUlds;
	// LooseCargo
	@Valid
	@NgenCosysAppAnnotation
	private List<FreightFlightManifestByShipmentModel> freightawbDetails;
	
	private String segmentUldGruopDetailsCount;
	
	private List<String> segmentUldGruopDetailsCountList;

}
