/**
 * 
 * SearchFlight.java
 * 
 * Copyright <PRE><IMG SRC = XX></IMG></PRE>
 *
 * Version      Date			Author      Reason
 * 1.0          15 December, 2017	NIIT      -
 */
package com.ngen.cosys.satssginterfaces.mss.model;

import java.time.LocalDate;

import org.springframework.stereotype.Component;

import com.ngen.cosys.framework.model.BaseBO;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * This Model is for search operation.
 * 
 * @author NIIT Technologies Ltd
 *
 */
@ApiModel
@Component
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class SearchFlight extends BaseBO{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String flightKey;
	
	private Long flightId;
	
	private LocalDate flightOriginDate;

}
