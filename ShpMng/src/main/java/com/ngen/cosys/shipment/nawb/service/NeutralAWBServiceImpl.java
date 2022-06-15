/**
 * 
 * NeutralAWBServiceImpl.java
 * 
 * Copyright
 * 
 * <PRE>
 * <IMG SRC = XX></IMG>
 * </PRE>
 *
 * Version Date Author Reason 1.0 29 January, 2018 NIIT -
 */
package com.ngen.cosys.shipment.nawb.service;

import java.math.BigInteger;
import java.time.LocalDate;
/**
 * This Implementation Class takes care of the responsibilities of Neutral Awb
 * maintenance
 * 
 * @author NIIT Technologies Ltd
 *
 */
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import com.ngen.cosys.framework.constant.ErrorType;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.shipment.model.AgentInfo;
import com.ngen.cosys.shipment.nawb.dao.NeutralAWBDAO;
import com.ngen.cosys.shipment.nawb.model.NeutralAWBMaster;
import com.ngen.cosys.shipment.nawb.model.SIDHeaderDetail;
import com.ngen.cosys.shipment.nawb.model.SearchNAWBRQ;
import com.ngen.cosys.shipment.nawb.model.SearchSIDRQ;
import com.ngen.cosys.shipment.nawb.model.SearchStockRQ;
import com.ngen.cosys.shipment.nawb.model.Stock;
import com.ngen.cosys.shipment.validators.NeutralAWBValidator;

@Service
public class NeutralAWBServiceImpl implements NeutralAWBService {

   @Autowired
   private NeutralAWBDAO neutralAWBDAO;

   @Autowired
   private NeutralAWBValidator neutralAWBValidator;

   /*
    * (non-Javadoc)
    * 
    * @see
    * com.ngen.cosys.shipment.nawb.service.NeutralAWBService#searchSIDList(com.ngen
    * .cosys.shipment.nawb.model.SearchSIDRQ)
    */
   @Override
   public List<SIDHeaderDetail> searchSIDList(SearchSIDRQ searchSIDRQ) throws CustomException {
      return neutralAWBDAO.searchSIDList(searchSIDRQ);
   }

   /*
    * (non-Javadoc)
    * 
    * @see
    * com.ngen.cosys.shipment.nawb.service.NeutralAWBService#searchSIDDetails(com.
    * ngen.cosys.shipment.nawb.model.SearchSIDRQ)
    */
   @Override
   public NeutralAWBMaster searchSIDDetails(SearchNAWBRQ searchSIDRQ) throws CustomException {
      return neutralAWBDAO.searchSIDDetails(searchSIDRQ);
   }

   /*
    * (non-Javadoc)
    * 
    * @see
    * com.ngen.cosys.shipment.nawb.service.NeutralAWBService#searchAWBFromStockList
    * (com.ngen.cosys.shipment.nawb.model.SearchStockRQ)
    */
   @Override
   public List<Stock> searchAWBFromStockList(SearchStockRQ searchStockRQ) throws CustomException {
      return neutralAWBDAO.searchAWBFromStockList(searchStockRQ);
   }

   /*
    * (non-Javadoc)
    * 
    * @see
    * com.ngen.cosys.shipment.nawb.service.NeutralAWBService#searchNAWBDetails(com.
    * ngen.cosys.shipment.nawb.model.SearchNAWBRQ)
    */
   @Override
   public NeutralAWBMaster searchNAWBDetails(SearchNAWBRQ searchNAWBRQ) throws CustomException {
      NeutralAWBMaster nawbMaster = neutralAWBDAO.searchNAWBDetails(searchNAWBRQ);
      nawbMaster.getRouting().setFrom(nawbMaster.getOrigin());
      nawbMaster.getRouting().setTo(nawbMaster.getDestination());
      return nawbMaster;
   }

   /*
    * (non-Javadoc)
    * 
    * @see
    * com.ngen.cosys.shipment.nawb.service.NeutralAWBService#saveNAWB(com.ngen.
    * cosys.shipment.nawb.model.NeutralAWBMaster)
    */
   @Override
   @Transactional(readOnly = false, rollbackFor = Throwable.class)
   public NeutralAWBMaster saveNAWB(NeutralAWBMaster neutralAWBMaster) throws CustomException {
      String temp1 = neutralAWBMaster.getAwbNumber();
      String temp2 = neutralAWBMaster.getAwbNumber();
      String temp3 = temp1.substring(0, 3);
      String temp4 = temp2.substring(temp2.length() - 3);
      neutralAWBMaster.setAwbSuffix(temp4);
      neutralAWBMaster.setAwbPrefix(temp3);
      NeutralAWBMaster neutralAWBMasterResponse = null;
      Stock stock = new Stock();
      stock.setAwbNumber(neutralAWBMaster.getAwbNumber());
      SearchNAWBRQ searchNAWBRQ = new SearchNAWBRQ();
      searchNAWBRQ.setAwbNumber(neutralAWBMaster.getAwbNumber());
      searchNAWBRQ.setSidHeaderId(neutralAWBMaster.getSidHeaderId());
      neutralAWBMasterResponse = neutralAWBDAO.saveNAWBMasterDetails(neutralAWBMaster);
      BigInteger neutralAWBId = neutralAWBMasterResponse.getNeutralAWBId();
      BigInteger shipmentFreightWayBillId = neutralAWBMasterResponse.getShipmentFreightWayBillId();
      neutralAWBMaster.setNeutralAWBId(neutralAWBId);
      neutralAWBMaster.setShipmentFreightWayBillId(shipmentFreightWayBillId);
      neutralAWBMasterResponse.getShipperInfo().setNeutralAWBId(neutralAWBId);
      neutralAWBMasterResponse.getShipperInfo().setShipmentFreightWayBillId(shipmentFreightWayBillId.longValue());
      neutralAWBMasterResponse.getShipperInfo().setCustomerType("SHP");
      neutralAWBMasterResponse = neutralAWBDAO.saveNAWBShipperInfoDetails(neutralAWBMasterResponse);
      neutralAWBMasterResponse.getConsigneeInfo().setNeutralAWBId(neutralAWBId);
      neutralAWBMasterResponse.getConsigneeInfo().setShipmentFreightWayBillId(shipmentFreightWayBillId.longValue());
      neutralAWBMasterResponse.getConsigneeInfo().setCustomerType("CNE");

      neutralAWBMasterResponse = neutralAWBDAO.saveNAWBConsigneeInfoDetails(neutralAWBMasterResponse);
      BigInteger neutralAWBShipperCustomerInfoId = neutralAWBMasterResponse.getShipperInfo()
            .getNeutralAWBCustomerInfoId();
      neutralAWBMasterResponse.getShipperInfo().getCustomerAddressInfo()
            .setNeutralAWBCustomerInfoId(neutralAWBShipperCustomerInfoId);

      BigInteger neutralAWBConsigneeCustomerInfoId = neutralAWBMasterResponse.getConsigneeInfo()
            .getNeutralAWBCustomerInfoId();
      neutralAWBMasterResponse.getConsigneeInfo().getCustomerAddressInfo()
            .setNeutralAWBCustomerInfoId(neutralAWBConsigneeCustomerInfoId);
      // FWB shipper and consignee info id s
      long neutralFWBShipperCustomerInfoId = neutralAWBMasterResponse.getShipperInfo()
            .getShipmentFreightWayBillCustomerInfoId();
      neutralAWBMasterResponse.getShipperInfo().getCustomerAddressInfo()
            .setShipmentFreightWayBillCustomerInfoId(neutralFWBShipperCustomerInfoId);

      long neutralFWBConsigneeCustomerInfoId = neutralAWBMasterResponse.getConsigneeInfo()
            .getShipmentFreightWayBillCustomerInfoId();
      neutralAWBMasterResponse.getConsigneeInfo().getCustomerAddressInfo()
            .setShipmentFreightWayBillCustomerInfoId(neutralFWBConsigneeCustomerInfoId);

      neutralAWBMasterResponse = neutralAWBDAO.saveNAWBShipperAddressInfoDetails(neutralAWBMasterResponse);
      BigInteger neutralAWBShipperCustomerAddressInfoId = neutralAWBMasterResponse.getShipperInfo()
            .getCustomerAddressInfo().getNeutralAWBCustomerAddressInfoId();
      // FWB shipper customer address info id
      long neutralFWBShipperCustomerAddressInfoId = neutralAWBMasterResponse.getShipperInfo().getCustomerAddressInfo()
            .getShipmentFreightWayBillCustomerAddressInfoId();

      neutralAWBMasterResponse = neutralAWBDAO.saveNAWBConsigneeAddressInfoDetails(neutralAWBMasterResponse);
      BigInteger neutralAWBConsigneeCustomerAddressInfoId = neutralAWBMasterResponse.getConsigneeInfo()
            .getCustomerAddressInfo().getNeutralAWBCustomerAddressInfoId();
      long neutralFWBConsigneeCustomerAddressInfoId = neutralAWBMasterResponse.getConsigneeInfo()
            .getCustomerAddressInfo().getShipmentFreightWayBillCustomerAddressInfoId();

      neutralAWBMasterResponse.getConsigneeInfo().getCustomerAddressInfo().getCustomerContactInfo().forEach(contact -> {
         contact.setNeutralAWBCustomerAddressInfoId(neutralAWBConsigneeCustomerAddressInfoId);
         contact.setShipmentFreightWayBillCustomerAddressInfoId(neutralFWBConsigneeCustomerAddressInfoId);
      });
      neutralAWBDAO.saveNAWBConsigneeContactInfoDetails(neutralAWBMasterResponse);

      neutralAWBMasterResponse.getShipperInfo().getCustomerAddressInfo().getCustomerContactInfo().forEach(contact -> {
         contact.setNeutralAWBCustomerAddressInfoId(neutralAWBShipperCustomerAddressInfoId);
         contact.setShipmentFreightWayBillCustomerAddressInfoId(neutralFWBShipperCustomerAddressInfoId);
      });
      neutralAWBDAO.saveNAWBShipperContactInfoDetails(neutralAWBMasterResponse);

      neutralAWBMasterResponse.getAgentInfo().setNeutralAWBId(neutralAWBId);
      neutralAWBMasterResponse.getAgentInfo().setShipmentFreightWayBillId(shipmentFreightWayBillId.longValue());
      neutralAWBMasterResponse = neutralAWBDAO.saveAgentInfo(neutralAWBMaster);
      neutralAWBMasterResponse = neutralAWBDAO.saveNAWBRoutingDetails(neutralAWBMaster);
      neutralAWBMasterResponse = neutralAWBDAO.saveNAWBAccountingInfoDetails(neutralAWBMaster);
      neutralAWBMasterResponse = neutralAWBDAO.saveNAWBSpecialHandlingDetails(neutralAWBMaster);
      neutralAWBMasterResponse = neutralAWBDAO.saveNAWBCommodityDetails(neutralAWBMaster);
      neutralAWBMasterResponse = neutralAWBDAO.saveNAWBChargeDetails(neutralAWBMaster);
      neutralAWBMasterResponse = neutralAWBDAO.saveNAWBCustomsDetails(neutralAWBMaster);
      neutralAWBMasterResponse = neutralAWBDAO.saveAwbFlight(neutralAWBMaster);
      issueAWBInStock(stock);
      updateIssuedNumer(neutralAWBMaster);
      return neutralAWBMasterResponse;
   }

   /*
    * (non-Javadoc)
    * 
    * @see
    * com.ngen.cosys.shipment.nawb.service.NeutralAWBService#issueAWBInStock(com.
    * ngen.cosys.shipment.nawb.model.Stock)
    */
   @Override
   public Stock issueAWBInStock(Stock stock) throws CustomException {
      return neutralAWBDAO.issueAWBInStock(stock);
   }

   @Override
   public int updateIssuedNumer(NeutralAWBMaster neutralAWBMaster) throws CustomException {
      neutralAWBMaster.setStatus("ISSUED");
      neutralAWBMaster.setRequestNumber(neutralAWBMaster.getSidNumber());
      neutralAWBMaster.setShipmentNumber(neutralAWBMaster.getAwbNumber());
      return neutralAWBDAO.updateIssuedNumer(neutralAWBMaster);
   }

   // --------------------------------------------------------------------------------------------------------------------
   @Override
   @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
   public NeutralAWBMaster saveneutralAWB(NeutralAWBMaster neutralAWBMaster) throws CustomException {
      // validations
      if (ObjectUtils.isEmpty(neutralAWBMaster.getCarriersExecutionDate())) {
         neutralAWBMaster.setCarriersExecutionDate(LocalDate.now());
      }

      if (neutralAWBMaster.getAwbNumber() == null || neutralAWBMaster.getAwbNumber() == "") {
         neutralAWBMaster.addError("export.select.awb.number", "awbNumber", ErrorType.ERROR);
         neutralAWBMaster.setFlagError(true);
      }
      neutralAWBValidator.validateNAWBModel(neutralAWBMaster);
      neutralAWBValidator.checkDuplicateDestination(neutralAWBMaster);
      neutralAWBValidator.checkDuplicateDueAgent(neutralAWBMaster);
      neutralAWBValidator.checkDuplicateDueCarrier(neutralAWBMaster);
      neutralAWBValidator.validateCommodity(neutralAWBMaster);
      neutralAWBValidator.validateDueAgent(neutralAWBMaster);
      neutralAWBValidator.validateDueCarrier(neutralAWBMaster);
      neutralAWBValidator.checkValidChargeDeclaration(neutralAWBMaster);
      neutralAWBValidator.checkAlsoNotify(neutralAWBMaster);
      neutralAWBValidator.validateSsrOci(neutralAWBMaster);

      if (neutralAWBMaster.isFlagError()) {
         throw new CustomException();
      } else {
         // stock for issuing Awb in stock and updating status to issued.
         Stock stock = new Stock();
         stock.setAwbNumber(neutralAWBMaster.getAwbNumber());
         neutralAWBMaster.setAwbPrefix(neutralAWBMaster.getAwbNumber().substring(0, 3));
         neutralAWBMaster
               .setAwbSuffix(neutralAWBMaster.getAwbNumber().substring(3, neutralAWBMaster.getAwbNumber().length()));
         NeutralAWBMaster neutralAWBMasterResponse = neutralAWBDAO.saveneutralAWB(neutralAWBMaster);

         // insertig or updating shipper details
         neutralAWBMasterResponse.getShipperInfo().setCustomerType("SHP");
         neutralAWBMasterResponse.getShipperInfo().setNeutralAWBId(neutralAWBMasterResponse.getNeutralAWBId());
         neutralAWBMasterResponse.getShipperInfo()
               .setShipmentFreightWayBillId(neutralAWBMasterResponse.getShipmentFreightWayBillId().longValue());
         neutralAWBMasterResponse = neutralAWBDAO.saveNAWBShipperInfoDetails(neutralAWBMasterResponse);
         BigInteger neutralAWBShipperCustomerInfoId = neutralAWBMasterResponse.getShipperInfo()
               .getNeutralAWBCustomerInfoId();
         long shipmentFreightWayBillCustomerInfoId = neutralAWBMasterResponse.getShipperInfo()
               .getShipmentFreightWayBillCustomerInfoId();
         neutralAWBMaster.getShipperInfo().getCustomerAddressInfo()
               .setNeutralAWBCustomerInfoId(neutralAWBShipperCustomerInfoId);
         neutralAWBMaster.getShipperInfo().getCustomerAddressInfo()
               .setShipmentFreightWayBillCustomerInfoId(shipmentFreightWayBillCustomerInfoId);
         neutralAWBMasterResponse = neutralAWBDAO.saveNAWBShipperAddressInfoDetails(neutralAWBMasterResponse);
         BigInteger neutralAWBShipperCustomerAddressInfoId = neutralAWBMasterResponse.getShipperInfo()
               .getCustomerAddressInfo().getNeutralAWBCustomerAddressInfoId();
         long shipmentFreightWayBillCustomerAddressInfoId = neutralAWBMasterResponse.getShipperInfo()
               .getCustomerAddressInfo().getShipmentFreightWayBillCustomerAddressInfoId();
         neutralAWBMasterResponse.getShipperInfo().getCustomerAddressInfo().getCustomerContactInfo()
               .forEach(contact -> {
                  contact.setNeutralAWBCustomerAddressInfoId(neutralAWBShipperCustomerAddressInfoId);
                  contact.setShipmentFreightWayBillCustomerAddressInfoId(shipmentFreightWayBillCustomerAddressInfoId);
               });
         neutralAWBDAO.saveNAWBShipperContactInfoDetails(neutralAWBMasterResponse);

         // inserting or updating consignee details
         neutralAWBMasterResponse.getConsigneeInfo().setCustomerType("CNE");
         neutralAWBMasterResponse.getConsigneeInfo().setNeutralAWBId(neutralAWBMasterResponse.getNeutralAWBId());
         neutralAWBMasterResponse.getConsigneeInfo()
               .setShipmentFreightWayBillId(neutralAWBMasterResponse.getShipmentFreightWayBillId().longValue());
         neutralAWBMasterResponse = neutralAWBDAO.saveNAWBConsigneeInfoDetails(neutralAWBMasterResponse);
         BigInteger neutralAWBConsigneeCustomerInfoId = neutralAWBMasterResponse.getConsigneeInfo()
               .getNeutralAWBCustomerInfoId();
         long shipmentFreightWayBillConsigneeCustomerInfoId = neutralAWBMasterResponse.getConsigneeInfo()
               .getShipmentFreightWayBillCustomerInfoId();
         neutralAWBMasterResponse.getConsigneeInfo().getCustomerAddressInfo()
               .setNeutralAWBCustomerInfoId(neutralAWBConsigneeCustomerInfoId);
         neutralAWBMasterResponse.getConsigneeInfo().getCustomerAddressInfo()
               .setShipmentFreightWayBillCustomerInfoId(shipmentFreightWayBillConsigneeCustomerInfoId);
         neutralAWBMasterResponse = neutralAWBDAO.saveNAWBConsigneeAddressInfoDetails(neutralAWBMasterResponse);
         BigInteger neutralAWBConsigneeCustomerAddressInfoId = neutralAWBMasterResponse.getConsigneeInfo()
               .getCustomerAddressInfo().getNeutralAWBCustomerAddressInfoId();
         long neutralFWBConsigneeCustomerAddressInfoId = neutralAWBMasterResponse.getConsigneeInfo()
               .getCustomerAddressInfo().getShipmentFreightWayBillCustomerAddressInfoId();
         neutralAWBMasterResponse.getConsigneeInfo().getCustomerAddressInfo().getCustomerContactInfo()
               .forEach(contact -> {
                  contact.setNeutralAWBCustomerAddressInfoId(neutralAWBConsigneeCustomerAddressInfoId);
                  contact.setShipmentFreightWayBillCustomerAddressInfoId(neutralFWBConsigneeCustomerAddressInfoId);
               });
         neutralAWBDAO.saveNAWBConsigneeContactInfoDetails(neutralAWBMasterResponse);

         // inserting or updating also notify details
         if (neutralAWBMasterResponse.getAlsoNotify().getCustomerName() != null
               && neutralAWBMasterResponse.getAlsoNotify().getCustomerAddressInfo().getStreetAddress1() != null
               && neutralAWBMasterResponse.getAlsoNotify().getCustomerAddressInfo().getCustomerPlace() != null
               && neutralAWBMasterResponse.getAlsoNotify().getCustomerAddressInfo().getCountryCode() != null) {
            neutralAWBMasterResponse.getAlsoNotify().setNeutralAWBId(neutralAWBMasterResponse.getNeutralAWBId());
            neutralAWBMasterResponse.getAlsoNotify()
                  .setShipmentFreightWayBillId(neutralAWBMasterResponse.getShipmentFreightWayBillId().longValue());
            neutralAWBMasterResponse = neutralAWBDAO.saveNAWBAlsoNotifyInfoDetails(neutralAWBMasterResponse);
            BigInteger neutralAWBAlsoNotifyCustomerInfoId = neutralAWBMasterResponse.getAlsoNotify()
                  .getNeutralAWBCustomerInfoId();
            long neutralFWBAlsoNotifyCustomerInfoId = neutralAWBMasterResponse.getAlsoNotify()
                  .getShipmentFreightWayBillCustomerInfoId();
            neutralAWBMasterResponse.getAlsoNotify().getCustomerAddressInfo()
                  .setNeutralAWBCustomerInfoId(neutralAWBAlsoNotifyCustomerInfoId);
            neutralAWBMasterResponse.getAlsoNotify().getCustomerAddressInfo()
                  .setShipmentFreightWayBillCustomerInfoId(neutralFWBAlsoNotifyCustomerInfoId);
            ;
            neutralAWBMasterResponse = neutralAWBDAO.saveNAWBAlsoNotifyAddressInfoDetails(neutralAWBMasterResponse);
            BigInteger neutralAWBAlsoNotifyCustomerAddressInfoId = neutralAWBMasterResponse.getAlsoNotify()
                  .getCustomerAddressInfo().getNeutralAWBCustomerAddressInfoId();
            long neutralFWBAlsoNotifyCustomerAddressInfoId = neutralAWBMasterResponse.getAlsoNotify()
                  .getCustomerAddressInfo().getShipmentFreightWayBillCustomerAddressInfoId();
            neutralAWBMasterResponse.getAlsoNotify().getCustomerAddressInfo().getCustomerContactInfo()
                  .forEach(contact -> {
                     contact.setNeutralAWBCustomerAddressInfoId(neutralAWBAlsoNotifyCustomerAddressInfoId);
                     contact.setShipmentFreightWayBillCustomerAddressInfoId(neutralFWBAlsoNotifyCustomerAddressInfoId);
                  });
            neutralAWBDAO.saveNAWBAlsoNotifyContactInfoDetails(neutralAWBMasterResponse);
         }

         // inserting or updating routing
         neutralAWBMasterResponse = neutralAWBDAO.saveNAWBRoutingDetails(neutralAWBMaster);
         // inserting or updating flight booking
         neutralAWBMasterResponse = neutralAWBDAO.saveAwbFlight(neutralAWBMaster);

         // inserting or updating agent info
         neutralAWBMasterResponse.getAgentInfo().setNeutralAWBId(neutralAWBMasterResponse.getNeutralAWBId());
         neutralAWBMasterResponse.getAgentInfo()
               .setShipmentFreightWayBillId(neutralAWBMasterResponse.getShipmentFreightWayBillId().longValue());
         neutralAWBMasterResponse = neutralAWBDAO.saveAgentInfo(neutralAWBMaster);

         // insert or update shc
         neutralAWBMasterResponse = neutralAWBDAO.saveNAWBSpecialHandlingDetails(neutralAWBMaster);

         // inserting or updating accounting info
         neutralAWBMasterResponse = neutralAWBDAO.saveNAWBAccountingInfoDetails(neutralAWBMasterResponse);

         // inserting or updating rate description
         neutralAWBMasterResponse = neutralAWBDAO.saveRateDescription(neutralAWBMasterResponse);

         // inserting or updating rate description other info
         neutralAWBMasterResponse = neutralAWBDAO.saveRateDescriptionInfo(neutralAWBMasterResponse);

         // inserting or updating handling information
         neutralAWBMasterResponse = neutralAWBDAO.saveHandlingInformation(neutralAWBMasterResponse);

         // inserting or updating charge declaration
         neutralAWBMasterResponse = neutralAWBDAO.saveNAWBChargeDetails(neutralAWBMasterResponse);

         // inserting or updating oci information
         neutralAWBMasterResponse = neutralAWBDAO.saveNAWBCustomsDetails(neutralAWBMasterResponse);

         // inserting or updating local Authorization
         neutralAWBMasterResponse = neutralAWBDAO.localAuthorization(neutralAWBMasterResponse);
         BigInteger neutralAWBLocalAuthorityInfoId = neutralAWBMasterResponse.getNeutralAwbCustoms()
               .getNeutralAWBLocalAuthorityInfoId();
         neutralAWBMasterResponse.getNeutralAwbCustoms().getNeutralAWBLocalAuthDetails().stream()
               .forEach(l -> l.setNeutralAWBLocalAuthorityInfoId(neutralAWBLocalAuthorityInfoId));
         neutralAWBMasterResponse = neutralAWBDAO.localAuthorizationDetails(neutralAWBMasterResponse);

         // inserting or updating other charges
         neutralAWBMasterResponse = neutralAWBDAO.saveOtherChargesDueAgent(neutralAWBMasterResponse);
         neutralAWBMasterResponse = neutralAWBDAO.saveOtherChargesDueCarrier(neutralAWBMasterResponse);

         // inserting or updating ppd col value
         neutralAWBMasterResponse = neutralAWBDAO.savePPdDetails(neutralAWBMasterResponse);
         neutralAWBMasterResponse = neutralAWBDAO.saveColDetails(neutralAWBMasterResponse);
         issueAWBInStock(stock);
         updateIssuedNumer(neutralAWBMaster);
         return neutralAWBMasterResponse;
      }
   }

   @Override
   public AgentInfo fetchAgentInfo(AgentInfo agent) throws CustomException {
      return neutralAWBDAO.fetchAgentInfo(agent);
   }

   @Override
   public String getAirlineName(String prefix) throws CustomException {
      return neutralAWBDAO.getAirlineName(prefix);
   }

	@Override
	public SearchStockRQ updateInProcessForAwbNumber(SearchStockRQ searchStockRQ) throws CustomException {
		return neutralAWBDAO.updateInProcessForAwbNumber(searchStockRQ);
	}

	@Override
	public boolean checkIfImportShipment(SearchNAWBRQ searchSIDRQ) throws CustomException {
		return neutralAWBDAO.checkIfImportShipment(searchSIDRQ);
	}

	@Override
	public Boolean checkMalOcsForNawb(NeutralAWBMaster nawbShipment) throws CustomException {
		return this.neutralAWBDAO.checkMalOcsForNawb(nawbShipment);
	}

}