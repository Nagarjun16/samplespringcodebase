/**
 * PrintAWBBarCodeServiceImpl.java
 * 
 * Copyright
 * 
 * <PRE>
 * <IMG SRC = XX></IMG>
 * </PRE>
 *
 * Version Date Author Reason 1.0 23 January, 2018 NIIT -
 */
package com.ngen.cosys.impbd.service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ngen.cosys.framework.constant.ErrorType;
import com.ngen.cosys.framework.constant.SaveIndicator;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.impbd.model.AWBPrintRequest;
import com.ngen.cosys.impbd.model.AWBPrintResponse;
import com.ngen.cosys.impbd.model.CdhDocumentmaster;
import com.ngen.cosys.impbd.model.Flight;
import com.ngen.cosys.impbd.model.Printer;
import com.ngen.cosys.impbd.printer.util.PrinterService;
import com.ngen.cosys.impbd.shipment.document.service.ShipmentMasterService;
import com.ngen.cosys.service.util.enums.PrinterType;
import com.ngen.cosys.service.util.enums.ReportRequestType;
import com.ngen.cosys.service.util.model.ReportRequest;
import com.ngen.cosys.shipment.dao.ShipmentVerificationDAO;
import com.ngen.cosys.timezone.util.TenantZoneTime;

/**
 * This class takes care of the responsibilities related to the PrintAWBBarCode
 * Service operations that comes from the Controller.
 * 
 * @author NIIT Technologies Ltd
 *
 */
@Service
public class PrintAWBBarCodeServiceImpl implements PrintAWBBarCodeService {

   private static final Logger LOGGER = LoggerFactory.getLogger(PrintAWBBarCodeServiceImpl.class);

   @Autowired
   private ShipmentVerificationDAO shipmentVerificationDAO;

   @Autowired
   private PrinterService printerService;

   @Autowired
   private ShipmentMasterService shipmentMasService;

   /*
    * (non-Javadoc)
    * 
    * @see
    * com.ngen.cosys.shipment.service.PrintAWBBarCodeService#validateAWBNumber(com.
    * ngen.cosys.shipment.model.SearchAWBManifest)
    */
   @Override
   public AWBPrintRequest validateAWBNumber(AWBPrintRequest awbDetail) throws CustomException {
      if (shipmentVerificationDAO.validateAWBNumber(awbDetail)) {
         awbDetail.setFlagSaved(SaveIndicator.YES.toString());
         List<AWBPrintResponse> shipmentInfo = shipmentVerificationDAO.getShipmentInformation(awbDetail);
         shipmentInfo.forEach(item -> {
            Flight flight = new Flight();
            flight.setFlightKey(item.getFlightKey());
            flight.setFlightId(item.getFlight());
            awbDetail.getFlightDetails().add(flight);
         });
      } else {
         awbDetail.setFlagSaved(SaveIndicator.NO.toString());
      }

      AWBPrintRequest docInfo = shipmentVerificationDAO.checkShipmentReceiveOriginallyStatus(awbDetail.getAwbNumber());
      if (!ObjectUtils.isEmpty(docInfo) && !StringUtils.isEmpty(docInfo.getPhotoCopyValue())) {
         awbDetail.setPhotoCopy(true);
      }
      if (!ObjectUtils.isEmpty(docInfo) && !StringUtils.isEmpty(docInfo.getDocumentReceivedValue())) {
         awbDetail.setDocumentReceivedFlag(true);
      }
      return awbDetail;
   }

   /*
    * (non-Javadoc)
    * 
    * @see
    * com.ngen.cosys.barcodeprint.service.PrintAWBBarCodeService#printBarcode(com.
    * ngen.cosys.barcodeprint.model.AWBPrintRequest)
    */
   @Override
   public AWBPrintRequest printBarcode(AWBPrintRequest shpInfo) throws CustomException {
      AWBPrintResponse shipmentInfo = shipmentVerificationDAO.getShipmentInformationByFlightId(shpInfo);
      String shipmentId = shipmentVerificationDAO.checkShipmentExistInShipmentMaster(shpInfo.getAwbNumber());
      if (shipmentInfo == null) {
         shipmentInfo = new AWBPrintResponse();
      }
      shipmentInfo.setShipmentId(shipmentId);
      shipmentInfo.setShipmentType("AWB");
      shipmentInfo.setAwbNumber(shpInfo.getAwbNumber());
      if (shipmentId == null) {
         shipmentVerificationDAO.saveShipmentMasterDetails(shipmentInfo);
      }

      shipmentInfo.setDocumentReceivedFlag(true);
      shipmentInfo.setPhotoCopyAwbFlag(shpInfo.isPhotoCopy());
      shipmentInfo.setBarcodePrintedFlag(true);
      if (!SaveIndicator.YES.toString().equalsIgnoreCase(shpInfo.getFlagSaved())) {

         if (StringUtils.isEmpty(shpInfo.getFlightKeyId())) {
            throw new CustomException("emptyFlightkeyErr", null, ErrorType.ERROR);
         }
         if (StringUtils.isEmpty(shpInfo.getFlightDate())) {
            throw new CustomException("emptyFlightDateErr", null, ErrorType.ERROR);
         }

         String flightId = shipmentVerificationDAO.getFlightIdByFlightKeyAndDate(shpInfo);
         if (StringUtils.isEmpty(flightId)) {
            throw new CustomException("awb.FlightNotFound", null, ErrorType.ERROR);
         }
         shipmentInfo.setPieces(BigDecimal.ZERO);
         shipmentInfo.setWeight(BigDecimal.ZERO);
         shipmentInfo.setFlight(flightId);
         shipmentInfo.setCargoIrregularityCode("FDAW");
         Random rand = new Random();
         // Generate random integers in range 0 to 999
         int transactionSequenceNumber = rand.nextInt(1000);
         shipmentInfo.setTransactionSequenceNo(BigInteger.valueOf(transactionSequenceNumber));

         if (!shipmentVerificationDAO.checkShipmentIrregularityDetails(shipmentInfo)) {
            shipmentVerificationDAO.saveShipmentIrregularityDetails(shipmentInfo);
         }

      }
      AWBPrintResponse shipmentDetails = shipmentVerificationDAO.checkShipmentVerificationDetails(shpInfo);
      if (shipmentDetails != null) {
         shipmentInfo.setPhotoCopyAwbFlag(shpInfo.isPhotoCopy());
         shipmentVerificationDAO.updateShipmentVerificationDetails(shipmentInfo);
      } else {
         shipmentVerificationDAO.saveShipmentVerificationDetails(shipmentInfo);
      }
      Printer printer = new Printer();
      printer.setAwbNumBarcode(shpInfo.getAwbNumber());
      printer.setAwbNumTextCode(shpInfo.getAwbNumber());
      printer.setPrinterName(shpInfo.getPrinterName());
      printer.setPhotoCopy(shpInfo.isPhotoCopy());
      printer.setFlightNo(shpInfo.getFltKey());
      printer.setCreatedBy(shpInfo.getCreatedBy());
      printAWBTag(printer);
      printAuditTrail(printer);
      return shpInfo;
   }

   public void printAWBTag(Printer printer) {
      try {
         ReportRequest report = new ReportRequest();
         report.setRequestType(ReportRequestType.PRINT);
         report.setPrinterType(PrinterType.AWB_BARCODE);
         report.setQueueName(printer.getPrinterName());

         Map<String, Object> parameters = new HashMap<>();
         parameters.put("awbNumBarCode", printer.getAwbNumBarcode());
         parameters.put("awbNumTextCode", printer.getAwbNumTextCode());

         report.setParameters(parameters);
         if (null != report.getQueueName()) {
            printerService.printReport(report);
         }
      } catch (Exception e) {
         LOGGER.error("Not Printing", e);
      }
   }

   public void printAuditTrail(Printer printer) throws CustomException {
      Map<String, Object> flightMap = new HashMap<>();
      flightMap.put("Flight Key", printer.getFlightNo());
      flightMap.put("AWB Number", printer.getAwbNumTextCode());
      flightMap.put("Photo Copy", printer.isPhotoCopy());
      Map<String, Object> auditMap = new HashMap<>();
      auditMap.put("eventDateTime",
    		  TenantZoneTime.getZoneDateTime(LocalDateTime.now(), printer.getTenantId()).toString());
      auditMap.put("eventActor", printer.getCreatedBy());
      auditMap.put("eventName", "INBOUND_PRINT_AWB_BARCODE");
      auditMap.put("eventAction", "INSERT");
      auditMap.put("entityType", "AWB");
      auditMap.put("entityValue", printer.getAwbNumTextCode());
      ObjectMapper obj = new ObjectMapper();
      String jsonStr = null;
      try {
         HashMap<String, Object> eventMap = new HashMap<>();
         eventMap.put("eventValue", flightMap);
         jsonStr = obj.writeValueAsString(eventMap);
      } catch (JsonProcessingException e) {
         // Do nothing
      }
      auditMap.put("eventValue", jsonStr);
      shipmentVerificationDAO.captutreAuditTrail((HashMap<String, Object>) auditMap);
   }

   @Override
   public AWBPrintRequest printMultiBarcode(List<AWBPrintRequest> shpInfo) throws CustomException {
      if (!CollectionUtils.isEmpty(shpInfo)) {

         // Set the first 4 digit number from AWB Suffix
         shpInfo.forEach(t -> {
            // Check if the ShipmentNumber is empty OR not if no extract first 4 digit number from AWB Suffix
            if (!StringUtils.isEmpty(t.getAwbNumber())) {
               t.setFirstFourDigitsAfterPrefix(Integer
            		   .valueOf(t.getAwbNumber().substring(3, t.getAwbNumber().length()-4)));
            }
         });

         // Sort the list
         List<AWBPrintRequest> sortedShpInfo = shpInfo.stream()
               .sorted(Comparator.comparingInt(AWBPrintRequest::getFirstFourDigitsAfterPrefix)).collect(Collectors.toList());

         for (AWBPrintRequest awbPrintRequest : sortedShpInfo) {
            CdhDocumentmaster modeldata = new CdhDocumentmaster();
            modeldata.setCopyno(0);
            modeldata.setCarriercode(awbPrintRequest.getCarrierCode());
            modeldata.setDestination(awbPrintRequest.getDestination());
            modeldata.setFlightoffpoint(awbPrintRequest.getFlightOffPoint());
            modeldata.setShipmentid(awbPrintRequest.getShipmentId());
            modeldata.setShipmentNumber(awbPrintRequest.getAwbNumber());
            modeldata.setPrinterForAT(awbPrintRequest.getPrinterForAT());
            // Print Barcode
            printAwbBarcode(awbPrintRequest);

            // Insert/update Data To Cdh_DocumentMaster
            shipmentMasService.updateCDHShipmetMasterData(modeldata);

         }
      }
      return null;
   }

   private void printAwbBarcode(AWBPrintRequest request) {
      ReportRequest report = new ReportRequest();
      report.setRequestType(ReportRequestType.PRINT);
      report.setPrinterType(PrinterType.AWB_BARCODE);
      report.setQueueName(request.getPrinterName());
      Map<String, Object> parameters = new HashMap<>();
      parameters.put("awbNumBarCode", request.getAwbNumber());
      parameters.put("awbNumTextCode", request.getAwbNumber());
      report.setParameters(parameters);
      if (null != report.getQueueName()) {
         printerService.printReport(report);
      }
   }

}