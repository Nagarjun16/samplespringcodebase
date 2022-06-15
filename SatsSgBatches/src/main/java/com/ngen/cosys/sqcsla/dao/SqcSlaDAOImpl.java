package com.ngen.cosys.sqcsla.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.ngen.cosys.framework.dao.BaseDAO;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.sqcsla.model.Emails;

@Repository("sqcSlaDAOImpl")
public class SqcSlaDAOImpl extends BaseDAO implements SqcSlaDAO {

   @Autowired
   @Qualifier("sqlSessionTemplate")
   private SqlSessionTemplate sqlSession;

   @Override
   public List<Emails> getSqcGroupEmail() throws CustomException {
      return super.fetchList("sqlSQCGroupEmail", null, sqlSession);
   }
   
   @Override
   public List<Emails> getCagGroupEmail() throws CustomException {
      return super.fetchList("sqlCAGGroupEmail", null, sqlSession);
   }
}
