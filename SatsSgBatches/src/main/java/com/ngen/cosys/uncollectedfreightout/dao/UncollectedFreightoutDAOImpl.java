package com.ngen.cosys.uncollectedfreightout.dao;

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
import com.ngen.cosys.uncollectedfreightout.model.IRPNotificationDetails;
import com.ngen.cosys.uncollectedfreightout.model.ShipmentRemarksModel;
import com.ngen.cosys.uncollectedfreightout.model.UncollectedFreightoutShipmentModel;

@Repository
public class UncollectedFreightoutDAOImpl extends BaseDAO implements UncollectedFreightoutDAO {

	@Autowired
	@Qualifier("sqlSessionTemplate")
	SqlSession sqlSessionShipment;

	@Override
	public List<UncollectedFreightoutShipmentModel> sendDateForUncollectedFreightout(
			UncollectedFreightoutShipmentModel requestModel) throws CustomException {
		return super.fetchList("sqlGetUncollectedFreightout", requestModel, sqlSessionShipment);
	}

	@Override
	public List<String> getcustomercode(UncollectedFreightoutShipmentModel requestModel) throws CustomException {
		return super.fetchList("getcustomercode", requestModel, sqlSessionShipment);
	}

	@Override
	@NgenAuditAction(entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.SHIPMENT_IRP_BATCHJOB)
	public void createShipmentRemarks(ShipmentRemarksModel shipmentRemarksModel) throws CustomException {
		this.insertData("insertShipmentRemarks", shipmentRemarksModel, sqlSessionShipment);
	}

	@Override
	public List<String> getEmailAddresses() throws CustomException {
		return this.fetchList("getEmailAddressesforuldbtcoolport", null, sqlSessionShipment);
	}

	@Override
	public void createIrpNotification(IRPNotificationDetails shipmentNotification) throws CustomException {
		this.insertData("insertIrpNotificationDetails", shipmentNotification, sqlSessionShipment);
	}

	@Override
	public IRPNotificationDetails getIrpNotification(UncollectedFreightoutShipmentModel shipmentNotification)
			throws CustomException {
		return this.fetchObject("getIRPNotificationDetails", shipmentNotification, sqlSessionShipment);
	}

	@Override
	public void updateIrpNotification(IRPNotificationDetails shipmentNotification) throws CustomException {
		this.updateData("updateIRPNotificationDetails", shipmentNotification, sqlSessionShipment);
	}

}