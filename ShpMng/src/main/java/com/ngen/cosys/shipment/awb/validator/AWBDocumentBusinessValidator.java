package com.ngen.cosys.shipment.awb.validator;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.framework.model.BaseBO;
import com.ngen.cosys.shipment.awb.model.AWB;

public interface AWBDocumentBusinessValidator {
  
   
   AWB validate(BaseBO baseModel)throws CustomException;
}