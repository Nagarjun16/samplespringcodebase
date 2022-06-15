package com.ngen.cosys.satssginterfaces.mss.model;
 import java.math.BigInteger;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.stereotype.Component;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
@ApiModel
@XmlRootElement
@Component
@ToString
@Getter
@Setter
@NoArgsConstructor
public class BuildUpShipment extends Shipment {	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BigInteger uldSequenceId;
	//private List<ShipmentInventory> inventoryList;
	private List<SHC> shcList;
	private List<ShipmentHouse> houseList;

}
