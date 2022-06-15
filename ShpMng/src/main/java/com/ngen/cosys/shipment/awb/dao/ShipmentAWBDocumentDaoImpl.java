/**
 * This is a repository implementation class for Shipment Document on
 * AWB/CBV/UCB
 */
package com.ngen.cosys.shipment.awb.dao;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.ngen.cosys.audit.NgenAuditAction;
import com.ngen.cosys.audit.NgenAuditEntityType;
import com.ngen.cosys.audit.NgenAuditEventType;
import com.ngen.cosys.billing.api.Charge;
import com.ngen.cosys.billing.api.enums.ChargeEvents;
import com.ngen.cosys.billing.api.enums.ReferenceType;
import com.ngen.cosys.billing.api.model.ChargeableEntity;
import com.ngen.cosys.customs.api.enums.CustomsShipmentType;
import com.ngen.cosys.customs.api.model.CustomsShipmentInfo;
import com.ngen.cosys.customs.api.model.CustomsShipmentLocalAuthorityInfo;
import com.ngen.cosys.damage.enums.FlagCRUD;
import com.ngen.cosys.export.commonbooking.model.CommonBooking;
import com.ngen.cosys.export.commonbooking.service.CommonBookingService;
import com.ngen.cosys.framework.constant.ErrorType;
import com.ngen.cosys.framework.dao.BaseDAO;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.helper.DomesticInternationalHelper;
import com.ngen.cosys.helper.HAWBHandlingHelper;
import com.ngen.cosys.helper.model.DomesticInternationalHelperRequest;
import com.ngen.cosys.helper.model.HAWBHandlingHelperRequest;
import com.ngen.cosys.multitenancy.feature.support.FeatureUtility;
import com.ngen.cosys.multitenancy.utilities.MultiTenantUtility;
import com.ngen.cosys.routing.RoutingRequestModel;
import com.ngen.cosys.shipment.awb.model.AWB;
import com.ngen.cosys.shipment.awb.model.AwbModelForFWB;
import com.ngen.cosys.shipment.awb.model.OverseasConsignee;
import com.ngen.cosys.shipment.awb.model.ShipmentMasterCustomerAddressInfo;
import com.ngen.cosys.shipment.awb.model.ShipmentMasterCustomerContactInfo;
import com.ngen.cosys.shipment.awb.model.ShipmentMasterCustomerInfo;
import com.ngen.cosys.shipment.awb.model.ShipmentMasterHandlingArea;
import com.ngen.cosys.shipment.awb.model.ShipmentMasterLocalAuthorityDetails;
import com.ngen.cosys.shipment.awb.model.ShipmentMasterLocalAuthorityInfo;
import com.ngen.cosys.shipment.awb.model.ShipmentMasterRoutingInfo;
import com.ngen.cosys.shipment.awb.model.ShipmentMasterShc;
import com.ngen.cosys.shipment.awb.model.ShipmentMasterShcHandlingGroup;
import com.ngen.cosys.shipment.awb.model.ShipmentOtherChargeInfo;
import com.ngen.cosys.shipment.awb.model.ShipmentRemarksModel;
import com.ngen.cosys.shipment.dao.ShipmentOnHoldDAO;
import com.ngen.cosys.shipment.enums.AWBDocumentQueryId;
import com.ngen.cosys.shipment.model.IrregularityDetail;

@Repository
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
public class ShipmentAWBDocumentDaoImpl extends BaseDAO implements ShipmentAWBDocumentDao {

   @Autowired
   @Qualifier("sqlSessionTemplate")
   private SqlSessionTemplate sqlSessionTemplate;

   @Autowired
   private ShipmentOnHoldDAO shipmentOnHoldDAO;
   
   @Autowired
   private CommonBookingService commonBookingService;
   
   private static final String HAWBHandling = "HAWB.Handling";
   
   @Autowired
   private HAWBHandlingHelper hawbHandlingHelper;
   
   @Autowired
   private DomesticInternationalHelper domesticInternationalHelper;

   @Override
   public AWB getShipment(AWB awbDetails) throws CustomException {
	  //Checking Document info for the shipment with the flight
	
	  AWB awbResponse=this.fetchObject(AWBDocumentQueryId.SQL_GET_AWBDOCUMENT_INFO.toString(), awbDetails, sqlSessionTemplate);
   
	  Boolean originalFlag=this.fetchObject(AWBDocumentQueryId.SQL_GET_VERIFICATION_INFO.toString(), awbDetails, sqlSessionTemplate);
	  awbDetails.setOriginal(originalFlag);
	 //setting the downstream Flags
	  AWB downstreamFlags=this.fetchObject(AWBDocumentQueryId.SQL_GET_DOWNSTREAM_FLAGS.toString(), awbDetails, sqlSessionTemplate);
	  if(!ObjectUtils.isEmpty(downstreamFlags)) {  
		  awbResponse.setShipmentInventoryFlag(downstreamFlags.getShipmentInventoryFlag());
		  awbResponse.setShipmentIrregularityFlag(downstreamFlags.getShipmentIrregularityFlag());
		  awbResponse.setShipmentFreightOutFlag(downstreamFlags.getShipmentFreightOutFlag());
	  }
	  return awbResponse;
   }

   @Override
   public AWB getAcceptanceInfo(AWB awbDetails) throws CustomException {
      return this.fetchObject(AWBDocumentQueryId.SQL_GET_ACCEPTANCE_INFO.toString(), awbDetails, sqlSessionTemplate);
   }

   @Override
   public AWB getFwbInfo(AWB awbDetails) throws CustomException {
      return this.fetchObject(AWBDocumentQueryId.SQL_GET_FWB_INFO.toString(), awbDetails, sqlSessionTemplate);
   }

   @Override
   public AWB getShipmentInfoFromImportECC(AWB awbDetails) throws CustomException {
      AWB awb = this.fetchObject(AWBDocumentQueryId.SQL_GET_SHIPMENT_INFO_FROM_ECC.toString(), awbDetails,
            sqlSessionTemplate);

      // Check if not empty then populate SHC
      if (!ObjectUtils.isEmpty(awb)) {
         List<ShipmentMasterShc> shcs = this.fetchList(AWBDocumentQueryId.SQL_GET_SHIPMENT_INFO_FROM_ECC_SHC.toString(),
               awbDetails, sqlSessionTemplate);
         awb.setShcs(shcs);
      }

      return awb;
   }

   @Override
   public AWB getShipmentInfoFromBooking(AWB awbDetails) throws CustomException {
      return this.fetchObject(AWBDocumentQueryId.SQL_GET_SHIPMENT_INFO_BOOKING.toString(), awbDetails,
            sqlSessionTemplate);
   }

   @Override
   public Boolean checkShipmentExists(AWB awbDetails) throws CustomException {
      return this.fetchObject(AWBDocumentQueryId.SQL_CHECK_SHIPMENTMASTER_EXISTS.toString(), awbDetails,
            sqlSessionTemplate);
   }

   @Override
   public Boolean isPartShipment(AWB awbDetails) throws CustomException {
      BigInteger isPart = this.fetchObject(AWBDocumentQueryId.SQL_CHECK_FOR_PART_SHIPMENT.toString(), awbDetails,
            sqlSessionTemplate);
      Optional<BigInteger> part = Optional.ofNullable(isPart);
      if (part.isPresent() && part.get().intValue() > 0) {
         return Boolean.TRUE;
      } else {
         return Boolean.FALSE;
      }

   }

   @Override
   public Boolean isSVCShipment(AWB awbDetails) throws CustomException {
      return this.fetchObject(AWBDocumentQueryId.SQL_CHECK_FOR_SVC_SHIPMENT.toString(), awbDetails, sqlSessionTemplate);
   }

   @Override
   @NgenAuditAction(entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.AWB_DOCUMENT)
   public void createShipment(AWB awbDetails) throws CustomException {
      awbDetails.setShipmentNumberForAuditTrail(awbDetails.getShipmentNumber());
      BigInteger shipmentId = super.fetchObject(AWBDocumentQueryId.SQL_GET_SHIPMENTMASTERID.toString(), awbDetails,
            sqlSessionTemplate);
      if (ObjectUtils.isEmpty(shipmentId)) {
    	  if(awbDetails!=null && awbDetails.getOrigin()!=null && awbDetails.getDestination()!=null) {
    	    DomesticInternationalHelperRequest request = new DomesticInternationalHelperRequest();
			request.setOrigin(awbDetails.getOrigin());
			request.setDestination(awbDetails.getDestination());
			awbDetails.setHandledByDOMINT(domesticInternationalHelper.getDOMINTHandling(request));
    	  }
         this.insertData(AWBDocumentQueryId.SQL_CREATE_SHIPMENTMASTER.toString(), awbDetails, sqlSessionTemplate);
      } else {
         // if shipment changed transhipment to local updating ChangeToTranshipmentOn for Billing
         String destination = super.fetchObject(AWBDocumentQueryId.SQL_GET_SHIPMENTMASTER_DESTINATION.toString(),
               shipmentId, sqlSessionTemplate);

         boolean importFlag = super.fetchObject(AWBDocumentQueryId.SQL_CHECK_IMPORT_SHIPMENT.toString(), shipmentId,
               sqlSessionTemplate);

         if (MultiTenantUtility.isTranshipment(awbDetails.getOrigin(), awbDetails.getDestination())
               && importFlag) {
            ChargeableEntity chargeableEntity = new ChargeableEntity();
            chargeableEntity.setReferenceId(awbDetails.getShipmentId());
            chargeableEntity.setProcessType(ChargeEvents.PROCESS_IMP.getEnum());
            chargeableEntity.setReferenceType(ReferenceType.SHIPMENT.getReferenceType());
            chargeableEntity.setHandlingTerminal(awbDetails.getTerminal());
            chargeableEntity.setUserCode(awbDetails.getLoggedInUser());
            chargeableEntity.setCarrierCode(awbDetails.getCarrierCode());
            Charge.cancelCharge(chargeableEntity);
         }

         if (!destination.equalsIgnoreCase(awbDetails.getDestination())
               && MultiTenantUtility.isTenantCityOrAirport(awbDetails.getDestination())) {
            awbDetails.setChangeToTranshipmentOn(awbDetails.getCreatedOn());
         }
         if (!destination.equalsIgnoreCase(awbDetails.getDestination())){
        	 this.deleteCustomsData(awbDetails);
         }
         // Set the shipment id
         awbDetails.setShipmentId(shipmentId);
         this.updateData(AWBDocumentQueryId.SQL_UPDATE_SHIPMENTMASTER.toString(), awbDetails, sqlSessionTemplate);
         if (awbDetails.getOldPieces() != null && awbDetails.getPieces().compareTo(awbDetails.getOldPieces()) != 0) {
            this.updateData("sqlNullifyDeliveredOnInShipmentMaster", awbDetails, sqlSessionTemplate);
         }
      }
   }
   @NgenAuditAction(entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.AWB_DOCUMENT)
   public void updateShipmentType(AWB awbData) throws CustomException {
	  awbData.setShipmentNumberForAuditTrail(awbData.getShipmentNumber());
      this.updateData(AWBDocumentQueryId.SQL_UPDATE_SHIPMENTTYPE.toString(), awbData, sqlSessionTemplate);

   }

   public void deleteCustomsData(AWB shipmentData) throws CustomException {
      CustomsShipmentInfo customsShipmentInfo = new CustomsShipmentInfo();
      customsShipmentInfo.setShipmentNumber(shipmentData.getShipmentNumber());
      if (MultiTenantUtility.isTenantCityOrAirport(shipmentData.getOrigin())) {
         customsShipmentInfo.setShipmentType(CustomsShipmentType.Type.EXPORT);
      } else {
         customsShipmentInfo.setShipmentType(CustomsShipmentType.Type.IMPORT);

      }
      customsShipmentInfo
            .setReferenceId(this.fetchObject("sqlGetEarliestFlightToAttach", customsShipmentInfo, sqlSessionTemplate));
      List<CustomsShipmentInfo> shipmentIds = this.fetchList("sqlGetSyncCustomsShipmentInfoId", customsShipmentInfo,
            sqlSessionTemplate);
      if (!CollectionUtils.isEmpty(shipmentIds)) {
         for (CustomsShipmentInfo id : shipmentIds) {
            customsShipmentInfo.setId(id.getCustomShipmentInfoId());
            customsShipmentInfo.setFlightId(id.getFlightId());
            // 1. Delete LocalAuthorityDetails
            if (ObjectUtils.isEmpty(customsShipmentInfo.getLocalAuthorityInfo())) {
               CustomsShipmentLocalAuthorityInfo localAuthorityInfo = new CustomsShipmentLocalAuthorityInfo();
               localAuthorityInfo.setReferenceId(id.getCustomShipmentInfoId());
               customsShipmentInfo.setLocalAuthorityInfo(localAuthorityInfo);
            } else {
               customsShipmentInfo.getLocalAuthorityInfo().setReferenceId(id.getCustomShipmentInfoId());
            }

            super.deleteData("sqlDeleteSyncCustomsShimentLocalAuthorityDetails",
                  customsShipmentInfo.getLocalAuthorityInfo(), sqlSessionTemplate);

            // 2. Delete LocalAuthorityInfo
            super.deleteData("sqlDeleteSyncCustomsShimentLocalAuthorityInfo",
                  customsShipmentInfo.getLocalAuthorityInfo(), sqlSessionTemplate);

            // 3. Delete Customer Address Info
            if (!ObjectUtils.isEmpty(customsShipmentInfo.getCustomerInfo())) {
               customsShipmentInfo.getCustomerInfo().setReferenceId(id.getCustomShipmentInfoId());
               super.deleteData("sqlDeleteSyncCustomsShimentCustomerAddressInfo", customsShipmentInfo.getCustomerInfo(),
                     sqlSessionTemplate);

               // 4. Delete Customer Info
               super.deleteData("sqlDeleteSyncCustomsShimentCustomerInfo", customsShipmentInfo.getCustomerInfo(),
                     sqlSessionTemplate);
            } else {
               super.deleteData("sqlDeleteSyncCustomsShimentCustomerAddressInfoById", customsShipmentInfo,
                     sqlSessionTemplate);

               // 4. Delete Customer Info
               super.deleteData("sqlDeleteSyncCustomsShimentCustomerInfoById", customsShipmentInfo, sqlSessionTemplate);

            }

            // 5. Delete Customer Shipment Info
            super.deleteData("sqlDeleteSyncCustomsShipmentInfoOnNoInvOrFreightOutInfo", customsShipmentInfo,
                  sqlSessionTemplate);
         }
      }
   }

   // local Authority delete operation
   @Override
   public boolean deleteShipmentMasterLocalAuthorityDetails(
         ShipmentMasterLocalAuthorityInfo shipmentMasterLocalAuthorityInfo) throws CustomException {
      for (ShipmentMasterLocalAuthorityDetails details : shipmentMasterLocalAuthorityInfo.getDetails()) {
         deleteData(AWBDocumentQueryId.DELETE_SHIPMENT_MASTER_LOCAL_AUTHORITY_DETAILS.toString(), details,
               sqlSessionTemplate);
      }
      int success1 = deleteData(AWBDocumentQueryId.DELETE_SHIPMENT_MASTER_LOCAL_AUTHORITY_INFO.toString(),
            shipmentMasterLocalAuthorityInfo, sqlSessionTemplate);
      return (success1 != 0);
   }

   @Override
   public void createShipmentMasterCustomerAddressInfo(
         ShipmentMasterCustomerAddressInfo shipmentMasterCustomerAddressInfo) throws CustomException {
      Boolean b = this.fetchObject(AWBDocumentQueryId.SQL_CHECK_SHIPMENTMASTERCUSTOMERADDRESSINFO.toString(),
            shipmentMasterCustomerAddressInfo, sqlSessionTemplate);
      if (b) {
         this.updateData(AWBDocumentQueryId.SQL_UPDATE_SHIPMENTMASTERCUSTOMERADDRESSINFO.toString(),
               shipmentMasterCustomerAddressInfo, sqlSessionTemplate);
      } else {
         this.insertData(AWBDocumentQueryId.SQL_CREATE_SHIPMENTMASTERCUSTOMERADDRESSINFO.toString(),
               shipmentMasterCustomerAddressInfo, sqlSessionTemplate);
      }
   }

   @Override
   public void createShipmentMasterCustomerContactInfo(List<ShipmentMasterCustomerContactInfo> contactsList)
         throws CustomException {
      String b = this.fetchObject(AWBDocumentQueryId.SQL_CHECK_SHIPMENTMASTERCUSTOMERCONTACTINFO.toString(),
            contactsList.get(0), sqlSessionTemplate);

      deleteData(AWBDocumentQueryId.DELETE_AWB_CUST_CONTACT_INFO_DETAILS.toString(), b, sqlSessionTemplate);

      for (ShipmentMasterCustomerContactInfo contacts : contactsList) {
    	 contacts.setFlagCRUD(Optional.ofNullable(contacts.getFlagCRUD()).orElse("C"));
         if ((!StringUtils.isEmpty(contacts.getContactTypeCode()) && !StringUtils.isEmpty(contacts.getContactTypeDetail())) && (!StringUtils.isEmpty(b))
               && (!StringUtils.isEmpty(contacts.getFlagCRUD()) && !contacts.getFlagCRUD().equalsIgnoreCase("D"))) {
            contacts.setShipmentCustomerAddInfoId(new BigInteger(b));
            this.insertData(AWBDocumentQueryId.SQL_CREATE_SHIPMENTMASTERCUSTOMERCONTACTINFO.toString(), contacts,
                  sqlSessionTemplate);
         }else if((!StringUtils.isEmpty(contacts.getContactTypeCode()) && StringUtils.isEmpty(contacts.getContactTypeDetail())) ||
        		 (StringUtils.isEmpty(contacts.getContactTypeCode()) && !StringUtils.isEmpty(contacts.getContactTypeDetail()))){
        	 contacts.addError("awb.cne.contact.dtl.mandatory", "contactTypeDetail", ErrorType.ERROR);
             throw new CustomException(contacts.getMessageList());
         }
      }
   }

   @Override
   public void createIvrsCustomerContactInfo(List<ShipmentMasterCustomerContactInfo> contactsList)
         throws CustomException {
      for (ShipmentMasterCustomerContactInfo contacts : contactsList) {
         contacts.setFlagCRUD(Optional.ofNullable(contacts.getFlagCRUD()).orElse("C"));
         switch (contacts.getFlagCRUD()) {
         case FlagCRUD.Type.CREATE:
        	if ((!StringUtils.isEmpty(contacts.getContactTypeCode()) && !StringUtils.isEmpty(contacts.getContactTypeDetail()))) {
              this.insertData("sqlInsertIvrsCustomerContactInfo", contacts, sqlSessionTemplate);
        	}else if((!StringUtils.isEmpty(contacts.getContactTypeCode()) && StringUtils.isEmpty(contacts.getContactTypeDetail()))
        			||(StringUtils.isEmpty(contacts.getContactTypeCode()) && !StringUtils.isEmpty(contacts.getContactTypeDetail()))){
        	 contacts.addError("agent.ivrs.dtl.mandatory", "contactTypeDetail", ErrorType.ERROR);
        	 throw new CustomException(contacts.getMessageList());
            }
            break;
         case FlagCRUD.Type.UPDATE:
            if ((!StringUtils.isEmpty(contacts.getContactTypeCode()) && !StringUtils.isEmpty(contacts.getContactTypeDetail()))) {
              updateData("sqlUpdateIvrsCustomerContactInfo", contacts, sqlSessionTemplate);
        	}else if((!StringUtils.isEmpty(contacts.getContactTypeCode()) && StringUtils.isEmpty(contacts.getContactTypeDetail()))
        			||(StringUtils.isEmpty(contacts.getContactTypeCode()) && !StringUtils.isEmpty(contacts.getContactTypeDetail()))){
        	 contacts.addError("agent.ivrs.dtl.mandatory", "contactTypeDetail", ErrorType.ERROR);
        	 throw new CustomException(contacts.getMessageList());
            }
            break;
         case FlagCRUD.Type.DELETE:
            deleteData("sqlDeleteIvrsCustomerContactInfo", contacts, sqlSessionTemplate);
            break;
         default:
            break;
         }
      }
   }

   @Override
   public void createShipmentMasterCustomerInfo(ShipmentMasterCustomerInfo shipmentMasterCustomerInfo)
         throws CustomException {
      Integer b = this.fetchObject(AWBDocumentQueryId.SQL_CHECK_SHIPMENTMASTERCUSTOMERINFO.toString(),
            shipmentMasterCustomerInfo, sqlSessionTemplate);
      if (b != null) {
         shipmentMasterCustomerInfo.setId(BigInteger.valueOf(b));
         this.updateData(AWBDocumentQueryId.SQL_UPDATE_SHIPMENTMASTERCUSTOMERINFO.toString(),
               shipmentMasterCustomerInfo, sqlSessionTemplate);
      } else {
         this.insertData(AWBDocumentQueryId.SQL_CREATE_SHIPMENTMASTERCUSTOMERINFO.toString(),
               shipmentMasterCustomerInfo, sqlSessionTemplate);
      }
   }

   @Override
   public BigInteger getAppointedAgentId(ShipmentMasterCustomerInfo shipmentMasterCustomerInfo) throws CustomException {
      return super.fetchObject("sqlGetValidAppointedAgent", shipmentMasterCustomerInfo, sqlSessionTemplate);
   }

   @Override
   public void createShipmentMasterHandlingArea(ShipmentMasterHandlingArea shipmentMasterHandlingArea)
         throws CustomException {
      Boolean b = this.fetchObject(AWBDocumentQueryId.SQL_CHECK_SHIPMENTMASTERHANDLINGAREA.toString(),
            shipmentMasterHandlingArea, sqlSessionTemplate);
      if (!b) {
         this.insertData(AWBDocumentQueryId.SQL_CREATE_SHIPMENTMASTERHANDLINGAREA.toString(),
               shipmentMasterHandlingArea, sqlSessionTemplate);
      }
   }

   @Override
   public void createShipmentMasterRoutingInfo(List<ShipmentMasterRoutingInfo> routing) throws CustomException {
      // Delete the existing routing information
      deleteData(AWBDocumentQueryId.SQL_DELETE_SHIPMENTMASTERROUTINGINFO.toString(), routing, sqlSessionTemplate);

      for (ShipmentMasterRoutingInfo t : routing) {
         if (!StringUtils.isEmpty(t.getCarrier()) || !StringUtils.isEmpty(t.getFromPoint())) {
            // insert the new routing info
            this.insertData(AWBDocumentQueryId.SQL_CREATE_SHIPMENTMASTERROUTINGINFO.toString(), t, sqlSessionTemplate);
         }
      }

   }

   @Override
   public void createShipmentMasterShc(List<ShipmentMasterShc> shcs) throws CustomException {
      this.deleteData(AWBDocumentQueryId.DELETE_AWB_SHC_DETAILS.toString(), shcs, sqlSessionTemplate);
      for (ShipmentMasterShc shc : shcs) {
         if (Optional.ofNullable(shc.getSpecialHandlingCode()).isPresent()
               && (!shc.getSpecialHandlingCode().isEmpty())) {
            this.insertData(AWBDocumentQueryId.SQL_CREATE_SHIPMENTMASTERSHC.toString(), shc, sqlSessionTemplate);
         }
      }
   }

   @Override
   public void deleteShipmentMasterShc(AWB shipmentMaster) throws CustomException {
      this.deleteData(AWBDocumentQueryId.DELETE_AWB_SHC_DETAILS.toString(), shipmentMaster, sqlSessionTemplate);
   }

   @Override
   public void createShipmentMasterShcHandlingGroup(List<ShipmentMasterShcHandlingGroup> shcHandlingGroup)
         throws CustomException {
      Boolean b = this.fetchObject(AWBDocumentQueryId.SQL_CHECK_SHIPMENTMASTERSHCHANDLINGGROUP.toString(),
            shcHandlingGroup, sqlSessionTemplate);
      if (!b) {
         this.insertData(AWBDocumentQueryId.SQL_CREATE_SHIPMENTMASTERSHCHANDLINGGROUP.toString(), shcHandlingGroup,
               sqlSessionTemplate);
      }
   }

   @Override
   public void createShipmentOtherChargeInfo(ShipmentOtherChargeInfo shipmentOtherChargeInfo) throws CustomException {
      if (!ObjectUtils.isEmpty(shipmentOtherChargeInfo)) {
         Boolean b = this.fetchObject(AWBDocumentQueryId.SQL_CHECK_SHIPMENTOTHERCHARGEINFO.toString(),
               shipmentOtherChargeInfo, sqlSessionTemplate);
         if (b) {
            this.updateData(AWBDocumentQueryId.SQL_UPDATE_SHIPMENTOTHERCHARGEINFO.toString(), shipmentOtherChargeInfo,
                  sqlSessionTemplate);
         } else {
            this.insertData(AWBDocumentQueryId.SQL_CREATE_SHIPMENTOTHERCHARGEINFO.toString(), shipmentOtherChargeInfo,
                  sqlSessionTemplate);
         }
      }
   }


   @Override
   public void create(ShipmentRemarksModel shipmentRemarksModel) throws CustomException {
      // Check the record if updated based on remakrs id OR shipment otherwise insert
      int recordUpdatedCount = 0;
      if (shipmentRemarksModel.getShipmentRemarksId() != null) {
         recordUpdatedCount = this.updateData(AWBDocumentQueryId.UPDATE_AWB_SSROSI_DETAILS_BY_ID.toString(),
               shipmentRemarksModel, sqlSessionTemplate);
      } else {
         recordUpdatedCount = this.updateData(AWBDocumentQueryId.UPDATE_AWB_SSROSI_DETAILS_BY_SHIPMENT.toString(),
               shipmentRemarksModel, sqlSessionTemplate);
      }

      if (recordUpdatedCount == 0) {
         this.insertData(AWBDocumentQueryId.INSERT_SHIPMENT_REMARKS.toString(), shipmentRemarksModel,
               sqlSessionTemplate);
      }
   }
   
   @Override
   public List<BigInteger> getShipmentMasterLocalAuthorityInfo(ShipmentMasterLocalAuthorityInfo shipmentMasterLocalAuthorityInfo)
		   throws CustomException {
	   return fetchList(AWBDocumentQueryId.GET_SHIPMENT_MASTER_LOCAL_AUTHORITY_INFO.toString(),
			   shipmentMasterLocalAuthorityInfo, sqlSessionTemplate);
   }

   @Override
   public void insertShipmentMasterLocalAuthorityDetails(ShipmentMasterLocalAuthorityInfo localAuthority)
         throws CustomException {
      int seq = 1;
      if (!StringUtils.isEmpty(localAuthority.getType()) && (!localAuthority.getFlagCRUD().equalsIgnoreCase("D"))) {
			// only one Local authority info for Export and transhipment
			List<BigInteger> localAuthInfoIds = getShipmentMasterLocalAuthorityInfo(localAuthority);
			if (!CollectionUtils.isEmpty(localAuthInfoIds)) {
				for (BigInteger id : localAuthInfoIds) {
					localAuthority.setShipmentMasterLocalAuthInfoId(id.longValue());
					this.deleteData(AWBDocumentQueryId.DELETE_SHIPMENT_MASTER_LOCAL_AUTHORITY_DETAILS.toString(),
							localAuthority, sqlSessionTemplate);
					this.deleteData(AWBDocumentQueryId.DELETE_SHIPMENT_MASTER_LOCAL_AUTHORITY_INFO.toString(),
							localAuthority, sqlSessionTemplate);
				}
			}

			this.insertData(AWBDocumentQueryId.SQL_INSERT_SHIPMENT_DELIVERY_REQUEST_LOCAL_AUTHORITY.toString(),
					localAuthority, sqlSessionTemplate);

			// insery local authority info detail
			if (!CollectionUtils.isEmpty(localAuthority.getDetails())) {
				for (ShipmentMasterLocalAuthorityDetails localDetails : localAuthority.getDetails()) {
					localDetails.setTransactionSequenceNo(new BigInteger(seq + ""));
					seq++;
					localDetails.setShipmentMasterLocalAuthInfoId(localAuthority.getShipmentMasterLocalAuthInfoId());
					if (!StringUtils.isEmpty(localDetails.getReferenceNumber())) {
						this.insertData(AWBDocumentQueryId.SQL_INSERT_SHIPMENT_DELIVERY_REQUEST_LOCAL_AUTHORITY_DETAIL
								.toString(), localDetails, sqlSessionTemplate);
					}

				}
			}
		}
   }

   @Override
   public ShipmentRemarksModel get(ShipmentRemarksModel shipmentRemarksModel) throws CustomException {
      return this.fetchObject(AWBDocumentQueryId.GET_SHIPMENT_REMARKS.toString(), shipmentRemarksModel,
            sqlSessionTemplate);
   }

   @Override
   public void createOverseaseConsignee(AWB shipmentMaster) throws CustomException {
      if (!ObjectUtils.isEmpty(shipmentMaster.getConsignee())) {
         // INSTANTIATE OBJECT
         OverseasConsignee overseasConsignee = new OverseasConsignee();

         // SET THE VALUES
         ShipmentMasterCustomerInfo custOverseasConsigneeInfo = shipmentMaster.getConsignee();

         // set Overseas data
         overseasConsignee.setConsigneeName(shipmentMaster.getConsignee().getCustomerName());
         overseasConsignee.setDestinationCode(shipmentMaster.getDestination());
         overseasConsignee.setConsigneeState(custOverseasConsigneeInfo.getAddress().getStateCode());
         overseasConsignee.setConsigneeAddress(custOverseasConsigneeInfo.getAddress().getStreetAddress());
         overseasConsignee.setConsigneePostalCode(custOverseasConsigneeInfo.getAddress().getPostal());
         overseasConsignee.setCountryCode(custOverseasConsigneeInfo.getAddress().getCountryCode());
         overseasConsignee.setConsigneePlace(custOverseasConsigneeInfo.getAddress().getPlace());

         List<ShipmentMasterCustomerContactInfo> custOverseasConsigneeContactInfo = custOverseasConsigneeInfo
               .getAddress().getContacts();
         custOverseasConsigneeContactInfo.forEach(e1 -> {
            if (!Optional.ofNullable(e1).isPresent() && "TE".equalsIgnoreCase(e1.getContactTypeCode())) {
               overseasConsignee.setConsigneePhone(e1.getContactTypeDetail());
            }
         });

         int res = fetchObject("sqlGetCustomerInfoShipmentMaster", overseasConsignee, sqlSessionTemplate);
         if (res == 0) {
            // consignee overseas data
            insertData("insertOverseasConsigneeShipmentMaster", overseasConsignee, sqlSessionTemplate);
         } else {
            updateData("updateOverseasConsigneeShipmentMaster", overseasConsignee, sqlSessionTemplate);
         }
      }
   }

   @Override
   public void createShipmentVerification(AWB awbDetails) throws CustomException {
      Integer shpMasterVerificationUpdateCount = this.updateData(
            AWBDocumentQueryId.SQL_UPDATE_SHIPMENTMASTERVERFICATION.toString(), awbDetails, sqlSessionTemplate);
      if (shpMasterVerificationUpdateCount == 0) {
         this.insertData(AWBDocumentQueryId.SQL_CREATE_SHIPMENTMASTERVERFICATION.toString(), awbDetails,
               sqlSessionTemplate);
      }
   }

   @Override
   public BigDecimal fetchExchangeRate(AWB awbDetail) throws CustomException {
      BigDecimal exchangeRate = fetchObject(AWBDocumentQueryId.FETCH_EXCHANGE_RATE.toString(), awbDetail,
            sqlSessionTemplate);
      return (exchangeRate == null ? BigDecimal.ZERO : exchangeRate);
   }

   @Override
   public void createIrregulaity(AWB shipmentMaster) throws CustomException {
      int irregularityCount = fetchObject(AWBDocumentQueryId.GETIRREGULARITYCOUNT.toString(), shipmentMaster,
            sqlSessionTemplate);
      if (irregularityCount == 0) {
         IrregularityDetail irregularityDetail = new IrregularityDetail();
         irregularityDetail.setShipmentNumber(shipmentMaster.getShipmentNumber());
         irregularityDetail.setShipmentDate(shipmentMaster.getShipmentdate());
         irregularityDetail.setIrregularityType("OVCD");
         irregularityDetail.setPieces(shipmentMaster.getPieces());
         irregularityDetail.setWeight(shipmentMaster.getWeight());
         irregularityDetail.setFlightKey(shipmentMaster.getFlightId().toString());
         this.insertData(AWBDocumentQueryId.SQL_CREATE_IRREGULARITY.toString(), irregularityDetail, sqlSessionTemplate);
      }
   }

   @Override
   public void createTracing(AWB shipmentMaster) throws CustomException {
      int tracingCount = fetchObject(AWBDocumentQueryId.GETTRACINGCOUNT.toString(), shipmentMaster, sqlSessionTemplate);
      if (tracingCount == 0) {
         BigInteger lastCaseNumber = shipmentOnHoldDAO.getMaxCaseNumber();
         lastCaseNumber = lastCaseNumber.add(BigInteger.ONE);
         String caseNumber = "CT"
               + org.apache.commons.lang.StringUtils.leftPad(String.valueOf(lastCaseNumber.intValue()), 5, '0');
         shipmentMaster.setCaseNumber(caseNumber);
         this.insertData(AWBDocumentQueryId.SQL_CREATE_TRACING.toString(), shipmentMaster, sqlSessionTemplate);
      }
   }

   @Override
   public void updateShipmentDocumentReceivedOn(AWB shipmentMaster) throws CustomException {
      this.updateData(AWBDocumentQueryId.SQL_UPDATE_DOCUMENTRECEIVEDON.toString(), shipmentMaster, sqlSessionTemplate);
   }

   @Override
   public String getCarrierCodeBasedOnAwbPrefix(AWB awbDetails) throws CustomException {

      String carrierCode = null;

      // 1. Get the booking carrier code
      carrierCode = this.fetchObject("sqlGetShipmentCarrierFromBooking", awbDetails, sqlSessionTemplate);

      if (!StringUtils.isEmpty(carrierCode)) {
         // 2. Get the arrival manifest carrier code
         carrierCode = this.fetchObject("sqlGetShipmentCarrierFromArrivalManifest", awbDetails, sqlSessionTemplate);

         // 3. Get the default carrier code based on airline prefix
         if (!StringUtils.isEmpty(carrierCode)) {
            carrierCode = this.fetchObject(AWBDocumentQueryId.SQL_GET_CARRIERCODE_ON_PREFIX_AWB.toString(), awbDetails,
                  sqlSessionTemplate);
         }
      }

      return carrierCode;
   }

   @Override
   public void deleteAwbDocumentFromFWB(AWB shipmentMaster) throws CustomException {
      if (!ObjectUtils.isEmpty(shipmentMaster)) {
         deleteData("deleteRemarksSSRFWB", shipmentMaster.getSsrRemarksList(), sqlSessionTemplate);
         deleteData("deleteRemarksOSIFWB", shipmentMaster.getOsiRemarksList(), sqlSessionTemplate);
         if (!ObjectUtils.isEmpty(shipmentMaster.getConsignee())) {
            deleteData("deleteAWBShipperContactInfoFWB", shipmentMaster.getConsignee().getAddress().getContacts(),
                  sqlSessionTemplate);
            deleteData("deleteAWBShipperAddInfoFWB", shipmentMaster.getConsignee().getAddress(), sqlSessionTemplate);
         }
         if (!ObjectUtils.isEmpty(shipmentMaster.getShipper())) {
            deleteData("deleteAWBShipperContactInfoFWB", shipmentMaster.getShipper().getAddress().getContacts(),
                  sqlSessionTemplate);
            deleteData("deleteAWBShipperAddInfoFWB", shipmentMaster.getShipper().getAddress(), sqlSessionTemplate);
         }
         deleteData("deleteAWBShipperInfoFWB", shipmentMaster.getShipper(), sqlSessionTemplate);

         deleteData("deleteAWBShipperInfoFWB", shipmentMaster.getConsignee(), sqlSessionTemplate);

         // Routing

         deleteData("deleteAWBRoutingFWB", shipmentMaster.getRouting(), sqlSessionTemplate);

         // Special Handling Code
         deleteData("deleteAWBSHCFWB", shipmentMaster.getShcs(), sqlSessionTemplate);

         // Delete fwb
         deleteData("deleteAWBFromFWB", shipmentMaster, sqlSessionTemplate);
      }

   }

   /*
    * (non-Javadoc)
    * 
    * @see
    * com.ngen.cosys.shipment.awb.dao.ShipmentAWBDocumentDao#isValidConsignee(com.
    * ngen.cosys.shipment.awb.model.ShipmentMasterCustomerInfo)
    */
   @Override
   public boolean isValidConsignee(ShipmentMasterCustomerInfo consignee) throws CustomException {
      return this.fetchObject("sqlCheckValidConsignee", consignee, sqlSessionTemplate);
   }

   /*
    * (non-Javadoc)
    * 
    * @see com.ngen.cosys.shipment.awb.dao.ShipmentAWBDocumentDao#
    * isAppointedAgentRequired(com.ngen.cosys.shipment.awb.model.
    * ShipmentMasterCustomerInfo)
    */
   @Override
   public boolean isAppointedAgentRequired(ShipmentMasterCustomerInfo consignee) throws CustomException {
      return this.fetchObject("sqlCheckAppointedAgentExists", consignee, sqlSessionTemplate);
   }

   /*
    * (non-Javadoc)
    * 
    * @see com.ngen.cosys.shipment.awb.dao.ShipmentAWBDocumentDao#
    * isConsigneeAppointedAgentValid(com.ngen.cosys.shipment.awb.model.
    * ShipmentMasterCustomerInfo)
    */
   @Override
   public boolean isConsigneeAppointedAgentValid(ShipmentMasterCustomerInfo consignee) throws CustomException {
      return this.fetchObject("sqlCheckValidConsigneeAppointedAgent", consignee, sqlSessionTemplate);
   }

   /*
    * (non-Javadoc)
    * 
    * @see
    * com.ngen.cosys.shipment.awb.dao.ShipmentAWBDocumentDao#isValidShipper(com.
    * ngen.cosys.shipment.awb.model.ShipmentMasterCustomerInfo)
    */
   @Override
   public boolean isValidShipper(ShipmentMasterCustomerInfo consignee) throws CustomException {
      return this.fetchObject("sqlCheckValidShipper", consignee, sqlSessionTemplate);
   }

   @Override
   public AwbModelForFWB getShipment(AwbModelForFWB awbDetails) throws CustomException {
      return null;
   }

   @Override
   public void createShipment(AwbModelForFWB awbData) throws CustomException {
   }

   @Override
   public AwbModelForFWB getFwb(AWB awbDetails) throws CustomException {
      return this.fetchObject(AWBDocumentQueryId.SQL_GET_FWB_INFO.toString(), awbDetails, sqlSessionTemplate);
   }

   @Override
   public ShipmentMasterCustomerInfo getEmailInfo(ShipmentMasterCustomerInfo awbDetails) throws CustomException {
      return this.fetchObject(AWBDocumentQueryId.SQL_GET_EMAIL_INFO.toString(), awbDetails, sqlSessionTemplate);
   }

   @Override
   public ShipmentMasterCustomerInfo getFWBConsigneeInfo(AWB awbDetails) throws CustomException {
      return this.fetchObject(AWBDocumentQueryId.SQL_GET_FWB_CONSIGNEE_INFO.toString(), awbDetails, sqlSessionTemplate);
   }

   @Override
   public List<ShipmentMasterCustomerInfo> getFWBConsigneeAgentInfo(AWB awbDetails) throws CustomException {
      return this.fetchList(AWBDocumentQueryId.SQL_GET_FWB_CONSIGNEE_AGENT_INFO.toString(), awbDetails,
            sqlSessionTemplate);
   }

   @Override
   public List<ShipmentMasterCustomerInfo> sqlGetFWBConsigneeAgentInfoOnSelect(ShipmentMasterCustomerInfo awbDetails)
         throws CustomException {
      return this.fetchList(AWBDocumentQueryId.SQL_GET_FWB_CONSIGNEE_AGENT_INFO_ON_SELECT.toString(), awbDetails,
            sqlSessionTemplate);
   }

   @Override
   public BigDecimal getccFee() throws CustomException {
      return this.fetchObject(AWBDocumentQueryId.SQL_GET_CC_FEE.toString(), null, sqlSessionTemplate);
   }

   @Override
   public BigDecimal getMinccFee() throws CustomException {
      return this.fetchObject(AWBDocumentQueryId.SQL_GET_MIN_CC_FEE.toString(), null, sqlSessionTemplate);
   }

   /*
    * (non-Javadoc)
    * 
    * @see com.ngen.cosys.shipment.awb.dao.ShipmentAWBDocumentDao#
    * getDirectConsigneeCustomerId()
    */
   @Override
   public BigInteger getDirectConsigneeCustomerId() throws CustomException {
      return super.fetchObject(AWBDocumentQueryId.SQL_GET_DIRECT_CONSIGNEE_CUSTOMER_ID.toString(), new Object(),
            sqlSessionTemplate);
   }

   /*
    * (non-Javadoc)
    * 
    * @see com.ngen.cosys.shipment.awb.dao.ShipmentAWBDocumentDao#
    * getDirectShipperCustomerId()
    */
   @Override
   public BigInteger getDirectShipperCustomerId() throws CustomException {
      return super.fetchObject(AWBDocumentQueryId.SQL_GET_DIRECT_SHIPPER_CUSTOMER_ID.toString(), new Object(),
            sqlSessionTemplate);
   }

   @Override
   public Boolean isValidAppointedAgentCode(ShipmentMasterCustomerInfo consigneeDetails) throws CustomException {
      return this.fetchObject(AWBDocumentQueryId.SQL_IS_VALID_APPOINTED_AGENT_ID.toString(), consigneeDetails,
            sqlSessionTemplate);
   }

   /*
    * (non-Javadoc)
    * 
    * @see
    * com.ngen.cosys.shipment.awb.dao.ShipmentAWBDocumentDao#getInboundFlightId(com
    * .ngen.cosys.shipment.awb.model.AWB)
    */
   @Override
   public BigInteger getInboundFlightId(AWB awb) throws CustomException {
      return this.fetchObject(AWBDocumentQueryId.SQL_GET_FLIGHTDETAILS.toString(), awb, sqlSessionTemplate);
   }

   @Override
   public Boolean isShipmentOtherChargeInfoExist(ShipmentOtherChargeInfo shipmentOtherChargeInfo)
         throws CustomException {
      if (!ObjectUtils.isEmpty(shipmentOtherChargeInfo)) {
         return this.fetchObject(AWBDocumentQueryId.SQL_CHECK_SHIPMENTOTHERCHARGEINFO.toString(),
               shipmentOtherChargeInfo, sqlSessionTemplate);

      } else {
         return false;
      }
   }

   @Override
   public Integer isShipmentMasterCustomerInfoExist(ShipmentMasterCustomerInfo shipmentMasterCustomerInfo)
         throws CustomException {

      return this.fetchObject(AWBDocumentQueryId.SQL_CHECK_SHIPMENTMASTERCUSTOMERINFO.toString(),
            shipmentMasterCustomerInfo, sqlSessionTemplate);

   }

   @Override
	public void createShipmentFromFwb(AWB awbDetails) throws CustomException {

		// derive Domestic && International
		DomesticInternationalHelperRequest domesticInternationalHelperRequest = new DomesticInternationalHelperRequest();
		domesticInternationalHelperRequest.setOrigin(awbDetails.getOrigin());
		domesticInternationalHelperRequest.setDestination(awbDetails.getDestination());
		String domesticOrInternational = domesticInternationalHelper
				.getDOMINTHandling(domesticInternationalHelperRequest);

		awbDetails.setHandledByDOMINT(domesticOrInternational);

		// validate FWB && FHL
		// check FHL for AWB if exist set H else M
		HAWBHandlingHelperRequest hAWBHandlingHelperRequest = new HAWBHandlingHelperRequest();
		hAWBHandlingHelperRequest.setShipmentNumber(awbDetails.getShipmentNumber());
		hAWBHandlingHelperRequest.setShipmentDate(awbDetails.getShipmentdate());
		hAWBHandlingHelperRequest.setOrigin(awbDetails.getOrigin());
		hAWBHandlingHelperRequest.setDestination(awbDetails.getDestination());
		String handledByMasterHouse = hawbHandlingHelper.getHandledByMasterHouse(hAWBHandlingHelperRequest);
		if (!StringUtils.isEmpty(handledByMasterHouse)) {
			awbDetails.setHandledByMasterHouse(handledByMasterHouse);
		}

		awbDetails.setShipmentNumberForAuditTrail(awbDetails.getShipmentNumber());
		BigInteger shipmentId = super.fetchObject(AWBDocumentQueryId.SQL_GET_SHIPMENTMASTERID.toString(), awbDetails,
				sqlSessionTemplate);
		if (ObjectUtils.isEmpty(shipmentId)) {
			this.insertData(AWBDocumentQueryId.SQL_CREATE_SHIPMENTMASTER.toString(), awbDetails, sqlSessionTemplate);
		} else {
			// Set the shipment id
			awbDetails.setShipmentId(shipmentId);
			this.updateData(AWBDocumentQueryId.SQL_UPDATE_SHIPMENTMASTER_FROM_FWB.toString(), awbDetails,
					sqlSessionTemplate);
		}
	}

   @Override
   public String getShipmentCustomerInfoIdForShipper(AWB awbDetails) throws CustomException {
      return fetchObject("sqlGetShipmentCustomerInfoIdForShipper", awbDetails, sqlSessionTemplate);
   }

   @Override
   public String getShipmentCustomerInfoIdForConsignee(AWB awbDetails) throws CustomException {
      return fetchObject("sqlGetShipmentCustomerInfoIdForConsignee", awbDetails, sqlSessionTemplate);
   }

   @Override
   public String getShipmentCustomerAddInfoId(String shipmentCustomerInfoId) throws CustomException {
      return fetchObject("sqlGetShipmentCustomerAddInfoId", shipmentCustomerInfoId, sqlSessionTemplate);
   }

   @Override
   public int deleteShipmentMasterCustomerContactInfo(String shipmentCustomerAddInfoId) throws CustomException {
      return deleteData("sqlDeleteShipmentMasterCustomerContactInfo", shipmentCustomerAddInfoId, sqlSessionTemplate);
   }

   @Override
   public int deleteShipmentMasterCustomerAddressInfo(String shipmentCustomerInfoId) throws CustomException {
      return deleteData("sqlDeleteShipmentMasterCustomerAddressInfo", shipmentCustomerInfoId, sqlSessionTemplate);
   }

   @Override
   public int deleteShipmentMasterCustomerInfoForShipper(AWB awbDetails) throws CustomException {
      return deleteData("sqlDeleteShipmentMasterCustomerInfoForShipper", awbDetails, sqlSessionTemplate);
   }

   @Override
   public int deleteShipmentMasterCustomerInfoForConsignee(AWB awbDetails) throws CustomException {
      return deleteData("sqlDeleteShipmentMasterCustomerInfoForConsignee", awbDetails, sqlSessionTemplate);
   }

   @Override
   public Boolean checkValidCurrency(ShipmentOtherChargeInfo requestModel) throws CustomException {
      return this.fetchObject(AWBDocumentQueryId.SQL_IS_VALID_CURRENCY.toString(), requestModel, sqlSessionTemplate);
   }

   @Override
   public ShipmentMasterCustomerAddressInfo getAllAppointedAgents(ShipmentMasterCustomerInfo request)
         throws CustomException {
      return this.fetchObject(AWBDocumentQueryId.SQL_GET_ALL_APPOINTED_AGENT.toString(), request, sqlSessionTemplate);
   }

   /*
    * (non-Javadoc)
    * 
    * @see com.ngen.cosys.shipment.awb.dao.ShipmentAWBDocumentDao#
    * updateOriginDestinationToOtherSources(com.ngen.cosys.shipment.awb.model.AWB)
    */
   @Override
   public void updateOriginDestinationToOtherSources(AWB awbDetails) throws CustomException {
      // 1. Update pre-lodge
      this.updateData("sqlUpdateOriginDestinationPrelodgeFromAWBDocument", awbDetails, sqlSessionTemplate);

      // 2. Update arrival manifest
      this.updateData("sqlUpdateOriginDestinationArrivalManifestFromAWBDocument", awbDetails, sqlSessionTemplate);

      // 3. Update booking
      this.updateBookingInfo(awbDetails);

      // 4. Update eAcceptance
      this.updateData("sqlUpdateOriginDestinationAcceptanceFromAWBDocument", awbDetails, sqlSessionTemplate);

      // 5. Update FWB
      this.updateData("sqlUpdateOriginDestinationFWBFromAWBDocument", awbDetails, sqlSessionTemplate);

      // 6. Update FHL
      this.updateData("sqlUpdateOriginDestinationFHLFromAWBDocument", awbDetails, sqlSessionTemplate);

      // 7. Update Customs
      this.updateData("sqlUpdateOriginDestinationCustomsFromAWBDocument", awbDetails, sqlSessionTemplate);

      // 8. Update Export Manifest
      this.updateData("sqlUpdateOriginDestinationExportManifestFromAWBDocument", awbDetails, sqlSessionTemplate);

   }
   
   private void updateBookingInfo(AWB awbDetails) throws CustomException {
	   // booking update 
       CommonBooking booking= new CommonBooking();
       booking.setShipmentNumber(awbDetails.getShipmentNumber());
       booking.setShipmentDate(awbDetails.getShipmentdate());
       boolean check =commonBookingService.checkDocInOrAcceptanceFinalize(booking);
       if(check) {
      	 // update booking data
      	 booking.setOrigin(awbDetails.getOrigin());
      	 booking.setDestination(awbDetails.getDestination());
      	 booking.setPieces(awbDetails.getPieces());
      	 booking.setWeight(awbDetails.getWeight());
      	 booking.setNog(awbDetails.getNatureOfGoodsDescription());
      	 booking.setWeightUnitCode(awbDetails.getWeightUnitCode());
      	 booking.setCreatedBy(awbDetails.getCreatedBy());
      	 commonBookingService.updateBookingMethod(booking);
       }
   }

   public BigInteger isShipmentLoaded(AWB awb) throws CustomException {
      return this.fetchObject(AWBDocumentQueryId.SQL_GET_IS_SHIPMENT_LOADED.toString(), awb, sqlSessionTemplate);

   }

   public BigInteger isPoGeneratedForShipment(AWB awb) throws CustomException {
      return this.fetchObject(AWBDocumentQueryId.SQL_GET_IS_PO_GENERATED.toString(), awb, sqlSessionTemplate);
   }

   public String getShipmentType(AWB awb) throws CustomException {
      return this.fetchObject(AWBDocumentQueryId.SQL_GET_SHIPMENT_TYPE.toString(), awb, sqlSessionTemplate);
   }

   @Override
   public List<ShipmentMasterRoutingInfo> getRoutingInfo(RoutingRequestModel model) throws CustomException {
      return fetchList(AWBDocumentQueryId.SQL_CHECK_SHIPMENTMASTERROUTINGINFO_WITH_SHIPMENTNUMBER.toString(), model,
            sqlSessionTemplate);
   }

   public BigInteger getStatusUpdateEventPieces(AWB awb) throws CustomException {
      return this.fetchObject(AWBDocumentQueryId.SQL_GET_STATUS_UPDATE_EVENT_PIECES.toString(), awb,
            sqlSessionTemplate);
   }

   public AWB getBreakDownAndFoundPieces(AWB awb) throws CustomException {

      return this.fetchObject(AWBDocumentQueryId.SQL_GET_BREAKDOWN_FOUND_PIECES.toString(), awb, sqlSessionTemplate);
   }

   @Override
   public void deleteImpShipmentVerification(AWB awb) throws CustomException {
      updateData("sqlClearShipmentVerificationForFlightFromAwbDocument", awb, sqlSessionTemplate);
      deleteData("sqlDeleteShipmentVerificationFromAwbDocument", awb, sqlSessionTemplate);
   }

   /*
    * (non-Javadoc)
    * 
    * @see
    * com.ngen.cosys.shipment.awb.dao.ShipmentAWBDocumentDao#isValidExemptionCode(
    * com.ngen.cosys.shipment.awb.model.ShipmentMasterLocalAuthorityDetails)
    */
   @Override
   public boolean isValidExemptionCode(ShipmentMasterLocalAuthorityDetails requestModel) throws CustomException {
      int isValidExemptionCode = this.fetchObject("sqlCheckIsValidLocalAuthorityExemptionCode", requestModel,
            sqlSessionTemplate);
      return (isValidExemptionCode > 0);
   }

	@Override
	public AWB getLastModifiedInfo(AWB awb) throws CustomException {
		return  this.fetchObject("getLastUpdatedInfo", awb, sqlSessionTemplate);
	}

	@Override
	public Boolean checkDocumentAcceptance(AWB requestModel) throws CustomException {
		return  this.fetchObject("checkDocumentAcceptanceDoneOrNot", requestModel, sqlSessionTemplate);
	}

	@Override
	public AWB getDeliveredPieces(AWB data) throws CustomException {
		
		return  this.fetchObject("sqlGetDeliveredPiecesForAwbDocument", data, sqlSessionTemplate);
	}

	
	 /**
	    * Method to Delete All SHCs comes under COU SHCHandlingGroupCode
	    * 
	    * @param awbData
	    * @return void
	    * @throws CustomException
	    */
	@Override
	public void deleteCOUShc(AWB awbData) throws CustomException {
		//delete couGroup shcs from shipment_inventorySHC
		 deleteData("sqlDeleteCouGroupSHCsInventorySHC", awbData, sqlSessionTemplate);
		 
		 //delete couGroup shcs from inbound breakdown
		 deleteData("sqlDeleteCouGroupSHCsImpBreakDownShc", awbData, sqlSessionTemplate);
		 
		 
		 //delete couGroup shcs from Shipment_MasterSHC
		 deleteData("sqlDeleteCouGroupSHCsMasterSHC", awbData, sqlSessionTemplate);
	}
	
	@Override
	public Integer isFlightCompleted(BigInteger flightId) throws CustomException {
		return fetchObject("sqlCheckFlightCompleted", flightId, sqlSessionTemplate);
	}

	@Override
	public String getMessageType(Map<String, BigInteger> map) throws CustomException {
		return fetchObject("sqlGetMessageType", map, sqlSessionTemplate);
	}

	@Override
	public String getClearingAgentName(String agentCode) throws CustomException {
		return fetchObject("sqlFetchClearingAgentName", agentCode, sqlSessionTemplate);
	}

	@Override
	public Integer isInternationalShipment(BigInteger shipmentId) throws CustomException {
		return fetchObject("isInternationalShipment", shipmentId, sqlSessionTemplate);
	}

}