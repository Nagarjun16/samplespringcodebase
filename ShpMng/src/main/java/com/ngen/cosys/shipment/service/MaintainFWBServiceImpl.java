package com.ngen.cosys.shipment.service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.ngen.cosys.billing.api.Charge;
import com.ngen.cosys.billing.api.enums.ChargeEvents;
import com.ngen.cosys.billing.api.enums.ReferenceType;
import com.ngen.cosys.billing.api.model.ChargeableEntity;
import com.ngen.cosys.export.commonbooking.model.CommonBooking;
import com.ngen.cosys.export.commonbooking.service.CommonBookingService;
import com.ngen.cosys.framework.constant.ErrorType;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.multitenancy.feature.model.ApplicationFeatures;
import com.ngen.cosys.multitenancy.feature.support.FeatureUtility;
import com.ngen.cosys.multitenancy.utilities.MultiTenantUtility;
import com.ngen.cosys.processing.engine.rule.executor.RuleExecutor;
import com.ngen.cosys.processing.engine.rule.fact.FactPayload;
import com.ngen.cosys.processing.engine.rule.fact.FactShipment;
import com.ngen.cosys.processing.engine.rule.group.FWBGroup;
import com.ngen.cosys.routing.DeriveRoutingHelper;
import com.ngen.cosys.routing.RoutingRequestModel;
import com.ngen.cosys.routing.RoutingResponseModel;
import com.ngen.cosys.shipment.awb.dao.ShipmentAWBDocumentDao;
import com.ngen.cosys.shipment.awb.model.AWB;
import com.ngen.cosys.shipment.awb.model.AwbModelForFWB;
import com.ngen.cosys.shipment.awb.model.ShipmentMasterCustomerAddressInfo;
import com.ngen.cosys.shipment.awb.model.ShipmentMasterCustomerInfo;
import com.ngen.cosys.shipment.awb.model.ShipmentMasterHandlingArea;
import com.ngen.cosys.shipment.awb.model.ShipmentMasterRoutingInfo;
import com.ngen.cosys.shipment.awb.model.ShipmentOtherChargeInfo;
import com.ngen.cosys.shipment.awb.model.ShipmentOtherChargeInfoFWB;
import com.ngen.cosys.shipment.awb.model.ShipmentRemarksModel;
import com.ngen.cosys.shipment.dao.MaintainFWBDAO;
import com.ngen.cosys.shipment.enums.CustomerType;
import com.ngen.cosys.shipment.model.CustomerInfo;
import com.ngen.cosys.shipment.model.FWB;
import com.ngen.cosys.shipment.model.FWBDetails;
import com.ngen.cosys.shipment.model.FetchFWBRequest;
import com.ngen.cosys.shipment.model.FetchRouting;
import com.ngen.cosys.shipment.model.RateDescOtherInfo;
import com.ngen.cosys.shipment.model.RateDescription;
import com.ngen.cosys.shipment.model.Routing;
import com.ngen.cosys.shipment.util.ShipmentUtility;
import com.ngen.cosys.shipment.validator.ShpMngBusinessValidator;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
public class MaintainFWBServiceImpl implements MaintainFWBService {
   private static final String EXCEPTION = "Exception Happened ... ";

   private static final Logger lOgger = LoggerFactory.getLogger(MaintainFWBService.class);
   @Autowired
   private ShipmentAWBDocumentDao daoAwb;

   @Autowired
   private MaintainFWBDAO dao;

   @Autowired
   private RuleExecutor ruleExecutor;

   @Autowired
   @Qualifier("maintainFWBValidator")
   private ShpMngBusinessValidator fwbBusinessValidator;

   @Autowired
   private DeriveRoutingHelper deriveShipmentRouting;
   
   @Autowired
   private CommonBookingService commonBookingService;

   @Override
   public FWB get(FetchFWBRequest requestModel) throws CustomException {
      String awbNumber = requestModel.getAwbNumber();
      FWB fwb = dao.get(awbNumber);
      Optional<FWB> o = Optional.ofNullable(fwb);
      if (!o.isPresent()) {
         fwb = dao.fwbOnSearch(awbNumber);
         Optional<FWB> oq = Optional.ofNullable(fwb);
         if (!oq.isPresent()) {
            throw new CustomException("awb.no.fwb", null, ErrorType.ERROR);
         }
      } else {
         fwb.setNatureOfgoods(fwb.getNatureOfGoodsDescription());
			if (!CollectionUtils.isEmpty(fwb.getRateDescription())) {
				for (RateDescription rate : fwb.getRateDescription()) {
					if (!CollectionUtils.isEmpty(rate.getRateDescriptionOtherInfo())) {
						for (RateDescOtherInfo rateDesc : rate.getRateDescriptionOtherInfo()) {
							if (!ObjectUtils.isEmpty(rateDesc) && !StringUtils.isEmpty(rateDesc.getRateLine())
									&& rateDesc.getRateLine().equalsIgnoreCase("ND")
									&& !rateDesc.getMeasurementUnitCode().isEmpty()
									&& rateDesc.getMeasurementUnitCode().equalsIgnoreCase("NDA")) {
								rateDesc.setNoDimensionAvailable(true);
							}
						}
					}
				}
			}
      }

      return fwb;
   }

   @Override
   @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
   public FWB save(FWB requestModel) throws CustomException {
      // validate the form
      FWB responseModel = (FWB) fwbBusinessValidator.validate(requestModel);
      if (responseModel.isErrorFalg()) {
         throw new CustomException();
      }
      if (!ShipmentUtility.isBaseModelHasError(responseModel)) {
         // AWB Number set
         responseModel = awbNumberSet(responseModel);
         // Customer Type set
         responseModel.setConsigneeInfo(customerTypeSet(responseModel.getConsigneeInfo(), CustomerType.CONSIGNEE));
         responseModel.setShipperInfo(customerTypeSet(responseModel.getShipperInfo(), CustomerType.SHIPPER));
         responseModel.setAlsoNotify(customerTypeSet(responseModel.getAlsoNotify(), CustomerType.ALSO_NOTIFY));

         // IATA code in agent temp
         if (!Optional.ofNullable(responseModel.getAgentInfo().getIATACargoAgentNumericCode()).isPresent()) {
            responseModel.getAgentInfo().setIATACargoAgentNumericCode(new BigInteger("0"));
         }

         responseModel = dao.save(responseModel);

         // saving AWB Document
         // If it can override
         AWB shipmentMaster = new AWB();
         shipmentMaster.setShipmentNumber(responseModel.getAwbNumber());
         shipmentMaster.setShipmentdate(responseModel.getAwbDate());
         // 1. Pull FWB message data OR Shipment Master data
         AWB awbData = daoAwb.getShipment(shipmentMaster);
         // Retrieve the FWB info
         AwbModelForFWB shipmentMasterTemp = daoAwb.getFwb(shipmentMaster);
         if (!ObjectUtils.isEmpty(awbData)) {
            shipmentMaster.setShipmentId(awbData.getShipmentId());
            shipmentMaster.setDocumentPouchReceivedOn(awbData.getDocumentPouchReceivedOn());
            if (!StringUtils.isEmpty(awbData.getDocumentType())
                  && "ORIGINAL".equalsIgnoreCase(awbData.getDocumentType())) {
               shipmentMaster.setDocumentReceivedOn(awbData.getDocumentDate());
            } else if (!StringUtils.isEmpty(awbData.getDocumentType())
                  && "COPY".equalsIgnoreCase(awbData.getDocumentType())) {
               shipmentMaster.setPhotoCopyReceivedOn(awbData.getDocumentDate());
            }

            // Set the Carrier Code if only empty
            if (StringUtils.isEmpty(awbData.getCarrierCode())) {
               shipmentMaster.setCarrierCode(shipmentMasterTemp.getCarrierCode());
            } else {
               shipmentMaster.setCarrierCode(awbData.getCarrierCode());
            }

            // Set the Nature Of Goods Description if only empty
            if (StringUtils.isEmpty(awbData.getNatureOfGoodsDescription())) {
               shipmentMaster.setNatureOfGoodsDescription(shipmentMasterTemp.getNatureOfGoodsDescription());
            } else if (StringUtils.isEmpty(requestModel.getNatureOfGoodsDescription())) {
               shipmentMaster.setNatureOfGoodsDescription(awbData.getNatureOfGoodsDescription());
            } else {
               shipmentMaster.setNatureOfGoodsDescription(requestModel.getNatureOfgoods());
            }

            // Override the consignee appointed agent if found
            if (!ObjectUtils.isEmpty(awbData.getConsignee()) && !ObjectUtils.isEmpty(shipmentMaster.getConsignee())) {
               shipmentMaster.getConsignee().setAppointedAgent(awbData.getConsignee().getAppointedAgent());
            }

            // Override the shipper appointed agent if found
            if (!ObjectUtils.isEmpty(awbData.getShipper()) && !ObjectUtils.isEmpty(shipmentMaster.getShipper())) {
               shipmentMaster.getShipper().setAppointedAgent(awbData.getShipper().getAppointedAgent());
            }

         } else {
            shipmentMaster.setCarrierCode(shipmentMasterTemp.getCarrierCode());
            shipmentMaster.setNatureOfGoodsDescription(shipmentMasterTemp.getNatureOfGoodsDescription());
         }
         shipmentMaster.setOrigin(shipmentMasterTemp.getOrigin());
         shipmentMaster.setDestination(shipmentMasterTemp.getDestination());
         shipmentMaster.setPieces(shipmentMasterTemp.getPieces());
         shipmentMaster.setWeight(shipmentMasterTemp.getWeight());
         shipmentMaster.setWeightUnitCode(shipmentMasterTemp.getWeightUnitCode());

         shipmentMaster.setShipmentType("AWB");
         shipmentMaster.setConsignee(shipmentMasterTemp.getConsignee());
         shipmentMaster.setShipper(shipmentMasterTemp.getShipper());
         shipmentMaster.setAlsoNotify(shipmentMasterTemp.getNotify());
         shipmentMaster.setRouting(shipmentMasterTemp.getRouting());
         shipmentMaster.setShcs(shipmentMasterTemp.getShcs());
         shipmentMaster.setSsrRemarksList(shipmentMasterTemp.getSsrRemarksList());
         shipmentMaster.setOsiRemarksList(shipmentMasterTemp.getOsiRemarksList());
         ShipmentOtherChargeInfo otherChargeInfo=new ShipmentOtherChargeInfo();
         ShipmentOtherChargeInfoFWB otherFWBChargeInfo= shipmentMasterTemp.getOtherChargeInfo();
         if(!ObjectUtils.isEmpty(otherFWBChargeInfo)) {
         if(!StringUtils.isEmpty(otherFWBChargeInfo.getCustomsOrigin())) {
         otherChargeInfo.setCustomsOrigin(otherFWBChargeInfo.getCustomsOrigin());
         }
         if(!StringUtils.isEmpty(otherFWBChargeInfo.getCurrency())) {
         otherChargeInfo.setCurrency(otherFWBChargeInfo.getCurrency());
         }
         if(!StringUtils.isEmpty(otherFWBChargeInfo.getChargeCode())) {
         otherChargeInfo.setChargeCode(otherFWBChargeInfo.getChargeCode());
         }
         if(!StringUtils.isEmpty(otherFWBChargeInfo.getDueFromAirline())) {
         otherChargeInfo.setDueFromAirline(new BigDecimal(otherFWBChargeInfo.getDueFromAirline()));
         }
         if(!StringUtils.isEmpty(otherFWBChargeInfo.getDueFromAgent())) {
         otherChargeInfo.setDueFromAgent(new BigDecimal(otherFWBChargeInfo.getDueFromAgent()));
         }
         if(!StringUtils.isEmpty(otherFWBChargeInfo.getFreightCharges())) {
         otherChargeInfo.setFreightCharges(new BigDecimal(otherFWBChargeInfo.getFreightCharges()));
         }
         otherChargeInfo.setExchangeRate(otherFWBChargeInfo.getExchangeRate());
         otherChargeInfo.setTotalCollectChargesChargeAmount(otherFWBChargeInfo.getTotalCollectChargesChargeAmount());
         otherChargeInfo.setDestinationCurrencyChargeAmount(otherFWBChargeInfo.getDestinationCurrencyChargeAmount());
         if(!StringUtils.isEmpty(otherFWBChargeInfo.getCcFee())) {
         otherChargeInfo.setCcFee(otherFWBChargeInfo.getCcFee());
         }
         if(!StringUtils.isEmpty(otherFWBChargeInfo.getTax())) {
         otherChargeInfo.setTax(new BigDecimal(otherFWBChargeInfo.getTax()));
         }
         if(!StringUtils.isEmpty(otherFWBChargeInfo.getTotal())) {
         otherChargeInfo.setTotal(otherFWBChargeInfo.getTotal());
         }
         if(!StringUtils.isEmpty(otherFWBChargeInfo.getValuationCharges())) {
         otherChargeInfo.setValuationCharges(new BigDecimal(otherFWBChargeInfo.getValuationCharges()));
         }
         }
         shipmentMaster.setOtherChargeInfo(otherChargeInfo);

         // Check whether an shipment is an EAWB
         Boolean eawb = this.dao.isShipmentAnEAWB(requestModel);
         if (eawb && !MultiTenantUtility.isTenantCityOrAirport(shipmentMasterTemp.getOrigin())
               && ObjectUtils.isEmpty(shipmentMaster.getDocumentReceivedOn())
               && ObjectUtils.isEmpty(shipmentMaster.getPhotoCopyReceivedOn())) {
            shipmentMaster.setDocumentReceivedOn(LocalDateTime.now());
         }

         // Derive part shipment for a shipment
         Boolean partShipment = dao.isPartShipment(requestModel);
         shipmentMaster.setPartShipment(partShipment);

         // Derive Service Shipment
         Boolean svcShipment = dao.isSVCShipment(requestModel);
         shipmentMaster.setSvc(svcShipment);

         BigInteger loadeShipmentCount= dao.getShipmentLodedinfo(shipmentMaster);
         
         //bug 11549 for ACES discrepancy
         if(loadeShipmentCount == null || loadeShipmentCount.intValue() == 0) {
          // Add / update shipment master
          daoAwb.createShipmentFromFwb(shipmentMaster);
         }
         
         // booking update 
         CommonBooking booking= new CommonBooking();
         booking.setShipmentNumber(shipmentMaster.getShipmentNumber());
         booking.setShipmentDate(shipmentMaster.getShipmentdate());
         boolean check =commonBookingService.checkDocInOrAcceptanceFinalize(booking);
         if(!check) {
        	 // update booking data
        	 booking.setOrigin(shipmentMaster.getOrigin());
        	 booking.setDestination(shipmentMaster.getDestination());
        	 booking.setPieces(shipmentMaster.getPieces());
        	 booking.setWeight(shipmentMaster.getWeight());
        	 booking.setNog(shipmentMaster.getNatureOfGoodsDescription());
        	 booking.setWeightUnitCode(shipmentMaster.getWeightUnitCode());
        	 booking.setCreatedBy(shipmentMaster.getCreatedBy());
        	 commonBookingService.updateBookingMethod(booking);
         }

         // Add routing info
         this.createRoutingInfo(shipmentMaster);

         // Add SHC
         this.createSHC(shipmentMaster);

         // Add SHC Group
         if (!CollectionUtils.isEmpty(shipmentMaster.getShcHandlingGroup())) {
            shipmentMaster.getShcHandlingGroup().forEach(t -> t.setShipmentId(shipmentMaster.getShipmentId()));
            daoAwb.createShipmentMasterShcHandlingGroup(shipmentMaster.getShcHandlingGroup());
         }

         // Add Other Charge Info
         if (shipmentMaster.getOtherChargeInfo() != null) {
            shipmentMaster.getOtherChargeInfo().setShipmentId(shipmentMaster.getShipmentId());
            Boolean exist = daoAwb.isShipmentOtherChargeInfoExist(shipmentMaster.getOtherChargeInfo());
            if ((!exist) || (exist && !MultiTenantUtility.isTenantCityOrAirport(shipmentMaster.getDestination()))) {
               daoAwb.createShipmentOtherChargeInfo(shipmentMaster.getOtherChargeInfo());
            }
         }

         ShipmentMasterHandlingArea shipmentMasterHandlingArea = new ShipmentMasterHandlingArea();
         shipmentMasterHandlingArea.setHandledBy(requestModel.getTerminal());
         shipmentMaster.setHandlingArea(shipmentMasterHandlingArea);
         shipmentMasterHandlingArea.setShipmentId(shipmentMaster.getShipmentId());

         // Consignee
         if (!MultiTenantUtility.isTenantCityOrAirport(requestModel.getOrigin()) ||
					FeatureUtility.isFeatureEnabled(ApplicationFeatures.Exp.FWB.CreateShipmentOnReceipt.class)) {
            Optional<ShipmentMasterCustomerInfo> consignee = Optional.ofNullable(shipmentMaster.getConsignee());
            if (consignee.isPresent()) {
               shipmentMaster.getConsignee().setShipmentId(shipmentMaster.getShipmentId());
               Integer id = daoAwb.isShipmentMasterCustomerInfoExist(shipmentMaster.getConsignee());

               if (ObjectUtils.isEmpty(id) || (!ObjectUtils.isEmpty(id)
                     && !MultiTenantUtility.isTenantCityOrAirport(requestModel.getDestination()))) {
                  this.createConsignee(shipmentMaster);
               }
            }
         }

         // Shipper
         if (MultiTenantUtility.isTranshipment(requestModel.getOrigin(), requestModel.getDestination()) ||
					FeatureUtility.isFeatureEnabled(ApplicationFeatures.Exp.FWB.CreateShipmentOnReceipt.class)) {
            Optional<ShipmentMasterCustomerInfo> shipper = Optional.ofNullable(shipmentMaster.getShipper());
            if (shipper.isPresent()) {
               shipmentMaster.getShipper().setShipmentId(shipmentMaster.getShipmentId());
               Integer id = daoAwb.isShipmentMasterCustomerInfoExist(shipmentMaster.getShipper());
               if (ObjectUtils.isEmpty(id) || (!ObjectUtils.isEmpty(id)
                     && !MultiTenantUtility.isTenantCityOrAirport(requestModel.getDestination()))) {
                  this.createShipper(shipmentMaster);
               }
            }
         }

         // Notify
         this.createNotify(shipmentMaster);
         // OSI Remarks
         this.createOSIRemarks(shipmentMaster);
         // SSR Remarks
         this.createSSRRemarks(shipmentMaster);

         // cargo processing enginee
         executeRuleEngineProcess(requestModel);

         // Create the charge entry for manual creation of FWB in case of export
         // shipment
			if (MultiTenantUtility.isTenantCityOrAirport(requestModel.getOrigin()) ||
					FeatureUtility.isFeatureEnabled(ApplicationFeatures.Exp.FWB.CreateShipmentOnReceipt.class)) {

				// Get the shipment id
				FWB chargeModel = this.dao.getShipmentInfoForCharges(requestModel);
				if (!ObjectUtils.isEmpty(chargeModel)) {
					ChargeableEntity chargeableEntity = new ChargeableEntity();
					if (requestModel.getReceivedManuallyFlag()) {
						chargeableEntity.setReferenceType(ReferenceType.SHIPMENT.getReferenceType());
						chargeableEntity.setReferenceId(chargeModel.getShipmentId());
						chargeableEntity.setAdditionalReferenceId(
								BigInteger.valueOf(requestModel.getShipmentFreightWayBillId()));
						chargeableEntity.setAdditionalReferenceType(ReferenceType.FWB_ID.getReferenceType());
						chargeableEntity.setEventType(ChargeEvents.EXP_FWB_UPDATE);
						chargeableEntity.setProcessType(ChargeEvents.PROCESS_EXP.getEnum());
						chargeableEntity.setHandlingTerminal(requestModel.getTerminal());
						chargeableEntity.setUserCode(requestModel.getLoggedInUser());
						chargeableEntity.setCustomerId(chargeModel.getCustomerId());
						chargeableEntity.setQuantity(BigDecimal.valueOf(1));
						Charge.calculateCharge(chargeableEntity);
					} else {
						Charge.cancelCharge(chargeableEntity);
					}
				}
			}
      }

      return responseModel;
   }

   private void executeRuleEngineProcess(FWB requestModel) {
      //
      FactPayload factPayload = new FactPayload();
      FactShipment factShipment = new FactShipment();
      factShipment.setShipmentNumber(requestModel.getAwbNumber());
      factShipment.setShipmentDate(requestModel.getAwbDate());
      factPayload.setFactShipment(factShipment);
      factPayload.setRulesPayload(new ArrayList<>());
      factPayload.getRulesPayload().add(FWBGroup.class);
      // Set Audit info
      factPayload.setCreatedBy(requestModel.getCreatedBy());
      factPayload.setCreatedOn(requestModel.getCreatedOn());
      factPayload.setModifiedBy(requestModel.getModifiedBy());
      factPayload.setModifiedOn(requestModel.getModifiedOn());
      // Execute Rule
      try {
         this.ruleExecutor.closeRuleFailure(factPayload);
      } catch (CustomException e) {
         lOgger.error(EXCEPTION, e);
      }
   }

   private void createRoutingInfo(AWB shipmentMaster) throws CustomException {
      // Add Routing Info
      if (!CollectionUtils.isEmpty(shipmentMaster.getRouting())) {
         // Derive the routing info
         RoutingRequestModel requestModel = new RoutingRequestModel();
         requestModel.setShipmentNumber(shipmentMaster.getShipmentNumber());
         requestModel.setShipmentDate(shipmentMaster.getShipmentdate());
         requestModel.setShipmentOrigin(shipmentMaster.getOrigin());
         requestModel.setShipmentDestination(shipmentMaster.getDestination());
         requestModel.setCarrier(shipmentMaster.getCarrierCode());

         List<RoutingResponseModel> routing = this.deriveShipmentRouting.getRoutes(requestModel);
         if (!CollectionUtils.isEmpty(routing)) {
            // Add the newly derived routing info
            List<ShipmentMasterRoutingInfo> shipmentRouting = new ArrayList<>();
            for (RoutingResponseModel r : routing) {
               ShipmentMasterRoutingInfo c = new ShipmentMasterRoutingInfo();
               c.setCarrier(r.getNextCarrier());
               c.setFromPoint(r.getNextDestination());
               // Add it to the list
               shipmentRouting.add(c);
            }
            shipmentRouting.forEach(t -> t.setShipmentId(shipmentMaster.getShipmentId()));
            daoAwb.createShipmentMasterRoutingInfo(shipmentRouting);
         }
      }
   }

   private void createSHC(AWB shipmentMaster) throws CustomException {
      // Add SHC
      if (!CollectionUtils.isEmpty(shipmentMaster.getShcs())) {
         shipmentMaster.getShcs().forEach(t -> t.setShipmentId(shipmentMaster.getShipmentId()));
         daoAwb.createShipmentMasterShc(shipmentMaster.getShcs());
      }
   }

   private void createOSIRemarks(AWB shipmentMaster) throws CustomException {
      // Remarks
      if (!CollectionUtils.isEmpty(shipmentMaster.getOsiRemarksList())) {
         for (ShipmentRemarksModel t : shipmentMaster.getOsiRemarksList()) {
            if (Optional.ofNullable(t).isPresent() && Optional.ofNullable(t.getShipmentRemarks()).isPresent()
                  && !t.getShipmentRemarks().isEmpty()) {
               t.setShipmentId(shipmentMaster.getShipmentId());
               t.setShipmentNumber(shipmentMaster.getShipmentNumber());
               t.setShipmentdate(shipmentMaster.getShipmentdate());
               t.setRemarkType("OSI");
               t.setShipmentType(shipmentMaster.getShipmentType());
               this.createShipmentRemarks(t);
            }
         }
      }
   }

   private void createSSRRemarks(AWB shipmentMaster) throws CustomException {
      // Remarks
      if (!CollectionUtils.isEmpty(shipmentMaster.getSsrRemarksList())) {
         for (ShipmentRemarksModel t : shipmentMaster.getSsrRemarksList()) {
            if (Optional.ofNullable(t).isPresent() && Optional.ofNullable(t.getShipmentRemarks()).isPresent()) {
               t.setShipmentId(shipmentMaster.getShipmentId());
               t.setShipmentNumber(shipmentMaster.getShipmentNumber());
               t.setShipmentdate(shipmentMaster.getShipmentdate());
               t.setRemarkType("SSR");
               t.setShipmentType(shipmentMaster.getShipmentType());
               t.setShipmentRemarksId(t.getShipmentRemarksId());
               this.createShipmentRemarks(t);
            }
         }
      }
   }

   private void createShipmentRemarks(ShipmentRemarksModel shipmentRemarksModel) throws CustomException {
      if (shipmentRemarksModel.getShipmentRemarks() != null) {
         // Create a remarks
         this.daoAwb.create(shipmentRemarksModel);
      }
   }

   /**
    * Method to create Shipper for an shipment
    * 
    * @param shipmentMaster
    * @throws CustomException
    */
   private void createShipper(AWB shipmentMaster) throws CustomException {
      // Add Shipper
      Optional<ShipmentMasterCustomerInfo> shipper = Optional.ofNullable(shipmentMaster.getShipper());
      if (shipper.isPresent()) {
         shipmentMaster.getShipper().setShipmentId(shipmentMaster.getShipmentId());
         if (!StringUtils.isEmpty(shipmentMaster.getShipper().getCustomerName())) {
            daoAwb.createShipmentMasterCustomerInfo(shipmentMaster.getShipper());

            Optional<ShipmentMasterCustomerAddressInfo> shipperAddress = Optional
                  .ofNullable(shipmentMaster.getShipper().getAddress());
            if (shipperAddress.isPresent()) {
               shipmentMaster.getShipper().getAddress().setShipmentCustomerInfoId(shipmentMaster.getShipper().getId());

               daoAwb.createShipmentMasterCustomerAddressInfo(shipmentMaster.getShipper().getAddress());
            }

            if (shipperAddress.isPresent()
                  && !CollectionUtils.isEmpty(shipmentMaster.getShipper().getAddress().getContacts())) {
               shipmentMaster.getShipper().getAddress().getContacts()
                     .forEach(t -> t.setId(shipmentMaster.getShipper().getAddress().getShipmentCustomerInfoId()));

               daoAwb.createShipmentMasterCustomerContactInfo(shipmentMaster.getShipper().getAddress().getContacts());
            }
         }
      }
   }

   /**
    * Method to create Shipper for an shipment
    * 
    * @param shipmentMaster
    * @throws CustomException
    */
   private void createNotify(AWB shipmentMaster) throws CustomException {
      // Add Shipper
      Optional<ShipmentMasterCustomerInfo> notify = Optional.ofNullable(shipmentMaster.getAlsoNotify());
      if (notify.isPresent()) {
         shipmentMaster.getAlsoNotify().setShipmentId(shipmentMaster.getShipmentId());
         if (!StringUtils.isEmpty(shipmentMaster.getAlsoNotify().getCustomerName())) {

            daoAwb.createShipmentMasterCustomerInfo(shipmentMaster.getAlsoNotify());

            Optional<ShipmentMasterCustomerAddressInfo> notifyAddress = Optional
                  .ofNullable(shipmentMaster.getAlsoNotify().getAddress());
            if (notifyAddress.isPresent()) {
               shipmentMaster.getAlsoNotify().getAddress()
                     .setShipmentCustomerInfoId(shipmentMaster.getAlsoNotify().getId());

               daoAwb.createShipmentMasterCustomerAddressInfo(shipmentMaster.getAlsoNotify().getAddress());
            }

            if (notifyAddress.isPresent()
                  && !CollectionUtils.isEmpty(shipmentMaster.getAlsoNotify().getAddress().getContacts())) {
               shipmentMaster.getAlsoNotify().getAddress().getContacts()
                     .forEach(t -> t.setId(shipmentMaster.getAlsoNotify().getAddress().getShipmentCustomerInfoId()));
               daoAwb.createShipmentMasterCustomerContactInfo(
                     shipmentMaster.getAlsoNotify().getAddress().getContacts());
            }
         }
      }
   }

   /**
    * Method to create consignee for an shipment
    * 
    * @param shipmentMaster
    * @throws CustomException
    */
   private void createConsignee(AWB shipmentMaster) throws CustomException {
      // Add consignee
      Optional<ShipmentMasterCustomerInfo> consignee = Optional.ofNullable(shipmentMaster.getConsignee());
      if (consignee.isPresent()) {
         shipmentMaster.getConsignee().setShipmentId(shipmentMaster.getShipmentId());
         if (!StringUtils.isEmpty(shipmentMaster.getConsignee().getCustomerName())) {

            daoAwb.createShipmentMasterCustomerInfo(shipmentMaster.getConsignee());

            // Add address info
            Optional<ShipmentMasterCustomerAddressInfo> consigneeAddress = Optional
                  .ofNullable(shipmentMaster.getConsignee().getAddress());
            if (consigneeAddress.isPresent()) {
               shipmentMaster.getConsignee().getAddress()
                     .setShipmentCustomerInfoId(shipmentMaster.getConsignee().getId());

               daoAwb.createShipmentMasterCustomerAddressInfo(shipmentMaster.getConsignee().getAddress());
            }

            if (consigneeAddress.isPresent()
                  && !CollectionUtils.isEmpty(shipmentMaster.getConsignee().getAddress().getContacts())) {
               shipmentMaster.getConsignee().getAddress().getContacts()
                     .forEach(t -> t.setId(shipmentMaster.getConsignee().getAddress().getShipmentCustomerInfoId()));
               daoAwb.createShipmentMasterCustomerContactInfo(shipmentMaster.getConsignee().getAddress().getContacts());
            }
         }
      }
   }

   public FWB awbNumberSet(FWB fwb) {
      String actualAwbNumber = fwb.getAwbNumber();
      fwb.setAwbSuffix(actualAwbNumber.substring(3));
      fwb.setAwbPrefix(actualAwbNumber.substring(0, 3));
      fwb.setMessageProcessedDate(LocalDateTime.now());
      fwb.setMessageStatus("NEW");
      return fwb;
   }

   public CustomerInfo customerTypeSet(CustomerInfo c, CustomerType type) {
      c.setCustomerType(type.getType());
      return c;
   }

   @Override
   @Transactional
   public void delete(FWB requestModel) throws CustomException {
		Boolean isDeliveryInitailted = checkisShipmentDelivered(requestModel);
		if (!isDeliveryInitailted) {
			dao.delete(requestModel);
		} else {
			requestModel.addError("data.shipment.delivered", null, ErrorType.ERROR);
			throw new CustomException();
		}
	}

   @Override
   public FWB flagReadStatus(FWB fwb) throws CustomException {
      final String read = "R";
      final String create = "C";
      if (Optional.ofNullable(fwb).isPresent()) {
         fwb.setFlagCRUD(read);
      }
      // SHC list
      if (Optional.ofNullable(fwb.getShcode()).isPresent()) {
         fwb.getShcode().stream().forEach(e -> {
            e.setFlagCRUD(read);
         });
      }
      // Flight Booking
      if (Optional.ofNullable(fwb.getFlightBooking()).isPresent()) {
         fwb.getFlightBooking().stream().forEach(e -> {
            e.setFlagCRUD(read);
         });
      }
      // Routing
      if (Optional.ofNullable(fwb.getRouting()).isPresent()) {
         fwb.getRouting().stream().forEach(e -> {
            e.setFlagCRUD(read);
         });
      }
      // Consignee
      if (Optional.ofNullable(fwb.getConsigneeInfo()).isPresent()) {
         fwb.getConsigneeInfo().setFlagCRUD(read);
         if (Optional.ofNullable(fwb.getConsigneeInfo().getCustomerAddressInfo()).isPresent()) {
            fwb.getConsigneeInfo().getCustomerAddressInfo().setFlagCRUD(read);
            if (Optional.ofNullable(fwb.getConsigneeInfo().getCustomerAddressInfo().getCustomerContactInfo())
                  .isPresent()) {
               fwb.getConsigneeInfo().getCustomerAddressInfo().getCustomerContactInfo().stream().forEach(e -> {
                  e.setFlagCRUD(read);
               });
            }
         }
      }
      // Shipper
      if (Optional.ofNullable(fwb.getShipperInfo()).isPresent()) {
         fwb.getShipperInfo().setFlagCRUD(read);
         if (Optional.ofNullable(fwb.getShipperInfo().getCustomerAddressInfo()).isPresent()) {
            fwb.getShipperInfo().getCustomerAddressInfo().setFlagCRUD(read);
            if (Optional.ofNullable(fwb.getShipperInfo().getCustomerAddressInfo().getCustomerContactInfo())
                  .isPresent()) {
               fwb.getShipperInfo().getCustomerAddressInfo().getCustomerContactInfo().stream().forEach(e -> {
                  e.setFlagCRUD(read);
               });
            }
         }
      }
      // Also Notify
      if (Optional.ofNullable(fwb.getAlsoNotify()).isPresent()) {
         fwb.getAlsoNotify().setFlagCRUD(read);
         if (Optional.ofNullable(fwb.getAlsoNotify().getCustomerAddressInfo()).isPresent()) {
            fwb.getAlsoNotify().getCustomerAddressInfo().setFlagCRUD(read);
            if (Optional.ofNullable(fwb.getAlsoNotify().getCustomerAddressInfo().getCustomerContactInfo())
                  .isPresent()) {
               fwb.getAlsoNotify().getCustomerAddressInfo().getCustomerContactInfo().stream().forEach(e -> {
                  e.setFlagCRUD(read);
               });
            }
         }
      }
      // Accouting Info
      if (Optional.ofNullable(fwb.getAccountingInfo()).isPresent()) {
         fwb.getAccountingInfo().stream().forEach(e -> {
            if (Optional.ofNullable(e.getInformationIdentifier()).isPresent()
                  || Optional.ofNullable(e.getAccountingInformation()).isPresent()) {
               e.setFlagCRUD(read);
            } else {
               e.setFlagCRUD(create);
            }
         });
      }
      // Charge Declaration
      if (Optional.ofNullable(fwb.getChargeDeclaration()).isPresent()) {
         fwb.getChargeDeclaration().setFlagCRUD(read);
      }

      // cdc
      if (Optional.ofNullable(fwb.getChargeDestCurrency()).isPresent()) {
         fwb.getChargeDeclaration().setFlagCRUD(read);
      }
      /* Temp */
      if (Optional.ofNullable(fwb.getSsrInfo()).isPresent()) {
         fwb.getSsrInfo().stream().forEach(e -> {
            if (Optional.ofNullable(e.getServiceRequestType()).isPresent()
                  || Optional.ofNullable(e.getServiceRequestcontent()).isPresent()) {
               e.setFlagCRUD(read);
            } else {
               e.setFlagCRUD(create);
            }
         });
      }
      if (Optional.ofNullable(fwb.getOsiInfo()).isPresent()) {
         fwb.getOsiInfo().stream().forEach(e -> {
            if (Optional.ofNullable(e.getServiceRequestType()).isPresent()
                  || Optional.ofNullable(e.getServiceRequestcontent()).isPresent()) {
               e.setFlagCRUD(read);
            } else {
               e.setFlagCRUD(create);
            }
         });
      }
      /* Temp */
      // Agent Info
      if (Optional.ofNullable(fwb.getAgentInfo()).isPresent()) {
         fwb.getAgentInfo().setFlagCRUD(read);
      }
      // Rate description and rate desc other info

      for (RateDescription rate : fwb.getRateDescription())
         if (Optional.ofNullable(fwb.getRateDescription()).isPresent()) {
            rate.setFlagCRUD(read);

            if (Optional.ofNullable(rate.getRateDescriptionOtherInfo()).isPresent()) {
               rate.getRateDescriptionOtherInfo().stream().forEach(e -> {
                  e.setFlagCRUD(read);
               });
            }
         }

      // Other charges
      if (Optional.ofNullable(fwb.getOtherCharges()).isPresent()) {
         fwb.getOtherCharges().stream().forEach(e -> {
            e.setFlagCRUD(read);
         });
      }
      // PPD
      if (Optional.ofNullable(fwb.getPpd()).isPresent()) {
         fwb.getPpd().setFlagCRUD(read);
      }
      // Col
      if (Optional.ofNullable(fwb.getPpd()).isPresent()) {
         fwb.getCol().setFlagCRUD(read);
      }

      // Shipment Reference info
      if (Optional.ofNullable(fwb.getShipmentReferenceInfor()).isPresent()) {
         fwb.getShipmentReferenceInfor().setFlagCRUD(read);
      }
      // Nominate Handling Party
      if (Optional.ofNullable(fwb.getFwbNominatedHandlingParty()).isPresent()) {
         fwb.getFwbNominatedHandlingParty().setFlagCRUD(read);
      }
      // Other participant info
      if (Optional.ofNullable(fwb.getOtherParticipantInfo()).isPresent()) {
         fwb.getOtherParticipantInfo().stream().forEach(e -> {
            e.setFlagCRUD(read);
         });
      }
      // Other customs Info
      if (Optional.ofNullable(fwb.getOtherCustomsInfo()).isPresent()) {
         fwb.getOtherCustomsInfo().stream().forEach(e -> {
            e.setFlagCRUD(read);
         });
      }
      return fwb;
   }

   @Override
   @Transactional
   public Routing fetchRoutingDetails(FetchRouting requestModel) throws CustomException {
      return dao.getRoutingData(requestModel);
   }

   @Override
   public FWBDetails fetchFWBDetailsForMobile(FWBDetails requestModel) throws CustomException {
      return dao.fetchFWBDetailsForMobile(requestModel);
   }

   @Override
   @Transactional
   public FWBDetails saveFWBDetailsForMobile(FWBDetails requestModel) throws CustomException {
      return dao.saveFWBDetailsForMobile(requestModel);
   }

	@Override
	public Boolean checkisShipmentDelivered(FWB requestModel) throws CustomException {
		return dao.isShipmentDeliveryInitiated(requestModel);
	}
}