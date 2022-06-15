package com.ngen.cosys.satssginterfaces.mss.model;

import javax.xml.bind.annotation.XmlRootElement;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ApiModel
@XmlRootElement
@ToString
@Getter
@Setter
@NoArgsConstructor
public class ManifestHouse extends ShipmentHouse{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long shipmentHouseInfoId;
	private long shipmentInfoId;
}
