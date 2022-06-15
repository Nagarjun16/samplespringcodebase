/**
 * 
 * AssignContainerToDestinationController.java
 * 
 * Copyright <PRE><IMG SRC = XX></IMG></PRE>
 *
 * Version      Date       Author      Reason
 * v1         14 April, 2017   NIIT      -
 */
package com.ngen.cosys.shipment.mail.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ngen.cosys.annotation.NgenCosysAppInfraAnnotation;
import com.ngen.cosys.framework.config.UtilitiesModelConfiguration;
import com.ngen.cosys.framework.constant.ErrorType;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.framework.model.BaseResponse;
import com.ngen.cosys.shipment.mail.model.AssignContainerToDestination;
import com.ngen.cosys.shipment.mail.model.SearchAssignToContainerToDestinationDetails;
import com.ngen.cosys.shipment.mail.service.AssignContainerToDestinationService;

import io.swagger.annotations.ApiOperation;

@NgenCosysAppInfraAnnotation
public class AssignContainerToDestinationController {

   @Autowired
   private UtilitiesModelConfiguration utility;

   @Autowired
   private AssignContainerToDestinationService assignToContainerDestinationService;

   /**
    * @param request
    * @return
    * @throws CustomException
    */
   @ApiOperation("Searching Assign Container To Destination Details")
   @RequestMapping(value = "/api/shipment/mail/searchAssignContainerDetails", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
   public BaseResponse<AssignContainerToDestination> searchAssignContainerDetails(
         @Valid @RequestBody SearchAssignToContainerToDestinationDetails request) throws CustomException {
      BaseResponse<AssignContainerToDestination> response = utility.getBaseResponseInstance();
      AssignContainerToDestination data = assignToContainerDestinationService.searchAssignContainerDetails(request);
      if (StringUtils.isEmpty(data)) {
         response.setSuccess(false);
         throw new CustomException("DISSCH003", "assignContainerToDestination", ErrorType.ERROR);
      } else {
         response.setSuccess(true);
         response.setData(data);
      }
      return response;
   }

   /**
    * @param request
    * @return
    * @throws CustomException
    */
   @ApiOperation("Save Assign Container To Destination Details")
   @RequestMapping(value = "/api/shipment/mail/saveAssignContainerDetails", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
   public BaseResponse<AssignContainerToDestination> saveAssignContainerDetails(
         @Valid @RequestBody AssignContainerToDestination request) throws CustomException {
      BaseResponse<AssignContainerToDestination> response = utility.getBaseResponseInstance();
      response.setData(assignToContainerDestinationService.saveAssignContainerDetails(request));
      return response;
   }

   /**
    * @param request
    * @return
    * @throws CustomException
    */
   @ApiOperation("Delete Assign Container To Destination Details")
   @RequestMapping(value = "/api/shipment/mail/deleteAssignContainerDetails", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
   public BaseResponse<AssignContainerToDestination> deleteAssignContainerDetails(
         @Valid @RequestBody SearchAssignToContainerToDestinationDetails request) throws CustomException {
      BaseResponse<AssignContainerToDestination> response = utility.getBaseResponseInstance();
      response.setData(assignToContainerDestinationService.deleteAssignContainerDetails(request));
      return response;
   }
}