/**
 * CommonFlightIdDAO.java
 * 
 * Copyright <PRE><IMG SRC = XX></IMG></PRE>
 *
 * Version      Date       Author      Reason
 * 1.0          04 Jan, 2018  NIIT      -
 */
package com.ngen.cosys.shipment.dao;

import java.util.List;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.shipment.model.CommonFlightId;

public interface CommonFlightIdDAO {

	/**
	 * Returns the FlightID for the given Search Flight criteria.
	 * 
	 * @param commonFlight
	 * @return flightID
	 * @throws CustomException
	 */
	String getFlightId(CommonFlightId commonFlight) throws  CustomException;
	
	/**
	 * @param commonFlight
	 * @return
	 * @throws CustomException
	 */
	List<CommonFlightId> getFlightKeyDate(CommonFlightId commonFlight) throws CustomException;
}