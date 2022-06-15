package com.ngen.cosys.shipment.coolportmonitoring.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ngen.cosys.annotation.NgenCosysAppInfraAnnotation;
import com.ngen.cosys.framework.config.UtilitiesModelConfiguration;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.framework.model.BaseResponse;
import com.ngen.cosys.shipment.coolportmonitoring.model.CoolportMonitoringDetail;
import com.ngen.cosys.shipment.coolportmonitoring.model.CoolportMonitoringSearch;
import com.ngen.cosys.shipment.coolportmonitoring.service.CoolportMonitoringService;

@NgenCosysAppInfraAnnotation
public class CoolportMonitoringController {

   @Autowired
   private UtilitiesModelConfiguration utilitiesModelConfiguration;

   @Autowired
   private CoolportMonitoringService coolportmonitoringservice;

   /**
    * @param paramSearch
    * @return BaseResponse<List<CoolportMonitoringDetail>>
    * @throws CustomException
    */
   @RequestMapping(value = "/api/shipment/cooolportmonitoring/get", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
   public BaseResponse<List<CoolportMonitoringDetail>> fetch(@RequestBody CoolportMonitoringSearch paramSearch)
         throws CustomException {
      @SuppressWarnings("unchecked")
      BaseResponse<List<CoolportMonitoringDetail>> coolportmonitoringdetailmodel = utilitiesModelConfiguration
            .getBaseResponseInstance();
      List<CoolportMonitoringDetail> res = coolportmonitoringservice.fetch(paramSearch);
      coolportmonitoringdetailmodel.setData(res);
      return coolportmonitoringdetailmodel;
   }

   @RequestMapping(value = "/api/shipment/cooolportmonitoring/update", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
   public BaseResponse<List<CoolportMonitoringDetail>> saveTemparatureRange(
         @RequestBody List<CoolportMonitoringSearch> paramSearch) throws CustomException {
      @SuppressWarnings("unchecked")
      BaseResponse<List<CoolportMonitoringDetail>> coolportmonitoringdetailmodel = utilitiesModelConfiguration
            .getBaseResponseInstance();
      List<CoolportMonitoringDetail> res = coolportmonitoringservice.saveTemparatureRange(paramSearch);
      coolportmonitoringdetailmodel.setData(res);
      return coolportmonitoringdetailmodel;
   }
}