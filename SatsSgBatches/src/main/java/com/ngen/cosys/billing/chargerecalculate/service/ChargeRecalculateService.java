package com.ngen.cosys.billing.chargerecalculate.service;

import com.ngen.cosys.framework.exception.CustomException;

public interface ChargeRecalculateService {

   void recalculateCharges() throws CustomException;

}
