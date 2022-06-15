package com.ngen.cosys.satssg.breakdown.model;

import java.math.BigDecimal;
import java.math.BigInteger;

import javax.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;

import com.ngen.cosys.annotation.NgenCosysAppAnnotation;
import com.ngen.cosys.framework.model.BaseBO;


import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@ApiModel
@Getter
@Setter
@NoArgsConstructor
@NgenCosysAppAnnotation
@Validated
public class BreakDownSummaryTonnageHandledModel extends BaseBO{
	
	/**
	 * System generated serial version id
	 */
	private static final long serialVersionUID = 7549567964609170805L;
	
	
    private String cargoType;
    private BigDecimal tonnage;
    private String remark;
    private BigInteger rowNumber;
    private BigInteger flightId;
    private BigInteger summaryId;
    
    private BigInteger tonnageId;
    
    private String addTonnage;
    
    
}
