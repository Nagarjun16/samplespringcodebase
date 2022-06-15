package com.ngen.cosys.billing.sap.service;

import java.io.FileNotFoundException;
import java.io.IOException;

import com.ngen.cosys.framework.exception.CustomException;

public interface SapInboundFileProcessor {

   public void processFile() throws CustomException, IOException ;

   public String getInboundFileFolder(String folderId) throws CustomException;

   void processFile1() throws CustomException, IOException;
}
