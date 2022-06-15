/**
 *   ChangeAwbHawbController.java
 *   
 *   Copyright <PRE><IMG SRC = XX></IMG></PRE>
 *
 * Version      Date         Author      Reason
 * 1.0          30 May, 2018   NIIT      -
 */
package com.ngen.cosys.shipment.changeofawb.controller;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ngen.cosys.annotation.NgenCosysAppInfraAnnotation;
import com.ngen.cosys.annotation.PostRequest;
import com.ngen.cosys.audit.NgenAuditAction;
import com.ngen.cosys.audit.NgenAuditEntityType;
import com.ngen.cosys.audit.NgenAuditEventType;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.framework.model.BaseResponse;
import com.ngen.cosys.shipment.changeofawb.model.ChangeOfAwbHawb;
import com.ngen.cosys.shipment.changeofawb.service.ChangeAwbHawbService;
import com.ngen.cosys.shipment.validatorgroup.ChangeAwbValidationGroup;
import com.ngen.cosys.shipment.validatorgroup.ChangeOfHawbGroup;

import io.swagger.annotations.ApiOperation;

/**
 * Controller for change of AWB and HAWB
 * 
 *
 */
@NgenCosysAppInfraAnnotation
public class ChangeAwbHawbController {

   @Autowired
   private BeanFactory beanFactory;
   @Autowired
   private ChangeAwbHawbService changeofAwbHawbSevice;

//   @NgenAuditAction(entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.ADD_CHANGE_AWB_HAWB)
   @ApiOperation(value = "Update Shipment Number")
   @PostRequest(path = "api/shipment/updateawbhawb/updateawb", method = RequestMethod.POST)
   public BaseResponse<ChangeOfAwbHawb> updateShipmentNumber(
         @RequestBody @Validated(value = ChangeAwbValidationGroup.class) ChangeOfAwbHawb request)
         throws CustomException {
      BaseResponse<ChangeOfAwbHawb> response = (BaseResponse<ChangeOfAwbHawb>) this.beanFactory
            .getBean(BaseResponse.class);
      ChangeOfAwbHawb responseData = changeofAwbHawbSevice.updateAwbNumber(request);
      if (responseData.getMessageList() != null) {
         response.setMessageList(responseData.getMessageList());
         response.setSuccess(false);
      } else {
         response.setData(responseData);
         response.setSuccess(true);
      }
      return response;
   }

//   @NgenAuditAction(entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.ADD_CHANGE_AWB_HAWB)
   @ApiOperation(value = "Update HAWB Number")
   @PostRequest(path = "api/shipment/updateawbhawb/updatehawb", method = RequestMethod.POST)
   public BaseResponse<ChangeOfAwbHawb> updateHawbNumber(
         @RequestBody @Validated(value = ChangeOfHawbGroup.class) ChangeOfAwbHawb request) throws CustomException {
      BaseResponse<ChangeOfAwbHawb> response = (BaseResponse<ChangeOfAwbHawb>) this.beanFactory
            .getBean(BaseResponse.class);
      ChangeOfAwbHawb responseData = changeofAwbHawbSevice.updateHawbNumber(request);
      if (responseData.getMessageList() != null) {
         response.setMessageList(responseData.getMessageList());
         response.setSuccess(false);
      } else {
         response.setData(responseData);
         response.setSuccess(true);
      }
      return response;
   }
}