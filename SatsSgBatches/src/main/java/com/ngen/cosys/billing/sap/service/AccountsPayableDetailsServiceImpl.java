package com.ngen.cosys.billing.sap.service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ngen.cosys.billing.sap.dao.AccountsPayableDetailsProcessDAO;
import com.ngen.cosys.billing.sap.enums.FileFolder;
import com.ngen.cosys.billing.sap.model.AccountsPayableDetails;
import com.ngen.cosys.billing.sap.utility.APVenderKey;
import com.ngen.cosys.framework.exception.CustomException;

@Service("accountsPayableDetailsProcessImpl")
public class AccountsPayableDetailsServiceImpl extends SapOutFileProcessor {

   @Autowired
   AccountsPayableDetailsProcessDAO accountsPayableDetailsProcessDAO;

   @Autowired
   APVenderKey apVenderKey;

   /**
    * Retrieve data from DB and writing data in file
    * 
    * @param BufferedWriter
    * @throws IOException,CustomException
    * @return int
    */

   @Override
   @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
   public int writingFileData(BufferedWriter writer) throws CustomException {
      List<AccountsPayableDetails> accountPayableDetailsList = accountsPayableDetailsProcessDAO.fetchAccountsPayableDetails();
      List<String> fileHeaderAndDataLineList = this.getFileDocumentHeaderAndDataLine(accountPayableDetailsList);
      int dataCount = fileHeaderAndDataLineList.size();

      fileHeaderAndDataLineList.forEach(data -> {
         try {
            writer.write(data);
            writer.newLine();

         } catch (IOException e) {
            // file exception
         }

      });

      List<Long> billGenIds = accountPayableDetailsList.stream().map(e -> e.getBillGenId()).collect(Collectors.toList());
      sapInterfaceProcessDAO.updateCustomerPostingStatus(billGenIds);

      return dataCount;
   }

   /**
    * organizing DB data according to SAP file formate and creating file data line
    * 
    * @param List<object>
    * @return List<String>
    */
   BigDecimal hashTotal = BigDecimal.ZERO;
   int chargeCodeCount = 0;

   private List<String> getFileDocumentHeaderAndDataLine(List<AccountsPayableDetails> accountPayableDetailsList) {
      List<String> dataList = new ArrayList<>();
      String customerId = null;
      String chargeCodeId = null;
      chargeCodeCount = 0;
      int docHeaderIndex = 0;
      int docLineIndex = 0;
      int docLineDIndex = 0;

      BigDecimal amount = BigDecimal.ZERO;

      for (AccountsPayableDetails accountPayableObj : accountPayableDetailsList) {
         if (accountPayableObj.getCustomerId() != null && accountPayableObj.getCustomerId().equalsIgnoreCase(customerId)) {

        /*    if (accountPayableObj.getChargeCodeId() != null && accountPayableObj.getChargeCodeId().equalsIgnoreCase(chargeCodeId)) {
               hashTotal = hashTotal.add(accountPayableObj.getSgdAmount());
               amount = amount.add(accountPayableObj.getSgdAmount());
               this.updateDocumentHeaderAndDataLine(dataList, accountPayableObj, amount, chargeCodeCount, docHeaderIndex, docLineIndex,docLineDIndex);

            } else {
               hashTotal = hashTotal.add(accountPayableObj.getSgdAmount());
               amount = accountPayableObj.getSgdAmount();
               int[] index = this.addNewDocumentHeaderAndDataLine(dataList, accountPayableObj, amount, chargeCodeCount);
               docHeaderIndex = index[0];
               docLineIndex = index[1];
               docLineDIndex=index[2];
               chargeCodeCount++;
            }*/
            
            hashTotal = hashTotal.add(accountPayableObj.getSgdAmount());
            amount = accountPayableObj.getSgdAmount();
            int[] index = this.addNewDocumentHeaderAndDataLine(dataList, accountPayableObj, amount, chargeCodeCount);
          /*  docHeaderIndex = index[0];
            docLineIndex = index[1];
            docLineDIndex=index[2];*/
            chargeCodeCount++;
            

         } else {

            amount = accountPayableObj.getSgdAmount();
            hashTotal = hashTotal.add(accountPayableObj.getSgdAmount());
            int[] index = this.addNewDocumentHeaderAndDataLine(dataList, accountPayableObj, amount, chargeCodeCount);
            /*docHeaderIndex = index[0];
            docLineIndex = index[1];
            docLineDIndex=index[2];*/
            chargeCodeCount++;
         }

         customerId = accountPayableObj.getCustomerId();
         chargeCodeId = accountPayableObj.getChargeCodeId();

      }
      return dataList;

   }

   /**
    * updating data line and document header
    * 
    * @param List<String>
    * @param AccountsPayableDetails
    * @param amount
    * @param chargeCodeCount
    *           chargecode count
    * @param docHeaderIndex
    *           headed index
    * @param docLineIndex
    *           data line index
    * 
    * 
    */

   private void updateDocumentHeaderAndDataLine(List<String> dataList, AccountsPayableDetails accountPayableObj, BigDecimal amount, 
         int chargeCodeCount, int docHeaderIndex, int docLineIndex,int docLineDIndex) {
      accountPayableObj.setTotalSGDAmount(amount);
      accountPayableObj.setDocumentSequenceNumber(getDocumentSequenceNumberFormat(chargeCodeCount));
      //header
      String docHeader = this.createDocumentHeader(accountPayableObj);
      dataList.set(docHeaderIndex, docHeader);
      // credit posting
      String documentLine = this.createDocumentline(accountPayableObj);
      dataList.set(docLineIndex, documentLine);
      // debit posting
      String documentDLine = this.createDocumentDebitline(accountPayableObj);
      dataList.set(docLineDIndex, documentDLine);

   }

   /**
    * adding new document header and data line
    * 
    * @param List<String>
    * @param AccountsPayableDetails
    * @param chargeCodeCount
    *           chargecode count
    * @param amount
    * @return int[0] headerIndex,int[1] datalineindex
    */

   private int[] addNewDocumentHeaderAndDataLine(List<String> dataList, AccountsPayableDetails accountPayableObj, BigDecimal amount, int chargeCodeCount) {
      chargeCodeCount++;
      int[] index = new int[3];

      accountPayableObj.setTotalSGDAmount(amount);
      accountPayableObj.setDocumentSequenceNumber(getDocumentSequenceNumberFormat(chargeCodeCount));

      // document header
      index[0] = dataList.size();
      String docHeader = this.createDocumentHeader(accountPayableObj);
      dataList.add(docHeader);

      // document C line
      index[1] = dataList.size();
      String documentLine = this.createDocumentline(accountPayableObj);
      dataList.add(documentLine);
      
      // document D line
      index[2] = dataList.size();
      String documentDLine = this.createDocumentDebitline(accountPayableObj);
      dataList.add(documentDLine);
      
      
      
      return index;
   }

   /**
    * creating file header according to SAP file formate
    * 
    * @param BufferedWriter
    */

   @Override
   protected void writingFileHeader(BufferedWriter writer, String seqNumber, String strDate) throws IOException {
      StringBuilder header = new StringBuilder("ASATS0000000000CBDysorderssaap");
      header.append(strDate.substring(0, 8));
      header.append(strDate.substring(0, 8));
      header.append("81757");
      header.append(addBlankSpace(238));
      writer.write(header.toString());
      writer.newLine();
   }

   /**
    * creating file trailer according to SAP file formate
    * 
    * @param BufferedWriter
    */

   @Override
   protected void writingFileTrailer(BufferedWriter writer, int dataCount) throws IOException {
      StringBuilder trailer = new StringBuilder("ZSATS9999999999");
      trailer.append(String.format("%05d", chargeCodeCount));
      NumberFormat hashTotalFormatter = new DecimalFormat("#0000000000000.00");
      trailer.append(hashTotalFormatter.format(hashTotal));
      trailer.append(addBlankSpace(253));
      writer.write(trailer.toString());
   }

   /**
    * creating document line according to SAP file formate
    * 
    * @param SalesAndDistributionBillingDetails
    * @return String
    */

   private String createDocumentline(AccountsPayableDetails accountPayableObj) {
      StringBuilder documentLine = new StringBuilder("LSATS");
      documentLine.append(accountPayableObj.getDocumentSequenceNumber());
      documentLine.append(String.format("%03d", 1));
      documentLine.append("C");
      documentLine.append(addBlankSpace(10));
      String sapCustomerCode = String.format("%10s", accountPayableObj.getApVendorCode());
      documentLine.append(sapCustomerCode);
      documentLine.append(addBlankSpace(20));
      NumberFormat foreignAmount = new DecimalFormat("#00000000000.00");
      documentLine.append(foreignAmount.format(accountPayableObj.getTotalSGDAmount()));
      documentLine.append(foreignAmount.format(accountPayableObj.getTotalSGDAmount()));
      documentLine.append(addBlankSpace(3));
      documentLine.append(foreignAmount.format(0.00));
      documentLine.append(foreignAmount.format(0.00));
      // Text Line
      documentLine.append(addBlankSpace(50));
      documentLine.append(addBlankSpace(38));
      // ap vendor key
      String payyename="";
      if(accountPayableObj.getApVendorCode().equalsIgnoreCase("0001002008"))
         payyename="test1";
      else
         payyename="test2";
      int apVendorKey = apVenderKey.getAPVendorKey(accountPayableObj.getApVendorCode(), "LUFTHANSA CARGO AG", accountPayableObj.getAccountNo(), accountPayableObj.getTotalSGDAmount());
      documentLine.append(StringUtils.leftPad(String.valueOf(apVendorKey), 11));
      documentLine.append(addBlankSpace(62));
      documentLine.append(String.format("%04d", 0));
      documentLine.append(addBlankSpace(6));
      return documentLine.toString();

   }
   
   private String createDocumentDebitline(AccountsPayableDetails accountPayableObj) {
      StringBuilder documentLine = new StringBuilder("LSATS");
      documentLine.append(accountPayableObj.getDocumentSequenceNumber());
      documentLine.append(String.format("%03d", 2));
      documentLine.append("D");
      documentLine.append(addBlankSpace(10));
      documentLine.append(addBlankSpace(10));
      documentLine.append("81757");
      documentLine.append(addBlankSpace(15));
      NumberFormat foreignAmount = new DecimalFormat("#00000000000.00");
      documentLine.append(foreignAmount.format(accountPayableObj.getTotalSGDAmount()));
      documentLine.append(foreignAmount.format(accountPayableObj.getTotalSGDAmount()));
      documentLine.append("NI ");
      documentLine.append(foreignAmount.format(0.00));
      documentLine.append(foreignAmount.format(0.00));
      // Text Line
      documentLine.append(addBlankSpace(50));
      documentLine.append(addBlankSpace(38));
      // ap vendor key
      documentLine.append(addBlankSpace(11));
      documentLine.append(addBlankSpace(62));
      documentLine.append(String.format("%04d", 0));
      documentLine.append(addBlankSpace(6));
      return documentLine.toString();

   }

   /**
    * creating document header according to SAP file formate
    * 
    * @param SalesAndDistributionBillingDetails
    * @return String
    */

   private String createDocumentHeader(AccountsPayableDetails accountPayableObj) {
      SimpleDateFormat postingDateFormate = new SimpleDateFormat("yyyyMMdd");
      StringBuilder docheader = new StringBuilder("HSATS");
      docheader.append(accountPayableObj.getDocumentSequenceNumber());
      docheader.append(String.format("%03d", 2));
      // need to check document reference no
      String billingFrequency;
      if(accountPayableObj.getBillingFrequency().equalsIgnoreCase("Monthly")) {
         billingFrequency="MONTHL";
      }
      else {
         billingFrequency="FORTNI";
      }
         
         
      docheader.append(accountPayableObj.getApVendorCode());
      docheader.append(billingFrequency);

      // Document Date
      LocalDate convertedDate = LocalDate.parse(postingDateFormate.format(accountPayableObj.getBillGenDate()), DateTimeFormatter.ofPattern("yyyyMMdd"));
      docheader.append(convertedDate.toString().replaceAll("[\\-\\+\\.\\^:,]", ""));
      // Posting Date
      convertedDate = convertedDate.withDayOfMonth(convertedDate.getMonth().length(convertedDate.isLeapYear()));
      docheader.append(convertedDate.toString().replaceAll("[\\-\\+\\.\\^:,]", ""));

      docheader.append("KR");
      String text = "AP Voucher for COB";
      docheader.append(String.format("%-30s", text));
      docheader.append(addBlankSpace(20));
      docheader.append("SGD");
      NumberFormat foreignTotalFormatter = new DecimalFormat("#0000000000000.00");
      docheader.append(foreignTotalFormatter.format(accountPayableObj.getTotalSGDAmount()));
      docheader.append(foreignTotalFormatter.format(accountPayableObj.getTotalSGDAmount()));
      docheader.append(addBlankSpace(152));

      return docheader.toString();
   }

   @Override
   protected String getSequenceNumber() throws CustomException {
      return accountsPayableDetailsProcessDAO.getAccountPayableFileSequencesNumber();
   }

   @Override
   protected String getFileFolderDetails(String strDate, String seqNumber) throws CustomException {
      String toProcessFolder = sapInterfaceProcessDAO.getOutboundFileFolder(FileFolder.SAP_AP_OUTBOUND_TOPROCESS.toString());
      String fileName = this.createFileName(strDate, seqNumber);
      File file = new File(toProcessFolder, fileName);
      return file.getPath();
   }

   private String createFileName(String strDate, String seqNumber) {
      StringBuilder fileName = new StringBuilder();
      fileName.append("ysorderssaap");
      //fileName.append(".dat");
      return fileName.toString();
   }

   @Override
   protected void moveFile() throws CustomException {
      try {
         String toProcessFolder = sapInterfaceProcessDAO.getOutboundFileFolder(FileFolder.SAP_AP_OUTBOUND_TOPROCESS.toString());
         String processingFolder = sapInterfaceProcessDAO.getOutboundFileFolder(FileFolder.SAP_AP_OUTBOUND_PROCESSING.toString());
         sapFileReader.movingFileByFolder(toProcessFolder, processingFolder);
      } catch (Exception e) {
         throw new CustomException();
      }
      
   }

}
