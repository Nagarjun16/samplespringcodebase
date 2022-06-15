/**
 * 
 * MaintainServiceProviderController.java
 * 
 * Copyright
 * 
 * <PRE>
 * <IMG SRC = XX></IMG>
 * </PRE>
 *
 * Version Date Author Reason 1.0 31 May, 2018 NIIT -
 */
package com.ngen.cosys.impbd.service.provider.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.ngen.cosys.annotation.NgenCosysAppInfraAnnotation;
import com.ngen.cosys.framework.config.UtilitiesModelConfiguration;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.framework.model.BaseResponse;
import com.ngen.cosys.impbd.service.MaintainSeriviceProviderGroup;
import com.ngen.cosys.impbd.service.provider.model.ServiceProviderModel;
import com.ngen.cosys.impbd.service.provider.service.MaintainServiceProviderService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * This controller takes care of the maintaining Service Provider list.
 * 
 * @author NIIT Technologies Ltd
 *
 */
@NgenCosysAppInfraAnnotation
public class MaintainServiceProviderController {
   private static Logger LOGGER = LoggerFactory.getLogger(MaintainServiceProviderController.class);
   @Autowired
   private MaintainServiceProviderService serviceProviderService;
   @Autowired
   private UtilitiesModelConfiguration utilitiesModelConfiguration;

   /**
    * REST api to fetch Maintain Service Provider List
    * 
    * @return complete list of Maintain Service Provider details,flight
    *         details,email details
    * @throws CustomException
    */
   @ApiOperation("Searching for Maintain Service Provider List")
   @PostMapping(path = "/api/import/serviceProvider/getAllServiceProvider")
   public BaseResponse<List<ServiceProviderModel>> getAllServiceProvider(
         @ApiParam(value = "Request Object", required = true) @Valid @RequestBody ServiceProviderModel serviceProviderModel)
         throws CustomException {
      BaseResponse<List<ServiceProviderModel>> serviceProviderList = utilitiesModelConfiguration
            .getBaseResponseInstance();
      serviceProviderList.setData(serviceProviderService.getAllServiceProvider(serviceProviderModel));
      LOGGER.debug("Log message at DEBUG level from getting Maintain Service Provider Details");
      return serviceProviderList;
   }

   /**
    * REST api to insert Maintain Service Provider
    * 
    * @param list
    *           of Maintain Service Provider Values Which Has to be inserted
    * @return list of Maintain Service Provider available after successfully
    * @throws CustomException
    */
   @ApiOperation("Create for Maintain Service Provider")
   @PostMapping(path = "/api/import/serviceProvider/createServiceProvider")
   public BaseResponse<List<ServiceProviderModel>> createServiceProvider(
         @Validated(value = MaintainSeriviceProviderGroup.class) @RequestBody List<ServiceProviderModel> serviceProviderModel)
         throws CustomException {
      BaseResponse<List<ServiceProviderModel>> createServiceProvider = utilitiesModelConfiguration
            .getBaseResponseInstance();
      createServiceProvider.setData(serviceProviderService.createServiceProvider(serviceProviderModel));
      LOGGER.debug("Log message at DEBUG level from insert Maintain Service Provider Details");
      return createServiceProvider;
   }

   /**
    * REST api to Update Maintain Service Provider
    * 
    * @param list
    *           of Maintain Service Provider Values Which Has to be Updated
    * @return list of Maintain Service Provider available after successfully
    * @throws CustomException
    */
   @ApiOperation("Create for Maintain Service Provider")
   @PostMapping(path = "/api/import/serviceProvider/updateServiceProvider")
   public BaseResponse<List<ServiceProviderModel>> updateServiceProvider(
         @ApiParam(value = "updateService Object", required = true) @Valid @RequestBody List<ServiceProviderModel> serviceProviderModel)
         throws CustomException {
      BaseResponse<List<ServiceProviderModel>> updateServiceProvider = utilitiesModelConfiguration
            .getBaseResponseInstance();
      updateServiceProvider.setData(serviceProviderService.updateServiceProvider(serviceProviderModel));
      LOGGER.debug("Log message at DEBUG level from update Maintain Service Provider Details");
      return updateServiceProvider;
   }

   /**
    * REST api to Delete Maintain Service Provider
    * 
    * @param list
    *           of Maintain Service Provider Values Which Has to be Deleted
    * @return list of Maintain Service Provider available after successfully
    * @throws CustomException
    */
   @ApiOperation("Delete for Maintain Service Provider")
   @PostMapping(path = "/api/import/serviceProvider/deleteServiceProvider")
   public BaseResponse<List<ServiceProviderModel>> deleteServiceProvider(
         @ApiParam(value = "deleteService Object", required = true) @Valid @RequestBody List<ServiceProviderModel> serviceProviderModel)
         throws CustomException {
      BaseResponse<List<ServiceProviderModel>> deleteServiceList = utilitiesModelConfiguration
            .getBaseResponseInstance();
      deleteServiceList.setData(serviceProviderService.deleteServiceProvider(serviceProviderModel));
      LOGGER.debug("Log message at DEBUG level from update Maintain Service Provider Details");
      return deleteServiceList;
   }

   /**
    * REST api to edit Maintain Service Provider
    * 
    * @param list
    *           of Maintain Service Provider Values Which Has to be Deleted
    * @return list of Maintain Service Provider available after successfully
    * @throws CustomException
    */
   @ApiOperation("Edit for Maintain Service Provider")
   // @RequestMapping(value = "/api/import/inboundtrolly/editInboundTrolly", method
   // = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces =
   // MediaType.APPLICATION_JSON_VALUE)
   @PostMapping(path = "/api/import/serviceProvider/editServiceProvider")
   public BaseResponse<List<ServiceProviderModel>> editServiceProvider(
         @ApiParam(value = "Edit Service Provider", required = true) @Valid @RequestBody List<ServiceProviderModel> serviceProviderModel)
         throws CustomException {
      BaseResponse<List<ServiceProviderModel>> serviceProviderList = utilitiesModelConfiguration
            .getBaseResponseInstance();
      // ServiceProviderModel list =
      // serviceProviderService.editServiceProvider(serviceProviderModel);
      serviceProviderList.setData(serviceProviderService.editServiceProvider(serviceProviderModel));
      return serviceProviderList;
   }
}