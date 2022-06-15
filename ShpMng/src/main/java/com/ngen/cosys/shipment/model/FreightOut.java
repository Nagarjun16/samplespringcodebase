package com.ngen.cosys.shipment.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import org.apache.ibatis.type.MappedTypes;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ngen.cosys.JsonSerializer.LocalDateTimeSerializer;
import com.ngen.cosys.framework.model.BaseBO;

import groovy.transform.ToString;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * This model class ShipmentInventory entity to able user to remove
 * shipment on hold or get to know other details regarding shipment on hold 
 * e.g. warehouse location, freight In, freight in date, SHC etc.
 * 
 * @author NIIT Technologies Ltd
 * @version 1.0
 */

@ApiModel
@Component
@ToString
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@MappedTypes(LocalDateTime.class)
public class FreightOut extends BaseBO {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
     * unique shipment ID
     */
	private int shipmentIdfreight; 
	
	private int shipmentfrightOutId; 
	
	private String freightOut;
	
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	private LocalDateTime flightKeyDate;
	
	private int piecesFreightOut;
	
	private BigDecimal weightFreightOut;
	
	private BigDecimal chargeableWeightFreightOut;
	
	private String specialHandlingCodeFreightOut;
	
	 /**
	    * shipment handling code
	    */
	private ArrayList<String> shcListFreightOut;

}
