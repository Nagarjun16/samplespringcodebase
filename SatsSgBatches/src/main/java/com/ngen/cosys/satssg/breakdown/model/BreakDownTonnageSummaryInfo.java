package com.ngen.cosys.satssg.breakdown.model;

import java.math.BigDecimal;

import com.ngen.cosys.framework.model.BaseBO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
public class BreakDownTonnageSummaryInfo extends BaseBO  {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long impBreakDownSummaryId;

    private String cargoType;

    private Long impBreakDownTonnageSummaryInfoId;

    private BigDecimal deductableTonnageWeight;

    private BigDecimal reason;
}
