package com.ngen.cosys.impbd.workinglist.model;

import java.math.BigInteger;

import com.ngen.cosys.framework.model.BaseBO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@NoArgsConstructor
public class BreakDownWorkingListShipmentMismatchPieces extends BaseBO {

	/**
	 * System Generated Serial Version id
	 */
	private static final long serialVersionUID = -4756590034783446817L;
	private String segment;
	private String awbNumber;
	private String uldNumber;
	private BigInteger mnPieces;
	private BigInteger bdPieces;
	private String irregularity;
	private String offloaded;

}
