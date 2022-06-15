package com.ngen.cosys.ForeignUld.Dao;

import java.time.LocalDateTime;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ngen.cosys.ForeignUld.Model.ForeignUldArrivedOnIncomingFlightModel;
import com.ngen.cosys.framework.dao.BaseDAO;
import com.ngen.cosys.framework.exception.CustomException;

@Repository
public class ForeignUldArrivedOnIncomingFlightDaoImpl extends BaseDAO implements ForeignUldArrivedOnIncomingFlightDao {

   @Autowired
   private SqlSessionTemplate sqlSession;

   @Override
   public String getReportOnTheBasisOfCarriercode() throws CustomException {
      return this.fetchObject("getReportOnTheBasisOfCarriercode", null, sqlSession);
   }

   @Override
   public List<String> getEmailAddresses(String carrierCode) throws CustomException {
      return this.fetchList("getEmailAddresses", carrierCode, sqlSession);
   }

   @Override
   public void updateLatestFromDateForForeignULD() throws CustomException {
      this.updateData("sqlUpdateLatestFromDateForForeignULD", LocalDateTime.now(), sqlSession);
   }

   @Override
   public ForeignUldArrivedOnIncomingFlightModel getParametersForForeignUldsReport() throws CustomException {
      return this.fetchObject("sqlGetFromDateForForeignUldsCarrier", null, sqlSession);
   }

}
