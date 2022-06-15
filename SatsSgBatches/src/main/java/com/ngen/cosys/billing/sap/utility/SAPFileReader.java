package com.ngen.cosys.billing.sap.utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.util.FileSystemUtils;

import com.ngen.cosys.billing.sap.enums.FileNamePrefix;
import com.ngen.cosys.billing.sap.service.SapInboundFileProcessor;
import com.ngen.cosys.framework.exception.CustomException;

@Component
public class SAPFileReader {

   @Autowired
   @Qualifier("sapInvoiceAndCreditNoteProcessImpl")
   private SapInboundFileProcessor sapInvoiceAndCreditNoteFileProcessor;

   private static Logger logger = LoggerFactory.getLogger(SAPFileReader.class);

   public InputStream getFileInputStream(String toProcessFolder, String processingFolder, String fileNameprefix)
         throws FileNotFoundException, CustomException {
      InputStream inputStream = null;
      File sourceFolder = new File(toProcessFolder);
      File destinationFolder = new File(processingFolder);
      this.movingFile(sourceFolder, destinationFolder);
      File[] listOfFiles = destinationFolder.listFiles();
      for (int i = 0; i < listOfFiles.length; i++) {

         if (listOfFiles[i].isFile()) {
            String filePrefix = listOfFiles[i].getName().substring(0, 4);

            if (filePrefix.equalsIgnoreCase(fileNameprefix)) {
               inputStream = new FileInputStream(new File(listOfFiles[i].getPath()));
               return inputStream;
            }
         }
      }
      return inputStream;

   }

   public void movingFile(File sourceFolder, File destinationFolder) throws FileNotFoundException {
      if (!destinationFolder.exists()) {
         destinationFolder.mkdirs();
      }
      // Check weather source exists and it is folder.
      if (sourceFolder.exists() && sourceFolder.isDirectory()) {
         // Get list of the files and iterate over them
         File[] listOfFiles = sourceFolder.listFiles();

         if (listOfFiles.length > 0) {
            for (int i = 0; i < listOfFiles.length; i++) {

               // for (File file : listOfFiles) {
               // Move files to destination folder
               listOfFiles[i].renameTo(new File(destinationFolder, listOfFiles[i].getName()));
               return;
            }
         } else {
            throw new FileNotFoundException("File not found");
         }
      } else {
         logger.info("Folder does not exists {}", sourceFolder);

      }

   }

   public void movingFileByFolder(String sourceFolder, String destinationFolder) throws FileNotFoundException {
      File sourceFolderFileFolder = new File(sourceFolder);
      File destinationFolderFileFolder = new File(destinationFolder);

      if (!destinationFolderFileFolder.exists()) {
         destinationFolderFileFolder.mkdirs();
      }
      // Check weather source exists and it is folder.
      if (sourceFolderFileFolder.exists() && sourceFolderFileFolder.isDirectory()) {
         // Get list of the files and iterate over them
         File[] listOfFiles = sourceFolderFileFolder.listFiles();
         if (listOfFiles != null) {
            for (File file : listOfFiles) {
               // Move files to destination folder
               file.renameTo(new File(destinationFolderFileFolder, file.getName()));

            }
         } else {
            throw new FileNotFoundException("File not found");
         }
      } else {
         logger.error("Folder does not exists {}", sourceFolder);
      }

   }

   /**
    * Copy a file from one location to another
    * 
    * @param from
    * @param to
    * @throws IOException
    */
   public void copyFile(String src, String dest) throws IOException {
      File sourceFolderFileFolder = new File(src);
      File destinationFolderFileFolder = new File(dest);
      FileSystemUtils.copyRecursively(sourceFolderFileFolder, destinationFolderFileFolder);
   }

   public String getFileName(String toProcessFolder) {
      String fileName = null;
      File sourceFolder = new File(toProcessFolder);
      File[] listOfFiles = sourceFolder.listFiles();
      if (listOfFiles.length > 0 && listOfFiles[0].isFile()) {
         fileName = listOfFiles[0].getName();
      }
      return fileName;

   }

   public void moveFileDestination(File singleFile, File destinationFolder) {
      if (!destinationFolder.exists()) {
         destinationFolder.mkdirs();
      }
      // Check weather file exist
      if (singleFile.exists() && singleFile.isFile()) {
         try {
            singleFile.renameTo(new File(destinationFolder, singleFile.getName()));
            return;
         } catch (Exception e) {
            logger.info("\"File is failed to move!", singleFile);
         }

      } else {
         logger.info("File does not exists {}", singleFile);
      }

   }

   public InputStream readSingleFileAndCreateFileInputStream(String processingFolder, String fileName)
         throws CustomException, IOException {
      InputStream inputStream = null;
      File processingFiles = new File(processingFolder);
      File[] listOfFiles = processingFiles.listFiles();
      Arrays.sort(listOfFiles);
      try {
         for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
               String filePrefix = listOfFiles[i].getName().substring(0, 4);
               if (listOfFiles[i].getName().equalsIgnoreCase(fileName)
                     && (filePrefix.equalsIgnoreCase(FileNamePrefix.INVOICE.value())
                           || filePrefix.equalsIgnoreCase(FileNamePrefix.MATERIAL_MASTER.value())
                           || filePrefix.equalsIgnoreCase(FileNamePrefix.CUSTOMER_MASTER.value()))) {
                  inputStream = new FileInputStream(new File(listOfFiles[i].getPath()));
                  return inputStream;
               }
            }
         }
      } catch (Exception e) {
         logger.info("File does not exists in processing folder");
      }
      /*
       * finally { if(inputStream != null) { //inputStream.close(); } }
       */

      return inputStream;
   }

}
