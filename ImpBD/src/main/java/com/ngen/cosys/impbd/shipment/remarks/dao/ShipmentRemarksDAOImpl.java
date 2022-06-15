package com.ngen.cosys.impbd.shipment.remarks.dao;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.ngen.cosys.framework.dao.BaseDAO;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.impbd.shipment.remarks.constant.ShipmentRemarksSqlId;
import com.ngen.cosys.impbd.shipment.remarks.model.ShipmentRemarksModel;

@Repository
public class ShipmentRemarksDAOImpl extends BaseDAO implements ShipmentRemarksDAO {

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
	public void create(ShipmentRemarksModel shipmentRemarksModel) throws CustomException {
		this.insertData(ShipmentRemarksSqlId.INSERT_SHIPMENT_REMARKS.getQueryId(), shipmentRemarksModel, sqlSession);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ngen.cosys.impbd.shipment.remarks.dao.ShipmentRemarksDAO#get(com.ngen.
	 * cosys.impbd.shipment.remarks.model.ShipmentRemarksModel)
	 */
	@Override
	public ShipmentRemarksModel get(ShipmentRemarksModel shipmentRemarksModel) throws CustomException {
		return this.fetchObject(ShipmentRemarksSqlId.GET_SHIPMENT_REMARKS.getQueryId().toString(), shipmentRemarksModel,
				sqlSession);
	}

}