package com.ngen.cosys.satssg.interfaces.singpost.dao;

import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ngen.cosys.framework.dao.BaseDAO;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.satssg.interfaces.singpost.model.UldAutoWeightModel;

@Repository("UpdateUldAutoWeightDao")
public class UpdateUldAutoWeightDaoImpl extends BaseDAO implements UpdateUldAutoWeightDao {

   @Autowired
   private SqlSession sqlSessionTemplate;

   @Override
   public Integer updateUldAutoWeight(UldAutoWeightModel uldAutoWeightModel) throws CustomException {
      return updateData("updateUldAutoWeight", uldAutoWeightModel, sqlSessionTemplate);
   }

   /*
    * (non-Javadoc)
    * 
    * @see com.ngen.cosys.satssg.interfaces.singpost.dao.UpdateUldAutoWeightDao#
    * insertUpdateUldAutoWeightRequest(com.ngen.cosys.satssg.interfaces.singpost.
    * model.UldAutoWeightModel)
    */
   @Override
   public void insertUpdateUldAutoWeightRequest(UldAutoWeightModel request) throws CustomException {
      this.insertData("sqlInsertUpdateAutoWeightRequest", request, sqlSessionTemplate);
   }

   /*
    * (non-Javadoc)
    * 
    * @see com.ngen.cosys.satssg.interfaces.singpost.dao.UpdateUldAutoWeightDao#
    * logMasterAuditData(java.util.Map)
    */
   @Override
   public void logMasterAuditData(Map<String, Object> auditMap) throws CustomException {
      this.insertData("sqlInsertUpdateAutoWeightRequestAuditData", auditMap, sqlSessionTemplate);
   }

}