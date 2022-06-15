package com.ngen.cosys.billing.gstirn.model;

import java.math.BigInteger;
import java.util.List;

import com.ngen.cosys.framework.model.BaseBO;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class IRNPostingModel extends BaseBO {

	/**
	 * default
	 */
	private static final long serialVersionUID = 1L;

	private String tenantId;

	private Boolean fetchById;

	private List<BigInteger> paymentReceiptIds;

}
