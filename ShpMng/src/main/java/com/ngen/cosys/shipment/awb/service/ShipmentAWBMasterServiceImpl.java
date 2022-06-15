/**
 * This is a service implementation component for Shipment Document on
 * AWB/CBV/UCB
 */
package com.ngen.cosys.shipment.awb.service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

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

import com.ngen.cosys.events.enums.EventStatus;
import com.ngen.cosys.events.enums.EventTypes;
import com.ngen.cosys.events.payload.CourierToCommercialCargoConversionEvent;
import com.ngen.cosys.events.payload.EMailEvent;
import com.ngen.cosys.events.payload.ShipmentStatusUpdateEvent;
import com.ngen.cosys.events.producer.CourierToCommercialCargoConversionStoreEventProducer;
import com.ngen.cosys.events.producer.SendEmailEventProducer;
import com.ngen.cosys.events.producer.ShipmentStatusUpdateEventProducer;
import com.ngen.cosys.events.template.model.TemplateBO;
import com.ngen.cosys.framework.constant.ErrorType;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.helper.DomesticInternationalHelper;
import com.ngen.cosys.helper.model.DomesticInternationalHelperRequest;
import com.ngen.cosys.multitenancy.feature.model.ApplicationFeatures;
import com.ngen.cosys.multitenancy.feature.support.FeatureUtility;
import com.ngen.cosys.multitenancy.utilities.MultiTenantUtility;
import com.ngen.cosys.routing.DeriveRoutingHelper;
import com.ngen.cosys.routing.RoutingRequestModel;
import com.ngen.cosys.routing.RoutingResponseModel;
import com.ngen.cosys.shipment.awb.dao.ShipmentAWBDocumentDao;
import com.ngen.cosys.shipment.awb.model.AWB;
import com.ngen.cosys.shipment.awb.model.ShipmentMasterCustomerAddressInfo;
import com.ngen.cosys.shipment.awb.model.ShipmentMasterCustomerInfo;
import com.ngen.cosys.shipment.awb.model.ShipmentMasterLocalAuthorityInfo;
import com.ngen.cosys.shipment.awb.model.ShipmentMasterRoutingInfo;
import com.ngen.cosys.shipment.awb.model.ShipmentMasterShc;
import com.ngen.cosys.shipment.awb.model.ShipmentOtherChargeInfo;
import com.ngen.cosys.shipment.awb.model.ShipmentRemarksModel;
import com.ngen.cosys.shipment.awb.validator.AWBDocumentBusinessValidator;
import com.ngen.cosys.shipment.enums.CustomerType;
import com.ngen.cosys.shipment.util.ShipmentUtility;
import com.ngen.cosys.timezone.util.TenantZoneTime;
import com.ngen.cosys.validator.dao.ShipmentValidationDao;
import com.ngen.cosys.validator.enums.ShipmentType;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
public class ShipmentAWBMasterServiceImpl implements ShipmentAWBMasterService {

   private static final Logger LOGGER = LoggerFactory.getLogger(ShipmentAWBMasterService.class);

   @Autowired
   private ShipmentAWBDocumentDao dao;

   @Autowired
   private ShipmentAWBDocumentDao shipmentAWBDocumentDAO;

   @Autowired
   @Qualifier("shipmentMasterValidator")
   private AWBDocumentBusinessValidator validator;

   @Autowired
   private SendEmailEventProducer publisher;

   @Autowired
   private DeriveRoutingHelper deriveRoutingHelper;

   @Autowired
   private ShipmentUtility shipmentUtility;

   @Autowired
   private ShipmentValidationDao shipmentValidationDao;
   
   @Autowired
   private DomesticInternationalHelper domIntHelper;
   
   @Autowired
   private CourierToCommercialCargoConversionStoreEventProducer courierToCommercialProducer;
   
   @Autowired
   private ShipmentStatusUpdateEventProducer shipmentStatusUpdateEventProducer;

   // Document Type Enum
   private enum DocumentType {
      ORIGINAL(Type.ORIGINAL),

      COPY(Type.COPY);

      public class Type {
         public static final String ORIGINAL = "ORIGINAL";
         public static final String COPY = "COPY";
      }

      private DocumentType(String status) {
         // Do nothing
      }

   }

   /*
    * (non-Javadoc)
    * 
    * @see
    * com.ngen.cosys.shipment.awb.service.ShipmentAWBMasterService#get(com.ngen.
    * cosys.shipment.awb.model.AWB)
    */
   @Override
   public AWB get(AWB awbDetails) throws CustomException {
      // Shipment Data
      AWB shipmentMaster = dao.getShipment(awbDetails);

      // Check for Shipment Existence
      Optional<AWB> o = Optional.ofNullable(shipmentMaster);
      if (o.isPresent()) {
         // Check for shipment type selected
         validateShipmentType(awbDetails, shipmentMaster);
      } else {
         // 1. If no shipment record found then try fetching the data from
         // ECC/Pre-Inbound Planning Sheet for Shipment Type which is COU/CBV
         // Check for shipment type and set the CBV/UCB as COU
         // AWB awbInfo
         shipmentMaster = this.dao.getShipmentInfoFromImportECC(awbDetails);

         // 2. Step 1 fails then fetch from Booking
         Optional<AWB> oShipmentMaster = Optional.ofNullable(shipmentMaster);
         if (!oShipmentMaster.isPresent()) {
            shipmentMaster = this.dao.getShipmentInfoFromBooking(awbDetails);
            //
            oShipmentMaster = Optional.ofNullable(shipmentMaster);

            // 3. Step 1 and 2 fails then set the defaults
            if (!oShipmentMaster.isPresent()) {
               shipmentMaster = new AWB();
               shipmentMaster.setWeightUnitCode("K");
            }
         }

         // Set the export flag
         if (MultiTenantUtility.isTenantCityOrAirport(shipmentMaster.getOrigin())) {
            shipmentMaster.setIsExport(Boolean.TRUE);
         }

         // Derive part shipment
         Boolean partShipment = dao.isPartShipment(shipmentMaster);
         shipmentMaster.setPartShipment(partShipment);

         // Derive SVC shipment
         Boolean svcShipment = dao.isSVCShipment(shipmentMaster);
         shipmentMaster.setSvc(svcShipment);

         // Check for shipment type and set the CBV/UCB as COU
         if (ShipmentType.Type.CBV.equalsIgnoreCase(awbDetails.getShipmentType())
               || ShipmentType.Type.UCB.equalsIgnoreCase(awbDetails.getShipmentType())) {
            ShipmentMasterShc shc = new ShipmentMasterShc();
            shc.setSpecialHandlingCode("COU");

            // SHCS
            List<ShipmentMasterShc> shcs = new ArrayList<>();
            shcs.add(shc);

            shipmentMaster.setShcs(shcs);
         }

         // Get the carrier code based on incoming/outgoing/airline prefix
         shipmentMaster.setCarrierCode(dao.getCarrierCodeBasedOnAwbPrefix(awbDetails));
      }

      // Check for nullability
      o = Optional.ofNullable(shipmentMaster);
      performNullAction(shipmentMaster, o);

      // Populate the default IXX and EXX
      if (o.isPresent()) {
         // IXX customer id
         shipmentMaster.setDirectConsigneeCustomerId(this.dao.getDirectConsigneeCustomerId());
         // EXX customer id
         shipmentMaster.setDirectShipperCustomerId(this.dao.getDirectShipperCustomerId());
      }

      // Instantiate an dummy empty object for customer i.e. Shipper/Consignee since
      // the page is dependent on such response object
      if (ObjectUtils.isEmpty(shipmentMaster.getShipper())) {
         shipmentMaster.setShipper(new ShipmentMasterCustomerInfo());
      }
      
      //setting the Dom/Int using DomIntHelper
      if(shipmentMaster!=null && shipmentMaster.getOrigin()!=null && shipmentMaster.getDestination()!=null)
		{
		DomesticInternationalHelperRequest request=new DomesticInternationalHelperRequest();
		request.setOrigin(shipmentMaster.getOrigin());
		request.setDestination(shipmentMaster.getDestination());
		shipmentMaster.setHandledByDOMINT(domIntHelper.getDOMINTHandling(request));
		}
      
    // If Domestic & Feature is Disabled handledBy cannot be changed
    		if(shipmentMaster.getHandledByDOMINT()!=null && shipmentMaster.getHandledByDOMINT().equalsIgnoreCase("DOM") 
    				&& !FeatureUtility.isFeatureEnabled(ApplicationFeatures.Gen.DomesticHAWBHandling.class)) {
    			shipmentMaster.setHandlingChangeFlag(false);
    		}
      
      
      return shipmentMaster;
   }

   private void validateShipmentType(AWB awbDetails, AWB shipmentMaster) throws CustomException {
	   
	   
	   if (FeatureUtility.isFeatureEnabled(ApplicationFeatures.Awb.CourierToCommercial.class)) {
		   if (!awbDetails.getShipmentType().equalsIgnoreCase(shipmentMaster.getShipmentType())) {
			   if(shipmentMaster.getShipmentType().equalsIgnoreCase(ShipmentType.Type.CBV)){
		         throw new CustomException("data.shipment.type.cbv", "shipmentNumber", ErrorType.ERROR,
		               new Object[] { shipmentMaster.getShipmentType() });
		      }
			   if(shipmentMaster.getShipmentType().equalsIgnoreCase(ShipmentType.Type.AWB)){
			         throw new CustomException("data.shipment.type.awb", "shipmentNumber", ErrorType.ERROR,
			               new Object[] { shipmentMaster.getShipmentType() });
			      }
		   }
		   
	      }
	   
	   
	   
      if (!awbDetails.getShipmentType().equalsIgnoreCase(shipmentMaster.getShipmentType())) {
         throw new CustomException("data.shipment.type.not.matching", "shipmentNumber", ErrorType.ERROR,
               new Object[] { shipmentMaster.getShipmentType() });
      }
   }

   private void performNullAction(final AWB shipmentMaster, Optional<AWB> o) throws CustomException {
      BigDecimal ccFee = dao.getccFee();
      BigDecimal minccFee = dao.getMinccFee();
      if (!StringUtils.isEmpty(ccFee)) {
         shipmentMaster.setCcFeeprecentage(ccFee);
      }
      if (!StringUtils.isEmpty(minccFee)) {
         shipmentMaster.setMinccFee(minccFee);
      }
      if (!o.isPresent()) {
         throw new CustomException("data.awb.not.found", "shipmentNumber", ErrorType.ERROR);
      }
   }

   @Override
   @Transactional(rollbackFor = { Exception.class, RuntimeException.class })
   public AWB createShipment(AWB shipmentMaster) throws CustomException {
     
	   LocalDateTime startTime = TenantZoneTime.getZoneDateTime(LocalDateTime.now(), shipmentMaster.getTenantId());
      // Validate the data
      validator.validate(shipmentMaster);

      BigDecimal exchangeRate = shipmentAWBDocumentDAO.fetchExchangeRate(shipmentMaster);
      if (StringUtils.isEmpty(exchangeRate) && (!StringUtils.isEmpty(shipmentMaster.getOtherChargeInfo().getCurrency()))
            && MultiTenantUtility.isTenantCityOrAirport(shipmentMaster.getDestination())) {
         shipmentMaster.addError("billing.no.exchg.rate.available","ExchangeRate", ErrorType.ERROR);
         return shipmentMaster;
      }
      
      //validating chargeable weight
      if(shipmentMaster.getWeight()!=null && shipmentMaster.getChargeableWeight()!=null) {
      int chargeWeightCheckData = shipmentMaster.getWeight()
				.compareTo(shipmentMaster.getChargeableWeight());
		if (chargeWeightCheckData == 1) {
			throw new CustomException("chargeable.lesser.then.weight", null,
					ErrorType.ERROR);
		}
      }
      
      // Document Received/Copy date/time based on Document Type and Document Date
      shipmentMaster.setDocRecieved(Boolean.FALSE);
      shipmentMaster.setCopyAwb(Boolean.FALSE);
      shipmentMaster.setDocumentReceivedOn(null);
      shipmentMaster.setPhotoCopyReceivedOn(null);

		if (!StringUtils.isEmpty(shipmentMaster.getDocumentType())) {
			switch (shipmentMaster.getDocumentType()) {
			case DocumentType.Type.ORIGINAL:
				shipmentMaster.setDocRecieved(Boolean.TRUE);
				if(!ObjectUtils.isEmpty(shipmentMaster.getDocumentDate())){
				 shipmentMaster.setDocumentReceivedOn(shipmentMaster.getDocumentDate());
				}else {
				 //for import Doc Arrival Date as FlightDate
				 shipmentMaster.setDocumentReceivedOn(shipmentMaster.getFlightDate());
				}
				break;
			case DocumentType.Type.COPY:
				shipmentMaster.setCopyAwb(Boolean.TRUE);
				if(!ObjectUtils.isEmpty(shipmentMaster.getDocumentDate())){
				 shipmentMaster.setPhotoCopyReceivedOn(shipmentMaster.getDocumentDate());
				}else {
				 //for import Doc Arrival Date as FlightDate 
				 shipmentMaster.setPhotoCopyReceivedOn(shipmentMaster.getFlightDate());
				}
				break;
			default:
				break;
			}
		}
      

      if (shipmentMaster.getPouchRecieved().compareTo(shipmentMaster.getOldPouchRecieved()) != 0) {
         shipmentMaster.setDocumentPouchReceivedOn(startTime);
      }

      // Check for Origin/Destination
      if (!shipmentMaster.getOrigin().equalsIgnoreCase(shipmentMaster.getOrigin())) {
         // Derive part shipment
         Boolean partShipment = dao.isPartShipment(shipmentMaster);
         shipmentMaster.setPartShipment(partShipment);
      }
      Boolean partShipment = dao.isPartShipment(shipmentMaster);
      if (partShipment) {
         shipmentMaster.setPartShipment(partShipment);
      }

      // Check SVC has been selected from screen other wise update the default SVC
      // which is marked from Booking
      if (!shipmentMaster.getSvc()) {
         Boolean svcShipment = dao.isSVCShipment(shipmentMaster);
         shipmentMaster.setSvc(svcShipment);
      }

      // Add shipment master
      dao.createShipment(shipmentMaster);

      // Execute only for import
      if (!MultiTenantUtility.isTenantCityOrAirport(shipmentMaster.getOrigin()) && !shipmentMaster.getIsExport()) {
         dao.createShipmentVerification(shipmentMaster);
      }

      // Add routing info
      this.createRoutingInfo(shipmentMaster);

      // Add SHC
      this.createSHC(shipmentMaster);

      // add other charges info
      this.createOtherChargeDelcaration(shipmentMaster);

      // Consignee
      this.createConsignee(shipmentMaster);
      // Also Notify
      this.createAlsoNotify(shipmentMaster);

      // Oversease Consignee
      Optional<ShipmentMasterCustomerInfo> oConsignee = Optional.ofNullable(shipmentMaster.getConsignee());
      if (oConsignee.isPresent() && !StringUtils.isEmpty(oConsignee.get().getCustomerName())
            && oConsignee.get().getOverseasCustomer()) {
         dao.createOverseaseConsignee(shipmentMaster);
      }

      // Shipper
      this.createShipper(shipmentMaster);

      // save local Authpority only in case of Export and Transhipment
      if (!MultiTenantUtility.isTenantCityOrAirport(shipmentMaster.getDestination())) {
         this.saveShipmentMasterLocalAuthorityInfo(shipmentMaster);
      }

      // OSI Remarks
      this.createOSIRemarks(shipmentMaster);

      // SSR Remarks
      this.createSSRRemarks(shipmentMaster);
      // SSR Remarks
      this.createGeneralRemarks(shipmentMaster);

      // Send email to consignee
      this.sendEmailConsinee(shipmentMaster);

      if (!StringUtils.isEmpty(shipmentMaster.getOvcdReasonCode())) {
         this.createIrregularityandTracing(shipmentMaster);
      }

      // Update the origin/destination to other sources like
      // FWB/FHL/EAcceptance/Booking
      this.dao.updateOriginDestinationToOtherSources(shipmentMaster);

      LocalDateTime endTime = TenantZoneTime.getZoneDateTime(LocalDateTime.now(), shipmentMaster.getTenantId());
      LOGGER.warn("AWB Document Service - SAVE :: Start Time : {}, End Time : {}", startTime, endTime);
      startTime = TenantZoneTime.getZoneDateTime(LocalDateTime.now(), shipmentMaster.getTenantId());
      // Submit data to customs
      if (!shipmentMaster.getIsExport()) {
         shipmentUtility.submitToCustoms(shipmentMaster);
      }
      endTime = TenantZoneTime.getZoneDateTime(LocalDateTime.now(), shipmentMaster.getTenantId());
      //get RCF StatusUpdate event Pieces and BreakDownPices and FoundPieces
      if (!shipmentMaster.getIsExport()) {
       BigInteger  statusUpdateEventPieces = dao.getStatusUpdateEventPieces(shipmentMaster);
       shipmentMaster.setRcfStatusUpdateEventPieces(statusUpdateEventPieces);
       AWB breakDownFoundPieces=dao.getBreakDownAndFoundPieces(shipmentMaster);
       if(!ObjectUtils.isEmpty(breakDownFoundPieces)) {
	       shipmentMaster.setBreakDownPieces(breakDownFoundPieces.getBreakDownPieces());
	       shipmentMaster.setBreakDownWeight(breakDownFoundPieces.getBreakDownWeight());
	       shipmentMaster.setFoundPieces(breakDownFoundPieces.getFoundPieces());
       }
      }
      LOGGER.warn("AWB Document Customs Submission - SAVE :: Start Time : {}, End Time : {}", startTime, endTime);
      return shipmentMaster;
   }

   private void createIrregularityandTracing(AWB shipmentMaster) throws CustomException {
      // Check both old pieces OR not null
      if (shipmentMaster.getOldPieces() != null && shipmentMaster.getOldWeight() != null) {
         BigInteger differPieces = shipmentMaster.getPieces().subtract(shipmentMaster.getOldPieces());
         BigDecimal differWeight = shipmentMaster.getWeight().subtract(shipmentMaster.getOldWeight());

         // Create an irregularity/tracing in case of OVCD
         if ((differPieces != null && differWeight != null)
               && (differPieces.intValue() > 0 || differWeight.intValue() > 0)) {
            shipmentMaster.setIrregularityPieces(differPieces);
            shipmentMaster.setIrregularityWeight(differWeight);

            // Create Irregularity
            dao.createIrregulaity(shipmentMaster);

            // Create Tracing
            dao.createTracing(shipmentMaster);
         }
      }
   }

   private void sendEmailConsinee(AWB shipmentMaster) {
      if (MultiTenantUtility.isTenantCityOrAirport(shipmentMaster.getDestination())) {
         EMailEvent emailEvent = new EMailEvent();
         String subject = "Yours shipment reached to destination AWB Number <" + shipmentMaster.getShipmentNumber()
               + ">";
         emailEvent.setMailSubject(subject);
         emailEvent.setMailBody(subject);
         emailEvent.setMailTo(shipmentMaster.getConsignee().getContactEmail());

         // Template Params
         TemplateBO template = new TemplateBO();
         template.setTemplateName("CONSIGNEE EMAIL");

         Map<String, String> mapParameters = new HashMap<>();
         mapParameters.put("ShipmentNumber", shipmentMaster.getShipmentNumber());
         template.setTemplateParams(mapParameters);

         emailEvent.setTemplate(template);

         // Publish event
         publisher.publish(emailEvent);
      }
   }

   private void createRoutingInfo(AWB shipmentMaster) throws CustomException {
      // Add Routing Info
      if (!CollectionUtils.isEmpty(shipmentMaster.getRouting())) {
         shipmentMaster.getRouting().forEach(t -> t.setShipmentId(shipmentMaster.getShipmentId()));
         dao.createShipmentMasterRoutingInfo(shipmentMaster.getRouting());
      }
   }

   private void createSHC(AWB shipmentMaster) throws CustomException {
      // Add SHC
      if (!CollectionUtils.isEmpty(shipmentMaster.getShcs())) {
         shipmentMaster.getShcs().forEach(t -> t.setShipmentId(shipmentMaster.getShipmentId()));
         dao.createShipmentMasterShc(shipmentMaster.getShcs());
      } else {
         dao.deleteShipmentMasterShc(shipmentMaster);
      }
   }

   private void createOtherChargeDelcaration(AWB shipmentMaster) throws CustomException {
      Optional<ShipmentOtherChargeInfo> otherChargeInfo = Optional.ofNullable(shipmentMaster.getOtherChargeInfo());
      shipmentMaster.getOtherChargeInfo().setShipmentId(shipmentMaster.getShipmentId());
      if (otherChargeInfo.isPresent()) {
         shipmentMaster.getOtherChargeInfo().setCustomsOrigin(shipmentMaster.getCustomsOriginCode());
         dao.createShipmentOtherChargeInfo(shipmentMaster.getOtherChargeInfo());
      }
   }

   private void createOSIRemarks(AWB shipmentMaster) throws CustomException {
      // Remarks
      for (ShipmentRemarksModel t : shipmentMaster.getOsiRemarksList()) {
         if (!StringUtils.isEmpty(t.getShipmentRemarks())) {
            t.setShipmentId(shipmentMaster.getShipmentId());
            t.setShipmentNumber(shipmentMaster.getShipmentNumber());
            t.setShipmentdate(shipmentMaster.getShipmentdate());
            t.setRemarkType("OSI");
            t.setShipmentType(shipmentMaster.getShipmentType());
            this.createShipmentRemarks(t);
         }
      }
   }

   private void createSSRRemarks(AWB shipmentMaster) throws CustomException {
      // Remarks
      for (ShipmentRemarksModel t : shipmentMaster.getSsrRemarksList()) {
         if (!StringUtils.isEmpty(t.getShipmentRemarks())) {
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

   private void createGeneralRemarks(AWB shipmentMaster) throws CustomException {
      // Remarks
      for (ShipmentRemarksModel t : shipmentMaster.getGeneralRemarks()) {
         if (!StringUtils.isEmpty(t.getShipmentRemarks())) {
            t.setShipmentNumber(shipmentMaster.getShipmentNumber());
            t.setShipmentId(shipmentMaster.getShipmentId());
            t.setShipmentdate(shipmentMaster.getShipmentdate());
            t.setRemarkType("GEN");
            t.setShipmentType(shipmentMaster.getShipmentType());
            t.setShipmentRemarksId(t.getShipmentRemarksId());
            this.createShipmentRemarks(t);
         }
      }
   }

   private void createShipper(AWB shipmentMaster) throws CustomException {
      // Add Shipper
      Optional<ShipmentMasterCustomerInfo> shipper = Optional.ofNullable(shipmentMaster.getShipper());
      if (shipper.isPresent() && !StringUtils.isEmpty(shipper.get().getCustomerCode())
            || shipper.isPresent() && !StringUtils.isEmpty(shipper.get().getCustomerName())) {
         shipmentMaster.getShipper().setShipmentId(shipmentMaster.getShipmentId());
         shipmentMaster.getShipper().setCustomerType(CustomerType.SHIPPER.getType());
         dao.createShipmentMasterCustomerInfo(shipmentMaster.getShipper());

         Optional<ShipmentMasterCustomerAddressInfo> shipperAddress = Optional
               .ofNullable(shipmentMaster.getShipper().getAddress());
         if (shipperAddress.isPresent()) {

            shipmentMaster.getShipper().getAddress().setShipmentCustomerInfoId(shipmentMaster.getShipper().getId());
            dao.createShipmentMasterCustomerAddressInfo(shipmentMaster.getShipper().getAddress());
         }

         if (shipperAddress.isPresent()
               && !CollectionUtils.isEmpty(shipmentMaster.getShipper().getAddress().getContacts())) {
            shipmentMaster.getShipper().getAddress().getContacts()
                  .forEach(t -> t.setId(shipmentMaster.getShipper().getAddress().getShipmentCustomerInfoId()));
            dao.createShipmentMasterCustomerContactInfo(shipmentMaster.getShipper().getAddress().getContacts());
         }
      }
      // On removing shipper details should delete all shipper specific details
      if (shipper.isPresent() && StringUtils.isEmpty(shipper.get().getCustomerName())) {
         String shipmentCustomerInfoId = dao.getShipmentCustomerInfoIdForShipper(shipmentMaster);
         if (Objects.nonNull(shipmentCustomerInfoId)) {
            String shipmentCustomerAddInfoId = dao.getShipmentCustomerAddInfoId(shipmentCustomerInfoId);
            if (!StringUtils.isEmpty(shipmentCustomerAddInfoId)) {
               dao.deleteShipmentMasterCustomerContactInfo(shipmentCustomerAddInfoId);
               dao.deleteShipmentMasterCustomerAddressInfo(shipmentCustomerInfoId);
               dao.deleteShipmentMasterCustomerInfoForShipper(shipmentMaster);
            }
         }
      }
   }

   private void createShipmentRemarks(ShipmentRemarksModel shipmentRemarksModel) throws CustomException {
      if (shipmentRemarksModel.getShipmentRemarks() != null) {
         // Create a remarks
         this.dao.create(shipmentRemarksModel);
      }
   }

   private void saveShipmentMasterLocalAuthorityInfo(AWB shipmentMaster) throws CustomException {
      if (Optional.ofNullable(shipmentMaster.getLocalAuthority()).isPresent()) {
         for (ShipmentMasterLocalAuthorityInfo lar : shipmentMaster.getLocalAuthority()) {
            lar.setShipmentId(shipmentMaster.getShipmentId());
            if (StringUtils.isEmpty(lar.getFlagCRUD())) {
               lar.setFlagCRUD("C");
            }
            dao.insertShipmentMasterLocalAuthorityDetails(lar);

         }
      }
   }

   /**
    * . Method to create consignee for an shipment
    * 
    * @param shipmentMaster
    * @throws CustomException
    */
   private void createConsignee(AWB shipmentMaster) throws CustomException {
      // Add consignee
      Optional<ShipmentMasterCustomerInfo> consignee = Optional.ofNullable(shipmentMaster.getConsignee());
		if (consignee.isPresent() && !StringUtils.isEmpty(consignee.get().getCustomerCode())
				|| consignee.isPresent() && !StringUtils.isEmpty(consignee.get().getCustomerName())) {
			shipmentMaster.getConsignee().setShipmentId(shipmentMaster.getShipmentId());
			shipmentMaster.getConsignee().setCustomerType(CustomerType.CONSIGNEE.getType());
			// if AppointedAgent is null
			if (ObjectUtils.isEmpty(shipmentMaster.getConsignee().getAppointedAgent())
					&& !StringUtils.isEmpty(shipmentMaster.getConsignee().getAppointedAgentCode())) {
				BigInteger appointedAgentId = dao.getAppointedAgentId(shipmentMaster.getConsignee());
				if (ObjectUtils.isEmpty(appointedAgentId)) {
					throw new CustomException("awb.apptd.agt.not.exist", "error", ErrorType.ERROR);
				}
				shipmentMaster.getConsignee().setAppointedAgent(appointedAgentId);
			}

			dao.createShipmentMasterCustomerInfo(shipmentMaster.getConsignee());

			// Add address info
			Optional<ShipmentMasterCustomerAddressInfo> consigneeAddress = Optional
					.ofNullable(shipmentMaster.getConsignee().getAddress());
			if (consigneeAddress.isPresent()) {
				shipmentMaster.getConsignee().getAddress()
						.setShipmentCustomerInfoId(shipmentMaster.getConsignee().getId());
				dao.createShipmentMasterCustomerAddressInfo(shipmentMaster.getConsignee().getAddress());
			}

			// Add contact info
			if (consigneeAddress.isPresent()
					&& !CollectionUtils.isEmpty(shipmentMaster.getConsignee().getAddress().getContacts())) {
				shipmentMaster.getConsignee().getAddress().getContacts()
						.forEach(t -> t.setId(shipmentMaster.getConsignee().getAddress().getShipmentCustomerInfoId()));
				dao.createShipmentMasterCustomerContactInfo(shipmentMaster.getConsignee().getAddress().getContacts());
			}			
			
		}
		
	  if(consignee.isPresent() && !CollectionUtils.isEmpty(consignee.get().getIvrsContactInfo()) 
			  && !StringUtils.isEmpty(consignee.get().getIvrsContactInfo().get(0).getCustomerType())) {
		  shipmentMaster.getConsignee().getIvrsContactInfo()
			.forEach(t -> t.setShipmentId(shipmentMaster.getShipmentId())); 
	       dao.createIvrsCustomerContactInfo(shipmentMaster.getConsignee().getIvrsContactInfo());
	  }

      // On removing consignee details should delete all consignee specific details
      if (consignee.isPresent() && StringUtils.isEmpty(consignee.get().getCustomerName())) {
         String shipmentCustomerInfoId = dao.getShipmentCustomerInfoIdForConsignee(shipmentMaster);
         if (Objects.nonNull(shipmentCustomerInfoId)) {
            String shipmentCustomerAddInfoId = dao.getShipmentCustomerAddInfoId(shipmentCustomerInfoId);
            if (!StringUtils.isEmpty(shipmentCustomerAddInfoId)) {
               dao.deleteShipmentMasterCustomerContactInfo(shipmentCustomerAddInfoId);
               dao.deleteShipmentMasterCustomerAddressInfo(shipmentCustomerInfoId);
               dao.deleteShipmentMasterCustomerInfoForConsignee(shipmentMaster);
            }
         }
      }

   }

   private void createAlsoNotify(AWB shipmentMaster) throws CustomException {
      // Add consignee
      Optional<ShipmentMasterCustomerInfo> alsoNotify = Optional.ofNullable(shipmentMaster.getAlsoNotify());
      if (alsoNotify.isPresent() && !StringUtils.isEmpty(alsoNotify.get().getCustomerCode())
            || alsoNotify.isPresent() && !StringUtils.isEmpty(alsoNotify.get().getCustomerName())) {
         shipmentMaster.getAlsoNotify().setShipmentId(shipmentMaster.getShipmentId());
         shipmentMaster.getAlsoNotify().setCustomerType(CustomerType.ALSO_NOTIFY.getType());
         dao.createShipmentMasterCustomerInfo(shipmentMaster.getAlsoNotify());

         // Add address info
         Optional<ShipmentMasterCustomerAddressInfo> alsoNotifyAddress = Optional
               .ofNullable(shipmentMaster.getAlsoNotify().getAddress());
         if (alsoNotifyAddress.isPresent()) {
            shipmentMaster.getAlsoNotify().getAddress()
                  .setShipmentCustomerInfoId(shipmentMaster.getAlsoNotify().getId());
            dao.createShipmentMasterCustomerAddressInfo(shipmentMaster.getAlsoNotify().getAddress());
         }

         // Add contact info
         if (alsoNotifyAddress.isPresent()
               && !CollectionUtils.isEmpty(shipmentMaster.getAlsoNotify().getAddress().getContacts())) {
            shipmentMaster.getAlsoNotify().getAddress().getContacts()
                  .forEach(t -> t.setId(shipmentMaster.getAlsoNotify().getAddress().getShipmentCustomerInfoId()));
            dao.createShipmentMasterCustomerContactInfo(shipmentMaster.getAlsoNotify().getAddress().getContacts());
         }
      }

   }

   @Override
   public List<RoutingResponseModel> routeDetails(RoutingRequestModel requestModel) throws CustomException {
      // Get the routing info
      List<RoutingResponseModel> routingInfo = deriveRoutingHelper.getRoutes(requestModel);

      if (!MultiTenantUtility.isTenantCityOrAirport(requestModel.getShipmentOrigin())) {
         // Check whether the AWB prefix exists
         boolean isAwbPrefixExists = shipmentValidationDao
               .isValidAwbPrefixForCarrier(requestModel.getShipmentNumber().substring(0, 3), requestModel.getCarrier());

         if (!CollectionUtils.isEmpty(routingInfo)) {
            for (RoutingResponseModel r : routingInfo) {
               // If this is an valid AWB prefix only then set the carrier code what is derived
               // from routing logic for non-GHA station
               if (!isAwbPrefixExists && !MultiTenantUtility.isTenantCityOrAirport(r.getNextDestination())) {
            	   List<ShipmentMasterRoutingInfo> routingList=dao.getRoutingInfo(requestModel);
            	   if(!CollectionUtils.isEmpty(routingList) && routingList.size() > 1) {
            		 r.setNextCarrier(routingList.get(1).getCarrier());
            	   }else {
            	     r.setNextCarrier(null);
            	   }
               }
            }
         }
      }
      return routingInfo;
   }

   @Override
   public AWB getAcceptanceInfo(AWB awbDetails) throws CustomException {
      return dao.getAcceptanceInfo(awbDetails);
   }

   @Override
   public ShipmentMasterCustomerInfo getEmailInfo(ShipmentMasterCustomerInfo awbDetails) throws CustomException {
      return dao.getEmailInfo(awbDetails);
   }

   @Override
   public ShipmentMasterCustomerInfo getFWBConsigneeInfo(AWB awbDetails) throws CustomException {
      ShipmentMasterCustomerInfo consigneedata = dao.getFWBConsigneeInfo(awbDetails);
      ShipmentMasterCustomerInfo agent = new ShipmentMasterCustomerInfo();
      List<ShipmentMasterCustomerInfo> consigneeAgentData = dao.getFWBConsigneeAgentInfo(awbDetails);
      if (!CollectionUtils.isEmpty(consigneeAgentData)) {
         if (consigneeAgentData.size() > 1) {
            agent.setNotifyPartyName("YES");
         }
         if (!ObjectUtils.isEmpty(awbDetails.getConsignee())) {
            BigInteger id = awbDetails.getConsignee().getAppointedAgent();
            String agtCode = awbDetails.getConsignee().getAppointedAgentCode();
            if (id != null) {
               consigneeAgentData = consigneeAgentData.stream().filter(str -> str.getCustomerId().compareTo(id) == 0)
                     .collect(Collectors.toList());
            } else if (agtCode != null) {
               consigneeAgentData = consigneeAgentData.stream()
                     .filter(str -> str.getCustomerCode().equalsIgnoreCase(agtCode)).collect(Collectors.toList());
            }
         }
         agent.setCustomerId(consigneeAgentData.get(0).getCustomerId());
         agent.setCustomerCode(consigneeAgentData.get(0).getCustomerCode());
      }
      if (consigneedata != null && consigneedata.getCustomerType() != null) {
         agent.setCustomerType(consigneedata.getCustomerType());
      }
      return agent;
   }

   @Override
   public List<ShipmentMasterCustomerInfo> getFWBConsigneeAgentInfoOnSelect(ShipmentMasterCustomerInfo awbDetails)
         throws CustomException {
      return dao.sqlGetFWBConsigneeAgentInfoOnSelect(awbDetails);
   }

   @Override
   public AWB getExchangeRate(AWB awbDetails) throws CustomException {
      BigDecimal exchangeRate = shipmentAWBDocumentDAO.fetchExchangeRate(awbDetails);
      if (StringUtils.isEmpty(exchangeRate) && (!StringUtils.isEmpty(awbDetails.getOtherChargeInfo().getCurrency()))) {
         awbDetails.addError(
               "No exchange rate available for this currency code. Check flight details date. Inform DM/Account section",
               "ExchangeRate", ErrorType.ERROR);

         return awbDetails;
      } else {
         awbDetails.getOtherChargeInfo().setExchangeRate(exchangeRate);
         return awbDetails;
      }
   }

   @Override
   public ShipmentMasterCustomerAddressInfo getAllAppointedAgents(ShipmentMasterCustomerInfo request)
         throws CustomException {
      return dao.getAllAppointedAgents(request);
   }

   @Override
   public AWB updateShipmentType(AWB requestModel) throws CustomException {

      // check PO Initiated/shipmentLoaded
      this.checkShipmentType(requestModel);
      //if couToCommercial feature  Enabled then need to update AWB as Shipment type
      if (FeatureUtility.isFeatureEnabled(ApplicationFeatures.Awb.CourierToCommercial.class)) {
    	  requestModel.setShipmentType(ShipmentType.Type.AWB);
    	  requestModel.setShipmentTypeConvertFrom(ShipmentType.Type.CBV);
    	  
    	    //delete Cou shcs
          dao.deleteCOUShc(requestModel);
      }

      // update Shipment type
      dao.updateShipmentType(requestModel);
    
      CourierToCommercialCargoConversionEvent courierToCommercialCargoConversionEvent = new CourierToCommercialCargoConversionEvent();
      courierToCommercialCargoConversionEvent.setFlightId(requestModel.getFlightId());
      courierToCommercialCargoConversionEvent.setShipmentId(requestModel.getShipmentId());
      courierToCommercialCargoConversionEvent.setShipmentNumber(requestModel.getShipmentNumber());
      courierToCommercialCargoConversionEvent.setEventName(EventTypes.Names.COURIER_TO_COMMERCIAL_CARGO_CONVERSION);
      courierToCommercialCargoConversionEvent.setFunction("AWB Document");
      courierToCommercialCargoConversionEvent.setPieces(requestModel.getActualPieces());
      courierToCommercialCargoConversionEvent.setWeight(requestModel.getActualWeight());
      courierToCommercialCargoConversionEvent.setStatus(EventStatus.Type.NEW);
      courierToCommercialCargoConversionEvent.setCreatedBy(requestModel.getCreatedBy());
      courierToCommercialCargoConversionEvent.setCreatedOn(requestModel.getCreatedOn());
      
      courierToCommercialProducer.publish(courierToCommercialCargoConversionEvent);;
      
      
      if(!MultiTenantUtility.isTenantCityOrAirport(requestModel.getOrigin())) {
	      if (ShipmentType.Type.CBV.equalsIgnoreCase(requestModel.getShipmentType())) {
	         // delete Customs Data if exist
	         dao.deleteCustomsData(requestModel);
	      } else if ( ShipmentType.Type.AWB.equalsIgnoreCase(requestModel.getShipmentType())) {
	         // inserting Data to Customs if couToCommercial feature is Disabled
	    		if (!FeatureUtility.isFeatureEnabled(ApplicationFeatures.Awb.CourierToCommercial.class)) {
	             shipmentUtility.submitToCustoms(requestModel);
	    		}
	      }
      }
      return requestModel;
   }

   public void checkShipmentType(AWB requestModel) throws CustomException {

      BigInteger poCount = dao.isPoGeneratedForShipment(requestModel);
      if (poCount.intValue() > 0) {
         requestModel.addError("awb.po.issued.shp.typ.cant.upd", "error", ErrorType.ERROR);
         throw new CustomException(requestModel.getMessageList());
      }
      BigInteger loadedCount = dao.isShipmentLoaded(requestModel);
      if (loadedCount.intValue() > 0) {
         requestModel.addError("awb.loaded.shp.typ.cant.upd", "error", ErrorType.ERROR);
         throw new CustomException(requestModel.getMessageList());
      }

   }
   
	public void publishShipmentEvent(AWB awbDetails, String messageType) throws CustomException {
		int isInternationalShipment=1;
		// Check the flight completed status
		if (!Objects.isNull(awbDetails.getFlightId())) {
			int isFlightCompleted = dao.isFlightCompleted(awbDetails.getFlightId());
			
			if(Objects.nonNull(awbDetails.getShipmentId())) {
				isInternationalShipment=dao.isInternationalShipment(awbDetails.getShipmentId());
			}
			
			if (isFlightCompleted > 0 && isInternationalShipment > 0) {
				ShipmentStatusUpdateEvent event = new ShipmentStatusUpdateEvent();
				event.setFlightId(awbDetails.getFlightId());
				event.setShipmentNumber(awbDetails.getShipmentNumberForAuditTrail());
				event.setCreatedBy(awbDetails.getCreatedBy());
				event.setCreatedOn(LocalDateTime.now());
				event.setSource("AWB");
				event.setFunction("AWB Document");
				event.setConsigneeName(awbDetails.getConsignee().getCustomerName());
				event.setConsigneeAddress(awbDetails.getConsignee().getAddress().getPlace());
				event.setClearingAgentCode(awbDetails.getConsignee().getAppointedAgentCode());
				if(Objects.nonNull(awbDetails.getConsignee().getAppointedAgentCode())) {
					String clearingAgentName=getClearingAgentName(awbDetails.getConsignee().getAppointedAgentCode());
					if(Objects.nonNull(clearingAgentName)) {
						event.setClearingAgentName(clearingAgentName);
					}
				}
				event.setMessageType(messageType);
				shipmentStatusUpdateEventProducer.publish(event);
			}
		}

	}
   public Map<String, BigInteger> createRequestMap(BigInteger flightId, BigInteger shipmentId) {
				Map<String, BigInteger> requestMap = new HashMap<>();
				requestMap.put("flightId", flightId);
				requestMap.put("shipmentId", shipmentId);
				return requestMap;
   }
   
   public String getMessageType(AWB awbDetails) throws CustomException{
	   String messageType=null;
	   if(Objects.nonNull(awbDetails.getFlightId()) && Objects.nonNull(awbDetails.getShipmentId())) {
		   messageType = dao.getMessageType(this.createRequestMap(awbDetails.getFlightId(), awbDetails.getShipmentId()));
	   }
	   return messageType;
   }
   
   public String getClearingAgentName(String appointedAgent) throws CustomException {
	   String clearingAgentName=null;
	   clearingAgentName = dao.getClearingAgentName(appointedAgent);
	   return clearingAgentName;
   }
   
}