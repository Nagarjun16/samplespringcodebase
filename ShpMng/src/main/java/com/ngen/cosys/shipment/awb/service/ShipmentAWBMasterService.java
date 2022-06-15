/**
 * This is a service interface for managing document information on AWB/CBV/UCB
 */
package com.ngen.cosys.shipment.awb.service;

import java.util.List;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.routing.RoutingRequestModel;
import com.ngen.cosys.routing.RoutingResponseModel;
import com.ngen.cosys.shipment.awb.model.AWB;
import com.ngen.cosys.shipment.awb.model.ShipmentMasterCustomerAddressInfo;
import com.ngen.cosys.shipment.awb.model.ShipmentMasterCustomerInfo;

public interface ShipmentAWBMasterService {

	/**
	 * Method to create document information
	 * 
	 * @param requestModel
	 * @return
	 * @throws CustomException
	 */
	AWB createShipment(AWB requestModel) throws CustomException;

	/**
	 * Method to get document information
	 * 
	 * @param requestModel
	 * @return AWB
	 * @throws CustomException
	 */
	AWB get(AWB requestModel) throws CustomException;

	/**
	 * Methor to derive routing details
	 * 
	 * @param requestModel
	 * @return List<RoutingResponseModel>
	 * @throws CustomException
	 */
	List<RoutingResponseModel> routeDetails(RoutingRequestModel requestModel) throws CustomException;

	AWB getAcceptanceInfo(AWB awbDetails) throws CustomException;

	ShipmentMasterCustomerInfo getEmailInfo(ShipmentMasterCustomerInfo awbDetails) throws CustomException;

	ShipmentMasterCustomerInfo getFWBConsigneeInfo(AWB awbDetails) throws CustomException;

	List<ShipmentMasterCustomerInfo> getFWBConsigneeAgentInfoOnSelect(ShipmentMasterCustomerInfo awbDetails)
			throws CustomException;

	AWB getExchangeRate(AWB awbDetails) throws CustomException;

	ShipmentMasterCustomerAddressInfo getAllAppointedAgents(ShipmentMasterCustomerInfo request) throws CustomException;
	
	AWB updateShipmentType(AWB requestModel) throws CustomException;
	
	void publishShipmentEvent(AWB awbDetails, String messageType) throws CustomException;
	
	public String getMessageType(AWB awbDetails) throws CustomException;

}