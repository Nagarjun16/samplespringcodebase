/**
 * This is a class which holds entire functionality for managing of inventory
 * information based on document/found pieces for shipment.
 * 
 * Usage: 1. Auto-wire this class service interface 2. invoke the
 * createInventory() method
 * 
 * @author NIIT Technologies Pvt Ltd
 * @version 1.0
 * @since 23/03/2018
 */
package com.ngen.cosys.satssginterfaces.mss.service;

import java.math.BigInteger;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.satssginterfaces.mss.dao.ShipmentInventoryAirmailInterfaceDAO;
import com.ngen.cosys.satssginterfaces.mss.model.InboundBreakdownAirmailInterfaceModel;
import com.ngen.cosys.satssginterfaces.mss.model.InboundBreakdownShipmentHouseAirmailInterfaceModel;
import com.ngen.cosys.satssginterfaces.mss.model.InboundBreakdownShipmentInventoryAirmailInterfaceModel;
import com.ngen.cosys.satssginterfaces.mss.model.InboundBreakdownShipmentShcAirmailInterfaceModel;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
public class ShipmentInventoryAirmailInterfaceServiceImpl implements ShipmentInventoryAirmailInterfaceService {

   @Autowired
   private ShipmentInventoryAirmailInterfaceDAO dao;

   /*
    * (non-Javadoc)
    * 
    * @see
    * com.ngen.cosys.impbd.shipment.inventory.service.ShipmentInventoryService#
    * createInventory(com.ngen.cosys.impbd.inboundbreakdown.model.
    * InboundBreakdownModel)
    */
   @Override
   public void createInventory(InboundBreakdownAirmailInterfaceModel inboundBreakdownModel) throws CustomException {
      // iterate through each inventory and create them
      for (InboundBreakdownShipmentInventoryAirmailInterfaceModel inventory : inboundBreakdownModel.getShipment().getInventory()) {
         // create storage
         BigInteger inventoryId = dao.getInventory(inventory);
         // Check for inventory check
         Optional<BigInteger> t = Optional.ofNullable(inventoryId);
         if (!t.isPresent() || t.get().intValue() == 0) {
            dao.createInventory(inventory);
            inventoryId = inventory.getInventoryId();
         } else {
            dao.updateInventory(inventory);
         }
         // Add house for a inventory
         this.createHouse(inventory, inventoryId);
         // Add shc for a inventory
         this.createShc(inventory, inventoryId);

      }
   }

   /**
    * Method to create SHC for a inventory
    * 
    * @param inventory
    * @param inventoryId
    * @throws CustomException
    */
   private void createShc(InboundBreakdownShipmentInventoryAirmailInterfaceModel inventory, BigInteger inventoryId)
         throws CustomException {
      // create storage shc
      for (InboundBreakdownShipmentShcAirmailInterfaceModel shc : inventory.getShc()) {
         shc.setShipmentInventoryId(inventoryId);
         Boolean shcExists = dao.getShc(shc);
         Optional<Boolean> oShc = Optional.ofNullable(shcExists);
         if (!oShc.isPresent() || !oShc.get() && !StringUtils.isEmpty(shc.getSpecialHandlingCode())) {
            dao.createInventoryShc(shc);
         }
      }
   }

   /**
    * Method to create House for a inventory
    * 
    * @param inventory
    * @param inventoryId
    * @throws CustomException
    */
   private void createHouse(InboundBreakdownShipmentInventoryAirmailInterfaceModel inventory, BigInteger inventoryId)
         throws CustomException {
      // create storage house
      for (InboundBreakdownShipmentHouseAirmailInterfaceModel house : inventory.getHouse()) {
         house.setShipmentInventoryId(inventory.getInventoryId());

         // Check for shipment master house and if not exists insert other wise increase
         // piece/weight
         BigInteger shipmentHouseId = dao.getShipmentMasterHouse(house);
         house.setShipmentHouseId(shipmentHouseId);
         if (!Optional.ofNullable(shipmentHouseId).isPresent()) {
            dao.createShipmentMasterHouse(house);
         } else {
            dao.updateShipmentMasterHouse(house);
         }

         // Insert the inventory house association data
         Boolean houseExists = dao.getInventoryHouse(house);         
         Optional<Boolean> oHouse = Optional.ofNullable(houseExists);
         if (!oHouse.isPresent() || !oHouse.get()) {            
            dao.createInventoryHouse(house);
         } else {
            dao.updateInventoryHouse(house);
         }
      }
   }

}