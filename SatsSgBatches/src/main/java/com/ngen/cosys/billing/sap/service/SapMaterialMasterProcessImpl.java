package com.ngen.cosys.billing.sap.service;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ngen.cosys.billing.sap.dao.SapMaterialMasterProcessDAO;
import com.ngen.cosys.billing.sap.enums.FileFolder;
import com.ngen.cosys.billing.sap.enums.FileNamePrefix;
import com.ngen.cosys.billing.sap.model.MaterialInfo;
import com.ngen.cosys.billing.sap.model.ParsedFileData;
import com.ngen.cosys.billing.sap.model.SAPFileControlTotal;
import com.ngen.cosys.billing.sap.model.SAPFileErrorInfo;
import com.ngen.cosys.billing.sap.model.SAPFileInfo;
import com.ngen.cosys.billing.sap.model.SAPFileRecord;
import com.ngen.cosys.framework.constant.ErrorType;
import com.ngen.cosys.framework.exception.CustomException;

import lombok.Getter;
import lombok.Setter;

@Service("sapMaterialMasterProcessImpl")
public class SapMaterialMasterProcessImpl extends SapInFileProcessor {

   @Autowired
   private SapMaterialMasterProcessDAO sapMaterialMasterProcessDAO;

   @Autowired
   private Validator validator;

   private static Logger logger = LoggerFactory.getLogger(SapMaterialMasterProcessImpl.class);

   @Getter
   @Setter
   String interfaceType = "MATERIAL_MASTER";

   /**
    * parsing material information and validating mandatory field
    * 
    * @param String
    * @param ParsedFileData
    * 
    */

   private void parseForMaterialInfo(String strLine, ParsedFileData parsedFileData) {
      MaterialInfo materialInfo = new MaterialInfo();
      materialInfo.setCompanyCode(strLine.substring(1, 5));
      materialInfo.setSalesOrganization(strLine.substring(5, 9).trim());
      materialInfo.setDistributionChannel(strLine.substring(9, 11).trim());
      materialInfo.setDivision(strLine.substring(11, 13).trim());
      materialInfo.setMaterialNumber(strLine.substring(13, 31).trim());
      materialInfo.setMaterialDescription(strLine.substring(31, 71).trim());
      materialInfo.setPricingType(strLine.substring(74, 83).trim());
      materialInfo.setUnitOfMeasurement(strLine.substring(71, 74).trim());
      materialInfo.setIndicator(strLine.substring(83, 84).trim());

      Set<ConstraintViolation<MaterialInfo>> violations = this.validator.validate(materialInfo);
      violations.forEach(item -> {
         SAPFileErrorInfo errorInfo = new SAPFileErrorInfo();
         errorInfo.setLineNo(parsedFileData.getCurrentLineNo());
         errorInfo.setReferenceInfo(materialInfo.getMaterialNumber());
         errorInfo.setProcessingInfo(item.getMessage());
         parsedFileData.getProccessingExceptionList().add(errorInfo);
      });
      parsedFileData.getMaterialInfoList().add(materialInfo);
   }

   /**
    * parsing file data
    * 
    * @param String
    * @param SAPFileRecord
    * @param ParsedFileData
    * @throws CustomException
    * 
    */

   @Override
   protected void parseForDataLine(String strLine, SAPFileRecord record, ParsedFileData parsedFileData)
         throws CustomException {
      if (record.getRecordType().equalsIgnoreCase("L")) {
         // File Details (Material Info)
         this.parseForMaterialInfo(strLine, parsedFileData);
      } else if (record.getRecordType().equalsIgnoreCase("R")) {
         // File Details (Control totals)
         this.parseForMaterialControlTotal(strLine, parsedFileData);
      }

   }

   /**
    * parsing control total of file and validating mandatory field
    * 
    * @param String
    * @param ParsedFileData
    * @throws CustomException
    */

   private void parseForMaterialControlTotal(String strLine, ParsedFileData parsedFileData) throws CustomException {

      SAPFileControlTotal controlTotal = new SAPFileControlTotal();
      controlTotal.setCompanyCode(strLine.substring(1, 5));
      controlTotal.setSalesOrganization(strLine.substring(5, 9).trim());
      controlTotal.setDistributionChannel(strLine.substring(9, 11).trim());
      controlTotal.setDivision(strLine.substring(11, 13).trim());
      String strConTotal = strLine.substring(13, 23).trim();
      int conTotal = 0;
      if (strConTotal.isEmpty()) {
         SAPFileErrorInfo errorInfo = new SAPFileErrorInfo();
         errorInfo.setLineNo(1);
         errorInfo.setReferenceInfo(null);
         errorInfo.setProcessingInfo("Control total is required");
         parsedFileData.getProccessingExceptionList().add(errorInfo);
      } else {
         try {
            conTotal = Integer.valueOf(strConTotal);
         } catch (Exception e) {
            SAPFileErrorInfo errorInfo = new SAPFileErrorInfo();
            errorInfo.setLineNo(1);
            errorInfo.setReferenceInfo(null);
            errorInfo.setProcessingInfo("Exception occur during pasrsing Control total");
            parsedFileData.getProccessingExceptionList().add(errorInfo);

         }
      }
      parsedFileData.getSapFileInfo().setControlTotalCount(conTotal);
      parsedFileData.getSapFileInfo().setTotalLineCount(conTotal);

      Set<ConstraintViolation<SAPFileControlTotal>> violations = this.validator.validate(controlTotal);
      violations.forEach(item -> {
         SAPFileErrorInfo errorInfo = new SAPFileErrorInfo();
         errorInfo.setLineNo(parsedFileData.getCurrentLineNo());
         errorInfo.setReferenceInfo(null);
         errorInfo.setProcessingInfo(item.getMessage());
         parsedFileData.getProccessingExceptionList().add(errorInfo);
      });
      this.validatingTotalNumberOfLineCount(parsedFileData);
   }

   /**
    * saving file data in database
    * 
    * @param ParsedFileData
    * @throws CustomException
    */

   @Override
   @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
   public void saveFileData(ParsedFileData parsedFileData) throws CustomException {
      sapMaterialMasterProcessDAO.saveSapMaterialMasterInfo(parsedFileData.getMaterialInfoList());

   }

   /**
    * validating file header information
    * 
    * @param SAPFileInfo
    * @throws CustomException
    * 
    */

   @Override
   protected void validatingFileHeader(SAPFileInfo sapFileInfo) throws CustomException {
      logger.info("Validating the filename and date and time {}", ' ');
      if (!sapFileInfo.getFileName().substring(0, 4).equalsIgnoreCase("O094"))
         throw new CustomException("FileValidatingFailedAsFileNameNotMacthed", null,
               ErrorType.ERROR);

   }

   /**
    * validating file number of count
    * 
    * @param ParsedFileData
    * @throws CustomException
    * 
    */

   private void validatingTotalNumberOfLineCount(ParsedFileData parsedFileData) throws CustomException {
      logger.info("Validating the actual record count and the count mentioned in the file control count {}", ' ');

      if (parsedFileData.getSapFileInfo().getControlTotalCount() != parsedFileData.getMaterialInfoList().size()) {
         logger.error(
               "Validating of file is failed due to actual record count and the count mentioned in the file control count {}",
               ' ');
         throw new CustomException(
               "ValidatingFileFailedDueToRecordCount",
               null, ErrorType.ERROR);
      }
   }

   @Override
   public void moveFileInArchivalFolder(String processingFolder) throws CustomException {
      try {
         String archivalFolder = sapInterfaceProcessDAO
               .getInboundFileFolder(FileFolder.SAP_O094_INBOUND_ARCHIVED.toString());
         sapFileReader.movingFileByFolder(processingFolder, archivalFolder);
      } catch (Exception e) {
         throw new CustomException();
      }

   }

   @Override
   public String getProcessingFolder() throws CustomException {
      return sapInterfaceProcessDAO.getInboundFileFolder(FileFolder.SAP_O094_INBOUND_PROCESSING.toString());
   }

   @Override
   public InputStream readFileAndCreateFileInputStream(String processingFolder)
         throws CustomException, FileNotFoundException {
      String toProcessFolder = sapInterfaceProcessDAO
            .getInboundFileFolder(FileFolder.SAP_O094_INBOUND_TOPROCESS.toString());
      return sapFileReader.getFileInputStream(toProcessFolder, processingFolder,
            FileNamePrefix.MATERIAL_MASTER.toString());
   }

   @Override
   public String getFileName() throws CustomException {
      String toProcessFolder = sapInterfaceProcessDAO
            .getInboundFileFolder(FileFolder.SAP_O094_INBOUND_TOPROCESS.toString());
      return sapFileReader.getFileName(toProcessFolder);
   }

   @Override
   public String getToProcessFolder() throws CustomException {
      return sapInterfaceProcessDAO.getInboundFileFolder(FileFolder.SAP_O094_INBOUND_TOPROCESS.toString());
   }

   @Override
   public String checkFileExistOrNotInDB(String fileName) throws CustomException {
      return sapInterfaceProcessDAO.getFileStatus(fileName) + "";
   }

   @Override
   public String getArchivalFolder() throws CustomException {
      return sapInterfaceProcessDAO.getInboundFileFolder(FileFolder.SAP_O094_INBOUND_ARCHIVED.toString());
   }

}
