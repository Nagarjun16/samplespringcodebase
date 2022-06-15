/**
 * OprFlightSegTempBO.java
 * 
 * Copyright <PRE><IMG SRC = XX></IMG></PRE>
 *
 * Version     Date			Author     Reason
 * 1.0        28 July, 2017	NIIT     -
 */
package com.ngen.cosys.flight.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * This supporting model class helps to prepare the Segments based on legs 
 * for Operative Flight.
 * 
 * @author NIIT Technologies Ltd
 * @version 1.0
 */
@ApiModel
@Component
@ToString
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Scope("prototype")
public class OprFlightSegTempBO implements Serializable {
	/**
    * The default serialVersionUID.
    */
   private static final long serialVersionUID = 1L;
   /**
	 * flightId of operating flight Segment Temp Bo
	 */
   private long flightId;
   /**
	 * fltCar of operating flight Segment Temp Bo
	 */
   private String fltCar;
   /**
	 * fltNum of operating flight Segment Temp Bo
	 */
	private String fltNum;
	/**
	 * datFlt of operating flight Segment Temp Bo
	 */
	private LocalDateTime datFlt;
	/**
	 * datStd of operating flight Segment Temp Bo
	 */
	private LocalDateTime datStd;
	/**
	 * datSta of operating flight Segment Temp Bo
	 */
	private LocalDateTime datSta;
	/**
	 * aptBrd of operating flight Segment Temp Bo
	 */
	private String aptBrd;
	/**
	 * brdLeg of operating flight Segment Temp Bo
	 */
	private Integer brdLeg;
	/**
	 * aptOff of operating flight Segment Temp Bo
	 */
	private String aptOff;
	/**
	 * offLeg of operating flight Segment Temp Bo
	 */
	private Integer offLeg;
	/**
	 * serviceType of operating flight Segment Temp Bo
	 */
	private String serviceType;
	/**
	 * userCode of operating flight Segment Temp Bo
	 */
	private String userCode;
	/**
	 * createdDateTime of operating flight Segment Temp Bo
	 */
	private LocalDateTime createdDateTime;
}