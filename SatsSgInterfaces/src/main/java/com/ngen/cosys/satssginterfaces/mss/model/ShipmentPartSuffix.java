/**
 * 
 * ShipmentPartSuffix.java
 * 
 * Copyright <PRE><IMG SRC = XX></IMG></PRE>
 *
 * Version      Date            Author      Reason
 * 1.0          22 December, 2017    NIIT      -
 */
package com.ngen.cosys.satssginterfaces.mss.model;
import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.ngen.cosys.framework.model.BaseBO;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * This model class represents ShipmentPartSuffix Request from UI.
 * 
 * @author NIIT Technologies Ltd
 * @version 1.0
 */
@ApiModel
@Component
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ShipmentPartSuffix extends BaseBO {
	/**
	 * The default serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;
	private String carrierCode;
	private String awbPrefix;
	private String crossBookingFlag;
	private String crossBookCarrier;
	private String startPrefix;
	private String endPrefix;
	private String primaryIdentifier;
	private String excludePrefix;
	private String createdUserCode;
	private String newSuffix;
	private transient LocalDateTime  createdDateTime; 
	private String lastUpdatedUserCode;
	private transient LocalDateTime lastUpdatedDateTime;
	
	private String flagUpdate ="Y";
  
}      
