package com.ngen.cosys.satssg.breakdown.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ngen.cosys.JsonSerializer.LocalDateTimeSerializer;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@ApiModel
@Getter
@Setter
@NoArgsConstructor
public class BreakDownSummaryUldModel extends ULDModel{
	
	/**
	 * System generated serial version id 
	 */
	private static final long serialVersionUID = 3902131233921150054L;
    private String contentCode;
    private String handlingMode;
    private BigDecimal manifestedWeight;
    private BigDecimal actualWeight;
    private BigDecimal differece;
    private String breakdownStaff;
    private Boolean sats=Boolean.FALSE;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime breakdownStartDataTime;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime breakdownEndDataTime;
    private List<String> serviceContractor;
    
    private BigInteger flightId;
    
    private BigInteger summaryId;
    
    private BigInteger rowNumber;
    
    
    
    
    
    
}
