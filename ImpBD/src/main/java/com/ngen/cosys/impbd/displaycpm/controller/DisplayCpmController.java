/**
 * This is a rest service for displaying CPM info
 */
package com.ngen.cosys.impbd.displaycpm.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ngen.cosys.annotation.NgenCosysAppInfraAnnotation;
import com.ngen.cosys.annotation.PostRequest;
import com.ngen.cosys.framework.config.UtilitiesModelConfiguration;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.framework.model.BaseResponse;
import com.ngen.cosys.impbd.displaycpm.model.DisplayCpmModel;
import com.ngen.cosys.impbd.displaycpm.service.DisplayCpmService;

import io.swagger.annotations.ApiOperation;

@NgenCosysAppInfraAnnotation
public class DisplayCpmController {

   @Autowired
   private DisplayCpmService service;

   @Autowired
   private UtilitiesModelConfiguration utilitiesModelConfiguration;

   @ApiOperation("API to get CPM info")
   @PostRequest(value = "api/displaycpm/search", method = RequestMethod.POST)
   public BaseResponse<DisplayCpmModel> getCPMInformation(@RequestBody @Valid DisplayCpmModel displayCpmModel)
         throws CustomException {

      @SuppressWarnings("unchecked")
      BaseResponse<DisplayCpmModel> cpmDetails = utilitiesModelConfiguration.getBaseResponseInstance();

      DisplayCpmModel detail = service.search(displayCpmModel);
      cpmDetails.setData(detail);
      return cpmDetails;
   }
}