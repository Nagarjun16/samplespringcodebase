package com.ngen.cosys.inward.model;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import com.ngen.cosys.annotation.NgenCosysAppAnnotation;
import com.ngen.cosys.audit.NgenAuditField;
import com.ngen.cosys.framework.model.BaseBO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
public class InwardSegmentConcatenate extends BaseBO{
	@NgenCosysAppAnnotation
	   @Valid
	   @NgenAuditField(fieldName = "shipmentDiscrepancy")
	  private List<InwardServiceReportShipmentDiscrepancyModel> shipmentDiscrepancy;

	   @NgenCosysAppAnnotation
	   @Valid
	  @NgenAuditField(fieldName = "physicalDiscrepancy")
	   private List<InwardServiceReportShipmentDiscrepancyModel> physicalDiscrepancy;
	   @Valid
	   @NgenAuditField(fieldName = "otherDiscrepancy")
	   private List<InwardServiceReportOtherDiscrepancyModel> otherDiscrepancy = new ArrayList<>();
	   
	   @NgenAuditField(fieldName = "Boarding Point")
	   private String boardingPoint;

	   @NgenAuditField(fieldName = "Off Point")
	   private String offPoint;
	   
	   private String segmentId;
	   private String segmentdesc;
	   
		private BigInteger id;
		private BigInteger flightId;
		private BigInteger inwardServiceReportId;
		   private BigInteger manifestedPages;
		   @NgenAuditField(fieldName = "actionTaken")
		   private String actionTaken;
		   private String natureOfDiscrepancies;
	

}
