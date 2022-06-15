/**
 * This is a service component implementation for Auto Expire PO
 * 
 * @author NIIT Technologies Pvt. Ltd
 */
package com.ngen.cosys.application.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ngen.cosys.application.dao.AutoExpireDeliveryRequestDAO;
import com.ngen.cosys.application.model.AutoExpireDeliveryRequestModel;
import com.ngen.cosys.audit.NgenAuditAction;
import com.ngen.cosys.audit.NgenAuditEntityType;
import com.ngen.cosys.audit.NgenAuditEventType;
import com.ngen.cosys.billing.api.Charge;
import com.ngen.cosys.billing.api.enums.ChargeEvents;
import com.ngen.cosys.billing.api.model.BillingShipment;
import com.ngen.cosys.framework.exception.CustomException;

@Service
@Transactional
public class AutoExpireDeliveryRequestServiceImpl implements AutoExpireDeliveryRequestService {

   @Autowired
   private AutoExpireDeliveryRequestDAO dao;

   /*
    * (non-Javadoc)
    * 
    * @see com.ngen.cosys.application.service.AutoExpireDeliveryRequestService#
    * getShipments()
    */
   @Override
   public List<AutoExpireDeliveryRequestModel> getShipments() throws CustomException {
      return this.dao.getShipments();
   }

   /*
    * (non-Javadoc)
    * 
    * @see
    * com.ngen.cosys.application.service.AutoExpireDeliveryRequestService#expirePO(
    * com.ngen.cosys.application.model.AutoExpireDeliveryRequestModel)
    */
   @Override
   @NgenAuditAction(entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.AUTO_EXPIRE_PO)
   public void expirePO(AutoExpireDeliveryRequestModel autoExpireDeliveryRequestModel) throws CustomException {
      // Expire the PO
      boolean markedAsExpired = this.dao.expirePO(autoExpireDeliveryRequestModel);

      // Cancel the charges
      if (markedAsExpired) {
         BillingShipment shipment = new BillingShipment();
         shipment.setShipmentNumber(autoExpireDeliveryRequestModel.getShipmentNumber());
         shipment.setShipmentDate(autoExpireDeliveryRequestModel.getShipmentDate());
         shipment.setUserCode("AUTOBATCHJOB");
         shipment.setProcessType(ChargeEvents.PROCESS_IMP.getEnum());
         shipment.setEventType(ChargeEvents.IMP_CANCEL_PO);
         Charge.calculateCharge(shipment);
      }
   }

}