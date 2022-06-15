package com.ngen.cosys.shipment.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.ngen.cosys.audit.NgenAuditAction;
import com.ngen.cosys.audit.NgenAuditEntityType;
import com.ngen.cosys.audit.NgenAuditEventType;
import com.ngen.cosys.framework.dao.BaseDAO;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.shipment.model.HoldNotifyShipment;
import com.ngen.cosys.shipment.model.SearchHoldNotifyShipment;
import com.ngen.cosys.shipment.model.UpdateHoldNotifyShipments;

@Repository
public class ShipmentHoldNotifyGroupDAOImpl extends BaseDAO implements ShipmentHoldNotifyGroupDAO {
	
	@Autowired
	@Qualifier("sqlSessionTemplate")
	private SqlSession sqlSession;
	   
	@Override
	public List<HoldNotifyShipment> onSearch(SearchHoldNotifyShipment search) throws CustomException {
		return fetchList("getHoldNotifyShipmentList", search, sqlSession);
	}

	
	@NgenAuditAction(entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.SHIPMENT_UNHOLD)
	@Override
	public boolean updateHold(HoldNotifyShipment updateHold) throws CustomException {
		return super.updateData("updateHoldForNotifyShipments", updateHold, sqlSession) >0;
		
	}


	@NgenAuditAction(entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.CHANGE_HOLD_NOTIFY_GROUP)
	@Override
	public boolean updateHoldNotifyGroup(HoldNotifyShipment updateHoldNotifyGroup) throws CustomException {
		return super.updateData("updateHoldForNotifyGroup", updateHoldNotifyGroup, sqlSession) >0;
	}


	@Override
	public boolean updateAck(UpdateHoldNotifyShipments updateAck) throws CustomException {
		return super.updateData("updateHoldForNotifyAck", updateAck, sqlSession) >0;
	}

}
