/**
 * This is a repository implementation class for persisting shipment inventory/shipment master house and shc
 */
package com.ngen.cosys.satssginterfaces.mss.dao;

import java.math.BigInteger;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.ngen.cosys.framework.dao.BaseDAO;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.satssginterfaces.mss.enums.ShipmentInventoryAirmailInterfaceSqlId;
import com.ngen.cosys.satssginterfaces.mss.model.InboundBreakdownShipmentHouseAirmailInterfaceModel;
import com.ngen.cosys.satssginterfaces.mss.model.InboundBreakdownShipmentInventoryAirmailInterfaceModel;
import com.ngen.cosys.satssginterfaces.mss.model.InboundBreakdownShipmentShcAirmailInterfaceModel;

@Repository
public class ShipmentInventoryAirmailInterfaceDAOImpl extends BaseDAO implements ShipmentInventoryAirmailInterfaceDAO {

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
	public void createInventory(InboundBreakdownShipmentInventoryAirmailInterfaceModel inventory) throws CustomException {
		super.insertData(ShipmentInventoryAirmailInterfaceSqlId.SQL_INSERT_INVENTORY.getQueryId(), inventory, sqlSessionTemplate);
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
	public BigInteger getInventory(InboundBreakdownShipmentInventoryAirmailInterfaceModel inventory) throws CustomException {
		return super.fetchObject(ShipmentInventoryAirmailInterfaceSqlId.SQL_CHECK_INVENTORY.getQueryId(), inventory,
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
	public void updateInventory(InboundBreakdownShipmentInventoryAirmailInterfaceModel inventory) throws CustomException {
		super.updateData(ShipmentInventoryAirmailInterfaceSqlId.SQL_UPDATE_PIECE_WEIGHT.getQueryId(), inventory, sqlSessionTemplate);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ngen.cosys.impbd.shipment.inventory.dao.ShipmentInventoryDAO#getShc(com.
	 * ngen.cosys.impbd.shipment.breakdown.model.InboundBreakdownShipmentShcModel)
	 */
	@Override
	public Boolean getShc(InboundBreakdownShipmentShcAirmailInterfaceModel shc) throws CustomException {
		return super.fetchObject(ShipmentInventoryAirmailInterfaceSqlId.SQL_CHECK_SHC.getQueryId(), shc, sqlSessionTemplate);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ngen.cosys.impbd.shipment.inventory.dao.ShipmentInventoryDAO#
	 * createInventoryShc(com.ngen.cosys.impbd.shipment.breakdown.model.
	 * InboundBreakdownShipmentShcModel)
	 */
	@Override
	public void createInventoryShc(InboundBreakdownShipmentShcAirmailInterfaceModel shc) throws CustomException {
		super.insertData(ShipmentInventoryAirmailInterfaceSqlId.SQL_INSERT_SHC.getQueryId(), shc, sqlSessionTemplate);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ngen.cosys.impbd.shipment.inventory.dao.ShipmentInventoryDAO#
	 * getShipmentMasterHouse(com.ngen.cosys.impbd.shipment.breakdown.model.
	 * InboundBreakdownShipmentHouseModel)
	 */
	@Override
	public BigInteger getShipmentMasterHouse(InboundBreakdownShipmentHouseAirmailInterfaceModel house) throws CustomException {
		return super.fetchObject(ShipmentInventoryAirmailInterfaceSqlId.SQL_GET_SHIPMENTHOUSEINFO.getQueryId(), house,
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
	public void createShipmentMasterHouse(InboundBreakdownShipmentHouseAirmailInterfaceModel house) throws CustomException {
		super.insertData(ShipmentInventoryAirmailInterfaceSqlId.SQL_CREATE_SHIPMENTHOUSEINFO.getQueryId(), house, sqlSessionTemplate);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ngen.cosys.impbd.shipment.inventory.dao.ShipmentInventoryDAO#
	 * updateShipmentMasterHouse(com.ngen.cosys.impbd.shipment.breakdown.model.
	 * InboundBreakdownShipmentHouseModel)
	 */
	@Override
	public void updateShipmentMasterHouse(InboundBreakdownShipmentHouseAirmailInterfaceModel house) throws CustomException {
		super.updateData(ShipmentInventoryAirmailInterfaceSqlId.SQL_UPDATE_SHIPMENTHOUSEINFO.getQueryId(), house, sqlSessionTemplate);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ngen.cosys.impbd.shipment.inventory.dao.ShipmentInventoryDAO#
	 * getInventoryHouse(com.ngen.cosys.impbd.shipment.breakdown.model.
	 * InboundBreakdownShipmentHouseModel)
	 */
	@Override
	public Boolean getInventoryHouse(InboundBreakdownShipmentHouseAirmailInterfaceModel house) throws CustomException {
		return super.fetchObject(ShipmentInventoryAirmailInterfaceSqlId.SQL_CHECK_HOUSE.getQueryId(), house, sqlSessionTemplate);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ngen.cosys.impbd.shipment.inventory.dao.ShipmentInventoryDAO#
	 * createInventoryHouse(com.ngen.cosys.impbd.shipment.breakdown.model.
	 * InboundBreakdownShipmentHouseModel)
	 */
	@Override
	public void createInventoryHouse(InboundBreakdownShipmentHouseAirmailInterfaceModel house) throws CustomException {
		super.insertData(ShipmentInventoryAirmailInterfaceSqlId.SQL_INSERT_HOUSE.getQueryId(), house, sqlSessionTemplate);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ngen.cosys.impbd.shipment.inventory.dao.ShipmentInventoryDAO#
	 * updateInventoryHouse(com.ngen.cosys.impbd.shipment.breakdown.model.
	 * InboundBreakdownShipmentHouseModel)
	 */
	@Override
	public void updateInventoryHouse(InboundBreakdownShipmentHouseAirmailInterfaceModel house) throws CustomException {
		super.updateData(ShipmentInventoryAirmailInterfaceSqlId.SQL_UPDATE_HOUSE_PIECE_WEIGHT.getQueryId(), house, sqlSessionTemplate);
	}

}