package com.ngen.cosys.billing.sap.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ngen.cosys.billing.sap.dao.SapInterfaceProcessDAO;
import com.ngen.cosys.billing.sap.enums.ProcessingStatus;
import com.ngen.cosys.billing.sap.model.ParsedFileData;
import com.ngen.cosys.billing.sap.model.SAPFileErrorInfo;
import com.ngen.cosys.billing.sap.model.SAPFileInfo;
import com.ngen.cosys.billing.sap.model.SAPFileRecord;
import com.ngen.cosys.billing.sap.utility.SAPFileReader;
import com.ngen.cosys.framework.constant.ErrorType;
import com.ngen.cosys.framework.exception.CustomException;

import lombok.Getter;
import lombok.Setter;

public abstract class SapInFileProcessor implements SapInboundFileProcessor {

   @Autowired
   protected SapInterfaceProcessDAO sapInterfaceProcessDAO;

   @Autowired
   protected SAPFileReader sapFileReader;

   @Getter
   @Setter
   private String interfaceType = "UNDEFINED";

   private static String userId = "SAP_INTERFACE";

   /**
    * Manage the parsing inbound file, and delegates the line parsing to
    * specialized implementation
    * 
    * @param InputStream
    * @return ParsedFileData
    * @throws CustomException
    * @throws IOException
    * 
    */
   @Override
   public void processFile() throws CustomException, FileNotFoundException {
      ParsedFileData parsedFileData = null;
      String processingFolder = this.getProcessingFolder();// getprocessingFolder
      String toProcessFolder = this.getToProcessFolder();// getProcessFolder
      String archivalFolder = this.getArchivalFolder();// getArchivalFolder
      File toProcessFiles = new File(toProcessFolder);
      File processFiles = new File(processingFolder);
      File archivalFiles = new File(archivalFolder);

      // check weather folder exist or not
      if (toProcessFiles.exists() && toProcessFiles.isDirectory()) {
         File[] listOfFiles = toProcessFiles.listFiles();
         Arrays.sort(listOfFiles);
         if (listOfFiles.length > 0) {
            for (int i = 0; i < listOfFiles.length; i++) {
               if (listOfFiles[i].isFile()) {
                  String checkFileStatus = this.checkFileExistOrNotInDB(listOfFiles[i].getName());
                  if (checkFileStatus.equalsIgnoreCase("0")) {
                     sapFileReader.moveFileDestination(listOfFiles[i], processFiles);
                     InputStream dataFileStream = null;
                     try {
                        dataFileStream = sapFileReader.readSingleFileAndCreateFileInputStream(processingFolder,
                              listOfFiles[i].getName());
                     } catch (IOException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                     }
                     parsedFileData = new ParsedFileData();
                     parsedFileData.getSapFileInfo().setFileName(listOfFiles[i].getName());
                     // 1. save file meta data
                     this.saveFileMetaData(parsedFileData);
                     String strLine;
                     String errorMessage = null;
                     boolean trailerCount = false;

                     if (dataFileStream != null) {
                        try (BufferedReader br = new BufferedReader(new InputStreamReader(dataFileStream))) {
                           // reading file line
                           while ((strLine = br.readLine()) != null) {
                              // 1. Parse for Record & LineType
                              parsedFileData.setCurrentLineNo(parsedFileData.getCurrentLineNo() + 1);
                              SAPFileRecord record = this.parseForRecordAndLineType(strLine);
                              //
                              if (record.getRecordType().equalsIgnoreCase("A")) {
                                 // Header record
                                 this.parseForFileHeader(strLine, parsedFileData);
                                 this.validatingFileHeader(parsedFileData.getSapFileInfo());
                              } else if (record.getRecordType().equalsIgnoreCase("Z")) {
                                 // Trailer record
                                 this.parseForFileTrailer(strLine, parsedFileData);
                                 this.validatingFileTrailer(parsedFileData);
                                 trailerCount = true;
                              } else if (trailerCount) {
                                 // after trailer if any record found file consider is invalid
                                 String message = "InvalidFileDueToExtraLine";
                                 throw new CustomException(message, null, ErrorType.ERROR);

                              } else {
                                 // file format specific parsing linesq
                                 this.parseForDataLine(strLine, record, parsedFileData);
                              }
                           }

                           // 2. Insert into Interface specific data
                           this.saveFileData(parsedFileData);

                        } catch (CustomException e) {
                           // catch and record excpetion in DB
                           errorMessage = e.getErrorCode();
                        } catch (Exception e) {
                           System.out.println(e);
                           errorMessage = "File format is invalid";
                        } finally {
                           parsedFileData.getSapFileInfo().setProcessingInfo(errorMessage);

                        }
                     } else {
                        parsedFileData.getSapFileInfo().setProcessingInfo("Not found valid file");
                     }

                     // After parsing, update data in DB

                     // 1. Update file meta data
                     this.updateFileMetaData(parsedFileData);
                     this.moveFileInArchivalFolder(processingFolder);
                  } else {
                     // insert file as new record with failed status
                     parsedFileData = new ParsedFileData();
                     parsedFileData.getSapFileInfo().setInterfaceType(this.getInterfaceType());
                     parsedFileData.getSapFileInfo().setCreatedUserCode(userId);
                     parsedFileData.getSapFileInfo().setProcessedStatus(ProcessingStatus.FAILED.toString());
                     parsedFileData.getSapFileInfo().setProcessingInfo("File already processed");
                     parsedFileData.getSapFileInfo().setFileName(listOfFiles[i].getName());
                     sapInterfaceProcessDAO.saveSAPFileInfo(parsedFileData.getSapFileInfo());
                     sapFileReader.moveFileDestination(listOfFiles[i], archivalFiles);
                  }
               }
            }

         } else {
            try {
               throw new FileNotFoundException("File not Found");
            } catch (FileNotFoundException e) {
               // TODO Auto-generated catch block
               e.printStackTrace();
            }
         }
      } else {
         throw new CustomException("custom.folder.not.exist", "", "");
      }

   }

   public void processFile1() throws CustomException, IOException {
      String processingFolder = this.getProcessingFolder();
      String fileName = this.getFileName();
      InputStream dataFileStream = this.readFileAndCreateFileInputStream(processingFolder);
      ParsedFileData parsedFileData = new ParsedFileData();
      parsedFileData.getSapFileInfo().setFileName(fileName);
      // 1. save file meta data
      this.saveFileMetaData(parsedFileData);
      String strLine;
      String errorMessage = null;
      boolean trailerCount = false;

      if (dataFileStream != null) {
         try (BufferedReader br = new BufferedReader(new InputStreamReader(dataFileStream))) {
            // reading file line
            while ((strLine = br.readLine()) != null) {
               // 1. Parse for Record & LineType
               parsedFileData.setCurrentLineNo(parsedFileData.getCurrentLineNo() + 1);
               SAPFileRecord record = this.parseForRecordAndLineType(strLine);
               //
               if (record.getRecordType().equalsIgnoreCase("A")) {
                  // Header record
                  this.parseForFileHeader(strLine, parsedFileData);
                  this.validatingFileHeader(parsedFileData.getSapFileInfo());
               } else if (record.getRecordType().equalsIgnoreCase("Z")) {
                  // Trailer record
                  this.parseForFileTrailer(strLine, parsedFileData);
                  this.validatingFileTrailer(parsedFileData);
                  trailerCount = true;
               } else if (trailerCount) {
                  // after trailer if any record found file consider is invalid
                  String message = "InvalidFileDueToExtraLine";
                  throw new CustomException(message, null, ErrorType.ERROR);

               } else {
                  // file format specific parsing lines
                  this.parseForDataLine(strLine, record, parsedFileData);
               }
            }

            // 2. Insert into Interface specific data
            this.saveFileData(parsedFileData);

         } catch (CustomException e) {
            // catch and record excpetion in DB
            errorMessage = e.getErrorCode();
         } catch (Exception e) {
            System.out.println(e);
            errorMessage = "File format is invalid";
         } finally {
            parsedFileData.getSapFileInfo().setProcessingInfo(errorMessage);

         }
      } else {
         parsedFileData.getSapFileInfo().setProcessingInfo("Not found valid file");
      }

      // After parsing, update data in DB

      // 1. Update file meta data
      this.updateFileMetaData(parsedFileData);
      this.moveFileInArchivalFolder(processingFolder);
   }

   public abstract String getArchivalFolder() throws CustomException;

   public abstract String checkFileExistOrNotInDB(String toProcessFolder) throws CustomException;

   public abstract String getToProcessFolder() throws CustomException;

   public abstract String getFileName() throws CustomException;

   public abstract InputStream readFileAndCreateFileInputStream(String toProcessFolder)
         throws CustomException, FileNotFoundException;

   public abstract String getProcessingFolder() throws CustomException;

   public abstract void moveFileInArchivalFolder(String toProcessingFolder) throws CustomException;

   /**
    * saving file mata data
    * 
    * @param ParsedFileData
    * @throws CustomException
    * 
    */

   @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
   public void saveFileMetaData(ParsedFileData parsedFileData) throws CustomException {
      parsedFileData.getSapFileInfo().setInterfaceType(this.getInterfaceType());
      parsedFileData.getSapFileInfo().setProcessedStatus(ProcessingStatus.IN_PROGRESS.toString());
      parsedFileData.getSapFileInfo().setCreatedUserCode(userId);
      sapInterfaceProcessDAO.saveSAPFileInfo(parsedFileData.getSapFileInfo());

   }

   /**
    * update file mata data and saving parsing error in DB
    * 
    * @param ParsedFileData
    * @throws CustomException
    * 
    */

   @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
   public void updateFileMetaData(ParsedFileData parsedFileData) throws CustomException {

      if (parsedFileData.getSapFileInfo().getProcessingInfo() != null
            && !parsedFileData.getSapFileInfo().getProcessingInfo().isEmpty()) {
         parsedFileData.getSapFileInfo().setProcessedStatus(ProcessingStatus.FAILED.toString());

      } else {
         parsedFileData.getSapFileInfo().setProcessedStatus(ProcessingStatus.SUCCESSFUL.toString());

      }

      if (parsedFileData.getSapFileInfo().getSapFileInfoId() != 0L) {
         sapInterfaceProcessDAO.updateInvoiceAndCreditFileInfo(parsedFileData.getSapFileInfo());
      } else {
         sapInterfaceProcessDAO.saveSAPFileInfo(parsedFileData.getSapFileInfo());
      }

      if (parsedFileData.getProccessingExceptionList() != null
            && !parsedFileData.getProccessingExceptionList().isEmpty()) {
         parsedFileData.getProccessingExceptionList()
               .forEach(iacn -> iacn.setSapFileInfoId(parsedFileData.getSapFileInfo().getSapFileInfoId()));

         sapInterfaceProcessDAO.saveFileParsingErrorMsg(parsedFileData.getProccessingExceptionList());
      }

   }

   /**
    * data to the process by implemetnation of the Interface messge type / file
    * type
    * 
    * @param ParsedFileData
    * @throws CustomException
    * 
    */
   public abstract void saveFileData(ParsedFileData parsedFileData) throws CustomException;

   /**
    * parsing file data
    * 
    * @param ParsedFileData
    * @throws CustomException
    * 
    */
   protected abstract void parseForDataLine(String strLine, SAPFileRecord record, ParsedFileData parsedFileData)
         throws CustomException;

   /**
    * validating of file header information
    * 
    * @param strLine
    * @throws CustomException
    * 
    */

   protected abstract void validatingFileHeader(SAPFileInfo sapFileInfo) throws CustomException;

   /**
    * parsing file header information
    * 
    * @param strLine
    * @param ParsedFileData
    * 
    */

   private void parseForFileHeader(String strLine, ParsedFileData parsedFileData) {

      parsedFileData.getSapFileInfo().setCompanyCode(strLine.substring(1, 5));
      String fileName = strLine.substring(5, 9) + strLine.substring(9, 13) + strLine.substring(13, 15)
            + strLine.substring(15, 21) + strLine.substring(21, 25);
      parsedFileData.getSapFileInfo().setFileName(fileName);
      String fileCreationDt = this.parseFileDateAndTime(strLine);
      parsedFileData.getSapFileInfo().setFileCreationDt(fileCreationDt);

      if (parsedFileData.getSapFileInfo().getFileName().isEmpty()) {
         SAPFileErrorInfo errorInfo = new SAPFileErrorInfo();
         errorInfo.setLineNo(1);
         errorInfo.setReferenceInfo(null);
         errorInfo.setProcessingInfo("File name is required");
         parsedFileData.getProccessingExceptionList().add(errorInfo);

      }

      if (parsedFileData.getSapFileInfo().getFileCreationDt().isEmpty()) {
         SAPFileErrorInfo errorInfo = new SAPFileErrorInfo();
         errorInfo.setLineNo(1);
         errorInfo.setReferenceInfo(null);
         errorInfo.setProcessingInfo("File creation date is required");
         parsedFileData.getProccessingExceptionList().add(errorInfo);

      }

   }

   /**
    * validating of file trailer information
    * 
    * @param strLine
    * @throws Exception
    * 
    */
   protected void validatingFileTrailer(ParsedFileData parsedFileData) throws CustomException {

   }

   /**
    * parsing file trailer information
    * 
    * @param strLine
    * @param ParsedFileData
    *           @throws
    * 
    */
   private void parseForFileTrailer(String strLine, ParsedFileData parsedFileData) {

      parsedFileData.getSapFileInfo().setRecordType(strLine.substring(0, 1));
      parsedFileData.getSapFileInfo().setCompanyCode(strLine.substring(1, 5));
      String strTotalLine = strLine.substring(5, 15).trim();
      int totalLine = 0;

      if (strTotalLine.isEmpty()) {
         SAPFileErrorInfo errorInfo = new SAPFileErrorInfo();
         errorInfo.setLineNo(1);
         errorInfo.setReferenceInfo(null);
         errorInfo.setProcessingInfo("Total line is required");
         parsedFileData.getProccessingExceptionList().add(errorInfo);
      } else {
         try {
            totalLine = Integer.valueOf(strTotalLine);
         } catch (Exception e) {
            SAPFileErrorInfo errorInfo = new SAPFileErrorInfo();
            errorInfo.setLineNo(1);
            errorInfo.setReferenceInfo(null);
            errorInfo.setProcessingInfo("Exception occur during pasrsing total line");
            parsedFileData.getProccessingExceptionList().add(errorInfo);

         }
      }

      parsedFileData.getSapFileInfo().setTotalLineCount(totalLine);
   }

   /**
    * parsing file creation date and time in date formate
    * 
    * @param strLine
    * @return String
    * 
    */

   protected String parseFileDateAndTime(String strLine) {
      String dateInString = strLine.substring(25, 33);
      String timeInString = strLine.substring(33, 39);
      StringBuilder builder = new StringBuilder();
      builder.append(dateInString.substring(0, 4));
      builder.append("-");
      builder.append(dateInString.substring(4, 6));
      builder.append("-");
      builder.append(dateInString.substring(6, 8));
      builder.append(" ");
      builder.append(timeInString.substring(0, 2));
      builder.append(":");
      builder.append(timeInString.substring(2, 4));
      builder.append(":");
      builder.append(timeInString.substring(4, 6));
      return builder.toString();
   }

   /**
    * This method will parse file basic information
    * 
    * @param strLine
    * @return SAPFileRecord
    * 
    */

   protected SAPFileRecord parseForRecordAndLineType(String strLine) {
      SAPFileRecord sapFileRecord = new SAPFileRecord();
      sapFileRecord.setRecordType(strLine.substring(0, 1));
      return sapFileRecord;
   }

   @Override
   public String getInboundFileFolder(String folderId) throws CustomException {
      return sapInterfaceProcessDAO.getInboundFileFolder(folderId);
   }

}
