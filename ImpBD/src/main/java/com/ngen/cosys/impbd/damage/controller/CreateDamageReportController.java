package com.ngen.cosys.impbd.damage.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ngen.cosys.annotation.NgenCosysAppInfraAnnotation;
import com.ngen.cosys.annotation.PostRequest;
import com.ngen.cosys.framework.config.UtilitiesModelConfiguration;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.framework.model.BaseResponse;
import com.ngen.cosys.impbd.damage.model.DamageReportModel;
import com.ngen.cosys.impbd.damage.model.DamageSearchModel;
import com.ngen.cosys.impbd.damage.service.CreateDamageReportService;
import com.ngen.cosys.validators.CaptureDamageReport;

import io.swagger.annotations.ApiOperation;

@NgenCosysAppInfraAnnotation
public class CreateDamageReportController {

   @Autowired
   private CreateDamageReportService service;

   @Autowired
   private UtilitiesModelConfiguration utilitiesModelConfiguration;

   @ApiOperation("fetch capture damage information")
   @PostRequest(value = "api/createdamagereport/search", method = RequestMethod.POST)
   public BaseResponse<Object> search(
         @RequestBody @Validated(value = CaptureDamageReport.class) @Valid DamageSearchModel damageSearchModel)
         throws CustomException {
      @SuppressWarnings("unchecked")
      BaseResponse<Object> damageReportModel = utilitiesModelConfiguration.getBaseResponseInstance();
      try {
         damageReportModel.setData(service.getDamageInformation(damageSearchModel));
      } catch (CustomException e) {
         damageReportModel.setData(damageSearchModel);
      }
      return damageReportModel;
   }

   @ApiOperation(value = "Api method to save damage additinal")
   @PostRequest(value = "api/createdamagereport/saveAddition", method = RequestMethod.POST)
   public BaseResponse<DamageReportModel> damage(@Valid @RequestBody DamageReportModel damageReportModel)
         throws CustomException {
      @SuppressWarnings("unchecked")
      BaseResponse<DamageReportModel> response = utilitiesModelConfiguration.getBaseResponseInstance();
      try {
         service.saveDamageInformation(damageReportModel);
         response.setData(damageReportModel);
      } catch (CustomException e) {
         response.setData(damageReportModel);
      }
      return response;
   }

   @ApiOperation(value = "Api method to finalize Cargo Damage")
   @PostRequest(value = "api/createdamagereport/finalize", method = RequestMethod.POST)
   public BaseResponse<DamageReportModel> damageFinalization(@Valid @RequestBody DamageReportModel damageReportModel)
         throws CustomException {
      @SuppressWarnings("unchecked")
      BaseResponse<DamageReportModel> resp = utilitiesModelConfiguration.getBaseResponseInstance();
      try {
         service.finalizeDamage(damageReportModel);
         resp.setData(damageReportModel);
      } catch (CustomException e) {
         resp.setData(damageReportModel);
      }
      return resp;

   }
}