/**
 * MarkShipmentForReuseServiceImpl.java
 * 
 * Copyright
 * 
 * <PRE>
 * <IMG SRC = XX></IMG>
 * </PRE>
 *
 * Version Date Author Reason 1.0 6 January, 2018 NIIT -
 */
package com.ngen.cosys.shipment.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ngen.cosys.framework.constant.ErrorType;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.shipment.dao.MarkShipmentForReuseDAO;
import com.ngen.cosys.shipment.model.AddShipmentNumberForReuse;
import com.ngen.cosys.shipment.model.MarkShipmentForReuse;
import com.ngen.cosys.shipment.model.SearchShipmentNumberForReuse;

/**
 * This class takes care of the responsibilities related to Maintaining Shipment
 * Numbers For Reuse service operation that comes from the controller.
 * 
 * @author NIIT Technologies Ltd
 *
 */
@Service
public class MarkShipmentForReuseServiceImpl implements MarkShipmentForReuseService {

   @Autowired
   private MarkShipmentForReuseDAO markShipmentForReuseDAO;

   /**
    * Search Awb Number which is available for reuse it will Return Awb Number
    * which is available for reuse if they exist else It will Return Null.
    * 
    * @param shipmentNumber
    * @return List<MarkShipmentForReuse>
    * @throws CustomException
    */
   @Override
   public List<SearchShipmentNumberForReuse> search(MarkShipmentForReuse shipmentNumber) throws CustomException {
      if (!markShipmentForReuseDAO.validateShipmentNumber(shipmentNumber)) {
         shipmentNumber.addError("AWBNOTEXISTS", "awbshipmentNumber", ErrorType.ERROR);
      }
      if (!shipmentNumber.getMessageList().isEmpty()) {
         throw new CustomException();
      }
      return markShipmentForReuseDAO.search(shipmentNumber);
   }

   /**
    * Search All Awb Number which are available for reuse it will Return Awb Number
    * List which are available for reuse if they exist else It will Return Null.
    * 
    * @param shipmentNumber
    * @return List<MarkShipmentForReuse>
    * @throws CustomException
    */
   @Override
   public List<SearchShipmentNumberForReuse> searchAll() throws CustomException {
      return markShipmentForReuseDAO.searchAll();
   }

   /**
    * Add Awb Number which is available for reuse it will Add Awb Number which is
    * available for reuse if they exist else It will Return Null.
    * 
    * @param shipmentNumber
    * @return List<MarkShipmentForReuse>
    * @throws CustomException
    */
   @Override
   public List<SearchShipmentNumberForReuse> add(AddShipmentNumberForReuse shipmentNumber) throws CustomException {
      if (markShipmentForReuseDAO.validateShipmentNumberToAdd(shipmentNumber)) {
         shipmentNumber.addError("AWBEXISTS", "shipmentNumber", ErrorType.ERROR);
      }
      if (!shipmentNumber.getMessageList().isEmpty()) {
         throw new CustomException();
      }
      return markShipmentForReuseDAO.add(shipmentNumber);

   }

   @Override
   public void delete(List<SearchShipmentNumberForReuse> searchShipmentNumberForReuse) throws CustomException {
      for (SearchShipmentNumberForReuse ele : searchShipmentNumberForReuse) {
         if ("D".equalsIgnoreCase(ele.getFlagCRUD())) {
            markShipmentForReuseDAO.delete(ele);
         }
      }
   }
}
