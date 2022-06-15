package com.ngen.cosys.billing.chargepost.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ngen.cosys.annotation.NgenCosysAppInfraAnnotation;
import com.ngen.cosys.annotation.PostRequest;
import com.ngen.cosys.audit.NgenAuditAction;
import com.ngen.cosys.audit.NgenAuditEntityType;
import com.ngen.cosys.audit.NgenAuditEventType;
import com.ngen.cosys.billing.chargepost.service.ChargePostService;
import com.ngen.cosys.framework.config.UtilitiesModelConfiguration;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.framework.model.BaseResponse;

/**
 * This controller takes care of all requests related to ChargePost
 * 
 * @author NIIT Technologies Ltd
 *
 */
@NgenCosysAppInfraAnnotation
public class ChargePostController {

   @Autowired
   private ChargePostService chargePostService;

   @Autowired
   private UtilitiesModelConfiguration utilitiesModelConfiguration;

   /**
    * 
    * @return
    * @throws CustomException
    * 
    */
   @NgenAuditAction(entityType = NgenAuditEntityType.CUSTOMER, eventName = NgenAuditEventType.GENERATE_SD_BILL)
   @PostRequest(value = "api/billing/chargepost/GenerateSDBill", method = RequestMethod.POST)
   public BaseResponse getGenerateSDBill() throws CustomException {
      BaseResponse chargePostResponse = utilitiesModelConfiguration.getBaseResponseInstance();
      chargePostService.getGenerateCustomerSDBill();
      return chargePostResponse;
   }

   @NgenAuditAction(entityType = NgenAuditEntityType.CUSTOMER, eventName = NgenAuditEventType.GENERATE_AP_BILL)
   @PostRequest(value = "api/billing/chargepost/GenerateAPBill", method = RequestMethod.POST)
   public BaseResponse getGenerateAPBill() throws CustomException {
      BaseResponse chargePostResponse = utilitiesModelConfiguration.getBaseResponseInstance();
      chargePostService.getGenerateCustomerAPBill();
      return chargePostResponse;
   }
}