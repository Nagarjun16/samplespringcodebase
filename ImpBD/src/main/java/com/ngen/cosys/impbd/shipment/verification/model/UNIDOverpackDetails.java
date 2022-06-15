/**
 * UNIDOverpackDetails.java
 * 
 * Copyright <PRE><IMG SRC = XX></IMG></PRE>
 *
 * Version      Date            Author      Reason
 * 1.0         06 MAR, 2018    NIIT        -
 */
package com.ngen.cosys.impbd.shipment.verification.model;


import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.validator.constraints.Length;
import org.springframework.stereotype.Component;

import com.ngen.cosys.framework.model.BaseBO;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
/**
 * This class takes care of the responsibilities related to the 
 * DG UNID overpack details data
 * 
 * @author NIIT Technologies Ltd
 *
 */
@ApiModel
@XmlRootElement
@Component
@ToString
@Getter
@Setter
@NoArgsConstructor
public class UNIDOverpackDetails extends BaseBO  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long expDgShipperDeclarationId;
	private Short dgdReferenceNo;
	private Long dgRegulationId;
	@Length(max = 1, message = "ERROR_ENTER_1_CHR_AUTOMANUALFLAG_FIELD")
	private String autoManualFlag;
	@Length(max = 4, message = "ERROR_ENTER_4_NUMBER_IN_OVERPACKNUMBER_FIELD")
	private Short overpackNumber;
}
