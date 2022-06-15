package com.ngen.cosys.billing.sap.service;

import com.ngen.cosys.framework.exception.CustomException;

public interface SapOutboundFileProcessor {
   public void createFile() throws CustomException;

}
