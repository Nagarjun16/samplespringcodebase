package com.ngen.cosys.shipment.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ngen.cosys.annotation.NgenCosysAppInfraAnnotation;
import com.ngen.cosys.annotation.PostRequest;
import com.ngen.cosys.audit.NgenAuditAction;
import com.ngen.cosys.audit.NgenAuditEntityType;
import com.ngen.cosys.audit.NgenAuditEventType;
import com.ngen.cosys.framework.config.UtilitiesModelConfiguration;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.framework.model.BaseResponse;
import com.ngen.cosys.shipment.model.DeleteRemarkBO;
import com.ngen.cosys.shipment.model.MaintainRemark;
import com.ngen.cosys.shipment.model.Maintainremarklist;
import com.ngen.cosys.shipment.model.RequestSearchRemarksBO;
import com.ngen.cosys.shipment.model.ResponseSearchRemarksBO;
import com.ngen.cosys.shipment.service.MaintainRemarkService;

/**
 * This class is controller for Code Share Flight functionalities.
 * 
 * @author NIIT Technologies Ltd.
 *
 */
@NgenCosysAppInfraAnnotation
public class MaintainRemarkController {

   @Autowired
   private UtilitiesModelConfiguration utilitiesModelConfiguration;
   @Autowired
   private MaintainRemarkService maintainRemarkService;

   /**
    * Getting the fields for remark
    * 
    * @return all the data of the remarks
    * @throws CustomException
    */
   @PostRequest(value = "/api/shipment/remark/get", method = RequestMethod.POST)
   public BaseResponse<ResponseSearchRemarksBO> fetchRemarkList(
         @Valid @RequestBody RequestSearchRemarksBO paramSearchRemarks) throws CustomException {
      BaseResponse<ResponseSearchRemarksBO> baseResponse = utilitiesModelConfiguration.getBaseResponseInstance();
      ResponseSearchRemarksBO res = maintainRemarkService.getRemark(paramSearchRemarks);
      baseResponse.setData(res);
      return baseResponse;
   }

   /**
    * Inserting the fields for remark
    * 
    * @return all the data of the remarks
    * @throws CustomException
    */
   @NgenAuditAction(entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.ADD_MAINTAIN_REMARK) 
   @PostRequest(value = "/api/shipment/remark/insert", method = RequestMethod.POST)
   public List<MaintainRemark> insertRemark(@RequestBody @Valid Maintainremarklist parmRemarkList) throws CustomException {
      maintainRemarkService.insertRemark(parmRemarkList.getMaintainremarkdetail());
      return parmRemarkList.getMaintainremarkdetail();
   }

   /**
    * deleting the fields for remark
    * 
    * @return all the data of the remarks
    * @throws CustomException
    */
   @PostRequest(value = "/api/shipment/remark/delete", method = RequestMethod.POST)
   public void deleteRemark(@RequestBody DeleteRemarkBO paramRemarksList) throws CustomException {
      maintainRemarkService.deleteRemark(paramRemarksList);
   }
}