package com.ngen.cosys.billing.sap.service;

import java.io.FileNotFoundException;
import java.io.InputStream;

import org.springframework.stereotype.Service;

import com.ngen.cosys.billing.sap.enums.FileFolder;
import com.ngen.cosys.billing.sap.enums.FileNamePrefix;
import com.ngen.cosys.framework.exception.CustomException;

@Service("sapCreditNoteProcessImpl")
public class SapCreditNoteService extends SapInvoiceAndCreditNoteProcessImpl {

   @Override
   public void moveFileInArchivalFolder(String processingFolder) throws CustomException {
      try {
         String archivalFolder = sapInterfaceProcessDAO.getInboundFileFolder(FileFolder.SAP_O097_INBOUND_ARCHIVED.toString());
         sapFileReader.movingFileByFolder(processingFolder, archivalFolder);
      } catch (Exception e) {
         throw new CustomException();
      }
      
      
   }

   @Override
   public String getProcessingFolder() throws CustomException {
      return sapInterfaceProcessDAO.getInboundFileFolder(FileFolder.SAP_O097_INBOUND_PROCESSING.toString());
   }
   
   @Override
   public String getToProcessFolder() throws CustomException {
	   String toProcessFolder=sapInterfaceProcessDAO.getInboundFileFolder(FileFolder.SAP_O097_INBOUND_TOPROCESS.toString());
      return toProcessFolder;
   }

   @Override
   public InputStream readFileAndCreateFileInputStream(String processingFolder) throws CustomException, FileNotFoundException {
      String toProcessFolder = sapInterfaceProcessDAO.getInboundFileFolder(FileFolder.SAP_O097_INBOUND_TOPROCESS.toString());
      return sapFileReader.getFileInputStream(toProcessFolder, processingFolder, FileNamePrefix.CREDIT_NOTE.toString());
   }
   
   @Override
   public String getFileName() throws CustomException {
      String toProcessFolder = sapInterfaceProcessDAO.getInboundFileFolder(FileFolder.SAP_O097_INBOUND_TOPROCESS.toString());
      return sapFileReader.getFileName(toProcessFolder);
   }
   
	

}
