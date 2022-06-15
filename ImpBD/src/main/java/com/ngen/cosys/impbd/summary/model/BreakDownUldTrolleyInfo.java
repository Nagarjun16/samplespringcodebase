package com.ngen.cosys.impbd.summary.model;

import java.math.BigInteger;
import java.time.LocalDate;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ngen.cosys.JsonSerializer.LocalDateTimeSerializer;
import com.ngen.cosys.framework.model.BaseBO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
public class BreakDownUldTrolleyInfo extends BaseBO {
	 /**
	 * 
	 */
		private static final long serialVersionUID = 1L;

		private BigInteger impShipmentVerificationId;

	    private String ULDTrolleyNumber;

	    private BigInteger impBreakDownULDTrolleyInfoId;

	    private String breakDownStaff;

	    private String serviceContractor;

	    private Boolean damagedFlag;
	    
	    @JsonSerialize(using = LocalDateTimeSerializer.class)
	    private LocalDate breakDownStartedAt;
	    
	    @JsonSerialize(using = LocalDateTimeSerializer.class)
	    private LocalDate breakDownEndedAt;

	    private String handlingAreaCode;

	    private String contentCode;

	    private Boolean breakFlag;

	    private String ghaStaff;
}
