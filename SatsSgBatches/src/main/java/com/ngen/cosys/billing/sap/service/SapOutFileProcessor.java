package com.ngen.cosys.billing.sap.service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ngen.cosys.billing.sap.dao.SapInterfaceProcessDAO;
import com.ngen.cosys.billing.sap.enums.ProcessingStatus;
import com.ngen.cosys.billing.sap.model.ParsedFileData;
import com.ngen.cosys.billing.sap.utility.SAPFileReader;
import com.ngen.cosys.framework.exception.CustomException;

import lombok.Getter;
import lombok.Setter;

public abstract class SapOutFileProcessor implements SapOutboundFileProcessor {

   @Autowired
   protected SapInterfaceProcessDAO sapInterfaceProcessDAO;

   @Autowired
   protected SAPFileReader sapFileReader;

   @Getter
   @Setter
   private String interfaceType = "UNDEFINED";

   private static String userId = "SAP_INTERFACE";

   /**
    * Manage the create Outbound file, and delegates to specialized implementation
    * 
    * @throws CustomException
    * @throws Exception
    * 
    */
   @Override
   @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
   public void createFile() throws CustomException {

      // check file created once in day or not
      int checkFileStatus = sapInterfaceProcessDAO.checkOutBoundFileGeneratedOrNot();
      // data count
      int dataCount = 0;
      String strDate = convertDateAndTime(sapInterfaceProcessDAO.getCurrentDate());
      String seqNumber = this.getSequenceNumber();
      String folderName = this.getFileFolderDetails(strDate, seqNumber);
      if (checkFileStatus == 0) {

         // Create our BufferedWriter.
         BufferedWriter writer;
         try {
            writer = new BufferedWriter(new FileWriter(folderName));

            // file header writer method
            this.writingFileHeader(writer, seqNumber, strDate);

            // file data writer method
            dataCount = this.writingFileData(writer);

            // file data trailer method
            this.writingFileTrailer(writer, dataCount);

            writer.close();

            File file = new File(folderName);
            if (file.exists()) {
               ParsedFileData parsedFileData = new ParsedFileData();
               parsedFileData.getSapFileInfo().setInterfaceType("SALES_AND_DISTRIBUTION");
               parsedFileData.getSapFileInfo().setCreatedUserCode(userId);
               parsedFileData.getSapFileInfo().setProcessedStatus(ProcessingStatus.SUCCESSFUL.toString());
               parsedFileData.getSapFileInfo().setProcessingInfo("Outbound File Generated Successfully");
               parsedFileData.getSapFileInfo().setFileName(file.getName());
               parsedFileData.getSapFileInfo().setTotalLineCount(dataCount+1);
               sapInterfaceProcessDAO.saveOutBoundSAPFileInfo(parsedFileData.getSapFileInfo());
            }
            this.moveFile();

         } catch (IOException e) {
            File file = new File(folderName);
            ParsedFileData parsedFileData = new ParsedFileData();
            parsedFileData.getSapFileInfo().setInterfaceType("SALES_AND_DISTRIBUTION");
            parsedFileData.getSapFileInfo().setCreatedUserCode(userId);
            parsedFileData.getSapFileInfo().setProcessedStatus(ProcessingStatus.FAILED.toString());
            parsedFileData.getSapFileInfo().setProcessingInfo("Outbound File Failed,Due to IO Exception");
            parsedFileData.getSapFileInfo().setFileName(file.getName());
            parsedFileData.getSapFileInfo().setTotalLineCount(dataCount+1);
            sapInterfaceProcessDAO.saveOutBoundSAPFileInfo(parsedFileData.getSapFileInfo());
            file.delete();

            throw new CustomException();
         }
      } else {
         File file = new File(folderName);
         ParsedFileData parsedFileData = new ParsedFileData();
         parsedFileData.getSapFileInfo().setInterfaceType("SALES_AND_DISTRIBUTION");
         parsedFileData.getSapFileInfo().setCreatedUserCode(userId);
         parsedFileData.getSapFileInfo().setProcessedStatus(ProcessingStatus.FAILED.toString());
         parsedFileData.getSapFileInfo().setProcessingInfo("Outbound File Generated already");
         parsedFileData.getSapFileInfo().setFileName(file.getName());
         parsedFileData.getSapFileInfo().setTotalLineCount(dataCount+1);
         sapInterfaceProcessDAO.saveOutBoundSAPFileInfo(parsedFileData.getSapFileInfo());
      }
   }

   protected abstract void moveFile() throws CustomException;

   protected abstract String getSequenceNumber() throws CustomException;

   protected abstract String getFileFolderDetails(String strDate, String seqNumber) throws CustomException;

   /**
    * writing file header information
    * 
    * @param BufferedWriter
    * @throws IOException
    * @throws CustomException
    * 
    */

   protected abstract void writingFileHeader(BufferedWriter writer, String seqNumber, String strDate)
         throws IOException, CustomException;

   /**
    * writing file trailer information
    * 
    * @param BufferedWriter
    * @param dataCount
    * @throws IOException
 * @throws CustomException 
    * 
    */

   protected abstract void writingFileTrailer(BufferedWriter writer, int dataCount) throws IOException, CustomException;

   /**
    * writing file data information
    * 
    * @param BufferedWriter
    * @return dataCount
    * @throws IOException
    * @throws CustomException
    * 
    */

   public abstract int writingFileData(BufferedWriter writer) throws CustomException;

   /**
    * adding blank space
    * 
    * @param StringBuilder
    * @param int
    * @param int
    * @return StringBuilder
    */

   protected String addBlankSpace(int size) {
      return StringUtils.rightPad(" ", size);

   }

   /**
    * get document sequence number
    * 
    * @param int
    * @return String
    */

   protected String getDocumentSequenceNumberFormat(int documentSequenceNumber) {

      return String.format("%010d", documentSequenceNumber);

   }

   protected String convertDateAndTime(String dateInString) {

      final String DATE_FORMAT = "yyy-MM-dd HH:mm:ss";
      LocalDateTime ldt = LocalDateTime.parse(dateInString, DateTimeFormatter.ofPattern(DATE_FORMAT));
      final String OUT_DATE_FORMATE = "yyyyMMddHHmmss";

      DateTimeFormatter format = DateTimeFormatter.ofPattern(OUT_DATE_FORMATE);

      return format.format(ldt);
   }

}
