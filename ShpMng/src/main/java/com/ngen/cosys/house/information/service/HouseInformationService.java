/**
 * 
 */
package com.ngen.cosys.house.information.service;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.shipment.information.model.ShipmentInfoModel;
import com.ngen.cosys.shipment.information.model.ShipmentInfoSearchReq;
import com.ngen.cosys.shipment.model.ShipmentMaster;

/**
 * @author Priyanka.Middha
 *
 */
public interface HouseInformationService {

	/**
	 * Fetches HAWB Information.
	 * 
	 * @param search
	 * @return ShipmentInfoModel
	 * @throws CustomException
	 */

	ShipmentInfoModel getHouseInfo(ShipmentInfoSearchReq search) throws CustomException;

	/**
	 * Changing handledBy HAWB Information.
	 * 
	 * @param search
	 * @return ShipmentMaster
	 * @throws CustomException
	 */
	ShipmentMaster changeHandling(ShipmentMaster shipmentMaster) throws CustomException;
	
	public void publishShipmentEvent(ShipmentMaster shipmentMaster) throws CustomException;

}
