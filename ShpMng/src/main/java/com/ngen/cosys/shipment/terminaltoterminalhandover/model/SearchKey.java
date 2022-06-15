package com.ngen.cosys.shipment.terminaltoterminalhandover.model;

import com.ngen.cosys.framework.model.BaseBO;
import com.ngen.cosys.validator.annotations.CheckContainerNumberConstraint;
import com.ngen.cosys.validator.annotations.CheckShipmentNumberConstraint;
import com.ngen.cosys.validator.enums.MandatoryType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class SearchKey extends BaseBO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2047897231885578842L;

//	@CheckShipmentNumberConstraint(mandatory = MandatoryType.Type.REQUIRED)
	private String awbnumber;

//	@CheckContainerNumberConstraint(mandatory = MandatoryType.Type.REQUIRED)
	private String uldnumber;

	private String awbUldnumber;

}
