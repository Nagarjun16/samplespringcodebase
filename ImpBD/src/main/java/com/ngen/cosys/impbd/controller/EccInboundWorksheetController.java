package com.ngen.cosys.impbd.controller;

import java.math.BigDecimal;
import java.math.BigInteger;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ngen.cosys.annotation.NgenCosysAppInfraAnnotation;
import com.ngen.cosys.annotation.PostRequest;
import com.ngen.cosys.audit.NgenAuditAction;
import com.ngen.cosys.audit.NgenAuditEntityType;
import com.ngen.cosys.audit.NgenAuditEventType;
import com.ngen.cosys.billing.api.Charge;
import com.ngen.cosys.billing.api.enums.ChargeEvents;
import com.ngen.cosys.billing.api.enums.ReferenceType;
import com.ngen.cosys.billing.api.model.ChargeableEntity;
import com.ngen.cosys.framework.config.UtilitiesModelConfiguration;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.framework.model.BaseResponse;
import com.ngen.cosys.impbd.model.EccInboundResult;
import com.ngen.cosys.impbd.model.SearchInbound;
import com.ngen.cosys.impbd.service.EccInboundWorksheetService;

/**
 * @author NIIT Technologies
 *
 */
@NgenCosysAppInfraAnnotation
public class EccInboundWorksheetController {
   @Autowired
   private EccInboundWorksheetService eccInboundWorksheetService;

   @Autowired
   private UtilitiesModelConfiguration utility;

   /**
    * @param searchInbound
    * @return EccInboundResult
    * @throws CustomException
    */
   @PostRequest(value = "/api/ecc/worksheetplanning/search", method = RequestMethod.POST)
   public BaseResponse<EccInboundResult> search(@Valid @RequestBody SearchInbound searchInbound)
         throws CustomException {
      @SuppressWarnings("unchecked")
      //Search
      BaseResponse<EccInboundResult> eccInboundResponse = utility.getBaseResponseInstance();
      EccInboundResult fetchObject = eccInboundWorksheetService.search(searchInbound);
      eccInboundResponse.setData(fetchObject);
      return eccInboundResponse;
   }

   /**
    * @param eccInboundResult
    * @return EccInboundResult
    * @throws CustomException
    */
   
   @PostRequest(value = "/api/ecc/worksheetplanning/save", method = RequestMethod.POST)
   public BaseResponse<EccInboundResult> save(@Valid @RequestBody EccInboundResult eccInboundResult)
         throws CustomException {
      @SuppressWarnings("unchecked")
      BaseResponse<EccInboundResult> eccInboundResultResponse = utility.getBaseResponseInstance();
      try {
         eccInboundResultResponse.setData(eccInboundWorksheetService.save(eccInboundResult));
         eccInboundResultResponse.getData().getShipmentList().forEach(e -> {
            e.getShipmentListDetails().forEach(ele -> {
               if (ele.isNoShow()) {
                  ChargeableEntity entity = new ChargeableEntity();
                  entity.setProcessType(ChargeEvents.PROCESS_IMP.getEnum());
                  entity.setEventType(ChargeEvents.IMP_NO_SHOW);
                  entity.setQuantity(BigDecimal.ONE);
                  entity.setCustomerId(ele.getAgent() != null ? new BigInteger(ele.getAgent()) : null);
                  entity.setUserCode(eccInboundResult.getLoggedInUser());
                  entity.setReferenceId(BigInteger.valueOf(ele.getWorksheetShipmentID()));
                  entity.setReferenceType(ReferenceType.IMP_ECC_NO_SHOW.getReferenceType());
                  entity.setHandlingTerminal(ele.getTerminal());
                  Charge.calculateCharge(entity);
               }

               if (e.isErrorFlag() || ele.isErrorFlag()) {

                  eccInboundResultResponse.setSuccess(false);
               }
            });
         });
         return eccInboundResultResponse;
      } catch (CustomException e) {
         eccInboundResultResponse.setData(eccInboundResult);
         return eccInboundResultResponse;
      }
   }

}