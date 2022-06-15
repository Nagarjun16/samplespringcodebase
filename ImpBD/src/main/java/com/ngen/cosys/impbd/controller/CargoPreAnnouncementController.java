/**
 * CargoPreAnnouncementController.java
 * 
 * Copyright
 * 
 * <PRE>
 * <IMG SRC = XX></IMG>
 * </PRE>
 *
 * Version Date Author Reason 1.0 31 December, 2017 NIIT -
 */
package com.ngen.cosys.impbd.controller;

import java.math.BigInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ngen.cosys.annotation.NgenCosysAppInfraAnnotation;
import com.ngen.cosys.framework.config.UtilitiesModelConfiguration;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.framework.model.BaseResponse;
import com.ngen.cosys.impbd.model.CargoPreAnnouncement;
import com.ngen.cosys.impbd.model.CargoPreAnnouncementBO;
import com.ngen.cosys.impbd.model.FlightDetails;
import com.ngen.cosys.impbd.service.CargoPreAnnouncementService;
import com.ngen.cosys.validators.ArrivalManifestValidationGroup;

import io.swagger.annotations.ApiOperation;

/**
 * This class is controller for CargoPreAnnouncement table while importing the
 * the ULD ,which will take care of Searching for ULDs with the Flight,date.
 * 
 * @author NIIT Technologies Ltd
 *
 */
@NgenCosysAppInfraAnnotation
public class CargoPreAnnouncementController {

   @Autowired
   private UtilitiesModelConfiguration utility;

   @Autowired
   private CargoPreAnnouncementService cargoPreAnnouncementService;

   @ApiOperation("get Corgo Pre-announcement Table Details")
   @RequestMapping(value = "/api/impbd/preannouncement/getCorgoPreannouncementTableDetails", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
   public BaseResponse<CargoPreAnnouncementBO> cargoPreAnnouncement(
         @RequestBody CargoPreAnnouncementBO cargoPreAnnouncementBO,
         @RequestHeader(value = "X-Auth-User-Id") String loggedInUser) throws CustomException {
      BaseResponse<CargoPreAnnouncementBO> cargoPreAnnouncementRes = utility.getBaseResponseInstance();
      cargoPreAnnouncementBO.setLoggedInUser(loggedInUser);
      cargoPreAnnouncementBO.setModifiedBy(loggedInUser);
      cargoPreAnnouncementBO.setCreatedBy(loggedInUser);
      cargoPreAnnouncementRes.setData(cargoPreAnnouncementService.cargoPreAnnouncement(cargoPreAnnouncementBO));
      cargoPreAnnouncementRes.setSuccess(true);
      return cargoPreAnnouncementRes;
   }

   @ApiOperation("Corgo Pre-announcement Table Details")
   @RequestMapping(value = "/api/impbd/preannouncement/insertUpdateCorgoPreannouncement", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
   public BaseResponse<CargoPreAnnouncementBO> insertCargoPreAnnouncement(
		 @Validated(value = ArrivalManifestValidationGroup.class) @RequestBody CargoPreAnnouncementBO cargoPreAnnouncementBO,
         @RequestHeader(value = "X-Auth-User-Id") String loggedInUser) throws CustomException {
      BaseResponse<CargoPreAnnouncementBO> cargoPreAnnouncementRes = utility.getBaseResponseInstance();
      cargoPreAnnouncementBO.setLoggedInUser(loggedInUser);
      cargoPreAnnouncementBO.setModifiedBy(loggedInUser);
      cargoPreAnnouncementBO.setCreatedBy(loggedInUser);
      cargoPreAnnouncementService.insertUpdateCargoPreAnnouncement(cargoPreAnnouncementBO);
      cargoPreAnnouncementRes.setSuccess(true);
      return cargoPreAnnouncementRes;
   }

   @ApiOperation("Corgo Pre-announcement Table Details")
   @RequestMapping(value = "/api/impbd/preannouncement/deleteCorgoPreannouncement", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
   public BaseResponse<CargoPreAnnouncementBO> deleteCargoPreAnnouncement(
         @RequestBody CargoPreAnnouncementBO cargoPreAnnouncementBO,
         @RequestHeader(value = "X-Auth-User-Id") String loggedInUser) throws CustomException {
      BaseResponse<CargoPreAnnouncementBO> cargoPreAnnouncementRes = utility.getBaseResponseInstance();
      cargoPreAnnouncementBO.setLoggedInUser(loggedInUser);
      cargoPreAnnouncementBO.setModifiedBy(loggedInUser);
      cargoPreAnnouncementBO.setCreatedBy(loggedInUser);
      cargoPreAnnouncementService.deleteCargoPreAnnouncement(cargoPreAnnouncementBO);
      cargoPreAnnouncementRes.setSuccess(true);
      return cargoPreAnnouncementRes;
   }

   @ApiOperation("Corgo Pre-announcement Table Details")
   @RequestMapping(value = "/api/impbd/preannouncement/finalizeUnfinalize", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
   public BaseResponse<CargoPreAnnouncementBO> finalizePA(@RequestBody CargoPreAnnouncementBO cargoPreAnnouncementBO,
         @RequestHeader(value = "X-Auth-User-Id") String loggedInUser) throws CustomException {
      BaseResponse<CargoPreAnnouncementBO> cargoPreAnnouncementRes = utility.getBaseResponseInstance();
      cargoPreAnnouncementBO.setLoggedInUser(loggedInUser);
      cargoPreAnnouncementBO.setModifiedBy(loggedInUser);
      cargoPreAnnouncementBO.setCreatedBy(loggedInUser);
      cargoPreAnnouncementService.finalizeAndunFinalize(cargoPreAnnouncementBO);
      cargoPreAnnouncementRes.setSuccess(true);
      return cargoPreAnnouncementRes;
   }

   @ApiOperation("get Corgo Pre-announcement Table Details")
   @RequestMapping(value = "/api/impbd/preannouncement/isFlightExist", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
   public BaseResponse<BigInteger> isFlightExist(@RequestBody FlightDetails flightDetails) throws CustomException {
      BaseResponse<BigInteger> cargoPreAnnouncementRes = utility.getBaseResponseInstance();
      cargoPreAnnouncementRes.setData(cargoPreAnnouncementService.isFlightExist(flightDetails));
      cargoPreAnnouncementRes.setSuccess(true);
      return cargoPreAnnouncementRes;
   }
   
   
   @ApiOperation("get Corgo Pre-announcement Table Details")
   @RequestMapping(value = "/api/impbd/preannouncement/updatebreakbulkIndicator", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
   public BaseResponse<BigInteger> updateBreaKBulkIndicator(@RequestBody CargoPreAnnouncement preannoucementInfo) throws CustomException {
      BaseResponse<BigInteger> cargoPreAnnouncementRes = utility.getBaseResponseInstance();
      cargoPreAnnouncementService.updateBreaKBulkIndicator(preannoucementInfo);
      cargoPreAnnouncementRes.setSuccess(true);
      return cargoPreAnnouncementRes;
   }

}