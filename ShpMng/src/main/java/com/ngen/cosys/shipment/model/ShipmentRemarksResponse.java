package com.ngen.cosys.shipment.model;

import javax.validation.constraints.Pattern;

import com.ngen.cosys.framework.model.BaseBO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class ShipmentRemarksResponse extends BaseBO {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String shipmentType;
	private String shipmentNumber;
	private String shipmentDate;
	private String remarkType;
	@Pattern(regexp = "^[A-Za-z0-9-. ]*$", message = "data.invalid.telex.characters")
	private String shipmentRemarks;
	private int shipmentRemarkId;

}
