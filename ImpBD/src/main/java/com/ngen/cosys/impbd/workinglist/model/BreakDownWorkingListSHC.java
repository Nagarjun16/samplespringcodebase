package com.ngen.cosys.impbd.workinglist.model;

import java.math.BigInteger;

import com.ngen.cosys.framework.model.BaseBO;
import com.ngen.cosys.model.SHCModel;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class BreakDownWorkingListSHC extends SHCModel {

	/**
	 * System Generated Serial Version id
	 */
	private static final long serialVersionUID = 2103836991757774847L;
	private BigInteger impArrivalManifestShipmentInfoId;
}
