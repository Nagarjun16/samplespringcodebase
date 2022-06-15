/**
 * 
 */
package com.ngen.cosys.house.information.dao;

import java.math.BigInteger;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.shipment.information.model.ShipmentInfoModel;
import com.ngen.cosys.shipment.information.model.ShipmentInfoSearchReq;
import com.ngen.cosys.shipment.model.ShipmentMaster;

/**
 * @author Priyanka.Middha
 *
 */
public interface HouseInformationDao {

	/**
	 * Method to get HAWB information for a given House
	 * 
	 * @param search
	 * @return ShipmentInfoModel 
	 * @throws CustomException
	 */
	ShipmentInfoModel getHouseInfo(ShipmentInfoSearchReq search) throws CustomException;	
	
	/**
	 * Method to change HandledBy
	 * 
	 * @param shipmentMaster
	 * @return 
	 * @throws CustomException
	 */
	void changeHandling(ShipmentMaster shipmentMaster) throws CustomException;
	
	public Integer isFlightCompleted(BigInteger flightId) throws CustomException;
	public BigInteger getFlightId(BigInteger shipmentId) throws CustomException;
	String getClearingAgentName(String agentCode) throws CustomException;
}
