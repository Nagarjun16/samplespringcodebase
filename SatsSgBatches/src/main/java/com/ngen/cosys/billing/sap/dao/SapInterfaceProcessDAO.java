package com.ngen.cosys.billing.sap.dao;

import java.util.List;

import com.ngen.cosys.billing.sap.model.SAPFileErrorInfo;
import com.ngen.cosys.billing.sap.model.SAPFileInfo;
import com.ngen.cosys.framework.exception.CustomException;

public interface SapInterfaceProcessDAO {

   public void updateCustomerPostingStatus(List<Long> billGenIds) throws CustomException;

   public String getOutboundFileFolder(String folderId) throws CustomException;

   public String getInboundFileFolder(String folderId) throws CustomException;

   public SAPFileInfo saveSAPFileInfo(SAPFileInfo sapFileInfo) throws CustomException;

   public SAPFileInfo updateInvoiceAndCreditFileInfo(SAPFileInfo sapFileInfo) throws CustomException;

   public List<SAPFileErrorInfo> saveFileParsingErrorMsg(List<SAPFileErrorInfo> sapFileErrorInfo) throws CustomException;

   public String getCurrentDate() throws CustomException;

   public int getFileStatus(String fileName) throws CustomException;

   public int checkOutBoundFileGeneratedOrNot() throws CustomException;

   public void saveOutBoundSAPFileInfo(SAPFileInfo sapFileInfo) throws CustomException;

  
}
