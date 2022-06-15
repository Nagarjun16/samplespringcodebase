package com.ngen.cosys.application.dao;

import java.math.BigInteger;
import java.util.List;

import com.ngen.cosys.application.model.RcsSchedulerDetail;
import com.ngen.cosys.framework.exception.CustomException;

public interface RcsSchedulerDao {
	public List<RcsSchedulerDetail> getList() throws CustomException;

	public BigInteger getIsShipmentEawb(String shipmentNumber) throws CustomException;

}
