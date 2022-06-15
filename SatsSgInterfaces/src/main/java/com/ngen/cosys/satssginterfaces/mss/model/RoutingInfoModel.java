package com.ngen.cosys.satssginterfaces.mss.model;

import java.math.BigInteger;

import com.ngen.cosys.framework.model.BaseBO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RoutingInfoModel extends BaseBO{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8718822209288310951L;
	private BigInteger shipmentId;
	private String fromPoint;
	private String carrier;

}
