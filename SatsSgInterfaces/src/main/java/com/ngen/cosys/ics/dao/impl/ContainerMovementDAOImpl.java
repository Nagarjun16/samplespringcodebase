package com.ngen.cosys.ics.dao.impl;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.ngen.cosys.framework.dao.BaseDAO;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.ics.dao.ContainerMovementDAO;
import com.ngen.cosys.ics.model.ContainerMovementModel;

@Repository
public class ContainerMovementDAOImpl extends BaseDAO implements ContainerMovementDAO {

   @Autowired
   @Qualifier("sqlSessionTemplate")
   private SqlSession sqlSession;

   @Override
   public Integer getContainerMovementStatus(ContainerMovementModel requestModel) throws CustomException {
      updateData("containerMovementStatusId", requestModel, sqlSession);
      return updateData("containerMovementStatusIdForUldMaster", requestModel, sqlSession);
   }

}
