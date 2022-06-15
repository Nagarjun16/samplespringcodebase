package com.ngen.cosys.impbd.shipment.remarks.model;

import java.math.BigInteger;

import javax.xml.bind.annotation.XmlRootElement;

import com.ngen.cosys.model.ShipmentModel;

import groovy.transform.ToString;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@XmlRootElement
@ApiModel
@ToString
@Setter
@Getter
@NoArgsConstructor
public class ShipmentRemarksModel extends ShipmentModel{
	
	/**
	 * System generated serial version id 
	 */
	private static final long serialVersionUID = -171427877249099384L;
	
	private BigInteger id;
	private BigInteger flightId;
	private String remarkType;
	private String shipmentRemarks;
	

}
