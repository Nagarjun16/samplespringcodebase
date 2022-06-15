package com.ngen.cosys.satssginterfaces.mss.service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.ngen.cosys.airmail.processor.MailbagProcessor;
import com.ngen.cosys.framework.constant.Action;
import com.ngen.cosys.framework.constant.ErrorType;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.satssginterfaces.mss.dao.MailExportAcceptanceDAO;
import com.ngen.cosys.satssginterfaces.mss.model.MailExportAcceptance;
import com.ngen.cosys.satssginterfaces.mss.model.MailExportAcceptanceRequest;
import com.ngen.cosys.satssginterfaces.mss.model.MailbagModel;
import com.ngen.cosys.satssginterfaces.mss.model.MailbagUtil;

@Service
public class MailExportAcceptanceServiceImpl implements MailExportAcceptanceService {

   @Autowired
   private MailExportAcceptanceDAO mailExportAcceptanceDAO;

   @Autowired
   private MailbagProcessor mailbagProcessor;

   /*
    * (non-Javadoc)
    * 
    * @see com.ngen.cosys.export.acceptance.service.MailExportAcceptanceService#
    * insertData(com.ngen.cosys.export.acceptance.model.
    * MailExportAcceptanceRequest)
    */
   @Override
   @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = CustomException.class)
   public MailExportAcceptanceRequest insertData(MailExportAcceptanceRequest request) throws CustomException {
      if (request.getUldNumber() == null && request.getWarehouseLocation() == null) {
         request.addError("enter.either.store.loc", "storeLocation", ErrorType.ERROR);
         request.addError("enter.either.warehouse.loc", "warehouseLocation", ErrorType.ERROR);
         throw new CustomException();
      }

      MailExportAcceptanceRequest mailDnObject = null;

      request.setSatsAssistedCar(mailExportAcceptanceDAO.isSatsAssistedFlight(request.getCarrierCode()));

      /*
       * Understanding is at any given point Shipment date injector will return only 1
       * shipment date and logical grouping can be done based on shipment number which
       * is Dispatch number
       */
      Map<Object, List<MailExportAcceptance>> acceptanceMap = request.getMailExportAcceptance().stream()
            .filter(a -> !a.getFlagCRUD().equalsIgnoreCase(Action.READ.toString()))
            .collect(Collectors.groupingBy(p -> p.getShipmentNumber()));

      if (request.isSatsAssistedCar()) {
         for (Map.Entry<Object, List<MailExportAcceptance>> accptEntryObj : acceptanceMap.entrySet()) {
            List<MailExportAcceptance> mailAcceptanceList = accptEntryObj.getValue();
            LocalDate shipmentDate = mailAcceptanceList.get(0).getShipmentDate();
            String shipmentNumber = (String) accptEntryObj.getKey();
            request.setShipmentNumber(shipmentNumber);
            request.setShipmentDate(shipmentDate);
            mailDnObject = mailExportAcceptanceDAO.getDnDetails(request);
            boolean flagUpdateRec = false;

            int newMailPieces = mailAcceptanceList.stream()
                  .filter(a -> a.getFlagCRUD().equalsIgnoreCase(Action.CREATE.toString()))
                  .map(MailExportAcceptance::getPieces).mapToInt(i -> i.intValue()).sum();
            double newMailWeight = mailAcceptanceList.stream()
                  .filter(a -> a.getFlagCRUD().equalsIgnoreCase(Action.CREATE.toString()))
                  .map(MailExportAcceptance::getWeight).mapToDouble(i -> i.doubleValue()).sum();

            if (null != mailDnObject) {
               flagUpdateRec = true;
               mailDnObject.setTotalPieces(mailDnObject.getTotalPieces().add(BigInteger.valueOf(newMailPieces)));
               mailDnObject.setTotalWeight(mailDnObject.getTotalWeight().add(BigDecimal.valueOf(newMailWeight)));
            } else {
               mailDnObject = new MailExportAcceptanceRequest();
               mailDnObject.setTotalPieces(BigInteger.valueOf(newMailPieces));
               mailDnObject.setTotalWeight(BigDecimal.valueOf(newMailWeight));
               mailDnObject.setServiceNumber(this.generateServiceNo());
               mailDnObject.setShipmentDate(shipmentDate);
               mailDnObject.setShipmentNumber(shipmentNumber);
            }

            mailDnObject.setAgentCode(request.getAgentCode());
            mailDnObject.setCarrierCode(request.getCarrierCode());
            mailDnObject.setCustomerId(mailExportAcceptanceDAO.getCustomerIdForPAFlight(request));
            mailDnObject.setDnOrigin(shipmentNumber.substring(2, 5));
            mailDnObject.setDnDestination(shipmentNumber.substring(8, 11));
            mailDnObject.setOriginOfficeExchange(shipmentNumber.substring(0, 6));
            mailDnObject.setDestinationOfficeExchange(shipmentNumber.substring(6, 12));
            mailDnObject.setMailBagMailCategory(shipmentNumber.substring(12, 13));
            mailDnObject.setMailBagDispatchYear(Integer.valueOf(shipmentNumber.substring(15, 16)));
            mailDnObject.setMailBagMailSubcategory(shipmentNumber.substring(13, 15));
            mailDnObject.setOutgoingCarrier(request.getOutgoingCarrier());
            mailDnObject.setTenantId(request.getTenantAirport());
            mailDnObject.setTerminal(request.getTerminal());
            mailDnObject.setUldNumber(request.getUldNumber());
            mailDnObject.setWarehouseLocation(request.getWarehouseLocation());
//            mailDnObject.setContainerLocation(request.getWarehouseLocation());
//            mailDnObject.setMailBagWeight( Integer.toString(Integer.parseInt(request.getMailBagWeight())/100));
            mailDnObject.setCreatedBy(request.getCreatedBy());
            if (flagUpdateRec) {
               mailAcceptanceList = mailAcceptanceList.stream()
                     .filter(a -> a.getFlagCRUD().equalsIgnoreCase(Action.CREATE.toString()))
                     .collect(Collectors.toList());
               if (!CollectionUtils.isEmpty(mailAcceptanceList)) {
                  mailExportAcceptanceDAO.updateDocPiecesWeight(mailDnObject);
                  mailExportAcceptanceDAO.updateShpMasterPiecesWeight(mailDnObject);

                  for (MailExportAcceptance e : mailAcceptanceList) {
                     e.setShipmentId(mailDnObject.getShipmentId());
                     e.setDocId(mailDnObject.getDocumentInfoId());
                     e.setUpdateDnNumber(request.getDnNumber());
                     e.setWarehouseLocation(request.getWarehouseLocation());
                     e.setCreatedBy(request.getCreatedBy());
                  }

                  mailExportAcceptanceDAO.insertEacceptnaceHouseInfo(mailAcceptanceList);
                  mailExportAcceptanceDAO.insertShipmentHouseInfo(mailAcceptanceList);
                  mailExportAcceptanceDAO.updateShipmentMasterComplete(mailDnObject.getShipmentId());
                  // Insert/Update into Shipment inventory
                  insertUpdateShipmentInventory(mailDnObject, mailAcceptanceList);
               }

            } else {
               MailExportAcceptanceRequest mail = mailExportAcceptanceDAO.insertServiceInfo(mailDnObject);
               mailDnObject.setServiceId(mail.getServiceId());
               try {
                  Thread.sleep(1000);
               } catch (InterruptedException ex) {
                  Thread.currentThread().interrupt();
               }
               mailExportAcceptanceDAO.insertDocumentInfo(mailDnObject);
               int DocumentInfoId = mailDnObject.getDocumentInfoId();

               MailExportAcceptanceRequest mail1 = mailExportAcceptanceDAO.insertShipmentinfo(mailDnObject);
               mailDnObject.setShipmentId(mail1.getShipmentId());
               int shipmentId = mailDnObject.getShipmentId();
               if (null != mailDnObject.getCustomerId()) {
                  mailExportAcceptanceDAO.insertShipmentCustomerInfo(mailDnObject);
               }
               mailExportAcceptanceDAO.insertShipmentHandlingAreaInfo(mailDnObject);

               mailAcceptanceList.forEach(e -> {
                  e.setShipmentId(shipmentId);
                  e.setDocId(DocumentInfoId);
                  e.setWarehouseLocation(request.getWarehouseLocation());
                  e.setCreatedBy(request.getCreatedBy());
               });
               mailExportAcceptanceDAO.insertEacceptnaceHouseInfo(mailAcceptanceList);
               mailExportAcceptanceDAO.insertShipmentHouseInfo(mailAcceptanceList);
               mailExportAcceptanceDAO.updateShipmentMasterComplete(shipmentId);
               insertUpdateShipmentInventory(mailDnObject, mailAcceptanceList);

            }
         }
      } else {
         request.getMailExportAcceptance().stream().forEach(e -> {

            e.setCarrierCode(request.getCarrierCode());
            e.setUldNumber(request.getUldNumber());
            e.setWarehouseLocation(request.getWarehouseLocation());
            String paFlightKey = e.getPaFlightKey();
            if (Optional.ofNullable(paFlightKey).isPresent()) {
               String[] s = paFlightKey.split(" ");
               e.setPaFlightKey(s[0]);
            }
         });
         MailExportAcceptanceRequest mail1 = mailExportAcceptanceDAO.insertShipmentinfo(mailDnObject);
         mailDnObject.setShipmentId(mail1.getShipmentId());
         int shipmentId = mailDnObject.getShipmentId();
         request.getMailExportAcceptance().forEach(e -> {
            e.setShipmentId(shipmentId);
            e.setWarehouseLocation(request.getWarehouseLocation());
         });
         mailExportAcceptanceDAO.insertShipmentHouseInfo(request.getMailExportAcceptance());

         mailDnObject = mailExportAcceptanceDAO.insertOuthouseInfo(request.getMailExportAcceptance());

      }
      if (StringUtils.isEmpty(request.getUldNumber())) {
         mailExportAcceptanceDAO.updateUldMasterLocation(request);
      }

      return request;
   }

   private String generateServiceNo() {
      LocalDateTime serviceNoInDateFormate = LocalDateTime.now();
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
      return serviceNoInDateFormate.format(formatter);
   }

   private void insertUpdateShipmentInventory(MailExportAcceptanceRequest mailDnObject,
         List<MailExportAcceptance> mailAcceptanceList) throws CustomException {

      Map<Object, List<MailExportAcceptance>> acceptanceMap = mailAcceptanceList.stream()
            .collect(Collectors.groupingBy(p -> Arrays.asList(p.getShipmentLocation(), p.getWarehouseLocation())));

      for (Map.Entry<Object, List<MailExportAcceptance>> acceptanceObj : acceptanceMap.entrySet()) {
         Object key = acceptanceObj.getKey();

         List<MailExportAcceptance> inventoryList = acceptanceObj.getValue();
         mailDnObject.setUldNumber(((List<String>) key).get(0));
         mailDnObject.setWarehouseLocation(((List<String>) key).get(1));
         mailDnObject.setInventoryPieces(BigInteger
               .valueOf(inventoryList.stream().map(MailExportAcceptance::getPieces).mapToInt(i -> i.intValue()).sum()));
         mailDnObject.setInventoryWeight(BigDecimal.valueOf(
               inventoryList.stream().map(MailExportAcceptance::getWeight).mapToDouble(i -> i.doubleValue()).sum()));
         if(StringUtils.isEmpty(mailDnObject.getWarehouseLocation())) {
            mailDnObject.setWarehouseLocation("TRUCKDOCK");
         }
         int objShipmentInvId = mailExportAcceptanceDAO.getShipmentInventoryId(mailDnObject);
         Object inventoryId = null;
         if (objShipmentInvId != 0) {
            inventoryId = (Object) objShipmentInvId;
         }
         if (Optional.ofNullable(inventoryId).isPresent()) {
            mailDnObject.setShipmentInventoryId((int) objShipmentInvId);
            mailExportAcceptanceDAO.updateShpInventoryPiecesWeight(mailDnObject);
         } else {
            mailExportAcceptanceDAO.insertShipmentInventory(mailDnObject);
            objShipmentInvId = mailDnObject.getShipmentInventoryId();
            mailExportAcceptanceDAO.insertShipmentInventorySHC(mailDnObject);
         }

         for (MailExportAcceptance ele : inventoryList) {

            ele.setShpInvId(objShipmentInvId);
         }
         mailExportAcceptanceDAO.insertShipmentInventoryHouse(inventoryList);
      }
   }

   private void validateSave(MailExportAcceptanceRequest request) throws CustomException {
      if (StringUtils.isEmpty(request.getAgentCode()) || StringUtils.isEmpty(request.getCarrierCode())) {
         throw new CustomException("MAILEXPORTACCPT02", "exportAcceptanceForm", ErrorType.ERROR);
      }
   }

   @Override
   public MailExportAcceptanceRequest getPAFlightMO(MailExportAcceptanceRequest request) throws CustomException {
      MailExportAcceptanceRequest finalResponse = new MailExportAcceptanceRequest();
      MailExportAcceptanceRequest resp = mailExportAcceptanceDAO.getPAFlightDetails(request);
      List<MailExportAcceptance> mailExportAcceptanceList = new ArrayList<>();
      MailExportAcceptance requestModel = new MailExportAcceptance();
      if (resp != null) {
         requestModel.setPaFlightDate(resp.getPaFlightDate());
         requestModel.setPaFlightId(resp.getPaFlightId());
         requestModel.setPaFlightKey(resp.getPaFlightKey());
         requestModel.setPaFlightSeg(resp.getPaFlightSeg());
         MailbagModel mailbagModel = MailbagUtil.getMailbag(request.getMailBagNumber());
         mailExportAcceptanceList.add(setMailbagModel(requestModel, mailbagModel));
         finalResponse.setMailExportAcceptance(mailExportAcceptanceList);
      } else {
         requestModel.setPaFlightId("");
         requestModel.setPaFlightKey("");
         MailbagModel mailbagModel = MailbagUtil.getMailbag(request.getMailBagNumber());
         mailExportAcceptanceList.add(setMailbagModel(requestModel, mailbagModel));
         finalResponse.setMailExportAcceptance(mailExportAcceptanceList);
      }
      return finalResponse;
   }

   private MailExportAcceptance setMailbagModel(MailExportAcceptance requestModel, MailbagModel mailbagModel) {
      requestModel.setMailBagNumber(mailbagModel.getMailBagNumber());
      requestModel.setOrigin(mailbagModel.getOriginAirport());
      requestModel.setCategory(mailbagModel.getCategory());
      requestModel.setMailType(mailbagModel.getSubCategory());
      requestModel.setYear(BigInteger.valueOf(Long.valueOf(mailbagModel.getYear())));
      requestModel.setDispatchNumber(mailbagModel.getDispatchNumber());
      requestModel.setReceptacleNumber(mailbagModel.getReceptableNumber());
      requestModel.setLastBagIndicator(Integer.valueOf(mailbagModel.getLastBagIndicator()));
      requestModel.setRegisteredIndicator(Integer.valueOf(mailbagModel.getRegisteredInsuredFlag()));
      requestModel.setWeight(BigDecimal.valueOf(Double.valueOf(mailbagModel.getWeight())));
      requestModel.setPieces(BigInteger.valueOf(1));
      requestModel.setOrigin(
            mailbagModel.getOriginCountry() + mailbagModel.getOriginAirport() + mailbagModel.getOriginQualifier());
      requestModel.setDestination(mailbagModel.getDestinationCountry() + mailbagModel.getDestinationAirport()
            + mailbagModel.getDestinationQualifier());
      return requestModel;
   }

}