package com.ngen.cosys.inward.model;

import java.math.BigInteger;

import com.ngen.cosys.audit.NgenAuditField;
import com.ngen.cosys.framework.model.BaseBO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class InwardSegmentModel extends BaseBO {
	   /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BigInteger flightId;
	   private String segmentId;
	   private String flightBoardPoint;
	   private BigInteger manifestedPages;
	   @NgenAuditField(fieldName = "actionTaken")
	   private String actionTaken;
	   private String natureOfDiscrepancies;
	  
	   

}
