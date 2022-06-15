package com.ngen.cosys.impbd.shipment.verification.model;

import java.math.BigInteger;
import java.util.List;

import com.ngen.cosys.model.SegmentModel;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@ApiModel
@Getter
@Setter
@NoArgsConstructor
public class DocumentVerificationFlightSegmentModel extends SegmentModel{

	/**
	 * System generated serial version id 
	 */
	private static final long serialVersionUID = -7869930843940221766L;
    private BigInteger segmentId;
    private String  transferType;
    private List<DocumentVerificationShipmentModel> documentVerificationShipmentModel;
    private List<DocumentVerificationShipmentModel> documentVerificationSHC;
    private List<DocumentVerificationShipmentModel> documentVerificationIrregularity;
}
