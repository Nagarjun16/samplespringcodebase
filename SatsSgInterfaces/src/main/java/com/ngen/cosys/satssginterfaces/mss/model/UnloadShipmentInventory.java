package com.ngen.cosys.satssginterfaces.mss.model;

import java.math.BigInteger;
import java.util.List;

import org.springframework.validation.annotation.Validated;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Validated
public class UnloadShipmentInventory extends ShipmentInventory {

	/**
	 * System generated serial version id
	 */
	Flight flight;
	private static final long serialVersionUID = 3216781916663473231L;
	private List<String> shcCodes;
	private List<String> houseNumbers;
	private String assUldTrolleyNumber; 
	private BigInteger loadedShipmentInfoId;
	private String code;
	
	

	
	
}
