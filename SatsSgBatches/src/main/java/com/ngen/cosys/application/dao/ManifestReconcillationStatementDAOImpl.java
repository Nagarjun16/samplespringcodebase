package com.ngen.cosys.application.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.ngen.cosys.framework.dao.BaseDAO;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.model.ManifesrReconciliationStatementModel;
    

@Repository("customsMRSDAO")
public class ManifestReconcillationStatementDAOImpl extends BaseDAO implements ManifestReconcillationStatementDAO {
   @Autowired
   @Qualifier("sqlSessionTemplate")
   private SqlSessionTemplate sqlSession;
   @Override
   public ManifesrReconciliationStatementModel getConsolidatedMrsInfo(
         ManifesrReconciliationStatementModel manifesrReconciliationStatementModel) throws CustomException {
      return this.fetchObject("getConsolidatedMrsInfo", manifesrReconciliationStatementModel,
            sqlSession);
    
   }
   @Override
   public List<ManifesrReconciliationStatementModel> getFlightsToSendMrs() throws CustomException {
      return super.fetchList("sqlGetFlightsToSendMrs", null, sqlSession);
   }
   
   @Override
   public void insertMrsOutInfo(ManifesrReconciliationStatementModel mrs) throws CustomException {

      insertData("insertMrsOutInfo", mrs, sqlSession);

   }
   @Override
   public void updateMrsSentDateToCustomsFlight(ManifesrReconciliationStatementModel mrs) throws CustomException {

      updateData("updateMrsSentDateToCustomsFlight", mrs, sqlSession);

   }
   @Override
   public void updateMrsSentDateToLinkMrs(ManifesrReconciliationStatementModel mrs) throws CustomException {

      updateData("updateMrsSentDateToLinkMrs", mrs, sqlSession);

   }
   @Override
   public String getMrsDestinationAddress(
         ) throws CustomException {
      return this.fetchObject("getMrsDestinationAddress", null,
            sqlSession);

   }
   @Override
   public String getMrsPimaAddress(
         ) throws CustomException {
      return this.fetchObject("getMrsPimaAddress", null,
            sqlSession);

   }
   @Override
   public String getMrsOriginatorAddress(
         ) throws CustomException {
      return this.fetchObject("getMrsOriginatorAddress", null,
            sqlSession);

   }
}
