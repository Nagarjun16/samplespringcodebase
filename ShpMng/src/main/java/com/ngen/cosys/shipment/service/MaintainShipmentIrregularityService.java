/**
 * 
 * MaintainShipmentIrregularityService.java
 * 
 * Copyright
 * 
 * <PRE>
 * <IMG SRC = XX></IMG>
 * </PRE>
 *
 * Version Date Author Reason 1.0 4 January, 2018 NIIT -
 */
package com.ngen.cosys.shipment.service;

import com.ngen.cosys.shipment.model.IrregularityDetail;
import com.ngen.cosys.shipment.model.IrregularitySummary;
import com.ngen.cosys.shipment.model.SearchShipmentIrregularity;

import java.math.BigInteger;
import java.util.List;

import com.ngen.cosys.framework.exception.CustomException;

/**
 * This interface takes care of maintaining shipment irregularities
 * 
 * @author NIIT Technologies Ltd
 *
 */
public interface MaintainShipmentIrregularityService {

   /**
    * Search a shipment
    * 
    * @return the searched shipment details
    * @throws CustomException
    */
   IrregularitySummary search(SearchShipmentIrregularity search) throws CustomException;

   /**
    * Add an irregularity
    * 
    * @return the added irregularity
    * @throws CustomException
    */
   IrregularitySummary add(IrregularitySummary maintain) throws CustomException;

   /**
    * Update an irregularity
    * 
    * @return the updated shipment
    * @throws CustomException
    */
   IrregularitySummary save(IrregularitySummary maintain) throws CustomException;

   /**
    * Delete an irregularity
    * 
    * @return the updated shipment with deleted irregularity
    * @throws CustomException
    */
   IrregularityDetail delete(List<IrregularityDetail> delete) throws CustomException;
   
   /**
    * get flightId
    * 
    * @return flightId
    * @throws CustomException
    */
   BigInteger getFlightId(IrregularityDetail irregularityInfo) throws CustomException;

   /**
    * Method to trigger break down event when there is a break down is done and
    * irregularity is added
    * 
    * @param irregularityInfo
    *           - Irregularity info
    * @throws CustomException
    */
   void raiseBreakDownEvent(IrregularityDetail irregularityInfo) throws CustomException;

}