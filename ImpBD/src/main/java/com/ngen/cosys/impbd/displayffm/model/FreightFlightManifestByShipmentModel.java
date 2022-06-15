package com.ngen.cosys.impbd.displayffm.model;

import java.math.BigInteger;
import java.util.List;

import javax.validation.Valid;

import com.ngen.cosys.annotation.NgenCosysAppAnnotation;
import com.ngen.cosys.model.ShipmentModel;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@NgenCosysAppAnnotation
public class FreightFlightManifestByShipmentModel extends ShipmentModel {
	private static final long serialVersionUID = 1L;

	private Boolean select = Boolean.FALSE;
	private BigInteger impFreightFlightManifestUldId;
	private BigInteger impFreightFlightManifestByShipmentId;
	private String offloadReasonCode;
	private Boolean offloadedFlag = Boolean.FALSE;
	private String rejectReason;
   private String shcCode;
   private String onwardMovement;

	@Valid
	private List<FreightFlightManifestShipmentDimensionModel> dimensions;

	@Valid
	private List<FreightFlightManifestShipmentOnwardMovementModel> movementInfo;

	@Valid
	private List<FreightFlightManifestOtherServiceInfoModel> osi;

	@Valid
	private List<FreightFlightManifestShipmentOciModel> oci;

	@Valid
	private List<FreightFlightManifestByShipmentSHCModel> shc;

}
