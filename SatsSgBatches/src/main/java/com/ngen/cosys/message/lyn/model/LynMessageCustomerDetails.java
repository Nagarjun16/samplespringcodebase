package com.ngen.cosys.message.lyn.model;

import java.math.BigInteger;

import com.ngen.cosys.framework.model.BaseBO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LynMessageCustomerDetails extends BaseBO {
	private String segment;
	private String countryCode;
	private String carrier;
	private String flightKey;
	private BigInteger interfaceMessageDefinitionByCustomerId;
}
