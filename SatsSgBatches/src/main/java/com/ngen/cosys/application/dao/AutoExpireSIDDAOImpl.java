package com.ngen.cosys.application.dao;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ngen.cosys.framework.dao.BaseDAO;
import com.ngen.cosys.framework.exception.CustomException;

@Repository
public class AutoExpireSIDDAOImpl extends BaseDAO implements AutoExpireSIDDAO {

   @Autowired
   private SqlSessionTemplate sqlSessionTemplate;

   /*
    * (non-Javadoc)
    * 
    * @see com.ngen.cosys.application.dao.AutoExpireSIDDAO#expireSID()
    */
   @Override
   public void expireSID() throws CustomException {
      this.updateData("sqlQueryAutoExpireSID", new Object(), sqlSessionTemplate);
   }

}
