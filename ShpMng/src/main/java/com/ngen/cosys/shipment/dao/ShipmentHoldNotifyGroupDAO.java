package com.ngen.cosys.shipment.dao;

import java.util.List;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.shipment.model.HoldNotifyShipment;
import com.ngen.cosys.shipment.model.SearchHoldNotifyShipment;
import com.ngen.cosys.shipment.model.UpdateHoldNotifyGroup;
import com.ngen.cosys.shipment.model.UpdateHoldNotifyShipments;

public interface ShipmentHoldNotifyGroupDAO {
	
	List<HoldNotifyShipment> onSearch(SearchHoldNotifyShipment search) throws CustomException;

	boolean updateHold(HoldNotifyShipment updateHold) throws CustomException;

	boolean updateHoldNotifyGroup(HoldNotifyShipment updateHoldNotifyGroup) throws CustomException;

	boolean updateAck(UpdateHoldNotifyShipments updateAck) throws CustomException;
}
