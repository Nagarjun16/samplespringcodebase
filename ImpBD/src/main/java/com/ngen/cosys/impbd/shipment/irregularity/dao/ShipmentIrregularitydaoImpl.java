package com.ngen.cosys.impbd.shipment.irregularity.dao;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.ngen.cosys.framework.dao.BaseDAO;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.impbd.shipment.irregularity.constant.ShipmentIrregularitySqlId;
import com.ngen.cosys.impbd.shipment.irregularity.model.ShipmentIrregularityModel;

@Repository
public class ShipmentIrregularitydaoImpl extends BaseDAO implements ShipmentIrregularityDAO {

   @Autowired
   @Qualifier("sqlSessionTemplate")
   private SqlSessionTemplate sqlSession;

   @Override
   public void insert(ShipmentIrregularityModel shipmentIrregularityModel) throws CustomException {
      super.insertData(ShipmentIrregularitySqlId.CREATE_SHIPMENT_IRREGULARITY.getQueryId(), shipmentIrregularityModel,
            sqlSession);
   }

   @Override
   public ShipmentIrregularityModel get(ShipmentIrregularityModel shipmentIrregularityModel) throws CustomException {
      return super.fetchObject(ShipmentIrregularitySqlId.GET_SHIPMENT_IRREGULARITY.getQueryId(),
            shipmentIrregularityModel, sqlSession);
   }

   @Override
   public void delete(ShipmentIrregularityModel shipmentIrregularityModel) throws CustomException {
      super.insertData(ShipmentIrregularitySqlId.DELETE_SHIPMENT_IRREGULARITY.getQueryId(), shipmentIrregularityModel,
            sqlSession);
   }

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ngen.cosys.impbd.shipment.irregularity.dao.ShipmentIrregularityDAO#
	 * validatePieceInfoForMorethanOneHouseShipment(com.ngen.cosys.impbd.shipment.
	 * irregularity.model.ShipmentIrregularityModel)
	 */
	@Override
	public Boolean validatePieceInfoForMorethanOneHouseShipment(ShipmentIrregularityModel shipmentIrregularityModel)
			throws CustomException {
		return super.fetchObject(
				ShipmentIrregularitySqlId.CHECK_MANIFESTED_PIECE_MATCHES_FOR_SHIPMENT_HOUSE_BREAK_DOWN_IRREGULARITY
						.getQueryId(),
				shipmentIrregularityModel, sqlSession);
	}

}