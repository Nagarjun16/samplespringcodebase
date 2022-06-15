package com.ngen.cosys.impbd.workinglist.model;

import java.math.BigDecimal;
import java.math.BigInteger;

import com.ngen.cosys.annotation.NgenCosysAppAnnotation;
import com.ngen.cosys.framework.model.BaseBO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@NgenCosysAppAnnotation
public class ExportWorkingListModel extends BaseBO {
	
	private static final long serialVersionUID = 1L;
	
	public BigInteger bookingPieces;
	
	public BigDecimal bookingWeight;
	
	public BigInteger bookingFlightid;
	
	public BigInteger bookingSegmentid;
	
	public BigInteger shipmentid;
	
	public Boolean partShipment;

}
