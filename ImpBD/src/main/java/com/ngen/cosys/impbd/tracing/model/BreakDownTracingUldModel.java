package com.ngen.cosys.impbd.tracing.model;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ngen.cosys.JsonSerializer.LocalDateSerializer;
import com.ngen.cosys.JsonSerializer.LocalDateTimeSerializer;
import com.ngen.cosys.annotation.InjectShipmentDate;
import com.ngen.cosys.annotation.NgenCosysAppAnnotation;
import com.ngen.cosys.model.ULDModel;
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
public class BreakDownTracingUldModel extends ULDModel{
	
	/**
	 * System generated serial version id  
	 */
	private static final long serialVersionUID = -3791920069949929617L;
	
	@CheckShipmentNumberConstraint(mandatory = "MandatoryType.Type.NOTREQUIRED")
    private String shipmentNumber;
    
    @InjectShipmentDate(shipmentNumberField = "shipmentNumber")
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate shipmentDate;
	
	private BigInteger shipmentId;	
	
    private Boolean uldDamage = Boolean.FALSE;
    private Boolean intactContainer = Boolean.FALSE;
    private Boolean breakContainer = Boolean.FALSE;
    
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime breakDownStartDate;
    
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime breakDownEndDate;
    
    private String breakDownDoneBy;
    
    private String handlingMode;
    
    private String handlingAreaCode;
	
    private List<BreakDownTracingShipmentModel> shipmentInULD;
    
    @CheckContainerNumberConstraint(mandatory = MandatoryType.Type.NOTREQUIRED, groups = {
          ArrivalManifestValidationGroup.class })
    private String uldNumber;
    
    private BigInteger segmentId;
    
    private String uldLocationDetails;
    
    private BigInteger flightSegmentId;
    
}
