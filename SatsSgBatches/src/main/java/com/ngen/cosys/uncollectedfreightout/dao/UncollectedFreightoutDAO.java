package com.ngen.cosys.uncollectedfreightout.dao;

import java.util.List;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.uncollectedfreightout.model.IRPNotificationDetails;
import com.ngen.cosys.uncollectedfreightout.model.ShipmentRemarksModel;
import com.ngen.cosys.uncollectedfreightout.model.UncollectedFreightoutShipmentModel;

public interface UncollectedFreightoutDAO {

	List<String> getcustomercode(UncollectedFreightoutShipmentModel shipment) throws CustomException;

	List<UncollectedFreightoutShipmentModel> sendDateForUncollectedFreightout(
			UncollectedFreightoutShipmentModel requestModel)throws CustomException;

   void createShipmentRemarks(ShipmentRemarksModel remarks)throws CustomException;
   List<String> getEmailAddresses() throws CustomException;
   void createIrpNotification(IRPNotificationDetails shipmentRemarksModel) throws CustomException;
   public IRPNotificationDetails getIrpNotification(UncollectedFreightoutShipmentModel shipmentNotification) throws CustomException;

   void updateIrpNotification(IRPNotificationDetails shipmentNotification) throws CustomException;
}
