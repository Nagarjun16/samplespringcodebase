package com.ngen.cosys.shipment.revive.controller;

import java.util.List;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ngen.cosys.annotation.NgenCosysAppInfraAnnotation;
import com.ngen.cosys.annotation.PostRequest;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.framework.model.BaseResponse;
import com.ngen.cosys.shipment.revive.model.ReviveShipmentModel;
import com.ngen.cosys.shipment.revive.model.ReviveShipmentSummary;
import com.ngen.cosys.shipment.revive.service.ReviveShipmentService;

@NgenCosysAppInfraAnnotation
public class ReviveShipmentController {

   @Autowired
   BeanFactory beanFactory;

   @Autowired
   ReviveShipmentService service;

   @PostRequest(path = "/api/shipment/reviveShipment/get", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
   public BaseResponse<ReviveShipmentSummary> getReviveShipmentInfo(@RequestBody ReviveShipmentSummary requestModel)
         throws CustomException {
      @SuppressWarnings("unchecked")
      BaseResponse<ReviveShipmentSummary> response = this.beanFactory.getBean(BaseResponse.class);
      try {
         List<ReviveShipmentModel> reviveShipmentList = this.service.getReviveShipmentInfo(requestModel);
         requestModel.setReviveShipmentList(reviveShipmentList);
         response.setData(requestModel);
      } catch (CustomException e) {
         response.setData(requestModel);
         response.setSuccess(false);
      }
      return response;
   }

   @PostRequest(path = "/api/shipment/reviveShipment/onrevive", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
   public BaseResponse<ReviveShipmentModel> onRevive(@RequestBody ReviveShipmentModel requestModel)
         throws CustomException {
      @SuppressWarnings("unchecked")
      BaseResponse<ReviveShipmentModel> response = this.beanFactory.getBean(BaseResponse.class);
      try {
         this.service.onRevive(requestModel);
         response.setData(requestModel);
      } catch (CustomException e) {
         response.setData(requestModel);
         response.setSuccess(false);
      }
      return response;
   }

}