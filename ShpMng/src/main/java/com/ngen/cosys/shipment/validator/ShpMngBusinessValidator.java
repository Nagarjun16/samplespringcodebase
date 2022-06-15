package com.ngen.cosys.shipment.validator;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.framework.model.BaseBO;

public interface ShpMngBusinessValidator {

	BaseBO validate(BaseBO baseModel) throws CustomException;

}