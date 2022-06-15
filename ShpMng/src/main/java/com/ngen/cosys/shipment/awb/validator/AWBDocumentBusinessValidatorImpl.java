/**
 * This is a validator for validating Shipment document on AWB/CBV/UCB
 */
package com.ngen.cosys.shipment.awb.validator;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.ngen.cosys.framework.constant.ErrorType;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.framework.model.BaseBO;
import com.ngen.cosys.multitenancy.feature.model.ApplicationFeatures;
import com.ngen.cosys.multitenancy.feature.support.FeatureUtility;
import com.ngen.cosys.multitenancy.utilities.MultiTenantUtility;
import com.ngen.cosys.shipment.awb.dao.ShipmentAWBDocumentDao;
import com.ngen.cosys.shipment.awb.model.AWB;
import com.ngen.cosys.shipment.awb.model.ShipmentMasterLocalAuthorityDetails;
import com.ngen.cosys.shipment.awb.model.ShipmentMasterLocalAuthorityInfo;
import com.ngen.cosys.shipment.awb.model.ShipmentMasterRoutingInfo;
import com.ngen.cosys.shipment.awb.model.ShipmentMasterShc;
import com.ngen.cosys.shipment.awb.model.ShipmentOtherChargeInfo;
import com.ngen.cosys.shipment.awb.model.ShipmentRemarksModel;
import com.ngen.cosys.shipment.validators.SaveAWBDocument;
import com.ngen.cosys.timezone.util.TenantZoneTime;
import com.ngen.cosys.validator.dao.MasterValidationDao;

@Component("shipmentMasterValidator")
public class AWBDocumentBusinessValidatorImpl implements AWBDocumentBusinessValidator {

   @Autowired
   private ShipmentAWBDocumentDao dao;

   @Autowired
   private MasterValidationDao masterDao;

   @Autowired
   private Validator validator;

   @Override
   public AWB validate(BaseBO baseModel) throws CustomException {

      AWB requestModel = (AWB) baseModel;

      // Validate AWB Document Model
      Set<ConstraintViolation<AWB>> violations = this.validator.validate(requestModel, SaveAWBDocument.class);
      for (final ConstraintViolation<?> violation : violations) {
         StringBuilder sbPath = new StringBuilder();
         sbPath.append(violation.getRootBeanClass().getName()).append(".").append(violation.getPropertyPath());
         requestModel.addError(violation.getMessage(), sbPath.toString(), ErrorType.ERROR);
      }

      // Throw exception if message list is not empty
      if (!CollectionUtils.isEmpty(requestModel.getMessageList())) {
         throw new CustomException(requestModel.getMessageList());
      }

      /**
       * Check the consignee information for import shipment
       */
      if (MultiTenantUtility.isTenantCityOrAirport(requestModel.getDestination())) {
         this.checkConsigneeInformation(requestModel);
      }
		AWB lastUpdatedInfo = this.dao.getLastModifiedInfo(requestModel);
		String[] placeHolders = new String[1];
		
		if (!ObjectUtils.isEmpty(lastUpdatedInfo) && ObjectUtils.isEmpty(requestModel.getLastUpdatedOn())) {
			placeHolders[0] = lastUpdatedInfo.getLastUpdatedUserCode();
			throw new CustomException("g.user.updated.data.search.again", "error", ErrorType.ERROR, placeHolders);
		}
		
		if (!ObjectUtils.isEmpty(lastUpdatedInfo)) {
			LocalDateTime lastUpdatedOn = TenantZoneTime.getZoneDateTime(requestModel.getLastUpdatedOn(), requestModel.getTenantId());
			if (lastUpdatedInfo.getLastUpdatedOn().isAfter(lastUpdatedOn)) {
				placeHolders[0] = lastUpdatedInfo.getLastUpdatedUserCode();
				throw new CustomException("g.user.updated.data.search.again", "error", ErrorType.ERROR, placeHolders);
			}

		}
		
		//Bug 15720
		this.validateDeliveredPicesWithAwbPieces(requestModel);
      
      if ((!StringUtils.isEmpty(requestModel.getFlightKey()) && !StringUtils.isEmpty(requestModel.getFlightDate()))) {
         BigInteger flightId = this.dao.getInboundFlightId(requestModel);

         if (StringUtils.isEmpty(flightId)) {
            throw new CustomException("incoming.flight.arrivalCheck", "flightKey", "E");
         }

         // deleting customs data for the given old flight if flight info modified in AWBDocument
         // comparing with requestModel flight flightId from Query
         if (!StringUtils.isEmpty(requestModel.getFlightId()) && flightId.compareTo(requestModel.getFlightId()) != 0) {
            dao.deleteImpShipmentVerification(requestModel);
            dao.deleteCustomsData(requestModel);
         }
         // if origin not SIN set flightId if AWB Document is Creating newly
         if (!MultiTenantUtility.isTenantCityOrAirport(requestModel.getOrigin())) {
            requestModel.setFlightId(flightId);
         }
      }

      // Check for SHC
      this.checkForDuplicateSHC(requestModel);
      this.checkForValidSHC(requestModel);

      // check for first routing
      this.checkForRoutingInfoMandatoryData(requestModel);

      // Duplicate check for OSI
      this.checkForDuplicateOSI(requestModel.getOsiRemarksList());

      // Duplicate check for SSR
      this.checkForDuplicateSSR(requestModel.getSsrRemarksList());

      // Check for Onward Routing Info
      this.checkForRoutingInfoWithOnwardsDestination(requestModel.getRouting());

      // Valid Currency
      if (!ObjectUtils.isEmpty(requestModel.getOtherChargeInfo())) {
         this.checkValidCurrency(requestModel.getOtherChargeInfo());
      }

      if(FeatureUtility.isFeatureEnabled(ApplicationFeatures.Customs.LocalAuthority.class)) {
      // Validate Local Authority Exemption Code if applicable
      this.validateLocalAuthorityInfo(requestModel.getLocalAuthority());
      }
      
      // To check Export shipment Document Acceptance Completed or not
      this.checkDocumentAcceptance(requestModel);
      
      return requestModel;
   }

   private void checkDocumentAcceptance(AWB requestModel) throws CustomException {
   
	   if (MultiTenantUtility.isTenantCityOrAirport(requestModel.getOrigin())) {
		   Boolean acceptanceFlag = dao.checkDocumentAcceptance(requestModel);
		   if(!acceptanceFlag) {
			   throw new CustomException("cargo.acceptance.prompt.error.message", "", ErrorType.ERROR);
		   }
	   }
	   
   }
   
   /**
    * Checks The Appointed Agent Code Is Valid Or Not, If not Then not to allow
    * 
    * @param requestModel
    * @throws CustomException
    */
   private void checkConsigneeInformation(AWB requestModel) throws CustomException {
      if (!ObjectUtils.isEmpty(requestModel.getConsignee())) {
         if (!StringUtils.isEmpty(requestModel.getConsignee().getCustomerName())) {
            if (!StringUtils.isEmpty(requestModel.getConsignee().getAppointedAgentCode())
                  && !"IXX".equalsIgnoreCase(requestModel.getConsignee().getAppointedAgentCode())) {
               Boolean validAppointedAgentCode = dao.isValidAppointedAgentCode(requestModel.getConsignee());
               if (!validAppointedAgentCode) {
                  requestModel.getConsignee().addError("data.consignee.appointed.agent.invalid", "address",
                        ErrorType.ERROR);
               }
            }
            // Throw the error
            if (!CollectionUtils.isEmpty(requestModel.getConsignee().getMessageList())) {
               throw new CustomException(requestModel.getConsignee().getMessageList());
            }
         }
      }
   }

   /**
    * Method to check duplicate SHC
    * 
    * @param shc
    * @throws CustomException
    */
   private AWB checkForDuplicateSHC(AWB requestModel) throws CustomException {
      Set<String> shcSet = new HashSet<>();
      if (!CollectionUtils.isEmpty(requestModel.getShcs())) {
         for (ShipmentMasterShc shc : requestModel.getShcs()) {
            if (shcSet.contains(shc.getSpecialHandlingCode())) {
               throw new CustomException("duplicate.shc.found", "", ErrorType.APP);
            } else {
               shcSet.add(shc.getSpecialHandlingCode());
            }
         }
      }
      return requestModel;
   }

   private AWB checkForValidSHC(AWB requestModel) throws CustomException {
      if (!CollectionUtils.isEmpty(requestModel.getShcs())) {
         for (ShipmentMasterShc shc : requestModel.getShcs()) {
            // Parameter Map
            Map<String, Object> parameterMap = new HashMap<>();
            parameterMap.put("code", shc.getSpecialHandlingCode());
            parameterMap.put("shipmentNumber", requestModel.getShipmentNumber());
            Boolean validShc = this.masterDao.isValidSHC(parameterMap);
            if (!validShc) {
               throw new CustomException("SHCCODE011", "", ErrorType.APP);
            }
         }
      }
      return requestModel;
   }

   /**
    * Method to check duplicate OSI line
    * 
    * @param ssrOsiInfo
    * @throws CustomException
    */
   private void checkForDuplicateOSI(List<ShipmentRemarksModel> osiInfo) throws CustomException {
      Set<String> osiLineSet = new HashSet<>();
      if (!CollectionUtils.isEmpty(osiInfo)) {
         for (ShipmentRemarksModel t : osiInfo) {
            if ("OSI".equalsIgnoreCase(t.getRemarkType()) && !StringUtils.isEmpty(t.getShipmentRemarks())
                  && osiLineSet.contains(t.getShipmentRemarks())) {
               throw new CustomException("awb.osi.duplicate.found", "", ErrorType.APP);
            } else {
               osiLineSet.add(t.getShipmentRemarks());
            }
         }
      }
   }

   /**
    * Method to check duplicate SSR line
    * 
    * @param ssrOsiInfo
    */
   private void checkForDuplicateSSR(List<ShipmentRemarksModel> ssrInfo) throws CustomException {
      Set<String> ssrLineSet = new HashSet<>();
      if (!CollectionUtils.isEmpty(ssrInfo)) {
         for (ShipmentRemarksModel t : ssrInfo) {
            if ("SSR".equalsIgnoreCase(t.getRemarkType()) && !StringUtils.isEmpty(t.getShipmentRemarks())
                  && ssrLineSet.contains(t.getShipmentRemarks())) {
               throw new CustomException("awb.ssr.duplicate.found", "", ErrorType.APP);
            } else {
               ssrLineSet.add(t.getShipmentRemarks());
            }
         }
      }
   }

   /**
    * Method to check duplicate OCI line
    * 
    * @param otherCustomsInfo
    * @throws CustomException
    */
   private void checkForRoutingInfoWithOnwardsDestination(List<ShipmentMasterRoutingInfo> routingList)
         throws CustomException {
      Set<String> routingSet = new HashSet<>();
      for (ShipmentMasterRoutingInfo routing : routingList) {
         if (Optional.ofNullable(routing.getFromPoint()).isPresent() && routing.getFromPoint().length() > 0) {
            String dest = routing.getFromPoint();
            if (routingSet.contains(dest)) {
               throw new CustomException("data.awb.destination.not.valid", "", ErrorType.APP);
            } else {
               routingSet.add(dest);
            }
         }
         if (Optional.ofNullable(routing.getFromPoint()).isPresent()) {
            Boolean validAirportCityCode = masterDao.isValidAirportCityCode(routing.getFromPoint());
            if (!validAirportCityCode) {
               throw new CustomException("invalid.shipment.origin.destination", "", ErrorType.APP);
            }
         }
         if (Optional.ofNullable(routing.getCarrier()).isPresent()) {
            Boolean validCarrier = masterDao.isCarrierExist(routing.getCarrier());
            if (!validCarrier) {
               throw new CustomException("CAR001", "", ErrorType.APP);
            }
         }
      }
   }

   private void checkForRoutingInfoMandatoryData(AWB requestModel) throws CustomException {
		if (!CollectionUtils.isEmpty(requestModel.getRouting())) {
			List<ShipmentMasterRoutingInfo> routingList = requestModel.getRouting();
			boolean routingFound = false;
			for (int i = 0; i < routingList.size(); i++) {
				if (!StringUtils.isEmpty(routingList.get(i).getCarrier())
						|| !StringUtils.isEmpty(routingList.get(i).getFromPoint())) {
					routingFound = true;
					break;
				}
			}

			// If no routing then stop processing
			if (!routingFound) {
				throw new CustomException("data.awb.routing.valid", "", ErrorType.APP);
			}

			for (ShipmentMasterRoutingInfo t : routingList) {
				if (MultiTenantUtility.isTranshipment(requestModel.getOrigin(), requestModel.getDestination())
						&& (!StringUtils.isEmpty(t.getFromPoint())
								&& MultiTenantUtility.isTenantCityOrAirport(t.getFromPoint())
								&& StringUtils.isEmpty(t.getCarrier()))) {
					throw new CustomException("awb.routing.carrier.mandatory", "carrier", ErrorType.APP,
							new String[] { t.getFromPoint() });
				}
			}
			
			boolean isTenantFound = false;
			for (ShipmentMasterRoutingInfo t : routingList) {
				if ((!StringUtils.isEmpty(t.getFromPoint())
						&& MultiTenantUtility.isTenantCityOrAirport(t.getFromPoint()))) {
					isTenantFound = true;
					break;
				}
			}
			// Check if origin is tenant then turn off the indicator
			if (!isTenantFound && MultiTenantUtility.isTenantCityOrAirport(requestModel.getOrigin())) {
				isTenantFound = true;
			}
			
			if(!isTenantFound) {
				throw new CustomException("awb.routing.tenant.mandatory", "fromPoint", ErrorType.APP,
						new String[] { MultiTenantUtility.getTenantIdFromContext() });
			}
			
		}
   }

   private void checkValidCurrency(ShipmentOtherChargeInfo requestModel) throws CustomException {
      if (!StringUtils.isEmpty(requestModel.getCurrency())) {
         Boolean validCurrency = dao.checkValidCurrency(requestModel);
         if (!validCurrency) {
            throw new CustomException("Invalid Currency", "", ErrorType.APP);
         }
      }
   }

   /*
    * Validate Local Authority Exemption Code if present
    */
   private void validateLocalAuthorityInfo(List<ShipmentMasterLocalAuthorityInfo> localAuthority)
         throws CustomException {
      if (!CollectionUtils.isEmpty(localAuthority)) {
         for (ShipmentMasterLocalAuthorityInfo t : localAuthority) {
            if ("EC".equalsIgnoreCase(t.getType()) && !CollectionUtils.isEmpty(t.getDetails())) {
               for (ShipmentMasterLocalAuthorityDetails d : t.getDetails()) {
                  if (!StringUtils.isEmpty(d.getReferenceNumber())) {
                     boolean isValidExemptionCode = this.dao.isValidExemptionCode(d);
                     if (!isValidExemptionCode) {
                        throw new CustomException("data.invalid.local.authority.exemption.code", "", ErrorType.APP);
                     }
                  }
               }
            }
         }
      }
   }
   
   private void validateDeliveredPicesWithAwbPieces(AWB requestData) throws CustomException{
	   
	   AWB deliveredInfo=dao.getDeliveredPieces(requestData);
	   if(!ObjectUtils.isEmpty(deliveredInfo) 
			   && deliveredInfo.getDeliveredPieces().intValue() > 0 
			   && deliveredInfo.getDeliveredWeight().intValue() > 0) {
		   
		   //1. After shipment deliver, user cannto reduce the total AWB pieces/weight compare to delivered pieces/weight.
		   if(requestData.getPieces().compareTo(deliveredInfo.getDeliveredPieces()) == -1 
				   || requestData.getWeight().compareTo(deliveredInfo.getDeliveredWeight())==-1) {
			   throw new CustomException("AWB_DOC_DEL_PCSWGT", "", ErrorType.APP);
		   }
		   //2. User cannot increase only pieces with out increasing weight.
		   if(requestData.getPieces().compareTo(deliveredInfo.getDeliveredPieces()) == 1 
				   && requestData.getWeight().compareTo(deliveredInfo.getDeliveredWeight()) != 1) {
			   throw new CustomException("AWB_DOC_DEL_WGT", "", ErrorType.APP);
		   }
		  
	   }
	   
	   
   }

}