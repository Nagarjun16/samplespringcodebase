package com.ngen.cosys.satssginterfaces.mss.model;

import java.math.BigInteger;

import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.stereotype.Component;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@ApiModel
@XmlRootElement
@Component
@Getter
@Setter
@NoArgsConstructor
public class UnloadShipmentSHCs  extends SHC{	
	/**
	 * System generated serial version id
	 */
	private static final long serialVersionUID = -1241318379436021184L;
	private BigInteger shipmentInventoryId;

}
