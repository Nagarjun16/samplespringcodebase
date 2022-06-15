/**
 * This is a service interface for handling HAWB Delete functionalities
 */
package com.ngen.cosys.shipment.deletehousewaybill.service;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.shipment.deletehousewaybill.model.DeleteHouseWayBillResponseModel;
import com.ngen.cosys.shipment.deletehousewaybill.model.DeleteHouseWayBillSearchModel;
import com.ngen.cosys.shipment.deletehousewaybill.model.UpdateShipmentDetails;

public interface DeleteHouseWayBillService {

	DeleteHouseWayBillResponseModel deleteHouseWayBill(DeleteHouseWayBillSearchModel deleteHouseWayBillSearchModel)throws CustomException;

	String checkForShipmentIsImportExport(DeleteHouseWayBillSearchModel deleteHouseWayBillSearchModel)throws CustomException;
	
	public void publishShipmentEvent(DeleteHouseWayBillSearchModel deleteHouseWayBillSearchModel, UpdateShipmentDetails UpdateShipmentDetail) throws CustomException;
	//DeleteHouseWayBillResponseModel deleteHouseWayBillExport(
			//DeleteHouseWayBillSearchModel deleteHouseWayBillSearchModel)throws CustomException;
	
	public UpdateShipmentDetails getShipmentDetails(DeleteHouseWayBillSearchModel deleteHouseWayBillSearchModel) throws CustomException;

}
