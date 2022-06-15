package com.ngen.cosys.billing.chargerecalculate.dao;

import java.math.BigInteger;
import java.util.List;

import com.ngen.cosys.framework.exception.CustomException;

public interface ChargeRecalculateDAO {

   List<BigInteger> fetchShipmentsForRecalculation() throws CustomException;

}
