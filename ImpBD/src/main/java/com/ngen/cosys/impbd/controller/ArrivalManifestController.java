package com.ngen.cosys.impbd.controller;

import java.util.List;

import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ngen.cosys.annotation.NgenCosysAppInfraAnnotation;
import com.ngen.cosys.annotation.PostRequest;
import com.ngen.cosys.framework.config.UtilitiesModelConfiguration;
import com.ngen.cosys.framework.constant.ErrorType;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.framework.model.BaseResponse;
import com.ngen.cosys.impbd.model.ArrivalManifestByFlightModel;
import com.ngen.cosys.impbd.model.ArrivalManifestBySegmentModel;
import com.ngen.cosys.impbd.model.ArrivalManifestShipmentInfoModel;
import com.ngen.cosys.impbd.model.ArrivalManifestUldModel;
import com.ngen.cosys.impbd.model.SearchArrivalManifestModel;
import com.ngen.cosys.impbd.service.ArrivalManifestService;
import com.ngen.cosys.validator.model.ReuseShipmentValidatorModel;
import com.ngen.cosys.validator.service.ReuseShipmentValidatorService;
import com.ngen.cosys.validators.ArrivalManifestValidationGroup;
import com.ngen.cosys.validators.UserAssignedCarrierValidation;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * This class is a takes care of services call for ArrivalManifest
 * Implementation.
 * 
 * @author NIIT Technologies Ltd
 *
 */

@NgenCosysAppInfraAnnotation(values = { ArrivalManifestValidationGroup.class })
public class ArrivalManifestController {

   @Autowired
   private UtilitiesModelConfiguration utilitiesModelConfiguration;

   @Autowired
   private ArrivalManifestService arrivalService;
   
   @Autowired
   ReuseShipmentValidatorService reuseShipmentValidatorService;
   
   
   private static final String SHIPMENT_NUMBER = "shipmentNumber";

   /**
    * REST api to fetch Arrival Shipments List
    * 
    * @return complete list of arrival manifest segment details,shipment
    *         details,uld details
    * @throws CustomException
    */
   @ApiOperation("fetch Arrival manifest list")
   @PostRequest(value = "api/config/arrivalManifest/fetchManifestDetails", method = RequestMethod.POST)
   public BaseResponse<ArrivalManifestByFlightModel> fetchArrivalManifestDetails(
         @ApiParam(value = "fetchArrivalManifest", required = true) @Validated(value = {
               ArrivalManifestValidationGroup.class,
               UserAssignedCarrierValidation.class }) @RequestBody SearchArrivalManifestModel searchArrivalManifestModel)
         throws CustomException {
      @SuppressWarnings("unchecked")
      BaseResponse<ArrivalManifestByFlightModel> arrivalShipmentDetails = utilitiesModelConfiguration
            .getBaseResponseInstance();
      arrivalShipmentDetails.setData(arrivalService.fetchShipmentDetails(searchArrivalManifestModel));
      return arrivalShipmentDetails;
   }

   /**
    * REST api to insert ULD/Shipment
    * 
    * @param list
    *           of ULD/Shipment Values Which Has to be inserted
    * @return list of ULD/Shipment available after successfully deleted
    * @throws CustomException
    */
   @ApiOperation("Create ULD Shipment")
   @PostRequest(value = "api/config/arrivalManifest/createULDShipment", method = RequestMethod.POST)
   public BaseResponse<ArrivalManifestByFlightModel> createULDShipment(
         @ApiParam(value = "createDimensionInfo", required = true) @Validated(value = ArrivalManifestValidationGroup.class) @RequestBody ArrivalManifestByFlightModel arrivalFlightDetails)
         throws CustomException {
      @SuppressWarnings("unchecked")
      BaseResponse<ArrivalManifestByFlightModel> arrivalShipmentDetails = utilitiesModelConfiguration
            .getBaseResponseInstance();
      for (ArrivalManifestBySegmentModel segmentDetails : arrivalFlightDetails.getSegments()) {
         segmentDetails.setFlightKey(arrivalFlightDetails.getFlightNumber());
         segmentDetails.setFlightDate(arrivalFlightDetails.getFlightDate());
         for (ArrivalManifestUldModel uldDetails : segmentDetails.getManifestedUlds()) {
            chechUldNumber(uldDetails);
            uldDetails.setFlightKey(arrivalFlightDetails.getFlightNumber());
            uldDetails.setFlightDate(arrivalFlightDetails.getFlightDate());
            for (ArrivalManifestShipmentInfoModel shipmentDetails : uldDetails.getShipments()) {
               chechShipmentNumber(shipmentDetails);
              if(shipmentDetails.getFlagCRUD().equalsIgnoreCase("C")) {
               boolean ifReused= checkReuseValidation(shipmentDetails);
               if(ifReused) {
                  	throw new CustomException("awb.cycl.exst.pls.purg", SHIPMENT_NUMBER, ErrorType.ERROR,
          					new String[] { shipmentDetails.getShipmentNumber() });
                  }
               }
               shipmentDetails.setUldNumber(uldDetails.getUldNumber());
               shipmentDetails.setFlightKey(arrivalFlightDetails.getFlightNumber());
               shipmentDetails.setFlightDate(arrivalFlightDetails.getFlightDate());
            }
         }

			for (ArrivalManifestShipmentInfoModel shipmentDetails : segmentDetails.getBulkShipments()) {
				chechShipmentNumber(shipmentDetails);
				if (shipmentDetails.getFlagCRUD().equalsIgnoreCase("C")) {
					//Boolean shipmentExited = checkExistingShipment(shipmentDetails);
					/*if (shipmentExited) {
						throw new CustomException("shipment.existed", SHIPMENT_NUMBER, ErrorType.ERROR,
								new String[] { shipmentDetails.getShipmentNumber() });
					}*/
					boolean ifReused = checkReuseValidation(shipmentDetails);
					if (ifReused) {
						throw new CustomException("awb.cycl.exst.pls.purg", SHIPMENT_NUMBER, ErrorType.ERROR,
								new String[] { shipmentDetails.getShipmentNumber() });
					}
				}

				shipmentDetails.setFlightKey(arrivalFlightDetails.getFlightNumber());
				shipmentDetails.setFlightDate(arrivalFlightDetails.getFlightDate());
				shipmentDetails.setUldNumber("Loose");
			}

      }
      try {
         arrivalService.createULDShipment(arrivalFlightDetails);
         arrivalService.createPreannounceMentData(arrivalFlightDetails);
         arrivalService.postProcessManifestInformation(arrivalFlightDetails);
         arrivalService.createThroughTransitAdvice(arrivalFlightDetails);
         arrivalShipmentDetails.setData(arrivalFlightDetails);
      } catch (CustomException e) {
         arrivalShipmentDetails.setData(arrivalFlightDetails);
         arrivalShipmentDetails.setSuccess(false);
      }
      return arrivalShipmentDetails;
   }
   
   /**
    * REST api to insert ULD/Shipment
    * 
    * @param list
    *           of ULD/Shipment Values Which Has to be inserted
    * @return list of ULD/Shipment available after successfully deleted
    * @throws CustomException
    */
   @ApiOperation("merge ffm Shipment")
   @PostRequest(value = "api/config/arrivalManifest/mergeFFMShipment", method = RequestMethod.POST)
   public BaseResponse<ArrivalManifestByFlightModel> mergeFFMShipment(
         @ApiParam(value = "createDimensionInfo", required = true) @RequestBody ArrivalManifestByFlightModel arrivalFlightDetails)
         throws CustomException {
      @SuppressWarnings("unchecked")
      BaseResponse<ArrivalManifestByFlightModel> arrivalShipmentDetails = utilitiesModelConfiguration
            .getBaseResponseInstance();
      for (ArrivalManifestBySegmentModel segmentDetails : arrivalFlightDetails.getSegments()) {
         segmentDetails.setFlightKey(arrivalFlightDetails.getFlightNumber());
         segmentDetails.setFlightDate(arrivalFlightDetails.getFlightDate());
         for (ArrivalManifestUldModel uldDetails : segmentDetails.getManifestedUlds()) {
            chechUldNumber(uldDetails);
            uldDetails.setFlightKey(arrivalFlightDetails.getFlightNumber());
            uldDetails.setFlightDate(arrivalFlightDetails.getFlightDate());
            for (ArrivalManifestShipmentInfoModel shipmentDetails : uldDetails.getShipments()) {
               chechShipmentNumber(shipmentDetails);
              if(shipmentDetails.getFlagCRUD().equalsIgnoreCase("C")) {
               boolean ifReused= checkReuseValidation(shipmentDetails);
               if(ifReused) {
                  	throw new CustomException("awb.cycl.exst.pls.purg", SHIPMENT_NUMBER, ErrorType.ERROR,
          					new String[] { shipmentDetails.getShipmentNumber() });
                  }
               }
               shipmentDetails.setUldNumber(uldDetails.getUldNumber());
               shipmentDetails.setFlightKey(arrivalFlightDetails.getFlightNumber());
               shipmentDetails.setFlightDate(arrivalFlightDetails.getFlightDate());
            }
         }

			for (ArrivalManifestShipmentInfoModel shipmentDetails : segmentDetails.getBulkShipments()) {
				chechShipmentNumber(shipmentDetails);
				if (shipmentDetails.getFlagCRUD().equalsIgnoreCase("C")) {
					Boolean shipmentExited = checkExistingShipment(shipmentDetails);
					if (shipmentExited) {
						throw new CustomException("shipment.existed", SHIPMENT_NUMBER, ErrorType.ERROR,
								new String[] { shipmentDetails.getShipmentNumber() });
					}
					boolean ifReused = checkReuseValidation(shipmentDetails);
					if (ifReused) {
						throw new CustomException("awb.cycl.exst.pls.purg", SHIPMENT_NUMBER, ErrorType.ERROR,
								new String[] { shipmentDetails.getShipmentNumber() });
					}
				}

				shipmentDetails.setFlightKey(arrivalFlightDetails.getFlightNumber());
				shipmentDetails.setFlightDate(arrivalFlightDetails.getFlightDate());
				shipmentDetails.setUldNumber("Loose");
			}

      }
      try {
         arrivalService.createULDShipment(arrivalFlightDetails);
         arrivalService.createPreannounceMentData(arrivalFlightDetails);
         arrivalService.postProcessManifestInformation(arrivalFlightDetails);
         arrivalService.createThroughTransitAdvice(arrivalFlightDetails);
         arrivalShipmentDetails.setData(arrivalFlightDetails);
      } catch (CustomException e) {
         arrivalShipmentDetails.setData(arrivalFlightDetails);
         arrivalShipmentDetails.setSuccess(false);
      }
      return arrivalShipmentDetails;
   }
   
	private boolean checkExistingShipment(ArrivalManifestShipmentInfoModel shipmentDetails) throws CustomException {
		boolean reuse = false;
		if (!ObjectUtils.isEmpty(shipmentDetails)) {
			reuse = arrivalService.isImportTransShipment(shipmentDetails);
		}
		return reuse;
	}

   private boolean checkReuseValidation(ArrivalManifestShipmentInfoModel shipmentDetails) {
	   ReuseShipmentValidatorModel reuseModel = new ReuseShipmentValidatorModel();
	   boolean reuse=false;
	   if(!ObjectUtils.isEmpty(shipmentDetails)) {
	  	  reuseModel.setAwbPiece(shipmentDetails.getTotalPieces());
	      reuseModel.setOrigin(shipmentDetails.getOrigin());
	      reuseModel.setDestination(shipmentDetails.getDestination());
	      reuseModel.setShipmentNumber(shipmentDetails.getShipmentNumber());
	      reuse=reuseShipmentValidatorService.isReusable(reuseModel);
          }
	return reuse;
  }

/**
    * REST api to delete ULD/Shipment List
    * 
    * @param list
    *           of ULD/Shipment List which have to be deleted
    * @return list of ULD/Shipment available after successfully deleted
    * @throws CustomException
    */
   @ApiOperation("Delete Shipment ULD/Shipment")
   @PostRequest(value = "api/config/arrivalManifest/deleteULDShipment", method = RequestMethod.POST)
   public BaseResponse<List<ArrivalManifestByFlightModel>> deleteULDShipment(
         @Valid @RequestBody ArrivalManifestUldModel arrivalShipmentInfo) throws CustomException {
      @SuppressWarnings("unchecked")
      BaseResponse<List<ArrivalManifestByFlightModel>> arrivalShipmentDetails = utilitiesModelConfiguration
            .getBaseResponseInstance();
      arrivalService.deleteULDShipment(arrivalShipmentInfo);
      arrivalShipmentDetails.setData(null);
      return arrivalShipmentDetails;
   }

   /**
    * REST api to insert Shipment Additional Information
    * 
    * @param list
    *           of ULD/Shipment Values Which Has to be inserted/Updated/Deleted
    * @return list of ULD/Shipment available after successfully deleted
    * @throws CustomException
    */
   @ApiOperation("Create Additional IOnformation")
   @PostRequest(value = "api/config/arrivalManifest/AdditionalInfo", method = RequestMethod.POST)
   public BaseResponse<List<ArrivalManifestByFlightModel>> createShipmentAdditionalInformation(
         @ApiParam(value = "createAdditionalInfo", required = true) @Valid @RequestBody ArrivalManifestShipmentInfoModel arrivalFlightDetails)
         throws CustomException {
      @SuppressWarnings("unchecked")
      BaseResponse<List<ArrivalManifestByFlightModel>> arrivalShipmentDetails = utilitiesModelConfiguration
            .getBaseResponseInstance();
      arrivalService.createAdditionalInfo(arrivalFlightDetails);
      arrivalShipmentDetails.setData(null);
      return arrivalShipmentDetails;
   }

   /**
    * REST api to check for Flight 12 hrs Check
    * 
    * @param Flight
    *           key and Flight Date
    * @return Boolean Variable stating that User Cn Proceed To Create Shipments are
    *         not.
    * @throws CustomException
    */
   @ApiOperation("checkValidFlight")
   @PostRequest(value = "api/config/arrivalManifest/FlightCheck", method = RequestMethod.POST)
   public BaseResponse<Boolean> checkValidFlight(
         @ApiParam(value = "checkFlight", required = true) @Valid @RequestBody ArrivalManifestByFlightModel arrivalFlightDetails)
         throws CustomException {
      @SuppressWarnings("unchecked")
      BaseResponse<Boolean> arrivalShipmentDetails = utilitiesModelConfiguration.getBaseResponseInstance();
      arrivalShipmentDetails.setData(arrivalService.checkValidFlight(arrivalFlightDetails));
      return arrivalShipmentDetails;
   }

   /**
    * REST api to check for Flight 12 hrs Check
    * 
    * @param Flight
    *           key and Flight Date
    * @return Boolean Variable stating that User Cn Proceed To Create Shipments are
    *         not.
    * @throws CustomException
    */
   @ApiOperation("fetchRoutingInformation")
   @PostRequest(value = "api/config/arrivalManifest/fetchRouting", method = RequestMethod.POST)
   public BaseResponse<ArrivalManifestShipmentInfoModel> fetchRouting(
         @ApiParam(value = "destination", required = true) @Valid @RequestBody ArrivalManifestByFlightModel arrivalFlightDetails)
         throws CustomException {
      @SuppressWarnings("unchecked")
      BaseResponse<ArrivalManifestShipmentInfoModel> arrivalShipmentDetails = utilitiesModelConfiguration
            .getBaseResponseInstance();
      arrivalShipmentDetails.setData(arrivalService.fetchRoutingDetail(arrivalFlightDetails));
      return arrivalShipmentDetails;
   }

   private void chechShipmentNumber(ArrivalManifestShipmentInfoModel shipmentDetails) throws CustomException {
      if (StringUtils.isEmpty(shipmentDetails.getShipmentNumber())) {
         throw new CustomException("g.shipment.number.mandatory", "shipmentNumber", ErrorType.ERROR);
      }
   }

   private void chechUldNumber(ArrivalManifestUldModel uldDetails) throws CustomException {
      if (StringUtils.isEmpty(uldDetails.getUldNumber())) {
         throw new CustomException("g.uld.number.mandatory", "ULDnumber", ErrorType.ERROR);
      }
   }

}