/**
 * MaintainShipmentIrregularityDAO.java
 * 
 * Copyright <PRE><IMG SRC = XX></IMG></PRE>
 *
 * Version      Date         Author      Reason
 * 1.0          4 January, 2018   NIIT      -
 */
package com.ngen.cosys.shipment.dao;

import java.math.BigInteger;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.shipment.model.IrregularityDetail;
import com.ngen.cosys.shipment.model.IrregularitySummary;
import com.ngen.cosys.shipment.model.SearchShipmentIrregularity;


public interface MaintainShipmentIrregularityDAO {
   
   /**
    * searching a particular irregularity dao
    * @return successfully searched irregularity
    * @throws CustomException
    */
   IrregularitySummary search(SearchShipmentIrregularity search) throws CustomException;
   /**
    * adding an irregularity dao
    * @return successfully add irregularity
    * @throws CustomException
    */
   IrregularityDetail add(IrregularityDetail maintain) throws  CustomException;
    /**
    * updating/saving an irregularity dao
    * @return successfully update irregularity
    * @throws CustomException
    */
   IrregularityDetail save(IrregularityDetail maintain) throws  CustomException;
   /**
    * delete an irregularity dao
    * @return successfully deleted irregularity
    * @throws CustomException
    */
   IrregularityDetail delete(IrregularityDetail maintain) throws  CustomException;
   /**
    * fetch FlightId
    * @return BigInteger FlightId
    * @throws CustomException
    */
	BigInteger getFlightId(IrregularityDetail irregularityInfo) throws CustomException;

	Integer checkforduplicate(IrregularityDetail add) throws CustomException;

	Integer checkforduplicate1(IrregularityDetail update) throws CustomException;

	Boolean checkForFlightDetails(IrregularityDetail requestModel) throws CustomException;
	
	Boolean checkDocumentFlag(IrregularityDetail requestModel) throws CustomException;
	Boolean checkAcceptanceFlag(IrregularityDetail requestModel) throws CustomException;
	String getFlightSegment(IrregularityDetail requestModel) throws CustomException;
	

}
