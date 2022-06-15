package com.ngen.cosys.satssginterfaces.mss.model;

import java.math.BigInteger;

import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.stereotype.Component;

import com.ngen.cosys.framework.model.BaseBO;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
@NoArgsConstructor
public class SHCS extends BaseBO {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BigInteger inboundBreakDownShipmentId;
	private String code;
	private BigInteger expPreOffloadShipmentInfoId;
	private BigInteger expOffloadShipmentInfoId;
	private BigInteger loadedShipmentInfoId;
	private BigInteger shipmentId;
	private BigInteger shipmentInventoryId;
	private BigInteger refrenceId;
	private BigInteger transTTWAConnectingFlightShipmentSHCId;
	private BigInteger transTTWAConnectingFlightShipmentId;
	private BigInteger transTTWAConnectingFlightULDId;

}
