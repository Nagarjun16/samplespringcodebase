/**
 * MarkShipmentForReuseDAO.java
 * 
 * Copyright
 * 
 * <PRE>
 * <IMG SRC = XX></IMG>
 * </PRE>
 *
 * Version Date Author Reason 1.0 6 January, 2018 NIIT -
 */
package com.ngen.cosys.shipment.dao;

import java.util.List;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.shipment.model.AddShipmentNumberForReuse;
import com.ngen.cosys.shipment.model.MarkShipmentForReuse;
import com.ngen.cosys.shipment.model.SearchShipmentNumberForReuse;

public interface MarkShipmentForReuseDAO {

   /**
    * Find Shipment for Reuse Detail it will Return AWB Number which if it is
    * available for Reuse else It will Return Null.
    * 
    * @param searchShipmentNumber
    * @return List<MarkShipmentForReuse>
    * @throws CustomException
    */
   List<SearchShipmentNumberForReuse> search(MarkShipmentForReuse shipmentNumber) throws CustomException;
   
   /**
   * 
   * @param searchShipmentNumber
   * @return List<MarkShipmentForReuse>
   * @throws CustomException
   */
   boolean validateShipmentNumber(MarkShipmentForReuse paramAWB) throws CustomException;

   /**
    * Find Shipment for Reuse Details it will Return all AWB Number which are
    * available for Reuse else It will Return Null.
    * 
    * @param searchShipmentNumber
    * @return List<MarkShipmentForReuse>
    * @throws CustomException
    */
   List<SearchShipmentNumberForReuse> searchAll() throws CustomException;

   /**
    * Add a Shipment Number available for Reuse
    * 
    * @param markShipmentNumber
    * @return list of successfully created Shipment Number's which are available
    *         for Reuse
    * @throws CustomException
    */
   List<SearchShipmentNumberForReuse> add(AddShipmentNumberForReuse shipmentNumber) throws CustomException;

   /**
    * Delete a Shipment Number which is available for Reuse
    * 
    * @param shipmentId
    * @return deleted Shipment Number which is available for Reuse
    * @throws CustomException
    */
   boolean delete(SearchShipmentNumberForReuse shipmentId) throws CustomException;
   
   /**
    * Delete a Shipment Number which is available for Reuse
    * 
    * @param shipmentId
    * @return deleted Shipment Number which is available for Reuse
    * @throws CustomException
    */
   boolean delete(List<SearchShipmentNumberForReuse> shipments) throws CustomException;
   
   /**
    * @param shipmentNumber
    * @return
    * @throws CustomException
    */
   String validateOrigin(AddShipmentNumberForReuse shipmentNumber) throws CustomException;
   
   boolean validateShipmentNumberToAdd(AddShipmentNumberForReuse paramAWB) throws CustomException;
}
