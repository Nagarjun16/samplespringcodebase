package com.ngen.cosys.satssginterfaces.mss.dao;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.ngen.cosys.framework.dao.BaseDAO;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.satssginterfaces.mss.enums.ShipmentRemarksAirmailInterfaceSqlId;
import com.ngen.cosys.satssginterfaces.mss.model.ShipmentRemarksAirmailInterfaceModel;

@Repository
public class ShipmentRemarksAirmailInterfaceDAOImpl extends BaseDAO implements ShipmentRemarksAirmailInterfaceDAO {

	@Autowired
	@Qualifier("sqlSessionTemplate")
	private SqlSessionTemplate sqlSession;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ngen.cosys.impbd.shipment.remarks.dao.ShipmentRemarksDAO#create(com.ngen.
	 * cosys.impbd.shipment.remarks.model.ShipmentRemarksModel)
	 */
	@Override
	public void create(ShipmentRemarksAirmailInterfaceModel shipmentRemarksModel) throws CustomException {
		this.insertData(ShipmentRemarksAirmailInterfaceSqlId.INSERT_SHIPMENT_REMARKS.getQueryId(), shipmentRemarksModel, sqlSession);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ngen.cosys.impbd.shipment.remarks.dao.ShipmentRemarksDAO#get(com.ngen.
	 * cosys.impbd.shipment.remarks.model.ShipmentRemarksModel)
	 */
	@Override
	public ShipmentRemarksAirmailInterfaceModel get(ShipmentRemarksAirmailInterfaceModel shipmentRemarksModel) throws CustomException {
		return this.fetchObject(ShipmentRemarksAirmailInterfaceSqlId.GET_SHIPMENT_REMARKS.getQueryId().toString(), shipmentRemarksModel,
				sqlSession);
	}

}