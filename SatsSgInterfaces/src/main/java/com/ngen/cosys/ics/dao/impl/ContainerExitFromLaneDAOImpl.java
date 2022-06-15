package com.ngen.cosys.ics.dao.impl;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.ngen.cosys.framework.dao.BaseDAO;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.ics.dao.ContainerExitFromLaneDAO;
import com.ngen.cosys.ics.model.ContainerExitFromLaneModel;

@Repository
public class ContainerExitFromLaneDAOImpl extends BaseDAO implements ContainerExitFromLaneDAO {

   @Autowired
   @Qualifier("sqlSessionTemplate")
   private SqlSession sqlSession;

   @Override
   public Integer getLaneInformationForContainerExit(ContainerExitFromLaneModel requestModel) throws CustomException {
      updateData("containerExitFromLaneId", requestModel, sqlSession);
      Integer updateData = updateData("containerExitFromLaneIdForUldMaster", requestModel, sqlSession);
      return updateData;
   }

}
