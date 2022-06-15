package com.ngen.cosys.shipment.model;

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
 * This model class ArrivalFlightInfo entity to show user Flight Details for Import
 * details
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
public class ArrivalFlightInfo extends BaseBO {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int flight_ID;

	/**
     * flight Key
     */
	private String flightKey;
	
	/**
	 * date on which flight has been scheduled
	 */
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	private LocalDateTime flightDate;
		
	/**
	 * shipment handling code
	 */
	private ArrayList<String> arrivalShipmentShcs;

}
