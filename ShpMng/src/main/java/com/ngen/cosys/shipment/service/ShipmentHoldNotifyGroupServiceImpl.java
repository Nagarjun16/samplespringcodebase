package com.ngen.cosys.shipment.service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.multitenancy.timezone.support.TenantTimeZoneUtility;
import com.ngen.cosys.shipment.dao.ShipmentHoldNotifyGroupDAO;
import com.ngen.cosys.shipment.model.HoldNotifyShipment;
import com.ngen.cosys.shipment.model.SearchHoldNotifyShipment;
import com.ngen.cosys.shipment.model.UpdateHoldNotifyGroup;
import com.ngen.cosys.shipment.model.UpdateHoldNotifyShipments;
import com.ngen.cosys.timezone.enums.TenantTimeZone;

@Service
@Transactional
public class ShipmentHoldNotifyGroupServiceImpl implements ShipmentHoldNotifyGroupService {

	@Autowired
	ShipmentHoldNotifyGroupDAO shipmentHoldNotifyGroupDao;

	@Override
	public List<HoldNotifyShipment> onSearch(SearchHoldNotifyShipment search) throws CustomException {

		return shipmentHoldNotifyGroupDao.onSearch(search);
	}

	@Override
	public boolean updateHold(UpdateHoldNotifyShipments updateHold) throws CustomException {
		for (HoldNotifyShipment updateUnHold : updateHold.getShipmentNumber()) {
			LocalDateTime tenantDateTime = TenantTimeZoneUtility.now();
			updateUnHold.setUnholdTime(tenantDateTime);
			updateUnHold.setUnholdBy(updateHold.getCreatedBy());
			updateUnHold.setUnHoldRemarks(updateHold.getUnHoldRemarks());
			shipmentHoldNotifyGroupDao.updateHold(updateUnHold);
		}
		return true;
	}

	@Override
	public boolean updateHoldNotifyGroup(UpdateHoldNotifyGroup updateHoldNotifyGroup) throws CustomException {
		for (HoldNotifyShipment updateNofityGroup : updateHoldNotifyGroup.getShipmentNumber()) {
			LocalDateTime tenantDateTime = TenantTimeZoneUtility.now();
			updateNofityGroup.setNotifyGroupUpdatedTime(tenantDateTime);
			updateNofityGroup.setNotifyGroupUpdatedBy(updateHoldNotifyGroup.getCreatedBy());
			updateNofityGroup.setHoldNotifyGroup(updateHoldNotifyGroup.getHoldNotifyGroup());
			shipmentHoldNotifyGroupDao.updateHoldNotifyGroup(updateNofityGroup);
		}
		return true;
	}

	@Override
	public boolean updateAck(UpdateHoldNotifyShipments updateAck) throws CustomException {
		return shipmentHoldNotifyGroupDao.updateAck(updateAck);
	}

}
