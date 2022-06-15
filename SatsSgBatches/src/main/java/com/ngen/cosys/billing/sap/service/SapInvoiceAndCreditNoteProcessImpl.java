package com.ngen.cosys.billing.sap.service;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ngen.cosys.billing.sap.dao.SapInvoiceAndCreditNoteProcessDAO;
import com.ngen.cosys.billing.sap.enums.EntryType;
import com.ngen.cosys.billing.sap.enums.FileFolder;
import com.ngen.cosys.billing.sap.enums.FileNamePrefix;
import com.ngen.cosys.billing.sap.model.InvoiceAndCreditNote;
import com.ngen.cosys.billing.sap.model.InvoiceAndCreditNoteEntry;
import com.ngen.cosys.billing.sap.model.ParsedFileData;
import com.ngen.cosys.billing.sap.model.SAPFileErrorInfo;
import com.ngen.cosys.billing.sap.model.SAPFileInfo;
import com.ngen.cosys.billing.sap.model.SAPFileRecord;
import com.ngen.cosys.framework.constant.ErrorType;
import com.ngen.cosys.framework.exception.CustomException;

import lombok.Getter;
import lombok.Setter;

/**
 * This service takes care of the sap invoice and credit note services.
 * 
 * @author NIIT Technologies Ltd
 *
 */

@Service("sapInvoiceAndCreditNoteProcessImpl")
public class SapInvoiceAndCreditNoteProcessImpl extends SapInFileProcessor {

   @Autowired
   private SapInvoiceAndCreditNoteProcessDAO sapInvoiceAndCreditNoteProcessDAO;

   @Autowired
   private Validator validator;

   private static Logger logger = LoggerFactory.getLogger(SapInvoiceAndCreditNoteProcessImpl.class);

   @Getter
   @Setter
   String interfaceType = "INVOICE_AND_CREDIT_NOTE";

   /**
    * parsing customer information and validating mandatory field
    * 
    * @param String
    * @param ParsedFileData
    * 
    */

   private void parseforCustomerDocumentInfo(String strLine, ParsedFileData parsedFileData) {
      InvoiceAndCreditNote invoiceAndCreditNote = new InvoiceAndCreditNote();
      invoiceAndCreditNote.setCreditNoteTypeIndicator(strLine.substring(0, 1).trim());
      invoiceAndCreditNote.setCustomerAccountNo(strLine.substring(1, 11).trim());
      invoiceAndCreditNote.setDocumentNo(strLine.substring(11, 21).trim());
      invoiceAndCreditNote.setRaiseBy("raise By");
      invoiceAndCreditNote.setCreditTerm(strLine.substring(25, 75).trim());
      if (strLine.length() > 75 && strLine.length() > 116)
         invoiceAndCreditNote.setCustomerName1(strLine.substring(75, 115).trim());
      else
         invoiceAndCreditNote.setCustomerName1(strLine.substring(75, strLine.length()).trim());
      if (strLine.length() > 115 && strLine.length() > 156)
         invoiceAndCreditNote.setCustomerName2(strLine.substring(115, 155).trim());
      else
         invoiceAndCreditNote.setCustomerName2(strLine.substring(115, strLine.length()).trim());
      if (strLine.length() > 155 && strLine.length() > 196)
         invoiceAndCreditNote.setCustomerName3(strLine.substring(155, 195).trim());
      else
         invoiceAndCreditNote.setCustomerName3(strLine.substring(155, strLine.length()).trim());
      if (strLine.length() > 195 && strLine.length() > 236)
         invoiceAndCreditNote.setCustomerName4(strLine.substring(195, 235).trim());
      else
         invoiceAndCreditNote.setCustomerName4(strLine.substring(195, strLine.length()).trim());
      if (strLine.length() > 235 && strLine.length() > 276)
         invoiceAndCreditNote.setStreet2(strLine.substring(235, 275).trim());
      else
         invoiceAndCreditNote.setStreet2(strLine.substring(235, strLine.length()).trim());
      if (strLine.length() > 275 && strLine.length() > 316)
         invoiceAndCreditNote.setStreet3(strLine.substring(275, 315).trim());
      else
         invoiceAndCreditNote.setStreet3(strLine.substring(275, strLine.length()).trim());
      if (strLine.length() > 315 && strLine.length() > 376)
         invoiceAndCreditNote.setCustomerStreet(strLine.substring(315, 375).trim());
      else
         invoiceAndCreditNote.setCustomerStreet(strLine.substring(315, strLine.length()).trim());
      if (strLine.length() > 375 && strLine.length() > 386)
         invoiceAndCreditNote.setPoBox(strLine.substring(375, 385).trim());
      else
         invoiceAndCreditNote.setPoBox(strLine.substring(375, strLine.length()).trim());
      if (strLine.length() > 385 && strLine.length() > 426)
         invoiceAndCreditNote.setCustomerCity(strLine.substring(385, 425).trim());
      else
         invoiceAndCreditNote.setCustomerCity(strLine.substring(385, strLine.length()).trim());
      if (strLine.length() > 425) {
    	  if(strLine.length() >= 436) {
    		  invoiceAndCreditNote.setCustomerPostalCode(strLine.substring(425, 435).trim());
    	  }else {
    		  invoiceAndCreditNote.setCustomerPostalCode(strLine.substring(425, strLine.length()).trim());
    	  }
      }
     // System.out.println("=======================strLine.length():"+strLine.length()+"===========================");
      if (strLine.length() == 436)
         invoiceAndCreditNote.setCancelIndicator(strLine.substring(435, 436).trim());

      Set<ConstraintViolation<InvoiceAndCreditNote>> violations = this.validator.validate(invoiceAndCreditNote);
      violations.forEach(item -> {
         SAPFileErrorInfo errorInfo = new SAPFileErrorInfo();
         errorInfo.setLineNo(parsedFileData.getCurrentLineNo());
         errorInfo.setReferenceInfo(invoiceAndCreditNote.getCustomerAccountNo());
         errorInfo.setProcessingInfo(item.getMessage());
         parsedFileData.getProccessingExceptionList().add(errorInfo);
      });

      parsedFileData.setCurrentInvoiceCreditNote(invoiceAndCreditNote);

   }

   /**
    * parsing file file document entry
    * 
    * @param String
    * @param ParsedFileData
    * @return InvoiceAndCreditNoteEntry
    * 
    */

   private InvoiceAndCreditNoteEntry parseforInvoiceAndCreditNoteEntry(String strLine, ParsedFileData parsedFileData,
         SAPFileRecord record) {
      InvoiceAndCreditNoteEntry currentInvoiceAndCreditNoteEntry = new InvoiceAndCreditNoteEntry();
      currentInvoiceAndCreditNoteEntry.setCreditNoteTypeIndicator(strLine.substring(0, 1).trim());
      currentInvoiceAndCreditNoteEntry.setLineSequenceNumber(record.getLineSequenceNo());
      currentInvoiceAndCreditNoteEntry.setFinSysInvoiceNo(strLine.substring(11, 21).trim());
      currentInvoiceAndCreditNoteEntry.setCosysBillingNumber(strLine.substring(25, 37).trim());
      currentInvoiceAndCreditNoteEntry.setBillingNumber(Integer.valueOf(strLine.substring(25, 37).trim()));
      currentInvoiceAndCreditNoteEntry.setMaterialCode(strLine.substring(37, 55).trim());
      currentInvoiceAndCreditNoteEntry.setMaterialDescription(strLine.substring(55, 95).trim());
      currentInvoiceAndCreditNoteEntry.setUnitOfMeasure(strLine.substring(112, 115).trim());

      currentInvoiceAndCreditNoteEntry.setQuantity(
            parserForStringToDouble(strLine.substring(95, 112).trim(), EntryType.QUANTITY.toString(), parsedFileData));
      currentInvoiceAndCreditNoteEntry.setRate(
            parserForStringToDouble(strLine.substring(115, 129).trim(), EntryType.RATE.toString(), parsedFileData));
      currentInvoiceAndCreditNoteEntry.setAmount(
            parserForStringToDouble(strLine.substring(129, 145).trim(), EntryType.AMOUNT.toString(), parsedFileData));

      Set<ConstraintViolation<InvoiceAndCreditNoteEntry>> violations = this.validator
            .validate(currentInvoiceAndCreditNoteEntry);
      violations.forEach(item -> {
         SAPFileErrorInfo errorInfo = new SAPFileErrorInfo();
         errorInfo.setLineNo(parsedFileData.getCurrentLineNo());
         errorInfo.setReferenceInfo(null);
         errorInfo.setProcessingInfo(item.getMessage());
         parsedFileData.getProccessingExceptionList().add(errorInfo);
      });

      parsedFileData.setCurrentInvoiceAndCreditNoteEntry(currentInvoiceAndCreditNoteEntry);

      return currentInvoiceAndCreditNoteEntry;
   }

   /**
    * parsing file creator name
    * 
    * @param String
    * @param ParsedFileData
    * 
    */

   private void parseForCustomerDocumentRaiseByLine(String strLine, ParsedFileData parsedFileData) {

      String raiseByLine = strLine.substring(25, strLine.length()).trim();

      parsedFileData.getCurrentInvoiceCreditNote().setRaiseBy(raiseByLine);

      Set<ConstraintViolation<InvoiceAndCreditNote>> violations = this.validator
            .validate(parsedFileData.getCurrentInvoiceCreditNote());
      violations.forEach(item -> {
         SAPFileErrorInfo errorInfo = new SAPFileErrorInfo();
         errorInfo.setLineNo(parsedFileData.getCurrentLineNo());
         errorInfo.setReferenceInfo(parsedFileData.getCurrentInvoiceCreditNote().getCustomerAccountNo());
         errorInfo.setProcessingInfo(item.getMessage());
         parsedFileData.getProccessingExceptionList().add(errorInfo);
      });

   }

   /**
    * parsing file document free text footer
    * 
    * @param ParsedFileData
    * @param String
    * 
    */

   private void parseForCustomerDocumentFreeTextFooter(ParsedFileData parsedFileData, String strLine) {
      String miscFooterTxt = parsedFileData.getCurrentInvoiceCreditNote().getDocumentMiscFooterText() == null ? ""
            : parsedFileData.getCurrentInvoiceCreditNote().getDocumentMiscFooterText();

      miscFooterTxt += strLine.substring(25, strLine.length()).trim() + '\n';

      parsedFileData.getCurrentInvoiceCreditNote().setDocumentMiscFooterText(miscFooterTxt);

   }

   /**
    * parsing file documet free text header
    * 
    * @param ParsedFileData
    * @param String
    * 
    */

   private void parseForCustomerDocumentFreeTextHeader(ParsedFileData parsedFileData, String strLine) {
      String miscHeaderTxt = parsedFileData.getCurrentInvoiceCreditNote().getDocumentMiscText() == null ? ""
            : parsedFileData.getCurrentInvoiceCreditNote().getDocumentMiscText();

      miscHeaderTxt += strLine.substring(25, strLine.length()).trim() + '\n';

      parsedFileData.getCurrentInvoiceCreditNote().setDocumentMiscText(miscHeaderTxt);

   }

   /**
    * parsing file documet grand total
    * 
    * @param String
    * @param ParsedFileData
    */

   private void parseForInvoiceAndCreditNoteEntryGrandTotal(String strLine, ParsedFileData parsedFileData) {
      double grandTotal = parserForStringToDouble(strLine.substring(129, 145).trim(), EntryType.GRAND_TOTAL.toString(),
            parsedFileData);
      parsedFileData.getCurrentInvoiceCreditNote().setGrandTotalAmount(grandTotal);

   }

   /**
    * parsing file documet sub total details
    * 
    * @param ParsedFileData
    * @param String
    * 
    */

   private void parseForInvoiceAndCreditNoteSubtotalLine(String strLine, ParsedFileData parsedFileData) {
      parsedFileData.getCurrentInvoiceAndCreditNoteEntry().setTotalAmount(parserForStringToDouble(
            strLine.substring(129, 145).trim(), EntryType.TOTAL_AMOUNT.toString(), parsedFileData));
      parsedFileData.getCurrentInvoiceAndCreditNoteEntry().setTotalQuantity(parserForStringToDouble(
            strLine.substring(95, 112).trim(), EntryType.TOTAL_QUANTITY.toString(), parsedFileData));
      parsedFileData.getCurrentInvoiceAndCreditNoteEntry().setTotalRate(parserForStringToDouble(
            strLine.substring(115, 129).trim(), EntryType.TOTAL_RATE.toString(), parsedFileData));

   }

   /**
    * parsing error in mandatory field
    * 
    * @param String
    * @param String
    * @param ParsedFileData
    * 
    */

   private double parserForStringToDouble(String str, String type, ParsedFileData parsedFileData) {

      if (str.isEmpty()) {

         SAPFileErrorInfo errorInfo = new SAPFileErrorInfo();
         errorInfo.setLineNo(parsedFileData.getCurrentLineNo());

         if (type.equalsIgnoreCase(EntryType.GRAND_TOTAL.toString()))
            errorInfo.setProcessingInfo("Grand total is required");
         else if (type.equalsIgnoreCase(EntryType.AMOUNT.toString()))
            errorInfo.setProcessingInfo("Amount is required");
         else if (type.equalsIgnoreCase(EntryType.QUANTITY.toString()))
            errorInfo.setProcessingInfo("Quantity is required");
         else if (type.equalsIgnoreCase(EntryType.RATE.toString()))
            errorInfo.setProcessingInfo("Rate is required");

         parsedFileData.getProccessingExceptionList().add(errorInfo);
      }

      double value = 0;
      try {
         value = Double.valueOf(str);
      } catch (Exception e) {
         SAPFileErrorInfo errorInfo = new SAPFileErrorInfo();
         errorInfo.setLineNo(parsedFileData.getCurrentLineNo());

         if (type.equalsIgnoreCase(EntryType.GRAND_TOTAL.toString()))
            errorInfo.setProcessingInfo("Exception is occur during parsing grand total of document");
         else if (type.equalsIgnoreCase(EntryType.AMOUNT.toString()))
            errorInfo.setProcessingInfo("Exception is occur during parsing amount of document");
         else if (type.equalsIgnoreCase(EntryType.QUANTITY.toString()))
            errorInfo.setProcessingInfo("Exception is occur during parsing quantity of document");
         else if (type.equalsIgnoreCase(EntryType.RATE.toString()))
            errorInfo.setProcessingInfo("Exception is occur during parsing rate of document");

         parsedFileData.getProccessingExceptionList().add(errorInfo);

      }
      return value;
   }

   /**
    * parsing file document entry additional text
    * 
    * @param String
    * @param ParsedFileData
    * 
    */

   private void parseForInvoiceAndCreditNoteEntryAdditionalText(String strLine, ParsedFileData parsedFileData,
         SAPFileRecord record) {
      /*
       * String miscTxt =
       * parsedFileData.getCurrentInvoiceAndCreditNoteEntry().getMaterialMiscTxt() ==
       * null ? "" :
       * parsedFileData.getCurrentInvoiceAndCreditNoteEntry().getMaterialMiscTxt();
       * miscTxt += strLine.substring(55, 95).trim()+'\n';
       * parsedFileData.getCurrentInvoiceAndCreditNoteEntry().setMaterialMiscTxt(
       * miscTxt);
       */

      InvoiceAndCreditNoteEntry currentEntryForNonMaterialType = new InvoiceAndCreditNoteEntry();

      currentEntryForNonMaterialType.setCreditNoteTypeIndicator(strLine.substring(0, 1).trim());
      currentEntryForNonMaterialType.setLineSequenceNumber(record.getLineSequenceNo());
      currentEntryForNonMaterialType.setMaterialCode(strLine.substring(37, 55).trim());
      currentEntryForNonMaterialType.setMaterialDescription(strLine.substring(55, 95).trim());
      currentEntryForNonMaterialType.setUnitOfMeasure(strLine.substring(112, 115).trim());

      currentEntryForNonMaterialType.setQuantity(
            parserForStringToDouble(strLine.substring(95, 112).trim(), EntryType.QUANTITY.toString(), parsedFileData));
      currentEntryForNonMaterialType.setRate(
            parserForStringToDouble(strLine.substring(115, 129).trim(), EntryType.RATE.toString(), parsedFileData));
      String amount = this.parseAmountForNonMaterialType(strLine.substring(129, 145).trim());

      currentEntryForNonMaterialType
            .setAmount(parserForStringToDouble(amount, EntryType.AMOUNT.toString(), parsedFileData));

      parsedFileData.setCurrentEntryForNonMaterialType(currentEntryForNonMaterialType);

   }

   private String parseAmountForNonMaterialType(String amountStr) {

      String newAmount = null;
      int lenght = amountStr.length();

      String symbol = amountStr.substring(lenght - 1, lenght);
      if (symbol.equalsIgnoreCase("-")) {
         // System.out.println("symbol==="+symbol);
         String newAmountStr = amountStr.replace(symbol, "");
         // System.out.println("newAmountStr==="+newAmountStr);
         newAmount = symbol + newAmountStr;
      } else {
         newAmount = amountStr;
      }

      return newAmount;
   }

   /**
    * parsing file basic information
    * 
    * @param String
    * @return SAPFileRecord
    * 
    */
   @Override
   protected SAPFileRecord parseForRecordAndLineType(String strLine) {
      SAPFileRecord sapFileRecord = new SAPFileRecord();
      sapFileRecord.setRecordType(strLine.substring(0, 1));
      if (strLine.length() >= 22)
         sapFileRecord.setLineType(strLine.substring(21, 22));
      if (strLine.length() >= 25) {
         try {
            sapFileRecord.setLineSequenceNo(Integer.valueOf(strLine.substring(22, 25)));
         } catch (Exception e) {
            // ignore any parse exception for integer
         }

      }
      return sapFileRecord;
   }

   /**
    * parsing file data
    * 
    * @param String
    * @param SAPFileRecord
    * @param ParsedFileData
    * 
    */

   @Override
   protected void parseForDataLine(String strLine, SAPFileRecord record, ParsedFileData parsedFileData) {
      if (record.getRecordType().equalsIgnoreCase("I") || record.getRecordType().equalsIgnoreCase("C")) {

         this.parseInvoiceAndCreditNoteDetails(strLine, record, parsedFileData);

      }
   }

   /**
    * parsing invoice and credit note details
    * 
    * @param String
    * @param SAPFileRecord
    * @param ParsedFileData
    * 
    */

   private void parseInvoiceAndCreditNoteDetails(String strLine, SAPFileRecord record, ParsedFileData parsedFileData) {
      if (record.getLineType().equalsIgnoreCase("0")) {
         // Document Header (Customer Info, Document : Invoice / Credit Note)
         this.parseforCustomerDocumentInfo(strLine, parsedFileData);
      }
      if (record.getLineType().equalsIgnoreCase("1")) {
         // Document Details (Free Text Header) Concatenate the free text into the parsed
         // document
         this.parseForCustomerDocumentFreeTextHeader(parsedFileData, strLine);
      }
      if (record.getLineType().equalsIgnoreCase("2")) {
         if (record.getLineSequenceNo() == 1) {
            // Document Details (for line items with SAP material code)
            this.parseforInvoiceAndCreditNoteEntry(strLine, parsedFileData, record);
            parsedFileData.setCurrentEntryForNonMaterialType(null);
         } else {
            // Document Details (for line items with addtional infor for material code)
            this.parseForInvoiceAndCreditNoteEntryAdditionalText(strLine, parsedFileData, record);
         }
      }
      if (record.getLineType().equalsIgnoreCase("3")) {
         // Document Details (for subtotal line)
         this.parseForInvoiceAndCreditNoteEntryAdditionalDetails(strLine, parsedFileData);
      }
      if (record.getLineType().equalsIgnoreCase("4")) {
         // Document Details (for grand total line)
         this.parseForInvoiceAndCreditNoteEntryGrandTotal(strLine, parsedFileData);
      }
      if (record.getLineType().equalsIgnoreCase("5")) {
         // Document Details (for Free Text Footer)
         this.parseForCustomerDocumentFreeTextFooter(parsedFileData, strLine);
      }
      if (record.getLineType().equalsIgnoreCase("6")) {
         // Document Details (‘Raised By’ line)
         this.parseForCustomerDocumentRaiseByLine(strLine, parsedFileData);
         parsedFileData.getInvoicAndCreditNoteDataList().add(parsedFileData.getCurrentInvoiceCreditNote());
      }

   }

   /**
    * parsing entry sub total deatils and creating list entry list object and set
    * in invoice and credit object
    * 
    * @param String
    * @param ParsedFileData
    * 
    */

   private void parseForInvoiceAndCreditNoteEntryAdditionalDetails(String strLine, ParsedFileData parsedFileData) {
      this.parseForInvoiceAndCreditNoteSubtotalLine(strLine, parsedFileData);
      if (parsedFileData.getCurrentInvoiceCreditNote() != null
            && parsedFileData.getCurrentInvoiceCreditNote().getInvoiceCreditNoteEntryList() != null
            && !parsedFileData.getCurrentInvoiceCreditNote().getInvoiceCreditNoteEntryList().isEmpty()) {
         parsedFileData.getCurrentInvoiceCreditNote().getInvoiceCreditNoteEntryList()
               .add(parsedFileData.getCurrentInvoiceAndCreditNoteEntry());
         if (parsedFileData.getCurrentEntryForNonMaterialType() != null) {
            parsedFileData.getCurrentInvoiceCreditNote().getInvoiceCreditNoteEntryList()
                  .add(parsedFileData.getCurrentEntryForNonMaterialType());
         }
      } else {
         List<InvoiceAndCreditNoteEntry> entrylist = new ArrayList<>();
         entrylist.add(parsedFileData.getCurrentInvoiceAndCreditNoteEntry());
         if (parsedFileData.getCurrentEntryForNonMaterialType() != null) {
            entrylist.add(parsedFileData.getCurrentEntryForNonMaterialType());
         }
         if (parsedFileData.getCurrentInvoiceCreditNote() != null)
            parsedFileData.getCurrentInvoiceCreditNote().setInvoiceCreditNoteEntryList(entrylist);

      }

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
      if (parsedFileData.getInvoicAndCreditNoteDataList() != null
            && !parsedFileData.getInvoicAndCreditNoteDataList().isEmpty()) {
         logger.info("File data is saving in database {}", ' ');
         List<InvoiceAndCreditNote> invoiceAndCreditNoteList = parsedFileData.getInvoicAndCreditNoteDataList();
         DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
         LocalDateTime dateTime = LocalDateTime.parse(parsedFileData.getSapFileInfo().getFileCreationDt(), formatter);
         invoiceAndCreditNoteList.forEach(iacn -> {
            iacn.setSapFileInfoId(parsedFileData.getSapFileInfo().getSapFileInfoId());
            iacn.getInvoiceCreditNoteEntryList().forEach(value -> value.setInvoiceDate(dateTime));
         });

         invoiceAndCreditNoteList.forEach(ls -> {

            try {
               sapInvoiceAndCreditNoteProcessDAO.saveInvoiceAndCreditNoteDetails(ls);
            } catch (CustomException e) {
               // DB CustomException
            }

         });
      }

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
      String companyCode =sapInvoiceAndCreditNoteProcessDAO.getCompanyCode();
      if (!sapFileInfo.getCompanyCode().equalsIgnoreCase(companyCode)) {
         String msg = "CompanyCodeValidationFailed";
         logger.error(msg);
         throw new CustomException(msg, null, ErrorType.ERROR);

      }

      if (!sapFileInfo.getFileName().substring(0, 4).equalsIgnoreCase("O096")
            && !sapFileInfo.getFileName().substring(0, 4).equalsIgnoreCase("O097")) {
         String msg = "FileValidatingFailedAsFileNameNotMacthed";
         logger.error(msg);
         throw new CustomException(msg, null, ErrorType.ERROR);
      }

   }

   /**
    * validating file tariler information
    * 
    * @param ParsedFileData
    * @throws CustomException
    * 
    */

   @Override
   protected void validatingFileTrailer(ParsedFileData parsedFileData) throws CustomException {
      logger.info("Validating the actual record count and the count mentioned in the file trailer {}", ' ');
      String companyCode =sapInvoiceAndCreditNoteProcessDAO.getCompanyCode();
      if (parsedFileData.getSapFileInfo().getTotalLineCount() != parsedFileData.getCurrentLineNo() - 2) {
         logger.error(
               "Validating of file is failed due to actual record count and the count mentioned in the file trailer {}",
               ' ');
         throw new CustomException(
               "ValidatingFileFailedDueToRecordCountTrailer",
               null, ErrorType.ERROR);
      }

      if (!parsedFileData.getSapFileInfo().getCompanyCode().equalsIgnoreCase(companyCode)) {
         String msg = "CompanyCodeValidationFailed";
         logger.error(msg);
         throw new CustomException(msg, null, ErrorType.ERROR);
      }
   }

   @Override
   public void moveFileInArchivalFolder(String processingFolder) throws CustomException {
      try {
         String archivalFolder = sapInterfaceProcessDAO
               .getInboundFileFolder(FileFolder.SAP_O096_INBOUND_ARCHIVED.toString());
         sapFileReader.movingFileByFolder(processingFolder, archivalFolder);
      } catch (Exception e) {
         throw new CustomException();
      }
     
   }

   @Override
   public String getProcessingFolder() throws CustomException {
      return sapInterfaceProcessDAO.getInboundFileFolder(FileFolder.SAP_O096_INBOUND_PROCESSING.toString());
   }

   @Override
   public InputStream readFileAndCreateFileInputStream(String processingFolder)
         throws CustomException, FileNotFoundException {
      String toProcessFolder = sapInterfaceProcessDAO
            .getInboundFileFolder(FileFolder.SAP_O096_INBOUND_TOPROCESS.toString());
      return sapFileReader.getFileInputStream(toProcessFolder, processingFolder, FileNamePrefix.INVOICE.toString());
   }

   @Override
   public String getFileName() throws CustomException {
      String toProcessFolder = sapInterfaceProcessDAO
            .getInboundFileFolder(FileFolder.SAP_O096_INBOUND_TOPROCESS.toString());
      return sapFileReader.getFileName(toProcessFolder);
   }

@Override
public String getToProcessFolder() throws CustomException {
	 String toProcessFolder = sapInterfaceProcessDAO
	            .getInboundFileFolder(FileFolder.SAP_O096_INBOUND_TOPROCESS.toString());
	return toProcessFolder;
}

@Override
public String checkFileExistOrNotInDB(String fileName) throws CustomException {
	return sapInterfaceProcessDAO.getFileStatus(fileName)+""; 	
}

@Override
public String getArchivalFolder() throws CustomException {
	return sapInterfaceProcessDAO.getInboundFileFolder(FileFolder.SAP_O096_INBOUND_ARCHIVED.toString());
}

}
