/**
 * 
 * MarkShipmentForReuseService.java
 * 
 * Copyright
 * 
 * <PRE>
 * <IMG SRC = XX></IMG>
 * </PRE>
 *
 * Version Date Author Reason 1.0 5 January, 2018 NIIT -
 */
package com.ngen.cosys.shipment.service;

import com.ngen.cosys.shipment.model.AddShipmentNumberForReuse;
import com.ngen.cosys.shipment.model.MarkShipmentForReuse;
import com.ngen.cosys.shipment.model.SearchShipmentNumberForReuse;

import java.util.List;

import com.ngen.cosys.framework.exception.CustomException;

/**
 * This interface takes care of Marking Shipment For Reuse
 * 
 * @author NIIT Technologies Ltd
 *
 */

public interface MarkShipmentForReuseService {

   /**
    * Find list of shipment number available for reuse
    * 
    * @param shipmentNumber
    * @return List of shipment number available for reuse
    * @throws CustomException
    */
   List<SearchShipmentNumberForReuse> search(MarkShipmentForReuse shipmentNumber) throws CustomException;

   /**
    * Find list of shipment number available for reuse
    * 
    * @param shipmentNumber
    * @return List of shipment number available for reuse
    * @throws CustomException
    */
   List<SearchShipmentNumberForReuse> searchAll() throws CustomException;

   /**
    * add a shipment number available for reuse
    * 
    * @param shipmentNumber
    * @return Shipment Number Reuse mapping that has been created
    * @throws CustomException
    */
   List<SearchShipmentNumberForReuse> add(AddShipmentNumberForReuse shipmentNumber) throws CustomException;

   /**
    * Delete a shipment number available for reuse
    * 
    * @param shipmentNumber
    * @return Shipment Number Reuse mapping that have been deleted
    * @throws CustomException
    */
   void delete(List<SearchShipmentNumberForReuse> searchShipmentNumberForReuse) throws CustomException;
}
