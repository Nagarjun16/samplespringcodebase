package com.ngen.cosys.impbd.tracing.model;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ngen.cosys.JsonSerializer.LocalDateSerializer;
import com.ngen.cosys.annotation.InjectShipmentDate;
import com.ngen.cosys.annotation.NgenCosysAppAnnotation;
import com.ngen.cosys.model.SegmentModel;
import com.ngen.cosys.validator.annotations.CheckContainerNumberConstraint;
import com.ngen.cosys.validator.annotations.CheckShipmentNumberConstraint;
import com.ngen.cosys.validator.enums.MandatoryType;
import com.ngen.cosys.validators.ArrivalManifestValidationGroup;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@ApiModel
@Getter
@Setter
@NoArgsConstructor
@NgenCosysAppAnnotation
public class BreakDownTracingFlightSegmentModel extends SegmentModel{
	
	/**
	 * System generated serial version id  
	 */
	private static final long serialVersionUID = 6914951046637757373L;
	
	@CheckShipmentNumberConstraint(mandatory = "MandatoryType.Type.NOTREQUIRED")
    private String shipmentNumber;
    
    @InjectShipmentDate(shipmentNumberField = "shipmentNumber")
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate shipmentDate;
    
    @CheckContainerNumberConstraint(mandatory = MandatoryType.Type.NOTREQUIRED, groups = {
          ArrivalManifestValidationGroup.class })
    private String uldNumber;
    
    private List<BreakDownTracingUldModel> uldData;
    
    //Loose Shipment
    private List<BreakDownTracingShipmentModel> looseShipment;
    
    private BigInteger intact=BigInteger.ZERO;
    
    private BigInteger breakULD=BigInteger.ZERO;
    
    private BigInteger offload=BigInteger.ZERO;
    
    private BigInteger surplus=BigInteger.ZERO;
    
    private BigInteger shortLand=BigInteger.ZERO;
    
    private BigInteger damage=BigInteger.ZERO;
    
    private BigInteger segmentULDCount=BigInteger.ZERO;
    
    
}
