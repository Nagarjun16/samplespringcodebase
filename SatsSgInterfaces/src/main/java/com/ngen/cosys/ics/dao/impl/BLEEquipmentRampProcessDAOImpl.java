package com.ngen.cosys.ics.dao.impl;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.ngen.cosys.framework.dao.BaseDAO;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.ics.dao.BLEEquipmentRampProcessDAO;
import com.ngen.cosys.ics.model.BLEEquipmentRampProcessRequestModel;

@Repository
public class BLEEquipmentRampProcessDAOImpl extends BaseDAO implements BLEEquipmentRampProcessDAO {

   @Autowired
   @Qualifier("sqlSessionTemplate")
   private SqlSession sqlSession;

   @Override
   public String isPalletDollyExist(BLEEquipmentRampProcessRequestModel request) throws CustomException {
      return fetchObject("isPalletDollyExistSqlId", request, sqlSession);
   }

   @Override
   public String fetchTripInfoIdBasedOnPalletID(BLEEquipmentRampProcessRequestModel request) throws CustomException {
      return fetchObject("getTripInfoIdBasedOnPD", request, sqlSession);
   }

   @Override
   public Integer updateRampProcessForRAMPOUT(BLEEquipmentRampProcessRequestModel request) throws CustomException {
      return updateData("updateRampProcessForRAMPOUTID", request, sqlSession);
   }

   public Integer insertPDInOutMovements(BLEEquipmentRampProcessRequestModel request) throws CustomException {
      return insertData("insertPDInOutMovementId", request, sqlSession);
   }

   @Override
   public String fetchPDMasterIdBasedOnPalletID(BLEEquipmentRampProcessRequestModel request) throws CustomException {
      return fetchObject("getPDMasterIdBasedOnPDId", request, sqlSession);
   }

}
