package com.ngen.cosys.impbd.tracing.activity.model;

import java.math.BigInteger;

import com.ngen.cosys.framework.model.BaseBO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TracingActivityShipmentShcModel extends BaseBO {

	/**
	 * System generated serial version id
	 */
	private static final long serialVersionUID = 8553990636110685094L;

	private BigInteger id;
	private BigInteger tracingActivityShipmentId;
	private String specialHandlingCode;

}