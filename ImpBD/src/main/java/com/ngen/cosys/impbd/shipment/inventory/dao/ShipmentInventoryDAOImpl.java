/**
 * This is a repository implementation class for persisting shipment inventory/shipment master house and shc
 */
package com.ngen.cosys.impbd.shipment.inventory.dao;

import java.math.BigInteger;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.ngen.cosys.audit.NgenAuditAction;
import com.ngen.cosys.audit.NgenAuditEntityType;
import com.ngen.cosys.audit.NgenAuditEventType;
import com.ngen.cosys.framework.constant.Action;
import com.ngen.cosys.framework.dao.BaseDAO;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.impbd.shipment.breakdown.model.InboundBreakdownHAWBModel;
import com.ngen.cosys.impbd.shipment.breakdown.model.InboundBreakdownShipmentHouseModel;
import com.ngen.cosys.impbd.shipment.breakdown.model.InboundBreakdownShipmentInventoryModel;
import com.ngen.cosys.impbd.shipment.breakdown.model.InboundBreakdownShipmentModel;
import com.ngen.cosys.impbd.shipment.breakdown.model.InboundBreakdownShipmentShcModel;
import com.ngen.cosys.impbd.shipment.breakdown.model.InboundUldFlightModel;
import com.ngen.cosys.impbd.shipment.document.model.ShipmentMaster;
import com.ngen.cosys.impbd.shipment.inventory.constant.ShipmentInventorySqlId;

@Repository
public class ShipmentInventoryDAOImpl extends BaseDAO implements ShipmentInventoryDAO {

	@Autowired
	@Qualifier("sqlSessionTemplate")
	private SqlSessionTemplate sqlSessionTemplate;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ngen.cosys.impbd.shipment.inventory.dao.ShipmentInventoryDAO#
	 * createInventory(com.ngen.cosys.impbd.shipment.breakdown.model.
	 * InboundBreakdownShipmentInventoryModel)
	 */
	@Override
	@NgenAuditAction(entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.INBOUND_BREAKDOWN)
	public void createInventory(InboundBreakdownShipmentInventoryModel inventory) throws CustomException {
		if((inventory.getInventoryId() == null || inventory.getInventoryId().intValue() == 0) 
				&& !Action.DELETE.toString().equalsIgnoreCase(inventory.getFlagCRUD())) {
			super.insertData(ShipmentInventorySqlId.SQL_INSERT_INVENTORY.getQueryId(), inventory, sqlSessionTemplate);
		}else if(!Action.DELETE.toString().equalsIgnoreCase(inventory.getFlagCRUD()) && Action.UPDATE.toString().equalsIgnoreCase(inventory.getFlagCRUD())){
			super.updateData(ShipmentInventorySqlId.SQL_UPDATE_INVENTORY.getQueryId(), inventory, sqlSessionTemplate);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ngen.cosys.impbd.shipment.inventory.dao.ShipmentInventoryDAO#getInventory
	 * (com.ngen.cosys.impbd.shipment.breakdown.model.
	 * InboundBreakdownShipmentInventoryModel)
	 */
	@Override
	public BigInteger getInventory(InboundBreakdownShipmentInventoryModel inventory) throws CustomException {
		return super.fetchObject(ShipmentInventorySqlId.SQL_CHECK_INVENTORY.getQueryId(), inventory,
				sqlSessionTemplate);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ngen.cosys.impbd.shipment.inventory.dao.ShipmentInventoryDAO#
	 * updateInventory(com.ngen.cosys.impbd.shipment.breakdown.model.
	 * InboundBreakdownShipmentInventoryModel)
	 */
	@Override
	public void updateInventory(InboundBreakdownShipmentInventoryModel inventory) throws CustomException {
		super.updateData(ShipmentInventorySqlId.SQL_UPDATE_PIECE_WEIGHT.getQueryId(), inventory, sqlSessionTemplate);
	}
	
	@Override
	@NgenAuditAction(entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.INBOUND_BREAKDOWN)
	public void deleteInventory(InboundBreakdownShipmentInventoryModel breakdownModel) throws CustomException {
		this.deleteInventoryShcs(breakdownModel);
		super.deleteData(ShipmentInventorySqlId.SQL_DELETE_INVENTORY.getQueryId(), breakdownModel, sqlSessionTemplate);
	}
	
	private void deleteInventoryShcs(InboundBreakdownShipmentInventoryModel breakdownModel) throws CustomException {
		super.deleteData(ShipmentInventorySqlId.SQL_DELETE_SHC.getQueryId(), breakdownModel, sqlSessionTemplate);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ngen.cosys.impbd.shipment.inventory.dao.ShipmentInventoryDAO#getShc(com.
	 * ngen.cosys.impbd.shipment.breakdown.model.InboundBreakdownShipmentShcModel)
	 */
	@Override
	public Boolean getShc(InboundBreakdownShipmentShcModel shc) throws CustomException {
		return super.fetchObject(ShipmentInventorySqlId.SQL_CHECK_SHC.getQueryId(), shc, sqlSessionTemplate);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ngen.cosys.impbd.shipment.inventory.dao.ShipmentInventoryDAO#
	 * createInventoryShc(com.ngen.cosys.impbd.shipment.breakdown.model.
	 * InboundBreakdownShipmentShcModel)
	 */
	@Override
	public void createInventoryShc(InboundBreakdownShipmentShcModel shc) throws CustomException {
		super.insertData(ShipmentInventorySqlId.SQL_INSERT_SHC.getQueryId(), shc, sqlSessionTemplate);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ngen.cosys.impbd.shipment.inventory.dao.ShipmentInventoryDAO#
	 * getShipmentMasterHouse(com.ngen.cosys.impbd.shipment.breakdown.model.
	 * InboundBreakdownShipmentHouseModel)
	 */
	@Override
	public BigInteger getShipmentMasterHouse(InboundBreakdownShipmentHouseModel house) throws CustomException {
		return super.fetchObject(ShipmentInventorySqlId.SQL_GET_SHIPMENTHOUSEINFO.getQueryId(), house,
				sqlSessionTemplate);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ngen.cosys.impbd.shipment.inventory.dao.ShipmentInventoryDAO#
	 * createShipmentMasterHouse(com.ngen.cosys.impbd.shipment.breakdown.model.
	 * InboundBreakdownShipmentHouseModel)
	 */
	@Override
	@NgenAuditAction(entityType = NgenAuditEntityType.MAILBAG, eventName = NgenAuditEventType.MAIL_BREAKDOWN)
	public void createShipmentMasterHouse(InboundBreakdownShipmentHouseModel house) throws CustomException {
		super.insertData(ShipmentInventorySqlId.SQL_CREATE_SHIPMENTHOUSEINFO.getQueryId(), house, sqlSessionTemplate);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ngen.cosys.impbd.shipment.inventory.dao.ShipmentInventoryDAO#
	 * updateShipmentMasterHouse(com.ngen.cosys.impbd.shipment.breakdown.model.
	 * InboundBreakdownShipmentHouseModel)
	 */
	@Override
	@NgenAuditAction(entityType = NgenAuditEntityType.MAILBAG, eventName = NgenAuditEventType.MAIL_BREAKDOWN)
	public void updateShipmentMasterHouse(InboundBreakdownShipmentHouseModel house) throws CustomException {
		super.updateData(ShipmentInventorySqlId.SQL_UPDATE_SHIPMENTHOUSEINFO.getQueryId(), house, sqlSessionTemplate);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ngen.cosys.impbd.shipment.inventory.dao.ShipmentInventoryDAO#
	 * getInventoryHouse(com.ngen.cosys.impbd.shipment.breakdown.model.
	 * InboundBreakdownShipmentHouseModel)
	 */
	@Override
	public Boolean getInventoryHouse(InboundBreakdownShipmentHouseModel house) throws CustomException {
		return super.fetchObject(ShipmentInventorySqlId.SQL_CHECK_HOUSE.getQueryId(), house, sqlSessionTemplate);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ngen.cosys.impbd.shipment.inventory.dao.ShipmentInventoryDAO#
	 * createInventoryHouse(com.ngen.cosys.impbd.shipment.breakdown.model.
	 * InboundBreakdownShipmentHouseModel)
	 */
	@Override
	public void createInventoryHouse(InboundBreakdownShipmentHouseModel house) throws CustomException {
		super.insertData(ShipmentInventorySqlId.SQL_INSERT_HOUSE.getQueryId(), house, sqlSessionTemplate);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ngen.cosys.impbd.shipment.inventory.dao.ShipmentInventoryDAO#
	 * updateInventoryHouse(com.ngen.cosys.impbd.shipment.breakdown.model.
	 * InboundBreakdownShipmentHouseModel)
	 */
	@Override
	public void updateInventoryHouse(InboundBreakdownShipmentHouseModel house) throws CustomException {
		super.updateData(ShipmentInventorySqlId.SQL_UPDATE_HOUSE_PIECE_WEIGHT.getQueryId(), house, sqlSessionTemplate);
	}

	@Override
	public List<InboundUldFlightModel> getFlightInforForULd(InboundUldFlightModel breakdownModel)
			throws CustomException {
		return super.fetchList("sqlgetFlightInforForULd", breakdownModel, sqlSessionTemplate);

	}

	@Override
	public void createHouseAWBInfo(InboundBreakdownHAWBModel hawbInfo) throws CustomException {
		super.insertData(ShipmentInventorySqlId.SQL_INSERT_HAWB_FOR_SHIPMENT.getQueryId(), hawbInfo, sqlSessionTemplate);
		// update Shipment HandledByMasterHouse
		ShipmentMaster shipmentData=new ShipmentMaster();
		shipmentData.setShipmentNumber(hawbInfo.getShipmentNumber());
		shipmentData.setShipmentdate(hawbInfo.getShipmentdate());
		shipmentData.setHandledByMasterHouse("H");
		shipmentData.setChargeableWeight(hawbInfo.getHawbChargebleWeight());
		super.updateData("sqlUpdateShipmentMasterhandledByMasterHouse", shipmentData, sqlSessionTemplate);
		
	}

	@Override
	public InboundBreakdownShipmentModel getShipmentHousesBreakDownPices(InboundBreakdownShipmentModel requestData)
			throws CustomException {
		return super.fetchObject("sqlGetTotalShipmentHousesBreakdownPiecesWeight", requestData, sqlSessionTemplate);
	}
	
	

}