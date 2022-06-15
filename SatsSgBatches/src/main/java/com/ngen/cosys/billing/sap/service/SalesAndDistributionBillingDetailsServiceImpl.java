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

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ngen.cosys.billing.sap.dao.SalesAndDistributionBillingDetailsDAO;
import com.ngen.cosys.billing.sap.enums.FileFolder;
import com.ngen.cosys.billing.sap.model.SalesAndDistributionBillingDetails;
import com.ngen.cosys.framework.constant.ErrorType;
import com.ngen.cosys.framework.exception.CustomException;

@Service("salesAndDistributionBillingDetailsProcessImpl")
public class SalesAndDistributionBillingDetailsServiceImpl extends SapOutFileProcessor {

   private static final Logger lOgger = LoggerFactory.getLogger(SapOutFileProcessor.class);
   private static final String GENRATESDFile = "genratesdfile";
   private final String EXCEPTION = "Exception Happened ... ";

   @Autowired
   private SalesAndDistributionBillingDetailsDAO salesAndDistributionBillingDetailsDAO;

   /**
    * Retrieve data from DB and writing data in file
    * 
    * @param BufferedWriter
    * @throws IOException,CustomException
    * @return int
    */

   @Override
   public int writingFileData(BufferedWriter writer) throws CustomException {
      List<SalesAndDistributionBillingDetails> salesAndDistributionList = salesAndDistributionBillingDetailsDAO
            .fetchSalesDistributionBillingDetails();
      List<String> fileHeaderAndDataLineList = this.getFileDocumentHeaderAndDataLine(salesAndDistributionList);
      int dataCount = fileHeaderAndDataLineList.size();
      for (String data : fileHeaderAndDataLineList) {
         try {
            writer.write(data);
            writer.newLine();
         } catch (Exception e) {
            throw new CustomException();
         }
      }
      List<Long> billGenIds = salesAndDistributionList.stream().map(e -> e.getBillGenId()).collect(Collectors.toList());
      sapInterfaceProcessDAO.updateCustomerPostingStatus(billGenIds);
      return dataCount;
   }

   /**
    * organizing DB data according to SAP file formate and creating file data line
    * 
    * @param List<object>
    * @return List<String>
 * @throws CustomException 
    */

   private List<String> getFileDocumentHeaderAndDataLine(
         List<SalesAndDistributionBillingDetails> salesAndDistributionList) throws CustomException {
      String customerId = null;
      String chargeCodeId = null;
      String groupChargeCodeId = null;
      int chargeCodeCount = 0;
      int listIndex = 0;
      BigDecimal quantity = BigDecimal.ZERO;
      BigDecimal amount = BigDecimal.ZERO;
      List<String> dataList = new ArrayList<>();

      for (SalesAndDistributionBillingDetails saleAndDistributionObj : salesAndDistributionList) {
         if (saleAndDistributionObj.getCustomerId() != null
               && saleAndDistributionObj.getCustomerId().equalsIgnoreCase(customerId)) {

            boolean condition;
            if (StringUtils.isEmpty(saleAndDistributionObj.getGroupChargeCodeId())) {
               condition = saleAndDistributionObj.getChargeCodeId() != null
                     && saleAndDistributionObj.getChargeCodeId().equalsIgnoreCase(chargeCodeId);
            } else {
               condition = saleAndDistributionObj.getGroupChargeCodeId() != null
                     && saleAndDistributionObj.getGroupChargeCodeId().equalsIgnoreCase(groupChargeCodeId);
            }

            if (condition) {
               amount = amount.add(saleAndDistributionObj.getAmount());
               quantity = quantity.add(saleAndDistributionObj.getCumulativeOrderQuantity());
               this.addNewDataLineInExistDocumentHeader(dataList, saleAndDistributionObj, chargeCodeCount, listIndex,
                     amount, quantity);
            } else {
               chargeCodeCount++;
               amount = saleAndDistributionObj.getAmount();
               quantity = saleAndDistributionObj.getCumulativeOrderQuantity();
               listIndex = this.addNewDocumentHeaderAndDataLine(dataList, saleAndDistributionObj, chargeCodeCount);
            }
         } else {
            chargeCodeCount++;
            amount = saleAndDistributionObj.getAmount();
            quantity = saleAndDistributionObj.getCumulativeOrderQuantity();
            listIndex = this.addNewDocumentHeaderAndDataLine(dataList, saleAndDistributionObj, chargeCodeCount);
         }

         customerId = saleAndDistributionObj.getCustomerId();
         chargeCodeId = saleAndDistributionObj.getChargeCodeId();
         groupChargeCodeId = saleAndDistributionObj.getGroupChargeCodeId();

      }
      return dataList;
   }

   /**
    * adding new data line in exist document header
    * 
    * @param List<String>
    * @param SalesAndDistributionBillingDetails
    * @param chargeCodeCount
    *           chargecode count
    * @param listIndex
    *           headed list index
    * @param amount
    * @param quantity
 * @throws CustomException 
    */

   private void addNewDataLineInExistDocumentHeader(List<String> dataList,
         SalesAndDistributionBillingDetails saleAndDistributionObj, int chargeCodeCount, int listIndex,
         BigDecimal amount, BigDecimal quantity) throws CustomException {
	   String SalesOrderTypeExternal=salesAndDistributionBillingDetailsDAO.getSaleAndDistributionExternalSaleOrderType();
	   String SalesOrderTypeInternal=salesAndDistributionBillingDetailsDAO.getSaleAndDistributionInternalSaleOrderType();
      saleAndDistributionObj.setTotalQuantity(quantity);
      saleAndDistributionObj.setTotalAmount(amount);
      saleAndDistributionObj.setDocumentSequenceNumber(getDocumentSequenceNumberFormat(chargeCodeCount));
      if (saleAndDistributionObj.getPricingType().equalsIgnoreCase("Internal")) {
         saleAndDistributionObj.setSalesOrderType(SalesOrderTypeInternal);
      } else {
         saleAndDistributionObj.setSalesOrderType(SalesOrderTypeExternal);
      }
      // document line
      String documentLine = this.createDocumentline(saleAndDistributionObj);
      dataList.add(documentLine);

      // change exist document header line
      String docHeader = this.createDocumentHeader(saleAndDistributionObj);
      dataList.set(listIndex, docHeader);

   }

   /**
    * adding new document header and data line
    * 
    * @param List<String>
    * @param SalesAndDistributionBillingDetails
    * @param chargeCodeCount
    *           chargecode count
    * @return chargeCodeCount header list index
 * @throws CustomException 
    */

   private int addNewDocumentHeaderAndDataLine(List<String> dataList,
         SalesAndDistributionBillingDetails saleAndDistributionObj, int chargeCodeCount) throws CustomException {
	   String SalesOrderTypeExternal=salesAndDistributionBillingDetailsDAO.getSaleAndDistributionExternalSaleOrderType();
	   String SalesOrderTypeInternal=salesAndDistributionBillingDetailsDAO.getSaleAndDistributionInternalSaleOrderType();
      saleAndDistributionObj.setTotalQuantity(saleAndDistributionObj.getCumulativeOrderQuantity());
      saleAndDistributionObj.setTotalAmount(saleAndDistributionObj.getAmount());
      saleAndDistributionObj.setDocumentSequenceNumber(getDocumentSequenceNumberFormat(chargeCodeCount));
      if (saleAndDistributionObj.getPricingType().equalsIgnoreCase("Internal")) {
         saleAndDistributionObj.setSalesOrderType(SalesOrderTypeInternal);
      } else {
         saleAndDistributionObj.setSalesOrderType(SalesOrderTypeExternal);
      }
      // document header
      int listIndex = dataList.size();
      String docHeader = this.createDocumentHeader(saleAndDistributionObj);
      dataList.add(docHeader);

      // document line
      String documentLine = this.createDocumentline(saleAndDistributionObj);
      dataList.add(documentLine);

      return listIndex;
   }

   /**
    * creating document line according to SAP file formate
    * 
    * @param SalesAndDistributionBillingDetails
    * @return String
 * @throws CustomException 
    */

   private String createDocumentline(SalesAndDistributionBillingDetails saleAndDistributionObj) throws CustomException {
	  String SalesOrderCompanyCode=salesAndDistributionBillingDetailsDAO.getSaleAndDistributionCompanyCode();
	  String SalesOrderPricingTypeCode=salesAndDistributionBillingDetailsDAO.getSaleAndDistributionPricingTypeCode();
      StringBuilder documentLine = new StringBuilder("L");
      //Company code
      documentLine.append(SalesOrderCompanyCode);
      // Document Sequence Number
      documentLine.append(saleAndDistributionObj.getDocumentSequenceNumber());
      // Material Number
      documentLine.append(StringUtils.leftPad(saleAndDistributionObj.getMaterialNumber(), 18));
      // Material Description only for Adhoc Material created in COSYS
      documentLine.append(addBlankSpace(40));
      // Unit of Measurement only for Adhoc Material created in COSYS
      documentLine.append("EA ");
      NumberFormat quantityFormatter = new DecimalFormat("#0000000000000.000");
      documentLine.append(quantityFormatter.format(saleAndDistributionObj.getCumulativeOrderQuantity().doubleValue()));
      NumberFormat weightFormatter = new DecimalFormat("#000000000000000.000");
      // if (saleAndDistributionObj.getGrossWeight() != null) {
      // documentLine.append(weightFormatter.format(saleAndDistributionObj.getGrossWeight().doubleValue()));
      // } else {
      documentLine.append(weightFormatter.format(0));
      // }
      //
      // if (saleAndDistributionObj.getNetWeight() != null) {
      // documentLine.append(weightFormatter.format(saleAndDistributionObj.getNetWeight().doubleValue()));
      // } else {
      documentLine.append(weightFormatter.format(0));
      // }

      NumberFormat amountFormatter = new DecimalFormat("#00000000000.00");
//      if (saleAndDistributionObj.getSalesOrderType().equalsIgnoreCase("ZS34")) {
      if (!saleAndDistributionObj.getPricingType().equalsIgnoreCase("Internal")) {

         documentLine.append(SalesOrderPricingTypeCode);
         documentLine.append(amountFormatter.format(saleAndDistributionObj.getAmount().doubleValue()));
      } else {
         documentLine.append(addBlankSpace(4));
         documentLine.append(amountFormatter.format(0));
      }
      // Discount Type & Discount amount only for ZS34
      //if (saleAndDistributionObj.getSalesOrderType().equalsIgnoreCase("ZS34")) {
      if (!saleAndDistributionObj.getPricingType().equalsIgnoreCase("Internal")) {
         // Discount Type
         documentLine.append("GP08");
         // Discount amount
         if (saleAndDistributionObj.getDiscountAmount().doubleValue() < 0) {
            documentLine.append(amountFormatter.format(0.00));
         } else {
            documentLine.append(amountFormatter.format(saleAndDistributionObj.getDiscountAmount().doubleValue()));
         }
      } else {
         // Discount Type
         documentLine.append(addBlankSpace(4));
         // Discount amount
         documentLine.append(amountFormatter.format(0.00));
      }

      // COSYS’s running sequence
      documentLine.append(StringUtils.leftPad(String.valueOf(saleAndDistributionObj.getBillingNumber()), 12, '0'));
      return documentLine.toString();

   }

   /**
    * creating file header according to SAP file formate
    * 
    * @param BufferedWriter
    * @throws CustomException
    */

   @Override
   protected void writingFileHeader(BufferedWriter writer, String seqNumber, String strDate)
         throws IOException, CustomException {
	  String SalesOrderCompanyCode=salesAndDistributionBillingDetailsDAO.getSaleAndDistributionCompanyCode();
      StringBuilder header = new StringBuilder("A");
      header.append(SalesOrderCompanyCode);
      header.append("0000000000I062");
      header.append(strDate.substring(0, 6));
      header.append(StringUtils.leftPad(seqNumber, 6, '0'));
      header.append(".dat");
      header.append(strDate);

      writer.write(header.toString());
      writer.newLine();
   }

   /**
    * creating file trailer according to SAP file formate
    * 
    * @param BufferedWriter
 * @throws CustomException 
    */

   @Override
   protected void writingFileTrailer(BufferedWriter writer, int dataCount) throws IOException, CustomException {
	  String SalesOrderCompanyCode=salesAndDistributionBillingDetailsDAO.getSaleAndDistributionCompanyCode();
      StringBuilder trailer = new StringBuilder("Z");
      trailer.append(SalesOrderCompanyCode);
      trailer.append("9999999999");
      trailer.append(String.format("%010d", dataCount));
      writer.write(trailer.toString());
   }

   /**
    * creating document header according to SAP file formate
    * 
    * @param SalesAndDistributionBillingDetails
    * @return String
 * @throws CustomException 
    */

   private String createDocumentHeader(SalesAndDistributionBillingDetails saleAndDistributionObj) throws CustomException {
	  String SalesOrderCompanyCode=salesAndDistributionBillingDetailsDAO.getSaleAndDistributionCompanyCode();
      SimpleDateFormat postingDateFormate = new SimpleDateFormat("yyyyMMdd");
      LocalDate convertedDate = LocalDate.parse(postingDateFormate.format(saleAndDistributionObj.getBillGenDate()),
            DateTimeFormatter.ofPattern("yyyyMMdd"));
      convertedDate = convertedDate.withDayOfMonth(convertedDate.getMonth().length(convertedDate.isLeapYear()));
      StringBuilder docheader = new StringBuilder("H");
      docheader.append(SalesOrderCompanyCode);
      docheader.append(saleAndDistributionObj.getDocumentSequenceNumber());
      docheader.append(saleAndDistributionObj.getSalesOrderType());
      docheader.append("SGD").append(saleAndDistributionObj.getSalesOrganization())
            .append(saleAndDistributionObj.getDistributionChannel()).append(saleAndDistributionObj.getDivision());
      docheader.append(convertedDate.toString().replaceAll("[\\-\\+\\.\\^:,]", ""));
      docheader.append(StringUtils.leftPad(saleAndDistributionObj.getSapCustomerCode(), 10));
      NumberFormat totalQuantityFormatter = new DecimalFormat("#000000000000000.000");
      docheader.append(totalQuantityFormatter.format(saleAndDistributionObj.getTotalQuantity().doubleValue()));

      NumberFormat totalAmountFormatter = new DecimalFormat("#000000000000000.00");
      if (!saleAndDistributionObj.getPricingType().equalsIgnoreCase("Internal")) {
         docheader.append(totalAmountFormatter.format(saleAndDistributionObj.getTotalAmount().doubleValue()));
      } else {
         docheader.append(totalAmountFormatter.format(0));
      }
      docheader.append(addBlankSpace(94));

      return docheader.toString();
   }

   @Override
   protected String getSequenceNumber() throws CustomException {
      return salesAndDistributionBillingDetailsDAO.getSaleAndDistributionFileSequencesNumber();
   }

   @Override
   protected String getFileFolderDetails(String strDate, String seqNumber) throws CustomException {
      String toProcessFolder = sapInterfaceProcessDAO
            .getOutboundFileFolder(FileFolder.SAP_SD_OUTBOUND_TOPROCESS.toString());
      String fileName = this.createFileName(strDate, seqNumber);
      File file = new File(toProcessFolder, fileName);
      return file.getPath();
   }

   private String createFileName(String strDate, String seqNumber) {
      StringBuilder fileName = new StringBuilder();
      fileName.append("I062");
      fileName.append(strDate.substring(0, 6));
      fileName.append(StringUtils.leftPad(seqNumber, 6, '0'));
      fileName.append(".dat");
      return fileName.toString();
   }

   @Override
   protected void moveFile() throws CustomException {
      String toProcessFolder = sapInterfaceProcessDAO
            .getOutboundFileFolder(FileFolder.SAP_SD_OUTBOUND_TOPROCESS.toString());
      String processingFolder = sapInterfaceProcessDAO
            .getOutboundFileFolder(FileFolder.SAP_SD_OUTBOUND_PROCESSING.toString());
      String archivalFolder = sapInterfaceProcessDAO
            .getOutboundFileFolder(FileFolder.SAP_SD_OUTBOUND_ARCHIVED.toString());

      try {
         sapFileReader.copyFile(toProcessFolder, archivalFolder);// copy file to archival folder for backup
         sapFileReader.movingFileByFolder(toProcessFolder, processingFolder);// move file to processing folder
      } catch (Exception e) {
         lOgger.error(EXCEPTION, e);
         throw new CustomException(e.getMessage(), GENRATESDFile, ErrorType.ERROR);
      }

   }

}
