package com.ngen.cosys.icms.model.operationFlight;

import java.math.BigInteger;

import org.springframework.stereotype.Component;

import com.ngen.cosys.framework.model.BaseBO;

import groovy.transform.ToString;
import lombok.Getter;
import lombok.Setter;

@Component
@Getter
@Setter
@ToString
public class OperationalFlightHandlingArea extends BaseBO {

	private static final long serialVersionUID = 1L;

	private long fltHandlingAreaId;

	private BigInteger flightId;

	private String terminalCode;

	private String createdUserId = "COSYS";
	private String modifiedUserId = "COSYS";

}
