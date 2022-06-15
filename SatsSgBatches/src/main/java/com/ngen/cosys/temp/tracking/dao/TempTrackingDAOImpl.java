package com.ngen.cosys.temp.tracking.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.ngen.cosys.framework.dao.BaseDAO;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.temp.tracking.model.TempTrackingRequestModel;

@Repository("TempTrackingDAO")
public class TempTrackingDAOImpl extends BaseDAO implements TempTrackingDAO {

   @Autowired
   @Qualifier("sqlSessionTemplate")
   private SqlSessionTemplate sqlSession;
   
   
   @Override
   public List<TempTrackingRequestModel> getTempTrackingDetails() throws CustomException {
      return super.fetchList("fetTempTrackingShipmentId", null, sqlSession);
   }
   
   
   
   
}
