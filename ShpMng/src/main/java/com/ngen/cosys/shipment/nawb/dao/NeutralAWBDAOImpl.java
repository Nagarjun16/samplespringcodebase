/**
 * 
 * NeutralAWBDAOImpl.java
 * 
 * Copyright
 * 
 * <PRE>
 * <IMG SRC = XX></IMG>
 * </PRE>
 *
 * Version Date Author Reason 1.0 29 January, 2018 NIIT -
 */
package com.ngen.cosys.shipment.nawb.dao;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.ngen.cosys.audit.NgenAuditAction;
import com.ngen.cosys.audit.NgenAuditEntityType;
import com.ngen.cosys.audit.NgenAuditEventType;
import com.ngen.cosys.framework.constant.ErrorType;
import com.ngen.cosys.framework.dao.BaseDAO;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.shipment.enums.NeutralAWBQueryIds;
import com.ngen.cosys.shipment.model.AccountingInfo;
import com.ngen.cosys.shipment.model.AgentInfo;
import com.ngen.cosys.shipment.model.ChargeDeclaration;
import com.ngen.cosys.shipment.model.CustomerContactInfo;
import com.ngen.cosys.shipment.model.FlightBooking;
import com.ngen.cosys.shipment.model.NeutralAWBLocalAuthDetails;
import com.ngen.cosys.shipment.model.OtherCharges;
import com.ngen.cosys.shipment.model.OtherCustomsInfo;
import com.ngen.cosys.shipment.model.RateDescOtherInfo;
import com.ngen.cosys.shipment.model.RateDescription;
import com.ngen.cosys.shipment.model.Routing;
import com.ngen.cosys.shipment.model.SHC;
import com.ngen.cosys.shipment.model.SSROSIInfo;
import com.ngen.cosys.shipment.nawb.model.NawbChargeValues;
import com.ngen.cosys.shipment.nawb.model.NeutralAWBMaster;
import com.ngen.cosys.shipment.nawb.model.SIDHeaderDetail;
import com.ngen.cosys.shipment.nawb.model.SearchNAWBRQ;
import com.ngen.cosys.shipment.nawb.model.SearchSIDRQ;
import com.ngen.cosys.shipment.nawb.model.SearchStockRQ;
import com.ngen.cosys.shipment.nawb.model.Stock;

/**
 * This Implementation Class takes care of CRUD operation for Neutral AWB with
 * database
 * 
 * @author NIIT Technologies Ltd
 *
 */
@Repository("neutralAWBDAO")
public class NeutralAWBDAOImpl extends BaseDAO implements NeutralAWBDAO {

   @Autowired
   @Qualifier("sqlSessionTemplate")
   private SqlSession sqlSessionNeutralAWB;

   /*
    * (non-Javadoc)
    * 
    * @see
    * com.ngen.cosys.shipment.nawb.dao.NeutralAWBDAO#searchSIDList(com.ngen.cosys.
    * shipment.nawb.model.SearchSIDRQ)
    */
   @Override
   public List<SIDHeaderDetail> searchSIDList(SearchSIDRQ searchSIDRQ) throws CustomException {
      return fetchList(NeutralAWBQueryIds.FETCH_SID_LIST.getQueryId(), searchSIDRQ, sqlSessionNeutralAWB);
   }

   /*
    * (non-Javadoc)
    * 
    * @see
    * com.ngen.cosys.shipment.nawb.dao.NeutralAWBDAO#searchSIDDetails(com.ngen.
    * cosys.shipment.nawb.model.SearchSIDRQ)
    */
   @Override
   public NeutralAWBMaster searchSIDDetails(SearchNAWBRQ searchSIDRQ) throws CustomException {
		String create = "C";
		NeutralAWBMaster neutralAWBMasterResponse = searchNAWBDetails(searchSIDRQ);
		if (neutralAWBMasterResponse == null) {
			neutralAWBMasterResponse = fetchObject(NeutralAWBQueryIds.SEARCH_SID_DETAILS.getQueryId(),
					searchSIDRQ.getSidHeaderId(), sqlSessionNeutralAWB);
			if (!Optional.ofNullable(neutralAWBMasterResponse).isPresent()) {
				List<NeutralAWBMaster> neutralAWBMasterResponses = fetchList(
						NeutralAWBQueryIds.SEARCH_AWB_RESERVATION_DETAILS.getQueryId(), searchSIDRQ,
						sqlSessionNeutralAWB);
				if (!CollectionUtils.isEmpty(neutralAWBMasterResponses)) {
					neutralAWBMasterResponse = neutralAWBMasterResponses.get(0);
					
					if("MAIL".equalsIgnoreCase(neutralAWBMasterResponse.getStockCategoryCode())) {
						SHC specialHandlingCode = new SHC();
						specialHandlingCode.setSpecialHandlingCode("MAL");
						specialHandlingCode.setFlagCRUD(create);
						
						List<SHC> defaultSHCs = new ArrayList<>();
						defaultSHCs.add(specialHandlingCode);
						
						neutralAWBMasterResponse.setShcCode(defaultSHCs);
					}else if("OCS".equalsIgnoreCase(neutralAWBMasterResponse.getStockCategoryCode())) {
						SHC specialHandlingCode = new SHC();
						specialHandlingCode.setSpecialHandlingCode("OCS");
						specialHandlingCode.setFlagCRUD(create);
						
						List<SHC> defaultSHCs = new ArrayList<>();
						defaultSHCs.add(specialHandlingCode);
						
						neutralAWBMasterResponse.setShcCode(defaultSHCs);
					}
					
					neutralAWBMasterResponse.setIsAwbReservation(true);
				}
			}

			if (!ObjectUtils.isEmpty(neutralAWBMasterResponse)) {

				if (Optional.ofNullable(neutralAWBMasterResponse.getShipperInfo()).isPresent()
						&& Optional.ofNullable(neutralAWBMasterResponse.getShipperInfo().getCustomerAddressInfo())
								.isPresent()
						&& Optional.ofNullable(neutralAWBMasterResponse.getShipperInfo().getCustomerAddressInfo()
								.getCustomerContactInfo()).isPresent()) {
					neutralAWBMasterResponse.getShipperInfo().getCustomerAddressInfo().getCustomerContactInfo().stream()
							.forEach(e -> e.setFlagCRUD(create));
				}

				if (Optional.ofNullable(neutralAWBMasterResponse.getConsigneeInfo()).isPresent()
						&& Optional.ofNullable(neutralAWBMasterResponse.getConsigneeInfo().getCustomerAddressInfo())
								.isPresent()
						&& Optional.ofNullable(neutralAWBMasterResponse.getConsigneeInfo().getCustomerAddressInfo()
								.getCustomerContactInfo()).isPresent()) {

					neutralAWBMasterResponse.getConsigneeInfo().getCustomerAddressInfo().getCustomerContactInfo()
							.stream().forEach(e -> e.setFlagCRUD(create));
				}

				if (Optional.ofNullable(neutralAWBMasterResponse.getAlsoNotify()).isPresent()
						&& Optional.ofNullable(neutralAWBMasterResponse.getAlsoNotify().getCustomerAddressInfo())
								.isPresent()
						&& Optional.ofNullable(neutralAWBMasterResponse.getAlsoNotify().getCustomerAddressInfo()
								.getCustomerContactInfo()).isPresent()) {
					neutralAWBMasterResponse.getAlsoNotify().getCustomerAddressInfo().getCustomerContactInfo().stream()
							.forEach(e -> e.setFlagCRUD(create));
				}

				if (Optional.ofNullable(neutralAWBMasterResponse.getRoutingList()).isPresent()) {
					neutralAWBMasterResponse.getRoutingList().stream().forEach(r -> r.setFlagCRUD(create));
				}
				if (Optional.ofNullable(neutralAWBMasterResponse.getFlightBookingList()).isPresent()) {
					neutralAWBMasterResponse.getFlightBookingList().stream().forEach(r -> r.setFlagCRUD(create));
				}

				if (Optional.ofNullable(neutralAWBMasterResponse.getAccountingInfo()).isPresent()) {
					neutralAWBMasterResponse.getAccountingInfo().stream().forEach(a -> a.setFlagCRUD(create));
				}
				if (Optional.ofNullable(neutralAWBMasterResponse.getRateDescription()).isPresent()) {
					neutralAWBMasterResponse.getRateDescription().stream().forEach(a -> a.setFlagCRUD(create));
				}
				if (Optional.ofNullable(neutralAWBMasterResponse.getRateDescriptionOtherInfo()).isPresent()) {
					neutralAWBMasterResponse.getRateDescriptionOtherInfo().stream().forEach(a -> a.setFlagCRUD(create));
				}
			}
		}

		if (!ObjectUtils.isEmpty(neutralAWBMasterResponse)) {

			// Instantiate a new object for charge declaration
			if (ObjectUtils.isEmpty(neutralAWBMasterResponse.getChargeDeclaration())) {
				ChargeDeclaration chargeDeclaration = new ChargeDeclaration();
				neutralAWBMasterResponse.setChargeDeclaration(chargeDeclaration);
			}

			if (!ObjectUtils
					.isEmpty(neutralAWBMasterResponse.getChargeDeclaration().getCarriageValueDeclarationNawb())) {
				neutralAWBMasterResponse.getChargeDeclaration().setCarriageValueDeclaration(
						neutralAWBMasterResponse.getChargeDeclaration().getCarriageValueDeclarationNawb().toString());
			} else {
				neutralAWBMasterResponse.getChargeDeclaration().setCarriageValueDeclaration("NVD");
			}
			if (!ObjectUtils
					.isEmpty(neutralAWBMasterResponse.getChargeDeclaration().getCustomsValueDeclarationNawb())) {
				neutralAWBMasterResponse.getChargeDeclaration().setCustomsValueDeclaration(
						neutralAWBMasterResponse.getChargeDeclaration().getCustomsValueDeclarationNawb().toString());
			} else {
				neutralAWBMasterResponse.getChargeDeclaration().setCustomsValueDeclaration("NCV");
			}
			if (!ObjectUtils
					.isEmpty(neutralAWBMasterResponse.getChargeDeclaration().getInsuranceValueDeclarationNawb())) {
				neutralAWBMasterResponse.getChargeDeclaration().setInsuranceValueDeclaration(
						neutralAWBMasterResponse.getChargeDeclaration().getInsuranceValueDeclarationNawb().toString());
			} else {
				neutralAWBMasterResponse.getChargeDeclaration().setInsuranceValueDeclaration("XXX");
			}
		}
      return neutralAWBMasterResponse;
   }

   /*
    * (non-Javadoc)
    * 
    * @see
    * com.ngen.cosys.shipment.nawb.dao.NeutralAWBDAO#searchAWBFromStockList(com.
    * ngen.cosys.shipment.nawb.model.SearchStockRQ)
    */
   @Override
   public List<Stock> searchAWBFromStockList(SearchStockRQ searchStockRQ) throws CustomException {
      return fetchList(NeutralAWBQueryIds.FETCH_AWB_FROM_STOCK_LIST.getQueryId(), searchStockRQ, sqlSessionNeutralAWB);
   }

   /*
    * (non-Javadoc)
    * 
    * @see
    * com.ngen.cosys.shipment.nawb.dao.NeutralAWBDAO#searchNAWBDetails(com.ngen.
    * cosys.shipment.nawb.model.SearchNAWBRQ)
    */
   @Override
   public NeutralAWBMaster searchNAWBDetails(SearchNAWBRQ searchNAWBRQ) throws CustomException {
      NeutralAWBMaster response = fetchObject(NeutralAWBQueryIds.SEARCH_NAWB_DETAILS.getQueryId(),
            searchNAWBRQ, sqlSessionNeutralAWB);

      // Check if NAWB is not created and FWB is created earlier
      if (ObjectUtils.isEmpty(response)) {
         // Check if the FWB data exists then throw error that FWB should not be created
         // for a NAWB case
         NeutralAWBMaster neutralAWBMaster = new NeutralAWBMaster();
         neutralAWBMaster.setAwbNumber(searchNAWBRQ.getAwbNumber());
         neutralAWBMaster.setAwbDate(searchNAWBRQ.getAwbDate());
         BigInteger fwbId = fetchObject("fetchNeutralFWBMaster", neutralAWBMaster, sqlSessionNeutralAWB);

         if (!ObjectUtils.isEmpty(fwbId)) {
            throw new CustomException("fwb.existsi.nawb", null, ErrorType.ERROR);
         }
      }

      return response;

   }

   /*
    * (non-Javadoc)
    * 
    * @see
    * com.ngen.cosys.shipment.nawb.dao.NeutralAWBDAO#saveNAWBMasterDetails(com.ngen
    * .cosys.shipment.nawb.model.NeutralAWBMaster)
    */
   @Override
   public NeutralAWBMaster saveNAWBMasterDetails(NeutralAWBMaster neutralAWBMaster) throws CustomException {
      BigInteger awbId = fetchNawbID(neutralAWBMaster);
      neutralAWBMaster.setNeutralAWBId(awbId);
      BigInteger fwbId = fetchObject("fetchNeutralFWBMaster", neutralAWBMaster, sqlSessionNeutralAWB);
      neutralAWBMaster.setShipmentFreightWayBillId(fwbId);
      int updatedRecordCount = updateData("updateNeutralAWBMaster", neutralAWBMaster, sqlSessionNeutralAWB);
      if (updatedRecordCount == 0) {
         insertData(NeutralAWBQueryIds.INSERT_NAWB_MASTER_DETAILS.getQueryId(), neutralAWBMaster, sqlSessionNeutralAWB);
      }
      updatedRecordCount = updateData("updateNeutralFWBMaster", neutralAWBMaster, sqlSessionNeutralAWB);
      if (updatedRecordCount == 0) {
         insertData("insertNeutralAWBMasterToFWBDetails", neutralAWBMaster, sqlSessionNeutralAWB);
      }
      return neutralAWBMaster;
   }

   /*
    * (non-Javadoc)
    * 
    * @see
    * com.ngen.cosys.shipment.nawb.dao.NeutralAWBDAO#saveNAWBShipperInfoDetails(com
    * .ngen.cosys.shipment.nawb.model.NeutralAWBMaster)
    */
   @Override
   public NeutralAWBMaster saveNAWBShipperInfoDetails(NeutralAWBMaster neutralAWBMaster) throws CustomException {

      neutralAWBMaster.getShipperInfo().setNeutralAWBId(neutralAWBMaster.getNeutralAWBId());
      int updatedRecordCount = updateData("updateAwbcustomerinfo", neutralAWBMaster.getShipperInfo(),
            sqlSessionNeutralAWB);
      if (updatedRecordCount == 0) {
         insertData("sqlinsertNeutralAWBCustomerInfoDetails", neutralAWBMaster.getShipperInfo(), sqlSessionNeutralAWB);
      } else {
         BigInteger id = fetchObject("fetchNeutralAWBCustomerInfoId", neutralAWBMaster.getShipperInfo(),
               sqlSessionNeutralAWB);
         neutralAWBMaster.getShipperInfo().setNeutralAWBCustomerInfoId(id);
      }
      // FWB Table insert
      neutralAWBMaster.getShipperInfo()
            .setShipmentFreightWayBillId(neutralAWBMaster.getShipmentFreightWayBillId().longValue());
      updatedRecordCount = updateData("updateNeutralFwbcustomerinfo", neutralAWBMaster.getShipperInfo(),
            sqlSessionNeutralAWB);
      if (updatedRecordCount == 0) {
         insertData("sqlinsertNeutralFWBCustomerInfoDetails", neutralAWBMaster.getShipperInfo(), sqlSessionNeutralAWB);
      } else {
         BigInteger id = fetchObject("fetchNeutralFWBCustomerInfoId", neutralAWBMaster.getShipperInfo(),
               sqlSessionNeutralAWB);
         neutralAWBMaster.getShipperInfo().setShipmentFreightWayBillCustomerInfoId(id.longValue());
      }
      return neutralAWBMaster;
   }

   /*
    * (non-Javadoc)
    * 
    * @see
    * com.ngen.cosys.shipment.nawb.dao.NeutralAWBDAO#saveNAWBConsigneeInfoDetails(
    * com.ngen.cosys.shipment.nawb.model.NeutralAWBMaster)
    */
   @Override
   public NeutralAWBMaster saveNAWBConsigneeInfoDetails(NeutralAWBMaster neutralAWBMaster) throws CustomException {
      neutralAWBMaster.getConsigneeInfo().setNeutralAWBId(neutralAWBMaster.getNeutralAWBId());
      int updatedRecordCount = updateData("updateAwbcustomerinfo", neutralAWBMaster.getConsigneeInfo(),
            sqlSessionNeutralAWB);
      if (updatedRecordCount == 0) {
         insertData("sqlinsertNeutralAWBCustomerInfoDetails", neutralAWBMaster.getConsigneeInfo(),
               sqlSessionNeutralAWB);
      } else {
         BigInteger id = fetchObject("fetchNeutralAWBCustomerInfoId", neutralAWBMaster.getConsigneeInfo(),
               sqlSessionNeutralAWB);
         neutralAWBMaster.getConsigneeInfo().setNeutralAWBCustomerInfoId(id);
      }
      // FWB Table insert
      neutralAWBMaster.getConsigneeInfo()
            .setShipmentFreightWayBillId(neutralAWBMaster.getShipmentFreightWayBillId().longValue());
      updatedRecordCount = updateData("updateNeutralFwbcustomerinfo", neutralAWBMaster.getConsigneeInfo(),
            sqlSessionNeutralAWB);
      if (updatedRecordCount == 0) {
         insertData("sqlinsertNeutralFWBCustomerInfoDetails", neutralAWBMaster.getConsigneeInfo(),
               sqlSessionNeutralAWB);
      } else {
         BigInteger id = fetchObject("fetchNeutralFWBCustomerInfoId", neutralAWBMaster.getConsigneeInfo(),
               sqlSessionNeutralAWB);
         neutralAWBMaster.getConsigneeInfo().setShipmentFreightWayBillCustomerInfoId(id.longValue());
      }
      return neutralAWBMaster;
   }

   /*
    * (non-Javadoc)
    * 
    * @see
    * com.ngen.cosys.shipment.nawb.dao.NeutralAWBDAO#saveSHCDetails(com.ngen.cosys.
    * shipment.nawb.model.NeutralAWBMaster)
    */
   @Override
   public NeutralAWBMaster saveSHCDetails(NeutralAWBMaster neutralAWBMaster) throws CustomException {
      ArrayList<?> success = insertList(NeutralAWBQueryIds.INSERT_NAWB_SHC_DETAILS.getQueryId(),
            neutralAWBMaster.getShcCode(), sqlSessionNeutralAWB);
      return (success != null ? neutralAWBMaster : null);
   }

   /*
    * (non-Javadoc)
    * 
    * @see
    * com.ngen.cosys.shipment.nawb.dao.NeutralAWBDAO#issueAWBInStock(com.ngen.cosys
    * .shipment.nawb.model.Stock)
    */
   @Override
   public Stock issueAWBInStock(Stock stock) throws CustomException {
      int success = updateData(NeutralAWBQueryIds.ISSUE_AWB_NUMBER_IN_STOCK.getQueryId(), stock, sqlSessionNeutralAWB);
      return (success == 1 ? stock : null);
   }

   /*
    * (non-Javadoc)
    * 
    * @see com.ngen.cosys.shipment.nawb.dao.NeutralAWBDAO#
    * saveNAWBShipperAddressInfoDetails(com.ngen.cosys.shipment.nawb.model.
    * NeutralAWBMaster)
    */
   @Override
   public NeutralAWBMaster saveNAWBShipperAddressInfoDetails(NeutralAWBMaster neutralAWBMaster) throws CustomException {

      int updatedRecordCount = updateData("updateCustomeraddressinfo",
            neutralAWBMaster.getShipperInfo().getCustomerAddressInfo(), sqlSessionNeutralAWB);
      if (updatedRecordCount == 0) {
         insertData(NeutralAWBQueryIds.INSERT_NAWB_CUSTOMER_ADDRESS_DETAILS.getQueryId(),
               neutralAWBMaster.getShipperInfo().getCustomerAddressInfo(), sqlSessionNeutralAWB);
      }
      // FWB table insertion
      updatedRecordCount = updateData("updateNeutralFWBCustomeraddressinfo",
            neutralAWBMaster.getShipperInfo().getCustomerAddressInfo(), sqlSessionNeutralAWB);
      if (updatedRecordCount == 0) {
         insertData("insertNeutralFWBCustomerAddressDetails",
               neutralAWBMaster.getShipperInfo().getCustomerAddressInfo(), sqlSessionNeutralAWB);
      }
      return neutralAWBMaster;
   }

   @Override
   public NeutralAWBMaster saveNAWBConsigneeAddressInfoDetails(NeutralAWBMaster neutralAWBMaster)
         throws CustomException {

      int updatedRecordCount = updateData("updateCustomeraddressinfo",
            neutralAWBMaster.getConsigneeInfo().getCustomerAddressInfo(), sqlSessionNeutralAWB);
      if (updatedRecordCount == 0) {
         insertData(NeutralAWBQueryIds.INSERT_NAWB_CUSTOMER_ADDRESS_DETAILS.getQueryId(),
               neutralAWBMaster.getConsigneeInfo().getCustomerAddressInfo(), sqlSessionNeutralAWB);
      }
      // FWB table insertion
      updatedRecordCount = updateData("updateNeutralFWBCustomeraddressinfo",
            neutralAWBMaster.getConsigneeInfo().getCustomerAddressInfo(), sqlSessionNeutralAWB);
      if (updatedRecordCount == 0) {
         insertData("insertNeutralFWBCustomerAddressDetails",
               neutralAWBMaster.getConsigneeInfo().getCustomerAddressInfo(), sqlSessionNeutralAWB);
      }

      return neutralAWBMaster;
   }

   /*
    * (non-Javadoc)
    * 
    * @see com.ngen.cosys.shipment.nawb.dao.NeutralAWBDAO#
    * saveNAWBShipperContactInfoDetails(com.ngen.cosys.shipment.nawb.model.
    * NeutralAWBMaster)
    */
   @Override
   public NeutralAWBMaster saveNAWBShipperContactInfoDetails(NeutralAWBMaster neutralAWBMaster) throws CustomException {
      for (CustomerContactInfo i : neutralAWBMaster.getShipperInfo().getCustomerAddressInfo()
            .getCustomerContactInfo()) {
         if (!i.getFlagCRUD().equals("R")) {
            i.setNeutralAWBCustomerAddressInfoId(
                  neutralAWBMaster.getShipperInfo().getCustomerAddressInfo().getNeutralAWBCustomerAddressInfoId());
            i.setShipmentFreightWayBillCustomerAddressInfoId(neutralAWBMaster.getShipperInfo().getCustomerAddressInfo()
                  .getShipmentFreightWayBillCustomerAddressInfoId());
            deleteData("deleteCustomercontactinfo", i, sqlSessionNeutralAWB);
            deleteData("deleteNeutralFWBCustomercontactinfo", i, sqlSessionNeutralAWB);
         }
      }

      for (CustomerContactInfo i : neutralAWBMaster.getShipperInfo().getCustomerAddressInfo()
            .getCustomerContactInfo()) {
         if (!i.getFlagCRUD().equals("D") && !i.getFlagCRUD().equals("R") && i.getContactIdentifier() != null
               && i.getContactDetail() != null) {
            i.setNeutralAWBCustomerAddressInfoId(
                  neutralAWBMaster.getShipperInfo().getCustomerAddressInfo().getNeutralAWBCustomerAddressInfoId());
            i.setShipmentFreightWayBillCustomerAddressInfoId(neutralAWBMaster.getShipperInfo().getCustomerAddressInfo()
                  .getShipmentFreightWayBillCustomerAddressInfoId());
            insertData(NeutralAWBQueryIds.INSERT_NAWB_CUSTOMER_CONTACT_INFO_DETAILS.getQueryId(), i,
                  sqlSessionNeutralAWB);
            insertData("insertNeutralFWBCustomerContactInfoDetails", i, sqlSessionNeutralAWB);
         }
      }
      return neutralAWBMaster;
   }

   /*
    * (non-Javadoc)
    * 
    * @see com.ngen.cosys.shipment.nawb.dao.NeutralAWBDAO#
    * saveNAWBShipperContactInfoDetails(com.ngen.cosys.shipment.nawb.model.
    * NeutralAWBMaster)
    */
   @Override
   public NeutralAWBMaster saveNAWBConsigneeContactInfoDetails(NeutralAWBMaster neutralAWBMaster)
         throws CustomException {
      for (CustomerContactInfo i : neutralAWBMaster.getConsigneeInfo().getCustomerAddressInfo()
            .getCustomerContactInfo()) {
         if (!i.getFlagCRUD().equals("R")) {
            i.setNeutralAWBCustomerAddressInfoId(
                  neutralAWBMaster.getConsigneeInfo().getCustomerAddressInfo().getNeutralAWBCustomerAddressInfoId());
            i.setShipmentFreightWayBillCustomerAddressInfoId(neutralAWBMaster.getConsigneeInfo()
                  .getCustomerAddressInfo().getShipmentFreightWayBillCustomerAddressInfoId());
            deleteData("deleteCustomercontactinfo", i, sqlSessionNeutralAWB);
            deleteData("deleteNeutralFWBCustomercontactinfo", i, sqlSessionNeutralAWB);
         }
      }

      for (CustomerContactInfo i : neutralAWBMaster.getConsigneeInfo().getCustomerAddressInfo()
            .getCustomerContactInfo()) {
         if (!i.getFlagCRUD().equals("D") && !i.getFlagCRUD().equals("R") && i.getContactIdentifier() != null
               && i.getContactDetail() != null) {
            i.setNeutralAWBCustomerAddressInfoId(
                  neutralAWBMaster.getConsigneeInfo().getCustomerAddressInfo().getNeutralAWBCustomerAddressInfoId());
            i.setShipmentFreightWayBillCustomerAddressInfoId(neutralAWBMaster.getConsigneeInfo()
                  .getCustomerAddressInfo().getShipmentFreightWayBillCustomerAddressInfoId());
            insertData(NeutralAWBQueryIds.INSERT_NAWB_CUSTOMER_CONTACT_INFO_DETAILS.getQueryId(), i,
                  sqlSessionNeutralAWB);
            insertData("insertNeutralFWBCustomerContactInfoDetails", i, sqlSessionNeutralAWB);
         }
      }
      return neutralAWBMaster;
   }

   /*
    * (non-Javadoc)
    * 
    * @see
    * com.ngen.cosys.shipment.nawb.dao.NeutralAWBDAO#saveNAWBRoutingDetails(com.
    * ngen.cosys.shipment.nawb.model.NeutralAWBMaster)
    */
   @Override
   public NeutralAWBMaster saveNAWBRoutingDetails(NeutralAWBMaster neutralAWBMaster) throws CustomException {
      neutralAWBMaster.getRoutingList().stream().forEach(r -> {
         r.setNeutralAWBId(neutralAWBMaster.getNeutralAWBId());
         r.setShipmentFreightWayBillId(neutralAWBMaster.getShipmentFreightWayBillId().longValue());
      });
      for (Routing i : neutralAWBMaster.getRoutingList()) {
         if (!i.getFlagCRUD().equals("R")) {
            deleteData("deleteRouting", i, sqlSessionNeutralAWB);
            deleteData("deleteFWBRouting", i, sqlSessionNeutralAWB);
         }
      }

      for (Routing i : neutralAWBMaster.getRoutingList()) {
         if (!i.getFlagCRUD().equals("D") && !i.getFlagCRUD().equals("R") && i.getTo() != null
               && i.getCarrierCode() != null) {
            insertData(NeutralAWBQueryIds.INSERT_NAWB_ROUTING.getQueryId(), i, sqlSessionNeutralAWB);
            insertData("insertNeutralFWBRouting", i, sqlSessionNeutralAWB);
         }
      }
      return neutralAWBMaster;
   }

   /*
    * (non-Javadoc)
    * 
    * @see
    * com.ngen.cosys.shipment.nawb.dao.NeutralAWBDAO#saveNAWBAccountingInfoDetails(
    * com.ngen.cosys.shipment.nawb.model.NeutralAWBMaster)
    */
   @Override
   public NeutralAWBMaster saveNAWBAccountingInfoDetails(NeutralAWBMaster neutralAWBMaster) throws CustomException {
      neutralAWBMaster.getAccountingInfo().stream().forEach(a -> {
         a.setNeutralAWBId(neutralAWBMaster.getNeutralAWBId());
         a.setShipmentFreightWayBillId(neutralAWBMaster.getShipmentFreightWayBillId().longValue());
      });
      for (AccountingInfo a : neutralAWBMaster.getAccountingInfo()) {
         if (a.getFlagCRUD().equals("C") && !StringUtils.isEmpty(a.getInformationIdentifier())) {
            BigInteger id = fetchObject("fetchNeutralAWBAccountingInfo", a, sqlSessionNeutralAWB);
            if (ObjectUtils.isEmpty(id)) {
               insertData(NeutralAWBQueryIds.INSERT_NAWB_ACCOUNTING_INFO.getQueryId(), a, sqlSessionNeutralAWB);
            } else {
               a.setExpNeutralAWBAccountingInfoId(id);
            }
         } else if (a.getFlagCRUD().equals("U") && !StringUtils.isEmpty(a.getInformationIdentifier())) {
            deleteData("deleteAccountinginfo", a, sqlSessionNeutralAWB);
            insertData(NeutralAWBQueryIds.INSERT_NAWB_ACCOUNTING_INFO.getQueryId(), a, sqlSessionNeutralAWB);
         } else if (a.getFlagCRUD().equals("D") && !StringUtils.isEmpty(a.getInformationIdentifier())) {
            deleteData("deleteAccountinginfo", a, sqlSessionNeutralAWB);
         }
         // FWB tables
         if (a.getFlagCRUD().equals("C") && !StringUtils.isEmpty(a.getInformationIdentifier())) {
            BigInteger id = fetchObject("fetchNeutralFWBAccountingInfo", a, sqlSessionNeutralAWB);
            if (ObjectUtils.isEmpty(id)) {
               insertData("insertNeutralFWBAccountingInfo", a, sqlSessionNeutralAWB);
            } else {
               a.setShipmentFreightWayBillAccountingInformationId(id.longValue());
            }
         } else if (a.getFlagCRUD().equals("U") && !StringUtils.isEmpty(a.getInformationIdentifier())) {
            deleteData("deleteNeutralFWBAccountinginfo", a, sqlSessionNeutralAWB);
            insertData("insertNeutralFWBAccountingInfo", a, sqlSessionNeutralAWB);
         } else if (a.getFlagCRUD().equals("D") && !StringUtils.isEmpty(a.getInformationIdentifier())) {
            deleteData("deleteNeutralFWBAccountinginfo", a, sqlSessionNeutralAWB);
         }
      }

      return neutralAWBMaster;
   }

   /*
    * (non-Javadoc)
    * 
    * @see
    * com.ngen.cosys.shipment.nawb.dao.NeutralAWBDAO#saveNAWBSpecialHandlingDetails
    * (com.ngen.cosys.shipment.nawb.model.NeutralAWBMaster)
    */
   @Override
   public NeutralAWBMaster saveNAWBSpecialHandlingDetails(NeutralAWBMaster neutralAWBMaster) throws CustomException {

      neutralAWBMaster.getShcCode().stream().forEach(a -> {
         a.setNeutralAWBId(neutralAWBMaster.getNeutralAWBId());
         a.setShipmentFreightWayBillId(neutralAWBMaster.getShipmentFreightWayBillId().longValue());
      });
      deleteData("deleteShc", neutralAWBMaster.getNeutralAWBId(), sqlSessionNeutralAWB);
      deleteData("deleteNeutralFWBShc", neutralAWBMaster.getShipmentFreightWayBillId(), sqlSessionNeutralAWB);
      if (!neutralAWBMaster.getShcCode().isEmpty()
            && neutralAWBMaster.getShcCode().get(0).getSpecialHandlingCode() != null) {
         insertData(NeutralAWBQueryIds.INSERT_NAWB_SHC_DETAILS.getQueryId(), neutralAWBMaster.getShcCode(),
               sqlSessionNeutralAWB);
         insertData("insertNeutralFWBSHCDetails", neutralAWBMaster.getShcCode(), sqlSessionNeutralAWB);
      }
      return neutralAWBMaster;
   }

   /*
    * (non-Javadoc)
    * 
    * @see
    * com.ngen.cosys.shipment.nawb.dao.NeutralAWBDAO#saveNAWBCommodityDetails(com.
    * ngen.cosys.shipment.nawb.model.NeutralAWBMaster)
    */
   @Override
   public NeutralAWBMaster saveNAWBCommodityDetails(NeutralAWBMaster neutralAWBMaster) throws CustomException {

      neutralAWBMaster.getRateDescription().stream().forEach(a -> {
         a.setNeutralAWBId(neutralAWBMaster.getNeutralAWBId());
         a.setShipmentFreightWayBillId(neutralAWBMaster.getShipmentFreightWayBillId().longValue());
      });
      deleteData("deleteRateDesc", neutralAWBMaster.getRateDescription(), sqlSessionNeutralAWB);
      deleteData("deleteRateDescOtherInfo", neutralAWBMaster.getRateDescriptionOtherInfo(), sqlSessionNeutralAWB);
      deleteData("deleteNeutralFWBRateDesc", neutralAWBMaster.getRateDescription(), sqlSessionNeutralAWB);
      deleteData("deleteNeutralFWBRateDescOtherInfo", neutralAWBMaster.getRateDescriptionOtherInfo(),
            sqlSessionNeutralAWB);
      insertData(NeutralAWBQueryIds.INSERT_NAWB_RATE_DESCRIPTION.getQueryId(), neutralAWBMaster.getRateDescription(),
            sqlSessionNeutralAWB);
      insertData(NeutralAWBQueryIds.INSERT_NAWB_RATE_DESC_OTHER_INFO.getQueryId(),
            neutralAWBMaster.getRateDescriptionOtherInfo(), sqlSessionNeutralAWB);
      insertData("insertNeutralFWBRateDescription", neutralAWBMaster.getRateDescription(), sqlSessionNeutralAWB);
      insertData("insertNeutralFWBRateDescOtherInfo", neutralAWBMaster.getRateDescriptionOtherInfo(),
            sqlSessionNeutralAWB);
      return neutralAWBMaster;

   }

   /*
    * (non-Javadoc)
    * 
    * @see
    * com.ngen.cosys.shipment.nawb.dao.NeutralAWBDAO#saveNAWBChargeDetails(com.ngen
    * .cosys.shipment.nawb.model.NeutralAWBMaster)
    */
   @Override
   public NeutralAWBMaster saveNAWBChargeDetails(NeutralAWBMaster neutralAWBMaster) throws CustomException {

      neutralAWBMaster.getChargeDeclaration().setNeutralAWBId(neutralAWBMaster.getNeutralAWBId());
      neutralAWBMaster.getChargeDeclaration()
            .setShipmentFreightWayBillId(neutralAWBMaster.getShipmentFreightWayBillId().longValue());
      if (neutralAWBMaster.getChargeDeclaration().getCarriageValueDeclaration() == null) {
         neutralAWBMaster.getChargeDeclaration().setNoCarriageValueDeclared("NVD");
         neutralAWBMaster.getChargeDeclaration().setCarriageValueDeclarationNawb(null);
      } else {
         if (!StringUtils.isEmpty(neutralAWBMaster.getChargeDeclaration().getCarriageValueDeclaration())
               && !neutralAWBMaster.getChargeDeclaration().getCarriageValueDeclaration().equalsIgnoreCase("NVD")) {
            neutralAWBMaster.getChargeDeclaration().setCarriageValueDeclarationNawb(
                  new BigDecimal(neutralAWBMaster.getChargeDeclaration().getCarriageValueDeclaration()));
         } else {
            neutralAWBMaster.getChargeDeclaration().setCarriageValueDeclarationNawb(null);
         }
      }
      if (neutralAWBMaster.getChargeDeclaration().getCustomsValueDeclaration() == null) {
         neutralAWBMaster.getChargeDeclaration().setNoCustomsValueDeclared("NCV");
         neutralAWBMaster.getChargeDeclaration().setCustomsValueDeclarationNawb(null);
      } else {
         if (!StringUtils.isEmpty(neutralAWBMaster.getChargeDeclaration().getCustomsValueDeclaration())
               && !neutralAWBMaster.getChargeDeclaration().getCustomsValueDeclaration().equalsIgnoreCase("NCV")) {
            neutralAWBMaster.getChargeDeclaration().setCustomsValueDeclarationNawb(
                  new BigDecimal(neutralAWBMaster.getChargeDeclaration().getCustomsValueDeclaration()));
         } else {
            neutralAWBMaster.getChargeDeclaration().setCustomsValueDeclarationNawb(null);
         }
      }
      if (neutralAWBMaster.getChargeDeclaration().getInsuranceValueDeclaration() == null) {
         neutralAWBMaster.getChargeDeclaration().setNoInsuranceValueDeclared("XXX");
         neutralAWBMaster.getChargeDeclaration().setInsuranceValueDeclarationNawb(null);
      } else {
         if (!StringUtils.isEmpty(neutralAWBMaster.getChargeDeclaration().getInsuranceValueDeclaration())
               && !neutralAWBMaster.getChargeDeclaration().getInsuranceValueDeclaration().equalsIgnoreCase("XXX")) {
            neutralAWBMaster.getChargeDeclaration().setInsuranceValueDeclarationNawb(
                  new BigDecimal(neutralAWBMaster.getChargeDeclaration().getInsuranceValueDeclaration()));
         } else {
            neutralAWBMaster.getChargeDeclaration().setInsuranceValueDeclarationNawb(null);
         }
      }

      int count = updateData("updateChargeDeclaration", neutralAWBMaster.getChargeDeclaration(), sqlSessionNeutralAWB);
      if (count == 0) {
         insertData("insertNeutralAWBChargeDeclaration", neutralAWBMaster.getChargeDeclaration(), sqlSessionNeutralAWB);
      }
      count = updateData("updateNeutralFWBChargeDeclaration", neutralAWBMaster.getChargeDeclaration(),
            sqlSessionNeutralAWB);
      if (count == 0) {
         insertData("insertNeutralFWBChargeDeclaration", neutralAWBMaster.getChargeDeclaration(), sqlSessionNeutralAWB);
      }

      return neutralAWBMaster;
   }

   /*
    * (non-Javadoc)
    * 
    * @see
    * com.ngen.cosys.shipment.nawb.dao.NeutralAWBDAO#saveNAWBCustomsDetails(com.
    * ngen.cosys.shipment.nawb.model.NeutralAWBMaster)
    */
   @Override
   public NeutralAWBMaster saveNAWBCustomsDetails(NeutralAWBMaster neutralAWBMaster) throws CustomException {
      // TODO: Delete all customs info and re-insert

      neutralAWBMaster.getOtherCustomsInfo().stream().forEach(n -> {
         n.setNeutralAWBId(neutralAWBMaster.getNeutralAWBId());
         n.setShipmentFreightWayBillId(neutralAWBMaster.getShipmentFreightWayBillId().longValue());
      });

      for (OtherCustomsInfo o : neutralAWBMaster.getOtherCustomsInfo()) {
         if (o.getFlagCRUD().equals("C") && o.getCountryCode() != null && o.getCountryCode() != "") {
            insertData(NeutralAWBQueryIds.INSERT_NAWB_OTHER_CUSTOMS_INFO.getQueryId(), o, sqlSessionNeutralAWB);
            insertData("insertNeutralFWBOtherCustomsInfo", o, sqlSessionNeutralAWB);
         } else if (o.getFlagCRUD().equals("U") && o.getCountryCode() != null && o.getCountryCode() != "") {
            updateData("updateOtherCustomsInfo", o, sqlSessionNeutralAWB);
            updateData("updateNeutralFWBOtherCustomsInfo", o, sqlSessionNeutralAWB);
         } else if (o.getFlagCRUD().equals("D")) {
            deleteData("deleteCustomInfo", o, sqlSessionNeutralAWB);
            deleteData("deleteNeutralFWBCustomInfo", o, sqlSessionNeutralAWB);
         }
      }

      return neutralAWBMaster;
   }

   @Override
   public NeutralAWBMaster saveAgentInfo(NeutralAWBMaster neutralAWBMaster) throws CustomException {
      int updateRecord = updateData("updateAgentInfo", neutralAWBMaster.getAgentInfo(), sqlSessionNeutralAWB);
      if (updateRecord == 0) {
         insertData(NeutralAWBQueryIds.INSERT_NAWB_AGENT_INFO.getQueryId(), neutralAWBMaster.getAgentInfo(),
               sqlSessionNeutralAWB);
      }
      updateRecord = updateData("updateNeutralFWBAgentInfo", neutralAWBMaster.getAgentInfo(), sqlSessionNeutralAWB);
      if (updateRecord == 0) {
         insertData("insertNeutralFWBAgentInfo", neutralAWBMaster.getAgentInfo(), sqlSessionNeutralAWB);
      }
      return neutralAWBMaster;
   }

   @Override
   public NeutralAWBMaster saveAwbFlight(NeutralAWBMaster neutralAWBMaster) throws CustomException {
      neutralAWBMaster.getFlightBookingList().stream().forEach(f -> {
         f.setNeutralAWBId(neutralAWBMaster.getNeutralAWBId());
         f.setShipmentFreightWayBillId(neutralAWBMaster.getShipmentFreightWayBillId());
      });

      for (FlightBooking i : neutralAWBMaster.getFlightBookingList()) {
         if (!i.getFlagCRUD().equals("R") && !i.getFlagCRUD().equals("C")) {
            deleteData("deleteFlightBooking", i, sqlSessionNeutralAWB);
            deleteData("deleteFWBFlightBooking", i, sqlSessionNeutralAWB);
         }
      }

      for (FlightBooking i : neutralAWBMaster.getFlightBookingList()) {
         if (!i.getFlagCRUD().equals("D") && !i.getFlagCRUD().equals("R") && i.getFlightDate() != null
               && i.getFlightNumber() != null && i.getCarrierCode() != null) {
            BigInteger id = fetchObject("fetchNeutralAWBFlightBookingId", i, sqlSessionNeutralAWB);
            if (ObjectUtils.isEmpty(id)) {
               insertData(NeutralAWBQueryIds.INSERT_NAWB_FLIGHT_BOOKING.getQueryId(), i, sqlSessionNeutralAWB);
            }
            id = fetchObject("fetchNeutralFWBFlightBookingId", i, sqlSessionNeutralAWB);
            if (ObjectUtils.isEmpty(id)) {
               insertData("insertNeutralFWBFlightBooking", i, sqlSessionNeutralAWB);
            }
         }
      }

      return neutralAWBMaster;
   }

   @Override
   public int checkAwbExist(SearchNAWBRQ s) throws CustomException {

      return fetchObject("selectNAWBcount", s, sqlSessionNeutralAWB);
   }

   @Override
   public int updateIssuedNumer(NeutralAWBMaster neutralAWBMaster) throws CustomException {
      int success = updateData("updateStatusInSidHeader", neutralAWBMaster, sqlSessionNeutralAWB);
      return success;
   }

   @Override
   public BigInteger fetchNawbID(NeutralAWBMaster neutralAWBMaster) throws CustomException {
      BigInteger nawbID = fetchObject("selectAwbNumber", neutralAWBMaster, sqlSessionNeutralAWB);
      return nawbID;
   }

   @Override
   public BigInteger customerInfoId(NeutralAWBMaster neutralAWBMaster) throws CustomException {
      BigInteger customerId = fetchObject("selectCustomerinfoId", neutralAWBMaster, sqlSessionNeutralAWB);
      return customerId;
   }

   // -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------
   @Override
	public NeutralAWBMaster saveneutralAWB(NeutralAWBMaster neutralAWBMaster) throws CustomException {
		BigInteger awbId = fetchNawbID(neutralAWBMaster);
		neutralAWBMaster.setNeutralAWBId(awbId);
		nawbMasterDetailsInsert(neutralAWBMaster);
		BigInteger fwbId = fetchObject("fetchNeutralFWBMaster", neutralAWBMaster, sqlSessionNeutralAWB);
		neutralAWBMaster.setShipmentFreightWayBillId(fwbId);
		int updatedRecordCount = updateData("updateNeutralFWBMaster", neutralAWBMaster, sqlSessionNeutralAWB);
		if (updatedRecordCount == 0) {
			insertData("insertNeutralAWBMasterToFWBDetails", neutralAWBMaster, sqlSessionNeutralAWB);
		}
		return neutralAWBMaster;

	}
   
   
	public void nawbMasterDetailsInsert(NeutralAWBMaster neutralAWBMaster) throws CustomException {
		if (Optional.ofNullable(neutralAWBMaster.getNeutralAWBId()).isPresent()) {
			updateNawbMasterDetails(neutralAWBMaster);
		} else {
			insertNawbMasterDetails(neutralAWBMaster);
		}
	}
	
	@NgenAuditAction(entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.NAWB_MANAGEMENT)
	public void insertNawbMasterDetails(NeutralAWBMaster neutralAWBMaster) throws CustomException {
		insertData(NeutralAWBQueryIds.INSERT_NAWB_MASTER_DETAILS.getQueryId(), neutralAWBMaster, sqlSessionNeutralAWB);

	}
	
	@NgenAuditAction(entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.NAWB_MANAGEMENT)
	public void updateNawbMasterDetails(NeutralAWBMaster neutralAWBMaster) throws CustomException {
		updateData("updateNeutralAWBMaster", neutralAWBMaster, sqlSessionNeutralAWB);
	}

@Override
   public NeutralAWBMaster saveNAWBAlsoNotifyInfoDetails(NeutralAWBMaster neutralAWBMaster) throws CustomException {
      neutralAWBMaster.getAlsoNotify().setCustomerType("NFY");
      int updatedRecordCount = updateData("updateAwbcustomerinfo", neutralAWBMaster.getAlsoNotify(),
            sqlSessionNeutralAWB);
      if (updatedRecordCount == 0) {
         insertData("sqlinsertNeutralAWBCustomerInfoDetails", neutralAWBMaster.getAlsoNotify(), sqlSessionNeutralAWB);
      }
      neutralAWBMaster.getAlsoNotify()
            .setShipmentFreightWayBillId(neutralAWBMaster.getShipmentFreightWayBillId().longValue());
      updatedRecordCount = updateData("updateNeutralFwbcustomerinfo", neutralAWBMaster.getAlsoNotify(),
            sqlSessionNeutralAWB);
      if (updatedRecordCount == 0) {
         insertData("sqlinsertNeutralFWBCustomerInfoDetails", neutralAWBMaster.getAlsoNotify(), sqlSessionNeutralAWB);
      } else {
         BigInteger id = fetchObject("fetchNeutralFWBCustomerInfoId", neutralAWBMaster.getAlsoNotify(),
               sqlSessionNeutralAWB);
         neutralAWBMaster.getAlsoNotify().setShipmentFreightWayBillCustomerInfoId(id.longValue());
      }
      return neutralAWBMaster;

   }

   @Override
   public NeutralAWBMaster saveNAWBAlsoNotifyAddressInfoDetails(NeutralAWBMaster neutralAWBMaster)
         throws CustomException {
      int updatedRecordCount = updateData("updateCustomeraddressinfo",
            neutralAWBMaster.getAlsoNotify().getCustomerAddressInfo(), sqlSessionNeutralAWB);
      if (updatedRecordCount == 0) {
         insertData(NeutralAWBQueryIds.INSERT_NAWB_CUSTOMER_ADDRESS_DETAILS.getQueryId(),
               neutralAWBMaster.getAlsoNotify().getCustomerAddressInfo(), sqlSessionNeutralAWB);
      }
      updatedRecordCount = updateData("updateNeutralFWBCustomeraddressinfo",
            neutralAWBMaster.getAlsoNotify().getCustomerAddressInfo(), sqlSessionNeutralAWB);
      if (updatedRecordCount == 0) {
         insertData("insertNeutralFWBCustomerAddressDetails", neutralAWBMaster.getAlsoNotify().getCustomerAddressInfo(),
               sqlSessionNeutralAWB);
      }
      return neutralAWBMaster;
   }

   @Override
   public NeutralAWBMaster saveNAWBAlsoNotifyContactInfoDetails(NeutralAWBMaster neutralAWBMaster)
         throws CustomException {
      for (CustomerContactInfo i : neutralAWBMaster.getAlsoNotify().getCustomerAddressInfo().getCustomerContactInfo()) {
         if (!i.getFlagCRUD().equals("R")) {
            deleteData("deleteCustomercontactinfo", i, sqlSessionNeutralAWB);
         }
      }

      for (CustomerContactInfo i : neutralAWBMaster.getAlsoNotify().getCustomerAddressInfo().getCustomerContactInfo()) {
         if (!i.getFlagCRUD().equals("D") && !i.getFlagCRUD().equals("R") && i.getContactIdentifier() != null
               && i.getContactDetail() != null) {
            insertData(NeutralAWBQueryIds.INSERT_NAWB_CUSTOMER_CONTACT_INFO_DETAILS.getQueryId(), i,
                  sqlSessionNeutralAWB);
         }
      }
      return neutralAWBMaster;
   }

   @Override
   public NeutralAWBMaster saveRateDescription(NeutralAWBMaster neutralAWBMaster) throws CustomException {
      for (RateDescription rateDescriptionForNawb : neutralAWBMaster.getRateDescriptionForNawb()) {
         rateDescriptionForNawb.setNeutralAWBId(neutralAWBMaster.getNeutralAWBId());
         rateDescriptionForNawb.setShipmentFreightWayBillId(neutralAWBMaster.getShipmentFreightWayBillId().longValue());
         if (rateDescriptionForNawb.getFlagCRUD().equals("C")) {
            BigInteger id = fetchObject("fetchNeutralAWBRateDescriptionId", rateDescriptionForNawb,
                  sqlSessionNeutralAWB);
            if (id == null || ObjectUtils.isEmpty(id)) {
               if (StringUtils.isEmpty(rateDescriptionForNawb.getCommodityItemNo())) {
                  rateDescriptionForNawb.setCommodityItemNo("0");
               }
               insertData("insertNeutralAWBRateDescription", rateDescriptionForNawb, sqlSessionNeutralAWB);
            } else {
               rateDescriptionForNawb.setNeutralAWBRateDescriptionId(id);
            }
            id = fetchObject("fetchNeutralFWBRateDescriptionId", rateDescriptionForNawb, sqlSessionNeutralAWB);
            if (ObjectUtils.isEmpty(id)) {
               insertData("insertNeutralFWBRateDescription", rateDescriptionForNawb, sqlSessionNeutralAWB);
            } else {
               rateDescriptionForNawb.setShipmentFreightWayBillRateDescriptionId(id.longValue());
            }
         }
         if (rateDescriptionForNawb.getFlagCRUD().equals("U")) {
            updateData("updateRateDescription", rateDescriptionForNawb, sqlSessionNeutralAWB);
            updateData("updateNeutralFWBRateDescription", rateDescriptionForNawb, sqlSessionNeutralAWB);
         }
         if (rateDescriptionForNawb.getFlagCRUD().equals("D")) {
            rateDescriptionForNawb.setNeutralAWBId(neutralAWBMaster.getNeutralAWBId());
            rateDescriptionForNawb
                  .setShipmentFreightWayBillId(neutralAWBMaster.getShipmentFreightWayBillId().longValue());

            for (RateDescOtherInfo otherInfo : rateDescriptionForNawb.getRateDescriptionOtherInfoForNawb()) {
               otherInfo.setShipmentFreightWayBillRateDescriptionId(
                     rateDescriptionForNawb.getShipmentFreightWayBillRateDescriptionId());
               ;
               deleteData("deleteNeutralFWBRateDescOtherInfoALl", otherInfo, sqlSessionNeutralAWB);
               deleteData("deleteRateDescOtherInfo", otherInfo, sqlSessionNeutralAWB);
            }
            deleteData("deleteRateDesc", rateDescriptionForNawb, sqlSessionNeutralAWB);
            deleteData("deleteNeutralFWBRateDesc", rateDescriptionForNawb, sqlSessionNeutralAWB);

         }
      }
      return neutralAWBMaster;
   }

   @Override
   public NeutralAWBMaster saveRateDescriptionInfo(NeutralAWBMaster neutralAWBMaster) throws CustomException {
      for (RateDescription rateDescriptionForNawb : neutralAWBMaster.getRateDescriptionForNawb()) {
         rateDescriptionForNawb.getRateDescriptionOtherInfoForNawb().stream().forEach(r -> {
            r.setNeutralAWBRateDescriptionId(rateDescriptionForNawb.getNeutralAWBRateDescriptionId());
            r.setShipmentFreightWayBillRateDescriptionId(
                  rateDescriptionForNawb.getShipmentFreightWayBillRateDescriptionId());
         });
         for (RateDescOtherInfo r : rateDescriptionForNawb.getRateDescriptionOtherInfoForNawb()) {
            if (r.getFlagCRUD().equals("C")) {
               if (r.getCountryCode() != null && r.getCountryCode().equals("")) {
                  r.setCountryCode(null);
               }
               insertData("insertNeutralAWBRateDescOtherInfo", r, sqlSessionNeutralAWB);
               insertData("insertNeutralFWBRateDescOtherInfo", r, sqlSessionNeutralAWB);
            } else if (r.getFlagCRUD().equals("U")) {
               updateData("updateRateDescOtherInfo", r, sqlSessionNeutralAWB);
               updateData("updateNeutralFWBRateDescOtherInfo", r, sqlSessionNeutralAWB);
            } else if (r.getFlagCRUD().equals("D")) {
               deleteData("deleteRateDescOtherInfo", r, sqlSessionNeutralAWB);
               deleteData("deleteNeutralFWBRateDescOtherInfo", r, sqlSessionNeutralAWB);
            }

         }
      }
      return neutralAWBMaster;
   }

   @Override
   public NeutralAWBMaster saveHandlingInformation(NeutralAWBMaster neutralAWBMaster) throws CustomException {
      deleteData("deleteSSROSIInfo", neutralAWBMaster, sqlSessionNeutralAWB);
      deleteData("deleteNeutralFWBSSROSIInfo", neutralAWBMaster, sqlSessionNeutralAWB);
      neutralAWBMaster.getSsrOsiInfo().stream().forEach(s -> s.setNeutralAWBId(neutralAWBMaster.getNeutralAWBId()));
      for (SSROSIInfo s : neutralAWBMaster.getSsrOsiInfo()) {
         if (s.getServiceRequestcontent() != null && s.getServiceRequestcontent() != "") {
            s.setShipmentFreightWayBillId(neutralAWBMaster.getShipmentFreightWayBillId().longValue());
            insertData("insertNeutralAWBSSROSIInfo", s, sqlSessionNeutralAWB);
            insertData("insertNeutralFWBSSROSIInfo", s, sqlSessionNeutralAWB);
         }

      }

      return neutralAWBMaster;
   }

   @Override
   public NeutralAWBMaster localAuthorization(NeutralAWBMaster neutralAWBMaster) throws CustomException {
      neutralAWBMaster.getNeutralAwbCustoms().setNeutralAWBId(neutralAWBMaster.getNeutralAWBId());
      if (neutralAWBMaster.getNeutralAwbCustoms().getFlagCRUD().equals("C")) {
         insertData("insertLocalAuthorization", neutralAWBMaster.getNeutralAwbCustoms(), sqlSessionNeutralAWB);
      }
      if (neutralAWBMaster.getNeutralAwbCustoms().getFlagCRUD().equals("U")) {
         deleteData("deleteAllAWBLocalAuthDetailsByNeutrakAWBId", neutralAWBMaster.getNeutralAwbCustoms(), sqlSessionNeutralAWB);
         deleteData("deleteLocalAuthDetails", neutralAWBMaster.getNeutralAwbCustoms(), sqlSessionNeutralAWB);
         insertData("insertLocalAuthorization", neutralAWBMaster.getNeutralAwbCustoms(), sqlSessionNeutralAWB);
         neutralAWBMaster.getNeutralAwbCustoms().getNeutralAWBLocalAuthDetails().stream().forEach(l -> {
            if (!l.getFlagCRUD().equals("D"))
               l.setFlagCRUD("C");
         });
      }

      return neutralAWBMaster;
   }

   @Override
   public NeutralAWBMaster localAuthorizationDetails(NeutralAWBMaster neutralAWBMaster) throws CustomException {
      for (NeutralAWBLocalAuthDetails l : neutralAWBMaster.getNeutralAwbCustoms().getNeutralAWBLocalAuthDetails()) {
         if (l.getFlagCRUD().equals("C") && l.getNeutralAWBLocalAuthorityInfoId() != null) {
            insertData("insertAWBLocalAuthDetail", l, sqlSessionNeutralAWB);
         }
         if (l.getFlagCRUD().equals("U") && l.getNeutralAWBLocalAuthorityInfoId() != null) {
            updateData("updateAWBLocalAuthDetails", l, sqlSessionNeutralAWB);
         }
         if (l.getFlagCRUD().equals("D") && l.getNeutralAWBLocalAuthorityInfoId() != null) {
            deleteData("deleteAWBLocalAuthDetails", l, sqlSessionNeutralAWB);
         }
      }
      return neutralAWBMaster;
   }

   @Override
   public NeutralAWBMaster saveOtherChargesDueAgent(NeutralAWBMaster neutralAWBMaster) throws CustomException {
      neutralAWBMaster.getOtherChargesDueAgent().stream().forEach(o -> {
         o.setNeutralAWBId(neutralAWBMaster.getNeutralAWBId());
         o.setShipmentFreightWayBillId(neutralAWBMaster.getShipmentFreightWayBillId().longValue());
         o.setChargeType("AGT");
      });
      for (OtherCharges o : neutralAWBMaster.getOtherChargesDueAgent()) {
         if (!o.getFlagCRUD().equals("R")) {
            deleteData("deleteOtherCharges", o, sqlSessionNeutralAWB);
            deleteData("deleteNeutralFWBOtherCharges", o, sqlSessionNeutralAWB);
         }
      }
      for (OtherCharges o : neutralAWBMaster.getOtherChargesDueAgent()) {
         if (o.getFlagCRUD().equalsIgnoreCase("C") || o.getFlagCRUD().equalsIgnoreCase("U")) {
            if (o.getChargeAmount() != null && o.getOtherChargeCode() != null && o.getOtherChargeIndicator() != null) {
               insertData("insertNeutralAWBOtherCharges", o, sqlSessionNeutralAWB);
               insertData("insertNeutralFWBOtherCharges", o, sqlSessionNeutralAWB);
            }
         }
      }
      return neutralAWBMaster;
   }

   @Override
   public NeutralAWBMaster saveOtherChargesDueCarrier(NeutralAWBMaster neutralAWBMaster) throws CustomException {
      neutralAWBMaster.getOtherChargesDueCarrier().stream().forEach(o -> {
         o.setNeutralAWBId(neutralAWBMaster.getNeutralAWBId());
         o.setShipmentFreightWayBillId(neutralAWBMaster.getShipmentFreightWayBillId().longValue());
         o.setChargeType("CAR");
      });
      for (OtherCharges o : neutralAWBMaster.getOtherChargesDueCarrier()) {
         if (!o.getFlagCRUD().equals("R")) {
            deleteData("deleteOtherCharges", o, sqlSessionNeutralAWB);
            deleteData("deleteNeutralFWBOtherCharges", o, sqlSessionNeutralAWB);
         }
      }
      for (OtherCharges o : neutralAWBMaster.getOtherChargesDueCarrier()) {
         if (o.getFlagCRUD().equalsIgnoreCase("C") || o.getFlagCRUD().equalsIgnoreCase("U")) {
            if (o.getChargeAmount() != null && o.getOtherChargeCode() != null && o.getOtherChargeIndicator() != null) {
               insertData("insertNeutralAWBOtherCharges", o, sqlSessionNeutralAWB);
               insertData("insertNeutralFWBOtherCharges", o, sqlSessionNeutralAWB);
            }
         }
      }
      return neutralAWBMaster;
   }

   @Override
   public NeutralAWBMaster savePPdDetails(NeutralAWBMaster neutralAWBMaster) throws CustomException {
      neutralAWBMaster.getPpd().setNeutralAWBId(neutralAWBMaster.getNeutralAWBId());
      neutralAWBMaster.getPpd().setShipmentFreightWayBillId(neutralAWBMaster.getShipmentFreightWayBillId().longValue());
      neutralAWBMaster.getPpd().setOtherChargeIndicator("P");

      if (neutralAWBMaster.getPpd().getChargeSummaryTotalChargeAmount() != null
            && neutralAWBMaster.getPpd().getChargeSummaryTotalChargeAmount().compareTo(BigDecimal.valueOf(0.00)) > 0)
         neutralAWBMaster.getPpd().setChargeSummaryTotalChargeIdentifier("P");

      if (neutralAWBMaster.getPpd().getTaxesChargeAmount() != null
            && neutralAWBMaster.getPpd().getTaxesChargeAmount().compareTo(BigDecimal.valueOf(0.00)) > 0)
         neutralAWBMaster.getPpd().setTaxesChargeIdentifier("P");

      if (neutralAWBMaster.getPpd().getTotalWeightChargeAmount() != null
            && neutralAWBMaster.getPpd().getTotalWeightChargeAmount().compareTo(BigDecimal.valueOf(0.00)) > 0)
         neutralAWBMaster.getPpd().setTotalWeightChargeIdentifier("P");

      if (neutralAWBMaster.getPpd().getTotalOtherChargesDueCarrierChargeAmount() != null && neutralAWBMaster.getPpd()
            .getTotalOtherChargesDueCarrierChargeAmount().compareTo(BigDecimal.valueOf(0.00)) > 0)
         neutralAWBMaster.getPpd().setTotalOtherChargesDueCarrierChargeIdentifier("P");

      if (neutralAWBMaster.getPpd().getTotalOtherChargesDueAgentChargeAmount() != null && neutralAWBMaster.getPpd()
            .getTotalOtherChargesDueAgentChargeAmount().compareTo(BigDecimal.valueOf(0.00)) > 0)
         neutralAWBMaster.getPpd().setTotalOtherChargesDueAgentChargeIdentifier("P");

      int count = updateData("updatePrepaidCollectChargeSummary", neutralAWBMaster.getPpd(), sqlSessionNeutralAWB);
      if (count == 0) {
         insertData("insertNeutralAWBPrepaidCollectChargeSummary", neutralAWBMaster.getPpd(), sqlSessionNeutralAWB);
      }

      count = updateData("updateNeutralFWBPrepaidCollectChargeSummary", neutralAWBMaster.getPpd(),
            sqlSessionNeutralAWB);
      if (count == 0) {
         insertData("insertNeutralFWBPrepaidCollectChargeSummary", neutralAWBMaster.getPpd(), sqlSessionNeutralAWB);
      }
      return neutralAWBMaster;
   }

   @Override
   public NeutralAWBMaster saveColDetails(NeutralAWBMaster neutralAWBMaster) throws CustomException {
      neutralAWBMaster.getCol().setNeutralAWBId(neutralAWBMaster.getNeutralAWBId());
      neutralAWBMaster.getCol().setShipmentFreightWayBillId(neutralAWBMaster.getShipmentFreightWayBillId().longValue());
      neutralAWBMaster.getCol().setOtherChargeIndicator("C");

      if (neutralAWBMaster.getCol().getChargeSummaryTotalChargeAmount() != null
            && neutralAWBMaster.getCol().getChargeSummaryTotalChargeAmount().compareTo(BigDecimal.valueOf(0.00)) > 0)
         neutralAWBMaster.getCol().setChargeSummaryTotalChargeIdentifier("C");

      if (neutralAWBMaster.getCol().getTaxesChargeAmount() != null
            && neutralAWBMaster.getCol().getTaxesChargeAmount().compareTo(BigDecimal.valueOf(0.00)) > 0)
         neutralAWBMaster.getCol().setTaxesChargeIdentifier("C");

      if (neutralAWBMaster.getCol().getTotalOtherChargesDueAgentChargeAmount() != null && neutralAWBMaster.getCol()
            .getTotalOtherChargesDueAgentChargeAmount().compareTo(BigDecimal.valueOf(0.00)) > 0)
         neutralAWBMaster.getCol().setTotalOtherChargesDueAgentChargeIdentifier("C");

      if (neutralAWBMaster.getCol().getTotalOtherChargesDueCarrierChargeAmount() != null && neutralAWBMaster.getCol()
            .getTotalOtherChargesDueCarrierChargeAmount().compareTo(BigDecimal.valueOf(0.00)) > 0)
         neutralAWBMaster.getCol().setTotalOtherChargesDueCarrierChargeIdentifier("C");

      if (neutralAWBMaster.getCol().getTotalWeightChargeAmount() != null
            && neutralAWBMaster.getCol().getTotalWeightChargeAmount().compareTo(BigDecimal.valueOf(0.00)) > 0)
         neutralAWBMaster.getCol().setTotalWeightChargeIdentifier("C");

      int count = updateData("updatePrepaidCollectChargeSummary", neutralAWBMaster.getCol(), sqlSessionNeutralAWB);
      if (count == 0) {
         insertData("insertNeutralAWBPrepaidCollectChargeSummary", neutralAWBMaster.getCol(), sqlSessionNeutralAWB);
      }
      count = updateData("updateNeutralFWBPrepaidCollectChargeSummary", neutralAWBMaster.getPpd(),
            sqlSessionNeutralAWB);
      if (count == 0) {
         insertData("insertNeutralFWBPrepaidCollectChargeSummary", neutralAWBMaster.getPpd(), sqlSessionNeutralAWB);
      }
      return neutralAWBMaster;
   }

   @Override
   public NeutralAWBMaster searchAWB(NeutralAWBMaster neutralAWBMaster) throws CustomException {
      return fetchObject("fetchAWBForSID", neutralAWBMaster, sqlSessionNeutralAWB);
   }

   @Override
   public AgentInfo fetchAgentInfo(AgentInfo agent) throws CustomException {
      return fetchObject(NeutralAWBQueryIds.FETCH_AGENT_DETAILS.getQueryId(), agent, sqlSessionNeutralAWB);
   }

   @Override
   public String getAirlineName(String prefix) throws CustomException {
      String airlineName = fetchObject("getAirlineName", prefix, sqlSessionNeutralAWB);
      if (!ObjectUtils.isEmpty(airlineName)) {
         return airlineName;
      } else {
         return null;
      }
   }

   @Override
   public SearchStockRQ updateInProcessForAwbNumber(SearchStockRQ searchStockRQ) throws CustomException {
      BigInteger inProcess = fetchObject("checkInProcess", searchStockRQ, sqlSessionNeutralAWB);
      if (ObjectUtils.isEmpty(inProcess) || inProcess.equals(BigInteger.valueOf(0))) {
         updateData("updateInProcessForSelectedAwbNumber", searchStockRQ, sqlSessionNeutralAWB);
      } else {
         throw new CustomException("Nawb.Inprcess", null, ErrorType.ERROR);
      }

      return searchStockRQ;
   }

   @Override
   public boolean checkIfImportShipment(SearchNAWBRQ searchSIDRQ) throws CustomException {
      return fetchObject("sqlQueryCHeckIfImportShipmentNAWB", searchSIDRQ, sqlSessionNeutralAWB);
   }

   @Override
   public Boolean checkMalOcsForNawb(NeutralAWBMaster nawbShipment) throws CustomException {
      return this.fetchObject("checkmalocsfornawb", nawbShipment, sqlSessionNeutralAWB);
   }

   @Override
   public NawbChargeValues getNawbChargeDetails(NeutralAWBMaster chargevalues) throws CustomException {
      List<NawbChargeValues> nawbChargeValuesList = this.fetchList("getNawbChargeDetails", chargevalues,
            sqlSessionNeutralAWB);
      if (!CollectionUtils.isEmpty(nawbChargeValuesList)) {
         return nawbChargeValuesList.get(0);
      }

      return null;
   }

}