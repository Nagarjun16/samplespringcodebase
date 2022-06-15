package com.ngen.cosys.billing.chargerecalculate.service;

import java.math.BigInteger;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.ngen.cosys.billing.api.CheckPaymentStatus;
import com.ngen.cosys.billing.chargerecalculate.controller.ChargeRecalculateController;
import com.ngen.cosys.billing.chargerecalculate.dao.ChargeRecalculateDAO;
import com.ngen.cosys.framework.exception.CustomException;

@Service
public class ChargeRecalculateServiceImpl implements ChargeRecalculateService {

   @Autowired
   private ChargeRecalculateDAO chargeRecalculateDAO;

   @Autowired
   CheckPaymentStatus checkPaymentStatus;

   private static Logger logger = LoggerFactory.getLogger(ChargeRecalculateController.class);

   @Override
   public void recalculateCharges() throws CustomException {
      List<BigInteger> shipmentList = chargeRecalculateDAO.fetchShipmentsForRecalculation();
      if (!CollectionUtils.isEmpty(shipmentList)) {
         logger.info("Shipment List Size: " + shipmentList.size());
         for (BigInteger shipment : shipmentList) {
            checkPaymentStatus.getCheckPaymentStatusByShipmentId(shipment.longValue());
         }
      }
   }
}