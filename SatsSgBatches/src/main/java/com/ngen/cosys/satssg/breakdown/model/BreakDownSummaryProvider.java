package com.ngen.cosys.satssg.breakdown.model;

import java.math.BigInteger;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BreakDownSummaryProvider {
	
	private BigInteger ExemptTime;
	
	private boolean delayExempt = Boolean.FALSE;
	
	private String serviceContractor;

}
