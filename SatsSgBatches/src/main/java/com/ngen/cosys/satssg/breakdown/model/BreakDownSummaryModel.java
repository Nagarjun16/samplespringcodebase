package com.ngen.cosys.satssg.breakdown.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ngen.cosys.JsonSerializer.LocalDateTimeSerializer;
import com.ngen.cosys.model.FlightModel;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@XmlRootElement
@ApiModel
@Getter
@Setter
@NoArgsConstructor
public class BreakDownSummaryModel extends FlightModel{

	/**
	 * System generated serial version id
	 */
	private static final long serialVersionUID = 1167558283832826833L;


    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime firstUldTowInTime;
    
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime lastUldTowInTime;
    
   @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime breakdownCompletionDataTime;
   
   @JsonSerialize(using = LocalDateTimeSerializer.class)
   private LocalDateTime flightCompletionDataTime;
   
   private String delayInMinutes;
   private String dutyManager;
   private String checker;
   private String breakDownStaffGroup;
   private BigDecimal tonnageBreakDownBySp;
   private BigDecimal tonnageBreakDownBySats;  
	
   private List<BreakDownSummaryUldModel> uldInfo;
	
	
	private List<BreakDownSummaryTonnageHandledModel> tonnageHandlingInfo;
	
	
}
