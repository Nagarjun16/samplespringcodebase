/**
 * This is a class which holds entire functionality for managing of shipment verification
 * 
 * @author NIIT Technologies Pvt Ltd
 * @version 1.0
 * @since 23/03/2018
 */
package com.ngen.cosys.satssginterfaces.mss.dao;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.ngen.cosys.framework.dao.BaseDAO;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.satssginterfaces.mss.enums.ShipmentVerificationAirmailInterfaceSqlId;
import com.ngen.cosys.satssginterfaces.mss.model.ShipmentVerificationAirmailInterfaceModel;

@Repository
public class ShipmentVerificationAirlineInterfaceDAOImpl extends BaseDAO implements ShipmentVerificationAirlineInterfaceDAO {

	@Autowired
	@Qualifier("sqlSessionTemplate")
	private SqlSessionTemplate sqlSessionTemplate;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ngen.cosys.impbd.shipment.verification.dao.ShipmentVerificationDAO#create
	 * (com.ngen.cosys.impbd.shipment.verification.model.ShipmentVerificationModel)
	 */
	@Override
	public void create(ShipmentVerificationAirmailInterfaceModel shipmentVerification) throws CustomException {
		this.insertData(ShipmentVerificationAirmailInterfaceSqlId.SQL_INSERT_SHIPMENT_VERIFICATION.toString(), shipmentVerification,
				sqlSessionTemplate);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ngen.cosys.impbd.shipment.verification.dao.ShipmentVerificationDAO#update
	 * (com.ngen.cosys.impbd.shipment.verification.model.ShipmentVerificationModel)
	 */
	@Override
	public void update(ShipmentVerificationAirmailInterfaceModel shipmentVerification) throws CustomException {
		this.updateData(ShipmentVerificationAirmailInterfaceSqlId.SQL_UPDATE_SHIPMENT_VERIFICATION.toString(), shipmentVerification,
				sqlSessionTemplate);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ngen.cosys.impbd.shipment.verification.dao.ShipmentVerificationDAO#get(
	 * com.ngen.cosys.impbd.shipment.verification.model.ShipmentVerificationModel)
	 */
	@Override
	public ShipmentVerificationAirmailInterfaceModel get(ShipmentVerificationAirmailInterfaceModel shipmentVerification) throws CustomException {
		return this.fetchObject(ShipmentVerificationAirmailInterfaceSqlId.SQL_GET_SHIPMENT_VERIFICATION.toString(),
				shipmentVerification, sqlSessionTemplate);
	}

	@Override
	public Integer updateDgList(ShipmentVerificationAirmailInterfaceModel shipmentVerification) throws CustomException {
		return this.updateData(ShipmentVerificationAirmailInterfaceSqlId.SQL_UPDATE_DG_CHECKLIST.toString(), shipmentVerification,
				sqlSessionTemplate);
	}

	@Override
	public void insertDgList(ShipmentVerificationAirmailInterfaceModel shipmentVerification) throws CustomException {
		this.insertData(ShipmentVerificationAirmailInterfaceSqlId.SQL_INSERT_DG_CHECKLIST.toString(), shipmentVerification,
				sqlSessionTemplate);
		
	}

}