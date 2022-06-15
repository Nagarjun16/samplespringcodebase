package com.ngen.cosys.shipment.service;

import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import com.ngen.cosys.framework.constant.Action;
import com.ngen.cosys.framework.constant.ErrorType;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.shipment.dao.CommonFlightIdDAO;
import com.ngen.cosys.shipment.dao.ShipmentOnHoldDAO;
import com.ngen.cosys.shipment.model.CommonFlightId;
import com.ngen.cosys.shipment.model.SearchAWB;
import com.ngen.cosys.shipment.model.ShipmentInventory;
import com.ngen.cosys.shipment.model.ShipmentMaster;

/**
 * This class takes care of the responsibilities related to the Flights
 * responsible for shipment on hold service operation that comes from the
 * controller.
 * 
 * @author NIIT Technologies Ltd
 *
 */
@Service
@Transactional
public class ShipmentOnHoldServiceImpl implements ShipmetnOnHoldService {

   @Autowired
   private ShipmentOnHoldDAO shipmentOnHoldDAO;

   @Autowired
   private CommonFlightIdDAO commonFlightIdDAO;
   //bug-742 change
   @Override
   public List<ShipmentMaster> getLockDetails(SearchAWB paramAWB) throws CustomException {
      if (!shipmentOnHoldDAO.validateShipmentNumber(paramAWB)) {
         paramAWB.addError("INVALIDSHP", "", ErrorType.ERROR);
      }

      if (!paramAWB.getMessageList().isEmpty()) {
         throw new CustomException();
      }
      if (!StringUtils.isEmpty(paramAWB.getFlightKey()) && null != paramAWB.getFlightKeyDate()) {
         CommonFlightId commonFlightId = new CommonFlightId();
         commonFlightId.setFlightKey(paramAWB.getFlightKey());
         commonFlightId.setFlightDate(paramAWB.getFlightKeyDate());
         if (commonFlightIdDAO.getFlightId(commonFlightId) == null) {
            paramAWB.addError("INFLT001", "flightKey", ErrorType.ERROR);
         }
         if (!paramAWB.getMessageList().isEmpty()) {
            throw new CustomException();
         } else {
            paramAWB.setFlightId(Integer.parseInt(this.commonFlightIdDAO.getFlightId(commonFlightId)));
         }
      }
      return shipmentOnHoldDAO.getLockDetails(paramAWB);
   }

   @Override
   public void updateLockDetails(ShipmentMaster shipmentMaster) throws CustomException {
      List<ShipmentInventory> shpFlag = shipmentMaster.getShipmentInventories().stream()
            .filter(obj -> Action.UPDATE.toString().equals(obj.getFlagCRUD())).collect(Collectors.toList());

      if ("N".equalsIgnoreCase(shipmentMaster.getFlagUpdate()) && shpFlag.isEmpty()) {
         shipmentMaster.addError("SHPNOUPDATE", "awbHoldForm", ErrorType.ERROR);
      }
      
      if (!shipmentMaster.getMessageList().isEmpty()) {
         throw new CustomException();
      }

      if (!ObjectUtils.isEmpty(shipmentMaster) && shipmentMaster.isHold()
            && shipmentMaster.getReasonForHold().isEmpty()) {
         shipmentMaster.addError("SHP_HOLD", "reasonForHold", ErrorType.ERROR);
      }
      if (!shipmentMaster.getMessageList().isEmpty()) {
         throw new CustomException();
      }
      List<ShipmentInventory> shp = shipmentMaster.getShipmentInventories();
      for (ShipmentInventory ee : shp) {
         if (ee.isHold() && StringUtils.isEmpty(ee.getReasonForHold())) {
            ee.addError("SHP_HOLD", "reasonForHold", ErrorType.ERROR);
         }
         if (!ee.getMessageList().isEmpty()) {
            throw new CustomException();
         }
         if(!ee.isHold()) {
        	 if(StringUtils.isEmpty(ee.getShipmentNumber())) {
        		 ee.setShipmentNumber(shipmentMaster.getShipmentNumber());
        	 }
        	 BigInteger infoID = shipmentOnHoldDAO.getTracingShipmentInfoId(ee);
             if(infoID != null && !infoID.equals(BigInteger.ZERO)) {
	             ee.setComTracingShipmentInfoId(infoID);
	        	 shipmentOnHoldDAO.updateTracingRecords(ee);
	        	 shipmentOnHoldDAO.insertTracingRecordActivity(ee);
        	 }
         }
         
      }
      shipmentOnHoldDAO.updateLockDetails(shipmentMaster);
   }

   @Override
   @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
   public ShipmentMaster generateCTOcase(ShipmentMaster shipmentMaster) throws CustomException {
      BigInteger lastCaseNumber = shipmentOnHoldDAO.getMaxCaseNumber();
      lastCaseNumber = lastCaseNumber.add(BigInteger.ONE);
      String caseNumber = "CT" + StringUtils.leftPad(String.valueOf(lastCaseNumber.intValue()), 5, '0');
      shipmentMaster.setCaseNumber(caseNumber);
      createNewTracingRecord(shipmentMaster);
      return shipmentMaster;
   }

   /**
    * @param tracingActivitydata
    * @throws CustomException
    */
   @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
   private void createNewTracingRecord(ShipmentMaster tracingActivitydata) throws CustomException {
      shipmentOnHoldDAO.insertTracingRecords(tracingActivitydata);
      for (ShipmentInventory shcdata : tracingActivitydata.getShipmentInventories()) {
         shcdata.setComTracingShipmentInfoId(tracingActivitydata.getComTracingShipmentInfoId());
         shipmentOnHoldDAO.insertTracingShipmentInfo(shcdata);
      }
   }

   @Override
   public boolean isHoldExists(ShipmentMaster paramAWB) throws CustomException {
      return this.shipmentOnHoldDAO.isHoldExists(paramAWB);
   }
}